package com.conduent.tpms.intx.dao.impl;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.conduent.tpms.intx.config.LoadJpaQueries;
import com.conduent.tpms.intx.constants.IntxConstant;
import com.conduent.tpms.intx.dao.IagFileStatsDao;
import com.conduent.tpms.intx.model.IagFileStatistics;
import com.conduent.tpms.intx.model.IagFileStats;

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
		String queryFile = LoadJpaQueries.getQueryById(IntxConstant.INSERT_IA_FILE_STATS);
		paramSource.addValue(IntxConstant.QUERY_PARAM_INTX_FILE_NAME, iagFileStats.getOutputFileName());
		paramSource.addValue(IntxConstant.QUERY_PARAM_XFER_CONTROL_ID, IntxConstant.ZERO); // It should be 0 as per PB
		paramSource.addValue(IntxConstant.QUERY_PARAM_PROCESSED_FLAG, IntxConstant.PROCESSED_FLAG_VALUE);
		paramSource.addValue(IntxConstant.DEPOSIT_ID,IntxConstant.ZERO);
		paramSource.addValue(IntxConstant.FILE_SEQ_NUMBER,header.substring((header.length()-1) - 6).trim()); 
		paramSource.addValue(IntxConstant.FILE_TYPE, IntxConstant.FILE_EXTENSION_INTX);
		paramSource.addValue(IntxConstant.FILE_DATE, Date.valueOf(iagFileStats.getUpdateTs().toString().substring(0,10)));
		iagFileStats.setAtpFileId(getAtpFileSequence());	
		paramSource.addValue(IntxConstant.QUERY_PARAM_ATP_FILE_ID,iagFileStats.getAtpFileId() );
		paramSource.addValue(IntxConstant.QUERY_PARAM_INPUT_COUNT, header.substring(24, 32));
		paramSource.addValue(IntxConstant.QUERY_PARAM_OUTPUT_COUNT,IntxConstant.ZERO); // It should be 0 as per PB
		paramSource.addValue(IntxConstant.QUERY_PARAM_FROM_AGENCY, iagFileStats.getFromAgency());
		paramSource.addValue(IntxConstant.QUERY_PARAM_TO_AGENCY, iagFileStats.getToAgency());
		paramSource.addValue(IntxConstant.QUERY_PARAM_UPDATE_TS, iagFileStats.getUpdateTs());
		namedParameterJdbcTemplate.update(queryFile, paramSource);
	}else {
		iagFileStats.setAtpFileId(commonAtpFileId);
	}
	}
	
	@Override 
	public void updateIagFileStats(IagFileStatistics iagFileStats) {
//		IagFileStatistics iagFileStatistics
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		String queryFile = LoadJpaQueries.getQueryById(IntxConstant.UPDATE_IA_FILE_STATS);
		paramSource.addValue(IntxConstant.QUERY_PARAM_INTX_FILE_NAME, iagFileStats.getOutputFileName());
		paramSource.addValue(IntxConstant.QUERY_PARAM_ATP_FILE_ID, iagFileStats.getAtpFileId());
//		paramSource.addValue(IntxConstant.QUERY_PARAM_INPUT_COUNT, iagFileStats.getInputRecCount());
		paramSource.addValue(IntxConstant.QUERY_PARAM_OUTPUT_COUNT, IntxConstant.ZERO);
		paramSource.addValue(IntxConstant.QUERY_PARAM_FROM_AGENCY, iagFileStats.getFromAgency());
		paramSource.addValue(IntxConstant.QUERY_PARAM_TO_AGENCY, iagFileStats.getToAgency());
		paramSource.addValue(IntxConstant.QUERY_PARAM_UPDATE_TS, iagFileStats.getUpdateTs());
		namedParameterJdbcTemplate.update(queryFile, paramSource);

	}
	
	@Override
	public void updateRecordCount(String fileName, Long fileRecordCount) {

		try {
			MapSqlParameterSource paramSource = new MapSqlParameterSource();
			String queryFile = LoadJpaQueries.getQueryById(IntxConstant.UPDATE_RECORD_COUNT_IN_IA_FILE_STATS);
			paramSource.addValue(IntxConstant.QUERY_PARAM_INPUT_COUNT,  fileRecordCount);
			paramSource.addValue(IntxConstant.QUERY_PARAM_INTX_FILE_NAME,fileName );
			namedParameterJdbcTemplate.update(queryFile, paramSource);
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
	}


//	@Override
	public Long getAtpFileSequence() {
		
		String queryFile = LoadJpaQueries.getQueryById(IntxConstant.GET_ATP_FILE_SEQUENCE_IAG);
		return jdbcTemplate.queryForObject(queryFile, Long.class);
		
	}
	
	
	public Long checkAtpFileInStats(IagFileStatistics iagFileStats) {
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		String queryFile = LoadJpaQueries.getQueryById(IntxConstant.CHECK_ATP_FILE_ID_IN_IA_FILE_STATS);
		
		paramSource.addValue(IntxConstant.QUERY_PARAM_INTX_FILE_NAME, iagFileStats.getOutputFileName());
		
		List<IagFileStats> val = namedParameterJdbcTemplate.query(queryFile,paramSource,BeanPropertyRowMapper.newInstance(IagFileStats.class));
		if (val.isEmpty()) {
			return null;
		} else {
			return val.get(0).getAtpFileId();
		}
		
	}
	
	

}
