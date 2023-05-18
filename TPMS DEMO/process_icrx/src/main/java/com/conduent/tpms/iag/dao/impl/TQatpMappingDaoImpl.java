package com.conduent.tpms.iag.dao.impl;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.conduent.tpms.iag.constants.ICRXConstants;
import com.conduent.tpms.iag.dao.IXferControlDao;
import com.conduent.tpms.iag.dao.TQatpMappingDao;
import com.conduent.tpms.iag.dto.AccountTollPostDTO;
import com.conduent.tpms.iag.dto.AckFileInfoDto;
import com.conduent.tpms.iag.dto.AgencyTxPendingDto;
import com.conduent.tpms.iag.dto.AwayAgencyDto;
import com.conduent.tpms.iag.dto.FileInfoDto;
import com.conduent.tpms.iag.dto.FileParserParameters;
import com.conduent.tpms.iag.dto.MappingInfoDto;
import com.conduent.tpms.iag.exception.TransactionFileDataMismatch;
import com.conduent.tpms.iag.model.AgencyInfoVO;
import com.conduent.tpms.iag.model.FileDetails;
import com.conduent.tpms.iag.model.QatpStatistics;
import com.conduent.tpms.iag.model.TagDeviceDetails;
import com.conduent.tpms.iag.model.TranDetail;
import com.conduent.tpms.iag.model.XferControl;


@Repository
public class TQatpMappingDaoImpl implements TQatpMappingDao {

	private static final Logger dao_log = LoggerFactory.getLogger(TQatpMappingDaoImpl.class);

