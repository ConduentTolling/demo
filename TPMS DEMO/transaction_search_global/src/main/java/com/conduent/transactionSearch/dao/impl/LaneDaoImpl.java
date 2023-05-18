package com.conduent.transactionSearch.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.conduent.transactionSearch.config.LoadQueries;
import com.conduent.transactionSearch.dao.LaneDao;
import com.conduent.transactionSearch.model.Lane;

@Repository
public class LaneDaoImpl implements LaneDao 
{
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public List<Lane> getLane() 
	{
		String queryToCheckFile = LoadQueries.getQueryById("GET_T_LANE");
		return jdbcTemplate.query(queryToCheckFile, new BeanPropertyRowMapper<Lane>(Lane.class));
	}

}
