package com.example.demo.dto;

import java.util.List;

public class PackNamesRequest {
	 private Long boxNumber;
	    private Long packerId;
	    private List<String> productNames;
	    private Integer districtId;

		public PackNamesRequest(Long boxNumber, Long packerId, List<String> productNames, Integer districtId) {
			super();
			this.boxNumber = boxNumber;
			this.packerId = packerId;
			this.productNames = productNames;
			this.districtId = districtId;
		}
		public Long getBoxNumber() {
			return boxNumber;
		}
		public void setBoxNumber(Long boxNumber) {
			this.boxNumber = boxNumber;
		}
		public Long getPackerId() {
			return packerId;
		}
		public void setPackerId(Long packerId) {
			this.packerId = packerId;
		}
		public List<String> getProductNames() {
			return productNames;
		}
		public void setProductNames(List<String> productNames) {
			this.productNames = productNames;
		}
		public Integer getDistrictId() {
			return districtId;
		}
		public void setDistrictiId(Integer districtId) {
			this.districtId = districtId;
		}
	    
	    
	
}
