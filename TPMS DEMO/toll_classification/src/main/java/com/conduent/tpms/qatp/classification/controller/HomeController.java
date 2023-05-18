package com.conduent.tpms.qatp.classification.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	/**
	 * Running TRF service health check
	 * 
	 * @return
	 */
	@GetMapping("/api/toll-classification-healthCheck")
	@RequestMapping(value = "/api/toll-classification-healthCheck", method = RequestMethod.GET)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK",response = String.class )})
    @ApiOperation(value="Service status check, returns a string response if toll classification service is running. ")

	public String healthCheck() {
		logger.info("Classification health check get URL triggered");
		return "Toll classification service is running..";
	}

}
