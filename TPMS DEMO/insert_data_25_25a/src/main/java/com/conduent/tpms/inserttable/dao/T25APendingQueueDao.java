package com.conduent.tpms.inserttable.dao;

import java.text.ParseException;

import com.conduent.tpms.inserttable.model.T25APendingQueueRequestModel;


public interface T25APendingQueueDao {
	
	public Integer insertPendingDaoDetails(T25APendingQueueRequestModel t25APendingQueueRequest) throws ParseException;

}
