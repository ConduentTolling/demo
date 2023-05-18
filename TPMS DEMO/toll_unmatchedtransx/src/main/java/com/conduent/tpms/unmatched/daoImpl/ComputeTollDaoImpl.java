package com.conduent.tpms.unmatched.daoImpl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

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

import com.conduent.app.timezone.utility.TimeZoneConv;
import com.conduent.tpms.unmatched.constant.LoadJpaQueries;
import com.conduent.tpms.unmatched.constant.UnmatchedConstant;
import com.conduent.tpms.unmatched.dao.ComputeTollDao;
import com.conduent.tpms.unmatched.dto.CommonUnmatchedDto;
import com.conduent.tpms.unmatched.dto.DeviceList;
import com.conduent.tpms.unmatched.dto.EntryTransactionDto;
import com.conduent.tpms.unmatched.dto.ExitTransactionDto;
import com.conduent.tpms.unmatched.dto.ImageReviewDto;
import com.conduent.tpms.unmatched.dto.PlateInfoDto;
import com.conduent.tpms.unmatched.dto.TProcessParamterDto;
import com.conduent.tpms.unmatched.dto.TollScheduleDto;
import com.conduent.tpms.unmatched.dto.TransactionDto;
import com.conduent.tpms.unmatched.dto.UpdateTranDetailDto;
import com.conduent.tpms.unmatched.dto.ViolTxDto;
import com.conduent.tpms.unmatched.model.TollPriceSchedule;



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
	  private TimeZoneConv timeZoneConv;
	 
	
	private static final Logger logger = LoggerFactory.getLogger(ComputeTollDaoImpl.class);

	/**
	 * Get Price Schedule
	 */
	@Override
	public TollPriceSchedule getPriceSchedule(LocalDate effectiveDate, LocalDate expiryDate, String daysInd,
			Integer agencyId) {
		String queryToCheckFile = LoadJpaQueries.getQueryById(UnmatchedConstant.GET_PRICING_SCHEDULE);
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
		String queryToCheckFile = LoadJpaQueries.getQueryById(UnmatchedConstant.GET_TOLL_PRICES);
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
	public void insertInToHomeEtcQueue(CommonUnmatchedDto commonDTO) {
		logger.info("Insert into Home etc Queue table starts for lane tx id {}", commonDTO.getLaneTxId());

		String query = LoadJpaQueries.getQueryById(UnmatchedConstant.INSERT_INTO_T_HOME_ETC_QUEUE);

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
		String query = LoadJpaQueries.getQueryById(UnmatchedConstant.UPDATE_T_TRANS_TABLE);		
		jdbcTemplate.update(query, transactionDto.getEtcAccountId(), transactionDto.getAtpFileId(),transactionDto.getAccountType(),
				transactionDto.getAccAgencyId(), transactionDto.getEtcFareAmount(),
				transactionDto.getPostedFareAmount(), LocalDateTime.now(),
				transactionDto.getTxType(), transactionDto.getTxSubType(), transactionDto.getReciprocityTrx(),
				transactionDto.getExpectedRevenueAmount(), transactionDto.getCashFareAmount(),
				transactionDto.getVideoFareAmount(),transactionDto.getTxStatus(), transactionDto.getPlanType(),transactionDto.getIsViolation(),
				transactionDto.getTollRevenueType(),transactionDto.getLaneTxId());

	}

	@Override
	public void getQtpStatitics(TransactionDto transactionDto) {
		String query = LoadJpaQueries.getQueryById(UnmatchedConstant.GET_T_QATP_STATISTICS);
		
		//logger.info("getFileType:::{}",getFileType(transactionDto));
		logger.info("getExtFileId:::{}",transactionDto.getExtFileId());
	
		String atp_file_id= (String)jdbcTemplate.queryForObject(query, String.class, transactionDto.getExtFileId(),getFileType(transactionDto));
		logger.info("atp_file_id:::{}",atp_file_id);
		if(StringUtils.isNotBlank(atp_file_id))
		transactionDto.setAtpFileId(Long.valueOf(atp_file_id));

	}
	
	@Override
	public void updateQtpStatitics(TransactionDto transactionDto) {
		String query = LoadJpaQueries.getQueryById(UnmatchedConstant.UPDATE_T_QATP_STATISTICS);
		jdbcTemplate.update(query, transactionDto.getAtpFileId(),getFileType(transactionDto));

	}
	
	private String getFileType(TransactionDto transactionDto) {
		
	return(transactionDto.getTxType()!=null && "O".equalsIgnoreCase(transactionDto.getTxType()))? "ECTX":("R".equalsIgnoreCase(transactionDto.getTxType()))?"REJC":
		"V".equalsIgnoreCase(transactionDto.getTxType())? "IBTS":"E".equalsIgnoreCase(transactionDto.getTxType())?"ATPP":" ";
	}
	/**
	 * Insert into Queue table
	 */
	@Override
	public void insertInToViolQueue(CommonUnmatchedDto commonDTO) {
		logger.info("Insert into Viol Queue table starts for lane tx id {}", commonDTO.getLaneTxId());

		String query = LoadJpaQueries.getQueryById(UnmatchedConstant.INSERT_INTO_T_VIOL_QUEUE);

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
		private void setTxStatus(CommonUnmatchedDto commonDTO, final PreparedStatement ps) throws SQLException {
			if (commonDTO.getTxStatus() != null) {
				ps.setInt(92, commonDTO.getTxStatus());
			} else {
				ps.setNull(92, java.sql.Types.NUMERIC);
			}
		}
	
	private void setVideoFareAmount(CommonUnmatchedDto commonDTO, final PreparedStatement ps) throws SQLException {
		if (commonDTO.getVideoFareAmount() != null) {
			ps.setDouble(91, commonDTO.getVideoFareAmount());
		} else {
			ps.setNull(91, java.sql.Types.NUMERIC);
		}
	}

	private void setCashFareAmount(CommonUnmatchedDto commonDTO, final PreparedStatement ps) throws SQLException {
		if (commonDTO.getCashFareAmount() != null) {
			ps.setDouble(90, commonDTO.getCashFareAmount());
		} else {
			ps.setNull(90, java.sql.Types.NUMERIC);
		}
	}

	private void setExpectedRevenueAmount(CommonUnmatchedDto commonDTO, final PreparedStatement ps)
			throws SQLException {
		if (commonDTO.getExpectedRevenueAmount() != null) {
			ps.setDouble(89, commonDTO.getExpectedRevenueAmount());
		} else {
			ps.setNull(89, java.sql.Types.NUMERIC);
		}
	}

	private void setReciprocityTx(CommonUnmatchedDto commonDTO, final PreparedStatement ps) throws SQLException {
		if (commonDTO.getReciprocityTrx() != null) {
			ps.setString(88, commonDTO.getReciprocityTrx());
		} else {
			ps.setNull(88, java.sql.Types.VARCHAR);
		}
	}

	private void setUpdateTs(CommonUnmatchedDto commonDTO, final PreparedStatement ps) throws SQLException {
		if (commonDTO.getUpdateTs() != null) {
			ps.setTimestamp(87, Timestamp.valueOf(timeZoneConv.currentTime()));
			//ps.setTimestamp(87, Timestamp.valueOf("2022-04-04 08:45:45"));
		} else {
			ps.setNull(87, java.sql.Types.TIMESTAMP);
		}
	}

	private void setTxDate(CommonUnmatchedDto commonDTO, final PreparedStatement ps) throws SQLException {
		if (!StringUtils.isBlank(commonDTO.getTxDate())) {
			ps.setDate(85, Date.valueOf(commonDTO.getTxDate()));
		} else {
			ps.setNull(85, java.sql.Types.DATE);
		}
	}

	private void setCscLookupKey(CommonUnmatchedDto commonDTO, final PreparedStatement ps) throws SQLException {
		if (!StringUtils.isBlank(commonDTO.getCscLookupKey())) {
			ps.setString(86, commonDTO.getCscLookupKey());
		} else {
			ps.setNull(86, java.sql.Types.VARCHAR);
		}
	}

	private void setDepositId(CommonUnmatchedDto commonDTO, final PreparedStatement ps) throws SQLException {
		if (!StringUtils.isBlank(commonDTO.getDepositId())) {
			ps.setString(84, commonDTO.getDepositId());
		} else {
			ps.setNull(84, java.sql.Types.VARCHAR);
		}
	}

	private void entryDeviceWriteCount(CommonUnmatchedDto commonDTO, final PreparedStatement ps)
			throws SQLException {
		if (commonDTO.getEntryDeviceWriteCount() != null) {
			ps.setInt(83, commonDTO.getEntryDeviceWriteCount());
		} else {
			ps.setNull(83, java.sql.Types.NUMERIC);
		}
	}

	private void entryDeviceReadCount(CommonUnmatchedDto commonDTO, final PreparedStatement ps)
			throws SQLException {
		if (commonDTO.getEntryDeviceReadCount() != null) {
			ps.setInt(82, commonDTO.getEntryDeviceReadCount());
		} else {
			ps.setNull(82, java.sql.Types.NUMERIC);
		}
	}

	private void setDeviceWriteCount(CommonUnmatchedDto commonDTO, final PreparedStatement ps)
			throws SQLException {
		if (commonDTO.getDeviceWriteCount() != null) {
			ps.setInt(81, commonDTO.getDeviceWriteCount());
		} else {
			ps.setNull(81, java.sql.Types.NUMERIC);
		}
	}

	private void setDeviceReadCount(CommonUnmatchedDto commonDTO, final PreparedStatement ps) throws SQLException {
		if (commonDTO.getDeviceReadCount() != null) {
			ps.setInt(80, commonDTO.getDeviceReadCount());
		} else {
			ps.setNull(80, java.sql.Types.NUMERIC);
		}
	}

	private void setMileage(CommonUnmatchedDto commonDTO, final PreparedStatement ps) throws SQLException {
		if (commonDTO.getMileage() != null) {
			ps.setInt(79, commonDTO.getMileage());
		} else {
			ps.setNull(79, java.sql.Types.NUMERIC);
		}
	}

	private void setExternFileDate(CommonUnmatchedDto commonDTO, final PreparedStatement ps) throws SQLException {
		if (commonDTO.getExternFileDate() != null) {
			ps.setDate(78, Date.valueOf(commonDTO.getExternFileDate()));
		} else {
			ps.setNull(78, java.sql.Types.DATE);
		}
	}

	private void setReconSubCodeInd(CommonUnmatchedDto commonDTO, final PreparedStatement ps) throws SQLException {
		if (commonDTO.getReconSubCodeInd() != null) {
			ps.setInt(77, commonDTO.getReconSubCodeInd());
		} else {
			ps.setNull(77, java.sql.Types.NUMERIC);
		}
	}

	private void setReconStatusInd(CommonUnmatchedDto commonDTO, final PreparedStatement ps) throws SQLException {
		if (commonDTO.getReconStatusInd() != null) {
			ps.setInt(76, commonDTO.getReconStatusInd());
		} else {
			ps.setNull(76, java.sql.Types.NUMERIC);
		}
	}

	private void setReconDate(CommonUnmatchedDto commonDTO, final PreparedStatement ps) throws SQLException {
		if (commonDTO.getReconDate() != null) {
			ps.setDate(75, Date.valueOf(commonDTO.getReconDate()));
		} else {
			ps.setNull(75, java.sql.Types.DATE);
		}
	}

	private void setCorrReasonId(CommonUnmatchedDto commonDTO, final PreparedStatement ps) throws SQLException {
		if (commonDTO.getCorrReasonId() != null) {
			ps.setInt(74, commonDTO.getCorrReasonId());
		} else {
			ps.setNull(74, java.sql.Types.NUMERIC);
		}
	}

	private void setIsReserved(CommonUnmatchedDto commonDTO, final PreparedStatement ps) throws SQLException {
		if (!StringUtils.isBlank(commonDTO.getIsReversed())) {
			ps.setString(73, commonDTO.getIsReversed());
		} else {
			ps.setNull(73, java.sql.Types.VARCHAR);
		}
	}

	private void setATPFileId(CommonUnmatchedDto commonDTO, final PreparedStatement ps) throws SQLException {
		if (commonDTO.getAtpFileId() != null) {
			ps.setLong(72, commonDTO.getAtpFileId());
		} else {
			ps.setNull(72, java.sql.Types.NUMERIC);
		}
	}

	private void setPostedDate(CommonUnmatchedDto commonDTO, final PreparedStatement ps) throws SQLException {
		if (commonDTO.getPostedDate() != null) {
			ps.setDate(71, Date.valueOf(commonDTO.getPostedDate()));
		} else {
			ps.setNull(71, java.sql.Types.DATE);
		}
	}

	private void setRevenueDate(CommonUnmatchedDto commonDTO, final PreparedStatement ps) throws SQLException {
		if (commonDTO.getRevenueDate() != null) {
			ps.setDate(70, Date.valueOf(commonDTO.getRevenueDate()));
		} else {
			ps.setNull(70, java.sql.Types.DATE);
		}
	}

	private void setPlateNumber(CommonUnmatchedDto commonDTO, final PreparedStatement ps) throws SQLException {
		if (!StringUtils.isBlank(commonDTO.getPlateNumber())) {
			ps.setString(69, commonDTO.getPlateNumber());
		} else {
			ps.setNull(69, java.sql.Types.VARCHAR);
		}
	}

	private void setPlateState(CommonUnmatchedDto commonDTO, final PreparedStatement ps) throws SQLException {
		if (!StringUtils.isBlank(commonDTO.getPlateState())) {
			ps.setString(68, commonDTO.getPlateState());
		} else {
			ps.setNull(68, java.sql.Types.VARCHAR);
		}
	}

	private void setPlateCountry(CommonUnmatchedDto commonDTO, final PreparedStatement ps) throws SQLException {
		if (!StringUtils.isBlank(commonDTO.getPlateCountry())) {
			ps.setString(67, commonDTO.getPlateCountry());
		} else {
			ps.setNull(67, java.sql.Types.VARCHAR);
		}
	}

	private void setImageTaken(CommonUnmatchedDto commonDTO, final PreparedStatement ps) throws SQLException {
		if (!StringUtils.isBlank(commonDTO.getImageTaken())) {
			ps.setString(66, commonDTO.getImageTaken());
		} else {
			ps.setNull(66, java.sql.Types.VARCHAR);
		}
	}

	private void setSpeedViolFlag(CommonUnmatchedDto commonDTO, final PreparedStatement ps) throws SQLException {
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

	private void setPlanTypeId(CommonUnmatchedDto commonDTO, final PreparedStatement ps) throws SQLException {
		if (commonDTO.getPlanTypeId() != null) {
			ps.setInt(64, commonDTO.getPlanTypeId());
		} else {
			ps.setNull(64, java.sql.Types.NUMERIC);
		}
	}

	private void setPreTxnBalance(CommonUnmatchedDto commonDTO, final PreparedStatement ps) throws SQLException {
		if (commonDTO.getPreTxnBalance() != null) {
			ps.setDouble(63, commonDTO.getPreTxnBalance());
		} else {
			ps.setNull(63, java.sql.Types.NUMERIC);
		}
	}

	private void setPostDeviceStatus(CommonUnmatchedDto commonDTO, final PreparedStatement ps)
			throws SQLException {
		if (commonDTO.getPostDeviceStatus() != null) {
			ps.setInt(62, commonDTO.getPostDeviceStatus());
		} else {
			ps.setNull(62, java.sql.Types.NUMERIC);
		}
	}

	private void setLaneDeviceStatus(CommonUnmatchedDto commonDTO, final PreparedStatement ps)
			throws SQLException {
		if (commonDTO.getLaneDeviceStatus() != null) {
			ps.setLong(61, commonDTO.getLaneDeviceStatus());
		} else {
			ps.setNull(61, java.sql.Types.NUMERIC);
		}
	}

	private void setBuffereReadFlag(CommonUnmatchedDto commonDTO, final PreparedStatement ps) throws SQLException {
		if (!StringUtils.isBlank(commonDTO.getBufferReadFlag())) {
			ps.setString(60, commonDTO.getBufferReadFlag());
		} else {
			ps.setNull(60, java.sql.Types.VARCHAR);
		}
	}

	private void setDeviceProgramStatus(CommonUnmatchedDto commonDTO, final PreparedStatement ps)
			throws SQLException {
		if (!StringUtils.isBlank(commonDTO.getDeviceProgramStatus())) {
			ps.setString(59, commonDTO.getDeviceProgramStatus());
		} else {
			ps.setNull(59, java.sql.Types.VARCHAR);
		}
	}

	private void setReadAviAxles(CommonUnmatchedDto commonDTO, final PreparedStatement ps) throws SQLException {
		if (commonDTO.getReadAviAxles() != null) {
			ps.setDouble(58, commonDTO.getReadAviAxles());
		} else {
			ps.setNull(58, java.sql.Types.NUMERIC);
		}
	}

	private void setReadAviClass(CommonUnmatchedDto commonDTO, final PreparedStatement ps) throws SQLException {
		if (commonDTO.getReadAviClass() != null) {
			ps.setDouble(57, commonDTO.getReadAviClass());
		} else {
			ps.setNull(57, java.sql.Types.NUMERIC);
		}
	}

	private void setAccountAgencyId(CommonUnmatchedDto commonDTO, final PreparedStatement ps) throws SQLException {
		if (commonDTO.getAccountAgencyId() != null) {
			ps.setDouble(56, commonDTO.getAccountAgencyId());
		} else {
			ps.setNull(56, java.sql.Types.NUMERIC);
		}
	}

	private void setEtcAccountId(CommonUnmatchedDto commonDTO, final PreparedStatement ps) throws SQLException {
		if (commonDTO.getEtcAccountId() != null) {
			ps.setDouble(55, commonDTO.getEtcAccountId());
		} else {
			ps.setNull(55, java.sql.Types.NUMERIC);
		}
	}

	private void setDeviceAxles(CommonUnmatchedDto commonDTO, final PreparedStatement ps) throws SQLException {
		if (commonDTO.getDeviceAxles() != null) {
			ps.setInt(54, commonDTO.getDeviceAxles());
		} else {
			ps.setNull(54, java.sql.Types.NUMERIC);
		}
	}

	private void setDeviceIagClass(CommonUnmatchedDto commonDTO, final PreparedStatement ps) throws SQLException {
		if (commonDTO.getDeviceIagClass() != null) {
			ps.setInt(53, commonDTO.getDeviceIagClass());
		} else {
			ps.setNull(53, java.sql.Types.NUMERIC);
		}
	}

	private void setDeviceAgencyClass(CommonUnmatchedDto commonDTO, final PreparedStatement ps)
			throws SQLException {
		if (commonDTO.getDeviceAgencyClass() != null) {
			ps.setInt(52, commonDTO.getDeviceAgencyClass());
		} else {
			ps.setNull(52, java.sql.Types.NUMERIC);
		}
	}

	private void setDeviceCodedClass(CommonUnmatchedDto commonDTO, final PreparedStatement ps)
			throws SQLException {
		if (commonDTO.getDeviceCodedClass() != null) {
			ps.setInt(51, commonDTO.getDeviceCodedClass());
		} else {
			ps.setNull(51, java.sql.Types.NUMERIC);
		}
	}

	private void setAccountType(CommonUnmatchedDto commonDTO, final PreparedStatement ps) throws SQLException {
		if (commonDTO.getAccountType() != null) {
			ps.setInt(50, commonDTO.getAccountType());
		} else {
			ps.setNull(50, java.sql.Types.NUMERIC);
		}
	}

	private void setDeviceNo(CommonUnmatchedDto commonDTO, final PreparedStatement ps) throws SQLException {
		if (!StringUtils.isBlank(commonDTO.getDeviceNo())) {
			ps.setString(49, commonDTO.getDeviceNo());
		} else {
			ps.setNull(49, java.sql.Types.VARCHAR);
		}
	}

	private void setReceiptIssued(CommonUnmatchedDto commonDTO, final PreparedStatement ps) throws SQLException {
		if (commonDTO.getReceiptIssued() != null) {
			ps.setInt(48, commonDTO.getReceiptIssued());
		} else {
			ps.setNull(48, java.sql.Types.NUMERIC);
		}
	}

	private void setVehicleSpeed(CommonUnmatchedDto commonDTO, final PreparedStatement ps) throws SQLException {
		if (commonDTO.getVehicleSpeed() != null) {
			ps.setInt(47, commonDTO.getVehicleSpeed());
		} else {
			ps.setNull(47, java.sql.Types.NUMERIC);
		}
	}

	private void setPriceScheduleId(CommonUnmatchedDto commonDTO, final PreparedStatement ps) throws SQLException {
		if (commonDTO.getPriceScheduleId() != null) {
			ps.setInt(46, commonDTO.getPriceScheduleId());
		} else {
			ps.setNull(46, java.sql.Types.NUMERIC);
		}
	}

	private void setIsPeak(CommonUnmatchedDto commonDTO, final PreparedStatement ps) throws SQLException {
		if (!StringUtils.isBlank(commonDTO.getIsPeak())) {
			ps.setString(45, commonDTO.getIsPeak());
		} else {
			ps.setNull(45, java.sql.Types.VARCHAR);
		}
	}

	private void setIsMedianFare(CommonUnmatchedDto commonDTO, final PreparedStatement ps) throws SQLException {
		if (!StringUtils.isBlank(commonDTO.getIsMedianFare())) {
			ps.setString(44, commonDTO.getIsMedianFare());
		} else {
			ps.setNull(44, java.sql.Types.VARCHAR);
		}
	}

	private void setIsDiscountable(CommonUnmatchedDto commonDTO, final PreparedStatement ps) throws SQLException {
		if (!StringUtils.isBlank(commonDTO.getIsDiscountable())) {
			ps.setString(43, commonDTO.getIsDiscountable());
		} else {
			ps.setNull(43, java.sql.Types.VARCHAR);
		}
	}

	private void setUnrealizedAmount(CommonUnmatchedDto commonDTO, final PreparedStatement ps)
			throws SQLException {
		if (commonDTO.getUnrealizedAmount() != null) {
			ps.setDouble(42, commonDTO.getUnrealizedAmount());
		} else {
			ps.setNull(42, java.sql.Types.NUMERIC);
		}
	}

	private void setCollectedAmount(CommonUnmatchedDto commonDTO, final PreparedStatement ps) throws SQLException {
		if (commonDTO.getCollectedAmount() != null) {
			ps.setDouble(41, commonDTO.getCollectedAmount());
		} else {
			ps.setNull(41, java.sql.Types.NUMERIC);
		}
	}

	private void setPostedFareAmount(CommonUnmatchedDto commonDTO, final PreparedStatement ps)
			throws SQLException {
		if (commonDTO.getPostedFareAmount() != null) {
			ps.setDouble(40, commonDTO.getPostedFareAmount());
		} else {
			ps.setNull(40, java.sql.Types.NUMERIC);
		}
	}

	private void setEtcFareAmount(CommonUnmatchedDto commonDTO, final PreparedStatement ps) throws SQLException {
		if (commonDTO.getEtcFareAmount() != null) {
			ps.setDouble(39, commonDTO.getEtcFareAmount());
		} else {
			ps.setNull(39, java.sql.Types.NUMERIC);
		}
	}

	private void setReverseAxles(CommonUnmatchedDto commonDTO, final PreparedStatement ps) throws SQLException {
		if (commonDTO.getReverseAxles() != null) {
			ps.setInt(38, commonDTO.getReverseAxles());
		} else {
			ps.setNull(38, java.sql.Types.NUMERIC);
		}
	}

	private void setForwardAxles(CommonUnmatchedDto commonDTO, final PreparedStatement ps) throws SQLException {
		if (commonDTO.getForwardAxles() != null) {
			ps.setInt(37, commonDTO.getForwardAxles());
		} else {
			ps.setNull(37, java.sql.Types.NUMERIC);
		}
	}

	private void postclassAxles(CommonUnmatchedDto commonDTO, final PreparedStatement ps) throws SQLException {
		if (commonDTO.getPostClassAxles() != null) {
			ps.setInt(36, commonDTO.getPostClassAxles());
		} else {
			ps.setNull(36, java.sql.Types.NUMERIC);
		}
	}

	private void postclassClass(CommonUnmatchedDto commonDTO, final PreparedStatement ps) throws SQLException {
		if (commonDTO.getPostClassClass() != null) {
			ps.setInt(35, commonDTO.getPostClassClass());
		} else {
			ps.setNull(35, java.sql.Types.NUMERIC);
		}
	}

	private void preclassAxles(CommonUnmatchedDto commonDTO, final PreparedStatement ps) throws SQLException {
		if (commonDTO.getPreClassAxles() != null) {
			ps.setInt(34, commonDTO.getPreClassAxles());
		} else {
			ps.setNull(34, java.sql.Types.NUMERIC);
		}
	}

	private void setPreclassClass(CommonUnmatchedDto commonDTO, final PreparedStatement ps) throws SQLException {
		if (commonDTO.getPreClassClass() != null) {
			ps.setInt(33, commonDTO.getPreClassClass());
		} else {
			ps.setNull(33, java.sql.Types.NUMERIC);
		}
	}

	private void setCollectorAxles(CommonUnmatchedDto commonDTO, final PreparedStatement ps) throws SQLException {
		if (commonDTO.getCollectorAxles() != null) {
			ps.setInt(32, commonDTO.getCollectorAxles());
		} else {
			ps.setNull(32, java.sql.Types.NUMERIC);
		}
	}

	private void setCollectorClass(CommonUnmatchedDto commonDTO, final PreparedStatement ps) throws SQLException {
		if (commonDTO.getCollectorClass() != null) {
			ps.setInt(31, commonDTO.getCollectorClass());
		} else {
			ps.setNull(31, java.sql.Types.NUMERIC);
		}
	}

	private void setActualExtraAxles(CommonUnmatchedDto commonDTO, final PreparedStatement ps)
			throws SQLException {
		if (commonDTO.getActualExtraAxles() != null) {
			ps.setInt(30, commonDTO.getActualExtraAxles());
		} else {
			ps.setNull(30, java.sql.Types.NUMERIC);
		}
	}

	private void setActualAxles(CommonUnmatchedDto commonDTO, final PreparedStatement ps) throws SQLException {
		if (commonDTO.getActualAxles() != null) {
			ps.setInt(29, commonDTO.getActualAxles());
		} else {
			ps.setNull(29, java.sql.Types.NUMERIC);
		}
	}

	private void setActualClass(CommonUnmatchedDto commonDTO, final PreparedStatement ps) throws SQLException {
		if (commonDTO.getActualClass() != null) {
			ps.setLong(28, commonDTO.getActualClass());
		} else {
			ps.setNull(28, java.sql.Types.NUMERIC);
		}
	}

	private void setTollRevenueType(CommonUnmatchedDto commonDTO, final PreparedStatement ps) throws SQLException {
		if (commonDTO.getTollRevenueType() != null) {
			ps.setInt(27, commonDTO.getTollRevenueType());
		} else {
			ps.setNull(27, java.sql.Types.NUMERIC);
		}
	}

	private void setLaneTxType(CommonUnmatchedDto commonDTO, final PreparedStatement ps) throws SQLException {
		if (commonDTO.getLanetxType() != null) {
			ps.setLong(26, commonDTO.getLanetxType());
		} else {
			ps.setNull(26, java.sql.Types.NUMERIC);
		}
	}

	private void setLaneTxStatus(CommonUnmatchedDto commonDTO, final PreparedStatement ps) throws SQLException {
		if (commonDTO.getLaneTxStatus() != null) {
			ps.setLong(25, commonDTO.getLaneTxStatus());
		} else {
			ps.setNull(25, java.sql.Types.NUMERIC);
		}
	}

	private void setEntryVehicleSpeed(CommonUnmatchedDto commonDTO, final PreparedStatement ps)
			throws SQLException {
		if (commonDTO.getEntryVehicleSpeed() != null) {
			ps.setLong(24, commonDTO.getEntryVehicleSpeed());
		} else {
			ps.setNull(24, java.sql.Types.NUMERIC);
		}
	}

	private void setEntryTxSeqNum(CommonUnmatchedDto commonDTO, final PreparedStatement ps) throws SQLException {
		if (commonDTO.getEntryTxSeqNumber() != null) {
			ps.setLong(23, commonDTO.getEntryTxSeqNumber());
		} else {
			ps.setNull(23, java.sql.Types.NUMERIC);
		}
	}

	private void setEntryTimestamp(CommonUnmatchedDto commonDTO, final PreparedStatement ps) throws SQLException {
		if (commonDTO.getEntryTimestamp() != null) {
//			ps.setTimestamp(22, Timestamp.valueOf(commonDTO.getEntryTimestamp().toLocalDateTime()));
			ps.setObject(22, commonDTO.getEntryTimestamp());
		} else {
			ps.setNull(22, java.sql.Types.TIMESTAMP);
		}
	}

	private void setEntryPlazaId(CommonUnmatchedDto commonDTO, final PreparedStatement ps) throws SQLException {
		if (commonDTO.getEntryPlazaId() != null) {
			ps.setInt(21, commonDTO.getEntryPlazaId());
		} else {
			ps.setNull(21, java.sql.Types.NUMERIC);
		}
	}

	private void setEntryLaneId(CommonUnmatchedDto commonDTO, final PreparedStatement ps) throws SQLException {
		if (commonDTO.getEntryLaneId() != null) {
			ps.setInt(20, commonDTO.getEntryLaneId());
		} else {
			ps.setNull(20, java.sql.Types.NUMERIC);
		}
	}

	private void setEntryDataSource(CommonUnmatchedDto commonDTO, final PreparedStatement ps) throws SQLException {
		if (!StringUtils.isBlank(commonDTO.getEntryDataSource())) {
			ps.setString(19, commonDTO.getEntryDataSource());
		} else {
			ps.setNull(19, java.sql.Types.VARCHAR);
		}
	}

	private void setTourSegmentId(CommonUnmatchedDto commonDTO, final PreparedStatement ps) throws SQLException {
		if (commonDTO.getTourSegmentId() != null) {
			ps.setLong(18, commonDTO.getTourSegmentId());
		} else {
			ps.setNull(18, java.sql.Types.NUMERIC);
		}
	}

	private void setCollectorId(CommonUnmatchedDto commonDTO, final PreparedStatement ps) throws SQLException {
		if (commonDTO.getCollectorId() != null) {
			ps.setLong(17, commonDTO.getCollectorId());
		} else {
			ps.setNull(17, java.sql.Types.NUMERIC);
		}
	}

	private void setLaneHealth(CommonUnmatchedDto commonDTO, final PreparedStatement ps) throws SQLException {
		if (commonDTO.getLaneHealth() != null) {
			ps.setInt(16, commonDTO.getLaneHealth());
		} else {
			ps.setNull(16, java.sql.Types.NUMERIC);
		}
	}

	private void setLaneState(CommonUnmatchedDto commonDTO, final PreparedStatement ps) throws SQLException {
		if (commonDTO.getLaneState() != null) {
			ps.setLong(15, commonDTO.getLaneState());
		} else {
			ps.setNull(15, java.sql.Types.NUMERIC);
		}
	}

	private void setLaneType(CommonUnmatchedDto commonDTO, final PreparedStatement ps) throws SQLException {
		if (commonDTO.getLaneType() != null) {
			ps.setLong(14, commonDTO.getLaneType());
		} else {
			ps.setNull(14, java.sql.Types.NUMERIC);
		}
	}

	private void setLaneMode(CommonUnmatchedDto commonDTO, final PreparedStatement ps) throws SQLException {
		if (commonDTO.getLaneMode() != null) {
			ps.setLong(13, commonDTO.getLaneMode());
		} else {
			ps.setNull(13, java.sql.Types.NUMERIC);
		}
	}

	private void setTollSystemType(CommonUnmatchedDto commonDTO, final PreparedStatement ps) throws SQLException {
		if (!StringUtils.isBlank(commonDTO.getTollSystemType())) {
			ps.setString(12, commonDTO.getTollSystemType());
		} else {
			ps.setNull(12, java.sql.Types.VARCHAR);
		}
	}

	private void setTxSubType(CommonUnmatchedDto commonDTO, final PreparedStatement ps) throws SQLException {
		if (!StringUtils.isBlank(commonDTO.getTxSubtypeInd())) {
			ps.setString(11, commonDTO.getTxSubtypeInd());
		} else {
			ps.setNull(11, java.sql.Types.VARCHAR);
		}
	}

	private void setTxType(CommonUnmatchedDto commonDTO, final PreparedStatement ps) throws SQLException {
		if (!StringUtils.isBlank(commonDTO.getTxTypeInd())) {
			ps.setString(10, commonDTO.getTxTypeInd());
		} else {
			ps.setNull(10, java.sql.Types.VARCHAR);
		}
	}

	private void setTxModeSeq(CommonUnmatchedDto commonDTO, final PreparedStatement ps) throws SQLException {
		if (commonDTO.getTxModSeq() != null) {
			ps.setInt(9, commonDTO.getTxModSeq());
		} else {
			ps.setNull(9, java.sql.Types.NUMERIC);
		}
	}

	private void setTxTimestamp(CommonUnmatchedDto commonDTO, final PreparedStatement ps) throws SQLException {
		if (commonDTO.getTxTimestamp() != null) {
//			ps.setTimestamp(8, Timestamp.valueOf(commonDTO.getTxTimestamp().toLocalDateTime()));
			ps.setObject(8, commonDTO.getTxTimestamp());
		} else {
			ps.setNull(8, java.sql.Types.TIMESTAMP);
		}
	}

	private void setLaneId(CommonUnmatchedDto commonDTO, final PreparedStatement ps) throws SQLException {
		if (commonDTO.getLaneId() != null) {
			ps.setInt(7, commonDTO.getLaneId());
		} else {
			ps.setNull(7, java.sql.Types.NUMERIC);
		}
	}

	private void setPlazaId(CommonUnmatchedDto commonDTO, final PreparedStatement ps) throws SQLException {
		if (commonDTO.getPlazaId() != null) {
			ps.setLong(6, commonDTO.getPlazaId());
		} else {
			ps.setNull(6, java.sql.Types.NUMERIC);
		}
	}

	private void setPlazaAgencyId(CommonUnmatchedDto commonDTO, final PreparedStatement ps) throws SQLException {
		if (commonDTO.getPlazaAgencyId() != null) {
			ps.setInt(5, commonDTO.getPlazaAgencyId());
		} else {
			ps.setNull(5, java.sql.Types.NUMERIC);
		}
	}

	private void setExternFileId(CommonUnmatchedDto commonDTO, final PreparedStatement ps) throws SQLException {
		if (commonDTO.getExternFileId() != null) {
			ps.setLong(4, commonDTO.getExternFileId());
		} else {
			ps.setNull(4, java.sql.Types.NUMERIC);
		}
	}

	private void setTxSeqNumber(CommonUnmatchedDto commonDTO, final PreparedStatement ps) throws SQLException {
		if (commonDTO.getTxSeqNumber() != null) {
			ps.setLong(3, commonDTO.getTxSeqNumber());
		} else {
			ps.setNull(3, java.sql.Types.NUMERIC);
		}
	}

	private void setTxExternRefNo(CommonUnmatchedDto commonDTO, final PreparedStatement ps) throws SQLException {
		if (!StringUtils.isBlank(commonDTO.getTxExternRefNo())) {
			ps.setString(2, commonDTO.getTxExternRefNo());
		} else {
			ps.setNull(2, java.sql.Types.VARCHAR);
		}
	}

	private void setLaneTxId(CommonUnmatchedDto commonDTO, final PreparedStatement ps) throws SQLException {
		if (commonDTO.getLaneTxId() != null) {
			ps.setLong(1, commonDTO.getLaneTxId());
		} else {
			ps.setNull(1, java.sql.Types.NUMERIC);
		}
	}

	@Override
	public List<TollScheduleDto> getTollAmountByRevenueType(LocalDate txDate, Integer plazaId, Integer entryPlazaId,
			Integer vehicleClass, List<String> revenueTypeIdList, Long planTypeId, Integer priceScheduleId) {
		String queryToCheckFile = LoadJpaQueries.getQueryById(UnmatchedConstant.GET_TOLL_PRICES);
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
	public void updateMatchedDeviceNumberTxEntry(EntryTransactionDto entryTransactionDto, ExitTransactionDto exitTransactionDto, Map<String,String> reportsMap) {
	
		Integer count = 0;
		String query = LoadJpaQueries.getQueryById(UnmatchedConstant.UPDATE_MATCHED_DEVICE_NUMBER_TX_ENTRY);
		entryTransactionDto.setTxStatus(UnmatchedConstant.MATCHED_STATUS);
		entryTransactionDto.setMatchedTxExternRefNo(exitTransactionDto.getTxExternRefNo());
		jdbcTemplate.update(query, entryTransactionDto.getTxStatus(),entryTransactionDto.getMatchedTxExternRefNo(),entryTransactionDto.getDeviceNo(),
				entryTransactionDto.getLaneTxId());
		
		logger.info("::::::::::: updateMatchedTxEntry Successfully :::::::::::");
		
		count++;
		reportsMap.put("Matched Device Entry Count", count.toString() );

	}

	@Override
	public void updateMatchedDeviceNumberTxExit(ExitTransactionDto exitTransactionDto, EntryTransactionDto entryTransactionDto, Map<String,String> reportsMap) {
		
		Integer count = 0;
		String query = LoadJpaQueries.getQueryById(UnmatchedConstant.UPDATE_MATCHED_DEVICE_NUMBER_TX_EXIT);
		exitTransactionDto.setTxStatus(UnmatchedConstant.MATCHED_STATUS);
		//exitTransactionDto.setTxExternRefNo(entryTransactionDto.getTxExternRefNo());
		jdbcTemplate.update(query,exitTransactionDto.getTxStatus(),exitTransactionDto.getDeviceNo(), exitTransactionDto.getLaneTxId());
		
		logger.info("::::::::::: updateMatchedTxExit Successfully :::::::::::");
		
		count++;
		reportsMap.put("Matched Device Exit Count", count.toString());
	}
	

	@Override
	public List<TProcessParamterDto> getProcessParamtersList() {
		
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		
		paramSource.addValue(UnmatchedConstant.PARAM_GROUP, UnmatchedConstant.UNMATCHED);
		String queryRules =	LoadJpaQueries.getQueryById("GET_PROCESS_PARAMETERS");
		
		
		logger.info("unmatched info fetched from t_process_parameters table successfully."+ queryRules );
		
		return namedJdbcTemplate.query(queryRules,paramSource,new BeanPropertyRowMapper<TProcessParamterDto>(TProcessParamterDto.class));
		}
	
	@Override
	public List<TProcessParamterDto> getCrossMatchedProcessParamter() {
		
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		
		paramSource.addValue(UnmatchedConstant.PARAM_GROUP, UnmatchedConstant.UNMATCHED_CROSSMATCHED);
		String queryRules =	LoadJpaQueries.getQueryById("GET_PROCESS_PARAMETERS");
		
		
		logger.info("unmatched info fetched from t_process_parameters table successfully."+ queryRules );
		
		return namedJdbcTemplate.query(queryRules,paramSource,new BeanPropertyRowMapper<TProcessParamterDto>(TProcessParamterDto.class));
		}

	@Override
	public List<ExitTransactionDto> getExitData(String startProcessDate,String endProcessDate) {

		String query = LoadJpaQueries.getQueryById(UnmatchedConstant.GET_UNMATCHED_EXIT);
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue("START_DATE", startProcessDate);
		paramSource.addValue("END_DATE", endProcessDate);//endProcessDate 50
						
		List<ExitTransactionDto> list = namedJdbcTemplate.query(query, paramSource,
				new BeanPropertyRowMapper<ExitTransactionDto>(ExitTransactionDto.class));
				
		if (!list.isEmpty()) {
			return list;
		}
		return null;
		
	}

	@Override
	public List<EntryTransactionDto> getDeviceEntryData(String DeviceNo, Integer laneId, Integer plazaAgencyId, String txTimeStamp) {
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		
		
		String queryRules =	LoadJpaQueries.getQueryById("GET_MCHING_DEVICE");
		
		paramSource.addValue("PLAZA_AGENCY_ID", plazaAgencyId);
		paramSource.addValue("LANE_ID", laneId);
		paramSource.addValue("TX_TIMESTAMP", txTimeStamp);
		paramSource.addValue("DEVICE_NO", DeviceNo);
		
		logger.info("getting macing device from query.."+ queryRules );
		
		List<EntryTransactionDto> list = namedJdbcTemplate.query(queryRules, paramSource,
				BeanPropertyRowMapper.newInstance(EntryTransactionDto.class));

		if (!list.isEmpty()) {
			logger.info("List size:" + list.size());
			return list;
		}

		logger.info("List is Empty");

		return null;
	}

	@Override
	public void updateEntryStatusAndTxType(EntryTransactionDto entryTransactionDto, Map<String,String> reportsMap) {
			Integer count=0;
			String query = LoadJpaQueries.getQueryById(UnmatchedConstant.UPDATE_DISCARD_TX_ENTRY);
			entryTransactionDto.setTxStatus(UnmatchedConstant.DISCARD_STATUS);
			entryTransactionDto.setTxTypeInd(UnmatchedConstant.D);
			jdbcTemplate.update(query, entryTransactionDto.getTxStatus(),entryTransactionDto.getTxTypeInd(),entryTransactionDto.getLaneTxId());
			
			logger.info("::::::::::: update discard status for entry transaction Successfully :::::::::::");
			count++;
			
			reportsMap.put("Entry Matched Discard count", count.toString());

		}
	
	
	public String convertDate(String date) throws ParseException {
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat format2 = new SimpleDateFormat("dd-MMM-YY");
		java.util.Date convertedDate = format1.parse(date);
		String convertedDate2 = format2.format(convertedDate);
		System.out.println(convertedDate2);
		return convertedDate2;
	}
	
	

	@Override
	public List<ViolTxDto> getVoilTxData(String sectionId, String agencyId,LocalDate transactionDate) throws ParseException {
		
		String query = LoadJpaQueries.getQueryById(UnmatchedConstant.GET_MATCHED_STATUS_VOIL_TX);
		String tempTxDate = transactionDate != null ? String.valueOf(transactionDate) : null;
		
		String txtDate = convertDate(tempTxDate);
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue("SECTION_ID", sectionId);
		paramSource.addValue("AGENCY_ID", agencyId);
		paramSource.addValue("TX_DATE", txtDate);
	
		logger.info("End Process date :: "+txtDate);
		logger.info("getting macing device from query.."+ query );
				
		List<ViolTxDto> list = namedJdbcTemplate.query(query, paramSource,
				BeanPropertyRowMapper.newInstance(ViolTxDto.class));
		if (!list.isEmpty()) {
			return list;
		}
		return null;
	}

	@Override
	public List<EntryTransactionDto> getMachingPlateEntryData(String agencyId, String sectionId, Integer laneId,
			String txTimeStamp, LocalDate txDate, String plateState, String plateNumber) {
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		
		
		String queryRules =	LoadJpaQueries.getQueryById("GET_MCHING_PLATE");
		String tempTxDate = txDate != null ? String.valueOf(txDate) : null;
		logger.info("tempTxDate {}",tempTxDate);
		paramSource.addValue("AGENCY_ID", agencyId);
		paramSource.addValue("SECTION_ID", sectionId);
		paramSource.addValue("LANE_ID", laneId);
		paramSource.addValue("TX_TIMESTAMP", txTimeStamp);
		paramSource.addValue("TX_DATE", tempTxDate);
		paramSource.addValue("PLATE_STATE", plateState);
		paramSource.addValue("PLATE_NUMBER", plateNumber);
		
//		paramSource.addValue("AGENCY_ID", "1");
//		paramSource.addValue("SECTION_ID", "1");
//		paramSource.addValue("LANE_ID", "19228");
//		paramSource.addValue("TX_TIMESTAMP", "2022-05-11 18:54:12.650-04:00");
//		paramSource.addValue("TX_DATE", "2022-05-11");
//		paramSource.addValue("PLATE_STATE", "NY");
//		paramSource.addValue("PLATE_NUMBER", "MH130");
		
		
		logger.info("getting macing device from query.."+ queryRules );
		
		List<EntryTransactionDto> list = namedJdbcTemplate.query(queryRules, paramSource,
				BeanPropertyRowMapper.newInstance(EntryTransactionDto.class));

		if (!list.isEmpty()) {
			logger.info("List size:" + list.size());
			return list;
		}

		logger.info("List is Empty");

		return null;
	}

	@Override
	public List<TProcessParamterDto> getImageTrxProcessParamtersList() {

		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		
		paramSource.addValue(UnmatchedConstant.PARAM_GROUP, UnmatchedConstant.IMAGE_TXN);
		String queryRules =	LoadJpaQueries.getQueryById("GET_PROCESS_PARAMETERS");
		
		
		logger.info("Image transaction info fetched from t_process_parameters table successfully."+ queryRules );
		
		return namedJdbcTemplate.query(queryRules,paramSource,new BeanPropertyRowMapper<TProcessParamterDto>(TProcessParamterDto.class));
			
	}

	@Override
	public List<ImageReviewDto> getImageTrxParamConfig(String startProcessDate, String endProcessDate) throws ParseException {

		String query = LoadJpaQueries.getQueryById(UnmatchedConstant.GET_IMAGE_TRX_PROCESS);
		
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue("START_DATE", startProcessDate);
		paramSource.addValue("END_DATE", endProcessDate);//endProcessDate 50
		//paramSource.addValue("IMAGE_PROCESS_PERSENTAGE", imageProcessPercentage);
				
		List<ImageReviewDto> list = namedJdbcTemplate.query(query, paramSource,
				new BeanPropertyRowMapper<ImageReviewDto>(ImageReviewDto.class));
				
		if (!list.isEmpty()) {
			return list;
		}
		return null;
	}

	@Override
	public void updateMatchedPlateNumberTxEntry(EntryTransactionDto entryTransactionDto, ViolTxDto voilTxDto, Map<String,String> reportsMap) {
		
		Integer count=0;
		
		String query = LoadJpaQueries.getQueryById(UnmatchedConstant.UPDATE_MATCHED_PLATE_NUMBER_TX_ENTRY);
		entryTransactionDto.setTxStatus(UnmatchedConstant.MATCHED_PLATE_STATUS);
		entryTransactionDto.setMatchedTxExternRefNo(voilTxDto.getTxExternRefNo());
		jdbcTemplate.update(query, entryTransactionDto.getTxStatus(),entryTransactionDto.getMatchedTxExternRefNo(),entryTransactionDto.getPlateNumber(),
				entryTransactionDto.getLaneTxId());
		
		logger.info("::::::::::: updateMatchedTxEntry Successfully :::::::::::");
		
		count++;
		reportsMap.put("Matched Plate Entry Count", count.toString());
		
	}

	@Override
	public void updateMatchedPlateNumberVoilTx(ViolTxDto voilTxDto, EntryTransactionDto entryTransactionDto) {
		
		String query = LoadJpaQueries.getQueryById(UnmatchedConstant.UPDATE_MATCHED_PLATE_NUMBER_VIOL_TX);
		voilTxDto.setTxStatus(UnmatchedConstant.MATCHED_STATUS);
		
		voilTxDto.setEntryDataSource(entryTransactionDto.getEntryDataSource());
		voilTxDto.setEntryDeviceReadCount(entryTransactionDto.getEntryDeviceReadCount());
		voilTxDto.setEntryDeviceWriteCount(entryTransactionDto.getEntryDeviceWriteCount());
		voilTxDto.setEntryLaneId(entryTransactionDto.getEntryLaneId());
		voilTxDto.setEntryPlazaId(entryTransactionDto.getEntryPlazaId());
		voilTxDto.setEntryTimestamp(entryTransactionDto.getEntryTimestamp());
		voilTxDto.setEntryTxSeqNumber(entryTransactionDto.getEntryTxSeqNumber());
		voilTxDto.setEntryVehicleSpeed(entryTransactionDto.getEntryVehicleSpeed());
		jdbcTemplate.update(query,voilTxDto.getTxStatus(),voilTxDto.getEntryDataSource(),
				voilTxDto.getEntryDeviceReadCount(),voilTxDto.getEntryDeviceReadCount(),
				voilTxDto.getEntryDeviceWriteCount(),voilTxDto.getLaneId(),voilTxDto.getPlazaId(),
				voilTxDto.getEntryTimestamp(),voilTxDto.getEntryTxSeqNumber(),voilTxDto.getEntryVehicleSpeed(),
				voilTxDto.getPlateNumber(), voilTxDto.getLaneTxId());
		
		logger.info("::::::::::: updateMatchedViolTx Successfully :::::::::::");
	}

	@Override
	public void updateEntryDataToDiscard(EntryTransactionDto entryTransactionDto, Map<String,String> reportsMap) {
		
		Integer count = 0;
		String query = LoadJpaQueries.getQueryById(UnmatchedConstant.UPDATE_DISCARD_TX_ENTRY_FOR_PLATE);
		entryTransactionDto.setTxStatus(UnmatchedConstant.DISCARD_STATUS);
		entryTransactionDto.setTxTypeInd(UnmatchedConstant.D);
		jdbcTemplate.update(query, entryTransactionDto.getTxStatus(),entryTransactionDto.getTxTypeInd(),entryTransactionDto.getLaneTxId());
		
		logger.info("::::::::::: update discard status for entry transaction Successfully :::::::::::");
		
		count++;
		
		reportsMap.put("Matched Plate Entry Discard Count", count.toString());
	}

	@Override
	public Integer NY12Plan(String deviceNo, String trxDate) {
		String queryString = LoadJpaQueries.getQueryById(UnmatchedConstant.NY_PLAN_COUNT);
		
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue("DEVICE_NO", deviceNo);//"00810901121"
		paramSource.addValue("TRX_DATE", trxDate);
	
		logger.info("NY PLAN TX DATE :" + trxDate);
		Integer count = namedJdbcTemplate.queryForObject(queryString, paramSource, Integer.class);

		if (count>0) {
			logger.info("NY_PLAN_COUNT size:" + count);
			return count;
		}else {
			return 0;
		}
		

		
	}

	@Override
	public Long getEtcAccountId(String deviceNo) {

		String queryString = LoadJpaQueries.getQueryById(UnmatchedConstant.GET_ETC_ACCOUNT_ID);
		
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue("DEVICE_NO", deviceNo);//"00810901121"
		
		Long etcAccounId = namedJdbcTemplate.queryForObject(queryString, paramSource, Long.class);

		if (etcAccounId>0) {
			logger.info("GET_ETC_ACCOUNT_ID size:" + etcAccounId);
			return etcAccounId;
		}else {
			return 0L;
		}
	}

	@Override
	public List<PlateInfoDto> getPlateInfo(Long etcAccountId1, LocalDate txDate) {
		
		String queryString = LoadJpaQueries.getQueryById(UnmatchedConstant.GET_PLATE_INFO);
		
		String tempTxDate = txDate != null ? String.valueOf(txDate) : null;
		logger.info("tempTxDate {}",tempTxDate);
		
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue("ETC_ACC_ID", etcAccountId1);
		paramSource.addValue("TX_DATE", tempTxDate);

		logger.info("getting macing device from query.."+ queryString );
		
		List<PlateInfoDto> list = namedJdbcTemplate.query(queryString, paramSource,
				BeanPropertyRowMapper.newInstance(PlateInfoDto.class));

		if (!list.isEmpty()) {
			logger.info("List size:" + list.size());
			return list;
		}

		logger.info("List is Empty");

		return null;
	}

	@Override
	public List<EntryTransactionDto> getMachingPlateFromDevice(Integer plazaAgencyId, Integer laneId,
			String txTimeStamp, LocalDate txDate, String plateState, String plateNumber, String plateCountry, Long plateType) {

		String queryString = LoadJpaQueries.getQueryById(UnmatchedConstant.GET_MCHING_PLATE_FROM_DEVICE);
			
		String tempTxDate = txDate != null ? String.valueOf(txDate) : null;
		logger.info("tempTxDate {}",tempTxDate);
		
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		
		paramSource.addValue("AGENCY_ID", plazaAgencyId);
		paramSource.addValue("LANE_ID", laneId);
		paramSource.addValue("TX_TIMESTAMP", txTimeStamp);
		paramSource.addValue("TX_DATE", tempTxDate);
		paramSource.addValue("PLATE_STATE", plateState);
		paramSource.addValue("PLATE_NUMBER", plateNumber);
		paramSource.addValue("PLATE_COUNTRY", plateCountry);
		paramSource.addValue("PLATE_TYPE", plateType);

		logger.info("getting macing device-plate from query.."+ queryString );
		
		List<EntryTransactionDto> list = namedJdbcTemplate.query(queryString, paramSource,
				BeanPropertyRowMapper.newInstance(EntryTransactionDto.class));

		if (!list.isEmpty()) {
			logger.info("List size:" + list.size());
			return list;
		}

		logger.info("List is Empty");

		return null;
	}

	@Override
	public void updateMatchedPlateNumberTxExit(ExitTransactionDto exitTransactionDto, EntryTransactionDto entryPlateList, Map<String,String> reportsMap) {
		
			Integer count = 0;
			
				String query = LoadJpaQueries.getQueryById(UnmatchedConstant.UPDATE_MATCHED_PLATE_NUMBER_TX_EXIT);
				exitTransactionDto.setTxStatus(UnmatchedConstant.MATCHED_STATUS);
				//exitTransactionDto.setTxExternRefNo(entryTransactionDto.getTxExternRefNo());
				jdbcTemplate.update(query,exitTransactionDto.getTxStatus(), exitTransactionDto.getLaneTxId());
				
				logger.info("::::::::::: updateMatchedTxExit Successfully :::::::::::");
				
				count++;
				
				reportsMap.put("Matched Device Exit Count", count.toString());
	}

	@Override
	public List<DeviceList> getDeviceUsingEtcAccountID(Long etcAccountId) {
		String queryString = LoadJpaQueries.getQueryById(UnmatchedConstant.GET_DEVICE_NUMBER);
		
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue("ETC_ACC_ID", etcAccountId);//"50009986"
		
		List<DeviceList> deviceNo = namedJdbcTemplate.query(queryString, paramSource, BeanPropertyRowMapper.newInstance(DeviceList.class));

		if (!deviceNo.isEmpty()) {
			logger.info("Device List size:" + deviceNo.size());
			return deviceNo;
		}
		logger.info("List is Empty");

		return null;
	}

	@Override
	public List<PlateInfoDto> getEtcAccountIdFromPlate(String plateState, String plateNumber, LocalDate txDate) {

		logger.info("=====crossmatched================== #3");
		String queryString = LoadJpaQueries.getQueryById(UnmatchedConstant.GET_ETC_ACC_FROM_PLATE);
		logger.info("=====crossmatched================== #4");
		String tempTxDate = txDate != null ? String.valueOf(txDate) : null;
		logger.info("tempTxDate {}",tempTxDate);
		logger.info("=====crossmatched================== #5");
		MapSqlParameterSource paramSource = new MapSqlParameterSource();

		paramSource.addValue("TX_DATE", tempTxDate);
		paramSource.addValue("PLATE_STATE", plateState);
		paramSource.addValue("PLATE_NUMBER", plateNumber);
		
//		paramSource.addValue("TX_DATE", "2022-05-21");
//		paramSource.addValue("PLATE_STATE", "NY");
//		paramSource.addValue("PLATE_NUMBER", "MH130");


		logger.info("getting macing plate-device from query.."+ queryString );
		
		List<PlateInfoDto> list = namedJdbcTemplate.query(queryString, paramSource,
				BeanPropertyRowMapper.newInstance(PlateInfoDto.class));

		if (!list.isEmpty()) {
			logger.info("List size:" + list.size());
			return list;
		}

		logger.info("List is Empty");

		return null;
	}

	@Override
	public List<EntryTransactionDto> getMatchingDeviceFromPlate(String deviceNo, Integer laneId, Integer plazaAgencyId,
			String txTimeStamp) {


		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		
		
		String queryRules =	LoadJpaQueries.getQueryById(UnmatchedConstant.GET_MCHING_DEVICE_USING_PLATE);
		
		paramSource.addValue("PLAZA_AGENCY_ID", plazaAgencyId);
		paramSource.addValue("LANE_ID", laneId);
		paramSource.addValue("TX_TIMESTAMP", txTimeStamp);
		paramSource.addValue("DEVICE_NO", deviceNo);
		
		logger.info("getting macing device from query.."+ queryRules );
		
		List<EntryTransactionDto> list = namedJdbcTemplate.query(queryRules, paramSource,
				BeanPropertyRowMapper.newInstance(EntryTransactionDto.class));

		if (!list.isEmpty()) {
			logger.info("List size:" + list.size());
			return list;
		}

		logger.info("List is Empty");

		return null;
	}

	@Override
	public List<EntryTransactionDto> getExpiredEntryList(String tempEndDate) {

		String query = LoadJpaQueries.getQueryById(UnmatchedConstant.GET_EXPIRE_ENTRY);
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue("END_DATE", tempEndDate);
						
		List<EntryTransactionDto> list = namedJdbcTemplate.query(query, paramSource,
				new BeanPropertyRowMapper<EntryTransactionDto>(EntryTransactionDto.class));
				
		if (!list.isEmpty()) {
			return list;
		}
		return null;
	}

	@Override
	public List<ExitTransactionDto> getExpiredExitList(String tempEndDate) {
		
		String query = LoadJpaQueries.getQueryById(UnmatchedConstant.GET_EXPIRE_EXIT);
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue("END_DATE", tempEndDate);
						
		List<ExitTransactionDto> list = namedJdbcTemplate.query(query, paramSource,
				new BeanPropertyRowMapper<ExitTransactionDto>(ExitTransactionDto.class));
				
		if (!list.isEmpty()) {
			return list;
		}
		return null;
	}

	@Override
	public List<ViolTxDto> getExpiredViolList(String tempEndDate) {

		String query = LoadJpaQueries.getQueryById(UnmatchedConstant.GET_EXPIRED_VIOL_DATA);
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue("END_DATE", tempEndDate);
				
		List<ViolTxDto> list = namedJdbcTemplate.query(query, paramSource,
				new BeanPropertyRowMapper<ViolTxDto>(ViolTxDto.class));
				
		if (!list.isEmpty()) {
			return list;
		}
		return null;
	}
	
	@Override
	public List<ViolTxDto> getExpiredViolList(String tempEndDate, String agencyId) {

		String query = LoadJpaQueries.getQueryById(UnmatchedConstant.GET_EXPIRED_VIOL_DATA);
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue("END_DATE", tempEndDate);
		paramSource.addValue("AGENCY_ID", agencyId);
						
		List<ViolTxDto> list = namedJdbcTemplate.query(query, paramSource,
				new BeanPropertyRowMapper<ViolTxDto>(ViolTxDto.class));
				
		if (!list.isEmpty()) {
			return list;
		}
		return null;
	}

	@Override
	public String getMaxEndDate() {

		String queryString = LoadJpaQueries.getQueryById(UnmatchedConstant.GET_MAX_END_DATE);
		
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		
		
		String maxEndDate = namedJdbcTemplate.queryForObject(queryString,paramSource, String.class);

		if (maxEndDate!=null) {
			logger.info("MaxEndDate :" + maxEndDate);
			return maxEndDate;
		}else {
			return null;
		}
	}

	@Override
	public void updateTTransDetailTableVIolTx(EntryTransactionDto entryList, ViolTxDto exitList) {

		String query = LoadJpaQueries.getQueryById(UnmatchedConstant.UPDATE_MATCHED_TRAN);
		
		UpdateTranDetailDto updateTranDetail = new UpdateTranDetailDto();
		updateTranDetail.setLaneTxId(exitList.getLaneTxId());
		updateTranDetail.setMatchedTxExternRefNo(entryList.getTxExternRefNo());
		updateTranDetail.setTxStatus(UnmatchedConstant.MATCHED_PLATE_STATUS);
		updateTranDetail.setTxType(UnmatchedConstant.U);
		
		jdbcTemplate.update(query, updateTranDetail.getTxStatus(),updateTranDetail.getMatchedTxExternRefNo(),updateTranDetail.getTxType(),
				updateTranDetail.getLaneTxId());
		
		logger.info("::::::::::: updateMatched T_TRAN_DETAIL Table for Viol_TX table :::::::::::");
	}
	
	@Override
	public void updateTTransDetailTable(EntryTransactionDto entryList, ExitTransactionDto exitList1) {

		String query = LoadJpaQueries.getQueryById(UnmatchedConstant.UPDATE_MATCHED_TRAN);
		
		UpdateTranDetailDto updateTranDetail = new UpdateTranDetailDto();
		updateTranDetail.setLaneTxId(exitList1.getLaneTxId());
		updateTranDetail.setMatchedTxExternRefNo(entryList.getTxExternRefNo());
		updateTranDetail.setTxStatus(UnmatchedConstant.MATCHED_STATUS);
		updateTranDetail.setTxType(UnmatchedConstant.U);
		
		jdbcTemplate.update(query, updateTranDetail.getTxStatus(),updateTranDetail.getMatchedTxExternRefNo(),updateTranDetail.getTxType(),
				updateTranDetail.getLaneTxId());
		
		logger.info("::::::::::: updateMatched T_TRAN_DETAIL Table for Exit Successfully :::::::::::");
	}

	@Override
	public void updateEntryTransDetailTable(EntryTransactionDto entryList) {
		
		String query = LoadJpaQueries.getQueryById(UnmatchedConstant.UPDATE_MATCHED_TRAN);
		
		UpdateTranDetailDto updateTranDetail = new UpdateTranDetailDto();
		updateTranDetail.setLaneTxId(entryList.getLaneTxId());
		updateTranDetail.setMatchedTxExternRefNo(entryList.getMatchedTxExternRefNo());
		updateTranDetail.setTxStatus(UnmatchedConstant.DISCARD_STATUS);
		updateTranDetail.setTxType(UnmatchedConstant.D);
		
		jdbcTemplate.update(query, updateTranDetail.getTxStatus(),updateTranDetail.getMatchedTxExternRefNo(),updateTranDetail.getTxType(),
				updateTranDetail.getLaneTxId());
		
		logger.info("::::::::::: updateMatched T_TRAN_DETAIL Table for Entry Successfully :::::::::::");
	}

	@Override
	public void updateExpiredEntryTransaction(EntryTransactionDto entryTransactionDto) {

		String query = LoadJpaQueries.getQueryById(UnmatchedConstant.UPDATE_DISCARD_TX_ENTRY);
		entryTransactionDto.setTxStatus(UnmatchedConstant.EXPIRED_ENTRY_DISCARD_STATUS);
		entryTransactionDto.setTxTypeInd(UnmatchedConstant.D);
		jdbcTemplate.update(query, entryTransactionDto.getTxStatus(),entryTransactionDto.getTxTypeInd(),entryTransactionDto.getLaneTxId());
		
		logger.info("::::::::::: update discard Expired Entry transaction Successfully :::::::::::");
		
	}

	@Override
	public void updateExpiredEntryTransDetailTable(EntryTransactionDto expiredEntryList) {

		String query = LoadJpaQueries.getQueryById(UnmatchedConstant.UPDATE_EXPIRED_TRAN);
		
		UpdateTranDetailDto updateTranDetail = new UpdateTranDetailDto();
		updateTranDetail.setLaneTxId(expiredEntryList.getLaneTxId());
		updateTranDetail.setTxStatus(UnmatchedConstant.EXPIRED_ENTRY_DISCARD_STATUS);
		updateTranDetail.setTxType(UnmatchedConstant.D);
		
		jdbcTemplate.update(query, updateTranDetail.getTxStatus(),updateTranDetail.getTxType(),
				updateTranDetail.getLaneTxId());
		
		logger.info("::::::::::: updateMatched T_TRAN_DETAIL Table for Expired Entry Successfully :::::::::::");
	}

	@Override
	public void updateExpiredExitTransaction(ExitTransactionDto expiredExitList) {

		String query = LoadJpaQueries.getQueryById(UnmatchedConstant.UPDATE_EXPIRED_EXIT);
		expiredExitList.setTxStatus(UnmatchedConstant.EXPIRED_EXIT_STATUS);
		jdbcTemplate.update(query, expiredExitList.getTxStatus(),expiredExitList.getLaneTxId());
		
		logger.info("::::::::::: update discard Expired Exit transaction Successfully :::::::::::");
	}

	@Override
	public void updateExpiredExitTransDetailTable(ExitTransactionDto expiredExitList, Double discountAmount) {

		String query = LoadJpaQueries.getQueryById(UnmatchedConstant.UPDATE_EXPIRED_EXIT_TRAN);
		
		UpdateTranDetailDto updateTranDetail = new UpdateTranDetailDto();
		updateTranDetail.setLaneTxId(expiredExitList.getLaneTxId());
		updateTranDetail.setTxStatus(UnmatchedConstant.EXPIRED_EXIT_STATUS);
		updateTranDetail.setTxType(UnmatchedConstant.U);
		//updateTranDetail.setEtcFareAmount(discountAmount);//commented as PB & Nived suggested for UAT Bug 71487
		
		
		jdbcTemplate.update(query, updateTranDetail.getTxStatus(),updateTranDetail.getTxType(),updateTranDetail.getLaneTxId());
		
		logger.info("::::::::::: updateMatched T_TRAN_DETAIL Table for Expired Exit Successfully :::::::::::");
	}

	@Override
	public void updateEntryTransDetailTableForMatchedRefrence(EntryTransactionDto entryList,
			ExitTransactionDto exitList) {

		String query = LoadJpaQueries.getQueryById(UnmatchedConstant.UPDATE_MATCHED_TRAN);
		
		UpdateTranDetailDto updateTranDetail = new UpdateTranDetailDto();
		updateTranDetail.setLaneTxId(entryList.getLaneTxId());
		updateTranDetail.setMatchedTxExternRefNo(exitList.getTxExternRefNo());
		updateTranDetail.setTxStatus(UnmatchedConstant.DISCARD_STATUS);
		updateTranDetail.setTxType(UnmatchedConstant.D);
		
		jdbcTemplate.update(query, updateTranDetail.getTxStatus(),updateTranDetail.getMatchedTxExternRefNo(),updateTranDetail.getTxType(),
				updateTranDetail.getLaneTxId());
		
		logger.info("::::::::::: updateMatched T_TRAN_DETAIL Table for For TxRefNo Successfully :::::::::::");
		
	}

	@Override
	public void updateEntryStatusAndTxTypeForMatchedRefrence(EntryTransactionDto entryTransactionDto,
			ExitTransactionDto exitTransactionDto, Map<String, String> reportsMap) {

		Integer count = 0;
		String query = LoadJpaQueries.getQueryById(UnmatchedConstant.UPDATE_DISCARD_TRAN_FOR_REF);
		entryTransactionDto.setTxStatus(UnmatchedConstant.DISCARD_STATUS);
		entryTransactionDto.setMatchedTxExternRefNo(exitTransactionDto.getTxExternRefNo());
		entryTransactionDto.setTxTypeInd(UnmatchedConstant.D);
		jdbcTemplate.update(query, entryTransactionDto.getTxStatus(),entryTransactionDto.getTxTypeInd(),entryTransactionDto.getMatchedTxExternRefNo(),
				entryTransactionDto.getLaneTxId());
		
		logger.info("::::::::::: updateMatched Discard Entry Successfully for corssMatched :::::::::::");
		
		count++;
		
		reportsMap.put("Entry Matched Discard Count", count.toString());
		
	}

	@Override
	public void updateEntryTransDetailForDiscard(EntryTransactionDto entryList, ViolTxDto violTxDto) {
		// TODO Auto-generated method stub
		String query = LoadJpaQueries.getQueryById(UnmatchedConstant.UPDATE_MATCHED_TRAN);
		
		UpdateTranDetailDto updateTranDetail = new UpdateTranDetailDto();
		updateTranDetail.setLaneTxId(entryList.getLaneTxId());
		updateTranDetail.setMatchedTxExternRefNo(violTxDto.getTxExternRefNo());
		updateTranDetail.setTxStatus(UnmatchedConstant.DISCARD_STATUS);
		updateTranDetail.setTxType(UnmatchedConstant.D);
		
		jdbcTemplate.update(query, updateTranDetail.getTxStatus(),updateTranDetail.getMatchedTxExternRefNo(),updateTranDetail.getTxType(),
				updateTranDetail.getLaneTxId());
		
		logger.info("::::::::::: updateMatched T_TRAN_DETAIL Table for plate-device crossmatch Successfully :::::::::::");
	}

	@Override
	public void updateEntryDataToDiscardForPlate(EntryTransactionDto entryTransactionDto, ViolTxDto violTxDto1,
			Map<String, String> reportsMap) {
		
		Integer count = 0;
		String query = LoadJpaQueries.getQueryById(UnmatchedConstant.UPDATE_DISCARD_TRAN_FOR_REF);
		entryTransactionDto.setTxStatus(UnmatchedConstant.DISCARD_STATUS);
		entryTransactionDto.setMatchedTxExternRefNo(violTxDto1.getTxExternRefNo());
		entryTransactionDto.setTxTypeInd(UnmatchedConstant.D);
		jdbcTemplate.update(query, entryTransactionDto.getTxStatus(),entryTransactionDto.getTxTypeInd(),entryTransactionDto.getMatchedTxExternRefNo(),
				entryTransactionDto.getLaneTxId());
		
		logger.info("::::::::::: update discard status in plate-device crossmatched for entry transaction Successfully :::::::::::");
		
		count++;
		
		reportsMap.put("Matched Plate Entry Discard Count", count.toString());
		
	}

	@Override
	public Long getEtcAccountId(String plateState, String plateNumber, LocalDate txDate) {

		logger.info("=====plate matching ================== #A");
		String queryString = LoadJpaQueries.getQueryById(UnmatchedConstant.GET_ETC_ACC_FROM_PLATE);
		logger.info("=====plate matching================== #B");
		String tempTxDate = txDate != null ? String.valueOf(txDate) : null;
		logger.info("tempTxDate {}",tempTxDate);
		logger.info("=====plate matching================== #C");
		MapSqlParameterSource paramSource = new MapSqlParameterSource();

		paramSource.addValue("TX_DATE", tempTxDate);
		paramSource.addValue("PLATE_STATE", plateState);
		paramSource.addValue("PLATE_NUMBER", plateNumber);

		List<PlateInfoDto> list = namedJdbcTemplate.query(queryString, paramSource,
				BeanPropertyRowMapper.newInstance(PlateInfoDto.class));

		if (!list.isEmpty()) {
			logger.info("List size:" + list.size());
			return list.get(0).getEtcAccountId();
		}
		else {
		logger.info("List is Empty");

		return 0L;
		}
	}
	
}
