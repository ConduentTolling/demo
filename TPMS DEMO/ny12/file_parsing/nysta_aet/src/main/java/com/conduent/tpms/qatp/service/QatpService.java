package com.conduent.tpms.qatp.service;

import java.io.File;

public interface QatpService {

	public void process() throws Exception;
	
	public void parallelProcess(String fileName) throws Exception ;
	
}
