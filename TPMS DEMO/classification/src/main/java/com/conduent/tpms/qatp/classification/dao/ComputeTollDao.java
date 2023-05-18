package com.conduent.tpms.qatp.classification.dao;

import java.time.LocalDate;
import java.util.List;

import com.conduent.tpms.qatp.classification.dto.CommonClassificationDto;
import com.conduent.tpms.qatp.classification.dto.TUnmatchedTx;
import com.conduent.tpms.qatp.classification.dto.TollScheduleDto;
import com.conduent.tpms.qatp.classification.dto.TransactionDto;
import com.conduent.tpms.qatp.classification.model.TCode;
import com.conduent.tpms.qatp.classification.model.TollPostLimit;
import com.conduent.tpms.qatp.classification.model.TollPriceSchedule;
import com.conduent.tpms.qatp.classification.utility.MasterDataCache;

/**
 * ComputeToll Dao interface.
 *
 */
public interface ComputeTollDao {

	public TollPriceSchedule getPriceSchedule(LocalDate effectiveDate, LocalDate expiryDate, String daysInd,
			Integer agencyId);

	public TollScheduleDto getTollAmount(LocalDate expiryDate, Integer plazaId, Integer entryPlazaId,
			Integer vehicleClass, Integer revenueTypeId, Long planTypeId, Integer priceScheduleId);

	public List<TollScheduleDto> getTollAmountByRevenueType(LocalDate expiryDate, Integer plazaId, Integer entryPlazaId,
			Integer vehicleClass, List<String> revenueTypeIdList, Long planTypeId, Integer priceScheduleId);

	public void insertInToHomeEtcQueue(CommonClassificationDto commonDTO);

	public void updateTTransDetail(TransactionDto transactionDto);
	
	public void getQtpStatitics(TransactionDto transactionDto);
	
	public void updateQtpStatitics(TransactionDto transactionDto);

	public void insertInToViolQueue(CommonClassificationDto commonDTO);
	
	public List<TollPriceSchedule> getAllPriceSchedule();
	
	public List<TollScheduleDto> getAllTollSchedule();

	/**
	 *  record inserted into T_UNMATCHED_ENTRY_TX table
	 */
	public void insertIntoTUnmatchedEntryTx(TUnmatchedTx unMatchedTx);

	/**
	 *  record inserted into T_UNMATCHED_EXIT_TX table
	 */
	public void insertIntoTUnmatchedExitTx(TUnmatchedTx unMatchedTx);

	public Integer NY12Plan(String deviceNo, String txDate);
	
	public void updateTTransDetailforNY12(TransactionDto transactionDto);

	public List<TollPostLimit> getTollPostLimit();

	public void insertToTEPSINTXLog(TransactionDto tempTxDto, MasterDataCache masterDataCache);

	public void insertToTEPSFTOLLog(TransactionDto tempTxDto);
}
