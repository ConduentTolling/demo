package com.conduent.tpms.process25a.controller;


import java.io.IOException;
import java.time.format.DateTimeFormatter;

import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.conduent.app.timezone.utility.TimeZoneConv;
import com.conduent.tpms.process25a.service.Process25AService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ApiResponse;

@RestController
public class Process25AController {

	private static final Logger logger = LoggerFactory.getLogger(Process25AController.class);

	
	/*
	 * @Autowired IcrxFileParserImpl genericIITCFileParserImpl;
	 * 
	 * @Autowired TQatpMappingDaoImpl tQatpMappingDaoImpl;
	 */
	
	@Autowired
	ApplicationContext applicationContext;
	
	@Autowired
	TimeZoneConv timeZoneConv;
	
	
	@GetMapping("/api/process/25A")
	@ApiOperation("Creates reconcilliation files")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK",response = String.class )})
	public String  loadICRXFile() throws IOException, InterruptedException 
	{
		logger.info("Time as per timezone:{}",timeZoneConv.currentTime());
		logger.info("Date as per timezone:{}",timeZoneConv.currentDate());
		MDC.put("logFileName", "PROCESS_25A_".concat(timeZoneConv.currentTime().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"))));
        logger.info("logFileName: {}",MDC.get("logFileName"));
		Process25AService process25AService = applicationContext.getBean(Process25AService.class);
		logger.info("Instance :", process25AService);
		logger.info("in CTRL"+Thread.currentThread().getName());
	
		return process25AService.processTransactions();
	}
	
	
	
	@GetMapping("/api/healthCheck/25A")
	public String healthCheck() throws IOException {
		logger.info("Health check get URL triggered.");
		return "25A service is running..";
	}
	
}
