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
import com.conduent.tpms.iag.dto.TFOFileStats;
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
	
	//
	@Override
	public Long updateAckStatus(AckFileInfoDto ackFileInfoDto) {

		KeyHolder keyHolder = new GeneratedKeyHolder();
		String query = LoadJpaQueries.getQueryById("UPDATE_ACK_FILE_STATUS");

		MapSqlParameterSource paramSource = null;

		paramSource = new MapSqlParameterSource();
		if(ackFileInfoDto.getAckFileName()!=null)
		paramSource.addValue(Constants.ACK_FILE_NAME, ackFileInfoDto.getAckFileName());
		if(ackFileInfoDto.getAckFileDate()!=null)
		paramSource.addValue(Constants.ACK_FILE_DATE, ackFileInfoDto.getAckFileDate());
		if(ackFileInfoDto.getAckFileTime()!=null)
		paramSource.addValue(Constants.ACK_FILE_TIME, ackFileInfoDto.getAckFileTime().replaceAll("(?<=\\G\\d{2})", ":").substring(0,8));
		if(ackFileInfoDto.getFileType()!=null)
		paramSource.addValue(Constants.ACK_FILE_TYPE, ackFileInfoDto.getFileType());
		if(ackFileInfoDto.getAckFileStatus()!=null)
		paramSource.addValue(Constants.ACK_FILE_STATUS, ackFileInfoDto.getAckFileStatus());
		if(ackFileInfoDto.getTrxFileName()!=null)
		paramSource.addValue(Constants.TRX_FILE_NAME, ackFileInfoDto.getTrxFileName());
		
		paramSource.addValue(Constants.RECON_FILE_NAME, ackFileInfoDto.getReconFileName());
		
		if(ackFileInfoDto.getFileType()!=null)
		paramSource.addValue(Constants.FILE_TYPE, ackFileInfoDto.getFileType());
		if(ackFileInfoDto.getFromAgency()!=null)
		paramSource.addValue(Constants.FROM_AGENCY, ackFileInfoDto.getFromAgency());
		if(ackFileInfoDto.getToAgency()!=null)
		paramSource.addValue(Constants.TO_AGENCY, ackFileInfoDto.getToAgency());
		if(ackFileInfoDto.getExternFileId()!=null)
		paramSource.addValue(Constants.EXTERN_FILE_ID, ackFileInfoDto.getExternFileId());
		if(ackFileInfoDto.getAtpFileId()!=null)
		paramSource.addValue(Constants.ATP_FILE_ID, ackFileInfoDto.getAtpFileId());
		namedJdbcTemplate.update(query, paramSource, keyHolder, new String[] { Constants.ACK_FILE_NAME });
		Map<String, Object> map = keyHolder.getKeys();
		String fileName = null;
		if (null != map) {
			fileName = map.get(Constants.ACK_FILE_NAME).toString();
		}
		dao_log.info("Entry In ACK table sucessfully updated");
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
	public TFOFileStats getFOFileStatsInfo(String foStatsFileName) {

		if (foStatsFileName.substring(23, 27).equals(Constants.FNTX_FILE_NAME)
				|| foStatsFileName.substring(23, 27).equals(Constants.FTXN_FILE_NAME)) {
			// GetFileStatsInfoFNTXSql
			String queryRules = LoadJpaQueries.getQueryById("GET_FILE_STATS_INFO_FNTX_SQL");

			MapSqlParameterSource paramSource = null;

			paramSource = new MapSqlParameterSource();
			
			paramSource.addValue(Constants.IN_TRX_FILE_NAME, foStatsFileName);
			

			List<TFOFileStats> listOfObj = namedJdbcTemplate.query(queryRules, paramSource,
					BeanPropertyRowMapper.newInstance(TFOFileStats.class));

			Optional<TFOFileStats> iagFileInfo = listOfObj.stream().findFirst();

			if (iagFileInfo.isPresent()) {
				return iagFileInfo.get();
			}
		} else if (foStatsFileName.substring(23, 27).equals(Constants.FNDX_FILE_NAME)
				|| foStatsFileName.substring(23, 27).equals(Constants.FDXN_FILE_NAME)) {
			// GetFileStatsInfoFNDXSql
			String queryRules = LoadJpaQueries.getQueryById("GET_FILE_STATS_INFO_FNDX_SQL");

			MapSqlParameterSource paramSource = null;

			paramSource = new MapSqlParameterSource();
			paramSource.addValue(Constants.IN_DIST_FILE_NAME, foStatsFileName);

			List<TFOFileStats> listOfObj = namedJdbcTemplate.query(queryRules, paramSource,
					BeanPropertyRowMapper.newInstance(TFOFileStats.class));

			Optional<TFOFileStats> iagFileInfo = listOfObj.stream().findFirst();

			if (iagFileInfo.isPresent()) {
				return iagFileInfo.get();
			}
		} else if (foStatsFileName.substring(23, 27).equals(Constants.FNRX_FILE_NAME)
				|| foStatsFileName.substring(23, 27).equals(Constants.FRXN_FILE_NAME)) {
			// GetFileStatsInfoFNRXSql
			String queryRules = LoadJpaQueries.getQueryById("GET_FILE_STATS_INFO_FNRX_SQL");

			MapSqlParameterSource paramSource = null;

			paramSource = new MapSqlParameterSource();
			paramSource.addValue(Constants.IN_RECON_FILE_NAME, foStatsFileName);

			List<TFOFileStats> listOfObj = namedJdbcTemplate.query(queryRules, paramSource,
					BeanPropertyRowMapper.newInstance(TFOFileStats.class));

			Optional<TFOFileStats> iagFileInfo = listOfObj.stream().findFirst();

			if (iagFileInfo.isPresent()) {
				return iagFileInfo.get();
			}
		} else if (foStatsFileName.substring(23, 27).equals(Constants.INRX_FILE_NAME)
				|| foStatsFileName.substring(23, 27).equals(Constants.IRXN_FILE_NAME)) {
			// GetFileStatsInfoINRXSql
			String queryRules = LoadJpaQueries.getQueryById("GET_FILE_STATS_INFO_INRX_SQL");

			MapSqlParameterSource paramSource = null;

			paramSource = new MapSqlParameterSource();
			paramSource.addValue(Constants.IN_RECON_FILE_NAME, foStatsFileName);
			
			List<TFOFileStats> listOfObj = namedJdbcTemplate.query(queryRules, paramSource,
					BeanPropertyRowMapper.newInstance(TFOFileStats.class));

			Optional<TFOFileStats> iagFileInfo = listOfObj.stream().findFirst();

			if (iagFileInfo.isPresent()) {
				return iagFileInfo.get();
			}
		} else if (foStatsFileName.substring(23, 27).equals(Constants.INTX_FILE_NAME)
				|| foStatsFileName.substring(23, 27).equals(Constants.ITXN_FILE_NAME)) {
			// GetFileStatsInfoINTXSql

			String is_home_agency = LoadJpaQueries.getQueryById("GET_IS_HOME_AGENCY_FLAG");

			MapSqlParameterSource paramSource = null;

			paramSource = new MapSqlParameterSource();
			paramSource.addValue(Constants.IN_DEVICE_PREFIX_LCA, foStatsFileName.substring(0, 4));

			if (is_home_agency == "Y") {

				String queryRules = LoadJpaQueries.getQueryById("GET_FILE_STATS_INFO_INTX_SQL");

				MapSqlParameterSource paramSource1 = null;

				paramSource1 = new MapSqlParameterSource();
				paramSource1.addValue(Constants.IN_TRX_FILE_NAME, foStatsFileName);

				List<TFOFileStats> listOfObj = namedJdbcTemplate.query(queryRules, paramSource1,
						BeanPropertyRowMapper.newInstance(TFOFileStats.class));

				Optional<TFOFileStats> iagFileInfo = listOfObj.stream().findFirst();

				if (iagFileInfo.isPresent()) {
					return iagFileInfo.get();
				}

			} else {

				String queryRules = LoadJpaQueries.getQueryById("GET_FILE_STATS_INFO_ITXN_SQL");

				MapSqlParameterSource paramSource1 = null;

				paramSource1 = new MapSqlParameterSource();
				paramSource1.addValue(Constants.IN_TRX_FILE_NAME, foStatsFileName);

				List<TFOFileStats> listOfObj = namedJdbcTemplate.query(queryRules, paramSource1,
						BeanPropertyRowMapper.newInstance(TFOFileStats.class));

				Optional<TFOFileStats> iagFileInfo = listOfObj.stream().findFirst();

				if (iagFileInfo.isPresent()) {
					return iagFileInfo.get();
				}

			}

		}

		return null;
	}
	
	public Long checkAtpFileInStats(AckFileInfoDto ackFileInfoDto) {
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		String queryFile = LoadJpaQueries.getQueryById(Constants.CHECK_ATP_FILE_ID_IN_IA_FILE_STATS);
		//something wrong over here
		String fileName = ackFileInfoDto.getTrxFileName();
		String ConditionFileName =null;
		//String ConditionFileName = (fileName != null && (fileName.endsWith(".FNTX")||fileName.endsWith(".FTXN")))?Constants.FNTX_FILE_NAME:Constants.QUERY_PARAM_ICRX_FILE_NAME;
		if (fileName!=null && fileName.endsWith(".FNTX")||fileName.endsWith(".FTXN")) {
			ConditionFileName = Constants.IN_TRX_FILE_NAME;
		}else if(fileName!=null && fileName.endsWith(".FNDX")||fileName.endsWith(".FDXN")) {
			ConditionFileName = Constants.IN_DIST_FILE_NAME;
		}else if(fileName!=null && fileName.endsWith(".FNRX")||fileName.endsWith(".FRXN")) {
			ConditionFileName = Constants.IN_RECON_FILE_NAME;
		}else if(fileName!=null && fileName.endsWith(".INRX")||fileName.endsWith(".IRXN")) {
			ConditionFileName = Constants.IN_RECON_FILE_NAME;
		}else if(fileName!=null && fileName.endsWith(".INTX")||fileName.endsWith(".ITXN")) {
			ConditionFileName = Constants.IN_TRX_FILE_NAME;
		}
		//input needs to be provided for QUERY_PARAM_ICTX_FILE_NAME and QUERY_PARAM_ICRX_FILE_NAME
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
