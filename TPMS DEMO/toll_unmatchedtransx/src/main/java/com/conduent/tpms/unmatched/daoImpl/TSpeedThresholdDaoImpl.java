package com.conduent.tpms.unmatched.daoImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.conduent.tpms.unmatched.constant.LoadJpaQueries;
import com.conduent.tpms.unmatched.constant.UnmatchedConstant;
import com.conduent.tpms.unmatched.dao.TSpeedThresholdDao;
import com.conduent.tpms.unmatched.model.SpeedThreshold;



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
		String queryFile = LoadJpaQueries.getQueryById(UnmatchedConstant.GET_SPEED_THRESHOLD_FOR_LANE);
		paramSource.addValue(UnmatchedConstant.IN_AGENCY_ID,agencyId);
		paramSource.addValue(UnmatchedConstant.IN_ACCT_TYPE,accountType);
		paramSource.addValue(UnmatchedConstant.IN_LANE_ID,laneId);
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
		String queryFile = LoadJpaQueries.getQueryById(UnmatchedConstant.GET_SPEED_THRESHOLD_FOR_AGENCY);
		paramSource.addValue(UnmatchedConstant.IN_AGENCY_ID,agencyId);
		paramSource.addValue(UnmatchedConstant.IN_ACCT_TYPE,accountType);
		
		List<SpeedThreshold> list = namedJdbcTemplate.query(queryFile, paramSource, BeanPropertyRowMapper.newInstance(SpeedThreshold.class));
		if(!list.isEmpty()) {
			return list.get(0);
		}
		
		return null;
	}

}
