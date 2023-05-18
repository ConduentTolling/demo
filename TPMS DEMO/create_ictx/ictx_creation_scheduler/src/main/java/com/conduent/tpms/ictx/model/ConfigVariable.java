package com.conduent.tpms.ictx.model;

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

	@Value("${config.ictxserviceurl}")
	private String ictxServiceUrl;
	
	@Value("${config.itxcserviceurl}")
	private String itxcserviceurl;

	public String getIctxServiceUrl() {
		return ictxServiceUrl;
	}

	public void setIctxServiceUrl(String ictxServiceUrl) {
		this.ictxServiceUrl = ictxServiceUrl;
	}
	
	

	public String getItxcServiceUrl() {
		return itxcserviceurl;
	}

	public void setItxcServiceUrl(String itxcserviceurl) {
		this.itxcserviceurl = itxcserviceurl;
	}

	@Override
	public String toString() {
		return "ConfigVariable [ictxServiceUrl=" + ictxServiceUrl + ", itxcserviceurl=" + itxcserviceurl + "]";
	}

}
