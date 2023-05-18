package com.conduent.tpms.iag.service;

public interface ITAGCreationService {

	/**
	 * 
	 * @param agencyId
	 * @param fileFormat
	 * @param genType
	 */
	void createItagFile(int agencyId, String fileFormat, String genType);

	void processFileCreation(int agencyId, String fileFormat, String genType);

}
