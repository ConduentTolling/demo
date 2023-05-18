package com.conduent.tpms.iag.controller;

import java.io.IOException;

import java.time.format.DateTimeFormatter;
import java.util.logging.LogManager;

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
 * @author urvashic
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
	@RequestMapping(value = "/buildMTATSIFile", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses( value = {  @ApiResponse( code =200, message ="OK" , response =String.class)  } )
	@ApiOperation(value="creating MTA TSI")
	public String processLoadTags(@RequestBody  RequestObject requestObject) throws IOException, InvalidFileGenerationTypeException, InvalidFileFormatException {
		log.info("Starting MTA_TSI file creation process..");
		
		MDC.put("logFileName", "CREATE_MTA_TSI_".concat(timeZoneConv.currentTime().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"))));
        log.info("logFileName: {}",MDC.get("logFileName"));
		return iagSevice.fileCreationTemplate(requestObject.getAgencyId(), requestObject.getFileFormat(), requestObject.getGenType(), false);
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
	@RequestMapping(value = "/buildMTATSIFileRFK", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses( value = {  @ApiResponse( code =200, message ="OK" , response =String.class)  } )
	@ApiOperation(value="creating MTA TSI")
	public String processLoadTagsRFK(@RequestBody  RequestObject requestObject) throws IOException, InvalidFileGenerationTypeException, InvalidFileFormatException {
		log.info("Starting MTA_TSI RFK file creation process..");
		
		MDC.put("logFileName", "CREATE_MTA_TSI_RFK_".concat(timeZoneConv.currentTime().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"))));
        log.info("logFileName: {}",MDC.get("logFileName"));
		return iagSevice.fileCreationTemplate(requestObject.getAgencyId(), requestObject.getFileFormat(), requestObject.getGenType(), true);
	}
	
	/**
	 * Running tag service health check
	 * 
	 * @return
	 */
	@RequestMapping(value = "/healthCheck/mta_tsi", method = RequestMethod.GET)
	@ApiResponses( value = {  @ApiResponse( code =200, message ="OK" , response =String.class)  } )
	@ApiOperation(value="Health check")
	private String checkService() {
		return "IAG Service is Running";
	}
	
	
}
