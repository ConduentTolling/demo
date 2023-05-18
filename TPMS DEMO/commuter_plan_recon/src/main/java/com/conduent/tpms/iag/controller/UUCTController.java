package com.conduent.tpms.iag.controller;

import java.io.IOException;
import java.time.format.DateTimeFormatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.conduent.app.timezone.utility.TimeZoneConv;
import com.conduent.tpms.iag.service.UUCTCommuterTripsService;


/**
 * Controller class for http request
 * 
 * @author taniyan
 *
 */

@RestController
public class UUCTController {
	
	private static final Logger log = LoggerFactory.getLogger(UUCTController.class);
	
	@Autowired
	UUCTCommuterTripsService uuctService;
	
	@Autowired
	TimeZoneConv timeZoneConv;
	
	@GetMapping("/uuctprocessing/commutertrips")
	public String processingCommuterTrips() throws IOException, InterruptedException 
	{
		MDC.put("logFileName", "UUCT_".concat(timeZoneConv.currentTime().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"))));
		log.info("logFileName: {}",MDC.get("logFileName"));
		
		log.info("Starting UUCT Processing for Commuter Trips...");
		uuctService.processingCommuterTrips();
		return "Job executed successfully";
	}
	
	
	/**
	 * Running uuct commuter trip service health check
	 * 
	 * @return
	 */
	
	
	  @GetMapping("/uuctprocessing/postusage") public String processingPostUsage()
	  throws IOException, InterruptedException { 
		  MDC.put("logFileName","UUCT_".concat(timeZoneConv.currentTime().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")))); log.info("logFileName: {}",MDC.get("logFileName"));
	  
	  log.info("Starting UUCT Processing for Post Trips...");
	  uuctService.processingPostUsageTrip();
	  return "Job executed successfully"; 
	  }
	 
	
	
	@GetMapping("/healthCheck/commutertrips")
	private String checkService(@RequestParam String planType) {
		
		return planType;
	}
	
	
}
