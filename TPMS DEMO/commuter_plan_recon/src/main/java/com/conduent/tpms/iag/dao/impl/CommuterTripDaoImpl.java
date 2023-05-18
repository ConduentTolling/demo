package com.conduent.tpms.iag.dao.impl;

import java.time.LocalDate;

import java.time.YearMonth;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.conduent.app.timezone.utility.TimeZoneConv;
import com.conduent.tpms.iag.config.LoadJpaQueries;
import com.conduent.tpms.iag.constants.UUCTConstants;
import com.conduent.tpms.iag.dao.CommuterTripDao;
import com.conduent.tpms.iag.dto.PaymentDTO;
import com.conduent.tpms.iag.dto.PostUsagePlanDetailDto;
import com.conduent.tpms.iag.dto.TProcessParamterDto;
import com.conduent.tpms.iag.dto.TransactionDTO;
import com.conduent.tpms.iag.model.BatchUserInfo;
import com.conduent.tpms.iag.model.TTripHistory;

@Repository
public class CommuterTripDaoImpl implements CommuterTripDao{
	
	private static final Logger log = LoggerFactory.getLogger(CommuterTripDaoImpl.class);
	
	@Autowired
	private NamedParameterJdbcTemplate namedJdbcTemplate;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	TimeZoneConv timeZoneConv;
	
	
	@Override
	public List<TTripHistory> getTripHistoryDetails() 
	{	
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		
		String queryRules = LoadJpaQueries.getQueryById("LOAD_TRIP_DETAILS_FROM_T_TRIP_HISTORY");
		log.info("Trip details fetched from T_TRIP_HISTORY table successfully....");
		
		List<TTripHistory> tripInfoList = namedJdbcTemplate.query(queryRules, paramSource,
				BeanPropertyRowMapper.newInstance(TTripHistory.class));
		
		return tripInfoList;
	}
	
	@Override
	public List<PostUsagePlanDetailDto> getPostUsagePlanDetails() 
	{
		
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		
		String queryRules = LoadJpaQueries.getQueryById("LOAD_PLAN_DETAILS_FROM_T_POST_USAGE_PLAN_POLICY");
		//String queryRules = LoadJpaQueries.getQueryById("LOAD_TRIP_DETAILS_FROM_T_TRIP_HISTORY_FOR_POST");
		log.info("Plan Policy details fetched from T_POST_USAGE_PLAN_POLICY table successfully....");
		
		List<PostUsagePlanDetailDto> planPolicyDetailList = namedJdbcTemplate.query(queryRules, paramSource,
				BeanPropertyRowMapper.newInstance(PostUsagePlanDetailDto.class));
		
		return planPolicyDetailList;	
	}


	@Override
	public void UpdateTTripHistoryforTripLeftZero(int tripLeft , int apdId) 
	{
		int updateResult = 0;
		
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		
		paramSource.addValue(UUCTConstants.RECON_FLAG,UUCTConstants.RECON_FLAG_T);
		paramSource.addValue(UUCTConstants.RECON_DATE,LocalDate.now());
		paramSource.addValue(UUCTConstants.UPDATE_TS,timeZoneConv.currentTime());
		paramSource.addValue(UUCTConstants.TRIPS_LEFT, tripLeft);
		paramSource.addValue(UUCTConstants.APD_ID,apdId);
		
		String queryForUpdate = LoadJpaQueries.getQueryById("UPDATE_T_TRIP_HISTORY_TABLE_FOR_TRIP_LEFT_ZERO");
		
		updateResult = namedJdbcTemplate.update(queryForUpdate,paramSource);
		log.info("Number of rows updated...{}",updateResult);
		
	}

