package com.conduent.tpms.qatp.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import com.conduent.tpms.qatp.config.LoadJpaQueries;
import com.conduent.tpms.qatp.constants.Constants;
import com.conduent.tpms.qatp.constants.FileStatusInd;
import com.conduent.tpms.qatp.constants.QATPConstants;
import com.conduent.tpms.qatp.dao.TQatpMappingDao;
import com.conduent.tpms.qatp.dto.FileInfoDto;
import com.conduent.tpms.qatp.dto.FileParserParameters;
import com.conduent.tpms.qatp.dto.MappingInfoDto;
import com.conduent.tpms.qatp.model.ReconciliationCheckPoint;
import com.conduent.tpms.qatp.model.TagDeviceDetails;
import com.conduent.tpms.qatp.model.XferControl;

@Repository
public class TQatpMappingDaoImpl implements TQatpMappingDao 
{

	private static final Logger dao_log = LoggerFactory.getLogger(TQatpMappingDaoImpl.class);

	@Autowired
	private NamedParameterJdbcTemplate namedJdbcTemplate;

	@Override
	public Long getAgencyId(String filePrefix) 
	{
		return null;
	}

	@Override
	public boolean checkIfFileProcessedAlready(String fileName)
	{
		boolean isFileExist = false;
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		String queryToCheckFile = LoadJpaQueries.getQueryById("CHECK_FILE_ALREADY_PROCESSED");

		paramSource.addValue(QATPConstants.FILE_NAME, FilenameUtils.getBaseName(fileName));
		paramSource.addValue(QATPConstants.XFER_XMIT_STATUS, QATPConstants.PROCESS_STATUS_COMPLETE);
		int count = namedJdbcTemplate.queryForObject(queryToCheckFile, paramSource, Integer.class);
		if (count > 0) {
			isFileExist = true;
		}
		return isFileExist;
	}

	@Override
	public Long insertReconciliationCheckPoint(ReconciliationCheckPoint reconciliationCheckPoint)
	{
		KeyHolder keyHolder = new GeneratedKeyHolder();
		String query = LoadJpaQueries.getQueryById("INSERT_INTO_FILE_CHECKPOINT");

		MapSqlParameterSource paramSource = null;

		paramSource = new MapSqlParameterSource();
		paramSource.addValue(QATPConstants.FILE_NAME,reconciliationCheckPoint.getFileName());
		paramSource.addValue(QATPConstants.PROCESS_DATE,reconciliationCheckPoint.getProcessDate());
		paramSource.addValue(QATPConstants.FILE_STATUS_IND,reconciliationCheckPoint.getFileStatusInd().getCode()); 
		paramSource.addValue(QATPConstants.LAST_LANE_TX_ID,reconciliationCheckPoint.getLastLaneTxTd()); 
		paramSource.addValue(QATPConstants.LAST_TX_TIMESTAMP,reconciliationCheckPoint.getLastTxTimeStatmp()); 
		paramSource.addValue(QATPConstants.LAST_EXTERN_LANE_ID,reconciliationCheckPoint.getLastExternLaneId()); 
		paramSource.addValue(QATPConstants.LAST_EXTERN_PLAZA_ID,reconciliationCheckPoint.getLastExternPlazaId()); 
		paramSource.addValue(QATPConstants.RECORD_COUNT,reconciliationCheckPoint.getRecordCount());	

		namedJdbcTemplate.update(query, paramSource, keyHolder, new String[] { QATPConstants.RECONCILIATION_ID});

		Map<String, Object> map = keyHolder.getKeys();
		Long id=null;

		if(null!=map) 
		{ 
			id = Long.parseLong(map.get(QATPConstants.RECONCILIATION_ID).toString()); 
		}
		return id;

	}

	@Override
	public Long getLastProcessedRecordCount(Long agencyId) {
		return null;
	}

	@Override
	public int insertTagDeviceDetails(TagDeviceDetails tagDetails) {
		return 0;
	}

