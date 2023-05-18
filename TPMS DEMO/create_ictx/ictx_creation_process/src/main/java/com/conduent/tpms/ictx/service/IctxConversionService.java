package com.conduent.tpms.ictx.service;

import com.conduent.tpms.ictx.dto.MessageConversionDto;
import com.conduent.tpms.ictx.model.AwayTransaction;

/**
 * ICTX message conversion service
 * 
 * @author deepeshb
 *
 */
public interface IctxConversionService {

	/**
	 * Get Ictx message info
	 * 
	 * @param awayTransaction
	 * @return MessageConversionDto
	 */
	public MessageConversionDto getIctxTransaction(AwayTransaction awayTransaction,String fileType);

}
