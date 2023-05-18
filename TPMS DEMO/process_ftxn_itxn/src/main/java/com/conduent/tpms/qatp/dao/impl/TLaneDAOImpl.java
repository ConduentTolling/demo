package com.conduent.tpms.qatp.dao.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.conduent.tpms.qatp.config.LoadJpaQueries;
import com.conduent.tpms.qatp.dao.TLaneDAO;
import com.conduent.tpms.qatp.dto.TLaneDto;
import com.conduent.tpms.qatp.model.LaneIdExtLaneInfo;

@Repository
public class TLaneDAOImpl implements TLaneDAO{

	private static final Logger dao_log = LoggerFactory.getLogger(TLaneDAOImpl.class);

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public List<TLaneDto> getAllTLane() {
		String queryToCheckFile = LoadJpaQueries.getQueryById("GET_ALL_T_LANE");
		return jdbcTemplate.query(queryToCheckFile, new BeanPropertyRowMapper<TLaneDto>(TLaneDto.class));
	}

	
	@Override
	public List<LaneIdExtLaneInfo> getAwayExtLanePlazaList() {
		String queryToCheckFile = LoadJpaQueries.getQueryById("GET_AWAY_AGENCY_EXT_LANE_PLAZA_INFO");
		dao_log.info("Query GET_ALL_T_LANE");
		return jdbcTemplate.query(queryToCheckFile, new BeanPropertyRowMapper<LaneIdExtLaneInfo>(LaneIdExtLaneInfo.class));

	}
}
