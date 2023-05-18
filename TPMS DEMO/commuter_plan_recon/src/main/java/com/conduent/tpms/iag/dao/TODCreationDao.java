package com.conduent.tpms.iag.dao;

import java.util.List;

import com.conduent.tpms.iag.model.BatchUserInfo;
import com.conduent.tpms.iag.model.TourOfDuty;

public interface TODCreationDao 
{
	public BatchUserInfo batchUserDetail();
	
	public List<TourOfDuty> listOfFinancialsNotClosed(String userId, String status, Boolean financials);
	
	public List<TourOfDuty> listOfStatusClosebyFinancialsOpen(String userId, String status, Boolean financials);
	
	public void updateTOD(final TourOfDuty dto);

}
