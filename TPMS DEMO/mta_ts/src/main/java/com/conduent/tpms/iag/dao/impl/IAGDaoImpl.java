package com.conduent.tpms.iag.dao.impl;

import java.io.File;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.conduent.app.timezone.utility.TimeZoneConv;
import com.conduent.tpms.iag.config.LoadJpaQueries;
import com.conduent.tpms.iag.constants.Constants;
import com.conduent.tpms.iag.dao.IAGDao;
import com.conduent.tpms.iag.dto.FileCreationParameters;
import com.conduent.tpms.iag.dto.FileInfoDto;
import com.conduent.tpms.iag.dto.ITAGHeaderInfoVO;
import com.conduent.tpms.iag.dto.InterAgencyFileXferDto;
import com.conduent.tpms.iag.dto.MappingInfoDto;
import com.conduent.tpms.iag.model.Agency;
import com.conduent.tpms.iag.model.TAGDevice;
import com.conduent.tpms.iag.utility.IAGValidationUtils;

/**
 * Implementation class for IAGDao
 * 
 * @author Urvashi C
 *
 */	
@Repository
public class IAGDaoImpl implements IAGDao {
	
	private static final Logger log = LoggerFactory.getLogger(IAGDaoImpl.class);
	
	@Autowired
	private NamedParameterJdbcTemplate namedJdbcTemplate;
	
	@Autowired
	private JdbcTemplate  jdbcTemplate;
	
	@Autowired
	public IAGValidationUtils validationUtils;
	
	@Autowired
	TimeZoneConv timeZoneConv;
	
	@Override
	public FileCreationParameters getMappingConfigurationDetails(FileCreationParameters fileDto) {

		log.info("Getting mapping configuration from table..");
		FileCreationParameters fileDetails = new FileCreationParameters();
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		String queryFile = LoadJpaQueries.getQueryById("GET_OUTPUT_FILE_CONFIGURATION_INFO");
		try {
		
		paramSource.addValue(Constants.FILE_TYPE, fileDto.getFileType());
		paramSource.addValue(Constants.AGENCY_ID, fileDto.getAgencyId());
		paramSource.addValue(Constants.FILE_FORMAT, fileDto.getGenType());

		FileInfoDto info = namedJdbcTemplate.queryForObject(queryFile, paramSource,
				BeanPropertyRowMapper.newInstance(FileInfoDto.class));
		fileDetails.setFileInfoDto(info);
		log.debug("File info dto value: {}",fileDetails.getFileInfoDto().toString());

		String queryRules = LoadJpaQueries.getQueryById("GET_OUTPUT_FIELD_VALIDATION_DETAILS");

		List<MappingInfoDto> nameMappingDetails = namedJdbcTemplate.query(queryRules, paramSource,
				BeanPropertyRowMapper.newInstance(MappingInfoDto.class));

		List<MappingInfoDto> fileNameMappingDetails = nameMappingDetails.stream()
				.filter(f -> f.getFieldType().equals("FILENAME")).collect(Collectors.toList());
		log.debug(fileNameMappingDetails.toString());
		fileDetails.setFileNameMappingInfo(fileNameMappingDetails);

		List<MappingInfoDto> headerMappingDetails = nameMappingDetails.stream()
				.filter(f -> f.getFieldType().equals("HEADER")).collect(Collectors.toList());
		log.debug(headerMappingDetails.toString());
		fileDetails.setHeaderMappingInfoList(headerMappingDetails);

		List<MappingInfoDto> detailMappingDetails = nameMappingDetails.stream()
				.filter(f -> f.getFieldType().equals("DETAIL")).collect(Collectors.toList());
		log.debug(detailMappingDetails.toString());
		fileDetails.setDetailRecMappingInfo(detailMappingDetails);
		
		List<MappingInfoDto> trailerMappingDetails = nameMappingDetails.stream()
				.filter(f -> f.getFieldType().equals("TRAILER")).collect(Collectors.toList());
		log.debug(trailerMappingDetails.toString());
		fileDetails.setTrailerMappingInfoDto(trailerMappingDetails);

		log.info("Collected mapping configuration from table..");
		}catch (Exception e) {
			log.error("SQL query failed: {}", queryFile);
			e.printStackTrace();
		}
		return fileDetails;
	}

