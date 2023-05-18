package com.conduent.tpms.iag.dao.impl;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.conduent.tpms.iag.dto.IncrTagStatusRecord;
import com.conduent.tpms.iag.dto.MappingInfoDto;
import com.conduent.tpms.iag.model.TAGDevice;
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
	TimeZoneConv timeZoneConv;
	
	private List<TAGDevice> listOfDeviceInfo = new ArrayList<>();
	
	@Override
	public FileCreationParameters getMappingConfigurationDetails(FileCreationParameters fileDto) {

		log.info("Getting mapping configuration from table..");

		FileCreationParameters fileDetails = new FileCreationParameters();
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		String queryFile = LoadJpaQueries.getQueryById("GET_OUTPUT_FILE_CONFIGURATION_INFO");

		paramSource.addValue(Constants.FILE_TYPE, fileDto.getFileType());
		paramSource.addValue(Constants.AGENCY_ID, fileDto.getAgencyId());
		paramSource.addValue(Constants.FILE_FORMAT, Constants.FIXED);

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
		//String queryRules = LoadJpaQueries.getQueryById("SELECT_LAST_DOWNLOAD_TS");
		//log.info("File Download TS fetched successfully.");
		//String dwnldTS = (String) jdbcTemplate.queryForObject(queryRules, String.class);
		//return dwnldTS;
		String queryRules = LoadJpaQueries.getQueryById("SELECT_LAST_DOWNLOAD_TS_NEW");
		try {
		    String dwnldTS = (String) jdbcTemplate.queryForObject(queryRules, String.class);
			log.info("File Download TS fetched successfully: {}", dwnldTS);
			LocalDateTime previousFileDateTime = LocalDateTime.parse(dwnldTS, DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
			return previousFileDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'"));
		} catch (EmptyResultDataAccessException e) {
			log.error("Full ITAG File Not Found.");
			return "";
		} catch (Exception e) {
			log.error("Exception Occured: {}", e.getMessage());
			//return timeZoneConv.currentTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'"));
			return "";
		}
	}

	@Override
	public List<TAGDevice> getDevieListFromTTagAllSorted1(int agencyId, String genType,List<IncrTagStatusRecord> devicenoList) 
	{
			TAGDevice deviceInfo = null;
			for(IncrTagStatusRecord deviceNoInfo :devicenoList) 
			{
				
				MapSqlParameterSource paramSource = new MapSqlParameterSource();
				paramSource.addValue(Constants.GEN_TYPE, genType);
				paramSource.addValue(Constants.device_no, deviceNoInfo.getDeviceNo());
				
				String queryRules = LoadJpaQueries.getQueryById("LOAD_DEVICES_FROM_T_TAG_STATUS_ALLSORTED");
				
				deviceInfo = namedJdbcTemplate.queryForObject(queryRules, paramSource, BeanPropertyRowMapper.newInstance(TAGDevice.class));
				log.info("Agency info fetched from T_TAG_STATUS_ALLSORTED table successfully..{}",deviceInfo);
				
				listOfDeviceInfo.add(deviceInfo);
			}
		return listOfDeviceInfo;
	}
	
	@Override
	public List<IncrTagStatusRecord> getDeviceNoFromTInrTagStatusAllSorted(String genType) {
		
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue(Constants.GEN_TYPE, genType);
		String queryRules = LoadJpaQueries.getQueryById("LOAD_DEVICE_NO_FROM_T_INCR_TAG_STATUS_ALLSORTED");
		log.info("Agency info fetched from T_INCR_TAG_STATUS_ALLSORTED table successfully.");
		
		return namedJdbcTemplate.query(queryRules, paramSource, BeanPropertyRowMapper.newInstance(IncrTagStatusRecord.class));

	}

}

