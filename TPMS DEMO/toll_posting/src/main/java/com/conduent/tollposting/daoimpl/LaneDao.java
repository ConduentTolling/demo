package com.conduent.tollposting.daoimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.conduent.tollposting.config.LoadJpaQueries;
import com.conduent.tollposting.dao.ILaneDao;
import com.conduent.tollposting.model.Lane;

@Repository
public class LaneDao implements ILaneDao 
{
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public List<Lane> getLane() 
	{
		String queryToCheckFile = LoadJpaQueries.getQueryById("GET_T_LANE");
		return jdbcTemplate.query(queryToCheckFile, new BeanPropertyRowMapper<Lane>(Lane.class));
	}

}
