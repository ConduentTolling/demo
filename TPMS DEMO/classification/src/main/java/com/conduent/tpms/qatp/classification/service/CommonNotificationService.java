package com.conduent.tpms.qatp.classification.service;

import com.conduent.tpms.qatp.classification.dto.TransactionDto;
import com.conduent.tpms.qatp.classification.utility.MasterDataCache;


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

	public void pushMessagetoParkingQueue(TransactionDto transactionDto, MasterDataCache masterDataCache2);

}
