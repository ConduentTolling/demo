package com.conduent.tollposting.daoimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.conduent.tollposting.config.LoadJpaQueries;
import com.conduent.tollposting.dao.IProcessParameterDao;
import com.conduent.tollposting.model.ProcessParameters;


@Repository
public class ProcessParameterDao implements IProcessParameterDao {

	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public List<ProcessParameters> getProcessParameters()
	{
		String queryToCheckFile = LoadJpaQueries.getQueryById("GET_T_PROCESS_PARAMETERS");
		return jdbcTemplate.query(queryToCheckFile, new BeanPropertyRowMapper<ProcessParameters>(ProcessParameters.class));
	}

	@Override
	public List<ProcessParameters> getMTARejAccount() {
		String queryToCheckFile = LoadJpaQueries.getQueryById("GET_HOME_AGENCY_ACCOUNT");
		return jdbcTemplate.query(queryToCheckFile, new BeanPropertyRowMapper<ProcessParameters>(ProcessParameters.class));
	}
	
	@Override
	public ProcessParameters getTollAmountValue()
	{
		String queryToCheckFile = LoadJpaQueries.getQueryById("GET_TOLL_AMOUNT_VALUE");
		return jdbcTemplate.queryForObject(queryToCheckFile, new BeanPropertyRowMapper<ProcessParameters>(ProcessParameters.class));
	}

	@Override
	public List<ProcessParameters> getProcessParametersICTX() {
		String queryToCheckFile = LoadJpaQueries.getQueryById("GET_T_PROCESS_PARAMETERS_ICTX");
		return jdbcTemplate.query(queryToCheckFile, new BeanPropertyRowMapper<ProcessParameters>(ProcessParameters.class));
	}

}