	@Override
	public FileParserParameters getMappingConfigurationDetails(FileParserParameters fielDto) {
		FileParserParameters fileDetails = new FileParserParameters();
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		String queryFile = LoadJpaQueries.getQueryById("GET_FILE_INFO_CONFIGURATION");

		paramSource.addValue(Constants.FILE_TYPE, fielDto.getFileType());
		paramSource.addValue(Constants.AGENCY_ID, fielDto.getAgencyId());
		paramSource.addValue(Constants.FILE_FORMAT, Constants.FIXED);

		FileInfoDto info = namedJdbcTemplate.queryForObject(queryFile, paramSource,
				BeanPropertyRowMapper.newInstance(FileInfoDto.class));
		fileDetails.setFileInfoDto(info);

		String queryRules = LoadJpaQueries.getQueryById("GET_FIELD_VALIDATION_DETAILS");


		List<MappingInfoDto> nameMappingDetails = namedJdbcTemplate.query(queryRules, paramSource,
				BeanPropertyRowMapper.newInstance(MappingInfoDto.class));
		getMinMaxListValues(nameMappingDetails);
		List<MappingInfoDto> fileNameMappingDetails=nameMappingDetails.stream().filter(f-> f.getFieldType().equals("FILENAME")).collect(Collectors.toList());

		fileDetails.setFileNameMappingInfo(fileNameMappingDetails);
		fileDetails.setFileNameSize(fileNameMappingDetails.get(fileNameMappingDetails.size()-1).getEndPosition());


		List<MappingInfoDto> headerMappingDetails=nameMappingDetails.stream().filter(f-> f.getFieldType().equals("HEADER")).collect(Collectors.toList());

		fileDetails.setHeaderMappingInfoList(headerMappingDetails);
		
		fileDetails.setHeaderSize(headerMappingDetails.get(headerMappingDetails.size()-1).getEndPosition());


		List<MappingInfoDto> detailMappingDetails =nameMappingDetails.stream().filter(f-> f.getFieldType().equals("DETAIL")).collect(Collectors.toList());
		fileDetails.setDetailRecMappingInfo(detailMappingDetails);
		
		fileDetails.setDetailSize(detailMappingDetails.get(detailMappingDetails.size()-1).getEndPosition());

		List<MappingInfoDto> trailerMappingDetails =nameMappingDetails.stream().filter(f-> f.getFieldType().equals("TRAILER")).collect(Collectors.toList());
		fileDetails.setTrailerMappingInfoDto(trailerMappingDetails);
		
		fileDetails.setTrailerSize(trailerMappingDetails.get(trailerMappingDetails.size()-1).getEndPosition());

		return fileDetails;

	}

	private void getMinMaxListValues(List<MappingInfoDto> fileNameMappingDetails) {
		for (MappingInfoDto infoRow : fileNameMappingDetails) {
			if (Constants.FIXED_VALUE.equals(infoRow.getValidationType())
					|| Constants.DATE.equals(infoRow.getValidationType())) {
				infoRow.setFixeddValidValue(infoRow.getValidationValue());
			} else if (Constants.RANGE.equals(infoRow.getValidationType())) {
				String[] rangeValue = infoRow.getValidationValue().replaceAll("\\s", "").split("-");
				infoRow.setMinRangeValue(Long.valueOf(rangeValue[0]));// 00
				infoRow.setMaxRangeValue(Long.valueOf(rangeValue[1]));
			} else if (Constants.LOV.equals(infoRow.getValidationType())) {
				List<String> listOfValue = Arrays.asList(infoRow.getValidationValue().split(","));
				infoRow.setListOfValidValues(listOfValue);
			}
		}
	}

	@Override
	public XferControl checkFileDownloaded(String fileName)
	{
		try
		{
			MapSqlParameterSource paramSource = new MapSqlParameterSource();
			String queryToCheckFile = "SELECT XFER_CONTROL_ID, XFER_FILE_ID, XFER_FILE_NAME, DATE_CREATED, TIME_CREATED, FILE_SIZE, FILE_CHECKSUM, NUM_RECS, HASH_TOTAL, XFER_PATH, XFER_XMIT_STATUS, XFER_RETRY, UPDATE_TS, FILE_TYPE  FROM MASTER.T_XFER_CONTROL WHERE XFER_FILE_NAME='"+fileName+"' AND XFER_XMIT_STATUS ='C' order by XFER_CONTROL_ID desc";//LoadJpaQueries.getQueryById("CHECK_FILE_DOWNLOADED");
			//String queryToCheckFile = "SELECT XFER_CONTROL_ID, XFER_FILE_ID, XFER_FILE_NAME, DATE_CREATED, TIME_CREATED, FILE_SIZE, FILE_CHECKSUM, NUM_RECS, HASH_TOTAL, XFER_PATH, XFER_XMIT_STATUS, XFER_RETRY, UPDATE_TS, FILE_TYPE  FROM MASTER.T_XFER_CONTROL WHERE XFER_FILE_NAME='"+fileName+"' AND XFER_XMIT_STATUS ='C'";//LoadJpaQueries.getQueryById("CHECK_FILE_DOWNLOADED");
			//paramSource.addValue(QATPConstants.XFER_FILE_NAME, FilenameUtils.getBaseName(fileName));
			//paramSource.addValue(QATPConstants.XFER_XMIT_STATUS, QATPConstants.PROCESS_STATUS_COMPLETE);
			//XferControl obj = namedJdbcTemplate.queryForObject(queryToCheckFile, paramSource,BeanPropertyRowMapper.newInstance(XferControl.class));
			List<XferControl> listOfObj = namedJdbcTemplate.query(queryToCheckFile, paramSource,BeanPropertyRowMapper.newInstance(XferControl.class));
			if(listOfObj.size()>0) 
			{
				return listOfObj.get(0);
			}
			else
			{
				return null;
			}
			//return obj;
		}
		catch(EmptyResultDataAccessException ex)
		{
			dao_log.info("Exception: {}",ex.getMessage());
		}
		return null;
	}

