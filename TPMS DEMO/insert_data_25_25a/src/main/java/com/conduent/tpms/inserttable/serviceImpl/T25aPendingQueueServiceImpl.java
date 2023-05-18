package com.conduent.tpms.inserttable.serviceImpl;

import java.text.ParseException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Service;

import com.conduent.tpms.inserttable.dao.T25APendingQueueDao;
import com.conduent.tpms.inserttable.model.T25APendingQueueRequestModel;
import com.conduent.tpms.inserttable.service.T25aPendingQueueService;

@Service
public class T25aPendingQueueServiceImpl implements T25aPendingQueueService {

	@Autowired
	T25APendingQueueDao t25APendingQueueDao;
	


	final Logger logger = LoggerFactory.getLogger(T25aPendingQueueServiceImpl.class);
	MapSqlParameterSource paramSource = new MapSqlParameterSource();

	@Override
	public Integer insertPendingDetails(T25APendingQueueRequestModel t25APendingQueueRequest)
			throws ParseException {

		return t25APendingQueueDao.insertPendingDaoDetails(t25APendingQueueRequest);

	}
}
