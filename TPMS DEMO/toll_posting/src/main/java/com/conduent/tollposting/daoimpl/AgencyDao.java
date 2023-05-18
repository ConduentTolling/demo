package com.conduent.tollposting.daoimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.conduent.tollposting.config.LoadJpaQueries;
import com.conduent.tollposting.dao.IAgencyDao;
import com.conduent.tollposting.model.Agency;

@Repository
public class AgencyDao implements IAgencyDao {


	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public List<Agency> getAgency() 
	{
		String queryToCheckFile = LoadJpaQueries.getQueryById("GET_T_AGENCY");

		return jdbcTemplate.query(queryToCheckFile, new BeanPropertyRowMapper<Agency>(Agency.class));
	}

}
