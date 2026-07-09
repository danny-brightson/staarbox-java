package com.example.demo.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(
    name = "CustomerPromo",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"CustomerId", "PromoCodeId"})
    }
)
public class CustomerPromo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long customerId;

    private Long promoCodeId;

    private LocalDateTime usedDate;

	public CustomerPromo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CustomerPromo(Long id, Long customerId, Long promoCodeId, LocalDateTime usedDate) {
		super();
		this.id = id;
		this.customerId = customerId;
		this.promoCodeId = promoCodeId;
		this.usedDate = usedDate;
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

	public Long getPromoCodeId() {
		return promoCodeId;
	}

	public void setPromoCodeId(Long promoCodeId) {
		this.promoCodeId = promoCodeId;
	}

	public LocalDateTime getUsedDate() {
		return usedDate;
	}

	public void setUsedDate(LocalDateTime usedDate) {
		this.usedDate = usedDate;
	}

    // Getters & Setters
	
    
    
}
