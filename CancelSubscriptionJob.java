package com.example.demo.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.demo.repo.CustomerDetailsRepo;
import com.example.demo.repo.StagingRenewalRepo;

@Component
public class CancelSubscriptionJob {

    @Autowired
    private CustomerDetailsRepo customerDetailsRepo;

    @Autowired
    private StagingRenewalRepo stagingRepo;


    @Scheduled(cron = "0 59 23 * * ?")

    public void cancelSubscriptions() {

        System.out.println("Cron running at: " + LocalDateTime.now());
        customerDetailsRepo.cancelExpiredSubscriptions();

        System.out.println("Expired subscriptions cancelled successfully");
    }

        @Scheduled(cron = "0 59 23 * * ?")
    public void processRenewals() {
        // code above
    }
}