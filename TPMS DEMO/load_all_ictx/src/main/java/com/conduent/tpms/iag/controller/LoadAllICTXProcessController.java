package com.conduent.tpms.iag.controller;


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

import com.conduent.tpms.ictx.service.LoadAllICTXService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
public class LoadAllICTXProcessController {

	private static final Logger logger = LoggerFactory.getLogger(LoadAllICTXProcessController.class);

	@Autowired
	ApplicationContext applicationContext;
	
	@Autowired 
	private RestTemplate restTemplate;
	
	@Value("${healthcheck.connect.url}")
	private String healthCheckURL;
	
	
	@GetMapping("/loadAllIAGfiles")
	@ApiOperation("Loads All Iag Files")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Job initiated successfully",response = String.class )})
	public String loadAllfiles(@RequestParam @ApiParam(name = "fileType", value = "File Type", example = "ICTX") String fileType) throws IOException, InterruptedException 
	{
		logger.info("Retrived file type :"+fileType);
		LoadAllICTXService loadAllICTXService = applicationContext.getBean(LoadAllICTXService.class);
		logger.info("in CTRL");
		loadAllICTXService.loadAllFiles(fileType);

		return "Job initiated successfully";
	}
	
	
	@GetMapping("/testService")
	@ApiOperation("Loads All Iag Files")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Success",response = String.class )})
	public String test() {

	return "Success";



	}

	
	@GetMapping("/nystaHealthCheck")
	@ApiOperation("Nysta Health Check")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Job executed successfully : ",response = String.class )})
	public String loadAllNystaFiles(@RequestParam @ApiParam(name = "request", value = "No. of Requests", example = "1") int request) throws IOException
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
