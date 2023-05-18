package com.conduent.parking.posting.daoimpl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.conduent.parking.posting.constant.Constants;
import com.conduent.parking.posting.dao.ITripHistoryDao;
import com.conduent.parking.posting.model.TripHistory;

@Repository
public class TripHistoryDao implements ITripHistoryDao
{
	private static final Logger logger = LoggerFactory.getLogger(TripHistoryDao.class);
	
	@Autowired
	private NamedParameterJdbcTemplate namedJdbcTemplate;
	
	
	@Override
	public TripHistory getTripHistoryStartEndDate(String apdId, LocalDate trxDate) 
	{
		
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue(Constants.APD_ID, apdId);
		paramSource.addValue(Constants.TX_DATE, trxDate);
		String sql = "SELECT APD_ID,TRIP_START_DATE, TRIP_END_DATE, TRIPS_MADE, TRIPS_LEFT, RECON_FLAG, LAST_TX_DATE , USAGE_AMOUNT, FULL_FARE_AMOUNT, DISCOUNTED_AMOUNT FROM ( SELECT APD_ID,TRIP_START_DATE AS TRIP_START_DATE, TRIP_END_DATE AS TRIP_END_DATE, TRIPS_MADE, TRIPS_LEFT, RECON_FLAG, LAST_TX_DATE AS LAST_TX_DATE, USAGE_AMOUNT AS USAGE_AMOUNT, FULL_FARE_AMOUNT AS FULL_FARE_AMOUNT, DISCOUNTED_AMOUNT AS DISCOUNTED_AMOUNT FROM T_TRIP_HISTORY WHERE APD_ID = :APD_ID AND TRIP_END_DATE >= :TX_DATE AND TRIP_START_DATE<=:TX_DATE ORDER BY TRIP_START_DATE asc, TRIP_END_DATE asc ) X WHERE rownum < 2";
		LocalDateTime fromTime = LocalDateTime.now();
		List<TripHistory> list= namedJdbcTemplate.query(sql,paramSource, new BeanPropertyRowMapper<TripHistory>(TripHistory.class));
		logger.debug("##SQL Time Taken for thread {} HOSTNAME: {} in getAccountInfo: {} ms", Thread.currentThread().getName(),System.getenv("HOSTNAME"), ChronoUnit.MILLIS.between(fromTime, LocalDateTime.now()));
		if(list!=null && !list.isEmpty())
		{
			return list.get(0);
		}
		return null;
	}
	
	@Override
	public TripHistory getTripHistory(String apdId, LocalDate trxDate) 
	{
		
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue(Constants.APD_ID, apdId);
		paramSource.addValue(Constants.TX_DATE, trxDate);
		String sql = "SELECT APD_ID,TRIP_START_DATE, TRIP_END_DATE, TRIPS_MADE, TRIPS_LEFT, RECON_FLAG, LAST_TX_DATE , USAGE_AMOUNT, FULL_FARE_AMOUNT, DISCOUNTED_AMOUNT FROM ( SELECT APD_ID,TRIP_START_DATE AS TRIP_START_DATE, TRIP_END_DATE AS TRIP_END_DATE, TRIPS_MADE, TRIPS_LEFT, RECON_FLAG, LAST_TX_DATE AS LAST_TX_DATE, USAGE_AMOUNT AS USAGE_AMOUNT, FULL_FARE_AMOUNT AS FULL_FARE_AMOUNT, DISCOUNTED_AMOUNT AS DISCOUNTED_AMOUNT FROM T_TRIP_HISTORY WHERE APD_ID = :APD_ID AND TRIP_END_DATE >= :TX_DATE AND RECON_FLAG = 'F' ORDER BY TRIP_START_DATE asc, TRIP_END_DATE asc ) X WHERE rownum < 2";
		return (TripHistory) namedJdbcTemplate.queryForObject(sql,paramSource, new BeanPropertyRowMapper<TripHistory>(TripHistory.class));
	}
	
