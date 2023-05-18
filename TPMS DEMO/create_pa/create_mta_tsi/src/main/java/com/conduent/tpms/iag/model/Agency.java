package com.conduent.tpms.iag.model;

import io.swagger.annotations.ApiModelProperty;

/**
 *
 * Model class for T_Agency table
 * 
 * @author Urvashic
 *
 */
public class Agency {

	
	private Long agencyId;
	private String agencyName;
	private String agencyShortName;
	private String devicePrefix;
	private String isHomeAgency;
	private Integer parentAgencyId;
	private String scheduledPricingFlag;
	public Long getAgencyId() {
		return agencyId;
	}
	public void setAgencyId(Long agencyId) {
		this.agencyId = agencyId;
	}
	public String getAgencyName() {
		return agencyName;
	}
	public void setAgencyName(String agencyName) {
		this.agencyName = agencyName;
	}
	public String getAgencyShortName() {
		return agencyShortName;
	}
	public void setAgencyShortName(String agencyShortName) {
		this.agencyShortName = agencyShortName;
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
	
	@Override
	public String toString() {
		return "Agency [agencyId=" + agencyId + ", agencyName=" + agencyName + ", agencyShortName=" + agencyShortName
				+ ", devicePrefix=" + devicePrefix + ", isHomeAgency=" + isHomeAgency + ", parentAgencyId="
				+ parentAgencyId + ", scheduledPricingFlag=" + scheduledPricingFlag + "]";
	}

	/*agency_id,
    agency_name,
    Upper(agency_short_name),
    device_prefix,
    is_home_agency,
    To_number(parent_agency_id),
    scheduled_pricing_flag,
    clmm_adj_allowed,
    current_iag_version,
    is_iag_style_reciprocity,
    calculate_toll_amount,
    consortium,
    file_prefix
	*/
	
	
	
	
}
