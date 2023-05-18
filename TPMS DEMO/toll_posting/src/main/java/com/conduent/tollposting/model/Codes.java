package com.conduent.tollposting.model;

public class Codes 
{
	private Long codeId;
	private String codeType;
	private String codeValue;
	private String displayValue;
	private String displayValueLong;
	
	public Long getCodeId() {
		return codeId;
	}
	public void setCodeId(Long codeId) {
		this.codeId = codeId;
	}
	public String getCodeType() {
		return codeType;
	}
	public void setCodeType(String codeType) {
		this.codeType = codeType;
	}
	public String getCodeValue() {
		return codeValue;
	}
	public void setCodeValue(String codeValue) {
		this.codeValue = codeValue;
	}
	public String getDisplayValue() {
		return displayValue;
	}
	public void setDisplayValue(String displayValue) {
		this.displayValue = displayValue;
	}
	public String getDisplayValueLong() {
		return displayValueLong;
	}
	public void setDisplayValueLong(String displayValueLong) {
		this.displayValueLong = displayValueLong;
	}
}