package com.conduent.tpms.qatp.classification.service;

import java.io.IOException;
import java.util.List;

import com.conduent.tpms.qatp.classification.dto.TransactionDto;

/**
 * Read Transaction Message from Oracle Streaming Service
 * 
 * @author deepeshb
 *
 */
public interface MessageReaderService {

	/**
	 * Read Transaction Messages from Oracle Streaming Service
	 * 
	 * @return List<TransactionDto>
	 * @throws IOException
	 */
	public List<TransactionDto> readMessages() throws IOException;

}
