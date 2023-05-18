package com.conduent.tollposting.daoimpl;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.batch.BatchProperties.Jdbc;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import com.conduent.tollposting.config.LoadJpaQueries;
import com.conduent.tollposting.constant.Constants;
import com.conduent.tollposting.dao.IAccountPlanDao;
import com.conduent.tollposting.model.AccountPlan;

@Repository
public class AccountPlanDao implements IAccountPlanDao
{
	
	@Autowired
	private NamedParameterJdbcTemplate namedJdbcTemplate;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

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
		List<AccountPlan> list=null;
		try {
			String queryToCheckFile = LoadJpaQueries.getQueryById("GET_ACCOUNT_PLAN_INFO_IN_MASTER");
			list = jdbcTemplate.query(queryToCheckFile, 
					new BeanPropertyRowMapper<AccountPlan>(AccountPlan.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
}
