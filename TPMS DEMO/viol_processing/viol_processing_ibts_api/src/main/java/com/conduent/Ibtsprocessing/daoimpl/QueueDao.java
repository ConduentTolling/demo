package com.conduent.Ibtsprocessing.daoimpl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.stereotype.Repository;

import com.conduent.Ibtsprocessing.constant.LoadJpaQueries;
import com.conduent.Ibtsprocessing.constant.QatpClassificationConstant;
import com.conduent.Ibtsprocessing.dao.IQueueDao;
import com.conduent.Ibtsprocessing.dto.IbtsViolProcessDTO;
import com.conduent.app.timezone.utility.TimeZoneConv;

@Repository
public class QueueDao implements IQueueDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	TimeZoneConv timezoneconv;

	private static final Logger logger = LoggerFactory.getLogger(QueueDao.class);

	/**
	 * Insert into Queue table
	 */
	@Override
	public void insertInToQueue(IbtsViolProcessDTO commonDTO) {
		logger.debug("Insert into QUEUE table starts for etc account id {}", commonDTO.getEtcAccountId());

		String query = LoadJpaQueries.getQueryById(QatpClassificationConstant.INSERT_INTO_QUEUE);

		PreparedStatementCreator psc = new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				final PreparedStatement ps = connection.prepareStatement(query);
				if (commonDTO.getLaneTxId() != null) {
					ps.setLong(1, commonDTO.getLaneTxId());
				} else {
					ps.setNull(1, java.sql.Types.NUMERIC);
				}

				if (commonDTO.getTxExternRefNo() != null) {
					ps.setString(2, commonDTO.getTxExternRefNo());
				} else {
					ps.setNull(2, java.sql.Types.VARCHAR);
				}

				if (commonDTO.getTxSeqNumber() != null) {
					ps.setLong(3, commonDTO.getTxSeqNumber());
				} else {
					ps.setNull(3, java.sql.Types.NUMERIC);
				}

				if (commonDTO.getExternFileId() != null) {
					ps.setLong(4, commonDTO.getExternFileId());
				} else {
					ps.setNull(4, java.sql.Types.NUMERIC);
				}

				if (commonDTO.getPlazaAgencyId() != null) {
					ps.setInt(5, commonDTO.getPlazaAgencyId());
				} else {
					ps.setNull(5, java.sql.Types.NUMERIC);
				}

				if (commonDTO.getPlazaId() != null) {
					ps.setLong(6, commonDTO.getPlazaId());
				} else {
					ps.setNull(6, java.sql.Types.NUMERIC);
				}

				if (commonDTO.getLaneId() != null) {
					ps.setLong(7, commonDTO.getLaneId());
				} else {
					ps.setNull(7, java.sql.Types.NUMERIC);
				}

				if (commonDTO.getTxTimeStamp() != null) {
					//ps.setTimestamp(8, commonDTO.getTxTimestamp());
					ps.setTimestamp(8,  Timestamp.valueOf(commonDTO.getTxTimeStamp().toLocalDateTime()));
				} else {
					ps.setNull(8, java.sql.Types.VARCHAR);
				}

				if (commonDTO.getTxModSeq() != null) {
					ps.setLong(9, commonDTO.getTxModSeq());
				} else {
					ps.setNull(9, java.sql.Types.NUMERIC);
				}

				if (commonDTO.getTxType() != null) {
					ps.setString(10, commonDTO.getTxType());
				} else {
					ps.setNull(10, java.sql.Types.NUMERIC);
				}

				if (commonDTO.getTxSubtype() != null) {
					ps.setString(11, commonDTO.getTxSubtype());
				} else {
					ps.setNull(11, java.sql.Types.VARCHAR);
				}

				if (commonDTO.getTollSystemType() != null) {
					ps.setString(12, commonDTO.getTollSystemType());
				} else {
					ps.setNull(12, java.sql.Types.VARCHAR);
				}

				if (commonDTO.getLaneMode() != null) {
					ps.setLong(13, commonDTO.getLaneMode());
				} else {
					ps.setNull(13, java.sql.Types.NUMERIC);
				}

				if (commonDTO.getLaneType() != null) {
					ps.setInt(14, commonDTO.getLaneType());
				} else {
					ps.setNull(14, java.sql.Types.VARCHAR);
				}

				if (commonDTO.getLaneState() != null) {
					ps.setLong(15, commonDTO.getLaneState());
				} else {
					ps.setNull(15, java.sql.Types.NUMERIC);
				}

				if (commonDTO.getLaneHealth() != null) {
					ps.setLong(16, commonDTO.getLaneHealth());
				} else {
					ps.setNull(16, java.sql.Types.NUMERIC);
				}

				if (commonDTO.getcollectorNumber() != null) {
					ps.setInt(17, commonDTO.getcollectorNumber());
				} else {
					ps.setNull(17, java.sql.Types.VARCHAR);
				}

				if (commonDTO.getTourSegment() != null) {
					ps.setInt(18, commonDTO.getTourSegment());
				} else {
					ps.setNull(18, java.sql.Types.VARCHAR);
				}

				if (commonDTO.getEntryDataSource() != null) {
					ps.setString(19, commonDTO.getEntryDataSource());
				} else {
					ps.setNull(19, java.sql.Types.VARCHAR);
				}

				if (commonDTO.getEntryLaneId() != null) {
					ps.setLong(20, commonDTO.getEntryLaneId());
				} else {
					ps.setNull(20, java.sql.Types.NUMERIC);
				}

				if (commonDTO.getEntryPlazaId() != null) {
					ps.setLong(21, commonDTO.getEntryPlazaId());
				} else {
					ps.setNull(21, java.sql.Types.NUMERIC);
				}

				if (commonDTO.getEntryTimestamp() != null) {
					ps.setTimestamp(22, Timestamp.valueOf(commonDTO.getEntryTimestamp().toLocalDateTime()));
				} else {
					ps.setNull(22, java.sql.Types.TIMESTAMP);
				}

				if (commonDTO.getEntryTxSeqNumber() != null) {
					ps.setLong(23, commonDTO.getEntryTxSeqNumber());
				} else {
					ps.setNull(23, java.sql.Types.NUMERIC);
				}

				if (commonDTO.getEntryVehicleSpeed() != null) {
					ps.setLong(24, commonDTO.getEntryVehicleSpeed());
				} else {
					ps.setNull(24, java.sql.Types.NUMERIC);
				}

				if (commonDTO.getLaneTxStatus() != null) {
					ps.setLong(25, commonDTO.getLaneTxStatus());
				} else {
					ps.setNull(25, java.sql.Types.NUMERIC);
				}

				if (commonDTO.getLanetxType() != null) {
					ps.setLong(26, commonDTO.getLanetxType());
				} else {
					ps.setNull(26, java.sql.Types.NUMERIC);
				}

				if (commonDTO.getTollRevenueType() != null) {
					ps.setLong(27, commonDTO.getTollRevenueType());
				} else {
					ps.setNull(27, java.sql.Types.NUMERIC);
				}

				if (commonDTO.getActualClass() != null) {
					ps.setLong(28, commonDTO.getActualClass());
				} else {
					ps.setNull(28, java.sql.Types.NUMERIC);
				}

				if (commonDTO.getActualAxles() != null) {
					ps.setInt(29, commonDTO.getActualAxles());
				} else {
					ps.setNull(29, java.sql.Types.NUMERIC);
				}

				if (commonDTO.getActualExtraAxles() != null) {
					ps.setInt(30, commonDTO.getActualExtraAxles());
				} else {
					ps.setNull(30, java.sql.Types.NUMERIC);
				}

				if (commonDTO.getCollectorClass() != null) {
					ps.setInt(31, commonDTO.getCollectorClass());
				} else {
					ps.setNull(31, java.sql.Types.NUMERIC);
				}

				if (commonDTO.getCollectorAxles() != null) {
					ps.setInt(32, commonDTO.getCollectorAxles());
				} else {
					ps.setNull(32, java.sql.Types.NUMERIC);
				}

				if (commonDTO.getPreClassClass() != null) {
					ps.setInt(33, commonDTO.getPreClassClass());
				} else {
					ps.setNull(33, java.sql.Types.NUMERIC);
				}

				if (commonDTO.getPreClassAxles() != null) {
					ps.setInt(34, commonDTO.getPreClassAxles());
				} else {
					ps.setNull(34, java.sql.Types.NUMERIC);
				}

				if (commonDTO.getPostClassClass() != null) {
					ps.setInt(35, commonDTO.getPostClassClass());
				} else {
					ps.setNull(35, java.sql.Types.NUMERIC);
				}

				if (commonDTO.getPostClassAxles() != null) {
					ps.setInt(36, commonDTO.getPostClassAxles());
				} else {
					ps.setNull(36, java.sql.Types.NUMERIC);
				}

				if (commonDTO.getForwardAxles() != null) {
					ps.setInt(37, commonDTO.getForwardAxles());
				} else {
					ps.setNull(37, java.sql.Types.NUMERIC);
				}

				if (commonDTO.getReverseAxles() != null) {
					ps.setInt(38, commonDTO.getReverseAxles());
				} else {
					ps.setNull(38, java.sql.Types.NUMERIC);
				}

				if (commonDTO.getFullFareAmount() != null) {
					ps.setDouble(39, commonDTO.getFullFareAmount());
				} else {
					ps.setNull(39, java.sql.Types.NUMERIC);
				}

				if (commonDTO.getDiscountedAmount() != null) {
					ps.setDouble(40, commonDTO.getDiscountedAmount());
				} else {
					ps.setNull(40, java.sql.Types.NUMERIC);
				}

				if (commonDTO.getCollectedAmount() != null) {
					ps.setDouble(41, commonDTO.getCollectedAmount());
				} else {
					ps.setNull(41, java.sql.Types.NUMERIC);
				}

				if (commonDTO.getUnrealizedAmount() != null) {
					ps.setDouble(42, commonDTO.getUnrealizedAmount());
				} else {
					ps.setNull(42, java.sql.Types.NUMERIC);
				}

				if (commonDTO.getIsDiscountable() != null) {
					ps.setString(43, commonDTO.getIsDiscountable());
				} else {
					ps.setNull(43, java.sql.Types.VARCHAR);
				}

				if (commonDTO.getIsMedianFare() != null) {
					ps.setString(44, commonDTO.getIsMedianFare());
				} else {
					ps.setNull(44, java.sql.Types.VARCHAR);
				}

				if (commonDTO.getIsPeak() != null) {
					ps.setString(45, commonDTO.getIsPeak());
				} else {
					ps.setNull(45, java.sql.Types.VARCHAR);
				}

				if (commonDTO.getPriceScheduleId() != null) {
					ps.setInt(46, commonDTO.getPriceScheduleId());
				} else {
					ps.setNull(46, java.sql.Types.NUMERIC);
				}

				if (commonDTO.getVehicleSpeed() != null) {
					ps.setInt(47, commonDTO.getVehicleSpeed());
				} else {
					ps.setNull(47, java.sql.Types.NUMERIC);
				}

				if (commonDTO.getReceiptIssued() != null) {
					ps.setInt(48, commonDTO.getReceiptIssued());
				} else {
					ps.setNull(48, java.sql.Types.NUMERIC);
				}

				if (commonDTO.getDeviceNo() != null) {
					ps.setString(49, commonDTO.getDeviceNo());
				} else {
					ps.setNull(49, java.sql.Types.VARCHAR);
				}

				if (commonDTO.getAccountType() != null) {
					ps.setInt(50, commonDTO.getAccountType());
				} else {
					ps.setNull(50, java.sql.Types.NUMERIC);
				}
				if (commonDTO.getDeviceCodedClass() != null) {
					ps.setInt(51, commonDTO.getDeviceCodedClass());
				} else {
					ps.setNull(51, java.sql.Types.NUMERIC);
				}

				if (commonDTO.getDeviceAgencyClass() != null) {
					ps.setInt(52, commonDTO.getDeviceAgencyClass());
				} else {
					ps.setNull(52, java.sql.Types.NUMERIC);
				}

				if (commonDTO.getDeviceIagClass() != null) {
					ps.setInt(53, commonDTO.getDeviceIagClass());
				} else {
					ps.setNull(53, java.sql.Types.NUMERIC);
				}

				if (commonDTO.getDeviceAxles() != null) {
					ps.setInt(54, commonDTO.getDeviceAxles());
				} else {
					ps.setNull(54, java.sql.Types.NUMERIC);
				}

				if (commonDTO.getEtcAccountId() != null) {
					ps.setDouble(55, commonDTO.getEtcAccountId());
				} else {
					ps.setNull(55, java.sql.Types.NUMERIC);
				}

				if (commonDTO.getAccountAgencyId() != null) {
					ps.setDouble(56, commonDTO.getAccountAgencyId());
				} else {
					ps.setNull(56, java.sql.Types.NUMERIC);
				}

				if (commonDTO.getReadAviClass() != null) {
					ps.setDouble(57, commonDTO.getReadAviClass());
				} else {
					ps.setNull(57, java.sql.Types.NUMERIC);
				}

				if (commonDTO.getReadAviAxles() != null) {
					ps.setDouble(58, commonDTO.getReadAviAxles());
				} else {
					ps.setNull(58, java.sql.Types.NUMERIC);
				}

				if (commonDTO.getDeviceProgramStatus() != null) {
					ps.setString(59, commonDTO.getDeviceProgramStatus());
				} else {
					ps.setNull(59, java.sql.Types.NUMERIC);
				}

				if (commonDTO.getBufferReadFlag() != null) {
					ps.setString(60, commonDTO.getBufferReadFlag());
				} else {
					ps.setNull(60, java.sql.Types.NUMERIC);
				}

				if (commonDTO.getLaneDeviceStatus() != null) {
					ps.setInt(61, commonDTO.getLaneDeviceStatus());
				} else {
					ps.setNull(61, java.sql.Types.NUMERIC);
				}

				if (commonDTO.getPostDeviceStatus() != null) {
					ps.setInt(62, commonDTO.getPostDeviceStatus());
				} else {
					ps.setNull(62, java.sql.Types.NUMERIC);
				}

				if (commonDTO.getPreTxnBalance() != null) {
					ps.setLong(63, commonDTO.getPreTxnBalance());
				} else {
					ps.setNull(63, java.sql.Types.NUMERIC);
				}

				if (commonDTO.getPlanTypeId() != null) {
					ps.setInt(64, commonDTO.getPlanTypeId());
				} else {
					ps.setNull(64, java.sql.Types.NUMERIC);
				}

				/*
				 * if (commonDTO.getTxStatus() != null) { ps.setInt(65,
				 * commonDTO.getTxStatus()); } else { ps.setNull(65, java.sql.Types.NUMERIC); }
				 */

				if (commonDTO.getSpeedViolFlag() != null) {
					ps.setString(65, commonDTO.getSpeedViolFlag());
				} else {
					ps.setNull(65, java.sql.Types.NUMERIC);
				}

				if (commonDTO.getImageTaken() != null) {
					ps.setString(66, commonDTO.getImageTaken());
				} else {
					ps.setNull(66, java.sql.Types.VARCHAR);
				}

				if (commonDTO.getPlateCountry() != null) {
					ps.setString(67, commonDTO.getPlateCountry());
				} else {
					ps.setNull(67, java.sql.Types.VARCHAR);
				}

				if (commonDTO.getPlateState() != null) {
					ps.setString(68, commonDTO.getPlateState());
				} else {
					ps.setNull(68, java.sql.Types.VARCHAR);
				}

				if (commonDTO.getPlateNumber() != null) {
					ps.setString(69, commonDTO.getPlateNumber());
				} else {
					ps.setNull(69, java.sql.Types.VARCHAR);
				}

				if (commonDTO.getRevenueDate() != null) {
					ps.setDate(70, Date.valueOf(commonDTO.getRevenueDate()));
				} else {
					ps.setNull(70, java.sql.Types.DATE);
				}

				if (commonDTO.getPostedDate() != null) {
					ps.setDate(71, Date.valueOf(commonDTO.getPostedDate()));
				} else {
					ps.setNull(71, java.sql.Types.DATE);
				}

				if (commonDTO.getAtpFileId() != null) {
					ps.setInt(72, commonDTO.getAtpFileId());
				} else {
					ps.setNull(72, java.sql.Types.VARCHAR);
				}

				if (commonDTO.getIsReversed() != null) {
					ps.setString(73, commonDTO.getIsReversed());
				} else {
					ps.setNull(73, java.sql.Types.VARCHAR);
				}

				if (commonDTO.getCorrReasonId() != null) {
					ps.setFloat(74, commonDTO.getCorrReasonId());
				} else {
					ps.setNull(74, java.sql.Types.NUMERIC);
				}

				if (commonDTO.getReconDate() != null) {
					ps.setDate(75, Date.valueOf(commonDTO.getReconDate()));
				} else {
					ps.setNull(75, java.sql.Types.DATE);
				}

				if (commonDTO.getReconStatusInd() != null) {
					ps.setInt(76, commonDTO.getReconStatusInd());
				} else {
					ps.setNull(76, java.sql.Types.NUMERIC);
				}

				if (commonDTO.getReconSubCodeInd() != null) {
					ps.setInt(77, commonDTO.getReconSubCodeInd());
				} else {
					ps.setNull(77, java.sql.Types.NUMERIC);
				}

				if (commonDTO.getExternFileDate() != null) {
					ps.setDate(78, Date.valueOf(commonDTO.getExternFileDate()));
				} else {
					ps.setNull(78, java.sql.Types.DATE);
				}

				if (commonDTO.getMileage() != null) {
					ps.setInt(79, commonDTO.getMileage());
				} else {
					ps.setNull(79, java.sql.Types.NUMERIC);
				}

				if (commonDTO.getDeviceReadCount() != null) {
					ps.setInt(80, commonDTO.getDeviceReadCount());
				} else {
					ps.setNull(80, java.sql.Types.NUMERIC);
				}

				if (commonDTO.getDeviceWriteCount() != null) {
					ps.setInt(81, commonDTO.getDeviceWriteCount());
				} else {
					ps.setNull(81, java.sql.Types.NUMERIC);
				}

				if (commonDTO.getEntryDeviceReadCount() != null) {
					ps.setInt(82, commonDTO.getEntryDeviceReadCount());
				} else {
					ps.setNull(82, java.sql.Types.NUMERIC);
				}

				if (commonDTO.getEntryDeviceWriteCount() != null) {
					ps.setInt(83, commonDTO.getEntryDeviceWriteCount());
				} else {
					ps.setNull(83, java.sql.Types.NUMERIC);
				}

				if (commonDTO.getDepositId() != null) {
					ps.setString(84, commonDTO.getDepositId());
				} else {
					ps.setNull(84, java.sql.Types.NUMERIC);
				}

				if (commonDTO.getTxDate() != null) {
					ps.setDate(85, Date.valueOf(commonDTO.getTxDate()));
				} else {
					ps.setNull(85, java.sql.Types.DATE);
				}

				if (commonDTO.getCscLookupKey() != null) {
					ps.setString(86, commonDTO.getCscLookupKey());
				} else {
					ps.setNull(86, java.sql.Types.VARCHAR);
				}

				if (commonDTO.getUpdateTs() != null) {
					ps.setTimestamp(87, Timestamp.valueOf(timezoneconv.currentTime()));
				} else {
					ps.setNull(87, java.sql.Types.TIMESTAMP);
				}
				
				if (commonDTO.getReciprocityTrx() != null) {
					ps.setString(88, commonDTO.getReciprocityTrx());
				} else {
					ps.setNull(88, java.sql.Types.VARCHAR);
				}

				if (commonDTO.getTollAmount() != null) {
					ps.setDouble(89, commonDTO.getTollAmount());
				} else {
					ps.setNull(89, java.sql.Types.NUMERIC);
				}
				return ps;
			}
		};

		jdbcTemplate.update(psc);
		logger.info("Insert into QUEUE table ends for etc account id {}", commonDTO.getEtcAccountId());
		logger.info("Insert into QUEUE table sucessfull", commonDTO);

	}

}
