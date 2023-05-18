package com.conduent.tpms.iag.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.conduent.tpms.iag.config.LoadJpaQueries;
import com.conduent.tpms.iag.constants.Constants;
import com.conduent.tpms.iag.dao.FTAGIncrementDao;
import com.conduent.tpms.iag.dto.FileCreationParameters;
import com.conduent.tpms.iag.dto.FileInfoDto;
import com.conduent.tpms.iag.dto.IncrTagStatusRecord;
import com.conduent.tpms.iag.dto.MappingInfoDto;
import com.conduent.tpms.iag.model.FTAGDevice;

@Repository
public class FTAGIncrementDaoImpl implements FTAGIncrementDao {

	private static final Logger log = LoggerFactory.getLogger(FTAGIncrementDaoImpl.class);

	@Autowired
	private NamedParameterJdbcTemplate namedJdbcTemplate;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	private List<FTAGDevice> list = new ArrayList<>();

	/**
	 * Get device information from V_DEVICE table
	 *
	 * @return List<VDeviceDto>
	 */
	@Override
	public List<FTAGDevice> getDeviceListFromTTagAllSorted(int agencyId, String genType,
			List<IncrTagStatusRecord> deviceNoList) {
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		FTAGDevice deviceInfo = null;
		for (IncrTagStatusRecord deviceNoInfo : deviceNoList) {
			paramSource.addValue(Constants.GEN_TYPE, genType);
			paramSource.addValue(Constants.DEVICE_NO, deviceNoInfo.getDeviceNo());

			String queryRules = LoadJpaQueries.getQueryById("LOAD_DEVICES_FROM_T_TAG_STATUS_ALLSORTED");

			deviceInfo = namedJdbcTemplate.queryForObject(queryRules, paramSource, BeanPropertyRowMapper.newInstance(FTAGDevice.class));
			log.info("Agency info fetched from T_Agency table successfully...", deviceInfo);
			// (queryRules, paramSource,
			// BeanPropertyRowMapper.newInstance(FTAGDevice.class));
			list.add(deviceInfo);
		}
		return list;

	}

	@Override
	public List<IncrTagStatusRecord> getDeviceNoFromTInrTagStatusAllSorted(String genType) {

		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue(Constants.GEN_TYPE, genType);
		String queryRules = LoadJpaQueries.getQueryById("LOAD_DEVICE_NO_FROM_T_INCR_TAG_STATUS_ALLSORTED");
		log.info("Agency info fetched from T_INCR_TAG_STATUS_ALLSORTED table successfully.");

		return namedJdbcTemplate.query(queryRules, paramSource,
				BeanPropertyRowMapper.newInstance(IncrTagStatusRecord.class));

	}

	@Override
	public FileCreationParameters getMappingConfigurationDetails(FileCreationParameters fileDto) {

		log.info("Getting mapping configuration from table..");

		FileCreationParameters fileDetails = new FileCreationParameters();
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		String queryFile = LoadJpaQueries.getQueryById("GET_OUTPUT_FILE_CONFIGURATION_INFO");

		paramSource.addValue(Constants.FILE_TYPE, fileDto.getFileType());
		paramSource.addValue(Constants.AGENCY_ID, fileDto.getAgencyId());
		paramSource.addValue(Constants.FILE_FORMAT, Constants.INCREMENT);

		FileInfoDto info = namedJdbcTemplate.queryForObject(queryFile, paramSource,
				BeanPropertyRowMapper.newInstance(FileInfoDto.class));
		fileDetails.setFileInfoDto(info);
		// fileDetails.getFileInfoDto().getAgencyId();
		System.out.println(fileDetails.getFileInfoDto().toString());
		System.out.println(info.toString());

		String queryRules = LoadJpaQueries.getQueryById("GET_OUTPUT_FIELD_VALIDATION_DETAILS");

		List<MappingInfoDto> nameMappingDetails = namedJdbcTemplate.query(queryRules, paramSource,
				BeanPropertyRowMapper.newInstance(MappingInfoDto.class));
		// getMinMaxListValues(nameMappingDetails);

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
	public String getLastDwnldTS() {
		String queryRules = LoadJpaQueries.getQueryById("SELECT_LAST_DOWNLOAD_TS");
		log.info("File Download TS fetched successfully.");
		String dwnldTS = (String) jdbcTemplate.queryForObject(queryRules, String.class);
		return dwnldTS;

	}

}
