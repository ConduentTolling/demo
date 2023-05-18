package com.conduent.tpms.unmatched.serviceImpl;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.conduent.tpms.unmatched.constant.UnmatchedConstant;
import com.conduent.tpms.unmatched.dao.ComputeTollDao;
import com.conduent.tpms.unmatched.dto.DeviceList;
import com.conduent.tpms.unmatched.dto.EntryTransactionDto;
import com.conduent.tpms.unmatched.dto.ExitTransactionDto;
import com.conduent.tpms.unmatched.dto.ImageReviewDto;
import com.conduent.tpms.unmatched.dto.MaxTollDataDto;
import com.conduent.tpms.unmatched.dto.NY12Dto;
import com.conduent.tpms.unmatched.dto.PlateInfoDto;
import com.conduent.tpms.unmatched.dto.TProcessParamterDto;
import com.conduent.tpms.unmatched.dto.TollCalculationDto;
import com.conduent.tpms.unmatched.dto.TollCalculationResponseDto;
import com.conduent.tpms.unmatched.dto.TransactionDetailDto;
import com.conduent.tpms.unmatched.dto.TransactionDto;
import com.conduent.tpms.unmatched.dto.ViolTxDto;
import com.conduent.tpms.unmatched.dto.ViolTxUpdateDto;
import com.conduent.tpms.unmatched.model.ConfigVariable;
import com.conduent.tpms.unmatched.service.AccountInfoService;
import com.conduent.tpms.unmatched.service.DeviceMatchingService;
import com.conduent.tpms.unmatched.service.MessageQueueService;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;

/**
 * Common Classificaton Service Implementation
 * 
 * @author deepeshb
 *
 */
@Service
public class DeviceMatchingServiceImpl implements DeviceMatchingService {

	@Autowired
	private AccountInfoService accountInfoService;

	@Autowired
	private ComputeTollDao computeTollDAO;

	@Autowired
	MessageQueueService qatpClassificationService;

	@Autowired
	ConfigVariable configVariable;

	@Autowired
	private RestTemplate restTemplate;

	private static final Logger logger = LoggerFactory.getLogger(DeviceMatchingService.class);

	protected List<TProcessParamterDto> processparamDetailsForDevice = new ArrayList<>();
	protected List<TProcessParamterDto> processparamDetailsForPlate = new ArrayList<>();
	Optional<TProcessParamterDto> startProcessDate;
	Optional<TProcessParamterDto> endProcessDate;
	Optional<TProcessParamterDto> deviceEndProcessDate;
	Optional<TProcessParamterDto> deviceStartProcessDate;
	Optional<TProcessParamterDto> plateStartProcessDate;
	Optional<TProcessParamterDto> plateEndProcessDate;
	Optional<TProcessParamterDto> imageEndProcessDate;
	Optional<TProcessParamterDto> timeWindows;
	Optional<TProcessParamterDto> imagePercentage;
	Optional<TProcessParamterDto> crossmatched;

	@Override
	public void getUnmatchedTrxBasedOnDeviceNo() throws ParseException {

		try {
			processparamDetailsForDevice = computeTollDAO.getProcessParamtersList();
			getParamtersInfo(processparamDetailsForDevice);

			processparamDetailsForPlate = computeTollDAO.getImageTrxProcessParamtersList();
			getParamtersInfoForPlateNumber(processparamDetailsForPlate);
			
			logger.info("Calling Expired Transaction..");
			callExpiredTransaction(processparamDetailsForDevice, processparamDetailsForPlate);
		}
		catch(Exception e) {
			e.printStackTrace();
			logger.info("Getting Excption message while calling getUnmatchedTrxBasedOnDeviceNo method {}",e.getMessage());
		}
	}


	private void callExpiredTransaction(List<TProcessParamterDto> processparamDetailsForDevice, List<TProcessParamterDto> processparamDetailsForPlate) throws ParseException {

		deviceEndProcessDate = processparamDetailsForDevice.stream()
				.filter(e -> e.getParam_name().equals(UnmatchedConstant.UNMATCHED_END_PROCESS_DATE)).findAny();
		deviceStartProcessDate = processparamDetailsForDevice.stream()
				.filter(e -> e.getParam_name().equals(UnmatchedConstant.UNMATCHED_START_PROCESS_DATE)).findAny();
		plateStartProcessDate = processparamDetailsForPlate.stream()
				.filter(e -> e.getParam_name().equals(UnmatchedConstant.IMAGE_TXN_START_PROCESS_DATE)).findAny();
		plateEndProcessDate = processparamDetailsForPlate.stream()
				.filter(e -> e.getParam_name().equals(UnmatchedConstant.IMAGE_TXN_END_PROCESS_DATE)).findAny();

		String plateStartDate = plateStartProcessDate.get().getParam_value();
		String plateEndDate = plateEndProcessDate.get().getParam_value();
		String deviceEndDate = deviceEndProcessDate.get().getParam_value();
		String deviceStartDate = deviceStartProcessDate.get().getParam_value();

		logger.info("Plate Start Process Date ==> {}",plateStartDate);
		logger.info("Plate End Process Date ==> {}",plateEndDate);
		logger.info("Device Start Process Date ==> {}",deviceStartDate);
		logger.info("Device End Process Date ==> {}",deviceEndDate);
		
		Map<String,String> reportsMap=new HashMap<String,String>();
		getExpiredEntryTransaction(reportsMap);
		getExpiredExitTransaction(deviceEndDate, reportsMap);
		getExpiredViolTxTransaction(plateEndDate, reportsMap);

		/*
		List<ImageReviewDto> ImageReviewDto = computeTollDAO.getImageTrxParamConfig(plateStartDate, plateEndDate);

		if (ImageReviewDto!=null)
		{
			for (ImageReviewDto imageReviewDto : ImageReviewDto) {

				String agencyId = imageReviewDto.getAgencyId();

				try {
					//getExpiredEntryTransaction(reportsMap);
					//getExpiredExitTransaction(deviceEndDate, reportsMap);
					getExpiredViolTxTransaction(plateEndDate, agencyId,reportsMap);
					
				}catch(Exception e) {
					e.printStackTrace();
					logger.info("Excption message for Expired Tranasacation {}",e.getMessage());
				}
			}

			for(Map.Entry<String, String> m:reportsMap.entrySet()){  
				logger.info(m.getKey()+" ==== > "+m.getValue());  
			}
		}
		*/

	}


