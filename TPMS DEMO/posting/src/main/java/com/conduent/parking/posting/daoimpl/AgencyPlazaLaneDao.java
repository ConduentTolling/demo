package com.conduent.parking.posting.daoimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.conduent.parking.posting.config.LoadJpaQueries;
import com.conduent.parking.posting.dao.IAgencyPlazaLaneDao;
import com.conduent.parking.posting.model.AgencyPlazaLane;

@Repository
public class AgencyPlazaLaneDao implements IAgencyPlazaLaneDao
{
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public List<AgencyPlazaLane> getAgencyPlazaLane() 
	{
		String queryToCheckFile = LoadJpaQueries.getQueryById("GET_T_AGENCY_PLAZA_LANE");

		return jdbcTemplate.query(queryToCheckFile, new BeanPropertyRowMapper<AgencyPlazaLane>(AgencyPlazaLane.class));
	}

}
