package com.conduent.tpms.intx.service;

import com.conduent.tpms.intx.dto.AtpMessageDto;
import com.conduent.tpms.intx.model.AwayTransaction;

public interface AtpMessageFormatService {

	/**
	 * Get ATP message
	 * 
	 * @param AwayTransaction
	 * @return AtpMessageDto
	 */
	public AtpMessageDto getAtpMessage(AwayTransaction txDTO,String fileType);

}
