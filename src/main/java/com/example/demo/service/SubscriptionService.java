package com.example.demo.service;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.CustomerDetails;
import com.example.demo.dto.RenewalResponse;
import com.example.demo.entity.StagingRenewal;
import com.example.demo.repo.CustomerDetailsRepo;
import com.example.demo.repo.StagingRenewalRepo;


@Service
public class SubscriptionService {

    @Autowired
    private CustomerDetailsRepo customerRepo;

    @Autowired
    private StagingRenewalRepo stagingRepo;

    private static final int DELIVERY_DAYS = 26;

    public String updatePackDetails(Long customerId, Long newPackId) {

        CustomerDetails customer = customerRepo.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        
        // Long currentPack = customer.getPackDetailsId() != 0
        
        //         ? Long.valueOf(customer.getPackDetailsId())
        //         : null;
        Long currentPack = Long.valueOf(customer.getPackDetailsId());

        LocalDateTime renewalDate = customer.getNextrenewalDate() != null
                ? customer.getNextrenewalDate().toInstant()
                    .atZone(java.time.ZoneId.systemDefault())
                    .toLocalDateTime()
                : null;

        LocalDateTime today = LocalDateTime.now();

        if (renewalDate == null) {
            throw new RuntimeException("Renewal date missing");
        }

        // if (currentPack != null && currentPack.equals(newPackId)) {
        //     return "No change in pack";
        // }

        
        // if (!renewalDate.isAfter(today)) {
        //     customerRepo.updatePackDirect(customerId, newPackId);
        //     return "Pack updated directly";
        // }

       
        Optional<StagingRenewal> existing = stagingRepo.findByCustomerId(customerId);

        StagingRenewal s;
        if (existing.isPresent()) {
             s = existing.get();
        }

        //     StagingRenewal s = existing.get();
        //     s.setNewPackId(newPackId);
        //     s.setPaymentDate(LocalDateTime.now());
        //     stagingRepo.save(s);

        //     return "Staging updated";

        // } 
        else {

            s = new StagingRenewal();
            s.setCustomerId(customerId);
            s.setOldPackId(currentPack);
            // s.setNewPackId(newPackId);
            // s.setPaymentDate(LocalDateTime.now());
            s.setNextRenewalDate(renewalDate);
            s.setProcessed(false);

        }
            s.setNewPackId(newPackId);
            s.setPaymentDate(LocalDateTime.now());

            // current renewal date (from customer)
            s.setCurrentRenewalDate(renewalDate);

            // upcoming renewal date (example logic)
            // s.setUpcomingRenewalDate(renewalDate.plusDays(30));

            // ✅ 26 delivery days excluding Sunday
        LocalDateTime upcomingDate = addDeliveryDays(renewalDate, DELIVERY_DAYS);
        s.setUpcomingRenewalDate(upcomingDate);


            // renewal done time
            s.setRenewalDate(LocalDateTime.now());

            System.out.println("Saving staging:");
            System.out.println("CustomerId: " + s.getCustomerId());
            System.out.println("OldPackId: " + s.getOldPackId());
            System.out.println("NewPackId: " + s.getNewPackId());
            
            stagingRepo.save(s);
            return "Saved in staging";
    }

        // CORE SUBSCRIPTION LOGIC
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


    public RenewalResponse getRenewalDetails(Long customerId) {

        CustomerDetails customer = customerRepo.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        Optional<StagingRenewal> stagingOpt = stagingRepo.findByCustomerId(customerId);

        RenewalResponse response = new RenewalResponse();

        response.setCustomerId(customerId);
        response.setCurrentPackId(Long.valueOf(customer.getPackDetailsId()));
        response.setCurrentNextRenewalDate(customer.getNextrenewalDate());

        if (stagingOpt.isPresent()) {
            StagingRenewal s = stagingOpt.get();

            response.setRenewalAvailable(true);
            response.setNewPackId(s.getNewPackId());
            response.setPaymentDate(s.getPaymentDate());
            response.setUpcomingRenewalDate(s.getNextRenewalDate());
            response.setUpcomingRenewalDate(s.getUpcomingRenewalDate());
        } else {
            response.setRenewalAvailable(false);
        }
        return response;
    }
}