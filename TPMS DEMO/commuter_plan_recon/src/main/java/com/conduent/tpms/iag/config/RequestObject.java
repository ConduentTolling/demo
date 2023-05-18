package com.conduent.tpms.iag.config;

/**
 * To send input parameters as json object in POST call
 * 
 * @author taniyan
 *
 */

public class RequestObject 
{
	/**
	 * Home agency id
	 */
	int agencyId;
	
	/**
	 * Required file format
	 */
	String fileType;
	
	/**
	 * Required file generation type
	 */
	String genType;
	
	
	public int getAgencyId() {
		return agencyId;
	}
	public void setAgencyId(int agencyId) {
		this.agencyId = agencyId;
	}
	public String getFileFormat() {
		return fileType;
	}
	public void setFileFormat(String fileFormat) {
		this.fileType = fileFormat;
	}
	public String getGenType() {
		return genType;
	}
	public void setGenType(String genType) {
		this.genType = genType;
	}
	
	@Override
	public String toString() {
		return "RequestObject [agencyId=" + agencyId + ", fileType=" + fileType + ", genType=" + genType + "]";
	}
	
	
}
