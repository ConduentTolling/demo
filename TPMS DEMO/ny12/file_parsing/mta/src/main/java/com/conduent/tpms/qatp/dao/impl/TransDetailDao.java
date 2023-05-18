package com.conduent.tpms.qatp.dao.impl;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import com.conduent.tpms.qatp.config.LoadJpaQueries;
import com.conduent.tpms.qatp.constants.Constants;
import com.conduent.tpms.qatp.dao.ITransDetailDao;
import com.conduent.tpms.qatp.model.TranDetail;

@Repository
public class TransDetailDao implements ITransDetailDao {

	@Autowired
	private NamedParameterJdbcTemplate namedJdbcTemplate;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public void saveTransDetail(TranDetail tranDetail) {
		// CREATE SEQUENCE T_TRAN_DETAILS_SEQ START WITH 1 INCREMENT BY 1 NOCACHE
		// NOCYCLE;
		String query = LoadJpaQueries.getQueryById("INSERT_T_TRAN_DETAIL");

		MapSqlParameterSource paramSource = null;

		tranDetail.setLaneTxId(loadNextSeq(1).get(0));

		paramSource = new MapSqlParameterSource();
		paramSource.addValue(Constants.LANE_TX_ID, tranDetail.getLaneTxId());
		paramSource.addValue(Constants.TX_EXTERN_REF_NO, tranDetail.getTxExternRefNo());
		paramSource.addValue(Constants.TX_TYPE, tranDetail.getTxType());
		paramSource.addValue(Constants.TX_SUB_TYPE, tranDetail.getTxSubType());
		paramSource.addValue(Constants.TOLL_SYSTEM_TYPE, tranDetail.getTollSystemType());
		paramSource.addValue(Constants.TOLL_REVENUE_TYPE, tranDetail.getTollRevenueType());
		paramSource.addValue(Constants.ENTRY_TX_TIMESTAMP, tranDetail.getEntryTxTimeStamp());
		paramSource.addValue(Constants.ENTRY_PLAZA_ID, tranDetail.getEntryPlazaId());
		paramSource.addValue(Constants.ENTRY_LANE_ID, tranDetail.getEntryLaneId());
		paramSource.addValue(Constants.ENTRY_TX_SEQ_NUMBER, tranDetail.getEntryTxSeqNumber());
		paramSource.addValue(Constants.ENTRY_DATA_SOURCE, tranDetail.getEntryDataSource());
		paramSource.addValue(Constants.ENTRY_VEHICLE_SPEED, tranDetail.getEntryVehicleSpeed());
		paramSource.addValue(Constants.PLAZA_AGENCY_ID, tranDetail.getPlazaAgencyId());
		paramSource.addValue(Constants.PLAZA_ID, tranDetail.getPlazaId());
		paramSource.addValue(Constants.LANE_ID, tranDetail.getLaneId());
		paramSource.addValue(Constants.TX_DATE, tranDetail.getTxDate());
		paramSource.addValue(Constants.TX_TIMESTAMP, tranDetail.getTxTimeStamp());
		paramSource.addValue(Constants.LANE_MODE, tranDetail.getLaneMode());
		paramSource.addValue(Constants.LANE_STATE, tranDetail.getLaneState());
		paramSource.addValue(Constants.TRX_LANE_SERIAL, tranDetail.getTrxLaneSerial());
		paramSource.addValue(Constants.DEVICE_NO, tranDetail.getDeviceNo());
		paramSource.addValue(Constants.AVC_CLASS, tranDetail.getAvcClass());
		paramSource.addValue(Constants.TAG_IAG_CLASS, tranDetail.getTagIagClass());
		paramSource.addValue(Constants.TAG_CLASS, tranDetail.getTagClass());
		paramSource.addValue(Constants.TAG_AXLES, tranDetail.getTagAxles());
		paramSource.addValue(Constants.ACTUAL_CLASS, tranDetail.getActualClass());
		paramSource.addValue(Constants.ACTUAL_AXLES, tranDetail.getActualAxles());
		paramSource.addValue(Constants.EXTRA_AXLES, tranDetail.getExtraAxles());
		paramSource.addValue(Constants.PLATE_STATE, tranDetail.getPlateState());
		paramSource.addValue(Constants.PLATE_NUMBER, tranDetail.getPlateNumber());
		paramSource.addValue(Constants.PLATE_TYPE, tranDetail.getPlateType());
		paramSource.addValue(Constants.PLATE_COUNTRY, tranDetail.getPlateCountry());
		paramSource.addValue(Constants.READ_PERF, tranDetail.getReadPerf());
		paramSource.addValue(Constants.WRITE_PERF, tranDetail.getWritePerf());
		paramSource.addValue(Constants.PROG_STAT, tranDetail.getProgStat());
		paramSource.addValue(Constants.COLLECTOR_NUMBER, tranDetail.getCollectorNumber());
		paramSource.addValue(Constants.IMAGE_CAPTURED, tranDetail.getImageCaptured());
		paramSource.addValue(Constants.VEHICLE_SPEED, tranDetail.getVehicleSpeed());
		paramSource.addValue(Constants.SPEED_VIOLATION, tranDetail.getSpeedViolation());
		paramSource.addValue(Constants.RECIPROCITY_TRX, tranDetail.getReciprocityTrx());
		paramSource.addValue(Constants.IS_VIOLATION, tranDetail.getIsViolation());
		paramSource.addValue(Constants.IS_LPR_ENABLED, tranDetail.getIsLprEnabled());
		paramSource.addValue(Constants.FULL_FARE_AMOUNT, tranDetail.getFullFareAmount());
		paramSource.addValue(Constants.DISCOUNTED_AMOUNT, tranDetail.getDiscountedAmount());
		paramSource.addValue(Constants.UNREALIZED_AMOUNT, tranDetail.getUnrealizedAmount());
		paramSource.addValue(Constants.EXT_FILE_ID, tranDetail.getExtFileId());
		paramSource.addValue(Constants.RECEIVED_DATE, tranDetail.getReceivedDate());
		paramSource.addValue(Constants.ACCOUNT_TYPE, tranDetail.getAccountType());
		paramSource.addValue(Constants.ACCT_AGENCY_ID, tranDetail.getAccAgencyId());
		paramSource.addValue(Constants.ETC_ACCOUNT_ID, tranDetail.getEtcAccountId());
		paramSource.addValue(Constants.ETC_SUBACCOUNT, tranDetail.getEtcSubAccount());
		paramSource.addValue(Constants.DST_FLAG, tranDetail.getDstFlag());
		paramSource.addValue(Constants.IS_PEAK, tranDetail.getIsPeak());
		paramSource.addValue(Constants.FARE_TYPE, tranDetail.getFareType());
		paramSource.addValue(Constants.UPDATE_TS, tranDetail.getUpdateTs());
		paramSource.addValue(Constants.TRANSACTION_INFO, tranDetail.getTransactionInfo());
		paramSource.addValue(Constants.ETC_TX_STATUS, tranDetail.getEtcTxStatus());
		paramSource.addValue(Constants.DEPOSIT_ID, tranDetail.getDepositId());
		paramSource.addValue(Constants.POSTED_DATE, tranDetail.getPostedDate());
		paramSource.addValue(Constants.LANE_DATA_SOURCE, tranDetail.getLaneDataSource());
		paramSource.addValue(Constants.LANE_TYPE, tranDetail.getLaneType());
		paramSource.addValue(Constants.LANE_HEALTH, tranDetail.getLaneHealth());
		paramSource.addValue(Constants.AVC_AXLES, tranDetail.getAvcAxles());
		paramSource.addValue(Constants.TOUR_SEGMENT_ID, tranDetail.getTourSegmentId());
		paramSource.addValue(Constants.BUF_READ, tranDetail.getBufRead());
		paramSource.addValue(Constants.READER_ID, tranDetail.getReaderId());
		paramSource.addValue(Constants.TOLL_AMOUNT, tranDetail.getTollAmount());
		paramSource.addValue(Constants.DEBIT_CREDIT, tranDetail.getDebitCredit());
		paramSource.addValue(Constants.ETC_VALID_STATUS, tranDetail.getEtcValidStatus());
		paramSource.addValue(Constants.DISCOUNTED_AMOUNT_2, tranDetail.getDiscountedAmount2());
		paramSource.addValue(Constants.VIDEO_FARE_AMOUNT, tranDetail.getVideoFareAmount());
		paramSource.addValue(Constants.PLAN_TYPE, tranDetail.getPlanType());
		paramSource.addValue(Constants.RESERVED, tranDetail.getReserved());
		paramSource.addValue(Constants.ATP_FILE_ID, tranDetail.getAtpFileId());
		paramSource.addValue(Constants.PLAN_CHARGED, tranDetail.getPlanCharged());
		paramSource.addValue(Constants.AET_FLAG, tranDetail.getAetFlag());
		

		namedJdbcTemplate.update(query, paramSource);

	}

