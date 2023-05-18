package com.conduent.tpms.qatp.parser.agency;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.conduent.app.timezone.utility.TimeZoneConv;
import com.conduent.tpms.qatp.constants.AckStatusCode;
import com.conduent.tpms.qatp.constants.FOConstants;
import com.conduent.tpms.qatp.constants.INTXConstants;
import com.conduent.tpms.qatp.constants.QATPConstants;
import com.conduent.tpms.qatp.dao.IQatpDao;
import com.conduent.tpms.qatp.dao.TQatpMappingDao;
import com.conduent.tpms.qatp.dao.impl.InsertFOTrnxStatDaoImpl;
import com.conduent.tpms.qatp.dto.AckFileInfoDto;
import com.conduent.tpms.qatp.dto.AckFileWrapper;
import com.conduent.tpms.qatp.dto.FOTrnxDTO;
import com.conduent.tpms.qatp.dto.FOTrnxHeaderInfoVO;
import com.conduent.tpms.qatp.dto.FileNameDetailVO;
import com.conduent.tpms.qatp.dto.MappingInfoDto;
import com.conduent.tpms.qatp.dto.NystaHeaderInfoVO;
import com.conduent.tpms.qatp.exception.FNTXFileNumDuplicationException;
import com.conduent.tpms.qatp.exception.CustomException;
import com.conduent.tpms.qatp.exception.InvalidFileHeaderException;
import com.conduent.tpms.qatp.exception.InvalidFileNameException;
import com.conduent.tpms.qatp.exception.InvalidRecordCountException;
import com.conduent.tpms.qatp.exception.InvalidRecordException;
import com.conduent.tpms.qatp.model.ReconciliationCheckPoint;
import com.conduent.tpms.qatp.model.TranDetail;
import com.conduent.tpms.qatp.model.Transaction;
import com.conduent.tpms.qatp.parser.FileParserImpl;
import com.conduent.tpms.qatp.service.ITransDetailService;
import com.conduent.tpms.qatp.utility.AsyncProcessCall;
import com.conduent.tpms.qatp.utility.Convertor;
import com.conduent.tpms.qatp.utility.GenericValidation;
import com.conduent.tpms.qatp.utility.MasterDataCache;
import com.conduent.tpms.qatp.validation.TextFileReader;

@Component
public class FOTrnxFixlengthParser extends FileParserImpl 
{

	private static final Logger logger = LoggerFactory.getLogger(FOTrnxFixlengthParser.class);
	
//	private List<NystaAetTxDto> detailVOList;
//	private NystaHeaderInfoVO headerVO;
//	NystaAetTxDto detailVO;
//	
//	public NystaAetTxDto getDetailVO() {
//		return detailVO;
//	}
//
//	public void setDetailVO(NystaAetTxDto detailVO) {
//		this.detailVO = detailVO;
//	}
	
	private List<FOTrnxDTO> detailVOList;
	private FOTrnxHeaderInfoVO headerVO;
	FOTrnxDTO detailVO;
	private boolean check = true;
	double totalAmount = 0.0;
	int counter;
	
	public FOTrnxDTO getDetailVO() {
		return detailVO;
	}

	public void setDetailVO(FOTrnxDTO detailVO) {
		this.detailVO = detailVO;
	}


	@Autowired
	TimeZoneConv timeZoneConv;
	
	@Autowired
	AsyncProcessCall asycnprocesscall;

	
	public FOTrnxFixlengthParser(TQatpMappingDao tQatpMappingDao,IQatpDao qatpDao,ITransDetailService transDetailService,GenericValidation genericValidation,
			MasterDataCache masterDataCache,InsertFOTrnxStatDaoImpl insertNystaStatDaoImpl,TimeZoneConv timeZoneConv,AsyncProcessCall asyncProcessCall)
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
//		setFileFormat(QATPConstants.MTX);
//		setFileType(QATPConstants.FIXED_LENGTH);
//		setAgencyId(QATPConstants.HOME_AGENCY_ID);
//		setIsHederPresentInFile(true);
//		setIsDetailsPresentInFile(true);
//		setIsTrailerPresentInFile(true);
		//getFileParserParameter().setAgencyId("007");
		//getFileParserParameter().setFileType(QATPConstants.MTX);
//		fileNameVO = new FileNameDetailVO();
//		detailVOList = new ArrayList<NystaAetTxDto>();
//		headerVO = new NystaHeaderInfoVO();
		
