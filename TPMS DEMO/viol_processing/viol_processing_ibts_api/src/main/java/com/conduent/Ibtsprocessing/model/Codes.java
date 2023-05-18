package com.conduent.Ibtsprocessing.model;

public class Codes {
	
	public Codes() {
		super();
	}

	private String codeType; 
	private Integer codeId;
	private String codeValue;
	public String getCodeType() {
	return codeType;
	}
	public void setCodeType(String codeType) {
		this.codeType = codeType;
	}
	public Integer getCodeId() {
		return codeId;
	}
	public void setCodeId(Integer codeId) {
		this.codeId = codeId;
	}
	public String getCodeValue() {    
		return codeValue;
	}
	public void setCodeValue(String codeValue) {
		this.codeValue = codeValue;
	}	

	@Override
	public String toString() {
		return "Codes [codeType=" + codeType + ", codeId=" + codeId + ", codeValue=" + codeValue + ", getCodeType()="
				+ getCodeType() + ", getCodeId()=" + getCodeId() + ", getCodeValue()=" + getCodeValue()
				+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString()
				+ "]";
	   }
}
