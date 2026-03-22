package com.example.demo.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "checkoutdata")
public class CheckoutData {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "last_name")
	private String lastName;

	@Column(name = "email")
	private String email;

	@Column(name = "phone")
	private String phone;

	@Column(name = "pincode")
	private String pincode;

	@Column(name = "address")
	private String address;

	@Column(name = "locality")
	private String locality;

	@Column(name = "landmark")
	private String landmark;

	@Column(name = "city")
	private String city;

	@Column(name = "state")
	private String state;

	@Column(name = "plan_name")
	private String planName;
	// Billing Address Fields
	@Column(name = "B_first_name")
	private String B_firstName;

	@Column(name = "B_last_name")
	private String B_lastName;

	@Column(name = "B_email")
	private String B_email;

	@Column(name = "B_phone")
	private String B_phone;

	@Column(name = "B_address")
	private String B_address;

	@Column(name = "B_company_name")
	private String B_companyName;

	@Column(name = "B_gst_number")
	private String B_gstNumber;

	@Column(name = "B_landmark")
	private String B_landmark;

	@Column(name = "B_city")
	private String B_city;

	@Column(name = "B_state")
	private String B_state;

	@Column(name = "created_at")
	private LocalDateTime createdAt;
	
	@Column(name = "IsEggAdded")
    public Boolean isEggadded;
	
	@Column(name = "deliveryNotes")
    public String deliveryNotes;

	// ✅ Constructors
	public CheckoutData() {
	}

	
	public CheckoutData(Long id, String firstName, String lastName, String email, String phone, String pincode,
			String address, String locality, String landmark, String city, String state, String planName,
			String b_firstName, String b_lastName, String b_email, String b_phone, String b_address,
			String b_companyName, String b_gstNumber, String b_landmark, String b_city, String b_state,
			LocalDateTime createdAt, Boolean isEggadded, String deliveryNotes) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.phone = phone;
		this.pincode = pincode;
		this.address = address;
		this.locality = locality;
		this.landmark = landmark;
		this.city = city;
		this.state = state;
		this.planName = planName;
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
		this.createdAt = createdAt;
		this.isEggadded = isEggadded;
		this.deliveryNotes = deliveryNotes;
	}


	// ✅ Getters & Setters

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPincode() {
		return pincode;
	}

	public void setPincode(String pincode) {
		this.pincode = pincode;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getLocality() {
		return locality;
	}

	public void setLocality(String locality) {
		this.locality = locality;
	}

	public String getLandmark() {
		return landmark;
	}

	public void setLandmark(String landmark) {
		this.landmark = landmark;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getPlanName() {
		return planName;
	}

	public void setPlanName(String planName) {
		this.planName = planName;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
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


	public Boolean getIsEggadded() {
		return isEggadded;
	}


	public void setIsEggadded(Boolean isEggadded) {
		this.isEggadded = isEggadded;
	}


	public String getDeliveryNotes() {
		return deliveryNotes;
	}


	public void setDeliveryNotes(String deliveryNotes) {
		this.deliveryNotes = deliveryNotes;
	}

}
