package com.example.demo.dto;



public class addressChangeDto {
	
		private Long customerId;  // Changed to Long
		private String addressLine1;
		private String addressLine2;
		private String addressType;
		private Integer districtId;  // Changed to Integer
		private Integer stateId;
		private String pinCode;
		private Integer delivaryTimingId;
		private Double newLatitude;
		private Double newLongitude;
		public addressChangeDto() {
			super();
			// TODO Auto-generated constructor stub
		}
		
		public addressChangeDto(Long customerId, String addressLine1, String addressLine2, String addressType,
				Integer districtId, Integer stateId, String pinCode, Integer delivaryTimingId, Double newLatitude,
				Double newLongitude) {
			super();
			this.customerId = customerId;
			this.addressLine1 = addressLine1;
			this.addressLine2 = addressLine2;
			this.addressType = addressType;
			this.districtId = districtId;
			this.stateId = stateId;
			this.pinCode = pinCode;
			this.delivaryTimingId = delivaryTimingId;
			this.newLatitude = newLatitude;
			this.newLongitude = newLongitude;
		}

		public Long getCustomerId() {
			return customerId;
		}
		public void setCustomerId(Long customerId) {
			this.customerId = customerId;
		}
		public String getAddressLine1() {
			return addressLine1;
		}
		public void setAddressLine1(String addressLine1) {
			this.addressLine1 = addressLine1;
		}
		public String getAddressLine2() {
			return addressLine2;
		}
		public void setAddressLine2(String addressLine2) {
			this.addressLine2 = addressLine2;
		}
		public String getAddressType() {
			return addressType;
		}
		public void setAddressType(String addressType) {
			this.addressType = addressType;
		}
		public Integer getDistrictId() {
			return districtId;
		}
		public void setDistrictId(Integer districtId) {
			this.districtId = districtId;
		}
		public Integer getStateId() {
			return stateId;
		}
		public void setStateId(Integer stateId) {
			this.stateId = stateId;
		}
		public String getPinCode() {
			return pinCode;
		}
		public void setPinCode(String pinCode) {
			this.pinCode = pinCode;
		}
		public Integer getDelivaryTimingId() {
			return delivaryTimingId;
		}
		public void setDelivaryTimingId(Integer delivaryTimingId) {
			this.delivaryTimingId = delivaryTimingId;
		}

		public Double getNewLatitude() {
			return newLatitude;
		}

		public void setNewLatitude(Double newLatitude) {
			this.newLatitude = newLatitude;
		}

		public Double getNewLongitude() {
			return newLongitude;
		}

		public void setNewLongitude(Double newLongitude) {
			this.newLongitude = newLongitude;
		}

		// Constructors, Getters, Setters...
		
	

}
