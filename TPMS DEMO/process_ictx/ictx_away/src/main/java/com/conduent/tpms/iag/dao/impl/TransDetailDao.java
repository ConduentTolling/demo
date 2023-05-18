package com.conduent.tpms.iag.dao.impl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.conduent.app.timezone.utility.TimeZoneConv;
//import com.conduent.app.timezone.utility.TimeZoneConv;
import com.conduent.tpms.iag.config.LoadJpaQueries;
import com.conduent.tpms.iag.constants.Constants;
import com.conduent.tpms.iag.constants.ICTXConstants;
import com.conduent.tpms.iag.dao.ITransDetailDao;
import com.conduent.tpms.iag.dto.AckFileInfoDto;
import com.conduent.tpms.iag.model.QatpStatistics;
import com.conduent.tpms.iag.model.TranDetail;
import com.conduent.tpms.iag.utility.DateUtils;

@Repository
public class TransDetailDao implements ITransDetailDao 
{

	private static final Logger log = LoggerFactory.getLogger(TransDetailDao.class);
	@Autowired
	private NamedParameterJdbcTemplate namedJdbcTemplate;

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private TimeZoneConv timeZoneConv;


	//Not called
	@Override
	public void saveTransDetail(TranDetail tranDetail) 
	{
		//CREATE SEQUENCE SEQ_LANE_TX_ID START WITH 1 INCREMENT BY 1 NOCACHE NOCYCLE;
		String query = LoadJpaQueries.getQueryById("INSERT_T_TRAN_DETAIL");
		log.info("saveTransDetail query {}",query);
		MapSqlParameterSource paramSource = null;
		DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern( "yyyyMMddHHmmss" ) ;
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern( "yyyyMMdd" ) ;
		tranDetail.setLaneTxId(loadNextSeq(1).get(0));
		log.info("LaneTxId {}",tranDetail.getLaneTxId());

		paramSource = new MapSqlParameterSource();
		paramSource.addValue(Constants.LANE_TX_ID,tranDetail.getLaneTxId());
		paramSource.addValue(Constants.TX_EXTERN_REF_NO,tranDetail.getTxExternRefNo());
		paramSource.addValue(Constants.TX_TYPE,tranDetail.getTxType());
		paramSource.addValue(Constants.TX_SUB_TYPE,tranDetail.getTxSubType());
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
		
		LocalDate localDate = LocalDate.parse( tranDetail.getTxDate(), formatter ) ;
		tranDetail.setTxDate(localDate.toString());
		paramSource.addValue(Constants.TX_DATE,tranDetail.getTxDate());
		
		//LocalDateTime TxTimeStamp = LocalDateTime.parse(tranDetail.getTxTimestamp(), formatter1 ) ;
	//	DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MMM-yyyy HH.mm.ss");
		//String formattedDate = TxTimeStamp.format(myFormatObj);
	//	tranDetail.setTxTimestamp(formattedDate);
		
		paramSource.addValue(Constants.TX_TIMESTAMP,tranDetail.getTxTimestamp());
		paramSource.addValue(Constants.LANE_MODE,tranDetail.getLaneMode());
		paramSource.addValue(Constants.LANE_STATE,tranDetail.getLaneState());
		paramSource.addValue(Constants.TRX_LANE_SERIAL,tranDetail.getLaneSn());
		paramSource.addValue(Constants.DEVICE_NO,tranDetail.getDeviceNo());
		paramSource.addValue(Constants.AVC_CLASS,tranDetail.getDeviceCodedClass());
		paramSource.addValue(Constants.TAG_IAG_CLASS,tranDetail.getDeviceIagClass());
		paramSource.addValue(Constants.TAG_CLASS,tranDetail.getDeviceAgencyClass());
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
		paramSource.addValue(Constants.PROG_STAT, 0);//tranDetail.getProgStat());
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
		paramSource.addValue(Constants.EXT_FILE_ID,tranDetail.getExternFileId());
		paramSource.addValue(Constants.RECEIVED_DATE,tranDetail.getReceivedDate());
		paramSource.addValue(Constants.ACCOUNT_TYPE,tranDetail.getAccountType());
		paramSource.addValue(Constants.ACCT_AGENCY_ID,tranDetail.getAccountAgencyId());
		paramSource.addValue(Constants.ETC_ACCOUNT_ID,tranDetail.getEtcAccountId());
		paramSource.addValue(Constants.ETC_SUBACCOUNT,tranDetail.getEtcSubAccount());
		paramSource.addValue(Constants.DST_FLAG,tranDetail.getDstFlag());
		paramSource.addValue(Constants.IS_PEAK,tranDetail.getIsPeak());
		paramSource.addValue(Constants.FARE_TYPE,tranDetail.getFareType());
		paramSource.addValue(Constants.UPDATE_TS,tranDetail.getUpdateTs());
//		paramSource.addValue(Constants.UPDATE_TS,timeZoneConv.currentTime());
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
	
		namedJdbcTemplate.update(query, paramSource);
		log.info("saveTransDetail query has been executed successfully");

	}

	
	
