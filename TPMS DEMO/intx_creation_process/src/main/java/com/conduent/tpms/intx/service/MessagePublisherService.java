package com.conduent.tpms.intx.service;

import java.util.List;

import com.conduent.tpms.intx.dto.AtpMessageDto;

/**
 * Message publishing service
 * 
 * @author deepeshb
 *
 */
public interface MessagePublisherService {

	/**
	 * Publish message to stream
	 * 
	 * @param tempAtpMessageDto
	 * @return long
	 */
	public long publishMessageToStream(List<AtpMessageDto> tempAtpMessageDto);
	
}
