package com.conduent.tpms.iag.dao.impl;

import java.io.File;
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

import com.conduent.tpms.iag.config.LoadJpaQueries;
import com.conduent.tpms.iag.constants.ICLPConstants;
import com.conduent.tpms.iag.dao.IAGDao;
import com.conduent.tpms.iag.dto.FileCreationParameters;
import com.conduent.tpms.iag.dto.FileInfoDto;
import com.conduent.tpms.iag.dto.MappingInfoDto;
import com.conduent.tpms.iag.model.TAGDevice;
import com.conduent.tpms.iag.model.VAddressDto;
import com.conduent.tpms.iag.model.VCustomerNameDto;
import com.conduent.tpms.iag.model.VVehicle;
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
	
	@Override
	public FileCreationParameters getMappingConfigurationDetails(FileCreationParameters fileDto) {

		log.info("Getting mapping configuration from table..");

		FileCreationParameters fileDetails = new FileCreationParameters();
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		String queryFile = LoadJpaQueries.getQueryById("GET_OUTPUT_FILE_CONFIGURATION_INFO");

		paramSource.addValue(ICLPConstants.FILE_TYPE, fileDto.getFileType());
		paramSource.addValue(ICLPConstants.AGENCY_ID, fileDto.getAgencyId());
		paramSource.addValue(ICLPConstants.FILE_FORMAT, ICLPConstants.FIXED);

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
	public int getetcaccountid(String deviceno) {

		int etc_account_id;
		
		try {
			
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue(ICLPConstants.LC_DEVICE_NO, deviceno);
		String queryRules = LoadJpaQueries.getQueryById("GET_ETC_ACCOUNT_ID_FROM_DEVICE_NO");
		etc_account_id = namedJdbcTemplate.queryForObject(queryRules, paramSource,Integer.class);

		}catch(EmptyResultDataAccessException ex) {
			log.info("Account Id not present for Device_no:{} checking for 10 digit",deviceno);
			
			String devicenoOld = deviceno.substring(1,4)+deviceno.substring(6,14);
			log.info("Account Id checking for Device_no:{} checking for 10 digit",devicenoOld);
			MapSqlParameterSource paramSource = new MapSqlParameterSource();
			paramSource.addValue(ICLPConstants.LC_DEVICE_NO, devicenoOld);
			String queryRules = LoadJpaQueries.getQueryById("GET_ETC_ACCOUNT_ID_FROM_DEVICE_NO");
			etc_account_id = namedJdbcTemplate.queryForObject(queryRules, paramSource,Integer.class);
		}
		

		return etc_account_id;

	}

	@Override
	public List<VVehicle> getPlateInfo(int etc_account_id) 
	{
		List<VVehicle> plateinfo = null;
		
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue(ICLPConstants.LC_ETC_ACCOUNT_ID, etc_account_id);
		String queryRules = LoadJpaQueries.getQueryById("GET_PLATE_INFO_FROM_V_VEHICLE");
		plateinfo = namedJdbcTemplate.query(queryRules, paramSource,BeanPropertyRowMapper.newInstance(VVehicle.class));
		
		return plateinfo;
	}


	@Override
	public VCustomerNameDto getCustomerName(int etc_account_id) 
	{

		VCustomerNameDto cutomernameinfo ;
		
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue(ICLPConstants.LC_ETC_ACCOUNT_ID, etc_account_id);
		String queryRules = LoadJpaQueries.getQueryById("GET_CUSTOMER_NAME_INFO_FROM_V_CUSTOMER_NAME");
		cutomernameinfo = namedJdbcTemplate.queryForObject(queryRules, paramSource,BeanPropertyRowMapper.newInstance(VCustomerNameDto.class));
		
		return cutomernameinfo;
	}


	@Override
	public VAddressDto getAddressInfo(int etc_account_id) {
		
		VAddressDto addressinfoAddressDtos;
		
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue(ICLPConstants.LC_ETC_ACCOUNT_ID, etc_account_id);
		String queryRules = LoadJpaQueries.getQueryById("GET_ADDRESS_INFO_FROM_V_ADDRESS");
		addressinfoAddressDtos = namedJdbcTemplate.queryForObject(queryRules, paramSource,BeanPropertyRowMapper.newInstance(VAddressDto.class));
		
		return addressinfoAddressDtos;
	}


	@Override
	public VAddressDto getCompanyName(int etc_account_id) {
	
		VAddressDto cmpName;
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue(ICLPConstants.LC_ETC_ACCOUNT_ID, etc_account_id);
		String queryRules = LoadJpaQueries.getQueryById("GET_COMPANY_NAME_FROM_V_ETC_ACCOUNT");
		cmpName = namedJdbcTemplate.queryForObject(queryRules, paramSource,BeanPropertyRowMapper.newInstance(VAddressDto.class));
		
		return cmpName;
	}
	
	@Override
	public Long getAgencyId(int etc_account_id) {
	
		Long agencyId;
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue(ICLPConstants.LC_ETC_ACCOUNT_ID, etc_account_id);
		String queryRules = LoadJpaQueries.getQueryById("GET_AGENCY_ID_FROM_V_ETC_ACCOUNT");
		agencyId = namedJdbcTemplate.queryForObject(queryRules, paramSource,Long.class);
		
		return agencyId;
	}
	
	@Override
	public String getInvalidStatusFlag() {
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		String queryToCheckFile = LoadJpaQueries.getQueryById(ICLPConstants.SELECT_INVALID_TAG_STATUS);
		return namedJdbcTemplate.queryForObject(queryToCheckFile,paramSource, String.class);
	}
	
}
