package com.example.demo.dto;

public class TokenRequest {
	   private String phoneNumber;
	    private String fcmToken;
		public TokenRequest() {
			super();
			// TODO Auto-generated constructor stub
		}
		public TokenRequest(String phoneNumber, String fcmToken) {
			super();
			this.phoneNumber = phoneNumber;
			this.fcmToken = fcmToken;
		}
		public String getPhoneNumber() {
			return phoneNumber;
		}
		public void setPhoneNumber(String phoneNumber) {
			this.phoneNumber = phoneNumber;
		}
		public String getFcmToken() {
			return fcmToken;
		}
		public void setFcmToken(String fcmToken) {
			this.fcmToken = fcmToken;
		}
	    
}
