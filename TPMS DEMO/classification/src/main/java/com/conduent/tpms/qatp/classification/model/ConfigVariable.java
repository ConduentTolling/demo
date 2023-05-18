package com.conduent.tpms.qatp.classification.model;

import javax.annotation.PostConstruct;

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

	@Value("${config.groupname}")
	private String groupName;

	@Value("${config.messagelimit}")
	private String messageLimit;
	
	@Value("${config.parkingposting.streamid}")
	private String parkingStreamId;
	
	/////// unused //////
	@Value("${config.home.streamid}")
	private String homeAgencyStreamId;

	@Value("${config.away.streamid}")
	private String awayAgencyStreamId;

	@Value("${config.violation.streamid}")
	private String violationStreamId;
	
	
	
	
	public String getHomeAgencyStreamId() {
		return homeAgencyStreamId;
	}

	public void setHomeAgencyStreamId(String homeAgencyStreamId) {
		this.homeAgencyStreamId = homeAgencyStreamId;
	}

	public String getAwayAgencyStreamId() {
		return awayAgencyStreamId;
	}

	public void setAwayAgencyStreamId(String awayAgencyStreamId) {
		this.awayAgencyStreamId = awayAgencyStreamId;
	}

	public String getViolationStreamId() {
		return violationStreamId;
	}

	public void setViolationStreamId(String violationStreamId) {
		this.violationStreamId = violationStreamId;
	}

	public String getParkingStreamId() {
		return parkingStreamId;
	}

	public void setParkingStreamId(String parkingStreamId) {
		this.parkingStreamId = parkingStreamId;
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

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getMessageLimit() {
		return messageLimit;
	}

	public void setMessageLimit(String messageLimit) {
		this.messageLimit = messageLimit;
	}
	
	@PostConstruct
	public void init() {
	
	}

}
