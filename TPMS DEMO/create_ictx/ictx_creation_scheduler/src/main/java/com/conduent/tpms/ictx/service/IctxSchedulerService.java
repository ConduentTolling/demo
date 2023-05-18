package com.conduent.tpms.ictx.service;

/**
 * ICTX Scheduler Service Interface to start the process
 * 
 * @author deepeshb
 *
 */
public interface IctxSchedulerService {

	/**
	 * To start the process of the service
	 * @param fileType 
	 */
	void process(String fileType);

	/**
	 * To start the processing of transaction message
	 */
	void processHubFile(String fileType);
	
}
