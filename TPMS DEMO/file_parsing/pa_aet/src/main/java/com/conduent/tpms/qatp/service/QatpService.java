package com.conduent.tpms.qatp.service;

public interface QatpService {

	public void process();
	
	public void parallelProcess(String fileName) throws Exception ;
	
}
