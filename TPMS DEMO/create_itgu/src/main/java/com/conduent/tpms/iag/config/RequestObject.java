package com.conduent.tpms.iag.config;

import io.swagger.annotations.ApiModelProperty;

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
	@ApiModelProperty(value = "Agency Id", dataType = "Integer", required = true, example = "2")
	int agencyId;
	
	/**
	 * Required file format
	 */
	@ApiModelProperty(value = "Agency file type", dataType = "String", required = true, example = "ITGU")
	String fileType;
	
	/**
	 * Required file generation type
	 */
	@ApiModelProperty(value = "Agency file generation type", dataType = "String", required = true, example = "FULL/INCR")
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
