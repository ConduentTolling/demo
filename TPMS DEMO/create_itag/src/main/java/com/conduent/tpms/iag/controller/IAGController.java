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

import com.conduent.app.timezone.utility.TimeZoneConv;
import com.conduent.tpms.iag.config.RequestObject;
import com.conduent.tpms.iag.exception.InvalidFileFormatException;
import com.conduent.tpms.iag.exception.InvalidFileGenerationTypeException;
import com.conduent.tpms.iag.service.ITAGCreationService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * Controller class for http request
 * 
 * @author urvashic
 *
 */

@RestController
public class IAGController {
	
	private static final Logger log = LoggerFactory.getLogger(IAGController.class);

	@Autowired
	ITAGCreationService iagSevice;
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
	@RequestMapping(value = "/buildTagFile", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = String.class )})
    @ApiOperation(value="Creating ITAG File")
	public String processLoadTags(@RequestBody  RequestObject requestObject) throws IOException, InvalidFileGenerationTypeException, InvalidFileFormatException {
		log.info("Starting create_itag process..");

		MDC.put("logFileName", "CREATE_ITAG_".concat(timeZoneConv.currentTime().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"))));
		log.info("logFileName: {}",MDC.get("logFileName"));

		return iagSevice.processFileCreation(requestObject.getAgencyId(), requestObject.getFileFormat(), requestObject.getGenType());

	}
	
	/**
	 * Running tag service health check
	 * 
	 * @return
	 */
	@RequestMapping(value = "/healthCheck/create_itag", method = RequestMethod.GET)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK",response = String.class )})
    @ApiOperation(value="Service status check, returns a string response if create_itag service is running.")
	private String checkService() {
		return "CREATE ITAG Service is Running";

	}
	
	
}
