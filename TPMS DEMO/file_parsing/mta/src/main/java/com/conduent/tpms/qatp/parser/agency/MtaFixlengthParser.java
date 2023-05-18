package com.conduent.tpms.qatp.parser.agency;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.conduent.app.timezone.utility.TimeZoneConv;
import com.conduent.tpms.qatp.constants.AckStatusCode;
import com.conduent.tpms.qatp.constants.Constants;
import com.conduent.tpms.qatp.constants.FileStatusInd;
import com.conduent.tpms.qatp.constants.QATPConstants;
import com.conduent.tpms.qatp.dao.IQatpDao;
import com.conduent.tpms.qatp.dao.ITransDetailDao;
import com.conduent.tpms.qatp.dao.TQatpMappingDao;
import com.conduent.tpms.qatp.dao.impl.InsertMTADaoImpl;
import com.conduent.tpms.qatp.dto.AckFileInfoDto;
import com.conduent.tpms.qatp.dto.AckFileWrapper;
import com.conduent.tpms.qatp.dto.MappingInfoDto;
import com.conduent.tpms.qatp.dto.MtaDetailInfoVO;
import com.conduent.tpms.qatp.dto.MtaFileNameDetailVO;
import com.conduent.tpms.qatp.dto.MtaHeaderinfoVO;
import com.conduent.tpms.qatp.dto.MtaTrailerInfoVO;
import com.conduent.tpms.qatp.dto.TollCalculationDto;
import com.conduent.tpms.qatp.dto.TollCalculationResponseDto;
import com.conduent.tpms.qatp.exception.TRXFileNumDuplicationException;
import com.conduent.tpms.qatp.exception.CustomException;
import com.conduent.tpms.qatp.exception.EmptyLineException;
import com.conduent.tpms.qatp.exception.InvalidFileNameException;
import com.conduent.tpms.qatp.exception.InvalidRecordCountException;
import com.conduent.tpms.qatp.exception.InvlidFileDetailException;
import com.conduent.tpms.qatp.exception.InvlidFileHeaderException;
import com.conduent.tpms.qatp.model.AtpFileIdObject;
import com.conduent.tpms.qatp.model.ConfigVariable;
import com.conduent.tpms.qatp.model.ReconciliationCheckPoint;
import com.conduent.tpms.qatp.model.TranDetail;
import com.conduent.tpms.qatp.model.Transaction;
import com.conduent.tpms.qatp.service.ITransDetailService;
import com.conduent.tpms.qatp.utility.AsyncProcessCall;
import com.conduent.tpms.qatp.utility.Convertor;
import com.conduent.tpms.qatp.utility.GenericValidation;
import com.conduent.tpms.qatp.utility.LocalDateAdapter;
import com.conduent.tpms.qatp.utility.LocalDateTimeAdapter;
import com.conduent.tpms.qatp.utility.MasterDataCache;
import com.conduent.tpms.qatp.utility.OffsetDateTimeConverter;
import com.conduent.tpms.qatp.validation.FileParserImpl;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

@Component
public class MtaFixlengthParser extends FileParserImpl {
	
	TollCalculationResponseDto tollAmount;
	
	@Autowired
	TimeZoneConv timeZoneConv;
	
	@Autowired
	ConfigVariable configVariable;
	
	@Autowired
	private RestTemplate restTemplate;

	
	public MtaDetailInfoVO detailVO;
	public MtaTrailerInfoVO trailerVO;
	private List<MtaDetailInfoVO> detailsRecord;
	private boolean check = true;

	private static final Logger logger = LoggerFactory.getLogger(MtaFixlengthParser.class);
	
	public MtaFixlengthParser(TQatpMappingDao tQatpMappingDao,IQatpDao qatpDao,ITransDetailService transDetailService,GenericValidation genericValidation,
			MasterDataCache masterDataCache,InsertMTADaoImpl insertMtaDaoImpl,TimeZoneConv timeZoneConv,AsyncProcessCall asyncProcessCall,ITransDetailDao transDetailDao)
	{
		this.tQatpMappingDao=tQatpMappingDao;
		this.qatpDao=qatpDao;
		this.transDetailService=transDetailService;
		this.genericValidation=genericValidation;
		this.masterDataCache=masterDataCache;
		this.insertMtaDaoImpl=insertMtaDaoImpl;
		this.timeZoneConv=timeZoneConv;
		this.asyncProcessCall=asyncProcessCall;
		this.transDetailDao=transDetailDao;
		//this.textFileReader=textFileReader;
		initialize();
	}

