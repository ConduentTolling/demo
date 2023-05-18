package com.conduent.tpms.qatp.classification.service;

import com.conduent.tpms.qatp.classification.dto.CommonClassificationDto;
import com.conduent.tpms.qatp.classification.dto.TUnmatchedTx;
import com.conduent.tpms.qatp.classification.dto.TransactionDto;

/**
 * Common Classification Service Interface
 * 
 * @author Sameer
 *
 */
public interface CommonClassificationService {

	/**
	 *Insert message to Home etc Queue table
	 */
	public void insertToHomeEtcQueue(CommonClassificationDto object);

	

	/**
	 *Insert message to Viol Queue table
	 */
	public void insertToViolQueue(CommonClassificationDto object);
	
	
	/**
	 *Push Message to Oracle Streaming Service
	 */	
	public void pushMessage(CommonClassificationDto object, String streamId);

	/**
	 *Insert data into T_UNMATCHED_ENTRY_TX table
	 */
	public void insertToTUnmatchedEntryTx(TUnmatchedTx unMatchedEntry);
	
	/**
	 *Insert data into T_UNMATCHED_EXIT_TX table
	 */
	public void insertToTUnmatchedExitTx(TUnmatchedTx unMatchedEntry);

}
