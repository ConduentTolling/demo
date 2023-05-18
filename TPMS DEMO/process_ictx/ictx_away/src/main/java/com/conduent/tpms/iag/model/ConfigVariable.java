package com.conduent.tpms.iag.model;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Class for configuration value from properties file for different environment
 * 
 * @author Urvashic
 *
 */
@Component
public class ConfigVariable {


	@Value("${config.configfilepath}")
	private String configfilepath;

	@Value("${config.home.streamid}")
	private String streamId;
	
	@Value("${config.ocrAPI}")
	private String ocrAPI;

	@Value("${config.accountApiUri}")
	private String accountApiUri;
	
	@Value("${config.insertLaneAPI}")
	private String insertLane;
	
	public String getInsertLane() {
		return insertLane;
	}

	public void setInsertLane(String insertLane) {
		this.insertLane = insertLane;
	}

	public String getAccountApiUri() {
		return accountApiUri;
	}

	public void setAccountApiUri(String accountApiUri) {
		this.accountApiUri = accountApiUri;
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
