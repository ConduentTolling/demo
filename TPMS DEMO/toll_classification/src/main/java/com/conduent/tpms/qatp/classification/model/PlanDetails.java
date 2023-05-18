package com.conduent.tpms.qatp.classification.model;

public class PlanDetails {

	private Long planType;
	private String PlanTypeName;
	
	public Long getPlanType() {
		return planType;
	}
	public void setPlanType(Long planType) {
		this.planType = planType;
	}
	public String getPlanTypeName() {
		return PlanTypeName;
	}
	public void setPlanTypeName(String planTypeName) {
		PlanTypeName = planTypeName;
	}
	
	
	@Override
	public String toString() {
		return "PlanDetails [planType=" + planType + ", PlanTypeName=" + PlanTypeName + "]";
	}

	
	
}
