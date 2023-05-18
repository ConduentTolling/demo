package com.conduent.tpms.iag.dao.impl;

import java.io.File;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
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
import com.fasterxml.jackson.databind.BeanProperty;

/**
 * Implementation class for IAGDao
 * 
 * @author taniyan
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

		paramSource.addValue(Constants.FILE_TYPE, fileDto.getFileType());
		paramSource.addValue(Constants.AGENCY_ID, fileDto.getAgencyId());
		paramSource.addValue(Constants.FILE_FORMAT, Constants.FILE_GEN_TYPE_FULL);

		FileInfoDto info = namedJdbcTemplate.queryForObject(queryFile, paramSource,
				BeanPropertyRowMapper.newInstance(FileInfoDto.class));
		fileDetails.setFileInfoDto(info);
		System.out.println(fileDetails.getFileInfoDto().toString());
		System.out.println(info.toString());

		String queryRules = LoadJpaQueries.getQueryById("GET_OUTPUT_FIELD_VALIDATION_DETAILS");

		List<MappingInfoDto> nameMappingDetails = namedJdbcTemplate.query(queryRules, paramSource,
				BeanPropertyRowMapper.newInstance(MappingInfoDto.class));
		//getMinMaxListValues(nameMappingDetails);

		List<MappingInfoDto> fileNameMappingDetails = nameMappingDetails.stream()
				.filter(f -> f.getFieldType().equals("FILENAME")).collect(Collectors.toList());
		System.out.println(fileNameMappingDetails.toString());
		fileDetails.setFileNameMappingInfo(fileNameMappingDetails);

		List<MappingInfoDto> headerMappingDetails = nameMappingDetails.stream()
				.filter(f -> f.getFieldType().equals("HEADER")).collect(Collectors.toList());
		System.out.println(headerMappingDetails.toString());
		fileDetails.setHeaderMappingInfoList(headerMappingDetails);

		List<MappingInfoDto> detailMappingDetails = nameMappingDetails.stream()
				.filter(f -> f.getFieldType().equals("DETAIL")).collect(Collectors.toList());
		System.out.println(detailMappingDetails.toString());
		fileDetails.setDetailRecMappingInfo(detailMappingDetails);

		log.info("Collected mapping configuration from table..");
		
		return fileDetails;

	}

	@Override
	public List<TAGDevice> getDeviceInfofromTTagStatusAllSorted(int agencyId, String genType) {
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue(Constants.GEN_TYPE, genType);
		String queryRules = LoadJpaQueries.getQueryById("LOAD_DEVICES_FROM_T_TAG_STATUS_ALLSORTED");
		log.info("Agency info fetched from T_Agency table successfully.");
		
		return namedJdbcTemplate.query(queryRules, paramSource, BeanPropertyRowMapper.newInstance(TAGDevice.class));

	}
	
	@Override
	public String getLastDwnldTS() {
		String queryRules = LoadJpaQueries.getQueryById("SELECT_LAST_DOWNLOAD_TS");
		log.info("File Download TS fetched successfully.");
		String dwnldTS = (String) jdbcTemplate.queryForObject(queryRules, String.class);
		return dwnldTS;

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
	public void insertIntotTagstatusStatistics(ITAGHeaderInfoVO itagHeaderInfoVO, Map<String, String> recordCountMap,String fileName, Map<String, String> recordCountMapAwayItagFile) {
		String query = LoadJpaQueries.getQueryById("INSERT_INTO_T_TAGSTATUS_STATISTICS");
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		//paramSource.addValue(Constants.FILENAME, recordCountMap.get(Constants.FILENAME));
		paramSource.addValue(Constants.FILENAME, recordCountMap.get(Constants.ZIP_FILE_NAME));
		LocalDate date = validationUtils.getFormattedDate(recordCountMap.get(Constants.FILEDATE),"yyyyMMdd");
		paramSource.addValue(Constants.FILEDATE, date); 
		//paramSource.addValue(Constants.DEVICE_PREFIX, recordCountMap.get(Constants.DEVICE_PREFIX));
		paramSource.addValue(Constants.DEVICE_PREFIX, itagHeaderInfoVO.getMergeFileName().substring(0, 4));
		paramSource.addValue(Constants.ITAG_MERGED, itagHeaderInfoVO.getMergeFileName());
		//paramSource.addValue(Constants.GOOD, Long.valueOf(itagHeaderInfoVO.getValidCount1()));
		//paramSource.addValue(Constants.LBAL, Long.valueOf(itagHeaderInfoVO.getLowCount2()));
		//paramSource.addValue(Constants.INVALID, Long.valueOf(itagHeaderInfoVO.getInvalidCount3()));
		//paramSource.addValue(Constants.LOST, Long.valueOf(itagHeaderInfoVO.getLostCount4()));
		paramSource.addValue(Constants.GOOD,  recordCountMapAwayItagFile.get(Constants.GOOD));
		paramSource.addValue(Constants.LBAL, recordCountMapAwayItagFile.get(Constants.LOW_BAL));
		paramSource.addValue(Constants.ZERO, recordCountMapAwayItagFile.get(Constants.ZERO));
		paramSource.addValue(Constants.INVALID, 0);
		paramSource.addValue(Constants.LOST, recordCountMapAwayItagFile.get(Constants.LOST));
		paramSource.addValue(Constants.STOLEN, 0);
		paramSource.addValue(Constants.INVENTORY, 0);
		paramSource.addValue(Constants.TOTAL, itagHeaderInfoVO.getRecordCount());
		paramSource.addValue(Constants.UPDATE_TS, timeZoneConv.currentTime());
		paramSource.addValue(Constants.VERSION, Constants.ITAG_MERGE_VERSION);
		int id = namedJdbcTemplate.update(query, paramSource);
		log.info("File {} details inserted in TPMS.T_TAGSTATUS_STATISTICS with ID:{}",recordCountMap.get(Constants.ZIP_FILE_NAME), id);

		
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
	
	}

