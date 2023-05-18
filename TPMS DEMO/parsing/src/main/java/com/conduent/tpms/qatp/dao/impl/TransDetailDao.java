package com.conduent.tpms.qatp.dao.impl;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.conduent.app.timezone.utility.TimeZoneConv;
import com.conduent.tpms.qatp.config.LoadJpaQueries;
import com.conduent.tpms.qatp.constants.Constants;
import com.conduent.tpms.qatp.dao.ITransDetailDao;
import com.conduent.tpms.qatp.model.TranDetail;
import com.conduent.tpms.qatp.model.Transaction;

@Repository
public class TransDetailDao implements ITransDetailDao 
{

	@Autowired
	private NamedParameterJdbcTemplate namedJdbcTemplate;

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	TimeZoneConv timeZoneConv;


	@Override
	public void saveTransDetail(TranDetail tranDetail) 
	{
		//CREATE SEQUENCE T_TRAN_DETAILS_SEQ START WITH 1 INCREMENT BY 1 NOCACHE NOCYCLE;
		String query = LoadJpaQueries.getQueryById("INSERT_T_TRAN_DETAIL");

		MapSqlParameterSource paramSource = null;

		tranDetail.setLaneTxId(loadNextSeq(1).get(0));


		paramSource = new MapSqlParameterSource();
		paramSource.addValue(Constants.LANE_TX_ID,tranDetail.getLaneTxId());
		paramSource.addValue(Constants.TX_EXTERN_REF_NO,tranDetail.getTxExternRefNo());
		paramSource.addValue(Constants.TX_TYPE,tranDetail.getTxType());
		paramSource.addValue(Constants.TX_SUB_TYPE,tranDetail.getTxSubType());
		paramSource.addValue(Constants.EXT_FILE_ID,tranDetail.getExtFileId());
		paramSource.addValue(Constants.TOLL_SYSTEM_TYPE,tranDetail.getTollSystemType());
		paramSource.addValue(Constants.TOLL_REVENUE_TYPE,tranDetail.getTollRevenueType());
		paramSource.addValue(Constants.ENTRY_TX_TIMESTAMP,tranDetail.getEntryTxTimeStamp());
		paramSource.addValue(Constants.ENTRY_PLAZA_ID,tranDetail.getEntryPlazaId());
		paramSource.addValue(Constants.ENTRY_LANE_ID,tranDetail.getEntryLaneId());
		paramSource.addValue(Constants.ENTRY_TX_SEQ_NUMBER,tranDetail.getEntryTxSeqNumber());
		paramSource.addValue(Constants.ENTRY_DATA_SOURCE,tranDetail.getEntryDataSource());
		paramSource.addValue(Constants.ENTRY_VEHICLE_SPEED,tranDetail.getEntryVehicleSpeed());
		paramSource.addValue(Constants.PLAZA_AGENCY_ID,tranDetail.getPlazaAgencyId());
		paramSource.addValue(Constants.PLAZA_ID,tranDetail.getPlazaId());
		paramSource.addValue(Constants.LANE_ID,tranDetail.getLaneId());
		paramSource.addValue(Constants.TX_DATE,tranDetail.getTxDate());
		paramSource.addValue(Constants.TX_TIMESTAMP,tranDetail.getTxTimeStamp());
		paramSource.addValue(Constants.LANE_MODE,tranDetail.getLaneMode());
		paramSource.addValue(Constants.LANE_STATE,tranDetail.getLaneState());
		paramSource.addValue(Constants.TRX_LANE_SERIAL,tranDetail.getLaneSn());
		paramSource.addValue(Constants.DEVICE_NO,tranDetail.getDeviceNo());
		paramSource.addValue(Constants.AVC_CLASS,tranDetail.getAvcClass());
		paramSource.addValue(Constants.TAG_IAG_CLASS,tranDetail.getTagIagClass());
		paramSource.addValue(Constants.TAG_CLASS,tranDetail.getTagClass());
		paramSource.addValue(Constants.TAG_AXLES,tranDetail.getTagAxles());
		paramSource.addValue(Constants.ACTUAL_CLASS,tranDetail.getActualClass());
		paramSource.addValue(Constants.ACTUAL_AXLES,tranDetail.getActualAxles());
		paramSource.addValue(Constants.EXTRA_AXLES,tranDetail.getExtraAxles());
		paramSource.addValue(Constants.PLATE_STATE,tranDetail.getPlateState());
		paramSource.addValue(Constants.PLATE_NUMBER,tranDetail.getPlateNumber());
		paramSource.addValue(Constants.PLATE_TYPE,tranDetail.getPlateType());
		paramSource.addValue(Constants.PLATE_COUNTRY,tranDetail.getPlateCountry());
		paramSource.addValue(Constants.READ_PERF,tranDetail.getReadPerf());
		paramSource.addValue(Constants.WRITE_PERF,tranDetail.getWritePerf());
		paramSource.addValue(Constants.PROG_STAT,tranDetail.getProgStat());
		paramSource.addValue(Constants.COLLECTOR_NUMBER,tranDetail.getCollectorNumber());
		paramSource.addValue(Constants.IMAGE_CAPTURED,tranDetail.getImageCaptured());
		paramSource.addValue(Constants.VEHICLE_SPEED,tranDetail.getVehicleSpeed());
		paramSource.addValue(Constants.SPEED_VIOLATION,tranDetail.getSpeedViolation());
		paramSource.addValue(Constants.RECIPROCITY_TRX,tranDetail.getReciprocityTrx());
		paramSource.addValue(Constants.IS_VIOLATION,tranDetail.getIsViolation());
		//paramSource.addValue(Constants.IS_LPR_ENABLED,tranDetail.getIsLprEnabled());
		paramSource.addValue(Constants.FULL_FARE_AMOUNT,tranDetail.getFullFareAmount());
		paramSource.addValue(Constants.DISCOUNTED_AMOUNT,tranDetail.getDiscountedAmount());
		paramSource.addValue(Constants.UNREALIZED_AMOUNT,tranDetail.getUnrealizedAmount());
		paramSource.addValue(Constants.EXT_FILE_ID,tranDetail.getExtFileId());
		paramSource.addValue(Constants.RECEIVED_DATE,tranDetail.getReceivedDate());
		paramSource.addValue(Constants.ACCOUNT_TYPE,tranDetail.getAccountType());
		paramSource.addValue(Constants.ACCT_AGENCY_ID,tranDetail.getAccAgencyId());
		paramSource.addValue(Constants.ETC_ACCOUNT_ID,tranDetail.getEtcAccountId());
		paramSource.addValue(Constants.ETC_SUBACCOUNT,tranDetail.getEtcSubAccount());
		paramSource.addValue(Constants.DST_FLAG,tranDetail.getDstFlag());
		paramSource.addValue(Constants.IS_PEAK,tranDetail.getIsPeak());
		paramSource.addValue(Constants.FARE_TYPE,tranDetail.getFareType());
		//paramSource.addValue(Constants.UPDATE_TS,tranDetail.getUpdateTs());timeZoneConv
		paramSource.addValue(Constants.UPDATE_TS,timeZoneConv.currentTime());
		paramSource.addValue(Constants.LANE_DATA_SOURCE,tranDetail.getLaneDataSource());
		paramSource.addValue(Constants.LANE_TYPE,tranDetail.getLaneType());
		paramSource.addValue(Constants.LANE_HEALTH,tranDetail.getLaneHealth());
		paramSource.addValue(Constants.AVC_AXLES,tranDetail.getAvcAxles());
		paramSource.addValue(Constants.TOUR_SEGMENT_ID,tranDetail.getTourSegment());
		paramSource.addValue(Constants.BUF_READ,tranDetail.getBufRead());
		paramSource.addValue(Constants.READER_ID,tranDetail.getReaderId());
		paramSource.addValue(Constants.TOLL_AMOUNT,tranDetail.getTollAmount());
		paramSource.addValue(Constants.DEBIT_CREDIT,tranDetail.getDebitCredit());
		paramSource.addValue(Constants.ETC_VALID_STATUS,tranDetail.getEtcValidStatus());
		paramSource.addValue(Constants.DISCOUNTED_AMOUNT_2,tranDetail.getDiscountedAmount2());
		paramSource.addValue(Constants.VIDEO_FARE_AMOUNT,tranDetail.getVideoFareAmount());
		paramSource.addValue(Constants.PLAN_TYPE,tranDetail.getPlanType());
		paramSource.addValue(Constants.RESERVED,tranDetail.getReserved());
		paramSource.addValue(Constants.ATP_FILE_ID,tranDetail.getAtpFileId());
		paramSource.addValue(Constants.AET_FLAG,tranDetail.getAetFlag());
		namedJdbcTemplate.update(query, paramSource);

	}

