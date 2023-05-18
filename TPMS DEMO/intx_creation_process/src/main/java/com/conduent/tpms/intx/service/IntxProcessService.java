package com.conduent.tpms.intx.service;

public interface IntxProcessService {
	/**
	 * To start the process of the service
	 */
	void process(String externalFileId, Long awayAgencyId, String fileType) throws Exception;
	
}