		//FOTRNX
		setFileFormat(QATPConstants.FNTX);
		setFileType(QATPConstants.FIXED_LENGTH);
		setAgencyId(QATPConstants.FO_AGENCY_ID);
		setIsHederPresentInFile(true);
		setIsDetailsPresentInFile(true);
		setIsTrailerPresentInFile(false);
		getFileParserParameter().setAgencyId("002");
		getFileParserParameter().setFileType(QATPConstants.FNTX);
		fileNameVO = new FileNameDetailVO();
		detailVOList = new ArrayList<FOTrnxDTO>();
		headerVO = new FOTrnxHeaderInfoVO();
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
			detailVO=new FOTrnxDTO();
		}
	}

	public void doFieldMapping(String value, MappingInfoDto fMapping,long recordCount) throws InvalidRecordException, InvalidFileHeaderException, FNTXFileNumDuplicationException, InvalidFileNameException
	{
		check = true;
		switch (fMapping.getFieldName()) 
		{
		case FOConstants.F_FROM_AGENCY_ID:
			fileNameVO.setFromAgencyId(value);
			break;
		case FOConstants.F_TO_AGENCY_ID:
			fileNameVO.setToAgencyId(value);
			break;
		case FOConstants.F_FILE_DATE_TIME:
			fileNameVO.setFileDateTime(genericValidation.getFormattedDateTime(value));
			break;
		case FOConstants.F_EXTENSION:
			fileNameVO.setFileType(value.replace(".", ""));
			break;
		case FOConstants.H_FILE_TYPE:
			if(value.equals(fMapping.getValidationValue()))
			{
				headerVO.setFileType(value);
			}
			else
			{
				throw new InvalidFileHeaderException("File Type Mismatch");
			}
			
			break;
		case FOConstants.H_FROM_AGENCY_ID:
			if (validateAgencyMatch(value, fileNameVO.getFromAgencyId())) {
				headerVO.setFromAgencyId(value);
			} else {
				throw new InvalidFileHeaderException("H_FROM_AGENCY_ID mismatch..");
			}
			break;
		case FOConstants.H_TO_AGENCY_ID:
			if (validateAgencyMatch(value, fileNameVO.getToAgencyId())) {
				headerVO.setToAgencyId(value);
			} else {
				throw new InvalidFileHeaderException("H_TO_AGENCY_ID mismatch..");
			}
			break;
		case FOConstants.H_FILE_DATE:
			LocalDate date1 = genericValidation.getFormattedDate(value, fMapping.getFixeddValidValue());
			if(customDateValidation(date1)) {
			headerVO.setFileDate(date1);
			}
			break;
		case FOConstants.H_FILE_TIME:
			if (customTimeValidation(value)) {
				headerVO.setFileTime(value);
			}
			break;
		case FOConstants.H_RECORD_COUNT:
			headerVO.setRecordCount(value);
			break;
		case FOConstants.H_FNTX_FILE_NUM:
			
			String fileType=headerVO.getFileType();
			if(check && validFileNum(value))
			{
				if(!tQatpMappingDao.checkForDuplicateFileNum(value,headerVO.getFileType()))
				{
					headerVO.setFntxFileNum(value);
				}
				else
				{
					logger.info("'"+fileType+"'_FILE_NUM has been already used");
					throw new FNTXFileNumDuplicationException("'"+fileType+"'_FILE_NUM has been already used");
				}
			}
			else
			{
				throw new InvalidFileHeaderException("File Num Invalid");
			}
			
			break;
		case FOConstants.D_ETC_TRX_SERIAL_NUM:
			detailVO.setEtcTrxSerialNum(value);
			break;
		case FOConstants.D_ETC_REVENUE_DATE:
			detailVO.setEtcRevenuDate(value);
			break;
		case FOConstants.D_ETC_FAC_AGENCY:
//			if (validateAgencyMatch(value, headerVO.getFromAgencyId())) {
//			detailVO.setEtcFacAgency(value);
//			} else {
//				throw new InvalidRecordException("D_ETC_FAC_AGENCY mismatch..");
//			}
			detailVO.setEtcFacAgency(value);
			break;
		case FOConstants.D_ETC_TRX_TYPE:
			detailVO.setEtcTrxType(value);
			break;
		case FOConstants.D_ETC_ENTRY_DATE:
			detailVO.setEtcEntryDate(value);
//			if(genericValidation.buildDate(value,fMapping.getValidationValue())) {
//			detailVO.setEtcEntryDate(value);
//			}
			break;
		case FOConstants.D_ETC_ENTRY_TIME:
			detailVO.setEtcEntryTime(value);
//			if(genericValidation.buildTime(value,fMapping.getValidationValue())) {
//				detailVO.setEtcEntryTime(value);
//			}
			break;
		case FOConstants.D_ETC_ENTRY_PLAZA:
				detailVO.setEtcEntryPlaza(value);
			break;
		case FOConstants.D_ETC_ENTRY_LANE:
			detailVO.setEtcEntryLane(value);
			break;
		case FOConstants.D_ETC_TAG_AGENCY:
			detailVO.setEtcTagAgency(value);
			break;
		case FOConstants.D_ETC_TAG_SERIAL_NUMBER:
			detailVO.setEtcTagSerialNumber(value);
			break;
		case FOConstants.D_ETC_READ_PERFORMANCE:
			detailVO.setEtcReadPerformance(value);
			break;
		case FOConstants.D_ETC_WRITE_PERF: 
			detailVO.setEtcWritePref(value);
			break;
		case FOConstants.D_ETC_TAG_PGM_STATUS:
			detailVO.setEtcTagPgmStatus(value);
			break;
		case FOConstants.D_ETC_LANE_MODE:
			detailVO.setEtcLaneMode(value);
			break;
		case FOConstants.D_ETC_VALIDATION_STATUS:
			detailVO.setEtcValidationStatus(value);
			break;
		case FOConstants.D_ETC_LIC_STATE:
			detailVO.setEtcLicState(value);
			break;
		case FOConstants.D_ETC_LIC_NUMBER:
			detailVO.setEtcLicNumber(value);
			break;
		case FOConstants.D_ETC_CLASS_CHARGED:
			detailVO.setEtcClassCharged(value);
			break;
		case FOConstants.D_ETC_ACTUAL_AXLES:
			detailVO.setEtcActualAxles(value);
			break;
		case FOConstants.D_ETC_EXIT_SPEED:
			detailVO.setEtcExitSpeed(value);
			break;
		case FOConstants.D_ETC_OVER_SPEED:
			detailVO.setEtcOverSpeed(value);
			break;
		case FOConstants.D_ETC_EXIT_DATE:
			detailVO.setEtcExitDate(value);
			break;
		case FOConstants.D_ETC_EXIT_TIME:
			detailVO.setEtcExitTime(value);
			break;
		case FOConstants.D_ETC_EXIT_PLAZA:
			detailVO.setEtcExitPlaza(value);
			break;
		case FOConstants.D_ETC_EXIT_LANE:
			detailVO.setEtcExitLane(value);
			break;
		case FOConstants.D_ETC_DEBIT_CREDIT:
			detailVO.setEtcDebitCredit(value);
			break;
		case FOConstants.D_ETC_TOLL_AMOUNT:
			detailVO.setEtcTollAmount(value);
			break;

		default:
		}
	}
	
