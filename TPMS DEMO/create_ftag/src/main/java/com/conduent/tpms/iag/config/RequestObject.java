package com.conduent.tpms.iag.config;

import io.swagger.annotations.ApiModelProperty;

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
    @ApiModelProperty(value = "File Format", dataType = "String", required = true, example = "FTAG")
	String fileFormat;
	
	/**
	 * Required file generation type
	 */
    @ApiModelProperty(value = "Gen Type", dataType = "String", required = true, example = "FULL")
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
	
}