	private void getParamtersInfo(List<TProcessParamterDto> processparamDetails2) {

		startProcessDate = processparamDetails2.stream()
				.filter(e -> e.getParam_name().equals(UnmatchedConstant.UNMATCHED_START_PROCESS_DATE)).findAny();
		endProcessDate = processparamDetails2.stream()
				.filter(e -> e.getParam_name().equals(UnmatchedConstant.UNMATCHED_END_PROCESS_DATE)).findAny();

		logger.info("=====StartProcessDate- Device=====" + startProcessDate.get().getParam_value());
		logger.info("=====EndProcessDate -Device =====" + endProcessDate.get().getParam_value());

		String tempStartDate = startProcessDate.get().getParam_value();
		String tempEndDate = endProcessDate.get().getParam_value();

		Map<String,String> reportsMap=new HashMap<String,String>();

		try {
			List<ExitTransactionDto> exitList = computeTollDAO.getExitData(tempStartDate, tempEndDate);

			if (exitList != null) {

				for (ExitTransactionDto exitTransactionDto : exitList) {

					String deviceNo = exitTransactionDto.getDeviceNo();
					Integer laneId = exitTransactionDto.getLaneId();
					Integer plazaAgencyId = exitTransactionDto.getPlazaAgencyId();
					OffsetDateTime txTimeStamp1 = exitTransactionDto.getTxTimestamp();

					String txTimeStamp = txTimeStamp1.toString().replace("T", " ");
					logger.info("Exit txTimeStamp :: ==> " + txTimeStamp1.toString().replace("T", " "));
					logger.info("Exit Table Device :: ==> " + deviceNo);

					logger.info("=====device matched================== #1");

					List<EntryTransactionDto> machingDeviceList = computeTollDAO.getDeviceEntryData(deviceNo, laneId,
							plazaAgencyId, txTimeStamp);

					if (machingDeviceList != null) {
						logger.info("=====device matched================== #2");
						for (EntryTransactionDto entryList : machingDeviceList) {
							logger.info("Exit externaml Ref no :: ==> " + exitTransactionDto.getTxExternRefNo());
							logger.info("Exit LaneTxId :: ==> " + exitTransactionDto.getLaneTxId());

							if (entryList.getDeviceNo().equals(exitTransactionDto.getDeviceNo())) {
								
								logger.info("Matched Entry Device LaneTxId :: ==> " + entryList.getLaneTxId());
								logger.info("Matched Exit Device LaneTxId :: ==> " + exitTransactionDto.getLaneTxId());
								
								logger.info("=====device matched================== #3");

								computeTollDAO.updateMatchedDeviceNumberTxEntry(entryList, exitTransactionDto, reportsMap);
								logger.info("Entry externaml Ref no :: ==> " + entryList.getTxExternRefNo());
								
								

								logger.info("=====device matched================== #4");
								computeTollDAO.updateMatchedDeviceNumberTxExit(exitTransactionDto, entryList, reportsMap);

								logger.info("=====device matched================== #5");
								computeTollDAO.updateTTransDetailTable(entryList, exitTransactionDto);//update T_TRAN_DETAIL

								logger.info("=====device matched================== #6");

								TransactionDto tempTxDto = getDeviceTransactionDto(entryList, exitTransactionDto);

								TollCalculationResponseDto tollCalculationResponseDto = getTollCalculationForDevice(tempTxDto);

								logger.info("=====device matched================== #7");

								Double discountAmount = tollCalculationResponseDto.getDiscountFare();
								Double extraExcelCharge = tollCalculationResponseDto.getExtraAxleCharge();
								Integer actualExtraExcel = tempTxDto.getExtraAxles();

								Double etcfareAmount = discountAmount + actualExtraExcel * extraExcelCharge;

								//tempTxDto.setEtcFareAmount(etcfareAmount);
								tempTxDto.setPostedFareAmount(etcfareAmount);

								TransactionDetailDto transactionDetailDto = accountInfoService
										.getAccountInformation(tempTxDto);
								logger.info("=====device matched================== #8");
								NY12Dto ny12Dto = getNY12Details(tempTxDto);
								TransactionDto txDTO = transactionDetailDto.getTransactionDto();

								Integer count = computeTollDAO.NY12Plan(txDTO.getDeviceNo(), txDTO.getTxDate());
								if (count > 0) 
								{
									logger.info("calling NY plan API...");
									callInsertAPI(ny12Dto, "NY12"); // NY12 API call
									logger.info("=====device matched================== #9");
								}else if((tempTxDto.getPlazaId()!=null && tempTxDto.getPlazaId().equals(UnmatchedConstant.EXTERN_PLAZA_25)) ||
			                            (tempTxDto.getPlazaId()!=null && tempTxDto.getPlazaId().equals(UnmatchedConstant.EXTERN_PLAZA_25A)) ||
			                            (tempTxDto.getEntryPlazaId()!=null && tempTxDto.getEntryPlazaId().equals(UnmatchedConstant.EXTERN_PLAZA_25)) ||
			                            (tempTxDto.getEntryPlazaId()!=null && tempTxDto.getEntryPlazaId().equals(UnmatchedConstant.EXTERN_PLAZA_25A)))
			                    {
									logger.info("calling 25/25A API....."); // 25/25A API call
									callInsertAPI(ny12Dto, "25A");
									logger.info("=====device matched================== #10");
			                    } else 
			                    {
									//push message to ATP queue
									qatpClassificationService.pushMessage(transactionDetailDto);
									logger.info("=====device matched================== #11");
								}
								// update status discard for entry transaction
								computeTollDAO.updateEntryStatusAndTxType(entryList, reportsMap);
								logger.info("=====device matched================== #12");
								computeTollDAO.updateEntryTransDetailTable(entryList);//update T_TRAN_DETAIL
								logger.info("=====device matched================== #13");
								
								TransactionDto tempTxDto1 = getEntryExpiredTransactionDto(entryList); // call method for entryDiscard transaction send to ATP queue
								TransactionDetailDto transactionDetailDto1 = new TransactionDetailDto();
								transactionDetailDto1.setTransactionDto(tempTxDto1);		
								qatpClassificationService.pushMessage(transactionDetailDto1);
								logger.info("======== Discard Entry Transaction for Device Pushed to ATP queue ================== #14");
								
							}
						}
					}
					else {
						processparamDetails2 = computeTollDAO.getCrossMatchedProcessParamter();
						crossmatched = processparamDetails2.stream()
								.filter(e -> e.getParam_name().equals(UnmatchedConstant.UNMATCHED_CROSSMATCHED_ALLOWED)).findAny();
						logger.info("=====crossmatched=====" + crossmatched.get().getParam_value());
						String crossMatchedAllowed = crossmatched.get().getParam_value();

						Long etcAccountId = exitTransactionDto.getEtcAccountId();
						LocalDate txDate = exitTransactionDto.getTxDate();

						if (crossMatchedAllowed.equals("Y"))
						{
							if (etcAccountId == 0 || etcAccountId==null) {
								etcAccountId = computeTollDAO.getEtcAccountId(deviceNo);
								logger.info("=====device cross matched================== #1");
							}
							List<PlateInfoDto> plateInfoList = computeTollDAO.getPlateInfo(etcAccountId, txDate);
							if (plateInfoList != null) {
								logger.info("=====device cross matched================== #2");
								for (PlateInfoDto plateInfoDto : plateInfoList) {
									String plateNumber = plateInfoDto.getPlateNumber();
									String plateState = plateInfoDto.getPlateState();
									String plateCountry = plateInfoDto.getPlateCountry();
									Long plateType =  plateInfoDto.getPlateType();

									exitTransactionDto.setPlateNumber(plateNumber);
									exitTransactionDto.setPlateState(plateState);
									exitTransactionDto.setPlateCountry(plateCountry);

									List<EntryTransactionDto> machingPlateList = computeTollDAO
											.getMachingPlateFromDevice(plazaAgencyId, laneId, txTimeStamp,
													txDate, plateState, plateNumber, plateCountry, plateType);
									if (machingPlateList != null) {
										logger.info("=====device cross matched================== #3");
										for (EntryTransactionDto entryPlateList : machingPlateList) {

											if ((entryPlateList.getPlateNumber().equals(exitTransactionDto.getPlateNumber()))
													&&(entryPlateList.getPlateState().equals(exitTransactionDto.getPlateState()))) {
												
												logger.info("Matched Entry Device CrossMatched LaneTxId :: ==> " + entryPlateList.getLaneTxId());
												logger.info("Matched Exit Device CrossMatched LaneTxId :: ==> " + exitTransactionDto.getLaneTxId());
												
												logger.info("=====device cross matched================== #4");
												//call for update exit matched status
												computeTollDAO.updateMatchedPlateNumberTxExit(exitTransactionDto,entryPlateList,reportsMap);
												logger.info("=====device cross matched================== #5");
												computeTollDAO.updateTTransDetailTable(entryPlateList, exitTransactionDto);//update T_TRAN_DETAIL
												logger.info("=====device cross matched================== #6");
												//Push Message to ATP queue
												TransactionDto tempTxDto = getDeviceTransactionDto(entryPlateList, exitTransactionDto);

												TollCalculationResponseDto tollCalculationResponseDto = getTollCalculationForDevice(tempTxDto); 
												logger.info("=====device cross matched================== #7");
												Double discountAmount = tollCalculationResponseDto.getDiscountFare();
												Double extraExcelCharge = tollCalculationResponseDto.getExtraAxleCharge();
												Integer actualExtraExcel = tempTxDto.getExtraAxles();

												Double etcfareAmount = discountAmount + actualExtraExcel * extraExcelCharge;

												//tempTxDto.setEtcFareAmount(etcfareAmount);
												tempTxDto.setPostedFareAmount(etcfareAmount);

												TransactionDetailDto transactionDetailDto = accountInfoService
														.getAccountInformation(tempTxDto);
												qatpClassificationService.pushMessage(transactionDetailDto);
												logger.info("=====device cross matched================== #8");

												//call for discard entry
												computeTollDAO.updateEntryStatusAndTxTypeForMatchedRefrence(entryPlateList, exitTransactionDto,reportsMap);
												logger.info("=====device cross matched================== #9");
												computeTollDAO.updateEntryTransDetailTableForMatchedRefrence(entryPlateList, exitTransactionDto);//update T_TRAN_DETAIL
												logger.info("=====device cross matched================== #10");
												
												TransactionDto tempTxDto1 = getEntryExpiredTransactionDto(entryPlateList); // call method for entryDiscard transaction send to ATP queue
												TransactionDetailDto transactionDetailDto1 = new TransactionDetailDto();
												transactionDetailDto1.setTransactionDto(tempTxDto1);		
												qatpClassificationService.pushMessage(transactionDetailDto1);
												logger.info("======== Discard Entry Transaction for Device Pushed to ATP queue ================== #11");
												
											}
										}

									}
								}

							}
						} 

					}


				}
				for(Map.Entry<String, String> m:reportsMap.entrySet()){  
					logger.info(m.getKey()+" ==== > "+m.getValue());  
				}  
			}



		}catch(Exception e) {
			logger.info("Excption message for Device Tranasacation {} ==> {}",e);
		}
	}


	private void getParamtersInfoForPlateNumber(List<TProcessParamterDto> processparamDetails2) throws ParseException {

		startProcessDate = processparamDetails2.stream()
				.filter(e -> e.getParam_name().equals(UnmatchedConstant.IMAGE_TXN_START_PROCESS_DATE)).findAny();
		endProcessDate = processparamDetails2.stream()
				.filter(e -> e.getParam_name().equals(UnmatchedConstant.IMAGE_TXN_END_PROCESS_DATE)).findAny();

		logger.info("=====StartProcessDate-Plate =====" + startProcessDate.get().getParam_value());
		logger.info("=====EndProcessDate-Plate =====" + endProcessDate.get().getParam_value());


		String tempStartDate = startProcessDate.get().getParam_value();
		String tempEndDate = endProcessDate.get().getParam_value();

		Map<String,String> reportsMap=new HashMap<String,String>();

		try {

			List<ImageReviewDto> ImageReviewDto = computeTollDAO.getImageTrxParamConfig(tempStartDate, tempEndDate);

			if (ImageReviewDto!=null)
			{
				logger.info("=====plate matched================== #1");
				for (ImageReviewDto imageReviewDto : ImageReviewDto) {

					String sectionId = imageReviewDto.getSectionId();
					LocalDate txDate = imageReviewDto.getTxDate();
					String agencyId = imageReviewDto.getAgencyId();

					List<ViolTxDto> violtxList = computeTollDAO.getVoilTxData(sectionId, agencyId, txDate);

					if (violtxList != null) {
						logger.info("=====plate matched================== #2");
						for (ViolTxDto voilTxDto : violtxList) {

							String plateNumber = voilTxDto.getPlateNumber();
							String plateState = voilTxDto.getPlateState();
							Integer laneId = voilTxDto.getLaneId();
							OffsetDateTime txTimeStamp1 = voilTxDto.getTxTimestamp();
							String txTimeStamp = txTimeStamp1.toString().replace("T", " ");

							logger.info("Plate Number from T_VOIL_TX table :: ==> " + plateNumber);

							List<EntryTransactionDto> machingPlateList = computeTollDAO.getMachingPlateEntryData(agencyId,
									sectionId, laneId, txTimeStamp, txDate, plateState, plateNumber);

							if (machingPlateList != null) {
								logger.info("=====plate matched================== #3");
								for (EntryTransactionDto entryList : machingPlateList) {
									//	for (ViolTxDto violTxDto1 : violtxList) {
									logger.info("Voil Tx externaml Ref no :: ==> " + voilTxDto.getTxExternRefNo());
									logger.info("Voil Tx LaneTxId :: ==> " + voilTxDto.getLaneTxId());

									//if (entryList.getPlateNumber().equals(violTxDto1.getPlateNumber())) 
									String entryPlateNumber= entryList.getPlateNumber();
									String entryPlateState= entryList.getPlateState();
									String violTxPlateNumber = voilTxDto.getPlateNumber();
									String violTxPlateState = voilTxDto.getPlateState();


									if	((entryPlateNumber.equals(violTxPlateNumber))
											&&(entryPlateState.equals(violTxPlateState))) {
										
										logger.info("Matched Entry Plate LaneTxId :: ==> " + entryList.getLaneTxId());
										logger.info("Matched ViolTx Plate LaneTxId :: ==> " + voilTxDto.getLaneTxId());
										
										logger.info("=====plate matched================== #4");
										computeTollDAO.updateMatchedPlateNumberTxEntry(entryList, voilTxDto, reportsMap);
										logger.info("Entry externaml Ref no :: ==> " + entryList.getTxExternRefNo());
										logger.info("Entry LaneTxId :: ==> " + entryList.getLaneTxId());

										computeTollDAO.updateTTransDetailTableVIolTx(entryList, voilTxDto);// update in T_tran_Detail
										logger.info("=====plate matched================== #5");
										TollCalculationResponseDto tollCalculationResponseDto = getTollCalculationForPlate(voilTxDto,entryList );
										TollCalculationResponseDto tollCalculationResponseDto1 = getTollCalculationForRevenueType1(voilTxDto,entryList );
										logger.info("=====plate matched================== #6");
										Double fullFareAmount = tollCalculationResponseDto.getFullFare();
										Double extraExcelCharge = tollCalculationResponseDto.getExtraAxleCharge();
										Integer actualExtraExcel = voilTxDto.getActualExtraAxles();
										
										Double fullFareAmount1 = tollCalculationResponseDto1.getFullFare();
										Double extraExcelCharge1 = tollCalculationResponseDto1.getExtraAxleCharge();
										Integer actualExtraExcel1 = voilTxDto.getActualExtraAxles();
										
										Double videoFareAmount = fullFareAmount + actualExtraExcel * extraExcelCharge;
										Double etcFareAmount = fullFareAmount1 + actualExtraExcel1 * extraExcelCharge1;

										voilTxDto.setVideoFareAmount(videoFareAmount); 
										voilTxDto.setPostedFareAmount(videoFareAmount);
										voilTxDto.setEtcFareAmount(etcFareAmount);
										
										LocalDate txDate1 = voilTxDto.getTxDate();
										Long etcAccountId = computeTollDAO.getEtcAccountId(plateState, plateNumber, txDate1);
										
										// calling update voilTx API
										String deviceNo="noDevice";
										callviolUpdateAPI(voilTxDto, entryList, deviceNo, etcAccountId, reportsMap);
										logger.info("=====plate matched================== #7");
										// update status discard for entry transaction
										computeTollDAO.updateEntryDataToDiscard(entryList, reportsMap);
										logger.info("=====plate matched================== #8");
										computeTollDAO.updateEntryTransDetailTable(entryList);// update in T_tran_Detail
										logger.info("=====plate matched================== #9");
										
										TransactionDto tempTxDto1 = getEntryExpiredTransactionDto(entryList); // call method for entryDiscard transaction send to ATP queue
										TransactionDetailDto transactionDetailDto1 = new TransactionDetailDto();
										transactionDetailDto1.setTransactionDto(tempTxDto1);		
										qatpClassificationService.pushMessage(transactionDetailDto1);
										logger.info("======== Discard Entry Transaction for Device Pushed to ATP queue ================== #10");
									} 

								}
							}else {

								processparamDetails2 = computeTollDAO.getCrossMatchedProcessParamter();
								crossmatched = processparamDetails2.stream()	.filter(e -> e.getParam_name()
										.equals(UnmatchedConstant.UNMATCHED_CROSSMATCHED_ALLOWED)).findAny();
								logger.info("=====crossmatched=====" + crossmatched.get().getParam_value());
								String crossMatchedAllowed = crossmatched.get().getParam_value();

								Long etcAccountId = voilTxDto.getEtcAccountId() !=null ? voilTxDto.getEtcAccountId() : 0 ;
								LocalDate txDate1 = voilTxDto.getTxDate();
								Integer plazaAgencyId = voilTxDto.getPlazaAgencyId();

								if(crossMatchedAllowed.equals("Y")) {
									logger.info("=====plate crossmatched================== #1");
									if (etcAccountId == 0 ) {
										logger.info("=====plate crossmatched================== #2");
										List<PlateInfoDto> plateInfoList = computeTollDAO.getEtcAccountIdFromPlate(plateState, plateNumber, txDate1);
										logger.info("=====plate crossmatched================== #6");
										if(plateInfoList !=null)
										{
											logger.info("=====plate crossmatched================== #7");
											for (PlateInfoDto pp : plateInfoList) {
												Long etcAccountId1 = pp.getEtcAccountId();
												logger.info("=====cplate rossmatched================== #8");
												deviceInfo(etcAccountId1, laneId, plazaAgencyId, txTimeStamp, voilTxDto, reportsMap);
											}
										}

									} else {
										deviceInfo(etcAccountId, laneId, plazaAgencyId, txTimeStamp, voilTxDto, reportsMap);

									}

								}
							}

						}
					}
				}

				for(Map.Entry<String, String> m:reportsMap.entrySet()){  
					logger.info(m.getKey()+" ==== > "+m.getValue());  
				}  
			}
		}catch(Exception e) {
			e.printStackTrace();
			logger.info("Excption message for Plate ==> {}",e.getMessage());
			
		}


	}




