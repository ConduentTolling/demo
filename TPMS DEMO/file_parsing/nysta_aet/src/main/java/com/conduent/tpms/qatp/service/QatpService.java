package com.conduent.tpms.qatp.service;

import java.io.File;

public interface QatpService {

	public void process(Integer agencyId) throws Exception;

	void parallelProcess(String fileName,Integer agencyId) throws Exception;
}
