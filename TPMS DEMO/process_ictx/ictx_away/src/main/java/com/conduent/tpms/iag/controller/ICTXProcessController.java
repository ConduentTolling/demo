package com.conduent.tpms.iag.controller;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.conduent.app.timezone.utility.TimeZoneConv;
import com.conduent.tpms.iag.dto.AccountApiInfoDto;
import com.conduent.tpms.iag.dto.ClassifiactionQueueDto;
import com.conduent.tpms.iag.service.ITXCService;
import com.conduent.tpms.iag.service.IctxService;
import com.conduent.tpms.iag.utility.LocalDateAdapter;
import com.conduent.tpms.iag.utility.LocalDateTimeAdapter;
import com.conduent.tpms.iag.utility.MessagePublisher;
import com.conduent.tpms.iag.validation.IctxFileParserImpl;
import com.google.common.base.Stopwatch;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@RestController
public class ICTXProcessController {

	private static final Logger logger = LoggerFactory.getLogger(ICTXProcessController.class);

	/*
	 * @Autowired IctxFileParserImpl genericICTXFileParserImpl;
	 * 
	 * @Autowired ItxcFileParserImpl genericITXCFileParserImpl;
	 * 
	 * @Autowired TQatpMappingDaoImpl tQatpMappingDaoImpl;
	 */
	@Autowired
	private ApplicationContext applicationContext;
	@Autowired
	TimeZoneConv timeZoneConv;
	
	/*
	 * @GetMapping("/loadfile/ictx") public String loadICTXfile() throws
	 * IOException, InterruptedException {
	 * genericICTXFileParserImpl.fileParseTemplate(); return
	 * "Successfully Uploaded file";
	 * 
	 * }
	 * 
	 * @GetMapping("/loadfile/itxc") public String loadITXCfile() throws
	 * IOException, InterruptedException {
	 * genericITXCFileParserImpl.fileParseTemplateITXC(); return
	 * "Successfully Uploaded file";
	 * 
	 * }
	 */

	@PostMapping("/loadfile/ictx")
	public void loadAllfilesICTX(@RequestBody File file) throws IOException, InterruptedException {
//		logger.info("TimeZone{}",timeZoneConv.currentTime());
		MDC.put("logFileName", "ICTX_AWAY_".concat(timeZoneConv.currentTime().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"))));
        logger.info("logFileName: {}",MDC.get("logFileName"));
		IctxService loadAllICTXService = applicationContext.getBean(IctxService.class);
		logger.info("Instance :", loadAllICTXService);
		Stopwatch stopwatch = Stopwatch.createStarted();

		loadAllICTXService.processFiles(file);
		stopwatch.stop();
		long millis = stopwatch.elapsed(TimeUnit.MILLISECONDS);
		logger.info("Total time taken to execute loadAllfilesICTX {}",millis);
	}
	
	@PostMapping("/loadfile/itxc")
	public void loadAllfilesITXC(@RequestBody File file) throws IOException, InterruptedException {
		MDC.put("logFileName", "ICTX_AWAY_".concat(timeZoneConv.currentTime().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"))));
        logger.info("logFileName: {}",MDC.get("logFileName"));
		ITXCService loadAllITXCService = applicationContext.getBean(ITXCService.class);
		//ITXCService loadAllITXCService = applicationContext.getBean(ITXCService.class);
		logger.info("Instance :", loadAllITXCService);

		loadAllITXCService.processFiles(file);

	}

	@GetMapping("/healthCheck/ictx")
	public String healthCheck() throws IOException {
		logger.info("Health check get URL triggered.");
		return "ICTX service is running...";
	}

	@PostMapping("/upload")
	public void uploadProcessCsv() {
		try {
			System.out.println("upload url triggered");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Autowired
	IctxFileParserImpl fileParserImpl;


	@GetMapping("/push_msg_to_classification_queue/")
	public String pushMessageToOSS() {
		try {
			ClassifiactionQueueDto classifiactionQueueDto = new ClassifiactionQueueDto();
			classifiactionQueueDto.setLaneTxId(Long.valueOf(239));
			classifiactionQueueDto.setTxTypeInd("R");
			classifiactionQueueDto.setTxSubType("C");
			classifiactionQueueDto.setTollSystemType("B");
			classifiactionQueueDto.setTollRevenueType(1);
			classifiactionQueueDto.setEntryTxTimeStamp(LocalDateTime.now());
			classifiactionQueueDto.setEntryPlazaId(72);
			classifiactionQueueDto.setEntryDataSource("*");
			classifiactionQueueDto.setPlazaId(72);
			classifiactionQueueDto.setLaneMode(9);
			classifiactionQueueDto.setDeviceNo("00408694551");
			Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
					.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
					.excludeFieldsWithoutExposeAnnotation().create();
			byte[] msg = gson.toJson(classifiactionQueueDto).getBytes();
			if (MessagePublisher.publishMessage(classifiactionQueueDto.getLaneTxId().toString(), msg)) {
				return "Message pushed sucessfully";
			} else {
				return "Message not pushed sucessfully";
			}
		} catch (Exception ex) {
			return ex.getMessage();
		}
	}
}