package com.conduent.tpms.iag.dto;

import java.io.Serializable;

import com.conduent.tpms.iag.validation.BaseVO;

public class IagFileNameInfo extends BaseVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3724514409417122576L;

	private String fromAgencyId;
	public String getFromAgencyId() {
		return fromAgencyId;
	}





	public void setFromAgencyId(String fromAgencyId) {
		this.fromAgencyId = fromAgencyId;
	}





	public String getToAgencyId() {
		return toAgencyId;
	}





	public void setToAgencyId(String toAgencyId) {
		this.toAgencyId = toAgencyId;
	}





	public String getFileDateTime() {
		return fileDateTime;
	}





	public void setFileDateTime(String fileDateTime) {
		this.fileDateTime = fileDateTime;
	}





	public String getExtension() {
		return extension;
	}





	public void setExtension(String extension) {
		this.extension = extension;
	}





	public String getFileExtension() {
		return fileExtension;
	}





	public void setFileExtension(String fileExtension) {
		this.fileExtension = fileExtension;
	}





	private String toAgencyId;
	private String fileDateTime;
	private String extension;
	private String fileExtension;
	private String fromToAgencyId;

		
	
	
	
	public String getFromToAgencyId() {
		return fromToAgencyId;
	}





	public void setFromToAgencyId(String fromToAgencyId) {
		this.fromToAgencyId = fromToAgencyId;
	}





	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}