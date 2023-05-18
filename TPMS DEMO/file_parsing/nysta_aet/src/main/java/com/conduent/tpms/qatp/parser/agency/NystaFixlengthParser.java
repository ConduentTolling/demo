package com.conduent.tpms.qatp.parser.agency;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

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
import com.conduent.tpms.qatp.dao.TQatpMappingDao;
import com.conduent.tpms.qatp.dao.impl.InsertNystaStatDaoImpl;
import com.conduent.tpms.qatp.dto.AckFileInfoDto;
import com.conduent.tpms.qatp.dto.AckFileWrapper;
import com.conduent.tpms.qatp.dto.FileNameDetailVO;
import com.conduent.tpms.qatp.dto.MappingInfoDto;
import com.conduent.tpms.qatp.dto.NystaAetTxDto;
import com.conduent.tpms.qatp.dto.NystaHeaderInfoVO;
import com.conduent.tpms.qatp.dto.TollCalculationDto;
import com.conduent.tpms.qatp.dto.TollCalculationResponseDto;
import com.conduent.tpms.qatp.exception.CustomException;
import com.conduent.tpms.qatp.exception.EmptyLineException;
import com.conduent.tpms.qatp.exception.InvalidDetailException;
import com.conduent.tpms.qatp.exception.InvalidFileHeaderException;
import com.conduent.tpms.qatp.exception.InvalidFileNameException;
import com.conduent.tpms.qatp.exception.InvalidFileStructureException;
import com.conduent.tpms.qatp.exception.InvalidRecordCountException;
import com.conduent.tpms.qatp.exception.InvalidTrailerException;
import com.conduent.tpms.qatp.model.AtpFileIdObject;
import com.conduent.tpms.qatp.model.ConfigVariable;
import com.conduent.tpms.qatp.model.QatpStatistics;
import com.conduent.tpms.qatp.model.ReconciliationCheckPoint;
import com.conduent.tpms.qatp.model.TranDetail;
import com.conduent.tpms.qatp.model.Transaction;
import com.conduent.tpms.qatp.parser.FileParserImpl;
import com.conduent.tpms.qatp.service.ITransDetailService;
import com.conduent.tpms.qatp.utility.AsyncProcessCall;
import com.conduent.tpms.qatp.utility.Convertor;
import com.conduent.tpms.qatp.utility.GenericValidation;
import com.conduent.tpms.qatp.utility.MasterDataCache;
import com.google.common.base.Stopwatch;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

@Component
public class NystaFixlengthParser extends FileParserImpl 
{

	private static final Logger logger = LoggerFactory.getLogger(NystaFixlengthParser.class);
	
	private List<NystaAetTxDto> detailVOList;
	private NystaHeaderInfoVO headerVO;
	NystaAetTxDto detailVO;
	
	public NystaAetTxDto getDetailVO() {
		return detailVO;
	}

	public void setDetailVO(NystaAetTxDto detailVO) {
		this.detailVO = detailVO;
	}
	
	TollCalculationResponseDto tollAmount;

	@Autowired
	TimeZoneConv timeZoneConv;
	
	@Autowired
	AsyncProcessCall asycnprocesscall;
	
	@Autowired
	ConfigVariable configVariable;

	@Autowired
	private RestTemplate restTemplate;

	public NystaFixlengthParser(TQatpMappingDao tQatpMappingDao,IQatpDao qatpDao,ITransDetailService transDetailService,GenericValidation genericValidation,
			MasterDataCache masterDataCache,InsertNystaStatDaoImpl insertNystaStatDaoImpl,TimeZoneConv timeZoneConv,AsyncProcessCall asyncProcessCall)
	{
		this.tQatpMappingDao=tQatpMappingDao;
		this.qatpDao=qatpDao;
		this.transDetailService=transDetailService;
		this.genericValidation=genericValidation;
		this.masterDataCache=masterDataCache;
		this.insertNystaStatDaoImpl=insertNystaStatDaoImpl;
		this.timeZoneConv=timeZoneConv;
		this.asyncProcessCall=asyncProcessCall;
		//this.textFileReader=textFileReader;
		initialize();
	}
	
