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
import com.conduent.tpms.iag.constants.Constants;
import com.conduent.tpms.iag.exception.InvalidFileFormatException;
import com.conduent.tpms.iag.exception.InvalidFileGenerationTypeException;
import com.conduent.tpms.iag.service.TSFileCreationService;

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
	TSFileCreationService tsFileSevice;
	@Autowired
	TimeZoneConv timeZoneConv;
	
	//create interface put all code in impl class
	
	/**
	 * Process the loading of active tag devices
	 * 
	 * @param requestObject
	 * @return
	 * @throws IOException
	 * @throws InvalidFileFormatException 
	 * @throws InvalidFileGenerationTypeException 
	 */
	@RequestMapping(value = "/buildTsFile", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK",response = String.class )})
	@ApiOperation(value="API call to build MTA tag status file.")
	public String processLoadTags(@RequestBody  RequestObject requestObject) throws IOException, InvalidFileGenerationTypeException, InvalidFileFormatException {
		log.info("Starting tag process..");
		MDC.put("logFileName", "MTA_TS_".concat(timeZoneConv.currentTime().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"))));
		log.info("logFileName: {}",MDC.get("logFileName"));
		return tsFileSevice.fileCreationTemplate(requestObject.getAgencyId(), requestObject.getFileFormat(), requestObject.getGenType(),false);
		//return "Job executed";
	}
	
	
	
	@RequestMapping(value = "/buildTsFileRFK", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK",response = String.class )})
	@ApiOperation(value="API call to build MTA tag status file.")
	public String processLoadTagsForRFK(@RequestBody  RequestObject requestObject) throws IOException, InvalidFileGenerationTypeException, InvalidFileFormatException {
		log.info("Starting tag process..");
		MDC.put("logFileName", "MTA_TS_".concat(timeZoneConv.currentTime().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"))).concat(Constants.isRFK));
		log.info("logFileName: {}",MDC.get("logFileName"));
		return tsFileSevice.fileCreationTemplate(requestObject.getAgencyId(), requestObject.getFileFormat(), requestObject.getGenType(),true);
		//return "Job executed";
	}
	
	
	
	//call this instead of fileCreationTemplate 47
	//move this in IAG base class
	public void processLoadTagsForAllAgencies() {
		// home agency = gethomeagecny id
		//call fileCreatonTemplate with home agency(gethomeagecny)
		
		//get all away agencies 
		//agencyList=getAllAwayAgencies()
		
		//for(){ agency
		//fileCreationTemplate
		// }
		
	}
	
	/**
	 * Running tag service health check
	 * 
	 * @return
	 */
	@RequestMapping(value = "/healthCheck/mta_ts", method = RequestMethod.GET, produces = {MediaType.ALL_VALUE})
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK",response = String.class )})
    @ApiOperation(value="Service status check, returns a string response if mta_ts service is running.")
	private String checkService() {
		log.info("IAG Service is Running..");
		return "IAG Service is Running";
	}
	
	
}
