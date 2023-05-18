package com.conduent.tpms.inrx.controller;


import java.io.File;
import java.io.IOException;
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
import com.conduent.tpms.inrx.dao.impl.TQatpMappingDaoImpl;
import com.conduent.tpms.inrx.service.IRXNService;
import com.conduent.tpms.inrx.service.INRXService;
import com.conduent.tpms.inrx.validation.InrxFileParserImpl;

@RestController
public class INRXProcessController {

	private static final Logger logger = LoggerFactory.getLogger(INRXProcessController.class);

	
	@Autowired
	InrxFileParserImpl genericIITCFileParserImpl;
	
	@Autowired
	TQatpMappingDaoImpl tQatpMappingDaoImpl;
	
	@Autowired
	ApplicationContext applicationContext;
	
	@Autowired
	TimeZoneConv timeZoneConv;
	
	
	@PostMapping("/loadfile/inrx")
	public void  loadINRXFile(@RequestBody File file) throws IOException, InterruptedException 
	{
		logger.info("Time as per timezone:{}",timeZoneConv.currentTime());
		logger.info("Date as per timezone:{}",timeZoneConv.currentDate());
		MDC.put("logFileName", "PROCESS_INRX_".concat(timeZoneConv.currentTime().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"))));
        logger.info("logFileName: {}",MDC.get("logFileName"));
		INRXService inrxService = applicationContext.getBean(INRXService.class);
		logger.info("Instance :", inrxService);
		logger.info("in CTRL"+Thread.currentThread().getName());
		
	
		inrxService.processFiles(file);
		logger.info("Process Completed.. ");
	}
	
	
	@PostMapping("/loadfile/irxn")
	public void  loadIRXNFile(@RequestBody File file) throws IOException, InterruptedException 
	{
		MDC.put("logFileName", "PROCESS_INRX_".concat(timeZoneConv.currentTime().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"))));
        logger.info("logFileName: {}",MDC.get("logFileName"));
		IRXNService irxnService = applicationContext.getBean(IRXNService.class);
		logger.info("Instance :", irxnService);
		logger.info("in CTRL"+Thread.currentThread().getName());
	
		irxnService.processFiles(file);
	}
	
	
	
	@GetMapping("/healthCheck/intx")
	public String healthCheck() throws IOException {
		logger.info("Health check get URL triggered.");
		return "INTX service is running..";
	}
	
	
}
