package com.conduent.tpms.unmatched.service;

import com.conduent.tpms.unmatched.dto.AccountInfoDto;
import com.conduent.tpms.unmatched.dto.TransactionDetailDto;
import com.conduent.tpms.unmatched.dto.TransactionDto;

/**
 * Account Information Service
 * 
 * @author deepeshb
 *
 */
public interface AccountInfoService {

	/**
	 * Get Account Information
	 * 
	 * @param TransactionDto 
	 * @return TransactionDetailDto 
	 */
	public TransactionDetailDto getAccountInformation(TransactionDto tempTxDto);

	
	/**
	 * Companion tag Check
	 * 
	 * @param TransactionDto
	 * @param AccountInfoDto
	 * @return TransactionDetailDto
	 */
	public TransactionDetailDto checkCompanionTag(TransactionDto tempTxDto,
			AccountInfoDto tempAccountInfoDto);
}
