package com.conduent.tpms.intx.dao.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.conduent.tpms.intx.config.LoadJpaQueries;
import com.conduent.tpms.intx.constants.IntxConstant;
import com.conduent.tpms.intx.dao.IagFileStatisticsDao;
import com.conduent.tpms.intx.model.IagFileStatistics;

import ch.qos.logback.classic.Logger;

/**
 * Iag File Statistics Dao
 * 
 * @author deepeshb
 *
 */
@Repository
public class IagFileStatisticsDaoImpl implements IagFileStatisticsDao {

	private static final Logger logger = (Logger) LoggerFactory.getLogger(IagFileStatisticsDaoImpl.class);	
	
	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	/**
	 * Insert Iag File Statistics
	 * 
	 * @param iagFileStatistics
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void insertIagFileStatistics(IagFileStatistics iagFileStatistics, String header) {
		try {
			MapSqlParameterSource paramSource = new MapSqlParameterSource();
			String queryFile = LoadJpaQueries.getQueryById(IntxConstant.INSERT_IAG_FILE_STATISTICS);
			paramSource.addValue(IntxConstant.QUERY_PARAM_INPUT_FILE_NAME, iagFileStatistics.getInputFileName());
			paramSource.addValue(IntxConstant.QUERY_PARAM_OUTPUT_FILE_NAME, iagFileStatistics.getOutputFileName());
			paramSource.addValue(IntxConstant.QUERY_PARAM_XFER_CONTROL_ID, iagFileStatistics.getXferControlId());
			paramSource.addValue(IntxConstant.QUERY_PARAM_ATP_FILE_ID, iagFileStatistics.getAtpFileId());
			paramSource.addValue(IntxConstant.QUERY_PARAM_INPUT_REC_COUNT,iagFileStatistics.getOutputRecCount() ); // swapped as per PB 
			paramSource.addValue(IntxConstant.QUERY_PARAM_OUTPUT_REC_COUNT,  header.substring(24, 32));// swapped as per PB 
			paramSource.addValue(IntxConstant.QUERY_PARAM_FROM_AGENCY, iagFileStatistics.getFromAgency());
			paramSource.addValue(IntxConstant.QUERY_PARAM_TO_AGENCY, iagFileStatistics.getToAgency());
			paramSource.addValue(IntxConstant.QUERY_PARAM_UPDATE_TS, iagFileStatistics.getUpdateTs());
			logger.info("Insert IagFileStatistics record : {}",paramSource);
			namedParameterJdbcTemplate.update(queryFile, paramSource);
		} catch (DataAccessException e) {
			logger.info("DataAccessException in insertIagFileStatistics: {}",e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			logger.info("Exception in insertIagFileStatistics: {}",e.getMessage());
			e.printStackTrace();
		}

	}
	


	/**
	 * Update Iag File Statistics
	 * 
	 * @param iagFileStatistics
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void updateIagFileStatistics(IagFileStatistics iagFileStatistics) {

		try {
			MapSqlParameterSource paramSource = new MapSqlParameterSource();
			String queryFile = LoadJpaQueries.getQueryById(IntxConstant.UPDATE_IAG_FILE_STATISTICS);
			paramSource.addValue(IntxConstant.QUERY_PARAM_INPUT_REC_COUNT,iagFileStatistics.getOutputRecCount() ); // swapped as per PB 
//			paramSource.addValue(IntxConstant.QUERY_PARAM_OUTPUT_REC_COUNT,  iagFileStatistics.getInputRecCount());
			paramSource.addValue(IntxConstant.QUERY_PARAM_OUTPUT_FILE_NAME, iagFileStatistics.getOutputFileName());
			paramSource.addValue(IntxConstant.QUERY_PARAM_ATP_FILE_ID, iagFileStatistics.getAtpFileId());
			paramSource.addValue(IntxConstant.QUERY_PARAM_XFER_CONTROL_ID, iagFileStatistics.getXferControlId());
			paramSource.addValue(IntxConstant.QUERY_PARAM_FROM_AGENCY, iagFileStatistics.getFromAgency());
			paramSource.addValue(IntxConstant.QUERY_PARAM_TO_AGENCY, iagFileStatistics.getToAgency());
			paramSource.addValue(IntxConstant.QUERY_PARAM_UPDATE_TS, LocalDateTime.now());
			logger.info("Update IagFileStatistics record : {}",paramSource);
			namedParameterJdbcTemplate.update(queryFile, paramSource);
		} catch (DataAccessException e) {
			logger.info("Exception in insertIagFileStatistics: {}",e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * Get Iag File Statistics
	 * 
	 * @param iagFileStatistics
	 */
	@Override
	public IagFileStatistics getIagFileStatistics(IagFileStatistics iagFileStatistics) {
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		String queryFile = LoadJpaQueries.getQueryById(IntxConstant.GET_IAG_FILE_STATISTICS);
		paramSource.addValue(IntxConstant.QUERY_PARAM_OUTPUT_FILE_NAME, iagFileStatistics.getOutputFileName());
		paramSource.addValue(IntxConstant.QUERY_PARAM_XFER_CONTROL_ID, iagFileStatistics.getXferControlId());
		paramSource.addValue(IntxConstant.QUERY_PARAM_FROM_AGENCY, iagFileStatistics.getFromAgency());
		paramSource.addValue(IntxConstant.QUERY_PARAM_TO_AGENCY, iagFileStatistics.getToAgency());
		List<IagFileStatistics> tempIagFileStatisticsList = namedParameterJdbcTemplate.query(queryFile, paramSource,
				BeanPropertyRowMapper.newInstance(IagFileStatistics.class));
		if (tempIagFileStatisticsList.isEmpty()) {
			return null;
		} else {
			return tempIagFileStatisticsList.get(0);
		}
	}