	public void batchInsert(List<TranDetail> txlist) 
	{
		String query = LoadJpaQueries.getQueryById("INSERT_T_TRAN_DETAIL");
		log.info("TranDetail-size: {}",txlist.size());
		//List<Map<String,Object>> paramSourceList = new ArrayList<>(txlist.size());
		log.info("Generating ATP file ID..");
//		String atpFileId= loadNextAtpFileId();
//		log.info("ATPFileId: {}",atpFileId);
		
//		DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern( "yyyyMMddHHmmss" ) ;
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern( "yyyyMMdd" ) ;
		Map<String,Object> paramSource = new HashMap<String,Object>();
		try {
			for(TranDetail tranDetail : txlist )
			{
				//In case of correction file we will LaneTxId already present
				if(tranDetail.getCorrReasonId() == null || tranDetail.getLaneTxId() == null) {
					tranDetail.setLaneTxId(loadNextSeq(1).get(0));
				}
				
				log.info("LaneTxId {}",tranDetail.getLaneTxId());
				paramSource.put(Constants.LANE_TX_ID,tranDetail.getLaneTxId());
				paramSource.put(Constants.TX_EXTERN_REF_NO,tranDetail.getTxExternRefNo());
				paramSource.put(Constants.TX_TYPE,tranDetail.getTxType());
				paramSource.put(Constants.TX_SUB_TYPE,tranDetail.getTxSubType());
				paramSource.put(Constants.TOLL_SYSTEM_TYPE,tranDetail.getTollSystemType());
				paramSource.put(Constants.TOLL_REVENUE_TYPE,tranDetail.getTollRevenueType());
			
				paramSource.put(Constants.ENTRY_TX_TIMESTAMP,tranDetail.getEntryTxTimeStamp());
				paramSource.put(Constants.ENTRY_PLAZA_ID,tranDetail.getEntryPlazaId());
				paramSource.put(Constants.ENTRY_LANE_ID,tranDetail.getEntryLaneId());
				paramSource.put(Constants.ENTRY_TX_SEQ_NUMBER,tranDetail.getEntryTxSeqNumber());
				paramSource.put(Constants.ENTRY_DATA_SOURCE,tranDetail.getEntryDataSource());
				paramSource.put(Constants.ENTRY_VEHICLE_SPEED,tranDetail.getEntryVehicleSpeed());
				paramSource.put(Constants.PLAZA_AGENCY_ID,tranDetail.getPlazaAgencyId());
				paramSource.put(Constants.PLAZA_ID,tranDetail.getPlazaId());
				paramSource.put(Constants.LANE_ID,tranDetail.getLaneId());
				
				paramSource.put(Constants.TX_DATE,null);
				if(tranDetail.getTxDate() != null) {
				System.out.println(tranDetail.getTxDate());
				LocalDate localDate = LocalDate.parse(tranDetail.getTxDate(), formatter) ;
				DateTimeFormatter myFormatDate = DateTimeFormatter.ofPattern("dd-MMM-yy");
				String formattedTxDate = localDate.format(myFormatDate);
				paramSource.put(Constants.TX_DATE,formattedTxDate);
				
				String newDate = DateUtils.getDateYYYYMMDD(tranDetail.getTxDate(), Constants.dateFormator);
				tranDetail.setTxDate(newDate);
				}
				
				paramSource.put(Constants.TX_TIMESTAMP,null);
				if(tranDetail.getTxTimestamp() != null) {
				log.info("tranDetail.getTxTimestamp():{}",tranDetail.getTxTimestamp());
				paramSource.put(Constants.TX_TIMESTAMP,tranDetail.getTxTimestamp());
				}
				paramSource.put(Constants.LANE_MODE,tranDetail.getLaneMode());
				paramSource.put(Constants.LANE_STATE,tranDetail.getLaneState());
				paramSource.put(Constants.TRX_LANE_SERIAL,tranDetail.getLaneSn());
				paramSource.put(Constants.DEVICE_NO,tranDetail.getDeviceNo());
				paramSource.put(Constants.AVC_CLASS,tranDetail.getDeviceCodedClass());
				paramSource.put(Constants.TAG_IAG_CLASS,tranDetail.getDeviceIagClass());
				paramSource.put(Constants.TAG_CLASS,tranDetail.getDeviceAgencyClass());
				paramSource.put(Constants.TAG_AXLES,tranDetail.getTagAxles());
				paramSource.put(Constants.ACTUAL_CLASS,tranDetail.getActualClass());
				paramSource.put(Constants.ACTUAL_AXLES,tranDetail.getActualAxles());
				paramSource.put(Constants.EXTRA_AXLES,tranDetail.getExtraAxles());
				paramSource.put(Constants.PLATE_STATE,tranDetail.getPlateState());
				paramSource.put(Constants.PLATE_NUMBER,tranDetail.getPlateNumber());
				paramSource.put(Constants.PLATE_TYPE,tranDetail.getPlateType());
				paramSource.put(Constants.PLATE_COUNTRY,tranDetail.getPlateCountry());
				paramSource.put(Constants.READ_PERF,tranDetail.getReadPerf());
				paramSource.put(Constants.WRITE_PERF,tranDetail.getWritePerf());
				paramSource.put(Constants.PROG_STAT,tranDetail.getProgStat());
				paramSource.put(Constants.COLLECTOR_NUMBER,tranDetail.getCollectorNumber());
				paramSource.put(Constants.IMAGE_CAPTURED,tranDetail.getImageCaptured());
				paramSource.put(Constants.VEHICLE_SPEED,tranDetail.getVehicleSpeed());
				paramSource.put(Constants.SPEED_VIOLATION,tranDetail.getSpeedViolation());
				paramSource.put(Constants.RECIPROCITY_TRX,tranDetail.getReciprocityTrx());
				paramSource.put(Constants.IS_VIOLATION,tranDetail.getIsViolation());
				//paramSource.put(Constants.IS_LPR_ENABLED,tranDetail.getIsLprEnabled());
				paramSource.put(Constants.FULL_FARE_AMOUNT,tranDetail.getFullFareAmount());
				paramSource.put(Constants.DISCOUNTED_AMOUNT,tranDetail.getDiscountedAmount());
				paramSource.put(Constants.UNREALIZED_AMOUNT,tranDetail.getUnrealizedAmount());
				paramSource.put(Constants.EXT_FILE_ID,tranDetail.getExternFileId());
				paramSource.put(Constants.RECEIVED_DATE,tranDetail.getReceivedDate());
				paramSource.put(Constants.ACCOUNT_TYPE,tranDetail.getAccountType());
				log.info("Setting AccountAgencyId {} for EtcAccountId {}",tranDetail.getAccountAgencyId(), tranDetail.getEtcAccountId());
				paramSource.put(Constants.ACCT_AGENCY_ID,tranDetail.getAccountAgencyId());
				paramSource.put(Constants.ETC_ACCOUNT_ID,tranDetail.getEtcAccountId());
				paramSource.put(Constants.ETC_SUBACCOUNT,tranDetail.getEtcSubAccount());
				paramSource.put(Constants.DST_FLAG,tranDetail.getDstFlag());
				paramSource.put(Constants.IS_PEAK,tranDetail.getIsPeak());
				paramSource.put(Constants.FARE_TYPE,tranDetail.getFareType());
				paramSource.put(Constants.UPDATE_TS,timeZoneConv.currentTime());
				paramSource.put(Constants.LANE_DATA_SOURCE,tranDetail.getLaneDataSource());
				paramSource.put(Constants.LANE_TYPE,tranDetail.getLaneType());
				paramSource.put(Constants.LANE_HEALTH,tranDetail.getLaneHealth());
				paramSource.put(Constants.AVC_AXLES,tranDetail.getAvcAxles());
				paramSource.put(Constants.TOUR_SEGMENT_ID,tranDetail.getTourSegment());
				paramSource.put(Constants.BUF_READ,tranDetail.getBufRead());
				paramSource.put(Constants.READER_ID,tranDetail.getReaderId());
				paramSource.put(Constants.TOLL_AMOUNT,tranDetail.getTollAmount());
				paramSource.put(Constants.DEBIT_CREDIT,tranDetail.getDebitCredit());
				paramSource.put(Constants.ETC_VALID_STATUS,tranDetail.getEtcValidStatus());
				paramSource.put(Constants.DISCOUNTED_AMOUNT_2,tranDetail.getDiscountedAmount2());
				paramSource.put(Constants.VIDEO_FARE_AMOUNT,tranDetail.getVideoFareAmount());
				paramSource.put(Constants.PLAN_TYPE,tranDetail.getPlanType());
				paramSource.put(Constants.RESERVED,tranDetail.getReserved());
				paramSource.put(Constants.ATP_FILE_ID,getAtPFileIdFromTranDetail(tranDetail));
				paramSource.put(Constants.EXPECTED_REVENUE_AMOUNT,tranDetail.getExpectedRevenueAmount());
				paramSource.put(Constants.CASH_FARE_AMOUNT,tranDetail.getCashFareAmount());
				paramSource.put(Constants.POSTED_FARE_AMOUNT,tranDetail.getPostedFareAmount());
				paramSource.put(Constants.ETC_FARE_AMOUNT,tranDetail.getExpectedRevenueAmount());
				paramSource.put(Constants.TRANSACTION_INFO,tranDetail.getTransactionInfo());
				paramSource.put(Constants.PLAN_CHARGED,tranDetail.getPlanCharged());
				paramSource.put(Constants.TX_STATUS,tranDetail.getTxStatus());
				paramSource.put(Constants.POSTED_DATE,tranDetail.getPostedDate());
				paramSource.put(Constants.DEPOSIT_ID,tranDetail.getDepositId());
				
				log.info("Inserting data for record {}",tranDetail);	
				namedJdbcTemplate.update(query, paramSource);
			//	paramSourceList.add(paramSource);
			}
		} catch (DataAccessException e) {
			log.error("DataAccessException for record data {}", paramSource);
			log.error("{}",e); //ENTRY_TX_TIMESTAMP
			e.printStackTrace();
		} catch (Exception e) {
			log.error("Exception for record data {}", paramSource);
			log.error("{}",e);
		}
/*	
		Map<String, Object>[] maps = new HashMap[paramSourceList.size()];
        Map<String, Object>[] batchValues = (Map<String, Object>[]) paramSourceList.toArray(maps);
        System.out.println("batchValues"+batchValues);
        namedJdbcTemplate.batchUpdate(query, batchValues); */
        
		log.info("Batch update TranDetail exit ");	
	}

	
	public String loadNextAtpFileId() 
	{
		String query = LoadJpaQueries.getQueryById("GET_ATP_FILE_ID");
		@SuppressWarnings("deprecation")
		String result = (String) jdbcTemplate.queryForObject(
				 query, new Object[] {  }, String.class);
		return result;
	}
	
