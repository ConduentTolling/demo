package com.conduent.tpms.qatp.dao.impl;

import java.math.BigInteger;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.conduent.app.timezone.utility.TimeZoneConv;
import com.conduent.tpms.qatp.config.LoadJpaQueries;
import com.conduent.tpms.qatp.constants.Constants;
import com.conduent.tpms.qatp.dao.IQatpDao;
import com.conduent.tpms.qatp.dto.AckFileInfoDto;
import com.conduent.tpms.qatp.dto.FileDetailsDto;
import com.conduent.tpms.qatp.dto.FileInfoDto;
import com.conduent.tpms.qatp.dto.MappingInfoDto;
import com.conduent.tpms.qatp.dto.PbFileStatsDto;
import com.conduent.tpms.qatp.dto.PbaDetailInfoVO;

@Repository
public class QatpDaoImpl implements IQatpDao {
	
	private static final Logger logger = LoggerFactory.getLogger(QatpDaoImpl.class);

	@Autowired
	private NamedParameterJdbcTemplate namedJdbcTemplate;

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	TimeZoneConv timeZoneConv;

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
	
	@Override
	public Long insertPbFileStats(PbFileStatsDto pbFileStatsDto) 
	{

		KeyHolder keyHolder = new GeneratedKeyHolder();
		String query = LoadJpaQueries.getQueryById("INSERT_PB_FILE_STATS");

		MapSqlParameterSource paramSource = null;

		paramSource = new MapSqlParameterSource();
		
		paramSource.addValue(Constants.XFER_CONTROL_ID, pbFileStatsDto.getXferControlId());
		paramSource.addValue(Constants.ICTX_FILE_NAME, pbFileStatsDto.getIctxFileName());
		paramSource.addValue(Constants.FILE_SEQ_NUMBER, pbFileStatsDto.getFileSeqNumber());
		paramSource.addValue(Constants.INPUT_COUNT, pbFileStatsDto.getInputCount());
		paramSource.addValue(Constants.FILE_TYPE, pbFileStatsDto.getFileType());
		paramSource.addValue(Constants.FROM_AGENCY, pbFileStatsDto.getFromAgency());
		paramSource.addValue(Constants.TO_AGENCY, pbFileStatsDto.getToAgency());
		paramSource.addValue(Constants.FILE_DATE, pbFileStatsDto.getFileDate());
		paramSource.addValue(Constants.PROCESSED_FLAG, pbFileStatsDto.getProcessedFlag());
		paramSource.addValue(Constants.UPDATE_TS, timeZoneConv.currentTime());
		
		namedJdbcTemplate.update(query, paramSource, keyHolder, new String[] { Constants.ICTX_FILE_NAME });
		Map<String, Object> map = keyHolder.getKeys();
		String fileName = null;
		
		if (null != map) 
		{
			fileName = map.get(Constants.ICTX_FILE_NAME).toString();
			logger.info("Data inserted in T_PB_FILE_STATS successfully for Filename : {}", fileName);
		}
		else 
		{
			logger.info("Data inserted in T_PB_FILE_STATS failed for Filename : {}", fileName);
		}
	
		return fileName != null ? 1L : 0L;
	}

	
}
