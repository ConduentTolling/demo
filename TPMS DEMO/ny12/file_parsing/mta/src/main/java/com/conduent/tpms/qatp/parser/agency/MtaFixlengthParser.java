package com.conduent.tpms.qatp.parser.agency;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
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
import com.conduent.tpms.qatp.constants.QATPConstants;
import com.conduent.tpms.qatp.dao.IQatpDao;
import com.conduent.tpms.qatp.dao.ITransDetailDao;
import com.conduent.tpms.qatp.dao.TQatpMappingDao;
import com.conduent.tpms.qatp.dao.impl.InsertMTADaoImpl;
import com.conduent.tpms.qatp.dto.AckFileInfoDto;
import com.conduent.tpms.qatp.dto.AckFileWrapper;
import com.conduent.tpms.qatp.dto.MappingInfoDto;
import com.conduent.tpms.qatp.dto.MtaDetailInfoVO;
import com.conduent.tpms.qatp.dto.MtaFileNameInfo;
import com.conduent.tpms.qatp.dto.MtaHeaderinfoVO;
import com.conduent.tpms.qatp.dto.MtaLaneHeaderInfoVO;
import com.conduent.tpms.qatp.exception.CustomException;
import com.conduent.tpms.qatp.exception.InvalidRecordCountException;
import com.conduent.tpms.qatp.model.QatpBufferRecordType;
import com.conduent.tpms.qatp.model.ReconciliationCheckPoint;
import com.conduent.tpms.qatp.model.TranDetail;
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
public class MtaFixlengthParser extends FileParserImpl{

	// MtaFixlengthParser mtaFixlengthParser = new MtaFixlengthParser();
	private MtaDetailInfoVO detailVO;
	private MtaHeaderinfoVO headerVO;
	private MtaLaneHeaderInfoVO laneheaderVO;
	List<MtaDetailInfoVO> detailsRecord;
	private static final Logger log = LoggerFactory.getLogger(MtaFixlengthParser.class);
	
	@Autowired
	MasterDataCache masterDataCache;
	
	@Autowired
	TimeZoneConv timeZoneConv;
	
	public MtaFixlengthParser(TQatpMappingDao tQatpMappingDao,IQatpDao qatpDao,ITransDetailService transDetailService,GenericValidation genericValidation,
			MasterDataCache masterDataCache,InsertMTADaoImpl insertMTADaoImpl,TimeZoneConv timeZoneConv,AsyncProcessCall asyncProcessCall,ITransDetailDao transDetailDao)
	{
		this.tQatpMappingDao=tQatpMappingDao;
		this.qatpDao=qatpDao;
		this.transDetailService=transDetailService;
		this.genericValidation=genericValidation;
		this.masterDataCache=masterDataCache;
		this.insertMTADaoImpl=insertMTADaoImpl;
		this.timeZoneConv=timeZoneConv;
		this.asyncProcessCall=asyncProcessCall;
		this.transDetailDao = transDetailDao;
		//this.textFileReader=textFileReader;
		initialize();
	}

	@Override
	public void initialize() {
		setFileFormat(QATPConstants.FIXED);
		setFileType(QATPConstants.TRX);
		setAgencyId(QATPConstants.HOME_AGENCY_ID);
		setIsHederPresentInFile(true);
		setIsDetailsPresentInFile(true);
		setIsTrailerPresentInFile(true);
		setLaneHeaderPresentInFile(true);
		getFileParserParameter().setAgencyId("002");
		getFileParserParameter().setFileType(QATPConstants.TRX);
		fileNameVO = new MtaFileNameInfo();
		headerVO = new MtaHeaderinfoVO();
		detailVO = new MtaDetailInfoVO();
		laneheaderVO = new MtaLaneHeaderInfoVO();
		detailsRecord = new ArrayList<MtaDetailInfoVO>();

	}

	@Override
	public void processEndOfLine(String fileSection) {
		if (fileSection.equals("DETAIL")) {
			detailsRecord.add(detailVO);
		}
	}

