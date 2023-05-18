package com.conduent.tpms.inserttable.serviceImpl;

import java.text.ParseException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Service;

import com.conduent.tpms.inserttable.dao.TNY12PendingQueueDao;
import com.conduent.tpms.inserttable.model.TNY12PendingQueueRequestModel;
import com.conduent.tpms.inserttable.service.TNY12PendingQueueService;

@Service
public class TNY12PendingQueueServiceImpl implements TNY12PendingQueueService {

	@Autowired
	TNY12PendingQueueDao tNY12PendingQueueDao;
	


	final Logger logger = LoggerFactory.getLogger(TNY12PendingQueueServiceImpl.class);
	MapSqlParameterSource paramSource = new MapSqlParameterSource();

	@Override
	public Integer insertPendingDetails(TNY12PendingQueueRequestModel tNY12PendingQueueRequest)
			throws ParseException {

		return tNY12PendingQueueDao.insertPendingDaoDetails(tNY12PendingQueueRequest);

	}
}