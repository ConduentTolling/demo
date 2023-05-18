package com.conduent.tpms.unmatched.controller;

import java.text.ParseException;
import java.time.format.DateTimeFormatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.conduent.app.timezone.utility.TimeZoneConv;
import com.conduent.tpms.unmatched.service.DeviceMatchingService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ApiResponse;


@RestController
@RequestMapping("/api")
public class TollUnmatchedTxController {
	
	private static final Logger logger = LoggerFactory.getLogger(TollUnmatchedTxController.class);
	
	@Autowired
	protected DeviceMatchingService deviceMachingService;
	
	@Autowired
	TimeZoneConv timeZoneConv;
	
	@GetMapping("/unmatchedTrx/deviceNumber")
	@RequestMapping(value = "/unmatchedTrx/deviceNumber", method = RequestMethod.GET)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK",response = String.class )})
    @ApiOperation(value="API call for toll-unmathced service.")
	public String unamtchedTrxUsingDeviceNumber() throws ParseException {
		
		MDC.put("logFileName", "UNMATCHED_TRX_".concat(timeZoneConv.currentTime().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"))));
		logger.info("logFileName: {}",MDC.get("logFileName"));
		
		deviceMachingService.getUnmatchedTrxBasedOnDeviceNo();
		return "Matched transaction pushed successfully";
	}
	
//	@GetMapping("/unmatchedTrx/plateNumber")
//	public String unamtchedTrxUsingPlateNumber() throws ParseException {
//		
//		deviceMachingService.getUnmatchedTrxBasedOnPlateNo();
//		return "Mached number plate pushed sucessfully";
//	}
	
//	@GetMapping("/unmatchedTrx/expiredExitTransaction")
//	public String expiredExitTransaction() {
//		
//		deviceMachingService.getExpiredExitTransactions();
//		return "Got Exit expired transaction sucessfully";
//	}
//	
//	@GetMapping("/unmatchedTrx/expiredEntryTransaction")
//	public String expiredEntryTransaction() {
//		
//		deviceMachingService.getExpiredEntryTransactions();
//		return "Got Entry expired transaction sucessfully";
//	}
//	
//	@GetMapping("/unmatchedTrx/expiredViolTxTransaction")
//	public String expiredViolTxTransaction() {
//		
//		deviceMachingService.getExpiredViolTxTransactions();
//		return "Got Viol_TX expired transaction sucessfully";
//	}

}
