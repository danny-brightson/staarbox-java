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
@Table(name = "lkpavailabledistrict")
@Where(clause = "StatusId = 1")
public class LkpAvailableDistrict {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Id")
	private Long id;

	@Column(name = "District")
	private String district;

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

	@Column(name = "DistrictCode")
	private String DistrictCode;

	@Column(name = "StateId")
	private Long stateId;

	public LkpAvailableDistrict() {
		super();
		// TODO Auto-generated constructor stub
	}

	public LkpAvailableDistrict(Long id, String district, Long statusId, String createdBy, LocalDateTime createdTime,
			String modefiedBy, LocalDate modefiedTime, String districtCode, Long stateId) {
		super();
		this.id = id;
		this.district = district;
		this.statusId = statusId;
		this.createdBy = createdBy;
		this.createdTime = createdTime;
		this.modefiedBy = modefiedBy;
		this.modefiedTime = modefiedTime;
		DistrictCode = districtCode;
		this.stateId = stateId;
	}

	public LkpAvailableDistrict(Long id, String district, Long statusId, String createdBy, LocalDateTime createdTime,
			String modefiedBy, LocalDate modefiedTime, Long stateId) {
		super();
		this.id = id;
		this.district = district;
		this.statusId = statusId;
		this.createdBy = createdBy;
		this.createdTime = createdTime;
		this.modefiedBy = modefiedBy;
		this.modefiedTime = modefiedTime;
		this.stateId = stateId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
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

	public String getDistrictCode() {
		return DistrictCode;
	}

	public void setDistrictCode(String districtCode) {
		DistrictCode = districtCode;
	}

	public Long getStateId() {
		return stateId;
	}

	public void setStateId(Long stateId) {
		this.stateId = stateId;
	}

}
