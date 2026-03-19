package com.example.demo.dto;

public class LocationRequestDto {
	private String deliveryBoyPhoneNumber;
    private Double latitude;
    private Double longitude;
	public LocationRequestDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	public LocationRequestDto(String deliveryBoyPhoneNumber, Double latitude, Double longitude) {
		super();
		this.deliveryBoyPhoneNumber = deliveryBoyPhoneNumber;
		this.latitude = latitude;
		this.longitude = longitude;
	}
	public String getDeliveryBoyPhoneNumber() {
		return deliveryBoyPhoneNumber;
	}
	public void setDeliveryBoyPhoneNumber(String deliveryBoyPhoneNumber) {
		this.deliveryBoyPhoneNumber = deliveryBoyPhoneNumber;
	}
	public Double getLatitude() {
		return latitude;
	}
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}
	public Double getLongitude() {
		return longitude;
	}
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
}
