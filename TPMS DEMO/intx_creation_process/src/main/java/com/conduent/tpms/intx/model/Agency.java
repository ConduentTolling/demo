package com.conduent.tpms.intx.model;

/**
 * Agency Model
 * 
 * @author deepeshb
 *
 */
public class Agency {

	private Long agencyId;

	private String devicePrefix;

	private String isHomeAgency;

	private Integer parentAgencyId;

	private String scheduledPricingFlag;

	private String calculateTollAmount;

	private String agencyShortName;

	public Long getAgencyId() {
		return agencyId;
	}

	public void setAgencyId(Long agencyId) {
		this.agencyId = agencyId;
	}

	public String getDevicePrefix() {
		return devicePrefix;
	}

	public void setDevicePrefix(String devicePrefix) {
		this.devicePrefix = devicePrefix;
	}

	public String getIsHomeAgency() {
		return isHomeAgency;
	}

	public void setIsHomeAgency(String isHomeAgency) {
		this.isHomeAgency = isHomeAgency;
	}

	public Integer getParentAgencyId() {
		return parentAgencyId;
	}

	public void setParentAgencyId(Integer parentAgencyId) {
		this.parentAgencyId = parentAgencyId;
	}

	public String getScheduledPricingFlag() {
		return scheduledPricingFlag;
	}

	public void setScheduledPricingFlag(String scheduledPricingFlag) {
		this.scheduledPricingFlag = scheduledPricingFlag;
	}

	public String getCalculateTollAmount() {
		return calculateTollAmount;
	}

	public void setCalculateTollAmount(String calculateTollAmount) {
		this.calculateTollAmount = calculateTollAmount;
	}

	public String getAgencyShortName() {
		return agencyShortName;
	}

	public void setAgencyShortName(String agencyShortName) {
		this.agencyShortName = agencyShortName;
	}

	@Override
	public String toString() {
		return "Agency [agencyId=" + agencyId + ", devicePrefix=" + devicePrefix + ", isHomeAgency=" + isHomeAgency
				+ ", parentAgencyId=" + parentAgencyId + ", scheduledPricingFlag=" + scheduledPricingFlag
				+ ", calculateTollAmount=" + calculateTollAmount + ", agencyShortName=" + agencyShortName + "]";
	}

}
