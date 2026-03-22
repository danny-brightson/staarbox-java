package com.example.demo.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.hibernate.annotations.Where;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;

@SuppressWarnings("deprecation")
@Entity
@Table(name = "stagingpackaging")
@Where(clause = "StatusId = 1")
public class Packaging {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Id")
	private Long id;
	
	@Column(name = "CustomerId")
	private Long customerId;
	
	@Column(name = "DistrictId")
	private int districtId;
	
	@Column(name = "NumberCode")
	private String numberCode;

	@Lob
	@Column(name = "QrCode")
	private byte[] qrCode;
	
	@Column(name = "IsVerified")
	private boolean isVerified;
	
	@Column(name = "StatusId")
	private Long statusId;
	
	@Column(name = "BoxNumber")
	private Long boxNumber;
	
	@Column(name = "PlanCode")
	private String planCode;
	
	@Column(name = "CustomerName")
	private String customerName;
	
	@Column(name = "CreatedBy")
	private String createdBy;

	@Column(name = "CreatedTime")
	private LocalDateTime createdTime;

	@Column(name = "ModefiedBy")
	private String modefiedBy;

	@Column(name = "ModefiedTime")
	private LocalDate modefiedTime;

	
	// getters and setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public int getDistrictId() {
		return districtId;
	}

	public void setDistrictId(int districtId) {
		this.districtId = districtId;
	}

	public String getNumberCode() {
		return numberCode;
	}

	public void setNumberCode(String numberCode) {
		this.numberCode = numberCode;
	}

	public byte[] getQrCode() {
		return qrCode;
	}

	public void setQrCode(byte[] qrCode) {
		this.qrCode = qrCode;
	}

	public boolean getIsVerified() {
		return isVerified;
	}

	public void setIsVerified(boolean isVerified) {
		this.isVerified = isVerified;
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

	public Packaging() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	public Long getBoxNumber() {
		return boxNumber;
	}

	public void setBoxNumber(Long boxNumber) {
		this.boxNumber = boxNumber;
	}
	
	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public Packaging(Long id, Long customerId, int districtId, String numberCode, byte[] qrCode, boolean isVerified,
			Long statusId, Long boxNumber, String planCode, String customerName, String createdBy,
			LocalDateTime createdTime, String modefiedBy, LocalDate modefiedTime) {
		super();
		this.id = id;
		this.customerId = customerId;
		this.districtId = districtId;
		this.numberCode = numberCode;
		this.qrCode = qrCode;
		this.isVerified = isVerified;
		this.statusId = statusId;
		this.boxNumber = boxNumber;
		this.planCode = planCode;
		this.customerName = customerName;
		this.createdBy = createdBy;
		this.createdTime = createdTime;
		this.modefiedBy = modefiedBy;
		this.modefiedTime = modefiedTime;
	}

	public String getPlanCode() {
		return planCode;
	}

	public void setPlanCode(String planCode) {
		this.planCode = planCode;
	}

	
}
