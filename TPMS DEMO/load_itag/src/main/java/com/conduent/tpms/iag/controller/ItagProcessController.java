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
import com.conduent.tpms.iag.service.ITAGService;

@RestController
public class ItagProcessController {

	private static final Logger logger = LoggerFactory.getLogger(ItagProcessController.class);

	@Autowired
	private ApplicationContext applicationContext;
	
	@Autowired
	TimeZoneConv timeZoneConv;
	
	@PostMapping("/loadfile/itag")
	public void  loadITAGFile(@RequestBody File file, @RequestParam("agencySequence") int agencySequence) throws IOException, InterruptedException 
	{
		MDC.put("logFileName", "LOAD_ITAG_".concat(timeZoneConv.currentTime().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"))));
		logger.info("logFileName: {}",MDC.get("logFileName"));
		
		ITAGService itagService = applicationContext.getBean(ITAGService.class);
		logger.info("Instance :", itagService);
		logger.info("in CTRL"+Thread.currentThread().getName());
		logger.info("File {} agencySequence : {}", file.getName(), agencySequence);
	
		itagService.processFiles(file, agencySequence);
	}
	
	@GetMapping("/healthCheck/itag")
	public String healthCheck() throws IOException {
		logger.info("Health check get URL triggered.");
		return "ITAG service is running..";
	}
	
}
