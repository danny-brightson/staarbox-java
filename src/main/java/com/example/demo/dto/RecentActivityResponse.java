package com.example.demo.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class RecentActivityResponse {

    private BigDecimal lastPaidAmount;
    private LocalDateTime lastPaymentDate;
    private LocalDateTime lastCustomaizedDate;
    private LocalDateTime RenewedDate;

    public RecentActivityResponse(BigDecimal lastPaidAmount,
                                  LocalDateTime lastPaymentDate,
                                  LocalDateTime lastCustomaizedDate,
                                  LocalDateTime RenewedDate) {
        this.lastPaidAmount = lastPaidAmount;
        this.lastPaymentDate = lastPaymentDate;
        this.lastCustomaizedDate = lastCustomaizedDate;
        this.RenewedDate = RenewedDate;
  }

    // Getters
    public BigDecimal getLastPaidAmount() { return lastPaidAmount; }
    public LocalDateTime getLastPaymentDate() { return lastPaymentDate; }
    public LocalDateTime getLastCustomaizedDate() { return lastCustomaizedDate; }
    public LocalDateTime getRenewedDate() { return RenewedDate; }

	public void setLastPaidAmount(BigDecimal lastPaidAmount) {
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

