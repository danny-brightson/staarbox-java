package com.example.demo.Scheduler;
import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.SchedulerLog;
import com.example.demo.entity.Wallet;
import com.example.demo.projection.CustomerPackDistrictProjection;
import com.example.demo.repo.CustomerDetailsRepo;
import com.example.demo.repo.PackagingRepo;
import com.example.demo.repo.PricePerPackDetailsRepository;
import com.example.demo.repo.SchedulerLogRepo;
import com.example.demo.repo.WalletRepository;

@Component
public class WalletDeductionScheduler {

    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private CustomerDetailsRepo customerRepo;

    @Autowired
    private PackagingRepo packagingRepo;

    @Autowired
    private PricePerPackDetailsRepository priceRepo;

    @Autowired
    private SchedulerLogRepo schedulerLogRepo;
    
 

    @Scheduled(cron = "0 0 9 * * *", zone = "Asia/Kolkata")
    @Transactional
    public void walletDeductionScheduler() {

        SchedulerLog log = new SchedulerLog();
        log.setJobName("WALLET_DEDUCTION_JOB");
        log.setStartTime(LocalDateTime.now());
        log.setStatus("STARTED");

        int total = 0;
        int success = 0;
        int failure = 0;

        try {
            LocalDate today = LocalDate.now();

            // ? Skip Sunday
            if (today.getDayOfWeek() == DayOfWeek.SUNDAY) {
                log.setStatus("SKIPPED_SUNDAY");
                log.setEndTime(LocalDateTime.now());
                schedulerLogRepo.save(log);
                return;
            }

            // ? Get today's QR customers
            List<Long> customerIds = packagingRepo.findTodayCustomerIds(today);

            for (Long customerId : customerIds) {
                total++;

                try {
                    processCustomerDeduction(customerId, today);
                    success++;
                } catch (Exception e) {
                    failure++;
                    e.printStackTrace();
                }
            }

            log.setStatus("SUCCESS");

        } catch (Exception e) {
            log.setStatus("FAILED");
            log.setErrorMessage(e.getMessage());
        } finally {
            log.setEndTime(LocalDateTime.now());
            log.setProcessedCount(total);

            schedulerLogRepo.save(log);
        }
    }

    private void processCustomerDeduction(Long customerId, LocalDate today) {

        Wallet wallet = walletRepository.findByCustomerId(customerId)
                .orElseThrow(() -> new RuntimeException("Wallet not found"));

        BigDecimal balance = wallet.getAmount();

        CustomerPackDistrictProjection res =
                customerRepo.findPackAndDistrictByCustomerId(customerId);

        BigDecimal minAmount = priceRepo
                .findMinAmount(res.getPackDetailsId(), res.getDistrictId());

        if (minAmount == null) {
            throw new RuntimeException("Min amount not found");
        }

        BigDecimal amountToDeduct;

        // ? CASE 1: Customized yesterday
        if (wallet.getLastCustomaizedDate() != null &&
            wallet.getLastCustomaizedDate().toLocalDate().isEqual(today.minusDays(1)) &&
            wallet.getLastCustomizedAmount() != null) {

            amountToDeduct = wallet.getAmount() .subtract(wallet.getLastCustomizedAmount());
            		
        } else {
            // ? CASE 2: Normal deduction
            amountToDeduct =  wallet.getAmount() .subtract(minAmount);
        }

        // ? CASE 3: Balance check
        if (balance.compareTo(amountToDeduct) >= 0) {

            // ?? Deduct amount
            walletRepository.updateWalletAmount(customerId, amountToDeduct);

        } else {

            // ? Not enough balance ? skip deduction
            System.out.println("Skipping deduction for customer " + customerId);

           
            }
        }
    }