	/**
	 * Get ATP File Id 
	 * 
	 * @param iagFileStatistics
	 */
	@Override
	public IagFileStatistics getAtpFileId(IagFileStatistics iagFileStatistics) {
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		String queryFile = LoadJpaQueries.getQueryById(IntxConstant.GET_ATP_FILE_ID);
		paramSource.addValue(IntxConstant.QUERY_PARAM_OUTPUT_FILE_NAME, iagFileStatistics.getOutputFileName());
		paramSource.addValue(IntxConstant.QUERY_PARAM_FROM_AGENCY, iagFileStatistics.getFromAgency());
		paramSource.addValue(IntxConstant.QUERY_PARAM_TO_AGENCY, iagFileStatistics.getToAgency());
		List<IagFileStatistics> tempIagFileStatisticsList = namedParameterJdbcTemplate.query(queryFile, paramSource,
				BeanPropertyRowMapper.newInstance(IagFileStatistics.class));
		if (tempIagFileStatisticsList.isEmpty()) {
			return null;
		} else {
			return tempIagFileStatisticsList.get(0);
		}
	}
	
	
	@Override
	public void updateRecordCount(String fileName, Long fileRecordCount) {

		try {
			MapSqlParameterSource paramSource = new MapSqlParameterSource();
			String queryFile = LoadJpaQueries.getQueryById(IntxConstant.UPDATE_RECORD_COUNT_IN_IAG_FILE_STATISTICS);
			paramSource.addValue(IntxConstant.QUERY_PARAM_OUTPUT_REC_COUNT, fileRecordCount);
			paramSource.addValue(IntxConstant.QUERY_PARAM_OUTPUT_FILE_NAME,fileName );
			
			logger.info("Update IagFileStatistics record : {}",paramSource);
			namedParameterJdbcTemplate.update(queryFile, paramSource);
		} catch (DataAccessException e) {
			logger.info("Exception in insertIagFileStatistics: {}",e.getMessage());
			e.printStackTrace();
		}
	}
}
