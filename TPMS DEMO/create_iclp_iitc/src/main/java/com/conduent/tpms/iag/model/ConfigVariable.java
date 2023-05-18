package com.conduent.tpms.iag.model;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


/**
 * 
 * Config class for application-profiles
 * 
 * @author taniyan
 *
 */

@Component
public class ConfigVariable {

	@Value("${config.fileSrcPath}")
	private String fileSrcPath;
	
	public String getFileSrcPath() {
		return fileSrcPath;
	}

	public void setFileSrcPath(String fileSrcPath) {
		this.fileSrcPath = fileSrcPath;
	}

	@PostConstruct
	public void init() {

	}
}
