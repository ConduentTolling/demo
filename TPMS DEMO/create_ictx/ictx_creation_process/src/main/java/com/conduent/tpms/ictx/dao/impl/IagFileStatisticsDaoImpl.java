package com.conduent.tpms.ictx.dao.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.conduent.tpms.ictx.config.LoadJpaQueries;
import com.conduent.tpms.ictx.constants.IctxConstant;
import com.conduent.tpms.ictx.dao.IagFileStatisticsDao;
import com.conduent.tpms.ictx.model.IagFileStatistics;
import com.conduent.tpms.ictx.model.QatpStatistics;

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
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

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
			String queryFile = LoadJpaQueries.getQueryById(IctxConstant.INSERT_IAG_FILE_STATISTICS);
			paramSource.addValue(IctxConstant.QUERY_PARAM_INPUT_FILE_NAME, iagFileStatistics.getInputFileName());
			paramSource.addValue(IctxConstant.QUERY_PARAM_OUTPUT_FILE_NAME, iagFileStatistics.getOutputFileName());
			paramSource.addValue(IctxConstant.QUERY_PARAM_XFER_CONTROL_ID, iagFileStatistics.getXferControlId());
			paramSource.addValue(IctxConstant.QUERY_PARAM_ATP_FILE_ID, iagFileStatistics.getAtpFileId());
			paramSource.addValue(IctxConstant.QUERY_PARAM_INPUT_REC_COUNT,iagFileStatistics.getInputRecCount());
			paramSource.addValue(IctxConstant.QUERY_PARAM_OUTPUT_REC_COUNT,  Long.valueOf(header.substring(40, 48)));// swapped as per PB 
			paramSource.addValue(IctxConstant.QUERY_PARAM_FROM_AGENCY, iagFileStatistics.getFromAgency());
			paramSource.addValue(IctxConstant.QUERY_PARAM_TO_AGENCY, iagFileStatistics.getToAgency());
			paramSource.addValue(IctxConstant.QUERY_PARAM_UPDATE_TS, iagFileStatistics.getUpdateTs());
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
			String queryFile = LoadJpaQueries.getQueryById(IctxConstant.UPDATE_IAG_FILE_STATISTICS);
			paramSource.addValue(IctxConstant.QUERY_PARAM_INPUT_REC_COUNT,iagFileStatistics.getOutputRecCount() ); // swapped as per PB 
