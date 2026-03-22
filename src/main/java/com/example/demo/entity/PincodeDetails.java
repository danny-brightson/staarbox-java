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
@Table(name = "pincodedetails")
@Where(clause = "StatusId = 1")
public class PincodeDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Id")
	private Long id;
	
	@Column(name = "PinCode")
	private String pinCode;
	
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

	public PincodeDetails() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PincodeDetails(Long id, String pinCode, Long statusId, String createdBy,
			LocalDateTime createdTime, String modefiedBy, LocalDate modefiedTime) {
		super();
		this.id = id;
		this.pinCode = pinCode;
		this.statusId = statusId;
		this.createdBy = createdBy;
		this.createdTime = createdTime;
		this.modefiedBy = modefiedBy;
		this.modefiedTime = modefiedTime;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPinCode() {
		return pinCode;
	}

	public void setPinCode(String pinCode) {
		this.pinCode = pinCode;
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
}
