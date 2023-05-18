package com.conduent.tpms.iag.controller;


import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.context.ApplicationContext;

import com.conduent.app.timezone.utility.TimeZoneConv;
import com.conduent.tpms.iag.dao.impl.TQatpMappingDaoImpl;
import com.conduent.tpms.iag.service.IITCService;
import com.conduent.tpms.iag.validation.InvalidTagFileParserImpl;

@RestController
public class IITCProcessController {

	private static final Logger logger = LoggerFactory.getLogger(IITCProcessController.class);

	@Autowired
	 private ApplicationContext applicationContext;
	
	@Autowired
	TimeZoneConv timeZoneConv;
	
	
	@PostMapping("/loadfile/iitc")
	public void  loadIITCFile(@RequestBody File file) throws IOException, InterruptedException 
	{
		logger.info("Time as per timezone:{}",timeZoneConv.currentTime());
		MDC.put("logFileName", "LOAD_IITC_".concat(timeZoneConv.currentTime().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"))));
		logger.info("logFileName: {}",MDC.get("logFileName"));
		IITCService iitcService = applicationContext.getBean(IITCService.class);
		logger.info("Instance :{}", iitcService);
		logger.info("in CTRL"+Thread.currentThread().getName());
	
		iitcService.processFiles(file);
	}
	
	@GetMapping("/healthCheck/iitc")
	public String healthCheck() throws IOException {
		logger.info("Health check get URL triggered.");
		return "IITC service is running....";
	}
	
	@PostMapping("/upload")
	public void uploadProcessCsv() {
		try {
			System.out.println("upload url triggered");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
