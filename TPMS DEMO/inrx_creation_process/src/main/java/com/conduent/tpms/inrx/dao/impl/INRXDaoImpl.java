package com.conduent.tpms.inrx.dao.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.conduent.tpms.inrx.config.LoadJpaQueries;
import com.conduent.tpms.inrx.constants.Constants;
import com.conduent.tpms.inrx.dao.INRXDao;
import com.conduent.tpms.inrx.model.FileCreationParameters;
import com.conduent.tpms.inrx.model.FileInfoDto;
import com.conduent.tpms.inrx.model.FileStats;
import com.conduent.tpms.inrx.model.MappingInfoDto;
import com.conduent.tpms.inrx.model.TxDetailRecord;


/**
 * Interface for DAO operations
 * 
 * @author petetid
 *
 */
@Repository
public class INRXDaoImpl implements INRXDao
{
	private static final Logger log = LoggerFactory.getLogger(INRXDaoImpl.class);
	
	@Autowired
	private NamedParameterJdbcTemplate namedJdbcTemplate;
	
	@Override
	public List<FileStats> getFileControlIdIdListForINTXFiles(String fileType, String agencyId) {
		MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
		//mapSqlParameterSource.addValue(Constants.FILE_TYPE, fileType);
		//Long agencyID = Long.parseLong((StringUtils.leftPad(agencyId, 3, "0")));
		String agencyID = StringUtils.leftPad(agencyId, 3, "0");
		mapSqlParameterSource.addValue(Constants.AGENCY_ID, agencyID);
		String queryRules = null;
		List<FileStats> list = null;
		try {
		queryRules = LoadJpaQueries.getQueryById("GET_FILE_CONTROL_IDS");
		log.info("mapSqlParameterSource: {}", mapSqlParameterSource);
		list = namedJdbcTemplate.query(queryRules, mapSqlParameterSource, BeanPropertyRowMapper.newInstance(FileStats.class));
		log.info("FileStats list fetched from T_FO_RECON_FILE_STATS table successfully:{}",list);
		}catch (Exception e) {
			log.error("Query for agencyId {} failed. SQL Query: {}",agencyID, queryRules);
			log.error("Exception: {}",e);
			log.error("Exception: {}",e.getMessage());
		}
		return list;
	}
	
	@Override
	public long getHCountForINTXFile(long fileControlId) {
		MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
		mapSqlParameterSource.addValue(Constants.FILE_CONTROL_ID, fileControlId);
		String queryRules = null;
		long inputCount = 0;
		try {
		queryRules = LoadJpaQueries.getQueryById("GET_FILE_COUNT_FROM_TRAN_DETAIL");
		inputCount = namedJdbcTemplate.queryForObject(queryRules, mapSqlParameterSource, Long.class);
		log.info("HCount fetched from t_tran_detail table successfully: {}",inputCount);
		}catch (Exception e) {
			log.error("Query for FILE_CONTROL_ID {} failed. SQL Query: {}",fileControlId, queryRules);
			e.printStackTrace();
		}
		return inputCount;
	}
	
	@Override
	public long getECountForINTXFile(long fileControlId) {
		MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
		mapSqlParameterSource.addValue(Constants.FILE_CONTROL_ID, fileControlId);
		String queryRules = null;
		long inputCount = 0;
		try {
		queryRules = LoadJpaQueries.getQueryById("GET_FILE_COUNT_FROM_ACCT_TOLL");
		inputCount = namedJdbcTemplate.queryForObject(queryRules, mapSqlParameterSource, Long.class);
		log.info("ECount fetched from t_account_toll table successfully: {}",inputCount);
		}catch (Exception e) {
			log.error("Query for FILE_CONTROL_ID {} failed. SQL Query: {}",fileControlId, queryRules);
			e.printStackTrace();
		}
		return inputCount;
	}

