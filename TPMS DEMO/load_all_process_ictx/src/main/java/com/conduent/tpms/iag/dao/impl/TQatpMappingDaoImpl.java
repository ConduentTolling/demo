package com.conduent.tpms.iag.dao.impl;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

import com.conduent.tpms.iag.config.LoadJpaQueries;
import com.conduent.tpms.iag.constants.FileProcessStatus;
import com.conduent.tpms.iag.constants.ICTXConstants;
import com.conduent.tpms.iag.dao.TQatpMappingDao;
import com.conduent.tpms.iag.dto.FileInfoDto;
import com.conduent.tpms.iag.dto.FileParserParameters;
import com.conduent.tpms.iag.dto.MappingInfoDto;
import com.conduent.tpms.iag.exception.FileAlreadyProcessedException;
import com.conduent.tpms.iag.model.FileDetails;


@Repository
public class TQatpMappingDaoImpl implements TQatpMappingDao {

	private static final Logger log = LoggerFactory.getLogger(TQatpMappingDaoImpl.class);

	@Autowired
	private NamedParameterJdbcTemplate namedJdbcTemplate;

	@Autowired
	private JdbcTemplate jdbcTemplate;


	/**
	 * File already processed or not
	 * @throws FileAlreadyProcessedException 
	 */
	@Override
	public FileDetails checkIfFileProcessedAlready(String fileName) throws FileAlreadyProcessedException {

		try
		{
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		String queryToCheckFile = "SELECT * FROM T_LANE_TX_CHECKPOINT WHERE FILE_NAME='"+fileName+"'";
		
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

	/**
	 * Inserting address in address table
	 */
//	@Override
//	public void insertaddressinaddresstable(int address_id) {
//		
//		String query = LoadJpaQueries.getQueryById("INSERT_ADDRESS_ID_INTO_ADDRESS");
//		
//		MapSqlParameterSource paramSource = null;
//		
//		paramSource = new MapSqlParameterSource();
//		paramSource.addValue(IITCConstants.INVALID_ADDRESS_ID,address_id);
//		int addressid = namedJdbcTemplate.update(query, paramSource);
//		
//		log.info("Address_id inserted into address table"+addressid);
//		
//	}
//	
	/**
	 * Inserting file details
	 * @param file details 
	 * @return id
	 */
	@Override
	public Long insertFileDetails(FileDetails fileDetails) {

		log.debug("IITC file {} details to insert into checkpoint table....", fileDetails.toString());

		log.info("Inserting IITC file {} details into checkpoint table....", fileDetails.getFileName());

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
		paramSource.addValue(ICTXConstants.UPDATE_TS, Timestamp.valueOf(fileDetails.getUpdateTs()));

		namedJdbcTemplate.update(query, paramSource, keyHolder, new String[] { ICTXConstants.ID });
		Map<String, Object> map = keyHolder.getKeys();
		Long id = null;
		if (null != map) {
			id = Long.parseLong(map.get(ICTXConstants.ID).toString());
		}
		log.info("File {} details inserted into checkpoint with status code {}.", fileDetails.getFileName(),fileDetails.getProcessStatus().getCode());

		return id;
	}


	/**
	 * Mapping from tables
	 * @param file type, agency id , file format
	 * @return file details
	 */
	@Override
	public FileParserParameters getMappingConfigurationDetails(String fileType) {

		FileParserParameters fileDetails = new FileParserParameters();
		try {
			log.info("Getting mapping configuration from table..");

			fileDetails = new FileParserParameters();
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

			log.info("Collected mapping configuration from table..");
			log.info("File name mapping details collected from table : {}",fileDetails.getFileNameMappingInfo().toString());
			//return fileDetails;
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			log.info("Exception while fetching configuration..");
			e.printStackTrace();
		}
		return fileDetails;

	}

	private void getMinMaxListValues(List<MappingInfoDto> fileNameMappingDetails) {
		for (MappingInfoDto infoRow : fileNameMappingDetails) {
			if (ICTXConstants.FIXED_VALUE.equals(infoRow.getValidationType())
					|| ICTXConstants.DATE.equals(infoRow.getValidationType())) {
				infoRow.setFixeddValidValue(infoRow.getValidationValue());
			} else if (ICTXConstants.RANGE.equals(infoRow.getValidationType())) {
				String[] rangeValue = infoRow.getValidationValue().replaceAll("\\s", "").split("-");
				infoRow.setMinRangeValue(Long.valueOf(rangeValue[0]));
				infoRow.setMaxRangeValue(Long.valueOf(rangeValue[1]));
			} else if (ICTXConstants.LOV.equals(infoRow.getValidationType())) {
				List<String> listOfValue = Arrays.asList(infoRow.getValidationValue().split(","));
				infoRow.setListOfValidValues(listOfValue);
			}
		}
	}

	/**
	 * Last successful file
	 * @return list of file
	 */
//	@Override
//	public String getLastSuccesfulProcessedFile(String fromAgencyId2) {
//		fromAgencyId2 = fromAgencyId2 + "%";
//		String queryToGetFileName = "SELECT FILE_NAME FROM  (SELECT * from TPMS.T_LANE_TX_CHECKPOINT WHERE FILE_TYPE='IITC' AND PROCESS_STATUS='C' AND  FILE_NAME LIKE '"
//				+ fromAgencyId2 + "' ORDER BY  TX_DATE DESC)  WHERE ROWNUM < 2";
//		
//		Stream<String> stream = jdbcTemplate.queryForStream(queryToGetFileName, (rs,
//				rowNum) -> new String(rs.getString(1)));
//		List<String> list = stream.collect(Collectors.toList());
//
//		if (list.isEmpty()) {
//			return "";
//		}else {
//			return list.get(0);
//		}
//	}
	
	/**
	 * check if file downloaded
	 * @return list of obj if file is downloaded
	 */
//	@Override
//	public XferControl checkFileDownloaded(String fileName) {
//		MapSqlParameterSource paramSource = new MapSqlParameterSource();
//		String queryToCheckFile = "SELECT XFER_CONTROL_ID, XFER_FILE_ID, XFER_FILE_NAME, DATE_CREATED, TIME_CREATED, FILE_SIZE, FILE_CHECKSUM, NUM_RECS, HASH_TOTAL, XFER_PATH, XFER_XMIT_STATUS, XFER_RETRY, UPDATE_TS, FILE_TYPE  FROM T_XFER_CONTROL WHERE XFER_FILE_NAME='"+fileName+"' AND XFER_XMIT_STATUS ='C'";
//		List<XferControl> listOfObj = namedJdbcTemplate.query(queryToCheckFile, paramSource,BeanPropertyRowMapper.newInstance(XferControl.class));
//		if(listOfObj.size()>0) {
//			log.info("File present in XFER_CONTROL table : {}",listOfObj.get(0));
//			return listOfObj.get(0);
//		}
//		return null;
//	}
//	
//	/**
//	 * check if recpord exists
//	 * @return list of records
//	 */
//	@Override
//	public List<CustomerAddressRecord> checkifrecordexists() {
//		
//		String query = "select INVALID_ADDRESS_ID , FIRST_NAME , LAST_NAME , MIDDLE_INITIAL , COMPANY_NAME , STREET_1 , STREET_2 , CITY , STATE ,ZIP_CODE , ZIP_PLUS_4 from TPMS.T_OA_ADDRESS "; // select all fields
//	       
//        Stream<CustomerAddressRecord> stream = jdbcTemplate.queryForStream(query, (rs,
//                rowNum) -> new CustomerAddressRecord(rs.getInt(1) , rs.getString(2), rs.getString(3) ,
//                		rs.getString(4) , rs.getString(5) ,rs.getString(6) , rs.getString(7) , 
//                		rs.getString(8) , rs.getString(9) , rs.getString(10) , rs.getString(11)));
//       
//        List<CustomerAddressRecord> list = stream.collect(Collectors.toList());
//       
//        return list;
//		
//	}
	
	/**
	 * Getting zipcode
	 * 
	 * @return list of zipcode
	 */
//	@Override
//	public List<String> getZipCodeList() {
//		
//		String query = "select ZIPCODE from CRM.V_ZIPCODE";
//	       
//		List<String> list= jdbcTemplate.query(query, new BeanPropertyRowMapper<String>(String.class));
//		/*
//		 * Stream<String> stream = jdbcTemplate.queryForStream(query, (rs, rowNum) ->
//		 * new String(rs.getString(1)));
//		 * 
//		 * List<String> list = stream.collect(Collectors.toList());
//		 */
//        return list;
//	}
//	
//	/**
//	 * check if record exists in device
//	 * @param device no
//	 * @return device no details if present
//	 */
//	@Override
//	public TagDeviceDetails checkRecordExistInDevice(String device_no) {
//		
//		MapSqlParameterSource paramSource = new MapSqlParameterSource();
//		String querytocheckrecord = "select * from TPMS.T_OA_DEVICES WHERE DEVICE_NO='"+device_no+"' AND IAG_TAG_STATUS=3";
//		List<TagDeviceDetails> devicedetail = namedJdbcTemplate.query(querytocheckrecord, paramSource,BeanPropertyRowMapper.newInstance(TagDeviceDetails.class));
//		if(devicedetail.size()>0) {
//			return devicedetail.get(0);
//		}
//		return null;
//	}
//	
//	/**
//	 * update address id in devices
//	 * @param address id , device id
//	 * 
//	 */
//	@Override
//	public void updateInvalidAddressIdinDevices(String device_no , int address_id) {
//		
//		jdbcTemplate.update(
//				"update TPMS.T_OA_DEVICES set INVALID_ADDRESS_ID =? where DEVICE_NO = ? ",
//				address_id,device_no);
//				log.info("Updated invalid address id for Device No.: {}",device_no);
//		
//	}
//	
	/**
	 * Update file in checkpoint table
	 * @param file details
	 * 
	 */
	@Override
	public void updateFileIntoCheckpoint(FileDetails fileDetails) {

		log.info("Updating IITC file {} details into checkpoint table with process status: {}",
				fileDetails.getFileName(), fileDetails.getProcessStatus());

		KeyHolder keyHolder = new GeneratedKeyHolder();
		String query = LoadJpaQueries.getQueryById("UPDATE_FILE_STATUS_INTO_CHECKPOINT");

		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue(ICTXConstants.FILE_NAME, fileDetails.getFileName());
		paramSource.addValue(ICTXConstants.FILE_TYPE, fileDetails.getFileType());
		paramSource.addValue(ICTXConstants.PROCESS_STATUS, fileDetails.getProcessStatus().getCode());
		paramSource.addValue(ICTXConstants.FILE_COUNT, fileDetails.getFileCount());
		paramSource.addValue(ICTXConstants.UPDATE_TS, Timestamp.valueOf(fileDetails.getUpdateTs()));
		paramSource.addValue(ICTXConstants.PROCESSED_COUNT, fileDetails.getProcessedCount());
		paramSource.addValue(ICTXConstants.SUCCESS_COUNT, fileDetails.getSuccessCount());
		paramSource.addValue(ICTXConstants.EXCEPTION_COUNT, fileDetails.getExceptionCount());
		

		namedJdbcTemplate.update(query, paramSource, keyHolder, new String[] { ICTXConstants.ID });
		Map<String, Object> map = keyHolder.getKeys();
		Long id = null;
		if (null != map) {
			id = Long.parseLong(map.get(ICTXConstants.ID).toString());
		}
		log.info("File checkpoint Details Inserted with Id= {}", id);

	}
//	
//	/**
//	 * Inserting ack details
//	 * @param file details
//	 * 
//	 */
//	@Override
//	public void insertAckDetails(AckFileInfoDto ackFileInfoDto) {
//
//		log.info("Inserting Ack file details into ack table..");
//
//		String query = LoadJpaQueries.getQueryById("INSERT_FILE_ACK_INTO_ACK_TABLE");
//		MapSqlParameterSource paramSource = new MapSqlParameterSource();
//		paramSource.addValue(IITCConstants.ACK_FILE_NAME, ackFileInfoDto.getAckFileName());
//		paramSource.addValue(IITCConstants.ACK_FILE_DATE, ackFileInfoDto.getAckFileDate());
//		paramSource.addValue(IITCConstants.ACK_FILE_TIME, ackFileInfoDto.getAckFileTime());
//		paramSource.addValue(IITCConstants.ACK_FILE_STATUS, Integer.parseInt(ackFileInfoDto.getAckFileStatus()));
//		paramSource.addValue(IITCConstants.TRX_FILE_NAME, ackFileInfoDto.getTrxFileName());
//		paramSource.addValue(IITCConstants.RECON_FILE_NAME, ackFileInfoDto.getReconFileName());
//		paramSource.addValue(IITCConstants.FILE_TYPE, ackFileInfoDto.getFileType());
//		paramSource.addValue(IITCConstants.FROM_AGENCY, ackFileInfoDto.getFromAgency());
//		paramSource.addValue(IITCConstants.TO_AGENCY, ackFileInfoDto.getToAgency());
//		paramSource.addValue(IITCConstants.EXTERN_FILE_ID, ackFileInfoDto.getExternFileId());
//		paramSource.addValue(IITCConstants.ATP_FILE_ID, ackFileInfoDto.getAtpFileId());
//		int id = namedJdbcTemplate.update(query, paramSource);
//
//		log.info("Inserted Ack file {} details into ack table", ackFileInfoDto.getAckFileName());
//
//	}
//
//	/**
//	 * Inserting customer record
//	 * @param cutomers information
//	 * @return list of insertion
//	 */
//	@Override
//	public int[] insertInvalidCustomerRecord(List<CustomerAddressRecord> list) {
//
//
//		log.debug("Starting batch insertion for list of invalid tag customer..");
//
//		String insertQuery = LoadJpaQueries.getQueryById("INSERT_INTO_T_OA_ADDRESS");
//		int[] updateCounts = jdbcTemplate.batchUpdate(insertQuery, new BatchPreparedStatementSetter() 
//		{
//			public void setValues(PreparedStatement ps, int i) throws SQLException {
//				ps.setString(1, list.get(i).getFirstName());
//				ps.setString(2, list.get(i).getLastName());
//				ps.setString(3, list.get(i).getMiddleInitial());
//				ps.setString(4, list.get(i).getCompanyName());
//				ps.setString(5, list.get(i).getStreet1());
//				ps.setString(6, list.get(i).getStreet2());
//				ps.setString(7, list.get(i).getCity());
//				ps.setString(8, list.get(i).getState());
//				ps.setString(9, list.get(i).getZipCode());
//				ps.setString(10, list.get(i).getZipPlus4());
//				ps.setTimestamp(11, Timestamp.valueOf(list.get(i).getUpdateTs()));
//			}
//
//			public int getBatchSize() {
//				return list.size();
//			}
//		});
//		log.debug("Total invalid customers' addresses inserted: {}", updateCounts);
//		return updateCounts;
//	
//	}
//
//	/**
//	 * check if record exists
//	 * @param table columns
//	 * @return list of record
//	 */
//	@Override
//	public CustomerAddressRecord checkifrecordexists(CustomerAddressRecord record) {
//
//		
//		String query = "select INVALID_ADDRESS_ID , FIRST_NAME , LAST_NAME , MIDDLE_INITIAL , COMPANY_NAME , STREET_1 , STREET_2 , CITY , STATE ,ZIP_CODE , ZIP_PLUS_4 from TPMS.T_OA_ADDRESS WHERE "
//				+" FIRST_NAME='"+record.getFirstName()+"' AND LAST_NAME='"+record.getLastName()+"' AND MIDDLE_INITIAL='"+record.getMiddleInitial()+"' AND COMPANY_NAME='"+record.getCompanyName()+"' "
//						+ "AND STREET_1='"+record.getStreet1()+"' AND STREET_2='"+record.getStreet2()+"' AND CITY='"+record.getCity()+"' AND STATE='"+record.getState()+"' "
//								+ "AND ZIP_CODE='"+record.getZipCode()+"' AND ZIP_PLUS_4='"+record.getZipPlus4()+"' ORDER BY UPDATE_TS DESC"; 
//	       
//        Stream<CustomerAddressRecord> stream = jdbcTemplate.queryForStream(query, (rs,
//                rowNum) -> new CustomerAddressRecord(rs.getInt(1) , rs.getString(2), rs.getString(3) ,
//                		rs.getString(4) , rs.getString(5) ,rs.getString(6) , rs.getString(7) , 
//                		rs.getString(8) , rs.getString(9) , rs.getString(10) , rs.getString(11)));
//       
//        List<CustomerAddressRecord> list = stream.collect(Collectors.toList());
//       
//        if(list.size() > 0) {
//        	return list.get(0);
//        }else {
//        return null;
//        }
//	}
}
