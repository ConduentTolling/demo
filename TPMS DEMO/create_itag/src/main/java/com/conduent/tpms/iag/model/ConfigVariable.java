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

	@Value("${config.fileDestPath}")
	private String fileDestPath;
	
	@Value("${config.itagiclpFileDestPath}")
	private String itagiclpFileDestPath;
	
	@Value("${config.statFileDestPath}")
	private String statFileDestPath;

	public String getFileDestPath() {
		return fileDestPath;
	}

	public void setFileDestPath(String fileDestPath) {
		this.fileDestPath = fileDestPath;
	}
	
	public String getItagiclpFileDestPath() {
		return itagiclpFileDestPath;
	}

	public void setItagiclpFileDestPath(String itagiclpFileDestPath) {
		this.itagiclpFileDestPath = itagiclpFileDestPath;
	}

	public String getStatFileDestPath() {
		return statFileDestPath;
	}

	public void setStatFileDestPath(String statFileDestPath) {
		this.statFileDestPath = statFileDestPath;
	}

	@PostConstruct
	public void init() {

	}
}
