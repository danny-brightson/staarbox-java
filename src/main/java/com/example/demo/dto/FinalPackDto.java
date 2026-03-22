package com.example.demo.dto;

import java.util.ArrayList;
import java.util.List;

public class FinalPackDto {
	private SimpleIngredient eggOrSeed;
	private List<SimpleIngredient> fruits;
	private List<SimpleIngredient> nuts;
	private boolean eggAdded;
	private Integer boxNumber;
	private String numberCode;
	private boolean isVerified;
	private SimpleIngredient sandwich;

	public FinalPackDto() {
		this.eggOrSeed = eggOrSeed;
		this.eggAdded = eggAdded;
		this.boxNumber = boxNumber;
		this.numberCode =numberCode;
		fruits = new ArrayList<>();
		nuts = new ArrayList<>();
		this.isVerified = isVerified;
		this.sandwich = sandwich;
	}
	

	public SimpleIngredient getEggOrSeed() {
		return eggOrSeed;
	}

	public void setEggOrSeed(SimpleIngredient eggOrSeed) {
		this.eggOrSeed = eggOrSeed;
	}

	public List<SimpleIngredient> getFruits() {
		return fruits;
	}

	public void setFruits(List<SimpleIngredient> fruits) {
		this.fruits = fruits;
	}

	public List<SimpleIngredient> getNuts() {
		return nuts;
	}

	public void setNuts(List<SimpleIngredient> nuts) {
		this.nuts = nuts;
	}

	public boolean isEggAdded() {
		return eggAdded;
	}

	public void setEggAdded(boolean eggAdded) {
		this.eggAdded = eggAdded;
	}

	public Integer getBoxNumber() {
		return boxNumber;
	}

	public void setBoxNumber(Integer boxNumber) {
		this.boxNumber = boxNumber;
	}

	public String getNumberCode() {
		return numberCode;
	}

	public void setNumberCode(String numberCode) {
		this.numberCode = numberCode;
	}


	public boolean isVerified() {
		return isVerified;
	}


	public void setVerified(boolean isVerified) {
		this.isVerified = isVerified;
	}


	public SimpleIngredient getSandwich() {
		return sandwich;
	}


	public void setSandwich(SimpleIngredient sandwich) {
		this.sandwich = sandwich;
	}
	

}
