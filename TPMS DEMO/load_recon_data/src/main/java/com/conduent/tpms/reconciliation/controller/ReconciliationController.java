package com.conduent.tpms.reconciliation.controller;

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
import com.conduent.tpms.reconciliation.service.ReconciliationService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ApiResponse;



@RestController
@RequestMapping("/api")
public class ReconciliationController {
	
	private static final Logger logger = LoggerFactory.getLogger(ReconciliationController.class);
	
	@Autowired
	protected ReconciliationService reconciliationService;
	
	@Autowired
	TimeZoneConv timeZoneConv;
	
	
	@GetMapping("/reconciliation/loadReconData")
	@RequestMapping(value = "/reconciliation/loadReconData", method = RequestMethod.GET)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK",response = String.class )})
    @ApiOperation(value="API call for reconciliation service.")
	public String loadData() throws ParseException {
		
		MDC.put("logFileName", "RECONCILIATION_".concat(timeZoneConv.currentTime().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"))));
		logger.info("logFileName: {}",MDC.get("logFileName"));
		
		reconciliationService.loadReconciliationData();
		return "Record insert and update into T_Reconciliation table successfully..";
	}

}
