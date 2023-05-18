package com.conduent.tpms.icrx.controller;

import java.time.format.DateTimeFormatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.conduent.app.timezone.utility.TimeZoneConv;
import com.conduent.tpms.icrx.service.IcrxProcessService;

/**
 * ICRX Creation Process Controller
 * 
 * @author urvashic
 *
 */
@RestController
@RequestMapping("/api/process") 
public class IcrxController {

	private static final Logger logger = LoggerFactory.getLogger(IcrxController.class);

	@Autowired
	IcrxProcessService icrxProcessService;
	
	@Autowired
	TimeZoneConv timeZoneConv;

	/**
	 * Start the process of icrx creation for away agency in request
	 * 
	 * @param awayAgencyId
	 * @return ResponseEntity<String>
	 * @throws Exception
	 */
	@GetMapping("/{filetype}/{awayagencyid}")
	public ResponseEntity<String> process(@PathVariable("filetype") String fileType, @PathVariable("awayagencyid") Long awayAgencyId) throws Exception {
		logger.info("Icrx File Creation process started for Away Agency Id: {}", awayAgencyId);
		MDC.put("logFileName", "ICRX_CREATION_PROCESS_".concat(timeZoneConv.currentTime().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"))));
        logger.info("logFileName: {}",MDC.get("logFileName"));
		icrxProcessService.process(fileType, awayAgencyId);
		logger.info("Icrx File Creation process completed for Away Agency Id: {}", awayAgencyId);
		return new ResponseEntity<>("Request processed sucessfully for Away Agency Id:" + awayAgencyId, HttpStatus.OK);
	}

}