	@Override
	public void initialize() 
	{
		logger.info("Initializing process...");
		setFileFormat(QATPConstants.MTX);
		setFileType(QATPConstants.FIXED_LENGTH);
		setAgencyId(QATPConstants.HOME_AGENCY_ID);
		setIsHederPresentInFile(true);
		setIsDetailsPresentInFile(true);
		setIsTrailerPresentInFile(true);
		getFileParserParameter().setAgencyId("001");
		getFileParserParameter().setFileType(QATPConstants.MTX);
		fileNameVO = new FileNameDetailVO();
		detailVOList = new ArrayList<NystaAetTxDto>();
		headerVO = new NystaHeaderInfoVO();
	}


	public void processEndOfLine(String fileSection) 
	{
		if(fileSection.equals("DETAIL"))
		{
			detailVOList.add(detailVO);
		}
	}

	public void processStartOfLine(String fileSection) 
	{

		if(fileSection.equals("DETAIL"))
		{
			detailVO=new NystaAetTxDto();
		}
	}

	public void doFieldMapping(String value, MappingInfoDto fMapping)
	{

		switch (fMapping.getFieldName()) 
		{
		
		case "F_FILE_DATE":
			fileNameVO.setFileDate(value);
			break;
		case "RECORD_COUNT":
			headerVO.setRecordCount(value);
			break;
		case "H_DATE":
			headerVO.setFileDate(value);
			break;

		case "D_RECORD_TYPE_CODE":
			detailVO.setRecordTypeCode(value);
			break;
		case  "D_ETC_TRX_TYPE" :
			detailVO.setEtcTrxType(value);
			break;
		case  "D_ETC_ENTRY_DATE" :
			detailVO.setEtcEntryDate(value);
			break;
		case  "D_ETC_ENTRY_TIME" :
			detailVO.setEtcEntryTime(value);
			break;
		case  "D_ETC_ENTRY_PLAZA_ID" :
			detailVO.setEtcEntryPlazaId(value);
			break;
		case  "D_ETC_ENTRY_LANE_ID" :
			detailVO.setEtcEntryLaneId(value);
			break;
		case  "D_ETC_ENTRY_DATA_SOURCE" :
			detailVO.setEtcEntryDataSource(value);
			break;
		case  "D_ETC_APP_ID" :
			detailVO.setEtcAppId(value);
			break;
		case  "D_ETC_TYPE" :
			detailVO.setEtcType(value);
			break;
		case  "D_ETC_GROUP_ID" :
			detailVO.setEtcGroupId(value);
			break;
		case  "D_ETC_AGENCY_ID" :
			detailVO.setEtcAgencyId(value);
			break;
		case  "D_ETC_TAG_SERIAL_NUM" :
			detailVO.setEtcTagSerialNum(value);
			break;
		case  "D_ETC_SERIAL_NUMBER" :
			detailVO.setEtcSerialNumber(value);
			break;
		case  "D_ETC_READ_PERFORMANCE" :
			detailVO.setEtcReadPerformance(value);
			break;
		case  "D_LANE_MODE" :
			detailVO.setEtcLaneMode(value);
			break;
		case  "D_ETC_COLLECTOR_ID" :
			detailVO.setEtcCollectorId(value);
			break;
		case  "D_ETC_DERIVED_VEH_CLASS" :
			detailVO.setEtcDerivedVehClass(value);
			break;
		case  "D_ETC_READ_VEH_TYPE" :
			detailVO.setEtcReadVehType(value);
			break;
		case  "D_ETC_READ_VEH_AXLES" :
			detailVO.setEtcReadVehAxles(value);
			break;
		case  "D_ETC_READ_VEH_WEIGHT" :
			detailVO.setEtcReadVehWeight(value);
			break;
		case  "D_ETC_READ_REAR_TIRES" :
			detailVO.setEtcReadRearTires(value);
			break;
		case  "D_ETC_WRITE_VEH_TYPE" :
			detailVO.setEtcWriteVehType(value);
			break;
		case  "D_ETC_WRITE_VEH_AXLES" :
			detailVO.setEtcWriteVehAxles(value);
			break;
		case  "D_ETC_WRITE_VEH_WEIGHT" :
			detailVO.setEtcWriteVehWeight(value);
			break;
		case  "D_ETC_WRITE_REAR_TIRES" :
			detailVO.setEtcWriteRearTires(value);
			break;
		case  "D_ETC_IND_VEH_CLASS" :
			detailVO.setEtcIndVehClass(value);
			break;
		case  "D_ETC_VALIDATION_STATUS" :
			detailVO.setEtcValidationStatus(value);
			break;
		case  "D_ETC_VIOL_OBSERVED" :
			detailVO.setEtcViolObserved(value);
			break;
		case  "D_ETC_IMAGE_CAPTURED" :
			detailVO.setEtcImageCaptured(value);
			break;
		case  "D_ETC_REVENUE_TYPE" :
			detailVO.setEtcRevenueType(value);
			break;
		case  "D_ETC_READ_AGENCY_DATA" :
			detailVO.setEtcReadAgencyData(value);
			break;
		case  "D_ETC_WRITE_AGENCY_DATA" :
			detailVO.setEtcWritAgencyData(value);
			break;
		case  "D_ETC_PRE_WRITE_TRX_NUM" :
			detailVO.setEtcPreWritTrxNum(value);
			break;
		case  "D_ETC_POST_WRITE_TRX_NUM" :
			detailVO.setEtcPostWritTrxNum(value);
			break;
		case  "D_ETC_IND_VEH_AXLES" :
			detailVO.setEtcIndVehAxles(value);
			break;
		case  "D_ETC_IND_AXLE_OFFSET" :
			detailVO.setEtcIndAxleOffset(value);
			break;
		case  "D_ETC_VEH_SPEED" :
			detailVO.setEtcVehSpeed(value);
			break;
		case  "D_ETC_EXIT_DATE" :
			detailVO.setEtcExitDate(value);
			break;
		case  "D_ETC_EXIT_TIME" :
			detailVO.setEtcExitTime(value);
			break;
		case  "D_ETC_EXIT_PLAZA_ID" :
			detailVO.setEtcExitPlazaId(value);
			break;
		case  "D_ETC_EXIT_LANE_ID" :
			detailVO.setEtcExitLaneId(value);
			break;
		case  "D_ETC_BUFFERED_FLAG" :
			detailVO.setEtcBufferedFeed(value);
			break;
		case  "D_ETC_TXN_SRL_NUM" :
			detailVO.setEtcTxnSrlNum(value);
			break;
		case  "D_FROMT_OCR_PLATE_COUNTRY" :
			detailVO.setFrontOcrPlateCountry(value);
			break;
		case  "D_FRONT_OCR_PLATE_STATE" :
			detailVO.setFrontOcrPlateState(value);
			break;
		case  "D_FRONT_OCR_PLATE_TYPE" :
			detailVO.setFrontOcrPlateType(value);
			break;
		case  "D_FRONT_OCR_PLATE_NUMBER" :
			detailVO.setFrontOcrPlateNumber(value);
			break;
		case  "D_FRONT_OCR_CONFIDENCE" :
			detailVO.setFrontOcrConfidence(value);
			break;
		case  "D_FRONT_OCR_IMAGE_INDEX" :
			detailVO.setFrontOcrImageIndex(value);
			break;
		case  "D_FRONT_IMGAE_COLOR" :
			detailVO.setFrontImageColor(value);
			break;
		case  "D_REAR_OCR_PLATE_COUNTRY" :
			detailVO.setRearOcrPlateCountry(value);
			break;
		case  "D_REAR_OCR_PLATE_STATE" :
			detailVO.setRearOcrPlateState(value);
			break;
		case  "D_REAR_OCR_PLATE_TYPE" :
			detailVO.setRearOcrPlateType(value);
			break;
		case  "D_REAR_OCR_PLATE_NUMBER" :
			detailVO.setRearOcrPlateNumber(value);
			break;
		case  "D_REAR_OCR_CONFIDENCE" :
			detailVO.setRearOcrConfidence(value);
			break;
		case  "D_REAR_OCR_IMGAE_INDEX" :
			detailVO.setRearOcrImageIndex(value);
			break;
		case  "D_REAR_IMAGE_COLOR" :
			detailVO.setRearImageColor(value);
			break;
		case "D_ETC_CLASS_MISMATCH":
			detailVO.setEtcClassMismatch(value);
			break;
		case  "D_ETC_RECORD_TERM" :
			detailVO.setEtcRecordTerm(value);
			break;
		default:
		}
	}
	@Override
	public void finishFileProcess() throws CustomException, InterruptedException 
	{
		LocalDateTime from = null;
		from = LocalDateTime.now();
		for(NystaAetTxDto obj : detailVOList)
		{
			logger.info(obj.toString());
			Transaction t= obj.getTransaction(masterDataCache,headerVO,xferControl,agencyIdNystaOrNysba);
			logger.info(t.toString());
			try
			{
				transDetailService.saveTransDetail(t.getTranDetail(timeZoneConv,masterDataCache));
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
		}
		logger.debug("Time Taken for finish file process  : {} - Completed in {} ms", ChronoUnit.MILLIS.between(from, LocalDateTime.now()));

	}
	
	@Override
	public void saveRecords(ReconciliationCheckPoint reconciliationCheckPoint) throws InterruptedException 
	{
		//TollCalculationResponseDto tollAmount = null;
		
		Stopwatch stopwatch = Stopwatch.createStarted();
		
		if (detailVOList!=null && !detailVOList.isEmpty() && detailVOList.size() > 0) 
		{
			List<TranDetail> list = new ArrayList<TranDetail>();
			Transaction trxn=null;
			TranDetail tranDetail=null;
			for(NystaAetTxDto obj : detailVOList)
			{
				logger.debug("NystaAetTxDTO {}",obj);
				trxn=obj.getTransaction(masterDataCache, headerVO, xferControl,agencyIdNystaOrNysba);
				
				tranDetail=trxn.getTranDetail(timeZoneConv,masterDataCache);
				logger.debug("TranDetail {}",tranDetail);
				
				//tollAmount = calculateTollAmount(tranDetail);
				//setAmounts(tollAmount,tranDetail);
				setAmounts(tranDetail);
				
				list.add(tranDetail);
				
			}
			List<Long> txSeqlist = transDetailService.loadNextSeq(list.size());
			setLaneTxId(list,txSeqlist,list.size());
			
			transDetailService.batchInsert(list);
			logger.debug("{} record inserted in T_TRAN_TABLE",list.size());
			
			stopwatch.stop();
			long millis = stopwatch.elapsed(TimeUnit.MILLISECONDS);
			logger.info("Time taken to {} record inserted in T_TRAN_TABLE {}",list.size(), millis);
			
			TranDetail lastRecord=list.get(list.size()-1);
			reconciliationCheckPoint.setLastLaneTxTd(lastRecord.getLaneTxId());
			reconciliationCheckPoint.setLastTxTimeStatmp(lastRecord.getTxTimeStamp().toLocalDateTime());
			reconciliationCheckPoint.setLastExternLaneId(lastRecord.getExternLaneId());
			reconciliationCheckPoint.setLastExternPlazaId(lastRecord.getExternPlazaId());
			reconciliationCheckPoint.setXferControlId(xferControl.getXferControlId());
			
			//collect atpFileID
			List<AtpFileIdObject> atpFileIdList = transDetailService.getAtpFileId(xferControl.getXferControlId());
			logger.info("Atp File Id {} ",atpFileIdList);
			
			try
			{
				transDetailService.publishMessages(list,atpFileIdList,agencyIdNystaOrNysba);
				transDetailService.pushToOCR(list);
			}
			catch(Exception ex)
			{
				logger.info("Exception {}",ex.getMessage());
			}
			
			detailVOList.clear();
		}
	}
	

	public void setAmounts(TranDetail tranDetail) {

//		if(tollAmount!=null)
//		{	
//			//tranDetail.setEtcFareAmount(tollAmount.getDiscountFare()); already commented //PB told to set it by default  //change to full fare
//			tranDetail.setEtcFareAmount(tollAmount.getFullFare());
//			tranDetail.setCashFareAmount(tollAmount.getFullFare());			//PB told to set it by default
//			
//			if (tranDetail.getAetFlag()!=null && tranDetail.getAetFlag().equals("Y"))	//aet flag = Y calculate ITOL else VIDEO FARE
//			{
//				tranDetail.setItolFareAmount(tollAmount.getFullFare());
//			}
//			else 
//			{
//				tranDetail.setVideoFareAmount(tollAmount.getFullFare());
//			}						
//		}
//		else
//		{
//			tranDetail.setEtcFareAmount(0d);
//			tranDetail.setVideoFareAmount(0d);
//			tranDetail.setCashFareAmount(0d);
//			tranDetail.setItolFareAmount(0d);
//			tranDetail.setExpectedRevenueAmount(0d);
//			tranDetail.setPostedFareAmount(0d);
//		}
		
		// New Toll Calculation Logic
		
		tranDetail.setEtcFareAmount(0d);
		tranDetail.setVideoFareAmount(0d);
		tranDetail.setCashFareAmount(0d);
		tranDetail.setItolFareAmount(0d);
		tranDetail.setExpectedRevenueAmount(0d);
		tranDetail.setPostedFareAmount(0d);
		
		TollCalculationResponseDto rs1 = calculateTollAmount(tranDetail, 1, 1);	//etcFare Amount
		if(rs1 != null) 
		{
			Double fullFare = rs1.getFullFare() != null ? rs1.getFullFare() : 0d;
			Double extraAxleChargeCash = rs1.getExtraAxleChargeCash() != null ? rs1.getExtraAxleChargeCash() : 0d;
			Integer extraAxles = tranDetail.getExtraAxles() != null ? tranDetail.getExtraAxles() : 0;
			
			Double etcFareAmount = fullFare + (extraAxles * extraAxleChargeCash);
			tranDetail.setEtcFareAmount(etcFareAmount);
		}		
				
		TollCalculationResponseDto rs2 = calculateTollAmount(tranDetail, 60, 217); //video Fare Amount // bug 16545 changes
		if(rs2 != null) 
		{
			Double fullFare = rs2.getFullFare() != null ? rs2.getFullFare() : 0d;
			Double extraAxleChargeCash = rs2.getExtraAxleChargeCash() != null ? rs2.getExtraAxleChargeCash() : 0d;
			Integer extraAxles = tranDetail.getExtraAxles() != null ? tranDetail.getExtraAxles() : 0;
			
			Double videoFareAmount = fullFare + (extraAxles * extraAxleChargeCash);
			tranDetail.setVideoFareAmount(videoFareAmount);
		}		
		
		TollCalculationResponseDto rs3 = calculateTollAmount(tranDetail, 3, 1); //cashFareAmount
		if(rs3 != null) 
		{
			Double fullFare = rs3.getFullFare() != null ? rs3.getFullFare() : 0d;
			Double extraAxleChargeCash = rs3.getExtraAxleChargeCash() != null ? rs3.getExtraAxleChargeCash() : 0d;
			Integer extraAxles = tranDetail.getExtraAxles() != null ? tranDetail.getExtraAxles() : 0;
			
			Double cashFareAmount = fullFare + (extraAxles * extraAxleChargeCash);
			tranDetail.setCashFareAmount(cashFareAmount);
		}		
		
		TollCalculationResponseDto rs4 = calculateTollAmount(tranDetail, 60, 1); //itolFareAmount
		if(rs4 != null) 
		{
			Double fullFare = rs4.getFullFare() != null ? rs4.getFullFare() : 0d;
			Double extraAxleChargeCash = rs4.getExtraAxleChargeCash() != null ? rs4.getExtraAxleChargeCash() : 0d;
			Integer extraAxles = tranDetail.getExtraAxles() != null ? tranDetail.getExtraAxles() : 0;
			
			Double itolFareAmount = fullFare + (extraAxles * extraAxleChargeCash);
			tranDetail.setItolFareAmount(itolFareAmount);
		}			
			
		if (tranDetail.getTollRevenueType() == 1) // if TOLL_REVENUE_TYPE is ETC
		{
			tranDetail.setExpectedRevenueAmount(tranDetail.getEtcFareAmount()); //expectedRevenueAmount as etcFareAmount			
		}
		else if(tranDetail.getTollRevenueType() == 9 || tranDetail.getTollRevenueType() == 60) // if TOLL_REVENUE_TYPE is VIDEO
		{
			tranDetail.setExpectedRevenueAmount(tranDetail.getVideoFareAmount()); //expectedRevenueAmount as videoFareAmount
		}
		tranDetail.setPostedFareAmount(tranDetail.getExpectedRevenueAmount());
		
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
		try {
			HttpEntity<String> entity = new HttpEntity<String>(gson.toJson(tollCalculationDto), headers);

			ResponseEntity<String> result = restTemplate.postForEntity(configVariable.getTollCalculationUri(), entity,String.class);
			logger.info("Toll Calculation result: {}", result);
			if (result.getStatusCodeValue() == 200) {

				JsonObject jsonObject = new Gson().fromJson(result.getBody(), JsonObject.class);
				//logger.info("Got tollcalculation response for device : {}", jsonObject);

				return gson.fromJson(jsonObject.getAsJsonObject("amounts"), TollCalculationResponseDto.class);

			} else {
				JsonObject jsonObject = new Gson().fromJson(result.getBody(), JsonObject.class);
				logger.info("Got toll calculation response: {}", jsonObject.toString());
				return null;
			}

		} catch (Exception e) {
			logger.info("Error: Exception {} in Toll Calculation API for Device",e.getMessage());
			return null;
		}
		
	}

	private void setLaneTxId(List<TranDetail> tempTxlist, List<Long> txSeqlist, Integer sizeOftxRec) 
	{
		for (int i = 0; i < sizeOftxRec; i++) {
			tempTxlist.get(i).setLaneTxId(txSeqlist.get(i));
		}
	}
	
	@Override
	public AckFileWrapper prePareAckFileMetaData(AckStatusCode ackStatusCode,File file)
	{
		AckFileWrapper ackObj=new AckFileWrapper();
		LocalDateTime date=LocalDateTime.now();

		StringBuilder sbFileName = new StringBuilder();
		sbFileName.append(file.getName().split("\\.")[0]).append(QATPConstants.ACK_EXT);

		StringBuilder sbFileContent = new StringBuilder();
		if(ackStatusCode.getCode().equals("07") || ackStatusCode.getCode().equals("08"))
		{
			headerVO.setRecordCount(QATPConstants.DEFAULT_COUNT);
		}

		sbFileContent.append(StringUtils.rightPad(file.getName(), 40, " ")).append(StringUtils.leftPad(headerVO.getRecordCount(),8,"0"))
		.append(genericValidation.getTodayTimestamp(date)).append(ackStatusCode.getCode());

		ackObj.setAckFileName(sbFileName.toString());
		ackObj.setSbFileContent(sbFileContent.toString());
		ackObj.setFile(file);
		if(ackStatusCode.equals(AckStatusCode.SUCCESS))
		{
			ackObj.setFileDestDir(getConfigurationMapping().getFileInfoDto().getProcDir());
		}
		else
		{
			ackObj.setFileDestDir(getConfigurationMapping().getFileInfoDto().getUnProcDir());
		}
		    File lock = new File(getConfigurationMapping().getFileInfoDto().getSrcDir()+ "\\"+ file.getName()+ ".lock");
	        ackObj.setLockFile(lock);

		/*Prepare AckStatus table*/
		AckFileInfoDto ackFileInfoDto=new AckFileInfoDto();
		ackFileInfoDto.setAckFileDate(LocalDate.now());
		String fileTime=genericValidation.getTodayTimestamp(date, "hhmmss");
		ackFileInfoDto.setAckFileTime(fileTime);
		ackFileInfoDto.setFileType(getFileFormat());
		ackFileInfoDto.setAckFileName(sbFileName.toString());
		ackFileInfoDto.setAckFileStatus(ackStatusCode.getCode());
		ackFileInfoDto.setFileType(getFileFormat());
		ackFileInfoDto.setTrxFileName(file.getName());
		//ackFileInfoDto.setFromAgency(getAgencyId());
		ackFileInfoDto.setFromAgency(QATPConstants.NYSTA_FROM_AGENCY);  
		ackFileInfoDto.setToAgency(QATPConstants.NYSTA_DEVICE_PREFIX);
		ackFileInfoDto.setExternFileId(xferControl.getXferControlId());
		ackObj.setAckFileInfoDto(ackFileInfoDto);
		return ackObj;
	}
	
	@Override
	public AckFileWrapper prePareAckFileMetaDataForFileAlreadyProcessed(AckStatusCode ackStatusCode,File file)
	{
		AckFileWrapper ackObj=new AckFileWrapper();
		LocalDateTime date=LocalDateTime.now();

		StringBuilder sbFileName = new StringBuilder();
		sbFileName.append(file.getName().split("\\.")[0]).append(QATPConstants.ACK_EXT);

		StringBuilder sbFileContent = new StringBuilder();

		sbFileContent.append(StringUtils.rightPad(file.getName(), 40, " ")).append(StringUtils.leftPad(headerVO.getRecordCount(),8,"0"))
		.append(genericValidation.getTodayTimestamp(date)).append(ackStatusCode.getCode());

		ackObj.setAckFileName(sbFileName.toString());
		ackObj.setSbFileContent(sbFileContent.toString());
		ackObj.setFile(file);
		if(ackStatusCode.equals(AckStatusCode.SUCCESS))
		{
			ackObj.setFileDestDir(getConfigurationMapping().getFileInfoDto().getProcDir());
		}
		else
		{
			ackObj.setFileDestDir(getConfigurationMapping().getFileInfoDto().getUnProcDir());
		}
		    File lock = new File(getConfigurationMapping().getFileInfoDto().getSrcDir()+ "\\"+ file.getName()+ ".lock");
	        ackObj.setLockFile(lock);

		/*Prepare AckStatus table*/
		AckFileInfoDto ackFileInfoDto=new AckFileInfoDto();
		ackFileInfoDto.setAckFileDate(LocalDate.now());
		String fileTime=genericValidation.getTodayTimestamp(date, "hhmmss");
		ackFileInfoDto.setAckFileTime(fileTime);
		ackFileInfoDto.setFileType(getFileFormat());
		ackFileInfoDto.setAckFileName(sbFileName.toString());
		ackFileInfoDto.setAckFileStatus(ackStatusCode.getCode());
		ackFileInfoDto.setFileType(getFileFormat());
		ackFileInfoDto.setTrxFileName(file.getName());
		//ackFileInfoDto.setFromAgency(getAgencyId());
		ackFileInfoDto.setFromAgency(QATPConstants.NYSTA_FROM_AGENCY);
		ackFileInfoDto.setToAgency(QATPConstants.NYSTA_DEVICE_PREFIX);
		ackFileInfoDto.setExternFileId(reconciliationCheckPoint.getXferControlId());
		ackObj.setAckFileInfoDto(ackFileInfoDto);
		return ackObj;
	}

	@Override
	public void validateRecordCount() throws InvalidRecordCountException
	{
		if((recordCount)!= Convertor.toLong(headerVO.getRecordCount()))
		{
			throw new InvalidRecordCountException("Invalid header record count"); 
		}
	}
	
	@Override
	public void processedPartiallyExecutedFile(File file,String externFileId) throws IOException, InvalidFileNameException, InvalidFileHeaderException, EmptyLineException, InvalidDetailException, InvalidTrailerException, InvalidFileStructureException, InterruptedException
	{
		logger.info("Started with partially executing of file {}",file.getName());
		boolean endOfFile = false;
		LocalDateTime from = null;
		
		BufferedReader reader = getFileReader(file);
		String previousLine = reader.readLine();
		String currentLine = reader.readLine();
		
		
		for(;;currentLine=reader.readLine())
		{
			if(currentLine.substring(135,155).matches(externFileId))
			{
				String nextRecord = reader.readLine();
				for(;;nextRecord=reader.readLine())
				{
					if(!nextRecord.equals(Constants.E))
					{
						from = LocalDateTime.now();
						isFirstRead=false;
						parseAndValidateDetails(nextRecord);
						
						if(detailVOList.size() >0)
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

}