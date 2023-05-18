package com.conduent.Ibtsprocessing.constant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ConfigVariable 
{

	@Value("${spring.profiles.active}")
	private String profile;
	
	@Value("${config.configfilepath}")
	private String configfilepath;
	
	@Value("${config.atpQueue}")
	private String atpQueue;
	
	@Value("${config.groupname}")
	private String groupName;
	
	@Value("${config.streamid}")
	private String streamId;
	
	
	@Value("${config.messagelimit}")
	private String messageLimit;
	
	@Value("${config.away.streamid}")
	private String awayAgencyStreamId;
	
	public String getExclusionUrl() {
		return exclusionUrl;
	}

	public void setExclusionUrl(String exclusionUrl) {
		this.exclusionUrl = exclusionUrl;
	}

	@Value("${config.exclusion.url}")
	private String exclusionUrl;
	
	public String getAwayAgencyStreamId() {
		return awayAgencyStreamId;
	}

	public void setAwayAgencyStreamId(String awayAgencyStreamId) {
		this.awayAgencyStreamId = awayAgencyStreamId;
	}

	public String getStreamId() {
		return streamId;
	}

	public void setStreamId(String streamId) {
		this.streamId = streamId;
	}

	

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getProfile() {
		return profile;
	}

	public void setProfile(String profile) {
		this.profile = profile;
	}

	

	

	public String getConfigfilepath() {
		return configfilepath;
	}

	public void setConfigfilepath(String configfilepath) {
		this.configfilepath = configfilepath;
	}

	public String getAtpQueue() {
		return atpQueue;
	}

	public void setAtpQueue(String atpQueue) {
		this.atpQueue = atpQueue;
	}

	public String getMessageLimit() {
		return messageLimit;
	}

	public void setMessageLimit(String messageLimit) {
		this.messageLimit = messageLimit;
	}
	
	
}