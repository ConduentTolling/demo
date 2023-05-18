package com.conduent.parking.posting.daoimpl;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.conduent.parking.posting.config.LoadJpaQueries;
import com.conduent.parking.posting.constant.Constants;
import com.conduent.parking.posting.dao.ITollExceptionDao;
import com.conduent.parking.posting.serviceimpl.TollException;

@Repository
public class TollExceptionDao implements ITollExceptionDao{

	private static final Logger logger = LoggerFactory.getLogger(TollExceptionDao.class);
	
	@Autowired
	private NamedParameterJdbcTemplate namedJdbcTemplate;
	
	@Override
	public void saveTollException(TollException tollException) 
	{
		String query = LoadJpaQueries.getQueryById("INSERT_T_TOLL_EXCEPTION");
		MapSqlParameterSource paramSource = null;
		paramSource = new MapSqlParameterSource();
		paramSource.addValue(Constants.LANE_TX_ID,tollException.getLaneTxId());
		paramSource.addValue(Constants.TX_DATE,tollException.getTxDate());
		paramSource.addValue(Constants.TX_DATE_TIME, tollException.getTxTimeStamp());
		paramSource.addValue(Constants.TX_STATUS, tollException.getTxStatus());
		paramSource.addValue(Constants.POSTED_DATE, tollException.getPostedDate());
		paramSource.addValue(Constants.ROW_ID, tollException.getRowId());
		paramSource.addValue(Constants.DEPOSITE_ID, tollException.getDepositId());
		paramSource.addValue(Constants.REVENUE_DATE, tollException.getRevenueDate());
		
		LocalDateTime fromTime = LocalDateTime.now();
		namedJdbcTemplate.update(query, paramSource);
		logger.debug("##SQL Time Taken for thread {} HOSTNAME: {} in saveTollException: {} ms", Thread.currentThread().getName(),System.getenv("HOSTNAME"), ChronoUnit.MILLIS.between(fromTime, LocalDateTime.now()));
	}
}