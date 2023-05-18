package com.conduent.tpms.reconciliation.daoImpl;

import java.time.LocalDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.conduent.app.timezone.utility.TimeZoneConv;
import com.conduent.tpms.reconciliation.constant.LoadJpaQueries;
import com.conduent.tpms.reconciliation.constant.ReconciliationConstant;
import com.conduent.tpms.reconciliation.dao.ReconciliationDao;
import com.conduent.tpms.reconciliation.dto.AccountTollDto;
import com.conduent.tpms.reconciliation.dto.AgencyPendingDto;
import com.conduent.tpms.reconciliation.dto.EntryTxDto;
import com.conduent.tpms.reconciliation.dto.ExitTxDto;
import com.conduent.tpms.reconciliation.dto.PlazaDto;
import com.conduent.tpms.reconciliation.dto.ReconciliationDto;
import com.conduent.tpms.reconciliation.dto.RtQueueDto;
import com.conduent.tpms.reconciliation.dto.TLaneDto;
import com.conduent.tpms.reconciliation.dto.TranDetailDto;
import com.conduent.tpms.reconciliation.dto.UpdateReconDto;
import com.conduent.tpms.reconciliation.dto.ViolTxDto;
import com.conduent.tpms.reconciliation.model.RTMapping;
import com.conduent.tpms.reconciliation.model.ReconPolicy;



/**
 * Compute Toll Dao Implementation
 *
 */
@Repository
public class ReconciliationDaoImpl implements ReconciliationDao {

	@Autowired
	private NamedParameterJdbcTemplate namedJdbcTemplate;

	@Autowired
	private JdbcTemplate jdbcTemplate;
	

	@Autowired 
	  private TimeZoneConv timeZoneConv;
	 
	
	private static final Logger logger = LoggerFactory.getLogger(ReconciliationDaoImpl.class);


	
	
	@Override
	public List<RtQueueDto> getLaneTxId() {
		String query = LoadJpaQueries.getQueryById("SELECT_T_RT_QUEUE");
		
		List<RtQueueDto> list = jdbcTemplate.query(query,
				new BeanPropertyRowMapper<RtQueueDto>(RtQueueDto.class));
		
		if (!list.isEmpty()) {
			return list;
		}
		return null;
	}

	@Override
	public List<AccountTollDto> getTAccountData(Long laneTxId) {

		String query = LoadJpaQueries.getQueryById("SELECT_T_ACCOUNT_TOLL");

		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue("LANE_TX_ID", laneTxId);
		List<AccountTollDto> list = namedJdbcTemplate.query(query,paramSource,
				new BeanPropertyRowMapper<AccountTollDto>(AccountTollDto.class));
		
		if (!list.isEmpty()) {
			return list;
		}
		return null;
	}
	
	@Override
	public List<TranDetailDto> getTranDetails(Long laneTxId) {

		String query = LoadJpaQueries.getQueryById("SELECT_T_TRAN_DETAIL");
		
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue("LANE_TX_ID", laneTxId);
		List<TranDetailDto> list = namedJdbcTemplate.query(query,paramSource,
				new BeanPropertyRowMapper<TranDetailDto>(TranDetailDto.class));
		
		if (!list.isEmpty()) {
			return list;
		}
		return null;
	}
	
	
	@Override
	public List<AgencyPendingDto> getAgencyPendingDetails(Long laneTxId) {

		String query = LoadJpaQueries.getQueryById("SELECT_T_AGENCY_PENDING_DETAIL");
		
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue("LANE_TX_ID", laneTxId);
		List<AgencyPendingDto> list = namedJdbcTemplate.query(query,paramSource,
				new BeanPropertyRowMapper<AgencyPendingDto>(AgencyPendingDto.class));
		
		if (!list.isEmpty()) {
			return list;
		}
		return null;
	}

	@Override
	public List<ViolTxDto> getViolTxDetails(Long laneTxId) {

		String query = LoadJpaQueries.getQueryById("SELECT_T_VIOL_TX");
		
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue("LANE_TX_ID", laneTxId);
		List<ViolTxDto> list = namedJdbcTemplate.query(query,paramSource,
				new BeanPropertyRowMapper<ViolTxDto>(ViolTxDto.class));
		
		if (!list.isEmpty()) {
			return list;
		}
		return null;
	}

	
	@Override
	public List<EntryTxDto> getEntryTxDetails(Long laneTxId) {

		String query = LoadJpaQueries.getQueryById("SELECT_T_ENTRY_TX");
		
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue("LANE_TX_ID", laneTxId);
		List<EntryTxDto> list = namedJdbcTemplate.query(query,paramSource,
				new BeanPropertyRowMapper<EntryTxDto>(EntryTxDto.class));
		
		if (!list.isEmpty()) {
			return list;
		}
		return null;
	}

