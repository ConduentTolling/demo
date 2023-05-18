package com.conduent.tpms.qatp.classification.service;

import com.conduent.tpms.qatp.classification.dto.AccountInfoDto;
import com.conduent.tpms.qatp.classification.dto.TransactionDetailDto;
import com.conduent.tpms.qatp.classification.dto.TransactionDto;

/**
 * Transaction Classification Service Interface
 * 
 * @author deepeshb
 *
 */
public interface TransactionClassificationService {

	/**
	 * Classify Transaction
	 * 
	 * @param TransactionDto
	 * @param AccountInfoDto
	 * @return TransactionDetailDto
	 */
	public TransactionDetailDto processTransactionClassification(TransactionDto tempTxDto, AccountInfoDto tempAccountInfoDto);
}
