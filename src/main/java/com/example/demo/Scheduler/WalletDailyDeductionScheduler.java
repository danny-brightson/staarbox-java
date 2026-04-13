package com.example.demo.Scheduler;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.demo.repo.CustomerDetailsRepo;
import com.example.demo.service.WalletService;

@Component
public class WalletDailyDeductionScheduler {

    @Autowired
    private WalletService walletService;

    @Autowired
    private CustomerDetailsRepo customerDetailsRepo;

    // Every day at 12:00 AM midnight
    @Scheduled(cron = "0 0 0 * * *", zone = "Asia/Kolkata")
    public void runDailyWalletDeduction() {

        System.out.println("==== Wallet Deduction Job Started ====");

        // 1. Customized customers
        List<Long> customizedIds = customerDetailsRepo.findAllCustomizedCustomerIds();
        for (Long customerId : customizedIds) {
            try {
                walletService.deductDailyWallet(customerId, true);
                System.out.println("Customized deduction done: " + customerId);
            } catch (Exception e) {
                System.out.println("Error for customer " + customerId + ": " + e.getMessage());
            }
        }

        // 2. Non-customized customers
        List<Long> nonCustomizedIds = customerDetailsRepo.findAllNonCustomizedActiveCustomerIds();
        for (Long customerId : nonCustomizedIds) {
            try {
                walletService.deductDailyWallet(customerId, false);
                System.out.println("Normal deduction done: " + customerId);
            } catch (Exception e) {
                System.out.println("Error for customer " + customerId + ": " + e.getMessage());
            }
        }

        System.out.println("==== Wallet Deduction Job Completed ====");
    }
}