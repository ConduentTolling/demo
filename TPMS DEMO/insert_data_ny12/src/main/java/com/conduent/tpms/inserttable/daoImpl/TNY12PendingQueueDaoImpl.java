package com.conduent.tpms.inserttable.daoImpl;

import java.text.ParseException;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.conduent.tpms.inserttable.constant.LoadJpaQueries;
import com.conduent.tpms.inserttable.dao.TNY12PendingQueueDao;
import com.conduent.tpms.inserttable.model.TNY12PendingQueueRequestModel;

@Repository
public class TNY12PendingQueueDaoImpl implements TNY12PendingQueueDao{
	
	

	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;

	final static Logger logger = LoggerFactory.getLogger(TNY12PendingQueueDaoImpl.class);


	
	@Override
	public Integer insertPendingDaoDetails(TNY12PendingQueueRequestModel tNY12PendingQueueRequest) throws ParseException {
		logger.info("Entering insertPlanSuspensionDaoDetails Dao Method ");
		String queryCountString = LoadJpaQueries.getQueryById("GET_COUNT_T_NY12_PENDING_QUEUE");
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue("LANE_TX_ID", tNY12PendingQueueRequest.getLaneTxId());
		Integer id2=jdbcTemplate.queryForObject(queryCountString,paramSource,Integer.class);
		
		Integer id=0;
		
		
		
		logger.info("Pojo Object {}", tNY12PendingQueueRequest);
		try {
        if (id2 == 0) {
			logger.info("No existing plan found for laneTxId {}", tNY12PendingQueueRequest.getLaneTxId());
			paramSource.addValue("ETC_ACCOUNT_ID",tNY12PendingQueueRequest.getEtcAccountId());
			paramSource.addValue("LANE_TX_ID",tNY12PendingQueueRequest.getLaneTxId());
			paramSource.addValue("TX_EXTERN_REF_NO",tNY12PendingQueueRequest.getTxExternRefNo());
			paramSource.addValue("MATCHED_TX_EXTERN_REF_NO",tNY12PendingQueueRequest.getMatchedTxExternRefNo());
			paramSource.addValue("TX_COMPLETE_REF_NO",tNY12PendingQueueRequest.getTxCompleteRefNo());
			paramSource.addValue("TX_MATCH_STATUS",tNY12PendingQueueRequest.getTxMatchStatus());
			paramSource.addValue("TX_SEQ_NUMBER",tNY12PendingQueueRequest.getTxSeqNumber());
			paramSource.addValue("EXTERN_FILE_ID",tNY12PendingQueueRequest.getExternFileId());
			paramSource.addValue("ATP_FILE_ID",tNY12PendingQueueRequest.getAtpFileId());
			paramSource.addValue("EXTERN_FILE_DATE",tNY12PendingQueueRequest.getExternFileDate());
			paramSource.addValue("TX_TIMESTAMP",isValid(tNY12PendingQueueRequest.getTxTimestamp()));
			
			logger.info("TX_DATE ::: {}",tNY12PendingQueueRequest.getTxDate());	
			paramSource.addValue("TX_DATE",tNY12PendingQueueRequest.getTxDate());
			
			paramSource.addValue("TX_TYPE_IND",tNY12PendingQueueRequest.getTxTypeInd());
			paramSource.addValue("TX_SUBTYPE_IND",tNY12PendingQueueRequest.getTxSubTypeInd());
			paramSource.addValue("TOLL_SYSTEM_TYPE",tNY12PendingQueueRequest.getTollSystemType());
			paramSource.addValue("PLAZA_AGENCY_ID",tNY12PendingQueueRequest.getPlazaAgencyId());
			paramSource.addValue("PLAZA_ID",tNY12PendingQueueRequest.getPlazaId());
			paramSource.addValue("LANE_ID",tNY12PendingQueueRequest.getLaneId());
			paramSource.addValue("LANE_MODE",tNY12PendingQueueRequest.getLaneMode());
			paramSource.addValue("LANE_TYPE",tNY12PendingQueueRequest.getLaneType());
			paramSource.addValue("LANE_STATE",tNY12PendingQueueRequest.getLaneState());
			paramSource.addValue("LANE_HEALTH",tNY12PendingQueueRequest.getLaneHealth());
			paramSource.addValue("ENTRY_DATA_SOURCE",tNY12PendingQueueRequest.getEntryDataSource());
			paramSource.addValue("ENTRY_LANE_ID",tNY12PendingQueueRequest.getEntryLaneId());
			paramSource.addValue("ENTRY_PLAZA_ID",tNY12PendingQueueRequest.getEntryPlazaId());
			paramSource.addValue("ENTRY_TIMESTAMP",isValid(tNY12PendingQueueRequest.getEntryTimeStamp()));
			paramSource.addValue("ENTRY_TX_SEQ_NUMBER",tNY12PendingQueueRequest.getEntryTxSeqNumber());
			paramSource.addValue("ENTRY_VEHICLE_SPEED",tNY12PendingQueueRequest.getEntryVehicleSpeed());
			paramSource.addValue("LANE_TX_STATUS",tNY12PendingQueueRequest.getLaneTxStatus());
			paramSource.addValue("LANE_TX_TYPE",tNY12PendingQueueRequest.getLaneTxType());
			paramSource.addValue("TOLL_REVENUE_TYPE",tNY12PendingQueueRequest.getTollRevenueType());
			paramSource.addValue("ACTUAL_CLASS",tNY12PendingQueueRequest.getActualClass());
			paramSource.addValue("ACTUAL_AXLES",tNY12PendingQueueRequest.getActualAxles());
			paramSource.addValue("ACTUAL_EXTRA_AXLES",tNY12PendingQueueRequest.getActualExtraAxles());
			paramSource.addValue("COLLECTOR_CLASS",tNY12PendingQueueRequest.getCollectorClass());
			paramSource.addValue("COLLECTOR_AXLES",tNY12PendingQueueRequest.getCollectorAxles());
			paramSource.addValue("PRECLASS_CLASS",tNY12PendingQueueRequest.getPreclassClass());
			paramSource.addValue("PRECLASS_AXLES",tNY12PendingQueueRequest.getPreclassAxles());
			paramSource.addValue("POSTCLASS_CLASS",tNY12PendingQueueRequest.getPostclassClass());
			paramSource.addValue("POSTCLASS_AXLES",tNY12PendingQueueRequest.getPostclassAxles());
			paramSource.addValue("FORWARD_AXLES",tNY12PendingQueueRequest.getForwardAxles());
			paramSource.addValue("REVERSE_AXLES",tNY12PendingQueueRequest.getReverseAxles());
			paramSource.addValue("DISCOUNTED_AMOUNT",tNY12PendingQueueRequest.getDiscountedAmount());
			paramSource.addValue("COLLECTED_AMOUNT",tNY12PendingQueueRequest.getCollectedAmount());
			paramSource.addValue("UNREALIZED_AMOUNT",tNY12PendingQueueRequest.getUnRealizedAmount());
			paramSource.addValue("VEHICLE_SPEED",tNY12PendingQueueRequest.getVehicleSpeed());
			paramSource.addValue("DEVICE_NO",tNY12PendingQueueRequest.getDeviceNo());
			paramSource.addValue("ACCOUNT_TYPE",tNY12PendingQueueRequest.getAccountType());
			paramSource.addValue("DEVICE_IAG_CLASS",tNY12PendingQueueRequest.getDeviceIagClass());
			paramSource.addValue("DEVICE_AXLES",tNY12PendingQueueRequest.getDeviceAxles());
			paramSource.addValue("ACCOUNT_AGENCY_ID",tNY12PendingQueueRequest.getAccountAgencyId());
			paramSource.addValue("READ_AVI_CLASS",tNY12PendingQueueRequest.getReadAviClass());
			paramSource.addValue("READ_AVI_AXLES",tNY12PendingQueueRequest.getReadAviAxles());
			paramSource.addValue("DEVICE_PROGRAM_STATUS",tNY12PendingQueueRequest.getDeviceProgramStatus());
			paramSource.addValue("BUFFERED_READ_FLAG",tNY12PendingQueueRequest.getBufferedReadFlag());
			paramSource.addValue("LANE_DEVICE_STATUS",tNY12PendingQueueRequest.getLaneDeviceStatus());
			paramSource.addValue("POST_DEVICE_STATUS",tNY12PendingQueueRequest.getPostDeviceStatus());
			paramSource.addValue("PRE_TXN_BALANCE",tNY12PendingQueueRequest.getPreTxnBalance());
			
			logger.info("TX_STATUS ::: {}",tNY12PendingQueueRequest.getTxStatus());
			paramSource.addValue("TX_STATUS",tNY12PendingQueueRequest.getTxStatus());
			
			paramSource.addValue("COLLECTOR_ID",tNY12PendingQueueRequest.getCollectorId());
			paramSource.addValue("TOUR_SEGMENT_ID",tNY12PendingQueueRequest.getTourSegmentId());
			paramSource.addValue("SPEED_VIOL_FLAG",tNY12PendingQueueRequest.getSpeedViolFlag());
			paramSource.addValue("IMAGE_TAKEN",tNY12PendingQueueRequest.getImagetaken());
			paramSource.addValue("PLATE_COUNTRY",tNY12PendingQueueRequest.getPlateCountry());
			paramSource.addValue("PLATE_STATE",tNY12PendingQueueRequest.getPlateState());
			paramSource.addValue("PLATE_NUMBER",tNY12PendingQueueRequest.getPlateNumber());
			
			
			paramSource.addValue("REVENUE_DATE",tNY12PendingQueueRequest.getRevenueDate());
			paramSource.addValue("EVENT",tNY12PendingQueueRequest.getEvent());
			paramSource.addValue("HOV_FLAG",tNY12PendingQueueRequest.getHovFlag());
			paramSource.addValue("IS_RECIPROCITY_TXN",tNY12PendingQueueRequest.getIsReciprocityTxn());
			paramSource.addValue("CSC_LOOKUP_KEY",tNY12PendingQueueRequest.getCscLookUpKey());
			
			paramSource.addValue("CASH_FARE_AMOUNT",tNY12PendingQueueRequest.getCashFareAmount());
			paramSource.addValue("DISCOUNTED_AMOUNT_2",tNY12PendingQueueRequest.getDiscountedAmount2());
			paramSource.addValue("ETC_FARE_AMOUNT",tNY12PendingQueueRequest.getEtcFareAmount());
			paramSource.addValue("EXPECTED_REVENUE_AMOUNT",tNY12PendingQueueRequest.getExpectedRevenueAmount());
			paramSource.addValue("POSTED_FARE_AMOUNT",tNY12PendingQueueRequest.getPostedFareAmount());
			paramSource.addValue("VIDEO_FARE_AMOUNT",tNY12PendingQueueRequest.getVideoFareAmount());

			String queryString = LoadJpaQueries.getQueryById("INSERT_INTO_T_NY12_PENDING_QUEUE");
			id = jdbcTemplate.update(queryString, paramSource);
			logger.info("Successfully data inserted into T_NY12_PENDING_QUEUE Table for Lane_TX_Id {}",tNY12PendingQueueRequest.getLaneTxId());
        }
		}catch (Exception ex) {
			ex.getMessage();
			ex.printStackTrace();
			logger.info("Record not inserted into T_NY12_PENDING_QUEUE Table properly" + ex.getMessage());
		}
		
		
		/*
		 * if (id2 == 0) { paramSource.addValue("LANE_TX_ID",
		 * tNY12PendingQueueRequest.getLaneTxId());
		 * 
		 * String queryString =
		 * LoadJpaQueries.getQueryById("INSERT_INTO_T_25A_PENDING_QUEUE"); id =
		 * jdbcTemplate.update(queryString, paramSource);
		 * 
		 * }
		 */
        return id;
	}	
	
	public static OffsetDateTime isValid(String s) {
		if (s != null) {
			return OffsetDateTime.parse(s, DateTimeFormatter.ISO_OFFSET_DATE_TIME);
		} else {
			return null;
		}
	}
}
