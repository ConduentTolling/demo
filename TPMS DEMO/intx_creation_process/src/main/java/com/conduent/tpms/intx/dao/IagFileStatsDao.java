package com.conduent.tpms.intx.dao;

import com.conduent.tpms.intx.model.IagFileStatistics;

/**
 * Iag File Statistics Dao
 * 
 * @author deepeshb
 *
 */
public interface IagFileStatsDao {

	/**
	 * Insert Iag File Statistics
	 * 
	 * @param iagFileStatistics
	 */
	void insertIagFileStats(IagFileStatistics iagFileStats,String header);
	public void updateIagFileStats(IagFileStatistics iagFileStats);
	public void updateRecordCount(String fileName,Long recordCount);

	

}