	@Override
	public void UpdateTTripHistory(int apdId, TTripHistory tripinfo) 
	{
		int updateResult = 0;
		
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		

		paramSource.addValue(UUCTConstants.RECON_FLAG,UUCTConstants.RECON_FLAG_F);
		paramSource.addValue(UUCTConstants.RECON_DATE,LocalDate.now());
		paramSource.addValue(UUCTConstants.UPDATE_TS,timeZoneConv.currentTime());
		paramSource.addValue(UUCTConstants.APD_ID,apdId);
		paramSource.addValue(UUCTConstants.TRIP_START_DATE, tripinfo.getTripStartDate());
		
		String queryForUpdate = LoadJpaQueries.getQueryById("UPDATE_T_TRIP_HISTORY_TABLE_ACCTSUSPEND");
		
		updateResult = namedJdbcTemplate.update(queryForUpdate,paramSource);
		log.info("Number of rows updated...{}",updateResult);	
		
	}
	
	
	public void UpdateTTripHistory(int apdId,double amt) 
	{
		int updateResult = 0;
		
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		
		paramSource.addValue(UUCTConstants.RECON_FLAG,UUCTConstants.RECON_FLAG_T);
		paramSource.addValue(UUCTConstants.RECON_DATE,LocalDate.now());
		paramSource.addValue(UUCTConstants.UPDATE_TS,timeZoneConv.currentTime());
		paramSource.addValue(UUCTConstants.AMOUNT_CHARGE, amt);
		paramSource.addValue(UUCTConstants.APD_ID,apdId);
		

		//paramSource.addValue(UUCTConstants.RECON_FLAG,UUCTConstants.RECON_FLAG_Y);
		//paramSource.addValue(UUCTConstants.RECON_DATE,LocalDate.now());
		//paramSource.addValue(UUCTConstants.UPDATE_TS,timeZoneConv.currentTime());
		//paramSource.addValue(UUCTConstants.APD_ID,apdId);

		
		String queryForUpdate = LoadJpaQueries.getQueryById("UPDATE_T_TRIP_HISTORY_TABLE");
		
		updateResult = namedJdbcTemplate.update(queryForUpdate,paramSource);
		log.info("Number of rows updated...{}",updateResult);	
		
	}


	
	public void UpdateTTripHistory(int apdId,double amt,TTripHistory tripInfo) 
	{
		int updateResult = 0;
		
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		
		paramSource.addValue(UUCTConstants.RECON_FLAG,UUCTConstants.RECON_FLAG_T);
		paramSource.addValue(UUCTConstants.RECON_DATE,LocalDate.now());
		paramSource.addValue(UUCTConstants.UPDATE_TS,timeZoneConv.currentTime());
		paramSource.addValue(UUCTConstants.AMOUNT_CHARGE, -amt);
		paramSource.addValue(UUCTConstants.TRIPS_CHARGED, tripInfo.getTripsLeft());
		
		paramSource.addValue(UUCTConstants.TRIP_START_DATE, tripInfo.getTripStartDate());
		paramSource.addValue(UUCTConstants.APD_ID,apdId);
		
		String queryForUpdate = LoadJpaQueries.getQueryById("UPDATE_T_TRIP_HISTORY_TABLE");
		
		updateResult = namedJdbcTemplate.update(queryForUpdate,paramSource);
		log.info("Number of rows updated...{}",updateResult);	
		
	}
	
	

	@Override
	public String getPlanType(int etc_account_id) 
	{
		
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue(UUCTConstants.ETC_ACCOUNT_ID, etc_account_id);
		
		String queryforPlanType = LoadJpaQueries.getQueryById("GET_PLAN_TYPE_FROM_V_ACCOUNT_PLAN_DETAIL_TABLE");
		
		String planType = namedJdbcTemplate.queryForObject(queryforPlanType, paramSource,String.class);
		log.info("Plan Type etc account id {} is {}",etc_account_id,planType);
		
		return planType;
		
		
	}

