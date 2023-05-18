package com.conduent.tpms.NY12.model;

import io.swagger.annotations.ApiModelProperty;

public class Plaza 
{
	
	private Long plazaId;
	private String name;
	private String externPlazaId;
	private Long agencyId;
	private String revenueTime;
	private Integer plazaGroup;
	//  PLAZA_TYPE_IND ,
	private String calculateTollAmount;
	private String lprEnabled;
    
	public Long getPlazaId() {
		return plazaId;
	}
	public void setPlazaId(Long plazaId) {
		this.plazaId = plazaId;
	}
	public String getExternPlazaId() {
		return externPlazaId;
	}
	public void setExternPlazaId(String externPlazaId) {
		this.externPlazaId = externPlazaId;
	}
	public Long getAgencyId() {
		return agencyId;
	}
	public void setAgencyId(Long agencyId) {
		this.agencyId = agencyId;
	}
	public String getRevenueTime() {
		return revenueTime;
	}
	public void setRevenueTime(String revenueTime) {
		this.revenueTime = revenueTime;
	}
	public Integer getPlazaGroup() {
		return plazaGroup;
	}
	public void setPlazaGroup(Integer plazaGroup) {
		this.plazaGroup = plazaGroup;
	}
	public String getCalculateTollAmount() {
		return calculateTollAmount;
	}
	public void setCalculateTollAmount(String calculateTollAmount) {
		this.calculateTollAmount = calculateTollAmount;
	}
	public String getLprEnabled() {
		return lprEnabled;
	}
	public void setLprEnabled(String lprEnabled) {
		this.lprEnabled = lprEnabled;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
