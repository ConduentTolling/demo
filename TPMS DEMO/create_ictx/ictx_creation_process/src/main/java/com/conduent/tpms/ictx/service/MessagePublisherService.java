package com.conduent.tpms.ictx.service;

import java.util.List;

import com.conduent.tpms.ictx.dto.AtpMessageDto;

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
