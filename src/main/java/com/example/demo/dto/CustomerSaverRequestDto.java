package com.example.demo.dto;

public class CustomerSaverRequestDto {
	    public String firstName;
	    public String lastName;
	    public String email;
	    public String phone;
	    public String pincode;
	    public String address;
	    public String locality;
	    public String landmark;
	    public String city;
	    public String state;
	    public String planName;
	    public Boolean isEggadded;
	    public String deliveryNotes;
	    
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
	    
		public CustomerSaverRequestDto() {
			super();
			// TODO Auto-generated constructor stub
		}
		
		
		public CustomerSaverRequestDto(String firstName, String lastName, String email, String phone, String pincode,
				String address, String locality, String landmark, String city, String state, String planName,
				Boolean isEggadded, String deliveryNotes, String b_firstName, String b_lastName, String b_email,
				String b_phone, String b_address, String b_companyName, String b_gstNumber, String b_landmark,
				String b_city, String b_state) {
			super();
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
			this.isEggadded = isEggadded;
			this.deliveryNotes = deliveryNotes;
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
