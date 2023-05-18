package com.conduent.tpms.ibts.away.tx.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.conduent.tpms.ibts.away.tx.config.LoadJpaQueries;
import com.conduent.tpms.ibts.away.tx.dao.AwayTransactionDao;
import com.conduent.tpms.ibts.away.tx.model.AwayTransaction;

/**
 * Away Transaction Dao Implementation
 * 
 * @author deepeshb
 *
 */
@Repository
public class AwayTransactionDaoImpl implements AwayTransactionDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private NamedParameterJdbcTemplate namedJdbcTemplate;

	private static final Logger logger = LoggerFactory.getLogger(AwayTransactionDaoImpl.class);

	@Override
	public void insert(AwayTransaction awayTransaction) {
		logger.info("Insert into Agency Tx Pending table starts for lane tx id {}", awayTransaction.getLaneTxId());

		String query = LoadJpaQueries.getQueryById("INSERT_INTO_T_AGENCY_TX_PENDING");

		PreparedStatementCreator psc = new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				final PreparedStatement ps = connection.prepareStatement(query);
				setLaneTxId(awayTransaction, ps);
				setTxExternRefNo(awayTransaction, ps);
				setTxSeqNumber(awayTransaction, ps);
				setExternFileId(awayTransaction, ps);
				setPlazaAgencyId(awayTransaction, ps);
				setPlazaId(awayTransaction, ps);
				setLaneId(awayTransaction, ps);
				setTxTimestamp(awayTransaction, ps);
				setTxModeSeq(awayTransaction, ps);

				setTxType(awayTransaction, ps);

				setTxSubType(awayTransaction, ps);

				setTollSystemType(awayTransaction, ps);

				setLaneMode(awayTransaction, ps);

				setLaneType(awayTransaction, ps);

				setLaneState(awayTransaction, ps);

				setLaneHealth(awayTransaction, ps);

				setCollectorId(awayTransaction, ps);

				setTourSegmentId(awayTransaction, ps);

				setEntryDataSource(awayTransaction, ps);

				setEntryLaneId(awayTransaction, ps);

				setEntryPlazaId(awayTransaction, ps);

				setEntryTimestamp(awayTransaction, ps);

				setEntryTxSeqNum(awayTransaction, ps);

				setEntryVehicleSpeed(awayTransaction, ps);

				setLaneTxStatus(awayTransaction, ps);

				setLaneTxType(awayTransaction, ps);

				setTollRevenueType(awayTransaction, ps);

				setActualClass(awayTransaction, ps);

				setActualAxles(awayTransaction, ps);

				setActualExtraAxles(awayTransaction, ps);

				setCollectorClass(awayTransaction, ps);

				setCollectorAxles(awayTransaction, ps);

				setPreclassClass(awayTransaction, ps);

				preclassAxles(awayTransaction, ps);

				postclassClass(awayTransaction, ps);

				postclassAxles(awayTransaction, ps);

				setForwardAxles(awayTransaction, ps);

				setReverseAxles(awayTransaction, ps);

				setEtcFareAmount(awayTransaction, ps);

				setPostedFareAmount(awayTransaction, ps);

				setCollectedAmount(awayTransaction, ps);

				setUnrealizedAmount(awayTransaction, ps);

				setIsDiscountable(awayTransaction, ps);

				setIsMedianFare(awayTransaction, ps);

				setIsPeak(awayTransaction, ps);

				setPriceScheduleId(awayTransaction, ps);

				setVehicleSpeed(awayTransaction, ps);

				setReceiptIssued(awayTransaction, ps);

				setDeviceNo(awayTransaction, ps);

				setAccountType(awayTransaction, ps);
				setDeviceCodedClass(awayTransaction, ps);

				setDeviceAgencyClass(awayTransaction, ps);

				setDeviceIagClass(awayTransaction, ps);

				setDeviceAxles(awayTransaction, ps);

				setEtcAccountId(awayTransaction, ps);

				setAccountAgencyId(awayTransaction, ps);

				setReadAviClass(awayTransaction, ps);

				setReadAviAxles(awayTransaction, ps);

				setDeviceProgramStatus(awayTransaction, ps);

				setBuffereReadFlag(awayTransaction, ps);

				setLaneDeviceStatus(awayTransaction, ps);

				setPostDeviceStatus(awayTransaction, ps);

				setPreTxnBalance(awayTransaction, ps);

				setPlanTypeId(awayTransaction, ps);

				setEtcTxStatus(awayTransaction, ps);

				setSpeedViolFlag(awayTransaction, ps);

				setImageTaken(awayTransaction, ps);

				setPlateCountry(awayTransaction, ps);

				setPlateState(awayTransaction, ps);

				setPlateNumber(awayTransaction, ps);

				setRevenueDate(awayTransaction, ps);

				setPostedDate(awayTransaction, ps);

				setATPFileId(awayTransaction, ps);

				setIsReserved(awayTransaction, ps);

				setCorrReasonId(awayTransaction, ps);

				setReconDate(awayTransaction, ps);

				setReconStatusInd(awayTransaction, ps);

				setReconSubCodeInd(awayTransaction, ps);

				setExternFileDate(awayTransaction, ps);

				setMileage(awayTransaction, ps);

				setDeviceReadCount(awayTransaction, ps);

				setDeviceWriteCount(awayTransaction, ps);

				entryDeviceReadCount(awayTransaction, ps);

				entryDeviceWriteCount(awayTransaction, ps);

				setDepositId(awayTransaction, ps);

				setTxDate(awayTransaction, ps);

				setCscLookupKey(awayTransaction, ps);

				setUpdateTs(awayTransaction, ps);

				setReciprocityTx(awayTransaction, ps);

				setExpectedRevenueAmount(awayTransaction, ps);

				setSourceSystem(awayTransaction, ps);

				setVideoFareAmount(awayTransaction, ps);
				setCashFareAmount(awayTransaction, ps);
				setTxStatus(awayTransaction, ps);
				return ps;
			}

		};

		jdbcTemplate.update(psc);
		logger.info("Insert into Agency Tx Pending table ends for lane tx id {}", awayTransaction.getLaneTxId());

	}

	private void setTxStatus(AwayTransaction awayTransaction, final PreparedStatement ps) throws SQLException {
		if (awayTransaction.getTxStatus() != null) {
			ps.setLong(94, awayTransaction.getTxStatus());
		} else {
			ps.setNull(94, java.sql.Types.NUMERIC);
		}
	}
	
	private void setVideoFareAmount(AwayTransaction awayTransaction, final PreparedStatement ps) throws SQLException {
		if (awayTransaction.getVideoFareAmount() != null) {
			ps.setDouble(93, awayTransaction.getVideoFareAmount());
		} else {
			ps.setNull(93, java.sql.Types.NUMERIC);
		}
	}

	private void setCashFareAmount(AwayTransaction awayTransaction, final PreparedStatement ps) throws SQLException {
		if (awayTransaction.getCashFareAmount() != null) {
			ps.setDouble(92, awayTransaction.getCashFareAmount());
		} else {
			ps.setNull(92, java.sql.Types.NUMERIC);
		}
	}

	private void setSourceSystem(AwayTransaction awayTransaction, PreparedStatement ps) throws SQLException {
		if (awayTransaction.getSourceSystem() != null) {
			ps.setInt(91, awayTransaction.getSourceSystem());
		} else {
			ps.setNull(91, java.sql.Types.NUMERIC);
		}

	}

	private void setExpectedRevenueAmount(AwayTransaction awayTransaction, final PreparedStatement ps)
			throws SQLException {
		if (awayTransaction.getExpectedRevenueAmount() != null) {
			ps.setDouble(90, awayTransaction.getExpectedRevenueAmount());
		} else {
			ps.setNull(90, java.sql.Types.NUMERIC);
		}
	}

	private void setReciprocityTx(AwayTransaction awayTransaction, final PreparedStatement ps) throws SQLException {
		if (awayTransaction.getReciprocityTrx() != null) {
			ps.setString(89, awayTransaction.getReciprocityTrx());
		} else {
			ps.setNull(89, java.sql.Types.VARCHAR);
		}
	}

	private void setUpdateTs(AwayTransaction awayTransaction, final PreparedStatement ps) throws SQLException {
		if (awayTransaction.getUpdateTs() != null) {
			ps.setTimestamp(88, Timestamp.valueOf(LocalDateTime.now()));
		} else {
			ps.setNull(88, java.sql.Types.TIMESTAMP);
		}
	}

	private void setTxDate(AwayTransaction awayTransaction, final PreparedStatement ps) throws SQLException {
		if (!StringUtils.isBlank(awayTransaction.getTxDate())) {
			ps.setDate(86, Date.valueOf(awayTransaction.getTxDate()));
		} else {
			ps.setNull(86, java.sql.Types.DATE);
		}
	}

	private void setCscLookupKey(AwayTransaction awayTransaction, final PreparedStatement ps) throws SQLException {
		if (!StringUtils.isBlank(awayTransaction.getCscLookupKey())) {
			ps.setString(87, awayTransaction.getCscLookupKey());
		} else {
			ps.setNull(87, java.sql.Types.VARCHAR);
		}
	}

	private void setDepositId(AwayTransaction awayTransaction, final PreparedStatement ps) throws SQLException {
		if (!StringUtils.isBlank(awayTransaction.getDepositId())) {
			ps.setString(85, awayTransaction.getDepositId());
		} else {
			ps.setNull(85, java.sql.Types.VARCHAR);
		}
	}

	private void entryDeviceWriteCount(AwayTransaction awayTransaction, final PreparedStatement ps)
			throws SQLException {
		if (awayTransaction.getEntryDeviceWriteCount() != null) {
			ps.setInt(84, awayTransaction.getEntryDeviceWriteCount());
		} else {
			ps.setNull(84, java.sql.Types.NUMERIC);
		}
	}

	private void entryDeviceReadCount(AwayTransaction awayTransaction, final PreparedStatement ps) throws SQLException {
		if (awayTransaction.getEntryDeviceReadCount() != null) {
			ps.setInt(83, awayTransaction.getEntryDeviceReadCount());
		} else {
			ps.setNull(83, java.sql.Types.NUMERIC);
		}
	}

	private void setDeviceWriteCount(AwayTransaction awayTransaction, final PreparedStatement ps) throws SQLException {
		if (awayTransaction.getDeviceWriteCount() != null) {
			ps.setInt(82, awayTransaction.getDeviceWriteCount());
		} else {
			ps.setNull(82, java.sql.Types.NUMERIC);
		}
	}

	private void setDeviceReadCount(AwayTransaction awayTransaction, final PreparedStatement ps) throws SQLException {
		if (awayTransaction.getDeviceReadCount() != null) {
			ps.setInt(81, awayTransaction.getDeviceReadCount());
		} else {
			ps.setNull(81, java.sql.Types.NUMERIC);
		}
	}

	private void setMileage(AwayTransaction awayTransaction, final PreparedStatement ps) throws SQLException {
		if (awayTransaction.getMileage() != null) {
			ps.setInt(80, awayTransaction.getMileage());
		} else {
			ps.setNull(80, java.sql.Types.NUMERIC);
		}
	}

	private void setExternFileDate(AwayTransaction awayTransaction, final PreparedStatement ps) throws SQLException {
		if (awayTransaction.getExternFileDate() != null) {
			ps.setDate(79, Date.valueOf(awayTransaction.getExternFileDate()));
		} else {
			ps.setNull(79, java.sql.Types.DATE);
		}
	}

	private void setReconSubCodeInd(AwayTransaction awayTransaction, final PreparedStatement ps) throws SQLException {
		if (awayTransaction.getReconSubCodeInd() != null) {
			ps.setInt(78, awayTransaction.getReconSubCodeInd());
		} else {
			ps.setNull(78, java.sql.Types.NUMERIC);
		}
	}

	private void setReconStatusInd(AwayTransaction awayTransaction, final PreparedStatement ps) throws SQLException {
		if (awayTransaction.getReconStatusInd() != null) {
			ps.setInt(77, awayTransaction.getReconStatusInd());
		} else {
			ps.setNull(77, java.sql.Types.NUMERIC);
		}
	}

	private void setReconDate(AwayTransaction awayTransaction, final PreparedStatement ps) throws SQLException {
		if (awayTransaction.getReconDate() != null) {
			ps.setDate(76, Date.valueOf(awayTransaction.getReconDate()));
		} else {
			ps.setNull(76, java.sql.Types.DATE);
		}
	}

	private void setCorrReasonId(AwayTransaction awayTransaction, final PreparedStatement ps) throws SQLException {
		if (awayTransaction.getCorrReasonId() != null) {
			ps.setInt(75, awayTransaction.getCorrReasonId());
		} else {
			ps.setNull(75, java.sql.Types.NUMERIC);
		}
	}

	private void setIsReserved(AwayTransaction awayTransaction, final PreparedStatement ps) throws SQLException {
		if (!StringUtils.isBlank(awayTransaction.getIsReversed())) {
			ps.setString(74, awayTransaction.getIsReversed());
		} else {
			ps.setNull(74, java.sql.Types.VARCHAR);
		}
	}

	private void setATPFileId(AwayTransaction awayTransaction, final PreparedStatement ps) throws SQLException {
		if (awayTransaction.getAtpFileId() != null) {
			ps.setLong(73, awayTransaction.getAtpFileId());
		} else {
			ps.setNull(73, java.sql.Types.NUMERIC);
		}
	}

	private void setPostedDate(AwayTransaction awayTransaction, final PreparedStatement ps) throws SQLException {
		if (awayTransaction.getPostedDate() != null) {
			ps.setDate(72, Date.valueOf(awayTransaction.getPostedDate()));
		} else {
			ps.setNull(72, java.sql.Types.DATE);
		}
	}

	private void setRevenueDate(AwayTransaction awayTransaction, final PreparedStatement ps) throws SQLException {
		if (awayTransaction.getRevenueDate() != null) {
			ps.setDate(71, Date.valueOf(awayTransaction.getRevenueDate()));
		} else {
			ps.setNull(71, java.sql.Types.DATE);
		}
	}

	private void setPlateNumber(AwayTransaction awayTransaction, final PreparedStatement ps) throws SQLException {
		if (!StringUtils.isBlank(awayTransaction.getPlateNumber())) {
			ps.setString(70, awayTransaction.getPlateNumber());
		} else {
			ps.setNull(70, java.sql.Types.VARCHAR);
		}
	}

	private void setPlateState(AwayTransaction awayTransaction, final PreparedStatement ps) throws SQLException {
		if (!StringUtils.isBlank(awayTransaction.getPlateState())) {
			ps.setString(69, awayTransaction.getPlateState());
		} else {
			ps.setNull(69, java.sql.Types.VARCHAR);
		}
	}

	private void setPlateCountry(AwayTransaction awayTransaction, final PreparedStatement ps) throws SQLException {
		if (!StringUtils.isBlank(awayTransaction.getPlateCountry())) {
			ps.setString(68, awayTransaction.getPlateCountry());
		} else {
			ps.setNull(68, java.sql.Types.VARCHAR);
		}
	}

	private void setImageTaken(AwayTransaction awayTransaction, final PreparedStatement ps) throws SQLException {
		if (!StringUtils.isBlank(awayTransaction.getImageTaken())) {
			ps.setString(67, awayTransaction.getImageTaken());
		} else {
			ps.setNull(67, java.sql.Types.VARCHAR);
		}
	}

	private void setSpeedViolFlag(AwayTransaction awayTransaction, final PreparedStatement ps) throws SQLException {
		if (!StringUtils.isBlank(awayTransaction.getSpeedViolFlag())) {
			ps.setString(66, awayTransaction.getSpeedViolFlag());
		} else {
			ps.setNull(66, java.sql.Types.VARCHAR);
		}
	}

	private void setEtcTxStatus(AwayTransaction awayTransaction, final PreparedStatement ps) throws SQLException {
		if (awayTransaction.getEtcTxStatus() != null) {
			ps.setInt(65, awayTransaction.getEtcTxStatus());
		} else {
			ps.setNull(65, java.sql.Types.NUMERIC);
		}
	}

	private void setPlanTypeId(AwayTransaction awayTransaction, final PreparedStatement ps) throws SQLException {
		if (awayTransaction.getPlanTypeId() != null) {
			ps.setInt(64, awayTransaction.getPlanTypeId());
		} else {
			ps.setNull(64, java.sql.Types.NUMERIC);
		}
	}

	private void setPreTxnBalance(AwayTransaction awayTransaction, final PreparedStatement ps) throws SQLException {
		if (awayTransaction.getPreTxnBalance() != null) {
			ps.setDouble(63, awayTransaction.getPreTxnBalance());
		} else {
			ps.setNull(63, java.sql.Types.NUMERIC);
		}
	}

	private void setPostDeviceStatus(AwayTransaction awayTransaction, final PreparedStatement ps) throws SQLException {
		if (awayTransaction.getPostDeviceStatus() != null) {
			ps.setInt(62, awayTransaction.getPostDeviceStatus());
		} else {
			ps.setNull(62, java.sql.Types.NUMERIC);
		}
	}

	private void setLaneDeviceStatus(AwayTransaction awayTransaction, final PreparedStatement ps) throws SQLException {
		if (awayTransaction.getLaneDeviceStatus() != null) {
			ps.setLong(61, awayTransaction.getLaneDeviceStatus());
		} else {
			ps.setNull(61, java.sql.Types.NUMERIC);
		}
	}

	private void setBuffereReadFlag(AwayTransaction awayTransaction, final PreparedStatement ps) throws SQLException {
		if (!StringUtils.isBlank(awayTransaction.getBufferedReadFlag())) {
			ps.setString(60, awayTransaction.getBufferedReadFlag());
		} else {
			ps.setNull(60, java.sql.Types.VARCHAR);
		}
	}

	private void setDeviceProgramStatus(AwayTransaction awayTransaction, final PreparedStatement ps)
			throws SQLException {
		if (!StringUtils.isBlank(awayTransaction.getDeviceProgramStatus())) {
			ps.setString(59, awayTransaction.getDeviceProgramStatus());
		} else {
			ps.setNull(59, java.sql.Types.VARCHAR);
		}
	}

	private void setReadAviAxles(AwayTransaction awayTransaction, final PreparedStatement ps) throws SQLException {
		if (awayTransaction.getReadAviAxles() != null) {
			ps.setDouble(58, awayTransaction.getReadAviAxles());
		} else {
			ps.setNull(58, java.sql.Types.NUMERIC);
		}
	}

	private void setReadAviClass(AwayTransaction awayTransaction, final PreparedStatement ps) throws SQLException {
		if (awayTransaction.getReadAviClass() != null) {
			ps.setDouble(57, awayTransaction.getReadAviClass());
		} else {
			ps.setNull(57, java.sql.Types.NUMERIC);
		}
	}

	private void setAccountAgencyId(AwayTransaction awayTransaction, final PreparedStatement ps) throws SQLException {
		if (awayTransaction.getAccountAgencyId() != null) {
			ps.setDouble(56, awayTransaction.getAccountAgencyId());
		} else {
			ps.setNull(56, java.sql.Types.NUMERIC);
		}
	}

	private void setEtcAccountId(AwayTransaction awayTransaction, final PreparedStatement ps) throws SQLException {
		if (awayTransaction.getEtcAccountId() != null) {
			ps.setDouble(55, awayTransaction.getEtcAccountId());
		} else {
			ps.setNull(55, java.sql.Types.NUMERIC);
		}
	}

	private void setDeviceAxles(AwayTransaction awayTransaction, final PreparedStatement ps) throws SQLException {
		if (awayTransaction.getDeviceAxles() != null) {
			ps.setInt(54, awayTransaction.getDeviceAxles());
		} else {
			ps.setNull(54, java.sql.Types.NUMERIC);
		}
	}

	private void setDeviceIagClass(AwayTransaction awayTransaction, final PreparedStatement ps) throws SQLException {
		if (awayTransaction.getDeviceIagClass() != null) {
			ps.setInt(53, awayTransaction.getDeviceIagClass());
		} else {
			ps.setNull(53, java.sql.Types.NUMERIC);
		}
	}

	private void setDeviceAgencyClass(AwayTransaction awayTransaction, final PreparedStatement ps) throws SQLException {
		if (awayTransaction.getDeviceAgencyClass() != null) {
			ps.setInt(52, awayTransaction.getDeviceAgencyClass());
		} else {
			ps.setNull(52, java.sql.Types.NUMERIC);
		}
	}

	private void setDeviceCodedClass(AwayTransaction awayTransaction, final PreparedStatement ps) throws SQLException {
		if (awayTransaction.getDeviceCodedClass() != null) {
			ps.setInt(51, awayTransaction.getDeviceCodedClass());
		} else {
			ps.setNull(51, java.sql.Types.NUMERIC);
		}
	}

	private void setAccountType(AwayTransaction awayTransaction, final PreparedStatement ps) throws SQLException {
		if (awayTransaction.getAccountType() != null) {
			ps.setInt(50, awayTransaction.getAccountType());
		} else {
			ps.setNull(50, java.sql.Types.NUMERIC);
		}
	}

	private void setDeviceNo(AwayTransaction awayTransaction, final PreparedStatement ps) throws SQLException {
		if (!StringUtils.isBlank(awayTransaction.getDeviceNo())) {
			ps.setString(49, awayTransaction.getDeviceNo());
		} else {
			ps.setNull(49, java.sql.Types.VARCHAR);
		}
	}

	private void setReceiptIssued(AwayTransaction awayTransaction, final PreparedStatement ps) throws SQLException {
		if (awayTransaction.getReceiptIssued() != null) {
			ps.setInt(48, awayTransaction.getReceiptIssued());
		} else {
			ps.setNull(48, java.sql.Types.NUMERIC);
		}
	}

	private void setVehicleSpeed(AwayTransaction awayTransaction, final PreparedStatement ps) throws SQLException {
		if (awayTransaction.getVehicleSpeed() != null) {
			ps.setInt(47, awayTransaction.getVehicleSpeed());
		} else {
			ps.setNull(47, java.sql.Types.NUMERIC);
		}
	}

	private void setPriceScheduleId(AwayTransaction awayTransaction, final PreparedStatement ps) throws SQLException {
		if (awayTransaction.getPriceScheduleId() != null) {
			ps.setInt(46, awayTransaction.getPriceScheduleId());
		} else {
			ps.setNull(46, java.sql.Types.NUMERIC);
		}
	}

	private void setIsPeak(AwayTransaction awayTransaction, final PreparedStatement ps) throws SQLException {
		if (!StringUtils.isBlank(awayTransaction.getIsPeak())) {
			ps.setString(45, awayTransaction.getIsPeak());
		} else {
			ps.setNull(45, java.sql.Types.VARCHAR);
		}
	}

	private void setIsMedianFare(AwayTransaction awayTransaction, final PreparedStatement ps) throws SQLException {
		if (!StringUtils.isBlank(awayTransaction.getIsMedianFare())) {
			ps.setString(44, awayTransaction.getIsMedianFare());
		} else {
			ps.setNull(44, java.sql.Types.VARCHAR);
		}
	}

	private void setIsDiscountable(AwayTransaction awayTransaction, final PreparedStatement ps) throws SQLException {
		if (!StringUtils.isBlank(awayTransaction.getIsDiscountable())) {
			ps.setString(43, awayTransaction.getIsDiscountable());
		} else {
			ps.setNull(43, java.sql.Types.VARCHAR);
		}
	}

	private void setUnrealizedAmount(AwayTransaction awayTransaction, final PreparedStatement ps) throws SQLException {
		if (awayTransaction.getUnrealizedAmount() != null) {
			ps.setDouble(42, awayTransaction.getUnrealizedAmount());
		} else {
			ps.setNull(42, java.sql.Types.NUMERIC);
		}
	}

	private void setCollectedAmount(AwayTransaction awayTransaction, final PreparedStatement ps) throws SQLException {
		if (awayTransaction.getCollectedAmount() != null) {
			ps.setDouble(41, awayTransaction.getCollectedAmount());
		} else {
			ps.setNull(41, java.sql.Types.NUMERIC);
		}
	}

	private void setPostedFareAmount(AwayTransaction awayTransaction, final PreparedStatement ps) throws SQLException {
		if (awayTransaction.getPostedFareAmount() != null) {
			ps.setDouble(40, awayTransaction.getPostedFareAmount());
		} else {
			ps.setNull(40, java.sql.Types.NUMERIC);
		}
	}

	private void setEtcFareAmount(AwayTransaction awayTransaction, final PreparedStatement ps) throws SQLException {
		if (awayTransaction.getEtcFareAmount() != null) {
			ps.setDouble(39, awayTransaction.getEtcFareAmount());
		} else {
			ps.setNull(39, java.sql.Types.NUMERIC);
		}
	}

	private void setReverseAxles(AwayTransaction awayTransaction, final PreparedStatement ps) throws SQLException {
		if (awayTransaction.getReverseAxles() != null) {
			ps.setInt(38, awayTransaction.getReverseAxles());
		} else {
			ps.setNull(38, java.sql.Types.NUMERIC);
		}
	}

	private void setForwardAxles(AwayTransaction awayTransaction, final PreparedStatement ps) throws SQLException {
		if (awayTransaction.getForwardAxles() != null) {
			ps.setInt(37, awayTransaction.getForwardAxles());
		} else {
			ps.setNull(37, java.sql.Types.NUMERIC);
		}
	}

	private void postclassAxles(AwayTransaction awayTransaction, final PreparedStatement ps) throws SQLException {
		if (awayTransaction.getPostClassAxles() != null) {
			ps.setInt(36, awayTransaction.getPostClassAxles());
		} else {
			ps.setNull(36, java.sql.Types.NUMERIC);
		}
	}

	private void postclassClass(AwayTransaction awayTransaction, final PreparedStatement ps) throws SQLException {
		if (awayTransaction.getPostClassClass() != null) {
			ps.setInt(35, awayTransaction.getPostClassClass());
		} else {
			ps.setNull(35, java.sql.Types.NUMERIC);
		}
	}

	private void preclassAxles(AwayTransaction awayTransaction, final PreparedStatement ps) throws SQLException {
		if (awayTransaction.getPreClassAxles() != null) {
			ps.setInt(34, awayTransaction.getPreClassAxles());
		} else {
			ps.setNull(34, java.sql.Types.NUMERIC);
		}
	}

	private void setPreclassClass(AwayTransaction awayTransaction, final PreparedStatement ps) throws SQLException {
		if (awayTransaction.getPreClassClass() != null) {
			ps.setInt(33, awayTransaction.getPreClassClass());
		} else {
			ps.setNull(33, java.sql.Types.NUMERIC);
		}
	}

	private void setCollectorAxles(AwayTransaction awayTransaction, final PreparedStatement ps) throws SQLException {
		if (awayTransaction.getCollectorAxles() != null) {
			ps.setInt(32, awayTransaction.getCollectorAxles());
		} else {
			ps.setNull(32, java.sql.Types.NUMERIC);
		}
	}

	private void setCollectorClass(AwayTransaction awayTransaction, final PreparedStatement ps) throws SQLException {
		if (awayTransaction.getCollectorClass() != null) {
			ps.setInt(31, awayTransaction.getCollectorClass());
		} else {
			ps.setNull(31, java.sql.Types.NUMERIC);
		}
	}

	private void setActualExtraAxles(AwayTransaction awayTransaction, final PreparedStatement ps) throws SQLException {
		if (awayTransaction.getActualExtraAxles() != null) {
			ps.setInt(30, awayTransaction.getActualExtraAxles());
		} else {
			ps.setNull(30, java.sql.Types.NUMERIC);
		}
	}

	private void setActualAxles(AwayTransaction awayTransaction, final PreparedStatement ps) throws SQLException {
		if (awayTransaction.getActualAxles() != null) {
			ps.setInt(29, awayTransaction.getActualAxles());
		} else {
			ps.setNull(29, java.sql.Types.NUMERIC);
		}
	}

	private void setActualClass(AwayTransaction awayTransaction, final PreparedStatement ps) throws SQLException {
		if (awayTransaction.getActualClass() != null) {
			ps.setLong(28, awayTransaction.getActualClass());
		} else {
			ps.setNull(28, java.sql.Types.NUMERIC);
		}
	}

	private void setTollRevenueType(AwayTransaction awayTransaction, final PreparedStatement ps) throws SQLException {
		if (awayTransaction.getTollRevenueType() != null) {
			ps.setInt(27, awayTransaction.getTollRevenueType());
		} else {
			ps.setNull(27, java.sql.Types.NUMERIC);
		}
	}

	private void setLaneTxType(AwayTransaction awayTransaction, final PreparedStatement ps) throws SQLException {
		if (awayTransaction.getLanetxType() != null) {
			ps.setLong(26, awayTransaction.getLanetxType());
		} else {
			ps.setNull(26, java.sql.Types.NUMERIC);
		}
	}

	private void setLaneTxStatus(AwayTransaction awayTransaction, final PreparedStatement ps) throws SQLException {
		if (awayTransaction.getLaneTxStatus() != null) {
			ps.setLong(25, awayTransaction.getLaneTxStatus());
		} else {
			ps.setNull(25, java.sql.Types.NUMERIC);
		}
	}

	private void setEntryVehicleSpeed(AwayTransaction awayTransaction, final PreparedStatement ps) throws SQLException {
		if (awayTransaction.getEntryVehicleSpeed() != null) {
			ps.setLong(24, awayTransaction.getEntryVehicleSpeed());
		} else {
			ps.setNull(24, java.sql.Types.NUMERIC);
		}
	}

	private void setEntryTxSeqNum(AwayTransaction awayTransaction, final PreparedStatement ps) throws SQLException {
		if (awayTransaction.getEntryTxSeqNumber() != null) {
			ps.setLong(23, awayTransaction.getEntryTxSeqNumber());
		} else {
			ps.setNull(23, java.sql.Types.NUMERIC);
		}
	}

	private void setEntryTimestamp(AwayTransaction awayTransaction, final PreparedStatement ps) throws SQLException {
		if (!StringUtils.isBlank(awayTransaction.getEntryTimestamp())) {
//			ps.setTimestamp(22, Timestamp.valueOf(awayTransaction.getEntryTimestamp()));
			ps.setObject(22, OffsetDateTime.parse(awayTransaction.getEntryTimestamp(), DateTimeFormatter.ISO_OFFSET_DATE_TIME));
			//ps.setObject(22, awayTransaction.getEntryTimestamp());
		} else {
			ps.setNull(22, java.sql.Types.TIMESTAMP);
		}
	}

	private void setEntryPlazaId(AwayTransaction awayTransaction, final PreparedStatement ps) throws SQLException {
		if (awayTransaction.getEntryPlazaId() != null) {
			ps.setInt(21, awayTransaction.getEntryPlazaId());
		} else {
			ps.setNull(21, java.sql.Types.NUMERIC);
		}
	}

	private void setEntryLaneId(AwayTransaction awayTransaction, final PreparedStatement ps) throws SQLException {
		if (awayTransaction.getEntryLaneId() != null) {
			ps.setInt(20, awayTransaction.getEntryLaneId());
		} else {
			ps.setNull(20, java.sql.Types.NUMERIC);
		}
	}

	private void setEntryDataSource(AwayTransaction awayTransaction, final PreparedStatement ps) throws SQLException {
		if (!StringUtils.isBlank(awayTransaction.getEntryDataSource())) {
			ps.setString(19, awayTransaction.getEntryDataSource());
		} else {
			ps.setNull(19, java.sql.Types.VARCHAR);
		}
	}

	private void setTourSegmentId(AwayTransaction awayTransaction, final PreparedStatement ps) throws SQLException {
		if (awayTransaction.getTourSegmentId() != null) {
			ps.setLong(18, awayTransaction.getTourSegmentId());
		} else {
			ps.setNull(18, java.sql.Types.NUMERIC);
		}
	}

	private void setCollectorId(AwayTransaction awayTransaction, final PreparedStatement ps) throws SQLException {
		if (awayTransaction.getCollectorId() != null) {
			ps.setLong(17, awayTransaction.getCollectorId());
		} else {
			ps.setNull(17, java.sql.Types.NUMERIC);
		}
	}

	private void setLaneHealth(AwayTransaction awayTransaction, final PreparedStatement ps) throws SQLException {
		if (awayTransaction.getLaneHealth() != null) {
			ps.setInt(16, awayTransaction.getLaneHealth());
		} else {
			ps.setNull(16, java.sql.Types.NUMERIC);
		}
	}

	private void setLaneState(AwayTransaction awayTransaction, final PreparedStatement ps) throws SQLException {
		if (awayTransaction.getLaneState() != null) {
			ps.setLong(15, awayTransaction.getLaneState());
		} else {
			ps.setNull(15, java.sql.Types.NUMERIC);
		}
	}

	private void setLaneType(AwayTransaction awayTransaction, final PreparedStatement ps) throws SQLException {
		if (awayTransaction.getLaneType() != null) {
			ps.setLong(14, awayTransaction.getLaneType());
		} else {
			ps.setNull(14, java.sql.Types.NUMERIC);
		}
	}

	private void setLaneMode(AwayTransaction awayTransaction, final PreparedStatement ps) throws SQLException {
		if (awayTransaction.getLaneMode() != null) {
			ps.setLong(13, awayTransaction.getLaneMode());
		} else {
			ps.setNull(13, java.sql.Types.NUMERIC);
		}
	}

	private void setTollSystemType(AwayTransaction awayTransaction, final PreparedStatement ps) throws SQLException {
		if (!StringUtils.isBlank(awayTransaction.getTollSystemType())) {
			ps.setString(12, awayTransaction.getTollSystemType());
		} else {
			ps.setNull(12, java.sql.Types.VARCHAR);
		}
	}

	private void setTxSubType(AwayTransaction awayTransaction, final PreparedStatement ps) throws SQLException {
		if (!StringUtils.isBlank(awayTransaction.getTxSubType())) {
			ps.setString(11, awayTransaction.getTxSubType());
		} else {
			ps.setNull(11, java.sql.Types.VARCHAR);
		}
	}

	private void setTxType(AwayTransaction awayTransaction, final PreparedStatement ps) throws SQLException {
		if (!StringUtils.isBlank(awayTransaction.getTxType())) {
			ps.setString(10, awayTransaction.getTxType());
		} else {
			ps.setNull(10, java.sql.Types.VARCHAR);
		}
	}

	private void setTxModeSeq(AwayTransaction awayTransaction, final PreparedStatement ps) throws SQLException {
		if (awayTransaction.getTxModSeq() != null) {
			ps.setInt(9, awayTransaction.getTxModSeq());
		} else {
			ps.setNull(9, java.sql.Types.NUMERIC);
		}
	}

	private void setTxTimestamp(AwayTransaction awayTransaction, final PreparedStatement ps) throws SQLException {
		if (!StringUtils.isBlank(awayTransaction.getTxTimestamp())) {
//			ps.setTimestamp(8, Timestamp.valueOf(awayTransaction.getTxTimestamp()));
				ps.setObject(8, OffsetDateTime.parse(awayTransaction.getTxTimestamp(), DateTimeFormatter.ISO_OFFSET_DATE_TIME));
			//ps.setObject(8, awayTransaction.getTxTimestamp());
		} else {
			ps.setNull(8, java.sql.Types.TIMESTAMP);
		}
	}

	private void setLaneId(AwayTransaction awayTransaction, final PreparedStatement ps) throws SQLException {
		if (awayTransaction.getLaneId() != null) {
			ps.setInt(7, awayTransaction.getLaneId());
		} else {
			ps.setNull(7, java.sql.Types.NUMERIC);
		}
	}

	private void setPlazaId(AwayTransaction awayTransaction, final PreparedStatement ps) throws SQLException {
		if (awayTransaction.getPlazaId() != null) {
			ps.setLong(6, awayTransaction.getPlazaId());
		} else {
			ps.setNull(6, java.sql.Types.NUMERIC);
		}
	}

	private void setPlazaAgencyId(AwayTransaction awayTransaction, final PreparedStatement ps) throws SQLException {
		if (awayTransaction.getPlazaAgencyId() != null) {
			ps.setInt(5, awayTransaction.getPlazaAgencyId());
		} else {
			ps.setNull(5, java.sql.Types.NUMERIC);
		}
	}

	private void setExternFileId(AwayTransaction awayTransaction, final PreparedStatement ps) throws SQLException {
		if (awayTransaction.getExternFileId() != null) {
			ps.setLong(4, awayTransaction.getExternFileId());
		} else {
			ps.setNull(4, java.sql.Types.NUMERIC);
		}
	}

	private void setTxSeqNumber(AwayTransaction awayTransaction, final PreparedStatement ps) throws SQLException {
		if (awayTransaction.getTxSeqNumber() != null) {
			ps.setLong(3, awayTransaction.getTxSeqNumber());
		} else {
			ps.setNull(3, java.sql.Types.NUMERIC);
		}
	}

	private void setTxExternRefNo(AwayTransaction awayTransaction, final PreparedStatement ps) throws SQLException {
		if (!StringUtils.isBlank(awayTransaction.getTxExternRefNo())) {
			ps.setString(2, awayTransaction.getTxExternRefNo());
		} else {
			ps.setNull(2, java.sql.Types.VARCHAR);
		}
	}

	private void setLaneTxId(AwayTransaction awayTransaction, final PreparedStatement ps) throws SQLException {
		if (awayTransaction.getLaneTxId() != null) {
			ps.setLong(1, awayTransaction.getLaneTxId());
		} else {
			ps.setNull(1, java.sql.Types.NUMERIC);
		}
	}

	@Override
	public AwayTransaction checkLaneTxId(Long laneTxId) {
		String query = LoadJpaQueries.getQueryById("GET_LANE_TX_ID");
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue("LANE_TX_ID", laneTxId);
		if (query != null) {
			List<AwayTransaction> list = namedJdbcTemplate.query(query, paramSource,
					BeanPropertyRowMapper.newInstance(AwayTransaction.class));
			if (!list.isEmpty()) {
				return list.get(0);
			}
		}
		return null;
	}
}
