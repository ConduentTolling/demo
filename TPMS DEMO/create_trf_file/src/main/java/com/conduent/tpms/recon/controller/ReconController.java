package com.conduent.tpms.recon.controller;

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
import com.conduent.tpms.recon.config.RequestObject;
import com.conduent.tpms.recon.exception.InvalidFileFormatException;
import com.conduent.tpms.recon.exception.InvalidFileGenerationTypeException;
import com.conduent.tpms.recon.service.TRFFileCreationSevice;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;


/**
 * Controller class for http request
 * 
 * @author shrikantm3
 *
 */

@RestController
public class ReconController {
	
	private static final Logger log = LoggerFactory.getLogger(ReconController.class);

	@Autowired
	TRFFileCreationSevice trfSevice;
	@Autowired
	TimeZoneConv timeZoneConv;
	
	@RequestMapping(value = "/buildTRFFile", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK",response = String.class )})
	@ApiOperation(value = "API call to build TRF Creation File.")
	public String processLoadTags(@RequestBody  RequestObject requestObject) throws IOException, InvalidFileGenerationTypeException, InvalidFileFormatException {
		log.info("Starting TRF creation process..");
		RequestObject req = requestObject;
		System.out.println(req.toString());
		MDC.put("logFileName", "CREATE_TRF_".concat(timeZoneConv.currentTime().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"))));
		log.info("logFileName: {}",MDC.get("logFileName"));
		trfSevice.fileCreationTemplate(requestObject.getAgencyId(), requestObject.getFileFormat(), requestObject.getGenType());
		return "Job executed successfully";
	}

	
	/**
	 * Running TRF service health check
	 * 
	 * @return
	 */
	@RequestMapping(value = "/healthCheck/create_trf", method = RequestMethod.GET)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK",response = String.class )})
	@ApiOperation(value = "API call to health check for TRF Creation File.")
	private String checkService() {
		log.info("TRF Service is Running..");
		return "TRF Service is Running";

	}
	
	
}
