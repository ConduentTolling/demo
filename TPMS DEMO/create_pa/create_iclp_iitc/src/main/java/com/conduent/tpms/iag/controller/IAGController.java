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
import com.conduent.tpms.iag.service.IAGFileCreationServiceIITC;
import com.conduent.tpms.iag.service.IAGFileCreationSevice;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

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
	IAGFileCreationServiceIITC iagServiceIITC;
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
	
	@RequestMapping(value = "/buildICLPFile", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = String.class )})
    @ApiOperation(value="Creating ICLP File")	
	public String processLoadTags(@RequestBody  RequestObject requestObject) throws IOException, InvalidFileGenerationTypeException, InvalidFileFormatException {
		log.info("Starting iclp file creation process..");
		RequestObject req = requestObject;
		System.out.println(req.toString());
		
		MDC.put("logFileName", "CREATE_ICLP_IITC_".concat(timeZoneConv.currentTime().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"))));
        log.info("logFileName: {}",MDC.get("logFileName"));
		iagSevice.fileCreationTemplate(requestObject.getAgencyId(), requestObject.getFileFormat(), requestObject.getGenType());
		return "Job executed successfully";
	}
	
	
	@RequestMapping(value = "/buildIITCFile", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK",response = String.class )})
    @ApiOperation(value="Creating IITC File")
	public String processLoadTagsforIITC(@RequestBody  RequestObject requestObject) throws IOException, InvalidFileGenerationTypeException, InvalidFileFormatException {
		log.info("Starting iitc file creation process..");
		RequestObject req = requestObject;
		System.out.println(req.toString());
		
		MDC.put("logFileName", "CREATE_ICLP_IITC_".concat(timeZoneConv.currentTime().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"))));
        log.info("logFileName: {}",MDC.get("logFileName"));
		iagServiceIITC.fileCreationTemplate(requestObject.getAgencyId(), requestObject.getFileFormat(), requestObject.getGenType());
		return "Job executed successfully";
	}
	
	/**
	 * Running tag service health check
	 * 
	 * @return
	 */
	@RequestMapping(value = "/healthCheck/create_iclp_iitc", method = RequestMethod.GET)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK",response = String.class )})
    @ApiOperation(value="Service status check, returns a string response if create_iclp_iitc service is running.")
	private String checkService() {
		return "IAG Service is Running";

	}
	
	
}
