package com.conduent.tpms.unmatched.service;

import com.conduent.tpms.unmatched.dto.AccountInfoDto;
import com.conduent.tpms.unmatched.dto.TransactionDetailDto;
import com.conduent.tpms.unmatched.dto.TransactionDto;

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
