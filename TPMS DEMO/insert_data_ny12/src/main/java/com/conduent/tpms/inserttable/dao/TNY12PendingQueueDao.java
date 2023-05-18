package com.conduent.tpms.inserttable.dao;

import java.text.ParseException;

import com.conduent.tpms.inserttable.model.TNY12PendingQueueRequestModel;


public interface TNY12PendingQueueDao {
	
	public Integer insertPendingDaoDetails(TNY12PendingQueueRequestModel tNY12PendingQueueRequest) throws ParseException;

}
