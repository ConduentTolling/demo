package com.conduent.tpms.intx.model;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ConfigVariable {

	@Value("${config.intxServiceUrl}")
	private String intxServiceUrl;
	
	@Value("${config.itxnServiceUrl}")
	private String itxnServiceUrl;
	
	@Value("${config.threadcount}")
	private int threadCount;

	public String getIntxServiceUrl() {
		return intxServiceUrl;
	}

	public void setIntxServiceUrl(String intxServiceUrl) {
		this.intxServiceUrl = intxServiceUrl;
	}

	public String getItxnServiceUrl() {
		return itxnServiceUrl;
	}

	public void setItxnServiceUrl(String itxnServiceUrl) {
		this.itxnServiceUrl = itxnServiceUrl;
	}

    public int getThreadCount() {
        return threadCount;
    }

    public void setThreadCount(int threadCount) {
        this.threadCount = threadCount;
    }

    @Override
    public String toString() {
        return "ConfigVariable [intxServiceUrl=" + intxServiceUrl + ", itxnServiceUrl=" + itxnServiceUrl
                + ", threadCount=" + threadCount + "]";
    }

}
