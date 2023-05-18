package com.conduent.tpms.intx.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.conduent.tpms.intx.config.LoadJpaQueries;
import com.conduent.tpms.intx.constants.IntxConstant;
import com.conduent.tpms.intx.dao.QatpStatisticsDao;
import com.conduent.tpms.intx.model.QatpStatistics;

/**
 * 
 * Qatp Statistics Dao 
 * 
 * @author deepeshb
 *
 */
@Repository
public class QatpStatisticsDaoImpl implements QatpStatisticsDao {

	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	/**
	 * Get QatpStatistics Info
	 * 
	 * @param atpFileId
	 * @return QatpStatistics
	 */
	@Override
	public QatpStatistics getQatpStatistics(Long atpFileId) {
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		String queryFile = LoadJpaQueries.getQueryById(IntxConstant.GET_QATP_STATISTICS);
		paramSource.addValue(IntxConstant.QUERY_PARAM_ATP_FILE_ID, atpFileId);
		List<QatpStatistics> tempQatpStatisticsList = namedParameterJdbcTemplate.query(queryFile, paramSource,
				BeanPropertyRowMapper.newInstance(QatpStatistics.class));
		if (tempQatpStatisticsList.isEmpty()) {
			return null;
		} else {
			return tempQatpStatisticsList.get(0);
		}
	}
	
	@Override
	public QatpStatistics getQatpStatisticsByXferId(Long xferFileId) {
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		String queryFile = LoadJpaQueries.getQueryById(IntxConstant.GET_T_FILE_STATISTICS_BY_FILEID);
		paramSource.addValue(IntxConstant.XFER_CONTROL_ID, xferFileId);
		List<QatpStatistics> tempQatpStatisticsList = namedParameterJdbcTemplate.query(queryFile, paramSource,
				BeanPropertyRowMapper.newInstance(QatpStatistics.class));
		if (tempQatpStatisticsList.isEmpty()) {
			return null;
		} else {
			return tempQatpStatisticsList.get(0);
		}
	}
	
	@Override
	public QatpStatistics getQatpStatisticsFromFileName(String fileName) {
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		String queryFile = LoadJpaQueries.getQueryById(IntxConstant.GET_QATP_STATISTICS_FROM_FILE_NAME);
		paramSource.addValue(IntxConstant.QUERY_PARAM_FILE_NAME, fileName);
		List<QatpStatistics> tempQatpStatisticsList = namedParameterJdbcTemplate.query(queryFile, paramSource,
				BeanPropertyRowMapper.newInstance(QatpStatistics.class));
		if (tempQatpStatisticsList.isEmpty()) {
			return null;
		} else {
			return tempQatpStatisticsList.get(0);
		}
	}
}
