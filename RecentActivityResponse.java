package com.example.demo.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class RecentActivityResponse {

    private Long lastPaidAmount;
    private LocalDateTime lastPaymentDate;
    private LocalDateTime lastCustomaizedDate;
    private LocalDateTime RenewedDate;

    public RecentActivityResponse(Long lastPaidAmount,
                                  LocalDateTime lastPaymentDate,
                                  LocalDateTime lastCustomaizedDate,
                                  LocalDateTime RenewedDate) {
        this.lastPaidAmount = lastPaidAmount;
        this.lastPaymentDate = lastPaymentDate;
        this.lastCustomaizedDate = lastCustomaizedDate;
        this.RenewedDate = RenewedDate;
  }

    // Getters
    public Long getLastPaidAmount() { return lastPaidAmount; }
    public LocalDateTime getLastPaymentDate() { return lastPaymentDate; }
    public LocalDateTime getLastCustomaizedDate() { return lastCustomaizedDate; }
    public LocalDateTime getRenewedDate() { return RenewedDate; }

	public void setLastPaidAmount(Long lastPaidAmount) {
		this.lastPaidAmount = lastPaidAmount;
	}

	public void setLastPaymentDate(LocalDateTime lastPaymentDate) {
		this.lastPaymentDate = lastPaymentDate;
	}

	public void setLastCustomaizedDate(LocalDateTime lastCustomaizedDate) {
		this.lastCustomaizedDate = lastCustomaizedDate;
	}

	public void setRenewedDate(LocalDateTime renewedDate) {
		RenewedDate = renewedDate;
	}
    
    
}

