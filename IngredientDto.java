package com.example.demo.dto;

import java.math.BigDecimal;

public class IngredientDto {
	private Long id; 
	private String name;
	private String category;
	private String weight;
	private BigDecimal protein;
	private BigDecimal fiber;
	private BigDecimal fat;
	private BigDecimal carboHydreate;
	private BigDecimal sugar;
	
	
	
	public IngredientDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public IngredientDto(Long id, String name, String category, String weight, BigDecimal protein, BigDecimal fiber,
			BigDecimal fat, BigDecimal carboHydreate, BigDecimal sugar) {
		super();
		this.id = id;
		this.name = name;
		this.category = category;
		this.weight = weight;
		this.protein = protein;
		this.fiber = fiber;
		this.fat = fat;
		this.carboHydreate = carboHydreate;
		this.sugar = sugar;
	}






	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCategory() {
		return category;
	}



	public void setCategory(String category) {
		this.category = category;
	}



	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getWeight() {
		return weight;
	}
	public void setWeight(String weight) {
		this.weight = weight;
	}
	public BigDecimal getProtein() {
		return protein;
	}
	public void setProtein(BigDecimal protein) {
		this.protein = protein;
	}
	public BigDecimal getFiber() {
		return fiber;
	}
	public void setFiber(BigDecimal fiber) {
		this.fiber = fiber;
	}
	public BigDecimal getFat() {
		return fat;
	}
	public void setFat(BigDecimal fat) {
		this.fat = fat;
	}
	public BigDecimal getCarboHydreate() {
		return carboHydreate;
	}
	public void setCarboHydreate(BigDecimal carboHydreate) {
		this.carboHydreate = carboHydreate;
	}
	public BigDecimal getSugar() {
		return sugar;
	}
	public void setSugar(BigDecimal sugar) {
		this.sugar = sugar;
	}

	// Constructor



	// Getters and setters
}
