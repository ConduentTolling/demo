package com.conduent.tpms.qatp.parser.agency;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.conduent.app.timezone.utility.TimeZoneConv;
import com.conduent.tpms.qatp.constants.AckStatusCode;
import com.conduent.tpms.qatp.constants.QATPConstants;
import com.conduent.tpms.qatp.dto.AckFileInfoDto;
import com.conduent.tpms.qatp.dto.AckFileWrapper;
import com.conduent.tpms.qatp.dto.FileNameDetailVO;
import com.conduent.tpms.qatp.dto.MappingInfoDto;
import com.conduent.tpms.qatp.dto.NystaAetTxDto;
import com.conduent.tpms.qatp.dto.NystaHeaderInfoVO;
import com.conduent.tpms.qatp.exception.CustomException;
import com.conduent.tpms.qatp.exception.InvalidRecordCountException;
import com.conduent.tpms.qatp.model.ReconciliationCheckPoint;
import com.conduent.tpms.qatp.model.TranDetail;
import com.conduent.tpms.qatp.model.Transaction;
import com.conduent.tpms.qatp.parser.FileParserImpl;
import com.conduent.tpms.qatp.utility.Convertor;
import com.conduent.tpms.qatp.utility.LocalDateAdapter;
import com.conduent.tpms.qatp.utility.LocalDateTimeAdapter;
import com.conduent.tpms.qatp.utility.OffsetDateTimeConverter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Component
public class NystaFixlengthParser extends FileParserImpl 
{

	private static final Logger logger = LoggerFactory.getLogger(NystaFixlengthParser.class);
	
	private List<NystaAetTxDto> detailVOList;
	private NystaHeaderInfoVO headerVO;
	NystaAetTxDto detailVO;

	@Autowired
	TimeZoneConv timeZoneConv;
	
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
		getFileParserParameter().setAgencyId("007");
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
		case  "D_ETC_LANE_MODE" :
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
		case  "D_ETC_RECORD_TERM" :
			detailVO.setEtcRecordTerm(value);
		default:
		}
	}
	@Override
	public void finishFileProcess() throws CustomException 
	{
		for(NystaAetTxDto obj : detailVOList)
		{
			logger.info(obj.toString());
			Transaction t= obj.getTransaction(masterDataCache,headerVO,xferControl);
			logger.info(t.toString());
			try
			{
				transDetailService.saveTransDetail(t.getTranDetail(timeZoneConv));
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
		}

	}

	@Override
	public void saveRecords(ReconciliationCheckPoint reconciliationCheckPoint) 
	{
		if (detailVOList!=null && !detailVOList.isEmpty() && detailVOList.size() > 0) 
		{
			List<TranDetail> list = new ArrayList<TranDetail>();
			Transaction trxn=null;
			TranDetail tranDetail=null;
			for(NystaAetTxDto obj : detailVOList)
			{
				logger.info("NystaAetTxDTO {}",obj);
				trxn=obj.getTransaction(masterDataCache, headerVO, xferControl);
				logger.info("Transaction {}",trxn);
				tranDetail=trxn.getTranDetail(timeZoneConv);
				logger.info("TranDetail {}",tranDetail);
				
				logger.info("Record to be inserted in list wit etc_account_id : {}",tranDetail.getEtcAccountId());
				list.add(tranDetail);
				count++;
				logger.info("List with records inserted {} and count {} ",list.size(),count);
			}
			count = 0;
			List<Long> txSeqlist = transDetailService.loadNextSeq(list.size());
			setLaneTxId(list,txSeqlist,list.size());
			transDetailService.batchInsert(list);
			logger.info("{} record inserted in T_TRAN_TABLE",list.size());
			TranDetail lastRecord=list.get(list.size()-1);
			reconciliationCheckPoint.setLastLaneTxTd(lastRecord.getLaneTxId());
			reconciliationCheckPoint.setLastTxTimeStatmp(lastRecord.getTxTimeStamp().toLocalDateTime());
			reconciliationCheckPoint.setLastExternLaneId(lastRecord.getExternLaneId());
			reconciliationCheckPoint.setLastExternPlazaId(lastRecord.getExternPlazaId());
			
			/**Push messages to OSS**/
			/*
			 * Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new
			 * LocalDateAdapter()) .registerTypeAdapter(ZonedDateTime.class, new
			 * ZonedDateTimeConverter(DateTimeFormatter.ISO_ZONED_DATE_TIME))
			 * .excludeFieldsWithoutExposeAnnotation().create();
			 */
			
			Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
					.registerTypeAdapter(OffsetDateTime.class, new OffsetDateTimeConverter(DateTimeFormatter.ISO_OFFSET_DATE_TIME))
					.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
					.registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
					.excludeFieldsWithoutExposeAnnotation().create();
			
			try
			{
				transDetailService.publishMessages(list, gson);
				transDetailService.pushToOCR(list,gson);
			}
			catch(Exception ex)
			{
				logger.info("Exception {}",ex.getMessage());
			}
			
			detailVOList.clear();
		}
	}
	private void setLaneTxId(List<TranDetail> tempTxlist, List<Long> txSeqlist, Integer sizeOftxRec) {
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
	public void validateRecordCount() throws InvalidRecordCountException
	{
		if((recordCount)!= Convertor.toLong(headerVO.getRecordCount()))
		{
			throw new InvalidRecordCountException("Invalid header record count"); 
		}
	}

}