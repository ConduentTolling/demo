package com.conduent.tpms.iag.dao.impl;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.conduent.tpms.iag.config.LoadJpaQueries;
import com.conduent.tpms.iag.constants.Constants;
import com.conduent.tpms.iag.dao.IQatpDao;
import com.conduent.tpms.iag.dto.AckFileInfoDto;
import com.conduent.tpms.iag.dto.FileDetailsDto;
import com.conduent.tpms.iag.dto.FileInfoDto;
import com.conduent.tpms.iag.dto.MappingInfoDto;

@Repository
public class QatpDaoImpl implements IQatpDao {

	@Autowired
	private NamedParameterJdbcTemplate namedJdbcTemplate;

	@Override
	public FileDetailsDto getMappingConfigurationDetails(FileDetailsDto fileDetailsDto) {
		// TODO: Inform Mayrui
		// FileDetailsDto fileDetails = new FileDetailsDto();

		// Query:Different database query or java Stream

		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		String queryFile = LoadJpaQueries.getQueryById("GET_FILE_INFO_CONFIGURATION");

		paramSource.addValue(Constants.FILE_TYPE, fileDetailsDto.getFileType());
		paramSource.addValue(Constants.AGENCY_ID, fileDetailsDto.getAgencyId());
		paramSource.addValue(Constants.FILE_FORMAT, fileDetailsDto.getFileFormat());

		FileInfoDto info = namedJdbcTemplate.queryForObject(queryFile, paramSource,
				BeanPropertyRowMapper.newInstance(FileInfoDto.class));
		fileDetailsDto.setFileInfoDto(info);

		String queryRules = LoadJpaQueries.getQueryById("GET_FIELD_VALIDATION_DETAILS");

		paramSource.addValue(Constants.FIELD_TYPE, Constants.FILENAME);
		paramSource.addValue(Constants.AGENCY_ID, fileDetailsDto.getAgencyId());

		List<MappingInfoDto> fileNameMappingDetails = namedJdbcTemplate.query(queryRules, paramSource,
				BeanPropertyRowMapper.newInstance(MappingInfoDto.class));
		getMinMaxListValues(fileNameMappingDetails);
		fileDetailsDto.setFileNameMappingInfo(fileNameMappingDetails);

		paramSource.addValue(Constants.FIELD_TYPE, Constants.HEADER);

		List<MappingInfoDto> headerMappingDetails = namedJdbcTemplate.query(queryRules, paramSource,
				BeanPropertyRowMapper.newInstance(MappingInfoDto.class));
		getMinMaxListValues(headerMappingDetails);
		fileDetailsDto.setHeaderMappingInfoList(headerMappingDetails);
		paramSource.addValue(Constants.FIELD_TYPE, Constants.DETAIL);

		List<MappingInfoDto> detailMappingDetails = namedJdbcTemplate.query(queryRules, paramSource,
				BeanPropertyRowMapper.newInstance(MappingInfoDto.class));
		getMinMaxListValues(detailMappingDetails);
		fileDetailsDto.setDetailRecMappingInfo(detailMappingDetails);

		return fileDetailsDto;

	}

	private void getMinMaxListValues(List<MappingInfoDto> fileNameMappingDetails) {
		for (MappingInfoDto infoRow : fileNameMappingDetails) {
			if (Constants.FIXED_VALUE.equals(infoRow.getValidationType())
					|| Constants.DATE.equals(infoRow.getValidationType())) {
				infoRow.setFixeddValidValue(infoRow.getValidationValue());
			} else if (Constants.RANGE.equals(infoRow.getValidationType())) {
				String[] rangeValue = infoRow.getValidationValue().replaceAll("\\s", "").split("-");
				infoRow.setMinRangeValue(new BigInteger(rangeValue[0]));
				infoRow.setMaxRangeValue(new BigInteger(rangeValue[1]));
			} else if (Constants.LOV.equals(infoRow.getValidationType())) {
				List<String> listOfValue = Arrays.asList(infoRow.getValidationValue().split(","));
				infoRow.setListOfValidValues(listOfValue);
			}
		}
	}

	@Override
	public Long insertAckStatus(AckFileInfoDto ackFileInfoDto) {

		KeyHolder keyHolder = new GeneratedKeyHolder();
		String query = LoadJpaQueries.getQueryById("INSERT_ACK_FILE_STATUS");

		MapSqlParameterSource paramSource = null;

		paramSource = new MapSqlParameterSource();
		paramSource.addValue(Constants.ACK_FILE_NAME, ackFileInfoDto.getAckFileName());
		paramSource.addValue(Constants.ACK_FILE_DATE, ackFileInfoDto.getAckFileDate());
		paramSource.addValue(Constants.ACK_FILE_TIME, ackFileInfoDto.getAckFileTime());
		paramSource.addValue(Constants.ACK_FILE_TYPE, ackFileInfoDto.getFileType());
		paramSource.addValue(Constants.ACK_FILE_STATUS, ackFileInfoDto.getAckFileStatus());
		paramSource.addValue(Constants.TRX_FILE_NAME, ackFileInfoDto.getTrxFileName());
		paramSource.addValue(Constants.RECON_FILE_NAME, ackFileInfoDto.getReconFileName());
		paramSource.addValue(Constants.FILE_TYPE, ackFileInfoDto.getFileType());
		paramSource.addValue(Constants.FROM_AGENCY, ackFileInfoDto.getFromAgency());
		paramSource.addValue(Constants.TO_AGENCY, ackFileInfoDto.getToAgency());
		paramSource.addValue(Constants.EXTERN_FILE_ID, ackFileInfoDto.getExternFileId());
		paramSource.addValue(Constants.ATP_FILE_ID, ackFileInfoDto.getAtpFileId());

		namedJdbcTemplate.update(query, paramSource, keyHolder, new String[] { Constants.ACK_FILE_NAME });
		Map<String, Object> map = keyHolder.getKeys();
		String fileName = null;
		if (null != map) {
			fileName = map.get(Constants.ACK_FILE_NAME).toString();
		}
		return fileName != null ? 1L : 0L;
	}
}