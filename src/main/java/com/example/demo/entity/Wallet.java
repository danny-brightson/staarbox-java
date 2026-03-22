package com.example.demo.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Wallet")
public class Wallet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Long id;

    @Column(name = "CustomerId", nullable = false)
    private Long customerId;

    @Column(name = "Amount", nullable = false)
    private BigDecimal amount;

    @Column(name = "LastPaymentDate")
    private LocalDateTime lastPaymentDate;
    
    @Column(name = "lastCustomaizedDate")
    private LocalDateTime lastCustomaizedDate;
    
    @Column(name = "lastpaidAmount")
    private BigDecimal lastpaidAmount;
    
    @Column(name = "LastCustomizedAmount")
    private BigDecimal LastCustomizedAmount;

    
    

    // ?? Constructors
    public Wallet() {
    }

    // ?? Getters & Setters
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

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDateTime getLastPaymentDate() {
        return lastPaymentDate;
    }

    public void setLastPaymentDate(LocalDateTime lastPaymentDate) {
        this.lastPaymentDate = lastPaymentDate;
  }

	public LocalDateTime getLastCustomaizedDate() {
		return lastCustomaizedDate;
	}

	public void setLastCustomaizedDate(LocalDateTime lastCustomaizedDate) {
		this.lastCustomaizedDate = lastCustomaizedDate;
	}

	public BigDecimal getLastpaidAmount() {
		return lastpaidAmount;
	}

	public void setLastpaidAmount(BigDecimal lastpaidAmount) {
		this.lastpaidAmount = lastpaidAmount;
	}

	public Wallet(Long id, Long customerId, BigDecimal amount, LocalDateTime lastPaymentDate,
			LocalDateTime lastCustomaizedDate, BigDecimal lastpaidAmount, BigDecimal lastCustomizedAmount) {
		super();
		this.id = id;
		this.customerId = customerId;
		this.amount = amount;
		this.lastPaymentDate = lastPaymentDate;
		this.lastCustomaizedDate = lastCustomaizedDate;
		this.lastpaidAmount = lastpaidAmount;
		LastCustomizedAmount = lastCustomizedAmount;
	}

	public BigDecimal getLastCustomizedAmount() {
		return LastCustomizedAmount;
	}

	public void setLastCustomizedAmount(BigDecimal lastCustomizedAmount) {
		LastCustomizedAmount = lastCustomizedAmount;
	}



}

