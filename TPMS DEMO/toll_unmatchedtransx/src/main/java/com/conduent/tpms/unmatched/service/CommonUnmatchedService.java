package com.conduent.tpms.unmatched.service;

import com.conduent.tpms.unmatched.dto.CommonUnmatchedDto;

/**
 * Common Classification Service Interface
 * 
 * @author Sameer
 *
 */
public interface CommonUnmatchedService {

	/**
	 *Insert message to Home etc Queue table
	 */
	public void insertToHomeEtcQueue(CommonUnmatchedDto object);

	

	/**
	 *Insert message to Viol Queue table
	 */
	public void insertToViolQueue(CommonUnmatchedDto object);
	
	
	/**
	 *Push Message to Oracle Streaming Service
	 */	
	public void pushMessage(CommonUnmatchedDto object, String streamId);
	
	
}
