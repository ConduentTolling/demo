package com.conduent.tpms.iag.dao.impl;

import java.sql.Date;



import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
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

import com.conduent.tpms.iag.config.LoadJpaQueries;
import com.conduent.tpms.iag.constants.FileProcessStatus;
import com.conduent.tpms.iag.constants.ICLPConstants;
import com.conduent.tpms.iag.dao.TQatpMappingDao;
import com.conduent.tpms.iag.dto.AckFileInfoDto;
import com.conduent.tpms.iag.dto.FileInfoDto;
import com.conduent.tpms.iag.dto.FileParserParameters;
import com.conduent.tpms.iag.dto.ICLPDetailInfoVO;
import com.conduent.tpms.iag.dto.MappingInfoDto;
import com.conduent.tpms.iag.model.AgencyInfoVO;
import com.conduent.tpms.iag.model.FileDetails;
import com.conduent.tpms.iag.model.LicPlateDetails;
import com.conduent.tpms.iag.model.XferControl;
import com.conduent.tpms.iag.utility.GenericValidation;
import com.conduent.tpms.iag.validation.GenericICLPFileParserImpl;

import io.vavr.collection.Iterator;


@Repository
public class TQatpMappingDaoImpl implements TQatpMappingDao {

	private static final Logger dao_log = LoggerFactory.getLogger(TQatpMappingDaoImpl.class);

