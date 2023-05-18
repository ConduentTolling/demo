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
import com.conduent.tpms.iag.service.impl.MTAGFileCreationServiceImpl;


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
	MTAGFileCreationServiceImpl iagSevice;
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
   	

	@RequestMapping(value = "/createTAGNFile", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK",response = String.class )})
	@ApiOperation(value="API call to build mtag file.")
	public String processLoadTags(@RequestBody  RequestObject requestObject) throws IOException, InvalidFileGenerationTypeException, InvalidFileFormatException {
		log.info("Starting tag process..");

		MDC.put("logFileName", "TAGN_".concat(timeZoneConv.currentTime().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"))));
		log.info("logFileName: {}",MDC.get("logFileName"));
		return iagSevice.fileCreationTemplate(requestObject.getAgencyId(), requestObject.getFileFormat(), requestObject.getGenType());
		//return "Job executed";
	}
	

	/**
	 * Process the loading of active tag devices
	 * 
	 * @param requestObject
	 * @return
	 * @throws IOException
	 * @throws InvalidFileFormatException 
	 * @throws InvalidFileGenerationTypeException 
	 */
   	
	@RequestMapping(value = "/createNysbaTAGNFile", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK",response = String.class )})
	@ApiOperation(value="API call to build NYSBA mtag file.")
	public String processLoadTagsNysba(@RequestBody  RequestObject requestObject) throws IOException, InvalidFileGenerationTypeException, InvalidFileFormatException {
		log.info("Starting tag process..");
		MDC.put("logFileName", "NYSBA_TAGN_".concat(timeZoneConv.currentTime().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"))));
		log.info("logFileName: {}",MDC.get("logFileName"));
		return iagSevice.fileCreationTemplate(requestObject.getAgencyId(), requestObject.getFileFormat(), requestObject.getGenType());
		//return "Job executed";
	}
	
	/**
	 * Running tag service health check
	 * 
	 * @return
	 */
	@RequestMapping(value = "/healthCheck/nysta_mtag", method = RequestMethod.GET)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK",response = String.class )})
	@ApiOperation(value="API call to build NYSTA mtag file.")
	private String checkService() {
		return "NYSTA MTAG Service is Running";
	}
	
}
