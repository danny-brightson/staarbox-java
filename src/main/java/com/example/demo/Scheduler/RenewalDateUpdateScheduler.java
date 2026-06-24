package com.example.demo.Scheduler;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.SchedulerLog;
import com.example.demo.projection.RenewalSchedulerProjection;
import com.example.demo.repo.CustomerDetailsRepo;
import com.example.demo.repo.SchedulerLogRepo;

@Component
public class RenewalDateUpdateScheduler {

    @Autowired
    private CustomerDetailsRepo customerRepo;

    @Autowired
    private SchedulerLogRepo schedulerLogRepo;

    @Scheduled(cron = "0 0 9 * * *", zone = "Asia/Kolkata")
    @Transactional
    public void updateRenewalDateScheduler() {

        SchedulerLog log = new SchedulerLog();

        log.setJobName("RENEWAL_DATE_UPDATE_JOB");
        log.setStartTime(LocalDateTime.now());
        log.setStatus("STARTED");

        int total = 0;
        int success = 0;
        int failure = 0;
        int skippedCancelled = 0;
        int skippedWalletCondition = 0;

        try {

            LocalDate today = LocalDate.now();

            // Skip Sunday
            if (today.getDayOfWeek() == DayOfWeek.SUNDAY) {

                log.setStatus("SKIPPED_SUNDAY");
                log.setEndTime(LocalDateTime.now());

                schedulerLogRepo.save(log);
                return;
            }

            List<RenewalSchedulerProjection> customers =
                    customerRepo.findCustomersForRenewalScheduler();

            total = customers.size();

            for (RenewalSchedulerProjection customer : customers) {

                try {

                    // Skip cancelled today
                    if (customer.getCancelledCount() > 0) {

                        skippedCancelled++;
                        continue;
                    }

                    BigDecimal walletAmount =
                            customer.getWalletAmount();

                    BigDecimal minAmount =
                            customer.getMinAmount();

                    BigDecimal maxAmount =
                            customer.getMaxAmount();

                    boolean shouldUpdate =
                            walletAmount.compareTo(minAmount) > 0
                            &&
                            walletAmount.compareTo(maxAmount) < 0;

                    if (shouldUpdate) {

                        customerRepo.updateRenewalDate(
                                customer.getCustomerId(),
                                Date.valueOf(today));

                        success++;

                    } else {

                        skippedWalletCondition++;
                    }

                } catch (Exception ex) {

                    failure++;
                    ex.printStackTrace();
                }
            }

            log.setStatus("SUCCESS");

            log.setRemarks(
                    "Total=" + total +
                    ", Updated=" + success +
                    ", CancelledSkipped=" + skippedCancelled +
                    ", WalletConditionSkipped=" + skippedWalletCondition +
                    ", Failed=" + failure);

        } catch (Exception e) {

            log.setStatus("FAILED");
            log.setErrorMessage(e.getMessage());
        }

        finally {

            log.setEndTime(LocalDateTime.now());
            log.setProcessedCount(total);

            schedulerLogRepo.save(log);
        }
    }
}
