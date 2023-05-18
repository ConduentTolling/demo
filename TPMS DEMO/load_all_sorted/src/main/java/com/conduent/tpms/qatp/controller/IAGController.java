package com.conduent.tpms.qatp.controller;
import java.io.IOException;
import java.time.format.DateTimeFormatter;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.conduent.app.timezone.utility.TimeZoneConv;
import com.conduent.tpms.qatp.config.RequestObject;
import com.conduent.tpms.qatp.exception.TPMSGateway;
import com.conduent.tpms.qatp.service.IAGSevice;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;


/**
 * Controller class for End Points
 * 
 * @author Urvashi C
 *
 */

@RestController
public class IAGController {
	
	private static final Logger log = LoggerFactory.getLogger(IAGController.class);

	@Autowired
	IAGSevice iagSevice;
	
	@Autowired
	TimeZoneConv timezoneConv;
	
	/**
	 * Process the loading of active tag devices
	 * @RequestBody RequestObject
	 */ 
	@RequestMapping(value = "/processActiveTags", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK",response = TPMSGateway.class )})
    @ApiOperation(value="Loading all tag details")
	public TPMSGateway<String> processLoadTags(@RequestBody @Valid RequestObject requestObject) throws IOException 
	{
		log.info("Starting itag process..");
		MDC.put("logFileName", "IAG_TAGS_".concat(timezoneConv.currentTime().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"))));
		log.info("logFileName: {}",MDC.get("logFileName"));
		log.info("Request Object..{}",requestObject.toString());
		String response=iagSevice.loadValidTagDetails(requestObject.getStartDeviceNo(), requestObject.getEndDeviceNo(), requestObject.getLoadType(),requestObject.getEnableHistory());
		//return "Job executed";
		return new TPMSGateway<String>(true, HttpStatus.OK, response);
		
	}
	
	/**
	 * Running tag service check
	 * @return String
	 */
	@RequestMapping(value = "/healthCheck/iag-itag", method = RequestMethod.GET)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK",response = TPMSGateway.class )})
    @ApiOperation(value="Health Check for load all sorted")
	private String checkService() {
		return "IAG Service is Running";

	}
	
}
