package com.example.demo.dto;

public class CustomerResponseDto {

	private Long id;
	private String name;
	
	public CustomerResponseDto(Long id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public CustomerResponseDto() {
		super();
		// TODO Auto-generated constructor stub
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
	
}
