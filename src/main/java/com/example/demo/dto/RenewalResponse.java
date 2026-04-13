package com.example.demo.dto;

import java.time.LocalDateTime;
import java.util.Date;

public class RenewalResponse {

    private Long customerId;
    private Long currentPackId;
    private Date currentNextRenewalDate;

    private boolean renewalAvailable;
    private Long newPackId;
    private LocalDateTime paymentDate;
    private LocalDateTime upcomingRenewalEndDate;
    private LocalDateTime upcomingRenewalStartDate;

    public Long getCustomerId() { return customerId; }
    public void setCustomerId(Long customerId) { this.customerId = customerId; }

    public Long getCurrentPackId() { return currentPackId; }
    public void setCurrentPackId(Long currentPackId) { this.currentPackId = currentPackId; }

    public Date getCurrentNextRenewalDate() { return currentNextRenewalDate; }
    public void setCurrentNextRenewalDate(Date currentNextRenewalDate) { this.currentNextRenewalDate = currentNextRenewalDate; }

    public boolean isRenewalAvailable() { return renewalAvailable; }
    public void setRenewalAvailable(boolean renewalAvailable) { this.renewalAvailable = renewalAvailable; }

    public Long getNewPackId() { return newPackId; }
    public void setNewPackId(Long newPackId) { this.newPackId = newPackId; }

    public LocalDateTime getPaymentDate() { return paymentDate; }
    public void setPaymentDate(LocalDateTime paymentDate) { this.paymentDate = paymentDate; }

    public LocalDateTime getUpcomingRenewalEndDate() { return upcomingRenewalEndDate; }
    public void setUpcomingRenewalEndDate(LocalDateTime upcomingRenewalEndDate) { this.upcomingRenewalEndDate = upcomingRenewalEndDate; }
    
	public LocalDateTime getUpcomingRenewalStartDate() {
		return upcomingRenewalStartDate;
	}
	public void setUpcomingRenewalStartDate(LocalDateTime upcomingRenewalStartDate) {
		this.upcomingRenewalStartDate = upcomingRenewalStartDate;
	}
    
}