	private TransactionDto getDeviceTransactionDto(EntryTransactionDto entryList, ExitTransactionDto exitList) {

		TransactionDto tempTxDto = new TransactionDto();
		tempTxDto.setLaneTxId(exitList.getLaneTxId());
		tempTxDto.setTxExternRefNo(exitList.getTxExternRefNo());
		tempTxDto.setTxType(exitList.getTxTypeInd());
		tempTxDto.setTxSubType(exitList.getTxSubtypeInd());
		tempTxDto.setTollSystemType(exitList.getTollSystemType());
		tempTxDto.setTollRevenueType(exitList.getTollRevenueType());
		tempTxDto.setTxTimeStamp(exitList.getTxTimestamp());
		// entry start
		tempTxDto.setEntryTxTimeStamp(entryList.getEntryTimestamp());
		tempTxDto.setEntryPlazaId(entryList.getEntryPlazaId() !=null ? entryList.getEntryPlazaId() : 0);
		tempTxDto.setEntryLaneId(entryList.getEntryLaneId());
		tempTxDto.setEntryTxSeqNumber(entryList.getEntryTxSeqNumber());
		tempTxDto.setEntryDataSource(entryList.getEntryDataSource());
		tempTxDto.setEntryVehicleSpeed(entryList.getEntryVehicleSpeed());
		tempTxDto.setReadPerf(entryList.getEntryDeviceReadCount());
		tempTxDto.setWritePerf(entryList.getEntryDeviceWriteCount());
		// entry close
		tempTxDto.setAccAgencyId(exitList.getAccountAgencyId());
		tempTxDto.setVehicleSpeed(exitList.getVehicleSpeed());
		tempTxDto.setPlazaAgencyId(exitList.getPlazaAgencyId());
		tempTxDto.setPlazaId(exitList.getPlazaId());
		tempTxDto.setLaneId(exitList.getLaneId());
		// LocalDate txDate = exitList.getTxDate();
		tempTxDto.setTxDate(exitList.getTxDate().toString());
		tempTxDto.setLaneMode(exitList.getLaneMode());
		tempTxDto.setLaneState(exitList.getLaneState());
		tempTxDto.setLaneSn(exitList.getTxSeqNumber());
		tempTxDto.setDeviceNo(exitList.getDeviceNo());
		tempTxDto.setDeviceCodedClass(exitList.getDeviceCodedClass());
		tempTxDto.setDeviceAgencyClass(exitList.getDeviceAgencyClass());
		tempTxDto.setTagAxles(exitList.getDeviceAxles());
		tempTxDto.setActualClass(exitList.getActualClass());
		tempTxDto.setActualAxles(exitList.getActualAxles());
		tempTxDto.setExtraAxles(exitList.getActualExtraAxles());
		tempTxDto.setPlateState(exitList.getPlateState());
		tempTxDto.setPlateNumber(exitList.getPlateNumber());
		tempTxDto.setPlateCountry(exitList.getPlateCountry());
		tempTxDto.setReadPerf(exitList.getDeviceReadCount());
		tempTxDto.setWritePerf(exitList.getDeviceWriteCount());
		tempTxDto.setProgStat(exitList.getDeviceProgramStatus());
		tempTxDto.setCollectorNumber(exitList.getCollectorId());
		tempTxDto.setImageCaptured(exitList.getImageTaken());
		tempTxDto.setSpeedViolation(exitList.getSpeedViolFlag());
		// tempTxDto.setReciprocityTrx(exitList.getreci);
		// tempTxDto.setIsLprEnabled(exitList.getIs);
		tempTxDto.setUnrealizedAmount(exitList.getUnrealizedAmount());
		tempTxDto.setExtFileId(exitList.getExternFileId());
		tempTxDto.setReceivedDate(exitList.getRevenueDate().toString());
		tempTxDto.setAccountType(exitList.getAccountType());
		tempTxDto.setAccAgencyId(exitList.getAccountAgencyId());
		tempTxDto.setEtcAccountId(exitList.getEtcAccountId());
		// tempTxDto.setEtcSubAccount(exitList.setetc);
		// tempTxDto.setDstFlag(exitList.getdst);
		tempTxDto.setIsPeak(exitList.getIsPeak());
		// tempTxDto.setFareType(exitList.getfare);
		tempTxDto.setUpdateTs(exitList.getUpdateTs().toString());
		tempTxDto.setTxStatus(exitList.getTxStatus().toString());
		// tempTxDto.setAetFlag(exitList.getAe);
		// tempTxDto.setReserved(exitList.getres);
		tempTxDto.setAvcClass(exitList.getDeviceAgencyClass());
		// tempTxDto.setTagClass(exitList.getta);
		// tempTxDto.setTransactionInfo(exitList.gettr);
		// tempTxDto.setLaneDataSource(exitList.getla);
		tempTxDto.setLaneHealth(exitList.getLaneHealth().longValue());
		tempTxDto.setTourSegment(exitList.getTourSegmentId() != null ? exitList.getTourSegmentId().longValue() : 0);
		tempTxDto.setBufRead(exitList.getBufferedRreadFlag());
		// tempTxDto.setReaderId(exitList.getrea);
		// tempTxDto.setDiscountedAmount(exitList.getDiscountedAmount().doubleValue());//
		// **
		if (exitList.getDeviceIagClass() != null) {
			tempTxDto.setTagIagClass(exitList.getDeviceIagClass().longValue());
		}
		// tempTxDto.setPlanCharged(exitList.getplan);
		//tempTxDto.setActualAxles(exitList.getActualAxles());// **
		tempTxDto.setAtpFileId(exitList.getAtpFileId());
		tempTxDto.setPlanType(exitList.getPlanTypeId());
		tempTxDto.setEtcFareAmount(exitList.getEtcFareAmount());
		tempTxDto.setPostedFareAmount(exitList.getPostedFareAmount());
		tempTxDto.setExpectedRevenueAmount(exitList.getExpectedRevenueAmount());
		tempTxDto.setVideoFareAmount(exitList.getVideoFareAmount());
		tempTxDto.setCashFareAmount(exitList.getCashFareAmount());

		return tempTxDto;

	}