	@Override
	public List<TripHistory> getTripHistory(String apdId,LocalDate txDate,List<String> reconFlag) 
	{
		
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue(Constants.APD_ID, apdId);
		paramSource.addValue(Constants.TX_DATE, txDate);
		paramSource.addValue("RECON_FALG", reconFlag);
		String sql = "SELECT APD_ID,TRIP_END_DATE,TRIP_START_DATE,TRIPS_MADE,TRIPS_LEFT,RECON_FLAG,LAST_TX_DATE,USAGE_AMOUNT,FULL_FARE_AMOUNT,DISCOUNTED_AMOUNT FROM T_TRIP_HISTORY WHERE APD_ID = :APD_ID AND TRIP_START_DATE <= :TX_DATE AND TRIP_END_DATE + interval '0' day >= :TX_DATE AND RECON_FLAG in (:RECON_FALG)";
		return namedJdbcTemplate.query(sql,paramSource, new BeanPropertyRowMapper<TripHistory>(TripHistory.class));
	}
	
	/** Q1. Check if transaction falls between trip history records **/
	@Override
	public TripHistory getTripHistory(String apdId,LocalDate txDate,Long suspDay) 
	{
		
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue(Constants.APD_ID, apdId);
		paramSource.addValue(Constants.TX_DATE, txDate);
		paramSource.addValue(Constants.SUSP_DAY, suspDay);
		String sql = "SELECT APD_ID,TRIP_START_DATE, TRIP_END_DATE, TRIPS_MADE, TRIPS_LEFT, RECON_FLAG, LAST_TX_DATE , USAGE_AMOUNT, FULL_FARE_AMOUNT, DISCOUNTED_AMOUNT     FROM ( SELECT  APD_ID,TRIP_START_DATE AS TRIP_START_DATE, TRIP_END_DATE AS TRIP_END_DATE, TRIPS_MADE, TRIPS_LEFT, RECON_FLAG, LAST_TX_DATE AS LAST_TX_DATE , USAGE_AMOUNT AS USAGE_AMOUNT, FULL_FARE_AMOUNT AS FULL_FARE_AMOUNT, DISCOUNTED_AMOUNT AS DISCOUNTED_AMOUNT    FROM  T_TRIP_HISTORY WHERE  APD_ID = :APD_ID AND  TRIP_START_DATE <= :TX_DATE AND  TRIP_END_DATE + :SUSP_DAY >= :TX_DATE ORDER BY TRIP_START_DATE asc, TRIP_END_DATE asc ) X    WHERE rownum < 2";
		LocalDateTime fromTime = LocalDateTime.now();
		List<TripHistory> list= namedJdbcTemplate.query(sql,paramSource, new BeanPropertyRowMapper<TripHistory>(TripHistory.class));
		logger.debug("##SQL Time Taken for thread {} HOSTNAME: {} in getTripHistory: {} ms", Thread.currentThread().getName(),System.getenv("HOSTNAME"), ChronoUnit.MILLIS.between(fromTime, LocalDateTime.now()));
		if(list.isEmpty())
		{
			return null;
		}
		else
		{
			return list.get(0);
		}
	}
	
	/** Q2. Get trip history record where trx date before trip end_date and unreconciled **/
	@Override
	public TripHistory getTripHistoryBeforeEndDate(String apdId,LocalDate txDate,Long suspDay) 
	{
		
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue(Constants.APD_ID, apdId);
		paramSource.addValue(Constants.TX_DATE, txDate);
		paramSource.addValue(Constants.SUSP_DAY, suspDay);
		String sql = "SELECT APD_ID,TRIP_START_DATE, TRIP_END_DATE, TRIPS_MADE, TRIPS_LEFT, RECON_FLAG, LAST_TX_DATE , USAGE_AMOUNT, FULL_FARE_AMOUNT, DISCOUNTED_AMOUNT     FROM ( SELECT  APD_ID,TRIP_START_DATE AS TRIP_START_DATE, TRIP_END_DATE AS TRIP_END_DATE, TRIPS_MADE, TRIPS_LEFT, RECON_FLAG, LAST_TX_DATE AS LAST_TX_DATE , USAGE_AMOUNT AS USAGE_AMOUNT, FULL_FARE_AMOUNT AS FULL_FARE_AMOUNT, DISCOUNTED_AMOUNT AS DISCOUNTED_AMOUNT    FROM  T_TRIP_HISTORY WHERE  APD_ID = :APD_ID  AND  TRIP_END_DATE + :SUSP_DAY >= :TX_DATE ORDER BY TRIP_START_DATE asc, TRIP_END_DATE asc ) X    WHERE rownum < 2";
		LocalDateTime fromTime = LocalDateTime.now();
		List<TripHistory> list= namedJdbcTemplate.query(sql,paramSource, new BeanPropertyRowMapper<TripHistory>(TripHistory.class));
		logger.debug("##SQL Time Taken for thread {} HOSTNAME: {} in getTripHistoryBeforeEndDate: {} ms", Thread.currentThread().getName(),System.getenv("HOSTNAME"), ChronoUnit.MILLIS.between(fromTime, LocalDateTime.now()));
		if(list.isEmpty())
		{
			return null;
		}
		else
		{
			return list.get(0);
		}
	}
	
