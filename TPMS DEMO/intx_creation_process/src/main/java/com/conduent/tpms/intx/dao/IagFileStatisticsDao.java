package com.conduent.tpms.intx.dao;

import com.conduent.tpms.intx.model.IagFileStatistics;

/**
 * Iag File Statistics Dao
 * 
 * @author deepeshb
 *
 */
public interface IagFileStatisticsDao {

	/**
	 * Insert Iag File Statistics
	 * 
	 * @param iagFileStatistics
	 */
	void insertIagFileStatistics(IagFileStatistics iagFileStatistics,String header);

	/**
	 * Update Iag File Statistics
	 * 
	 * @param iagFileStatistics
	 */
	void updateIagFileStatistics(IagFileStatistics iagFileStatistics);

	/**
	 * Get Iag File Statistics
	 * 
	 * @param iagFileStatistics
	 */
	IagFileStatistics getIagFileStatistics(IagFileStatistics iagFileStatistics);
	
	/**
	 * Get ATP File Id 
	 * 
	 * @param iagFileStatistics
	 */
	IagFileStatistics getAtpFileId(IagFileStatistics iagFileStatistics);
	
	public void updateRecordCount(String fileName,Long recordCount);

}
