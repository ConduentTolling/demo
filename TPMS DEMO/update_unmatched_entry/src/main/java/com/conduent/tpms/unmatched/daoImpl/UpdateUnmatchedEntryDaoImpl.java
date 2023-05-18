package com.conduent.tpms.unmatched.daoImpl;

import java.text.ParseException;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.conduent.app.timezone.utility.TimeZoneConv;
import com.conduent.tpms.unmatched.constant.LoadJpaQueries;
import com.conduent.tpms.unmatched.dao.UpdateUnmatchedEntryDao;
import com.conduent.tpms.unmatched.model.TUnmatchedEntryTxRequestModel;

@Repository
public class UpdateUnmatchedEntryDaoImpl implements UpdateUnmatchedEntryDao {

	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;
	
	@Autowired
	TimeZoneConv timeZoneConv;

	final static Logger logger = LoggerFactory.getLogger(UpdateUnmatchedEntryDaoImpl.class);
	
	public Integer nvlValue = 3;
	public Integer txStatusValue = 604;
	public String outputTypeValue = "AUT";
	public String outputFileValue = "AUTO_GENERATED_NUMBER";
	
	
	@Override
	public Integer updateUnmatchedEntryDaoDetails(TUnmatchedEntryTxRequestModel tUnmatchedEntryTxRequest)
			throws ParseException {
		logger.info("Entering UpdateUnmatchedEntryTxDaoDetails Dao Method ");
		String queryCountString = LoadJpaQueries.getQueryById("GET_COUNT_T_UNMATCHED_ENTRY_TX");
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		
		paramSource.addValue("LANE_TX_ID", tUnmatchedEntryTxRequest.getLaneTxId());
		Integer id2 = jdbcTemplate.queryForObject(queryCountString, paramSource, Integer.class);

		@SuppressWarnings("unused")
		Integer id = 0;
		
		String updateQuery = "UPDATE T_UNMATCHED_ENTRY_TX SET ";

		StringBuilder finalQueryToBeExecuted = new StringBuilder();

		logger.info("Pojo Object {}", tUnmatchedEntryTxRequest);

		if (id2 >= 1) {
			logger.info("Existing plan found for laneTxId {}", tUnmatchedEntryTxRequest.getLaneTxId());

			if (notNull(tUnmatchedEntryTxRequest.getTxExternRefNo())) {

				paramSource.addValue("TX_EXTERN_REF_NO", tUnmatchedEntryTxRequest.getTxExternRefNo());
				finalQueryToBeExecuted.append("TX_EXTERN_REF_NO=:TX_EXTERN_REF_NO,");
			}

			if (notNull(tUnmatchedEntryTxRequest.getEtcAccountId())) {

				paramSource.addValue("ETC_ACCOUNT_ID", tUnmatchedEntryTxRequest.getEtcAccountId());
				finalQueryToBeExecuted.append(" ETC_ACCOUNT_ID=:ETC_ACCOUNT_ID,");
			}

			if (notNull(tUnmatchedEntryTxRequest.getTxSeqNumber())) {
				paramSource.addValue("TX_SEQ_NUMBER", tUnmatchedEntryTxRequest.getTxSeqNumber());
				finalQueryToBeExecuted.append(" TX_SEQ_NUMBER=:TX_SEQ_NUMBER,");
			}

			if (notNull(tUnmatchedEntryTxRequest.getExternFileId())) {
				paramSource.addValue("EXTERN_FILE_ID", tUnmatchedEntryTxRequest.getExternFileId());
				finalQueryToBeExecuted.append(" EXTERN_FILE_ID=:EXTERN_FILE_ID,");
			}

			if (notNull(tUnmatchedEntryTxRequest.getAtpFileId())) {
				paramSource.addValue("ATP_FILE_ID", tUnmatchedEntryTxRequest.getAtpFileId());
				finalQueryToBeExecuted.append(" ATP_FILE_ID=:ATP_FILE_ID,");
			}

			if (notNull(tUnmatchedEntryTxRequest.getExternFileDate())) {
				paramSource.addValue("EXTERN_FILE_DATE", tUnmatchedEntryTxRequest.getExternFileDate());
				finalQueryToBeExecuted.append(" EXTERN_FILE_DATE=:EXTERN_FILE_DATE,");
			}

			if (notNull(tUnmatchedEntryTxRequest.getTxTimeStamp())) {
				logger.info("TX_TIMESTAMP ::: {}", tUnmatchedEntryTxRequest.getTxTimeStamp());
				paramSource.addValue("TX_TIMESTAMP", isValid(tUnmatchedEntryTxRequest.getTxTimeStamp()));
				finalQueryToBeExecuted.append(" TX_TIMESTAMP=:TX_TIMESTAMP,");
			}

			if (notNull(tUnmatchedEntryTxRequest.getTxDate())) {
				logger.info("TX_DATE ::: {}", tUnmatchedEntryTxRequest.getTxDate());
				paramSource.addValue("TX_DATE", tUnmatchedEntryTxRequest.getTxDate());
				finalQueryToBeExecuted.append(" TX_DATE=:TX_DATE,");
			}

			if (notNull(tUnmatchedEntryTxRequest.getTxTypeInd())) {
				paramSource.addValue("TX_TYPE_IND", tUnmatchedEntryTxRequest.getTxTypeInd());
				finalQueryToBeExecuted.append(" TX_TYPE_IND=:TX_TYPE_IND,");
			}

			if (notNull(tUnmatchedEntryTxRequest.getTxSubtypeInd())) {
				paramSource.addValue("TX_SUBTYPE_IND", tUnmatchedEntryTxRequest.getTxSubtypeInd());
				finalQueryToBeExecuted.append(" TX_SUBTYPE_IND=:TX_SUBTYPE_IND,");
			}

			if (notNull(tUnmatchedEntryTxRequest.getTollSystemType())) {
				paramSource.addValue("TOLL_SYSTEM_TYPE", tUnmatchedEntryTxRequest.getTollSystemType());
				finalQueryToBeExecuted.append(" TOLL_SYSTEM_TYPE=:TOLL_SYSTEM_TYPE,");
			}

			if (notNull(tUnmatchedEntryTxRequest.getPlazaAgencyId())) {
				paramSource.addValue("PLAZA_AGENCY_ID", tUnmatchedEntryTxRequest.getPlazaAgencyId());
				finalQueryToBeExecuted.append(" PLAZA_AGENCY_ID=:PLAZA_AGENCY_ID,");
			}

			if (notNull(tUnmatchedEntryTxRequest.getPlazaId())) {
				paramSource.addValue("PLAZA_ID", tUnmatchedEntryTxRequest.getPlazaId());
				finalQueryToBeExecuted.append(" PLAZA_ID=:PLAZA_ID,");
			}

			if (notNull(tUnmatchedEntryTxRequest.getLaneId())) {
				paramSource.addValue("LANE_ID", tUnmatchedEntryTxRequest.getLaneId());
				finalQueryToBeExecuted.append(" LANE_ID=:LANE_ID,");
			}

			if (notNull(tUnmatchedEntryTxRequest.getLaneMode())) {
				paramSource.addValue("LANE_MODE", tUnmatchedEntryTxRequest.getLaneMode());
				finalQueryToBeExecuted.append(" LANE_MODE=:LANE_MODE,");
			}

			if (notNull(tUnmatchedEntryTxRequest.getLaneType())) {
				paramSource.addValue("LANE_TYPE", tUnmatchedEntryTxRequest.getLaneType());
				finalQueryToBeExecuted.append(" LANE_TYPE=:LANE_TYPE,");
			}

			if (notNull(tUnmatchedEntryTxRequest.getLaneState())) {
				paramSource.addValue("LANE_STATE", tUnmatchedEntryTxRequest.getLaneState());
				finalQueryToBeExecuted.append(" LANE_STATE=:LANE_STATE,");
			}

			if (notNull(tUnmatchedEntryTxRequest.getLaneHealth())) {
				paramSource.addValue("LANE_HEALTH", tUnmatchedEntryTxRequest.getLaneHealth());
				finalQueryToBeExecuted.append(" LANE_HEALTH=:LANE_HEALTH,");
			}

			if (notNull(tUnmatchedEntryTxRequest.getEntryDataSource())) {
				paramSource.addValue("ENTRY_DATA_SOURCE", tUnmatchedEntryTxRequest.getEntryDataSource());
				finalQueryToBeExecuted.append(" ENTRY_DATA_SOURCE=:ENTRY_DATA_SOURCE,");
			}

			if (notNull(tUnmatchedEntryTxRequest.getEntryLaneId())) {
				paramSource.addValue("ENTRY_LANE_ID", tUnmatchedEntryTxRequest.getEntryLaneId());
				finalQueryToBeExecuted.append(" ENTRY_LANE_ID=:ENTRY_LANE_ID,");
			}

			if (notNull(tUnmatchedEntryTxRequest.getEntryPlazaId())) {
				paramSource.addValue("ENTRY_PLAZA_ID", tUnmatchedEntryTxRequest.getEntryPlazaId());
				finalQueryToBeExecuted.append(" ENTRY_PLAZA_ID=:ENTRY_PLAZA_ID,");
			}

			if (notNull(tUnmatchedEntryTxRequest.getEntryTimestamp())) {
				logger.info("ENTRY_TIMESTAMP ::: {}", tUnmatchedEntryTxRequest.getEntryTimestamp());
				paramSource.addValue("ENTRY_TIMESTAMP", isValid(tUnmatchedEntryTxRequest.getEntryTimestamp()));
				finalQueryToBeExecuted.append(" ENTRY_TIMESTAMP=:ENTRY_TIMESTAMP,");
			}

			if (notNull(tUnmatchedEntryTxRequest.getEntryTxSeqNumber())) {
				paramSource.addValue("ENTRY_TX_SEQ_NUMBER", tUnmatchedEntryTxRequest.getEntryTxSeqNumber());
				finalQueryToBeExecuted.append(" ENTRY_TX_SEQ_NUMBER=:ENTRY_TX_SEQ_NUMBER,");
			}
			// getTxSeqNumber

			if (notNull(tUnmatchedEntryTxRequest.getEntryVehicleSpeed())) {
				paramSource.addValue("ENTRY_VEHICLE_SPEED", tUnmatchedEntryTxRequest.getEntryVehicleSpeed());
				finalQueryToBeExecuted.append(" ENTRY_VEHICLE_SPEED=:ENTRY_VEHICLE_SPEED,");
			}

			if (notNull(tUnmatchedEntryTxRequest.getLaneTxStatus())) {
			  paramSource.addValue("LANE_TX_STATUS",tUnmatchedEntryTxRequest.getLaneTxStatus());
			  finalQueryToBeExecuted.append(" LANE_TX_STATUS=:LANE_TX_STATUS,"); 
			  }
			 
			if (notNull(tUnmatchedEntryTxRequest.getLaneTxType())) {
				paramSource.addValue("LANE_TX_TYPE", tUnmatchedEntryTxRequest.getLaneTxType());
				finalQueryToBeExecuted.append(" LANE_TX_TYPE=:LANE_TX_TYPE,");
			}

			if (notNull(tUnmatchedEntryTxRequest.getTollRevenueType())) {
				paramSource.addValue("TOLL_REVENUE_TYPE", tUnmatchedEntryTxRequest.getTollRevenueType());
				finalQueryToBeExecuted.append(" TOLL_REVENUE_TYPE=:TOLL_REVENUE_TYPE,");
			}

			if (notNull(tUnmatchedEntryTxRequest.getActualClass())) {
				paramSource.addValue("ACTUAL_CLASS", tUnmatchedEntryTxRequest.getActualClass());
				finalQueryToBeExecuted.append(" ACTUAL_CLASS=:ACTUAL_CLASS,");
			}

			if (notNull(tUnmatchedEntryTxRequest.getActualAxles())) {
				paramSource.addValue("ACTUAL_AXLES", tUnmatchedEntryTxRequest.getActualAxles());
				finalQueryToBeExecuted.append(" ACTUAL_AXLES=:ACTUAL_AXLES,");
			}

			if (notNull(tUnmatchedEntryTxRequest.getActualExtraAxles())) {
				paramSource.addValue("ACTUAL_EXTRA_AXLES", tUnmatchedEntryTxRequest.getActualExtraAxles());
				finalQueryToBeExecuted.append(" ACTUAL_EXTRA_AXLES=:ACTUAL_EXTRA_AXLES,");
			}

			if (notNull(tUnmatchedEntryTxRequest.getCollectorClass())) {
				paramSource.addValue("COLLECTOR_CLASS", tUnmatchedEntryTxRequest.getCollectorClass());
				finalQueryToBeExecuted.append(" COLLECTOR_CLASS=:COLLECTOR_CLASS,");
			}

			if (notNull(tUnmatchedEntryTxRequest.getCollectorAxles())) {
				paramSource.addValue("COLLECTOR_AXLES", tUnmatchedEntryTxRequest.getCollectorAxles());
				finalQueryToBeExecuted.append(" COLLECTOR_AXLES=:COLLECTOR_AXLES,");
			}

			if (notNull(tUnmatchedEntryTxRequest.getPreclassClass())) {
				paramSource.addValue("PRECLASS_CLASS", tUnmatchedEntryTxRequest.getPreclassClass());
				finalQueryToBeExecuted.append(" PRECLASS_CLASS=:PRECLASS_CLASS,");
			}

			if (notNull(tUnmatchedEntryTxRequest.getPreclassAxles())) {
				paramSource.addValue("PRECLASS_AXLES", tUnmatchedEntryTxRequest.getPreclassAxles());
				finalQueryToBeExecuted.append(" PRECLASS_AXLES=:PRECLASS_AXLES,");
			}

			if (notNull(tUnmatchedEntryTxRequest.getPostclassClass())) {
				paramSource.addValue("POSTCLASS_CLASS", tUnmatchedEntryTxRequest.getPostclassClass());
				finalQueryToBeExecuted.append(" POSTCLASS_CLASS=:POSTCLASS_CLASS,");
			}

			if (notNull(tUnmatchedEntryTxRequest.getPostclassAxles())) {
				paramSource.addValue("POSTCLASS_AXLES", tUnmatchedEntryTxRequest.getPostclassAxles());
				finalQueryToBeExecuted.append(" POSTCLASS_AXLES=:POSTCLASS_AXLES,");
			}

			if (notNull(tUnmatchedEntryTxRequest.getForwardAxles())) {
				paramSource.addValue("FORWARD_AXLES", tUnmatchedEntryTxRequest.getForwardAxles());
				finalQueryToBeExecuted.append(" FORWARD_AXLES=:FORWARD_AXLES,");
			}

			if (notNull(tUnmatchedEntryTxRequest.getReverseAxles())) {
				paramSource.addValue("REVERSE_AXLES", tUnmatchedEntryTxRequest.getReverseAxles());
				finalQueryToBeExecuted.append(" REVERSE_AXLES=:REVERSE_AXLES,");
			}

			if (notNull(tUnmatchedEntryTxRequest.getCollectedAmount())) {
				paramSource.addValue("COLLECTED_AMOUNT", tUnmatchedEntryTxRequest.getCollectedAmount());
				finalQueryToBeExecuted.append(" COLLECTED_AMOUNT=:COLLECTED_AMOUNT,");
			}

			if (notNull(tUnmatchedEntryTxRequest.getUnrealizedAmount())) {
				paramSource.addValue("UNREALIZED_AMOUNT", tUnmatchedEntryTxRequest.getUnrealizedAmount());
				finalQueryToBeExecuted.append(" UNREALIZED_AMOUNT=:UNREALIZED_AMOUNT,");
			}

			if (notNull(tUnmatchedEntryTxRequest.getVehicleSpeed())) {
				paramSource.addValue("VEHICLE_SPEED", tUnmatchedEntryTxRequest.getVehicleSpeed());
				finalQueryToBeExecuted.append(" VEHICLE_SPEED=:VEHICLE_SPEED,");
			}

			if (notNull(tUnmatchedEntryTxRequest.getDeviceNo())) {
				paramSource.addValue("DEVICE_NO", tUnmatchedEntryTxRequest.getDeviceNo());
				finalQueryToBeExecuted.append(" DEVICE_NO=:DEVICE_NO,");
			}

			if (notNull(tUnmatchedEntryTxRequest.getAccountType())) {
				paramSource.addValue("ACCOUNT_TYPE", tUnmatchedEntryTxRequest.getAccountType());
				finalQueryToBeExecuted.append(" ACCOUNT_TYPE=:ACCOUNT_TYPE,");
			}

			if (notNull(tUnmatchedEntryTxRequest.getDeviceIagClass())) {
				paramSource.addValue("DEVICE_IAG_CLASS", tUnmatchedEntryTxRequest.getDeviceIagClass());
				finalQueryToBeExecuted.append(" DEVICE_IAG_CLASS=:DEVICE_IAG_CLASS,");
			}

			if (notNull(tUnmatchedEntryTxRequest.getDeviceAxles())) {
				paramSource.addValue("DEVICE_AXLES", tUnmatchedEntryTxRequest.getDeviceAxles());
				finalQueryToBeExecuted.append(" DEVICE_AXLES=:DEVICE_AXLES,");
			}

			if (notNull(tUnmatchedEntryTxRequest.getAccountAgencyId())) {
				paramSource.addValue("ACCOUNT_AGENCY_ID", tUnmatchedEntryTxRequest.getAccountAgencyId());
				finalQueryToBeExecuted.append(" ACCOUNT_AGENCY_ID=:ACCOUNT_AGENCY_ID,");
			}

			if (notNull(tUnmatchedEntryTxRequest.getReadAviClass())) {
				paramSource.addValue("READ_AVI_CLASS", tUnmatchedEntryTxRequest.getReadAviClass());
				finalQueryToBeExecuted.append(" READ_AVI_CLASS=:READ_AVI_CLASS,");
			}

			if (notNull(tUnmatchedEntryTxRequest.getReadAviAxles())) {
				paramSource.addValue("READ_AVI_AXLES", tUnmatchedEntryTxRequest.getReadAviAxles());
				finalQueryToBeExecuted.append(" READ_AVI_AXLES=:READ_AVI_AXLES,");
			}

			if (notNull(tUnmatchedEntryTxRequest.getDeviceProgramStatus())) {
				paramSource.addValue("DEVICE_PROGRAM_STATUS", tUnmatchedEntryTxRequest.getDeviceProgramStatus());
				finalQueryToBeExecuted.append(" DEVICE_PROGRAM_STATUS=:DEVICE_PROGRAM_STATUS,");
			}

			if (notNull(tUnmatchedEntryTxRequest.getBufferedReadFlag())) {
				paramSource.addValue("BUFFERED_READ_FLAG", tUnmatchedEntryTxRequest.getBufferedReadFlag());
				finalQueryToBeExecuted.append(" BUFFERED_READ_FLAG=:BUFFERED_READ_FLAG,");
			}

			if (notNull(tUnmatchedEntryTxRequest.getLaneDeviceStatus())) {
				paramSource.addValue("LANE_DEVICE_STATUS", tUnmatchedEntryTxRequest.getLaneDeviceStatus());
				finalQueryToBeExecuted.append(" LANE_DEVICE_STATUS=:LANE_DEVICE_STATUS,");
			}

			if (notNull(tUnmatchedEntryTxRequest.getPostDeviceStatus())) {
				paramSource.addValue("POST_DEVICE_STATUS", tUnmatchedEntryTxRequest.getPostDeviceStatus());
				finalQueryToBeExecuted.append(" POST_DEVICE_STATUS=:POST_DEVICE_STATUS,");
			}

			if (notNull(tUnmatchedEntryTxRequest.getPreTxnBalance())) {
				paramSource.addValue("PRE_TXN_BALANCE", tUnmatchedEntryTxRequest.getPreTxnBalance());
				finalQueryToBeExecuted.append(" PRE_TXN_BALANCE=:PRE_TXN_BALANCE,");
			}
			
			if (notNull(tUnmatchedEntryTxRequest.getTxStatus())) {
			  logger.info("TX_STATUS ::: {}", tUnmatchedEntryTxRequest.getTxStatus());  
			   paramSource.addValue("TX_STATUS", decode(tUnmatchedEntryTxRequest,txStatusValue)); 
			  finalQueryToBeExecuted.append(" TX_STATUS=:TX_STATUS,");
			  }
			 

			if (notNull(tUnmatchedEntryTxRequest.getCollectorId())) {
				paramSource.addValue("COLLECTOR_ID", tUnmatchedEntryTxRequest.getCollectorId());
				finalQueryToBeExecuted.append(" COLLECTOR_ID=:COLLECTOR_ID,");
			}

			if (notNull(tUnmatchedEntryTxRequest.getTourSegmentId())) {
				paramSource.addValue("TOUR_SEGMENT_ID", tUnmatchedEntryTxRequest.getTourSegmentId());
				finalQueryToBeExecuted.append(" TOUR_SEGMENT_ID=:TOUR_SEGMENT_ID,");
			}

			if (notNull(tUnmatchedEntryTxRequest.getSpeedViolFlag())) {
				paramSource.addValue("SPEED_VIOL_FLAG", tUnmatchedEntryTxRequest.getSpeedViolFlag());
				finalQueryToBeExecuted.append(" SPEED_VIOL_FLAG=:SPEED_VIOL_FLAG,");
			}

			if (notNull(tUnmatchedEntryTxRequest.getImageTaken())) {
				paramSource.addValue("IMAGE_TAKEN", tUnmatchedEntryTxRequest.getImageTaken());
				finalQueryToBeExecuted.append(" IMAGE_TAKEN=:IMAGE_TAKEN,");
			}

			if (notNull(tUnmatchedEntryTxRequest.getPlateCountry())) {
				paramSource.addValue("PLATE_COUNTRY", tUnmatchedEntryTxRequest.getPlateCountry());
				finalQueryToBeExecuted.append(" PLATE_COUNTRY=:PLATE_COUNTRY,");
			}

			if (notNull(tUnmatchedEntryTxRequest.getPlateState())) {
				paramSource.addValue("PLATE_STATE", tUnmatchedEntryTxRequest.getPlateState());
				finalQueryToBeExecuted.append(" PLATE_STATE=:PLATE_STATE,");
			}

			if (notNull(tUnmatchedEntryTxRequest.getPlateNumber())) {
				paramSource.addValue("PLATE_NUMBER", tUnmatchedEntryTxRequest.getPlateNumber());
				finalQueryToBeExecuted.append(" PLATE_NUMBER=:PLATE_NUMBER,");
			}

			if (notNull(tUnmatchedEntryTxRequest.getRevenueDate())) {
				paramSource.addValue("REVENUE_DATE", tUnmatchedEntryTxRequest.getRevenueDate());
				finalQueryToBeExecuted.append(" REVENUE_DATE=:REVENUE_DATE,");
			}

			if (notNull(tUnmatchedEntryTxRequest.getMatchedTxExternRefNo())) {
				paramSource.addValue("MATCHED_TX_EXTERN_REF_NO", tUnmatchedEntryTxRequest.getMatchedTxExternRefNo());
				finalQueryToBeExecuted.append(" MATCHED_TX_EXTERN_REF_NO=:MATCHED_TX_EXTERN_REF_NO,");
			}

			if (notNull(tUnmatchedEntryTxRequest.getTxModSeq())) {
				paramSource.addValue("TX_MOD_SEQ",
						(tUnmatchedEntryTxRequest.getTxModSeq() != null ? tUnmatchedEntryTxRequest.getTxModSeq() : 0));
				finalQueryToBeExecuted.append(" TX_MOD_SEQ=:TX_MOD_SEQ,");
			}

			/*
			 * if (notNull(tUnmatchedEntryTxRequest.getEntryTxSeqNumber())) {
			 * paramSource.addValue("ENTRY_TX_SEQ_NUMBER",tUnmatchedEntryTxRequest.
			 * getEntryTxSeqNumber());
			 * finalQueryToBeExecuted.append(" ENTRY_TX_SEQ_NUMBER=:ENTRY_TX_SEQ_NUMBER,");
			 * }
			 */

			if (notNull(tUnmatchedEntryTxRequest.getIsDiscountable())) {
				paramSource.addValue("IS_DISCOUNTABLE", tUnmatchedEntryTxRequest.getIsDiscountable());
				finalQueryToBeExecuted.append(" IS_DISCOUNTABLE=:IS_DISCOUNTABLE,");
			}

			if (notNull(tUnmatchedEntryTxRequest.getIsMedianFare())) {
				paramSource.addValue("IS_MEDIAN_FARE", tUnmatchedEntryTxRequest.getIsMedianFare());
				finalQueryToBeExecuted.append(" IS_MEDIAN_FARE=:IS_MEDIAN_FARE,");
			}

			if (notNull(tUnmatchedEntryTxRequest.getIsPeak())) {
				paramSource.addValue("IS_PEAK", tUnmatchedEntryTxRequest.getIsPeak());
				finalQueryToBeExecuted.append(" IS_PEAK=:IS_PEAK,");
			}

			if (notNull(tUnmatchedEntryTxRequest.getPriceScheduleId())) {
				paramSource.addValue("PRICE_SCHEDULE_ID", tUnmatchedEntryTxRequest.getPriceScheduleId());
				finalQueryToBeExecuted.append(" PRICE_SCHEDULE_ID=:PRICE_SCHEDULE_ID,");
			}

			if (notNull(tUnmatchedEntryTxRequest.getDeviceCodedClass())) {
				paramSource.addValue("DEVICE_CODED_CLASS", tUnmatchedEntryTxRequest.getDeviceCodedClass());
				finalQueryToBeExecuted.append(" DEVICE_CODED_CLASS=:DEVICE_CODED_CLASS,");
			}

			if (notNull(tUnmatchedEntryTxRequest.getDeviceAgencyClass())) {
				paramSource.addValue("DEVICE_AGENCY_CLASS", tUnmatchedEntryTxRequest.getDeviceAgencyClass());
				finalQueryToBeExecuted.append(" DEVICE_AGENCY_CLASS=:DEVICE_AGENCY_CLASS,");
			}

			if (notNull(tUnmatchedEntryTxRequest.getPlanTypeId())) {
				paramSource.addValue("PLAN_TYPE_ID", tUnmatchedEntryTxRequest.getPlanTypeId());
				finalQueryToBeExecuted.append(" PLAN_TYPE_ID=:PLAN_TYPE_ID,");
			}

			if (notNull(tUnmatchedEntryTxRequest.getPostedDate())) {
				paramSource.addValue("POSTED_DATE", tUnmatchedEntryTxRequest.getPostedDate());
				finalQueryToBeExecuted.append(" POSTED_DATE=:POSTED_DATE,");
			}

			if (notNull(tUnmatchedEntryTxRequest.getIsReversed())) {
				paramSource.addValue("IS_REVERSED", tUnmatchedEntryTxRequest.getIsReversed());
				finalQueryToBeExecuted.append(" IS_REVERSED=:IS_REVERSED,");
			}

			if (notNull(tUnmatchedEntryTxRequest.getCorrReasonId())) {
				paramSource.addValue("CORR_REASON_ID", tUnmatchedEntryTxRequest.getCorrReasonId());
				finalQueryToBeExecuted.append(" CORR_REASON_ID=:CORR_REASON_ID,");
			}

			if (notNull(tUnmatchedEntryTxRequest.getReconDate())) {
				paramSource.addValue("RECON_DATE", tUnmatchedEntryTxRequest.getReconDate());
				finalQueryToBeExecuted.append(" RECON_DATE=:RECON_DATE,");
			}

			if (notNull(tUnmatchedEntryTxRequest.getReconStatusInd())) {
				paramSource.addValue("RECON_STATUS_IND", tUnmatchedEntryTxRequest.getReconStatusInd());
				finalQueryToBeExecuted.append(" RECON_STATUS_IND=:RECON_STATUS_IND,");
			}

			if (notNull(tUnmatchedEntryTxRequest.getReconSubCodeInd())) {
				paramSource.addValue("RECON_SUB_CODE_IND", tUnmatchedEntryTxRequest.getReconSubCodeInd());
				finalQueryToBeExecuted.append(" RECON_SUB_CODE_IND=:RECON_SUB_CODE_IND,");
			}

			if (notNull(tUnmatchedEntryTxRequest.getMileage())) {
				paramSource.addValue("MILEAGE", tUnmatchedEntryTxRequest.getMileage());
				finalQueryToBeExecuted.append(" MILEAGE=:MILEAGE,");
			}

			if (notNull(tUnmatchedEntryTxRequest.getDeviceReadCount())) {
				paramSource.addValue("DEVICE_READ_COUNT", tUnmatchedEntryTxRequest.getDeviceReadCount());
				finalQueryToBeExecuted.append(" DEVICE_READ_COUNT=:DEVICE_READ_COUNT,");
			}

			if (notNull(tUnmatchedEntryTxRequest.getDeviceWriteCount())) {
				paramSource.addValue("DEVICE_WRITE_COUNT", tUnmatchedEntryTxRequest.getDeviceWriteCount());
				finalQueryToBeExecuted.append(" DEVICE_WRITE_COUNT=:DEVICE_WRITE_COUNT,");
			}

			if (notNull(tUnmatchedEntryTxRequest.getEntryDeviceReadCount())) {
				paramSource.addValue("ENTRY_DEVICE_READ_COUNT", tUnmatchedEntryTxRequest.getEntryDeviceReadCount());
				finalQueryToBeExecuted.append(" ENTRY_DEVICE_READ_COUNT=:ENTRY_DEVICE_READ_COUNT,");
			}

			if (notNull(tUnmatchedEntryTxRequest.getEntryDeviceWriteCount())) {
				paramSource.addValue("ENTRY_DEVICE_WRITE_COUNT", tUnmatchedEntryTxRequest.getEntryDeviceWriteCount());
				finalQueryToBeExecuted.append(" ENTRY_DEVICE_WRITE_COUNT=:ENTRY_DEVICE_WRITE_COUNT,");
			}

			if (notNull(tUnmatchedEntryTxRequest.getDepositId())) {
				paramSource.addValue("DEPOSIT_ID", tUnmatchedEntryTxRequest.getDepositId());
				finalQueryToBeExecuted.append(" DEPOSIT_ID=:DEPOSIT_ID,");
			}

			if (notNull(tUnmatchedEntryTxRequest.getEtcFareAmount())) {
				paramSource.addValue("ETC_FARE_AMOUNT", tUnmatchedEntryTxRequest.getEtcFareAmount());
				finalQueryToBeExecuted.append(" ETC_FARE_AMOUNT=:ETC_FARE_AMOUNT,");
			}

			if (notNull(tUnmatchedEntryTxRequest.getPostedFareAmount())) {
				paramSource.addValue("POSTED_FARE_AMOUNT", tUnmatchedEntryTxRequest.getPostedFareAmount());
				finalQueryToBeExecuted.append(" POSTED_FARE_AMOUNT=:POSTED_FARE_AMOUNT,");
			}

			if (notNull(tUnmatchedEntryTxRequest.getExpectedRevenueAmount())) {
				paramSource.addValue("EXPECTED_REVENUE_AMOUNT", tUnmatchedEntryTxRequest.getExpectedRevenueAmount());
				finalQueryToBeExecuted.append(" EXPECTED_REVENUE_AMOUNT=:EXPECTED_REVENUE_AMOUNT,");
			}

			if (notNull(tUnmatchedEntryTxRequest.getVideoFareAmount())) {
				paramSource.addValue("VIDEO_FARE_AMOUNT", tUnmatchedEntryTxRequest.getVideoFareAmount());
				finalQueryToBeExecuted.append(" VIDEO_FARE_AMOUNT=:VIDEO_FARE_AMOUNT,");
			}

			if (notNull(tUnmatchedEntryTxRequest.getCashFareAmount())) {
				paramSource.addValue("CASH_FARE_AMOUNT", tUnmatchedEntryTxRequest.getCashFareAmount());
				finalQueryToBeExecuted.append(" CASH_FARE_AMOUNT=:CASH_FARE_AMOUNT,");
			}

			if (notNull(tUnmatchedEntryTxRequest.getReceiptIssued())) {
				paramSource.addValue("RECEIPT_ISSUED", tUnmatchedEntryTxRequest.getReceiptIssued());
				finalQueryToBeExecuted.append(" RECEIPT_ISSUED=:RECEIPT_ISSUED,");
			}
			
			//added and inserted new properties from here
			
				//paramSource.addValue("EVENT_TYPE", tUnmatchedEntryTxRequest.getEventType());
				paramSource.addValue("EVENT_TYPE", nvl(tUnmatchedEntryTxRequest, nvlValue));
				finalQueryToBeExecuted.append("EVENT_TYPE=:EVENT_TYPE,");
			
			
			if(notNull(tUnmatchedEntryTxRequest.getOutputFileType())) {
				//paramSource.addValue("OUTPUT_FILE_TYPE", tUnmatchedEntryTxRequest.getOutputFileType());
				paramSource.addValue("OUTPUT_FILE_TYPE", decode(tUnmatchedEntryTxRequest, outputTypeValue));
				finalQueryToBeExecuted.append("OUTPUT_FILE_TYPE=:OUTPUT_FILE_TYPE,");
			}
			
			paramSource.addValue("EVENT_TIMESTAMP", timeZoneConv.currentTime());
			finalQueryToBeExecuted.append("EVENT_TIMESTAMP=:EVENT_TIMESTAMP,");
			
			if(notNull(tUnmatchedEntryTxRequest.getImageBatchID())) {
				paramSource.addValue("IMAGE_BATCH_ID", tUnmatchedEntryTxRequest.getImageBatchID());
				finalQueryToBeExecuted.append("IMAGE_BATCH_ID=:IMAGE_BATCH_ID,");
			}
			
			if(notNull(tUnmatchedEntryTxRequest.getImageBatchSeqNumber())) {
				paramSource.addValue("IMAGE_BATCH_SEQ_NUMBER", tUnmatchedEntryTxRequest.getImageBatchSeqNumber());
				finalQueryToBeExecuted.append("IMAGE_BATCH_SEQ_NUMBER=:IMAGE_BATCH_SEQ_NUMBER,");
			}
			
			if(notNull(tUnmatchedEntryTxRequest.getOutputFileID())) {
				//paramSource.addValue("OUTPUT_FILE_ID", tUnmatchedEntryTxRequest.getOutputFileID());
				paramSource.addValue("OUTPUT_FILE_ID", decodeFileID(tUnmatchedEntryTxRequest, outputFileValue));
				finalQueryToBeExecuted.append("OUTPUT_FILE_ID=:OUTPUT_FILE_ID,");
			}
			
			if(notNull(tUnmatchedEntryTxRequest.getImageIndex())) {
				paramSource.addValue("IMAGE_INDEX", tUnmatchedEntryTxRequest.getImageIndex());
				finalQueryToBeExecuted.append("IMAGE_INDEX=:IMAGE_INDEX,");
			}
			
			if(notNull(tUnmatchedEntryTxRequest.getImgFileIndex())) {
				paramSource.addValue("IMG_FILE_INDEX", tUnmatchedEntryTxRequest.getImgFileIndex());
				finalQueryToBeExecuted.append("IMG_FILE_INDEX=:IMG_FILE_INDEX,");
			}
			
			if(notNull(tUnmatchedEntryTxRequest.getImageRvwClerkID())) {
				paramSource.addValue("IMAGE_RVW_CLERK_ID", tUnmatchedEntryTxRequest.getImageRvwClerkID());
				finalQueryToBeExecuted.append("IMAGE_RVW_CLERK_ID=:IMAGE_RVW_CLERK_ID,");
			}
			
			if(notNull(tUnmatchedEntryTxRequest.getReviewedVehicleType())) {
				paramSource.addValue("REVIEWED_VEHICLE_TYPE", tUnmatchedEntryTxRequest.getReviewedVehicleType());
				finalQueryToBeExecuted.append("REVIEWED_VEHICLE_TYPE=:REVIEWED_VEHICLE_TYPE,");
			}
			
			if(notNull(tUnmatchedEntryTxRequest.getReviewedDate())) {
				logger.info("REVIEWED_DATE ::: {}", tUnmatchedEntryTxRequest.getReviewedDate());
				paramSource.addValue("REVIEWED_DATE",tUnmatchedEntryTxRequest.getReviewedDate());
				finalQueryToBeExecuted.append("REVIEWED_DATE=:REVIEWED_DATE,");
			}
			
			if(notNull(tUnmatchedEntryTxRequest.getDmvPlateType())) {
				paramSource.addValue("DMV_PLATE_TYPE", tUnmatchedEntryTxRequest.getDmvPlateType());
				finalQueryToBeExecuted.append("DMV_PLATE_TYPE=:DMV_PLATE_TYPE,");
			}
			
			if(notNull(tUnmatchedEntryTxRequest.getTaxi())) {
				paramSource.addValue("TAXI", tUnmatchedEntryTxRequest.getTaxi());
				finalQueryToBeExecuted.append("TAXI=:TAXI,");
			}
			
			if(notNull(tUnmatchedEntryTxRequest.getLimousine())) {
				paramSource.addValue("LIMOUSINE", tUnmatchedEntryTxRequest.getLimousine());
				finalQueryToBeExecuted.append("LIMOUSINE=:LIMOUSINE,");
			}
			
			if(notNull(tUnmatchedEntryTxRequest.getTrailerPlate())) {
				paramSource.addValue("TRAIL_PLATE", tUnmatchedEntryTxRequest.getTrailerPlate());
				finalQueryToBeExecuted.append("TRAIL_PLATE=:TRAIL_PLATE,");
			}
			
			if(notNull(tUnmatchedEntryTxRequest.getImageReviewTimeStamp())) {
				logger.info("IMAGE_REVIEW_TIME_STAMP ::: {}", tUnmatchedEntryTxRequest.getImageReviewTimeStamp());
				paramSource.addValue("IMAGE_REVIEW_TIME_STAMP",tUnmatchedEntryTxRequest.getImageReviewTimeStamp());
				finalQueryToBeExecuted.append("IMAGE_REVIEW_TIME_STAMP=:IMAGE_REVIEW_TIME_STAMP,");
			}
			
			if(notNull(tUnmatchedEntryTxRequest.getFrontImgRejReason())) {
				paramSource.addValue("FRONT_IMG_REJ_REASON", tUnmatchedEntryTxRequest.getFrontImgRejReason());
				finalQueryToBeExecuted.append("FRONT_IMG_REJ_REASON=:FRONT_IMG_REJ_REASON,");
			}
			
			if(notNull(tUnmatchedEntryTxRequest.getReviewedClass())) {
				paramSource.addValue("REVIEWED_CLASS", tUnmatchedEntryTxRequest.getReviewedClass());
				finalQueryToBeExecuted.append("REVIEWED_CLASS=:REVIEWED_CLASS,");
			}
				 
			paramSource.addValue("UPDATE_TS", timeZoneConv.currentTime());
			finalQueryToBeExecuted.append(" UPDATE_TS=:UPDATE_TS ");

			// private OffsetDateTime txTimeStamp=OffsetDateTime.of(LocalDateTime,
			// ZoneOffset.of("-04:00"));
			paramSource.addValue("LANE_TX_ID", tUnmatchedEntryTxRequest.getLaneTxId());
			finalQueryToBeExecuted.append(" WHERE LANE_TX_ID=:LANE_TX_ID");

			updateQuery += finalQueryToBeExecuted.toString();

			logger.info("updateQuery :: {}", updateQuery);

			// String queryString =
			// LoadJpaQueries.getQueryById("UPDATE_INTO_T_UNMATCHED_ENTRY_TX");
			id = jdbcTemplate.update(updateQuery, paramSource);

		}

		/*
		 * if (id2 == 0) { paramSource.addValue("LANE_TX_ID",
		 * tUnmatchedEntryTxRequest.getLaneTxId());
		 * 
		 * String queryString =
		 * LoadJpaQueries.getQueryById("INSERT_INTO_T_25A_PENDING_QUEUE"); id =
		 * jdbcTemplate.update(queryString, paramSource);
		 * 
		 * }
		 */
		return id2;
	}