	@Override
	public String getPlanName(Integer planType) 
	{
		
			String planName;
			MapSqlParameterSource paramSource = new MapSqlParameterSource();
			paramSource.addValue(UUCTConstants.PLAN_TYPE, planType);
			//paramSource.addValue(UUCTConstants.IS_COMMUTE, UUCTConstants.IS_COMMUTE_VALUE);
			
			String queryforPlanType = LoadJpaQueries.getQueryById("GET_PLAN_NAME_FROM_V_PLAN_POLICY_TABLE");
			
			planName = namedJdbcTemplate.queryForObject(queryforPlanType, paramSource,String.class);
			log.info("Plan Name etc account id {} is {}",planType,planName);
			
			return planName;
		
	}

	@Override
	public String getAccountType(int etc_account_id) 
	{
		
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue(UUCTConstants.ETC_ACCOUNT_ID, etc_account_id);
		
		String queryforPlanType = LoadJpaQueries.getQueryById("GET_ACCOUNT_TYPE_FROM_V_ETC_ACCOUNT_TABLE");
		
		String accountType = namedJdbcTemplate.queryForObject(queryforPlanType, paramSource,String.class);
		log.info("Account Type etc account id {} is {}",etc_account_id,accountType);
		
		return accountType;
	}

	@Override
	public Integer getAccountAgencyID(int etc_account_id) 
	{
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue(UUCTConstants.ETC_ACCOUNT_ID, etc_account_id);
		
		String queryforAgencyId = LoadJpaQueries.getQueryById("GET_AGENCY_ID_FROM_V_ETC_ACCOUNT_TABLE");
		
		Integer agency_id = namedJdbcTemplate.queryForObject(queryforAgencyId, paramSource,Integer.class);
		log.info("Account Id for etc account id {} is {}",etc_account_id,agency_id);
		
		return agency_id;
	}

	@Override
	public Integer getMapAgencyID(String planName) 
	{
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue(UUCTConstants.PLAN_NAME, planName);
		
		String queryforAgencyId = LoadJpaQueries.getQueryById("GET_MAP_AGENCY_ID_FROM_V_PLAN_POLICY_TABLE");
		
		Integer map_agency_id = namedJdbcTemplate.queryForObject(queryforAgencyId, paramSource,Integer.class);
		log.info("Map agency Id for plan name {} is {}",planName,map_agency_id);
		
		return map_agency_id;
	}

	@Override
	public boolean checkRecordExitsin_T_PAYMENT(int etc_account_id) 
	{
		List<PaymentDTO> paymentrecord = new ArrayList<>();
		
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue(UUCTConstants.ETC_ACCOUNT_ID, etc_account_id);
		
		String queryforAgencyId = LoadJpaQueries.getQueryById("CHECK_IF_RECORD_EXISTS_IN_T_PAYMENT_TABLE");
		
		paymentrecord = namedJdbcTemplate.query(queryforAgencyId, paramSource,BeanPropertyRowMapper.newInstance(PaymentDTO.class));
		if(paymentrecord.isEmpty())
		{
			log.info("Record is not present for etc account id :{}",etc_account_id);
			return false;
		}
		else
		{
			log.info("Record is present for etc account id :{}",etc_account_id);
			return true;
		}
	}

	@Override
	public boolean checkRecordExitsin_T_TRANSACTION(int etc_account_id, int month) 
	{
		List<TransactionDTO> transactionrecord = new ArrayList<>();
		
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue(UUCTConstants.ETC_ACCOUNT_ID, etc_account_id);
		//paramSource.addValue(UUCTConstants.month,month);
		paramSource.addValue(UUCTConstants.MONTH, month);
		
		String queryforAgencyId = LoadJpaQueries.getQueryById("CHECK_IF_RECORD_EXISTS_IN_T_TRANSACTION_TABLE_new");
		
		transactionrecord = namedJdbcTemplate.query(queryforAgencyId, paramSource,BeanPropertyRowMapper.newInstance(TransactionDTO.class));
		if(transactionrecord.isEmpty())
		{
			log.info("Record is not present for etc account id :{}",etc_account_id);
			return false;
		}
		else
		{
			log.info("Record is present for etc account id :{}",etc_account_id);
			return true;
		}
	}
	
