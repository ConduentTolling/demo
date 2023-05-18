package com.conduent.tpms.inserttable.serviceImpl;

import java.text.ParseException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Service;

import com.conduent.tpms.inserttable.dao.TLaneDao;
import com.conduent.tpms.inserttable.model.TLaneRequestModel;
import com.conduent.tpms.inserttable.service.TLaneService;

@Service
public class TLaneServiceImpl implements TLaneService {

	@Autowired
	TLaneDao tLaneDao;
	


	final Logger logger = LoggerFactory.getLogger(TLaneServiceImpl.class);
	MapSqlParameterSource paramSource = new MapSqlParameterSource();

	@Override
	public Integer insertTLane(TLaneRequestModel tLaneRequestModel)
			throws ParseException {

		return tLaneDao.insertTLaneDao(tLaneRequestModel);

	}
}
