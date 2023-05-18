package com.conduent.tpms.icrx.model;

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

	@Value("${config.icrxserviceurl}")
	private String ictxServiceUrl;
	
	@Value("${config.threadcount}")
	private int threadCount;

	public int getThreadCount() {
		return threadCount;
	}

	public void setThreadCount(int threadCount) {
		this.threadCount = threadCount;
	}

	public String getIcrxServiceUrl() {
		return ictxServiceUrl;
	}

	public void setIcrxServiceUrl(String ictxServiceUrl) {
		this.ictxServiceUrl = ictxServiceUrl;
	}

	@Override
	public String toString() {
		return "ConfigVariable [ictxServiceUrl=" + ictxServiceUrl + ", threadCount=" + threadCount + "]";
	}


}
