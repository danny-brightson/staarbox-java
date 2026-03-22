package com.example.demo.dto;

public class PlanCountDto {

	private String planCode;
    private Long planCount;
    private Long totalBoxCount;

    public PlanCountDto(String planCode, Long planCount, Long totalBoxCount) {
        this.planCode = planCode;
        this.planCount = planCount;
        this.totalBoxCount = totalBoxCount;
    }
	public PlanCountDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getPlanCode() {
		return planCode;
	}
	public void setPlanCode(String planCode) {
		this.planCode = planCode;
	}
	public Long getPlanCount() {
		return planCount;
	}
	public void setPlanCount(Long planCount) {
		this.planCount = planCount;
	}
	public Long getTotalBoxCount() {
		return totalBoxCount;
	}
	public void setTotalBoxCount(Long totalBoxCount) {
		this.totalBoxCount = totalBoxCount;
	}
    
    
	
}