	@Override
	public void batchInsert(List<TranDetail> list) {
		// String query = LoadJpaQueries.getQueryById("INSERT_T_TRAN_DETAIL");
		String query = "INSERT INTO T_TRAN_DETAIL \n"
				+ "					(LANE_TX_ID,TX_EXTERN_REF_NO, TX_TYPE, TX_SUB_TYPE, TOLL_SYSTEM_TYPE, TOLL_REVENUE_TYPE, \n"
				+ "					ENTRY_TX_TIMESTAMP, ENTRY_PLAZA_ID, ENTRY_LANE_ID, ENTRY_TX_SEQ_NUMBER, ENTRY_DATA_SOURCE, \n"
				+ "					ENTRY_VEHICLE_SPEED, PLAZA_AGENCY_ID, PLAZA_ID, LANE_ID, TX_DATE, TX_TIMESTAMP, \n"
				+ "					LANE_MODE, LANE_STATE, TRX_LANE_SERIAL, DEVICE_NO, AVC_CLASS, TAG_IAG_CLASS, \n"
				+ "					TAG_CLASS, TAG_AXLES, ACTUAL_CLASS, ACTUAL_AXLES, EXTRA_AXLES, \n"
				+ "					PLATE_STATE, PLATE_NUMBER, PLATE_TYPE, PLATE_COUNTRY, READ_PERF, WRITE_PERF, \n"
				+ "					PROG_STAT, COLLECTOR_NUMBER, IMAGE_CAPTURED, VEHICLE_SPEED, SPEED_VIOLATION, \n"
				+ "					RECIPROCITY_TRX, IS_VIOLATION, FULL_FARE_AMOUNT, DISCOUNTED_AMOUNT, \n"
				+ "					UNREALIZED_AMOUNT, EXT_FILE_ID, RECEIVED_DATE, ACCOUNT_TYPE, ACCT_AGENCY_ID, \n"
				+ "					ETC_ACCOUNT_ID, ETC_SUBACCOUNT, DST_FLAG, IS_PEAK,FARE_TYPE,UPDATE_TS,TRANSACTION_INFO, \n"
				+ "					ETC_TX_STATUS, DEPOSIT_ID, POSTED_DATE, LANE_DATA_SOURCE, LANE_TYPE, \n"
				+ "					LANE_HEALTH, AVC_AXLES, TOUR_SEGMENT_ID, BUF_READ, READER_ID, TOLL_AMOUNT, \n"
				+ "					DEBIT_CREDIT, ETC_VALID_STATUS, DISCOUNTED_AMOUNT_2, VIDEO_FARE_AMOUNT, PLAN_TYPE, \n"
				+ "					RESERVED, ATP_FILE_ID, PLAN_CHARGED,AET_FLAG) \n"
				+ "					values (?,?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?, ?,?, ?, ?, ?, ?, ?, ?, \n"
				+ "					?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?, \n"
				+ "					?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?)";
		List<MapSqlParameterSource> paramSourceList = new ArrayList<>(list.size());
		jdbcTemplate.batchUpdate(query, new BatchPreparedStatementSetter() {

			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {

				ps.setObject(1, (list.get(i).getLaneTxId()));
				ps.setObject(2, (list.get(i).getTxExternRefNo()));

				ps.setString(3, list.get(i).getTxType());
				ps.setString(4, list.get(i).getTxSubType());
				ps.setString(5, list.get(i).getTollSystemType());
				ps.setObject(6, (list.get(i).getTollRevenueType()));
				//ps.setInt(6, list.get(i).getTollRevenueType());
				ps.setObject(7, (list.get(i).getEntryTxTimeStamp()));
				ps.setObject(8, (list.get(i).getEntryPlazaId()));
				ps.setObject(9, (list.get(i).getEntryLaneId()));
				ps.setObject(10, (list.get(i).getEntryTxSeqNumber()));

				ps.setString(11, list.get(i).getEntryDataSource());
				ps.setObject(12, list.get(i).getEntryVehicleSpeed());
				ps.setObject(13, (list.get(i).getPlazaAgencyId()));
				ps.setObject(14, (list.get(i).getPlazaId()));
				ps.setObject(15, (list.get(i).getLaneId()));
				// ps.setObject(16, (list.get(i).getTxDate()));
				ps.setObject(16, (list.get(i).getTxDate()), java.sql.Types.DATE);

				ps.setObject(17, (list.get(i).getTxTimeStamp()));
				ps.setObject(18, (list.get(i).getLaneMode()));
				ps.setObject(19, (list.get(i).getLaneState()));

				ps.setObject(20, (list.get(i).getTrxLaneSerial()));

				ps.setObject(21, (list.get(i).getDeviceNo()));
				ps.setObject(22, (list.get(i).getAvcClass()));
				ps.setObject(23, (list.get(i).getTagIagClass()));
				ps.setObject(24, (list.get(i).getTagClass()));
				ps.setObject(25, (list.get(i).getTagAxles()));
				ps.setObject(26, (list.get(i).getActualClass()));
				ps.setObject(27, (list.get(i).getActualAxles()));
				ps.setObject(28, (list.get(i).getExtraAxles()));
				ps.setObject(29, (list.get(i).getPlateState()));
				ps.setObject(30, (list.get(i).getPlateNumber()));
				ps.setObject(31, (list.get(i).getPlateType()));

				ps.setObject(32, (list.get(i).getPlateCountry()));
				ps.setObject(33, (list.get(i).getReadPerf()));
				ps.setObject(34, (list.get(i).getWritePerf()));
				ps.setObject(35, (list.get(i).getProgStat()));
				ps.setObject(36, (list.get(i).getCollectorNumber()));
				ps.setObject(37, (list.get(i).getImageCaptured()));
				ps.setObject(38, (list.get(i).getVehicleSpeed()));
				ps.setObject(39, (list.get(i).getSpeedViolation()));
				ps.setObject(40, (list.get(i).getReciprocityTrx()));
				ps.setObject(41, (list.get(i).getIsViolation()));
				//ps.setObject(42, (list.get(i).getIsLprEnabled()));
				ps.setObject(42, (list.get(i).getFullFareAmount()));
				ps.setObject(43, (list.get(i).getDiscountedAmount()));
				ps.setObject(44, (list.get(i).getUnrealizedAmount()));

				ps.setObject(45, (list.get(i).getExtFileId()));
				// ps.setObject(46, (list.get(i).getReceivedDate()));
				ps.setObject(46, (list.get(i).getReceivedDate()), java.sql.Types.DATE);

				ps.setObject(47, (list.get(i).getAccountType()));
				ps.setObject(48, (list.get(i).getAccAgencyId()));
				ps.setObject(49, (list.get(i).getEtcAccountId()));
				ps.setObject(50, (list.get(i).getEtcSubAccount()));
				ps.setObject(51, (list.get(i).getDstFlag()));
				ps.setObject(52, (list.get(i).getIsPeak()));
				ps.setObject(53, (list.get(i).getFareType()));
				// ps.setObject(55, (list.get(i).getUpdateTs()));
				ps.setObject(54, (list.get(i).getUpdateTs()), java.sql.Types.TIMESTAMP);
				ps.setObject(55, (list.get(i).getTransactionInfo()));
				ps.setObject(56, (list.get(i).getEtcTxStatus()));
				ps.setObject(57, (list.get(i).getDepositId()));
				ps.setObject(58, (list.get(i).getPostedDate()),java.sql.Types.DATE);
				ps.setObject(59, (list.get(i).getLaneDataSource()));
				ps.setObject(60, (list.get(i).getLaneType()));
				ps.setObject(61, (list.get(i).getLaneHealth()));
				ps.setObject(62, (list.get(i).getAvcAxles()));
				ps.setObject(63, (list.get(i).getTourSegmentId()));
				ps.setObject(64, (list.get(i).getBufRead()));

				ps.setObject(65, (list.get(i).getReaderId()));
				// ps.setObject(46, (list.get(i).getReceivedDate()));
				ps.setObject(66, (list.get(i).getTollAmount()));

				ps.setObject(67, (list.get(i).getDebitCredit()));
				ps.setObject(68, (list.get(i).getEtcValidStatus()));
				ps.setObject(69, (list.get(i).getDiscountedAmount2()));
				ps.setObject(70, (list.get(i).getVideoFareAmount()));
				ps.setObject(71, (list.get(i).getPlanType()));
				ps.setObject(72, (list.get(i).getReserved()));
				ps.setObject(73, (list.get(i).getAtpFileId()));
				// ps.setObject(55, (list.get(i).getUpdateTs()));
				ps.setObject(74, (list.get(i).getPlanCharged()));
				ps.setObject(75, (list.get(i).getAetFlag()));

			}

			@Override
			public int getBatchSize() {
				// TODO Auto-generated method stub
				return list.size();
			}
		});

	}

	public List<Long> loadNextSeq(Integer numOfSeq) {
		String sql = "select TPMS.SEQ_LANE_TX_ID.nextval as nextseq from dual connect by level <= :numOfSeq";
		List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql, numOfSeq);
		List<Long> result = new ArrayList<>();
		for (Map<String, Object> row : rows) {
			BigDecimal t = ((BigDecimal) row.get("nextseq"));
			Long seq = t.longValue();
			result.add(seq);
		}
		return result;
	}

}