	@Override
	public List<TProcessParamterDto> getProcessParamtersList() 
	{
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		
		paramSource.addValue(UUCTConstants.PARAM_GROUP, UUCTConstants.UUCT);
		
		String queryRules =	LoadJpaQueries.getQueryById("SELECT_INFO_FROM_T_PROCESS_PARAMTERS");
			
		log.info("Agency info fetched from T_Agency table successfully.");
			
		return namedJdbcTemplate.query(queryRules,paramSource,new BeanPropertyRowMapper<TProcessParamterDto>(TProcessParamterDto.class));
			
		}


	@Override
	public String getnumdays() {
		// TODO Auto-generated method stub
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue(UUCTConstants.PARAM_GROUP, UUCTConstants.UUCTDAY);
		
		String queryRules =LoadJpaQueries.getQueryById("GET_NUMDAYS_PROCESSPARAM");
        return namedJdbcTemplate.queryForObject(queryRules, paramSource, String.class);
		//return null;
	}

	@Override
	public String getAccountsuspended(int etc_act_id) {
		// TODO Auto-generated method stub
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		
		paramSource.addValue(UUCTConstants.ETC_ACCOUNT_ID,etc_act_id);
		String queryRules =LoadJpaQueries.getQueryById("GET_ACCOUNT_SUSPENDED_FROM_V_ETC_ACCOUNT_TABLE");
		return namedJdbcTemplate.queryForObject(queryRules, paramSource, String.class);
		
		//return null;
	}

	@Override
	public String getAccountStatus(int etc_act_id) {
		// TODO Auto-generated method stub
		
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue(UUCTConstants.ETC_ACCOUNT_ID, etc_act_id);
		
		String query =LoadJpaQueries.getQueryById("GET_ACCOUNT_STATUS_CD_FROM_V_ETC_ACCOUNT_TABLE");
		return namedJdbcTemplate.queryForObject(query,paramSource,String.class);
		
		//return null;
	}

	@Override
	public String getPlanRenew(int id) {
		// TODO Auto-generated method stub
		
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue(UUCTConstants.PLAN_TYPE, id);
		String query = LoadJpaQueries.getQueryById("GET_PLAN_RENEEW_FROM_V_PLAN_POLICY_TABLE");
		return namedJdbcTemplate.queryForObject(query, paramSource, String.class);
		//return null;
	}

	@Override
	public List<TTripHistory> checktripfornextperiod(int etc_act_id, int month) {
		// TODO Auto-generated method stub
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue(UUCTConstants.ETC_ACCOUNT_ID, etc_act_id);
		paramSource.addValue(UUCTConstants.month, month);
		String query =LoadJpaQueries.getQueryById("CHECK_RECORD_EXISTS_FOR_NEXTPERIOD");
		//return namedJdbcTemplate.queryForObject(query, paramSource, new BeanPropertyRowMapper<>(TTripHistory.class));
		return namedJdbcTemplate.query(query, paramSource, new BeanPropertyRowMapper<>(TTripHistory.class));
		//return 0;
	}

