package com.conduent.tpms.qatp.parser.agency;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
import org.springframework.stereotype.Component;

import com.conduent.app.timezone.utility.TimeZoneConv;
import com.conduent.tpms.qatp.constants.AckStatusCode;
import com.conduent.tpms.qatp.constants.Constants;
import com.conduent.tpms.qatp.constants.FileStatusInd;
import com.conduent.tpms.qatp.constants.QATPConstants;
import com.conduent.tpms.qatp.dao.IQatpDao;
import com.conduent.tpms.qatp.dao.ITransDetailDao;
import com.conduent.tpms.qatp.dao.TQatpMappingDao;
import com.conduent.tpms.qatp.dao.impl.InsertPbaDaoImpl;
import com.conduent.tpms.qatp.dto.AckFileInfoDto;
import com.conduent.tpms.qatp.dto.AckFileWrapper;
import com.conduent.tpms.qatp.dto.MappingInfoDto;
import com.conduent.tpms.qatp.dto.PbFileStatsDto;
import com.conduent.tpms.qatp.dto.PbaDetailInfoVO;
import com.conduent.tpms.qatp.dto.PbaFileNameDetailVO;
import com.conduent.tpms.qatp.dto.PbaHeaderInfoVO;
import com.conduent.tpms.qatp.exception.ICTXFileNumDuplicationException;
import com.conduent.tpms.qatp.exception.CustomException;
import com.conduent.tpms.qatp.exception.EmptyLineException;
import com.conduent.tpms.qatp.exception.InvalidFileNameException;
import com.conduent.tpms.qatp.exception.InvalidRecordCountException;
import com.conduent.tpms.qatp.exception.InvlidFileDetailException;
import com.conduent.tpms.qatp.exception.InvlidFileHeaderException;
import com.conduent.tpms.qatp.model.AtpFileIdObject;
import com.conduent.tpms.qatp.model.ReconciliationCheckPoint;
import com.conduent.tpms.qatp.model.TranDetail;
import com.conduent.tpms.qatp.model.Transaction;
import com.conduent.tpms.qatp.parser.FileParserImpl;
import com.conduent.tpms.qatp.service.ITransDetailService;
import com.conduent.tpms.qatp.utility.AsyncProcessCall;
import com.conduent.tpms.qatp.utility.Convertor;
import com.conduent.tpms.qatp.utility.DateUtils;
import com.conduent.tpms.qatp.utility.GenericValidation;
import com.conduent.tpms.qatp.utility.LocalDateAdapter;
import com.conduent.tpms.qatp.utility.LocalDateTimeAdapter;
import com.conduent.tpms.qatp.utility.MasterDataCache;
import com.conduent.tpms.qatp.utility.OffsetDateTimeConverter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Component
public class PbaFixlengthParser extends FileParserImpl {
	
	@Autowired
	TimeZoneConv timeZoneConv;
	
	public PbaDetailInfoVO detailVO;
	private List<PbaDetailInfoVO> detailsRecord;
	private boolean check = true;

	private static final Logger logger = LoggerFactory.getLogger(PbaFixlengthParser.class);
	
	public PbaFixlengthParser(TQatpMappingDao tQatpMappingDao,IQatpDao qatpDao,ITransDetailService transDetailService,GenericValidation genericValidation,
			MasterDataCache masterDataCache,InsertPbaDaoImpl insertPaDaoImpl,TimeZoneConv timeZoneConv,AsyncProcessCall asyncProcessCall,ITransDetailDao transDetailDao)
	{
		this.tQatpMappingDao=tQatpMappingDao;
		this.qatpDao=qatpDao;
		this.transDetailService=transDetailService;
		this.genericValidation=genericValidation;
		this.masterDataCache=masterDataCache;
		this.insertPbaDaoImpl=insertPaDaoImpl;
		this.timeZoneConv=timeZoneConv;
		this.asyncProcessCall=asyncProcessCall;
		this.transDetailDao=transDetailDao;
		//this.textFileReader=textFileReader;
		initialize();
	}

	@Override
	public void initialize() {
		logger.info("Initializing process...");
		setFileFormat(QATPConstants.FIXED);
		setFileType(QATPConstants.TRX);
		setAgencyId(QATPConstants.HOME_AGENCY_ID);
		setIsHederPresentInFile(true);
		setIsDetailsPresentInFile(true);
		setIsTrailerPresentInFile(false);
		getFileParserParameter().setAgencyId("019");
		getFileParserParameter().setFileType(QATPConstants.TRX);
		fileNameVO = new PbaFileNameDetailVO();
		detailVO = new PbaDetailInfoVO();
		headerVO = new PbaHeaderInfoVO();
		detailsRecord = new ArrayList<PbaDetailInfoVO>();
		
	}

