package com.conduent.tpms.intx.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.conduent.tpms.intx.config.LoadJpaQueries;
import com.conduent.tpms.intx.constants.IntxConstant;
import com.conduent.tpms.intx.dao.LaneDao;
import com.conduent.tpms.intx.model.Lane;

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
		String queryToCheckFile = LoadJpaQueries.getQueryById(IntxConstant.GET_T_LANE);
		return jdbcTemplate.query(queryToCheckFile, new BeanPropertyRowMapper<Lane>(Lane.class));
	}

	@Override
	public List<Lane> getHomeLanePlazaList() {
		String queryToCheckFile = LoadJpaQueries.getQueryById(IntxConstant.GET_HOME_LANE_PLAZA_LIST);
		return jdbcTemplate.query(queryToCheckFile, new BeanPropertyRowMapper<Lane>(Lane.class));
	}

}
