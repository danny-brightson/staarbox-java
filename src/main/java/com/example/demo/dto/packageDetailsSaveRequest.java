package com.example.demo.dto;

public class packageDetailsSaveRequest {

	private int packDetailsId;
	

	private long customerId;
	
	public packageDetailsSaveRequest() {
		super();
		// TODO Auto-generated constructor stub
	}


	

	public packageDetailsSaveRequest(int packDetailsId, long customerId) {
		super();
		this.packDetailsId = packDetailsId;
		this.customerId = customerId;
	}




	public int getPackDetailsId() {
		return packDetailsId;
	}

	public void setPackDetailsId(int packDetailsId) {
		this.packDetailsId = packDetailsId;
	}


	public long getCustomerId() {
		return customerId;
	}


	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}
	
	
	
}
