package com.conduent.tpms.unmatched.dto;

public class PlateInfoDto {
	

	private Long etcAccountId;
	private String plateNumber;
	private String plateState;
	private String plateCountry;
	private Long plateType;
	
	
	public Long getEtcAccountId() {
		return etcAccountId;
	}
	public void setEtcAccountId(Long etcAccountId) {
		this.etcAccountId = etcAccountId;
	}
	public String getPlateNumber() {
		return plateNumber;
	}
	public void setPlateNumber(String plateNumber) {
		this.plateNumber = plateNumber;
	}
	public String getPlateState() {
		return plateState;
	}
	public void setPlateState(String plateState) {
		this.plateState = plateState;
	}
	public String getPlateCountry() {
		return plateCountry;
	}
	public void setPlateCountry(String plateCountry) {
		this.plateCountry = plateCountry;
	}
	public Long getPlateType() {
		return plateType;
	}
	public void setPlateType(Long plateType) {
		this.plateType = plateType;
	}
	@Override
	public String toString() {
		return "PlateInfoDto [etcAccountId=" + etcAccountId + ", plateNumber=" + plateNumber + ", plateState="
				+ plateState + ", plateCountry=" + plateCountry + ", plateType=" + plateType + "]";
	}
	
	
	
	
	
	 
}