	@Override
	public FileCreationParameters getMappingConfigurationDetails(FileCreationParameters fileDto) {

		log.info("Getting mapping configuration from table..");
		FileCreationParameters fileDetails = new FileCreationParameters();
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		String queryFile = LoadJpaQueries.getQueryById("GET_OUTPUT_FILE_CONFIGURATION_INFO");
		try {
		
		paramSource.addValue(Constants.FILE_TYPE, fileDto.getFileType());
		paramSource.addValue(Constants.AGENCY_ID, fileDto.getAgencyId());
		paramSource.addValue(Constants.FILE_FORMAT, fileDto.getGenType());

		FileInfoDto info = namedJdbcTemplate.queryForObject(queryFile, paramSource,
				BeanPropertyRowMapper.newInstance(FileInfoDto.class));
		fileDetails.setFileInfoDto(info);
		log.debug("File info dto value: {}",fileDetails.getFileInfoDto().toString());

		String queryRules = LoadJpaQueries.getQueryById("GET_OUTPUT_FIELD_VALIDATION_DETAILS");

		List<MappingInfoDto> nameMappingDetails = namedJdbcTemplate.query(queryRules, paramSource,
				BeanPropertyRowMapper.newInstance(MappingInfoDto.class));

		List<MappingInfoDto> fileNameMappingDetails = nameMappingDetails.stream()
				.filter(f -> f.getFieldType().equals("FILENAME")).collect(Collectors.toList());
		log.debug(fileNameMappingDetails.toString());
		fileDetails.setFileNameMappingInfo(fileNameMappingDetails);

		List<MappingInfoDto> headerMappingDetails = nameMappingDetails.stream()
				.filter(f -> f.getFieldType().equals("HEADER")).collect(Collectors.toList());
		log.debug(headerMappingDetails.toString());
		fileDetails.setHeaderMappingInfoList(headerMappingDetails);

		List<MappingInfoDto> detailMappingDetails = nameMappingDetails.stream()
				.filter(f -> f.getFieldType().equals("DETAIL")).collect(Collectors.toList());
		log.debug(detailMappingDetails.toString());
		fileDetails.setDetailRecMappingInfo(detailMappingDetails);
		
		List<MappingInfoDto> trailerMappingDetails = nameMappingDetails.stream()
				.filter(f -> f.getFieldType().equals("TRAILER")).collect(Collectors.toList());
		log.debug(trailerMappingDetails.toString());
		fileDetails.setTrailerMappingInfoDto(trailerMappingDetails);

		log.info("Collected mapping configuration from table..");
		}catch (Exception e) {
			log.error("SQL query failed: {}", queryFile);
			e.printStackTrace();
		}
		return fileDetails;
	}

	@Override
	public List<TxDetailRecord> getTxRecordList(long fileControlId, long plazaAgencyId) {
		MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
		mapSqlParameterSource.addValue(Constants.FILE_CONTROL_ID, fileControlId);
		mapSqlParameterSource.addValue(Constants.PLAZA_AGENCY_ID, plazaAgencyId);
		String queryRules = null;
		List<TxDetailRecord> list = null;
		try {
		queryRules = LoadJpaQueries.getQueryById("GET_TX_DETAILS");
		log.info("Transaction record mapSqlParameterSource for plazaAgencyId and FILE_CONTROL_ID: {}", mapSqlParameterSource);
		list = namedJdbcTemplate.query(queryRules, mapSqlParameterSource, BeanPropertyRowMapper.newInstance(TxDetailRecord.class));
		log.info("Detail records fetched from T_ICRX_RECON table successfully. list: {}",list);
		}catch (Exception e) {
			log.error("Query for xferControlId {} and plazaAgencyId {} failed. SQL Query: {}",fileControlId, plazaAgencyId, queryRules);
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public String getPostStatusForId(String txCodeId) {
		MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
		mapSqlParameterSource.addValue(Constants.CODE_ID, txCodeId);
		String code = null;
		try {
		String queryRules = LoadJpaQueries.getQueryById("GET_TX_STATUS_VALUE");
		code = namedJdbcTemplate.queryForObject(queryRules, mapSqlParameterSource, String.class);
		log.info("Tx Status code Value {} fetched for Id {} from master.t_codes table successfully.",code,txCodeId);
		} catch (Exception e) {
			log.error("Tx Status code Value not found for id {} from master.t_codes table.",txCodeId);
			e.printStackTrace();
		}
		return code;
	}

	@Override
	public String getPlanNameForPlanId(String planTypeId) {
		MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
		mapSqlParameterSource.addValue(Constants.PLAN_TYPE_ID, planTypeId);
		String planName = null;
		try {
			String queryRules = LoadJpaQueries.getQueryById("GET_PLAN_VALUE");
			planName = namedJdbcTemplate.queryForObject(queryRules, mapSqlParameterSource, String.class);
			log.info("planName Value {} fetched for id {} from CRM.v_plan_policy table successfully.",planName,planTypeId);
			return planName;
		} catch (Exception e) {
			log.error("planName Value not found for id {} from CRM.v_plan_policy table.",planTypeId);
			//e.printStackTrace();
			return null;
		}
		
	}

	@Override
	public void updateINRXDetailsIntoFileStats(FileStats fileStatsRecord) {
		MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
		mapSqlParameterSource.addValue(Constants.INRX_FILE_NAME, fileStatsRecord.getEpsReconFileName());
		mapSqlParameterSource.addValue(Constants.H_RECORD_COUNT, fileStatsRecord.getEpsReconCount());
		mapSqlParameterSource.addValue(Constants.PROCESSED_FLAG, fileStatsRecord.getProcessedFlag());
		mapSqlParameterSource.addValue(Constants.FILE_CONTROL_ID, fileStatsRecord.getFileControlId());
		
		
		
		String queryRules = null;
		try {
			queryRules = LoadJpaQueries.getQueryById("UPDATE_T_FO_RECON_FILE_STATS");
			int id = namedJdbcTemplate.update(queryRules, mapSqlParameterSource);
			log.info("TPMS.T_FO_RECON_FILE_STATS table updated successfully with id:{} for INRX file: {}.",id, fileStatsRecord.getTrxEpsFileName() );
		} catch (Exception e) {
			log.error("TPMS.T_FO_RECON_FILE_STATS table failed to update for query:{} ",queryRules);
			e.printStackTrace();
		}
	}
	
}