	private TransactionDto getEntryExpiredTransactionDto(EntryTransactionDto expiredEntryList) {
		TransactionDto tempTxDto = new TransactionDto();

		tempTxDto.setLaneTxId(expiredEntryList.getLaneTxId());
		tempTxDto.setTxExternRefNo(expiredEntryList.getTxExternRefNo());
		tempTxDto.setTxType(expiredEntryList.getTxTypeInd());
		tempTxDto.setTxSubType(expiredEntryList.getTxSubtypeInd());
		tempTxDto.setTollSystemType(expiredEntryList.getTollSystemType());
		tempTxDto.setTollRevenueType(expiredEntryList.getTollRevenueType());
		tempTxDto.setTxTimeStamp(expiredEntryList.getTxTimestamp());
		// entry start
		tempTxDto.setEntryTxTimeStamp(expiredEntryList.getEntryTimestamp());
		tempTxDto.setEntryPlazaId(expiredEntryList.getEntryPlazaId() !=null ? expiredEntryList.getEntryPlazaId() :0);
		tempTxDto.setEntryLaneId(expiredEntryList.getEntryLaneId());
		tempTxDto.setEntryTxSeqNumber(expiredEntryList.getEntryTxSeqNumber());
		tempTxDto.setEntryDataSource(expiredEntryList.getEntryDataSource());
		tempTxDto.setEntryVehicleSpeed(expiredEntryList.getEntryVehicleSpeed());
		tempTxDto.setReadPerf(expiredEntryList.getEntryDeviceReadCount());
		tempTxDto.setWritePerf(expiredEntryList.getEntryDeviceWriteCount());
		// entry close
		tempTxDto.setAccAgencyId(expiredEntryList.getAccountAgencyId());
		tempTxDto.setVehicleSpeed(expiredEntryList.getVehicleSpeed());
		tempTxDto.setPlazaAgencyId(expiredEntryList.getPlazaAgencyId());
		tempTxDto.setPlazaId(expiredEntryList.getPlazaId());
		tempTxDto.setLaneId(expiredEntryList.getLaneId());
		// LocalDate txDate = exitList.getTxDate();
		tempTxDto.setTxDate(expiredEntryList.getTxDate().toString() !=null ? expiredEntryList.getTxDate().toString() :null );
		tempTxDto.setLaneMode(expiredEntryList.getLaneMode());
		tempTxDto.setLaneState(expiredEntryList.getLaneState());
		tempTxDto.setLaneSn(expiredEntryList.getTxSeqNumber());
		tempTxDto.setDeviceNo(expiredEntryList.getDeviceNo());
		tempTxDto.setDeviceCodedClass(expiredEntryList.getDeviceCodedClass());
		tempTxDto.setDeviceAgencyClass(expiredEntryList.getDeviceAgencyClass());
		tempTxDto.setTagAxles(expiredEntryList.getDeviceAxles());
		tempTxDto.setActualClass(expiredEntryList.getActualClass());
		tempTxDto.setActualAxles(expiredEntryList.getActualAxles());
		tempTxDto.setExtraAxles(expiredEntryList.getActualExtraAxles());
		tempTxDto.setPlateState(expiredEntryList.getPlateState());
		tempTxDto.setPlateNumber(expiredEntryList.getPlateNumber());
		tempTxDto.setPlateCountry(expiredEntryList.getPlateCountry());
		tempTxDto.setReadPerf(expiredEntryList.getDeviceReadCount());
		tempTxDto.setWritePerf(expiredEntryList.getDeviceWriteCount());
		tempTxDto.setProgStat(expiredEntryList.getDeviceProgramStatus());
		tempTxDto.setCollectorNumber(expiredEntryList.getCollectorId());
		tempTxDto.setImageCaptured(expiredEntryList.getImageTaken());
		tempTxDto.setSpeedViolation(expiredEntryList.getSpeedViolFlag());
		// tempTxDto.setReciprocityTrx(exitList.getreci);
		// tempTxDto.setIsLprEnabled(exitList.getIs);
		tempTxDto.setUnrealizedAmount(expiredEntryList.getUnrealizedAmount());
		tempTxDto.setExtFileId(expiredEntryList.getExternFileId());
		tempTxDto.setReceivedDate(expiredEntryList.getRevenueDate().toString() !=null ? expiredEntryList.getRevenueDate().toString() :null );
		tempTxDto.setAccountType(expiredEntryList.getAccountType());
		tempTxDto.setAccAgencyId(expiredEntryList.getAccountAgencyId());
		tempTxDto.setEtcAccountId(expiredEntryList.getEtcAccountId());
		// tempTxDto.setEtcSubAccount(exitList.setetc);
		// tempTxDto.setDstFlag(exitList.getdst);
		tempTxDto.setIsPeak(expiredEntryList.getIsPeak());
		// tempTxDto.setFareType(exitList.getfare);
		tempTxDto.setUpdateTs(expiredEntryList.getUpdateTs().toString() !=null ? expiredEntryList.getUpdateTs().toString() :null );
		tempTxDto.setTxStatus(expiredEntryList.getTxStatus().toString());
		// tempTxDto.setAetFlag(exitList.getAe);
		// tempTxDto.setReserved(exitList.getres);
		tempTxDto.setAvcClass(expiredEntryList.getDeviceAgencyClass());
		// tempTxDto.setTagClass(exitList.getta);
		// tempTxDto.setTransactionInfo(exitList.gettr);
		// tempTxDto.setLaneDataSource(exitList.getla);
		tempTxDto.setLaneHealth(expiredEntryList.getLaneHealth().longValue());
		tempTxDto.setTourSegment(expiredEntryList.getTourSegmentId() != null ? expiredEntryList.getTourSegmentId().longValue() : 0);
		tempTxDto.setBufRead(expiredEntryList.getBufferedRreadFlag());
		// tempTxDto.setReaderId(exitList.getrea);
		// tempTxDto.setDiscountedAmount(exitList.getDiscountedAmount().doubleValue());//
		// **
		if (expiredEntryList.getDeviceIagClass() != null) {
			tempTxDto.setTagIagClass(expiredEntryList.getDeviceIagClass().longValue());
		}
		// tempTxDto.setPlanCharged(exitList.getplan);
		tempTxDto.setActualAxles(expiredEntryList.getPostclassAxles());// **
		tempTxDto.setAtpFileId(expiredEntryList.getAtpFileId());
		tempTxDto.setPlanType(expiredEntryList.getPlanTypeId());
		tempTxDto.setEtcFareAmount(expiredEntryList.getEtcFareAmount());
		tempTxDto.setPostedFareAmount(expiredEntryList.getPostedFareAmount());
		tempTxDto.setExpectedRevenueAmount(expiredEntryList.getExpectedRevenueAmount());
		tempTxDto.setVideoFareAmount(expiredEntryList.getVideoFareAmount());
		tempTxDto.setCashFareAmount(expiredEntryList.getCashFareAmount());

		return tempTxDto;
	}


	private TransactionDto getExitExpiredTransactionDto(ExitTransactionDto expiredExitList) {
		TransactionDto tempTxDto = new TransactionDto();

		tempTxDto.setLaneTxId(expiredExitList.getLaneTxId());
		tempTxDto.setTxExternRefNo(expiredExitList.getTxExternRefNo());
		tempTxDto.setTxType(expiredExitList.getTxTypeInd());
		tempTxDto.setTxSubType(expiredExitList.getTxSubtypeInd());
		tempTxDto.setTollSystemType(expiredExitList.getTollSystemType());
		tempTxDto.setTollRevenueType(expiredExitList.getTollRevenueType());
		tempTxDto.setTxTimeStamp(expiredExitList.getTxTimestamp());
		// entry start
		//		tempTxDto.setEntryTxTimeStamp(expiredExitList.getEntryTimestamp());
		//		tempTxDto.setEntryPlazaId(expiredExitList.getEntryPlazaId());
		//		tempTxDto.setEntryLaneId(expiredExitList.getEntryLaneId());
		//		tempTxDto.setEntryTxSeqNumber(expiredExitList.getEntryTxSeqNumber());
		//		tempTxDto.setEntryDataSource(expiredExitList.getEntryDataSource());
		//		tempTxDto.setEntryVehicleSpeed(expiredExitList.getEntryVehicleSpeed());
		//		tempTxDto.setReadPerf(expiredExitList.getEntryDeviceReadCount());
		//		tempTxDto.setWritePerf(expiredExitList.getEntryDeviceWriteCount());

		//tempTxDto.setEntryTxTimeStamp(null);
		tempTxDto.setEntryPlazaId(0);
		tempTxDto.setEntryLaneId(0);
		tempTxDto.setEntryTxSeqNumber(0);
		tempTxDto.setEntryDataSource(null);
		tempTxDto.setEntryVehicleSpeed(0);
		tempTxDto.setReadPerf(0);
		tempTxDto.setWritePerf(0);
		// entry close
		tempTxDto.setAccAgencyId(expiredExitList.getAccountAgencyId());
		tempTxDto.setVehicleSpeed(expiredExitList.getVehicleSpeed());
		tempTxDto.setPlazaAgencyId(expiredExitList.getPlazaAgencyId());
		tempTxDto.setPlazaId(expiredExitList.getPlazaId());
		tempTxDto.setLaneId(expiredExitList.getLaneId());
		// LocalDate txDate = exitList.getTxDate();
		tempTxDto.setTxDate(expiredExitList.getTxDate().toString() !=null ? expiredExitList.getTxDate().toString() :null );
		tempTxDto.setLaneMode(expiredExitList.getLaneMode());
		tempTxDto.setLaneState(expiredExitList.getLaneState());
		tempTxDto.setLaneSn(expiredExitList.getTxSeqNumber());
		tempTxDto.setDeviceNo(expiredExitList.getDeviceNo());
		tempTxDto.setDeviceCodedClass(expiredExitList.getDeviceCodedClass());
		tempTxDto.setDeviceAgencyClass(expiredExitList.getDeviceAgencyClass());
		tempTxDto.setTagAxles(expiredExitList.getDeviceAxles());
		tempTxDto.setActualClass(expiredExitList.getActualClass());
		tempTxDto.setActualAxles(expiredExitList.getActualAxles());
		tempTxDto.setExtraAxles(expiredExitList.getActualExtraAxles());
		tempTxDto.setPlateState(expiredExitList.getPlateState());
		tempTxDto.setPlateNumber(expiredExitList.getPlateNumber());
		tempTxDto.setPlateCountry(expiredExitList.getPlateCountry());
		tempTxDto.setReadPerf(expiredExitList.getDeviceReadCount());
		tempTxDto.setWritePerf(expiredExitList.getDeviceWriteCount());
		tempTxDto.setProgStat(expiredExitList.getDeviceProgramStatus());
		tempTxDto.setCollectorNumber(expiredExitList.getCollectorId());
		tempTxDto.setImageCaptured(expiredExitList.getImageTaken());
		tempTxDto.setSpeedViolation(expiredExitList.getSpeedViolFlag());
		// tempTxDto.setReciprocityTrx(exitList.getreci);
		// tempTxDto.setIsLprEnabled(exitList.getIs);
		tempTxDto.setUnrealizedAmount(expiredExitList.getUnrealizedAmount());
		tempTxDto.setExtFileId(expiredExitList.getExternFileId());
		tempTxDto.setReceivedDate(expiredExitList.getRevenueDate().toString() !=null ? expiredExitList.getRevenueDate().toString() :null );
		tempTxDto.setAccountType(expiredExitList.getAccountType());
		tempTxDto.setAccAgencyId(expiredExitList.getAccountAgencyId());
		tempTxDto.setEtcAccountId(expiredExitList.getEtcAccountId());
		// tempTxDto.setEtcSubAccount(exitList.setetc);
		// tempTxDto.setDstFlag(exitList.getdst);
		tempTxDto.setIsPeak(expiredExitList.getIsPeak());
		// tempTxDto.setFareType(exitList.getfare);
		tempTxDto.setUpdateTs(expiredExitList.getUpdateTs().toString() !=null ? expiredExitList.getRevenueDate().toString() :null );
		tempTxDto.setTxStatus(expiredExitList.getTxStatus().toString());
		// tempTxDto.setAetFlag(exitList.getAe);
		// tempTxDto.setReserved(exitList.getres);
		tempTxDto.setAvcClass(expiredExitList.getDeviceAgencyClass());
		// tempTxDto.setTagClass(exitList.getta);
		// tempTxDto.setTransactionInfo(exitList.gettr);
		// tempTxDto.setLaneDataSource(exitList.getla);
		tempTxDto.setLaneHealth(expiredExitList.getLaneHealth().longValue());
		tempTxDto.setTourSegment(expiredExitList.getTourSegmentId() != null ? expiredExitList.getTourSegmentId().longValue() : 0);
		tempTxDto.setBufRead(expiredExitList.getBufferedRreadFlag());
		// tempTxDto.setReaderId(exitList.getrea);
		// tempTxDto.setDiscountedAmount(exitList.getDiscountedAmount().doubleValue());//
		// **
		if (expiredExitList.getDeviceIagClass() != null) {
			tempTxDto.setTagIagClass(expiredExitList.getDeviceIagClass().longValue());
		}
		// tempTxDto.setPlanCharged(exitList.getplan);
		tempTxDto.setActualAxles(expiredExitList.getPostclassAxles());// **
		tempTxDto.setAtpFileId(expiredExitList.getAtpFileId());
		tempTxDto.setPlanType(expiredExitList.getPlanTypeId());
		tempTxDto.setEtcFareAmount(expiredExitList.getEtcFareAmount());
		tempTxDto.setPostedFareAmount(expiredExitList.getPostedFareAmount());
		tempTxDto.setExpectedRevenueAmount(expiredExitList.getExpectedRevenueAmount());
		tempTxDto.setVideoFareAmount(expiredExitList.getVideoFareAmount());
		tempTxDto.setCashFareAmount(expiredExitList.getCashFareAmount());

		return tempTxDto;
	}

