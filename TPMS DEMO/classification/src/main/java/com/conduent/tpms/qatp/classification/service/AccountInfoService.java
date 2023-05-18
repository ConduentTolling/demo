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
}
