package com.conduent.tpms.ictx.service;

/**
 * ICTX Process Service Interface to start the process
 * 
 * @author deepeshb
 *
 */
public interface IctxProcessService {

	/**
	 * To start the process of the service
	 */
	void process(Long awayAgencyId, String fileType) throws Exception;

	/**
	 * To start the processing of transaction messsage
	 * 
	 * @throws Exception
	 */
	void processHubFile(String fileType) throws Exception;

	
}
