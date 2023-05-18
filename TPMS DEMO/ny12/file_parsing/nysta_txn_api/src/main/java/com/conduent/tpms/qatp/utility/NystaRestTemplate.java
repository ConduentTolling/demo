package com.conduent.tpms.qatp.utility;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.conduent.app.timezone.utility.TimeZoneConv;
import com.conduent.tpms.qatp.dao.TQatpMappingDao;
import com.conduent.tpms.qatp.dto.FileParserParameters;
import com.conduent.tpms.qatp.dto.MappingInfoDto;
import com.conduent.tpms.qatp.dto.NystaAetTxDto;
import com.conduent.tpms.qatp.dto.NystaHeaderInfoVO;
import com.conduent.tpms.qatp.exception.InvalidDetailException;
import com.conduent.tpms.qatp.model.TranDetail;
import com.conduent.tpms.qatp.model.Transaction;
import com.conduent.tpms.qatp.model.XferControl;
import com.conduent.tpms.qatp.parser.agency.NystaFixlengthParser;
import com.conduent.tpms.qatp.service.ITransDetailService;
import com.conduent.tpms.qatp.service.QatpService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


@Component
public class NystaRestTemplate {

	private static final Logger logger = LoggerFactory.getLogger(NystaRestTemplate.class);

	@Autowired
	QatpService qatpService;

	@Autowired
	NystaFixlengthParser nystaFixlengthParser;

	@Autowired
	protected MasterDataCache masterDataCache;

	@Autowired
	protected ITransDetailService transDetailService;

	@Autowired
	TimeZoneConv timeZoneConv;

	@Autowired
	protected TQatpMappingDao tQatpMappingDao;

	@Autowired
	protected
	GenericValidation genericValidation;

	NystaAetTxDto detailVO;
	private List<NystaAetTxDto> detailVOList;
	private NystaHeaderInfoVO headerVO;
	protected XferControl xferControl;
	private FileParserParameters configurationMapping;

	public FileParserParameters getConfigurationMapping() {
		return configurationMapping;
	}

	public void setConfigurationMapping(FileParserParameters configurationMapping) {
		this.configurationMapping = configurationMapping;
	}

	protected FileParserParameters fileParserParameter=new FileParserParameters();

	public FileParserParameters getFileParserParameter() {
		return fileParserParameter;
	}

	public void setFileParserParameter(FileParserParameters fileParserParameter) {
		this.fileParserParameter = fileParserParameter;
	}

	public void callgetNystaTxnAPI() throws IOException, InvalidDetailException {

		setConfigurationMapping(tQatpMappingDao.getMappingConfigurationDetails1(getFileParserParameter()));

		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		// headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<String> entity = new HttpEntity<String>(headers);
		// "https://mocki.io/v1/d4867d8b-b5d5-4a48-a4ab-79131b5809b8";
		String resoureceUrl = "http://localhost:8080/api/demo";
		ResponseEntity<NystaAetTxDto> responseEntity = restTemplate.exchange(resoureceUrl, HttpMethod.GET, entity,
				NystaAetTxDto.class);
		System.out.println("tranDetail...==>" + responseEntity.getBody());

		if (responseEntity.getStatusCode() == HttpStatus.OK) {
			System.out.println(responseEntity);

			NystaAetTxDto obj = responseEntity.getBody();

			detailVO = responseEntity.getBody();
			List<NystaAetTxDto> list1 = new ArrayList<NystaAetTxDto>();
			list1.add(obj);
			System.out.println("List1  ====> " + list1);

			parseAndValidate(list1, configurationMapping.getDetailRecMappingInfo());

			Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
					.registerTypeAdapter(OffsetDateTime.class, new OffsetDateTimeConverter(DateTimeFormatter.ISO_OFFSET_DATE_TIME))
					.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
					.registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
					.excludeFieldsWithoutExposeAnnotation().create();

			try {
				// detailVOList = new ArrayList<NystaAetTxDto>();
				Transaction t = obj.getTransaction(masterDataCache, headerVO, xferControl);
				logger.info(t.toString());

				TranDetail tran = t.getTranDetail(timeZoneConv);
				transDetailService.saveTransDetail(tran);
				List<TranDetail> list = new ArrayList<TranDetail>();
				list.add(tran);
				// transDetailService.batchInsert(list);
				transDetailService.publishMessages(list, gson);
				transDetailService.pushToOCR(list, gson);
			} catch (Exception ex) {
				ex.printStackTrace();
				logger.info("Exception {}", ex.getMessage());
			}

		} else {
			logger.info("getting error == > responseEntity.getStatusCode() {}",responseEntity.getStatusCode() != HttpStatus.OK);
		}
	}



