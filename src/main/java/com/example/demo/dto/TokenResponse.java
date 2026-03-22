package com.example.demo.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TokenResponse {

	  private String accessToken;
	    private String refreshToken;
	    @JsonProperty("isDeliverPerson")
	    private boolean isDeliverPerson;
	    private List<Integer> customerIds;
	    private List<Integer> pendingCustomerIds;
		public TokenResponse() {
			super();
			// TODO Auto-generated constructor stub
		}


		public TokenResponse(String accessToken, String refreshToken, boolean isDeliverPerson,
				List<Integer> customerIds, List<Integer> pendingCustomerIds) {
			super();
			this.accessToken = accessToken;
			this.refreshToken = refreshToken;
			this.isDeliverPerson = isDeliverPerson;
			this.customerIds = customerIds;
			this.pendingCustomerIds = pendingCustomerIds;
		}




		public String getAccessToken() {
			return accessToken;
		}
		public void setAccessToken(String accessToken) {
			this.accessToken = accessToken;
		}
		public String getRefreshToken() {
			return refreshToken;
		}
		public void setRefreshToken(String refreshToken) {
			this.refreshToken = refreshToken;
		}

		public boolean isDeliverPerson() {
			return isDeliverPerson;
		}

		public void setDeliverPerson(boolean isDeliverPerson) {
			this.isDeliverPerson = isDeliverPerson;
		}


		public List<Integer> getCustomerIds() {
			return customerIds;
		}


		public void setCustomerIds(List<Integer> customerIds) {
			this.customerIds = customerIds;
		}


		public List<Integer> getPendingCustomerIds() {
			return pendingCustomerIds;
		}


		public void setPendingCustomerIds(List<Integer> pendingCustomerIds) {
			this.pendingCustomerIds = pendingCustomerIds;
		}
		
		

}
