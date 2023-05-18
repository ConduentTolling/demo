package com.conduent.tpms.parking.controller;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.conduent.tpms.parking.intxService.LoadAllINTXService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
public class LoadAllINTXProcessController {

	private static final Logger logger = LoggerFactory.getLogger(LoadAllINTXProcessController.class);

	@Autowired
	ApplicationContext applicationContext;
	
	@Autowired 
	private RestTemplate restTemplate;
	
	@Value("${healthcheck.connect.url}")
	private String healthCheckURL;
	
	
	@GetMapping("/loadAllParkingfiles")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Job initiated successfully",response = String.class )})
    @ApiOperation(value="LOAD_ALL_INTX_SCHEDULER Process")
	public String loadAllfiles(@RequestParam String fileType) throws IOException, InterruptedException 
	{
		logger.info("Retrived file type :"+fileType);
		LoadAllINTXService loadAllINTXService = applicationContext.getBean(LoadAllINTXService.class);
		logger.info("in CTRL");
		loadAllINTXService.loadAllFiles(fileType);

		return "Job initiated successfully";
	}
	
	
	@GetMapping("/testService")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success",response = String.class )})
    @ApiOperation(value="Test Service Running..")
	public String test() {

	return "Success";



	}

	
	@GetMapping("/nystaHealthCheck")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK",response = String.class )})
	@ApiOperation(value="Service status check, returns a string response if LOAD_ALL_INTX_SCHEDULER service is running.")
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
