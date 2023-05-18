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
import com.conduent.tpms.inserttable.dao.T25APendingQueueDao;
import com.conduent.tpms.inserttable.model.T25APendingQueueRequestModel;

@Repository
public class T25APendingQueueDaoImpl implements T25APendingQueueDao{
	
	

	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;

	final static Logger logger = LoggerFactory.getLogger(T25APendingQueueDaoImpl.class);

	

	
	@Override
	public Integer insertPendingDaoDetails(T25APendingQueueRequestModel t25APendingQueueRequest) throws ParseException {
		logger.info("Entering insertPlanSuspensionDaoDetails Dao Method ");		
		String queryCountString = LoadJpaQueries.getQueryById("GET_COUNT_T_25A_PENDING_QUEUE");
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue("LANE_TX_ID", t25APendingQueueRequest.getLaneTxId());
		Integer id2=jdbcTemplate.queryForObject(queryCountString,paramSource,Integer.class);
		
		Integer id=0;
	
		logger.info("Pojo Object {}", t25APendingQueueRequest);
		try {
        if (id2 == 0) {
			logger.info("No existing plan found for laneTxId {}", t25APendingQueueRequest.getLaneTxId());
			paramSource.addValue("ETC_ACCOUNT_ID",t25APendingQueueRequest.getEtcAccountId());
			paramSource.addValue("LANE_TX_ID",t25APendingQueueRequest.getLaneTxId());
			paramSource.addValue("TX_EXTERN_REF_NO",t25APendingQueueRequest.getTxExternRefNo());
			paramSource.addValue("MATCHED_TX_EXTERN_REF_NO",t25APendingQueueRequest.getMatchedTxExternRefNo());
			paramSource.addValue("TX_COMPLETE_REF_NO",t25APendingQueueRequest.getTxCompleteRefNo());
			paramSource.addValue("TX_MATCH_STATUS",t25APendingQueueRequest.getTxMatchStatus());
			paramSource.addValue("TX_SEQ_NUMBER",t25APendingQueueRequest.getTxSeqNumber());
			paramSource.addValue("EXTERN_FILE_ID",t25APendingQueueRequest.getExternFileId());
			paramSource.addValue("ATP_FILE_ID",t25APendingQueueRequest.getAtpFileId());
			paramSource.addValue("EXTERN_FILE_DATE",t25APendingQueueRequest.getExternFileDate());
			paramSource.addValue("TX_TIMESTAMP",isValid(t25APendingQueueRequest.getTxTimestamp()));
			
			logger.info("TX_DATE ::: {}",t25APendingQueueRequest.getTxDate());	
			paramSource.addValue("TX_DATE",t25APendingQueueRequest.getTxDate());
			
			paramSource.addValue("TX_TYPE_IND",t25APendingQueueRequest.getTxTypeInd());
			paramSource.addValue("TX_SUBTYPE_IND",t25APendingQueueRequest.getTxSubTypeInd());
			paramSource.addValue("TOLL_SYSTEM_TYPE",t25APendingQueueRequest.getTollSystemType());
			paramSource.addValue("PLAZA_AGENCY_ID",t25APendingQueueRequest.getPlazaAgencyId());
			paramSource.addValue("PLAZA_ID",t25APendingQueueRequest.getPlazaId());
			paramSource.addValue("LANE_ID",t25APendingQueueRequest.getLaneId());
			paramSource.addValue("LANE_MODE",t25APendingQueueRequest.getLaneMode());
			paramSource.addValue("LANE_TYPE",t25APendingQueueRequest.getLaneType());
			paramSource.addValue("LANE_STATE",t25APendingQueueRequest.getLaneState());
			paramSource.addValue("LANE_HEALTH",t25APendingQueueRequest.getLaneHealth());
			paramSource.addValue("ENTRY_DATA_SOURCE",t25APendingQueueRequest.getEntryDataSource());
			paramSource.addValue("ENTRY_LANE_ID",t25APendingQueueRequest.getEntryLaneId());
			paramSource.addValue("ENTRY_PLAZA_ID",t25APendingQueueRequest.getEntryPlazaId());
			paramSource.addValue("ENTRY_TIMESTAMP",isValid(t25APendingQueueRequest.getEntryTimeStamp()));
			paramSource.addValue("ENTRY_TX_SEQ_NUMBER",t25APendingQueueRequest.getEntryTxSeqNumber());
			paramSource.addValue("ENTRY_VEHICLE_SPEED",t25APendingQueueRequest.getEntryVehicleSpeed());
			paramSource.addValue("LANE_TX_STATUS",t25APendingQueueRequest.getLaneTxStatus());
			paramSource.addValue("LANE_TX_TYPE",t25APendingQueueRequest.getLaneTxType());
			paramSource.addValue("TOLL_REVENUE_TYPE",t25APendingQueueRequest.getTollRevenueType());
			paramSource.addValue("ACTUAL_CLASS",t25APendingQueueRequest.getActualClass());
			paramSource.addValue("ACTUAL_AXLES",t25APendingQueueRequest.getActualAxles());
			paramSource.addValue("ACTUAL_EXTRA_AXLES",t25APendingQueueRequest.getActualExtraAxles());
			paramSource.addValue("COLLECTOR_CLASS",t25APendingQueueRequest.getCollectorClass());
			paramSource.addValue("COLLECTOR_AXLES",t25APendingQueueRequest.getCollectorAxles());
			paramSource.addValue("PRECLASS_CLASS",t25APendingQueueRequest.getPreclassClass());
			paramSource.addValue("PRECLASS_AXLES",t25APendingQueueRequest.getPreclassAxles());
			paramSource.addValue("POSTCLASS_CLASS",t25APendingQueueRequest.getPostclassClass());
			paramSource.addValue("POSTCLASS_AXLES",t25APendingQueueRequest.getPostclassAxles());
			paramSource.addValue("FORWARD_AXLES",t25APendingQueueRequest.getForwardAxles());
			paramSource.addValue("REVERSE_AXLES",t25APendingQueueRequest.getReverseAxles());
			paramSource.addValue("DISCOUNTED_AMOUNT",t25APendingQueueRequest.getDiscountedAmount());
			paramSource.addValue("COLLECTED_AMOUNT",t25APendingQueueRequest.getCollectedAmount());
			paramSource.addValue("UNREALIZED_AMOUNT",t25APendingQueueRequest.getUnRealizedAmount());
			paramSource.addValue("VEHICLE_SPEED",t25APendingQueueRequest.getVehicleSpeed());
			paramSource.addValue("DEVICE_NO",t25APendingQueueRequest.getDeviceNo());
			paramSource.addValue("ACCOUNT_TYPE",t25APendingQueueRequest.getAccountType());
			paramSource.addValue("DEVICE_IAG_CLASS",t25APendingQueueRequest.getDeviceIagClass());
			paramSource.addValue("DEVICE_AXLES",t25APendingQueueRequest.getDeviceAxles());
			paramSource.addValue("ACCOUNT_AGENCY_ID",t25APendingQueueRequest.getAccountAgencyId());
			paramSource.addValue("READ_AVI_CLASS",t25APendingQueueRequest.getReadAviClass());
			paramSource.addValue("READ_AVI_AXLES",t25APendingQueueRequest.getReadAviAxles());
			paramSource.addValue("DEVICE_PROGRAM_STATUS",t25APendingQueueRequest.getDeviceProgramStatus());
			paramSource.addValue("BUFFERED_READ_FLAG",t25APendingQueueRequest.getBufferedReadFlag());
			paramSource.addValue("LANE_DEVICE_STATUS",t25APendingQueueRequest.getLaneDeviceStatus());
			paramSource.addValue("POST_DEVICE_STATUS",t25APendingQueueRequest.getPostDeviceStatus());
			paramSource.addValue("PRE_TXN_BALANCE",t25APendingQueueRequest.getPreTxnBalance());
			
			logger.info("TX_STATUS ::: {}",t25APendingQueueRequest.getTxStatus());
			paramSource.addValue("TX_STATUS",t25APendingQueueRequest.getTxStatus());
			
			paramSource.addValue("COLLECTOR_ID",t25APendingQueueRequest.getCollectorId());
			paramSource.addValue("TOUR_SEGMENT_ID",t25APendingQueueRequest.getTourSegmentId());
			paramSource.addValue("SPEED_VIOL_FLAG",t25APendingQueueRequest.getSpeedViolFlag());
			paramSource.addValue("IMAGE_TAKEN",t25APendingQueueRequest.getImagetaken());
			paramSource.addValue("PLATE_COUNTRY",t25APendingQueueRequest.getPlateCountry());
			paramSource.addValue("PLATE_STATE",t25APendingQueueRequest.getPlateState());
			paramSource.addValue("PLATE_NUMBER",t25APendingQueueRequest.getPlateNumber());
			
			
			paramSource.addValue("REVENUE_DATE",t25APendingQueueRequest.getRevenueDate());
			paramSource.addValue("EVENT",t25APendingQueueRequest.getEvent());
			paramSource.addValue("HOV_FLAG",t25APendingQueueRequest.getHovFlag());
			paramSource.addValue("IS_RECIPROCITY_TXN",t25APendingQueueRequest.getIsReciprocityTxn());
			paramSource.addValue("CSC_LOOKUP_KEY",t25APendingQueueRequest.getCscLookUpKey());
			paramSource.addValue("FULL_FARE_AMOUNT",t25APendingQueueRequest.getFullFareAmount());
			paramSource.addValue("STATUS_CD",t25APendingQueueRequest.getStatusCd());
			paramSource.addValue("ERROR_MESSAGE",t25APendingQueueRequest.getErrorMessage());
			
			paramSource.addValue("CASH_FARE_AMOUNT",t25APendingQueueRequest.getCashFareAmount());
			paramSource.addValue("DISCOUNTED_AMOUNT_2",t25APendingQueueRequest.getDiscountedAmount2());
			paramSource.addValue("ETC_FARE_AMOUNT",t25APendingQueueRequest.getEtcFareAmount());
			paramSource.addValue("EXPECTED_REVENUE_AMOUNT",t25APendingQueueRequest.getExpectedRevenueAmount());
			paramSource.addValue("POSTED_FARE_AMOUNT",t25APendingQueueRequest.getPostedFareAmount());
			
			logger.info("VIDEO_FARE_AMOUNT ::: {}",t25APendingQueueRequest.getVideoFareAmount());
			paramSource.addValue("VIDEO_FARE_AMOUNT",t25APendingQueueRequest.getVideoFareAmount());
			

			
			String queryString = LoadJpaQueries.getQueryById("INSERT_INTO_T_25A_PENDING_QUEUE");
			id = jdbcTemplate.update(queryString, paramSource);
		
			logger.info("Successfully data inserted into T_25A_PENDING_QUEUE Table for Lane_TX_Id {}",t25APendingQueueRequest.getLaneTxId());
        }
        
		}catch (Exception ex) {
			ex.getMessage();
			ex.printStackTrace();
			logger.info("Record not inserted into T_25A_PENDING_QUEUE Table properly" + ex.getMessage());
		}
		
		
		/*
		 * if (id2 == 0) { paramSource.addValue("LANE_TX_ID",
		 * t25APendingQueueRequest.getLaneTxId());
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