	/**
	 *Get device information from V_DEVICE table
	 *
	 *@return List<VDeviceDto>
	 */
	@Override
	public List<TAGDevice> getDevieListFromTTagAllSorted(int agencyId, String genType) {
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue(Constants.GEN_TYPE, genType);
		String queryRules = LoadJpaQueries.getQueryById("LOAD_DEVICES_FROM_T_TAG_STATUS_ALLSORTED");
		log.info("Agency info fetched from T_Agency table successfully.");
		return namedJdbcTemplate.query(queryRules, paramSource, BeanPropertyRowMapper.newInstance(TAGDevice.class));

	}
	
	@Override
	public String getLastDwnldTS() {
		String queryRules = LoadJpaQueries.getQueryById("SELECT_LAST_DOWNLOAD_TS_NEW");
		log.info("File Download TS fetched successfully.");
		try {
			String dwnldTS = (String) jdbcTemplate.queryForObject(queryRules, String.class);
			return dwnldTS;
		}catch(EmptyResultDataAccessException e) {
			log.error("Full TS file not found");
			return "";
			}catch(Exception e) {
			log.error("Excpetion found while fetching file");
			return "";
		}
		
	}
	
	@Override
	public String getLastDwnldTSRFK() {
		String queryRules = LoadJpaQueries.getQueryById("SELECT_LAST_DOWNLOAD_TS_NEWRFK");
		log.info("File Download TS fetched successfully.");
		try {
			String dwnldTS = (String) jdbcTemplate.queryForObject(queryRules, String.class);
			return dwnldTS;
			
		}catch(EmptyResultDataAccessException e) {
			log.error("Full TS RFK file not found");
			return "";
		}catch(Exception e) {
			log.error("Excpetion found while fetching file");
			return "";
		}
		
		
		
	}
	
	@SuppressWarnings("finally")
	@Override
	public String getLastSuccessfullFile(Agency agency) {
		String filename = null;
		try {
			MapSqlParameterSource paramSource = new MapSqlParameterSource();
			paramSource.addValue(Constants.FILE_EXTENSION, Constants.ITAG);
			paramSource.addValue(Constants.FROM_DEVICE_PREFIX, agency.getFilePrefix());
			String queryRules = LoadJpaQueries.getQueryById("SELECT_LAST_SUCCESSFUL_ITAG_FILE");
			log.info("Fetching ITAG file");
			filename = namedJdbcTemplate.queryForObject(queryRules, paramSource, String.class);
			return filename;
		}catch (EmptyResultDataAccessException e) {
			// TODO Auto-generated catch block
			e.getMessage();
			return null;
		}catch (DataAccessException e) {
			// TODO Auto-generated catch block
			e.getMessage();
			return null;
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.getStackTrace();
		}finally {
			return filename;
		}
	}

	@Override
	public void insertIntoInterAgencyFileXFERTable(InterAgencyFileXferDto interAgencyFileXferDto) {
		log.info("Inserting file details into TPMS.T_INTER_AGENCY_FILE_XFER table..");

		String query = LoadJpaQueries.getQueryById("INSERT_INTO_T_INTER_AGENCY_FILE_XFER_TABLE");
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue(Constants.FILE_EXTENSION, interAgencyFileXferDto.getFileExtension());
		paramSource.addValue(Constants.FROM_DEVICE_PREFIX, interAgencyFileXferDto.getFromDevicePrefix()); // set
		paramSource.addValue(Constants.TO_DEVICE_PREFIX, interAgencyFileXferDto.getToDevicePrefix());
		paramSource.addValue(Constants.FILE_DATE, interAgencyFileXferDto.getFileDate());
		paramSource.addValue(Constants.FILE_TIME_STRING, interAgencyFileXferDto.getFileTimeString());
		paramSource.addValue(Constants.RECORD_COUNT, interAgencyFileXferDto.getRecordCount());
		paramSource.addValue(Constants.XFER_TYPE, interAgencyFileXferDto.getXferType());
		paramSource.addValue(Constants.PROCESS_STATUS, interAgencyFileXferDto.getProcessStatus());
		paramSource.addValue(Constants.UPDATE_TS, interAgencyFileXferDto.getUpdateTs());
		paramSource.addValue(Constants.FILE_NAME, interAgencyFileXferDto.getFileName());
		int id = namedJdbcTemplate.update(query, paramSource);
		log.info("File {} details inserted in TPMS.T_INTER_AGENCY_FILE_XFER with ID:{}",interAgencyFileXferDto.getFileName(), id);
	}


