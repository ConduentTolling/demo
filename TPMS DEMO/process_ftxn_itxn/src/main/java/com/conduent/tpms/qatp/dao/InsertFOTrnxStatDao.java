package com.conduent.tpms.qatp.dao;

import com.conduent.tpms.qatp.dto.FOTrnxHeaderInfoVO;
import com.conduent.tpms.qatp.exception.InvalidFileHeaderException;
import com.conduent.tpms.qatp.model.ReconciliationCheckPoint;
import com.conduent.tpms.qatp.model.XferControl;
import com.conduent.tpms.qatp.utility.GenericValidation;

public interface InsertFOTrnxStatDao {
	public XferControl getXferDataForStatistics(String fileName) throws Exception ;

	public int updateStatisticsTable(long xferControlId, String fileName);	
	
	public int updateFOReconFileStats(long xferControlId, String fileName, ReconciliationCheckPoint reconciliationCheckPoint);

	public int updateFOFileStats(long xferControlId, String fileName, ReconciliationCheckPoint reconciliationCheckPoint,
			GenericValidation genericValidation) throws InvalidFileHeaderException;

	public int updateQatpStatisticsTable(long xferControlId, String fileName);

	public int updateReconStatisticsTable(long xferControlId, String fileName);	
}
