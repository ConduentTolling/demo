package com.conduent.tpms.qatp.classification.service;

import com.conduent.tpms.qatp.classification.dto.AccountInfoDto;
import com.conduent.tpms.qatp.classification.dto.TransactionDetailDto;
import com.conduent.tpms.qatp.classification.dto.TransactionDto;

/**
 * Speed Process Service Interface
 * 
 * @author deepeshb
 *
 */
public interface SpeedProcessingService {
	/**
	 * Process Transaction for Speed Business Rule
	 * 
	 * @param TransactionDto
	 * @param AccountInfoDto
	 * @return TransactionDetailDto
	 */
	public TransactionDetailDto processSpeedBusinessRule(TransactionDto tempTxDto, AccountInfoDto tempAccountInfoDto);
}
