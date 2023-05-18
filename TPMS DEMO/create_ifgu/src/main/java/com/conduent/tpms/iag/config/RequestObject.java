package com.conduent.tpms.iag.config;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.springframework.format.annotation.NumberFormat;
import org.springframework.format.annotation.NumberFormat.Style;

import io.swagger.annotations.ApiModelProperty;

public class RequestObject 
{
	/**
	 * Home agency id
	 */
	@NotNull(message = "AgencyId shouldn't be null")
	@NumberFormat(style = Style.NUMBER)
	@ApiModelProperty(value = "Agency Id", dataType = "Integer", required = true, example = "2")
	int agencyId;
	
	/**
	 * Required file format
	 */
	@NotNull(message="FileFormat cannot be null")
	@Pattern(regexp = "IFGU",message="FileFormat should be IFGU")
	@ApiModelProperty(value = "File Format", dataType = "String", required = true, example = "IFGU")
	String fileFormat;
	
	/**
	 * Required file generation type
	 */
	@NotNull(message="GenType cannot be null")
	@Pattern(regexp = "INCREMENT",message="Gentype should be INCREMENT")
	@ApiModelProperty(value = "Gen type", dataType = "String", required = true, example = "INCREMENT")
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
