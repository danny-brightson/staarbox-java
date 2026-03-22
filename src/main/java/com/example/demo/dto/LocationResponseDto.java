package com.example.demo.dto;

import java.time.LocalDateTime;

public class LocationResponseDto {
	 private String deliveryBoyPhoneNumber;
	    private Double latitude;
	    private Double longitude;
	    private LocalDateTime updatedAt;
		public LocationResponseDto() {
			super();
			// TODO Auto-generated constructor stub
		}
		public LocationResponseDto(String deliveryBoyPhoneNumber, Double latitude, Double longitude,
				LocalDateTime updatedAt) {
			super();
			this.deliveryBoyPhoneNumber = deliveryBoyPhoneNumber;
			this.latitude = latitude;
			this.longitude = longitude;
			this.updatedAt = updatedAt;
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
		public LocalDateTime getUpdatedAt() {
			return updatedAt;
		}
		public void setUpdatedAt(LocalDateTime updatedAt) {
			this.updatedAt = updatedAt;
		}
}
