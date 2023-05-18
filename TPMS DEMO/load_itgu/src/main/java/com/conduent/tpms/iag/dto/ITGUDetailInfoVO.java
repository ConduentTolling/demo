package com.conduent.tpms.iag.dto;

import java.io.Serializable;

public class ITGUDetailInfoVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2798677267041309955L;

	private String tagAgencyId;
	
	private String devicePrefix;

	
	private String tagSerialNumber;
	
	private String tagStatus;
	
	private String tagPlan;
	
	private String filetype;
	
	private String tagAccntInfo;
	

	private String tagHomeAgency;
	
	private String tagACTypeIND;
	
	private String tagAccntno;
	
	private String tagProtocol;
	
	private String tagType;
	
	private String tagMount;
	
	private String tagClass;
	
	private String xferControlID;

	private String line;
	
	public String getLine() {
		return line;
	}



	public void setLine(String line) {
		this.line = line;
	}



	public ITGUDetailInfoVO(String tagAgencyId, String tagSerialNumber, String tagStatus, String tagPlan,
			String filetype, String tagAccntInfo, String tagHomeAgency, String tagACTypeIND, String tagAccntno,
			String tagProtocol, String tagType, String tagMount, String tagClass, String xferControlID) {
		super();
		this.tagAgencyId = tagAgencyId;
		this.tagSerialNumber = tagSerialNumber;
		this.tagStatus = tagStatus;
		this.tagPlan = tagPlan;
		this.filetype = filetype;
		this.tagAccntInfo = tagAccntInfo;
		this.tagHomeAgency = tagHomeAgency;
		this.tagACTypeIND = tagACTypeIND;
		this.tagAccntno = tagAccntno;
		this.tagProtocol = tagProtocol;
		this.tagType = tagType;
		this.tagMount = tagMount;
		this.tagClass = tagClass;
		this.xferControlID = xferControlID;
	}

	
	
	public ITGUDetailInfoVO(String tagAgencyId, String deviceprefix, String tagSerialNumber, String tagStatus, String tagHomeAgency, String tagACTypeIND,  String tagAccntno,
			String tagProtocol, String tagType ,  String tagMount , String tagClass, String filetype) {
		super();
		this.tagAgencyId = tagAgencyId;
		this.devicePrefix=deviceprefix;
		this.tagSerialNumber = tagSerialNumber;
		this.tagStatus = tagStatus;
		this.tagHomeAgency=tagHomeAgency;
		this.tagACTypeIND=tagACTypeIND;
		this.tagAccntno = tagAccntno;
		this.tagProtocol=tagProtocol;
		this.tagType=tagType;
		this.tagMount=tagMount;
		this.tagClass=tagClass;
		this.filetype = filetype;
	}
	
	
	
	
	
	public ITGUDetailInfoVO() {
		
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


	public String getTagStatus() {
		return tagStatus;
	}


	public void setTagStatus(String tagStatus) {
		this.tagStatus = tagStatus;
	}


	public String getTagPlan() {
		return tagPlan;
	}


	public void setTagPlan(String tagPlan) {
		this.tagPlan = tagPlan;
	}


	public String getFiletype() {
		return filetype;
	}


	public void setFiletype(String filetype) {
		this.filetype = filetype;
	}

	
	@Override
	public String toString() {
		return "ITAGDetailInfoVO [tagAgencyId=" + tagAgencyId + ", devicePrefix=" + devicePrefix + ", tagSerialNumber="
				+ tagSerialNumber + ", tagStatus=" + tagStatus + ", filetype=" + filetype + ", tagHomeAgency="
				+ tagHomeAgency + ", tagACTypeIND=" + tagACTypeIND + ", tagAccntno=" + tagAccntno + ", tagProtocol="
				+ tagProtocol + ", tagType=" + tagType + ", tagMount=" + tagMount + ", tagClass=" + tagClass + "]";
	}
	
	
	
	public String getTagAccntInfo() {
		return tagAccntInfo;
	}



	public void setTagAccntInfo(String tagAccntInfo) {
		this.tagAccntInfo = tagAccntInfo;
	}



	public String getTagHomeAgency() {
		return tagHomeAgency;
	}



	public void setTagHomeAgency(String tagHomeAgency) {
		this.tagHomeAgency = tagHomeAgency;
	}



	public String getTagACTypeIND() {
		return tagACTypeIND;
	}



	public void setTagACTypeIND(String tagACTypeIND) {
		this.tagACTypeIND = tagACTypeIND;
	}



	public String getTagAccntno() {
		return tagAccntno;
	}



	public void setTagAccntno(String tagAccntno) {
		this.tagAccntno = tagAccntno;
	}



	public String getTagProtocol() {
		return tagProtocol;
	}



	public void setTagProtocol(String tagProtocol) {
		this.tagProtocol = tagProtocol;
	}



	public String getTagType() {
		return tagType;
	}



	public void setTagType(String tagType) {
		this.tagType = tagType;
	}



	public String getTagMount() {
		return tagMount;
	}



	public void setTagMount(String tagMount) {
		this.tagMount = tagMount;
	}



	public String getTagClass() {
		return tagClass;
	}



	public void setTagClass(String tagClass) {
		this.tagClass = tagClass;
	}



	public String getXferControlID() {
		return xferControlID;
	}



	public void setXferControlID(String xferControlID) {
		this.xferControlID = xferControlID;
	}

	public String getDevicePrefix() {
		return devicePrefix;
	}


	public void setDevicePrefix(String devicePrefix) {
		this.devicePrefix = devicePrefix;
	}
	
	
	
}
