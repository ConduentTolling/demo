package com.conduent.tpms.qatp.config;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import io.swagger.annotations.ApiModelProperty;

public class RequestObject 
{
	@ApiModelProperty(value = "Starting device no", dataType = "String", required = true, example = "01687654789")
	@NotNull(message="StartDeviceNo cannot be null")
	@Pattern(regexp="\\b\\d{1,14}\\b",message="Invalid StartDeviceNo")
	String startDeviceNo;
	
	@ApiModelProperty(value = "End device no", dataType = "String", required = true, example = "01657658543")	
	@NotNull(message="EndDeviceNo cannot be null")
	@Pattern(regexp="\\b\\d{1,14}\\b",message="Invalid EndDeviceNo")
	String endDeviceNo;
	
	@ApiModelProperty(value = "Load Type", dataType = "long", required = true, example = "FULL/INCR")
	@NotNull(message="LoadType cannot be null")
	@Pattern(regexp="(?i)(FULL|INCR)",message="LoadType shall be either FULL or INCR")
	String loadType;
	
	@ApiModelProperty(value = "Enable History", dataType = "long", required = true, example = "YES/NO")
	@NotNull(message="EnableHistory cannot be null")
	@Pattern(regexp="(?i)(YES|NO)",message="EnableHistory shall be either YES or NO")
	String enableHistory;
	
	public String getLoadType() {
		return loadType;
	}
	public void setLoadType(String loadType) {
		this.loadType = loadType;
	}
	public String getStartDeviceNo() {
		return startDeviceNo;
	}
	public void setStartDeviceNo(String startDeviceNo) {
		this.startDeviceNo = startDeviceNo;
	}
	public String getEndDeviceNo() {
		return endDeviceNo;
	}
	public void setEndDeviceNo(String endDeviceNo) {
		this.endDeviceNo = endDeviceNo;
	}
	public String getEnableHistory() {
		return enableHistory;
	}
	public void setEnableHistory(String enableHistory) {
		this.enableHistory = enableHistory;
	}
	
	
	@Override
	public String toString() {
		return "RequestObject [startDeviceNo=" + startDeviceNo + ", endDeviceNo=" + endDeviceNo + ", loadType="
				+ loadType + ", enableHistory=" + enableHistory + "]";
	}
	
	
	
}
