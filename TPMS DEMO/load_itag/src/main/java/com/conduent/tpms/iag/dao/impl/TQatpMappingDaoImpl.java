package com.conduent.tpms.iag.dao.impl;

import java.sql.Date;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.conduent.app.timezone.utility.TimeZoneConv;
import com.conduent.tpms.iag.config.LoadJpaQueries;
import com.conduent.tpms.iag.constants.FileProcessStatus;
import com.conduent.tpms.iag.constants.ItagConstants;
import com.conduent.tpms.iag.dao.TQatpMappingDao;
import com.conduent.tpms.iag.dto.AckFileInfoDto;
import com.conduent.tpms.iag.dto.FileInfoDto;
import com.conduent.tpms.iag.dto.FileParserParameters;
import com.conduent.tpms.iag.dto.ITAGDetailInfoVO;
import com.conduent.tpms.iag.dto.MappingInfoDto;
import com.conduent.tpms.iag.model.AgencyInfoVO;
import com.conduent.tpms.iag.model.FileDetails;
import com.conduent.tpms.iag.model.TagDeviceDetails;
import com.conduent.tpms.iag.model.XferControl;


@Repository
public class TQatpMappingDaoImpl implements TQatpMappingDao {

	private static final Logger dao_log = LoggerFactory.getLogger(TQatpMappingDaoImpl.class);

	@Autowired
	private NamedParameterJdbcTemplate namedJdbcTemplate;

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	TimeZoneConv timeZoneConv;

	@Override
	public Long getAgencyId(String filePrefix) {
		return null;
	}

	@Override
	public FileDetails checkIfFileProcessedAlready(String fileName) {

		try
		{
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		String queryToCheckFile = "SELECT *  FROM T_LANE_TX_CHECKPOINT WHERE FILE_NAME='"+fileName+"'";
		
		return namedJdbcTemplate.queryForObject(queryToCheckFile, paramSource,new RowMapper<FileDetails>()
		{
			@Override
			public FileDetails mapRow(ResultSet rs, int rowNum) throws SQLException 
			{
				FileDetails fileDetails=new FileDetails();
				fileDetails.setFileName(rs.getString("FILE_NAME"));
				fileDetails.setFileType(rs.getString("FILE_TYPE"));
				fileDetails.setProcessName(rs.getString("PROCESS_NAME"));
				fileDetails.setProcessId(rs.getInt("PROCESS_ID"));
				fileDetails.setProcessStatus(FileProcessStatus.getByCode(rs.getString("PROCESS_STATUS")));
				fileDetails.setLaneTxId(rs.getInt("LANE_TX_ID"));
				fileDetails.setSerialNumber(rs.getInt("SERIAL_NUMBER"));
				fileDetails.setFileCount(rs.getInt("FILE_COUNT"));
				
				fileDetails.setProcessedCount(rs.getInt("PROCESSED_COUNT"));
				fileDetails.setSuccessCount(rs.getInt("SUCCESS_COUNT"));
				fileDetails.setExceptionCount(rs.getInt("EXCEPTION_COUNT"));
				fileDetails.setUpdateTs(rs.getTimestamp("UPDATE_TS").toLocalDateTime());
				return fileDetails;
			}
			
		});
		}
		catch(Exception ex)
		{
			return null;
		}
	}


