package com.example.demo.dto;

public class PincodeAvailableResponseDto {

	public String district;
	public boolean isAvailable;

	public PincodeAvailableResponseDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PincodeAvailableResponseDto(String district, boolean isAvailable) {
		super();
		this.district = district;
		this.isAvailable = isAvailable;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public boolean isAvailable() {
		return isAvailable;
	}

	public void setAvailable(boolean isAvailable) {
		this.isAvailable = isAvailable;
	}

	
}
