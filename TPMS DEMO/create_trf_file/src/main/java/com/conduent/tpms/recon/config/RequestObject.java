package com.conduent.tpms.recon.config;

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
	@ApiModelProperty(value = "Agency Id", dataType ="int", required = true, example = "2")
	int agencyId;
	
	/**
	 * Required file format
	 */
	@ApiModelProperty(value = "File Format", dataType ="String", required = true, example = "TRF")
	String fileFormat;
	
	/**
	 * Required file generation type
	 */
	@ApiModelProperty(value = "File Genaration Type", dataType ="String", required = true, example = "FIXED")
	String genType;
	
	
	public int getAgencyId() {
		return agencyId;
	}
	public void setAgencyId(int agencyId) {
		this.agencyId = agencyId;
	}
	
	public String getFileFormat() {
		return fileFormat;
	}
	public void setFileFormat(String fileFormat) {
		this.fileFormat = fileFormat;
	}
	public String getGenType() {
		return genType;
	}
	public void setGenType(String genType) {
		this.genType = genType;
	}
	@Override
	public String toString() {
		return "RequestObject [agencyId=" + agencyId + ", fileFormat=" + fileFormat + ", genType=" + genType + "]";
	}
	
	
	
}
