package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OtpVerificationRequest {
	   private String phoneNumber;
	   
	   @JsonProperty("isRegistered")
	    private boolean IsRegistered;
		public OtpVerificationRequest() {
			super();
			// TODO Auto-generated constructor stub
		}
		public OtpVerificationRequest(String phoneNumber, boolean isRegistered) {
			super();
			this.phoneNumber = phoneNumber;
			IsRegistered = isRegistered;
		}
		public String getPhoneNumber() {
			return phoneNumber;
		}
		public void setPhoneNumber(String phoneNumber) {
			this.phoneNumber = phoneNumber;
		}
		public boolean isIsRegistered() {
			return IsRegistered;
		}
		public void setIsRegistered(boolean isRegistered) {
			IsRegistered = isRegistered;
		}


}
