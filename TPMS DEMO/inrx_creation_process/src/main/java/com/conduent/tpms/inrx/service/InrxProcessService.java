package com.conduent.tpms.inrx.service;

/**
 * INRX Process Service Interface to start the process
 * 
 * @author petetid
 *
 */
public interface InrxProcessService {

	/**
	 * To start the process of the service
	 * @param fileType 
	 */
	void process(String fileType, Long awayAgencyId) throws Exception;

}
