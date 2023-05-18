package com.conduent.tpms.process25a.model;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ConfigVariable {

	@Value("${config.batchsize}")
	private Long batchSize;
	
	@Value("${config.tollPostingUri}")
	private String tollPostingUri;
	
	@Value("${config.configfilepath}")
	private String configFilePath;
	
	@Value("${config.icrx.streamid}")
	private String ibtsQueue;
	
	@Value("${config.maxTollUri}")
	private String maxTollUri;
	
	public String getMaxTollUri() {
		return maxTollUri;
	}

	public void setMaxTollUri(String maxTollUri) {
		this.maxTollUri = maxTollUri;
	}

	public String getConfigFilePath() {
		return configFilePath;
	}

	public void setConfigFilePath(String configFilePath) {
		this.configFilePath = configFilePath;
	}

	public String getIbtsQueue() {
		return ibtsQueue;
	}

	public void setIbtsQueue(String ibtsQueue) {
		this.ibtsQueue = ibtsQueue;
	}

	public String getTollPostingUri() {
		return tollPostingUri;
	}

	public void setTollPostingUri(String tollPostingUri) {
		this.tollPostingUri = tollPostingUri;
	}

	public Long getBatchSize() {
		return batchSize;
	}

	public void setBatchSize(Long batchSize) {
		this.batchSize = batchSize;
	}
}
