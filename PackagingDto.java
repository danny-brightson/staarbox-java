package com.example.demo.dto;

public class PackagingDto {
	private long id;
	private int districtId;
	private int zoneId;
	private int distanceId;
	private int deliveryTimingId;
	private int packDetailsId;
    private String deliveryCode;
    private String name;
    private boolean isPragnent;

	
	
	
	public PackagingDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	


	

	
	public PackagingDto(long id, int districtId, int zoneId, int distanceId, int deliveryTimingId, int packDetailsId,
			String deliveryCode, String name, boolean isPragnent) {
		super();
		this.id = id;
		this.districtId = districtId;
		this.zoneId = zoneId;
		this.distanceId = distanceId;
		this.deliveryTimingId = deliveryTimingId;
		this.packDetailsId = packDetailsId;
		this.deliveryCode = deliveryCode;
		this.name = name;
		this.isPragnent = isPragnent;
	}






	public long getId() {
		return id;
	}






	public void setId(long id) {
		this.id = id;
	}






	public int getDistrictId() {
		return districtId;
	}






	public void setDistrictId(int districtId) {
		this.districtId = districtId;
	}






	public int getZoneId() {
		return zoneId;
	}






	public void setZoneId(int zoneId) {
		this.zoneId = zoneId;
	}






	public int getDistanceId() {
		return distanceId;
	}






	public void setDistanceId(int distanceId) {
		this.distanceId = distanceId;
	}






	public int getDeliveryTimingId() {
		return deliveryTimingId;
	}






	public void setDeliveryTimingId(int deliveryTimingId) {
		this.deliveryTimingId = deliveryTimingId;
	}






	public int getPackDetailsId() {
		return packDetailsId;
	}






	public void setPackDetailsId(int packDetailsId) {
		this.packDetailsId = packDetailsId;
	}






	public String getDeliveryCode() {
		return deliveryCode;
	}






	public void setDeliveryCode(String deliveryCode) {
		this.deliveryCode = deliveryCode;
	}






	public String getName() {
		return name;
	}






	public void setName(String name) {
		this.name = name;
	}






	public boolean isPragnent() {
		return isPragnent;
	}






	public void setPragnent(boolean isPragnent) {
		this.isPragnent = isPragnent;
	}






	
}
