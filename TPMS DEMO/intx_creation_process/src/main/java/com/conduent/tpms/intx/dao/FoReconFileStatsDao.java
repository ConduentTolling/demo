package com.conduent.tpms.intx.dao;

import com.conduent.tpms.intx.model.FoReconFileStats;

public interface FoReconFileStatsDao {

	public void insertInFoReconFileStats(FoReconFileStats FoReconFileStats, String header);

	public void updateFoReconFileStats(FoReconFileStats FoReconFileStats);

	public FoReconFileStats getFoReconFileStats(FoReconFileStats FoReconFileStats);
	
	public FoReconFileStats getAtpFileId(FoReconFileStats FoReconFileStats);
	
	public void updateRecordCount(String fileName, Long recordCount);

}
