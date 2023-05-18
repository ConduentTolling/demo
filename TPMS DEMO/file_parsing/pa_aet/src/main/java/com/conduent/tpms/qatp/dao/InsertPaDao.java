package com.conduent.tpms.qatp.dao;

import com.conduent.tpms.qatp.model.XferControl;

public interface InsertPaDao {

	public XferControl getXferDataForStatistics(String fileName) throws Exception;

	public int insertPaFileInQATPStats(XferControl xferControl);

	public int updateStatisticsTable(long xferControlId, String fileName);
}
