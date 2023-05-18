package com.conduent.tpms.qatp.classification.service;

import com.conduent.tpms.qatp.classification.dto.TransactionDto;


/**
 * Common Notification Service Interface
 * 
 * @author Sameer
 *
 */
public interface CommonNotificationService {

	/**
	 *Push Message to Home Stream or Away Stream or call to IBTS API Based on Tx Type 
	 */
	public void pushMessage(TransactionDto txDTO);

}
