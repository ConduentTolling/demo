package com.conduent.tpms.qatp.classification.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.conduent.app.timezone.utility.TimeZoneConv;
import com.conduent.tpms.qatp.classification.config.LoadJpaQueries;
import com.conduent.tpms.qatp.classification.constants.QatpClassificationConstant;
import com.conduent.tpms.qatp.classification.dao.ComputeTollDao;
import com.conduent.tpms.qatp.classification.dto.CommonClassificationDto;
import com.conduent.tpms.qatp.classification.dto.TUnmatchedTx;
import com.conduent.tpms.qatp.classification.dto.TollScheduleDto;
import com.conduent.tpms.qatp.classification.dto.TransactionDto;
import com.conduent.tpms.qatp.classification.model.Agency;
import com.conduent.tpms.qatp.classification.model.AtpFileIdObject;
import com.conduent.tpms.qatp.classification.model.TollPostLimit;
import com.conduent.tpms.qatp.classification.model.TollPriceSchedule;
import com.google.common.base.Stopwatch;

/**
 * Compute Toll Dao Implementation
 *
 */
@Repository
public class ComputeTollDaoImpl implements ComputeTollDao {

	@Autowired
	private NamedParameterJdbcTemplate namedJdbcTemplate;

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	TimeZoneConv timeZoneConv;

	private static final Logger logger = LoggerFactory.getLogger(ComputeTollDaoImpl.class);

	/**
	 * Get Price Schedule
	 */
	@Override
	public TollPriceSchedule getPriceSchedule(LocalDate effectiveDate, LocalDate expiryDate, String daysInd,
			Integer agencyId) {
		String queryToCheckFile = LoadJpaQueries.getQueryById(QatpClassificationConstant.GET_PRICING_SCHEDULE);
		String tempEffectiveDate = effectiveDate != null ? String.valueOf(effectiveDate) : null;
		String tempExpiryDate = expiryDate != null ? String.valueOf(expiryDate) : null;

		List<TollPriceSchedule> list = jdbcTemplate.query(queryToCheckFile,
				new BeanPropertyRowMapper<TollPriceSchedule>(TollPriceSchedule.class), tempEffectiveDate,
				tempExpiryDate, daysInd, agencyId);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Get Toll Amount
	 */
	@Override
	public TollScheduleDto getTollAmount(LocalDate txDate, Integer plazaId, Integer entryPlazaId, Integer vehicleClass,
			Integer revenueTypeId, Long planTypeId, Integer priceScheduleId) {
		String queryToCheckFile = LoadJpaQueries.getQueryById(QatpClassificationConstant.GET_TOLL_PRICES);
		String temptxDate = txDate != null ? String.valueOf(txDate) : null;
		List<TollScheduleDto> list = jdbcTemplate.query(queryToCheckFile,
				new BeanPropertyRowMapper<TollScheduleDto>(TollScheduleDto.class), temptxDate, temptxDate, plazaId,
				entryPlazaId!=null?entryPlazaId:0, vehicleClass, revenueTypeId, planTypeId, priceScheduleId);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Insert into Queue table
	 */
	@Override
	public void insertInToHomeEtcQueue(CommonClassificationDto commonDTO) {
		logger.info("Insert into Home etc Queue table starts for lane tx id {}", commonDTO.getLaneTxId());

		String query = LoadJpaQueries.getQueryById(QatpClassificationConstant.INSERT_INTO_T_HOME_ETC_QUEUE);

		PreparedStatementCreator psc = new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				final PreparedStatement ps = connection.prepareStatement(query);
				setLaneTxId(commonDTO, ps);
				setTxExternRefNo(commonDTO, ps);
				setTxSeqNumber(commonDTO, ps);
				setExternFileId(commonDTO, ps);
				setPlazaAgencyId(commonDTO, ps);
				setPlazaId(commonDTO, ps);
				setLaneId(commonDTO, ps);
				setTxTimestamp(commonDTO, ps);
				setTxModeSeq(commonDTO, ps);

				setTxType(commonDTO, ps);

				setTxSubType(commonDTO, ps);

				setTollSystemType(commonDTO, ps);

				setLaneMode(commonDTO, ps);

				setLaneType(commonDTO, ps);

				setLaneState(commonDTO, ps);

				setLaneHealth(commonDTO, ps);

				setCollectorId(commonDTO, ps);

				setTourSegmentId(commonDTO, ps);

				setEntryDataSource(commonDTO, ps);

				setEntryLaneId(commonDTO, ps);

				setEntryPlazaId(commonDTO, ps);

				setEntryTimestamp(commonDTO, ps);

				setEntryTxSeqNum(commonDTO, ps);

				setEntryVehicleSpeed(commonDTO, ps);

				setLaneTxStatus(commonDTO, ps);

				setLaneTxType(commonDTO, ps);

				setTollRevenueType(commonDTO, ps);

				setActualClass(commonDTO, ps);

				setActualAxles(commonDTO, ps);

				setActualExtraAxles(commonDTO, ps);

				setCollectorClass(commonDTO, ps);

				setCollectorAxles(commonDTO, ps);

				setPreclassClass(commonDTO, ps);

				preclassAxles(commonDTO, ps);

				postclassClass(commonDTO, ps);

				postclassAxles(commonDTO, ps);

				setForwardAxles(commonDTO, ps);

				setReverseAxles(commonDTO, ps);

				setEtcFareAmount(commonDTO, ps);

				setPostedFareAmount(commonDTO, ps);

				setCollectedAmount(commonDTO, ps);

				setUnrealizedAmount(commonDTO, ps);

				setIsDiscountable(commonDTO, ps);

				setIsMedianFare(commonDTO, ps);

				setIsPeak(commonDTO, ps);

				setPriceScheduleId(commonDTO, ps);

				setVehicleSpeed(commonDTO, ps);

				setReceiptIssued(commonDTO, ps);

				setDeviceNo(commonDTO, ps);

				setAccountType(commonDTO, ps);
				setDeviceCodedClass(commonDTO, ps);

				setDeviceAgencyClass(commonDTO, ps);

				setDeviceIagClass(commonDTO, ps);

				setDeviceAxles(commonDTO, ps);

				setEtcAccountId(commonDTO, ps);

				setAccountAgencyId(commonDTO, ps);

				setReadAviClass(commonDTO, ps);

				setReadAviAxles(commonDTO, ps);

				setDeviceProgramStatus(commonDTO, ps);

				setBuffereReadFlag(commonDTO, ps);

				setLaneDeviceStatus(commonDTO, ps);

				setPostDeviceStatus(commonDTO, ps);

				setPreTxnBalance(commonDTO, ps);

				setPlanTypeId(commonDTO, ps);

		//		setEtcTxStatus(commonDTO, ps);

				setSpeedViolFlag(commonDTO, ps);

				setImageTaken(commonDTO, ps);

				setPlateCountry(commonDTO, ps);

				setPlateState(commonDTO, ps);

				setPlateNumber(commonDTO, ps);

				setRevenueDate(commonDTO, ps);

				setPostedDate(commonDTO, ps);

				setATPFileId(commonDTO, ps);

				setIsReserved(commonDTO, ps);

				setCorrReasonId(commonDTO, ps);

				setReconDate(commonDTO, ps);

				setReconStatusInd(commonDTO, ps);

				setReconSubCodeInd(commonDTO, ps);

				setExternFileDate(commonDTO, ps);

				setMileage(commonDTO, ps);

				setDeviceReadCount(commonDTO, ps);

				setDeviceWriteCount(commonDTO, ps);

				entryDeviceReadCount(commonDTO, ps);

				entryDeviceWriteCount(commonDTO, ps);

				setDepositId(commonDTO, ps);

				setTxDate(commonDTO, ps);

				setCscLookupKey(commonDTO, ps);

				setUpdateTs(commonDTO, ps);

				setReciprocityTx(commonDTO, ps);

				setExpectedRevenueAmount(commonDTO, ps);

				setCashFareAmount(commonDTO, ps);

				setVideoFareAmount(commonDTO, ps);
				//sakshi
				setTxStatus(commonDTO, ps);
				return ps;
			}

		};

		jdbcTemplate.update(psc);
		logger.info("Insert into Home etc Queue table ends for lane tx id {}", commonDTO.getLaneTxId());

	}

	/**
	 * Update T_TRAN_DETAIL
	 */
	//sakshi
	@Override
	public void updateTTransDetail(TransactionDto transactionDto) {
		String query = LoadJpaQueries.getQueryById(QatpClassificationConstant.UPDATE_T_TRANS_TABLE);
		jdbcTemplate.update(query, transactionDto.getEtcAccountId(), transactionDto.getAtpFileId(),transactionDto.getAccountType(),
				transactionDto.getAccAgencyId(), transactionDto.getEtcFareAmount(),
				transactionDto.getPostedFareAmount(), timeZoneConv.currentTime(),
				transactionDto.getTxType(), transactionDto.getTxSubType(), transactionDto.getReciprocityTrx(),
				transactionDto.getExpectedRevenueAmount(), transactionDto.getCashFareAmount(),

				transactionDto.getVideoFareAmount(),transactionDto.getTxStatus(), transactionDto.getPlanType(),transactionDto.getIsViolation(),
				transactionDto.getTollRevenueType(),transactionDto.getHovFlag(),transactionDto.getLaneTxId());

	}

