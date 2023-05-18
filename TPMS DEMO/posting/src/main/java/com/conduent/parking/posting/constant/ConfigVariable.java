package com.conduent.parking.posting.constant;

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
	
	@Value("${config.parkingQueue}")
	private String parkingQueue;
	
	@Value("${config.failureQueue}")
	private String failureQueue;
	
	@Value("${config.groupName}")
	private String groupName;
	
	public String getProfile() {
		return profile;
	}

	public void setProfile(String profile) {
		this.profile = profile;
	}

	public String getConfigFilePath() {
		return configFilePath;
	}

	public void setConfigFilePath(String configFilePath) {
		this.configFilePath = configFilePath;
	}

	public String getParkingQueue() 
	{ 
		return parkingQueue; 
	}

	public void setParkingQueue(String parkingQueue) 
	{ 
		this.parkingQueue = parkingQueue; 
	}

	public String getTollPostingUri() 
	{
		return tollPostingUri;
	}

	public void setTollPostingUri(String tollPostingUri) 
	{
		this.tollPostingUri = tollPostingUri;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getFailureQueue() {
		return failureQueue;
	}

	public void setFailureQueue(String failureQueue) {
		this.failureQueue = failureQueue;
	}
	
}