	private NY12Dto getNY12Details(TransactionDto tempTxDto) {

		NY12Dto ny12 = new NY12Dto();

		ny12.setLaneTxId(tempTxDto.getLaneTxId());
		ny12.setTxExternRefNo(tempTxDto.getTxExternRefNo());
		ny12.setTxSeqNumber(tempTxDto.getEntryTxSeqNumber());
		ny12.setExternFileId(tempTxDto.getExtFileId());
		ny12.setLaneId(tempTxDto.getLaneId());
		ny12.setTxTimestamp(tempTxDto.getTxTimeStamp().toString());
		ny12.setTxTypeInd(tempTxDto.getTxType());
		ny12.setTxSubTypeInd(tempTxDto.getTxSubType());
		ny12.setTollSystemType(tempTxDto.getTollSystemType());
		ny12.setLaneMode(tempTxDto.getLaneMode());
		ny12.setLaneType(tempTxDto.getLaneTxType());
		ny12.setLaneState(tempTxDto.getLaneState());
		ny12.setLaneHealth(tempTxDto.getLaneHealth());
		ny12.setPlazaAgencyId(tempTxDto.getPlazaAgencyId());
		ny12.setPlazaId(tempTxDto.getPlazaId());
		ny12.setCollectorId(tempTxDto.getCollectorNumber());
		ny12.setTourSegmentId(tempTxDto.getTourSegment());
		ny12.setEntryDataSource(tempTxDto.getEntryDataSource());
		ny12.setEntryLaneId(tempTxDto.getEntryLaneId());
		ny12.setEntryPlazaId(tempTxDto.getEntryPlazaId() !=null ? tempTxDto.getEntryPlazaId() :0);
		ny12.setEntryTimeStamp(tempTxDto.getEntryTxTimeStamp() !=null ? tempTxDto.getEntryTxTimeStamp().toString(): null);
		ny12.setEntryTxSeqNumber(tempTxDto.getEntryTxSeqNumber());
		ny12.setEntryVehicleSpeed(tempTxDto.getEntryVehicleSpeed());
		ny12.setLaneTxStatus(tempTxDto.getLaneTxStatus());
		ny12.setLaneTxType(tempTxDto.getLaneTxType());
		ny12.setTollRevenueType(tempTxDto.getTollRevenueType());
		ny12.setActualClass(tempTxDto.getActualClass());
		ny12.setActualAxles(tempTxDto.getActualAxles());
		ny12.setActualExtraAxles(tempTxDto.getExtraAxles());
		ny12.setCollectorClass(tempTxDto.getCollectorClass());
		ny12.setCollectorAxles(tempTxDto.getCollectorAxles());
		ny12.setPreclassClass(tempTxDto.getPreClass());
		ny12.setPreclassAxles(tempTxDto.getPreClassAxles());
		ny12.setPostclassClass(tempTxDto.getPostClass());
		ny12.setPostclassAxles(tempTxDto.getPostClassAxles());
		ny12.setForwardAxles(tempTxDto.getForwordAxles());
		ny12.setReverseAxles(tempTxDto.getReverseAxles());
		ny12.setDiscountedAmount(tempTxDto.getDiscountedAmount()!=null ? tempTxDto.getDiscountedAmount().longValue() : 0);
		ny12.setCollectedAmount(tempTxDto.getCollectedAmount() !=null ? tempTxDto.getCollectedAmount().longValue() : 0);
		ny12.setUnRealizedAmount(tempTxDto.getUnrealizedAmount() !=null ? tempTxDto.getUnrealizedAmount().longValue() : 0);
		ny12.setVehicleSpeed(tempTxDto.getVehicleSpeed());
		ny12.setDeviceNo(tempTxDto.getDeviceNo());
		ny12.setAccountType(tempTxDto.getAccountType());
		ny12.setDeviceIagClass(tempTxDto.getDeviceIagClass());
		ny12.setDeviceAxles(tempTxDto.getDeviceAxles());
		ny12.setEtcAccountId(tempTxDto.getEtcAccountId());
		ny12.setAccountAgencyId(tempTxDto.getAccAgencyId());
		ny12.setReadAviClass(tempTxDto.getReadAVIClass());
		ny12.setReadAviAxles(tempTxDto.getReadAVIAxles());
		ny12.setDeviceProgramStatus(tempTxDto.getDeviceProgramStatus());
		ny12.setBufferedReadFlag(tempTxDto.getBufRead());
		ny12.setLaneDeviceStatus(tempTxDto.getLaneDeviceStatus());
		ny12.setPostDeviceStatus(tempTxDto.getPostDeviceStatus());
		ny12.setPreTxnBalance(tempTxDto.getPreTrxbalance());
		ny12.setTxStatus(tempTxDto.getTxStatus());
		ny12.setSpeedViolFlag(tempTxDto.getSpeedViolation());
		ny12.setImagetaken(tempTxDto.getImageCaptured());
		ny12.setPlateCountry(tempTxDto.getPlateCountry());
		ny12.setPlateState(tempTxDto.getPlateState());
		ny12.setPlateNumber(tempTxDto.getPlateNumber());
		ny12.setRevenueDate(tempTxDto.getRevenueDate() !=null ? tempTxDto.getRevenueDate() :null );
		ny12.setAtpFileId(tempTxDto.getAtpFileId());
		ny12.setMatchedTxExternRefNo(tempTxDto.getMatchedTxExternRefNo());
		ny12.setTxCompleteRefNo(tempTxDto.getTxCompleteRefNo());
		ny12.setTxMatchStatus(tempTxDto.getMatchedTxStatus());
		ny12.setExternFileDate(tempTxDto.getExternFileDate() !=null ? tempTxDto.getExternFileDate() :null );
		ny12.setTxDate(tempTxDto.getTxDate() !=null ? tempTxDto.getTxDate() :null );
		ny12.setEvent(tempTxDto.getEvent());
		ny12.setHovFlag(tempTxDto.getHovFlag());
		ny12.setIsReciprocityTxn(tempTxDto.getIsReciprocityTrx());
		ny12.setCscLookUpKey(tempTxDto.getCscLookupKey());
		ny12.setCashFareAmount(tempTxDto.getCashFareAmount());
		ny12.setDiscountedAmount2(tempTxDto.getDiscountedAmount2() !=null ? tempTxDto.getDiscountedAmount2() : 0);
		ny12.setEtcFareAmount(tempTxDto.getEtcFareAmount() !=null ? tempTxDto.getEtcFareAmount() :0);
		ny12.setExpectedRevenueAmount(tempTxDto.getExpectedRevenueAmount() !=null ? tempTxDto.getExpectedRevenueAmount() : 0);
		ny12.setPostedFareAmount(tempTxDto.getPostedFareAmount() !=null ? tempTxDto.getPostedFareAmount() : 0);
		ny12.setVideoFareAmount(tempTxDto.getVideoFareAmount() !=null ? tempTxDto.getVideoFareAmount() : 0);

		return ny12;
	}

	public void callInsertAPI(NY12Dto ny12Dto, String apiName) {

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		Gson gson1 = new Gson();
		try {
			HttpEntity<String> entity = new HttpEntity<String>(gson1.toJson(ny12Dto), headers);

			if (apiName.equals("NY12")) {
				ResponseEntity<String> result = restTemplate.postForEntity(configVariable.getUrlPathNY12(), entity,
						String.class);

				if (result.getStatusCodeValue() == 200) {
					JsonObject jsonObject = new Gson().fromJson(result.getBody(), JsonObject.class);
					logger.info("Got NY12 API response {}", jsonObject);
				} else {
					logger.info("Got NY12 API response status code {}", result.getStatusCodeValue());
				}
			} else if (apiName.equals("25A")) {

				ResponseEntity<String> result = restTemplate.postForEntity(configVariable.getUrlPath25a(), entity,
						String.class);

				if (result.getStatusCodeValue() == 200) {
					JsonObject jsonObject = new Gson().fromJson(result.getBody(), JsonObject.class);
					logger.info("Got 25A API response {}", jsonObject);
				} else {
					logger.info("Got 25A API response status code {}", result.getStatusCodeValue());
				}

			} else {
				logger.info("Both NY12 & 25A API not calling.......");
			}

		} catch (Exception e) {
			logger.error("Error: {}", e.getMessage());
		}
	}


	public void callviolUpdateAPI(ViolTxDto violTxDto1, EntryTransactionDto entryList, String deviceNo, Long etcAccountId, Map<String,String> reportsMap) {

		Integer count = 0;
		ViolTxUpdateDto violUpdate = new ViolTxUpdateDto();
		violUpdate.setLANE_TX_ID(violTxDto1.getLaneTxId());
		violUpdate.setENTRY_LANE_ID(entryList.getEntryLaneId());
		violUpdate.setENTRY_PLAZA_ID(entryList.getEntryPlazaId() != null ? entryList.getEntryPlazaId().longValue() : 0);
		violUpdate.setENTRY_TIMESTAMP(entryList.getEntryTimestamp().toString());
		violUpdate.setENTRY_TX_SEQ_NUMBER(entryList.getEntryTxSeqNumber());
		violUpdate.setENTRY_VEHICLE_SPEED(entryList.getEntryVehicleSpeed());
		violUpdate.setENTRY_DATA_SOURCE(entryList.getEntryDataSource());
		violUpdate.setENTRY_DEVICE_READ_COUNT(entryList.getEntryDeviceReadCount());
		violUpdate.setENTRY_DEVICE_WRITE_COUNT(entryList.getEntryDeviceWriteCount());
		violUpdate.setPOSTED_FARE_AMOUNT(violTxDto1.getPostedFareAmount());
		violUpdate.setVIDEO_FARE_AMOUNT(violTxDto1.getVideoFareAmount());
		violUpdate.setETC_FARE_AMOUNT(violTxDto1.getEtcFareAmount());
		violUpdate.setETC_ACCOUNT_ID(etcAccountId);
		violUpdate.setTX_SUBTYPE_IND(UnmatchedConstant.M);
		violUpdate.setTX_STATUS(UnmatchedConstant.MATCHED_VIOL_STATUS);//653

		logger.info("### DeviceNo in VoilTx Update API ### {}",deviceNo);
		if (entryList.getDeviceNo() != null) {
			if (entryList.getDeviceNo().equals(deviceNo)) {
				logger.info("### inside device ###");
				violUpdate.setDEVICE_NO(entryList.getDeviceNo());
			}
		}

		logger.info("### Calling VoilTx Update API ###");

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		Gson gson1 = new Gson();
		try {
			HttpEntity<String> entity = new HttpEntity<String>(gson1.toJson(violUpdate), headers);

			ResponseEntity<String> result = restTemplate.postForEntity(configVariable.getUrlPathUpdateVoilTx(), entity,
					String.class);

			if (result.getStatusCodeValue() == 200) {
				logger.info("Got Voil Update API response {}", result.getBody());

				count++;
				reportsMap.put("Matched Plate Viol Tx Count", count.toString());
			} else {
				logger.info("Got Voil Update API response status code {}", result.getStatusCodeValue());
			}

		} catch (Exception e) {
			logger.error("Error: {}", e.getMessage());
		}
	}


