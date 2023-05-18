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
	public TLaneDto getMaxLaneId() 
	{
		String queryToCheckFile = "select * from (select * from master.t_lane order by LANE_ID desc) where rownum < 2";
		
		TLaneDto new_lane_id = jdbcTemplate.queryForObject(queryToCheckFile, new BeanPropertyRowMapper<TLaneDto>(TLaneDto.class));
		
		return new_lane_id;
	}

}