	/**Q3. As trip history found and update late trips **/
	@Override
	public void updateLateTrip(TripHistory history) 
	{
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue(Constants.APD_ID, history.getApdId());
		paramSource.addValue(Constants.TRIP_START_DATE, history.getTripStartDate());
		paramSource.addValue(Constants.TRIP_END_DATE, history.getTripEndDate());
		paramSource.addValue(Constants.LATE_TRIPS, history.getLateTrips());
		paramSource.addValue(Constants.USAGE_AMOUNT, history.getUsageAmount());
		paramSource.addValue(Constants.LAST_TX_DATE, history.getLastTxDate());
		paramSource.addValue(Constants.LANE_TX_ID, history.getLastLaneTxId());
		paramSource.addValue(Constants.FULL_FARE_AMOUNT, history.getFullFareAmount());
		paramSource.addValue(Constants.DISCOUNTED_AMOUNT, history.getDiscountedAmount());
		String sql = "UPDATE T_TRIP_HISTORY SET LATE_TRIPS = nvl(LATE_TRIPS,0) + :LATE_TRIPS, USAGE_AMOUNT = :USAGE_AMOUNT, LAST_TX_DATE = :LAST_TX_DATE, LAST_LANE_TX_ID = :LANE_TX_ID, FULL_FARE_AMOUNT = :FULL_FARE_AMOUNT, DISCOUNTED_AMOUNT = :DISCOUNTED_AMOUNT, UPDATE_TS = current_timestamp(3) WHERE APD_ID = :APD_ID AND TRIP_START_DATE = :TRIP_START_DATE AND TRIP_END_DATE = :TRIP_END_DATE";
		//String sql = "UPDATE T_TRIP_HISTORY SET LATE_TRIPS = nvl(LATE_TRIPS,0) + :LATE_TRIPS, UPDATE_TS = CURRENT_TIMESTAMP(2) WHERE APD_ID = :APD_ID AND TRIP_START_DATE = :TRIP_START_DATE AND TRIP_END_DATE = :TRIP_END_DATE";
		LocalDateTime fromTime = LocalDateTime.now();
		namedJdbcTemplate.update(sql,paramSource);
		logger.debug("##SQL Time Taken for thread {} HOSTNAME: {} in getAccountInfo: {} ms", Thread.currentThread().getName(),System.getenv("HOSTNAME"), ChronoUnit.MILLIS.between(fromTime, LocalDateTime.now()));
	}
	
	/** Q6. check for the last trips is exist and not reconciled **/
	@Override
	public TripHistory getTripHistory(String apdId,String reconFlag) 
	{
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue(Constants.APD_ID, apdId);
		String sql = "SELECT APD_ID,TRIP_START_DATE, TRIP_END_DATE, LAST_TX_DATE FROM ( SELECT APD_ID,TRIP_START_DATE AS TRIP_START_DATE, TRIP_END_DATE AS TRIP_END_DATE, LAST_TX_DATE AS LAST_TX_DATE FROM T_TRIP_HISTORY WHERE APD_ID = :APD_ID AND RECON_FLAG = :RECON_FLAG ORDER BY TRIP_START_DATE desc, TRIP_END_DATE desc ) X WHERE rownum < 2";
		LocalDateTime fromTime = LocalDateTime.now();
		List<TripHistory> list= namedJdbcTemplate.query(sql,paramSource, new BeanPropertyRowMapper<TripHistory>(TripHistory.class));
		logger.debug("##SQL Time Taken for thread {} HOSTNAME: {} in getTripHistory: {} ms", Thread.currentThread().getName(),System.getenv("HOSTNAME"), ChronoUnit.MILLIS.between(fromTime, LocalDateTime.now()));
		if(list.isEmpty())
		{
			return null;
		}
		else
		{
			return list.get(0);
		}
	}

