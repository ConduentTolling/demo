package com.conduent.tpms.qatp.dto;

import com.google.gson.annotations.Expose;

public class OCRDto {

	@Expose(serialize = true, deserialize = true)
	private Long    laneTxId;
	@Expose(serialize = true, deserialize = true)
	private String  fOcrPlateCountry;
	@Expose(serialize = true, deserialize = true)
	private String  fOcrPlateState;
	@Expose(serialize = true, deserialize = true)
	private String  fOcrPlateType;
	@Expose(serialize = true, deserialize = true)
	private String  fOcrPlateNumber;
	@Expose(serialize = true, deserialize = true)
	private Integer fOcrConfidence;
	@Expose(serialize = true, deserialize = true)
	private Integer fOcrImageIndex;
	@Expose(serialize = true, deserialize = true)
	private String  fOcrImageColor;
	@Expose(serialize = true, deserialize = true)
	private String  rOcrPlateCountry;
	@Expose(serialize = true, deserialize = true)
	private String  rOcrPlateState;
	@Expose(serialize = true, deserialize = true)
	private String  rOcrPlateType;
	@Expose(serialize = true, deserialize = true)
	private String  rOcrPlateNumber;
	@Expose(serialize = true, deserialize = true)
	private Integer rOcrConfidence;
	@Expose(serialize = true, deserialize = true)
	private Integer rOcrImageIndex;
	@Expose(serialize = true, deserialize = true)
	private String  rImageColor;
	@Expose(serialize = true, deserialize = true)
	private String txDate;
	
	public Long getLaneTxId() {
		return laneTxId;
	}
	public void setLaneTxId(Long laneTxId) {
		this.laneTxId = laneTxId;
	}
	public String getfOcrPlateCountry() {
		return fOcrPlateCountry;
	}
	public void setfOcrPlateCountry(String fOcrPlateCountry) {
		this.fOcrPlateCountry = fOcrPlateCountry;
	}
	public String getfOcrPlateState() {
		return fOcrPlateState;
	}
	public void setfOcrPlateState(String fOcrPlateState) {
		this.fOcrPlateState = fOcrPlateState;
	}
	public String getfOcrPlateType() {
		return fOcrPlateType;
	}
	public void setfOcrPlateType(String fOcrPlateType) {
		this.fOcrPlateType = fOcrPlateType;
	}
	public String getfOcrPlateNumber() {
		return fOcrPlateNumber;
	}
	public void setfOcrPlateNumber(String fOcrPlateNumber) {
		this.fOcrPlateNumber = fOcrPlateNumber;
	}
	public Integer getfOcrConfidence() {
		return fOcrConfidence;
	}
	public void setfOcrConfidence(Integer fOcrConfidence) {
		this.fOcrConfidence = fOcrConfidence;
	}
	public Integer getfOcrImageIndex() {
		return fOcrImageIndex;
	}
	public void setfOcrImageIndex(Integer fOcrImageIndex) {
		this.fOcrImageIndex = fOcrImageIndex;
	}
	public String getfOcrImageColor() {
		return fOcrImageColor;
	}

	public void setfOcrImageColor(String fOcrImageColor) {
		this.fOcrImageColor = fOcrImageColor;
	}
	public String getrOcrPlateCountry() {
		return rOcrPlateCountry;
	}
	public void setrOcrPlateCountry(String rOcrPlateCountry) {
		this.rOcrPlateCountry = rOcrPlateCountry;
	}
	public String getrOcrPlateState() {
		return rOcrPlateState;
	}
	public void setrOcrPlateState(String rOcrPlateState) {
		this.rOcrPlateState = rOcrPlateState;
	}
	public String getrOcrPlateType() {
		return rOcrPlateType;
	}
	public void setrOcrPlateType(String rOcrPlateType) {
		this.rOcrPlateType = rOcrPlateType;
	}
	public String getrOcrPlateNumber() {
		return rOcrPlateNumber;
	}
	public void setrOcrPlateNumber(String rOcrPlateNumber) {
		this.rOcrPlateNumber = rOcrPlateNumber;
	}
	public Integer getrOcrConfidence() {
		return rOcrConfidence;
	}
	public void setrOcrConfidence(Integer rOcrConfidence) {
		this.rOcrConfidence = rOcrConfidence;
	}
	public Integer getrOcrImageIndex() {
		return rOcrImageIndex;
	}
	public void setrOcrImageIndex(Integer rOcrImageIndex) {
		this.rOcrImageIndex = rOcrImageIndex;
	}
	public String getrImageColor() {
		return rImageColor;
	}
	public void setrImageColor(String rImageColor) {
		this.rImageColor = rImageColor;
	}
	public String getTxDate() {
		return txDate;
	}
	public void setTxDate(String txDate) {
		this.txDate = txDate;
	}
}
