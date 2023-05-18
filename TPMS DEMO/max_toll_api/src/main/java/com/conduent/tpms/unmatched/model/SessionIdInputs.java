package com.conduent.tpms.unmatched.model;

public class SessionIdInputs 
{
	private Integer plazaId;
	private Integer agencyId;
	private Integer sectionId;
	private String scheduledPricingFlag;
	
	
	public Integer getPlazaId() {
		return plazaId;
	}
	public void setPlazaId(Integer plazaId) {
		this.plazaId = plazaId;
	}
	public Integer getAgencyId() {
		return agencyId;
	}
	public void setAgencyId(Integer agencyId) {
		this.agencyId = agencyId;
	}
	
	public Integer getSectionId() {
		return sectionId;
	}
	public void setSectionId(Integer sectionId) {
		this.sectionId = sectionId;
	}
	public String getScheduledPricingFlag() {
		return scheduledPricingFlag;
	}
	public void setScheduledPricingFlag(String scheduledPricingFlag) {
		this.scheduledPricingFlag = scheduledPricingFlag;
	}
	
	@Override
	public String toString() {
		return "SessionIdInputs [plazaId=" + plazaId + ", agencyId=" + agencyId + ", sectionId=" + sectionId
				+ ", scheduledPricingFlag=" + scheduledPricingFlag + "]";
	}
	

}
