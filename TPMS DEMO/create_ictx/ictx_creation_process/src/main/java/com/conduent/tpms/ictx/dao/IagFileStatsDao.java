package com.conduent.tpms.ictx.dao;

import com.conduent.tpms.ictx.model.IagFileStatistics;
import com.conduent.tpms.ictx.model.IagFileStats;

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
