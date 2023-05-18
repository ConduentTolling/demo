package com.conduent.tpms.intx.dao;

import com.conduent.tpms.intx.model.FoReconFileStats;

public interface FoFileStatsDao {
	
	public void insertFoFileStats(FoReconFileStats foFileStats, String header);
	
	public void updateFoFileStats(FoReconFileStats foFileStats);
	
	public void updateRecordCount(String fileName, Long recordCount);
	
}
