package com.conduent.tpms.qatp.model;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ConfigVariable {

	@Value("${spring.ds-oracle.url}")
	private String oracleUrl;

	@Value("${spring.ds-oracle.username}")
	private String oracleUsername;

	@Value("${spring.ds-oracle.password}")
	private String oraclePassword;

	@Value("${spring.ds-h2.url}")
	private String h2Url;

	@Value("${spring.ds-h2.username}")
	private String h2Username;

	@Value("${spring.ds-h2.password}")
	private String h2Password;

	public String getOracleUrl() {
		return oracleUrl;
	}

	public void setOracleUrl(String oracleUrl) {
		this.oracleUrl = oracleUrl;
	}

	public String getOracleUsername() {
		return oracleUsername;
	}

	public void setOracleUsername(String oracleUsername) {
		this.oracleUsername = oracleUsername;
	}

	public String getOraclePassword() {
		return oraclePassword;
	}

	public void setOraclePassword(String oraclePassword) {
		this.oraclePassword = oraclePassword;
	}

	public String getH2Url() {
		return h2Url;
	}

	public void setH2Url(String h2Url) {
		this.h2Url = h2Url;
	}

	public String getH2Username() {
		return h2Username;
	}

	public void setH2Username(String h2Username) {
		this.h2Username = h2Username;
	}

	public String getH2Password() {
		return h2Password;
	}

	public void setH2Password(String h2Password) {
		this.h2Password = h2Password;
	}

	@PostConstruct
	public void init() {

	}
}
