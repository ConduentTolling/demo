package com.conduent.tpms.qeval.controller;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.conduent.tpms.qeval.service.QevaluationService;
import com.google.common.base.Stopwatch;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ApiResponse;

/**
 * Controller class
 * 
 * @author Urvashic
 *
 */

@RestController
public class ProcessController {

	private static final Logger logger = LoggerFactory.getLogger(ProcessController.class);

	
/*	@Autowired
	private ApplicationContext applicationContext; */
	
	@Autowired
	private QevaluationService qevaluationService;
	
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

	@GetMapping("/qevaluation/process")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Process completed",response = String.class )})
    @ApiOperation(value="Q-evaluation Process")
	public String executeQeval() throws IOException, InterruptedException {
//		logger.info("TimeZone{}",timeZoneConv.currentTime());
		//IctxService loadAllICTXService = applicationContext.getBean(IctxService.class);
		Stopwatch stopwatch = Stopwatch.createStarted();

		stopwatch.stop();
		long millis = stopwatch.elapsed(TimeUnit.MILLISECONDS);
		qevaluationService.process();
		logger.info("Total time taken to execute qevaluationService {}",millis);
		return "Process completed in "+millis+" ms.";
	}
	
	@GetMapping("/qevaluation/healthCheck")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK",response = String.class )})
	@ApiOperation(value="Service status check, returns a string response if QUARTERLY EVALUATION service is running.")
	public String healthCheck() throws IOException {
		logger.info("Service check URL triggered.");
		return "QUARTERLY EVALUATION service is running...";
	}


/*	@GetMapping("/push_msg_to_classification_queue/")
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
	} */
}