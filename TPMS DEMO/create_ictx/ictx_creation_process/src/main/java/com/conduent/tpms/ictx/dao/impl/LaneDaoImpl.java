package com.conduent.tpms.ictx.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.conduent.tpms.ictx.config.LoadJpaQueries;
import com.conduent.tpms.ictx.constants.IctxConstant;
import com.conduent.tpms.ictx.dao.LaneDao;
import com.conduent.tpms.ictx.model.Lane;

/**
 * Lane Dao Implementation
 * 
 * @author deepeshb
 *
 */
@Repository
public class LaneDaoImpl implements LaneDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	/**
	 * Get Lane Info
	 */
	@Override
	public List<Lane> getLane() {
		String queryToCheckFile = LoadJpaQueries.getQueryById(IctxConstant.GET_T_LANE);
		return jdbcTemplate.query(queryToCheckFile, new BeanPropertyRowMapper<Lane>(Lane.class));
	}

	@Override
	public List<Lane> getHomeLanePlazaList() {
		String queryToCheckFile = LoadJpaQueries.getQueryById(IctxConstant.GET_HOME_LANE_PLAZA_LIST);
		return jdbcTemplate.query(queryToCheckFile, new BeanPropertyRowMapper<Lane>(Lane.class));
	}

}
