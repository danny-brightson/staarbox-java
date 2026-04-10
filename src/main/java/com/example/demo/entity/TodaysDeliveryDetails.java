package com.example.demo.entity;


import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "todaysdeliverydetails")
public class TodaysDeliveryDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Long id;

    @Column(name = "DeliveryboyPhoneNumber")
    private String deliveryboyPhoneNumber;

    @Column(name = "NumberCode")
    private String numberCode;

    @Column(name = "BoxNumber")
    private Long boxNumber;

    @Column(name = "CustomerId")
    private Long customerId;

    @Column(name = "CustomerName")
    private String customerName;

    @Column(name = "Address")
    private String address;

    @Column(name = "AddressType")
    private String addressType;

    @Column(name = "DistrictId")
    private Integer districtId;

    @Column(name = "CustomerLatitude")
    private Double customerLatitude;

    @Column(name = "CustomerLongitude")
    private Double customerLongitude;

    @Column(name = "DeliveryTiming")
    private String deliveryTiming;

    @Column(name = "Isdelivered")
    private Boolean isDelivered;

    @Column(name = "ReasonForNotDelivered")
    private Integer reasonForNotDelivered;

    @Column(name = "StatusId")
    private Long statusId;

    @Column(name = "CreatedBy")
    private String createdBy;

    @Column(name = "CreatedTime")
    private LocalDateTime createdTime;

    @Column(name = "ModefiedBy")
    private String modefiedBy;

    @Column(name = "ModefiedTime")
    private LocalDate modefiedTime;

    // Getters and Setters

    public Long getId() { return id; }
    public TodaysDeliveryDetails() {
		super();
		// TODO Auto-generated constructor stub
	}
    
	public TodaysDeliveryDetails(Long id, String deliveryboyPhoneNumber, String numberCode, Long boxNumber,
			Long customerId, String customerName, String address, String addressType, Integer districtId,
			Double customerLatitude, Double customerLongitude, String deliveryTiming, Boolean isDelivered,
			Integer reasonForNotDelivered, Long statusId, String createdBy, LocalDateTime createdTime,
			String modefiedBy, LocalDate modefiedTime) {
		super();
		this.id = id;
		this.deliveryboyPhoneNumber = deliveryboyPhoneNumber;
		this.numberCode = numberCode;
		this.boxNumber = boxNumber;
		this.customerId = customerId;
		this.customerName = customerName;
		this.address = address;
		this.addressType = addressType;
		this.districtId = districtId;
		this.customerLatitude = customerLatitude;
		this.customerLongitude = customerLongitude;
		this.deliveryTiming = deliveryTiming;
		this.isDelivered = isDelivered;
		this.reasonForNotDelivered = reasonForNotDelivered;
		this.statusId = statusId;
		this.createdBy = createdBy;
		this.createdTime = createdTime;
		this.modefiedBy = modefiedBy;
		this.modefiedTime = modefiedTime;
	}
	public void setId(Long id) { this.id = id; }

    public String getDeliveryboyPhoneNumber() { return deliveryboyPhoneNumber; }
    public void setDeliveryboyPhoneNumber(String deliveryboyPhoneNumber) { this.deliveryboyPhoneNumber = deliveryboyPhoneNumber; }

    public String getNumberCode() { return numberCode; }
    public void setNumberCode(String numberCode) { this.numberCode = numberCode; }

    public Long getBoxNumber() { return boxNumber; }
    public void setBoxNumber(Long boxNumber) { this.boxNumber = boxNumber; }

    public Long getCustomerId() { return customerId; }
    public void setCustomerId(Long customerId) { this.customerId = customerId; }

    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getAddressType() { return addressType; }
    public void setAddressType(String addressType) { this.addressType = addressType; }

    public Integer getDistrictId() { return districtId; }
    public void setDistrictId(Integer districtId) { this.districtId = districtId; }

    public Double getCustomerLatitude() { return customerLatitude; }
    public void setCustomerLatitude(Double customerLatitude) { this.customerLatitude = customerLatitude; }

    public Double getCustomerLongitude() { return customerLongitude; }
    public void setCustomerLongitude(Double customerLongitude) { this.customerLongitude = customerLongitude; }

    public String getDeliveryTiming() { return deliveryTiming; }
    public void setDeliveryTiming(String deliveryTiming) { this.deliveryTiming = deliveryTiming; }

    public Boolean getIsDelivered() { return isDelivered; }
    public void setIsDelivered(Boolean isDelivered) { this.isDelivered = isDelivered; }

    public Integer getReasonForNotDelivered() { return reasonForNotDelivered; }
    public void setReasonForNotDelivered(Integer reasonForNotDelivered) { this.reasonForNotDelivered = reasonForNotDelivered; }

    public Long getStatusId() { return statusId; }
    public void setStatusId(Long statusId) { this.statusId = statusId; }

    public String getCreatedBy() { return createdBy; }
    public void setCreatedBy(String createdBy) { this.createdBy = createdBy; }

    public LocalDateTime getCreatedTime() { return createdTime; }
    public void setCreatedTime(LocalDateTime createdTime) { this.createdTime = createdTime; }

    public String getModefiedBy() { return modefiedBy; }
    public void setModefiedBy(String modefiedBy) { this.modefiedBy = modefiedBy; }

    public LocalDate getModefiedTime() { return modefiedTime; }
    
    public void setModefiedTime(LocalDate modefiedTime) { this.modefiedTime = modefiedTime; }
}

