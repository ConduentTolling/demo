package com.conduent.tpms.unmatched.service;

import java.text.ParseException;

/**
 * Common Classification Service Interface
 * 
 * @author Sameer
 *
 */
public interface DeviceMatchingService {

	
	public void getUnmatchedTrxBasedOnDeviceNo() throws ParseException;

	//public void getUnmatchedTrxBasedOnPlateNo() throws ParseException;

	public void getExpiredExitTransactions();

	public void getExpiredEntryTransactions();

	public void getExpiredViolTxTransactions();
	
	
}
