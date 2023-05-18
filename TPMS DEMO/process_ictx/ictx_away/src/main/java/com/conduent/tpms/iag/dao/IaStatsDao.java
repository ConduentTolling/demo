package com.conduent.tpms.iag.dao;

import java.util.List;

import com.conduent.tpms.iag.model.FileDetails;
import com.conduent.tpms.iag.model.IagFileStatistics;
import com.conduent.tpms.iag.model.TranDetail;
import com.conduent.tpms.iag.model.XferControl;

public interface IaStatsDao {


	void updateIaFileStats(List<TranDetail> tranDetailList, XferControl xferControl) ;
	
}