	public void batchInsertnEW(List<TranDetail> txlist) {
		String query = LoadJpaQueries.getQueryById("INSERT_T_TRAN_DETAIL_PARKING");
		
		jdbcTemplate.batchUpdate(query, new BatchPreparedStatementSetter() {
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				TranDetail tx = txlist.get(i);

		ps.setObject(1, tx.getLaneTxId() ,java.sql.Types.BIGINT); //LANE_TX_ID
		ps.setObject(2, tx.getTxExternRefNo() ,java.sql.Types.VARCHAR); //TX_EXTERN_REF_NO
		ps.setObject(3, tx.getTxType() ,java.sql.Types.VARCHAR); //TX_TYPE
		ps.setObject(4, tx.getTxSubType(),java.sql.Types.VARCHAR); //TX_SUB_TYPE
		ps.setObject(5, tx.getTollRevenueType(),java.sql.Types.BIGINT); //TOLL_REVENUE_TYPE
		ps.setObject(6, tx.getEntryTxTimeStamp() ,java.sql.Types.TIMESTAMP_WITH_TIMEZONE); //ENTRY_TX_TIMESTAMP
		ps.setObject(7, tx.getEntryPlazaId(),java.sql.Types.BIGINT); //ENTRY_PLAZA_ID
		ps.setObject(8, tx.getEntryLaneId(),java.sql.Types.BIGINT); //ENTRY_LANE_ID
		ps.setObject(9, tx.getPlazaAgencyId(),java.sql.Types.BIGINT); //PLAZA_AGENCY_ID
		ps.setObject(10, tx.getPlazaId(),java.sql.Types.BIGINT); //PLAZA_ID
		ps.setObject(11, tx.getLaneId(),java.sql.Types.BIGINT); //LANE_ID
		ps.setObject(12, tx.getTxDate(),java.sql.Types.DATE); //TX_DATE
		ps.setObject(13, tx.getTxTimeStamp() ,java.sql.Types.TIMESTAMP_WITH_TIMEZONE); //TX_TIMESTAMP
		ps.setObject(14, tx.getLaneMode(),java.sql.Types.BIGINT); //LANE_MODE
		ps.setObject(15, tx.getDeviceNo(),java.sql.Types.VARCHAR); //DEVICE_NO
		ps.setObject(16, tx.getActualClass(),java.sql.Types.BIGINT); //ACTUAL_CLASS
		ps.setObject(17, tx.getActualAxles(),java.sql.Types.BIGINT); //ACTUAL_AXLES
		ps.setObject(18, tx.getPlateState(),java.sql.Types.VARCHAR); //PLATE_STATE
		ps.setObject(19, tx.getPlateNumber(),java.sql.Types.VARCHAR); //PLATE_NUMBER
		ps.setObject(20, tx.getPlateCountry(),java.sql.Types.VARCHAR); //PLATE_COUNTRY
		ps.setObject(21, tx.getReadPerf(),java.sql.Types.BIGINT); //READ_PERF
		ps.setObject(22, tx.getWritePerf(),java.sql.Types.BIGINT); //WRITE_PERF
		ps.setObject(23, tx.getVehicleSpeed(),java.sql.Types.BIGINT); //VEHICLE_SPEED
		ps.setObject(24, tx.getSpeedViolation(),java.sql.Types.BIGINT); //SPEED_VIOLATION
		ps.setObject(25, tx.getDiscountedAmount(),java.sql.Types.BIGINT); //DISCOUNTED_AMOUNT
		ps.setObject(26, tx.getUnrealizedAmount(),java.sql.Types.BIGINT); //UNREALIZED_AMOUNT
		ps.setObject(27, tx.getReceivedDate() ,java.sql.Types.DATE); //RECEIVED_DATE
		ps.setObject(28, tx.getAccountType() ,java.sql.Types.BIGINT); //ACCOUNT_TYPE
		ps.setObject(29, tx.getAccAgencyId() ,java.sql.Types.BIGINT ); //ACCT_AGENCY_ID
		ps.setObject(30, tx.getEtcAccountId() ,java.sql.Types.BIGINT); //ETC_ACCOUNT_ID
		ps.setObject(31, tx.getEtcSubAccount() ,java.sql.Types.BIGINT); //ETC_SUBACCOUNT
		ps.setObject(32, timeZoneConv.currentTime() ,java.sql.Types.TIMESTAMP); //UPDATE_TS
		ps.setObject(33, tx.getDebitCredit() ,java.sql.Types.VARCHAR); //DEBIT_CREDIT
		ps.setObject(34, tx.getEtcValidStatus() ,java.sql.Types.BIGINT); //ETC_VALID_STATUS
		ps.setObject(35, tx.getDiscountedAmount2() ,java.sql.Types.BIGINT); //DISCOUNTED_AMOUNT_2
		ps.setObject(36, tx.getVideoFareAmount() ,java.sql.Types.BIGINT); //VIDEO_FARE_AMOUNT
		//ps.setObject(37, tx.getAtpFileId() ,java.sql.Types.BIGINT); //ATP_FILE_ID
		ps.setObject(37, tx.getAetFlag() ,java.sql.Types.VARCHAR) ;//AET_FLAG
		ps.setObject(38, tx.getExtFileId(),java.sql.Types.BIGINT); //EXT_FILE_ID
		ps.setObject(39, tx.getExpectedRevenueAmount(),java.sql.Types.DOUBLE); //EXPECTED_REVENUE_AMOUNT
		ps.setObject(40, tx.getPostedFareAmount(),java.sql.Types.DOUBLE); //POSTED_FARE_AMOUNT
		ps.setObject(41, tx.getEtcFareAmount(),java.sql.Types.DOUBLE); //ETC_FARE_AMOUNT
		ps.setObject(42, tx.getCashFareAmount(),java.sql.Types.DOUBLE); //CASH_FARE_AMOUNT
		ps.setObject(43, tx.getTxStatus(),java.sql.Types.INTEGER); //TX_STATUS
		ps.setObject(44, tx.getTollSystemType(),java.sql.Types.VARCHAR); //TOLL_SYSTEM_TYPE
		ps.setObject(45, tx.getProgStat(),java.sql.Types.INTEGER); //PROG_STAT
		ps.setObject(46, tx.getTollAmount(),java.sql.Types.DOUBLE); //TOLL_AMOUNT
	}

			@Override
			public int getBatchSize() {
				return txlist.size();
			}
		});
		
		
		/* SqlParameterSource[] batch = SqlParameterSourceUtils.createBatch(txlist.toArray());
		 
		 jdbcTemplate.batchUpdate(query, batch, getTypes());
		*/
	}
	
	public void batchInsertnEW1(List<TranDetail> txlist) {
		String query = LoadJpaQueries.getQueryById("INSERT_T_TRAN_DETAIL");
		
		jdbcTemplate.batchUpdate(query, new BatchPreparedStatementSetter() {
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				TranDetail tx = txlist.get(i);

		ps.setObject(1, tx.getLaneTxId() ,java.sql.Types.BIGINT); //LANE_TX_ID
		ps.setObject(2, tx.getTxExternRefNo() ,java.sql.Types.VARCHAR); //TX_EXTERN_REF_NO
		ps.setObject(3, tx.getTxType() ,java.sql.Types.VARCHAR); //TX_TYPE
		ps.setObject(4, tx.getTxSubType(),java.sql.Types.VARCHAR); //TX_SUB_TYPE
		ps.setObject(5, tx.getTollSystemType(),java.sql.Types.VARCHAR); //TOLL_SYSTEM_TYPE
		ps.setObject(6, tx.getTollRevenueType(),java.sql.Types.BIGINT); //TOLL_REVENUE_TYPE
		ps.setObject(7, tx.getEntryTxTimeStamp() ,java.sql.Types.TIMESTAMP_WITH_TIMEZONE); //ENTRY_TX_TIMESTAMP
		ps.setObject(8, tx.getEntryPlazaId(),java.sql.Types.BIGINT); //ENTRY_PLAZA_ID
		ps.setObject(9, tx.getEntryLaneId(),java.sql.Types.BIGINT); //ENTRY_LANE_ID
		ps.setObject(10, tx.getEntryTxSeqNumber(),java.sql.Types.BIGINT); //ENTRY_TX_SEQ_NUMBER
		ps.setObject(11, tx.getEntryDataSource(),java.sql.Types.VARCHAR); //ENTRY_DATA_SOURCE
		ps.setObject(12, tx.getEntryVehicleSpeed(),java.sql.Types.BIGINT); //ENTRY_VEHICLE_SPEED
		ps.setObject(13, tx.getPlazaAgencyId(),java.sql.Types.BIGINT); //PLAZA_AGENCY_ID
		ps.setObject(14, tx.getPlazaId(),java.sql.Types.BIGINT); //PLAZA_ID
		ps.setObject(15, tx.getLaneId(),java.sql.Types.BIGINT); //LANE_ID
		ps.setObject(16, tx.getTxDate(),java.sql.Types.DATE); //TX_DATE
		ps.setObject(17, tx.getTxTimeStamp() ,java.sql.Types.TIMESTAMP_WITH_TIMEZONE); //TX_TIMESTAMP
		ps.setObject(18, tx.getLaneMode(),java.sql.Types.BIGINT); //LANE_MODE
		ps.setObject(19, tx.getLaneState(),java.sql.Types.BIGINT); //LANE_STATE
		ps.setObject(20, tx.getLaneSn(),java.sql.Types.BIGINT); //TRX_LANE_SERIAL
		ps.setObject(21, tx.getDeviceNo(),java.sql.Types.VARCHAR); //DEVICE_NO
		ps.setObject(22, tx.getAvcClass(),java.sql.Types.BIGINT); //AVC_CLASS
		ps.setObject(23, tx.getTagIagClass(),java.sql.Types.BIGINT); //TAG_IAG_CLASS
		ps.setObject(24, tx.getTagClass(),java.sql.Types.BIGINT); //TAG_CLASS
		ps.setObject(25, tx.getTagAxles(),java.sql.Types.BIGINT); //TAG_AXLES
		ps.setObject(26, tx.getActualClass(),java.sql.Types.BIGINT); //ACTUAL_CLASS
		ps.setObject(27, tx.getActualAxles(),java.sql.Types.BIGINT); //ACTUAL_AXLES
		ps.setObject(28, tx.getExtraAxles(),java.sql.Types.BIGINT); //EXTRA_AXLES
		ps.setObject(29, tx.getPlateState(),java.sql.Types.VARCHAR); //PLATE_STATE
		ps.setObject(30, tx.getPlateNumber(),java.sql.Types.VARCHAR); //PLATE_NUMBER
		ps.setObject(31, tx.getPlateType(),java.sql.Types.BIGINT); //PLATE_TYPE
		ps.setObject(32, tx.getPlateCountry(),java.sql.Types.VARCHAR); //PLATE_COUNTRY
		ps.setObject(33, tx.getReadPerf(),java.sql.Types.BIGINT); //READ_PERF
		ps.setObject(34, tx.getWritePerf(),java.sql.Types.BIGINT); //WRITE_PERF
		ps.setObject(35, tx.getProgStat(),java.sql.Types.BIGINT); //PROG_STAT
		ps.setObject(36, tx.getCollectorNumber(),java.sql.Types.VARCHAR); //COLLECTOR_NUMBER
		ps.setObject(37, tx.getImageCaptured(),java.sql.Types.VARCHAR); //IMAGE_CAPTURED
		ps.setObject(38, tx.getVehicleSpeed(),java.sql.Types.BIGINT); //VEHICLE_SPEED
		ps.setObject(39, tx.getSpeedViolation(),java.sql.Types.BIGINT); //SPEED_VIOLATION
		ps.setObject(40, tx.getReciprocityTrx(),java.sql.Types.VARCHAR); //RECIPROCITY_TRX
		ps.setObject(41, tx.getIsViolation(),java.sql.Types.VARCHAR); //IS_VIOLATION
		//ps.setObject(42, tx.getIsLprEnabled(),java.sql.Types.VARCHAR); //IS_LPR_ENABLED
		ps.setObject(42, tx.getFullFareAmount(),java.sql.Types.BIGINT); //FULL_FARE_AMOUNT
		ps.setObject(43, tx.getDiscountedAmount(),java.sql.Types.BIGINT); //DISCOUNTED_AMOUNT
		ps.setObject(44, tx.getUnrealizedAmount(),java.sql.Types.BIGINT); //UNREALIZED_AMOUNT
		ps.setObject(45, tx.getExtFileId(),java.sql.Types.BIGINT); //EXT_FILE_ID
		ps.setObject(46, tx.getReceivedDate() ,java.sql.Types.DATE); //RECEIVED_DATE
		ps.setObject(47, tx.getAccountType() ,java.sql.Types.BIGINT); //ACCOUNT_TYPE
		ps.setObject(48, tx.getAccAgencyId() ,java.sql.Types.BIGINT ); //ACCT_AGENCY_ID
		ps.setObject(49, tx.getEtcAccountId() ,java.sql.Types.BIGINT); //ETC_ACCOUNT_ID
		ps.setObject(50, tx.getEtcSubAccount() ,java.sql.Types.BIGINT); //ETC_SUBACCOUNT
		ps.setObject(51, tx.getDstFlag() ,java.sql.Types.VARCHAR); //DST_FLAG
		ps.setObject(52, tx.getIsPeak() ,java.sql.Types.VARCHAR); //IS_PEAK
		ps.setObject(53, tx.getFareType() ,java.sql.Types.BIGINT); //FARE_TYPE
		ps.setObject(54, timeZoneConv.currentTime() ,java.sql.Types.TIMESTAMP); //UPDATE_TS
		ps.setObject(55, tx.getLaneDataSource() ,java.sql.Types.VARCHAR); //LANE_DATA_SOURCE
		ps.setObject(56, tx.getLaneType() ,java.sql.Types.BIGINT); //LANE_TYPE
		ps.setObject(57, tx.getLaneHealth() ,java.sql.Types.BIGINT); //LANE_HEALTH
		ps.setObject(58, tx.getAvcAxles() ,java.sql.Types.BIGINT); //AVC_AXLES
		ps.setObject(59, tx.getTourSegment() ,java.sql.Types.BIGINT); //TOUR_SEGMENT_ID
		ps.setObject(60, tx.getBufRead() ,java.sql.Types.VARCHAR); //BUF_READ
		ps.setObject(61, tx.getReaderId() ,java.sql.Types.VARCHAR);//readerid
		ps.setObject(62, tx.getTollAmount() ,java.sql.Types.BIGINT); //TOLL_AMOUNT
		ps.setObject(63, tx.getDebitCredit() ,java.sql.Types.VARCHAR); //DEBIT_CREDIT
		ps.setObject(64, tx.getEtcValidStatus() ,java.sql.Types.BIGINT); //ETC_VALID_STATUS
		ps.setObject(65, tx.getDiscountedAmount2() ,java.sql.Types.BIGINT); //DISCOUNTED_AMOUNT_2
		ps.setObject(66, tx.getVideoFareAmount() ,java.sql.Types.BIGINT); //VIDEO_FARE_AMOUNT
		ps.setObject(67, tx.getPlanType() ,java.sql.Types.BIGINT); //PLAN_TYPE
		ps.setObject(68, tx.getReserved() ,java.sql.Types.VARCHAR); //RESERVED
		ps.setObject(69, tx.getAtpFileId() ,java.sql.Types.BIGINT); //ATP_FILE_ID
		ps.setObject(70, tx.getAetFlag() ,java.sql.Types.VARCHAR) ;//AET_FLAG
		ps.setObject(71, tx.getMatchedTxExternRefNo(),java.sql.Types.VARCHAR); //MATCHED_TX_EXTERN_REF_NO
		
	}

			@Override
			public int getBatchSize() {
				return txlist.size();
			}
		});
		
		
		/* SqlParameterSource[] batch = SqlParameterSourceUtils.createBatch(txlist.toArray());
		 
		 jdbcTemplate.batchUpdate(query, batch, getTypes());
		*/
	}

	private int[] getTypes() {
		// TODO Auto-generated method stub
		return new int[] {
		Types.NUMERIC, //LANE_TX_ID
		Types.VARCHAR, //TX_EXTERN_REF_NO
		Types.VARCHAR, //TX_TYPE
		Types.VARCHAR, //TX_SUB_TYPE
		Types.VARCHAR, //TOLL_SYSTEM_TYPE
		Types.NUMERIC, //TOLL_REVENUE_TYPE
		Types.NUMERIC, //ENTRY_PLAZA_ID
		Types.NUMERIC, //ENTRY_LANE_ID
		Types.NUMERIC, //ENTRY_TX_SEQ_NUMBER
		Types.VARCHAR, //ENTRY_DATA_SOURCE
		Types.NUMERIC, //ENTRY_VEHICLE_SPEED
		Types.NUMERIC, //PLAZA_AGENCY_ID
		Types.NUMERIC, //PLAZA_ID
		Types.NUMERIC, //LANE_ID
		Types.DATE, //TX_DATE
		Types.NUMERIC, //LANE_MODE
		Types.NUMERIC, //LANE_STATE
		Types.NUMERIC, //TRX_LANE_SERIAL
		Types.VARCHAR, //DEVICE_NO
		Types.NUMERIC, //AVC_CLASS
		Types.NUMERIC, //TAG_IAG_CLASS
		Types.NUMERIC, //TAG_CLASS
		Types.NUMERIC, //TAG_AXLES
		Types.NUMERIC, //ACTUAL_CLASS
		Types.NUMERIC, //ACTUAL_AXLES
		Types.NUMERIC, //EXTRA_AXLES
		Types.VARCHAR, //PLATE_STATE
		Types.VARCHAR, //PLATE_NUMBER
		Types.NUMERIC, //PLATE_TYPE
		Types.VARCHAR, //PLATE_COUNTRY
		Types.NUMERIC, //READ_PERF
		Types.NUMERIC, //WRITE_PERF
		Types.NUMERIC, //PROG_STAT
		Types.VARCHAR, //COLLECTOR_NUMBER
		Types.VARCHAR, //IMAGE_CAPTURED
		Types.NUMERIC, //VEHICLE_SPEED
		Types.NUMERIC, //SPEED_VIOLATION
		Types.VARCHAR, //RECIPROCITY_TRX
		Types.VARCHAR, //IS_VIOLATION
		Types.VARCHAR, //IS_LPR_ENABLED
		Types.NUMERIC, //FULL_FARE_AMOUNT
		Types.NUMERIC, //DISCOUNTED_AMOUNT
		Types.NUMERIC, //UNREALIZED_AMOUNT
		Types.NUMERIC, //EXT_FILE_ID



		Types.DATE, //RECEIVED_DATE
		Types.NUMERIC, //ACCOUNT_TYPE
		Types.NUMERIC, //ACCT_AGENCY_ID




		Types.NUMERIC, //ETC_ACCOUNT_ID
		Types.NUMERIC, //ETC_SUBACCOUNT
		Types.VARCHAR, //DST_FLAG
		Types.VARCHAR, //IS_PEAK
		Types.NUMERIC, //FARE_TYPE
		Types.VARCHAR, //ETC_TX_STATUS
		Types.VARCHAR, //DEPOSIT_ID
		Types.DATE, //POSTED_DATE
		Types.VARCHAR, //LANE_DATA_SOURCE
		Types.NUMERIC, //LANE_TYPE
		Types.NUMERIC, //LANE_HEALTH
		Types.NUMERIC, //AVC_AXLES
		Types.NUMERIC, //TOUR_SEGMENT_ID
		Types.VARCHAR, //BUF_READ
		Types.NUMERIC, //TOLL_AMOUNT
		Types.VARCHAR, //DEBIT_CREDIT
		Types.NUMERIC, //ETC_VALID_STATUS
		Types.NUMERIC, //DISCOUNTED_AMOUNT_2
		Types.NUMERIC, //VIDEO_FARE_AMOUNT
		Types.NUMERIC, //PLAN_TYPE
		Types.VARCHAR, //RESERVED





		Types.NUMERIC, //ATP_FILE_ID
		Types.VARCHAR, //PLAN_CHARGED
		Types.VARCHAR, //TRANSACTION_INFO
		Types.NUMERIC, //ETC_FARE_AMOUNT
		Types.NUMERIC, //POSTED_FARE_AMOUNT
		Types.NUMERIC, //EXPECTED_REVENUE_AMOUNT
		Types.NUMERIC, //CASH_FARE_AMOUNT
		Types.NUMERIC, //TX_STATUS
		Types.TIMESTAMP_WITH_TIMEZONE, //TX_TIMESTAMP_BCK
		Types.TIMESTAMP_WITH_TIMEZONE, //TX_TIMESTAMP
		Types.TIMESTAMP_WITH_TIMEZONE, //ENTRY_TX_TIMESTAMP
		Types.VARCHAR, //AET_FLAG
		Types.TIMESTAMP //UPDATE_TS
		};
		}

	public List<Long> loadNextSeq(Integer numOfSeq) 
	{
		String sql = "select TPMS.SEQ_LANE_TX_ID.nextval as nextseq from dual connect by level <= :numOfSeq";
		List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql, numOfSeq);
		List<Long> result = new ArrayList<>();
		for (Map<String, Object> row : rows) 
		{
			BigDecimal t = ((BigDecimal) row.get("nextseq"));
			Long seq = t.longValue();
			result.add(seq);
		}
		return result;
	}

	@Override
	public void batchInsert(List<TranDetail> txlist) {
		// TODO Auto-generated method stub
		
	}

}
