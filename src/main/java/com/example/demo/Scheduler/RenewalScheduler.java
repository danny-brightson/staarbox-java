package com.example.demo.Scheduler;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.demo.entity.StagingRenewal;
import com.example.demo.repo.CustomerDetailsRepo;
import com.example.demo.repo.StagingRenewalRepo;

@Component
public class RenewalScheduler {

    @Autowired
    private StagingRenewalRepo stagingRepo;

    @Autowired
    private CustomerDetailsRepo customerRepo;


    @Scheduled(cron = "0 0 0 * * ?")
    public void processRenewals() {

        System.out.println("Running Renewal Scheduler...");

        List<StagingRenewal> list = stagingRepo.findAll();

        for (StagingRenewal s : list) {

            if (Boolean.TRUE.equals(s.getProcessed())) {
                continue;
            }

            if (s.getNextRenewalDate() != null &&
                !s.getNextRenewalDate().isAfter(LocalDateTime.now())) {

                customerRepo.updatePackDirect(
                        s.getCustomerId(),
                        s.getNewPackId(),
                        s.getUpcomingRenewalDate()
                );

                s.setProcessed(true);
                stagingRepo.save(s);

                System.out.println("Processed customer: " + s.getCustomerId());
                System.out.println("Checking customer: " + s.getCustomerId());
                System.out.println("Renewal Date: " + s.getNextRenewalDate());
            }
        }
    }
}