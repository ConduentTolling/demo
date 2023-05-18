package com.conduent.Ibtsprocessing.service;

import com.conduent.Ibtsprocessing.dto.IBTSViolDTO;
import com.conduent.Ibtsprocessing.dto.IbtsViolProcessDTO;

public interface IMessageProcessingJob {

	public void insert(IbtsViolProcessDTO object);

	/**
	 * Push Message to Oracle Streaming Service
	 */
	public void pushMessage(IbtsViolProcessDTO object, String streamId);

	/**
	 * Send Message to IBTS
	 * @return 
	 */
	public boolean sendToIBTS(IBTSViolDTO object);
}
