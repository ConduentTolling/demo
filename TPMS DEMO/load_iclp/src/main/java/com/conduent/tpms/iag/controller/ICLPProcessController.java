package com.conduent.tpms.iag.controller;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.conduent.app.timezone.utility.TimeZoneConv;
import com.conduent.tpms.iag.service.ICLPService;
import com.conduent.tpms.iag.validation.GenericICLPFileParserImpl;
import com.conduent.tpms.iag.validation.TestCalss;
import org.springframework.context.ApplicationContext;

@RestController
public class ICLPProcessController {
 /*
	@Autowired
	TestCalss testCalss;

	@Autowired
	GenericICLPFileParserImpl genericICLPFileParserImpl;
	*/
	
	@Autowired
	 private ApplicationContext applicationContext;
	
	@Autowired
	TimeZoneConv timeZoneConv;
	
	private static final Logger logger = LoggerFactory.getLogger(ICLPProcessController.class);

/*
	@GetMapping("/loadfile/iclp")
	public String loadICLPfile() throws IOException, InterruptedException {
		genericICLPFileParserImpl.fileParseTemplate();
		return "Successfully Uploaded file";
	}
	*/
	
	@PostMapping("/loadfile/iclp")
	public void  loadICLPFile(@RequestBody File file , @RequestParam("agencySequence") int agencySequence) throws IOException, InterruptedException 
	{
		MDC.put("logFileName", "LOAD_ICLP_".concat(timeZoneConv.currentTime().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"))));
		logger.info("logFileName: {}",MDC.get("logFileName"));
		ICLPService iclpService = applicationContext.getBean(ICLPService.class);
		logger.info("Instance :", iclpService);
		logger.info("in CTRL"+Thread.currentThread().getName());
		logger.info("File {} agencySequence : {}", file.getName(), agencySequence);
	
		iclpService.processFiles(file,agencySequence);
	}
	
	
	@GetMapping("/healthCheck/iclp")
	public String healthCheck() throws IOException {
		logger.info("Health check get URL triggered.");
		return "ICLP service is running..";
	}
	
	/*
	@GetMapping("memory-status")
	public Map getMemoryStatistics() {
	    Map<String, Long> stats = new HashMap<>();
	    stats.put("TotalMemory",Runtime.getRuntime().totalMemory());
	    stats.put("MaxMemory",Runtime.getRuntime().maxMemory());
	    stats.put("FreeMemory",Runtime.getRuntime().freeMemory());
	    	    
	    return stats;
	}
	*/
	
}
