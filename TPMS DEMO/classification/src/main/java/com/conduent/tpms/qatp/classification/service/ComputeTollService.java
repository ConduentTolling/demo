package com.conduent.tpms.qatp.classification.service;

import com.conduent.tpms.qatp.classification.dto.AccountInfoDto;
import com.conduent.tpms.qatp.classification.dto.TransactionDetailDto;
import com.conduent.tpms.qatp.classification.dto.TransactionDto;

/**
 * Compute Toll Service Interface
 * 
 * @author Sameer
 *
 */
public interface ComputeTollService {

	/**
	 *Compute Toll for Transaction
	 */
	public TransactionDto computeToll(TransactionDto transactionObject, AccountInfoDto accountObject);
	
	/**
	 * Compute Toll for PA Agency Transaction
	 */
	public TransactionDetailDto computePAToll(TransactionDetailDto transactionDetailDto);

}
