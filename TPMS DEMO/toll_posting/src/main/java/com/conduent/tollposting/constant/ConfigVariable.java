package com.conduent.tollposting.constant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ConfigVariable 
{
	@Value("${spring.profiles.active}")
	private String profile;

	@Value("${config.configfilepath}")
	private String configFilePath;

	@Value("${config.tollPostingUri}")
	private String tollPostingUri;
	
	@Value("${config.atpQueue}")
	private String atpQueue;
	
	@Value("${config.ibtsQueue}")
	private String ibtsQueue;
	
	@Value("${config.failureQueue}")
	private String failureQueue;
	
	@Value("${config.groupName}")
	private String groupName;
	
	@Value("${config.maxTollUri}")
	private String maxTollUri;
	
	@Value("${config.iagCorrUri}")
	private String iagCorrUri;
	
	@Value("${config.todId}")
	private String todId;
	
	@Value("${config.sleepTime}")
	private long sleepTime;
	
	@Value("${config.tollCalculation}")
	private String tollCalculationUri;
	
	@Value("${config.messageQueueSize}")
	private int messageQueueSize;

	public String getProfile() {
		return profile;
	}

	public String getConfigFilePath() {
		return configFilePath;
	}

	public String getTollPostingUri() {
		return tollPostingUri;
	}

	public String getAtpQueue() {
		return atpQueue;
	}

	public String getIbtsQueue() {
		return ibtsQueue;
	}

	public String getFailureQueue() {
		return failureQueue;
	}

	public String getGroupName() {
		return groupName;
	}

	public String getMaxTollUri() {
		return maxTollUri;
	}

	public String getIagCorrUri() {
		return iagCorrUri;
	}

	public String getTodId() {
		return todId;
	}

	public long getSleepTime() {
		return sleepTime;
	}

	public String getTollCalculationUri() {
		return tollCalculationUri;
	}

	public int getMessageQueueSize() {
		return messageQueueSize;
	}


	
}