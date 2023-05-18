package com.conduent.tpms.qeval.dto;
import java.io.Serializable;
import java.time.LocalDateTime;

public class TCodesVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String codeType; 
	private Integer codeId;
	private String codeValue;
	private String displayValueLong;
	private String charField1;
	private String charField2;
	private String numberField1;
	private String numberField2;
	private LocalDateTime updateTs;
	
	
	
	public TCodesVO() {
	
	}
	public TCodesVO(String codeType, Integer codeId, String codeValue, String displayValueLong, String charField1,
			String charField2, String numberField1, String numberField2, LocalDateTime updateTs) {
		super();
		this.codeType = codeType;
		this.codeId = codeId;
		this.codeValue = codeValue;
		this.displayValueLong = displayValueLong;
		this.charField1 = charField1;
		this.charField2 = charField2;
		this.numberField1 = numberField1;
		this.numberField2 = numberField2;
		this.updateTs = updateTs;
	}
	
	
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
	public String getDisplayValueLong() {
		return displayValueLong;
	}
	public void setDisplayValueLong(String displayValueLong) {
		this.displayValueLong = displayValueLong;
	}
	public String getCharField1() {
		return charField1;
	}
	public void setCharField1(String charField1) {
		this.charField1 = charField1;
	}
	public String getCharField2() {
		return charField2;
	}
	public void setCharField2(String charField2) {
		this.charField2 = charField2;
	}
	public String getNumberField1() {
		return numberField1;
	}
	public void setNumberField1(String numberField1) {
		this.numberField1 = numberField1;
	}
	public String getNumberField2() {
		return numberField2;
	}
	public void setNumberField2(String numberField2) {
		this.numberField2 = numberField2;
	}
	public LocalDateTime getUpdateTs() {
		return updateTs;
	}
	public void setUpdateTs(LocalDateTime updateTs) {
		this.updateTs = updateTs;
	}
	
	
	@Override
	public String toString() {
		return "TCodeVo [codeType=" + codeType + ", codeId=" + codeId + ", codeValue=" + codeValue
				+ ", displayValueLong=" + displayValueLong + ", charField1=" + charField1 + ", charField2=" + charField2
				+ ", numberField1=" + numberField1 + ", numberField2=" + numberField2 + ", updateTs=" + updateTs + "]";
	}
}
