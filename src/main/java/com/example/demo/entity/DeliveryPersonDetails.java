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
@Table(name = "deliverypersondetails")
@Where(clause = "StatusId = 1")
public class DeliveryPersonDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Id")
	private Long id;

	@Column(name = "PhoneNumber")
	private String phoneNumber;
	
	@Column(name = "ZoneId")
	private int zoneId;

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
	
	@Column(name = "DeliveryCode")
	private String deliveryCode;

	@Column(name = "districtId")
	private int districtId;

	@Column(name = "DistanceId")
	private String distanceId;

	public DeliveryPersonDetails() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DeliveryPersonDetails(Long id, String phoneNumber, int zoneId, Long statusId, String createdBy,
			LocalDateTime createdTime, String modefiedBy, LocalDate modefiedTime, String deliveryCode, int districtId,
			String distanceId) {
		super();
		this.id = id;
		this.phoneNumber = phoneNumber;
		this.zoneId = zoneId;
		this.statusId = statusId;
		this.createdBy = createdBy;
		this.createdTime = createdTime;
		this.modefiedBy = modefiedBy;
		this.modefiedTime = modefiedTime;
		this.deliveryCode = deliveryCode;
		this.districtId = districtId;
		this.distanceId = distanceId;
	}



	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public int getZoneId() {
		return zoneId;
	}

	public void setZoneId(int zoneId) {
		this.zoneId = zoneId;
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

	public String getDeliveryCode() {
		return deliveryCode;
	}

	public void setDeliveryCode(String deliveryCode) {
		this.deliveryCode = deliveryCode;
	}

	public int getDistrictId() {
		return districtId;
	}

	public void setDistrictId(int districtId) {
		this.districtId = districtId;
	}

	public String getDistanceId() {
		return distanceId;
	}

	public void setDistanceId(String distanceId) {
		this.distanceId = distanceId;
	}

}
