package com.conduent.tpms.iag.model;

public class Tcodes {

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

	public Tcodes(String codeType, Integer codeId, String codeValue) {
		super();
		this.codeType = codeType;
		this.codeId = codeId;
		this.codeValue = codeValue;
	}

	@Override
	public String toString() {
		return "Tcodes [codeType=" + codeType + ", codeId=" + codeId + ", codeValue=" + codeValue + "]";
	}

}
