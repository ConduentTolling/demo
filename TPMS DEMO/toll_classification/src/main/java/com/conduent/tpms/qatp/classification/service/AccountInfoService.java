package com.conduent.tpms.qatp.classification.service;

import com.conduent.tpms.qatp.classification.dto.AccountInfoDto;
import com.conduent.tpms.qatp.classification.dto.TransactionDetailDto;
import com.conduent.tpms.qatp.classification.dto.TransactionDto;

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
	
	public TransactionDetailDto checkHomeTag(TransactionDto tempTxDto,
			AccountInfoDto tempAccountInfoDto);


	public TransactionDetailDto checkAwayMTATag(TransactionDto tempTxDto, AccountInfoDto tempAccountInfoDto);
}
