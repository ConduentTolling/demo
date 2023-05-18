package com.conduent.tpms.inrx.service;

/**
 * INRX Scheduler Service Interface to start the process
 * 
 * @author petetid
 *
 */
public interface InrxSchedulerService {
	
	/**
	 * To start the process of the service
	 */
	void process(String fileType);

}
