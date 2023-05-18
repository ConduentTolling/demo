package com.conduent.tpms.qatp.controller;

import java.io.IOException;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.conduent.app.timezone.utility.TimeZoneConv;
import com.conduent.tpms.qatp.parser.agency.FOTrnxFixlengthParser;
import com.conduent.tpms.qatp.service.QatpService;
import com.conduent.tpms.qatp.utility.MasterDataCache;
import com.google.common.base.Stopwatch;

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
	MasterDataCache masterDataCache;
	
	@Autowired
	TimeZoneConv timeZoneConv;
	
	@Autowired
	FOTrnxFixlengthParser nystaFixlengthParser;
	
	@Autowired 
	private RestTemplate restTemplate;
	
	@Value("${healthcheck.connect.url}")
	private String healthCheckURL;

	
	@GetMapping("/process_trnx_file")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK",response = String.class )})
    @ApiOperation(value="Process Parking Files")
	public String processFile(@RequestParam String fileType) throws IOException {
		try 
		{
			if(fileType.equals("FNTX") || fileType.equals("INTX"))
			{
				MDC.put("logFileName", "PARSING_"+fileType+"_".concat(timeZoneConv.currentTime().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"))));
	            logger.info("logFileName: {}",MDC.get("logFileName"));
	            
				qatpService.process(fileType);
			}
			else
			{
				logger.info("Invalid File Type {}",fileType);
			}
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			return e.getMessage();
		}
		return "Job excuted successfully";

	}
	

	@GetMapping("/process_fntx_trnx_file_parallel")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK",response = String.class )})
    @ApiOperation(value="Process Parking Files")
	public String processFoTrnxFileParallel(@RequestParam String fileName) throws IOException 
	{ 
		MDC.put("logFileName", "PARSING_FNTX_".concat(timeZoneConv.currentTime().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"))));
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

	@GetMapping("/processFNTXFiles")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK",response = String.class )})
    @ApiOperation(value="Process Parking Files")
	public String loadAllFOTrnxFiles() throws IOException
	{
		
		MDC.put("logFileName", "PARSING_FNTX_".concat(timeZoneConv.currentTime().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"))));
        logger.info("logFileName: {}",MDC.get("logFileName"));
        
		Stopwatch stopwatch = Stopwatch.createStarted();
		
		List<String> result = nystaFixlengthParser.process();
		logger.info("Result: {}",result.toString());
		
		stopwatch.stop();
		long millis = stopwatch.elapsed(TimeUnit.MILLISECONDS);
		logger.info("Total time taken to process all FO files in source directory {}",millis);
		
		return "Job initiated successfully";
	}

	
	@GetMapping("/FOTrnxhealthCheck")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK",response = String.class )})
    @ApiOperation(value="Health Check for Parking")
	public String healthCheck() throws IOException 
	{
		Optional<String> podName = Optional.ofNullable(System.getenv("HOSTNAME"));
		logger.debug("Pod Name is {}",podName);
		logger.info("Health check get URL triggered on podName {}",podName);
		return "FO Trnx service is running on "+podName;

	}
	
	
	@GetMapping("/testCache/{id}")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK",response = String.class )})
    @ApiOperation(value="Health Check for Parking")
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

	
	@GetMapping("/FOHealthCheck")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK",response = String.class )})
    @ApiOperation(value="Health Check for Parking")
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
	

}