	@Override
	public void initialize() {
		logger.info("Initializing process...");
		setFileFormat(QATPConstants.CSV);
		setFileType(QATPConstants.TRX);
		setAgencyId(QATPConstants.HOME_AGENCY_ID);
		setIsHederPresentInFile(true);
		setIsDetailsPresentInFile(true);
		setIsTrailerPresentInFile(true);
		getFileParserParameter().setAgencyId("002");
		getFileParserParameter().setFileType(QATPConstants.TRX);
		fileNameVO = new MtaFileNameDetailVO();
		detailVO = new MtaDetailInfoVO();
		headerVO = new MtaHeaderinfoVO();
		trailerVO = new MtaTrailerInfoVO();
		detailsRecord = new ArrayList<MtaDetailInfoVO>();
		
	}

	@Override
	public void processStartOfLine(String fileSection) {
		if (fileSection.equals("DETAIL")) {
			detailVO = new MtaDetailInfoVO();
		}
	}

	@Override
	public void processEndOfLine(String fileSection) {
		if (fileSection.equals("DETAIL")) {
			detailsRecord.add(detailVO);
		}
	}

	@Override
	public void doFieldMapping(String value, MappingInfoDto fMapping) throws InvlidFileHeaderException, TRXFileNumDuplicationException {
		check = true;
		
		String val = value.isBlank() ? null : value;
		
		switch (fMapping.getFieldName()) 
		{
		case "F_HOST_SYSTEM":
			fileNameVO.setHostSystem(val);
			break;
		case "F_PLAZA":
			fileNameVO.setPlazaId(val);
			break;
		case "F_FILE_DATE":
			fileNameVO.setFileDate(val);
			break;

		case "F_FILE_HOUR":
			fileNameVO.setFileHour(val);
			break;
		
		case "F_NUMBER_OF_FILES":
			fileNameVO.setNumberOfFiles(val);
			break;
		
		case "F_FILE_EXTENSION":
			fileNameVO.setFileExtension(val);
			break;
		
		case "H_REC_TYPE":
			headerVO.setRecordType(val);
			break;	
			
		case "H_SERVICE_CENTER":
			headerVO.setServiceCenter(val);
			break;
		
		case "H_HUB_ID":
			headerVO.setHubId(val);
			break;
			
		case "H_AGENCY_ID":
			headerVO.setAgencyId(val);
			break;		

		case "H_LAST_UPLOAD":
			headerVO.setLastUpload(val);
			break;

		case "H_CURRENT_TS":
			headerVO.setCurrentTs(val);
			break;

		case "H_TOTAL":
			headerVO.setTotal(val);
			break;
			
		case "H_PREV_FILE_NAME":
			headerVO.setPrevFileName(val);
			break;

		case "D_REC_TYPE":
			detailVO.setRecordType(val);
			break;

		case "D_TRANS_TYPE":
			detailVO.setTransType(val);
			break;

		case "D_TAG_AGENCY_ID":
			detailVO.setTagAgencyId(val);
			break;

		case "D_TAG_SERIAL_NUMBER":
			detailVO.setTagSerialNumber(val);
			break;
			
		case "D_TCS_ID":
			detailVO.setTcsId(val);
			break;
			
		case "D_TS_CLASS":
			detailVO.setTsClass(val);
			break;
			
		case "D_TAG_CLASS":
			detailVO.setTagClass(val);
			break;

		case "D_VEHICLE_CLASS":
			detailVO.setVehicleClass(val);
			break;

		case "D_AVC_PROFILE":
			detailVO.setAvcProfile(val);
			break;

		case "D_AVC_AXLES":
			detailVO.setAvcAxles(val);
			break;

		case "D_EXIT_LANE_NUMBER":
			detailVO.setExitLaneNumber(val);
			break;

		case "D_EXIT_TRANSIT_DATE":
			detailVO.setExitTransitDate(val);
			break;

		case "D_EXIT_TRANSIT_TIME":
			detailVO.setExitTransitTime(val);
			break;
		
		case "D_EXIT_PLAZA":
			detailVO.setExitPlaza(val);
			break;
		
		case "D_ENTRY_LANE_NUMBER":
			detailVO.setEntryLaneNumber(val);
			break;

		case "D_ENTRY_TRANSIT_DATE":
			detailVO.setEntryTransitDate(val);
			break;

		case "D_ENTRY_TRANSIT_TIME":
			detailVO.setEntryTransitTime(val);
			break;
		
		case "D_ENTRY_PLAZA":
			detailVO.setEntryPlaza(val);
			break;
			
		case "D_DIRECTION":
			detailVO.setDirection(val);
			break;

		case "D_TI1_TAG_READ_STATUS":
			detailVO.setTi1TagReadStatus(val);
			break;
		
		case "D_TI2_ADDITIONAL_TAGS":
			detailVO.setTi2AdditionalTags(val);
			break;
			
		case "D_TI3_CLASS_MISMATCH_FLAG":
			detailVO.setTi3ClassMismatchFlag(val);
			break;
			
		case "D_TI4_TAG_STATUS":
			detailVO.setTi4TagStatus(val);
			break;
			
		case "D_TI5_PAYMENT_FLAG":
			detailVO.setTi5PaymentFlag(val);
			break;
			
		case "D_TI6_REVENUE_FLAG":
			detailVO.setTi6RevenueFlag(val);
			break;
			
		case "D_VEHICLE_SPEED":
			detailVO.setVehicleSpeed(val);
			break;

		case "D_FRONT_OCR_PLATE_COUNTRY":
			detailVO.setFrontOcrPlateCountry(val);
			break;

		case "D_FRONT_OCR_PLATE_STATE":
			detailVO.setFrontOcrPlateState(val);
			break;

		case "D_FRONT_OCR_PLATE_TYPE":
			detailVO.setFrontOcrPlateType(val);
			break;

		case "D_FRONT_OCR_PLATE_NUMBER":
			detailVO.setFrontOcrPlateNumber(val);
			break;

		case "D_FRONT_OCR_CONFIDENCE":
			detailVO.setFrontOcrConfidence(val);
			break;

		case "D_FRONT_OCR_IMAGE_INDEX":
			detailVO.setFrontOcrImageIndex(val);
			break;

		case "D_FRONT_IMAGE_COLOR":
			detailVO.setFrontImageColor(val);
			break;

		case "D_REAR_OCR_PLATE_COUNTRY":
			detailVO.setRearOcrPlateCountry(val);;
			break;

		case "D_REAR_OCR_PLATE_STATE":
			detailVO.setRearOcrPlateState(val);
			break;

		case "D_REAR_OCR_PLATE_TYPE":
			detailVO.setRearOcrPlateType(val);
			break;

		case "D_REAR_OCR_PLATE_NUMBER":
			detailVO.setRearOcrPlateNumber(val);
			break;

		case "D_REAR_OCR_CONFIDENCE":
			detailVO.setRearOcrConfidence(val);
			break;

		case "D_REAR_OCR_IMAGE_INDEX":
			detailVO.setRearOcrImageIndex(val);
			break;

		case "D_REAR_IMAGE_COLOR":
			detailVO.setRearImageColor(val);
			break;
			
		case "D_BEST_IMAGE":
			detailVO.setBestImage(val);
			break;
			
		case "D_EVENT":
			detailVO.setEvent(val);
			break;

		case "D_HOV_FLAG":
			detailVO.setHovFlag(val);
			break;

		case "D_HOV_PRESENCE_TIME":
			detailVO.setHovpresenceTime(val);
			break;

		case "D_HOV_LOSTPRESENCE_TIME":
			detailVO.setHovlostPresenceTime(val);
			break;

		case "D_LONGITUDE":
			detailVO.setLongitude(val);
			break;
			
		case "D_LATITUDE":
			detailVO.setLatitude(val);
			break;
			
		case "D_VMT":
			detailVO.setVmt(val);
			break;
			
		case "D_DURATION":
			detailVO.setDuration(val);
			break;
			
		case "D_VEHICLE_TYPE":
			detailVO.setVehicleType(val);
			break;
			
		case "D_VEHICLE_SUB_TYPE":
			detailVO.setVehicleSubType(val);
			break;
			
		case "D_DISCOUNT_PLAN_FLAG_CBD":
			detailVO.setDiscountPlanFlagCbd(val);
			break;
			
		case "D_CBD_POSTING_PLAN":
			detailVO.setCbdPostingPlan(val);
			break;
			
		case "D_AVC_DISCREPANCY_FLAG":
			detailVO.setAvcDiscrepancyFlag(val);
			break;
		
		case "D_DEGRADED":
			detailVO.setDegraded(val);
			break;

		case "D_EXPECTED_TOLL":
			detailVO.setExpectedToll(val);
			break;

		case "D_APPLIED_TOLL":
			detailVO.setAppliedToll(val);
			break;
			
		case "D_EXPECTED_ACCOUNT_TYPE":
			detailVO.setExpectedAccountType(val);
			break;
			
		case "D_HOME_EZPASS_RATE":
			detailVO.setHomeEzpassRate(val);
			break;
			
		case "D_ORIG_TCS_ID":
			detailVO.setOrigTcsId(val);
			break;
			
		case "D_UO_CODE":
			detailVO.setUoCode(val);
			break;
			
		case "D_UVID":
			detailVO.setUvid(val);
			break;
			
		case "D_DISPLAY_DATE":
			detailVO.setDisplayDate(val);
			break;
			
		case "D_DISPLAY_TIME":
			detailVO.setDisplayTime(val);
			break;
			
		case "D_DISPLAY_PLAZA":
			detailVO.setDisplayPlaza(val);
			break;
			
		case "D_PAYEE_ID":
			detailVO.setPayeeId(val);
			break;

		case "D_HOME_MID_TIER_RATE":
			detailVO.setHomeMidTierRate(val);
			break;

		case "D_AWAY_EZPASS_RATE":
			detailVO.setAwayEzpassRate(val);
			break;
			
		case "D_TBM_RATE":
			detailVO.setTbmRate(val);
			break;
			
		case "D_TRIP_TYPE":
			detailVO.setTripType(val);
			break;
			
		case "D_TRIP_QUANTITY_LIMIT":
			detailVO.setTripQuantityLimit(val);
			break;
			
		case "D_TRIP_AMOUNT_LIMIT":
			detailVO.setTripAmountLimit(val);
			break;
			
		case "D_TRIP_CHARGEABLE_TRX_REASON":
			detailVO.setTripChargeableTrxReason(val);
			break;
			
		case "D_TRIP_QUANTITY_REASON":
			detailVO.setTripQuantityReason(val);
			break;
			
		case "D_APPLIED_TOLL_REASON":
			detailVO.setAppliedTollReason(val);
			break;
			
		case "D_TRIP_UPCHARGE_FLAG":
			detailVO.setTripUpchargeFlag(val);
			break;
			
		case "D_MANUAL_FLAG":
			detailVO.setManualFlag(val);
			break;
			
		case "D_TAG_HOME_AGENCY":
			detailVO.setTagHomeAgency(val);
			break;
			
		case "T_REC_TYPE":
			trailerVO.setRecType(val);
			break;
				
		default:
		}

	}