	@Autowired
	private NamedParameterJdbcTemplate namedJdbcTemplate;

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	/*
	 * @Autowired GenericValidation genericValidation;
	 */

	
	
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
			dao_log.info("No record found in T_LANE_TX_CHECKPOINT table for file name : {}",fileName);
			return null;
		}
	}


	@Override
	public Long insertFileDetails(FileDetails fileDetails) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		String query = LoadJpaQueries.getQueryById("INSERT_INTO_FILE_CHECKPOINT");

		MapSqlParameterSource paramSource = null;

		paramSource = new MapSqlParameterSource();
		paramSource.addValue(ICLPConstants.FILE_NAME, fileDetails.getFileName());
		paramSource.addValue(ICLPConstants.FILE_TYPE, fileDetails.getFileType());
		paramSource.addValue(ICLPConstants.PROCESS_NAME, fileDetails.getProcessName());
		paramSource.addValue(ICLPConstants.PROCESS_ID, fileDetails.getProcessId());
		paramSource.addValue(ICLPConstants.PROCESS_STATUS, fileDetails.getProcessStatus().getCode());
		paramSource.addValue(ICLPConstants.TX_DATE, fileDetails.getTxDate());
		paramSource.addValue(ICLPConstants.LANE_TX_ID, fileDetails.getLaneTxId());
		paramSource.addValue(ICLPConstants.SERIAL_NUMBER, fileDetails.getSerialNumber());
		paramSource.addValue(ICLPConstants.FILE_COUNT, fileDetails.getFileCount());
		paramSource.addValue(ICLPConstants.PROCESSED_COUNT, fileDetails.getProcessedCount());
		paramSource.addValue(ICLPConstants.SUCCESS_COUNT, fileDetails.getSuccessCount());
		paramSource.addValue(ICLPConstants.EXCEPTION_COUNT, fileDetails.getExceptionCount());
		paramSource.addValue(ICLPConstants.UPDATE_TS, Timestamp.valueOf(fileDetails.getUpdateTs()));

		namedJdbcTemplate.update(query, paramSource, keyHolder, new String[] { ICLPConstants.ID });
		Map<String, Object> map = keyHolder.getKeys();
		Long id = null;
		if (null != map) {
			id = Long.parseLong(map.get(ICLPConstants.ID).toString());
		}
		dao_log.info("File checkpoint Details Inserted with Id= {}", id);

		return id;
	}

	@Override
	public int[] insertPlateDetails(List<LicPlateDetails> tagDeviceDetailsList) {
		int[] updatedRecords = batchInsert(tagDeviceDetailsList);
		return updatedRecords;
	}

	public int[] batchInsert(List<LicPlateDetails> list) {

		dao_log.debug("Starting batch insert for list of tag devices..");

		String insertQuery = LoadJpaQueries.getQueryById("INSERT_INTO_T_OA_PLATES");
		int[] updateCounts = jdbcTemplate.batchUpdate(insertQuery, new BatchPreparedStatementSetter() {
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				
				
				
				ps.setString(1, list.get(i).getDeviceNo());
				ps.setString(2, list.get(i).getPlateState());
				ps.setString(3, list.get(i).getPlateNumber());
				//ps.setInt(4, list.get(i).getPlateType());
				ps.setString(4, list.get(i).getPlateType());
				ps.setString(5,list.get(i).getLicHomeAgency());
				ps.setString(6, list.get(i).getLicAccntNo());
				ps.setString(7,list.get(i).getLicVin());
				ps.setString(8,list.get(i).getLicGuaranteed());
				//ps.setString(9, list.get(i).getLicEffectiveFrom());
				//ps.setString(10, list.get(i).getLicEffectiveTo());
				//ps.setTimestamp(9, list.get(i).getLicEffectiveFrom());
				//ps.setTimestamp(10, list.get(i).getLicEffectiveTo());
				ps.setTimestamp(9, Timestamp.valueOf(list.get(i).getLicEffectiveFrom()));
				ps.setTimestamp(10, Timestamp.valueOf(list.get(i).getLicEffectiveTo()));
				ps.setDate(11, Date.valueOf(list.get(i).getStartDate()));
				ps.setDate(12, Date.valueOf(list.get(i).getEndDate()));
				ps.setLong(13,list.get(i).getXferControlId());
				ps.setTimestamp(14, Timestamp.valueOf(list.get(i).getUpdateDeviceTs()));
				ps.setTimestamp(15, Timestamp.valueOf(list.get(i).getLastFileTS()));
				ps.setString(16, list.get(i).getRecord());
				
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
		FileParserParameters fileDetails = new FileParserParameters();
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		String queryFile = LoadJpaQueries.getQueryById("GET_FILE_INFO_CONFIGURATION");

		paramSource.addValue(ICLPConstants.FILE_TYPE, fileType);
		paramSource.addValue(ICLPConstants.AGENCY_ID, ICLPConstants.MTA_AGENCY_ID);
		paramSource.addValue(ICLPConstants.FILE_FORMAT, ICLPConstants.FIXED);

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

		return fileDetails;

	}

	private void getMinMaxListValues(List<MappingInfoDto> fileNameMappingDetails) {
		for (MappingInfoDto infoRow : fileNameMappingDetails) {
			if (ICLPConstants.FIXED_VALUE.equals(infoRow.getValidationType())
					|| ICLPConstants.DATE.equals(infoRow.getValidationType())) {
				infoRow.setFixeddValidValue(infoRow.getValidationValue());
			} else if (ICLPConstants.RANGE.equals(infoRow.getValidationType())) {
				String[] rangeValue = infoRow.getValidationValue().replaceAll("\\s", "").split("-");
				infoRow.setMinRangeValue(Long.valueOf(rangeValue[0]));
				infoRow.setMaxRangeValue(Long.valueOf(rangeValue[1]));
			} else if (ICLPConstants.LOV.equals(infoRow.getValidationType())) {
				List<String> listOfValue = Arrays.asList(infoRow.getValidationValue().split(","));
				infoRow.setListOfValidValues(listOfValue);
			}
		}
	}

	
	@Override
	public XferControl checkFileDownloaded(String fileName) {
		
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		String queryToCheckFile = "SELECT XFER_CONTROL_ID, XFER_FILE_ID, XFER_FILE_NAME, DATE_CREATED, TIME_CREATED, FILE_SIZE, FILE_CHECKSUM, NUM_RECS, HASH_TOTAL, XFER_PATH, XFER_XMIT_STATUS, XFER_RETRY, UPDATE_TS, FILE_TYPE  FROM MASTER.T_XFER_CONTROL WHERE XFER_FILE_NAME='"+fileName+"' AND XFER_XMIT_STATUS ='C'";
		List<XferControl> listOfObj = namedJdbcTemplate.query(queryToCheckFile, paramSource,BeanPropertyRowMapper.newInstance(XferControl.class));
		if(listOfObj.size()>0) {
			return listOfObj.get(0);
		}
		
		return null;
	}
	
	
	@Override
	public String getLastSuccesfulProcessedFile(String fromAgencyId2) {
		fromAgencyId2 = fromAgencyId2 + "%";
		
		String queryToGetFileName = "SELECT FILE_NAME FROM  (SELECT * from TPMS.T_LANE_TX_CHECKPOINT WHERE FILE_TYPE='ICLP' AND PROCESS_STATUS='C' AND  FILE_NAME LIKE '"
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
		KeyHolder keyHolder = new GeneratedKeyHolder();
		String query = LoadJpaQueries.getQueryById("UPDATE_FILE_STATUS_INTO_CHECKPOINT");

		MapSqlParameterSource paramSource = new MapSqlParameterSource();

		paramSource.addValue(ICLPConstants.FILE_NAME, fileDetails.getFileName());
		paramSource.addValue(ICLPConstants.FILE_TYPE, fileDetails.getFileType());
		paramSource.addValue(ICLPConstants.PROCESS_STATUS, fileDetails.getProcessStatus().getCode());
		paramSource.addValue(ICLPConstants.FILE_COUNT, fileDetails.getFileCount());
		paramSource.addValue(ICLPConstants.UPDATE_TS, Timestamp.valueOf(fileDetails.getUpdateTs()));
		paramSource.addValue(ICLPConstants.PROCESSED_COUNT, fileDetails.getProcessedCount());
		paramSource.addValue(ICLPConstants.SUCCESS_COUNT, fileDetails.getSuccessCount());
		paramSource.addValue(ICLPConstants.EXCEPTION_COUNT, fileDetails.getExceptionCount());

		namedJdbcTemplate.update(query, paramSource, keyHolder, new String[] { ICLPConstants.ID });
		Map<String, Object> map = keyHolder.getKeys();
		Long id = null;
		if (null != map) {
			id = Long.parseLong(map.get(ICLPConstants.ID).toString());
		}
		dao_log.info("File checkpoint Details updated with Id= {}", id);

	}

	@Override
	public void insertAckDetails(AckFileInfoDto ackFileInfoDto) {

		String query = LoadJpaQueries.getQueryById("INSERT_FILE_ACK_INTO_ACK_TABLE");
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue(ICLPConstants.ACK_FILE_NAME, ackFileInfoDto.getAckFileName());
		paramSource.addValue(ICLPConstants.ACK_FILE_DATE, ackFileInfoDto.getAckFileDate());
		paramSource.addValue(ICLPConstants.ACK_FILE_TIME, ackFileInfoDto.getAckFileTime());
		paramSource.addValue(ICLPConstants.ACK_FILE_STATUS, Integer.parseInt(ackFileInfoDto.getAckFileStatus()));
		paramSource.addValue(ICLPConstants.TRX_FILE_NAME, ackFileInfoDto.getTrxFileName());
		paramSource.addValue(ICLPConstants.RECON_FILE_NAME, ackFileInfoDto.getReconFileName());
		paramSource.addValue(ICLPConstants.FILE_TYPE, ackFileInfoDto.getFileType());
		paramSource.addValue(ICLPConstants.FROM_AGENCY, ackFileInfoDto.getFromAgency());
		paramSource.addValue(ICLPConstants.TO_AGENCY, ackFileInfoDto.getToAgency());
		paramSource.addValue(ICLPConstants.EXTERN_FILE_ID, ackFileInfoDto.getExternFileId());
		paramSource.addValue(ICLPConstants.ATP_FILE_ID, ackFileInfoDto.getAtpFileId());
		int id = namedJdbcTemplate.update(query, paramSource);

		dao_log.info("File Details Inserted with Id= {}", id);

	}

	@Override
	public List<AgencyInfoVO> getAgencyDetails() {
		
		String queryRules =	LoadJpaQueries.getQueryById("SELECT_INFO_FROM_T_AGENCY");
		
		dao_log.info("Agency info fetched from T_Agency table successfully.");
		
		List<AgencyInfoVO> list= jdbcTemplate.query(queryRules, new BeanPropertyRowMapper<AgencyInfoVO>(AgencyInfoVO.class));
return list;
	}

	
	/*
	 * public List<ICLPDetailInfoVO> getDiffRecordsFromExtTables() {
	 * 
	 * String query = LoadJpaQueries.getQueryById("GET_DIFFERENCE_OF_RECORDS");
	 * 
	 * Stream<ICLPDetailInfoVO> stream = jdbcTemplate.queryForStream(query, (rs,
	 * rowNum) -> new ICLPDetailInfoVO(rs.getString(1), rs.getString(2),
	 * rs.getString(3), rs.getString(4),rs.getString(5)));
	 * 
	 * List<ICLPDetailInfoVO> list = stream.collect(Collectors.toList());
	 * 
	 * return list; }
	 */
	
	public List<ICLPDetailInfoVO> getDiffRecordsFromExtTables_ICLP(String newAwaytagBucket, String oldAwaytagBucket) {
		 DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");

		 
		 
		String query = "select LIC_STATE,LIC_NUMBER,LIC_TYPE, DEVICE_NO, LIC_EFFECTIVE_FROM, LIC_EFFECTIVE_TO , LIC_HOME_AGENCY , LIC_ACCOUNT_NO , LIC_VIN , LIC_GUARANTEED, 'NEW' as filetype"
				+ "					from  (select LIC_STATE,LIC_NUMBER,LIC_TYPE, DEVICE_NO , LIC_EFFECTIVE_FROM, LIC_EFFECTIVE_TO , LIC_HOME_AGENCY , LIC_ACCOUNT_NO , LIC_VIN , LIC_GUARANTEED"
				+ "					         from "+newAwaytagBucket+" minus"
				+ "					      select LIC_STATE,LIC_NUMBER,LIC_TYPE, DEVICE_NO , LIC_EFFECTIVE_FROM, LIC_EFFECTIVE_TO , LIC_HOME_AGENCY , LIC_ACCOUNT_NO , LIC_VIN , LIC_GUARANTEED"
				+ "					         from "+oldAwaytagBucket+" ) x"
				+ "					union"
				+ "					select LIC_STATE,LIC_NUMBER,LIC_TYPE, DEVICE_NO, LIC_EFFECTIVE_FROM, LIC_EFFECTIVE_TO , LIC_HOME_AGENCY , LIC_ACCOUNT_NO , LIC_VIN , LIC_GUARANTEED , 'OLD' as filetype"
				+ "					from ( select LIC_STATE,LIC_NUMBER,LIC_TYPE, DEVICE_NO, LIC_EFFECTIVE_FROM, LIC_EFFECTIVE_TO , LIC_HOME_AGENCY , LIC_ACCOUNT_NO , LIC_VIN , LIC_GUARANTEED from "+oldAwaytagBucket
				+ "					    minus"
				+ "					      select LIC_STATE,LIC_NUMBER,LIC_TYPE, DEVICE_NO , LIC_EFFECTIVE_FROM, LIC_EFFECTIVE_TO , LIC_HOME_AGENCY , LIC_ACCOUNT_NO , LIC_VIN , LIC_GUARANTEED from "+newAwaytagBucket+")x"
				+ "					order by LIC_NUMBER,DEVICE_NO,filetype desc";

		
		
		
		Stream<ICLPDetailInfoVO> stream = jdbcTemplate.queryForStream(query, (rs,
			rowNum) -> new ICLPDetailInfoVO(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), datetimefromtb(rs.getString(5)), datetimefromtb(rs.getString(6)), rs.getString(7), rs.getString(8), rs.getString(9),rs.getString(10),rs.getString(11)));
		
		
		//System.out.println(list2);
		//LocalDateTime.parse(rs.getString(6)
		
		
		List<ICLPDetailInfoVO> list = stream.collect(Collectors.toList());
		dao_log.info(list.toString());
		return list;
	}
	
	
	public LocalDateTime datetimefromtb(String value) {
		
		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
		
		boolean dateval = validate(value);
		
		
		if(dateval) {
			//LocalDateTime dt4= LocalDateTime.of(2099, Month.DECEMBER, 31, 00, 00, 01, 123456789);
			//String dt5= dt4.toString().substring(0, 19).concat("Z");
			//LocalDateTime licefftod =getFormattedDateTimeNew(dt5);
			//LocalDateTime licefftod= genericValidation.getFormattedDateTimeNew(dt5);
			//return licefftod;
			LocalDateTime dt9=LocalDateTime.now();
			LocalDateTime licefftod= LocalDateTime.parse(dt9.toString().substring(0, 19).concat("Z"), dateFormatter);
			return licefftod;
			
		}
		else {
			try {
				return LocalDateTime.parse(value, dateFormatter);
			}catch(DateTimeParseException e) {
			        return null;
			}
			//return LocalDateTime.parse(value, dateFormatter);
		}
		
		
		
	}
	
    public LocalDateTime datetimefromtbto(String value) {
		
		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
		
		boolean dateval = validate(value);
		
		if(dateval) {
			//LocalDateTime dt4= LocalDateTime.of(2099, Month.DECEMBER, 31, 00, 00, 01, 123456789);
			//String dt5= dt4.toString().substring(0, 19).concat("Z");
			//LocalDateTime licefftod =getFormattedDateTimeNew(dt5);
			//LocalDateTime licefftod= genericValidation.getFormattedDateTimeNew(dt5);
			//return licefftod;
			//LocalDateTime dt9=LocalDateTime.now();
			//LocalDateTime licefftod= LocalDateTime.parse(dt9.toString().substring(0, 19).concat("Z"), dateFormatter);
			
			
			LocalDateTime dt4= LocalDateTime.of(2099, Month.DECEMBER, 31, 00, 00, 01, 123456789);
			String dt5= dt4.toString().substring(0, 19).concat("Z");
			LocalDateTime liceffto= LocalDateTime.parse(dt5, dateFormatter);
			
			
			return liceffto;
			
		}
		else {
			return LocalDateTime.parse(value, dateFormatter);
		}
		
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public  LocalDateTime getFormattedDateTimeNew(String dateValue) {
		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
      
        return  LocalDateTime.parse(dateValue, dateFormatter); 	
		
		
	}
	
	public boolean validate(String value)
    {
        String matcher_String = "\\s{"+value.length()+"}|[*]{"+value.length()+"}";
        if(value.matches(matcher_String)) 
        {
            return true;
        }
        else
        {
            return false;
        }
    }
	
	public List<ICLPDetailInfoVO> getDataFromNewExternTable(String newBucket) {

		String query = "select LIC_STATE,LIC_NUMBER,DEVICE_NO,LIC_TYPE,LIC_ACCOUNT_NO from "+newBucket;
		
		Stream<ICLPDetailInfoVO> stream = jdbcTemplate.queryForStream(query, (rs,
				rowNum) -> new ICLPDetailInfoVO(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),rs.getString(5)));
		
		List<ICLPDetailInfoVO> list = stream.collect(Collectors.toList());
		dao_log.info("Device list from {} table: {}",newBucket, list.toString());
		
		return list;
	}
	

	/*
	 * public List<ICLPDetailInfoVO> getDataFromExternTable(String
	 * externalTableName) {
	 * 
	 * String query =
	 * "select LIC_STATE,LIC_NUMBER,DEVICE_NO,LIC_TYPE,LIC_ACCOUNT_NO from "
	 * +externalTableName;
	 * 
	 * Stream<ICLPDetailInfoVO> stream = jdbcTemplate.queryForStream(query, (rs,
	 * rowNum) -> new ICLPDetailInfoVO(rs.getString(1), rs.getString(2),
	 * rs.getString(3), rs.getString(4)));
	 * 
	 * List<ICLPDetailInfoVO> list = stream.collect(Collectors.toList());
	 * 
	 * return list; }
	 */

	@Override
	public void updateLicPlateDetails(LicPlateDetails licPlateDetails) { 
		jdbcTemplate.update(
                "update TPMS.T_OA_PLATES set END_DATE = ?,UPDATE_TS =?,LAST_FILE_TS=?,LIC_EFFECTIVE_FROM =? , LIC_EFFECTIVE_TO=?  , LIC_HOME_AGENCY=? , LIC_ACCOUNT_NO=? , LIC_VIN =?, LIC_GUARANTEED=?  where DEVICE_NO = ? and PLATE_STATE = ? and PLATE_NUMBER =? AND PLATE_TYPE=?", 
                licPlateDetails.getEndDate(), licPlateDetails.getUpdateDeviceTs(),licPlateDetails.getLastFileTS(),
                licPlateDetails.getLicEffectiveFrom(), licPlateDetails.getLicEffectiveTo() ,licPlateDetails.getLicHomeAgency() , licPlateDetails.getLicAccntNo() ,licPlateDetails.getLicVin() , licPlateDetails.getLicGuaranteed(),
                licPlateDetails.getDeviceNo(),licPlateDetails.getPlateState(),licPlateDetails.getPlateNumber(),licPlateDetails.getPlateType());
		
		dao_log.info("Updated License plate details for device {}",licPlateDetails.getDeviceNo());
		
	}
	
	public List<ICLPDetailInfoVO> getDataFromOldExternTable(String oldBucket) {

		String query = "select LIC_STATE,LIC_NUMBER,DEVICE_NO,LIC_TYPE,LIC_ACCOUNT_NO from "+oldBucket;
		
		Stream<ICLPDetailInfoVO> stream = jdbcTemplate.queryForStream(query, (rs,
				rowNum) -> new ICLPDetailInfoVO(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),rs.getString(5)));
		
		List<ICLPDetailInfoVO> list = stream.collect(Collectors.toList());
		dao_log.info("Device list from {} table: {}",oldBucket, list.toString());
		return list;
	}

	@Override
	public LocalDate getIfDeviceExistForFuture(ICLPDetailInfoVO licPlateDetails) {
		
		LocalDate enddate = null;
		
		
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue(ICLPConstants.DEVICE_NO, licPlateDetails.getDeviceNo());
		//paramSource.addValue(ICLPConstants.PLATE_TYPE,licPlateDetails.getLicType().equals(ICLPConstants.UNUSED_LIC_PLATE) ? ICLPConstants.ZERO_VALUE : licPlateDetails.getLicType().trim());
		paramSource.addValue(ICLPConstants.PLATE_STATE, licPlateDetails.getLicState());
		paramSource.addValue(ICLPConstants.PLATE_NUMBER, licPlateDetails.getLicNumber().trim());
		
		//AND PLATE_TYPE=:PLATE_TYPE
		
		/*
		 * if(licPlateDetails.getLicType()==null) { queryToCheckFile=
		 * "select end_date from TPMS.T_OA_PLATES where DEVICE_NO=:DEVICE_NO AND PLATE_STATE=:PLATE_STATE  AND PLATE_NUMBER=:PLATE_NUMBER AND PLATE_TYPE is null order by start_date DESC"
		 * ; }else { queryToCheckFile =
		 * "select end_date from TPMS.T_OA_PLATES where DEVICE_NO=:DEVICE_NO AND PLATE_STATE=:PLATE_STATE  AND PLATE_NUMBER=:PLATE_NUMBER AND PLATE_TYPE=:PLATE_TYPE order by start_date DESC"
		 * ; }
		 */
		
		String queryToCheckFile  = "select end_date from TPMS.T_OA_PLATES where DEVICE_NO=:DEVICE_NO AND PLATE_STATE=:PLATE_STATE  AND PLATE_NUMBER=:PLATE_NUMBER  order by start_date DESC";
			
		List<LicPlateDetails> listOfObj = namedJdbcTemplate.query(queryToCheckFile, paramSource,BeanPropertyRowMapper.newInstance(LicPlateDetails.class));
		
		if(listOfObj.size()>0) {
			if(listOfObj.get(0).getEndDate().getYear() < 1900) {
			enddate = listOfObj.get(0).getEndDate().plusYears(100);
			}else {
				enddate = listOfObj.get(0).getEndDate();
			}
		}
		return enddate;
	}

	/*
	 * @Override public void updateDuplicateLicPlateDetails(LicPlateDetails
	 * licPlateDetails, LocalDate endDate) {
	 * 
	 * jdbcTemplate.update(
	 * "update TPMS.T_OA_PLATES set UPDATE_TS =?,LAST_FILE_TS=?, END_DATE=?, LIC_EFFECTIVE_FROM =? , LIC_EFFECTIVE_TO=?  , LIC_HOME_AGENCY=? , LIC_ACCOUNT_NO=? , LIC_VIN =?, LIC_GUARANTEED=? where DEVICE_NO = ? and PLATE_STATE = ? and PLATE_NUMBER =? and END_DATE =? AND PLATE_TYPE=:PLATE_TYPE"
	 * , licPlateDetails.getUpdateDeviceTs(),licPlateDetails.getLastFileTS(),
	 * licPlateDetails.getEndDate(),licPlateDetails.getLicEffectiveFrom(),
	 * licPlateDetails.getLicEffectiveTo() ,licPlateDetails.getLicHomeAgency() ,
	 * licPlateDetails.getLicAccntNo() ,licPlateDetails.getLicVin() ,
	 * licPlateDetails.getLicGuaranteed(),
	 * licPlateDetails.getDeviceNo(),licPlateDetails.getPlateState(),licPlateDetails
	 * .getPlateNumber() ,endDate, licPlateDetails.getPlateType());
	 * 
	 * dao_log.info("Updated Tag device details for tag {}",licPlateDetails.
	 * getDeviceNo()); }
	 */

   @Override
   public void updateDuplicateLicPlateDetails(LicPlateDetails licPlateDetails, LocalDate endDate) {
		
	  
	   
	   //AND PLATE_TYPE=:PLATE_TYPE
	   //licPlateDetails.getPlateType()
	   
	   if(licPlateDetails.getPlateType()==null) {
		   jdbcTemplate.update(
	                "update TPMS.T_OA_PLATES set  END_DATE=?      where DEVICE_NO = ? and PLATE_STATE = ? and PLATE_NUMBER =? and END_DATE =? AND PLATE_TYPE is null ", 
	              
	                licPlateDetails.getStartDate().minusDays(1),   
	                licPlateDetails.getDeviceNo(),licPlateDetails.getPlateState(),licPlateDetails.getPlateNumber() ,endDate);
	   }else {
		   jdbcTemplate.update(
	                "update TPMS.T_OA_PLATES set  END_DATE=?      where DEVICE_NO = ? and PLATE_STATE = ? and PLATE_NUMBER =? and END_DATE =? AND PLATE_TYPE=:PLATE_TYPE  ", 
	              
	                licPlateDetails.getStartDate().minusDays(1),   
	                licPlateDetails.getDeviceNo(),licPlateDetails.getPlateState(),licPlateDetails.getPlateNumber() ,endDate,licPlateDetails.getPlateType());
			
	   }
	   
		
		dao_log.info("Updated Tag device details for tag {}",licPlateDetails.getDeviceNo());
	}
	
   @Override
   public void updateDuplicateLicPlateDetailsForOld(LicPlateDetails licPlateDetails, LocalDate endDate) {
		
	   
	   if(licPlateDetails.getPlateType()==null) {
		   jdbcTemplate.update(
	                "update TPMS.T_OA_PLATES set  END_DATE=?      where DEVICE_NO = ? and PLATE_STATE = ? and PLATE_NUMBER =? and END_DATE =? AND PLATE_TYPE is null", 
	              
	                licPlateDetails.getStartDate(),   
	                licPlateDetails.getDeviceNo(),licPlateDetails.getPlateState(),licPlateDetails.getPlateNumber() ,endDate);
	   }else {
		   
		   jdbcTemplate.update(
	                "update TPMS.T_OA_PLATES set  END_DATE=?      where DEVICE_NO = ? and PLATE_STATE = ? and PLATE_NUMBER =? and END_DATE =? AND PLATE_TYPE=:PLATE_TYPE", 
	              
	                licPlateDetails.getStartDate(),   
	                licPlateDetails.getDeviceNo(),licPlateDetails.getPlateState(),licPlateDetails.getPlateNumber() ,endDate, licPlateDetails.getPlateType());
	   }
		
		
		dao_log.info("Updated Tag device details for tag {}",licPlateDetails.getDeviceNo());
	}
   

	@Override
	public List<ICLPDetailInfoVO> getDataFromExternTable1(String newAwaytagBucket) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public int getThresholdValue(String paramname) {
		// TODO Auto-generated method stub
		int thresholdValue =0;
		try {

			MapSqlParameterSource paramSource = new MapSqlParameterSource();
		     paramSource.addValue("param_name", paramname);
			
			String queryForprocessparam = LoadJpaQueries.getQueryById("SELECT_PROCESS_PARAMETER_THRESHOLD");
 
			try {
				
				 thresholdValue =namedJdbcTemplate.queryForObject(queryForprocessparam, paramSource, Integer.class);

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
	public List<String> getLicState() {
		// TODO Auto-generated method stub
		
  String queryRules =	LoadJpaQueries.getQueryById("SELECT_INFO_FROM_T_STATE");
		
		dao_log.info("State info fetched from T_STATE table successfully.");
		
		//List<String> list= jdbcTemplate.query(queryRules, new BeanPropertyRowMapper<String>(String.class));
		List<String> list =jdbcTemplate.queryForList(queryRules, String.class);
  return list;
		
	}


	@Override
	public String getAgencyid(String homeAgencyID) {
		// TODO Auto-generated method stub
		
		int agencyid=0;
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
	    paramSource.addValue("param_name", homeAgencyID);
		
		String queryForagencyid = LoadJpaQueries.getQueryById("SELECT_AGENCYID_FROM_T_AGENCY");
		
		try {
			
			 agencyid= namedJdbcTemplate.queryForObject(queryForagencyid, paramSource, Integer.class);

		} catch (EmptyResultDataAccessException empty) {
			empty.printStackTrace();
		}
		
		String agencyID= String.valueOf(agencyid);
			
		return agencyID;
		
	}
	
	
	

	
	
	
	}

