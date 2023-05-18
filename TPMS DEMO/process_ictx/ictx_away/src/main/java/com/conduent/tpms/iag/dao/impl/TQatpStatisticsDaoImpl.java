package com.conduent.tpms.iag.dao.impl;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.conduent.app.timezone.utility.TimeZoneConv;
import com.conduent.tpms.iag.config.LoadJpaQueries;
import com.conduent.tpms.iag.constants.Constants;
import com.conduent.tpms.iag.constants.ICTXConstants;
import com.conduent.tpms.iag.dao.TQatpStatisticsDao;
import com.conduent.tpms.iag.model.IagFileStatistics;
import com.conduent.tpms.iag.model.IagFileStats;
import com.conduent.tpms.iag.model.QatpStatistics;
import com.conduent.tpms.iag.model.XferControl;

@Repository
public class TQatpStatisticsDaoImpl implements TQatpStatisticsDao {

	private static final Logger logger = LoggerFactory.getLogger(TQatpStatisticsDaoImpl.class);

	@Autowired
	private NamedParameterJdbcTemplate namedJdbcTemplate;

	@Autowired
	JdbcTemplate jdbcTemplate;
	@Autowired
	TimeZoneConv timeZoneConv;

	@Override
	public long insertIntoQATPStatistics(XferControl xferControl ) {
		long insertionId = 0;
		logger.info("Inserting QATP STATS..");
		// duplicate checking from t_qatp_statistics table
		String queryForSearch = LoadJpaQueries.getQueryById("GET_T_QATP_STATISTICS");
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue(Constants.FILE_NAME, xferControl.getXferFileName());
		Iterator<QatpStatistics> qatpStatisticsList = null;
		qatpStatisticsList = namedJdbcTemplate
				.query(queryForSearch, paramSource, BeanPropertyRowMapper.newInstance(QatpStatistics.class)).stream()
				.iterator();
		//qatpStatisticsList = namedJdbcTemplate.query(queryForSearch, paramSource,BeanPropertyRowMapper.newInstance(QatpStatistics.class));
		
		List<String> fileTypeList = new ArrayList<String>();
		fileTypeList.add(Constants.STATUS_ECTX);
		fileTypeList.add(Constants.STATUS_REJC);
		String query = null;
		String sql = null;
		String sqlAtpId = null;
		
		if (!qatpStatisticsList.hasNext()) {

			query = LoadJpaQueries.getQueryById("INSERT_INTO_T_QATP_STATISTICS");
			sql = LoadJpaQueries.getQueryById("GEN_SEQ_ATP_FILE_ID");

			for (String fileType : fileTypeList) {
				sqlAtpId = jdbcTemplate.queryForObject(sql, String.class);
				logger.info("{}: Atp file Id for file type {}",sqlAtpId, fileType);

				logger.info("Inserting STATS for xfer control id: {} atpId: {}", xferControl.getXferControlId(), sqlAtpId);

				insertionId = jdbcTemplate.update(query, sqlAtpId, fileType,
						xferControl.getXferFileName(), LocalDate.now(), LocalTime.now().toString(), Constants.ZERO,
						Constants.ZERO, Constants.NO, LocalDate.now(), LocalTime.now().toString(),
						Constants.ZERO, timeZoneConv.currentTime(), xferControl.getXferControlId());
			}
		}else {
			logger.info("STATS record already exist for file:{}",xferControl.getXferFileName());
		}
		return insertionId;
	}

