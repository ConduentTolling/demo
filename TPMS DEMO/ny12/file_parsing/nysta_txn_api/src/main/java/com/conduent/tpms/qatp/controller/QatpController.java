package com.conduent.tpms.qatp.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.conduent.tpms.qatp.exception.InvalidDetailException;
import com.conduent.app.timezone.utility.TimeZoneConv;
import com.conduent.tpms.qatp.dto.ClassifiactionQueueDto;
import com.conduent.tpms.qatp.service.QatpService;
import com.conduent.tpms.qatp.utility.LocalDateAdapter;
import com.conduent.tpms.qatp.utility.LocalDateTimeAdapter;
import com.conduent.tpms.qatp.utility.MasterDataCache;
import com.conduent.tpms.qatp.utility.MessagePublisher;
import com.conduent.tpms.qatp.utility.NystaRestTemplate;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ApiResponse;

@RestController
@RequestMapping("/api")
public class QatpController 
{
	private static final Logger logger = LoggerFactory.getLogger(QatpController.class);

	@Autowired
	QatpService qatpService;

	@Autowired
	MasterDataCache masterDataCache;
	
	@Autowired
	private MessagePublisher messagePublisher;
	
	@Autowired
	TimeZoneConv timeZoneConv;
	
	@Autowired
	NystaRestTemplate nystaRestTemplate;

	
	/*
	 * API for nysta_trnx_api  
	 * 
	 */
	@GetMapping("/nysta_trnx_api")
	@RequestMapping(value = "/nysta_trnx_api", method = RequestMethod.GET)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK",response = String.class )})
    @ApiOperation(value="API call for nysta-trnx-api service.")
	public String processFile1() throws IOException {
		try 
		{
			MDC.put("logFileName", "NYSTA_TXN_API_".concat(timeZoneConv.currentTime().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"))));
	        logger.info("logFileName: {}",MDC.get("logFileName"));
			logger.info("Time as per timezone:{}",timeZoneConv.currentTime());
			nystaRestTemplate.callgetNystaTxnAPI();
		}
		catch (Exception ex) 
		{
			ex.printStackTrace();
			
			if(ex instanceof InvalidDetailException)
			{
				logger.info("Record is not validate.. ",ex);
			}
			return ex.getMessage();
		}
		return "Job excuted successfully";

	}
	
//	@GetMapping("/nysta_process_trnx_file")
//	public String processFile() throws IOException {
//		try 
//		{
//			//logger.info("Time as per timezone:{}",timeZoneConv.currentTime());
//			//logger.info("Date as per timezone:{}",timeZoneConv.currentDate());
//			MDC.put("logFileName", "NYSTA_TXN_API_".concat(timeZoneConv.currentTime().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"))));
//	        logger.info("logFileName: {}",MDC.get("logFileName"));
//			qatpService.process();
//		}
//		catch (Exception e) 
//		{
//			e.printStackTrace();
//			return e.getMessage();
//		}
//		return "Job excuted successfully";
//
//	}

	@GetMapping("/healthCheck")
	public String healthCheck() throws IOException 
	{
		logger.info("Health check get URL triggered");
		return "Nysta parser service is running";

	}

	//@GetMapping("/testCache/{id}")
	public String healthCheck(@PathVariable Integer id)
	{
		if(masterDataCache.isValidPlaza(id))
		{
			return "Lane Present";
		}
		else
		{
			return "Lane not present";
		}
	}

	//@GetMapping("/push_msg_to_classification_queue/")
	public String pushMessageToOSS()
	{
		try
		{
			ClassifiactionQueueDto classifiactionQueueDto=new ClassifiactionQueueDto();
			classifiactionQueueDto.setLaneTxId(Long.valueOf(239));
			classifiactionQueueDto.setTxTypeInd("R");
			classifiactionQueueDto.setTxSubType("C");
			classifiactionQueueDto.setTollSystemType("B");
			classifiactionQueueDto.setTollRevenueType(1);
			classifiactionQueueDto.setEntryTxTimeStamp(OffsetDateTime.now());
			classifiactionQueueDto.setEntryPlazaId(72);
			classifiactionQueueDto.setEntryDataSource("*");
			classifiactionQueueDto.setPlazaId(72);
			classifiactionQueueDto.setLaneMode(9);
			classifiactionQueueDto.setDeviceNo("00408694551");
			Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
					.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
					.excludeFieldsWithoutExposeAnnotation().create();
			byte[] msg = gson.toJson(classifiactionQueueDto).getBytes();
			if (messagePublisher.publishMessage(classifiactionQueueDto.getLaneTxId().toString(), msg)) 
			{ 
				return "Message pushed sucessfully";
			}
			else
			{
				return "Message not pushed sucessfully";
			}
		}
		catch(Exception ex)
		{
			return ex.getMessage();
		}
	}
	
	
	
	@GetMapping("/demo")
	 @ApiOperation(value="API call for testing purpose.")
	public String demo() throws IOException 
	{
		logger.info("Health check get URL triggered");
		//return "{\"laneTxId\":20000000577,\"txExternRefNo\":\"0000026619077601\",\"txType\":\"E\",\"txSubType\":\"Z\",\"tollSystemType\":\"C\",\"tollRevenueType\":1,\"entryTxTimeStamp\":\"2022-02-14T12:05:12.065-05:00\",\"entryPlazaId\":8,\"entryLaneId\":77,\"entryTxSeqNumber\":0,\"entryDataSource\":\"T\",\"entryVehicleSpeed\":0,\"plazaAgencyId\":1,\"plazaId\":15,\"laneId\":101,\"externPlazaId\":\"20W\",\"externLaneId\":\"07E\",\"txDate\":\"2022-02-14\",\"txTimeStamp\":\"2022-02-14T12:15:12.065-05:00\",\"laneMode\":9,\"laneState\":0,\"laneSn\":14001,\"deviceNo\":\"00400012029\",\"tagAxles\":2,\"actualClass\":22,\"actualAxles\":0,\"extraAxles\":0,\"plateType\":0,\"readPerf\":26,\"writePerf\":0,\"progStat\":0,\"collectorNumber\":\"78999\",\"imageCaptured\":\"F\",\"vehicleSpeed\":11,\"speedViolation\":0,\"reciprocityTrx\":\"F\",\"isViolation\":\"0\",\"isLprEnabled\":\"F\",\"fullFareAmount\":0.0,\"discountedAmount\":0.0,\"unrealizedAmount\":0.0,\"extFileId\":12345,\"receivedDate\":\"2022-02-14\",\"dstFlag\":\"F\",\"isPeak\":\"F\",\"fareType\":0,\"laneDataSource\":\"E\",\"laneType\":0,\"laneHealth\":0,\"avcAxles\":2,\"tourSegment\":0,\"bufRead\":\"T\",\"tollAmount\":0.0,\"debitCredit\":\"+\",\"etcValidStatus\":1,\"discountedAmount2\":0.0,\"videoFareAmount\":0.0,\"atpFileId\":0,\"avcClass\":22,\"tagIagClass\":0,\"tagClass\":22}";
		return "{\"etcTrxType\":\"C\", \"etcEntryDate\":\"20220224\", \"etcEntryTime\":\"13051265\", \"etcEntryPlazaId\":\"16W\", \"etcEntryLaneId\":\"01E\", \"etcEntryDataSource\":\"T\", \"etcAppId\":\"1\", \"etcType\":\"0\", \"etcGroupId\":\"065\", \"etcAgencyId\":\"004\", \"etcTagSerialNum\":\"00012029\", \"etcSerialNumber\":\"14001\", \"etcReadPerformance\":\"26\", \"etcLaneMode\":\"9\", \"etcCollectorId\":\"78999\", \"etcDerivedVehClass\":\"2L\", \"etcReadVehType\":\"01\", \"etcReadVehAxles\":\"02\", \"etcReadVehWeight\":\"0\", \"etcReadRearTires\":\"0\", \"etcWriteVehType\":\"00\", \"etcWriteVehAxles\":\"00\", \"etcWriteVehWeight\":\"0\", \"etcWriteRearTires\":\"0\", \"etcIndVehClass\":\"2L\", \"etcValidationStatus\":\"1\", \"etcViolObserved\":\"0\", \"etcImageCaptured\":\"0\", \"etcRevenueType\":\"00\", \"etcReadAgencyData\":\"***********\", \"etcWritAgencyData\":\"********\", \"etcPreWritTrxNum\":\"*****\", \"etcPostWritTrxNum\":\"*****\", \"etcIndVehAxles\":\"0\", \"etcIndAxleOffset\":\"+0\", \"etcVehSpeed\":\"11\", \"etcExitDate\":\"20220224\", \"etcExitTime\":\"13151265\", \"etcExitPlazaId\":\"20W\", \"etcExitLaneId\":\"07E\", \"etcBufferedFeed\":\"R\", \"etcTxnSrlNum\":\"026619077601\", \"frontOcrPlateCountry\":\"000\", \"frontOcrPlateState\":\"000\", \"frontOcrPlateType\":\"000\", \"frontOcrPlateNumber\":\"001\", \"frontOcrConfidence\":\"000\", \"frontOcrImageIndex\":\"00\", \"frontImageColor\":\"B\", \"rearOcrPlateCountry\":\"002\", \"rearOcrPlateState\":\"AB\", \"rearOcrPlateType\":\"000\", \"rearOcrPlateNumber\":\"KA03MK1801\", \"rearOcrConfidence\":\"000\", \"rearOcrImageIndex\":\"00\", \"rearImageColor\":\"B\"}";
	}
}
