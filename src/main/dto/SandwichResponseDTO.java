package com.example.demo.dto;

import java.math.BigDecimal;
import java.util.List;

public class SandwichResponseDTO {

    private BigDecimal minimumAmount;
    private List<SandwichDTO> sandwiches;

    public SandwichResponseDTO(BigDecimal minimumAmount,
                               List<SandwichDTO> sandwiches) {
        this.minimumAmount = minimumAmount;
        this.sandwiches = sandwiches;
    }

    public BigDecimal getMinimumAmount() {
        return minimumAmount;
    }

    public List<SandwichDTO> getSandwiches() {
        return sandwiches;
    }

	public void setMinimumAmount(BigDecimal minimumAmount) {
		this.minimumAmount = minimumAmount;
	}

	public void setSandwiches(List<SandwichDTO> sandwiches) {
		this.sandwiches = sandwiches;
	}
    
    
}