	@Override
	public void processStartOfLine(String fileSection) {
		if (fileSection.equals("DETAIL")) {
			detailVO = new PbaDetailInfoVO();
		}
	}

	@Override
	public void processEndOfLine(String fileSection) {
		if (fileSection.equals("DETAIL")) {
			detailsRecord.add(detailVO);
		}
	}

	@Override
	public void doFieldMapping(String value, MappingInfoDto fMapping) throws InvlidFileHeaderException, ICTXFileNumDuplicationException 
	{
		check = true;
		switch (fMapping.getFieldName()) 
		{
		case "F_FILE_NAME_FROM_ENTITY":
			fileNameVO.setFromEntity(value);
			break;
		case "F_FILE_TO_ENTITY":
			fileNameVO.setToEntity(value);
			break;
		case "F_FILE_DATE":
			fileNameVO.setFileDate(value);
			break;
		case "F_FILE_TIME":
			fileNameVO.setFileTime(value);
			break;
		case "F_FILE_DATE_TIME":
			fileNameVO.setFileDateTime(value);
			fileNameVO.setFileDate(value.substring(0, 8));
			fileNameVO.setFileTime(value.substring(8, 14));
			break;
		case "F_FILE_EXTENSION":
			fileNameVO.setFileType(value.replace(".", ""));
			break;
		case "H_FILE_TYPE":
			headerVO.setFileType(value);
			break;
		case "H_VERSION":
			headerVO.setVersion(value);
			break;
		case "H_FROM_ENTITY":
			headerVO.setFromEntity(value);
			break;
		case "H_TO_ENTITY":
			headerVO.setToEntity(value);
			break;
		case "H_FILE_DATE":
			headerVO.setFileDate(value);
			break;
		case "H_FILE_TIME":
			headerVO.setFileTime(value);
			break;
		case "H_FILE_DATE_TIME":
			headerVO.setFileDateTime(value);
			headerVO.setFileDate(value.substring(0, 10).replaceAll("-", ""));
			headerVO.setFileTime(value.substring(11, 19).replaceAll(":", ""));
			break;
			
		case "H_FILE_RECORD_COUNT":
			headerVO.setRecordCount(value);
			break;
		case "H_ICTX_FILE_NUM":
			//headerVO.setAtrnFileNum(value);
			if(check)
			{
				if(!tQatpMappingDao.checkForDuplicateFileNum(value))
				{
					headerVO.setIctxFileNum(value);
				}
				else
				{
					logger.info("ICTX_FILE_NUM has been already used");
					throw new ICTXFileNumDuplicationException("ICTX_FILE_NUM has been already used");
				}
			}
			break;
		case "D_ETC_TRX_SERIAL_NUM":
			detailVO.setEtcTrxSerialNum(value);
			break;
		case "D_ETC_REVENUE_DATE":
			detailVO.setEtcRevenueDate(value);
			break;
		case "D_ETC_FAC_AGENCY":
			detailVO.setEtcFacAgency(value);
			break;
		case "D_ETC_TRX_TYPE":
			detailVO.setEtcTrxType(value);
			break;
		case "D_ETC_ENTRY_DATE":
			detailVO.setEtcEntryDate(value);
			break;
		case "D_ETC_ENTRY_TIME":
			detailVO.setEtcEntryTime(value);
			break;
		case "D_ETC_ENTRY_DATE_TIME":
			detailVO.setEtcEntryDateTime(value);
			break;
		case "D_ETC_ENTRY_PLAZA":
			detailVO.setEtcEntryPlaza(value);
			break;
		case "D_ETC_ENTRY_LANE":
			detailVO.setEtcEntryLane(value);
			break;
		case "D_ETC_TAG_AGENCY":
			detailVO.setEtcTagAgency(value);
			break;
		case "D_ETC_TAG_SERIAL_NUMBER":
			detailVO.setEtcTagSerialNumber(value);
			break;
		case "D_ETC_READ_PERFORMANCE":
			detailVO.setEtcReadPerformance(value);
			break;
		case "D_ETC_WRITE_PERF":
			detailVO.setEtcWritePerf(value);
			break;
		case "D_ETC_TAG_PGM_STATUS":
			detailVO.setEtcTagPgmStatus(value);
			break;
		case "D_ETC_LANE_MODE":
			detailVO.setEtcLaneMode(value);
			break;
		case "D_ETC_VALIDATION_STATUS":
			detailVO.setEtcValidationStatus(value);
			break;
		case "D_ETC_LIC_STATE":
			detailVO.setEtcLicState(value);
			break;
		case "D_ETC_LIC_NUMBER":
			detailVO.setEtcLicNumber(value);
			break;
		case "D_ETC_LIC_TYPE":
			detailVO.setEtcLicType(value);
			break;
		case "D_ETC_CLASS_CHARGED":
			detailVO.setEtcClassCharged(value);
			break;
		case "D_ETC_ACTUAL_AXLES":
			detailVO.setEtcActualAxles(value);
			break;
		case "D_ETC_EXIT_SPEED":
			detailVO.setEtcExitSpeed(value);
			break;
		case "D_ETC_OVER_SPEED":
			detailVO.setEtcOverSpeed(value);
			break;
		case "D_ETC_EXIT_DATE":
			detailVO.setEtcExitDate(value);
			break;
		case "D_ETC_EXIT_TIME":
			detailVO.setEtcExitTime(value);
			break;
		case "D_ETC_EXIT_DATE_TIME":
			detailVO.setEtcExitDate(value.substring(0, 10).replaceAll("-", ""));
			detailVO.setEtcExitTime(value.substring(11, 19).replaceAll(":", ""));
			detailVO.setEtcExitDateTime(value);
			break;
		case "D_ETC_EXIT_PLAZA":
			detailVO.setEtcExitPlaza(value);
			break;
		case "D_ETC_EXIT_LANE":
			detailVO.setEtcExitLane(value);
			break;
		case "D_ETC_DEBIT_CREDIT":
			detailVO.setEtcDebitCredit(value);
			break;		
		case "D_ETC_TOLL_AMOUNT":
			detailVO.setEtcTollAmount(value);
			break;
		default:
		}

	}

