package com.example.demo.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "deliveryboylocation")
public class DeliveryBoyLocation {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Id")
    private Long id;
	
	@Column(name = "deliveryboyphonenumber")
    private String deliveryBoyPhoneNumber;
	
	@Column(name = "latitude")
    private Double latitude;
    
	@Column(name = "longitude")
    private Double longitude;

	@Column(name = "updatedAt")
    private LocalDateTime updatedAt = LocalDateTime.now();

	public DeliveryBoyLocation() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DeliveryBoyLocation(Long id, String deliveryBoyPhoneNumber, Double latitude, Double longitude,
			LocalDateTime updatedAt) {
		super();
		this.id = id;
		this.deliveryBoyPhoneNumber = deliveryBoyPhoneNumber;
		this.latitude = latitude;
		this.longitude = longitude;
		this.updatedAt = updatedAt;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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
