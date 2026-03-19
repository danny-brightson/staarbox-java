package com.example.demo.dto;

import java.util.ArrayList;
import java.util.List;

public class PackageResponseDto {

	
    private boolean isEggAdded;
    private IngredientDto eggOrSeed;

    private List<IngredientDto> fruits;
    private List<IngredientDto> nuts;
    private List<OptionalIngredientDto> optionals;
    private IngredientDto sandwich ;

    // Constructor
    public PackageResponseDto() {
        fruits = new ArrayList<>();
        nuts = new ArrayList<>();
        optionals = new ArrayList<>();
    }

	public boolean isEggAdded() {
		return isEggAdded;
	}

	public void setEggAdded(boolean isEggAdded) {
		this.isEggAdded = isEggAdded;
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

	public List<OptionalIngredientDto> getOptionals() {
		return optionals;
	}

	public void setOptionals(List<OptionalIngredientDto> optionals) {
		this.optionals = optionals;
	}

	public IngredientDto getSandwich() {
		return sandwich;
	}

	public void setSandwich(IngredientDto sandwich) {
		this.sandwich = sandwich;
	}

	
    // Getters and setters
}