	@Override
	public List<ExitTxDto> getExitTxDetails(Long laneTxId) {

		String query = LoadJpaQueries.getQueryById("SELECT_T_EXIT_TX");
		
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue("LANE_TX_ID", laneTxId);
		List<ExitTxDto> list = namedJdbcTemplate.query(query,paramSource,
				new BeanPropertyRowMapper<ExitTxDto>(ExitTxDto.class));
		
		if (!list.isEmpty()) {
			return list;
		}
		return null;
	}

	

	@Override
	public void insertIntoReconciliation(ReconciliationDto items) {
		// TODO Auto-generated method stub
		

		// TODO Auto-generated method stub
		logger.info("--------inside insertion method---------------");
		try {
		String queryRules = LoadJpaQueries.getQueryById("INSERT_INTO_T_RECONCILIATION");
		
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		
		logger.info("inside insertion method---------------------" + items.getLaneTxId());
		paramSource.addValue("laneTxId", items.getLaneTxId());
		paramSource.addValue("txExternRefNo", items.getTxExternRefNo());
		paramSource.addValue("debitCredit", items.getDebitCreditFlag());
		paramSource.addValue("postedFareAmount", items.getPostedFareAmount());
		paramSource.addValue("accountAgencyId", items.getAccountAgencyId());
		paramSource.addValue("revenueDate", items.getRevenueDate());
		paramSource.addValue("postedDate", items.getPostedDate());
		paramSource.addValue("trxSerialNumber", items.getTrxSerialNumber());
		paramSource.addValue("plateCountry", items.getPlateCountry());
		paramSource.addValue("plateState", items.getPlateState());
		paramSource.addValue("plateNumber", items.getPlateNumber());
		paramSource.addValue("txStatus", items.getTxStatus());
		paramSource.addValue("planTypeId", items.getPlanTypeId());
		paramSource.addValue("processStatus", items.getProcessStatus());
		
		paramSource.addValue("reconTxStatus", items.getReconTxStatus());
		paramSource.addValue("isFinalState", items.getIsFinalState());
		paramSource.addValue("createRt", items.getCreateRt());
		paramSource.addValue("reconStatusInd", items.getReconStatusInd());
		paramSource.addValue("reconSubCodeInd", items.getReconSubCodeInd());
		paramSource.addValue("plazaAgencyId", items.getPlazaAgencyId());
		paramSource.addValue("externFileId", items.getExternFileId());
		paramSource.addValue("plateType", items.getPlateType());
		//paramSource.addValue("updateTs", timeZoneConv.currentTime());
		paramSource.addValue("updateTs", LocalDateTime.now());
		paramSource.addValue("deleteFlag", items.getDeleteFlag());
		
		namedJdbcTemplate.update(queryRules, paramSource);
		
		}catch (Exception ex) {
			ex.getMessage();
			ex.printStackTrace();
			logger.info("Record not inserted into T_RECONCILIATION Table properly" + ex.getMessage());
		}
	
	}

	@Override
	public boolean isRecordExist(Long laneTxId) {
		
		Integer count = 0;
		String sql = "select count (*) from t_reconciliation where lane_tx_id= :LANE_TX_ID";
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue("LANE_TX_ID", laneTxId);
		count = namedJdbcTemplate.queryForObject(sql, paramSource, Integer.class);
		if (count >= 1) {
			return true;
		}
		return false;
	}
	
	

