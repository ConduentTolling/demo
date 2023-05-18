package com.conduent.tpms.iag.dao;

import com.conduent.tpms.iag.model.IagFileStatistics;
import com.conduent.tpms.iag.model.XferControl;

public interface TQatpStatisticsDao {
//	IaStatsDao

	public long insertIntoQATPStatistics(XferControl xferControl);
	
	public long updateIntoQATPStatistics(XferControl xferControl, long postCount, long rejctCount, long recCount,
			Double postTollAmt, Double rejctCount2);

	void insertIagFileStats(IagFileStatistics iagFileStatistics);
	
}
