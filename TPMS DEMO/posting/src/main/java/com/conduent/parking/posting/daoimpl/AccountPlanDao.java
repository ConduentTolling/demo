package com.conduent.parking.posting.daoimpl;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.conduent.parking.posting.config.LoadJpaQueries;
import com.conduent.parking.posting.constant.Constants;
import com.conduent.parking.posting.dao.IAccountPlanDao;
import com.conduent.parking.posting.model.AccountPlan;

@Repository
public class AccountPlanDao implements IAccountPlanDao
{
	
	@Autowired
	private NamedParameterJdbcTemplate namedJdbcTemplate;

	@Override
	public List<AccountPlan> getAccountPlanByEtcAccountId(Long etcAccountId,LocalDate txDate)
	{
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue(Constants.ETC_ACCOUNT_ID, etcAccountId);
		paramSource.addValue(Constants.TX_DATE, txDate);
		String queryToCheckFile = LoadJpaQueries.getQueryById("GET_ACCOUNT_PLAN_INFO");
		return namedJdbcTemplate.query(queryToCheckFile,paramSource, new BeanPropertyRowMapper<AccountPlan>(AccountPlan.class));
	}
	
	@Override
	public List<AccountPlan> getAccountPlanList()
	{
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		String queryToCheckFile = LoadJpaQueries.getQueryById("GET_ACCOUNT_PLAN_INFO_IN_MASTER");
		return namedJdbcTemplate.query(queryToCheckFile,paramSource, new BeanPropertyRowMapper<AccountPlan>(AccountPlan.class));
	}
}
