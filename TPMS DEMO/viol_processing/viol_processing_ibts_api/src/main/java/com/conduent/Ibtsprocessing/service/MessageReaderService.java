package com.conduent.Ibtsprocessing.service;

import java.io.IOException;
import java.util.List;

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
	public List<String> readMessages(String streamId, Integer messageLimit) throws IOException;

}