	/*
	 * public void parseAndValidateDetails(String line) throws
	 * InvalidDetailException {
	 * parseAndValidate(line,configurationMapping.getDetailSize(),
	 * configurationMapping.getDetailRecMappingInfo()); }
	 */


	public void parseAndValidate(List<NystaAetTxDto> nystaAetTxDto, List<MappingInfoDto> mappings)
			throws InvalidDetailException {

		if (nystaAetTxDto == null) {

			if (!mappings.get(1).getFieldType().equals("DETAIL")) {
				throw new InvalidDetailException("Invalid Detail Record or Detail Record is empty");
			}

		}
		boolean isValid = false;

		for (MappingInfoDto fieldMapping : mappings) {
			// isValid = false;

			for (NystaAetTxDto value : nystaAetTxDto) {

				// isValid = genericValidation.doValidation(fieldMapping, value1);
				try {
					isValid = doFieldMapping(value, fieldMapping);
					//logger.info("isValid {}", isValid);
				} catch (Exception ex) {
					logger.info("Exception {}", ex.getMessage());
				}
			}

			/*
			 * if (!isValid && fieldMapping.getFileLevelRejection() != null &&
			 * fieldMapping.getFileLevelRejection().equalsIgnoreCase("y")) {
			 * 
			 * if (fieldMapping.getFieldType().equals("DETAIL")) { throw new
			 * InvalidDetailException("Invalid Detail Record"); }
			 * 
			 * }
			 */
		}

	}


