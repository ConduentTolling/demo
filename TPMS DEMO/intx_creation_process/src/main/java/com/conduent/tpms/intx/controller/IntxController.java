package com.conduent.tpms.intx.controller;

import java.time.format.DateTimeFormatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.conduent.app.timezone.utility.TimeZoneConv;
import com.conduent.tpms.intx.constants.IntxConstant;
import com.conduent.tpms.intx.service.IntxProcessService;

/**
* INTX Creation Process Controller 
**/

@RestController
@RequestMapping("/api")
public class IntxController {

	private static final Logger logger = LoggerFactory.getLogger(IntxController.class);
	
	@Autowired
	ApplicationContext applicationContext;
	
	@Autowired
	TimeZoneConv timeZoneConv;

	/**
	 * Start the process of intx creation for away agency
	 * @param fileName
	 * @param awayAgencyId
	 * @return ResponseEntity<String>
	 * @throws Exception
	 */
	
	@GetMapping("/intx/create/{externalFileId}/{awayAgencyId}")
	public ResponseEntity<String> processIntx(@PathVariable("externalFileId") String externalFileId, @PathVariable("awayAgencyId") Long awayAgencyId) throws Exception {
		try {
			MDC.put("logFileName", "INTX_CREATION_PROCESS_".concat(timeZoneConv.currentTime().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"))));
	        logger.info("logFileName: {}",MDC.get("logFileName"));
	        logger.info("INTX File Creation process started for external File Id: {}, Away Agency Id: {}", externalFileId, awayAgencyId);
			IntxProcessService intxProcessService = applicationContext.getBean(IntxProcessService.class);
			intxProcessService.process(externalFileId, awayAgencyId, IntxConstant.FILE_EXTENSION_INTX);
			logger.info("INTX File Creation process completed for external File Id: {}, Away Agency Id: {}",externalFileId, awayAgencyId);
		} catch (BeansException e) {
			logger.error("Failed for external File Id: {}, Away Agency Id: {}",externalFileId, awayAgencyId);
			e.printStackTrace();
			return new ResponseEntity<>("Request processed sucessfully for external File Id: " + externalFileId +  " Away Agency Id: " + awayAgencyId, HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			logger.error("Failed for external File Id: {}, Away Agency Id: {}",externalFileId, awayAgencyId);
			e.printStackTrace();
			return new ResponseEntity<>("Request processed sucessfully for external File Id: " + externalFileId +  " Away Agency Id: " + awayAgencyId, HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>("Request processed sucessfully for external File Id: " + externalFileId +  " Away Agency Id: " + awayAgencyId, HttpStatus.OK);
	}
	
	/**
	 * Start the process of itxn creation for away agency
	 * @param fileName
	 * @param awayAgencyId
	 * @return ResponseEntity<String>
	 * @throws Exception
	 */
	
	@GetMapping("/itxn/create/{externalFileId}/{awayAgencyId}")
	public ResponseEntity<String> processItxn(@PathVariable("externalFileId") String externalFileId, @PathVariable("awayAgencyId") Long awayAgencyId) throws Exception {
		try {
			MDC.put("logFileName", "ITXN_CREATION_PROCESS_".concat(timeZoneConv.currentTime().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"))));
	        logger.info("logFileName: {}",MDC.get("logFileName"));
	        logger.info("ITXN File Creation process started for external File Id: {}, Away Agency Id: {}", externalFileId, awayAgencyId);
			IntxProcessService itxnProcessService = applicationContext.getBean(IntxProcessService.class);
			itxnProcessService.process(externalFileId, awayAgencyId, IntxConstant.FILE_EXTENSION_ITXN);
			logger.info("ITXN File Creation process completed for external File Id: {}, Away Agency Id: {}",externalFileId, awayAgencyId);
		} catch (BeansException e) {
			logger.error("Failed for external File Id: {}, Away Agency Id: {}",externalFileId, awayAgencyId);
			e.printStackTrace();
			return new ResponseEntity<>("Request processed sucessfully for external File Id: " + externalFileId +  " Away Agency Id: " + awayAgencyId, HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			logger.error("Failed for external File Id: {}, Away Agency Id: {}",externalFileId, awayAgencyId);
			e.printStackTrace();
			return new ResponseEntity<>("Request processed sucessfully for external File Id: " + externalFileId +  " Away Agency Id: " + awayAgencyId, HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>("Request processed sucessfully for external File Id: " + externalFileId +  " Away Agency Id: " + awayAgencyId, HttpStatus.OK);
	}

}
