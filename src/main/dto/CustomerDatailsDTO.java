package com.example.demo.dto;

import java.util.Date;

public class CustomerDatailsDTO {

	private String name;

	private String mailId;

	private Date dOB;

	private String genderId;

	private String addressLine1;

	private String addressLine2;

	private String pinCode;

	private int districtId;

	private int stateId;

	private String addressType;

	private int delivaryTimingId;

	private boolean isPragnent;

	private boolean isAllergic;

	private String fruitstoAvoid;

	private String phoneNumber;

	private Double latitude;

	private Double longtitude;

	private Boolean isEggPreferd;
	
	private Boolean IsDiabetic;
	
	// Billing Address
	public String B_firstName;
	public String B_lastName;
	public String B_email;
	public String B_phone;
	public String B_address;
	public String B_companyName;
	public String B_gstNumber;
	public String B_landmark;
	public String B_city;
	public String B_state;

	public CustomerDatailsDTO() {
		super();
		// TODO Auto-generated constructor stub
	}


	public CustomerDatailsDTO(String name, String mailId, Date dOB, String genderId, String addressLine1,
			String addressLine2, String pinCode, int districtId, int stateId, String addressType, int delivaryTimingId,
			boolean isPragnent, boolean isAllergic, String fruitstoAvoid, String phoneNumber, Double latitude,
			Double longtitude, Boolean isEggPreferd, Boolean IsDiabetic, String b_firstName, String b_lastName,
			String b_email, String b_phone, String b_address, String b_companyName, String b_gstNumber,
			String b_landmark, String b_city, String b_state) {
		super();
		this.name = name;
		this.mailId = mailId;
		this.dOB = dOB;
		this.genderId = genderId;
		this.addressLine1 = addressLine1;
		this.addressLine2 = addressLine2;
		this.pinCode = pinCode;
		this.districtId = districtId;
		this.stateId = stateId;
		this.addressType = addressType;
		this.delivaryTimingId = delivaryTimingId;
		this.isPragnent = isPragnent;
		this.isAllergic = isAllergic;
		this.fruitstoAvoid = fruitstoAvoid;
		this.phoneNumber = phoneNumber;
		this.latitude = latitude;
		this.longtitude = longtitude;
		this.isEggPreferd = isEggPreferd;
		this.IsDiabetic = IsDiabetic;
		B_firstName = b_firstName;
		B_lastName = b_lastName;
		B_email = b_email;
		B_phone = b_phone;
		B_address = b_address;
		B_companyName = b_companyName;
		B_gstNumber = b_gstNumber;
		B_landmark = b_landmark;
		B_city = b_city;
		B_state = b_state;
	}



	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMailId() {
		return mailId;
	}

	public void setMailId(String mailId) {
		this.mailId = mailId;
	}

	public Date getdOB() {
		return dOB;
	}

	public void setdOB(Date dOB) {
		this.dOB = dOB;
	}

	public String getGenderId() {
		return genderId;
	}

	public void setGenderId(String genderId) {
		this.genderId = genderId;
	}

	public String getAddressLine1() {
		return addressLine1;
	}

	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}

	public String getAddressLine2() {
		return addressLine2;
	}

	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
	}

	public String getPinCode() {
		return pinCode;
	}

	public void setPinCode(String pinCode) {
		this.pinCode = pinCode;
	}

	public int getDistrictId() {
		return districtId;
	}

	public void setDistrictId(int districtId) {
		this.districtId = districtId;
	}

	public int getStateId() {
		return stateId;
	}

	public void setStateId(int stateId) {
		this.stateId = stateId;
	}

	public String getAddressType() {
		return addressType;
	}

	public void setAddressType(String addressType) {
		this.addressType = addressType;
	}

	public int getDelivaryTimingId() {
		return delivaryTimingId;
	}

	public void setDelivaryTimingId(int delivaryTimingId) {
		this.delivaryTimingId = delivaryTimingId;
	}

	public boolean isPragnent() {
		return isPragnent;
	}

	public void setPragnent(boolean isPragnent) {
		this.isPragnent = isPragnent;
	}

	public boolean isAllergic() {
		return isAllergic;
	}

	public void setAllergic(boolean isAllergic) {
		this.isAllergic = isAllergic;
	}

	public String getFruitstoAvoid() {
		return fruitstoAvoid;
	}

	public void setFruitstoAvoid(String fruitstoAvoid) {
		this.fruitstoAvoid = fruitstoAvoid;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongtitude() {
		return longtitude;
	}

	public void setLongtitude(Double longtitude) {
		this.longtitude = longtitude;
	}

	public String getB_firstName() {
		return B_firstName;
	}

	public void setB_firstName(String b_firstName) {
		B_firstName = b_firstName;
	}

	public String getB_lastName() {
		return B_lastName;
	}

	public void setB_lastName(String b_lastName) {
		B_lastName = b_lastName;
	}

	public String getB_email() {
		return B_email;
	}

	public void setB_email(String b_email) {
		B_email = b_email;
	}

	public String getB_phone() {
		return B_phone;
	}

	public void setB_phone(String b_phone) {
		B_phone = b_phone;
	}

	public String getB_address() {
		return B_address;
	}

	public void setB_address(String b_address) {
		B_address = b_address;
	}

	public String getB_companyName() {
		return B_companyName;
	}

	public void setB_companyName(String b_companyName) {
		B_companyName = b_companyName;
	}

	public String getB_gstNumber() {
		return B_gstNumber;
	}

	public void setB_gstNumber(String b_gstNumber) {
		B_gstNumber = b_gstNumber;
	}

	public String getB_landmark() {
		return B_landmark;
	}

	public void setB_landmark(String b_landmark) {
		B_landmark = b_landmark;
	}

	public String getB_city() {
		return B_city;
	}

	public void setB_city(String b_city) {
		B_city = b_city;
	}

	public String getB_state() {
		return B_state;
	}

	public void setB_state(String b_state) {
		B_state = b_state;
	}

	public Boolean getIsEggPreferd() {
		return isEggPreferd;
	}

	public void setIsEggPreferd(Boolean isEggPreferd) {
		this.isEggPreferd = isEggPreferd;
	}


	public Boolean getIsDiabetic() {
		return IsDiabetic;
	}


	public void setIsDiabetic(Boolean isDiabetic) {
		IsDiabetic = isDiabetic;
	}



}