	/** Q7. Insert a new trip **/
	public void insertTripHistory(TripHistory history)
	{
		String query="INSERT INTO T_TRIP_HISTORY (APD_ID, TRIP_START_DATE, TRIP_END_DATE, RECON_DATE, TRIPS_MADE, LATE_TRIPS, AMOUNT_CHARGED, TRIPS_CHARGED, RECON_FLAG, TRIPS_LEFT, UPDATE_TS, LAST_TX_DATE, USAGE_AMOUNT, FULL_FARE_AMOUNT, DISCOUNTED_AMOUNT, ETC_ACCOUNT_ID, PLAN_TYPE, LAST_LANE_TX_ID ) values (:APD_ID, :TRIP_START_DATE, :TRIP_END_DATE, :RECON_DATE, :TRIPS_MADE,    :LATE_TRIPS,                      :TRIPS_CHARGED, :AMOUNT_CHARGED, :RECON_FLAG, :TRIPS_LEFT, current_timestamp(3), :LAST_TX_DATE, :USAGE_AMOUNT, :FULL_FARE_AMOUNT, :DISCOUNTED_AMOUNT, :ETC_ACCOUNT_ID, :PLAN_TYPE_ID, :LANE_TX_ID)";
		MapSqlParameterSource paramSource = null;
		paramSource = new MapSqlParameterSource();
		paramSource.addValue(Constants.APD_ID,history.getApdId());
		paramSource.addValue(Constants.TRIP_START_DATE,history.getTripStartDate());
		paramSource.addValue(Constants.TRIP_END_DATE, history.getTripEndDate());
		paramSource.addValue(Constants.RECON_DATE, history.getRecongDate());
		paramSource.addValue(Constants.TRIPS_MADE, history.getTripsMade());
		paramSource.addValue(Constants.LATE_TRIPS, history.getLateTrips());
		paramSource.addValue(Constants.AMOUNT_CHARGED, history.getAmountCharged());
		paramSource.addValue(Constants.TRIPS_CHARGED, history.getTripCharged());
		paramSource.addValue(Constants.RECON_FLAG, history.getReconFlag());
		paramSource.addValue(Constants.TRIPS_LEFT, history.getTripsLeft());
		paramSource.addValue(Constants.LAST_TX_DATE, history.getLastTxDate());
		paramSource.addValue(Constants.USAGE_AMOUNT, history.getUsageAmount());
		paramSource.addValue(Constants.FULL_FARE_AMOUNT, history.getFullFareAmount());
		paramSource.addValue(Constants.DISCOUNTED_AMOUNT, history.getDiscountedAmount());
		paramSource.addValue(Constants.ETC_ACCOUNT_ID, history.getEtcAccountId());
		paramSource.addValue(Constants.PLAN_TYPE_ID, history.getPlanTypeId());
		paramSource.addValue(Constants.LANE_TX_ID, history.getLastLaneTxId());
		LocalDateTime fromTime = LocalDateTime.now();
		namedJdbcTemplate.update(query, paramSource);
		logger.debug("##SQL Time Taken for thread {} HOSTNAME: {} in insertTripHistory: {} ms", Thread.currentThread().getName(),System.getenv("HOSTNAME"), ChronoUnit.MILLIS.between(fromTime, LocalDateTime.now()));
	}

