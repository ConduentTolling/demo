package com.conduent.tpms.ictx.dao.impl;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.conduent.tpms.ictx.config.LoadJpaQueries;
import com.conduent.tpms.ictx.constants.IctxConstant;
import com.conduent.tpms.ictx.dao.IagFileStatsDao;
import com.conduent.tpms.ictx.model.IagFileStatistics;
import com.conduent.tpms.ictx.model.IagFileStats;

/**
 * Iag File Statistics Dao
 * 
 * @author deepeshb
 *
 */
@Repository
public class IagFileStatsDaoImpl implements IagFileStatsDao {

	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	@Autowired
	private JdbcTemplate jdbcTemplate;

	
//	@Override 
//	public void insertIagFileStats(IagFileStats iagFileStats) {
	@Override 
	public void insertIagFileStats(IagFileStatistics iagFileStats,String header) {
//		IagFileStatistics iagFileStatistics
		Long commonAtpFileId = checkAtpFileInStats(iagFileStats);
		
		if(commonAtpFileId == null) {
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		String queryFile = LoadJpaQueries.getQueryById(IctxConstant.INSERT_IA_FILE_STATS);
		paramSource.addValue(IctxConstant.QUERY_PARAM_ICTX_FILE_NAME, iagFileStats.getOutputFileName());
		paramSource.addValue(IctxConstant.QUERY_PARAM_XFER_CONTROL_ID, IctxConstant.ZERO_VAL); // It should be 0 as per PB
		paramSource.addValue(IctxConstant.QUERY_PARAM_PROCESSED_FLAG, IctxConstant.PROCESSED_FLAG_VALUE);
		paramSource.addValue(IctxConstant.DEPOSIT_ID,IctxConstant.ZERO);
		paramSource.addValue(IctxConstant.FILE_SEQ_NUMBER,header.substring((header.length()-1) - 6).trim()); 
		paramSource.addValue(IctxConstant.FILE_TYPE, IctxConstant.FILE_EXTENSION_ICTX);
		paramSource.addValue(IctxConstant.FILE_DATE, Date.valueOf(iagFileStats.getUpdateTs().toString().substring(0,10)));
		iagFileStats.setAtpFileId(getAtpFileSequence());	
		paramSource.addValue(IctxConstant.QUERY_PARAM_ATP_FILE_ID,iagFileStats.getAtpFileId() );
		paramSource.addValue(IctxConstant.QUERY_PARAM_INPUT_COUNT, Long.valueOf(header.substring(40, 48)));
		paramSource.addValue(IctxConstant.QUERY_PARAM_OUTPUT_COUNT,IctxConstant.ZERO_VAL); // It should be 0 as per PB
		paramSource.addValue(IctxConstant.QUERY_PARAM_FROM_AGENCY, iagFileStats.getFromAgency());
		paramSource.addValue(IctxConstant.QUERY_PARAM_TO_AGENCY, iagFileStats.getToAgency());
		paramSource.addValue(IctxConstant.QUERY_PARAM_UPDATE_TS, iagFileStats.getUpdateTs());
		namedParameterJdbcTemplate.update(queryFile, paramSource);
	}else {
		iagFileStats.setAtpFileId(commonAtpFileId);
	}
	}
	
	@Override 
	public void updateIagFileStats(IagFileStatistics iagFileStats) {
//		IagFileStatistics iagFileStatistics
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		String queryFile = LoadJpaQueries.getQueryById(IctxConstant.UPDATE_IA_FILE_STATS);
		paramSource.addValue(IctxConstant.QUERY_PARAM_ICTX_FILE_NAME, iagFileStats.getOutputFileName());
		paramSource.addValue(IctxConstant.QUERY_PARAM_ATP_FILE_ID, iagFileStats.getAtpFileId());
//		paramSource.addValue(IctxConstant.QUERY_PARAM_INPUT_COUNT, iagFileStats.getInputRecCount());
		paramSource.addValue(IctxConstant.QUERY_PARAM_OUTPUT_COUNT, IctxConstant.ZERO);
		paramSource.addValue(IctxConstant.QUERY_PARAM_FROM_AGENCY, iagFileStats.getFromAgency());
		paramSource.addValue(IctxConstant.QUERY_PARAM_TO_AGENCY, iagFileStats.getToAgency());
		paramSource.addValue(IctxConstant.QUERY_PARAM_UPDATE_TS, iagFileStats.getUpdateTs());
		namedParameterJdbcTemplate.update(queryFile, paramSource);

	}
	
	@Override
	public void updateRecordCount(String fileName, Long fileRecordCount) {

		try {
			MapSqlParameterSource paramSource = new MapSqlParameterSource();
			String queryFile = LoadJpaQueries.getQueryById(IctxConstant.UPDATE_RECORD_COUNT_IN_IA_FILE_STATS);
			paramSource.addValue(IctxConstant.QUERY_PARAM_INPUT_COUNT,  fileRecordCount);
			paramSource.addValue(IctxConstant.QUERY_PARAM_ICTX_FILE_NAME,fileName );
			namedParameterJdbcTemplate.update(queryFile, paramSource);
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
	}


//	@Override
	public Long getAtpFileSequence() {
		
		String queryFile = LoadJpaQueries.getQueryById(IctxConstant.GET_ATP_FILE_SEQUENCE_IAG);
		return jdbcTemplate.queryForObject(queryFile, Long.class);
		
	}
	
	
	public Long checkAtpFileInStats(IagFileStatistics iagFileStats) {
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		String queryFile = LoadJpaQueries.getQueryById(IctxConstant.CHECK_ATP_FILE_ID_IN_IA_FILE_STATS);
		
		paramSource.addValue(IctxConstant.QUERY_PARAM_ICTX_FILE_NAME, iagFileStats.getOutputFileName());
		
		List<IagFileStats> val = namedParameterJdbcTemplate.query(queryFile,paramSource,BeanPropertyRowMapper.newInstance(IagFileStats.class));
		if (val.isEmpty()) {
			return null;
		} else {
			return val.get(0).getAtpFileId();
		}
		
	}
	
	

}