	@Override
	public void processStartOfLine(String fileSection) {
		log.info("Process start of line..");
		if (QATPConstants.DETAIL.equalsIgnoreCase(fileSection)) {
			detailVO = new MtaDetailInfoVO();
		}

	}

	@Override
	public void doFieldMapping(String value, MappingInfoDto fMapping) {

		switch (fMapping.getFieldName()) {

		case "F_FILE_PL":
			fileNameVO.setPlazaId(value);
			break;
		case "F_FILE_DATE":
			fileNameVO.setFileDate(value);
			break;
		case "F_FILE_SERIAL":
			fileNameVO.setSerialNumber(value);
			break;

		case "H_REC_TYPE":
			headerVO.setRecordType(value);
			break;

		case "H_SERVICE_CENTER":
			headerVO.setServiceCenter(value);
			break;
			
		case "H_AGENCY_ID":
			headerVO.setAgencyId(value);
			break;

		case "H_PLAZA_ID":
			headerVO.setPlazaId(value);
			break;

		case "H_LAST_UPLOAD":
			headerVO.setLastUpload(value);
			break;

		case "H_CURRENT_TS":
			headerVO.setCurrentTs(value);
			break;

		case "H_TOTAL":
			headerVO.setTotal(value);
			break;

		case "D_RECORD_START":
			detailVO.setRecordStart(value);
			break;

		case "D_RECORD_NUMBER":
			detailVO.setRecordNumber(value);
			break;

		case "D_TAG_SERIAL_NUM":
			detailVO.setTagSeialNum(value);
			break;

		case "D_AGENCY_ID":
			detailVO.setAgencyID(value);
			break;

		case "D_AVI_CLASS":
			detailVO.setAviClass(value);
			break;

		case "D_TAG_CLASS":
			detailVO.setTagClass(value);
			break;

		case "D_VEHICLE_CLASS":
			detailVO.setVehicleClass(value);
			break;

		case "D_AVC_PROFILE":
			detailVO.setAvcProfile(value);
			break;

		case "D_AVC_AXLES":
			detailVO.setAvcAxles(value);
			break;

		case "D_LANE_NUMBER":
			detailVO.setLaneNum(value);
			break;

		case "D_TRANSIT_DATE":
			detailVO.setTransitDate(value);
			break;

		case "D_TRANSIT_TIME":
			detailVO.setTransitTime(value);
			break;
		case "D_TRAN_DETAIL_SERAIL":
			detailVO.setTranDetailSerail(value);
			break;
			
		case "D_TRANSTION_INFO":
			detailVO.setTransactionInfo(value);
			break;

		case "D_VEHICLE_SPEED":
			detailVO.setVehicleSpeed(value);
			break;

		case "D_FRONT_OCR_PLATE_COUNTRY":
			detailVO.setFrontOcrPlateCountry(value);
			break;

		case "D_FRONT_OCR_PLATE_STATE":
			detailVO.setFrontOcrPlateState(value);
			break;

		case "D_FRONT_OCR_PLATE_TYPE":
			detailVO.setFrontOcrPlateType(value);
			break;

		case "D_FRONT_OCR_PLATE_NUMBER":
			detailVO.setFrontOcrPlateNum(value);
			break;

		case "D_FRONT_OCR_CONFIDENCE":
			detailVO.setFrontOcrConfidence(value);
			break;

		case "D_FRONT_OCR_IMAGE_INDEX":
			detailVO.setFrontOcrImageIndex(value);
			break;

		case "D_FRONT_IMAGE_COLOR":
			detailVO.setFrontImageColor(value);
			break;

		case "D_REAR_OCR_PLATE_COUNTRY":
			detailVO.setRearOcrPlateCountry(value);;
			break;

		case "D_REAR_OCR_PLATE_STATE":
			detailVO.setRearOcrPlateState(value);
			break;

		case "D_REAR_OCR_PLATE_TYPE":
			detailVO.setRearOcrPlateType(value);
			break;

		case "D_REAR_OCR_PLATE_NUMBER":
			detailVO.setRearOcrPlateNumber(value);
			break;

		case "D_REAR_OCR_CONFIDENCE":
			detailVO.setRearOcrConfidence(value);
			break;

		case "D_REAR_OCR_IMAGE_INDEX":
			detailVO.setRearOcrImageIndex(value);
			break;

		case "D_REAR_IMAGE_COLOR":
			detailVO.setRearImageColor(value);
			break;

		case "D_EVENT":
			detailVO.setEvent(value);
			break;

		case "D_HOV_FLAG":
			detailVO.setHovFlag(value);
			break;

		case "D_PRESENCE_TIME":
			detailVO.setPresenceTime(value);
			break;

		case "D_LOSTPRESENCE_TIME":
			detailVO.setLostPresenceTime(value);
			break;

		case "D_ETX":
			detailVO.setEtx(value);
			break;

		case "L_RECORD_START":
			laneheaderVO.setRecordStart(value);
			break;
			
		case "L_LANENUMBER":
			laneheaderVO.setLaneNum(value);
			break;
			
		case "L_CONFIG":
			laneheaderVO.setLaneConfig(value);
			break;

		case "L_VALUT_ID":
			laneheaderVO.setVaultId(value);
			break;

		case "L_READER_ID":
			laneheaderVO.setReaderid(value);
			break;
			
		case "L_ON_TIME":
			laneheaderVO.setOnTime(value);
			break;

		}
	}