	public Integer decode(TUnmatchedEntryTxRequestModel tUnmatchedEntryTxRequest,Integer txStatusValue) {
		if(tUnmatchedEntryTxRequest.getTxStatus() == 153) {
			tUnmatchedEntryTxRequest.setTxStatus(txStatusValue);
			return tUnmatchedEntryTxRequest.getTxStatus();
		}else {
			return tUnmatchedEntryTxRequest.getTxStatus();
		}	
	}
	
	public Integer nvl(TUnmatchedEntryTxRequestModel tUnmatchedEntryTxRequest,Integer nvlvalue) {
		if(tUnmatchedEntryTxRequest.getEventType()== null) {
			tUnmatchedEntryTxRequest.setEventType(nvlValue);
			return tUnmatchedEntryTxRequest.getEventType();
		}else {
		return tUnmatchedEntryTxRequest.getEventType();
		}
	}
	
	public String decode(TUnmatchedEntryTxRequestModel tUnmatchedEntryTxRequest, String outputTypeValue ) {
		if(tUnmatchedEntryTxRequest.getOutputFileType().equalsIgnoreCase("NORMAL")){
			tUnmatchedEntryTxRequest.setOutputFileType(outputTypeValue);
			return tUnmatchedEntryTxRequest.getOutputFileType();
		}else {
			return tUnmatchedEntryTxRequest.getOutputFileType();
		}
	}
	
	public String decodeFileID(TUnmatchedEntryTxRequestModel tUnmatchedEntryTxRequest,String outputFileValue) {
		if(tUnmatchedEntryTxRequest.getOutputFileID().equalsIgnoreCase("NORMAL") || tUnmatchedEntryTxRequest.getOutputFileID().equalsIgnoreCase("MIR_ONLY") ) {
			tUnmatchedEntryTxRequest.setOutputFileID(outputFileValue);
			return tUnmatchedEntryTxRequest.getOutputFileID();
		}
		return tUnmatchedEntryTxRequest.getOutputFileID();	
	}
	

	public static OffsetDateTime isValid(String s) {
		if (s != null) {
			return OffsetDateTime.parse(s, DateTimeFormatter.ISO_OFFSET_DATE_TIME);
			// return LocalDateTime.parse(s, DateTimeFormatter.ISO_DATE_TIME)
		} else {
			return null;
		}
	}

	public static boolean notNull(Object o) {
		if (o != null) {
			return true;
		} else {
			return false;
		}

	}
	
	
}