	@Override
	public void getQtpStatitics(TransactionDto transactionDto) {
		
		String query = LoadJpaQueries.getQueryById(QatpClassificationConstant.GET_T_QATP_STATISTICS);
	
		//String atp_file_id= (String)jdbcTemplate.queryForObject(query, String.class, transactionDto.getExtFileId(),getFileType(transactionDto));
		//if(StringUtils.isNotBlank(atp_file_id))
		//transactionDto.setAtpFileId(Long.valueOf(atp_file_id));
		
		

	}
	
	@Override
	public void updateQtpStatitics(TransactionDto transactionDto) {
		String query = LoadJpaQueries.getQueryById(QatpClassificationConstant.UPDATE_T_QATP_STATISTICS);
		//jdbcTemplate.update(query, transactionDto.getAtpFileId(),getFileType(transactionDto));
			
		String fileType= getFileType(transactionDto);
		logger.info("FileType====> {}", fileType);
			for(AtpFileIdObject atp : transactionDto.getAtpFileIdList()) {
				if(fileType.equals(atp.getFileType()))
				{
					logger.info("ATP FILE ID ====> {}", atp.getAtpFileId());
					transactionDto.setAtpFileId(atp.getAtpFileId());
					jdbcTemplate.update(query, atp.getAtpFileId(),fileType);
				}
			}
						
	}
	