	@Override
	public void insertIntotTagstatusStatistics(ITAGHeaderInfoVO itagHeaderInfoVO, Map<String, String> recordCountMap) {
		log.info("Inserting file details into TPMS.T_TAGSTATUS_STATISTICS table..");

		String query = LoadJpaQueries.getQueryById("INSERT_INTO_T_TAGSTATUS_STATISTICS");
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue(Constants.FILENAME, recordCountMap.get(Constants.FILENAME));
		LocalDate date = validationUtils.getFormattedDate(recordCountMap.get(Constants.FILEDATE),"yyyyMMdd");
		paramSource.addValue(Constants.FILEDATE, date); 
		paramSource.addValue(Constants.DEVICE_PREFIX, recordCountMap.get(Constants.DEVICE_PREFIX));
		paramSource.addValue(Constants.ITAG_MERGED, itagHeaderInfoVO.getMergeFileName());
		paramSource.addValue(Constants.GOOD, Long.valueOf(itagHeaderInfoVO.getValidCount1()));
		paramSource.addValue(Constants.LBAL, Long.valueOf(itagHeaderInfoVO.getLowCount2()));
		paramSource.addValue(Constants.INVALID, Long.valueOf(itagHeaderInfoVO.getInvalidCount3()));
		//paramSource.addValue(Constants.ZERO, Long.valueOf(Constants.DEFAULT_ZERO));
		paramSource.addValue(Constants.ZERO, Long.valueOf(itagHeaderInfoVO.getZeroCount()));
		paramSource.addValue(Constants.LOST, Long.valueOf(itagHeaderInfoVO.getLostCount4()));
		paramSource.addValue(Constants.STOLEN, 0);
		paramSource.addValue(Constants.INVENTORY, 0);
		paramSource.addValue(Constants.TOTAL, itagHeaderInfoVO.getRecordCount());
		paramSource.addValue(Constants.UPDATE_TS, Timestamp.valueOf(timeZoneConv.currentTime()));
		paramSource.addValue(Constants.VERSION, Constants.ITAG_MERGE_VERSION);
		int id = namedJdbcTemplate.update(query, paramSource);
		log.info("File {} details inserted in TPMS.T_TAGSTATUS_STATISTICS with ID:{}",recordCountMap.get("FILENAME"), id);

		
	}
	
	@Override
	public String checkIfFileAlreadyExists(File file) {
		
		try {
			MapSqlParameterSource paramSource = new MapSqlParameterSource();
			paramSource.addValue(Constants.FILENAME, file.getName());
			String query = LoadJpaQueries.getQueryById("CHECK_IF_FILE_ALREADY_EXISTS");
			
			String fileName = namedJdbcTemplate.queryForObject(query, paramSource, BeanPropertyRowMapper.newInstance(String.class));
			
			if(fileName != null)
			{
				log.info("Record exists with file name....{}",file.getName());
				return fileName;
			}
		}
		catch (DataAccessException e) 
		{
			e.getMessage();
			log.info("Record does not exists with file name....{}",file.getName());
		}
		return null;
		}

	@Override
	public void updateTSForExistingFileName(String fileName) {
		
		int updatecount = 0;
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue(Constants.FILENAME, fileName);
		paramSource.addValue(Constants.UPDATE_TS,timeZoneConv.currentTime());
		String query = LoadJpaQueries.getQueryById("UPDATE_TS_FOR_EXISTING_FILENAME");
		
		updatecount = namedJdbcTemplate.update(query, paramSource);
		log.info("Number of rows updated : {}", updatecount);
		
	}

	@Override
	public String getPrevfile() {
		// TODO Auto-generated method stub
		String queryRules = LoadJpaQueries.getQueryById("SELECT_PREVTS_FILE");
		log.info("Filename  TS fetched successfully.");
		try {
			String dwnldTS = (String) jdbcTemplate.queryForObject(queryRules, String.class);
			return dwnldTS;
		}catch(EmptyResultDataAccessException e) {
			log.error("Full file not found");
			return "";

		}catch(Exception e) {
			log.error("Exception found while fetching file");
			return "";
		}
		
		
		
	} 
	
	@Override
	public String getPrevfileRFK() {
		// TODO Auto-generated method stub
		String queryRules = LoadJpaQueries.getQueryById("SELECT_PREVTS_FILERFK");
		log.info("Filename  TS fetched successfully.");
		try {
			String dwnldTS = (String) jdbcTemplate.queryForObject(queryRules, String.class);
			return dwnldTS;
		}catch(EmptyResultDataAccessException e) {
			log.error("Full file RFK file not found");
			return "";
			
		}catch(Exception e) {
			log.error("Exception found while fetching file");
			return "";
		}	
		
	} 
}