	@Override
	public void updateIntoReconciliation(ReconciliationDto rec) {
		
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		
		String updateQuery="UPDATE T_RECONCILIATION SET ";
		
		StringBuilder finalQueryToBeExecuted = new StringBuilder();
		logger.info("inside updatation method---------------------" + rec.getLaneTxId());
		paramSource.addValue("LANE_TX_ID", rec.getLaneTxId());

		if (notNull(rec.getTxExternRefNo())) {
			paramSource.addValue("TX_EXTERN_REF_NO", rec.getTxExternRefNo());
			finalQueryToBeExecuted.append(" TX_EXTERN_REF_NO=:TX_EXTERN_REF_NO,");
		}

		if (notNull(rec.getDebitCreditFlag())) {
			paramSource.addValue("DEBIT_CREDIT", rec.getDebitCreditFlag());
			finalQueryToBeExecuted.append(" DEBIT_CREDIT=:DEBIT_CREDIT,");
		}
		
		if (notNull(rec.getPostedFareAmount())) {
			paramSource.addValue("POSTED_FARE_AMOUNT", rec.getPostedFareAmount());
			finalQueryToBeExecuted.append(" POSTED_FARE_AMOUNT=:POSTED_FARE_AMOUNT,");
		}

		if (notNull(rec.getAccountAgencyId())) {
			paramSource.addValue("ACCOUNT_AGENCY_ID", rec.getAccountAgencyId());
			finalQueryToBeExecuted.append(" ACCOUNT_AGENCY_ID=:ACCOUNT_AGENCY_ID,");
		}

		if (notNull(rec.getRevenueDate())) {
			paramSource.addValue("REVENUE_DATE", rec.getRevenueDate());
			finalQueryToBeExecuted.append(" REVENUE_DATE=:REVENUE_DATE,");
		}

		if (notNull(rec.getPostedDate())) {
			paramSource.addValue("POSTED_DATE", rec.getPostedDate());
			finalQueryToBeExecuted.append(" POSTED_DATE=:POSTED_DATE,");
		}
		
		if (notNull(rec.getTrxSerialNumber())) {
			paramSource.addValue("TXN_SERIAL_NUMBER", rec.getTrxSerialNumber());
			finalQueryToBeExecuted.append(" TXN_SERIAL_NUMBER=:TXN_SERIAL_NUMBER,");
		}

		if (notNull(rec.getPlateState())) {
			paramSource.addValue("PLATE_STATE", rec.getPlateState());
			finalQueryToBeExecuted.append(" PLATE_STATE=:PLATE_STATE,");
		}

		if (notNull(rec.getPlateNumber())) {
			paramSource.addValue("PLATE_NUMBER", rec.getPlateNumber());
			finalQueryToBeExecuted.append(" PLATE_NUMBER=:PLATE_NUMBER,");
		}
		
		if (notNull(rec.getTxStatus())) {
			paramSource.addValue("TX_STATUS", rec.getTxStatus());
			finalQueryToBeExecuted.append(" TX_STATUS=:TX_STATUS,");
		}
		
		if (notNull(rec.getPlanTypeId())) {
			paramSource.addValue("PLAN_TYPE_ID", rec.getPlanTypeId());
			finalQueryToBeExecuted.append(" PLAN_TYPE_ID=:PLAN_TYPE_ID,");
		}
		
		if (notNull(rec.getProcessStatus())) {
			paramSource.addValue("PROCESS_STATUS", rec.getProcessStatus());
			finalQueryToBeExecuted.append(" PROCESS_STATUS=:PROCESS_STATUS,");
		}
		
		if (notNull(rec.getReconTxStatus())) {
			paramSource.addValue("RECON_TX_STATUS", rec.getReconTxStatus());
			finalQueryToBeExecuted.append(" RECON_TX_STATUS=:RECON_TX_STATUS,");
		}
		
		if (notNull(rec.getIsFinalState())) {
			paramSource.addValue("IS_FINAL_STATE", rec.getIsFinalState());
			finalQueryToBeExecuted.append(" IS_FINAL_STATE=:IS_FINAL_STATE,");
		}
		
		if (notNull(rec.getCreateRt())) {
			paramSource.addValue("CREATE_RT", rec.getProcessStatus());
			finalQueryToBeExecuted.append(" CREATE_RT=:CREATE_RT,");
		}
		
		if (notNull(rec.getReconStatusInd())) {
			paramSource.addValue("RECON_STATUS_IND", rec.getReconStatusInd());
			finalQueryToBeExecuted.append(" RECON_STATUS_IND=:RECON_STATUS_IND,");
		}
		
		if (notNull(rec.getReconSubCodeInd())) {
			paramSource.addValue("RECON_SUB_CODE_IND", rec.getReconSubCodeInd());
			finalQueryToBeExecuted.append(" RECON_SUB_CODE_IND=:RECON_SUB_CODE_IND,");
		}
		//PLAZA_AGENCY_ID
		if (notNull(rec.getPlazaAgencyId())) {
			paramSource.addValue("PLAZA_AGENCY_ID", rec.getPlazaAgencyId());
			finalQueryToBeExecuted.append(" PLAZA_AGENCY_ID=:PLAZA_AGENCY_ID,");
		}
		
		if (notNull(rec.getExternFileId())) {
			paramSource.addValue("EXTERN_FILE_ID", rec.getExternFileId());
			finalQueryToBeExecuted.append(" EXTERN_FILE_ID=:EXTERN_FILE_ID,");
		}
		
		if (notNull(rec.getPlateType())) {
			paramSource.addValue("PLATE_TYPE", rec.getPlateType());
			finalQueryToBeExecuted.append(" PLATE_TYPE=:PLATE_TYPE,");
		}
		
		if (notNull(rec.getUpdateTs())) {
			paramSource.addValue("UPDATE_TS", timeZoneConv.currentTime());
			finalQueryToBeExecuted.append(" UPDATE_TS=:UPDATE_TS,");
		}
		
		if (notNull(rec.getDeleteFlag())) {
			paramSource.addValue("DELETE_FLAG", rec.getDeleteFlag());
			finalQueryToBeExecuted.append(" DELETE_FLAG=:DELETE_FLAG,");
		}
		
		if (notNull(rec.getPlateCountry())) {
			paramSource.addValue("PLATE_COUNTRY", rec.getPlateCountry());
			finalQueryToBeExecuted.append(" PLATE_COUNTRY=:PLATE_COUNTRY ");
		}
		else {
			String singleQuery = finalQueryToBeExecuted.toString();
			singleQuery= singleQuery.substring(0, singleQuery.length() - 1);
			finalQueryToBeExecuted = new StringBuilder();
			finalQueryToBeExecuted.append(singleQuery);
		}
		
		paramSource.addValue("LANE_TX_ID", rec.getLaneTxId());
		finalQueryToBeExecuted.append(" WHERE LANE_TX_ID=:LANE_TX_ID");

		String updateQuery1 = updateQuery + finalQueryToBeExecuted.toString();
		logger.info("updateQuery :: {}", updateQuery1);
		namedJdbcTemplate.update(updateQuery1, paramSource);

	}
	
	
	public static boolean notNull(Object o) {
		if (o != null) {
			return true;
		} else {
			return false;
		}

	}

