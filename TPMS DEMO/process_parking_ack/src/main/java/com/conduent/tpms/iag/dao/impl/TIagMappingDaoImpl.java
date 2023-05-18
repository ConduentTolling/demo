package com.conduent.tpms.iag.dao.impl;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.conduent.tpms.iag.config.LoadJpaQueries;
import com.conduent.tpms.iag.constants.Constants;
import com.conduent.tpms.iag.dao.TIagMappingDao;
import com.conduent.tpms.iag.dto.FileInfoDto;
import com.conduent.tpms.iag.dto.FileParserParameters;
import com.conduent.tpms.iag.dto.MappingInfoDto;

@Repository
public class TIagMappingDaoImpl implements TIagMappingDao {

	private static final Logger dao_log = LoggerFactory.getLogger(TIagMappingDaoImpl.class);

	@Autowired
	private NamedParameterJdbcTemplate namedJdbcTemplate;

	@Override
	public Long getAgencyId(String filePrefix) {
		return null;
	}

	@Override
	public Long getLastProcessedRecordCount(Long agencyId) {
		return null;
	}

	@Override
	public FileParserParameters getMappingConfigurationDetails(FileParserParameters fielDto) {
		FileParserParameters fileDetails = new FileParserParameters();
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		String queryFile = LoadJpaQueries.getQueryById("GET_FILE_INFO_CONFIGURATION");

		paramSource.addValue(Constants.FILE_TYPE, fielDto.getFileType());
		paramSource.addValue(Constants.AGENCY_ID, fielDto.getAgencyId());
		paramSource.addValue(Constants.FILE_FORMAT, Constants.FIXED);

		FileInfoDto info = namedJdbcTemplate.queryForObject(queryFile, paramSource,
				BeanPropertyRowMapper.newInstance(FileInfoDto.class));
		fileDetails.setFileInfoDto(info);

		String queryRules = LoadJpaQueries.getQueryById("GET_FIELD_VALIDATION_DETAILS");

		List<MappingInfoDto> nameMappingDetails = namedJdbcTemplate.query(queryRules, paramSource,
				BeanPropertyRowMapper.newInstance(MappingInfoDto.class));
		getMinMaxListValues(nameMappingDetails);
		List<MappingInfoDto> fileNameMappingDetails = nameMappingDetails.stream()
				.filter(f -> f.getFieldType().equals("2AGFILENAME")).collect(Collectors.toList());

		fileDetails.setFileNameMappingInfo(fileNameMappingDetails);
		
		List<MappingInfoDto> longFileNameMappingDetails = nameMappingDetails.stream()
				.filter(f -> f.getFieldType().equals("3AGFILENAME")).collect(Collectors.toList());

		fileDetails.setLongFileNameMappingInfo(longFileNameMappingDetails);
	
		List<MappingInfoDto> detailMappingDetails = nameMappingDetails.stream()
				.filter(f -> f.getFieldType().equals("2AGDETAIL")).collect(Collectors.toList());
		fileDetails.setDetailRecMappingInfo(detailMappingDetails);
		
		List<MappingInfoDto> longDetailMappingDetails = nameMappingDetails.stream()
				.filter(f -> f.getFieldType().equals("3AGDETAIL")).collect(Collectors.toList());
		fileDetails.setLongdetailRecMappingInfo(longDetailMappingDetails);

		return fileDetails;

	}

	private void getMinMaxListValues(List<MappingInfoDto> fileNameMappingDetails) {
		for (MappingInfoDto infoRow : fileNameMappingDetails) {
			if (Constants.FIXED_VALUE.equals(infoRow.getValidationType())
					|| Constants.DATE.equals(infoRow.getValidationType())) {
				infoRow.setFixeddValidValue(infoRow.getValidationValue());
			} else if (Constants.RANGE.equals(infoRow.getValidationType())) {
				String[] rangeValue = infoRow.getValidationValue().replaceAll("\\s", "").split("-");
				infoRow.setMinRangeValue(Long.valueOf(rangeValue[0]));// 00
				infoRow.setMaxRangeValue(Long.valueOf(rangeValue[1]));
			} else if (Constants.LOV.equals(infoRow.getValidationType())) {
				List<String> listOfValue = Arrays.asList(infoRow.getValidationValue().split(","));
				infoRow.setListOfValidValues(listOfValue);
			}
		}
	}

}