	@Override
	public void finishFileProcess() throws CustomException {
		try {
			for (MtaDetailInfoVO obj : detailsRecord) {
				log.info(obj.toString());
				QatpBufferRecordType t = obj.getBufferRecordType(headerVO,laneheaderVO,xferControl,fileNameVO,masterDataCache);
				log.info(t.toString());
				try {
					transDetailDao.saveTransDetail(t.getTranDetail(timeZoneConv));//need to pass transDetails
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void saveRecords(ReconciliationCheckPoint reconciliationCheckPoint) throws ParseException {
		if (detailsRecord != null && !detailsRecord.isEmpty() && detailsRecord.size() > 0)
			try {
				{
					List<TranDetail> list = new ArrayList<TranDetail>();
					List<Long> txSeqlist = new LinkedList<>();
					QatpBufferRecordType trxn=null;
					TranDetail tranDetail=null;
					LocalDateTime from = null;
					
					for (MtaDetailInfoVO obj : detailsRecord) 
					{
						trxn=(obj.getBufferRecordType(headerVO,laneheaderVO,xferControl,fileNameVO,masterDataCache));
						tranDetail=trxn.getTranDetail(timeZoneConv);
						list.add(tranDetail);
						log.info(list.toString());
					}
					
//					txSeqlist = transDetailDao.loadNextSeq(list.size());
//					setLaneTxId(list, txSeqlist, list.size());
//					
					from = LocalDateTime.now();
					txSeqlist = transDetailService.loadNextSeq(list.size());
					log.debug("Total time taken for loading nex seq - Completed in {} ms", ChronoUnit.MILLIS.between(from, LocalDateTime.now()));
					setLaneTxId(list,txSeqlist,list.size());
					
					//transDetailDao.batchInsert(list);
					from = LocalDateTime.now();
					transDetailService.batchInsert(list);
					log.debug("Time Taken for insertion in T_TRANDETAIL Table : {} - Completed in {} ms", ChronoUnit.MILLIS.between(from, LocalDateTime.now()));
					log.debug("{} record inserted in T_TRAN_TABLE",list.size());
					
					TranDetail lastRecord = list.get(list.size() - 1);
					reconciliationCheckPoint.setLastLaneTxTd(lastRecord.getLaneTxId());
					reconciliationCheckPoint.setLastTxTimeStatmp(lastRecord.getTxTimeStamp() != null ?
							lastRecord.getTxTimeStamp().toLocalDateTime() : null);
					reconciliationCheckPoint.setLastExternLaneId((laneheaderVO.getLaneNum().trim()));
					reconciliationCheckPoint.setLastExternPlazaId((headerVO.getPlazaId().trim()));
					reconciliationCheckPoint.setXferControlId(xferControl.getXferControlId());
					
					/** Push messages to OSS **/ 
					/*
					Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
							.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
							.excludeFieldsWithoutExposeAnnotation().create();
							*/
//					Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
//							.registerTypeAdapter(OffsetDateTime.class, new OffsetDateTimeConverter(DateTimeFormatter.ISO_OFFSET_DATE_TIME))
//							.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
//							.registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
//							.excludeFieldsWithoutExposeAnnotation().create();
					
					transDetailService.publishMessages(list);
					transDetailService.pushToOCR(list);
					detailsRecord.clear();
				}
			} catch (UnsupportedEncodingException e) {
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
	public void validateRecordCount() throws InvalidRecordCountException {
		if (recordCount != Convertor.toLong(headerVO.getTotal())) {
			throw new InvalidRecordCountException("Invalid Record Count");
		}
	}


	private String convertHexadecimalToDecimal(String value) {
		return String.valueOf(Integer.parseInt(value, 16));
	}

	@Override
	public AckFileWrapper prePareAckFileMetaData(AckStatusCode ackStatusCode, File file) {
		AckFileWrapper ackObj = new AckFileWrapper();
		LocalDateTime date = LocalDateTime.now();

		StringBuilder sbFileName = new StringBuilder();
		sbFileName.append("MTA_").append(file.getName().split("\\.")[0]).append(QATPConstants.ACK_EXT);

		StringBuilder sbFileContent = new StringBuilder();

		sbFileContent.append(StringUtils.rightPad(file.getName(), 40, ""))
				.append(genericValidation.getTodayTimestamp(date)).append(ackStatusCode.getCode());

		ackObj.setAckFileName(sbFileName.toString());
		ackObj.setSbFileContent(sbFileContent.toString());
		ackObj.setFile(file);
		if (ackStatusCode.equals(AckStatusCode.SUCCESS)) {
			ackObj.setFileDestDir(getConfigurationMapping().getFileInfoDto().getProcDir());
		} else if (ackStatusCode.equals(AckStatusCode.HEADER_FAIL) || ackStatusCode.equals(AckStatusCode.DETAIL_FAIL)
				|| ackStatusCode.equals(AckStatusCode.INVALID_RECORD_COUNT) || ackStatusCode.equals(AckStatusCode.INVALID_FILE_STRUCTURE) || ackStatusCode.equals(AckStatusCode.DUPLICATE_FILE_NUM)) {
			ackObj.setFileDestDir(getConfigurationMapping().getFileInfoDto().getUnProcDir());
		}

		/* Prepare AckStatus table */
		AckFileInfoDto ackFileInfoDto = new AckFileInfoDto();
		ackFileInfoDto.setAckFileDate(LocalDate.now());
		// String fileTime=genericValidation.getTodayTimestamp(date);
		DateTimeFormatter format = DateTimeFormatter.ofPattern("hhmmss");
		ackFileInfoDto.setAckFileTime(LocalDateTime.now().format(format));
		ackFileInfoDto.setFileType(getFileFormat());
		ackFileInfoDto.setAckFileName(sbFileName.toString());
		ackFileInfoDto.setAckFileStatus(ackStatusCode.getCode());
		ackFileInfoDto.setFileType(getFileType());
		ackFileInfoDto.setTrxFileName(file.getName());
		//ackFileInfoDto.setFromAgency(getAgencyId());
		ackFileInfoDto.setFromAgency(QATPConstants.MTA_FROM_AGENCY);
		ackFileInfoDto.setToAgency(QATPConstants.MTA_DEVICE_PREFIX);
		ackFileInfoDto.setExternFileId(xferControl.getXferControlId());
		ackObj.setAckFileInfoDto(ackFileInfoDto);
		
		return ackObj;
	}
}