	@Override
	public List<TranDetailDto> getTranLaneTxId() {
		String query = LoadJpaQueries.getQueryById("SELECT_T_TRAN_DETAIL");
		//String query = "select * from TPMS.T_TRAN_DETAIL where lane_tx_id in (20000051845,20000051847, 20000051848)";//,20000051847,20000051848)";
		//String query = "select * from TPMS.T_TRAN_DETAIL where lane_tx_id in (20000151838,20000151839,20000151840,20000151842,20000151843)";
		//t_unmatched_entry
		//String query = "select * from TPMS.T_TRAN_DETAIL where lane_tx_id in (20000152074,20000131442,20000127199,20000127113)";
		//v_viol_tx
		//String query = "select * from TPMS.T_TRAN_DETAIL where lane_tx_id in (20000050954,20000050955,20000050956,200000861912)";
		List<TranDetailDto> list = jdbcTemplate.query(query,
				new BeanPropertyRowMapper<TranDetailDto>(TranDetailDto.class));
		
		if (!list.isEmpty()) {
			return list;
		}
		return null;
	}


	@Override
	public void insertIntoT_RT_CheckPoint(Long firstLaneTxId, Long lastLaneTxId, String tableName) {
		try {
			String query = LoadJpaQueries.getQueryById("INSERT_T_RT_CHECKPOINT");
			MapSqlParameterSource paramSource = new MapSqlParameterSource();

			paramSource.addValue("LANE_TX_ID", firstLaneTxId);
			paramSource.addValue("LANE_TX_ID_TO", lastLaneTxId);
			paramSource.addValue("TABLE_NAME", tableName);
			paramSource.addValue("UPDATE_TS", timeZoneConv.currentTime());

			namedJdbcTemplate.update(query, paramSource);
			logger.info("Successfully data inserted into T_RT_CHECKPOINT Table for Lane_TX_Id {}", firstLaneTxId);
		} catch (Exception ex) {
			ex.getMessage();
			ex.printStackTrace();
			logger.info("Record not inserted into T_RT_CHECKPOINT Table properly" + ex.getMessage());
		}
	}


	@Override
	public ReconPolicy getReconPolicyInfo(String status, Integer plazaAgencyId) {

		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		String queryFile = LoadJpaQueries.getQueryById("GET_RECON_POLICY_INFO");
		paramSource.addValue("CODE_ID", status);
		paramSource.addValue("PLAZA_AGENCY_ID",plazaAgencyId);
		paramSource.addValue("CODE_TYPE","TX_STATUS");
		List<ReconPolicy> reconPolicyInfoList = namedJdbcTemplate.query(queryFile, paramSource,
				BeanPropertyRowMapper.newInstance(ReconPolicy.class));
		if (reconPolicyInfoList.isEmpty()) {
			return null;
		} else {
			logger.info("Successfully data get from PolicyInfo Table");
			return reconPolicyInfoList.get(0);
		}
	}

