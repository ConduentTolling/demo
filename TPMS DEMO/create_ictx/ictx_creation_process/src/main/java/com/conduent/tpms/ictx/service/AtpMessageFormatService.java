package com.conduent.tpms.ictx.service;

import com.conduent.tpms.ictx.dto.AtpMessageDto;
import com.conduent.tpms.ictx.model.AwayTransaction;

/**
 * ATP Message format service
 * 
 * @author deepeshb
 *
 */
public interface AtpMessageFormatService {

	/**
	 * Get ATP message
	 * 
	 * @param AwayTransaction
	 * @return AtpMessageDto
	 */
	public AtpMessageDto getAtpMessage(AwayTransaction txDTO,String fileType);
}
