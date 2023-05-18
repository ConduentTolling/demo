package com.conduent.tpms.icrx.dao.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.conduent.tpms.icrx.config.LoadJpaQueries;
import com.conduent.tpms.icrx.constants.Constants;
import com.conduent.tpms.icrx.dao.ICRXDao;
import com.conduent.tpms.icrx.model.FileCreationParameters;
import com.conduent.tpms.icrx.model.FileInfoDto;
import com.conduent.tpms.icrx.model.FileStats;
import com.conduent.tpms.icrx.model.MappingInfoDto;
import com.conduent.tpms.icrx.model.TxDetailRecord;


/**
 * Interface for DAO operations
 * 
 * @author Urvashi C
 *
 */
@Repository
public class ICRXDaoImpl implements ICRXDao
{
	private static final Logger log = LoggerFactory.getLogger(ICRXDaoImpl.class);
	
	@Autowired
	private NamedParameterJdbcTemplate namedJdbcTemplate;
	
	@Override
	public List<FileStats> getXferIdListForICTXFiles(String fileType, String agencyId) {
		MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
		mapSqlParameterSource.addValue(Constants.FILE_TYPE, fileType);
		//Long agencyID = Long.parseLong((StringUtils.leftPad(agencyId, 3, "0")));
		Long agencyID = Long.parseLong(agencyId);
		mapSqlParameterSource.addValue(Constants.AGENCY_ID, agencyID);
		String queryRules = null;
		List<FileStats> list = null;
		try {
			queryRules = LoadJpaQueries.getQueryById(Constants.GET_XFER_CONTROL_IDS);
			log.info("mapSqlParameterSource: {}", mapSqlParameterSource);
			list = namedJdbcTemplate.query(queryRules, mapSqlParameterSource, BeanPropertyRowMapper.newInstance(FileStats.class));
			log.info("FileStats list fetched from T_IA_FILE_STATS table successfully:{}",list);
		} catch (Exception e) {
			log.error("Query for agencyId {} failed. SQL Query: {}",agencyId, queryRules);
			log.error("Exception: {}",e);
			log.error("Exception: {}",e.getMessage());
		}
		return list;
	}
	
	@Override
	public long getHCountForICTXFile(long xferControlId) {
		MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
		mapSqlParameterSource.addValue(Constants.XFER_CONTROL_ID, xferControlId);
		String queryRules = null;
		long inputCount = 0;
		try {
			queryRules = LoadJpaQueries.getQueryById(Constants.GET_FILE_COUNT_FROM_TRAN_DETAIL);
			inputCount = namedJdbcTemplate.queryForObject(queryRules, mapSqlParameterSource, Long.class);
			log.info("HCount fetched from t_tran_detail table successfully: {}",inputCount);
		} catch (Exception e) {
			log.error("Query for xferControlId {} failed. SQL Query: {}",xferControlId, queryRules);
			e.printStackTrace();
		}
		return inputCount;
	}
	
	@Override
	public long getECountForICTXFile(long xferControlId) {
		MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
		mapSqlParameterSource.addValue(Constants.XFER_CONTROL_ID, xferControlId);
		String queryRules = null;
		long inputCount = 0;
		try {
			queryRules = LoadJpaQueries.getQueryById(Constants.GET_FILE_COUNT_FROM_ACCT_TOLL);
			inputCount = namedJdbcTemplate.queryForObject(queryRules, mapSqlParameterSource, Long.class);
			log.info("ECount fetched from t_account_toll table successfully: {}",inputCount);
		} catch (Exception e) {
			log.error("Query for xferControlId {} failed. SQL Query: {}",xferControlId, queryRules);
			e.printStackTrace();
		}
		return inputCount;
	}
	