	@Override
	public long updateIntoQATPStatistics(XferControl xferControl, long postCount, long rejctCount, long recCount, Double postTollAmt, Double rejctTollAmt) {
		logger.info("Updating QATP STATS..");
		
		String queryForSearch = LoadJpaQueries.getQueryById("GET_T_QATP_STATISTICS");
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue(Constants.FILE_NAME, xferControl.getXferFileName());
		
		List<QatpStatistics> qatpStatisticsList = namedJdbcTemplate.query(queryForSearch, paramSource,
				BeanPropertyRowMapper.newInstance(QatpStatistics.class));
		
		for (QatpStatistics qatpStatistics : qatpStatisticsList) {
			String fileType = qatpStatistics.getFileType();
			if(fileType.equals(Constants.STATUS_ECTX)) {
				paramSource.addValue(Constants.PROCESS_REC_COUNT, postCount);
				paramSource.addValue(Constants.AMOUNT, postTollAmt);
			}else if(fileType.equals(Constants.STATUS_REJC)) {
				paramSource.addValue(Constants.PROCESS_REC_COUNT, rejctCount);
				paramSource.addValue(Constants.AMOUNT, rejctTollAmt);
			}
			paramSource.addValue(Constants.RECORD_COUNT, recCount);
			paramSource.addValue(Constants.FILE_TYPE, fileType);
			paramSource.addValue(Constants.ATP_FILE_ID, qatpStatistics.getAtpFileId());
			paramSource.addValue(Constants.IS_PROCESSED, Constants.YES);
			paramSource.addValue(Constants.XFER_CONTROL_ID, qatpStatistics.getXferControlId());
			paramSource.addValue(Constants.UPDATE_TS, timeZoneConv.currentTime());
			KeyHolder keyHolder = new GeneratedKeyHolder();
			String query = LoadJpaQueries.getQueryById("UPDATE_STATUS_INTO_T_QATP_STATISTICS");
			namedJdbcTemplate.update(query, paramSource, keyHolder, new String[] { Constants.ID });
		}
		return 0;
	}
	
	@Override
	public void insertIagFileStats(IagFileStatistics iagFileStatistics) {
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		String queryFile = LoadJpaQueries.getQueryById(Constants.INSERT_IAG_FILE_STATS);
		
		try {
			if(checkAtpFileInStats(iagFileStatistics.getAtpFileId()) ==null) {
		
			paramSource.addValue(Constants.ICTX_FILE_NAME, iagFileStatistics.getInputFileName());
			paramSource.addValue(Constants.XFER_CONTROL_ID, iagFileStatistics.getXferControlId());
			paramSource.addValue(Constants.ATP_FILE_ID, iagFileStatistics.getAtpFileId());
			paramSource.addValue(Constants.FILE_SEQ_NUMBER, iagFileStatistics.getFileNumber());
			//add file_seq_number here
			paramSource.addValue(Constants.INPUT_COUNT, iagFileStatistics.getInputRecCount());
			paramSource.addValue(Constants.FROM_AGENCY, iagFileStatistics.getFromAgency());
			paramSource.addValue(Constants.TO_AGENCY, iagFileStatistics.getToAgency());
			paramSource.addValue(Constants.FILE_TYPE, iagFileStatistics.getFileType());
			paramSource.addValue(Constants.UPDATE_TS, timeZoneConv.currentTime());
			paramSource.addValue(Constants.PROCESSED_FLAG, Constants.NO);
			paramSource.addValue(Constants.FILE_DATE, iagFileStatistics.getFileDate());
			paramSource.addValue(Constants.DEPOSIT_ID, Constants.ZERO);
			logger.info("Inserting file into T_IA_FILE_STATS: {}",iagFileStatistics.getInputFileName());
			namedJdbcTemplate.update(queryFile, paramSource);
			}
		
		} catch (DataAccessException e) {
			logger.info("Exception while inserting data into T_IA_FILE_STATS: {}",paramSource);
			e.printStackTrace();
		}

	}
	
	
	private Long checkAtpFileInStats(Long atpFileId) {
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		String queryFile = LoadJpaQueries.getQueryById(ICTXConstants.CHECK_ATP_FILE_ID_IN_T_IA_FILE_STATS);
		
		
		
		paramSource.addValue(ICTXConstants.ATP_FILE_ID, atpFileId);
		
		List<IagFileStats> val = namedJdbcTemplate.query(queryFile,paramSource,BeanPropertyRowMapper.newInstance(IagFileStats.class));
		if (val.isEmpty()) {
			return null;
		} else {
			return val.get(0).getAtpFileId();
		}

	}
}
