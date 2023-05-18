package com.conduent.tpms.iag.dto;


/**
 * @author taniyan
 * 
 */

public class PostUsagePlanDetailDto 
{
	String planName;
	String usageType;
	Integer minUsage;
	Integer maxUsage;
	String benefitType;
	Integer benefitValue;
	public String getPlanName() {
		return planName;
	}
	public void setPlanName(String planName) {
		this.planName = planName;
	}
	public String getUsageType() {
		return usageType;
	}
	public void setUsageType(String usageType) {
		this.usageType = usageType;
	}
	public Integer getMinUsage() {
		return minUsage;
	}
	public void setMinUsage(Integer minUsage) {
		this.minUsage = minUsage;
	}
	public Integer getMaxUsage() {
		return maxUsage;
	}
	public void setMaxUsage(Integer maxUsage) {
		this.maxUsage = maxUsage;
	}
	public String getBenefitType() {
		return benefitType;
	}
	public void setBenefitType(String benefitType) {
		this.benefitType = benefitType;
	}
	public Integer getBenefitValue() {
		return benefitValue;
	}
	public void setBenefitValue(Integer benefitValue) {
		this.benefitValue = benefitValue;
	}
	
	@Override
	public String toString() {
		return "PostUsagePlanDetailDto [planName=" + planName + ", usageType=" + usageType + ", minUsage=" + minUsage
				+ ", maxUsage=" + maxUsage + ", benefitType=" + benefitType + ", benefitValue=" + benefitValue + "]";
	}
}
