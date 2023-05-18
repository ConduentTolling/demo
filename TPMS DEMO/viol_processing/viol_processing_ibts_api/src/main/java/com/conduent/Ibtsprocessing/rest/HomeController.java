package com.conduent.Ibtsprocessing.rest;


import java.time.format.DateTimeFormatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.conduent.Ibtsprocessing.oss.OssStreamClient;
import com.conduent.app.timezone.utility.TimeZoneConv;

@RestController
@RequestMapping("/api")
public class HomeController {
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

	// @Autowired
	private OssStreamClient messageReader;
	
	@Autowired
	TimeZoneConv timeZoneConv;
	
	@GetMapping("/testOSS")
	public String testOSS() {
		logger.info("Health check get URL triggered");
		MDC.put("logFileName", "IBTS_VIOL_PROCESSING_".concat(timeZoneConv.currentTime().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"))));
        logger.info("logFileName: {}",MDC.get("logFileName"));
		try {
			return messageReader.getMessage(1).toString();
		} catch (Exception e) {
			return e.getMessage();
		}
	}
}
