package com.conduent.tpms.intx.service;

import com.conduent.tpms.intx.dto.FileOperationStatus;

public interface IntxStatisticsService {

	/**
	 * Update Ictx Statistics
	 * 
	 * @param fileOperationStatus
	 * @throws Exception
	 */
	void updateIagStatistics(FileOperationStatus fileOperationStatus, String header) throws Exception;
	
}