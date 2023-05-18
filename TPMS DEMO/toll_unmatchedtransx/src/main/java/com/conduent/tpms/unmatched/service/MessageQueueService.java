package com.conduent.tpms.unmatched.service;

import com.conduent.tpms.unmatched.dto.TransactionDetailDto;

/**
 * QatpClassificationService Interface to start the process
 * 
 * @author deepeshb
 *
 */
public interface MessageQueueService {

	/**
	 * To start the process of the tx
	 */
	public void pushMessage(TransactionDetailDto tempTxDto) ;

	
}
