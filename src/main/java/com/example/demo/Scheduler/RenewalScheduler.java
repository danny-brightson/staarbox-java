package com.example.demo.Scheduler;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.demo.entity.SchedulerLog;
import com.example.demo.entity.StagingRenewal;
import com.example.demo.repo.CustomerDetailsRepo;
import com.example.demo.repo.SchedulerLogRepo;
import com.example.demo.repo.StagingRenewalRepo;

@Component
public class RenewalScheduler {

	@Autowired
	private StagingRenewalRepo stagingRepo;
	
	@Autowired
	private SchedulerLogRepo schedulerLogRepo;

	@Autowired
	private CustomerDetailsRepo customerRepo;

	@Scheduled(cron = "0 0 0 * * ?")
	public void processRenewals() {

		SchedulerLog log = new SchedulerLog();
		log.setJobName("Renewal Scheduler");
		log.setStartTime(LocalDateTime.now());
		log.setStatus("STARTED");

		int processedCount = 0;

		try {
			System.out.println("Running Renewal Scheduler...");

			List<StagingRenewal> list = stagingRepo.findAll();

			LocalDateTime startOfToday = LocalDateTime.now().toLocalDate().atStartOfDay();
			LocalDateTime startOfYesterday = startOfToday.minusDays(1);

			for (StagingRenewal s : list) {

				if (Boolean.TRUE.equals(s.getProcessed())) {
					continue;
				}

				if (s.getCurrentRenewalDate() != null && !s.getCurrentRenewalDate().isBefore(startOfYesterday)
						&& s.getCurrentRenewalDate().isBefore(startOfToday)) {

					customerRepo.updatePackDirect(s.getCustomerId(), s.getNewPackId(),s.getUpcomingRenewalDate());

					customerRepo.updateStartDate(s.getCustomerId(), s.getRenewalStartDate().toLocalDate());

					s.setProcessed(true);
					stagingRepo.save(s);

					processedCount++;

					System.out.println("Processed customer: " + s.getCustomerId());
				}
			}

			log.setStatus("SUCCESS");

		} catch (Exception e) {
			log.setStatus("FAILED");
			log.setErrorMessage(e.getMessage());
			e.printStackTrace();
		}

		log.setProcessedCount(processedCount);
		log.setEndTime(LocalDateTime.now());

		schedulerLogRepo.save(log);
	}
}