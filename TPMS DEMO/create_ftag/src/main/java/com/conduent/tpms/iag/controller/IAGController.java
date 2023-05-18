package com.conduent.tpms.iag.controller;

import java.io.IOException;
import java.time.format.DateTimeFormatter;

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
import com.conduent.tpms.iag.config.RequestObject;
import com.conduent.tpms.iag.exception.InvalidFileFormatException;
import com.conduent.tpms.iag.exception.InvalidFileGenerationTypeException;
import com.conduent.tpms.iag.exception.TPMSGateway;
import com.conduent.tpms.iag.service.FTAGFullLoadService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;



@RestController
public class IAGController {
	
	private static final Logger log = LoggerFactory.getLogger(IAGController.class);

	@Autowired
	FTAGFullLoadService iagSevice;
	
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
	@RequestMapping(value = "/createFtagFile", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "FTAG File created successfully",response = TPMSGateway.class )})
    @ApiOperation(value="Creation of FTAG File")
	public TPMSGateway<String> processLoadTags(@RequestBody  RequestObject requestObject) throws IOException, InvalidFileGenerationTypeException, InvalidFileFormatException {
		log.info("Starting create_ftag process..");
		//if(file.Exists("C:\\\\Users\\\\petetid\\\\Documents\\\\IAG-FTAG-creation-story\\\\FTAG_FILES"))
		MDC.put("logFileName", "CREATE_FTAG_".concat(timeZoneConv.currentTime().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"))));
		log.info("logFileName: {}",MDC.get("logFileName"));
		if(iagSevice.processFileCreation(requestObject.getAgencyId(), requestObject.getFileFormat(), requestObject.getGenType())) {
			return new TPMSGateway<String>(true, HttpStatus.OK, "FTAG File created successfully");
		}else {
		return new TPMSGateway<String>(true, HttpStatus.OK, "Failed to create FTAG File");
		}
	}
	
	/**
	 * Running tag service health check
	 * 
	 * @return
	 */
	  @ApiResponses(value = { @ApiResponse(code = 200, message = "OK",response = String.class )})
	  @ApiOperation(value="Service status check, returns a string response if Create_ftag service is running.")
	private String checkService() {
		return "CREATE FTAG Service is Running";

	}
	
	
}
