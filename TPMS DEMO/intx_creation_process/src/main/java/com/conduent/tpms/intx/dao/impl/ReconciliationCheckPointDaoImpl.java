package com.conduent.tpms.intx.dao.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.conduent.tpms.intx.config.LoadJpaQueries;
import com.conduent.tpms.intx.constants.IntxConstant;
import com.conduent.tpms.intx.dao.ReconciliationCheckPointDao;
import com.conduent.tpms.intx.model.ReconciliationCheckPoint;

/**
 * Reconciliation CheckPoint Dao
 * 
 * @author deepeshb
 *
 */
@Repository
public class ReconciliationCheckPointDaoImpl implements ReconciliationCheckPointDao {
	
	private static final Logger log = LoggerFactory.getLogger(ReconciliationCheckPointDaoImpl.class);

	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	/**
	 * Get file info
	 * 
	 * @param fromAgency
	 * @param toAgency
	 * @return ReconciliationCheckPoint
	 */
	@Override
	public ReconciliationCheckPoint getFileInfo(String fromAgency, String toAgency, String fileType) {
		StringBuilder sb = new StringBuilder();
		if(fileType.equalsIgnoreCase(IntxConstant.FILE_EXTENSION_INTX)) {
		sb.append(fromAgency).append(IntxConstant.CONSTANT_UNDERSCORE).append(toAgency)
				.append(IntxConstant.CONSTANT_PERCENT).append(IntxConstant.FILE_EXTENSION_DOT_INTX);
		}else if(fileType.equalsIgnoreCase(IntxConstant.FILE_EXTENSION_ITXN)) {
			sb.append(fromAgency).append(IntxConstant.CONSTANT_UNDERSCORE).append(toAgency)
			.append(IntxConstant.CONSTANT_PERCENT).append(IntxConstant.FILE_EXTENSION_DOT_ITXN);		
		}
		
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		String queryFile = LoadJpaQueries.getQueryById(IntxConstant.GET_RECONCILIATION_CHECKPOINT_INFO);
		paramSource.addValue(IntxConstant.QUERY_PARAM_FILE_NAME, sb.toString());

		log.info("Searching files in S/P states for {}", paramSource);
		List<ReconciliationCheckPoint> tempReconciliationCheckPointList = namedParameterJdbcTemplate.query(queryFile,
				paramSource, BeanPropertyRowMapper.newInstance(ReconciliationCheckPoint.class));
		log.info(queryFile);
		if (tempReconciliationCheckPointList.isEmpty()) {
			return null;
		} else {
			return tempReconciliationCheckPointList.get(0);
		}
	}

	/**
	 * Get file info by filename
	 * 
	 * @param fileName
	 * @return ReconciliationCheckPoint
	 */
	@Override
	public ReconciliationCheckPoint getFileInfoByFileName(String fileName) {
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		String queryFile = LoadJpaQueries.getQueryById(IntxConstant.GET_RECONCILIATION_CHECKPOINT_INFO);
		paramSource.addValue(IntxConstant.QUERY_PARAM_FILE_NAME, fileName);

		List<ReconciliationCheckPoint> tempReconciliationCheckPointList = namedParameterJdbcTemplate.query(queryFile,
				paramSource, BeanPropertyRowMapper.newInstance(ReconciliationCheckPoint.class));
		if (tempReconciliationCheckPointList.isEmpty()) {
			return null;
		} else {
			return tempReconciliationCheckPointList.get(0);
		}
	}

	/**
	 * Insert start entry
	 * 
	 * @param fileName
	 * @param recordCount
	 * @param fileStatusInd
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void insertStartEntry(String fileName, Long recordCount, String fileStatusInd) {
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		String queryFile = LoadJpaQueries.getQueryById(IntxConstant.INSERT_INTIAL_ENTRY_RECONCILIATION_CHECKPOINT_INFO);
		paramSource.addValue(IntxConstant.QUERY_PARAM_FILE_NAME, fileName);
		paramSource.addValue(IntxConstant.QUERY_PARAM_FILE_STATUS_IND, fileStatusInd);
		paramSource.addValue(IntxConstant.QUERY_PARAM_RECORD_COUNT, recordCount);
		namedParameterJdbcTemplate.update(queryFile, paramSource);
	}

	/**
	 * Update Record
	 * 
	 * @param fileName
	 * @param recordCount
	 * @param fileStatusInd
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void updateRecord(String fileName, Long recordCount, String fileStatusInd) {
		String query = LoadJpaQueries.getQueryById(IntxConstant.UPDATE_RECONCILIATION_CHECKPOINT_INFO);
		jdbcTemplate.update(query, fileStatusInd, recordCount, fileName);
	}

	/**
	 * Update status
	 * 
	 * @param fileName
	 * @param fileStatusInd
	 */
	@Override
	public void updateStatus(String fileName, String fileStatusInd) {
		String query = LoadJpaQueries.getQueryById(IntxConstant.UPDATE_RECONCILIATION_CHECKPOINT_STATUS);
		jdbcTemplate.update(query, fileStatusInd, fileName);
	}

}
