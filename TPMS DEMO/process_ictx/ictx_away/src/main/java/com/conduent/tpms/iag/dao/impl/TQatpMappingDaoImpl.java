package com.conduent.tpms.iag.dao.impl;


import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
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
import com.conduent.tpms.iag.constants.ICTXConstants;
import com.conduent.tpms.iag.constants.ITXCConstants;
import com.conduent.tpms.iag.dao.TQatpMappingDao;
import com.conduent.tpms.iag.dto.AckFileInfoDto;
import com.conduent.tpms.iag.dto.FileInfoDto;
import com.conduent.tpms.iag.dto.FileParserParameters;
import com.conduent.tpms.iag.dto.MappingInfoDto;
import com.conduent.tpms.iag.model.FileDetails;
import com.conduent.tpms.iag.model.IagFileStats;
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
	public FileDetails checkIfFileProcessedAlready(String fileName) {

		try
		{
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		String queryToCheckFile = "SELECT *  FROM TPMS.T_LANE_TX_CHECKPOINT WHERE FILE_NAME='"+fileName+"'";
		
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

/*	@Override
	public void insertaddressinaddresstable(int address_id) {
		
		String query = LoadJpaQueries.getQueryById("INSERT_ADDRESS_ID_INTO_ADDRESS");
		
		MapSqlParameterSource paramSource = null;
		
		paramSource = new MapSqlParameterSource();
		paramSource.addValue(ICTXConstants.INVALID_ADDRESS_ID,address_id);
		int addressid = namedJdbcTemplate.update(query, paramSource);
		
		dao_log.info("Address_id inserted into address table"+addressid);
		
	}
*/	
	
	@Override
	public Long insertFileDetails(FileDetails fileDetails) {

		dao_log.debug("File {} details to insert into checkpoint table..", fileDetails.toString());

		dao_log.info("Inserting file {} details into checkpoint table..", fileDetails.getFileName());

		KeyHolder keyHolder = new GeneratedKeyHolder();
		String query = LoadJpaQueries.getQueryById("INSERT_INTO_FILE_CHECKPOINT");

		MapSqlParameterSource paramSource = null;

		paramSource = new MapSqlParameterSource();
		paramSource.addValue(ICTXConstants.FILE_NAME, fileDetails.getFileName());
		paramSource.addValue(ICTXConstants.FILE_TYPE, fileDetails.getFileType());
		paramSource.addValue(ICTXConstants.PROCESS_NAME, fileDetails.getProcessName());
		paramSource.addValue(ICTXConstants.PROCESS_ID, fileDetails.getProcessId());
		paramSource.addValue(ICTXConstants.PROCESS_STATUS, fileDetails.getProcessStatus().getCode());
		paramSource.addValue(ICTXConstants.TX_DATE, fileDetails.getTxDate());
		paramSource.addValue(ICTXConstants.LANE_TX_ID, fileDetails.getLaneTxId());
		paramSource.addValue(ICTXConstants.SERIAL_NUMBER, fileDetails.getSerialNumber());
		paramSource.addValue(ICTXConstants.FILE_COUNT, fileDetails.getFileCount());
		paramSource.addValue(ICTXConstants.PROCESSED_COUNT, fileDetails.getProcessedCount());
		paramSource.addValue(ICTXConstants.SUCCESS_COUNT, fileDetails.getSuccessCount());
		paramSource.addValue(ICTXConstants.EXCEPTION_COUNT, fileDetails.getExceptionCount());
		paramSource.addValue(ICTXConstants.UPDATE_TS, timeZoneConv.currentTime());

		namedJdbcTemplate.update(query, paramSource, keyHolder, new String[] { ICTXConstants.ID });
		Map<String, Object> map = keyHolder.getKeys();
		Long id = null;
		if (null != map) {
			id = Long.parseLong(map.get(ICTXConstants.ID).toString());
		}
		dao_log.info("File {} details inserted into checkpoint with status code {}.", fileDetails.getFileName(),fileDetails.getProcessStatus().getCode());

		return id;
	}


	
	@Override
	public FileParserParameters getMappingConfigurationDetails(String fileType) {

		dao_log.info("Getting mapping configuration from table..");

		FileParserParameters fileDetails = new FileParserParameters();
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		String queryFile = LoadJpaQueries.getQueryById("GET_FILE_INFO_CONFIGURATION");

		paramSource.addValue(ICTXConstants.FILE_TYPE, fileType);
		paramSource.addValue(ICTXConstants.AGENCY_ID, ICTXConstants.HOME_AGENCY_ID);
		paramSource.addValue(ICTXConstants.FILE_FORMAT, ICTXConstants.FIXED);

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
		dao_log.info("File name mapping details collected from table : {}",fileDetails.getFileNameMappingInfo().toString());
		return fileDetails;

	}
	

	
	@Override
	public FileParserParameters getMappingConfigurationDetailsITXC(String fileType) {

		dao_log.info("Getting mapping configuration from table..");

		FileParserParameters fileDetails = new FileParserParameters();
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		String queryFile = LoadJpaQueries.getQueryById("GET_FILE_INFO_CONFIGURATION");

		paramSource.addValue(ICTXConstants.FILE_TYPE, fileType);
		paramSource.addValue(ITXCConstants.AGENCY_ID, ITXCConstants.HOME_AGENCY_ID);
		paramSource.addValue(ITXCConstants.FILE_FORMAT, ITXCConstants.FIXED);

		FileInfoDto info = namedJdbcTemplate.queryForObject(queryFile, paramSource,
				BeanPropertyRowMapper.newInstance(FileInfoDto.class));
		fileDetails.setFileInfoDto(info);

		String queryRules = LoadJpaQueries.getQueryById("GET_FIELD_VALIDATION_DETAILS_ITXC");

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
		dao_log.info("File name mapping details collected from table : {}",fileDetails.getFileNameMappingInfo().toString());
		return fileDetails;

	}

	private void getMinMaxListValues(List<MappingInfoDto> fileNameMappingDetails) {
		for (MappingInfoDto infoRow : fileNameMappingDetails) {
			if (ICTXConstants.FIXED_VALUE.equals(infoRow.getValidationType())
					|| ICTXConstants.DATE.equals(infoRow.getValidationType())) {
				infoRow.setFixeddValidValue(infoRow.getValidationValue());
			} else if (ICTXConstants.RANGE.equals(infoRow.getValidationType())) {
				String[] rangeValue = infoRow.getValidationValue().replaceAll("\\s", "").split("-");
				infoRow.setMinRangeValue(new BigInteger(rangeValue[0]));
				infoRow.setMaxRangeValue(new BigInteger(rangeValue[1]));
			} else if (ICTXConstants.LOV.equals(infoRow.getValidationType())) {
				List<String> listOfValue = Arrays.asList(infoRow.getValidationValue().split(","));
				infoRow.setListOfValidValues(listOfValue);
			}
		}
	}


	@Override
	public String getLastSuccesfulProcessedFile(String fromAgencyId2) {
		fromAgencyId2 = fromAgencyId2 + "%";
		String queryToGetFileName = "SELECT FILE_NAME FROM  (SELECT * from TPMS.T_LANE_TX_CHECKPOINT WHERE FILE_TYPE='IITC' AND PROCESS_STATUS='C' AND  FILE_NAME LIKE '"
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
	public XferControl checkFileDownloaded(String fileName) {
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		String queryToCheckFile = "SELECT XFER_CONTROL_ID, XFER_FILE_ID, XFER_FILE_NAME, DATE_CREATED, TIME_CREATED, FILE_SIZE, FILE_CHECKSUM, NUM_RECS, HASH_TOTAL, XFER_PATH, XFER_XMIT_STATUS, XFER_RETRY, UPDATE_TS, FILE_TYPE  FROM MASTER.T_XFER_CONTROL WHERE XFER_FILE_NAME='"+fileName+"' AND XFER_XMIT_STATUS ='C'";
		List<XferControl> listOfObj = namedJdbcTemplate.query(queryToCheckFile, paramSource,BeanPropertyRowMapper.newInstance(XferControl.class));
		if(listOfObj.size()>0) {
			dao_log.info("File present in XFER_CONTROL table : {}",listOfObj.get(0));
			return listOfObj.get(0);
		}
		return null;
	}
	

	@Override
	public TagDeviceDetails checkRecordExistInDevice(String device_no) {
		
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		String querytocheckrecord = "select * from T_QATP_STATISTICS";
		List<TagDeviceDetails> devicedetail = namedJdbcTemplate.query(querytocheckrecord, paramSource,BeanPropertyRowMapper.newInstance(TagDeviceDetails.class));
		if(devicedetail.size()>0) {
			return devicedetail.get(0);
		}
		return null;
	}
	
	@Override
	public void updateInvalidAddressIdinDevices(String device_no , int address_id) {
		
		jdbcTemplate.update(
				"update T_QATP_STATISTICS ",
				address_id,device_no);
				dao_log.info("Updated invalid address id for Device No.: {}",device_no);
		
	}
	
	@Override
	public void updateaddressidindevicetable(int address_id) {
		
		 dao_log.info("Updating address_id in device table"+address_id);
		 
		 KeyHolder keyHolder = new GeneratedKeyHolder();
			String query = LoadJpaQueries.getQueryById("UPDATE_ADDRESS_ID_IN_DEVICE_TABLE");

			MapSqlParameterSource paramSource = new MapSqlParameterSource();
			paramSource.addValue(ICTXConstants.INVALID_ADDRESS_ID,address_id);
			
			namedJdbcTemplate.update(query, paramSource, keyHolder, new String[] { ICTXConstants.INVALID_ADDRESS_ID });
			Map<String, Object> map = keyHolder.getKeys();
			Long addrid = null;
			if (null != map) {
				addrid = Long.parseLong(map.get(ICTXConstants.ID).toString());
			}
			dao_log.info("Address id Inserted in device table with Id= {}", addrid);
	}

	@Override
	public void updateFileIntoCheckpoint(FileDetails fileDetails) {

		dao_log.info("Updating file {} details into checkpoint table with process status: {}",
				fileDetails.getFileName(), fileDetails.getProcessStatus());

		KeyHolder keyHolder = new GeneratedKeyHolder();
		String query = LoadJpaQueries.getQueryById("UPDATE_FILE_STATUS_INTO_CHECKPOINT");

		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue(ICTXConstants.FILE_NAME, fileDetails.getFileName());
		paramSource.addValue(ICTXConstants.FILE_TYPE, fileDetails.getFileType());
		paramSource.addValue(ICTXConstants.PROCESS_STATUS, fileDetails.getProcessStatus().getCode());
		paramSource.addValue(ICTXConstants.FILE_COUNT, fileDetails.getFileCount());
		paramSource.addValue(ICTXConstants.UPDATE_TS, timeZoneConv.currentTime());
		paramSource.addValue(ICTXConstants.PROCESSED_COUNT, fileDetails.getProcessedCount());
		paramSource.addValue(ICTXConstants.SUCCESS_COUNT, fileDetails.getSuccessCount());
		paramSource.addValue(ICTXConstants.EXCEPTION_COUNT, fileDetails.getExceptionCount());
		paramSource.addValue(ICTXConstants.LANE_TX_ID,fileDetails.getLaneTxId());
		dao_log.info("Updating file details into checkpoint table with LaneTXID: {}",fileDetails.getProcessStatus());

		namedJdbcTemplate.update(query, paramSource, keyHolder, new String[] { ICTXConstants.ID });
		Map<String, Object> map = keyHolder.getKeys();
		Long id = null;
		if (null != map) {
			id = Long.parseLong(map.get(ICTXConstants.ID).toString());
		}
		dao_log.info("File checkpoint Details Inserted with Id= {}", id);

	}

	@Override
	public void insertAckDetails(AckFileInfoDto ackFileInfoDto) {

		dao_log.info("Inserting Ack file details into ack table..");

		try {
			String query = LoadJpaQueries.getQueryById("INSERT_FILE_ACK_INTO_ACK_TABLE");
			MapSqlParameterSource paramSource = new MapSqlParameterSource();
			paramSource.addValue(ICTXConstants.ACK_FILE_NAME, ackFileInfoDto.getAckFileName());
			paramSource.addValue(ICTXConstants.ACK_FILE_DATE, ackFileInfoDto.getAckFileDate());
			paramSource.addValue(ICTXConstants.ACK_FILE_TIME, ackFileInfoDto.getAckFileTime().replaceAll("(?<=\\G\\d{2})", ":").substring(0,8));
			paramSource.addValue(ICTXConstants.ACK_FILE_STATUS, Integer.parseInt(ackFileInfoDto.getAckFileStatus()));
			paramSource.addValue(ICTXConstants.TRX_FILE_NAME, ackFileInfoDto.getTrxFileName());
			paramSource.addValue(ICTXConstants.RECON_FILE_NAME, ackFileInfoDto.getReconFileName());
			paramSource.addValue(ICTXConstants.FILE_TYPE, ackFileInfoDto.getFileType());
			paramSource.addValue(ICTXConstants.FROM_AGENCY, ackFileInfoDto.getFromAgency());
			paramSource.addValue(ICTXConstants.TO_AGENCY, ackFileInfoDto.getToAgency());
			paramSource.addValue(ICTXConstants.EXTERN_FILE_ID, ackFileInfoDto.getExternFileId());
			paramSource.addValue(ICTXConstants.ATP_FILE_ID, checkAtpFileInStats(ackFileInfoDto));
			int id = namedJdbcTemplate.update(query, paramSource);

			dao_log.info("Inserted Ack file {} details into ack table", ackFileInfoDto.getAckFileName());
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public Long checkAtpFileInStats(AckFileInfoDto ackFileInfoDto) {
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		String queryFile = LoadJpaQueries.getQueryById(ICTXConstants.CHECK_ATP_FILE_ID_IN_IA_FILE_STATS);
		
		paramSource.addValue(ICTXConstants.QUERY_PARAM_ICTX_FILE_NAME, ackFileInfoDto.getTrxFileName());
		
		List<IagFileStats> val = namedJdbcTemplate.query(queryFile,paramSource,BeanPropertyRowMapper.newInstance(IagFileStats.class));
		if (val.isEmpty()) {
			return null;
		} else {
			return val.get(0).getAtpFileId();
		}
		
	}

	@Override
	public List<String> getZipCodeList() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public boolean checkForDuplicateFileNum(String value) {
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue(ICTXConstants.FILE_TYPE, ICTXConstants.ICTX);
		paramSource.addValue(ICTXConstants.ICTX_FILE_NUM, value);
		
		String query = LoadJpaQueries.getQueryById(ICTXConstants.CHECK_FOR_DUPLICATE_ICTX_FILE_NUM);
		long count;
		try {
			count = namedJdbcTemplate.queryForObject(query, paramSource, Integer.class);
			if (count > 0) {
				return true;
			} else {
				return false;
			}
		} 
		catch (DataAccessException e) {
			dao_log.info("Exception while checking duplicate file num {}",e.getMessage());
			return false;
		}
	}
	
	@Override
	public long getLastSuccesfulProcessedFileSeqNum() {
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		String query = LoadJpaQueries.getQueryById(ICTXConstants.GET_LATEST_FILE_SEQ_NUM);
		long lastSeqNum;
		try {
			lastSeqNum = namedJdbcTemplate.queryForObject(query, paramSource, Integer.class);
			if(lastSeqNum != 0l && lastSeqNum > 0) {
				return lastSeqNum;
			} else {
				return 0;
			}
		} catch (DataAccessException e) {
			return 0;
		}	
	}

}
