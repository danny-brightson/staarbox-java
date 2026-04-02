package com.example.demo.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.demo.entity.StagingRenewal;
import com.example.demo.repo.CustomerDetailsRepo;
import com.example.demo.repo.StagingRenewalRepo;

@Component
public class CancelSubscriptionJob {

    @Autowired
    private CustomerDetailsRepo customerDetailsRepo;

    @Autowired
    private StagingRenewalRepo stagingRepo;


   // @Scheduled(cron = "0 59 23 * * ?")

   // public void cancelSubscriptions() {

    //    System.out.println("Cron running at: " + LocalDateTime.now());
    //    customerDetailsRepo.cancelExpiredSubscriptions();

    //    System.out.println("Expired subscriptions cancelled successfully");
   // }

    @Scheduled(cron = "0 59 23 * * ?")
    public void processRenewals() {

        System.out.println("Renewal Cron started at: " + LocalDateTime.now());

        List<StagingRenewal> stagingList = stagingRepo.findByProcessedFalse();

        for (StagingRenewal s : stagingList) {

            // ? NULL CHECK
            if (s.getNextRenewalDate() == null) {
                continue;
            }

            if (!s.getNextRenewalDate().isAfter(LocalDateTime.now())) {

                customerDetailsRepo.updatePackDirect(
                        s.getCustomerId(),
                        s.getNewPackId(),
                        s.getUpcomingRenewalDate()
                );

                s.setProcessed(true);
                stagingRepo.save(s);

                System.out.println("Processed customer: " + s.getCustomerId());
            }
        }

        System.out.println("Renewal Cron completed");
    }
}
