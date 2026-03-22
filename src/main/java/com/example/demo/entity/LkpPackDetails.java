package com.example.demo.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.hibernate.annotations.Where;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@SuppressWarnings("deprecation")
@Entity
@Table(name = "lkppackdetails")
@Where(clause = "StatusId = 1")
public class LkpPackDetails {


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Id")
	private Long id;
	
	@Column(name = "PackageName")
	private String packageName;
	
	 
	@Column(name = "StatusId")
	private Long statusId;
	
	@Column(name = "CreatedBy")
	private String createdBy;
	
	@Column(name = "CreatedTime")
    private LocalDateTime createdTime;
	
	@Column(name = "ModefiedBy")
	private String modefiedBy;
    
	@Column(name = "ModefiedTime")
    private LocalDate modefiedTime;
	
	@Column(name = "planCode")
	private String planCode;

	public LkpPackDetails() {
		super();
		// TODO Auto-generated constructor stub
	}



	public LkpPackDetails(Long id, String packageName, Long statusId, String createdBy, LocalDateTime createdTime,
			String modefiedBy, LocalDate modefiedTime, String planCode) {
		super();
		this.id = id;
		this.packageName = packageName;
		this.statusId = statusId;
		this.createdBy = createdBy;
		this.createdTime = createdTime;
		this.modefiedBy = modefiedBy;
		this.modefiedTime = modefiedTime;
		this.planCode = planCode;
	}



	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public Long getStatusId() {
		return statusId;
	}

	public void setStatusId(Long statusId) {
		this.statusId = statusId;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public LocalDateTime getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(LocalDateTime createdTime) {
		this.createdTime = createdTime;
	}

	public String getModefiedBy() {
		return modefiedBy;
	}

	public void setModefiedBy(String modefiedBy) {
		this.modefiedBy = modefiedBy;
	}

	public LocalDate getModefiedTime() {
		return modefiedTime;
	}

	public void setModefiedTime(LocalDate modefiedTime) {
		this.modefiedTime = modefiedTime;
	}

	public String getPlanCode() {
		return planCode;
	}

	public void setPlanCode(String planCode) {
		this.planCode = planCode;
	}
}
