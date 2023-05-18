package com.conduent.tpms.iag.dao.impl;

import java.util.List;
import java.sql.Types;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.conduent.tpms.iag.config.LoadJpaQueries;
import com.conduent.tpms.iag.constants.UUCTConstants;
import com.conduent.tpms.iag.dao.TODCreationDao;
import com.conduent.tpms.iag.model.BatchUserInfo;
import com.conduent.tpms.iag.model.TourOfDuty;

@Repository
public class TODCreationDaoImpl implements TODCreationDao 
{
	
	private static final Logger log = LoggerFactory.getLogger(TODCreationDaoImpl.class);
	
	@Autowired
	private NamedParameterJdbcTemplate namedJdbcTemplate;
	
	@Override
	public BatchUserInfo batchUserDetail() 
	{
		BatchUserInfo userInfo = null;
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue(UUCTConstants.USER_ID, UUCTConstants.UUCT);
		
		String queryforUserInfo = LoadJpaQueries.getQueryById("GET_USER_INFO_FROM_T_BATCH_USER_TABLE");
		userInfo = namedJdbcTemplate.queryForObject(queryforUserInfo, paramSource,BeanPropertyRowMapper.newInstance(BatchUserInfo.class));
		
		return userInfo;
	}

	@Override
	public List<TourOfDuty> listOfFinancialsNotClosed(String userId, String status, Boolean financials) 
	{

		/*MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue(UUCTConstants.USER_ID,UUCTConstants.UUCT);
		paramSource.addValue(UUCTConstants.STATUS,UUCTConstants.OPEN);
		paramSource.addValue(UUCTConstants.FINANCIALS, UUCTConstants.Y);
		
		String sql = LoadJpaQueries.getQueryById("GET_LIST_OF_FINANCIALS_NOT_CLOSED");
		List<TourOfDuty> listofFinancialInfo = namedJdbcTemplate.query(sql, paramSource,BeanPropertyRowMapper.newInstance(TourOfDuty.class));
		
		return listofFinancialInfo;*/
		
			String sql = "SELECT * FROM FPMS.T_TOUR_OF_DUTY TSD WHERE TSD.USER_ID='UUCT' And TSD.STATUS='OPEN' And TSD.FINANCIALS='Y'";
			MapSqlParameterSource param = (MapSqlParameterSource) new MapSqlParameterSource("UUCT", userId)
					.addValue("OPEN", status).addValue("Y", financials, Types.CHAR);
			List<TourOfDuty> dtos = namedJdbcTemplate.query(sql, param,BeanPropertyRowMapper.newInstance(TourOfDuty.class));
			return dtos;
		
	}
	
	@Override
	public void updateTOD(TourOfDuty dto) 
	{
		final String sql = "update T_TOUR_OF_DUTY set STATUS = :status , FINANCIALS= :financials where TOD_ID=:todId ";
		MapSqlParameterSource param = (MapSqlParameterSource) new MapSqlParameterSource("status",
				dto.getStatus()).addValue("financials", dto.getFinancials()).addValue("todId", dto.getTodId());

		int updateresult = namedJdbcTemplate.update(sql, param);
		log.info("Number or rows updated.{}",updateresult);
	}

	@Override
	public List<TourOfDuty> listOfStatusClosebyFinancialsOpen(String userId, String status, Boolean financials) 
	{
		String sql = "SELECT * FROM FPMS.T_TOUR_OF_DUTY TSD WHERE TSD.USER_ID='UUCT' And TSD.STATUS='CLOSED' And TSD.FINANCIALS='Y'";
		MapSqlParameterSource param = (MapSqlParameterSource) new MapSqlParameterSource("UUCT", userId)
				.addValue("CLOSED", status).addValue("Y", financials, Types.CHAR);
		List<TourOfDuty> result = namedJdbcTemplate.query(sql, param,BeanPropertyRowMapper.newInstance(TourOfDuty.class));
		return result;
	}

}
