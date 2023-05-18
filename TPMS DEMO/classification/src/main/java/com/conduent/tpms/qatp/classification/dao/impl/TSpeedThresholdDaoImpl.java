package com.conduent.tpms.qatp.classification.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.conduent.tpms.qatp.classification.config.LoadJpaQueries;
import com.conduent.tpms.qatp.classification.constants.QatpClassificationConstant;
import com.conduent.tpms.qatp.classification.dao.TSpeedThresholdDao;
import com.conduent.tpms.qatp.classification.model.SpeedThreshold;

/**
 * 
 * Speed Threshold Dao Implementation
 * 
 * @author deepeshb
 *
 */
@Repository
public class TSpeedThresholdDaoImpl implements TSpeedThresholdDao{

	@Autowired
	private NamedParameterJdbcTemplate namedJdbcTemplate;
	
	
	/**
	 *Get Speed Limit for Lane
	 */
	@Override
	public SpeedThreshold getSpeedLimitForLane(Integer agencyId,Integer laneId, Integer accountType) {
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		String queryFile = LoadJpaQueries.getQueryById(QatpClassificationConstant.GET_SPEED_THRESHOLD_FOR_LANE);
		paramSource.addValue(QatpClassificationConstant.IN_AGENCY_ID,agencyId);
		paramSource.addValue(QatpClassificationConstant.IN_ACCT_TYPE,accountType);
		paramSource.addValue(QatpClassificationConstant.IN_LANE_ID,laneId);
		List<SpeedThreshold> list = namedJdbcTemplate.query(queryFile, paramSource, BeanPropertyRowMapper.newInstance(SpeedThreshold.class));
		if(!list.isEmpty()) {
			return list.get(0);
		}
		
		return null;
	}
	

	/**
	 *Get Speed Limit for Agency
	 */
	@Override
	public SpeedThreshold getSpeedLimitForAgency(Integer agencyId, Integer accountType) {
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		String queryFile = LoadJpaQueries.getQueryById(QatpClassificationConstant.GET_SPEED_THRESHOLD_FOR_AGENCY);
		paramSource.addValue(QatpClassificationConstant.IN_AGENCY_ID,agencyId);
		paramSource.addValue(QatpClassificationConstant.IN_ACCT_TYPE,accountType);
		
		List<SpeedThreshold> list = namedJdbcTemplate.query(queryFile, paramSource, BeanPropertyRowMapper.newInstance(SpeedThreshold.class));
		if(!list.isEmpty()) {
			return list.get(0);
		}
		
		return null;
	}

}
