package com.example.demo.dto;

import java.time.LocalDateTime;
import java.util.Date;

public class RenewalDateResponse {
  
	private LocalDateTime lastPaymentDate;
	private Date nextRenewalDate;
	private int packdetailsId;
	public RenewalDateResponse() {
		super();
		// TODO Auto-generated constructor stub
	}
	public RenewalDateResponse(LocalDateTime lastPaymentDate, Date nextRenewalDate, int packdetailsId) {
		super();
		this.lastPaymentDate = lastPaymentDate;
		this.nextRenewalDate = nextRenewalDate;
		this.packdetailsId = packdetailsId;
	}
	public LocalDateTime getLastPaymentDate() {
		return lastPaymentDate;
	}
	public void setLastPaymentDate(LocalDateTime lastPaymentDate) {
		this.lastPaymentDate = lastPaymentDate;
	}
	public Date getNextRenewalDate() {
		return nextRenewalDate;
	}
	public void setNextRenewalDate(Date nextRenewalDate) {
		this.nextRenewalDate = nextRenewalDate;
	}
	public int getPackdetailsId() {
		return packdetailsId;
	}
	public void setPackdetailsId(int packdetailsId) {
		this.packdetailsId = packdetailsId;
	}
	
	
	
}
