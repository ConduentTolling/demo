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

	@Value("${config.streamid.parking}")
	private String streamId;


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
}
