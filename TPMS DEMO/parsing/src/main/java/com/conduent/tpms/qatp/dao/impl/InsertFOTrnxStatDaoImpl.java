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
import com.conduent.tpms.qatp.constants.FOConstants;
import com.conduent.tpms.qatp.constants.INTXConstants;
import com.conduent.tpms.qatp.constants.QATPConstants;
import com.conduent.tpms.qatp.dao.InsertFOTrnxStatDao;
import com.conduent.tpms.qatp.dto.FOTrnxHeaderInfoVO;
import com.conduent.tpms.qatp.exception.InvalidFileHeaderException;
import com.conduent.tpms.qatp.model.QatpStatistics;
import com.conduent.tpms.qatp.model.ReconciliationCheckPoint;
import com.conduent.tpms.qatp.model.TFOFileStats;
import com.conduent.tpms.qatp.model.TFOReconFileStats;
import com.conduent.tpms.qatp.model.TranDetail;
import com.conduent.tpms.qatp.model.XferControl;
import com.conduent.tpms.qatp.utility.GenericValidation;

@Repository
public class InsertFOTrnxStatDaoImpl implements InsertFOTrnxStatDao {

	private static final Logger logger = LoggerFactory.getLogger(InsertFOTrnxStatDaoImpl.class);

	@Autowired
	private NamedParameterJdbcTemplate namedJdbcTemplate;

	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Autowired
	TimeZoneConv timeZoneConv;
	
	protected GenericValidation genericValidation;

