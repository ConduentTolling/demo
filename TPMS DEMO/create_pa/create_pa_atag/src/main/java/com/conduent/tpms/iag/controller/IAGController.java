package com.conduent.tpms.iag.controller;

import java.io.IOException;

import java.time.format.DateTimeFormatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.conduent.tpms.iag.config.RequestObject;
import com.conduent.tpms.iag.exception.InvalidFileFormatException;
import com.conduent.tpms.iag.exception.InvalidFileGenerationTypeException;
import com.conduent.tpms.iag.service.IAGFileCreationSevice;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import com.conduent.app.timezone.utility.TimeZoneConv;

/**
 * Controller class for http request
 * 
 * @author taniyan
 *
 */

@RestController
public class IAGController {
	
	private static final Logger log = LoggerFactory.getLogger(IAGController.class);

	@Autowired
	IAGFileCreationSevice iagSevice;
	@Autowired
	TimeZoneConv timeZoneConv;
	
	/**
	 * Process the loading of active tag devices
	 * 
	 * @param requestObject
	 * @return
	 * @throws IOException
	 * @throws InvalidFileFormatException 
	 * @throws InvalidFileGenerationTypeException 
	 */
	
	@RequestMapping(value = "/buildPAFullFile", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses( value = {  @ApiResponse( code =200, message ="OK" , response =String.class)  } )
	@ApiOperation(value="creating PA ATAG")
	public String processLoadTags(@RequestBody  RequestObject requestObject) throws IOException, InvalidFileGenerationTypeException, InvalidFileFormatException {
		log.info("Starting pa full file creation process..");
		RequestObject req = requestObject;
		System.out.println(req.toString());
		
		MDC.put("logFileName", "CREATE_PA_ATAG_".concat(timeZoneConv.currentTime().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"))));
        log.info("logFileName: {}",MDC.get("logFileName"));
		return iagSevice.fileCreationTemplate(requestObject.getAgencyId(), requestObject.getFileFormat(), requestObject.getGenType());
		//return "Job executed successfully";
	}

	
	/**
	 * Running tag service health check
	 * 
	 * @return
	 */
	@RequestMapping(value = "/healthCheck/create_pa_atag", method = RequestMethod.GET)
	private String checkService() {
		return "IAG Service is Running";

	}
	
	
}
