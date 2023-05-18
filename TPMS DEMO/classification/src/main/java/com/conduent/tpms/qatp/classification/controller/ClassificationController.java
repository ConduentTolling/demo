package com.conduent.tpms.qatp.classification.controller;


import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.conduent.app.timezone.utility.TimeZoneConv;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api")
public class ClassificationController {
	String methodName = null;
	String className = this.getClass().getName();
	HttpHeaders headers = new HttpHeaders();

	private static final Logger logger = LoggerFactory.getLogger(ClassificationController.class);

	@Autowired
	TimeZoneConv timeZoneConv;

	//@RequestMapping(value = "/healthCheckClassification", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@GetMapping(value="/healthCheckClassification")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = String.class) })
	@ApiOperation(value = "Health Check API for Classification Parking")
	public String healthCheck() {
		Optional<String> podName = Optional.ofNullable(System.getenv("HOSTNAME"));
		logger.debug("Pod Name is {}", podName);
		logger.info("Health check get URL triggered on podName {}", podName);
		return "Classification service is running on " + podName;
	}
}