	@Override
	public long getECountForICTXFileFromICRXRecon(long xferControlId) {
		MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
		mapSqlParameterSource.addValue(Constants.XFER_CONTROL_ID, xferControlId);
		String queryRules = null;
		try {
			long inputCount = 0;
			queryRules = LoadJpaQueries.getQueryById(Constants.GET_FILE_COUNT_FROM_ICRX_RECON);
			inputCount = namedJdbcTemplate.queryForObject(queryRules, mapSqlParameterSource, Long.class);
			log.info("ECount fetched from T_ICRX_RECON table successfully: {}", inputCount);
			return inputCount;
		} catch (Exception e) {
			log.error("Query for xferControlId {} failed. SQL Query: {}",xferControlId, queryRules);
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public FileCreationParameters getMappingConfigurationDetails(FileCreationParameters fileDto) {

		log.info("Getting mapping configuration from table..");
		FileCreationParameters fileDetails = new FileCreationParameters();
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		String queryFile = LoadJpaQueries.getQueryById(Constants.GET_OUTPUT_FILE_CONFIGURATION_INFO);
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
		} catch (Exception e) {
			log.error("SQL query failed: {}", queryFile);
			e.printStackTrace();
		}
		return fileDetails;
	}

	@Override
	public List<TxDetailRecord> getTxRecordList(long xferControlId, long plazaAgencyId) {
		MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
		mapSqlParameterSource.addValue(Constants.XFER_CONTROL_ID, xferControlId);
		mapSqlParameterSource.addValue(Constants.PLAZA_AGENCY_ID, plazaAgencyId);
		String queryRules = null;
		List<TxDetailRecord> list = null;
		try {
			queryRules = LoadJpaQueries.getQueryById(Constants.GET_TX_DETAILS);
			log.info("Transaction record mapSqlParameterSource for plazaAgencyId and XFER_CONTROL_ID: {}", mapSqlParameterSource);
			list = namedJdbcTemplate.query(queryRules, mapSqlParameterSource, BeanPropertyRowMapper.newInstance(TxDetailRecord.class));
			log.info("Detail records fetched from T_ICRX_RECON table successfully. list: {}",list);
		} catch (Exception e) {
			log.error("Query for xferControlId {} and plazaAgencyId {} failed. SQL Query: {}",xferControlId, plazaAgencyId, queryRules);
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
			String queryRules = LoadJpaQueries.getQueryById(Constants.GET_TX_STATUS_VALUE);
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
			String queryRules = LoadJpaQueries.getQueryById(Constants.GET_PLAN_VALUE);
			planName = namedJdbcTemplate.queryForObject(queryRules, mapSqlParameterSource, String.class);
			log.info("planName Value {} fetched for id {} from CRM.v_plan_policy table successfully.",planName,planTypeId);
		} catch (Exception e) {
			log.error("planName Value not found for id {} from CRM.v_plan_policy table.",planTypeId);
			e.printStackTrace();
		}
		return planName;
	}

	@Override
	public void updateICRXDetailsIntoFileStats(FileStats fileStatsRecord) {
		MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
		mapSqlParameterSource.addValue(Constants.ICRX_FILE_NAME, fileStatsRecord.getIcrxFileName());
		mapSqlParameterSource.addValue(Constants.H_RECORD_COUNT, fileStatsRecord.getOutputCount());
		mapSqlParameterSource.addValue(Constants.PROCESSED_FLAG, fileStatsRecord.getProcessedFlag());
		mapSqlParameterSource.addValue(Constants.XFER_CONTROL_ID, fileStatsRecord.getXferControlId());
		mapSqlParameterSource.addValue(Constants.DEPOSIT_ID, fileStatsRecord.getDepositId());
		mapSqlParameterSource.addValue(Constants.QUERY_PARAM_UPDATE_TS, fileStatsRecord.getUpdateTs());
		
		String queryRules = null;
		try {
			queryRules = LoadJpaQueries.getQueryById(Constants.UPDATE_T_IA_FILE_STATS);
			int id = namedJdbcTemplate.update(queryRules, mapSqlParameterSource);
			log.info("TPMS.T_IA_FILE_STATS table updated successfully with id:{} for ICRX file: {}.",id, fileStatsRecord.getIcrxFileName() );
		} catch (Exception e) {
			log.error("TPMS.T_IA_FILE_STATS table failed to update for query:{} ",queryRules);
			e.printStackTrace();
		}
	}
	
	@Override
	public String getDupExternRefForRJDPTxn(String transactionRowId) {
		MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
		mapSqlParameterSource.addValue(Constants.TRANSACTION_ROW_ID, transactionRowId);
		String queryRules = null;
		String extRefNo = "0";
		try {
			queryRules = LoadJpaQueries.getQueryById(Constants.GET_DUP_EXTERN_REF_NO);
			extRefNo = namedJdbcTemplate.queryForObject(queryRules, mapSqlParameterSource, String.class);
			log.info("DUP_EXTERN_REF_NO fetched from table successfully: {}", extRefNo);
		} catch (Exception e) {
			log.error("Query for Dup ExternRefNo {} failed. SQL Query: {}", transactionRowId, queryRules);
			e.printStackTrace();
			return "0";
		}
		return extRefNo.isEmpty() || extRefNo.isBlank() ? "0" : extRefNo;
	}
	
}
