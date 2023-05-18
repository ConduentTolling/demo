package com.conduent.tpms.qatp.parser.agency;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
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
import com.conduent.tpms.qatp.constants.QATPConstants;
import com.conduent.tpms.qatp.dao.IQatpDao;
import com.conduent.tpms.qatp.dao.ITransDetailDao;
import com.conduent.tpms.qatp.dao.TQatpMappingDao;
import com.conduent.tpms.qatp.dao.impl.InsertPaDaoImpl;
import com.conduent.tpms.qatp.dto.AckFileInfoDto;
import com.conduent.tpms.qatp.dto.AckFileWrapper;
import com.conduent.tpms.qatp.dto.MappingInfoDto;
import com.conduent.tpms.qatp.dto.PaDetailInfoVO;
import com.conduent.tpms.qatp.dto.PaFileNameDetailVO;
import com.conduent.tpms.qatp.dto.PaHeaderInfoVO;
import com.conduent.tpms.qatp.exception.ATRNFileNumDuplicationException;
import com.conduent.tpms.qatp.exception.CustomException;
import com.conduent.tpms.qatp.exception.InvalidRecordCountException;
import com.conduent.tpms.qatp.exception.InvlidFileHeaderException;
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

@Component
public class PaFixlengthParser extends FileParserImpl {
	
	@Autowired
	TimeZoneConv timeZoneConv;
	
	public PaDetailInfoVO detailVO;
	private List<PaDetailInfoVO> detailsRecord;
	private boolean check = true;

	private static final Logger logger = LoggerFactory.getLogger(PaFixlengthParser.class);
	
	public PaFixlengthParser(TQatpMappingDao tQatpMappingDao,IQatpDao qatpDao,ITransDetailService transDetailService,GenericValidation genericValidation,
			MasterDataCache masterDataCache,InsertPaDaoImpl insertPaDaoImpl,TimeZoneConv timeZoneConv,AsyncProcessCall asyncProcessCall,ITransDetailDao transDetailDao)
	{
		this.tQatpMappingDao=tQatpMappingDao;
		this.qatpDao=qatpDao;
		this.transDetailService=transDetailService;
		this.genericValidation=genericValidation;
		this.masterDataCache=masterDataCache;
		this.insertPaDaoImpl=insertPaDaoImpl;
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
		setFileType(QATPConstants.ATRN);
		setAgencyId(QATPConstants.HOME_AGENCY_ID);
		setIsHederPresentInFile(true);
		setIsDetailsPresentInFile(true);
		setIsTrailerPresentInFile(false);
		getFileParserParameter().setAgencyId("003");
		getFileParserParameter().setFileType(QATPConstants.ATRN);
		fileNameVO = new PaFileNameDetailVO();
		detailVO = new PaDetailInfoVO();
		headerVO = new PaHeaderInfoVO();
		detailsRecord = new ArrayList<PaDetailInfoVO>();
		
	}

	@Override
	public void processStartOfLine(String fileSection) {
		if (fileSection.equals("DETAIL")) {
			detailVO = new PaDetailInfoVO();
		}
	}

	@Override
	public void processEndOfLine(String fileSection) {
		if (fileSection.equals("DETAIL")) {
			detailsRecord.add(detailVO);
		}
	}

