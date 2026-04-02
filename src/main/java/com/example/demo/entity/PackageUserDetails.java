package com.example.demo.entity;


import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "PackageUserDetails", schema = "staarbox")
public class PackageUserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ModefiedTime")
    private LocalDateTime modefiedTime;

    @Column(name = "CreatedTime", nullable = false)
    private LocalDateTime createdTime;

    @Column(name = "DistrictId", nullable = false)
    private Long districtId;

    @Column(name = "StatusId", nullable = false)
    private Integer statusId;

    @Column(name = "CreatedBy", nullable = false)
    private String createdBy;

    @Column(name = "UserName", nullable = false, unique = true)
    private String userName;

    @Column(name = "Password", nullable = false)
    private String password;

    @Column(name = "ModefiedBy")
    private String modefiedBy;
    
    @Column(name = "isVerifier")
    private Boolean isVerifier;

    // Constructors
    public PackageUserDetails() {}

   
    public PackageUserDetails(Long id, LocalDateTime modefiedTime, LocalDateTime createdTime, Long districtId,
			Integer statusId, String createdBy, String userName, String password, String modefiedBy,
			Boolean isVerifier) {
		super();
		this.id = id;
		this.modefiedTime = modefiedTime;
		this.createdTime = createdTime;
		this.districtId = districtId;
		this.statusId = statusId;
		this.createdBy = createdBy;
		this.userName = userName;
		this.password = password;
		this.modefiedBy = modefiedBy;
		this.isVerifier = isVerifier;
	}


	// Getters and Setters

    public Long getId() {
        return id;
    }

    public LocalDateTime getModefiedTime() {
        return modefiedTime;
    }

    public void setModefiedTime(LocalDateTime modefiedTime) {
        this.modefiedTime = modefiedTime;
    }

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(LocalDateTime createdTime) {
        this.createdTime = createdTime;
    }

    public Long getDistrictId() {
        return districtId;
    }

    public void setDistrictId(Long districtId) {
        this.districtId = districtId;
    }

    public Integer getStatusId() {
        return statusId;
    }

    public void setStatusId(Integer statusId) {
        this.statusId = statusId;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getModefiedBy() {
        return modefiedBy;
    }

    public void setModefiedBy(String modefiedBy) {
        this.modefiedBy = modefiedBy;
    }


	public Boolean getIsVerifier() {
		return isVerifier;
	}


	public void setIsVerifier(Boolean isVerifier) {
		this.isVerifier = isVerifier;
	}


	public void setId(Long id) {
		this.id = id;
	}
    
    
}

