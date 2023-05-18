package com.conduent.tpms.icrx.service;

/**
 * ICRX Process Service Interface to start the process
 * 
 * @author urvashic
 *
 */
public interface IcrxProcessService {

	/**
	 * To start the process of the service
	 * @param fileType 
	 */
	void process(String fileType, Long awayAgencyId) throws Exception;

}