	@Override
	public Long insertFileDetails(FileDetails fileDetails) {

		dao_log.debug("ITAG file {} details to insert into checkpoint table..", fileDetails.toString());

		dao_log.info("Inserting ITAG file {} details into checkpoint table..", fileDetails.getFileName());

		KeyHolder keyHolder = new GeneratedKeyHolder();
		String query = LoadJpaQueries.getQueryById("INSERT_INTO_FILE_CHECKPOINT");

		MapSqlParameterSource paramSource = null;

		paramSource = new MapSqlParameterSource();
		paramSource.addValue(ItagConstants.FILE_NAME, fileDetails.getFileName());
		paramSource.addValue(ItagConstants.FILE_TYPE, fileDetails.getFileType());
		paramSource.addValue(ItagConstants.PROCESS_NAME, fileDetails.getProcessName());
		paramSource.addValue(ItagConstants.PROCESS_ID, fileDetails.getProcessId());
		paramSource.addValue(ItagConstants.PROCESS_STATUS, fileDetails.getProcessStatus().getCode());
		paramSource.addValue(ItagConstants.TX_DATE, fileDetails.getTxDate());
		paramSource.addValue(ItagConstants.LANE_TX_ID, fileDetails.getLaneTxId());
		paramSource.addValue(ItagConstants.SERIAL_NUMBER, fileDetails.getSerialNumber());
		paramSource.addValue(ItagConstants.FILE_COUNT, fileDetails.getFileCount());
		paramSource.addValue(ItagConstants.PROCESSED_COUNT, fileDetails.getProcessedCount());
		paramSource.addValue(ItagConstants.SUCCESS_COUNT, fileDetails.getSuccessCount());
		paramSource.addValue(ItagConstants.EXCEPTION_COUNT, fileDetails.getExceptionCount());
		paramSource.addValue(ItagConstants.UPDATE_TS,Timestamp.valueOf(fileDetails.getUpdateTs()));

		namedJdbcTemplate.update(query, paramSource, keyHolder, new String[] { ItagConstants.ID });
		Map<String, Object> map = keyHolder.getKeys();
		Long id = null;
		if (null != map) {
			id = Long.parseLong(map.get(ItagConstants.ID).toString());
		}
		dao_log.info("File {} details inserted into checkpoint with status code {}.", fileDetails.getFileName(),fileDetails.getProcessStatus().getCode());

		return id;
	}

	@Override
	public Long getLastProcessedRecordCount(Long agencyId) {
		return null;
	}
	
	@Override
	public int[] insertTagDeviceDetailsforHome(List<TagDeviceDetails> tagDeviceDetailsList) {
		dao_log.info("Inserting Tag device details for Home ..");

		int[] updatedRecords = batchInsertforHome(tagDeviceDetailsList);
		return updatedRecords;
	}
	
