package com.example.demo.service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.RenewalResponse;
import com.example.demo.entity.CustomerDetails;
import com.example.demo.entity.StagingRenewal;
import com.example.demo.repo.CustomerDetailsRepo;
import com.example.demo.repo.StagingRenewalRepo;

@Service
public class SubscriptionService {

    @Autowired
    private CustomerDetailsRepo customerRepo;

    @Autowired
    private StagingRenewalRepo stagingRepo;

    public String updatePackDetails(Long customerId, Long newPackId) {

        CustomerDetails customer = customerRepo.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        Long currentPack = Long.valueOf(customer.getPackDetailsId());

        // ✅ Fix LocalDate / Date conversion issue safely
       
     // ✅ REPLACE YOUR ENTIRE renewalDate BLOCK WITH THIS ONLY

        LocalDateTime renewalDate = null;

        if (customer.getNextrenewalDate() != null) {
        	renewalDate = customer.getNextrenewalDate()
                    .toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDateTime();
        }

        LocalDateTime today = LocalDateTime.now();

        // ✅ No renewal date = direct update
        if (renewalDate == null || !renewalDate.isAfter(today)) {

            LocalDateTime upcomingDate = addDeliveryDays(today, DELIVERY_DAYS);

            // If same pack selected → keep existing
            if (currentPack != null && currentPack.equals(newPackId)) {
                newPackId = currentPack;
            }

            customerRepo.updatePackDirect(customerId, newPackId, upcomingDate);

            // ✅ New start date = today
            customerRepo.updateStartDate(customerId, LocalDate.now());

            return "Pack updated directly";
        }

        // =========================================================
        // FUTURE RENEWAL → INSERT OR UPDATE STAGING TABLE
        // =========================================================
        Optional<StagingRenewal> existingOpt =
                stagingRepo.findLatestUnprocessed(customerId);

        StagingRenewal staging;

        if (existingOpt.isPresent()) {

            // ✅ UPDATE existing row instead of duplicate insert
            staging = existingOpt.get();

        } else {

            // ✅ CREATE new row only if no unprocessed row exists
            staging = new StagingRenewal();
            staging.setCustomerId(customerId);
            staging.setOldPackId(currentPack);
            staging.setProcessed(false);
        }

        // ✅ Always update latest requested values
        staging.setNewPackId(newPackId);
        staging.setPaymentDate(LocalDateTime.now());

        // Current customer renewal
        staging.setCurrentRenewalDate(renewalDate);

        // New cycle after current cycle ends
        LocalDateTime upcomingDate = addDeliveryDays(renewalDate, DELIVERY_DAYS);

        // Scheduler trigger date
        staging.setNextRenewalDate(renewalDate);

        // New pack cycle
        staging.setUpcomingRenewalDate(upcomingDate);

        // Start next day after current renewal
        staging.setRenewalStartDate(renewalDate.plusDays(1));

        System.out.println("Saving staging:");
        System.out.println("CustomerId: " + staging.getCustomerId());
        System.out.println("OldPackId: " + staging.getOldPackId());
        System.out.println("NewPackId: " + staging.getNewPackId());
        System.out.println("CurrentRenewalDate: " + staging.getCurrentRenewalDate());
        System.out.println("NextRenewalDate: " + staging.getNextRenewalDate());

        // ✅ Saves insert or update automatically
        stagingRepo.save(staging);

        return existingOpt.isPresent()
                ? "Existing staging updated"
                : "Saved in staging";
    }

    // =========================================================
    // CORE DELIVERY CALCULATION (Skip Sundays)
    // =========================================================
    private LocalDateTime addDeliveryDays(LocalDateTime startDate, int days) {

        LocalDateTime result = startDate;
        int count = 0;

        while (count < days) {
            result = result.plusDays(1);

            if (result.getDayOfWeek() != DayOfWeek.SUNDAY) {
                count++;
            }
        }

        return result;
    }

    // =========================================================
    // GET CUSTOMER RENEWAL DETAILS
    // =========================================================
    public RenewalResponse getRenewalDetails(Long customerId) {

        CustomerDetails customer = customerRepo.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        Optional<StagingRenewal> stagingOpt =
                stagingRepo.findLatestUnprocessed(customerId);

        RenewalResponse response = new RenewalResponse();

        response.setCustomerId(customerId);
        response.setCurrentPackId(Long.valueOf(customer.getPackDetailsId()));
        response.setCurrentNextRenewalDate(customer.getNextrenewalDate());

        if (stagingOpt.isPresent()) {

            StagingRenewal s = stagingOpt.get();

            response.setRenewalAvailable(true);
            response.setNewPackId(s.getNewPackId());
            response.setPaymentDate(s.getPaymentDate());

            // Current subscription end
            response.setUpcomingRenewalStartDate(s.getRenewalStartDate());

            // New subscription end after renewal
            response.setUpcomingRenewalEndDate(s.getUpcomingRenewalDate());

        } else {

            response.setRenewalAvailable(false);
        }
    }
}