package com.conduent.tpms.qatp.dao.impl;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.conduent.tpms.qatp.config.LoadJpaQueries;
import com.conduent.tpms.qatp.constants.CorrConstants;
import com.conduent.tpms.qatp.dao.IQatpDao;
import com.conduent.tpms.qatp.dto.AckFileInfoDto;
import com.conduent.tpms.qatp.dto.FileDetailsDto;
import com.conduent.tpms.qatp.dto.FileInfoDto;
import com.conduent.tpms.qatp.dto.MappingInfoDto;
import com.conduent.tpms.qatp.dto.TProcessParamterDto;

@Repository
public class QatpDaoImpl implements IQatpDao {
	
	private static final Logger dao_log = LoggerFactory.getLogger(QatpDaoImpl.class);

	@Autowired
	private NamedParameterJdbcTemplate namedJdbcTemplate;

	@Override
	public FileDetailsDto getMappingConfigurationDetails(FileDetailsDto fileDetailsDto) {
		// TODO: Inform Mayrui
		// FileDetailsDto fileDetails = new FileDetailsDto();

		// Query:Different database query or java Stream

		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		String queryFile = LoadJpaQueries.getQueryById("GET_FILE_INFO_CONFIGURATION");

		paramSource.addValue(CorrConstants.FILE_TYPE, fileDetailsDto.getFileType());
		paramSource.addValue(CorrConstants.AGENCY_ID, fileDetailsDto.getAgencyId());
		paramSource.addValue(CorrConstants.FILE_FORMAT, fileDetailsDto.getFileFormat());

		FileInfoDto info = namedJdbcTemplate.queryForObject(queryFile, paramSource,
				BeanPropertyRowMapper.newInstance(FileInfoDto.class));
		fileDetailsDto.setFileInfoDto(info);

		String queryRules = LoadJpaQueries.getQueryById("GET_FIELD_VALIDATION_DETAILS");

		paramSource.addValue(CorrConstants.FIELD_TYPE, CorrConstants.FILENAME);
		paramSource.addValue(CorrConstants.AGENCY_ID, fileDetailsDto.getAgencyId());

		List<MappingInfoDto> fileNameMappingDetails = namedJdbcTemplate.query(queryRules, paramSource,
				BeanPropertyRowMapper.newInstance(MappingInfoDto.class));
		getMinMaxListValues(fileNameMappingDetails);
		fileDetailsDto.setFileNameMappingInfo(fileNameMappingDetails);

		paramSource.addValue(CorrConstants.FIELD_TYPE, CorrConstants.HEADER);

		List<MappingInfoDto> headerMappingDetails = namedJdbcTemplate.query(queryRules, paramSource,
				BeanPropertyRowMapper.newInstance(MappingInfoDto.class));
		getMinMaxListValues(headerMappingDetails);
		fileDetailsDto.setHeaderMappingInfoList(headerMappingDetails);
		paramSource.addValue(CorrConstants.FIELD_TYPE, CorrConstants.DETAIL);

		List<MappingInfoDto> detailMappingDetails = namedJdbcTemplate.query(queryRules, paramSource,
				BeanPropertyRowMapper.newInstance(MappingInfoDto.class));
		getMinMaxListValues(detailMappingDetails);
		fileDetailsDto.setDetailRecMappingInfo(detailMappingDetails);

		return fileDetailsDto;

	}

	private void getMinMaxListValues(List<MappingInfoDto> fileNameMappingDetails) {
		for (MappingInfoDto infoRow : fileNameMappingDetails) {
			if (CorrConstants.FIXED_VALUE.equals(infoRow.getValidationType())
					|| CorrConstants.DATE.equals(infoRow.getValidationType())) {
				infoRow.setFixeddValidValue(infoRow.getValidationValue());
			} else if (CorrConstants.RANGE.equals(infoRow.getValidationType())) {
				String[] rangeValue = infoRow.getValidationValue().replaceAll("\\s", "").split("-");
				infoRow.setMinRangeValue(Long.valueOf(rangeValue[0]));
				infoRow.setMaxRangeValue(Long.valueOf(rangeValue[1]));
			} else if (CorrConstants.LOV.equals(infoRow.getValidationType())) {
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
		paramSource.addValue(CorrConstants.ACK_FILE_NAME, ackFileInfoDto.getAckFileName());
		paramSource.addValue(CorrConstants.ACK_FILE_DATE, ackFileInfoDto.getAckFileDate());
		paramSource.addValue(CorrConstants.ACK_FILE_TIME, ackFileInfoDto.getAckFileTime());
		paramSource.addValue(CorrConstants.ACK_FILE_TYPE, ackFileInfoDto.getFileType());
		paramSource.addValue(CorrConstants.ACK_FILE_STATUS, ackFileInfoDto.getAckFileStatus());
		paramSource.addValue(CorrConstants.TRX_FILE_NAME, ackFileInfoDto.getTrxFileName());
		paramSource.addValue(CorrConstants.RECON_FILE_NAME, ackFileInfoDto.getReconFileName());
		paramSource.addValue(CorrConstants.FILE_TYPE, ackFileInfoDto.getFileType());
		paramSource.addValue(CorrConstants.FROM_AGENCY, ackFileInfoDto.getFromAgency());
		paramSource.addValue(CorrConstants.TO_AGENCY, ackFileInfoDto.getToAgency());
		paramSource.addValue(CorrConstants.EXTERN_FILE_ID, ackFileInfoDto.getExternFileId());
		paramSource.addValue(CorrConstants.ATP_FILE_ID, ackFileInfoDto.getAtpFileId());

		namedJdbcTemplate.update(query, paramSource, keyHolder, new String[] { CorrConstants.ACK_FILE_NAME });
		Map<String, Object> map = keyHolder.getKeys();
		String fileName = null;
		if (null != map) {
			fileName = map.get(CorrConstants.ACK_FILE_NAME).toString();
		}
		return fileName != null ? 1L : 0L;
	}
	
	
	@Override
	public List<TProcessParamterDto> getProcessParamtersList() 
	{
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		
		paramSource.addValue(CorrConstants.PARAM_NAME, CorrConstants.PARKING_THRESHOLD);
		
		String queryRules =	LoadJpaQueries.getQueryById("SELECT_INFO_FROM_T_PROCESS_PARAMTERS");
			
		dao_log.info("Process Paramter Info fetched from T_PROCESS_PARAMETER table successfully.");
			
		return namedJdbcTemplate.query(queryRules,paramSource,new BeanPropertyRowMapper<TProcessParamterDto>(TProcessParamterDto.class));
			
		}
}
