package com.conduent.tollposting.daoimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.conduent.tollposting.config.LoadJpaQueries;
import com.conduent.tollposting.dao.ITollPostLimitDao;
import com.conduent.tollposting.model.TollPostLimit;

@Repository
public class TollPostLimitDao implements ITollPostLimitDao
{
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public List<TollPostLimit> getTollPostLimit()
	{
		String queryToCheckFile = LoadJpaQueries.getQueryById("GET_T_TOLL_POST_LIMIT");

		return jdbcTemplate.query(queryToCheckFile, new BeanPropertyRowMapper<TollPostLimit>(TollPostLimit.class));
	}

}