	private Long getAtPFileIdFromTranDetail(TranDetail tranDetail) {
		String fileType = ("I".equals(tranDetail.getTxType()))? "ECTX" : "REJC";
		Long xferControlId =  tranDetail.getExternFileId();
		
		return checkAtpFileInStatistics(fileType,xferControlId);
	}
	@Override
	public Long checkAtpFileInStatistics(String fileType, Long xferControlId) {
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		String queryFile = LoadJpaQueries.getQueryById(ICTXConstants.CHECK_ATP_FILE_ID_IN_T_QATP_STATISTICS);
		
		
		
		paramSource.addValue(ICTXConstants.FILE_TYPE, fileType);
		paramSource.addValue(ICTXConstants.XFER_CONTROL_ID, xferControlId);
		
		List<QatpStatistics> val = namedJdbcTemplate.query(queryFile,paramSource,BeanPropertyRowMapper.newInstance(QatpStatistics.class));
		if (val.isEmpty()) {
			return null;
		} else {
			return val.get(0).getAtpFileId();
		}
		
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



	public Long getLaneTxIdFromSerialNo(String txExternRefNo) {
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		String queryFile = LoadJpaQueries.getQueryById(ICTXConstants.CHECK_LANE_TX_ID_IN_T_TRAN_DETAIL);
		
		paramSource.addValue(Constants.TX_EXTERN_REF_NO, txExternRefNo);
		
		List<TranDetail> val = namedJdbcTemplate.query(queryFile,paramSource,BeanPropertyRowMapper.newInstance(TranDetail.class));
		if (val.isEmpty()) {
			return null;
		} else {
			return val.get(0).getLaneTxId();
		}
	}

}
