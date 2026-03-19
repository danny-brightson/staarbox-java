package com.example.demo.dto;

import java.math.BigDecimal;

public class SandwichDTO {

    private Long id;
    private String sandwichName;
    private BigDecimal rate;

    public SandwichDTO(Long id, String sandwichName, BigDecimal rate) {
        this.id = id;
        this.sandwichName = sandwichName;
        this.rate = rate;
    }

    public Long getId() {
        return id;
    }

    public String getSandwichName() {
        return sandwichName;
    }

    public BigDecimal getRate() {
        return rate;
    }

	public void setId(Long id) {
		this.id = id;
	}

	public void setSandwichName(String sandwichName) {
		this.sandwichName = sandwichName;
	}

	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}
    
    
}

