
package com.conduent.tpms.qatp.classification.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.conduent.tpms.qatp.classification.dto.TransactionDetailDto;
import com.conduent.tpms.qatp.classification.service.ComputeTollService;

@Service
public class PAClassificationServiceImpl extends QatpClassificationServiceImpl {

	private static final Logger logger = LoggerFactory.getLogger(PAClassificationServiceImpl.class);

	@Autowired
	ComputeTollService computeTollService;

	/**
	 * Compute Toll of the Transaction
	 * 
	 * @param TransactionDetailDto
	 * @return TransactionDetailDto
	 */
	
@Override
	protected TransactionDetailDto computeToll(TransactionDetailDto transactionDetailDto) {
		logger.info("Enter PA compute logic");
		if (transactionDetailDto == null || transactionDetailDto.getTransactionDto() == null
				|| transactionDetailDto.getAccountInfoDto() == null) {
			return null;
		}
		return computeTollService.computePAToll(transactionDetailDto);

	}

}