	private String getFileType(TransactionDto transactionDto) {

		return(transactionDto.getTxType()!=null && "O".equalsIgnoreCase(transactionDto.getTxType()))? "ECTX":("R".equalsIgnoreCase(transactionDto.getTxType()))?"REJC":
			"V".equalsIgnoreCase(transactionDto.getTxType())? "VIOL":
				"E".equalsIgnoreCase(transactionDto.getTxType())?"ATPP":
					"D".equalsIgnoreCase(transactionDto.getTxType())?"DISC":
						"U".equalsIgnoreCase(transactionDto.getTxType())?"UNMA":"";
	}
	/**
	 * Insert into Queue table
	 */
	@Override
	public void insertInToViolQueue(CommonClassificationDto commonDTO) {
		logger.info("Insert into Viol Queue table starts for lane tx id {}", commonDTO.getLaneTxId());

		String query = LoadJpaQueries.getQueryById(QatpClassificationConstant.INSERT_INTO_T_VIOL_QUEUE);

		PreparedStatementCreator psc = new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				final PreparedStatement ps = connection.prepareStatement(query);
				setLaneTxId(commonDTO, ps);
				setTxExternRefNo(commonDTO, ps);
				setTxSeqNumber(commonDTO, ps);
				setExternFileId(commonDTO, ps);
				setPlazaAgencyId(commonDTO, ps);
				setPlazaId(commonDTO, ps);
				setLaneId(commonDTO, ps);
				setTxTimestamp(commonDTO, ps);
				setTxModeSeq(commonDTO, ps);

				setTxType(commonDTO, ps);

				setTxSubType(commonDTO, ps);

				setTollSystemType(commonDTO, ps);

				setLaneMode(commonDTO, ps);

				setLaneType(commonDTO, ps);

				setLaneState(commonDTO, ps);

				setLaneHealth(commonDTO, ps);

				setCollectorId(commonDTO, ps);

				setTourSegmentId(commonDTO, ps);

				setEntryDataSource(commonDTO, ps);

				setEntryLaneId(commonDTO, ps);

				setEntryPlazaId(commonDTO, ps);

				setEntryTimestamp(commonDTO, ps);

				setEntryTxSeqNum(commonDTO, ps);

				setEntryVehicleSpeed(commonDTO, ps);

				setLaneTxStatus(commonDTO, ps);

				setLaneTxType(commonDTO, ps);

				setTollRevenueType(commonDTO, ps);

				setActualClass(commonDTO, ps);

				setActualAxles(commonDTO, ps);

				setActualExtraAxles(commonDTO, ps);

				setCollectorClass(commonDTO, ps);

				setCollectorAxles(commonDTO, ps);

				setPreclassClass(commonDTO, ps);

				preclassAxles(commonDTO, ps);

				postclassClass(commonDTO, ps);

				postclassAxles(commonDTO, ps);

				setForwardAxles(commonDTO, ps);

				setReverseAxles(commonDTO, ps);

				setEtcFareAmount(commonDTO, ps);

				setPostedFareAmount(commonDTO, ps);

				setCollectedAmount(commonDTO, ps);

				setUnrealizedAmount(commonDTO, ps);

				setIsDiscountable(commonDTO, ps);

				setIsMedianFare(commonDTO, ps);

				setIsPeak(commonDTO, ps);

				setPriceScheduleId(commonDTO, ps);

				setVehicleSpeed(commonDTO, ps);

				setReceiptIssued(commonDTO, ps);

				setDeviceNo(commonDTO, ps);

				setAccountType(commonDTO, ps);
				setDeviceCodedClass(commonDTO, ps);

				setDeviceAgencyClass(commonDTO, ps);

				setDeviceIagClass(commonDTO, ps);

				setDeviceAxles(commonDTO, ps);

				setEtcAccountId(commonDTO, ps);

				setAccountAgencyId(commonDTO, ps);

				setReadAviClass(commonDTO, ps);

				setReadAviAxles(commonDTO, ps);

				setDeviceProgramStatus(commonDTO, ps);

				setBuffereReadFlag(commonDTO, ps);

				setLaneDeviceStatus(commonDTO, ps);

				setPostDeviceStatus(commonDTO, ps);

				setPreTxnBalance(commonDTO, ps);

				setPlanTypeId(commonDTO, ps);

			//	setEtcTxStatus(commonDTO, ps);

				setSpeedViolFlag(commonDTO, ps);

				setImageTaken(commonDTO, ps);

				setPlateCountry(commonDTO, ps);

				setPlateState(commonDTO, ps);

				setPlateNumber(commonDTO, ps);

				setRevenueDate(commonDTO, ps);

				setPostedDate(commonDTO, ps);

				setATPFileId(commonDTO, ps);

				setIsReserved(commonDTO, ps);

				setCorrReasonId(commonDTO, ps);

				setReconDate(commonDTO, ps);

				setReconStatusInd(commonDTO, ps);

				setReconSubCodeInd(commonDTO, ps);

				setExternFileDate(commonDTO, ps);

				setMileage(commonDTO, ps);

				setDeviceReadCount(commonDTO, ps);

				setDeviceWriteCount(commonDTO, ps);

				entryDeviceReadCount(commonDTO, ps);

				entryDeviceWriteCount(commonDTO, ps);

				setDepositId(commonDTO, ps);

				setTxDate(commonDTO, ps);

				setCscLookupKey(commonDTO, ps);

				setUpdateTs(commonDTO, ps);

				setReciprocityTx(commonDTO, ps);

				setExpectedRevenueAmount(commonDTO, ps);

				setCashFareAmount(commonDTO, ps);

				setVideoFareAmount(commonDTO, ps);
				//sakshi
				setTxStatus(commonDTO, ps);
				return ps;
			}
		};

		jdbcTemplate.update(psc);
		logger.info("Insert into  Viol Queue table ends for lane tx id {}", commonDTO.getLaneTxId());

	}

	//sakshi
		private void setTxStatus(CommonClassificationDto commonDTO, final PreparedStatement ps) throws SQLException {
			if (commonDTO.getTxStatus() != null) {
				ps.setInt(92, commonDTO.getTxStatus());
			} else {
				ps.setNull(92, java.sql.Types.NUMERIC);
			}
		}
	
	private void setVideoFareAmount(CommonClassificationDto commonDTO, final PreparedStatement ps) throws SQLException {
		if (commonDTO.getVideoFareAmount() != null) {
			ps.setDouble(91, commonDTO.getVideoFareAmount());
		} else {
			ps.setNull(91, java.sql.Types.NUMERIC);
		}
	}

	private void setCashFareAmount(CommonClassificationDto commonDTO, final PreparedStatement ps) throws SQLException {
		if (commonDTO.getCashFareAmount() != null) {
			ps.setDouble(90, commonDTO.getCashFareAmount());
		} else {
			ps.setNull(90, java.sql.Types.NUMERIC);
		}
	}

	private void setExpectedRevenueAmount(CommonClassificationDto commonDTO, final PreparedStatement ps)
			throws SQLException {
		if (commonDTO.getExpectedRevenueAmount() != null) {
			ps.setDouble(89, commonDTO.getExpectedRevenueAmount());
		} else {
			ps.setNull(89, java.sql.Types.NUMERIC);
		}
	}

	private void setReciprocityTx(CommonClassificationDto commonDTO, final PreparedStatement ps) throws SQLException {
		if (commonDTO.getReciprocityTrx() != null) {
			ps.setString(88, commonDTO.getReciprocityTrx());
		} else {
			ps.setNull(88, java.sql.Types.VARCHAR);
		}
	}

	private void setUpdateTs(CommonClassificationDto commonDTO, final PreparedStatement ps) throws SQLException {
		if (commonDTO.getUpdateTs() != null) {
			ps.setTimestamp(87, Timestamp.valueOf(timeZoneConv.currentTime()));
		} else {
			ps.setNull(87, java.sql.Types.TIMESTAMP);
		}
	}

	private void setTxDate(CommonClassificationDto commonDTO, final PreparedStatement ps) throws SQLException {
		if (!StringUtils.isBlank(commonDTO.getTxDate())) {
			ps.setDate(85, Date.valueOf(commonDTO.getTxDate()));
		} else {
			ps.setNull(85, java.sql.Types.DATE);
		}
	}

	private void setCscLookupKey(CommonClassificationDto commonDTO, final PreparedStatement ps) throws SQLException {
		if (!StringUtils.isBlank(commonDTO.getCscLookupKey())) {
			ps.setString(86, commonDTO.getCscLookupKey());
		} else {
			ps.setNull(86, java.sql.Types.VARCHAR);
		}
	}

	private void setDepositId(CommonClassificationDto commonDTO, final PreparedStatement ps) throws SQLException {
		if (!StringUtils.isBlank(commonDTO.getDepositId())) {
			ps.setString(84, commonDTO.getDepositId());
		} else {
			ps.setNull(84, java.sql.Types.VARCHAR);
		}
	}

	private void entryDeviceWriteCount(CommonClassificationDto commonDTO, final PreparedStatement ps)
			throws SQLException {
		if (commonDTO.getEntryDeviceWriteCount() != null) {
			ps.setInt(83, commonDTO.getEntryDeviceWriteCount());
		} else {
			ps.setNull(83, java.sql.Types.NUMERIC);
		}
	}

	private void entryDeviceReadCount(CommonClassificationDto commonDTO, final PreparedStatement ps)
			throws SQLException {
		if (commonDTO.getEntryDeviceReadCount() != null) {
			ps.setInt(82, commonDTO.getEntryDeviceReadCount());
		} else {
			ps.setNull(82, java.sql.Types.NUMERIC);
		}
	}

	private void setDeviceWriteCount(CommonClassificationDto commonDTO, final PreparedStatement ps)
			throws SQLException {
		if (commonDTO.getDeviceWriteCount() != null) {
			ps.setInt(81, commonDTO.getDeviceWriteCount());
		} else {
			ps.setNull(81, java.sql.Types.NUMERIC);
		}
	}

	private void setDeviceReadCount(CommonClassificationDto commonDTO, final PreparedStatement ps) throws SQLException {
		if (commonDTO.getDeviceReadCount() != null) {
			ps.setInt(80, commonDTO.getDeviceReadCount());
		} else {
			ps.setNull(80, java.sql.Types.NUMERIC);
		}
	}

	private void setMileage(CommonClassificationDto commonDTO, final PreparedStatement ps) throws SQLException {
		if (commonDTO.getMileage() != null) {
			ps.setInt(79, commonDTO.getMileage());
		} else {
			ps.setNull(79, java.sql.Types.NUMERIC);
		}
	}

	private void setExternFileDate(CommonClassificationDto commonDTO, final PreparedStatement ps) throws SQLException {
		if (commonDTO.getExternFileDate() != null) {
			ps.setDate(78, Date.valueOf(commonDTO.getExternFileDate()));
		} else {
			ps.setNull(78, java.sql.Types.DATE);
		}
	}

	private void setReconSubCodeInd(CommonClassificationDto commonDTO, final PreparedStatement ps) throws SQLException {
		if (commonDTO.getReconSubCodeInd() != null) {
			ps.setInt(77, commonDTO.getReconSubCodeInd());
		} else {
			ps.setNull(77, java.sql.Types.NUMERIC);
		}
	}

	private void setReconStatusInd(CommonClassificationDto commonDTO, final PreparedStatement ps) throws SQLException {
		if (commonDTO.getReconStatusInd() != null) {
			ps.setInt(76, commonDTO.getReconStatusInd());
		} else {
			ps.setNull(76, java.sql.Types.NUMERIC);
		}
	}

	private void setReconDate(CommonClassificationDto commonDTO, final PreparedStatement ps) throws SQLException {
		if (commonDTO.getReconDate() != null) {
			ps.setDate(75, Date.valueOf(commonDTO.getReconDate()));
		} else {
			ps.setNull(75, java.sql.Types.DATE);
		}
	}

	private void setCorrReasonId(CommonClassificationDto commonDTO, final PreparedStatement ps) throws SQLException {
		if (commonDTO.getCorrReasonId() != null) {
			ps.setInt(74, commonDTO.getCorrReasonId());
		} else {
			ps.setNull(74, java.sql.Types.NUMERIC);
		}
	}

	private void setIsReserved(CommonClassificationDto commonDTO, final PreparedStatement ps) throws SQLException {
		if (!StringUtils.isBlank(commonDTO.getIsReversed())) {
			ps.setString(73, commonDTO.getIsReversed());
		} else {
			ps.setNull(73, java.sql.Types.VARCHAR);
		}
	}

	private void setATPFileId(CommonClassificationDto commonDTO, final PreparedStatement ps) throws SQLException {
		if (commonDTO.getAtpFileId() != null) {
			ps.setLong(72, commonDTO.getAtpFileId());
		} else {
			ps.setNull(72, java.sql.Types.NUMERIC);
		}
	}

	private void setPostedDate(CommonClassificationDto commonDTO, final PreparedStatement ps) throws SQLException {
		if (commonDTO.getPostedDate() != null) {
			ps.setDate(71, Date.valueOf(commonDTO.getPostedDate()));
		} else {
			ps.setNull(71, java.sql.Types.DATE);
		}
	}

	private void setRevenueDate(CommonClassificationDto commonDTO, final PreparedStatement ps) throws SQLException {
		if (commonDTO.getRevenueDate() != null) {
			ps.setDate(70, Date.valueOf(commonDTO.getRevenueDate()));
		} else {
			ps.setNull(70, java.sql.Types.DATE);
		}
	}

	private void setPlateNumber(CommonClassificationDto commonDTO, final PreparedStatement ps) throws SQLException {
		if (!StringUtils.isBlank(commonDTO.getPlateNumber())) {
			ps.setString(69, commonDTO.getPlateNumber());
		} else {
			ps.setNull(69, java.sql.Types.VARCHAR);
		}
	}

	private void setPlateState(CommonClassificationDto commonDTO, final PreparedStatement ps) throws SQLException {
		if (!StringUtils.isBlank(commonDTO.getPlateState())) {
			ps.setString(68, commonDTO.getPlateState());
		} else {
			ps.setNull(68, java.sql.Types.VARCHAR);
		}
	}

	private void setPlateCountry(CommonClassificationDto commonDTO, final PreparedStatement ps) throws SQLException {
		if (!StringUtils.isBlank(commonDTO.getPlateCountry())) {
			ps.setString(67, commonDTO.getPlateCountry());
		} else {
			ps.setNull(67, java.sql.Types.VARCHAR);
		}
	}

	private void setImageTaken(CommonClassificationDto commonDTO, final PreparedStatement ps) throws SQLException {
		if (!StringUtils.isBlank(commonDTO.getImageTaken())) {
			ps.setString(66, commonDTO.getImageTaken());
		} else {
			ps.setNull(66, java.sql.Types.VARCHAR);
		}
	}

	private void setSpeedViolFlag(CommonClassificationDto commonDTO, final PreparedStatement ps) throws SQLException {
		if (!StringUtils.isBlank(commonDTO.getSpeedViolFlag())) {
			ps.setString(65, commonDTO.getSpeedViolFlag());
		} else {
			ps.setNull(65, java.sql.Types.VARCHAR);
		}
	}

