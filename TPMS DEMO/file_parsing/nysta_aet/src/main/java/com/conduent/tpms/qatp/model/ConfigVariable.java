package com.conduent.tpms.qatp.model;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Class for configuration value from properties file for different environment
 * 
 * @author deepeshb
 *
 */
@Component
public class ConfigVariable {


	@Value("${config.configfilepath}")
	private String configfilepath;

	@Value("${config.streamid}")
	private String streamId;
	
	@Value("${config.ocrAPI}")
	private String ocrAPI;
	
	@Value("${config.insertLaneAPI}")
	private String insertLane;
	
	@Value("${config.oss.throttleFlag}")
	private String throttleFlag;
	
	@Value("${config.oss.throttleTime}")
	private Long throttleTime;
	
	@Value("${config.tollCalculationUri}")
	private String tollCalculationUri;
	
	
	public String getTollCalculationUri() {
		return tollCalculationUri;
	}

	public void setTollCalculationUri(String tollCalculationUri) {
		this.tollCalculationUri = tollCalculationUri;
	}

	public String getThrottleFlag() {
		return throttleFlag;
	}

	public void setThrottleFlag(String throttleFlag) {
		this.throttleFlag = throttleFlag;
	}

	public Long getThrottleTime() {
		return throttleTime;
	}

	public void setThrottleTime(Long throttleTime) {
		this.throttleTime = throttleTime;
	}

	public String getInsertLane() {
		return insertLane;
	}

	public void setInsertLane(String insertLane) {
		this.insertLane = insertLane;
	}

	public String getConfigfilepath() {
		return configfilepath;
	}

	public void setConfigfilepath(String configfilepath) {
		this.configfilepath = configfilepath;
	}


	public String getStreamId() {
		return streamId;
	}

	public void setStreamId(String streamId) {
		this.streamId = streamId;
	}

	public String getOcrAPI() {
		return ocrAPI;
	}

	public void setOcrAPI(String ocrAPI) {
		this.ocrAPI = ocrAPI;
	}

	
}