	public void callExpiredViolUpdateAPI(ViolTxDto violTxDto1 ) {

		ViolTxUpdateDto violUpdate = new ViolTxUpdateDto();
		violUpdate.setLANE_TX_ID(violTxDto1.getLaneTxId());
		violUpdate.setTX_SUBTYPE_IND(UnmatchedConstant.X);
		violUpdate.setVIDEO_FARE_AMOUNT(violTxDto1.getVideoFareAmount());
		violUpdate.setPOSTED_FARE_AMOUNT(violTxDto1.getPostedFareAmount());
		violUpdate.setETC_FARE_AMOUNT(violTxDto1.getEtcFareAmount());
		violUpdate.setTX_STATUS(UnmatchedConstant.NO_MATCHED_FOUND_VIOL_STATUS);//659
		logger.info("=====Expired VoilTX================== #3");
		logger.info("### Calling VoilTx Update API for Expired ###");

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		Gson gson1 = new Gson();
		try {
			HttpEntity<String> entity = new HttpEntity<String>(gson1.toJson(violUpdate), headers);

			ResponseEntity<String> result = restTemplate.postForEntity(configVariable.getUrlPathUpdateVoilTx(), entity,
					String.class);

			if (result.getStatusCodeValue() == 200) {
				logger.info("Got Voil Update API response for Expired transaction {}", result.getBody());
				logger.info("=====Expired VoilTX================== #4");
			} else {
				logger.info("Got Voil Update API response status code for Expired transaction {}", result.getStatusCodeValue());
			}

		} catch (Exception e) {
			logger.error("Error: {}", e.getMessage());
		}
	}


	private TransactionDto getPlateTransactionDto(EntryTransactionDto entryList, ViolTxDto violTxDto) {

		TransactionDto tempTxDto = new TransactionDto();
		tempTxDto.setLaneTxId(violTxDto.getLaneTxId());
		tempTxDto.setTxExternRefNo(violTxDto.getTxExternRefNo());
		tempTxDto.setTxType(violTxDto.getTxTypeInd());
		tempTxDto.setTxSubType(violTxDto.getTxSubtypeInd());
		tempTxDto.setTollSystemType(violTxDto.getTollSystemType());
		tempTxDto.setTollRevenueType(violTxDto.getTollRevenueType());
		tempTxDto.setTxTimeStamp(violTxDto.getTxTimestamp());
		// entry start
		tempTxDto.setEntryTxTimeStamp(entryList.getEntryTimestamp());
		tempTxDto.setEntryPlazaId(entryList.getEntryPlazaId());
		tempTxDto.setEntryLaneId(entryList.getEntryLaneId());
		tempTxDto.setEntryTxSeqNumber(entryList.getEntryTxSeqNumber());
		tempTxDto.setEntryDataSource(entryList.getEntryDataSource());
		tempTxDto.setEntryVehicleSpeed(entryList.getEntryVehicleSpeed());
		tempTxDto.setReadPerf(entryList.getEntryDeviceReadCount());
		tempTxDto.setWritePerf(entryList.getEntryDeviceWriteCount());
		// entry close
		tempTxDto.setAccAgencyId(violTxDto.getAccountAgencyId());
		tempTxDto.setVehicleSpeed(violTxDto.getVehicleSpeed());
		tempTxDto.setPlazaAgencyId(violTxDto.getPlazaAgencyId());
		tempTxDto.setPlazaId(violTxDto.getPlazaId());
		tempTxDto.setLaneId(violTxDto.getLaneId());
		// LocalDate txDate = exitList.getTxDate();
		tempTxDto.setTxDate(violTxDto.getTxDate().toString() !=null ? violTxDto.getTxDate().toString() : null);
		tempTxDto.setLaneMode(violTxDto.getLaneMode());
		tempTxDto.setLaneState(violTxDto.getLaneState());
		tempTxDto.setLaneSn(violTxDto.getTxSeqNumber());
		tempTxDto.setDeviceNo(violTxDto.getDeviceNo());
		tempTxDto.setDeviceCodedClass(violTxDto.getDeviceCodedClass());
		tempTxDto.setDeviceAgencyClass(violTxDto.getDeviceAgencyClass());
		tempTxDto.setTagAxles(violTxDto.getDeviceAxles());
		tempTxDto.setActualClass(violTxDto.getActualClass());
		tempTxDto.setActualAxles(violTxDto.getActualAxles());
		tempTxDto.setExtraAxles(violTxDto.getActualExtraAxles());
		tempTxDto.setPlateState(violTxDto.getPlateState());
		tempTxDto.setPlateNumber(violTxDto.getPlateNumber());
		tempTxDto.setPlateCountry(violTxDto.getPlateCountry());
		tempTxDto.setReadPerf(violTxDto.getDeviceReadCount());
		tempTxDto.setWritePerf(violTxDto.getDeviceWriteCount());
		tempTxDto.setProgStat(violTxDto.getDeviceProgramStatus());
		tempTxDto.setCollectorNumber(violTxDto.getCollectorId());
		tempTxDto.setImageCaptured(violTxDto.getImageTaken());
		tempTxDto.setSpeedViolation(violTxDto.getSpeedViolFlag());
		tempTxDto.setReciprocityTrx(violTxDto.getIsReciprocityTxn());
		//tempTxDto.setIsLprEnabled(violTxDto.getIs);
		tempTxDto.setUnrealizedAmount(violTxDto.getUnrealizedAmount());
		tempTxDto.setExtFileId(violTxDto.getExternFileId());
		tempTxDto.setReceivedDate(violTxDto.getRevenueDate().toString() !=null ? violTxDto.getRevenueDate().toString() : null);
		tempTxDto.setAccountType(violTxDto.getAccountType());
		tempTxDto.setAccAgencyId(violTxDto.getAccountAgencyId());
		tempTxDto.setEtcAccountId(violTxDto.getEtcAccountId());
		// tempTxDto.setEtcSubAccount(violTxDto.getet);
		// tempTxDto.setDstFlag(violTxDto.getdst);
		tempTxDto.setIsPeak(violTxDto.getIsPeak());
		// tempTxDto.setFareType(violTxDto.getfare);
		tempTxDto.setUpdateTs(violTxDto.getUpdateTs().toString() !=null ? violTxDto.getRevenueDate().toString() : null);
		tempTxDto.setTxStatus(violTxDto.getTxStatus().toString());
		tempTxDto.setAetFlag(violTxDto.getAetFlag());
		//tempTxDto.setReserved(violTxDto.getres);
		tempTxDto.setAvcClass(violTxDto.getDeviceAgencyClass());
		// tempTxDto.setTagClass(violTxDto.getta);
		// tempTxDto.setTransactionInfo(violTxDto.gettr);
		// tempTxDto.setLaneDataSource(violTxDto.getla);
		tempTxDto.setLaneHealth(violTxDto.getLaneHealth().longValue());
		tempTxDto.setTourSegment(violTxDto.getTourSegmentId() != null ? violTxDto.getTourSegmentId().longValue() : 0);
		tempTxDto.setBufRead(violTxDto.getBufferedReadFlag());//-------
		// tempTxDto.setReaderId(violTxDto.getrea);
		// tempTxDto.setDiscountedAmount(exitList.getDiscountedAmount().doubleValue());//
		// **
		if (violTxDto.getDeviceIagClass() != null) {
			tempTxDto.setTagIagClass(violTxDto.getDeviceIagClass().longValue());
		}
		// tempTxDto.setPlanCharged(violTxDto.getplan);
		tempTxDto.setActualAxles(violTxDto.getPostclassAxles());// **
		tempTxDto.setAtpFileId(violTxDto.getAtpFileId());
		tempTxDto.setPlanType(violTxDto.getPlanTypeId());
		tempTxDto.setEtcFareAmount(violTxDto.getEtcFareAmount());
		tempTxDto.setPostedFareAmount(violTxDto.getPostedFareAmount());
		tempTxDto.setExpectedRevenueAmount(violTxDto.getExpectedRevenueAmount());
		tempTxDto.setVideoFareAmount(violTxDto.getVideoFareAmount());
		tempTxDto.setCashFareAmount(violTxDto.getCashFareAmount());

		return tempTxDto;

	}

	public void deviceInfo(Long etcAccountId, Integer laneId, Integer plazaAgencyId,String txTimeStamp, ViolTxDto violTxDto1, Map<String,String> reports) {
		
		List<DeviceList> deviceList = computeTollDAO.getDeviceUsingEtcAccountID(etcAccountId);
		if(deviceList !=null)
		{
			for (DeviceList deviceNo1 : deviceList) {

				String deviceNo = deviceNo1.getDeviceNo();
				logger.info("=====plate crossmatched Device No================== {}", deviceNo);
				logger.info("=====plate crossmatched================== #9");
				List<EntryTransactionDto> machingDeviceList = computeTollDAO
						.getMatchingDeviceFromPlate(deviceNo, laneId, plazaAgencyId,txTimeStamp);
				logger.info("=====plate crossmatched================== #10");
				if (machingDeviceList != null) {

					for (EntryTransactionDto entryList1 : machingDeviceList) {
						if (entryList1.getDeviceNo().equals(deviceNo)) {

							logger.info("Matched Entry Plate CrossMatched LaneTxId :: ==> " + entryList1.getLaneTxId());
							logger.info("Matched ViolTx CrossMatched Plate LaneTxId :: ==> " + violTxDto1.getLaneTxId());

							logger.info("=====plate crossmatched================== #11");
							computeTollDAO.updateTTransDetailTableVIolTx(entryList1, violTxDto1);// update in T_tran_Detail
							logger.info("=====plate crossmatched================== #12");

							TollCalculationResponseDto tollCalculationResponseDto = getTollCalculationForPlate(violTxDto1,entryList1 );
							TollCalculationResponseDto tollCalculationResponseDto1 = getTollCalculationForRevenueType1(violTxDto1,entryList1 );

							logger.info("=====plate crossmatched called Tollcalculation API================== #12");
							Double fullFareAmount = tollCalculationResponseDto.getFullFare();
							Double extraExcelCharge = tollCalculationResponseDto.getExtraAxleCharge();
							Integer actualExtraExcel = violTxDto1.getActualExtraAxles();

							Double fullFareAmount1 = tollCalculationResponseDto1.getFullFare();
							Double extraExcelCharge1 = tollCalculationResponseDto1.getExtraAxleCharge();
							Integer actualExtraExcel1 = violTxDto1.getActualExtraAxles();

							Double videoFareAmount = fullFareAmount + actualExtraExcel * extraExcelCharge;
							Double etcFareAmount = fullFareAmount1 + actualExtraExcel1 * extraExcelCharge1;

							violTxDto1.setVideoFareAmount(videoFareAmount); 
							violTxDto1.setPostedFareAmount(videoFareAmount);
							violTxDto1.setEtcFareAmount(etcFareAmount);

							// calling update voilTx API
							callviolUpdateAPI(violTxDto1, entryList1, deviceNo, etcAccountId, reports);
							logger.info("=====plate crossmatched================== #13");
							// update status discard for entry transaction
							computeTollDAO.updateEntryDataToDiscardForPlate(entryList1,violTxDto1, reports);
							logger.info("=====plate crossmatched================== #14");
							computeTollDAO.updateEntryTransDetailForDiscard(entryList1, violTxDto1);// update in T_tran_Detail
							logger.info("=====plate crossmatched================== #15");

							TransactionDto tempTxDto1 = getEntryExpiredTransactionDto(entryList1); // call method for entryDiscard transaction send to ATP queue
							TransactionDetailDto transactionDetailDto1 = new TransactionDetailDto();
							transactionDetailDto1.setTransactionDto(tempTxDto1);		
							qatpClassificationService.pushMessage(transactionDetailDto1);
							logger.info("======== Discard Entry Transaction for Device Pushed to ATP queue ================== #16");
						}
					}
				}
			}
		}
	}

