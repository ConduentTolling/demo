package com.conduent.tpms.qatp.model;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


/**
 * Config class for application-profiles
 * 
 * @author Urvashi C
 *
 */

@Component
public class ConfigVariable {

	@Value("${config.accountApiUri}")
	private String accountApiUri;

	public String getAccountApiUri() {
		return accountApiUri;
	}

	public void setAccountApiUri(String accountApiUri) {
		this.accountApiUri = accountApiUri;
	}

	@PostConstruct
	public void init() {

	}
}
