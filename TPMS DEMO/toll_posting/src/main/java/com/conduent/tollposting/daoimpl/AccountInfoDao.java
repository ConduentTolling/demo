package com.conduent.tollposting.daoimpl;

import java.text.ParseException;
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

import com.conduent.tollposting.config.LoadJpaQueries;
import com.conduent.tollposting.constant.Constants;
import com.conduent.tollposting.dao.IAccountInfoDao;
import com.conduent.tollposting.dto.AccountInfoDTO;
import com.conduent.tollposting.utility.DateUtils;

@Repository
public class AccountInfoDao implements IAccountInfoDao
{
	private static final Logger logger = LoggerFactory.getLogger(AccountInfoDao.class);
	
	@Autowired
	private NamedParameterJdbcTemplate namedJdbcTemplate;

	@Override
	public AccountInfoDTO getAccountInfo(Long etcAccountId,Integer accountType,LocalDate txDate) throws ParseException 
	{
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue(Constants.ETC_ACCOUNT_ID, etcAccountId);
		paramSource.addValue(Constants.TX_DATE, DateUtils.convertToDateFormat(String.valueOf(txDate)));
		String queryToCheckFile =null;
		if(accountType.intValue()==11 || accountType.intValue()==12)
		{
			queryToCheckFile=LoadJpaQueries.getQueryById("GET_ACCOUNT_INFO_USING_ETC_ACCOUNT_NO_1");
		}
		else
		{
			queryToCheckFile=LoadJpaQueries.getQueryById("GET_ACCOUNT_INFO_USING_ETC_ACCOUNT_NO");
		}
		LocalDateTime fromTime = LocalDateTime.now();
		List<AccountInfoDTO> list= namedJdbcTemplate.query(queryToCheckFile,paramSource, new BeanPropertyRowMapper<AccountInfoDTO>(AccountInfoDTO.class));
		logger.debug("##SQL Time Taken for thread {} HOSTNAME: {} in getAccountInfo2: {} ms", Thread.currentThread().getName(),System.getenv("HOSTNAME"), ChronoUnit.MILLIS.between(fromTime, LocalDateTime.now()));
		if(list==null || list.isEmpty())
		{
			return null;
		}
		else
		{
			return list.get(0);
		}
	}

	@Override
	public void getAccountBalance(AccountInfoDTO accountInfo) 
	{
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue(Constants.ETC_ACCOUNT_ID, accountInfo.getEtcAccountId());
		String queryToCheckFile = LoadJpaQueries.getQueryById("GET_ACCOUNT_BALANCE");
		LocalDateTime fromTime = LocalDateTime.now();
		Double currentBalance=namedJdbcTemplate.queryForObject(queryToCheckFile,paramSource, new BeanPropertyRowMapper<Double>(Double.class));
		logger.debug("##SQL Time Taken for thread {} HOSTNAME: {} in getAccountBalance: {} ms", Thread.currentThread().getName(),System.getenv("HOSTNAME"), ChronoUnit.MILLIS.between(fromTime, LocalDateTime.now()));
		accountInfo.setCurrentBalance(currentBalance);
	}
}