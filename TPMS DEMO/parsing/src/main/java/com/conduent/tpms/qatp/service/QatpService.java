package com.conduent.tpms.qatp.service;

public interface QatpService {

	public void parallelProcess(String fileName) throws Exception ;

	public void process(String fileType) throws Exception;
	
}
