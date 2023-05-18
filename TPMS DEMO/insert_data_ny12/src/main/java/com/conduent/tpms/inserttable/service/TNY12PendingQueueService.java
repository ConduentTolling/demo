package com.conduent.tpms.inserttable.service;

import java.text.ParseException;

import com.conduent.tpms.inserttable.model.TNY12PendingQueueRequestModel;

public interface TNY12PendingQueueService {

	 public Integer insertPendingDetails(TNY12PendingQueueRequestModel tNY12PendingQueueRequest) throws ParseException;

}