	@Override
	public void inserttriphistorynextperiod(TTripHistory tripInfo) {
		
		log.info("inserting trip in trip history");
		// TODO Auto-generated method stub
		LocalDate trpstrtdate=null;
		LocalDate endDate =null;
		
		LocalDate lastTripEndDate = getLatestTripfromApdId(tripInfo.getEtcAccountId());
		if(null != lastTripEndDate) {
			 trpstrtdate = lastTripEndDate.plusDays(1);
			if(lastTripEndDate.getDayOfMonth()>=28) {
				LocalDate next = lastTripEndDate.plusMonths(1);
				 endDate   = YearMonth.of(next.getYear(), next.getMonth()).atEndOfMonth();
				//tripInfo.setTripEndDate(endDate);
			}else {
				 endDate= lastTripEndDate.plusMonths(1); 
				//tripInfo.setTripEndDate(lastTripEndDate.plusMonths(1));
			}
		}
		else {
			trpstrtdate= tripInfo.getLastTxDate();
			endDate =tripInfo.getLastTxDate().plusDays(getplandays(tripInfo.getPlanType())); // check
			//tripInfo.setTripStartDate(tripInfo.getLastTxDate());
			//tripInfo.setTripEndDate(tripInfo.getLastTxDate().plusDays(1));
		}
		
		
		
		String query = LoadJpaQueries.getQueryById("INSERT_T_TRIP_HISTORY");
		
		
		
		
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue(UUCTConstants.APD_ID,tripInfo.getApdId());
		paramSource.addValue(UUCTConstants.TRIP_START_DATE,trpstrtdate);
		paramSource.addValue(UUCTConstants.TRIP_END_DATE,endDate);
		paramSource.addValue(UUCTConstants.RECON_DATE ,null);
		paramSource.addValue(UUCTConstants.TRIPS_MADE,0);
		paramSource.addValue(UUCTConstants.TRIPS_CHARGED,0);
		paramSource.addValue(UUCTConstants.LATE_TRIPS,0);
		paramSource.addValue(UUCTConstants.AMOUNT_CHARGED,0);
		paramSource.addValue(UUCTConstants.RECON_FLAG,"F");
		paramSource.addValue(UUCTConstants.TRIPS_LEFT,getmintrips(tripInfo.getPlanType()));
		paramSource.addValue(UUCTConstants.UPDATE_TS ,timeZoneConv.currentTime());
		//paramSource.addValue(UUCTConstants.LAST_TX_DATE,LocalDate.now());
		paramSource.addValue(UUCTConstants.LAST_TX_DATE,null);
		paramSource.addValue(UUCTConstants.USAGE_AMOUNT,0); //check
		paramSource.addValue(UUCTConstants.FULL_FARE_AMOUNT,0); //check
		paramSource.addValue(UUCTConstants.DISCOUNTED_AMOUNT,0); //check
		paramSource.addValue(UUCTConstants.ETC_ACCOUNT_ID,tripInfo.getEtcAccountId());
		paramSource.addValue(UUCTConstants.PLAN_TYPE,tripInfo.getPlanType());
		//paramSource.addValue(UUCTConstants.LAST_LANE_TX_ID,tripInfo.getLastLaneTxId());
		paramSource.addValue(UUCTConstants.LAST_LANE_TX_ID,null);
		paramSource.addValue(UUCTConstants.LAST_TX_POST_TIMESTAMP,tripInfo.getLastTxPostTimeStamp()); //getLaneTxPostTimeStamp //getLastTxPostTimeStamp()
		paramSource.addValue(UUCTConstants.COMPOSITE_TRANSACTION_ID,null);
		paramSource.addValue(UUCTConstants.CSC_LOOKUP_KEY,null);
		
		
		
		int record =namedJdbcTemplate.update(query,paramSource);
		log.info("record inserted");
		
		
	}
	
	
	/*
	 * private void buildTripEndDate(TTripHistory tripInfo) { LocalDate
	 * lastTripEndDate = getLatestTripfromApdId(tripInfo.getApdId()); if(null !=
	 * lastTripEndDate) { tripInfo.setTripStartDate(lastTripEndDate.plusDays(1));
	 * if(lastTripEndDate.getDayOfMonth()>=28) { LocalDate next =
	 * lastTripEndDate.plusMonths(1); LocalDate endDate =
	 * YearMonth.of(next.getYear(), next.getMonth()).atEndOfMonth();
	 * tripInfo.setTripEndDate(endDate); }else {
	 * tripInfo.setTripEndDate(lastTripEndDate.plusMonths(1)); } } else {
	 * tripInfo.setTripStartDate(tripInfo.getLastTxDate());
	 * tripInfo.setTripEndDate(tripInfo.getLastTxDate().plusDays(planPolicy.
	 * getPlanDays())); }
	 * 
	 * }
	 */
	