	public int[] batchInsertforHome(List<TagDeviceDetails> list) {

		dao_log.debug("Starting batch insert for list of tag devices {}", list.toString());

		String insertQuery = LoadJpaQueries.getQueryById("INSERT_INTO_T_HA_DEVICES");
		int[] updateCounts = jdbcTemplate.batchUpdate(insertQuery, new BatchPreparedStatementSetter() {
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				ps.setString(1, list.get(i).getDeviceNo());
				ps.setString(2,list.get(i).getTagHomeAgency());
				ps.setString(3, list.get(i).getFileAgencyId());
				ps.setString(4,list.get(i).getTagAccountNo());
				ps.setString(5,list.get(i).getTagACTypeIND());
				ps.setString(6,list.get(i).getTagProtocol());
				ps.setString(7,list.get(i).getTagType());
				ps.setString(8,list.get(i).getTagMount());
				ps.setString(9,list.get(i).getTagClass());
				ps.setDate(10, Date.valueOf(list.get(i).getStartDate()));
				ps.setDate(11, Date.valueOf(list.get(i).getEndDate()));
				ps.setString(12,list.get(i).getIAGTagStatus());
				ps.setString(13, list.get(i).getInfileInd());
				ps.setLong(14,list.get(i).getXferControlID());
				ps.setTimestamp(15, Timestamp.valueOf(list.get(i).getLastFileTS()));
				ps.setTimestamp(16, Timestamp.valueOf(list.get(i).getUpdateDeviceTs()));
				ps.setString(17,list.get(i).getRecord());
			}

			public int getBatchSize() {
				return list.size();
			}
		});
		dao_log.debug("No. of tag devices inserted in batch {}", updateCounts);
		return updateCounts;
	}

	@Override
	public int[] insertTagDeviceDetailsforAway(List<TagDeviceDetails> tagDeviceDetailsList) {

		dao_log.info("Inserting Tag device details..");

		int[] updatedRecords = batchInsertforAway(tagDeviceDetailsList);
		return updatedRecords;
	}

	public int[] batchInsertforAway(List<TagDeviceDetails> list) {

		dao_log.debug("Starting batch insert for list of tag devices {}", list.toString());

		String insertQuery = LoadJpaQueries.getQueryById("INSERT_INTO_T_OA_DEVICES");
		int[] updateCounts = jdbcTemplate.batchUpdate(insertQuery, new BatchPreparedStatementSetter() {
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				ps.setString(1, list.get(i).getDeviceNo());
				ps.setString(2,list.get(i).getTagHomeAgency());
				ps.setString(3, list.get(i).getFileAgencyId());
				ps.setString(4,list.get(i).getTagAccountNo());
				ps.setString(5,list.get(i).getTagACTypeIND());
				ps.setString(6,list.get(i).getTagProtocol());
				ps.setString(7,list.get(i).getTagType());
				ps.setString(8,list.get(i).getTagMount());
				ps.setString(9,list.get(i).getTagClass());
				ps.setDate(10, Date.valueOf(list.get(i).getStartDate()));
				ps.setDate(11, Date.valueOf(list.get(i).getEndDate()));
				ps.setString(12,list.get(i).getIAGTagStatus());
				ps.setString(13, list.get(i).getInfileInd());
				//ps.setInt(14, list.get(i).getInvalidAddressId());
				ps.setLong(14,list.get(i).getXferControlID());
				ps.setTimestamp(15, Timestamp.valueOf(list.get(i).getLastFileTS()));
				ps.setTimestamp(16, Timestamp.valueOf(list.get(i).getUpdateDeviceTs()));
				ps.setString(17,list.get(i).getRecord());
				
				
			}

			public int getBatchSize() {
				return list.size();
			}
		});
		dao_log.debug("No. of tag devices inserted in batch {}", updateCounts);
		return updateCounts;
	}
	
	@Override
	public FileParserParameters getMappingConfigurationDetails(String fileType) {

		dao_log.info("Getting mapping configuration from table..");

		FileParserParameters fileDetails = new FileParserParameters();
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		String queryFile = LoadJpaQueries.getQueryById("GET_FILE_INFO_CONFIGURATION");
		
		
		paramSource.addValue(ItagConstants.FILE_TYPE, fileType);
		paramSource.addValue(ItagConstants.AGENCY_ID, ItagConstants.MTA_AGENCY_ID); 
		paramSource.addValue(ItagConstants.FILE_FORMAT, ItagConstants.FIXED);

		FileInfoDto info = namedJdbcTemplate.queryForObject(queryFile, paramSource,
				BeanPropertyRowMapper.newInstance(FileInfoDto.class));
		fileDetails.setFileInfoDto(info);

		String queryRules = LoadJpaQueries.getQueryById("GET_FIELD_VALIDATION_DETAILS");

		List<MappingInfoDto> nameMappingDetails = namedJdbcTemplate.query(queryRules, paramSource,
				BeanPropertyRowMapper.newInstance(MappingInfoDto.class));
		getMinMaxListValues(nameMappingDetails);

		List<MappingInfoDto> fileNameMappingDetails = nameMappingDetails.stream()
				.filter(f -> f.getFieldType().equals("FILENAME")).collect(Collectors.toList());

		fileDetails.setFileNameMappingInfo(fileNameMappingDetails);

		List<MappingInfoDto> headerMappingDetails = nameMappingDetails.stream()
				.filter(f -> f.getFieldType().equals("HEADER")).collect(Collectors.toList());

		fileDetails.setHeaderMappingInfoList(headerMappingDetails);

		List<MappingInfoDto> detailMappingDetails = nameMappingDetails.stream()
				.filter(f -> f.getFieldType().equals("DETAIL")).collect(Collectors.toList());
		fileDetails.setDetailRecMappingInfo(detailMappingDetails);

		dao_log.info("Collected mapping configuration from table..");
		return fileDetails;

	}

	private void getMinMaxListValues(List<MappingInfoDto> fileNameMappingDetails) {
		for (MappingInfoDto infoRow : fileNameMappingDetails) {
			if (ItagConstants.FIXED_VALUE.equals(infoRow.getValidationType())
					|| ItagConstants.DATE.equals(infoRow.getValidationType())) {
				infoRow.setFixeddValidValue(infoRow.getValidationValue());
			} else if (ItagConstants.RANGE.equals(infoRow.getValidationType())) {
				String[] rangeValue = infoRow.getValidationValue().replaceAll("\\s", "").split("-");
				infoRow.setMinRangeValue(Long.valueOf(rangeValue[0]));
				infoRow.setMaxRangeValue(Long.valueOf(rangeValue[1]));
			} else if (ItagConstants.LOV.equals(infoRow.getValidationType())) {
				List<String> listOfValue = Arrays.asList(infoRow.getValidationValue().split(","));
				infoRow.setListOfValidValues(listOfValue);
			}
		}
	}

	@Override
	public XferControl checkFileDownloaded(String fileName) {
		MapSqlParameterSource paramSource = new MapSqlParameterSource();

		try {
			String queryToCheckFile = "SELECT XFER_CONTROL_ID, XFER_FILE_ID, XFER_FILE_NAME, DATE_CREATED, TIME_CREATED, FILE_SIZE, FILE_CHECKSUM, NUM_RECS, HASH_TOTAL, XFER_PATH, XFER_XMIT_STATUS, XFER_RETRY, UPDATE_TS, FILE_TYPE  FROM MASTER.T_XFER_CONTROL WHERE XFER_FILE_NAME='"
					+ fileName + "' AND XFER_XMIT_STATUS ='C'";
			dao_log.info("queryToCheckFile: {}",queryToCheckFile);
			List<XferControl> listOfObj = namedJdbcTemplate.query(queryToCheckFile, paramSource,
					BeanPropertyRowMapper.newInstance(XferControl.class));
			if (listOfObj.size() > 0) {
				return listOfObj.get(0);
			} 
		} catch (Exception e) {
			dao_log.info("Exception while fetching xfer_control table..");
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String getLastSuccesfulProcessedFile(String fromAgencyId2) {
		fromAgencyId2 = fromAgencyId2 + "%";
		
		String queryToGetFileName = "SELECT FILE_NAME FROM  (SELECT * from TPMS.T_LANE_TX_CHECKPOINT WHERE FILE_TYPE='ITAG' AND PROCESS_STATUS='C' AND  FILE_NAME LIKE '"
				+ fromAgencyId2 + "' ORDER BY  TX_DATE DESC)  WHERE ROWNUM < 2";
		
		Stream<String> stream = jdbcTemplate.queryForStream(queryToGetFileName, (rs,
				rowNum) -> new String(rs.getString(1)));
		List<String> list = stream.collect(Collectors.toList());
		
		if (list.isEmpty()) {
			return "";
			
		}else {
			return list.get(0);
		}
	}

	@Override
	public void updateFileIntoCheckpoint(FileDetails fileDetails) {

		dao_log.info("Updating ITAG file {} details into checkpoint table with process status: {}",
				fileDetails.getFileName(), fileDetails.getProcessStatus());

		KeyHolder keyHolder = new GeneratedKeyHolder();
		String query = LoadJpaQueries.getQueryById("UPDATE_FILE_STATUS_INTO_CHECKPOINT");

		MapSqlParameterSource paramSource = new MapSqlParameterSource();

		paramSource.addValue(ItagConstants.FILE_NAME, fileDetails.getFileName());
		paramSource.addValue(ItagConstants.FILE_TYPE, fileDetails.getFileType());
		paramSource.addValue(ItagConstants.PROCESS_STATUS, fileDetails.getProcessStatus().getCode());
		paramSource.addValue(ItagConstants.FILE_COUNT, fileDetails.getFileCount());
		paramSource.addValue(ItagConstants.UPDATE_TS, Timestamp.valueOf(fileDetails.getUpdateTs()));
		//paramSource.addValue(ItagConstants.UPDATE_TS, timeZoneConv.currentTime());
		paramSource.addValue(ItagConstants.PROCESSED_COUNT, fileDetails.getProcessedCount());
		paramSource.addValue(ItagConstants.SUCCESS_COUNT, fileDetails.getSuccessCount());
		paramSource.addValue(ItagConstants.EXCEPTION_COUNT, fileDetails.getExceptionCount());
		

		namedJdbcTemplate.update(query, paramSource, keyHolder, new String[] { ItagConstants.ID });
		Map<String, Object> map = keyHolder.getKeys();
		Long id = null;
		if (null != map) {
			id = Long.parseLong(map.get(ItagConstants.ID).toString());
		}
		dao_log.info("File checkpoint Details Inserted with Id= {}", id);

	}

	@Override
	public List<AgencyInfoVO> getAgencyDetails() {
		
		String queryRules =	LoadJpaQueries.getQueryById("SELECT_INFO_FROM_T_AGENCY");
		
		dao_log.info("Agency info fetched from T_Agency table successfully.");
		
		List<AgencyInfoVO> list= jdbcTemplate.query(queryRules, new BeanPropertyRowMapper<AgencyInfoVO>(AgencyInfoVO.class));
return list;
	}

	@Override
	public void insertAckDetails(AckFileInfoDto ackFileInfoDto) {

		dao_log.info("Inserting Ack file details into ack table..");

		String query = LoadJpaQueries.getQueryById("INSERT_FILE_ACK_INTO_ACK_TABLE");
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue(ItagConstants.ACK_FILE_NAME, ackFileInfoDto.getAckFileName());
		paramSource.addValue(ItagConstants.ACK_FILE_DATE, ackFileInfoDto.getAckFileDate());
		paramSource.addValue(ItagConstants.ACK_FILE_TIME, ackFileInfoDto.getAckFileTime());
		paramSource.addValue(ItagConstants.ACK_FILE_STATUS, Integer.parseInt(ackFileInfoDto.getAckFileStatus()));
		paramSource.addValue(ItagConstants.TRX_FILE_NAME, ackFileInfoDto.getTrxFileName());
		paramSource.addValue(ItagConstants.RECON_FILE_NAME, ackFileInfoDto.getReconFileName());
		paramSource.addValue(ItagConstants.FILE_TYPE, ackFileInfoDto.getFileType());
		paramSource.addValue(ItagConstants.FROM_AGENCY, ackFileInfoDto.getFromAgency());
		paramSource.addValue(ItagConstants.TO_AGENCY, ackFileInfoDto.getToAgency());
		paramSource.addValue(ItagConstants.EXTERN_FILE_ID, ackFileInfoDto.getExternFileId());
		paramSource.addValue(ItagConstants.ATP_FILE_ID, ackFileInfoDto.getAtpFileId());
		int id = namedJdbcTemplate.update(query, paramSource);

		dao_log.info("Inserted Ack file {} details into ack table", ackFileInfoDto.getAckFileName());

	}

	/*
	 * public List<ITAGDetailInfoVO> getDiffRecordsFromExtTables() {
	 * 
	 * String query = LoadJpaQueries.getQueryById("GET_DIFFERENCE_OF_RECORDS");
	 * 
	 * 
	 * Stream<ITAGDetailInfoVO> stream = jdbcTemplate.queryForStream(query, (rs,
	 * rowNum) -> new ITAGDetailInfoVO(rs.getString(1), rs.getString(2),
	 * rs.getString(3), rs.getString(4),rs.getString(5)));
	 * 
	 * List<ITAGDetailInfoVO> list = stream.collect(Collectors.toList());
	 * dao_log.info(list.toString());
	 * 
	 * return list; }
	 */
	
	public List<String> getDataFromNewExternTable(String newBucket) {
		String query = "SELECT CONCAT(DEVICE_PREFIX,DEVICE_SERIAL) AS deviceNo FROM "+newBucket;
		
		List<String> deviceNoNewList = jdbcTemplate.query(query, new RowMapper<String>(){
            public String mapRow(ResultSet rs, int rowNum) throws SQLException 
            {
                    return rs.getString(1);
            }});
		dao_log.info("Device list from {} table: {}",newBucket, deviceNoNewList.toString());
		return deviceNoNewList;
	}

	@Override
	public void updateTagDeviceDetails(TagDeviceDetails itagDetail) {
		
		jdbcTemplate.update(
                "update TPMS.T_OA_DEVICES set END_DATE = ?,UPDATE_TS =?,LAST_FILE_TS=?, IAG_TAG_STATUS=? , TAG_HOME_AGENCY =? , TAG_AC_TYPE_IND =? ,  TAG_ACCOUNT_NO =? ,TAG_PROTOCOL=? , TAG_TYPE =? , TAG_MOUNT =?, TAG_CLASS =? where DEVICE_NO = ?", 
                itagDetail.getEndDate(), itagDetail.getUpdateDeviceTs(),itagDetail.getLastFileTS(),
                Integer.valueOf(itagDetail.getIAGTagStatus()),itagDetail.getTagHomeAgency(),itagDetail.getTagACTypeIND(),itagDetail.getTagAccountNo(),itagDetail.getTagProtocol(),itagDetail.getTagType(),itagDetail.getTagMount(),itagDetail.getTagClass(),itagDetail.getDeviceNo());
		
		dao_log.info("Updated Tag device details for tag {}",itagDetail.getDeviceNo());

	}
	
	@Override
	public void updateTagDeviceDetailsforHome(TagDeviceDetails itagDetail) {
		
		jdbcTemplate.update(
                "update TPMS.T_HA_DEVICES set END_DATE = ? where DEVICE_NO = ?", 
                itagDetail.getStartDate()
                ,itagDetail.getDeviceNo());
		
		dao_log.info("Updated Tag device details for tag {}",itagDetail.getDeviceNo());

	}

	@Override
	public List<String> getDataFromOldExternTable(String oldBucket) {
		String query = "SELECT CONCAT(DEVICE_PREFIX,DEVICE_SERIAL) AS deviceNo FROM "+oldBucket;
		
		List<String> deviceNoOldList = jdbcTemplate.query(query, new RowMapper<String>(){
            public String mapRow(ResultSet rs, int rowNum) throws SQLException 
            {
                    return rs.getString(1);
            }});
		dao_log.info("Device list from {} table: {}",oldBucket, deviceNoOldList.toString());
		return deviceNoOldList;
	}


	@Override
	public LocalDate getIfDeviceExistForFuture(String deviceNo) {
		
		LocalDate enddate = null;
		
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		
		String queryToCheckFile = "select end_date from TPMS.T_OA_DEVICES where DEVICE_NO='"+deviceNo+"' order by start_date DESC";

		List<TagDeviceDetails> listOfObj = namedJdbcTemplate.query(queryToCheckFile, paramSource,BeanPropertyRowMapper.newInstance(TagDeviceDetails.class));
		
		if(listOfObj.size()>0) {
			if(listOfObj.get(0).getEndDate().getYear() < 1900) {
			enddate = listOfObj.get(0).getEndDate().plusYears(100);
			}else {
				enddate = listOfObj.get(0).getEndDate();
			}
		}
		return enddate;
	}
	
	@Override
	public LocalDate getIfDeviceExistForFutureforHome(String deviceNo) {
		
		LocalDate enddate = null;
		
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		
		String queryToCheckFile = "select end_date from TPMS.T_HA_DEVICES where DEVICE_NO='"+deviceNo+"' order by start_date DESC";

		List<TagDeviceDetails> listOfObj = namedJdbcTemplate.query(queryToCheckFile, paramSource,BeanPropertyRowMapper.newInstance(TagDeviceDetails.class));
		
		if(listOfObj.size()>0) {
			if(listOfObj.get(0).getEndDate().getYear() < 1900) {
			enddate = listOfObj.get(0).getEndDate().plusYears(100);
			}else {
				enddate = listOfObj.get(0).getEndDate();
			}
		}
		return enddate;
	}

	
	
    //old update 
	/*
	 * @Override public void updateDuplicateTagDeviceDetails(TagDeviceDetails
	 * itagDetail, LocalDate prevEndDate) {
	 * 
	 * jdbcTemplate.update(
	 * "update TPMS.T_OA_DEVICES set END_DATE = ?,UPDATE_TS =?,LAST_FILE_TS=?, IAG_TAG_STATUS=? , TAG_HOME_AGENCY =? , TAG_AC_TYPE_IND =? ,  TAG_ACCOUNT_NO =? ,TAG_PROTOCOL=? , TAG_TYPE =? , TAG_MOUNT =?, TAG_CLASS =?     where DEVICE_NO = ? AND END_DATE = ?"
	 * , LocalDate.now().minusDays(1),
	 * itagDetail.getUpdateDeviceTs(),itagDetail.getLastFileTS(),
	 * Integer.valueOf(itagDetail.getIAGTagStatus()),itagDetail.getTagHomeAgency(),
	 * itagDetail.getTagACTypeIND(),itagDetail.getTagAccountNo(),itagDetail.
	 * getTagProtocol(),itagDetail.getTagType(),itagDetail.getTagMount(),itagDetail.
	 * getTagClass(),itagDetail.getDeviceNo(),prevEndDate);
	 * 
	 * dao_log.info("Updated Tag device details for tag {}",itagDetail.getDeviceNo()
	 * ); }
	 */
	
	//LocalDate.now().minusDays(1)
	
	@Override
	public void updateDuplicateTagDeviceDetails(TagDeviceDetails itagDetail, LocalDate prevEndDate) {
		
		jdbcTemplate.update(
                "update TPMS.T_OA_DEVICES set END_DATE = ?       where DEVICE_NO = ? AND END_DATE = ?", 
                itagDetail.getStartDate().minusDays(1), 
                itagDetail.getDeviceNo(),prevEndDate);
		
		dao_log.info("Updated Tag device details for tag {}",itagDetail.getDeviceNo());
	}
	
	@Override
	public void updateDuplicateTagDeviceDateForOld(TagDeviceDetails itagDetail, LocalDate prevEndDate) {
		
		jdbcTemplate.update(
                "update TPMS.T_OA_DEVICES set END_DATE = ?         where DEVICE_NO = ? AND END_DATE = ?", 
                itagDetail.getStartDate(), 
                itagDetail.getDeviceNo(),prevEndDate);
		
		dao_log.info("Updated Tag device details for tag {}",itagDetail.getDeviceNo());
	}
	
	
	//old update
	/*
	 * @Override public void updateDuplicateTagDeviceDetailsforHome(TagDeviceDetails
	 * itagDetail, LocalDate prevEndDate) {
	 * 
	 * jdbcTemplate.update(
	 * "update TPMS.T_HA_DEVICES set END_DATE = ?,UPDATE_TS =?,LAST_FILE_TS=?, IAG_TAG_STATUS=? , TAG_HOME_AGENCY =? , TAG_AC_TYPE_IND =? ,  TAG_ACCOUNT_NO =? ,TAG_PROTOCOL=? , TAG_TYPE =? , TAG_MOUNT =?, TAG_CLASS =?  where DEVICE_NO = ? AND END_DATE = ?"
	 * , itagDetail.getEndDate(),
	 * itagDetail.getUpdateDeviceTs(),itagDetail.getLastFileTS(),
	 * Integer.valueOf(itagDetail.getIAGTagStatus()),itagDetail.getTagHomeAgency(),
	 * itagDetail.getTagACTypeIND(),itagDetail.getTagAccountNo(),itagDetail.
	 * getTagProtocol(),itagDetail.getTagType(),itagDetail.getTagMount(),itagDetail.
	 * getTagClass(),itagDetail.getDeviceNo(),prevEndDate);
	 * 
	 * dao_log.info("Updated Tag device details for tag {}",itagDetail.getDeviceNo()
	 * ); }
	 */
	
	@Override
	public void updateDuplicateTagDeviceDetailsforHome(TagDeviceDetails itagDetail, LocalDate prevEndDate) {
		
		jdbcTemplate.update(
                "update TPMS.T_HA_DEVICES set END_DATE = ?  where DEVICE_NO = ? AND END_DATE = ?", 
                itagDetail.getStartDate().minusDays(1)
                 ,itagDetail.getDeviceNo(),prevEndDate);
		
		dao_log.info("Updated Tag device details for tag {}",itagDetail.getDeviceNo());
	}
	
	
	public List<ITAGDetailInfoVO> getDiffRecordsFromExtTables_ITAG(String newAwaytagBucket, String oldAwaytagBucket) {

		String query = "select tag_agency_id, device_prefix, device_serial,tag_status, tag_acct_info, tag_home_agency , tag_ac_type_ind , tag_account_no , tag_protocol , tag_type , tag_mount , tag_class,    'NEW' as filetype"
				+ "					from  (select tag_agency_id, device_prefix, device_serial,tag_status, tag_acct_info, tag_home_agency , tag_ac_type_ind , tag_account_no , tag_protocol , tag_type , tag_mount , tag_class "
				+ "					         from " + newAwaytagBucket + "  minus"
				+ "					       select tag_agency_id, device_prefix, device_serial,tag_status,tag_acct_info, tag_home_agency , tag_ac_type_ind , tag_account_no , tag_protocol , tag_type , tag_mount , tag_class "
				+ "					         from " + oldAwaytagBucket + " ) x"
				+ "					union"
				+ "					select tag_agency_id, device_prefix, device_serial,tag_status,tag_acct_info, tag_home_agency , tag_ac_type_ind , tag_account_no , tag_protocol , tag_type , tag_mount , tag_class ,  'OLD' as filetype"
				+ "					from ( select tag_agency_id, device_prefix, device_serial,tag_status, tag_acct_info ,tag_home_agency , tag_ac_type_ind , tag_account_no , tag_protocol , tag_type , tag_mount , tag_class  from " + oldAwaytagBucket
				+ "					    minus"
				+ "					       select tag_agency_id, device_prefix, device_serial,tag_status,tag_acct_info, tag_home_agency , tag_ac_type_ind , tag_account_no , tag_protocol , tag_type , tag_mount , tag_class  from " + newAwaytagBucket + ") x"
				+ "					order by device_prefix,device_serial,filetype desc";

		Stream<ITAGDetailInfoVO> stream = null;
		List<ITAGDetailInfoVO> list = new ArrayList<ITAGDetailInfoVO>();
		try {
			stream = jdbcTemplate.queryForStream(query, (rs,
					rowNum) -> new ITAGDetailInfoVO(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7), rs.getString(8) ,rs.getString(9) ,rs.getString(10) ,rs.getString(11) , rs.getString(12) ,rs.getString(13)));
			list = stream.collect(Collectors.toList());
		} catch (Exception e) {
			e.printStackTrace();
		}
		dao_log.info(list.toString());

		return list;
}
	
	public List<String> getDataFromExternTable_ITAG(String externalTableName) {
		String query = "SELECT CONCAT(DEVICE_PREFIX,DEVICE_SERIAL) AS deviceNo FROM " + externalTableName;
		List<String> deviceNoNewList = jdbcTemplate.query(query, new RowMapper<String>(){
            public String mapRow(ResultSet rs, int rowNum) throws SQLException 
            {
                    return rs.getString(1);
            }});
		return deviceNoNewList;
	}

	@Override
	public int getThresholdValue(String paramname) {
		// TODO Auto-generated method stub
		int thresholdValue =0;
		try {

			MapSqlParameterSource paramSource = new MapSqlParameterSource();
		     paramSource.addValue("param_name", paramname);
			
			String queryForNY12_Condition = LoadJpaQueries.getQueryById("SELECT_PROCESS_PARAMETER_THRESHOLD");
 
			try {
				
				 thresholdValue =namedJdbcTemplate.queryForObject(queryForNY12_Condition, paramSource, Integer.class);

			} catch (EmptyResultDataAccessException empty) {
				empty.printStackTrace();

			}
		}  catch (Exception e) {
			e.printStackTrace();
		}
		

		//return 100000;
		
		return thresholdValue;
	}

	@Override
	public String getAgencyid(String fileprefix) {
		// TODO Auto-generated method stub
		
		int agencyid=0;
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
	    paramSource.addValue("param_name", fileprefix);
		
		String queryForagencyid = LoadJpaQueries.getQueryById("SELECT_AGENCYID_FROM_T_AGENCY");
		
		try {
			
			 agencyid= namedJdbcTemplate.queryForObject(queryForagencyid, paramSource, Integer.class);

		} catch (EmptyResultDataAccessException empty) {
			empty.printStackTrace();
		}
		
		String agencyID= String.valueOf(agencyid);
			
		return agencyID;
		//return 0;
	}

	@Override
	public String getAgencyidHome(String fileprefix) {
		// TODO Auto-generated method stub
		
		int agencyid=0;
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
	    paramSource.addValue("param_name", fileprefix);
		
		String queryForagencyid = LoadJpaQueries.getQueryById("SELECT_AGENCYID_FROM_T_AGENCY_HOME");
		
		try {
			
			 agencyid= namedJdbcTemplate.queryForObject(queryForagencyid, paramSource, Integer.class);

		} catch (EmptyResultDataAccessException empty) {
			empty.printStackTrace();
		}
		
		String agencyID= String.valueOf(agencyid);
			
		return agencyID;
		
	}
}