package com.conduent.parking.posting.daoimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.conduent.parking.posting.config.LoadJpaQueries;
import com.conduent.parking.posting.dao.IProcessParameterDao;
import com.conduent.parking.posting.model.ProcessParameters;


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

}