	@Override
	public RTMapping getRtMappingInfo(Integer plazaAgencyId, String accountAgencyId, String planTypeId,
			Integer reconTxStatus) {

		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		String queryFile = LoadJpaQueries.getQueryById("GET_RT_MAPPPING_INFO");
		paramSource.addValue("PLAZA_AGENCY_ID", plazaAgencyId);
		paramSource.addValue("ACCOUNT_AGENCY_ID",accountAgencyId);
		paramSource.addValue("PLAN_TYPE",planTypeId);
		paramSource.addValue("RECON_TX_STATUS",reconTxStatus);
		List<RTMapping> rtMappingInfoList = namedJdbcTemplate.query(queryFile, paramSource,
				BeanPropertyRowMapper.newInstance(RTMapping.class));
		if (rtMappingInfoList.isEmpty()) {
			return null;
		} else {
			logger.info("Successfully data get from RT_MAPPING Table");
			return rtMappingInfoList.get(0);
		}
	}


	@Override
	public void insert_RT_CheckPoint_ForLastRun(LocalDateTime lastRunDateTime) {
		
		try {
			String query = LoadJpaQueries.getQueryById("INSERT_T_RT_CHECKPOINT_LASTRUN");
			MapSqlParameterSource paramSource = new MapSqlParameterSource();

			logger.info("Last Run Date time " + lastRunDateTime);
			logger.info("Update time stamp " + timeZoneConv.currentTime());
			
			paramSource.addValue("LAST_RUN_DATETIME", lastRunDateTime);
			paramSource.addValue("UPDATE_TS", timeZoneConv.currentTime());
			paramSource.addValue("TABLE_NAME", "T_RECONCILIATION");

			namedJdbcTemplate.update(query, paramSource);
			logger.info("Successfully data inserted into T_RT_CHECKPOINT Table for LAST_RUN_DATETIME {}", timeZoneConv.currentTime());
		} catch (Exception ex) {
			ex.getMessage();
			ex.printStackTrace();
			logger.info("Record not inserted into T_RT_CHECKPOINT Table properly" + ex.getMessage());
		}
		
	}

	@Override
	public void insertIntoT_RT_CheckPoint(List<TranDetailDto> tranDetailList) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<ReconciliationDto> selectReconDataForUpdate() {
		
		String query = LoadJpaQueries.getQueryById("GET_RECON_INFO_FOR_UPDATE");
		
		List<ReconciliationDto> list = jdbcTemplate.query(query,
				new BeanPropertyRowMapper<ReconciliationDto>(ReconciliationDto.class));
		
		if (!list.isEmpty()) {
			return list;
		}
		return null;
	}

	@Override
	public void updateRecon(ReconciliationDto rec) {
		
		String query = LoadJpaQueries.getQueryById("UPDATE_RECONCILIATION");
		
		UpdateReconDto updateReconDto = new UpdateReconDto();
		updateReconDto.setReconTxStatus(rec.getReconTxStatus());
		updateReconDto.setIsFinalState(ReconciliationConstant.IS_FINAL_STATE_T);
		updateReconDto.setPostedDate(rec.getPostedDate());
		updateReconDto.setCreateRt(rec.getCreateRt());
		updateReconDto.setLaneTxId(rec.getLaneTxId());
		
		jdbcTemplate.update(query, updateReconDto.getReconTxStatus(), updateReconDto.getIsFinalState(), updateReconDto.getPostedDate(),
				updateReconDto.getCreateRt(), updateReconDto.getLaneTxId());
		
		logger.info("::::::::::: updateMatched T_RECONCILIATION Table Successfully :::::::::::");
		
//		String query = LoadJpaQueries.getQueryById("UPDATE_RECONCILIATION");
//		jdbcTemplate.update(query, rec.getReconTxStatus(), rec.getIsFinalState(),
//				rec.getPostedDate(), rec.getCreateRt(), rec.getLaneTxId());
		
	}

	@Override
	public List<PlazaDto> getPlazaInfo() {
		String queryToCheckFile = LoadJpaQueries.getQueryById("GET_T_PLAZA");

		return jdbcTemplate.query(queryToCheckFile, new BeanPropertyRowMapper<PlazaDto>(PlazaDto.class));
	}

	@Override
	public List<TLaneDto> getLaneInfo() {
		String queryToCheckFile = LoadJpaQueries.getQueryById("GET_ALL_T_LANE");
		return jdbcTemplate.query(queryToCheckFile, new BeanPropertyRowMapper<TLaneDto>(TLaneDto.class));
	}
	
}