	@Override
	public void finishFileProcess() throws CustomException {
		try {
			for (MtaDetailInfoVO obj : detailsRecord) {
			logger.info(obj.toString());
			Transaction t = obj.getTransaction(masterDataCache, xferControl,headerVO, tQatpMappingDao);
			logger.info(t.toString());
			try
			{
				transDetailDao.saveTransDetail(t.getTranDetail(timeZoneConv, masterDataCache));
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
		}
			
		}catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}		
	}
	@Override
	public void saveRecords(ReconciliationCheckPoint reconciliationCheckPoint)
	{
		if (detailsRecord != null && !detailsRecord.isEmpty() && detailsRecord.size() > 0)
			try {
				{
					List<TranDetail> list = new ArrayList<TranDetail>();
					List<Long> txSeqlist = new LinkedList<>();
					Transaction trxn = null;
					TranDetail tranDetail=null;
					
					for (MtaDetailInfoVO obj : detailsRecord) 
					{
						trxn = obj.getTransaction(masterDataCache, xferControl,headerVO, tQatpMappingDao);
						tranDetail = trxn.getTranDetail(timeZoneConv, masterDataCache);						
						
						setAmounts(tranDetail);
						
						logger.info(list.toString());
						list.add(tranDetail);
					}
					
					txSeqlist = transDetailDao.loadNextSeq(list.size());
					setLaneTxId(list, txSeqlist, list.size());
					
					transDetailDao.batchInsert(list); //remove comment in next part
					
					TranDetail lastRecord = list.get(list.size() - 1);
					
					reconciliationCheckPoint.setLastLaneTxTd(lastRecord.getLaneTxId());
					reconciliationCheckPoint.setLastTxTimeStatmp(lastRecord.getTxTimeStamp() !=null ?
							lastRecord.getTxTimeStamp().toLocalDateTime(): null);
					reconciliationCheckPoint.setLastExternLaneId(lastRecord.getExternLaneId());
					reconciliationCheckPoint.setLastExternPlazaId(lastRecord.getExternPlazaId());
					//reconciliationCheckPoint.setFileSeqNum(lastRecord.getFileSeqNum());
					reconciliationCheckPoint.setFileType(Constants.TRX);
					reconciliationCheckPoint.setXferControlId(xferControl.getXferControlId());
					
					//collect atpFileID
					List<AtpFileIdObject> atpFileIdList = transDetailService.getAtpFileId(xferControl.getXferControlId());
					logger.info("Atp File Id {} ",atpFileIdList);
					
					try 
					{
						transDetailService.publishMessages(list, atpFileIdList); //remove comment in next part
							
						check = false;
						setIsHederPresentInFile(false);
						setIsDetailsPresentInFile(false);
					} 
					catch (Exception e) 
					{
						logger.info("Exception {}",e.getMessage());
					}
					
					detailsRecord.clear();
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

	public void setAmounts(TranDetail tranDetail) 
	{
		tranDetail.setEtcFareAmount(0d);
		tranDetail.setExpectedRevenueAmount(0d);
		tranDetail.setPostedFareAmount(0d);
		tranDetail.setVideoFareAmount(0d);
		tranDetail.setCashFareAmount(0d);
		tranDetail.setItolFareAmount(0d);
		tranDetail.setUnrealizedAmount(0d);

		
		TollCalculationResponseDto rs1 = calculateTollAmount(tranDetail, 60, 1);
		if(rs1 != null) 
		{
			Double fullFare = rs1.getFullFare() != null ? rs1.getFullFare() : 0d;
			Double extraAxleChargeCash = rs1.getExtraAxleChargeCash() != null ? rs1.getExtraAxleChargeCash() : 0d;
			Integer extraAxles = tranDetail.getExtraAxles() != null ? tranDetail.getExtraAxles() : 0;
			
			Double etcFareAmount = fullFare + (extraAxles * extraAxleChargeCash);
			tranDetail.setEtcFareAmount(etcFareAmount);
			tranDetail.setExpectedRevenueAmount(etcFareAmount);
			tranDetail.setPostedFareAmount(etcFareAmount);	
			tranDetail.setItolFareAmount(etcFareAmount);
		}	
		
		TollCalculationResponseDto rs2 = calculateTollAmount(tranDetail, 60, 217);
		if(rs2 != null) 
		{
			Double fullFare = rs2.getFullFare() != null ? rs2.getFullFare() : 0d;
			Double extraAxleChargeCash = rs2.getExtraAxleChargeCash() != null ? rs2.getExtraAxleChargeCash() : 0d;
			Integer extraAxles = tranDetail.getExtraAxles() != null ? tranDetail.getExtraAxles() : 0;
			
			Double videoFareAmount = fullFare + (extraAxles * extraAxleChargeCash);
			tranDetail.setVideoFareAmount(videoFareAmount);
		}	
		
	}
	
	private TollCalculationResponseDto calculateTollAmount(TranDetail tranDetail, Integer tollRevenueType, Integer planType) {

		TollCalculationDto tollCalculationDto = new TollCalculationDto();

		tollCalculationDto.setEntryPlazaId(tranDetail.getEntryPlazaId() != null ? tranDetail.getEntryPlazaId() : 0);
		tollCalculationDto.setExitPlazaId(tranDetail.getPlazaId());
		tollCalculationDto.setPlanType(planType);
		tollCalculationDto.setTollRevenueType(tollRevenueType);
		tollCalculationDto.setTxTimestamp(tranDetail.getTxTimeStamp().toString().replace("T", " "));
		tollCalculationDto.setAgencyId(tranDetail.getPlazaAgencyId());
		tollCalculationDto.setActualClass(tranDetail.getActualClass());
		tollCalculationDto.setAccountType(tranDetail.getAccountType());
		tollCalculationDto.setTollSystemType(tranDetail.getTollSystemType());
		tollCalculationDto.setLaneTxId(tranDetail.getLaneTxId());
		

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		logger.info("Setting data for TollCalculation for Device: {}",tollCalculationDto);
		Gson gson = new Gson();
		
		try 
		{
			HttpEntity<String> entity = new HttpEntity<String>(gson.toJson(tollCalculationDto), headers);

			ResponseEntity<String> result = restTemplate.postForEntity(configVariable.getTollCalculationUri(), entity,String.class);
			logger.info("Toll Calculation result: {}", result);
			
			if (result.getStatusCodeValue() == 200) 
			{

				JsonObject jsonObject = new Gson().fromJson(result.getBody(), JsonObject.class);
				//logger.info("Got tollcalculation response for device : {}", jsonObject);

				return gson.fromJson(jsonObject.getAsJsonObject("amounts"), TollCalculationResponseDto.class);

			} 
			else
			{
				JsonObject jsonObject = new Gson().fromJson(result.getBody(), JsonObject.class);
				logger.info("Got toll calculation response: {}", jsonObject.toString());
				return null;
			}

		} 
		catch (Exception e) 
		{
			logger.info("Error: Exception {} in Toll Calculation API for Device",e.getMessage());
			return null;
		}		
	}
	
	private void setLaneTxId(List<TranDetail> tempTxlist, List<Long> txSeqlist, Integer sizeOftxRec) 
	{
		for (int i = 0; i < sizeOftxRec; i++) 
		{
			tempTxlist.get(i).setLaneTxId(txSeqlist.get(i));
		}
	}

	@Override
	public AckFileWrapper prePareAckFileMetaData(AckStatusCode ackStatusCode, File file) 
	{
		AckFileWrapper ackObj = new AckFileWrapper();
		LocalDateTime date = LocalDateTime.now();
		String recCount = String.format("%08d", recordCount);

		StringBuilder sbFileName = new StringBuilder();
		
		//sbFileName.append("MTA_").append(file.getName().split("\\.")[0]).append(QATPConstants.ACK_EXT);
		sbFileName.append(file.getName().split("\\.")[0]).append("_TRX").append(QATPConstants.ACK_EXT);
		
		logger.info("File Name : {}", sbFileName.toString());
		
		StringBuilder sbFileContent = new StringBuilder();
		
		if(ackStatusCode.getCode().equals("07"))
		{
			headerVO.setTotal(QATPConstants.DEFAULT_COUNT);
		}
		
		sbFileContent.append("ACK ").append(",")
			.append(StringUtils.rightPad(file.getName(), 50, "")).append(",")
			.append(genericValidation.getTodayTimestamp(date).toString().substring(0, 8)).append(",")
			.append(genericValidation.getTodayTimestamp(date).toString().substring(8, 14)).append(",")
			.append(ackStatusCode.getCode());
		
		logger.info("File Content : {}", sbFileContent.toString());
		
		ackObj.setAckFileName(sbFileName.toString());
		ackObj.setSbFileContent(sbFileContent.toString());
		ackObj.setFile(file);
		if (ackStatusCode.equals(AckStatusCode.SUCCESS) || ackStatusCode.equals(AckStatusCode.GAP_IN_SEQ_NUM)) 
		{
			ackObj.setFileDestDir(getConfigurationMapping().getFileInfoDto().getProcDir());
		} 
		else if (ackStatusCode.equals(AckStatusCode.HEADER_FAIL) || ackStatusCode.equals(AckStatusCode.DETAIL_FAIL)
				|| ackStatusCode.equals(AckStatusCode.INVALID_RECORD_COUNT) || ackStatusCode.equals(AckStatusCode.DUPLICATE_FILE_NUM)) 
		{
			ackObj.setFileDestDir(getConfigurationMapping().getFileInfoDto().getUnProcDir());
		}

		/* Prepare AckStatus table */
		AckFileInfoDto ackFileInfoDto = new AckFileInfoDto();
		ackFileInfoDto.setAckFileDate(LocalDate.now());
		LocalTime conv_date = timeZoneConv.currentTime().toLocalTime();
		String fileTime = genericValidation.getTodayTimestamp(conv_date, "hhmmss");
		ackFileInfoDto.setAckFileTime(fileTime);
		ackFileInfoDto.setFileType(getFileFormat());
		ackFileInfoDto.setAckFileName(sbFileName.toString());
		ackFileInfoDto.setAckFileStatus(ackStatusCode.getCode());
		ackFileInfoDto.setFileType(getFileType());
		ackFileInfoDto.setTrxFileName(file.getName());
		ackFileInfoDto.setFromAgency(QATPConstants.MTA_FROM_AGENCY); 
		ackFileInfoDto.setToAgency(QATPConstants.MTA_DEVICE_PREFIX); 
		ackFileInfoDto.setExternFileId(xferControl.getXferControlId());
		ackObj.setAckFileInfoDto(ackFileInfoDto);
		return ackObj;
	}

	public void prepareAck(String statusCode) {
		// createAckFile("","",null);
	}

	@Override
	public void validateRecordCount() throws InvalidRecordCountException {
		if (recordCount != Convertor.toLong(headerVO.getTotal())) {
			throw new InvalidRecordCountException("Invalid Record Count");
		}
	}

	@Override
	public void processedPartiallyExecutedFile(File file,String externFileId) throws IOException, InvalidFileNameException, InvlidFileHeaderException, EmptyLineException, InvlidFileDetailException, InterruptedException, InvalidRecordCountException, TRXFileNumDuplicationException
	{
		logger.info("Started with partially executing of file {}",file.getName());
		boolean endOfFile = false;
		LocalDateTime from = null;
		
		BufferedReader reader = getFileReader(file);
		String previousLine = reader.readLine();
		String currentLine = reader.readLine();
		
		
		for(;;currentLine=reader.readLine())
		{					
			//Get all values available in line
			final String DELIMITER = ",";	
	        String[] value = currentLine.split(DELIMITER);
			
			if(value[4].matches(externFileId.toString())) //TCS_ID from the currentLine
			{
				String nextRecord = reader.readLine();
				for(;;nextRecord=reader.readLine())
				{
					if(!nextRecord.equals(Constants.E))
					{
						from = LocalDateTime.now();
						isFirstRead=false;
						parseAndValidateDetails(nextRecord);
						
						if(detailsRecord.size() >0)
						{
							saveRecords(reconciliationCheckPoint);
						}
						logger.debug("Total time taken for processing 1 record of partially executed file - Completed in {} ms", ChronoUnit.MILLIS.between(from, LocalDateTime.now()));
					}
					else
					{
						logger.info("End of file");
						closeFile();
						reconciliationCheckPoint.setRecordCount(Long.valueOf(recordCount).intValue());
						reconciliationCheckPoint.setFileStatusInd(FileStatusInd.COMPLETE);
						tQatpMappingDao.updateRecordCount(reconciliationCheckPoint);
						endOfFile =true;
						break;
					}
				}
			}
			if(endOfFile)
			{
				break;
			}
		}	
	}
	
	
	
	
	
	
	/*
	 * public Transaction getTransaction() {
	 * 
	 * Transaction obj = new Transaction();
	 * 
	 * if (detailVO.getAgTrxType().equals("E")) { obj.setTrxType("E");
	 * obj.setTrxSubtype("Z"); } else if (detailVO.getAgTrxType().equals("V")) {
	 * obj.setTrxType("E"); obj.setTrxSubtype("Z"); } else if
	 * (detailVO.getAgTrxType().equals("I")) { obj.setTrxType("E");
	 * obj.setTrxSubtype("Z"); } else { obj.setTrxType("R"); obj.setTrxSubtype("X");
	 * }
	 * 
	 * obj.setTollSystemType("B");
	 * 
	 * if (detailVO.getAgTrxType().contentEquals("V")) { obj.setTollRevenueType(19);
	 * } else if (detailVO.getAgTrxType().equals("I")) { obj.setTollRevenueType(50);
	 * } else { obj.setTollRevenueType(1);
	 * 
	 * }
	 * 
	 * obj.setEntryDate("********"); obj.setEntryTime("******");
	 * obj.setEntryLaneSN((long) 0);// need to discuss obj.setEntryDataSource("*");
	 * obj.setEntryVehicleSpeed(0);// need to discuss
	 * obj.setDeviceAgencyId(detailVO.getAgTagAgency());
	 * obj.setDeviceNumber(detailVO.getAgTagSerialNumber());
	 * 
	 * if (!"EMVI".contains(detailVO.getAgLaneMode())) { obj.setTrxType("R");
	 * obj.setTrxSubtype("M"); } if ("EV".contains(detailVO.getAgLaneMode())) {
	 * obj.setLaneMode("1"); } else if ("MI".contains(detailVO.getAgLaneMode())) {
	 * obj.setLaneMode("7"); } else { obj.setLaneMode("0");
	 * 
	 * }
	 * 
	 * obj.setLaneState("*"); obj.setCollectorNumber((long) 0);// need to discuss
	 * 
	 * 
	 * 11th condition
	 * 
	 * 
	 * 
	 * 
	 * obj.setTagClass(Convertor.toLong(detailVO.getAgTagClass()));// null
	 * obj.setTagAxles(Convertor.toInteger(detailVO.getAgAvcExtraAxles()));
	 * obj.setTagIAGClass((long) 0);// need to discuss
	 * obj.setAvcClass(Convertor.toInteger(detailVO.getAgAvcClass()));// null
	 * obj.setAvcAxles(Convertor.toLong(detailVO.getAgAvcExtraAxles()));
	 * obj.setEtcValidation(Convertor.toLong(detailVO.getAgValidationStatus()));//
	 * number obj.setReadPerf(Convertor.toInteger(detailVO.getAgReadPerformance()));
	 * obj.setWritePerf(Convertor.toInteger(detailVO.getAgWritePerf()));
	 * 
	 * if ("SUF*".contains(detailVO.getAgTagPgmStatus())) {
	 * obj.setProgStat(detailVO.getAgTagPgmStatus()); } if
	 * (detailVO.getAgValidationStatus().equals("V")) { obj.setImageCaptured("T"); }
	 * else { obj.setImageCaptured("F"); }
	 * 
	 * obj.setVehicleSpeed(Convertor.toInteger(detailVO.getAgExitSpeed()));// db
	 * getting 1 obj.setSpeedViolation(detailVO.getAgOverSpeed());
	 * 
	 * if (!validation.dateValidation(detailVO.getAgTrxDate(), "YYYYMMDD")) {
	 * obj.setTrxType("R"); obj.setTrxSubtype("D"); } else {
	 * obj.setTrxDate(detailVO.getAgTrxDate()); } if
	 * (!validation.timeValidation(detailVO.getAgTrxTime(), "HHMMSS")) {
	 * obj.setTrxType("R"); obj.setTrxSubtype("D"); } else {
	 * obj.setTrxDate(detailVO.getAgTrxTime());
	 * 
	 * } obj.setTrxLaneSN(Convertor.toLong(detailVO.getAgTrxLaneSeq()));
	 * 
	 * 
	 * 25 26 27
	 * 
	 * 
	 * if (!tLaneImpl.getAllTLane().contains(detailVO.getAgTrxLane())) {
	 * obj.setTrxType("R"); obj.setTrxSubtype("L"); } if
	 * (!tPlazaImpl.getAll().contains(detailVO.getAgTrxPlaza())) {
	 * obj.setTrxType("R"); obj.setTrxSubtype("L"); }
	 * 
	 * 
	 * 27
	 * 
	 * 
	 * obj.setTrxLaneSN(Convertor.toLong(detailVO.getAgTrxLaneSeq()));
	 * 
	 * if (detailVO.getAgReadType().equals("B")) { obj.setBufRead("T"); } else {
	 * obj.setBufRead("T"); }
	 * 
	 * obj.setExtHostRefNum(Convertor.toLong(detailVO.getAgTrxSerialNum()));
	 * obj.setReceivedDate(obj.getTrxDate());
	 * 
	 * if ("********".contains(obj.getDeviceNumber()) ||
	 * "00000000".contains(obj.getDeviceNumber()) ||
	 * "        ".contains(obj.getDeviceNumber())) {
	 * obj.setLicensePlate(detailVO.getAgLicNumber());
	 * obj.setLicenseState(detailVO.getAgLicState());
	 * //obj.setLicPlateType(detailVO.getAgLicType()); not present in transaction //
	 * obj.setConfidenceLevel(detailVO.getAgConfidence()); not present in
	 * transaction obj.setDeviceAgencyId("000"); obj.setDeviceNumber("00000000"); }
	 * else { obj.setLicensePlate("********"); obj.setLicenseState("**"); //
	 * obj.setLicPlateType("0"); not present in transaction //
	 * obj.setConfidenceLevel("0"); not present in transaction }
	 * 
	 * obj.setLaneHealth("****"); obj.setReaderId("****"); obj.setCorrection("***");
	 * obj.setTourSegmentId((long) 0); obj.setDstFlag("*");
	 * obj.setLaneDataSource("F");
	 * 
	 * obj.setFullFareAmt(Convertor.toLong(detailVO.getAgEzpassAmount())); //
	 * obj.setDiscountedFareOne(Convertor.toLong(detailVO.getAgEzpassAmountDs1()));
	 * //
	 * obj.setDiscountedFareTwo(Convertor.toLong(detailVO.getAgEzpassAmountDs2()));
	 * // obj.setVideoFareAmount(detailVO.getAgVideoAmount()); //
	 * obj.setTollAmount(Convertor.toLong(obj.getVideoFareAmount()));
	 * 
	 * 
	 * if (obj.getTrxType().equals("E")) { if
	 * (!detailVO.getAgPlanCharged().equals("NR")) { if
	 * (("SC".contains(detailVO.getAgPlanCharged())) ||
	 * ("CP".contains(detailVO.getAgPlanCharged()))) { obj.setLaneMode("8"); if
	 * ((obj.getFullFareAmt().equals("0") && obj.getDiscountedFareOne().equals("0"))
	 * && obj.getDiscountedFareTwo().equals("0") &&
	 * obj.getVideoFareAmount().equals("0")) { obj.setPlanCharged("888");
	 * obj.setTollAmount((long) 0); } else { long lowestToll1 =
	 * Math.min(obj.getFullFareAmt(), obj.getDiscountedFareOne()); long lowestToll2
	 * = Math.min(obj.getDiscountedFareTwo(),
	 * Convertor.toLong(obj.getVideoFareAmount())); long lowestTol =
	 * Math.min(lowestToll1, lowestToll2); obj.setTollAmount(lowestTol);
	 * obj.setPlanCharged("888"); if (lowestTol == obj.getDiscountedFareOne()) {
	 * obj.setPlanCharged("102"); } else if (lowestTol ==
	 * obj.getDiscountedFareTwo()) { if (detailVO.getAgPlanCharged().equals("SC") ||
	 * detailVO.getAgPlanCharged().equals("CP")) { obj.setPlanCharged("108"); } else
	 * if (detailVO.getAgPlanCharged().equals("SG") ||
	 * detailVO.getAgPlanCharged().equals("GRN")) { obj.setPlanCharged("054");
	 * 
	 * } else if (lowestTol == obj.getFullFareAmt()) { obj.setPlanCharged("888");
	 * 
	 * } else { obj.setPlanCharged("888");
	 * 
	 * }
	 * 
	 * } else { obj.setTollAmount(obj.getFullFareAmt()); obj.setPlanCharged("999");
	 * 
	 * } } } else { obj.setAtrnPlanType("0");
	 * obj.setAtrnPlanType(detailVO.getAgPlanCharged());
	 * 
	 * } } }
	 */

	// return obj;

}
