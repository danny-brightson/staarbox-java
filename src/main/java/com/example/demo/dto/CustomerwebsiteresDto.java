package com.example.demo.dto;

public class CustomerwebsiteresDto {
	private Long id;
	private String name;
	private String accessToken;
    private String refreshToken;
	public CustomerwebsiteresDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	public CustomerwebsiteresDto(Long id, String name, String accessToken, String refreshToken) {
		super();
		this.id = id;
		this.name = name;
		this.accessToken = accessToken;
		this.refreshToken = refreshToken;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
    
    
}