	/*
	 * public LocalDate getLatestTripfromApdId(Integer apdId) {
	 * MapSqlParameterSource paramSource = new MapSqlParameterSource();
	 * paramSource.addValue(UUCTConstants.APD_ID, apdId); String sql =
	 * "select TRIP_END_DATE from t_trip_history where APD_ID=:APD_ID and ROWNUM < 2 order by trip_end_date DESC"
	 * ; List<LocalDate> list = namedJdbcTemplate.queryForList(sql, paramSource,
	 * LocalDate.class); //LocalDate list = namedJdbcTemplate.queryForObject(sql,
	 * paramSource, LocalDate.class);
	 * 
	 * if(list.isEmpty()) return null; else return list.get(0); //return list; }
	 */

	@Override
	public LocalDate getLatestTripfromApdId(int etc_act_id) {
		// TODO Auto-generated method stub
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue(UUCTConstants.ETC_ACCOUNT_ID, etc_act_id);
		//String sql = "select TRIP_END_DATE from t_trip_history where APD_ID=:APD_ID and ROWNUM < 2 order by trip_end_date DESC" ;
		String sql = LoadJpaQueries.getQueryById("TLATEST_TRIP_ENDDATE");
		List<LocalDate> list = namedJdbcTemplate.queryForList(sql, paramSource, LocalDate.class);
		//LocalDate list = namedJdbcTemplate.queryForObject(sql, paramSource, LocalDate.class);
		
		if(list.isEmpty())
			return null;
		else
			return list.get(0); 
		//return list;
		
		
	}

	@Override
	public int getplandays(int plantype) {
		// TODO Auto-generated method stub

		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue(UUCTConstants.PLAN_TYPE, plantype);
		String query = LoadJpaQueries.getQueryById("GET_PLAN_DAYS");
		return namedJdbcTemplate.queryForObject(query, paramSource, Integer.class);
		//return 0;
	}

	@Override
	public int getmintrips(int plantype) {
		// TODO Auto-generated method stub

		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue(UUCTConstants.PLAN_TYPE, plantype);
		String query = LoadJpaQueries.getQueryById("GET_MINTRIP");
		return namedJdbcTemplate.queryForObject(query, paramSource, Integer.class);
		//return 0;
	}

	@Override
	public List<TTripHistory> getTripForPostUsage() {
		// TODO Auto-generated method stub
	   MapSqlParameterSource paramSource = new MapSqlParameterSource();
		
		String queryRules = LoadJpaQueries.getQueryById("LOAD_TRIP_DETAILS_FROM_T_TRIP_HISTORY_FOR_POST");
		log.info("Trip details fetched from T_TRIP_HISTORY table successfully....");
		
		List<TTripHistory> tripInfoList = namedJdbcTemplate.query(queryRules, paramSource,
				BeanPropertyRowMapper.newInstance(TTripHistory.class));
		
		return tripInfoList;
	}

	@Override
	public PostUsagePlanDetailDto getPostUsagePlanDetailsAccToPlan(String planame) {
		// TODO Auto-generated method stub

		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue(UUCTConstants.PLAN_NAME,planame);
		
		String queryRules = LoadJpaQueries.getQueryById("LOAD_PLAN_DETAILS_FROM_T_POST_USAGE_PLAN_POLICY_ACCTOPLAN");
		//String queryRules = LoadJpaQueries.getQueryById("LOAD_TRIP_DETAILS_FROM_T_TRIP_HISTORY_FOR_POST");
		log.info("Plan Policy details fetched from T_POST_USAGE_PLAN_POLICY table successfully....");
		
		/*
		 * List<PostUsagePlanDetailDto> planPolicyDetailList =
		 * namedJdbcTemplate.query(queryRules, paramSource,
		 * BeanPropertyRowMapper.newInstance(PostUsagePlanDetailDto.class));
		 */
		PostUsagePlanDetailDto planPolicyDetail =namedJdbcTemplate.queryForObject(queryRules, paramSource, BeanPropertyRowMapper.newInstance(PostUsagePlanDetailDto.class));
		
		return planPolicyDetail;	
		
		
	}


	
}
