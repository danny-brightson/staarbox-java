package com.example.demo.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;

	@Entity
	@Table(name = "scheduler_log")
	public class SchedulerLog {

	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column(name = "id") // ✅ fix
	    private Long id;

	    @Column(name = "job_name") // ✅ fix
	    private String jobName;

	    @Column(name = "start_time") // ✅ fix
	    private LocalDateTime startTime;

	    @Column(name = "end_time") // ✅ fix
	    private LocalDateTime endTime;

	    @Column(name = "status") // ✅ fix
	    private String status;

	    @Column(name = "processed_count") // ✅ fix
	    private Integer processedCount;

	    @Column(name = "error_message") // ✅ fix
	    private String errorMessage;

		public SchedulerLog() {
			super();
			// TODO Auto-generated constructor stub
		}

		public SchedulerLog(Long id, String jobName, LocalDateTime startTime, LocalDateTime endTime, String status,
				Integer processedCount, String errorMessage) {
			super();
			this.id = id;
			this.jobName = jobName;
			this.startTime = startTime;
			this.endTime = endTime;
			this.status = status;
			this.processedCount = processedCount;
			this.errorMessage = errorMessage;
		}

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getJobName() {
			return jobName;
		}

		public void setJobName(String jobName) {
			this.jobName = jobName;
		}

		public LocalDateTime getStartTime() {
			return startTime;
		}

		public void setStartTime(LocalDateTime startTime) {
			this.startTime = startTime;
		}

		public LocalDateTime getEndTime() {
			return endTime;
		}

		public void setEndTime(LocalDateTime endTime) {
			this.endTime = endTime;
		}

		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}

		public Integer getProcessedCount() {
			return processedCount;
		}

		public void setProcessedCount(Integer processedCount) {
			this.processedCount = processedCount;
		}

		public String getErrorMessage() {
			return errorMessage;
		}

		public void setErrorMessage(String errorMessage) {
			this.errorMessage = errorMessage;
		}

	    
	    // constructor + getters/setters same
	}

