package com.conduent.tpms.ictx.service;

import com.conduent.tpms.ictx.dto.FileOperationStatus;

/**
 * Ictx Statistics Service
 * 
 * @author deepeshb
 *
 */
public interface IctxStatisticsService {

	/**
	 * Update Ictx Statistics
	 * 
	 * @param fileOperationStatus
	 * @throws Exception
	 */
	void updateIagStatistics(FileOperationStatus fileOperationStatus,String header) throws Exception;
}
