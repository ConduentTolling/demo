package com.conduent.tpms.iag.dto;

import java.io.Serializable;

public class ITAGDetailInfoVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2798677267041309955L;

	private String tagAgencyId;
	
	private String tagSerialNumber;
	
	private String tagStatus;
	
	private String tagPlan;
	
	private String filetype;

	
	public ITAGDetailInfoVO(String tagAgencyId, String tagSerialNumber, String tagStatus, String tagPlan,
			String filetype) {
		super();
		this.tagAgencyId = tagAgencyId;
		this.tagSerialNumber = tagSerialNumber;
		this.tagStatus = tagStatus;
		this.tagPlan = tagPlan;
		this.filetype = filetype;
	}

	public ITAGDetailInfoVO() {
		
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
		return "ITAGDetailInfoVO [tagAgencyId=" + tagAgencyId + ", tagSerialNumber=" + tagSerialNumber + ", tagStatus="
				+ tagStatus + ", tagPlan=" + tagPlan + ", filetype=" + filetype + "]";
	}

	
	
	
}
