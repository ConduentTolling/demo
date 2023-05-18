package com.conduent.tpms.iag.config;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

/**
 * To send input parameters as json object in POST call
 * 
 * @author urvashic
 *
 */

public class RequestObject 
{
	/**
	 * Home agency id
	 */
	@ApiModelProperty(value="Agency ID",dataType ="Integer", required =true, example ="2")
	@JsonProperty(required=true)
	int agencyId;
	
	/**
	 * Required file format
	 */
	@ApiModelProperty(value="Agency file Type",dataType ="String", required =true, example ="TSI")
	@JsonProperty(required=true)
	String fileType;
	
	/**
	 * Required file generation type
	 */
	@ApiModelProperty(value ="Agency file generation file", dataType ="String",required=true,example="INCR")
	@JsonProperty(required=true)
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
