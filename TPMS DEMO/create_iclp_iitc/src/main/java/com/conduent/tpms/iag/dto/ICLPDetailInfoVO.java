package com.conduent.tpms.iag.dto;

import java.io.Serializable;

public class ICLPDetailInfoVO implements Serializable {

	/**
	 * License plate file detail records
	 */
	
	private static final long serialVersionUID = 2798677267041309955L;
	
	private String licState;
	
	private String licNumber;
	
	private String licType;
	
	private String tagAgencyId;
	
	private String tagSerialNumber;
	
	private String deviceNo;
	
	private String filetype;
	
	public ICLPDetailInfoVO() {
		
	}
	
	public ICLPDetailInfoVO(String licState, String licNumber, String licType, String deviceNo, String filetype) {
		super();
		this.licState = licState;
		this.licNumber = licNumber;
		this.licType = licType;
		this.deviceNo = deviceNo;
		this.filetype = filetype;
	}
	
	public ICLPDetailInfoVO(String licState, String licNumber, String deviceNo, String licType) {
		super();
		this.licState = licState;
		this.licNumber = licNumber;
		this.deviceNo = deviceNo;
		this.licType = licType;
	}

	public String getDeviceNo() {
		return deviceNo;
	}

	public void setDeviceNo(String deviceNo) {
		this.deviceNo = deviceNo;
	}
	
	public String getLicState() {
		return licState;
	}

	public void setLicState(String licState) {
		this.licState = licState;
	}

	public String getLicNumber() {
		return licNumber;
	}

	public void setLicNumber(String licNumber) {
		this.licNumber = licNumber;
	}

	public String getLicType() {
		return licType;
	}

	public void setLicType(String licType) {
		this.licType = licType;
	}

	public String getTagAgencyId() {
		return tagAgencyId;
	}

	public void setTagAgencyId(String tagAgencyId) {
		this.tagAgencyId = tagAgencyId;
	}

	public String getTagSerialNumber() {
		return tagSerialNumber;
	}

	public void setTagSerialNumber(String tagSerialNumber) {
		this.tagSerialNumber = tagSerialNumber;
	}

	public String getFiletype() {
		return filetype;
	}

	public void setFiletype(String filetype) {
		this.filetype = filetype;
	}

	@Override
	public String toString() {
		return "ICLPDetailInfoVO [licState=" + licState + ", licNumber=" + licNumber + ", licType=" + licType
				+ ", tagAgencyId=" + tagAgencyId + ", tagSerialNumber=" + tagSerialNumber + ", deviceNo=" + deviceNo
				+ ", filetype=" + filetype + "]";
	}

	
}
