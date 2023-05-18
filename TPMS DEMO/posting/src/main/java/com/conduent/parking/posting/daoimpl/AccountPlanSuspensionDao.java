package com.conduent.parking.posting.daoimpl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.conduent.parking.posting.constant.Constants;
import com.conduent.parking.posting.dao.IAccountPlanSuspensionDao;
import com.conduent.parking.posting.model.AccountPlanSuspension;

@Repository
public class AccountPlanSuspensionDao implements IAccountPlanSuspensionDao{

	private static final Logger logger = LoggerFactory.getLogger(AccountPlanSuspensionDao.class);
	
	@Autowired
	private NamedParameterJdbcTemplate namedJdbcTemplate;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public AccountPlanSuspension getAccountPlanSuspension(String apdId, Long etcAccountId, LocalDate trxDate) //:TODO ETC_ACCOUNT_ID or ETC_ACCOUNT_NUMBER
	{
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue(Constants.APD_ID, apdId);
		paramSource.addValue(Constants.ETC_ACCOUNT_ID, etcAccountId);
		paramSource.addValue(Constants.TX_DATE, trxDate);
		String queryToCheckFile ="SELECT ETC_ACCOUNT_ID,APD_ID,CREATE_DATE,START_DATE,nvl(END_DATE,sysdate+1) as END_DATE FROM T_ACCOUNT_PLAN_SUSPENSION WHERE APD_ID = :APD_ID AND ETC_ACCOUNT_ID = :ETC_ACCOUNT_ID AND suspension_status = 1 AND :TX_DATE BETWEEN START_DATE AND nvl(END_DATE,sysdate+1)";
		LocalDateTime fromTime = LocalDateTime.now();
		List<AccountPlanSuspension> list= namedJdbcTemplate.query(queryToCheckFile,paramSource, new BeanPropertyRowMapper<AccountPlanSuspension>(AccountPlanSuspension.class));
		logger.debug("##SQL Time Taken for thread {} HOSTNAME: {} in getAccountPlanSuspension: {} ms", Thread.currentThread().getName(),System.getenv("HOSTNAME"), ChronoUnit.MILLIS.between(fromTime, LocalDateTime.now()));
		if(list!=null && !list.isEmpty())
		{
			return list.get(0);
		}
		return null;
	}

	@Override
	public void updateAcountPlanSuspension(LocalDate txDate,String apdId,Long etcAccountId,LocalDate suspStart,LocalDate suspEnd) 
	{
		String updateQuery="UPDATE T_ACCOUNT_PLAN_SUSPENSION SET END_DATE = ?, SUSPENSION_STATUS = 2, UPDATE_TS = CURRENT_TIMESTAMP(2) WHERE APD_ID = ? AND ETC_ACCOUNT_ID = ? AND START_DATE = ? AND (END_DATE is NULL OR END_DATE= ?)";
		LocalDateTime fromTime = LocalDateTime.now();
		jdbcTemplate.update(updateQuery,txDate,apdId,etcAccountId,suspStart,suspEnd);
		logger.debug("##SQL Time Taken for thread {} HOSTNAME: {} in updateAcountPlanSuspension: {} ms", Thread.currentThread().getName(),System.getenv("HOSTNAME"), ChronoUnit.MILLIS.between(fromTime, LocalDateTime.now()));
	}
}