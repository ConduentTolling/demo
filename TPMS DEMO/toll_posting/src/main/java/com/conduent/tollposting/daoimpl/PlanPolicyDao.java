package com.conduent.tollposting.daoimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.conduent.tollposting.config.LoadJpaQueries;
import com.conduent.tollposting.dao.IPlanPolicyDao;
import com.conduent.tollposting.model.PlanPolicy;

@Repository
public class PlanPolicyDao implements IPlanPolicyDao
{
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public List<PlanPolicy> getPlanPolicy() 
	{
		String queryToCheckFile = LoadJpaQueries.getQueryById("GET_T_PLAN_POLICY");

		return jdbcTemplate.query(queryToCheckFile, new BeanPropertyRowMapper<PlanPolicy>(PlanPolicy.class));
	}
}
