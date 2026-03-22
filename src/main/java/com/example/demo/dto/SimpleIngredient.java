package com.example.demo.dto;

public class SimpleIngredient {
	 private String name;
	    private String weight;
	    private Boolean packed;
	
		public SimpleIngredient(String name, String weight, Boolean packed) {
			super();
			this.name = name;
			this.weight = weight;
			this.packed = packed;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getWeight() {
			return weight;
		}
		public void setWeight(String weight) {
			this.weight = weight;
		}
		public Boolean getPacked() {
			return packed;
		}
		public void setPacked(Boolean packed) {
			this.packed = packed;
		}
	    
	    
}
