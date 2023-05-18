package com.conduent.tpms.qatp.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.conduent.app.timezone.utility.TimeZoneConv;
import com.conduent.tpms.qatp.dto.ClassifiactionQueueDto;
import com.conduent.tpms.qatp.parser.agency.PbaFixlengthParser;
import com.conduent.tpms.qatp.service.QatpService;
import com.conduent.tpms.qatp.utility.LocalDateAdapter;
import com.conduent.tpms.qatp.utility.LocalDateTimeAdapter;
import com.conduent.tpms.qatp.utility.MessagePublisher;
import com.google.common.base.Stopwatch;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api")
public class QatpController 
{
	
	private static final Logger logger = LoggerFactory.getLogger(QatpController.class);

	@Autowired
	QatpService qatpService;

	@Autowired
	private MessagePublisher messagePublisher;
	
	@Autowired
	TimeZoneConv timeZoneConv;
	
	@Autowired 
	private RestTemplate restTemplate;
	
	@Autowired
	PbaFixlengthParser pbaFixlengthParser;
	
	@Value("${healthcheck.connect.url}")
	private String healthCheckURL;
	
	public Integer agencyId;

	@GetMapping("/pba_start")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK",response = String.class )})
    @ApiOperation(value="Processing PBA Files")
	public String processPbaFile() throws IOException 
	{
		try 
		{
			MDC.put("logFileName", "PBA_PARSING_".concat(timeZoneConv.currentTime().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"))));
            logger.info("logFileName: {}",MDC.get("logFileName"));
            
			logger.info("Time as per timezone:{}",timeZoneConv.currentTime());
			logger.info("Date as per timezone:{}",timeZoneConv.currentDate());
			
			agencyId=19;
			
			//main process
			qatpService.process(agencyId);
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			return e.getMessage();
		}
		return "Job excuted successfully";

	}
	
	@GetMapping("/tiba_start")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK",response = String.class )})
    @ApiOperation(value="Processing TIBA Files")
	public String processTibaFile() throws IOException 
	{
		try 
		{
			MDC.put("logFileName", "TIBA_PARSING_".concat(timeZoneConv.currentTime().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"))));
            logger.info("logFileName: {}",MDC.get("logFileName"));
            
			logger.info("Time as per timezone:{}",timeZoneConv.currentTime());
			logger.info("Date as per timezone:{}",timeZoneConv.currentDate());
			
			agencyId=63;
			
			//main process
			qatpService.process(agencyId);
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			return e.getMessage();
		}
		return "Job excuted successfully";

	}
	
	@GetMapping("/parallel_pba_process_trnx_file")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK",response = String.class )})
    @ApiOperation(value="Processing PBA Files")
	public String processFile1(@RequestParam String fileName) throws IOException 
	{ 
		
		MDC.put("logFileName", "PBA_PARSING_".concat(timeZoneConv.currentTime().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"))));
        logger.info("logFileName: {}",MDC.get("logFileName"));
        
		Stopwatch stopwatch = Stopwatch.createStarted();
		Optional<String> podName = Optional.ofNullable(System.getenv("HOSTNAME"));
		logger.debug("Pod Name is {}",podName);
			try 
			{
				qatpService.parallelProcess(fileName);
			}
			catch (Exception e) 
			{
				e.printStackTrace();
				return e.getMessage();
			}
			
		stopwatch.stop();
		long millis = stopwatch.elapsed(TimeUnit.MILLISECONDS);
		logger.info("Total time taken to process one file {} is {}",fileName,millis);
		
		return "Job excuted successfully on pod " +podName+ " for file "+fileName+ " in "+millis+" ms";

	}

	@GetMapping("/processPbaFiles")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK",response = String.class )})
    @ApiOperation(value="Processing PBA Files")
	public String loadAllNystaFiles() throws IOException
	{
		
		MDC.put("logFileName", "PBA_PARSING_".concat(timeZoneConv.currentTime().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"))));
        logger.info("logFileName: {}",MDC.get("logFileName"));
        
		Stopwatch stopwatch = Stopwatch.createStarted();
		
		List<String> result = pbaFixlengthParser.process();
		logger.info("Result: {}",result.toString());
		
		stopwatch.stop();
		long millis = stopwatch.elapsed(TimeUnit.MILLISECONDS);
		logger.info("Total time taken to process all files in source directory {}",millis);
		
		return "Job initiated successfully";
	}

	
	@GetMapping("/healthCheckPba")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK",response = String.class )})
    @ApiOperation(value="Processing PBA Files")
	public String healthCheck() throws IOException 
	{
		Optional<String> podName = Optional.ofNullable(System.getenv("HOSTNAME"));
		logger.debug("Pod Name is {}",podName);
		logger.info("Health check get URL triggered on podName {}",podName);
		return "PBA parser service is running on "+podName;

	}
	
	@GetMapping("/pbaHealthCheck")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK",response = String.class )})
    @ApiOperation(value="Health Check API for PBA Parsing")
	public String loadAllNystaFiles(@RequestParam int request) throws IOException
	{
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		//System.out.println("#### SessionID = " + (null!=request.getSession()?request.getSession().getId():"NULL"));
		HttpEntity<String> entity = new HttpEntity<String>(headers);
		List<String> info = new ArrayList<String>();
		
		for(int i = 0 ; i<=request ; i ++)
		{
			ResponseEntity<String> responseEntity = restTemplate.exchange(healthCheckURL, HttpMethod.GET, entity,String.class);
			info.add(responseEntity.getBody());
		}
		
		return "Job executed successfully : "+info;
	}

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
			if (messagePublisher.publishMessage(classifiactionQueueDto.getLaneTxId().toString(), msg)) {
				return "Message pushed sucessfully";
			} else {
				return "Message not pushed sucessfully";
			}
		} catch (Exception ex) {
			return ex.getMessage();
		}

	}
}