	@Autowired
	private NamedParameterJdbcTemplate namedJdbcTemplate;

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	IXferControlDao xferControlDao;

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
					fileDetails.setProcessId(rs.getLong("PROCESS_ID"));
					fileDetails.setProcessStatus(FileProcessStatus.getByCode(rs.getString("PROCESS_STATUS")));
					fileDetails.setLaneTxId(rs.getLong("LANE_TX_ID"));
					fileDetails.setSerialNumber(rs.getLong("SERIAL_NUMBER"));
					fileDetails.setFileCount(rs.getLong("FILE_COUNT"));

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
	public AgencyTxPendingDto getlaneTxIdFromAgencyTxPending(long laneTxId) {
		try
		{
			MapSqlParameterSource paramSource = new MapSqlParameterSource();
			String query= "SELECT *  FROM TPMS.T_AGENCY_TX_PENDING WHERE LANE_TX_ID='"+laneTxId+"'";

			return namedJdbcTemplate.queryForObject(query, paramSource,new RowMapper<AgencyTxPendingDto>()
			{

				@Override
				public AgencyTxPendingDto mapRow(ResultSet rs, int rowNum) throws SQLException 
				{
					AgencyTxPendingDto agencyPending=new AgencyTxPendingDto();
			
					agencyPending.setLaneTxId(rs.getLong("LANE_TX_ID"));
					agencyPending.setTxExternRefNo(rs.getString("TX_EXTERN_REF_NO"));
					agencyPending.setTxSeqNumber(rs.getLong("TX_SEQ_NUMBER"));
					agencyPending.setExternFileId(rs.getLong("EXTERN_FILE_ID"));
					agencyPending.setTxTimestamp((rs.getObject("TX_TIMESTAMP", OffsetDateTime.class)!=null) ? rs.getObject("TX_TIMESTAMP", OffsetDateTime.class):null);
					dao_log.info("txTime Stamp getting==>"+agencyPending.getTxTimestamp());
					
					agencyPending.setTxDate(rs.getDate("TX_DATE").toLocalDate());
					agencyPending.setTxModSeq(rs.getInt("TX_MOD_SEQ"));
					agencyPending.setTxType(rs.getString("TX_TYPE"));
					agencyPending.setTxSubtype(rs.getNString("TX_SUB_TYPE"));
					agencyPending.setTollSystemType(rs.getString("TOLL_SYSTEM_TYPE"));
					agencyPending.setLaneMode(rs.getInt("LANE_MODE"));
					agencyPending.setLaneType(rs.getInt("LANE_TYPE"));
					agencyPending.setLaneState(rs.getInt("LANE_STATE"));
					agencyPending.setLaneHealth(rs.getInt("LANE_HEALTH"));
					agencyPending.setPlazaAgencyId(rs.getInt("PLAZA_AGENCY_ID"));
					agencyPending.setPlazaId(rs.getInt("PLAZA_ID"));
					agencyPending.setLaneId(rs.getInt("LANE_ID"));
					agencyPending.setCollectorId(rs.getInt("COLLECTOR_ID"));
					agencyPending.setTourSegmentId(rs.getInt("TOUR_SEGMENT_ID"));
					agencyPending.setEntryDataSource(rs.getNString("ENTRY_DATA_SOURCE"));
					agencyPending.setEntryLaneId(rs.getInt("ENTRY_LANE_ID"));
					agencyPending.setEntryPlazaId(rs.getInt("ENTRY_PLAZA_ID"));
					//agencyPending.setEntryTimestamp(rs.getTimestamp("ENTRY_TIMESTAMP").toLocalDateTime());
					agencyPending.setEntryTxSeqNumber(rs.getInt("ENTRY_TX_SEQ_NUMBER"));
					agencyPending.setEntryVehicleSpeed(rs.getInt("ENTRY_VEHICLE_SPEED"));
					agencyPending.setLaneTxStatus(rs.getInt("LANE_TX_STATUS"));
					agencyPending.setLanetxType(rs.getInt("LANE_TX_TYPE"));
					agencyPending.setTollRevenueType(rs.getInt("TOLL_REVENUE_TYPE"));
					agencyPending.setActualClass(rs.getInt("ACTUAL_CLASS"));
					agencyPending.setActualAxles(rs.getInt("ACTUAL_AXLES"));
					agencyPending.setActualExtraAxles(rs.getInt("ACTUAL_EXTRA_AXLES"));
					agencyPending.setCollectorClass(rs.getInt("COLLECTOR_CLASS"));
					agencyPending.setCollectorAxles(rs.getInt("COLLECTOR_AXLES"));
					agencyPending.setPreclassClass(rs.getInt("PRECLASS_CLASS"));
					agencyPending.setPreclassAxles(rs.getInt("PRECLASS_AXLES"));
					agencyPending.setPostclassClass(rs.getInt("POSTCLASS_CLASS"));
					agencyPending.setPostclassAxles(rs.getInt("POSTCLASS_AXLES"));
					agencyPending.setForwardAxles(rs.getInt("FORWARD_AXLES"));
					agencyPending.setReverseAxles(rs.getInt("REVERSE_AXLES"));
					agencyPending.setFullFareAmount(rs.getInt("FULL_FARE_AMOUNT"));
					agencyPending.setDiscountedAmount(rs.getInt("DISCOUNTED_AMOUNT"));
					agencyPending.setCollectedAmount(rs.getDouble("COLLECTED_AMOUNT"));
					agencyPending.setUnrealizedAmount(rs.getDouble("UNREALIZED_AMOUNT"));
					agencyPending.setIsDiscountable(rs.getNString("IS_DISCOUNTABLE"));
					agencyPending.setIsMedianFare(rs.getNString("IS_MEDIAN_FARE"));
					agencyPending.setIsPeak(rs.getNString("IS_PEAK"));
					agencyPending.setVehicleSpeed(rs.getInt("VEHICLE_SPEED"));
					agencyPending.setReceiptIssued(rs.getInt("RECEIPT_ISSUED"));
					agencyPending.setDeviceNo(rs.getString("DEVICE_NO"));
					agencyPending.setAccountType(rs.getInt("ACCOUNT_TYPE"));
					agencyPending.setDeviceCodedClass(rs.getInt("DEVICE_CODED_CLASS"));
					agencyPending.setDeviceAgencyClass(rs.getInt("DEVICE_AGENCY_CLASS"));
					agencyPending.setDeviceIagClass(rs.getInt("DEVICE_IAG_CLASS"));
					agencyPending.setDeviceAxles(rs.getInt("DEVICE_AXLES"));
					agencyPending.setEtcAccountId(rs.getLong("ETC_ACCOUNT_ID"));
					agencyPending.setReadAviClass(rs.getInt("READ_AVI_CLASS"));
					agencyPending.setReadAviAxles(rs.getInt("READ_AVI_AXLES"));
					agencyPending.setDeviceProgramStatus(rs.getString("DEVICE_PROGRAM_STATUS"));
					agencyPending.setBufferReadFlag(rs.getString("BUFFERED_READ_FLAG"));
					agencyPending.setLaneDeviceStatus(rs.getString("LANE_DEVICE_STATUS"));
					agencyPending.setPostDeviceStatus(rs.getInt("POST_DEVICE_STATUS"));
					agencyPending.setPreTxnBalance(rs.getDouble("PRE_TXN_BALANCE"));
					/*
					 * agencyPending.setPlanTypeId(rs.getInt("PLAN_TYPE_ID"));
					 * dao_log.info("row===============>"+"63");
					 * agencyPending.setEtcTxStatus(rs.getInt("ETC_TX_STATUS"));
					 * dao_log.info("row===============>"+"64");
					 */
					agencyPending.setSpeedViolFlag(rs.getString("SPEED_VIOL_FLAG"));
					agencyPending.setImageTaken(rs.getString("IMAGE_TAKEN"));
					agencyPending.setPlateCountry(rs.getString("PLATE_COUNTRY"));
					agencyPending.setPlateState(rs.getString("PLATE_STATE"));
					agencyPending.setPlateNumber(rs.getString("PLATE_NUMBER"));
					
					agencyPending.setAtpFileId(rs.getString("ATP_FILE_ID"));
					agencyPending.setUpdateTs(rs.getTimestamp("UPDATE_TS").toLocalDateTime());
					agencyPending.setIsReversed(rs.getString("IS_REVERSED"));
					agencyPending.setCorrReasonId(rs.getInt("CORR_REASON_ID"));
					
					agencyPending.setReconStatusInd(rs.getInt("RECON_STATUS_IND"));
					agencyPending.setDeviceReadCount(rs.getInt("RECON_SUB_CODE_IND"));
					//agencyPending.setExternFileDate(rs.getDate("EXTERN_FILE_DATE").toLocalDate());
					agencyPending.setMileage(rs.getInt("MILEAGE"));
					agencyPending.setDeviceReadCount(rs.getInt("DEVICE_READ_COUNT"));
					agencyPending.setDeviceWriteCount(rs.getInt("DEVICE_WRITE_COUNT"));
					agencyPending.setEntryDeviceReadCount(rs.getInt("ENTRY_DEVICE_READ_COUNT"));
					agencyPending.setEntryDeviceWriteCount(rs.getInt("ENTRY_DEVICE_WRITE_COUNT"));
					agencyPending.setDepositeId(rs.getString("DEPOSIT_ID"));
					agencyPending.setReciprocityTrx(rs.getString("RECIPROCITY_TRX"));
					agencyPending.setTollAmount(rs.getDouble("TOLL_AMOUNT"));
					agencyPending.setDstAtpFileId(rs.getLong("DST_ATP_FILE_ID"));
					agencyPending.setSourceSystem(rs.getInt("SOURCE_SYSTEM"));
					agencyPending.setEtcFareAmount(rs.getDouble("ETC_FARE_AMOUNT"));
					agencyPending.setPostedFareAmount(rs.getDouble("POSTED_FARE_AMOUNT"));
					agencyPending.setExpectedRevenueAmount(rs.getDouble("EXPECTED_REVENUE_AMOUNT"));
					agencyPending.setVideoFareAmount(rs.getDouble("VIDEO_FARE_AMOUNT"));
					agencyPending.setCashFareAmount(rs.getDouble("CASH_FARE_AMOUNT"));
					agencyPending.setTxStatus(rs.getInt("TX_STATUS"));
					//agencyPending.setCscLookupKey(rs.getString("CSC_LOOKUP_KEY"));
					
					agencyPending.setPriceScheduleId(rs.getInt("PRICE_SCHEDULE_ID"));

					
					//agencyPending.setRevenueDate(rs.getDate("REVENUE_DATE").toLocalDate());
					if(rs.getDate("REVENUE_DATE")!=null) {
						agencyPending.setRevenueDate(rs.getDate("REVENUE_DATE").toLocalDate());
					}
					
					//agencyPending.setPostedDate(rs.getDate("POSTED_DATE").toLocalDate());
					if(rs.getDate("POSTED_DATE")!=null) {
						agencyPending.setPostedDate(rs.getDate("POSTED_DATE").toLocalDate());
					}
					
					//agencyPending.setReconDate(rs.getDate("RECON_DATE").toLocalDate());
					if(rs.getDate("RECON_DATE")!=null) {
						agencyPending.setReconDate(rs.getDate("RECON_DATE").toLocalDate());
					}
					
					//agencyPending.setAccountAgencyId(rs.getInt("ACCOUNT_AGENCY_ID"));
					if(rs.getInt("ACCOUNT_AGENCY_ID")!=0 ) {
						agencyPending.setAccountAgencyId(rs.getInt("ACCOUNT_AGENCY_ID"));
					}
					
					//agencyPending.setCscLookupKey(rs.getString("CSC_LOOKUP_KEY"));
					if(rs.getString("CSC_LOOKUP_KEY")!=null ) {
						agencyPending.setCscLookupKey(rs.getString("CSC_LOOKUP_KEY"));
					}
					
					//agencyPending.setEntryTimestamp(rs.getTimestamp("ENTRY_TIMESTAMP").toLocalDateTime());
					if(rs.getTimestamp("ENTRY_TIMESTAMP")!=null) {
						//agencyPending.setEntryTimestamp((OffsetDateTime) rs.getObject("ENTRY_TIMESTAMP"));
						agencyPending.setEntryTimestamp(rs.getObject("ENTRY_TIMESTAMP", OffsetDateTime.class));
					}
					
					if(agencyPending.getExternFileId()!=0) {
						
						XferControl getFileDateinfo = xferControlDao.getXferControlDate(agencyPending.getExternFileId());
						if(getFileDateinfo!=null && getFileDateinfo.getDateCreated()!=null) {
							agencyPending.setExternFileDate(getFileDateinfo.getDateCreated().toLocalDate());
							dao_log.info("EXTERN_FILE_Date ===============>"+agencyPending.getExternFileDate());
						}
					}
					dao_log.info("agencyPending obj===============>"+agencyPending.toString());
					return agencyPending;
				}

			});
		}
		catch(Exception ex)
		{
			ex.getMessage();
			ex.printStackTrace();
			dao_log.info("Record is not getting from TPMS.T_AGENCY_TX_PENDING Table properly or LaneTxId is not present in T_AGENCY_TX_PENDING table "+ex.getMessage()); 
			return null;
		}
	}

