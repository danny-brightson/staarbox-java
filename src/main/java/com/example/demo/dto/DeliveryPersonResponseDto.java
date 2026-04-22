package com.example.demo.dto;

public class DeliveryPersonResponseDto {

    private String name;
    private int districtId;
    private String deliveryCode;

    public DeliveryPersonResponseDto(String name, int districtId, String deliveryCode) {
        this.name = name;
        this.districtId = districtId;
        this.deliveryCode = deliveryCode;
    }

    public String getName() { return name; }
    public int getDistrictId() { return districtId; }
    public String getDeliveryCode() { return deliveryCode; }
}