	@Override
	public void doFieldMapping(String value, MappingInfoDto fMapping) throws InvlidFileHeaderException, ATRNFileNumDuplicationException {
		check = true;
		switch (fMapping.getFieldName()) {
		case "F_FILE_NAME_FROM_ENTITY":
			fileNameVO.setFromEntity(value);
			break;
		case "F_FILE_TO_ENTITY":
			fileNameVO.setToEntity(value);
		case "F_FILE_DATE":
			fileNameVO.setFileDate(value);
			break;
		case "F_FILE_TIME":
			fileNameVO.setFileTime(value);
			break;
		case "F_FILE_EXTENSION":
			fileNameVO.setFileType(value.replace(".", ""));
			break;
		case "H_FILE_TYPE":
			headerVO.setFileType(value);
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
		case "H_FILE_RECORD_COUNT":
			headerVO.setRecordCount(value);
			break;
		case "H_ATRN_FILE_NUM":
			//headerVO.setAtrnFileNum(value);
			if(check)
			{
				if(!tQatpMappingDao.checkForDuplicateFileNum(value))
				{
					headerVO.setAtrnFileNum(value);
				}
				else
				{
					logger.info("ATRN_FILE_NUM has been already used");
					throw new ATRNFileNumDuplicationException("ATRN_FILE_NUM has been already used");
				}
			}
			break;
		case "D_AG_TRX_SERIAL_NUM":
			detailVO.setAgTrxSerialNum(value);
			break;
		case "D_AG_REVENU_DATE":
			detailVO.setAgRevenuDate(value);
			break;
		case "D_AG_FAC_AGENCY":
			detailVO.setAgFacAgency(value);
			break;
		case "D_AG_TRX_TYPE":
			detailVO.setAgTrxType(value);
			break;
		case "D_AG_TRX_DATE":
			detailVO.setAgTrxDate(value);
			break;
		case "D_AG_TRX_TIME":
			detailVO.setAgTrxTime(value);
			break;
		case "D_AG_TRX_PLAZA":
			detailVO.setAgTrxPlaza(value);
			break;
		case "D_AG_TRX_LANE":
			detailVO.setAgTrxLane(value);
			break;
		case "D_AG_TRX_LANE_SEQ":
			detailVO.setAgTrxLaneSeq(value);
			break;
		case "D_AG_TAG_AGENCY":
			detailVO.setAgTagAgency(value);
			break;
		case "D_AG_TAG_SERIAL_NUMBER":
			detailVO.setAgTagSerialNumber(value);
			break;
		case "D_AG_READ_PERFORMANCE":
			detailVO.setAgReadPerformance(value);
			break;
		case "D_AG_WRITE_PERF":
			detailVO.setAgWritePerf(value);
			break;
		case "D_AG_READ_TYPE":
			detailVO.setAgReadType(value);
			break;
		case "D_AG_TAG_PGM_STATUS":
			detailVO.setAgTagPgmStatus(value);
			break;
		case "D_AG_LANE_MODE":
			detailVO.setAgLaneMode(value);
			break;
		case "D_AG_VALIDATION_STATUS":
			detailVO.setAgValidationStatus(value);
			break;
		case "D_AG_LIC_STATE":
			detailVO.setAgLicState(value);
			break;
		case "D_AG_LIC_NUMBER":
			detailVO.setAgLicNumber(value);
			break;
		case "D_AG_LIC_TYPE":
			detailVO.setAgLicType(value);
			break;
		case "D_AG_CONFIDENCE":
			detailVO.setAgConfidence(value);
		case "D_AG_TAG_CLASS":
			detailVO.setAgTagClass(value);
			break;
		case "D_AG_TAG_EXTRA_AXLES":
			detailVO.setAgTagExtraAxles(value);
			break;
		case "D_AG_AVC_CLASS":
			detailVO.setAgAvcClass(value);
			break;
		case "D_AG_AVC_EXTRA_AXLES":
			detailVO.setAgAvcExtraAxles(value);
			break;
		case "D_AG_MISMATCH":
			detailVO.setAgMismatch(value);
			break;
		case "D_AG_CLASS_CHARGED":
			detailVO.setAgClassCharged(value);
			break;
		case "D_AG_CHARGED_EXTRA_AXLES":
			detailVO.setAgChargedExtraAxles(value);
			break;
		case "D_AG_PLAN_CHARGED":
			detailVO.setAgPlanCharged(value);
			break;
		case "D_AG_EXIT_SPEED":
			detailVO.setAgExitSpeed(value);
			break;
		case "D_AG_OVER_SPEED":
			detailVO.setAgOverSpeed(value);
			break;
		case "D_AG_EZPASS_AMOUNT":
			detailVO.setAgEzpassAmount(value);
			break;
		case "D_AG_EZPASS_AMOUNT_DIS1":
			detailVO.setAgEzpassAmountDs1(value);
			break;
		case "D_AG_EZPASS_AMOUNT_DIS2":
			detailVO.setAgEzpassAmountDs2(value);
			break;
		case "D_AG_VIDEO_AMOUNT":
			detailVO.setAgVideoAmount(value);
			break;
		default:
		}

	}

	@Override
	public void finishFileProcess() throws CustomException {
		try {
			for (PaDetailInfoVO obj : detailsRecord) {
			logger.info(obj.toString());
			Transaction t = obj.getTransaction(masterDataCache, xferControl,headerVO);
			logger.info(t.toString());
			try
			{
				transDetailDao.saveTransDetail(t.getTranDetail(timeZoneConv));
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
	public void saveRecords(ReconciliationCheckPoint reconciliationCheckPoint){
		if (detailsRecord != null && !detailsRecord.isEmpty() && detailsRecord.size() > 0)
			try {
				{
					List<TranDetail> list = new ArrayList<TranDetail>();
					List<Long> txSeqlist = new LinkedList<>();
					Transaction trxn = null;
					TranDetail tranDetail=null;
					for (PaDetailInfoVO obj : detailsRecord) {
						trxn = obj.getTransaction(masterDataCache, xferControl,headerVO);
						tranDetail = trxn.getTranDetail(timeZoneConv);
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
					reconciliationCheckPoint.setFileType(Constants.ATRN);
					reconciliationCheckPoint.setXferControlId(xferControl.getXferControlId());

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
					try 
					{
						transDetailService.publishMessages(list);
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
	public AckFileWrapper prePareAckFileMetaData(AckStatusCode ackStatusCode, File file) {
		AckFileWrapper ackObj = new AckFileWrapper();
		LocalDateTime date = LocalDateTime.now();
		String recCount = String.format("%08d", recordCount);

		StringBuilder sbFileName = new StringBuilder();
		sbFileName.append(file.getName().substring(4, 7)).append("_").append(file.getName().split("\\.")[0])
				.append("_ATRN").append(QATPConstants.ACK_EXT);

		StringBuilder sbFileContent = new StringBuilder();

		sbFileContent.append(StringUtils.rightPad("ACK", 4)).append(file.getName().substring(4, 7))
				.append(file.getName().substring(0, 3)).append(StringUtils.rightPad(file.getName(), 50))
				.append(genericValidation.getTodayTimestamp(date)).append(ackStatusCode.getCode()).append("\n");

		ackObj.setAckFileName(sbFileName.toString());
		ackObj.setSbFileContent(sbFileContent.toString());
		ackObj.setFile(file);
		if (ackStatusCode.equals(AckStatusCode.SUCCESS) || ackStatusCode.equals(AckStatusCode.GAP_IN_SEQ_NUM)) {
			ackObj.setFileDestDir(getConfigurationMapping().getFileInfoDto().getProcDir());
		} else if (ackStatusCode.equals(AckStatusCode.HEADER_FAIL) || ackStatusCode.equals(AckStatusCode.DETAIL_FAIL)
				|| ackStatusCode.equals(AckStatusCode.INVALID_RECORD_COUNT) || ackStatusCode.equals(AckStatusCode.DUPLICATE_FILE_NUM)) {
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
		ackFileInfoDto.setFromAgency(file.getName().substring(4, 7));
		ackFileInfoDto.setToAgency(file.getName().substring(0, 3));
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
