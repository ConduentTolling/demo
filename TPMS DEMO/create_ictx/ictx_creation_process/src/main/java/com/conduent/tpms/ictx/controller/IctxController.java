package com.conduent.tpms.ictx.controller;

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
import com.conduent.tpms.ictx.constants.IctxConstant;
import com.conduent.tpms.ictx.dao.AwayTransactionDao;
import com.conduent.tpms.ictx.dao.SequenceDao;
import com.conduent.tpms.ictx.service.IctxProcessService;
import com.conduent.tpms.ictx.utility.StaticDataLoad;


/**
 * ICTX Creation Process Controller
 * 
 * @author deepeshb
 *
 */
@RestController
//@RequestMapping("/api/ictx/process")
public class IctxController {

	private static final Logger logger = LoggerFactory.getLogger(IctxController.class);

	/*
	 * @Autowired IctxProcessService ictxProcessService;
	 */

	@Autowired
	SequenceDao sequenceDao;

	@Autowired
	AwayTransactionDao awayTransactionDao;

	@Autowired
	StaticDataLoad staticDataLoad;
	
	@Autowired
	ApplicationContext applicationContext;
	
	@Autowired
	TimeZoneConv timeZoneConv;

	/**
	 * Start the process of ictx creation for away agency in reuqest
	 * 
	 * @param awayAgencyId
	 * @return ResponseEntity<String>
	 * @throws Exception
	 */
	@GetMapping("/api/ictx/process/{awayagencyid}/")
	public ResponseEntity<String> process(@PathVariable("awayagencyid") String awayAgencyId) throws Exception {
		try {
			logger.info("Ictx File Creation process started for Away Agency Id: {}", awayAgencyId);
			MDC.put("logFileName", "ICTX_CREATION_PROCESS_".concat(timeZoneConv.currentTime().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"))));
	        logger.info("logFileName: {}",MDC.get("logFileName"));
			IctxProcessService ictxProcessService = applicationContext.getBean(IctxProcessService.class);
			logger.info(ictxProcessService.toString());
			
			if(awayAgencyId.equalsIgnoreCase(IctxConstant.HUBFILE)) {
				ictxProcessService.processHubFile(IctxConstant.FILE_EXTENSION_ICTX);
			}else {
				Long awayAgencyIdVal = Long.parseLong(awayAgencyId);
				ictxProcessService.process(awayAgencyIdVal, IctxConstant.FILE_EXTENSION_ICTX);
			}

			logger.info("Ictx File Creation process completed for Away Agency Id: {}", awayAgencyId);
		} catch (BeansException e) {
			logger.info("Failed for agency {}",awayAgencyId);
			e.printStackTrace();
			return new ResponseEntity<>("Request processed sucessfully for Away Agency Id:" + awayAgencyId, HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			logger.info("Failed for agency {}",awayAgencyId);
			e.printStackTrace();
			return new ResponseEntity<>("Request processed sucessfully for Away Agency Id:" + awayAgencyId, HttpStatus.BAD_REQUEST);

		}
		return new ResponseEntity<>("Request processed sucessfully for Away Agency Id:" + awayAgencyId, HttpStatus.OK);

	}
	
	
	/**
	 * Start the process of itxc creation for away agency in reuqest
	 * 
	 * @param awayAgencyId
	 * @return ResponseEntity<String>
	 * @throws Exception
	 */
	@GetMapping("/api/itxc/process/{awayagencyid}/")
	public ResponseEntity<String> processItxc(@PathVariable("awayagencyid") Long awayAgencyId) throws Exception {
		try {
			logger.info("Itxc File Creation process started for Away Agency Id: {}", awayAgencyId);
			MDC.put("logFileName", "ITXC_CREATION_PROCESS_".concat(timeZoneConv.currentTime().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"))));
	        logger.info("logFileName: {}",MDC.get("logFileName"));
			IctxProcessService itxcProcessService = applicationContext.getBean(IctxProcessService.class);
			logger.info(itxcProcessService.toString());
			itxcProcessService.process(awayAgencyId, IctxConstant.FILE_EXTENSION_ITXC);

			logger.info("Itxc File Creation process completed for Away Agency Id: {}", awayAgencyId);
		} catch (BeansException e) {
			logger.info("Failed for agency {}",awayAgencyId);
			e.printStackTrace();
			return new ResponseEntity<>("Request processed sucessfully for Away Agency Id:" + awayAgencyId, HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			logger.info("Failed for agency {}",awayAgencyId);
			e.printStackTrace();
			return new ResponseEntity<>("Request processed sucessfully for Away Agency Id:" + awayAgencyId, HttpStatus.BAD_REQUEST);

		}
		return new ResponseEntity<>("Request processed sucessfully for Away Agency Id:" + awayAgencyId, HttpStatus.OK);

	}
	

}
