package com.conduent.tpms.iag.dao.impl;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.conduent.tpms.iag.config.LoadJpaQueries;
import com.conduent.tpms.iag.constants.Constants;
import com.conduent.tpms.iag.dao.TIagDao;
import com.conduent.tpms.iag.dto.AckFileInfoDto;
import com.conduent.tpms.iag.dto.FileDetailsDto;
import com.conduent.tpms.iag.dto.FileInfoDto;
import com.conduent.tpms.iag.dto.IaFileStats;
import com.conduent.tpms.iag.dto.MappingInfoDto;
import com.conduent.tpms.iag.model.XferControl;

@Repository
public class TIagDaoImpl implements TIagDao {

	private static final Logger dao_log = LoggerFactory.getLogger(AckFileInfoDto.class);
	
	@Autowired
	private NamedParameterJdbcTemplate namedJdbcTemplate;

	@Override
	public FileDetailsDto getMappingConfigurationDetails(FileDetailsDto fileDetailsDto) {
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		String queryFile = LoadJpaQueries.getQueryById("GET_FILE_INFO_CONFIGURATION");

		paramSource.addValue(Constants.FILE_TYPE, fileDetailsDto.getFileType());
		paramSource.addValue(Constants.AGENCY_ID, fileDetailsDto.getAgencyId());
		paramSource.addValue(Constants.FILE_FORMAT, fileDetailsDto.getFileFormat());

		FileInfoDto info = namedJdbcTemplate.queryForObject(queryFile, paramSource,
				BeanPropertyRowMapper.newInstance(FileInfoDto.class));
		fileDetailsDto.setFileInfoDto(info);

		String queryRules = LoadJpaQueries.getQueryById("GET_FIELD_VALIDATION_DETAILS");

		paramSource.addValue(Constants.FIELD_TYPE, Constants.FILENAMEAG2);
		paramSource.addValue(Constants.AGENCY_ID, fileDetailsDto.getAgencyId());

		List<MappingInfoDto> fileNameMappingDetails = namedJdbcTemplate.query(queryRules, paramSource,
				BeanPropertyRowMapper.newInstance(MappingInfoDto.class));
		getMinMaxListValues(fileNameMappingDetails);
		fileDetailsDto.setFileNameMappingInfo(fileNameMappingDetails);

		List<MappingInfoDto> headerMappingDetails = namedJdbcTemplate.query(queryRules, paramSource,
				BeanPropertyRowMapper.newInstance(MappingInfoDto.class));
		getMinMaxListValues(headerMappingDetails);
		paramSource.addValue(Constants.FIELD_TYPE, Constants.DETAILAG2);

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
				infoRow.setMinRangeValue(Long.valueOf(rangeValue[0]));
				infoRow.setMaxRangeValue(Long.valueOf(rangeValue[1]));
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
		paramSource.addValue(Constants.ACK_FILE_TIME, ackFileInfoDto.getAckFileTime().replaceAll("(?<=\\G\\d{2})", ":").substring(0,8));
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
		dao_log.info("Entry In ACK table sucessfully inserted");
		return fileName != null ? 1L : 0L;
		
	}

	@Override
	public AckFileInfoDto getAckFileNameInfo(String ackFileName) {
		
		String queryRules =	LoadJpaQueries.getQueryById("CHECK_FILE_DOWNLOADED_FROM_T_IA_ACK_STATUS");
		
		MapSqlParameterSource paramSource = null;

		paramSource = new MapSqlParameterSource();
		paramSource.addValue(Constants.ACK_FILE_NAME, ackFileName);
		
		
		List<AckFileInfoDto> listOfObj = namedJdbcTemplate.query(queryRules, paramSource,
				BeanPropertyRowMapper.newInstance(AckFileInfoDto.class));
		
		Optional<AckFileInfoDto> AckFileInfo = listOfObj.stream().findFirst();
		if (AckFileInfo.isPresent()) {
			return AckFileInfo.get();
		}
		return null;
		
	}
	
		


	@Override
	public XferControl getXferControlInfo(String xferFileName) {
		
		String queryRules =	LoadJpaQueries.getQueryById("CHECK_FILE_DOWNLOADED_FROM_T_XFER_CONTROL");
		
		MapSqlParameterSource paramSource = null;

		paramSource = new MapSqlParameterSource();
		paramSource.addValue(Constants.XFER_FILE_NAME, xferFileName);
		
		List<XferControl> listOfObj = namedJdbcTemplate.query(queryRules, paramSource,
				BeanPropertyRowMapper.newInstance(XferControl.class));
		
		Optional<XferControl> xferFileInfo = listOfObj.stream().findFirst();
		if (xferFileInfo.isPresent()) {
			return xferFileInfo.get();
		}
		return null;
	}

	@Override
	public IaFileStats getIaFileStatsInfo(String iaStatsFileName) {
		
		String queryRules =	LoadJpaQueries.getQueryById("CHECK_FILE_DOWNLOADED_FROM_T_IA_FILE_STATS");
		
		MapSqlParameterSource paramSource = null;

		paramSource = new MapSqlParameterSource();
		paramSource.addValue(Constants.ICTX_FILE_NAME, iaStatsFileName);
		paramSource.addValue(Constants.ICRX_FILE_NAME, iaStatsFileName);
		
		List<IaFileStats> listOfObj = namedJdbcTemplate.query(queryRules, paramSource,
				BeanPropertyRowMapper.newInstance(IaFileStats.class));
		
		Optional<IaFileStats> iagFileInfo = listOfObj.stream().findFirst();
		if (iagFileInfo.isPresent()) {
			return iagFileInfo.get();
		}
		return null;
	}
	
	public Long checkAtpFileInStats(AckFileInfoDto ackFileInfoDto) {
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		String queryFile = LoadJpaQueries.getQueryById(Constants.CHECK_ATP_FILE_ID_IN_IA_FILE_STATS);
		String fileName = ackFileInfoDto.getTrxFileName();
		
		String ConditionFileName = (fileName != null && (fileName.endsWith(".ICTX")||fileName.endsWith(".ITXC")))?Constants.QUERY_PARAM_ICTX_FILE_NAME:Constants.QUERY_PARAM_ICRX_FILE_NAME;
		
		queryFile =queryFile + " WHERE " + ConditionFileName +" = "+(":"+ConditionFileName);
		
		dao_log.info("checkAtpFileInStats queryFile{}",queryFile);
		dao_log.info("checkAtpFileInStats paramSource fileName{}",fileName);
		
		paramSource.addValue(ConditionFileName , fileName);

		List<IaFileStats> val = namedJdbcTemplate.query(queryFile,paramSource,BeanPropertyRowMapper.newInstance(IaFileStats.class));
		if (val.isEmpty()) {
			return null;
		} else {
			return Long.valueOf(val.get(0).getAtpFileId());
		}

	}

	
}
