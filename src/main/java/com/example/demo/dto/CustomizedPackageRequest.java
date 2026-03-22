package com.example.demo.dto;

import java.util.List;

public class CustomizedPackageRequest {

	private IngredientDto eggOrSeed;
	private List<IngredientDto> fruits;
	private List<IngredientDto> nuts;
	private boolean eggAdded;
	private Long customerId;
	private List<OptionalIngredientDto> optional;
	private IngredientDto sandwich;

	public CustomizedPackageRequest() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CustomizedPackageRequest(IngredientDto eggOrSeed, List<IngredientDto> fruits, List<IngredientDto> nuts,
			boolean eggAdded, Long customerId, List<OptionalIngredientDto> optional, IngredientDto sandwich) {
		super();
		this.eggOrSeed = eggOrSeed;
		this.fruits = fruits;
		this.nuts = nuts;
		this.eggAdded = eggAdded;
		this.customerId = customerId;
		this.optional = optional;
		this.sandwich = sandwich;
	}

	public IngredientDto getEggOrSeed() {
		return eggOrSeed;
	}

	public void setEggOrSeed(IngredientDto eggOrSeed) {
		this.eggOrSeed = eggOrSeed;
	}

	public List<IngredientDto> getFruits() {
		return fruits;
	}

	public void setFruits(List<IngredientDto> fruits) {
		this.fruits = fruits;
	}

	public List<IngredientDto> getNuts() {
		return nuts;
	}

	public void setNuts(List<IngredientDto> nuts) {
		this.nuts = nuts;
	}

	public boolean isEggAdded() {
		return eggAdded;
	}

	public void setEggAdded(boolean eggAdded) {
		this.eggAdded = eggAdded;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public List<OptionalIngredientDto> getOptional() {
		return optional;
	}

	public void setOptional(List<OptionalIngredientDto> optional) {
		this.optional = optional;
	}

	public IngredientDto getSandwich() {
		return sandwich;
	}

	public void setSandwich(IngredientDto sandwich) {
		this.sandwich = sandwich;
	}

}
