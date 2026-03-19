package com.example.demo.dto;

public class LoginResponse {
	private Long id;
	private Long districtId;
	private String accessToken;
	private String refreshToken;
	 private Boolean isVerifier;
	public LoginResponse() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public LoginResponse(Long id, Long districtId, String accessToken, String refreshToken, Boolean isVerifier) {
		super();
		this.id = id;
		this.districtId = districtId;
		this.accessToken = accessToken;
		this.refreshToken = refreshToken;
		this.isVerifier = isVerifier;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		id = id;
	}
	public Long getDistrictId() {
		return districtId;
	}
	public void setDistrictId(Long districtId) {
		this.districtId = districtId;
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

	public Boolean getIsVerifier() {
		return isVerifier;
	}

	public void setIsVerifier(Boolean isVerifier) {
		this.isVerifier = isVerifier;
	}
	
	
}
