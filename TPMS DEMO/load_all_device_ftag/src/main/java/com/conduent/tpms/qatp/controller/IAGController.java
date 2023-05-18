package com.conduent.tpms.qatp.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.jboss.logging.MDC;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.conduent.app.timezone.utility.TimeZoneConv;
import com.conduent.tpms.qatp.config.RequestObject;
import com.conduent.tpms.qatp.service.IAGSevice;


/**
 * Controller class for End Points
 * 
 * @author Urvashi C
 *
 */

@RestController
public class IAGController {
	
	private static final Logger log = LoggerFactory.getLogger(IAGController.class);

	@Autowired
	IAGSevice iagSevice;
	
	@Autowired
	TimeZoneConv timeZoneConv;
	
	/**
	 * Process the loading of active tag devices
	 * @RequestBody RequestObject
	 */ 
	@RequestMapping(value = "/loadAllDeviceFtag", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public String processLoadTags(@RequestBody  RequestObject requestObject) throws IOException 
	{
		log.info("Starting itag process..");
		MDC.put("logFileName", "LOAD_ALL_DEVICE_FTAG_".concat(timeZoneConv.currentTime().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"))));
		log.info("logFileName: {}",MDC.get("logFileName"));
		log.info("Request Object..{}",requestObject.toString());
		iagSevice.loadValidTagDetails(requestObject.getStartDeviceNo(), requestObject.getEndDeviceNo(), requestObject.getLoadType(),requestObject.getEnableHistory());
		return "Job executed";
	}
	
	/**
	 * Running tag service check
	 * @return String
	 */
	@RequestMapping(value = "/healthCheck/iag-ftag", method = RequestMethod.GET)
	private String checkService() {
		return "IAG Service is Running";

	}
	
}
