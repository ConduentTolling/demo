package com.conduent.tpms.iag.controller;

import java.io.IOException;
import java.time.format.DateTimeFormatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.conduent.app.timezone.utility.TimeZoneConv;
import com.conduent.tpms.iag.service.IagService;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;




@RestController
@RequestMapping("/api")
public class IagController {

	@Autowired
	IagService iagService;
	
	
	  @Autowired TimeZoneConv timeZoneConv;
	  
	  private static final Logger logger =
	  LoggerFactory.getLogger(IagController.class);
	 
	@GetMapping("/process_parking_ack")
	
	@ApiResponses(value = {@ApiResponse(code = 200, message = "Job executed successfully",response = String.class )})


	public String processAckFile() throws IOException {
		try 
		{
			
			  MDC.put("logFileName",
			  "PROCESS_PARKING_ACK".concat(timeZoneConv.currentTime().format(DateTimeFormatter.
			  ofPattern("yyyyMMddHHmmss"))));
			  logger.info("logFileName: {}",MDC.get("logFileName"));
			 
			iagService.process();
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			return e.getMessage();
		}
		return "Job executed successfully";

	}
}