	public void getExpiredEntryTransaction(Map<String,String> reports)
	{
		Integer count=0;
		String maxDate = computeTollDAO.getMaxEndDate();

		logger.info("Max End Date =={}", maxDate );

		logger.info(" Entered in Expired Entry Transaction" );
		try {
			List<EntryTransactionDto> expiredEntry = computeTollDAO.getExpiredEntryList(maxDate);
			logger.info(" Total no of records of Expired Entry Transaction {}", expiredEntry.size());
			if(expiredEntry!=null) {
				logger.info("=====Expired Entry================== #1");
				for (EntryTransactionDto expiredEntryList : expiredEntry)
				{
					expiredEntryList.setTxTypeInd(UnmatchedConstant.D);
					expiredEntryList.setTxStatus(UnmatchedConstant.EXPIRED_ENTRY_DISCARD_STATUS);
					logger.info(" Entered in Expired Entry Transaction LaneTxId {}", expiredEntryList.getLaneTxId());
					computeTollDAO.updateExpiredEntryTransaction(expiredEntryList);
					logger.info("=====Expired Entry================== #2");
					computeTollDAO.updateExpiredEntryTransDetailTable(expiredEntryList);//update T_TRAN_DETAIL
					logger.info("=====Expired Entry================== #3");
					TransactionDto tempTxDto = getEntryExpiredTransactionDto(expiredEntryList);
					TransactionDetailDto transactionDetailDto = new TransactionDetailDto();
					transactionDetailDto.setTransactionDto(tempTxDto);		
					qatpClassificationService.pushMessage(transactionDetailDto);
					logger.info("=====Expired Entry================== #4");
					count++;

					reports.put("Discard Entry Expired Count", count.toString());

				}

			}
		}catch(Exception e) {
			logger.info("Exception message in Expired Entry == > {}", e.getMessage() );
		}
	}

	public void getExpiredExitTransaction(String tempEndDate, Map<String,String> reports)
	{
		Integer count1 =0;

		logger.info(" Entered in Expired Exit Transaction" );

		try {
			List<ExitTransactionDto> expiredExit = computeTollDAO.getExpiredExitList(tempEndDate);
			logger.info("=====Expired Exit================== #1");
			logger.info(" Total no of records of Expired Exit Transaction {}", expiredExit.size());
			if (expiredExit != null) {
				logger.info("=====Expired Exit================== #2");
				for (ExitTransactionDto expiredExitList : expiredExit) {
					expiredExitList.setTxTypeInd(UnmatchedConstant.U);
					expiredExitList.setTxSubtypeInd(UnmatchedConstant.X);
					expiredExitList.setTxStatus(UnmatchedConstant.EXPIRED_EXIT_STATUS);

					logger.info(" Entered in Expired Exit Transaction LaneTxId {}", expiredExitList.getLaneTxId());
					TransactionDetailDto transactionDetailDto = new TransactionDetailDto();
					TransactionDto tempTxDto = getExitExpiredTransactionDto(expiredExitList);

					Double discountAmount = getMaxTollAmount(expiredExitList);
					logger.info("=====Expired Exit================== #3");
					//tempTxDto.setEtcFareAmount(discountAmount); //commented as PB & Nived suggested for UAT Bug 71487
					tempTxDto.setPostedFareAmount(discountAmount);

					computeTollDAO.updateExpiredExitTransaction(expiredExitList);
					logger.info("=====Expired Exit================== #4");
					computeTollDAO.updateExpiredExitTransDetailTable(expiredExitList, discountAmount);//update T_TRAN_DETAIL
					logger.info("=====Expired Exit================== #5");
					NY12Dto ny12Dto = getNY12Details(tempTxDto);
					transactionDetailDto.setTransactionDto(tempTxDto);
					TransactionDto txDTO = transactionDetailDto.getTransactionDto();


					Integer count = computeTollDAO.NY12Plan(txDTO.getDeviceNo(), txDTO.getTxDate());
					if (count > 0) {
						logger.info("calling NY plan API...");
						callInsertAPI(ny12Dto, "NY12"); // NY12 API call
						logger.info("=====Expired Exit================== #6");
					} else if (tempTxDto.getPlazaId().equals(UnmatchedConstant.EXTERN_PLAZA_25)
							|| tempTxDto.getPlazaId().equals(UnmatchedConstant.EXTERN_PLAZA_25A)
							|| (tempTxDto.getEntryPlazaId().equals(UnmatchedConstant.EXTERN_PLAZA_25)
									|| tempTxDto.getEntryPlazaId().equals(UnmatchedConstant.EXTERN_PLAZA_25A))) {

						logger.info("calling 25/25A API....."); // 25/25A API call
						callInsertAPI(ny12Dto, "25A");
						logger.info("=====Expired Exit================== #7");
					} else {
						qatpClassificationService.pushMessage(transactionDetailDto);
						logger.info("=====Expired Exit================== #8");
					}

					count1++;

					reports.put("Exit Expired Count", count1.toString());
				}
			}

		}catch(Exception e) {
			logger.info("Exception message in Expired Exit == > {}", e.getMessage() );
		}
	}

	public void getExpiredViolTxTransaction(String tempEndDate, Map<String,String> reportsMap)
	{
		Integer count = 0;
		try {
			logger.info(" ### Entered in ViolTx Expired Transaction ###");
			List<ViolTxDto> expiredViol = computeTollDAO.getExpiredViolList(tempEndDate);
			logger.info(" Total no of records of Expired VIOL_TX Transaction {}", expiredViol.size());
			logger.info("=====Expired VoilTX================== #1");
			if(expiredViol!=null) {

				for (ViolTxDto expiredList : expiredViol)
				{
					Double discountAmount = getMaxTollAmountForExpiredPlate(expiredList);
					expiredList.setVideoFareAmount(discountAmount);
					expiredList.setPostedFareAmount(discountAmount);
					expiredList.setEtcFareAmount(discountAmount);
					logger.info("=====Expired VoilTX================== #2");
					callExpiredViolUpdateAPI(expiredList);
					logger.info(" Entered in ViolTX Transaction LaneTxId {}", expiredList.getLaneTxId());
					logger.info("=====Expired VoilTX================== #5");
					count++;

					reportsMap.put("Expired ViolTX Count", count.toString());
				}

			}
		}catch(Exception e) {
			logger.info("Exception message in Expired ViolTx == > {}", e.getMessage() );
		}
	}
	//below method not in use
	public void getExpiredViolTxTransaction(String tempEndDate, String agencyId, Map<String,String> reportsMap)
	{
		Integer count = 0;
		try {
			logger.info(" ### Entered in ViolTx Expired Transaction ###");
			List<ViolTxDto> expiredViol = computeTollDAO.getExpiredViolList(tempEndDate, agencyId);
			logger.info(" Total no of records of Expired VIOL_TX Transaction {}", expiredViol.size());
			logger.info("=====Expired VoilTX================== #1");
			if(expiredViol!=null) {

				for (ViolTxDto expiredList : expiredViol)
				{
					Double discountAmount = getMaxTollAmountForExpiredPlate(expiredList);
					expiredList.setVideoFareAmount(discountAmount);
					expiredList.setPostedFareAmount(discountAmount);
					logger.info("=====Expired VoilTX================== #2");
					callExpiredViolUpdateAPI(expiredList);
					logger.info(" Entered in ViolTX Transaction LaneTxId {}", expiredList.getLaneTxId());
					logger.info("=====Expired VoilTX================== #5");
					count++;

					reportsMap.put("Expired ViolTX Count", count.toString());
				}

			}
		}catch(Exception e) {
			logger.info("Exception message in Expired ViolTx == > {}", e.getMessage() );
		}
	}


	private Double getMaxTollAmount(ExitTransactionDto exitList) {

		MaxTollDataDto maxTollDataDto = new MaxTollDataDto();
		maxTollDataDto.setEtcAccountId(exitList.getEtcAccountId() != null ? exitList.getEtcAccountId() : 0);
		maxTollDataDto.setLaneTxId(exitList.getLaneTxId());
		maxTollDataDto.setEntryPlazaId(exitList.getEntryPlazaId() != null ? exitList.getEntryPlazaId() : null);
		maxTollDataDto.setTollRevenueType(exitList.getTollRevenueType());
		maxTollDataDto.setcLass(exitList.getActualClass());
		maxTollDataDto.setPlanId(exitList.getPlanTypeId());
		
		maxTollDataDto.setTollRevenueType(exitList.getTollRevenueType());
		maxTollDataDto.setLaneId(exitList.getLaneId());
		maxTollDataDto.setPlazaAgencyId(exitList.getPlazaAgencyId());
		maxTollDataDto.setTxTimestamp(exitList.getTxTimestamp().toString().replace("T", " "));
		maxTollDataDto.setPlazaId(exitList.getPlazaId());
		maxTollDataDto.setDataSource(UnmatchedConstant.TPMS);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		logger.info("Setting data for maxToll: {}",maxTollDataDto);
		Gson gson1 = new Gson();
		try {
			HttpEntity<String> entity = new HttpEntity<String>(gson1.toJson(maxTollDataDto), headers);

			ResponseEntity<String> result = restTemplate.postForEntity(configVariable.getMaxTollUri(), entity,String.class);
			logger.info("MaxToll result for Expired Exit Transaction: {}", result);
			if (result.getStatusCodeValue() == 200) {

				JsonObject jsonObject = new Gson().fromJson(result.getBody(), JsonObject.class);
				logger.info("Got maxToll response for Expired Exit Transaction with laneTxId {}. Response: {}",maxTollDataDto.getLaneTxId(), jsonObject);

				JsonElement element = jsonObject.get("maxTollAmount");
				if(!(element instanceof JsonNull)) {
					return element.getAsDouble();
				} 
				else 
				{
					logger.info("Error: {}",jsonObject.get("message")); 
				}
			} else 
			{
				JsonObject jsonObject = new Gson().fromJson(result.getBody(), JsonObject.class);
				logger.info("Got Max Toll response for Expired Exit transaction{}",jsonObject);
			}

		} catch (Exception e) {
			logger.info("Error: Exception {} in maxTollApi",e.getMessage());
		}
		return 0.0;
	}