//	private void setEtcTxStatus(CommonClassificationDto commonDTO, final PreparedStatement ps) throws SQLException {
//		if (commonDTO.getEtcTxStatus() != null) {
//			ps.setInt(65, commonDTO.getEtcTxStatus());
//		} else {
//			ps.setNull(65, java.sql.Types.NUMERIC);
//		}
//	}

	private void setPlanTypeId(CommonClassificationDto commonDTO, final PreparedStatement ps) throws SQLException {
		if (commonDTO.getPlanTypeId() != null) {
			ps.setInt(64, commonDTO.getPlanTypeId());
		} else {
			ps.setNull(64, java.sql.Types.NUMERIC);
		}
	}

	private void setPreTxnBalance(CommonClassificationDto commonDTO, final PreparedStatement ps) throws SQLException {
		if (commonDTO.getPreTxnBalance() != null) {
			ps.setDouble(63, commonDTO.getPreTxnBalance());
		} else {
			ps.setNull(63, java.sql.Types.NUMERIC);
		}
	}

	private void setPostDeviceStatus(CommonClassificationDto commonDTO, final PreparedStatement ps)
			throws SQLException {
		if (commonDTO.getPostDeviceStatus() != null) {
			ps.setInt(62, commonDTO.getPostDeviceStatus());
		} else {
			ps.setNull(62, java.sql.Types.NUMERIC);
		}
	}

	private void setLaneDeviceStatus(CommonClassificationDto commonDTO, final PreparedStatement ps)
			throws SQLException {
		if (commonDTO.getLaneDeviceStatus() != null) {
			ps.setLong(61, commonDTO.getLaneDeviceStatus());
		} else {
			ps.setNull(61, java.sql.Types.NUMERIC);
		}
	}

	private void setBuffereReadFlag(CommonClassificationDto commonDTO, final PreparedStatement ps) throws SQLException {
		if (!StringUtils.isBlank(commonDTO.getBufferReadFlag())) {
			ps.setString(60, commonDTO.getBufferReadFlag());
		} else {
			ps.setNull(60, java.sql.Types.VARCHAR);
		}
	}

	private void setDeviceProgramStatus(CommonClassificationDto commonDTO, final PreparedStatement ps)
			throws SQLException {
		if (!StringUtils.isBlank(commonDTO.getDeviceProgramStatus())) {
			ps.setString(59, commonDTO.getDeviceProgramStatus());
		} else {
			ps.setNull(59, java.sql.Types.VARCHAR);
		}
	}

	private void setReadAviAxles(CommonClassificationDto commonDTO, final PreparedStatement ps) throws SQLException {
		if (commonDTO.getReadAviAxles() != null) {
			ps.setDouble(58, commonDTO.getReadAviAxles());
		} else {
			ps.setNull(58, java.sql.Types.NUMERIC);
		}
	}

	private void setReadAviClass(CommonClassificationDto commonDTO, final PreparedStatement ps) throws SQLException {
		if (commonDTO.getReadAviClass() != null) {
			ps.setDouble(57, commonDTO.getReadAviClass());
		} else {
			ps.setNull(57, java.sql.Types.NUMERIC);
		}
	}

	private void setAccountAgencyId(CommonClassificationDto commonDTO, final PreparedStatement ps) throws SQLException {
		if (commonDTO.getAccountAgencyId() != null) {
			ps.setDouble(56, commonDTO.getAccountAgencyId());
		} else {
			ps.setNull(56, java.sql.Types.NUMERIC);
		}
	}

	private void setEtcAccountId(CommonClassificationDto commonDTO, final PreparedStatement ps) throws SQLException {
		if (commonDTO.getEtcAccountId() != null) {
			ps.setDouble(55, commonDTO.getEtcAccountId());
		} else {
			ps.setNull(55, java.sql.Types.NUMERIC);
		}
	}

	private void setDeviceAxles(CommonClassificationDto commonDTO, final PreparedStatement ps) throws SQLException {
		if (commonDTO.getDeviceAxles() != null) {
			ps.setInt(54, commonDTO.getDeviceAxles());
		} else {
			ps.setNull(54, java.sql.Types.NUMERIC);
		}
	}

	private void setDeviceIagClass(CommonClassificationDto commonDTO, final PreparedStatement ps) throws SQLException {
		if (commonDTO.getDeviceIagClass() != null) {
			ps.setInt(53, commonDTO.getDeviceIagClass());
		} else {
			ps.setNull(53, java.sql.Types.NUMERIC);
		}
	}

	private void setDeviceAgencyClass(CommonClassificationDto commonDTO, final PreparedStatement ps)
			throws SQLException {
		if (commonDTO.getDeviceAgencyClass() != null) {
			ps.setInt(52, commonDTO.getDeviceAgencyClass());
		} else {
			ps.setNull(52, java.sql.Types.NUMERIC);
		}
	}

	private void setDeviceCodedClass(CommonClassificationDto commonDTO, final PreparedStatement ps)
			throws SQLException {
		if (commonDTO.getDeviceCodedClass() != null) {
			ps.setInt(51, commonDTO.getDeviceCodedClass());
		} else {
			ps.setNull(51, java.sql.Types.NUMERIC);
		}
	}

	private void setAccountType(CommonClassificationDto commonDTO, final PreparedStatement ps) throws SQLException {
		if (commonDTO.getAccountType() != null) {
			ps.setInt(50, commonDTO.getAccountType());
		} else {
			ps.setNull(50, java.sql.Types.NUMERIC);
		}
	}

	private void setDeviceNo(CommonClassificationDto commonDTO, final PreparedStatement ps) throws SQLException {
		if (!StringUtils.isBlank(commonDTO.getDeviceNo())) {
			ps.setString(49, commonDTO.getDeviceNo());
		} else {
			ps.setNull(49, java.sql.Types.VARCHAR);
		}
	}

	private void setReceiptIssued(CommonClassificationDto commonDTO, final PreparedStatement ps) throws SQLException {
		if (commonDTO.getReceiptIssued() != null) {
			ps.setInt(48, commonDTO.getReceiptIssued());
		} else {
			ps.setNull(48, java.sql.Types.NUMERIC);
		}
	}

	private void setVehicleSpeed(CommonClassificationDto commonDTO, final PreparedStatement ps) throws SQLException {
		if (commonDTO.getVehicleSpeed() != null) {
			ps.setInt(47, commonDTO.getVehicleSpeed());
		} else {
			ps.setNull(47, java.sql.Types.NUMERIC);
		}
	}

	private void setPriceScheduleId(CommonClassificationDto commonDTO, final PreparedStatement ps) throws SQLException {
		if (commonDTO.getPriceScheduleId() != null) {
			ps.setInt(46, commonDTO.getPriceScheduleId());
		} else {
			ps.setNull(46, java.sql.Types.NUMERIC);
		}
	}

	private void setIsPeak(CommonClassificationDto commonDTO, final PreparedStatement ps) throws SQLException {
		if (!StringUtils.isBlank(commonDTO.getIsPeak())) {
			ps.setString(45, commonDTO.getIsPeak());
		} else {
			ps.setNull(45, java.sql.Types.VARCHAR);
		}
	}

	private void setIsMedianFare(CommonClassificationDto commonDTO, final PreparedStatement ps) throws SQLException {
		if (!StringUtils.isBlank(commonDTO.getIsMedianFare())) {
			ps.setString(44, commonDTO.getIsMedianFare());
		} else {
			ps.setNull(44, java.sql.Types.VARCHAR);
		}
	}

	private void setIsDiscountable(CommonClassificationDto commonDTO, final PreparedStatement ps) throws SQLException {
		if (!StringUtils.isBlank(commonDTO.getIsDiscountable())) {
			ps.setString(43, commonDTO.getIsDiscountable());
		} else {
			ps.setNull(43, java.sql.Types.VARCHAR);
		}
	}

	private void setUnrealizedAmount(CommonClassificationDto commonDTO, final PreparedStatement ps)
			throws SQLException {
		if (commonDTO.getUnrealizedAmount() != null) {
			ps.setDouble(42, commonDTO.getUnrealizedAmount());
		} else {
			ps.setNull(42, java.sql.Types.NUMERIC);
		}
	}

	private void setCollectedAmount(CommonClassificationDto commonDTO, final PreparedStatement ps) throws SQLException {
		if (commonDTO.getCollectedAmount() != null) {
			ps.setDouble(41, commonDTO.getCollectedAmount());
		} else {
			ps.setNull(41, java.sql.Types.NUMERIC);
		}
	}

	private void setPostedFareAmount(CommonClassificationDto commonDTO, final PreparedStatement ps)
			throws SQLException {
		if (commonDTO.getPostedFareAmount() != null) {
			ps.setDouble(40, commonDTO.getPostedFareAmount());
		} else {
			ps.setNull(40, java.sql.Types.NUMERIC);
		}
	}

	private void setEtcFareAmount(CommonClassificationDto commonDTO, final PreparedStatement ps) throws SQLException {
		if (commonDTO.getEtcFareAmount() != null) {
			ps.setDouble(39, commonDTO.getEtcFareAmount());
		} else {
			ps.setNull(39, java.sql.Types.NUMERIC);
		}
	}

	private void setReverseAxles(CommonClassificationDto commonDTO, final PreparedStatement ps) throws SQLException {
		if (commonDTO.getReverseAxles() != null) {
			ps.setInt(38, commonDTO.getReverseAxles());
		} else {
			ps.setNull(38, java.sql.Types.NUMERIC);
		}
	}

	private void setForwardAxles(CommonClassificationDto commonDTO, final PreparedStatement ps) throws SQLException {
		if (commonDTO.getForwardAxles() != null) {
			ps.setInt(37, commonDTO.getForwardAxles());
		} else {
			ps.setNull(37, java.sql.Types.NUMERIC);
		}
	}

	private void postclassAxles(CommonClassificationDto commonDTO, final PreparedStatement ps) throws SQLException {
		if (commonDTO.getPostClassAxles() != null) {
			ps.setInt(36, commonDTO.getPostClassAxles());
		} else {
			ps.setNull(36, java.sql.Types.NUMERIC);
		}
	}

	private void postclassClass(CommonClassificationDto commonDTO, final PreparedStatement ps) throws SQLException {
		if (commonDTO.getPostClassClass() != null) {
			ps.setInt(35, commonDTO.getPostClassClass());
		} else {
			ps.setNull(35, java.sql.Types.NUMERIC);
		}
	}

	private void preclassAxles(CommonClassificationDto commonDTO, final PreparedStatement ps) throws SQLException {
		if (commonDTO.getPreClassAxles() != null) {
			ps.setInt(34, commonDTO.getPreClassAxles());
		} else {
			ps.setNull(34, java.sql.Types.NUMERIC);
		}
	}

	private void setPreclassClass(CommonClassificationDto commonDTO, final PreparedStatement ps) throws SQLException {
		if (commonDTO.getPreClassClass() != null) {
			ps.setInt(33, commonDTO.getPreClassClass());
		} else {
			ps.setNull(33, java.sql.Types.NUMERIC);
		}
	}

	private void setCollectorAxles(CommonClassificationDto commonDTO, final PreparedStatement ps) throws SQLException {
		if (commonDTO.getCollectorAxles() != null) {
			ps.setInt(32, commonDTO.getCollectorAxles());
		} else {
			ps.setNull(32, java.sql.Types.NUMERIC);
		}
	}

	private void setCollectorClass(CommonClassificationDto commonDTO, final PreparedStatement ps) throws SQLException {
		if (commonDTO.getCollectorClass() != null) {
			ps.setInt(31, commonDTO.getCollectorClass());
		} else {
			ps.setNull(31, java.sql.Types.NUMERIC);
		}
	}

	private void setActualExtraAxles(CommonClassificationDto commonDTO, final PreparedStatement ps)
			throws SQLException {
		if (commonDTO.getActualExtraAxles() != null) {
			ps.setInt(30, commonDTO.getActualExtraAxles());
		} else {
			ps.setNull(30, java.sql.Types.NUMERIC);
		}
	}

	private void setActualAxles(CommonClassificationDto commonDTO, final PreparedStatement ps) throws SQLException {
		if (commonDTO.getActualAxles() != null) {
			ps.setInt(29, commonDTO.getActualAxles());
		} else {
			ps.setNull(29, java.sql.Types.NUMERIC);
		}
	}

	private void setActualClass(CommonClassificationDto commonDTO, final PreparedStatement ps) throws SQLException {
		if (commonDTO.getActualClass() != null) {
			ps.setLong(28, commonDTO.getActualClass());
		} else {
			ps.setNull(28, java.sql.Types.NUMERIC);
		}
	}

	private void setTollRevenueType(CommonClassificationDto commonDTO, final PreparedStatement ps) throws SQLException {
		if (commonDTO.getTollRevenueType() != null) {
			ps.setInt(27, commonDTO.getTollRevenueType());
		} else {
			ps.setNull(27, java.sql.Types.NUMERIC);
		}
	}

	private void setLaneTxType(CommonClassificationDto commonDTO, final PreparedStatement ps) throws SQLException {
		if (commonDTO.getLanetxType() != null) {
			ps.setLong(26, commonDTO.getLanetxType());
		} else {
			ps.setNull(26, java.sql.Types.NUMERIC);
		}
	}

	private void setLaneTxStatus(CommonClassificationDto commonDTO, final PreparedStatement ps) throws SQLException {
		if (commonDTO.getLaneTxStatus() != null) {
			ps.setLong(25, commonDTO.getLaneTxStatus());
		} else {
			ps.setNull(25, java.sql.Types.NUMERIC);
		}
	}

	private void setEntryVehicleSpeed(CommonClassificationDto commonDTO, final PreparedStatement ps)
			throws SQLException {
		if (commonDTO.getEntryVehicleSpeed() != null) {
			ps.setLong(24, commonDTO.getEntryVehicleSpeed());
		} else {
			ps.setNull(24, java.sql.Types.NUMERIC);
		}
	}

	private void setEntryTxSeqNum(CommonClassificationDto commonDTO, final PreparedStatement ps) throws SQLException {
		if (commonDTO.getEntryTxSeqNumber() != null) {
			ps.setLong(23, commonDTO.getEntryTxSeqNumber());
		} else {
			ps.setNull(23, java.sql.Types.NUMERIC);
		}
	}

	private void setEntryTimestamp(CommonClassificationDto commonDTO, final PreparedStatement ps) throws SQLException {
		if (commonDTO.getEntryTimestamp() != null) {
//			ps.setTimestamp(22, Timestamp.valueOf(commonDTO.getEntryTimestamp().toLocalDateTime()));
			ps.setObject(22, commonDTO.getEntryTimestamp());
		} else {
			ps.setNull(22, java.sql.Types.TIMESTAMP);
		}
	}

	private void setEntryPlazaId(CommonClassificationDto commonDTO, final PreparedStatement ps) throws SQLException {
		if (commonDTO.getEntryPlazaId() != null) {
			ps.setInt(21, commonDTO.getEntryPlazaId());
		} else {
			ps.setNull(21, java.sql.Types.NUMERIC);
		}
	}

	private void setEntryLaneId(CommonClassificationDto commonDTO, final PreparedStatement ps) throws SQLException {
		if (commonDTO.getEntryLaneId() != null) {
			ps.setInt(20, commonDTO.getEntryLaneId());
		} else {
			ps.setNull(20, java.sql.Types.NUMERIC);
		}
	}

	private void setEntryDataSource(CommonClassificationDto commonDTO, final PreparedStatement ps) throws SQLException {
		if (!StringUtils.isBlank(commonDTO.getEntryDataSource())) {
			ps.setString(19, commonDTO.getEntryDataSource());
		} else {
			ps.setNull(19, java.sql.Types.VARCHAR);
		}
	}

	private void setTourSegmentId(CommonClassificationDto commonDTO, final PreparedStatement ps) throws SQLException {
		if (commonDTO.getTourSegmentId() != null) {
			ps.setLong(18, commonDTO.getTourSegmentId());
		} else {
			ps.setNull(18, java.sql.Types.NUMERIC);
		}
	}

	private void setCollectorId(CommonClassificationDto commonDTO, final PreparedStatement ps) throws SQLException {
		if (commonDTO.getCollectorId() != null) {
			ps.setLong(17, commonDTO.getCollectorId());
		} else {
			ps.setNull(17, java.sql.Types.NUMERIC);
		}
	}

	private void setLaneHealth(CommonClassificationDto commonDTO, final PreparedStatement ps) throws SQLException {
		if (commonDTO.getLaneHealth() != null) {
			ps.setInt(16, commonDTO.getLaneHealth());
		} else {
			ps.setNull(16, java.sql.Types.NUMERIC);
		}
	}

	private void setLaneState(CommonClassificationDto commonDTO, final PreparedStatement ps) throws SQLException {
		if (commonDTO.getLaneState() != null) {
			ps.setLong(15, commonDTO.getLaneState());
		} else {
			ps.setNull(15, java.sql.Types.NUMERIC);
		}
	}

	private void setLaneType(CommonClassificationDto commonDTO, final PreparedStatement ps) throws SQLException {
		if (commonDTO.getLaneType() != null) {
			ps.setLong(14, commonDTO.getLaneType());
		} else {
			ps.setNull(14, java.sql.Types.NUMERIC);
		}
	}

	private void setLaneMode(CommonClassificationDto commonDTO, final PreparedStatement ps) throws SQLException {
		if (commonDTO.getLaneMode() != null) {
			ps.setLong(13, commonDTO.getLaneMode());
		} else {
			ps.setNull(13, java.sql.Types.NUMERIC);
		}
	}

	private void setTollSystemType(CommonClassificationDto commonDTO, final PreparedStatement ps) throws SQLException {
		if (!StringUtils.isBlank(commonDTO.getTollSystemType())) {
			ps.setString(12, commonDTO.getTollSystemType());
		} else {
			ps.setNull(12, java.sql.Types.VARCHAR);
		}
	}

	private void setTxSubType(CommonClassificationDto commonDTO, final PreparedStatement ps) throws SQLException {
		if (!StringUtils.isBlank(commonDTO.getTxSubtypeInd())) {
			ps.setString(11, commonDTO.getTxSubtypeInd());
		} else {
			ps.setNull(11, java.sql.Types.VARCHAR);
		}
	}

	private void setTxType(CommonClassificationDto commonDTO, final PreparedStatement ps) throws SQLException {
		if (!StringUtils.isBlank(commonDTO.getTxTypeInd())) {
			ps.setString(10, commonDTO.getTxTypeInd());
		} else {
			ps.setNull(10, java.sql.Types.VARCHAR);
		}
	}

	private void setTxModeSeq(CommonClassificationDto commonDTO, final PreparedStatement ps) throws SQLException {
		if (commonDTO.getTxModSeq() != null) {
			ps.setInt(9, commonDTO.getTxModSeq());
		} else {
			ps.setNull(9, java.sql.Types.NUMERIC);
		}
	}

	private void setTxTimestamp(CommonClassificationDto commonDTO, final PreparedStatement ps) throws SQLException {
		if (commonDTO.getTxTimestamp() != null) {
//			ps.setTimestamp(8, Timestamp.valueOf(commonDTO.getTxTimestamp().toLocalDateTime()));
			ps.setObject(8, commonDTO.getTxTimestamp());
		} else {
			ps.setNull(8, java.sql.Types.TIMESTAMP);
		}
	}

	private void setLaneId(CommonClassificationDto commonDTO, final PreparedStatement ps) throws SQLException {
		if (commonDTO.getLaneId() != null) {
			ps.setInt(7, commonDTO.getLaneId());
		} else {
			ps.setNull(7, java.sql.Types.NUMERIC);
		}
	}

	private void setPlazaId(CommonClassificationDto commonDTO, final PreparedStatement ps) throws SQLException {
		if (commonDTO.getPlazaId() != null) {
			ps.setLong(6, commonDTO.getPlazaId());
		} else {
			ps.setNull(6, java.sql.Types.NUMERIC);
		}
	}

	private void setPlazaAgencyId(CommonClassificationDto commonDTO, final PreparedStatement ps) throws SQLException {
		if (commonDTO.getPlazaAgencyId() != null) {
			ps.setInt(5, commonDTO.getPlazaAgencyId());
		} else {
			ps.setNull(5, java.sql.Types.NUMERIC);
		}
	}

	private void setExternFileId(CommonClassificationDto commonDTO, final PreparedStatement ps) throws SQLException {
		if (commonDTO.getExternFileId() != null) {
			ps.setLong(4, commonDTO.getExternFileId());
		} else {
			ps.setNull(4, java.sql.Types.NUMERIC);
		}
	}

	private void setTxSeqNumber(CommonClassificationDto commonDTO, final PreparedStatement ps) throws SQLException {
		if (commonDTO.getTxSeqNumber() != null) {
			ps.setLong(3, commonDTO.getTxSeqNumber());
		} else {
			ps.setNull(3, java.sql.Types.NUMERIC);
		}
	}

	private void setTxExternRefNo(CommonClassificationDto commonDTO, final PreparedStatement ps) throws SQLException {
		if (!StringUtils.isBlank(commonDTO.getTxExternRefNo())) {
			ps.setString(2, commonDTO.getTxExternRefNo());
		} else {
			ps.setNull(2, java.sql.Types.VARCHAR);
		}
	}

	private void setLaneTxId(CommonClassificationDto commonDTO, final PreparedStatement ps) throws SQLException {
		if (commonDTO.getLaneTxId() != null) {
			ps.setLong(1, commonDTO.getLaneTxId());
		} else {
			ps.setNull(1, java.sql.Types.NUMERIC);
		}
	}

	@Override
	public List<TollScheduleDto> getTollAmountByRevenueType(LocalDate txDate, Integer plazaId, Integer entryPlazaId,
			Integer vehicleClass, List<String> revenueTypeIdList, Long planTypeId, Integer priceScheduleId) {
		String queryToCheckFile = LoadJpaQueries.getQueryById(QatpClassificationConstant.GET_TOLL_PRICES);
		String temptxDate = txDate != null ? String.valueOf(txDate) : null;

		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue("TX_DATE", temptxDate);
		paramSource.addValue("PLAZA_ID", plazaId);
		paramSource.addValue("ENTRY_PLAZA_ID", entryPlazaId!=null?entryPlazaId:0);
		paramSource.addValue("VEHICLE_CLASS", vehicleClass);
		paramSource.addValue("PLAN_TYPE_ID", planTypeId);
		paramSource.addValue("PRICE_SCHEDULE_ID", priceScheduleId);
		paramSource.addValue("REVENUE_TYPE", revenueTypeIdList);
		List<TollScheduleDto> list = namedJdbcTemplate.query(queryToCheckFile, paramSource,
				BeanPropertyRowMapper.newInstance(TollScheduleDto.class));

		if (!list.isEmpty()) {
			logger.info("List size:" + list.size());
			return list;
		}

		logger.info("List is Empty");

		return null;
	}

	@Override
	public List<TollPriceSchedule> getAllPriceSchedule() {
		String queryToCheckFile = LoadJpaQueries.getQueryById("GET_PRICING_SCHEDULE_NEW");
		return jdbcTemplate.query(queryToCheckFile, new BeanPropertyRowMapper<TollPriceSchedule>(TollPriceSchedule.class));
		
	}

	@Override
	public List<TollScheduleDto> getAllTollSchedule() {
		String queryToCheckFile = LoadJpaQueries.getQueryById("GET_TOLL_PRICES_NEW");
		return jdbcTemplate.query(queryToCheckFile, new BeanPropertyRowMapper<TollScheduleDto>(TollScheduleDto.class));
	}
	
	/**
	 *  record inserted into T_UNMATCHED_ENTRY_TX table
	 */

	@Override
	public void insertIntoTUnmatchedEntryTx(TUnmatchedTx unMatchedTx) {
		logger.info("Insert into T_UNMATCHED_ENTRY_TX for lane tx id {}", unMatchedTx.getLaneTxId());
		try {
			String query = LoadJpaQueries.getQueryById(QatpClassificationConstant.INSERT_INTO_T_UNMATCHED_ENTRY_TX);
			MapSqlParameterSource paramSource = new MapSqlParameterSource();
			paramSource.addValue("LANE_TX_ID", unMatchedTx.getLaneTxId());
			paramSource.addValue("TX_MOD_SEQ", unMatchedTx.getTxModSeq());
			
			paramSource.addValue("ACCOUNT_AGENCY_ID", unMatchedTx.getAccountAgencyId());
			paramSource.addValue("ACCOUNT_TYPE", unMatchedTx.getAccountType());
			paramSource.addValue("ACTUAL_AXLES", unMatchedTx.getActualAxles());
			paramSource.addValue("ACTUAL_CLASS", unMatchedTx.getActualClass());
			paramSource.addValue("ACTUAL_EXTRA_AXLES", unMatchedTx.getActualExtraAxles());
			paramSource.addValue("ATP_FILE_ID", unMatchedTx.getAtpFileId());
			paramSource.addValue("BUFFERED_READ_FLAG", unMatchedTx.getBufferedRreadFlag());
			paramSource.addValue("COLLECTED_AMOUNT", unMatchedTx.getCollectedAmount());
			paramSource.addValue("COLLECTOR_AXLES", unMatchedTx.getCollectorAxles());
			paramSource.addValue("COLLECTOR_CLASS", unMatchedTx.getCollectorClass());
			paramSource.addValue("COLLECTOR_ID", unMatchedTx.getCollectorId());
			paramSource.addValue("CORR_REASON_ID", unMatchedTx.getCorrReasonId());
			paramSource.addValue("DEPOSIT_ID", unMatchedTx.getDepositId());
			paramSource.addValue("DEVICE_AGENCY_CLASS", unMatchedTx.getDeviceAgencyClass());
			paramSource.addValue("DEVICE_AXLES", unMatchedTx.getDeviceAxles());
			paramSource.addValue("DEVICE_CODED_CLASS", unMatchedTx.getDeviceCodedClass());
			paramSource.addValue("DEVICE_IAG_CLASS", unMatchedTx.getDeviceIagClass());
			paramSource.addValue("DEVICE_NO", unMatchedTx.getDeviceNo());
			paramSource.addValue("DEVICE_PROGRAM_STATUS", unMatchedTx.getDeviceProgramStatus());
			paramSource.addValue("DEVICE_READ_COUNT", unMatchedTx.getDeviceReadCount());
			paramSource.addValue("DEVICE_WRITE_COUNT", unMatchedTx.getDeviceWriteCount());
			paramSource.addValue("ENTRY_DATA_SOURCE", unMatchedTx.getEntryDataSource());
			paramSource.addValue("ENTRY_DEVICE_READ_COUNT", unMatchedTx.getEntryDeviceReadCount());
			paramSource.addValue("ENTRY_DEVICE_WRITE_COUNT", unMatchedTx.getEntryDeviceWriteCount());
			paramSource.addValue("ENTRY_LANE_ID", unMatchedTx.getEntryLaneId());
			paramSource.addValue("ENTRY_PLAZA_ID", unMatchedTx.getEntryPlazaId());
			paramSource.addValue("ENTRY_TIMESTAMP", unMatchedTx.getEntryTimestamp());
			paramSource.addValue("ENTRY_TX_SEQ_NUMBER", unMatchedTx.getEntryTxSeqNumber());
			paramSource.addValue("ENTRY_VEHICLE_SPEED", unMatchedTx.getEntryVehicleSpeed());
			paramSource.addValue("ETC_ACCOUNT_ID", unMatchedTx.getEtcAccountId());
			paramSource.addValue("EXTERN_FILE_DATE", unMatchedTx.getExternFileDate());
			paramSource.addValue("EXTERN_FILE_ID", unMatchedTx.getExternFileId());
			paramSource.addValue("FORWARD_AXLES", unMatchedTx.getForwardAxles());
			paramSource.addValue("IMAGE_TAKEN", unMatchedTx.getImageTaken());
			paramSource.addValue("IS_DISCOUNTABLE", unMatchedTx.getIsDiscountable());
			paramSource.addValue("IS_MEDIAN_FARE", unMatchedTx.getIsMedianFare());
			paramSource.addValue("IS_PEAK", unMatchedTx.getIsPeak());
			paramSource.addValue("IS_REVERSED", unMatchedTx.getIsReversed());
			paramSource.addValue("LANE_DEVICE_STATUS", unMatchedTx.getLaneDeviceStatus());
			paramSource.addValue("LANE_HEALTH", unMatchedTx.getLaneHealth());
			paramSource.addValue("LANE_ID", unMatchedTx.getLaneId());
			paramSource.addValue("LANE_MODE", unMatchedTx.getLaneMode());
			paramSource.addValue("LANE_STATE", unMatchedTx.getLaneState());
			paramSource.addValue("LANE_TX_STATUS", unMatchedTx.getLaneTxStatus());
			paramSource.addValue("LANE_TX_TYPE", unMatchedTx.getLaneTxType());
			paramSource.addValue("LANE_TYPE", unMatchedTx.getLaneType());
			paramSource.addValue("MILEAGE", unMatchedTx.getMileage());
			paramSource.addValue("PLAN_TYPE_ID", unMatchedTx.getPlanTypeId());
			paramSource.addValue("PLATE_COUNTRY", unMatchedTx.getPlateCountry());
			paramSource.addValue("PLATE_NUMBER", unMatchedTx.getPlateNumber());
			paramSource.addValue("PLATE_STATE", unMatchedTx.getPlateState());
			paramSource.addValue("PLAZA_AGENCY_ID", unMatchedTx.getPlazaAgencyId());
			paramSource.addValue("PLAZA_ID", unMatchedTx.getPlazaId());
			paramSource.addValue("POSTCLASS_AXLES", unMatchedTx.getPostclassAxles());
			paramSource.addValue("POSTCLASS_CLASS", unMatchedTx.getPostclassClass());
			paramSource.addValue("POSTED_DATE", unMatchedTx.getPostedDate());
			paramSource.addValue("POST_DEVICE_STATUS", unMatchedTx.getPostDeviceStatus());
			paramSource.addValue("PRECLASS_AXLES", unMatchedTx.getPreclassAxles());
			paramSource.addValue("PRECLASS_CLASS", unMatchedTx.getPreclassClass());
			paramSource.addValue("PRE_TXN_BALANCE", unMatchedTx.getPreTxnBalance());
			paramSource.addValue("PRICE_SCHEDULE_ID", unMatchedTx.getPriceScheduleId());
			paramSource.addValue("READ_AVI_AXLES", unMatchedTx.getReadAviAxles());
			paramSource.addValue("READ_AVI_CLASS", unMatchedTx.getReadAviClass());
			paramSource.addValue("RECEIPT_ISSUED", unMatchedTx.getReceiptIssued());
			paramSource.addValue("RECON_DATE", unMatchedTx.getReconDate());
			paramSource.addValue("RECON_STATUS_IND", unMatchedTx.getReconStatusInd());
			paramSource.addValue("RECON_SUB_CODE_IND", unMatchedTx.getReconSubCodeInd());
			paramSource.addValue("REVENUE_DATE", unMatchedTx.getRevenueDate());
			paramSource.addValue("REVERSE_AXLES", unMatchedTx.getReverseAxles());
			paramSource.addValue("SPEED_VIOL_FLAG", unMatchedTx.getSpeedViolFlag());
			paramSource.addValue("TOLL_REVENUE_TYPE", unMatchedTx.getTollRevenueType());
			paramSource.addValue("TOLL_SYSTEM_TYPE", unMatchedTx.getTollSystemType());
			paramSource.addValue("TOUR_SEGMENT_ID", unMatchedTx.getTourSegmentId());
			paramSource.addValue("TX_DATE", unMatchedTx.getTxDate());
			paramSource.addValue("TX_EXTERN_REF_NO", unMatchedTx.getTxExternRefNo());
			paramSource.addValue("TX_SEQ_NUMBER", unMatchedTx.getTxSeqNumber());
			paramSource.addValue("TX_STATUS", unMatchedTx.getTxStatus());
			paramSource.addValue("TX_SUBTYPE_IND", unMatchedTx.getTxSubtypeInd());
			paramSource.addValue("TX_TIMESTAMP", unMatchedTx.getTxTimeStamp());
			paramSource.addValue("TX_TYPE_IND", unMatchedTx.getTxTypeInd());
			paramSource.addValue("UNREALIZED_AMOUNT", unMatchedTx.getUnrealizedAmount());
			paramSource.addValue("UPDATE_TS", unMatchedTx.getUpdateTs());
			paramSource.addValue("VEHICLE_SPEED", unMatchedTx.getVehicleSpeed());
			paramSource.addValue("EXPECTED_REVENUE_AMOUNT", unMatchedTx.getExpectedRevenueAmount());
			paramSource.addValue("CASH_FARE_AMOUNT", unMatchedTx.getCashFareAmount());
			paramSource.addValue("VIDEO_FARE_AMOUNT", unMatchedTx.getVideoFareAmount());
			paramSource.addValue("ETC_FARE_AMOUNT", unMatchedTx.getEtcFareAmount());
			paramSource.addValue("POSTED_FARE_AMOUNT", unMatchedTx.getPostedFareAmount());
			
			namedJdbcTemplate.update(query, paramSource);
			logger.info("Successfully data inserted into T_UNMATCHED_ENTRY_TX Table for Lane_TX_Id {}",unMatchedTx.getLaneTxId());
		} catch (Exception ex) {
			ex.getMessage();
			ex.printStackTrace();
			logger.info("Record not inserted into T_UNMATCHED_ENTRY_TX Table properly" + ex.getMessage());
		}

	}
	/**
	 *  record inserted into T_UNMATCHED_EXIT_TX table
	 */
	
	@Override
	public void insertIntoTUnmatchedExitTx(TUnmatchedTx unMatchedTx) {
		logger.info("Insert into T_UNMATCHED_EXIT_TX for lane tx id {}", unMatchedTx.getLaneTxId());
		try {
			String query = LoadJpaQueries.getQueryById(QatpClassificationConstant.INSERT_INTO_T_UNMATCHED_EXIT_TX);
			MapSqlParameterSource paramSource = new MapSqlParameterSource();
			
			paramSource.addValue("LANE_TX_ID", unMatchedTx.getLaneTxId());
			paramSource.addValue("TX_MOD_SEQ", unMatchedTx.getTxModSeq());
			
			paramSource.addValue("ACCOUNT_AGENCY_ID", unMatchedTx.getAccountAgencyId());
			paramSource.addValue("ACCOUNT_TYPE", unMatchedTx.getAccountType());
			paramSource.addValue("ACTUAL_AXLES", unMatchedTx.getActualAxles());
			paramSource.addValue("ACTUAL_CLASS", unMatchedTx.getActualClass());
			paramSource.addValue("ACTUAL_EXTRA_AXLES", unMatchedTx.getActualExtraAxles());
			paramSource.addValue("ATP_FILE_ID", unMatchedTx.getAtpFileId());
			paramSource.addValue("BUFFERED_READ_FLAG", unMatchedTx.getBufferedRreadFlag());
			paramSource.addValue("COLLECTED_AMOUNT", unMatchedTx.getCollectedAmount());
			paramSource.addValue("COLLECTOR_AXLES", unMatchedTx.getCollectorAxles());
			paramSource.addValue("COLLECTOR_CLASS", unMatchedTx.getCollectorClass());
			paramSource.addValue("COLLECTOR_ID", unMatchedTx.getCollectorId());
			paramSource.addValue("CORR_REASON_ID", unMatchedTx.getCorrReasonId());
			paramSource.addValue("DEPOSIT_ID", unMatchedTx.getDepositId());
			paramSource.addValue("DEVICE_AGENCY_CLASS", unMatchedTx.getDeviceAgencyClass());
			paramSource.addValue("DEVICE_AXLES", unMatchedTx.getDeviceAxles());
			paramSource.addValue("DEVICE_CODED_CLASS", unMatchedTx.getDeviceCodedClass());
			paramSource.addValue("DEVICE_IAG_CLASS", unMatchedTx.getDeviceIagClass());
			paramSource.addValue("DEVICE_NO", unMatchedTx.getDeviceNo());
			paramSource.addValue("DEVICE_PROGRAM_STATUS", unMatchedTx.getDeviceProgramStatus());
			paramSource.addValue("DEVICE_READ_COUNT", unMatchedTx.getDeviceReadCount());
			paramSource.addValue("DEVICE_WRITE_COUNT", unMatchedTx.getDeviceWriteCount());
			paramSource.addValue("ENTRY_DATA_SOURCE", unMatchedTx.getEntryDataSource());
			paramSource.addValue("ENTRY_DEVICE_READ_COUNT", unMatchedTx.getEntryDeviceReadCount());
			paramSource.addValue("ENTRY_DEVICE_WRITE_COUNT", unMatchedTx.getEntryDeviceWriteCount());
			paramSource.addValue("ENTRY_LANE_ID", unMatchedTx.getEntryLaneId());
			paramSource.addValue("ENTRY_PLAZA_ID", unMatchedTx.getEntryPlazaId());
			paramSource.addValue("ENTRY_TIMESTAMP", unMatchedTx.getEntryTimestamp());
			paramSource.addValue("ENTRY_TX_SEQ_NUMBER", unMatchedTx.getEntryTxSeqNumber());
			paramSource.addValue("ENTRY_VEHICLE_SPEED", unMatchedTx.getEntryVehicleSpeed());
			paramSource.addValue("ETC_ACCOUNT_ID", unMatchedTx.getEtcAccountId());
			paramSource.addValue("EXTERN_FILE_DATE", unMatchedTx.getExternFileDate());
			paramSource.addValue("EXTERN_FILE_ID", unMatchedTx.getExternFileId());
			paramSource.addValue("FORWARD_AXLES", unMatchedTx.getForwardAxles());
			paramSource.addValue("IMAGE_TAKEN", unMatchedTx.getImageTaken());
			paramSource.addValue("IS_DISCOUNTABLE", unMatchedTx.getIsDiscountable());
			paramSource.addValue("IS_MEDIAN_FARE", unMatchedTx.getIsMedianFare());
			paramSource.addValue("IS_PEAK", unMatchedTx.getIsPeak());
			paramSource.addValue("IS_REVERSED", unMatchedTx.getIsReversed());
			paramSource.addValue("LANE_DEVICE_STATUS", unMatchedTx.getLaneDeviceStatus());
			paramSource.addValue("LANE_HEALTH", unMatchedTx.getLaneHealth());
			paramSource.addValue("LANE_ID", unMatchedTx.getLaneId());
			paramSource.addValue("LANE_MODE", unMatchedTx.getLaneMode());
			paramSource.addValue("LANE_STATE", unMatchedTx.getLaneState());
			paramSource.addValue("LANE_TX_STATUS", unMatchedTx.getLaneTxStatus());
			paramSource.addValue("LANE_TX_TYPE", unMatchedTx.getLaneTxType());
			paramSource.addValue("LANE_TYPE", unMatchedTx.getLaneType());
			paramSource.addValue("MILEAGE", unMatchedTx.getMileage());
			paramSource.addValue("PLAN_TYPE_ID", unMatchedTx.getPlanTypeId());
			paramSource.addValue("PLATE_COUNTRY", unMatchedTx.getPlateCountry());
			paramSource.addValue("PLATE_NUMBER", unMatchedTx.getPlateNumber());
			paramSource.addValue("PLATE_STATE", unMatchedTx.getPlateState());
			paramSource.addValue("PLAZA_AGENCY_ID", unMatchedTx.getPlazaAgencyId());
			paramSource.addValue("PLAZA_ID", unMatchedTx.getPlazaId());
			paramSource.addValue("POSTCLASS_AXLES", unMatchedTx.getPostclassAxles());
			paramSource.addValue("POSTCLASS_CLASS", unMatchedTx.getPostclassClass());
			paramSource.addValue("POSTED_DATE", unMatchedTx.getPostedDate());
			paramSource.addValue("POST_DEVICE_STATUS", unMatchedTx.getPostDeviceStatus());
			paramSource.addValue("PRECLASS_AXLES", unMatchedTx.getPreclassAxles());
			paramSource.addValue("PRECLASS_CLASS", unMatchedTx.getPreclassClass());
			paramSource.addValue("PRE_TXN_BALANCE", unMatchedTx.getPreTxnBalance());
			paramSource.addValue("PRICE_SCHEDULE_ID", unMatchedTx.getPriceScheduleId());
			paramSource.addValue("READ_AVI_AXLES", unMatchedTx.getReadAviAxles());
			paramSource.addValue("READ_AVI_CLASS", unMatchedTx.getReadAviClass());
			paramSource.addValue("RECEIPT_ISSUED", unMatchedTx.getReceiptIssued());
			paramSource.addValue("RECON_DATE", unMatchedTx.getReconDate());
			paramSource.addValue("RECON_STATUS_IND", unMatchedTx.getReconStatusInd());
			paramSource.addValue("RECON_SUB_CODE_IND", unMatchedTx.getReconSubCodeInd());
			paramSource.addValue("REVENUE_DATE", unMatchedTx.getRevenueDate());
			paramSource.addValue("REVERSE_AXLES", unMatchedTx.getReverseAxles());
			paramSource.addValue("SPEED_VIOL_FLAG", unMatchedTx.getSpeedViolFlag());
			paramSource.addValue("TOLL_REVENUE_TYPE", unMatchedTx.getTollRevenueType());
			paramSource.addValue("TOLL_SYSTEM_TYPE", unMatchedTx.getTollSystemType());
			paramSource.addValue("TOUR_SEGMENT_ID", unMatchedTx.getTourSegmentId());
			paramSource.addValue("TX_DATE", unMatchedTx.getTxDate());
			paramSource.addValue("TX_EXTERN_REF_NO", unMatchedTx.getTxExternRefNo());
			paramSource.addValue("TX_SEQ_NUMBER", unMatchedTx.getTxSeqNumber());
			paramSource.addValue("TX_STATUS", unMatchedTx.getTxStatus());
			paramSource.addValue("TX_SUBTYPE_IND", unMatchedTx.getTxSubtypeInd());
			paramSource.addValue("TX_TIMESTAMP", unMatchedTx.getTxTimeStamp());
			paramSource.addValue("TX_TYPE_IND", unMatchedTx.getTxTypeInd());
			paramSource.addValue("UNREALIZED_AMOUNT", unMatchedTx.getUnrealizedAmount());
			paramSource.addValue("UPDATE_TS", unMatchedTx.getUpdateTs());
			paramSource.addValue("VEHICLE_SPEED", unMatchedTx.getVehicleSpeed());
			paramSource.addValue("EXPECTED_REVENUE_AMOUNT", unMatchedTx.getExpectedRevenueAmount());
			paramSource.addValue("CASH_FARE_AMOUNT", unMatchedTx.getCashFareAmount());
			paramSource.addValue("VIDEO_FARE_AMOUNT", unMatchedTx.getVideoFareAmount());
			paramSource.addValue("ETC_FARE_AMOUNT", unMatchedTx.getEtcFareAmount());
			paramSource.addValue("POSTED_FARE_AMOUNT", unMatchedTx.getPostedFareAmount());
			
			namedJdbcTemplate.update(query, paramSource);
			logger.info("Successfully data inserted into T_UNMATCHED_EXIT_TX Table for Lane_TX_Id {}",unMatchedTx.getLaneTxId());
		} catch (Exception ex) {
			ex.getMessage();
			ex.printStackTrace();
			logger.info("Record not inserted into T_UNMATCHED_EXIT_TX Table properly" + ex.getMessage());
		}

	}
	
	@Override
	public Integer NY12Plan(String deviceNo, String trxDate) {
		String queryString = LoadJpaQueries.getQueryById(QatpClassificationConstant.NY_PLAN_COUNT);
		
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue("DEVICE_NO", deviceNo);//"00810901121"
		paramSource.addValue("TRX_DATE", trxDate);
	
		logger.info("NY PLAN TX DATE :" + trxDate);
		Integer count = namedJdbcTemplate.queryForObject(queryString, paramSource, Integer.class);

		if (count>0) 
		{
			logger.info("NY_PLAN_COUNT size:" + count);
			return count;
		}
		else 
		{
			return 0;
		}
		
	}

	@Override
	public void updateTTransDetailforNY12(TransactionDto transactionDto) {
		String query = LoadJpaQueries.getQueryById(QatpClassificationConstant.UPDATE_T_TRANS_TABLE);
		Integer plan_type = QatpClassificationConstant.NY12_PLAN_TYPE;
		jdbcTemplate.update(query, transactionDto.getEtcAccountId(), transactionDto.getAtpFileId(),transactionDto.getAccountType(),
				transactionDto.getAccAgencyId(), transactionDto.getEtcFareAmount(),
				transactionDto.getPostedFareAmount(), timeZoneConv.currentTime(),
				transactionDto.getTxType(), transactionDto.getTxSubType(), transactionDto.getReciprocityTrx(),
				transactionDto.getExpectedRevenueAmount(), transactionDto.getCashFareAmount(),

				transactionDto.getVideoFareAmount(),transactionDto.getTxStatus(), plan_type,transactionDto.getIsViolation(),
				transactionDto.getTollRevenueType(),transactionDto.getHovFlag(),transactionDto.getLaneTxId());

	}

	@Override
	public List<TollPostLimit> getTollPostLimit() {

		String queryToCheckFile = LoadJpaQueries.getQueryById("GET_T_TOLL_POST_LIMIT");

		return jdbcTemplate.query(queryToCheckFile, new BeanPropertyRowMapper<TollPostLimit>(TollPostLimit.class));
	}
}
