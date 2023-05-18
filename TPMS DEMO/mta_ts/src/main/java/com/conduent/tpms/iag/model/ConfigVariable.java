package com.conduent.tpms.iag.model;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


/**
 * 
 * Config class for application-profiles
 * 
 * @author urvashic	
 *
 */

@Component
public class ConfigVariable {

	@Value("${config.itagFileDest}")
	private String itagFileDest;
	
	
	public String getItagFileDest() {
		return itagFileDest;
	}

	public void setItagFileDest(String itagFileDest) {
		this.itagFileDest = itagFileDest;
	}


	@PostConstruct
	public void init() {

	}
}