	@Override
	public ReconciliationCheckPoint getReconsilationCheckPoint(String fileName) 
	{
		try
		{
			MapSqlParameterSource paramSource = new MapSqlParameterSource();
			String query = "SELECT *  FROM TPMS.T_RECONCILIATION_CHECKPOINT WHERE FILE_NAME='"+fileName+"'";
			//paramSource.addValue(QATPConstants.FILE_NAME, fileName);
			return namedJdbcTemplate.queryForObject(query, paramSource,new RowMapper<ReconciliationCheckPoint>()
			{
				@Override
				public ReconciliationCheckPoint mapRow(ResultSet rs, int rowNum) throws SQLException 
				{
					ReconciliationCheckPoint reconciliationCheckPoint=new ReconciliationCheckPoint();
					reconciliationCheckPoint.setFileName(rs.getString("FILE_NAME"));
					reconciliationCheckPoint.setFileStatusInd(FileStatusInd.getByCode(rs.getString("FILE_STATUS_IND")));
					reconciliationCheckPoint.setRecordCount(rs.getInt("RECORD_COUNT"));
					reconciliationCheckPoint.setProcessDate(rs.getDate("PROCESS_DATE"));
					reconciliationCheckPoint.setXferControlId(rs.getLong("XFER_CONTROL_ID"));
					return reconciliationCheckPoint;
				}

			});
		}
		catch(Exception ex)
		{
			return null;
		}
	}

	@Override
	public void updateRecordCount(ReconciliationCheckPoint reconciliationCheckPoint) 
	{
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		String query = LoadJpaQueries.getQueryById("UPDATE_RECORD_COUNT");
		paramSource.addValue(QATPConstants.FILE_NAME, reconciliationCheckPoint.getFileName());
		paramSource.addValue(QATPConstants.RECORD_COUNT, reconciliationCheckPoint.getRecordCount());
		paramSource.addValue(QATPConstants.FILE_STATUS_IND,reconciliationCheckPoint.getFileStatusInd().getCode()); 
		paramSource.addValue(QATPConstants.LAST_LANE_TX_ID,reconciliationCheckPoint.getLastLaneTxTd()); 
		paramSource.addValue(QATPConstants.LAST_TX_TIMESTAMP,reconciliationCheckPoint.getLastTxTimeStatmp()); 
		paramSource.addValue(QATPConstants.LAST_EXTERN_LANE_ID,reconciliationCheckPoint.getLastExternLaneId()); 
		paramSource.addValue(QATPConstants.LAST_EXTERN_PLAZA_ID,reconciliationCheckPoint.getLastExternPlazaId());
		paramSource.addValue(QATPConstants.XFER_CONTROL_ID, reconciliationCheckPoint.getXferControlId());
		namedJdbcTemplate.update(query, paramSource);
	}

	@Override
	public long getPrevXferControlId(String name) 
	{
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		String queryToGetFileName = "select XFER_CONTROL_ID from (select * from master.t_xfer_control where XFER_FILE_NAME ='"+name+"' order by xfer_control_id) where rownum < 2";
		long lastXferControlId;
		
		try 
		{
			lastXferControlId = namedJdbcTemplate.queryForObject(queryToGetFileName, paramSource, Long.class);
			if(lastXferControlId>0)
			{
				return lastXferControlId;
			}
			else
			{
				return 0;
			}
		} 
		catch (DataAccessException e) 
		{
			return 0;
		}
	}

}
