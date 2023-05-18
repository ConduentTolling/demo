package com.conduent.tpms.inserttable.service;

import java.text.ParseException;

import com.conduent.tpms.inserttable.model.T25APendingQueueRequestModel;

public interface T25aPendingQueueService {

	 public Integer insertPendingDetails(T25APendingQueueRequestModel t25APendingQueueRequest) throws ParseException;

}
