package com.conduent.tpms.qatp.classification.service;

import com.conduent.tpms.qatp.classification.dto.TransactionDto;

/**
 * QatpClassificationService Interface to start the process
 * 
 * @author deepeshb
 *
 */
public interface QatpClassificationService {

	/**
	 * To start the process of the tx
	 */
	public void processTxMsg(TransactionDto tempTxDto) ;
	
}
