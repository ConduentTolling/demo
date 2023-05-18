package com.conduent.tpms.iag.service;

public interface FTAGIncrementService {

	/**
	 * 
	 * @param agencyId
	 * @param fileFormat
	 * @param genType
	 */
	boolean createFtagFile(int agencyId, String fileFormat, String genType);

	boolean processFileCreation(int agencyId, String fileFormat, String genType);

}
