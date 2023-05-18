package com.conduent.tpms.inrx.model;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Class for configuration value from properties file for different environment
 * 
 * @author petetid
 *
 */
@Component
public class ConfigVariable {

	@Value("${config.inrxserviceurl}")
	private String intxServiceUrl;
	
	@Value("${config.threadcount}")
	private int threadCount;

	public int getThreadCount() {
		return threadCount;
	}

	public void setThreadCount(int threadCount) {
		this.threadCount = threadCount;
	}

	public String getInrxServiceUrl() {
		return intxServiceUrl;
	}

	public void setInrxServiceUrl(String intxServiceUrl) {
		this.intxServiceUrl = intxServiceUrl;
	}

	@Override
	public String toString() {
		return "ConfigVariable [intxServiceUrl=" + intxServiceUrl + ", threadCount=" + threadCount + "]";
	}


}