	@Override
	public void finishFileProcess(PbFileStatsDto pbFileStatsDto) throws CustomException 
	{
		try
		{
			for (PbaDetailInfoVO obj : detailsRecord) 
			{
				logger.info(obj.toString());
				Transaction t = obj.getTransaction(masterDataCache, xferControl,headerVO, tQatpMappingDao, agencyIdPbaOrTiba);
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
			
			//Insert in T_PB_FILE_STATS
			qatpDao.insertPbFileStats(pbFileStatsDto);
			
		}
		catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}		
	}
	@Override
	public void saveRecords(ReconciliationCheckPoint reconciliationCheckPoint, PbFileStatsDto pbFileStatsDto){
		if (detailsRecord != null && !detailsRecord.isEmpty() && detailsRecord.size() > 0)
			try {
				{
					List<TranDetail> list = new ArrayList<TranDetail>();
					List<Long> txSeqlist = new LinkedList<>();
					Transaction trxn = null;
					TranDetail tranDetail=null;
					for (PbaDetailInfoVO obj : detailsRecord) 
					{
						trxn = obj.getTransaction(masterDataCache, xferControl,headerVO, tQatpMappingDao, agencyIdPbaOrTiba);
						tranDetail = trxn.getTranDetail(timeZoneConv, masterDataCache);
						logger.info(list.toString());
						list.add(tranDetail);
					}
					txSeqlist = transDetailDao.loadNextSeq(list.size());
					setLaneTxId(list, txSeqlist, list.size());
					transDetailDao.batchInsert(list);
					TranDetail lastRecord = list.get(list.size() - 1);
					reconciliationCheckPoint.setLastLaneTxTd(lastRecord.getLaneTxId());
					reconciliationCheckPoint.setLastTxTimeStatmp(lastRecord.getTxTimeStamp() !=null ?
							lastRecord.getTxTimeStamp().toLocalDateTime(): null);
					reconciliationCheckPoint.setLastExternLaneId(lastRecord.getExternLaneId());
					reconciliationCheckPoint.setLastExternPlazaId(lastRecord.getExternPlazaId());
					reconciliationCheckPoint.setFileSeqNum(lastRecord.getFileSeqNum());
					reconciliationCheckPoint.setFileType(Constants.TRX);
					reconciliationCheckPoint.setXferControlId(xferControl.getXferControlId());

					//PbFileStatsDto
					pbFileStatsDto.setXferControlId(xferControl.getXferControlId());
					pbFileStatsDto.setFileSeqNumber(Long.valueOf(reconciliationCheckPoint.getFileSeqNum()));
					pbFileStatsDto.setInputCount(recordCount);			
					pbFileStatsDto.setFileDate(DateUtils.parseLocalDate(headerVO.getFileDate(),"yyyyMMdd"));
					
					/** Push messages to OSS **/ 
					/*
					 * Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new
					 * LocalDateAdapter()) .registerTypeAdapter(LocalDateTime.class, new
					 * LocalDateTimeAdapter()) .excludeFieldsWithoutExposeAnnotation().create();
					 */
					
//					Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
//							.registerTypeAdapter(OffsetDateTime.class, new OffsetDateTimeConverter(DateTimeFormatter.ISO_OFFSET_DATE_TIME))
//							.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
//							.registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
//							.excludeFieldsWithoutExposeAnnotation().create();
//					
					//collect atpFileID
					List<AtpFileIdObject> atpFileIdList = transDetailService.getAtpFileId(xferControl.getXferControlId());
					logger.info("Atp File Id {} ",atpFileIdList);
					
					try 
					{
						transDetailService.publishMessages(list, atpFileIdList, agencyIdPbaOrTiba);
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

	private void setLaneTxId(List<TranDetail> tempTxlist, List<Long> txSeqlist, Integer sizeOftxRec) {
		for (int i = 0; i < sizeOftxRec; i++) {
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
		sbFileName.append(file.getName().substring(5, 9)).append("_").append(file.getName().split("\\.")[0])
				.append("_ICTX").append(QATPConstants.ACK_EXT);
		logger.info("File Name : {}", sbFileName.toString());
		
		StringBuilder sbFileContent = new StringBuilder();
		
		if(ackStatusCode.getCode().equals("07"))
		{
			headerVO.setRecordCount(QATPConstants.DEFAULT_COUNT);
		}
		
		sbFileContent.append(StringUtils.rightPad("ACK", 4)).append(file.getName().substring(5, 9))//4,7
				.append(file.getName().substring(0, 4)).append(StringUtils.rightPad(file.getName().replaceAll(".TRX", ".ICTX"), 50))
				.append(genericValidation.getTodayTimestamp(date)).append(ackStatusCode.getCode()).append("\n");
		logger.info("File Name : {}", sbFileContent.toString());
		
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
		String fileTime = genericValidation.getTodayTimestamp(date, "hhmmss");
		ackFileInfoDto.setAckFileTime(fileTime);
		ackFileInfoDto.setFileType(getFileFormat());
		ackFileInfoDto.setAckFileName(sbFileName.toString());
		ackFileInfoDto.setAckFileStatus(ackStatusCode.getCode());
		ackFileInfoDto.setFileType(getFileType());
		ackFileInfoDto.setTrxFileName(file.getName());
		ackFileInfoDto.setFromAgency(file.getName().substring(0, 4));
		ackFileInfoDto.setToAgency(file.getName().substring(5, 9));  // according to PBA NEW ICD toAgency is 0008 - file.getName().substring(5, 9)
		ackFileInfoDto.setExternFileId(xferControl.getXferControlId());
		ackObj.setAckFileInfoDto(ackFileInfoDto);
		return ackObj;
	}

	public void prepareAck(String statusCode) {
		// createAckFile("","",null);
	}

	@Override
	public void validateRecordCount() throws InvalidRecordCountException {
		if (recordCount != Convertor.toLong(headerVO.getRecordCount())) {
			throw new InvalidRecordCountException("Invalid Record Count");
		}
	}

	@Override
	public void processedPartiallyExecutedFile(File file,String externFileId, PbFileStatsDto pbFileStatsDto) throws IOException, InvalidFileNameException, InvlidFileHeaderException, EmptyLineException, InvlidFileDetailException, InterruptedException, InvalidRecordCountException, ICTXFileNumDuplicationException
	{
		logger.info("Started with partially executing of file {}",file.getName());
		boolean endOfFile = false;
		LocalDateTime from = null;
		
		BufferedReader reader = getFileReader(file);
		String previousLine = reader.readLine();
		String currentLine = reader.readLine();
		
		if(previousLine.length() == 40) // check whether line is Header
		{
			validateAndProcessFileName(file.getName());
			validateAndProcessHeader(previousLine);
		}
		
		for(;;currentLine=reader.readLine())
		{
			if(currentLine.substring(0,20).matches(externFileId))
			{
				String nextRecord = reader.readLine();
				for(;;nextRecord=reader.readLine())
				{
					if(nextRecord != null)
					{
						from = LocalDateTime.now();
						isFirstRead=false;
						parseAndValidateDetails(nextRecord);
						
						if(detailsRecord.size() >0)
						{
							saveRecords(reconciliationCheckPoint, pbFileStatsDto);
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
						
						//Insert in T_PB_FILE_STATS 
						qatpDao.insertPbFileStats(pbFileStatsDto);
						
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
