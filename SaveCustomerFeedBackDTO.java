package com.example.demo.dto;

import jakarta.persistence.Column;

public class SaveCustomerFeedBackDTO {

	private Long customerId;
	
	private String feedBack;

	private Boolean starRating;

	public SaveCustomerFeedBackDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public SaveCustomerFeedBackDTO(Long customerId, String feedBack, Boolean starRating) {
		super();
		this.customerId = customerId;
		this.feedBack = feedBack;
		this.starRating = starRating;
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
}
