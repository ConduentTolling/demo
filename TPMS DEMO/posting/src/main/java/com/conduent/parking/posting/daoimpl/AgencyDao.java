package com.conduent.parking.posting.daoimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.conduent.parking.posting.config.LoadJpaQueries;
import com.conduent.parking.posting.dao.IAgencyDao;
import com.conduent.parking.posting.model.Agency;

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
