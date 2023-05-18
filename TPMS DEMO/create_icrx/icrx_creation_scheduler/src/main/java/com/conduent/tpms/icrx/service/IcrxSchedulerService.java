package com.conduent.tpms.icrx.service;

/**
 * ICRX Scheduler Service Interface to start the process
 * 
 * @author deepeshb
 *
 */
public interface IcrxSchedulerService {
	
	/**
	 * To start the process of the service
	 */
	void process(String fileType);

}