private boolean validFileNum(String value) 
{
	
	String regex = "[0-9]{6}";
	Pattern p = Pattern.compile(regex);
	if (value == null) {
		return false;
	} else {
		Matcher m = p.matcher(value);
		return m.matches();
	}
}

private boolean customDateTimeValidation(String value) throws InvalidFileNameException 
{
	if (!fileNameVO.getFileDateTime().equals(value)) {
		logger.info("FileName Date{} is not matching with Header Date", value);
		throw new InvalidFileNameException("Date mismatch for File and Header");
	}
	return true;
	}

//	@Override
//	public void finishFileProcess() throws CustomException, InterruptedException 
//	{
//		LocalDateTime from = null;
//		from = LocalDateTime.now();
//		for(FOTrnxDTO obj : detailVOList)
//		{
//			logger.info(obj.toString());
//			Transaction t= obj.getTransaction(masterDataCache,headerVO,xferControl);
//			logger.info(t.toString());
//			try
//			{
//				transDetailService.saveTransDetail(t.getTranDetail(timeZoneConv,masterDataCache));
//			}
//			catch(Exception ex)
//			{
//				ex.printStackTrace();
//			}
//		}
//		logger.debug("Time Taken for finish file process  : {} - Completed in {} ms", ChronoUnit.MILLIS.between(from, LocalDateTime.now()));
//
//	}
	
	@Override
	public void saveRecords(ReconciliationCheckPoint reconciliationCheckPoint,long atpFileId,String fileType) throws InterruptedException 
	{
		if (detailVOList!=null && !detailVOList.isEmpty() && detailVOList.size() > 0) 
		{
			List<TranDetail> list = new ArrayList<TranDetail>();
			Transaction trxn=null;
			TranDetail tranDetail=null;
			LocalDateTime from = null;
			
			for(FOTrnxDTO obj : detailVOList)
			{	
				logger.debug("FOTrnsFileDetails {}",obj);
				trxn=obj.getTransaction(masterDataCache, headerVO, xferControl,tQatpMappingDao,fileType);
				//logger.info("Transaction {}",trxn);
				tranDetail=trxn.getTranDetail(timeZoneConv,masterDataCache);
				logger.debug("TranDetail {}",tranDetail);
				list.add(tranDetail);
			}
			from = LocalDateTime.now();
			List<Long> txSeqlist = transDetailService.loadNextSeq(list.size());
			logger.debug("Total time taken for loading nex seq - Completed in {} ms", ChronoUnit.MILLIS.between(from, LocalDateTime.now()));
			setLaneTxId(list,txSeqlist,list.size());
			
			from = LocalDateTime.now();
			transDetailService.batchInsert(list);
			logger.debug("Time Taken for insertion in T_TRANDETAIL Table : {} - Completed in {} ms", ChronoUnit.MILLIS.between(from, LocalDateTime.now()));
			logger.debug("{} record inserted in T_TRAN_TABLE",list.size());
			
			//asycnprocesscall.batchInsertinTTranDetail(list);
			
			TranDetail lastRecord=list.get(list.size()-1);
			reconciliationCheckPoint.setLastLaneTxTd(lastRecord.getLaneTxId());
			reconciliationCheckPoint.setLastTxTimeStatmp(lastRecord.getTxTimeStamp().toLocalDateTime());
			reconciliationCheckPoint.setLastExternLaneId(lastRecord.getExternLaneId());
			reconciliationCheckPoint.setLastExternPlazaId(lastRecord.getExternPlazaId());
			reconciliationCheckPoint.setFileSeqNum(lastRecord.getFileSeqNum());
			reconciliationCheckPoint.setFileType(fileType);
			reconciliationCheckPoint.setXferControlId(xferControl.getXferControlId());
			
			/**Push messages to OSS**/
			/*
			 * Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new
			 * LocalDateAdapter()) .registerTypeAdapter(ZonedDateTime.class, new
			 * ZonedDateTimeConverter(DateTimeFormatter.ISO_ZONED_DATE_TIME))
			 * .excludeFieldsWithoutExposeAnnotation().create();
			 */
			
			/*
			 * Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new
			 * LocalDateAdapter()) .registerTypeAdapter(OffsetDateTime.class, new
			 * OffsetDateTimeConverter(DateTimeFormatter.ISO_OFFSET_DATE_TIME))
			 * .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
			 * .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
			 * .excludeFieldsWithoutExposeAnnotation().create();
			 */
			
			try
			{
				logger.info("Publish Message to TPMS_PARKING Queue");
				transDetailService.publishMessages(list);
				check = false;
				setIsHederPresentInFile(false);
				setIsDetailsPresentInFile(false);
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
	public AckFileWrapper prePareAckFileMetaData(AckStatusCode ackStatusCode,File file,String fileType)
	{
		AckFileWrapper ackObj=new AckFileWrapper();
		LocalDateTime date=LocalDateTime.now();

		StringBuilder sbFileName = new StringBuilder();
		sbFileName.append(file.getName().split("\\.")[0]).append(FOConstants.UNDERSCORE).append(fileType).append(QATPConstants.ACK_EXT);

		StringBuilder sbFileContent = new StringBuilder();
		
		sbFileContent.append(FOConstants.ACK).append(" ").append(FOConstants.FNTX_FROM_AGENCY).append(FOConstants.FNTX_TO_AGENCY)
		.append(StringUtils.rightPad(file.getName(), 50, " ")).append(genericValidation.getTodayTimestamp(date))
		.append(ackStatusCode.getCode());

		ackObj.setAckFileName(sbFileName.toString());
		ackObj.setSbFileContent(sbFileContent.toString());
		ackObj.setFile(file);
		if(ackStatusCode.equals(AckStatusCode.SUCCESS) || ackStatusCode.equals(AckStatusCode.GAP_IN_SEQ_NUM))
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
		ackFileInfoDto.setFileType(fileType);
		ackFileInfoDto.setAckFileName(sbFileName.toString());
		ackFileInfoDto.setAckFileStatus(ackStatusCode.getCode());
		ackFileInfoDto.setFileType(fileType);
		ackFileInfoDto.setTrxFileName(file.getName());
		//ackFileInfoDto.setFromAgency(getAgencyId());
		ackFileInfoDto.setFromAgency(FOConstants.FNTX_TO_AGENCY);
		ackFileInfoDto.setToAgency(FOConstants.FNTX_FROM_AGENCY);
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
	
	private boolean validateAgencyMatch(String agencyId, String fileAgencyID)  { 
		if (fileAgencyID.equals(agencyId)) {
			return true;
		} else {
			logger.info("Agency id {} mismatch with file agency id {}.", agencyId, fileAgencyID);
			return false;
		}
	}
	
	public boolean customDateValidation(LocalDate value) throws InvalidFileHeaderException {

		if (!fileNameVO.getFileDateTime().toLocalDate().equals(value)) {
			logger.info("Header Date{} is not matching with Header Date", value);
			throw new InvalidFileHeaderException("Date mismatch for File and Header");
		}
		return true;
	}
	
	public boolean customTimeValidation(String value) throws InvalidFileHeaderException {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HHmmss");
		String time = formatter.format(fileNameVO.getFileDateTime().toLocalTime());
		if (!time.equals(value)) {
			logger.info("Header Time{} is not matching with File Time", value);
			throw new InvalidFileHeaderException("Time mismatch for File and Header");
		}
		return true;
	}

}