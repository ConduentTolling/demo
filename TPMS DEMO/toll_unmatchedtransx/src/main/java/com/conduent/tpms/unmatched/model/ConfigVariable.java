package com.conduent.tpms.unmatched.model;

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

	@Value("${config.streamid}")
	private String streamId;

	@Value("${config.groupname}")
	private String groupName;

	@Value("${config.messagelimit}")
	private String messageLimit;
	
	@Value("${config.accountApiUri}")
	private String accountApiUri;

	@Value("${config.home.streamid}")
	private String homeAgencyStreamId;

	@Value("${config.away.streamid}")
	private String awayAgencyStreamId;

	@Value("${config.violation.streamid}")
	private String violationStreamId;
	
	@Value("${config.25a.api.url}")
	private String urlPath25a;
	
	@Value("${config.ny12.api.url}")
	private String urlPathNY12;
	
	@Value("${config.update.voiltx.api.url}")
	private String urlPathUpdateVoilTx;
	
	@Value("${config.maxTollUri}")
	private String maxTollUri;
	
	@Value("${config.tollCalculationUri}")
	private String tollCalculationUri;
	
	
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
	
	

	public String getAccountApiUri() {
		return accountApiUri;
	}

	public void setAccountApiUri(String accountApiUri) {
		this.accountApiUri = accountApiUri;
	}


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
	public String getUrlPath25a() {
		return urlPath25a;
	}

	public void setUrlPath25a(String urlPath25a) {
		this.urlPath25a = urlPath25a;
	}
	public String getUrlPathNY12() {
		return urlPathNY12;
	}

	public void setUrlPathNY12(String urlPathNY12) {
		this.urlPathNY12 = urlPathNY12;
	}

	
	public String getUrlPathUpdateVoilTx() {
		return urlPathUpdateVoilTx;
	}

	public void setUrlPathUpdateVoilTx(String urlPathUpdateVoilTx) {
		this.urlPathUpdateVoilTx = urlPathUpdateVoilTx;
	}
	

	public String getMaxTollUri() {
		return maxTollUri;
	}

	public void setMaxTollUri(String maxTollUri) {
		this.maxTollUri = maxTollUri;
	}

	
	public String getTollCalculationUri() {
		return tollCalculationUri;
	}

	public void setTollCalculationUri(String tollCalculationUri) {
		this.tollCalculationUri = tollCalculationUri;
	}

	@PostConstruct
	public void init() {
	
	}

}