	public XferControl getXferDataForStatistics(String fileName) {

		try {
			MapSqlParameterSource paramSource = new MapSqlParameterSource();
			String query = "SELECT *  FROM MASTER.T_XFER_CONTROL WHERE XFER_FILE_NAME='"
					+ fileName + "'";

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

	public void insertFNTXData(XferControl xferControl,String fileType1,long recordCount) {
		
		// duplicate checking from t_qatp_statistics table
		String queryForSearch1 = LoadJpaQueries.getQueryById("GET_T_QATP_STATISTICS");
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue("FILE_NAME", xferControl.getXferFileName());
		Iterator<QatpStatistics> qatpStatisticsList = namedJdbcTemplate
				.query(queryForSearch1, paramSource, BeanPropertyRowMapper.newInstance(QatpStatistics.class)).stream()
				.iterator();
		
		List<String> fileTypeList = new ArrayList<String>();
		fileTypeList.add("ATPP");
		fileTypeList.add("ATPR");
		fileTypeList.add("REJC");
		fileTypeList.add("INTX");
		String query = null;
		String sql = null;
		String sqlAtpId = null;
		if (!(qatpStatisticsList.hasNext())) {

			query = LoadJpaQueries.getQueryById("INSERT_T_QATP_STATISTICS");
			sql = LoadJpaQueries.getQueryById("GEN_SEQ_ATP_FILE_ID");

			for (String fileType : fileTypeList) {
				sqlAtpId = jdbcTemplate.queryForObject(sql, String.class);
				logger.debug(sqlAtpId + " : atp file Id frofle type {}", fileType);

				//logger.info("Taking file {}", xferControl.getXferFileName());
				//LocalDate updateTS = LocalDate.now();
				LocalDateTime updateTS = timeZoneConv.currentTime();
				Long xferContrId = xferControl.getXferControlId();
				logger.info("Inserting data in T_QATP_STATISTICS table for xferContrId {}", xferContrId);

				// Inserting Data into TPMS.T_QATP_STATISTICS table
				jdbcTemplate.update(query, sqlAtpId, fileType,
						xferControl.getXferFileName(), LocalDate.now(), LocalTime.now().toString(), QATPConstants.ZERO,
						QATPConstants.ZERO, QATPConstants.NO, LocalDate.now(), LocalTime.now().toString(),
						QATPConstants.ZERO, updateTS, xferContrId);

			}
		}
	}
	
	public void insertINTXData(XferControl xferControl,String fileType1,long recordCount) {
		// duplicate checking from t_qatp_statistics table
		String queryForSearch1 = LoadJpaQueries.getQueryById("GET_T_QATP_STATISTICS");
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue("FILE_NAME", xferControl.getXferFileName());
		Iterator<QatpStatistics> qatpStatisticsList = namedJdbcTemplate
				.query(queryForSearch1, paramSource, BeanPropertyRowMapper.newInstance(QatpStatistics.class)).stream()
				.iterator();
		
		List<String> fileTypeList = new ArrayList<String>();
		fileTypeList.add("ATPP");
		fileTypeList.add("ATPR");
		fileTypeList.add("REJC");
		String query = null;
		String sql = null;
		String sqlAtpId = null;
		if (!(qatpStatisticsList.hasNext())) {

			query = LoadJpaQueries.getQueryById("INSERT_T_QATP_STATISTICS");
			sql = LoadJpaQueries.getQueryById("GEN_SEQ_ATP_FILE_ID");

			for (String fileType : fileTypeList) {
				sqlAtpId = jdbcTemplate.queryForObject(sql, String.class);
				logger.debug(sqlAtpId + " : atp file Id frofle type {}", fileType);

				//logger.info("Taking file {}", xferControl.getXferFileName());
				//LocalDate updateTS = LocalDate.now();
				LocalDateTime updateTS = timeZoneConv.currentTime();
				Long xferContrId = xferControl.getXferControlId();
				logger.info("Inserting data in T_QATP_STATISTICS table for xferContrId {}", xferContrId);

				// Inserting Data into TPMS.T_QATP_STATISTICS table
				jdbcTemplate.update(query, sqlAtpId, fileType,
						xferControl.getXferFileName(), LocalDate.now(), LocalTime.now().toString(), QATPConstants.ZERO,
						QATPConstants.ZERO, QATPConstants.NO, LocalDate.now(), LocalTime.now().toString(),
						QATPConstants.ZERO, updateTS, xferContrId);

			}
		}
	}
	@Override
	public int updateReconStatisticsTable(long xferControlId,String fileName) 
	{
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue(FOConstants.FILE_CONTROL_ID, xferControlId);
		paramSource.addValue(FOConstants.TRX_EPS_FILENAME, fileName);
		
		String query = "UPDATE TPMS.T_FO_RECON_FILE_STATS set FILE_CONTROL_ID=:FILE_CONTROL_ID where TRX_EPS_FILENAME=:TRX_EPS_FILENAME";
		int update = namedJdbcTemplate.update(query, paramSource);
		
		if(update>0)
		{
			logger.info("Updated TPMS.T_FO_RECON_FILE_STATS Table with File Control Id {} for fileName {}",xferControlId,fileName);
			return update;
		}
		else
		{
			return 0;
		}
	}
	
	@Override
	public int updateStatisticsTable(long xferControlId,String fileName) 
	{
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue(FOConstants.FILE_CONTROL_ID, xferControlId);
		paramSource.addValue(FOConstants.TRX_FILE_NAME, fileName);
		
		String query = "UPDATE TPMS.T_FO_FILE_STATS set FILE_CONTROL_ID=:FILE_CONTROL_ID where TRX_FILE_NAME=:TRX_FILE_NAME";
		int update = namedJdbcTemplate.update(query, paramSource);
		
		if(update>0)
		{
			logger.info("Updated TPMS.T_FO_FILE_STATS Table with File Control Id {} for fileName {}",xferControlId,fileName);
			return update;
		}
		else
		{
			return 0;
		}
	}
	
	@Override
	public int updateQatpStatisticsTable(long xferControlId,String fileName) 
	{
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue(FOConstants.XFER_CONTROL_ID, xferControlId);
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

	@Override
	public int updateFOFileStats(long xferControlId, String fileName,ReconciliationCheckPoint reconciliationCheckPoint,GenericValidation genericValidation) throws InvalidFileHeaderException 
	{
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		//LocalDate date1 = genericValidation.getFormattedDate(value, fMapping.getFixeddValidValue());
		paramSource.addValue(FOConstants.FROM_AGENCY, fileName.substring(0,3));
		paramSource.addValue(FOConstants.FILE_DATE, genericValidation.getFormattedDate(fileName.substring(8,16),"yyyyMMdd"));
		paramSource.addValue(FOConstants.FILE_SEQ_NUM, reconciliationCheckPoint.getFileSeqNum());
		paramSource.addValue(FOConstants.FILE_CONTROL_ID, xferControlId);
		paramSource.addValue(FOConstants.TRX_FILE_NAME, fileName);
		
		String query = "UPDATE TPMS.T_FO_FILE_STATS set \r\n"
				+ "FROM_AGENCY=:FROM_AGENCY , \r\n"
				+ "FILE_DATE=:FILE_DATE , \r\n"
				+ "FILE_SEQ_NUM=:FILE_SEQ_NUM \r\n"
				+ "where TRX_FILE_NAME=:TRX_FILE_NAME and FILE_CONTROL_ID=:FILE_CONTROL_ID";
		int update = namedJdbcTemplate.update(query, paramSource);
		
		if(update>0)
		{
			logger.info("Updated TPMS.T_FO_FILE_STATS Table with File Control Id {} for fileName {}",xferControlId,fileName);
			return update;
		}
		else
		{
			logger.info("Updation Failed");
			return 0;
		}
	}
	
	@Override
	public int updateFOReconFileStats(long xferControlId, String fileName,ReconciliationCheckPoint reconciliationCheckPoint) 
	{
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		
		paramSource.addValue("EXT_FILE_ID", xferControlId);
		String result = "select sum(TOLL_AMOUNT) from TPMS.T_TRAN_DETAIL where EXT_FILE_ID=:EXT_FILE_ID";
		Double amount = namedJdbcTemplate.queryForObject(result, paramSource, Double.class);
		
		paramSource.addValue(FOConstants.FROM_AGENCY, fileName.substring(0,3));
		paramSource.addValue(FOConstants.FILE_SEQ_NUMBER, reconciliationCheckPoint.getFileSeqNum());
		paramSource.addValue(FOConstants.TRX_EPS_AMOUNT, amount);
		paramSource.addValue(FOConstants.FILE_CONTROL_ID, xferControlId);
		paramSource.addValue(FOConstants.TRX_EPS_FILENAME, fileName);
		
		String query = "UPDATE TPMS.T_FO_RECON_FILE_STATS set \r\n"
				+ "FROM_AGENCY=:FROM_AGENCY , \r\n"
				+ "TRX_EPS_AMOUNT=:TRX_EPS_AMOUNT , \r\n"
				+ "FILE_SEQ_NUMBER=:FILE_SEQ_NUMBER \r\n"
				+ "where TRX_EPS_FILENAME=:TRX_EPS_FILENAME and FILE_CONTROL_ID=:FILE_CONTROL_ID";
		int update = namedJdbcTemplate.update(query, paramSource);
		
		if(update>0)
		{
			logger.info("Updated TPMS.T_FO_RECON_FILE_STATS Table with File Control Id {} for fileName {}",xferControlId,fileName);
			return update;
		}
		else
		{
			logger.info("Updation Failed");
			return 0;
		}
	}

	public void insertInFileStat(String fileType, long recordCount, XferControl xferControl) 
	{

		if(fileType.equals(FOConstants.FNTX))
		{
			String queryForSearch = LoadJpaQueries.getQueryById("GET_T_FO_FILE_STATS");
			MapSqlParameterSource paramSource1 = new MapSqlParameterSource();
			paramSource1.addValue("TRX_FILE_NAME", xferControl.getXferFileName());
			Iterator<TFOFileStats> qatpStatisticsList1 = namedJdbcTemplate
					.query(queryForSearch, paramSource1, BeanPropertyRowMapper.newInstance(TFOFileStats.class)).stream()
					.iterator();
			
			String query1 = null;
			if (!(qatpStatisticsList1.hasNext())) {

				query1 = LoadJpaQueries.getQueryById("INSERT_T_FO_FILE_STATS");
					LocalDateTime updateTS = timeZoneConv.currentTime();
					Long xferContrId = xferControl.getXferControlId();
					String fileName = xferControl.getXferFileName();
					logger.info("Inserting data in T_FO_FILE_STATS table for fileContrId {}", xferContrId);

					// Inserting Data into TPMS.T_QATP_STATISTICS table
					jdbcTemplate.update(query1,xferContrId,fileName,null,
							QATPConstants.NO,recordCount,QATPConstants.ZERO,QATPConstants.ZERO,
							FOConstants.FNTX_TO_AGENCY,null,null,FOConstants.FNTX,updateTS);
			}
		}
		else if (fileType.equals(INTXConstants.INTX))
		{
			String queryForSearch = LoadJpaQueries.getQueryById("GET_T_FO_RECON_FILE_STATS");
			MapSqlParameterSource paramSource1 = new MapSqlParameterSource();
			paramSource1.addValue("TRX_EPS_FILENAME", xferControl.getXferFileName());
			Iterator<TFOReconFileStats> qatpStatisticsList1 = namedJdbcTemplate
					.query(queryForSearch, paramSource1, BeanPropertyRowMapper.newInstance(TFOReconFileStats.class)).stream()
					.iterator();
			
			String query1 = null;
			if (!(qatpStatisticsList1.hasNext())) {
	
				query1 = LoadJpaQueries.getQueryById("INSERT_T_FO_RECON_FILE_STATS");
					LocalDateTime updateTS = timeZoneConv.currentTime();
					Long xferContrId = xferControl.getXferControlId();
					String fileName = xferControl.getXferFileName();
					logger.info("Inserting data in T_FO_RECON_FILE_STATS table for fileContrId {}", xferContrId);
	
					// Inserting Data into TPMS.T_QATP_STATISTICS table
					 jdbcTemplate.update(query1,xferContrId,fileName,
							recordCount,QATPConstants.ZERO,QATPConstants.NO,null,null,null,null,
							QATPConstants.ZERO,FOConstants.FNTX_TO_AGENCY,null,updateTS);
			}
		}
		
	
		
	}

}
