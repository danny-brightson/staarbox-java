package com.example.demo.service;

import com.example.demo.entity.CustomerDetails;
import com.example.demo.entity.StagingRenewal;
import com.example.demo.repo.CustomerDetailsRepo;
import com.example.demo.repo.StagingRenewalRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;


@Service
public class SubscriptionService {

    @Autowired
    private CustomerDetailsRepo customerRepo;

    @Autowired
    private StagingRenewalRepo stagingRepo;

    public String updatePackDetails(Long customerId, Long newPackId) {

        CustomerDetails customer = customerRepo.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

    
        Long currentPack = customer.getPackDetailsId() != 0
                ? Long.valueOf(customer.getPackDetailsId())
                : null;

        LocalDateTime renewalDate = customer.getNextrenewalDate() != null
                ? customer.getNextrenewalDate().toInstant()
                    .atZone(java.time.ZoneId.systemDefault())
                    .toLocalDateTime()
                : null;

        LocalDateTime today = LocalDateTime.now();

        if (renewalDate == null) {
            throw new RuntimeException("Renewal date missing");
        }

        if (currentPack != null && currentPack.equals(newPackId)) {
            return "No change in pack";
        }

        
        if (!renewalDate.isAfter(today)) {
            customerRepo.updatePackDirect(customerId, newPackId);
            return "Pack updated directly";
        }

       
        Optional<StagingRenewal> existing = stagingRepo.findByCustomerId(customerId);

        if (existing.isPresent()) {

            StagingRenewal s = existing.get();
            s.setNewPackId(newPackId);
            s.setPaymentDate(LocalDateTime.now());
            stagingRepo.save(s);

            return "Staging updated";

        } else {

            StagingRenewal s = new StagingRenewal();
            s.setCustomerId(customerId);
            s.setOldPackId(currentPack);
            s.setNewPackId(newPackId);
            s.setPaymentDate(LocalDateTime.now());
            s.setNextRenewalDate(renewalDate);
            s.setProcessed(false);

            stagingRepo.save(s);

            return "Staging created";
        }
    }
}