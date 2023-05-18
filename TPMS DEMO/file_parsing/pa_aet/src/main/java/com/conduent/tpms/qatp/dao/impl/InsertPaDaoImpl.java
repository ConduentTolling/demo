package com.conduent.tpms.qatp.dao.impl;

import java.sql.ResultSet;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import com.conduent.app.timezone.utility.TimeZoneConv;

import com.conduent.tpms.qatp.config.LoadJpaQueries;
import com.conduent.tpms.qatp.constants.QATPConstants;
import com.conduent.tpms.qatp.dao.InsertPaDao;
import com.conduent.tpms.qatp.model.QatpStatistics;
import com.conduent.tpms.qatp.model.XferControl;

@Repository
public class InsertPaDaoImpl implements InsertPaDao {

	private static final Logger logger = LoggerFactory.getLogger(InsertPaDaoImpl.class);

	@Autowired
	private NamedParameterJdbcTemplate namedJdbcTemplate;

	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Autowired
	TimeZoneConv timeZoneConv;

	@Override
	public XferControl getXferDataForStatistics(String fileName) {

		try {
			MapSqlParameterSource paramSource = new MapSqlParameterSource();
//			String query = "SELECT *  FROM MASTER.T_XFER_CONTROL WHERE XFER_FILE_NAME="
//			+ "'"+ fileName + "' order by xfer_control_id desc";
			String query = "SELECT *  FROM (SELECT * FROM MASTER.T_XFER_CONTROL WHERE "
			+ "XFER_FILE_NAME='"+ fileName + "' order by xfer_control_id desc) where rownum<2";
			
			return namedJdbcTemplate.queryForObject(query, paramSource, new RowMapper<XferControl>() {
				@Override
				public XferControl mapRow(ResultSet rs, int rowNum) throws SQLException {
					// Getting Data from TPMS.T_XFER_CONTROL table
					XferControl xferControl = new XferControl();
					xferControl.setXferFileName(rs.getString("XFER_FILE_NAME"));
					xferControl.setXferControlId(rs.getLong("XFER_CONTROL_ID"));

					return xferControl;
				}
			});
		} catch (Exception ex) {
			return null;
		}
	}

	public int insertPaFileInQATPStats(XferControl xferControl) {
		int nystaStatisticInsertionData = 0;
    //for checking duplicate
		String queryForSearch = LoadJpaQueries.getQueryById("GET_T_QATP_STATISTICS");
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue("FILE_NAME", xferControl.getXferFileName());
		Iterator<QatpStatistics> qatpStatisticsList = namedJdbcTemplate
				.query(queryForSearch, paramSource, BeanPropertyRowMapper.newInstance(QatpStatistics.class)).stream()
				.iterator();

		List<String> fileTypeList = new ArrayList<String>();
		fileTypeList.add("ATPP");
		fileTypeList.add("ATPR");
		fileTypeList.add("ECTX");
		fileTypeList.add("VIOL");
		fileTypeList.add("UNMA");
		fileTypeList.add("REJC");
		fileTypeList.add("XTOL");
		String query = null;
		String sql = null;
		String sqlAtpId = null;
		if (!(qatpStatisticsList.hasNext())) {

			query = LoadJpaQueries.getQueryById("INSERT_T_QATP_STATISTICS");
			sql = LoadJpaQueries.getQueryById("GEN_SEQ_ATP_FILE_ID");

			for (String fileType : fileTypeList) {
				sqlAtpId = jdbcTemplate.queryForObject(sql, String.class);
				logger.info(sqlAtpId + " : atp file Id frofle type {}", fileType);

				logger.info("Taking file {}", xferControl.getXferFileName());
				LocalDateTime updateTS = timeZoneConv.currentTime();
				Long xferContrId = xferControl.getXferControlId();
				logger.info("Inserting data in T_QATP_STATISTICS table for xferContrId {}", xferContrId);

				// Inserting Data into TPMS.T_QATP_STATISTICS table
				nystaStatisticInsertionData = jdbcTemplate.update(query, sqlAtpId, fileType,
						xferControl.getXferFileName(), LocalDate.now(), timeZoneConv.currentTime().toLocalTime().toString(), QATPConstants.ZERO,
						QATPConstants.ZERO, QATPConstants.NO, LocalDate.now(), timeZoneConv.currentTime().toLocalTime().toString(),
						QATPConstants.ZERO, updateTS, xferContrId);

			}
		}
		return nystaStatisticInsertionData;
	}
	
	@Override
	public int updateStatisticsTable(long xferControlId,String fileName) 
	{
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue(QATPConstants.XFER_CONTROL_ID, xferControlId);
		paramSource.addValue(QATPConstants.FILE_NAME, fileName);
		
		String query = "UPDATE TPMS.T_QATP_STATISTICS set XFER_CONTROL_ID=:XFER_CONTROL_ID where FILE_NAME=:FILE_NAME";
		int update = namedJdbcTemplate.update(query, paramSource);
		
		if(update>0)
		{
			logger.info("Updated T_QATP_STATISTICS Table with Xfer Control Id {} for fileName {}",xferControlId,fileName);
			return update;
		}
		else
		{
			return 0;
		}
	}
}