	public boolean doFieldMapping(NystaAetTxDto nystaAetTxDto, MappingInfoDto fMapping) throws InvalidDetailException
	{
		boolean isValid = false;
		switch (fMapping.getFieldName()) 
		{
		case "D_RECORD_TYPE_CODE":
			//detailVO.setRecordTypeCode(value);
			isValid=genericValidation.doValidation(fMapping, nystaAetTxDto.getRecordTypeCode());
			if(isValid==false && fMapping.getFileLevelRejection()!=null && fMapping.getFileLevelRejection().equals("Y")) {
				throw new InvalidDetailException("EtcEntryTime is not valid");
			}
			break;
		case  "D_ETC_TRX_TYPE" :
			isValid=genericValidation.doValidation(fMapping, nystaAetTxDto.getEtcTrxType());
			if(isValid==false && fMapping.getFileLevelRejection()!=null && fMapping.getFileLevelRejection().equals("Y")) {
				throw new InvalidDetailException("EtcTrxType is not valid");
			}
			break;
		case  "D_ETC_ENTRY_DATE" :
			isValid=genericValidation.doValidation(fMapping, nystaAetTxDto.getEtcEntryDate());
			if(isValid==false && fMapping.getFileLevelRejection()!=null && fMapping.getFileLevelRejection().equals("Y")) {
				throw new InvalidDetailException("EtcEntryDate is not valid");
			}
			break;
		case  "D_ETC_ENTRY_TIME" :
			isValid=genericValidation.doValidation(fMapping, nystaAetTxDto.getEtcEntryTime());
			if(isValid==false && fMapping.getFileLevelRejection()!=null && fMapping.getFileLevelRejection().equals("Y")) {
				throw new InvalidDetailException("EtcEntryTime is not valid");
			}
			break;
		case  "D_ETC_ENTRY_PLAZA_ID" :
			isValid=genericValidation.doValidation(fMapping, nystaAetTxDto.getEtcEntryPlazaId());
			if(isValid==false && fMapping.getFileLevelRejection()!=null && fMapping.getFileLevelRejection().equals("Y")) {
				throw new InvalidDetailException("EtcEntryPlazaId is not valid");
			}
			break;
		case  "D_ETC_ENTRY_LANE_ID" :
			isValid=genericValidation.doValidation(fMapping, nystaAetTxDto.getEtcEntryLaneId());
			if(isValid==false && fMapping.getFileLevelRejection()!=null && fMapping.getFileLevelRejection().equals("Y")) {
				throw new InvalidDetailException("EtcEntryLaneId is not valid");
			}
			break;
		case  "D_ETC_ENTRY_DATA_SOURCE" :
			isValid=genericValidation.doValidation(fMapping, nystaAetTxDto.getEtcEntryDataSource());
			if(isValid==false && fMapping.getFileLevelRejection()!=null && fMapping.getFileLevelRejection().equals("Y")) {
				throw new InvalidDetailException("EtcEntryDataSource is not valid");
			}
			break;
		case  "D_ETC_APP_ID" :
			isValid=genericValidation.doValidation(fMapping, nystaAetTxDto.getEtcAppId());
			if(isValid==false && fMapping.getFileLevelRejection()!=null && fMapping.getFileLevelRejection().equals("Y")) {
				throw new InvalidDetailException("EtcAppId is not valid");
			}
			break;
		case  "D_ETC_TYPE" :
			isValid=genericValidation.doValidation(fMapping, nystaAetTxDto.getEtcType());
			if(isValid==false && fMapping.getFileLevelRejection()!=null && fMapping.getFileLevelRejection().equals("Y")) {
				throw new InvalidDetailException("EtcType is not valid");
			}
			break;
		case  "D_ETC_GROUP_ID" :
			isValid=genericValidation.doValidation(fMapping, nystaAetTxDto.getEtcGroupId());
			if(isValid==false && fMapping.getFileLevelRejection()!=null && fMapping.getFileLevelRejection().equals("Y")) {
				throw new InvalidDetailException("EtcGroupId is not valid");
			}
			break;
		case  "D_ETC_AGENCY_ID" :
			isValid=genericValidation.doValidation(fMapping, nystaAetTxDto.getEtcAgencyId());
			if(isValid==false && fMapping.getFileLevelRejection()!=null && fMapping.getFileLevelRejection().equals("Y")) {
				throw new InvalidDetailException("EtcAgencyId is not valid");
			}
			break;
		case  "D_ETC_TAG_SERIAL_NUM" :
			isValid=genericValidation.doValidation(fMapping, nystaAetTxDto.getEtcTagSerialNum());
			if(isValid==false && fMapping.getFileLevelRejection()!=null && fMapping.getFileLevelRejection().equals("Y")) {
				throw new InvalidDetailException("EtcTagSerialNum is not valid");
			}
			break;
		case  "D_ETC_SERIAL_NUMBER" :
			isValid=genericValidation.doValidation(fMapping, nystaAetTxDto.getEtcSerialNumber());
			if(isValid==false && fMapping.getFileLevelRejection()!=null && fMapping.getFileLevelRejection().equals("Y")) {
				throw new InvalidDetailException("EtcSerialNumber is not valid");
			}
			break;
		case  "D_ETC_READ_PERFORMANCE" :
			isValid=genericValidation.doValidation(fMapping, nystaAetTxDto.getEtcReadPerformance());
			if(isValid==false && fMapping.getFileLevelRejection()!=null && fMapping.getFileLevelRejection().equals("Y")) {
				throw new InvalidDetailException("EtcReadPerformance is not valid");
			}
			break;
		case  "D_ETC_LANE_MODE" :
			isValid=genericValidation.doValidation(fMapping, nystaAetTxDto.getEtcLaneMode());
			if(isValid==false && fMapping.getFileLevelRejection()!=null && fMapping.getFileLevelRejection().equals("Y")) {
				throw new InvalidDetailException("EtcLaneMode is not valid");
			}
			break;
		case  "D_ETC_COLLECTOR_ID" :
			isValid=genericValidation.doValidation(fMapping, nystaAetTxDto.getEtcCollectorId());
			if(isValid==false && fMapping.getFileLevelRejection()!=null && fMapping.getFileLevelRejection().equals("Y")) {
				throw new InvalidDetailException("EtcCollectorId is not valid");
			}
			break;
		case  "D_ETC_DERIVED_VEH_CLASS" :
			isValid=genericValidation.doValidation(fMapping, nystaAetTxDto.getEtcDerivedVehClass());
			if(isValid==false && fMapping.getFileLevelRejection()!=null && fMapping.getFileLevelRejection().equals("Y")) {
				throw new InvalidDetailException("EtcDerivedVehClass is not valid");
			}
			break;
		case  "D_ETC_READ_VEH_TYPE" :
			isValid=genericValidation.doValidation(fMapping, nystaAetTxDto.getEtcReadVehType());
			if(isValid==false && fMapping.getFileLevelRejection()!=null && fMapping.getFileLevelRejection().equals("Y")) {
				throw new InvalidDetailException("EtcReadVehType is not valid");
			}
			break;
		case  "D_ETC_READ_VEH_AXLES" :
			isValid=genericValidation.doValidation(fMapping, nystaAetTxDto.getEtcReadVehAxles());
			if(isValid==false && fMapping.getFileLevelRejection()!=null && fMapping.getFileLevelRejection().equals("Y")) {
				throw new InvalidDetailException("EtcReadVehAxles is not valid");
			}
			break;
		case  "D_ETC_READ_VEH_WEIGHT" :
			isValid=genericValidation.doValidation(fMapping, nystaAetTxDto.getEtcReadVehWeight());
			if(isValid==false && fMapping.getFileLevelRejection()!=null && fMapping.getFileLevelRejection().equals("Y")) {
				throw new InvalidDetailException("EtcReadVehWeight is not valid");
			}
			break;
		case  "D_ETC_READ_REAR_TIRES" :
			isValid=genericValidation.doValidation(fMapping, nystaAetTxDto.getEtcReadRearTires());
			if(isValid==false && fMapping.getFileLevelRejection()!=null && fMapping.getFileLevelRejection().equals("Y")) {
				throw new InvalidDetailException("EtcReadRearTires is not valid");
			}
			break;
		case  "D_ETC_WRITE_VEH_TYPE" :
			isValid=genericValidation.doValidation(fMapping, nystaAetTxDto.getEtcWriteVehType());
			if(isValid==false && fMapping.getFileLevelRejection()!=null && fMapping.getFileLevelRejection().equals("Y")) {
				throw new InvalidDetailException("EtcWriteVehType is not valid");
			}
			break;
		case  "D_ETC_WRITE_VEH_AXLES" :
			isValid=genericValidation.doValidation(fMapping, nystaAetTxDto.getEtcWriteVehAxles());
			if(isValid==false && fMapping.getFileLevelRejection()!=null && fMapping.getFileLevelRejection().equals("Y")) {
				throw new InvalidDetailException("EtcWriteVehAxles is not valid");
			}
			break;
		case  "D_ETC_WRITE_VEH_WEIGHT" :
			isValid=genericValidation.doValidation(fMapping, nystaAetTxDto.getEtcWriteVehWeight());
			if(isValid==false && fMapping.getFileLevelRejection()!=null && fMapping.getFileLevelRejection().equals("Y")) {
				throw new InvalidDetailException("EtcWriteVehWeight is not valid");
			}
			break;
		case  "D_ETC_WRITE_REAR_TIRES" :
			isValid=genericValidation.doValidation(fMapping, nystaAetTxDto.getEtcWriteRearTires());
			if(isValid==false && fMapping.getFileLevelRejection()!=null && fMapping.getFileLevelRejection().equals("Y")) {
				throw new InvalidDetailException("EtcWriteRearTires is not valid");
			}
			break;
		case  "D_ETC_IND_VEH_CLASS" :
			isValid=genericValidation.doValidation(fMapping, nystaAetTxDto.getEtcIndVehClass());
			if(isValid==false && fMapping.getFileLevelRejection()!=null && fMapping.getFileLevelRejection().equals("Y")) {
				throw new InvalidDetailException("EtcIndVehClass is not valid");
			}
			break;
		case  "D_ETC_VALIDATION_STATUS" :
			isValid=genericValidation.doValidation(fMapping, nystaAetTxDto.getEtcValidationStatus());
			if(isValid==false && fMapping.getFileLevelRejection()!=null && fMapping.getFileLevelRejection().equals("Y")) {
				throw new InvalidDetailException("EtcValidationStatus is not valid");
			}
			break;
		case  "D_ETC_VIOL_OBSERVED" :
			isValid=genericValidation.doValidation(fMapping, nystaAetTxDto.getEtcViolObserved());
			if(isValid==false && fMapping.getFileLevelRejection()!=null && fMapping.getFileLevelRejection().equals("Y")) {
				throw new InvalidDetailException("EtcViolObserved is not valid");
			}
			break;
		case  "D_ETC_IMAGE_CAPTURED" :
			isValid=genericValidation.doValidation(fMapping, nystaAetTxDto.getEtcImageCaptured());
			if(isValid==false && fMapping.getFileLevelRejection()!=null && fMapping.getFileLevelRejection().equals("Y")) {
				throw new InvalidDetailException("EtcImageCaptured is not valid");
			}
			break;
		case  "D_ETC_REVENUE_TYPE" :
			isValid=genericValidation.doValidation(fMapping, nystaAetTxDto.getEtcRevenueType());
			if(isValid==false && fMapping.getFileLevelRejection()!=null && fMapping.getFileLevelRejection().equals("Y")) {
				throw new InvalidDetailException("EtcRevenueType is not valid");
			}
			break;
		case  "D_ETC_READ_AGENCY_DATA" :
			isValid=genericValidation.doValidation(fMapping, nystaAetTxDto.getEtcReadAgencyData());
			if(isValid==false && fMapping.getFileLevelRejection()!=null && fMapping.getFileLevelRejection().equals("Y")) {
				throw new InvalidDetailException("EtcReadAgencyData is not valid");
			}
			break;
		case  "D_ETC_WRITE_AGENCY_DATA" :
			isValid=genericValidation.doValidation(fMapping, nystaAetTxDto.getEtcWritAgencyData());
			if(isValid==false && fMapping.getFileLevelRejection()!=null && fMapping.getFileLevelRejection().equals("Y")) {
				throw new InvalidDetailException("EtcWritAgencyData is not valid");
			}
			break;
		case  "D_ETC_PRE_WRITE_TRX_NUM" :
			isValid=genericValidation.doValidation(fMapping, nystaAetTxDto.getEtcPreWritTrxNum());
			if(isValid==false && fMapping.getFileLevelRejection()!=null && fMapping.getFileLevelRejection().equals("Y")) {
				throw new InvalidDetailException("EtcPreWritTrxNum is not valid");
			}
			break;
		case  "D_ETC_POST_WRITE_TRX_NUM" :
			isValid=genericValidation.doValidation(fMapping, nystaAetTxDto.getEtcPostWritTrxNum());
			if(isValid==false && fMapping.getFileLevelRejection()!=null && fMapping.getFileLevelRejection().equals("Y")) {
				throw new InvalidDetailException("EtcPostWritTrxNum is not valid");
			}
			break;
		case  "D_ETC_IND_VEH_AXLES" :
			isValid=genericValidation.doValidation(fMapping, nystaAetTxDto.getEtcIndVehAxles());
			if(isValid==false && fMapping.getFileLevelRejection()!=null && fMapping.getFileLevelRejection().equals("Y")) {
				throw new InvalidDetailException("EtcIndVehAxles is not valid");
			}
			break;
		case  "D_ETC_IND_AXLE_OFFSET" :
			isValid=genericValidation.doValidation(fMapping, nystaAetTxDto.getEtcIndAxleOffset());
			if(isValid==false && fMapping.getFileLevelRejection()!=null && fMapping.getFileLevelRejection().equals("Y")) {
				throw new InvalidDetailException("EtcIndAxleOffset is not valid");
			}
			break;
		case  "D_ETC_VEH_SPEED" :
			isValid=genericValidation.doValidation(fMapping, nystaAetTxDto.getEtcVehSpeed());
			if(isValid==false && fMapping.getFileLevelRejection()!=null && fMapping.getFileLevelRejection().equals("Y")) {
				throw new InvalidDetailException("EtcVehSpeed is not valid");
			}
			break;
		case  "D_ETC_EXIT_DATE" :
			isValid=genericValidation.doValidation(fMapping, nystaAetTxDto.getEtcExitDate());
			if(isValid==false && fMapping.getFileLevelRejection()!=null && fMapping.getFileLevelRejection().equals("Y")) {
				throw new InvalidDetailException("EtcExitDate is not valid");
			}
			break;
		case  "D_ETC_EXIT_TIME" :
			isValid=genericValidation.doValidation(fMapping, nystaAetTxDto.getEtcExitTime());
			if(isValid==false && fMapping.getFileLevelRejection()!=null && fMapping.getFileLevelRejection().equals("Y")) {
				throw new InvalidDetailException("EtcExitTime is not valid");
			}
			break;
		case  "D_ETC_EXIT_PLAZA_ID" :
			isValid=genericValidation.doValidation(fMapping, nystaAetTxDto.getEtcExitPlazaId());
			if(isValid==false && fMapping.getFileLevelRejection()!=null && fMapping.getFileLevelRejection().equals("Y")) {
				throw new InvalidDetailException("EtcExitPlazaId is not valid");
			}
			break;
		case  "D_ETC_EXIT_LANE_ID" :
			isValid=genericValidation.doValidation(fMapping, nystaAetTxDto.getEtcExitLaneId());
			if(isValid==false && fMapping.getFileLevelRejection()!=null && fMapping.getFileLevelRejection().equals("Y")) {
				throw new InvalidDetailException("EtcExitLaneId is not valid");
			}
			break;
		case  "D_ETC_BUFFERED_FLAG" :
			isValid=genericValidation.doValidation(fMapping, nystaAetTxDto.getEtcBufferedFeed());
			if(isValid==false && fMapping.getFileLevelRejection()!=null && fMapping.getFileLevelRejection().equals("Y")) {
				throw new InvalidDetailException("EtcBufferedFeed is not valid");
			}
			break;
		case  "D_ETC_TXN_SRL_NUM" :
			isValid=genericValidation.doValidation(fMapping, nystaAetTxDto.getEtcTxnSrlNum());
			if(isValid==false && fMapping.getFileLevelRejection()!=null && fMapping.getFileLevelRejection().equals("Y")) {
				throw new InvalidDetailException("EtcTxnSrlNum is not valid");
			}
			break;
		case  "D_FROMT_OCR_PLATE_COUNTRY" :
			isValid=genericValidation.doValidation(fMapping, nystaAetTxDto.getFrontOcrPlateCountry());
			if(isValid==false && fMapping.getFileLevelRejection()!=null && fMapping.getFileLevelRejection().equals("Y")) {
				throw new InvalidDetailException("FrontOcrPlateCountry is not valid");
			}
			break;
		case  "D_FRONT_OCR_PLATE_STATE" :
			isValid=genericValidation.doValidation(fMapping, nystaAetTxDto.getFrontOcrPlateState());
			if(isValid==false && fMapping.getFileLevelRejection()!=null && fMapping.getFileLevelRejection().equals("Y")) {
				throw new InvalidDetailException("FrontOcrPlateState is not valid");
			}
			break;
		case  "D_FRONT_OCR_PLATE_TYPE" :
			isValid=genericValidation.doValidation(fMapping, nystaAetTxDto.getFrontOcrPlateType());
			if(isValid==false && fMapping.getFileLevelRejection()!=null && fMapping.getFileLevelRejection().equals("Y")) {
				throw new InvalidDetailException("FrontOcrPlateType is not valid");
			}
			break;
		case  "D_FRONT_OCR_PLATE_NUMBER" :
			isValid=genericValidation.doValidation(fMapping, nystaAetTxDto.getFrontOcrPlateNumber());
			if(isValid==false && fMapping.getFileLevelRejection()!=null && fMapping.getFileLevelRejection().equals("Y")) {
				throw new InvalidDetailException("FrontOcrPlateNumber is not valid");
			}
			break;
		case  "D_FRONT_OCR_CONFIDENCE" :
			isValid=genericValidation.doValidation(fMapping, nystaAetTxDto.getFrontOcrConfidence());
			if(isValid==false && fMapping.getFileLevelRejection()!=null && fMapping.getFileLevelRejection().equals("Y")) {
				throw new InvalidDetailException("FrontOcrConfidence is not valid");
			}
			break;
		case  "D_FRONT_OCR_IMAGE_INDEX" :
			isValid=genericValidation.doValidation(fMapping, nystaAetTxDto.getFrontOcrImageIndex());
			if(isValid==false && fMapping.getFileLevelRejection()!=null && fMapping.getFileLevelRejection().equals("Y")) {
				throw new InvalidDetailException("FrontOcrImageIndex is not valid");
			}
			break;
		case  "D_FRONT_IMGAE_COLOR" :
			isValid=genericValidation.doValidation(fMapping, nystaAetTxDto.getFrontImageColor());
			if(isValid==false && fMapping.getFileLevelRejection()!=null && fMapping.getFileLevelRejection().equals("Y")) {
				throw new InvalidDetailException("FrontImageColor is not valid");
			}
			break;
		case  "D_REAR_OCR_PLATE_COUNTRY" :
			isValid=genericValidation.doValidation(fMapping, nystaAetTxDto.getRearOcrPlateCountry());
			if(isValid==false && fMapping.getFileLevelRejection()!=null && fMapping.getFileLevelRejection().equals("Y")) {
				throw new InvalidDetailException("RearOcrPlateCountry is not valid");
			}
			break;
		case  "D_REAR_OCR_PLATE_STATE" :
			isValid=genericValidation.doValidation(fMapping, nystaAetTxDto.getRearOcrPlateState());
			if(isValid==false && fMapping.getFileLevelRejection()!=null && fMapping.getFileLevelRejection().equals("Y")) {
				throw new InvalidDetailException("RearOcrPlateState is not valid");
			}
			break;
		case  "D_REAR_OCR_PLATE_TYPE" :
			isValid=genericValidation.doValidation(fMapping, nystaAetTxDto.getRearOcrPlateType());
			if(isValid==false && fMapping.getFileLevelRejection()!=null && fMapping.getFileLevelRejection().equals("Y")) {
				throw new InvalidDetailException("RearOcrPlateType is not valid");
			}
			break;
		case  "D_REAR_OCR_PLATE_NUMBER" :
			isValid=genericValidation.doValidation(fMapping, nystaAetTxDto.getRearOcrPlateNumber());
			if(isValid==false && fMapping.getFileLevelRejection()!=null && fMapping.getFileLevelRejection().equals("Y")) {
				throw new InvalidDetailException("RearOcrPlateNumber is not valid");
			}
			break;
		case  "D_REAR_OCR_CONFIDENCE" :
			isValid=genericValidation.doValidation(fMapping, nystaAetTxDto.getRearOcrConfidence());
			if(isValid==false && fMapping.getFileLevelRejection()!=null && fMapping.getFileLevelRejection().equals("Y")) {
				throw new InvalidDetailException("RearOcrConfidence is not valid");
			}
			break;
		case  "D_REAR_OCR_IMGAE_INDEX" :
			isValid=genericValidation.doValidation(fMapping, nystaAetTxDto.getRearOcrImageIndex());
			if(isValid==false && fMapping.getFileLevelRejection()!=null && fMapping.getFileLevelRejection().equals("Y")) {
				throw new InvalidDetailException("RearOcrImageIndex is not valid");
			}
			break;
		case  "D_REAR_IMAGE_COLOR" :
			isValid=genericValidation.doValidation(fMapping, nystaAetTxDto.getRearImageColor());
			if(isValid==false && fMapping.getFileLevelRejection()!=null && fMapping.getFileLevelRejection().equals("Y")) {
				throw new InvalidDetailException("RearImageColor is not valid");
			}
			break;
		case  "D_ETC_RECORD_TERM" :
			isValid=genericValidation.doValidation(fMapping, nystaAetTxDto.getEtcRecordTerm());
			if(isValid==false && fMapping.getFileLevelRejection()!=null && fMapping.getFileLevelRejection().equals("Y")) {
				throw new InvalidDetailException("EtcRecordTerm is not valid");
			}
		default:
		}
		return isValid;
	}
	/*	
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

	public void finishFileProcess() throws CustomException
	{

	}

	 */


}