	@Override
	public Long insertFileDetails(FileDetails fileDetails) {

		dao_log.debug("file {} details to insert into checkpoint table..", fileDetails.toString());

		dao_log.info("Inserting  file {} details into checkpoint table..", fileDetails.getFileName());

		KeyHolder keyHolder = new GeneratedKeyHolder();
		String query = LoadJpaQueries.getQueryById("INSERT_INTO_FILE_CHECKPOINT");

		MapSqlParameterSource paramSource = null;

		paramSource = new MapSqlParameterSource();
		paramSource.addValue(ICRXConstants.FILE_NAME, fileDetails.getFileName());
		paramSource.addValue(ICRXConstants.FILE_TYPE, fileDetails.getFileType());
		paramSource.addValue(ICRXConstants.PROCESS_NAME, fileDetails.getProcessName());
		paramSource.addValue(ICRXConstants.PROCESS_ID, fileDetails.getProcessId());
		paramSource.addValue(ICRXConstants.PROCESS_STATUS, fileDetails.getProcessStatus().getCode());
		paramSource.addValue(ICRXConstants.TX_DATE, fileDetails.getTxDate());
		paramSource.addValue(ICRXConstants.LANE_TX_ID, fileDetails.getLaneTxId());
		paramSource.addValue(ICRXConstants.SERIAL_NUMBER, fileDetails.getSerialNumber());
		paramSource.addValue(ICRXConstants.FILE_COUNT, fileDetails.getFileCount());
		paramSource.addValue(ICRXConstants.PROCESSED_COUNT, fileDetails.getProcessedCount());
		paramSource.addValue(ICRXConstants.SUCCESS_COUNT, fileDetails.getSuccessCount());
		paramSource.addValue(ICRXConstants.EXCEPTION_COUNT, fileDetails.getExceptionCount());
		paramSource.addValue(ICRXConstants.UPDATE_TS, Timestamp.valueOf(fileDetails.getUpdateTs()));

		namedJdbcTemplate.update(query, paramSource, keyHolder, new String[] { ICRXConstants.ID });
		Map<String, Object> map = keyHolder.getKeys();
		Long id = null;
		if (null != map) {
			id = Long.parseLong(map.get(ICRXConstants.ID).toString());
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

		paramSource.addValue(ICRXConstants.FILE_TYPE, fileType);
		paramSource.addValue(ICRXConstants.AGENCY_ID, ICRXConstants.MTA_AGENCY_ID);
		paramSource.addValue(ICRXConstants.FILE_FORMAT, ICRXConstants.FIXED);

		FileInfoDto info = namedJdbcTemplate.query(queryFile, paramSource,
				BeanPropertyRowMapper.newInstance(FileInfoDto.class)).get(0);
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

	/**
	 * 
	 * IRXC mapping details
	 */

	@Override
	public FileParserParameters getIRXCMappingConfigurationDetails(FileParserParameters fileDto) {

		dao_log.info("Getting mapping configuration from table..");

		FileParserParameters fileDetails = new FileParserParameters();
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		String queryFile = LoadJpaQueries.getQueryById("GET_FILE_INFO_CONFIGURATION");

		paramSource.addValue(ICRXConstants.FILE_TYPE, ICRXConstants.IRXC);
		paramSource.addValue(ICRXConstants.AGENCY_ID, ICRXConstants.MTA_AGENCY_ID);
		paramSource.addValue(ICRXConstants.FILE_FORMAT, ICRXConstants.FIXED);

		FileInfoDto info = namedJdbcTemplate.queryForObject(queryFile, paramSource,
				BeanPropertyRowMapper.newInstance(FileInfoDto.class));
		fileDetails.setFileInfoDto(info);

		String queryRules = LoadJpaQueries.getQueryById("GET_IRXC_FIELD_VALIDATION_DETAILS");

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
			if (ICRXConstants.FIXED_VALUE.equals(infoRow.getValidationType())
					|| ICRXConstants.DATE.equals(infoRow.getValidationType())) {
				infoRow.setFixeddValidValue(infoRow.getValidationValue());
			} else if (ICRXConstants.RANGE.equals(infoRow.getValidationType())) {
				String[] rangeValue = infoRow.getValidationValue().replaceAll("\\s", "").split("-");
				infoRow.setMinRangeValue(Long.parseUnsignedLong(rangeValue[0]));
			    if(rangeValue[1].equalsIgnoreCase("99999999999999999999")) {
			    	infoRow.setMaxRangeValue(Long.MAX_VALUE);
			    }else {
			    	infoRow.setMaxRangeValue(Long.parseUnsignedLong(rangeValue[1]));
			    }
			} else if (ICRXConstants.LOV.equals(infoRow.getValidationType())) {
				List<String> listOfValue = Arrays.asList(infoRow.getValidationValue().split(","));
				infoRow.setListOfValidValues(listOfValue);
			}
		}
	}


	@Override
	public String getLastSuccesfulProcessedFile(String fromAgencyId2) {
		fromAgencyId2 = fromAgencyId2 + "%";
		String queryToGetFileName = "SELECT FILE_NAME FROM  (SELECT * from TPMS.T_LANE_TX_CHECKPOINT WHERE FILE_TYPE='ICRX' AND PROCESS_STATUS='C' AND  FILE_NAME LIKE '"
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
		dao_log.info("File present in XFER_CONTROL table Query : {}",queryToCheckFile);
		//String queryToCheckFile = "SELECT XFER_CONTROL_ID, XFER_FILE_ID, XFER_FILE_NAME, DATE_CREATED, TIME_CREATED, FILE_SIZE, FILE_CHECKSUM, NUM_RECS, HASH_TOTAL, XFER_PATH, XFER_XMIT_STATUS, XFER_RETRY, UPDATE_TS, FILE_TYPE  FROM T_XFER_CONTROL WHERE XFER_FILE_NAME='"+fileName+"' AND XFER_XMIT_STATUS ='C'";
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
		paramSource.addValue(ICRXConstants.INVALID_ADDRESS_ID,address_id);

		namedJdbcTemplate.update(query, paramSource, keyHolder, new String[] { ICRXConstants.INVALID_ADDRESS_ID });
		Map<String, Object> map = keyHolder.getKeys();
		Long addrid = null;
		if (null != map) {
			addrid = Long.parseLong(map.get(ICRXConstants.ID).toString());
		}
		dao_log.info("Address id Inserted in device table with Id= {}", addrid);
	}

	@Override
	public void updateFileIntoCheckpoint(FileDetails fileDetails) {

		dao_log.info("Updating ICRX file {} details into checkpoint table with process status: {}",
				fileDetails.getFileName(), fileDetails.getProcessStatus());

		KeyHolder keyHolder = new GeneratedKeyHolder();
		String query = LoadJpaQueries.getQueryById("UPDATE_FILE_STATUS_INTO_CHECKPOINT");

		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue(ICRXConstants.FILE_NAME, fileDetails.getFileName());
		paramSource.addValue(ICRXConstants.FILE_TYPE, fileDetails.getFileType());
		paramSource.addValue(ICRXConstants.PROCESS_STATUS, fileDetails.getProcessStatus().getCode());
		paramSource.addValue(ICRXConstants.FILE_COUNT, fileDetails.getFileCount());
		//Added Tx Date While Updating
		paramSource.addValue(ICRXConstants.TX_DATE, LocalDateTime.now().toLocalDate());
		paramSource.addValue(ICRXConstants.UPDATE_TS, Timestamp.valueOf(fileDetails.getUpdateTs()));
		paramSource.addValue(ICRXConstants.PROCESSED_COUNT, fileDetails.getProcessedCount());
		paramSource.addValue(ICRXConstants.SUCCESS_COUNT, fileDetails.getSuccessCount());
		paramSource.addValue(ICRXConstants.EXCEPTION_COUNT, fileDetails.getExceptionCount());
		//need to add serial num over here
		paramSource.addValue(ICRXConstants.SERIAL_NUMBER, fileDetails.getSerialNumber());


		namedJdbcTemplate.update(query, paramSource, keyHolder, new String[] { ICRXConstants.ID });
		Map<String, Object> map = keyHolder.getKeys();
		Long id = null;
		if (null != map) {
			id = Long.parseLong(map.get(ICRXConstants.ID).toString());
		}
		dao_log.info("File checkpoint Details Inserted with Id= {}", id);

	}
	
	@Override
	public void updateFileIntoCheckpointLaneTxId(FileDetails fileDetails) {

		dao_log.info("Updating ICRX file {} details into checkpoint table with process status: {}",
				fileDetails.getFileName(), fileDetails.getProcessStatus());

		KeyHolder keyHolder = new GeneratedKeyHolder();
		String query = LoadJpaQueries.getQueryById("UPDATE_FILE_STATUS_INTO_CHECKPOINT_LANE_TX_ID");

		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue(ICRXConstants.FILE_NAME, fileDetails.getFileName());
		paramSource.addValue(ICRXConstants.FILE_TYPE, fileDetails.getFileType());
		paramSource.addValue(ICRXConstants.PROCESS_STATUS, fileDetails.getProcessStatus().getCode());
		paramSource.addValue(ICRXConstants.FILE_COUNT, fileDetails.getFileCount());
		//Added Tx Date While Updating
		paramSource.addValue(ICRXConstants.TX_DATE, LocalDateTime.now().toLocalDate());
		paramSource.addValue(ICRXConstants.UPDATE_TS, Timestamp.valueOf(fileDetails.getUpdateTs()));
		paramSource.addValue(ICRXConstants.PROCESSED_COUNT, fileDetails.getProcessedCount());
		paramSource.addValue(ICRXConstants.SUCCESS_COUNT, fileDetails.getSuccessCount());
		paramSource.addValue(ICRXConstants.EXCEPTION_COUNT, fileDetails.getExceptionCount());
		paramSource.addValue(ICRXConstants.LANE_TX_ID, fileDetails.getLaneTxId());
		
		namedJdbcTemplate.update(query, paramSource, keyHolder, new String[] { ICRXConstants.ID });
		Map<String, Object> map = keyHolder.getKeys();
		Long id = null;
		if (null != map) {
			id = Long.parseLong(map.get(ICRXConstants.ID).toString());
		}
		dao_log.info("File checkpoint Details Inserted with Id= {}", id);

	}


	/*@Override
	public List<AgencyInfoVO> getAgencyDetails() {

		String queryRules =	LoadJpaQueries.getQueryById("SELECT_INFO_FROM_T_AGENCY");
		dao_log.info("Agency info fetched from T_Agency table successfully.");
		List<AgencyInfoVO> list= jdbcTemplate.query(queryRules, new BeanPropertyRowMapper<AgencyInfoVO>(AgencyInfoVO.class));
		return list;
	}*/

	@Override
	public void insertAckDetails(AckFileInfoDto ackFileInfoDto) {

		dao_log.info("Inserting Ack file details into ack table..");

		String query = LoadJpaQueries.getQueryById("INSERT_FILE_ACK_INTO_ACK_TABLE");
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue(ICRXConstants.ACK_FILE_NAME, ackFileInfoDto.getAckFileName());
		paramSource.addValue(ICRXConstants.ACK_FILE_DATE, ackFileInfoDto.getAckFileDate());
		//paramSource.addValue(ICRXConstants.ACK_FILE_TIME, ackFileInfoDto.getAckFileTime().replaceAll("(?<=\\G\\d{2})", ":").substring(0,8));
		paramSource.addValue(ICRXConstants.ACK_FILE_TIME, ackFileInfoDto.getAckFileTime());
		paramSource.addValue(ICRXConstants.ACK_FILE_STATUS, Integer.parseInt(ackFileInfoDto.getAckFileStatus()));
		paramSource.addValue(ICRXConstants.TRX_FILE_NAME, ackFileInfoDto.getTrxFileName());
		paramSource.addValue(ICRXConstants.RECON_FILE_NAME, ackFileInfoDto.getReconFileName());
		paramSource.addValue(ICRXConstants.FILE_TYPE, ackFileInfoDto.getFileType());
		paramSource.addValue(ICRXConstants.FROM_AGENCY, ackFileInfoDto.getToAgency());
		paramSource.addValue(ICRXConstants.TO_AGENCY, ackFileInfoDto.getFromAgency());
		paramSource.addValue(ICRXConstants.EXTERN_FILE_ID, ackFileInfoDto.getExternFileId());
		paramSource.addValue(ICRXConstants.ATP_FILE_ID, ackFileInfoDto.getAtpFileId());
		int id = namedJdbcTemplate.update(query, paramSource);

		dao_log.info("Inserted Ack file {} details into ack table", ackFileInfoDto.getAckFileName());

	}

	@Override
	public List<String> getZipCodeList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void insertQatpStatistics(QatpStatistics qatpStatistics) {
		// TODO Auto-generated method stub
		dao_log.info("Inserting Statistics details into statistics table..");
		String query = LoadJpaQueries.getQueryById("INSERT_FILE_STATISTICS_INTO_TABLE");
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue(ICRXConstants.FILE_NAME, qatpStatistics.getFileName());
		paramSource.addValue(ICRXConstants.INSERT_DATE, qatpStatistics.getInsertLocalDate());
		paramSource.addValue(ICRXConstants.INSERT_TIME, qatpStatistics.getInsertTime());
		paramSource.addValue(ICRXConstants.RECORD_COUNT, qatpStatistics.getRecordCount());
		paramSource.addValue(ICRXConstants.AMOUNT, qatpStatistics.getAmount());
		paramSource.addValue(ICRXConstants.IS_PROCESSED, qatpStatistics.getIsProcessed());
		paramSource.addValue(ICRXConstants.PROCESS_DATE, qatpStatistics.getProcessLocalDate());
		paramSource.addValue(ICRXConstants.PROCESS_TIME, qatpStatistics.getProcessTime());
		paramSource.addValue(ICRXConstants.PROCESS_REC_COUNT, qatpStatistics.getRecordCount());
		paramSource.addValue(ICRXConstants.UPDATE_TS, qatpStatistics.getUpLocalDateTs());
		paramSource.addValue(ICRXConstants.XFER_CONTROL_ID, qatpStatistics.getXferControlId());
		int id = namedJdbcTemplate.update(query, paramSource);
		dao_log.info("Inserted Ack file {} details into ack table", qatpStatistics.getFileName());
	}

	@Override
	public void insertaddressinaddresstable(int address_id) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<AgencyInfoVO> getAgencyDetails() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateTranDetail(TranDetail tranDetail) {
		String updateQuery = "UPDATE T_TRAN_DETAIL SET DEPOSIT_ID=?,POSTED_DATE=?, TX_STATUS = ?,UPDATE_TS=? WHERE LANE_TX_ID = ? ";
		jdbcTemplate.update(updateQuery,tranDetail.getDepositId(),new Timestamp(new Date().getTime()),tranDetail.getEtcTxStatus().getCode(),timeZoneConv.currentTime(), tranDetail.getLaneTxId());
		dao_log.info("Update TS converted as per TimeZone in T_TRAN Table succesfully."+timeZoneConv.currentTime());
		dao_log.info("Data Update in T_TRAN Table succesfully.");
	}
	
	@Override
	public void updateTranDetailPostedAmt(TranDetail tranDetail) {
		dao_log.info("Update T_TRAN_DETAIL updateTranDetailPostedAmt {} for Lane TX Id {}",tranDetail.getPostedFareAmount(), tranDetail.getLaneTxId());
		String updateQuery = "UPDATE T_TRAN_DETAIL SET DEPOSIT_ID=?,POSTED_DATE=?, TX_STATUS = ?,UPDATE_TS=?,POSTED_FARE_AMOUNT=? WHERE LANE_TX_ID = ? ";
		jdbcTemplate.update(updateQuery,tranDetail.getDepositId(),new Timestamp(new Date().getTime()),tranDetail.getEtcTxStatus().getCode(),timeZoneConv.currentTime(),tranDetail.getPostedFareAmount(), tranDetail.getLaneTxId());
		
		dao_log.info("Update TS converted as per TimeZone in T_TRAN Table succesfully."+timeZoneConv.currentTime());
		dao_log.info("Data Update in T_TRAN Table succesfully.");
	}
	

	@Override
	public void updateTranDetailWithFields(TranDetail tranDetail, AccountTollPostDTO toll) {
		dao_log.info("Update T_TRAN_DETAIL updateTranDetailWithFields {} for Lane TX Id {}",tranDetail.getPostedFareAmount(), tranDetail.getLaneTxId());
		String updateQuery = "UPDATE T_TRAN_DETAIL SET DEPOSIT_ID=?,POSTED_DATE=?, TX_STATUS = ?,UPDATE_TS=?,PLAN_TYPE=?,POSTED_FARE_AMOUNT=?,ETC_ACCOUNT_ID=? WHERE LANE_TX_ID = ? ";
		jdbcTemplate.update(updateQuery,tranDetail.getDepositId(),new Timestamp(new Date().getTime()),tranDetail.getEtcTxStatus().getCode(),timeZoneConv.currentTime(), toll.getPlanTypeId(), toll.getPostedFareAmount(), toll.getEtcAccountId(), tranDetail.getLaneTxId());	
		dao_log.info("Update TS converted as per TimeZone in T_TRAN Table succesfully."+timeZoneConv.currentTime());
		dao_log.info("Data Update in T_TRAN Table succesfully.");
	}
	
	@Override
	public void insertAwayAgency(AwayAgencyDto awayAgencyDto) {
		try {
			int id =0;
			String query = LoadJpaQueries.getQueryById("INSERT_DATA_INTO_AWAY_AGENCY_TABLE");

			MapSqlParameterSource paramSource = new MapSqlParameterSource();
			paramSource.addValue(ICRXConstants.LANE_TX_ID, awayAgencyDto.getLaneTxId());
			paramSource.addValue(ICRXConstants.ETC_ACCOUNT_ID, awayAgencyDto.getEtcAccountId());
			paramSource.addValue(ICRXConstants.TX_STATUS, awayAgencyDto.getTxStatus());
			paramSource.addValue(ICRXConstants.TX_DATE, awayAgencyDto.getTxDate());
			paramSource.addValue(ICRXConstants.POSTED_DATE, awayAgencyDto.getPostedDate());
			paramSource.addValue(ICRXConstants.DEPOSIT_ID, awayAgencyDto.getDepositId());
			paramSource.addValue(ICRXConstants.TX_TIMESTAMP, awayAgencyDto.getTxTimestamp());

			paramSource.addValue(ICRXConstants.REVENUE_DATE, awayAgencyDto.getRevenueDate());
			paramSource.addValue(ICRXConstants.TX_EXTERN_REF_NO, awayAgencyDto.getTxExternRefNo());
			paramSource.addValue(ICRXConstants.EXTERN_FILE_ID, awayAgencyDto.getExternFileId());
			paramSource.addValue(ICRXConstants.PLAZA_AGENCY_ID, awayAgencyDto.getPlazaAgencyId());
			paramSource.addValue(ICRXConstants.PLAZA_ID, awayAgencyDto.getPlazaId());
			paramSource.addValue(ICRXConstants.LANE_ID, awayAgencyDto.getLaneId());
			paramSource.addValue(ICRXConstants.DEVICE_NO, awayAgencyDto.getDeviceNo());
			paramSource.addValue(ICRXConstants.PLATE_COUNTRY, awayAgencyDto.getPlateCountry());
			paramSource.addValue(ICRXConstants.PLATE_NUMBER, awayAgencyDto.getPlateNumber());
			paramSource.addValue(ICRXConstants.PLATE_STATE, awayAgencyDto.getPlateState());
			paramSource.addValue(ICRXConstants.UPDATE_TS, timeZoneConv.currentTime());
			paramSource.addValue(ICRXConstants.ROW_ID, awayAgencyDto.getRowId());
			paramSource.addValue(ICRXConstants.ETC_DUP_SERIAL_NUM, awayAgencyDto.getDuplSerialNum());

			id = namedJdbcTemplate.update(query, paramSource);

			dao_log.info("Record inserted into T_AWAY_AGENCY_TX Table with Id= {}", id);
		}catch(Exception ex)
		{
			ex.getMessage();
			ex.printStackTrace();
			dao_log.info("Record not inserted into T_AWAY_AGENCY_TX Table properly"+ex.getMessage()); 
		}
	}
	
	
	
	@Override
	public void updateAwayAgency(AwayAgencyDto awayAgencyDto) {
		try {
			int id =0;
			String query = LoadJpaQueries.getQueryById("UPDATE_DATA_INTO_AWAY_AGENCY_TABLE");

			MapSqlParameterSource paramSource = new MapSqlParameterSource();
			paramSource.addValue(ICRXConstants.TX_STATUS, awayAgencyDto.getTxStatus());
			paramSource.addValue(ICRXConstants.LANE_TX_ID, awayAgencyDto.getLaneTxId());

			id = namedJdbcTemplate.update(query, paramSource);

			dao_log.info("updated into T_AWAY_AGENCY_TX Table with Id= {}", id);
		}catch(Exception ex)
		{
			ex.getMessage();
			ex.printStackTrace();
			dao_log.info("Record not updated into T_AWAY_AGENCY_TX Table properly"+ex.getMessage()); 
		}
	}

	@Override
	public void deleteAgencyTxPending(long laneTxId) {

		String query= "DELETE FROM TPMS.T_AGENCY_TX_PENDING WHERE LANE_TX_ID=?";

		int status  =jdbcTemplate.update(query, laneTxId);

		if(status != 0){
			dao_log.info("Record deleted from TPMS.T_AGENCY_TX_PENDING Table with Id= {}",+ laneTxId);
		}else{
			dao_log.info("No record found for delete in TPMS.T_AGENCY_TX_PENDING Table for laneTXId" + laneTxId);
		}

	}

	@Override
	public String getAccountAgencyId(long etcAccountIdParsing) {
		
		String query = "select agency_id from FPMS.t_fpms_account where etc_account_id='"+etcAccountIdParsing+"'";
		
		Stream<String> stream = jdbcTemplate.queryForStream(query, (rs,
				rowNum) -> new String(rs.getString(1)));
		List<String> list = stream.collect(Collectors.toList());
		
		dao_log.info("Account Agency Id  ======>" + list.get(0));

		if (list.isEmpty()) {
			return "";
		}else {
			return list.get(0);
		}
	}

	/*
	 * Below Method updates status of ICTX file and matches ICRX file with it.
	 */
	@Override
	public void updateICTXFileDetails(String ictxFileNum, FileDetails fileDetails) throws TransactionFileDataMismatch {
		
		String query= "UPDATE TPMS.T_IA_FILE_STATS SET ICRX_FILE_NAME=?, PROCESSED_FLAG=?,OUTPUT_COUNT=?,UPDATE_TS=? WHERE FILE_SEQ_NUMBER = ? ";

		int status  =jdbcTemplate.update(query,fileDetails.getFileName(),"Y",fileDetails.getFileCount(),fileDetails.getUpdateTs(),ictxFileNum);

		if(status != 0){
			dao_log.info("Record Updated from TPMS.T_IA_FILE_STATS Table with ictxFileNum=" + ictxFileNum);
		}else{
			dao_log.info("No record found for Update in TPMS.T_IA_FILE_STATS Table for ictxFileNum=" + ictxFileNum);
			throw new TransactionFileDataMismatch("No record found for Update in TPMS.T_IA_FILE_STATS Table for ictxFileNum=" + ictxFileNum);
		}
		
	}

	@Override
	public Long getATPFileId(String ictxFileNum) {
		
		String query= "SELECT ATP_FILE_ID FROM TPMS.T_IA_FILE_STATS WHERE FILE_SEQ_NUMBER = '"+ictxFileNum+"'";
		
		Stream<Long> stream = jdbcTemplate.queryForStream(query, (rs,
				rowNum) ->rs.getLong(1));
		List<Long> list = stream.collect(Collectors.toList());
		
		

		if (list.isEmpty()) {
			dao_log.info("ATP File Id is not available in T_IA_FILE_STATS");
			return null;
		}else {
		dao_log.info("ATP File Id  ======>" + list.get(0));
			return list.get(0);
		}
		
	}
	
	@Override
	public int getICTXRecordCountId(String ictxFileNum) {
		
		String query= "SELECT INPUT_COUNT FROM TPMS.T_IA_FILE_STATS WHERE FILE_SEQ_NUMBER = '"+ictxFileNum+"'";
		
		Stream<Integer> stream = jdbcTemplate.queryForStream(query, (rs,
				rowNum) ->rs.getInt(1));
		List<Integer> list = stream.collect(Collectors.toList());
		
		

		if (list.isEmpty()) {
			dao_log.info("ATP File Id is not available in T_IA_FILE_STATS");
			return 0;
		}else {
		dao_log.info("ICTX INPUT_COUNT  ======>" + list.get(0));
			return list.get(0);
		}
		
	}
	
	@Override
	public String getICTXFileName(String ictxFileNum) {
		
		String query= "SELECT ICTX_FILE_NAME FROM TPMS.T_IA_FILE_STATS WHERE FILE_SEQ_NUMBER = '"+ictxFileNum+"'";
		try {
		Stream<String> stream = jdbcTemplate.queryForStream(query, (rs,
				rowNum) ->rs.getString(1));
		List<String> list = stream.collect(Collectors.toList());
		
		

		if (list.isEmpty()) {
			dao_log.info("File Num is not available in T_IA_FILE_STATS");
			return "";
		}else {
		dao_log.info("ICTX_FILE_NAME  ======>" + list.get(0));
			return list.get(0);
		}
		}catch(Exception e) {
			e.printStackTrace();
			dao_log.info("Error While Fetching Transaction File::" + e.getMessage());
			return "";
		}
	}

	@Override
	public Boolean checkIfFileProcessedAlreadyBy(String strSerialNum, FileProcessStatus processStatus) {

		try
		{
			String strStatus = processStatus.getCode();
			Long serialNum = Long.parseLong(strSerialNum);
			MapSqlParameterSource paramSource = new MapSqlParameterSource();
			String queryToCheckFile = "SELECT SERIAL_NUMBER  FROM TPMS.T_LANE_TX_CHECKPOINT WHERE SERIAL_NUMBER='"+serialNum+"' AND PROCESS_STATUS='"+strStatus+"'";
			dao_log.info("queryToCheckFile  ======>" + queryToCheckFile);
			Stream<Long> stream = jdbcTemplate.queryForStream(queryToCheckFile, (rs,
					rowNum) ->rs.getLong(1));
			List<Long> list = stream.collect(Collectors.toList());
				
			if(list.size()>0) {
				return true;
			}else {
				return false;
			}
		}
		catch(Exception ex)
		{
			dao_log.info("Error While checkIfFileProcessedAlreadyBy::" + ex.getMessage());
			return false;
		}
	
	}


}