	@Override
	public void updateLateTripEnd_1(TripHistory history) {
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue(Constants.APD_ID, history.getApdId());
		paramSource.addValue(Constants.TRIP_START_DATE, history.getTripStartDate());
		paramSource.addValue(Constants.TRIP_END_DATE, history.getTripEndDate());
		paramSource.addValue(Constants.LATE_TRIPS, history.getLateTrips());
		paramSource.addValue(Constants.TRIPS_MADE, history.getTripsMade());
		paramSource.addValue(Constants.TRIPS_LEFT, history.getTripsLeft());
		paramSource.addValue(Constants.RECON_FLAG, history.getReconFlag());
		paramSource.addValue(Constants.RECON_DATE, history.getRecongDate());
		paramSource.addValue(Constants.USAGE_AMOUNT, history.getUsageAmount());
		paramSource.addValue(Constants.LAST_TX_DATE, history.getLastTxDate());
		paramSource.addValue(Constants.LANE_TX_ID, history.getLastLaneTxId());
		paramSource.addValue(Constants.FULL_FARE_AMOUNT, history.getFullFareAmount());
		paramSource.addValue(Constants.DISCOUNTED_AMOUNT, history.getDiscountedAmount());
		String sql = "UPDATE T_TRIP_HISTORY SET TRIPS_MADE = :TRIPS_MADE, TRIPS_LEFT = :TRIPS_LEFT, RECON_FLAG = :RECON_FLAG, RECON_DATE = :RECON_DATE, USAGE_AMOUNT = :USAGE_AMOUNT, LAST_TX_DATE = :LAST_TX_DATE, LAST_LANE_TX_ID = :LANE_TX_ID, FULL_FARE_AMOUNT = :FULL_FARE_AMOUNT, DISCOUNTED_AMOUNT = :DISCOUNTED_AMOUNT, UPDATE_TS = current_timestamp(3) WHERE APD_ID = :APD_ID AND TRIP_START_DATE = :TRIP_START_DATE AND TRIP_END_DATE = :TRIP_END_DATE";
		LocalDateTime fromTime = LocalDateTime.now();
		namedJdbcTemplate.update(sql,paramSource);
		logger.debug("##SQL Time Taken for thread {} HOSTNAME: {} in updateLateTripEnd_1: {} ms", Thread.currentThread().getName(),System.getenv("HOSTNAME"), ChronoUnit.MILLIS.between(fromTime, LocalDateTime.now()));
		
	}

	@Override
	public void updateLateTripEnd_2(TripHistory history) {
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue(Constants.APD_ID, history.getApdId());
		paramSource.addValue(Constants.TRIP_START_DATE, history.getTripStartDate());
		paramSource.addValue(Constants.TRIPS_MADE, history.getTripsMade());
		paramSource.addValue(Constants.TRIPS_LEFT, history.getTripsLeft());
		paramSource.addValue(Constants.RECON_FLAG, history.getReconFlag());
		paramSource.addValue(Constants.USAGE_AMOUNT, history.getUsageAmount());
		paramSource.addValue(Constants.LAST_TX_DATE, history.getLastTxDate());
		paramSource.addValue(Constants.LANE_TX_ID, history.getLastLaneTxId());
		paramSource.addValue(Constants.TRIP_END_DATE,history.getTripEndDate());
		paramSource.addValue(Constants.FULL_FARE_AMOUNT, history.getFullFareAmount());
		paramSource.addValue(Constants.DISCOUNTED_AMOUNT, history.getDiscountedAmount());
		String sql = "UPDATE T_TRIP_HISTORY SET TRIPS_MADE = :TRIPS_MADE, TRIPS_LEFT = :TRIPS_LEFT, RECON_FLAG = :RECON_FLAG, USAGE_AMOUNT = :USAGE_AMOUNT, LAST_TX_DATE = :LAST_TX_DATE, LAST_LANE_TX_ID = :LANE_TX_ID   , TRIP_END_DATE = :TRIP_END_DATE, FULL_FARE_AMOUNT = :FULL_FARE_AMOUNT, DISCOUNTED_AMOUNT = :DISCOUNTED_AMOUNT, UPDATE_TS = current_timestamp(3) WHERE APD_ID = :APD_ID AND TRIP_START_DATE = :TRIP_START_DATE";
		LocalDateTime fromTime = LocalDateTime.now();
		namedJdbcTemplate.update(sql,paramSource);
		logger.debug("##SQL Time Taken for thread {} HOSTNAME: {} in updateLateTripEnd_2: {} ms", Thread.currentThread().getName(),System.getenv("HOSTNAME"), ChronoUnit.MILLIS.between(fromTime, LocalDateTime.now()));
		
	}
}