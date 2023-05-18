package com.conduent.tpms.ictx.model;

public class TollCalculationDto {

	
	private Integer entryPlazaId;
	private Integer exitPlazaId;
	private Integer planType;
	private Integer tollRevenueType;
	private String txDate;
	private String txTimestamp;
	private Integer agencyId;
	private Integer actualClass;
	private Integer accountType;
	private String tollSystemType;
	
	public Integer getEntryPlazaId() {
		return entryPlazaId;
	}
	public void setEntryPlazaId(Integer entryPlazaId) {
		this.entryPlazaId = entryPlazaId;
	}
	public Integer getExitPlazaId() {
		return exitPlazaId;
	}
	public void setExitPlazaId(Integer exitPlazaId) {
		this.exitPlazaId = exitPlazaId;
	}
	public Integer getPlanType() {
		return planType;
	}
	public void setPlanType(Integer planType) {
		this.planType = planType;
	}
	public Integer getTollRevenueType() {
		return tollRevenueType;
	}
	public void setTollRevenueType(Integer tollRevenueType) {
		this.tollRevenueType = tollRevenueType;
	}
	public String getTxDate() {
		return txDate;
	}
	public void setTxDate(String txDate) {
		this.txDate = txDate;
	}
	public String getTxTimestamp() {
		return txTimestamp;
	}
	public void setTxTimestamp(String txTimestamp) {
		this.txTimestamp = txTimestamp;
	}
	public Integer getAgencyId() {
		return agencyId;
	}
	public void setAgencyId(Integer agencyId) {
		this.agencyId = agencyId;
	}
	public Integer getActualClass() {
		return actualClass;
	}
	public void setActualClass(Integer actualClass) {
		this.actualClass = actualClass;
	}
	
	public String getTollSystemType() {
		return tollSystemType;
	}
	public void setTollSystemType(String tollSystemType) {
		this.tollSystemType = tollSystemType;
	}
	public Integer getAccountType() {
		return accountType;
	}
	public void setAccountType(Integer accountType) {
		this.accountType = accountType;
	}
	@Override
	public String toString() {
		return "TollCalculationDto [entryPlazaId=" + entryPlazaId + ", exitPlazaId=" + exitPlazaId + ", planType="
				+ planType + ", tollRevenueType=" + tollRevenueType + ", txDate=" + txDate + ", txTimestamp="
				+ txTimestamp + ", agencyId=" + agencyId + ", actualClass=" + actualClass + ", accountType="
				+ accountType + ", tollSystemType=" + tollSystemType + "]";
	}
	
	
	

}