	private Double getMaxTollAmountForExpiredPlate(ViolTxDto exitList) {

		MaxTollDataDto maxTollDataDto = new MaxTollDataDto();
		maxTollDataDto.setEtcAccountId(exitList.getEtcAccountId() != null ? exitList.getEtcAccountId() : 0);
		maxTollDataDto.setLaneTxId(exitList.getLaneTxId());
		if(exitList.getEntryPlazaId()!=null && exitList.getEntryPlazaId() != 0 ) {
			maxTollDataDto.setEntryPlazaId(exitList.getEntryPlazaId());
		}
		//maxTollDataDto.setEntryPlazaId(exitList.getEntryPlazaId() != 0 ? exitList.getEntryPlazaId() : null);
		maxTollDataDto.setTollRevenueType(exitList.getTollRevenueType());
		maxTollDataDto.setcLass(exitList.getActualClass());
		maxTollDataDto.setPlanId(UnmatchedConstant.DEFAULT_PLAN_TYPE);
		
		maxTollDataDto.setTollRevenueType(exitList.getTollRevenueType());
		maxTollDataDto.setLaneId(exitList.getLaneId());
		maxTollDataDto.setPlazaAgencyId(exitList.getPlazaAgencyId());
		maxTollDataDto.setTxTimestamp(exitList.getTxTimestamp().toString().replace("T", " "));
		maxTollDataDto.setPlazaId(exitList.getPlazaId());
		maxTollDataDto.setDataSource(UnmatchedConstant.TPMS);
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		logger.info("Setting data for voil Tx Expired maxToll: {}",maxTollDataDto);
		Gson gson1 = new Gson();
		try {
			HttpEntity<String> entity = new HttpEntity<String>(gson1.toJson(maxTollDataDto), headers);

			ResponseEntity<String> result = restTemplate.postForEntity(configVariable.getMaxTollUri(), entity,String.class);
			logger.info("VoilTx Expired transaction MaxToll result: {}", result);
			if (result.getStatusCodeValue() == 200) {

				JsonObject jsonObject = new Gson().fromJson(result.getBody(), JsonObject.class);
				logger.info("Got maxToll response for VoilTx Expired transaction with laneTxId {}. Response: {}",maxTollDataDto.getLaneTxId(), jsonObject);

				JsonElement element = jsonObject.get("maxTollAmount");
				if(!(element instanceof JsonNull)) {
					return element.getAsDouble();
				} 
				else 
				{
					logger.info("Error: {}",jsonObject.get("message")); 
				}
			} else 
			{
				JsonObject jsonObject = new Gson().fromJson(result.getBody(), JsonObject.class);
				logger.info("Got Max Toll response for expired violTx {}",jsonObject);
			}

		} catch (Exception e) {
			logger.info("Error: Exception {} in maxTollApi",e.getMessage());
		}
		return 0.0;
	}

	private TollCalculationResponseDto getTollCalculationForDevice(TransactionDto tempTxDto) {

		TollCalculationDto tollCalculationDto = new TollCalculationDto();

		tollCalculationDto.setEntryPlazaId(tempTxDto.getEntryPlazaId() != null ? tempTxDto.getEntryPlazaId() : 0);
		tollCalculationDto.setExitPlazaId(tempTxDto.getPlazaId());
		tollCalculationDto.setPlanType(tempTxDto.getPlanType());
		tollCalculationDto.setTollRevenueType(tempTxDto.getTollRevenueType());
		tollCalculationDto.setTxDate(tempTxDto.getTxDate());
		tollCalculationDto.setTxTimestamp(tempTxDto.getTxTimeStamp().toString().replace("T", " "));
		tollCalculationDto.setAgencyId(tempTxDto.getPlazaAgencyId());
		tollCalculationDto.setActualClass(tempTxDto.getActualClass());

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		logger.info("Setting data for TollCalculation for Device: {}",tollCalculationDto);
		Gson gson = new Gson();
		try {
			HttpEntity<String> entity = new HttpEntity<String>(gson.toJson(tollCalculationDto), headers);

			ResponseEntity<String> result = restTemplate.postForEntity(configVariable.getTollCalculationUri(), entity,String.class);
			logger.info("Toll Calculation result for device: {}", result);
			if (result.getStatusCodeValue() == 200) {

				JsonObject jsonObject = new Gson().fromJson(result.getBody(), JsonObject.class);
				logger.info("Got tollcalculation response for device : {}", jsonObject);

				// JsonElement element = jsonObject.get("amounts");
				return gson.fromJson(jsonObject.getAsJsonObject("amounts"), TollCalculationResponseDto.class);

			} else {
				JsonObject jsonObject = new Gson().fromJson(result.getBody(), JsonObject.class);
				logger.info("Got tollcalculation response #### for Device: {}", jsonObject);
			}

		} catch (Exception e) {
			logger.info("Error: Exception {} in Toll Calculation API for Device",e.getMessage());
		}
		return null;
	}


	private TollCalculationResponseDto getTollCalculationForPlate(ViolTxDto violTxDto1, EntryTransactionDto entryList) {

		TollCalculationDto tollCalculationDto = new TollCalculationDto();

		tollCalculationDto.setEntryPlazaId(entryList.getEntryPlazaId()!= null ? entryList.getEntryPlazaId() : 0);
		tollCalculationDto.setExitPlazaId(violTxDto1.getPlazaId());
		//tollCalculationDto.setPlanType(violTxDto1.getPlanTypeId());
		tollCalculationDto.setPlanType(UnmatchedConstant.DEFAULT_PLAN_TYPE);
		tollCalculationDto.setTollRevenueType(violTxDto1.getTollRevenueType());
		tollCalculationDto.setTxDate(violTxDto1.getTxDate().toString());
		tollCalculationDto.setTxTimestamp(violTxDto1.getTxTimestamp().toString().replace("T", " "));
		tollCalculationDto.setAgencyId(violTxDto1.getPlazaAgencyId());
		tollCalculationDto.setActualClass(violTxDto1.getActualClass());


		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		logger.info("Setting data for TollCalculation for Plate : {}",tollCalculationDto);
		Gson gson = new Gson();
		try {
			HttpEntity<String> entity = new HttpEntity<String>(gson.toJson(tollCalculationDto), headers);

			ResponseEntity<String> result = restTemplate.postForEntity(configVariable.getTollCalculationUri(), entity,String.class);
			logger.info("Toll Calculation result for plate: {}", result);
			if (result.getStatusCodeValue() == 200) {

				JsonObject jsonObject = new Gson().fromJson(result.getBody(), JsonObject.class);
				logger.info("Got tollcalculation response for plate : {}", jsonObject);

				// JsonElement element = jsonObject.get("amounts");
				return gson.fromJson(jsonObject.getAsJsonObject("amounts"), TollCalculationResponseDto.class);

			} else {
				JsonObject jsonObject = new Gson().fromJson(result.getBody(), JsonObject.class);
				logger.info("Got tollcalculation response for Plate #### : {}", jsonObject);
			}

		} catch (Exception e) {
			logger.info("Error: Exception {} in Toll Calculation API for Plate",e.getMessage());
		}
		return null;
	}
	
	
	private TollCalculationResponseDto getTollCalculationForRevenueType1(ViolTxDto violTxDto1, EntryTransactionDto entryList) {

		TollCalculationDto tollCalculationDto = new TollCalculationDto();

		tollCalculationDto.setEntryPlazaId(entryList.getEntryPlazaId()!= null ? entryList.getEntryPlazaId() : 0);
		tollCalculationDto.setExitPlazaId(violTxDto1.getPlazaId());
		//tollCalculationDto.setPlanType(violTxDto1.getPlanTypeId());
		tollCalculationDto.setPlanType(UnmatchedConstant.DEFAULT_PLAN_TYPE);
		tollCalculationDto.setTollRevenueType(UnmatchedConstant.REVENUE_TYPE_1);
		tollCalculationDto.setTxDate(violTxDto1.getTxDate().toString());
		tollCalculationDto.setTxTimestamp(violTxDto1.getTxTimestamp().toString().replace("T", " "));
		tollCalculationDto.setAgencyId(violTxDto1.getPlazaAgencyId());
		tollCalculationDto.setActualClass(violTxDto1.getActualClass());


		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		logger.info("Setting data for TollCalculation for Plate : {}",tollCalculationDto);
		Gson gson = new Gson();
		try {
			HttpEntity<String> entity = new HttpEntity<String>(gson.toJson(tollCalculationDto), headers);

			ResponseEntity<String> result = restTemplate.postForEntity(configVariable.getTollCalculationUri(), entity,String.class);
			logger.info("Toll Calculation result for plate: {}", result);
			if (result.getStatusCodeValue() == 200) {

				JsonObject jsonObject = new Gson().fromJson(result.getBody(), JsonObject.class);
				logger.info("Got tollcalculation response for plate : {}", jsonObject);

				// JsonElement element = jsonObject.get("amounts");
				return gson.fromJson(jsonObject.getAsJsonObject("amounts"), TollCalculationResponseDto.class);

			} else {
				JsonObject jsonObject = new Gson().fromJson(result.getBody(), JsonObject.class);
				logger.info("Got tollcalculation response for Plate #### : {}", jsonObject);
			}

		} catch (Exception e) {
			logger.info("Error: Exception {} in Toll Calculation API for Plate",e.getMessage());
		}
		return null;
	}


//	@Override
//	public void getExpiredExitTransactions() {
//
//		Map<String,String> reportsMap1=new HashMap<String,String>();
//		processparamDetails = computeTollDAO.getProcessParamtersList();
//		endProcessDate = processparamDetails.stream()
//				.filter(e -> e.getParam_name().equals(UnmatchedConstant.UNMATCHED_END_PROCESS_DATE)).findAny();
//		String tempEndDate = endProcessDate.get().getParam_value();
//		logger.info("=====EndProcessDate for expired exit transaction=====" + endProcessDate.get().getParam_value());
//
//		try {
//
//			getExpiredExitTransaction(tempEndDate, reportsMap1);
//
//			for(Map.Entry<String, String> m:reportsMap1.entrySet()){ 
//				logger.info(m.getKey()+" ==== > "+m.getValue());  
//			}  
//		}catch(Exception e) {
//			logger.info("Exception for Exit expired transaction{}", e);  
//		}
//	}


	@Override
	public void getExpiredEntryTransactions() {
		Map<String,String> reportsMap1=new HashMap<String,String>();
		try {
			getExpiredEntryTransaction(reportsMap1);

			for(Map.Entry<String, String> m:reportsMap1.entrySet()){ 
				logger.info(m.getKey()+" ==== > "+m.getValue());  
			} 

		}catch(Exception e) {
			logger.info("Exception for Entry expired transaction{}", e);  
		}
	}


	@Override
	public void getExpiredExitTransactions() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void getExpiredViolTxTransactions() {
		// TODO Auto-generated method stub
		
	}



//	@Override
//	public void getExpiredViolTxTransactions() {
//
//		Map<String,String> reportsMap1=new HashMap<String,String>();
//		processparamDetails = computeTollDAO.getImageTrxProcessParamtersList();
//
//		endProcessDate = processparamDetails.stream()
//				.filter(e -> e.getParam_name().equals(UnmatchedConstant.IMAGE_TXN_END_PROCESS_DATE)).findAny();
//		String tempEndDate = endProcessDate.get().getParam_value();
//		logger.info("=====EndProcessDate for Voil_Tx transaction=====" + endProcessDate.get().getParam_value());
//
//		try {
//
//			//	getExpiredViolTxTransaction(tempEndDate, agencyId, reportsMap1);
//
//			for(Map.Entry<String, String> m:reportsMap1.entrySet()){ 
//				logger.info(m.getKey()+" ==== > "+m.getValue());  
//			}  
//		}catch(Exception e) {
//			logger.info("Exception for Viol_Tx expired transaction{}", e);  
//		}
//	}






	/*
	 * public static void main(String[] args) {
	 * 
	 * Map<String,String> map=new HashMap<String,String>(); Integer i = 10;
	 * map.put("A B",i.toString()); map.put("B C","Vijay XYZ");
	 * map.put("B C","Rahul AAA"); //Elements can traverse in any order
	 * for(Map.Entry<String, String> m:map.entrySet()){
	 * System.out.println(m.getKey()+" == > "+m.getValue()); } }
	 */
}