//			paramSource.addValue(IctxConstant.QUERY_PARAM_OUTPUT_REC_COUNT,  iagFileStatistics.getInputRecCount());
			paramSource.addValue(IctxConstant.QUERY_PARAM_OUTPUT_FILE_NAME, iagFileStatistics.getOutputFileName());
			paramSource.addValue(IctxConstant.QUERY_PARAM_ATP_FILE_ID, iagFileStatistics.getAtpFileId());
			paramSource.addValue(IctxConstant.QUERY_PARAM_XFER_CONTROL_ID, iagFileStatistics.getXferControlId());
			paramSource.addValue(IctxConstant.QUERY_PARAM_FROM_AGENCY, iagFileStatistics.getFromAgency());
			paramSource.addValue(IctxConstant.QUERY_PARAM_TO_AGENCY, iagFileStatistics.getToAgency());
			paramSource.addValue(IctxConstant.QUERY_PARAM_UPDATE_TS, LocalDateTime.now());
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
		String queryFile = LoadJpaQueries.getQueryById(IctxConstant.GET_IAG_FILE_STATISTICS);
		paramSource.addValue(IctxConstant.QUERY_PARAM_OUTPUT_FILE_NAME, iagFileStatistics.getOutputFileName());
		paramSource.addValue(IctxConstant.QUERY_PARAM_XFER_CONTROL_ID, iagFileStatistics.getXferControlId());
		paramSource.addValue(IctxConstant.QUERY_PARAM_FROM_AGENCY, iagFileStatistics.getFromAgency());
		paramSource.addValue(IctxConstant.QUERY_PARAM_TO_AGENCY, iagFileStatistics.getToAgency());
		List<IagFileStatistics> tempIagFileStatisticsList = namedParameterJdbcTemplate.query(queryFile, paramSource,
				BeanPropertyRowMapper.newInstance(IagFileStatistics.class));
		if (tempIagFileStatisticsList.isEmpty()) {
			return null;
		} else {
			return tempIagFileStatisticsList.get(0);
		}
	}
	
	/**
	 * Get Iag File Statistics
	 * 
	 * @param iagFileStatistics
	 */
	@Override
	public Boolean getIagFileStatisticsByXferControlId(IagFileStatistics iagFileStatistics) {
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		try {
		String queryFile = LoadJpaQueries.getQueryById(IctxConstant.GET_IAG_FILE_STATISTICS_BY_XFER_CONTROL_ID);
		paramSource.addValue(IctxConstant.QUERY_PARAM_OUTPUT_FILE_NAME, iagFileStatistics.getOutputFileName());
		paramSource.addValue(IctxConstant.QUERY_PARAM_XFER_CONTROL_ID, iagFileStatistics.getXferControlId());
		logger.info("Update IagFileStatistics record : {}",paramSource);
		List<IagFileStatistics> tempIagFileStatisticsList = namedParameterJdbcTemplate.query(queryFile, paramSource,
				BeanPropertyRowMapper.newInstance(IagFileStatistics.class));
		if (tempIagFileStatisticsList.isEmpty()) {
			logger.info("No records already present in TPMS.T_IAG_FILE_STATISTICS");
			return false;
		} else {
			logger.info("Records already present in TPMS.T_IAG_FILE_STATISTICS");
			return true;
		}
		}catch(Exception e) {
			logger.info("Error while fetching TPMS.T_IAG_FILE_STATISTICS"+e.getMessage());
			return false;
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
		String queryFile = LoadJpaQueries.getQueryById(IctxConstant.GET_ATP_FILE_ID);
		paramSource.addValue(IctxConstant.QUERY_PARAM_OUTPUT_FILE_NAME, iagFileStatistics.getOutputFileName());
		paramSource.addValue(IctxConstant.QUERY_PARAM_FROM_AGENCY, iagFileStatistics.getFromAgency());
		paramSource.addValue(IctxConstant.QUERY_PARAM_TO_AGENCY, iagFileStatistics.getToAgency());
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
			String queryFile = LoadJpaQueries.getQueryById(IctxConstant.UPDATE_RECORD_COUNT_IN_IAG_FILE_STATISTICS);
			paramSource.addValue(IctxConstant.QUERY_PARAM_OUTPUT_REC_COUNT, fileRecordCount);
			paramSource.addValue(IctxConstant.QUERY_PARAM_OUTPUT_FILE_NAME,fileName );
			
			logger.info("Update IagFileStatistics record : {}",paramSource);
			namedParameterJdbcTemplate.update(queryFile, paramSource);
		} catch (DataAccessException e) {
			logger.info("Exception in insertIagFileStatistics: {}",e.getMessage());
			e.printStackTrace();
		}
	}
	
	/**
	 * Update Iag File Statistics
	 * 
	 * @param iagFileStatistics
	 */
	@Override
	public void updateIagFileStatisticsValues(IagFileStatistics iagFileStatistics, String header) {
		try {
			MapSqlParameterSource paramSource = new MapSqlParameterSource();
			String queryFile = LoadJpaQueries.getQueryById(IctxConstant.UPDATE_IAG_FILE_STATISTICS_VALUES);
			paramSource.addValue(IctxConstant.QUERY_PARAM_INPUT_FILE_NAME, iagFileStatistics.getInputFileName());			
			paramSource.addValue(IctxConstant.QUERY_PARAM_ATP_FILE_ID, iagFileStatistics.getAtpFileId());
			paramSource.addValue(IctxConstant.QUERY_PARAM_INPUT_REC_COUNT,iagFileStatistics.getInputRecCount());
			paramSource.addValue(IctxConstant.QUERY_PARAM_OUTPUT_REC_COUNT,  Long.valueOf(header.substring(40, 48)));// swapped as per PB 
			paramSource.addValue(IctxConstant.QUERY_PARAM_UPDATE_TS, iagFileStatistics.getUpdateTs());
			paramSource.addValue(IctxConstant.QUERY_PARAM_TO_AGENCY, iagFileStatistics.getToAgency());
			
			paramSource.addValue(IctxConstant.QUERY_PARAM_OUTPUT_FILE_NAME, iagFileStatistics.getOutputFileName());
			paramSource.addValue(IctxConstant.QUERY_PARAM_XFER_CONTROL_ID, iagFileStatistics.getXferControlId());
			
			logger.info("Update IagFileStatistics record : {}",paramSource);
			namedParameterJdbcTemplate.update(queryFile, paramSource);
		} catch (DataAccessException e) {
			logger.info("DataAccessException in updateIagFileStatistics: {}",e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			logger.info("Exception in updateIagFileStatistics: {}",e.getMessage());
			e.printStackTrace();
		}

	}
}
