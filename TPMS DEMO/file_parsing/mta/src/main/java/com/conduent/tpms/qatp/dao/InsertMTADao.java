package com.conduent.tpms.qatp.dao;

import com.conduent.tpms.qatp.model.XferControl;

public interface InsertMTADao {

	public XferControl getXferDataForStatistics(String fileName) throws Exception;

	public int insertMtaFileInQATPStats(XferControl xferControl);

	public int updateStatisticsTable(long xferControlId, String fileName);	
}
