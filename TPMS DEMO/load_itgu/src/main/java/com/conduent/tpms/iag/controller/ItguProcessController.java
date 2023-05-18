package com.conduent.tpms.iag.controller;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.conduent.app.timezone.utility.TimeZoneConv;
import com.conduent.tpms.iag.service.ITGUService;

@RestController
public class ItguProcessController {

	private static final Logger logger = LoggerFactory.getLogger(ItguProcessController.class);

	@Autowired
	private ApplicationContext applicationContext;
	
	@Autowired
	TimeZoneConv timeZoneConv;
	
	@PostMapping("/loadfile/itgu")
	public void  loadITGUFile(@RequestBody File file) throws IOException, InterruptedException 
	{
		MDC.put("logFileName", "LOAD_ITGU_".concat(timeZoneConv.currentTime().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"))));
		logger.info("logFileName: {}",MDC.get("logFileName"));
		
		ITGUService itguService = applicationContext.getBean(ITGUService.class);
		logger.info("Instance :", itguService);
		logger.info("in CTRL"+Thread.currentThread().getName());
		logger.info("File {}", file.getName());
	
		itguService.processFiles(file);
	}
	
	@GetMapping("/healthCheck/itag")
	public String healthCheck() throws IOException {
		logger.info("Health check get URL triggered.");
		return "ITGU service is running..";
	}
	
}
