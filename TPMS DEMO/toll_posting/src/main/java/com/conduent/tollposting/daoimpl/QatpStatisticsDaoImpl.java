package com.conduent.tollposting.daoimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.conduent.tollposting.constant.Constants;
import com.conduent.tollposting.dao.QatpStatisticsDao;
import com.conduent.tollposting.dto.QatpStatistics;

@Repository
public class QatpStatisticsDaoImpl implements QatpStatisticsDao {

	@Autowired
	NamedParameterJdbcTemplate namedJdbcTemplate;

	@Override
	public List<QatpStatistics> getStatsRecords(Long externFileId) {
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue(Constants.XFER_CONTROL_ID, externFileId);
		String sql = "select ATP_FILE_ID,file_type,XFER_CONTROL_ID from t_qatp_statistics where XFER_CONTROL_ID=:XFER_CONTROL_ID and file_type in ('ATPR','ATPP')";
		return namedJdbcTemplate.query(sql,paramSource, new BeanPropertyRowMapper<QatpStatistics>(QatpStatistics.class));

	}

	@Override
	public void updateStatisticsRecord(List<QatpStatistics> qStatsList) {
		
		for (QatpStatistics qatpStatistics2 : qStatsList) {
			MapSqlParameterSource paramSource = new MapSqlParameterSource();
			paramSource.addValue(Constants.XFER_CONTROL_ID, qatpStatistics2.getXferControlId());
			
			if(qatpStatistics2.getFileType().equalsIgnoreCase("ATPP")) {
			String sqlAtpp = "update t_qatp_statistics set RECORD_COUNT=(RECORD_COUNT-1) where XFER_CONTROL_ID=:XFER_CONTROL_ID AND FILE_TYPE='ATPP'";
			namedJdbcTemplate.update(sqlAtpp,paramSource);
			}
			else if(qatpStatistics2.getFileType().equalsIgnoreCase("ATPR")) {
			String sqlAtpr = "update t_qatp_statistics set RECORD_COUNT=(RECORD_COUNT+1) where XFER_CONTROL_ID=:XFER_CONTROL_ID AND FILE_TYPE='ATPR'";
			namedJdbcTemplate.update(sqlAtpr,paramSource);
			}
			
		}
	}

	@Override
	public void updateTranDetailRecord(List<QatpStatistics> qStatsList, Long laneTxId) {
		for (QatpStatistics qatpStatistics2 : qStatsList) {
			MapSqlParameterSource paramSource = new MapSqlParameterSource();
			
			if(qatpStatistics2.getFileType().equalsIgnoreCase("ATPR")) 
			{
			paramSource.addValue(Constants.EXT_FILE_ID, qatpStatistics2.getXferControlId());
			paramSource.addValue(Constants.ATP_FILE_ID, qatpStatistics2.getAtpFileId());
			paramSource.addValue(Constants.LANE_TX_ID, laneTxId);
			String sqlAtpr = "update t_tran_detail set ATP_FILE_ID=:ATP_FILE_ID where EXT_FILE_ID=:EXT_FILE_ID and LANE_TX_ID=:LANE_TX_ID";
			namedJdbcTemplate.update(sqlAtpr,paramSource);
			}
			
		}
		
	}
	
	
}
