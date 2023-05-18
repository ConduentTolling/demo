package com.conduent.tpms.intx.service;

import com.conduent.tpms.intx.dto.MessageConversionDto;
import com.conduent.tpms.intx.model.AwayTransaction;

/**
 * INTX message conversion service
 * 
 * @author deepeshb
 *
 */
public interface IntxConversionService {

	/**
	 * Get Ictx message info
	 * 
	 * @param awayTransaction
	 * @return MessageConversionDto
	 */
	public MessageConversionDto getIntxTransaction(AwayTransaction awayTransaction,String fileType);

}
