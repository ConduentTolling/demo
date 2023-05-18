package com.conduent.tpms.iag.controller;


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
import org.springframework.web.bind.annotation.RestController;

import com.conduent.app.timezone.utility.TimeZoneConv;
import com.conduent.tpms.iag.dao.impl.TQatpMappingDaoImpl;
import com.conduent.tpms.iag.service.ICRXService;
import com.conduent.tpms.iag.service.IRXCService;
import com.conduent.tpms.iag.validation.IcrxFileParserImpl;

@RestController
public class ICRXProcessController {

	private static final Logger logger = LoggerFactory.getLogger(ICRXProcessController.class);

	
	@Autowired
	IcrxFileParserImpl genericIITCFileParserImpl;
	
	@Autowired
	TQatpMappingDaoImpl tQatpMappingDaoImpl;
	
	@Autowired
	ApplicationContext applicationContext;
	
	@Autowired
	TimeZoneConv timeZoneConv;
	
	
	@PostMapping("/loadfile/icrx")
	public void  loadICRXFile(@RequestBody File file) throws IOException, InterruptedException 
	{
		logger.info("Time as per timezone:{}",timeZoneConv.currentTime());
		logger.info("Date as per timezone:{}",timeZoneConv.currentDate());
		MDC.put("logFileName", "PROCESS_ICRX_".concat(timeZoneConv.currentTime().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"))));
        logger.info("logFileName: {}",MDC.get("logFileName"));
		ICRXService icrxService = applicationContext.getBean(ICRXService.class);
		logger.info("Instance :", icrxService);
		logger.info("in CTRL"+Thread.currentThread().getName());
		
	
		icrxService.processFiles(file);
	}
	
	
	@PostMapping("/loadfile/irxc")
	public void  loadIRXCFile(@RequestBody File file) throws IOException, InterruptedException 
	{
		MDC.put("logFileName", "PROCESS_ICRX_".concat(timeZoneConv.currentTime().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"))));
        logger.info("logFileName: {}",MDC.get("logFileName"));
		IRXCService irxcService = applicationContext.getBean(IRXCService.class);
		logger.info("Instance :", irxcService);
		logger.info("in CTRL"+Thread.currentThread().getName());
	
		irxcService.processFiles(file);
	}
	
	/*
	@GetMapping("/loadfile/icrx")
	public String loadICRXfile() throws IOException, InterruptedException {
		genericIITCFileParserImpl.fileParseTemplate("icrx");
		return "Successfully Uploaded icrx file";

	}
	*/
	
	@GetMapping("/healthCheck/ictx")
	public String healthCheck() throws IOException {
		logger.info("Health check get URL triggered.");
		return "ICTX service is running..";
	}
	
	
	/*
	 * @PostMapping("/upload") public void uploadProcessCsv() { try {
	 * System.out.println("upload url triggered"); } catch (Exception e) { // TODO
	 * Auto-generated catch block e.printStackTrace(); } }
	 * 
	 * @GetMapping("/getMissingFilesForAgency") public void getmissedfiles() throws
	 * IOException { genericIITCFileParserImpl.getMissingFilesForAgency();
	 * 
	 * }
	 */
}
