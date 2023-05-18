package com.conduent.tpms.unmatched.serviceImpl;

import java.text.ParseException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Service;

import com.conduent.tpms.unmatched.dao.UpdateUnmatchedEntryDao;
import com.conduent.tpms.unmatched.model.TUnmatchedEntryTxRequestModel;
import com.conduent.tpms.unmatched.service.UpdateUnmatchedEntryService;

@Service
public class UpdateUnmatchedEntryServiceImpl implements UpdateUnmatchedEntryService {

	@Autowired
	UpdateUnmatchedEntryDao updateUnmatchedEntryDao;

	final Logger logger = LoggerFactory.getLogger(UpdateUnmatchedEntryServiceImpl.class);
	MapSqlParameterSource paramSource = new MapSqlParameterSource();

	@Override
	public Integer updateUnmatchedEntryDetails(TUnmatchedEntryTxRequestModel tUnmatchedEntryTxRequest)
			throws ParseException {

		return updateUnmatchedEntryDao.updateUnmatchedEntryDaoDetails(tUnmatchedEntryTxRequest);

	}
}
