package com.conduent.tpms.unmatched.service;

import com.conduent.tpms.unmatched.dto.TransactionDto;

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
	public abstract void pushMessage(TransactionDto txDTO);



}
