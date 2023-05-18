package com.conduent.tpms.iag.dao.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.conduent.tpms.iag.config.LoadJpaQueries;
import com.conduent.tpms.iag.constants.Constants;
import com.conduent.tpms.iag.dao.TLaneDAO;
import com.conduent.tpms.iag.dto.TLaneDto;
import com.conduent.tpms.iag.model.LaneIdExtLaneInfo;

@Repository
public class TLaneDAOImpl implements TLaneDAO{

	private static final Logger log = LoggerFactory.getLogger(TLaneDAOImpl.class);

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public List<TLaneDto> getAllTLane() {
		String queryToCheckFile = LoadJpaQueries.getQueryById(Constants.GET_ALL_T_LANE);
		log.info("Query GET_ALL_T_LANE");
		return jdbcTemplate.query(queryToCheckFile, new BeanPropertyRowMapper<TLaneDto>(TLaneDto.class));
	}

	@Override
	public List<LaneIdExtLaneInfo> getAwayExtLanePlazaList() {
		String queryToCheckFile = LoadJpaQueries.getQueryById(Constants.GET_AWAY_AGENCY_EXT_LANE_PLAZA_INFO);
		log.info("Query GET_ALL_T_LANE_PLAZA");
		return jdbcTemplate.query(queryToCheckFile, new BeanPropertyRowMapper<LaneIdExtLaneInfo>(LaneIdExtLaneInfo.class));

	}
	
	@Override
	public TLaneDto getMaxLaneId() {
		String queryToCheckFile = LoadJpaQueries.getQueryById(Constants.GET_MAX_LANE_ID);
		TLaneDto new_lane_id = jdbcTemplate.queryForObject(queryToCheckFile, new BeanPropertyRowMapper<TLaneDto>(TLaneDto.class));		
		return new_lane_id;
	}

}
