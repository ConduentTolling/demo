package com.conduent.tpms.tollcalculation.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

public class APIInputs {
	
	@ApiModelProperty(hidden = true)
	private String entryPlazaId;

	@JsonProperty(required = true)
	// @NumberFormat(style=Style.NUMBER)
	private Integer exitPlazaId;

	@JsonProperty(required = true)
	// @NumberFormat(style=Style.NUMBER)
	private Integer planType;

	@JsonProperty(required = true)
	// @NumberFormat(style=Style.NUMBER)
	private Integer tollRevenueType;
	
	@JsonProperty(required = true)
	private String txTimestamp;

	@JsonProperty(required = true)
	// @NumberFormat(style=Style.NUMBER)
	private Integer agencyId;

	@JsonProperty(required = true)
	// @NumberFormat(style=Style.NUMBER)
	private Integer actualClass;

	@JsonProperty(required = true)
	private Integer accountType;
	
	@JsonProperty(required = true)
	private String tollSystemType;
	
	public Integer getAccountType() {
		return accountType;
	}

	public void setAccountType(Integer accountType) {
		this.accountType = accountType;
	}

	public String getTollSystemType() {
		return tollSystemType;
	}

	public void setTollSystemType(String tollSystemType) {
		this.tollSystemType = tollSystemType;
	}

	@JsonProperty(required = true)
	// @NumberFormat(style=Style.NUMBER)
	private Long laneTxId;
	
	public String getEntryPlazaId() {
		return entryPlazaId;
	}

	public void setEntryPlazaId(String entryPlazaId) {
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
	
	public Long getLaneTxId() {
		return laneTxId;
	}

	public void setLaneTxId(Long laneTxId) {
		this.laneTxId = laneTxId;
	}

	@Override
	public String toString() {
		return "APIInputs [entryPlazaId=" + entryPlazaId + ", exitPlazaId=" + exitPlazaId + ", planType=" + planType
				+ ", tollRevenueType=" + tollRevenueType + ", txTimestamp=" + txTimestamp + ", agencyId=" + agencyId
				+ ", actualClass=" + actualClass + ", accountType=" + accountType + ", tollSystemType=" + tollSystemType
				+ ", laneTxId=" + laneTxId + "]";
	}

	

	

	
}
