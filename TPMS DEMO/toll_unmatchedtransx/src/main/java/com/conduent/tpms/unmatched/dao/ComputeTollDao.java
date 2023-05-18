package com.conduent.tpms.unmatched.dao;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import com.conduent.tpms.unmatched.dto.CommonUnmatchedDto;
import com.conduent.tpms.unmatched.dto.DeviceList;
import com.conduent.tpms.unmatched.dto.EntryTransactionDto;
import com.conduent.tpms.unmatched.dto.ExitTransactionDto;
import com.conduent.tpms.unmatched.dto.ImageReviewDto;
import com.conduent.tpms.unmatched.dto.PlateInfoDto;
import com.conduent.tpms.unmatched.dto.TProcessParamterDto;
import com.conduent.tpms.unmatched.dto.TollScheduleDto;
import com.conduent.tpms.unmatched.dto.TransactionDto;
import com.conduent.tpms.unmatched.dto.ViolTxDto;
import com.conduent.tpms.unmatched.model.TollPriceSchedule;



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

	public void insertInToHomeEtcQueue(CommonUnmatchedDto commonDTO);

	public void updateTTransDetail(TransactionDto transactionDto);
	
	public void getQtpStatitics(TransactionDto transactionDto);
	
	public void updateQtpStatitics(TransactionDto transactionDto);

	public void insertInToViolQueue(CommonUnmatchedDto commonDTO);

	public List<TProcessParamterDto> getProcessParamtersList();

	public List<ExitTransactionDto> getExitData(String startProcessDate, String endProcessDate);
	
	public List<EntryTransactionDto> getDeviceEntryData(String deviceNo, Integer laneId, Integer plazaAgencyId,  String txTimeStamp);

	public void updateMatchedDeviceNumberTxEntry(EntryTransactionDto entryList, ExitTransactionDto exitList1, Map<String,String> reportsMap);

	public void updateMatchedDeviceNumberTxExit(ExitTransactionDto exitList1, EntryTransactionDto entryList, Map<String,String> reportsMap);
	

	public void updateEntryStatusAndTxType(EntryTransactionDto entryList, Map<String,String> reportsMap);

	public List<ViolTxDto> getVoilTxData(String sectionId, String agencyId, LocalDate txDate) throws ParseException;

	public List<EntryTransactionDto> getMachingPlateEntryData(String agencyId, String sectionId, Integer laneId,
			String txTimeStamp, LocalDate txDate, String plateState, String plateNumber);

	public List<TProcessParamterDto> getImageTrxProcessParamtersList();

	public List<ImageReviewDto> getImageTrxParamConfig(String startProcessDate, String endProcessDate) throws ParseException;

	public void updateMatchedPlateNumberTxEntry(EntryTransactionDto entryList, ViolTxDto voilTxDto1, Map<String,String> reportsMap);

	public void updateMatchedPlateNumberVoilTx(ViolTxDto voilTxDto1, EntryTransactionDto entryList);

	public void updateEntryDataToDiscard(EntryTransactionDto entryList, Map<String,String> reportsMap);

	public Integer NY12Plan(String deviceNo, String trxDate);

	public Long getEtcAccountId(String deviceNo);

	public List<PlateInfoDto> getPlateInfo(Long etcAccountId1, LocalDate txDate);

	public List<EntryTransactionDto> getMachingPlateFromDevice(Integer plazaAgencyId, Integer laneId,
			String txTimeStamp, LocalDate txDate, String plateState, String plateNumber, String plateCountry, Long plateType);

	public void updateMatchedPlateNumberTxExit(ExitTransactionDto exitList1, EntryTransactionDto entryPlateList, Map<String,String> reportsMap);

	public List<TProcessParamterDto> getCrossMatchedProcessParamter();

	public List<DeviceList> getDeviceUsingEtcAccountID(Long etcAccountId);

	public List<PlateInfoDto> getEtcAccountIdFromPlate(String plateState, String plateNumber, LocalDate txDate);

	public List<EntryTransactionDto> getMatchingDeviceFromPlate(String deviceNo, Integer laneId, Integer plazaAgencyId,
			String txTimeStamp);

	public List<EntryTransactionDto> getExpiredEntryList(String tempEndDate);

	public List<ExitTransactionDto> getExpiredExitList(String tempEndDate);
	
	public List<ViolTxDto> getExpiredViolList(String tempEndDate);
	
	public List<ViolTxDto> getExpiredViolList(String tempEndDate, String agencyId);

	public String getMaxEndDate();

	public void updateTTransDetailTable(EntryTransactionDto entryList, ExitTransactionDto exitList1);

	public void updateEntryTransDetailTable(EntryTransactionDto entryList);

	public void updateTTransDetailTableVIolTx(EntryTransactionDto entryList, ViolTxDto violTxDto1);

	public void updateExpiredEntryTransaction(EntryTransactionDto expiredEntryList);

	public void updateExpiredEntryTransDetailTable(EntryTransactionDto expiredEntryList);

	public void updateExpiredExitTransaction(ExitTransactionDto expiredExitList);

	public void updateExpiredExitTransDetailTable(ExitTransactionDto expiredExitList, Double discountAmount);

	public void updateEntryTransDetailTableForMatchedRefrence(EntryTransactionDto entryPlateList,
			ExitTransactionDto exitTransactionDto);

	public void updateEntryStatusAndTxTypeForMatchedRefrence(EntryTransactionDto entryPlateList,
			ExitTransactionDto exitTransactionDto, Map<String, String> reportsMap);

	public void updateEntryTransDetailForDiscard(EntryTransactionDto entryList1, ViolTxDto violTxDto1);

	public void updateEntryDataToDiscardForPlate(EntryTransactionDto entryList1, ViolTxDto violTxDto1,
			Map<String, String> reports);

	public Long getEtcAccountId(String plateState, String plateNumber, LocalDate txDate1);

	


}
