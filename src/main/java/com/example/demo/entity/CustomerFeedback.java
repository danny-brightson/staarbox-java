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
@Table(name = "customerfeedback")
@Where(clause = "StatusId = 1")
public class CustomerFeedback {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Id")
	private Long id;
	
	@Column(name = "CustomerId")
	private Long customerId;
	
	@Column(name = "FeedBack")
	private String feedBack;
	
	@Column(name = "StarRating")
	private Boolean starRating;
	 
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

	public CustomerFeedback() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CustomerFeedback(Long id, Long customerId, String feedBack, Boolean starRating, Long statusId,
			String createdBy, LocalDateTime createdTime, String modefiedBy, LocalDate modefiedTime) {
		super();
		this.id = id;
		this.customerId = customerId;
		this.feedBack = feedBack;
		this.starRating = starRating;
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

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public String getFeedBack() {
		return feedBack;
	}

	public void setFeedBack(String feedBack) {
		this.feedBack = feedBack;
	}

	public Boolean getStarRating() {
		return starRating;
	}

	public void setStarRating(Boolean starRating) {
		this.starRating = starRating;
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
