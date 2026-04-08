package com.example.demo.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "version_check")
public class VersionCheck {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "current_version")
    private String currentVersion;

    @Column(name = "latest_version")
    private String latestVersion;

    @Column(name = "is_force_update")
    private Boolean isForceUpdate;

    @Column(name = "is_soft_update")
    private Boolean isSoftUpdate;

    @Column(name = "created_time")
    private LocalDateTime createdTime;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "status_id")
    private Integer statusId;

	public VersionCheck() {
		super();
		// TODO Auto-generated constructor stub
	}

	public VersionCheck(Long id, String currentVersion, String latestVersion, Boolean isForceUpdate,
			Boolean isSoftUpdate, LocalDateTime createdTime, String createdBy, Integer statusId) {
		super();
		this.id = id;
		this.currentVersion = currentVersion;
		this.latestVersion = latestVersion;
		this.isForceUpdate = isForceUpdate;
		this.isSoftUpdate = isSoftUpdate;
		this.createdTime = createdTime;
		this.createdBy = createdBy;
		this.statusId = statusId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCurrentVersion() {
		return currentVersion;
	}

	public void setCurrentVersion(String currentVersion) {
		this.currentVersion = currentVersion;
	}

	public String getLatestVersion() {
		return latestVersion;
	}

	public void setLatestVersion(String latestVersion) {
		this.latestVersion = latestVersion;
	}

	public Boolean getIsForceUpdate() {
		return isForceUpdate;
	}

	public void setIsForceUpdate(Boolean isForceUpdate) {
		this.isForceUpdate = isForceUpdate;
	}

	public Boolean getIsSoftUpdate() {
		return isSoftUpdate;
	}

	public void setIsSoftUpdate(Boolean isSoftUpdate) {
		this.isSoftUpdate = isSoftUpdate;
	}

	public LocalDateTime getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(LocalDateTime createdTime) {
		this.createdTime = createdTime;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Integer getStatusId() {
		return statusId;
	}

	public void setStatusId(Integer statusId) {
		this.statusId = statusId;
	}

    
    // Getters & Setters
}
