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

	@Value("${config.fpmsApiUri}")
	private String fpmsApiUri;
	
	@Value("${config.fpmsApiUridistplan}")
	private String fpmsApiUridistplan;
	
	@Value("${config.processparameterY}")
	private String processparameterY;
	
	@Value("${config.processparameterN}")
	private String processparameterN;
	

	@Value("${config.rptDest}")
	private String rptFile;
	
	
	public String getRptFile() {
		return rptFile;
	}

	public void setRptFile(String rptFile) {
		this.rptFile = rptFile;
	}


	public String getProcessparameterY() {
		return processparameterY;
	}

	public void setProcessparameterY(String processparameterY) {
		this.processparameterY = processparameterY;
	}

	public String getProcessparameterN() {
		return processparameterN;
	}

	public void setProcessparameterN(String processparameterN) {
		this.processparameterN = processparameterN;
	}

	public String getFpmsApiUridistplan() {
		return fpmsApiUridistplan;
	}

	public void setFpmsApiUridistplan(String fpmsApiUridistplan) {
		this.fpmsApiUridistplan = fpmsApiUridistplan;
	}

	public String getFpmsApiUri() {
		return fpmsApiUri;
	}

	public void setFpmsApiUri(String fpmsApiUri) {
		this.fpmsApiUri = fpmsApiUri;
	}


	@PostConstruct
	public void init() {

	}

}
