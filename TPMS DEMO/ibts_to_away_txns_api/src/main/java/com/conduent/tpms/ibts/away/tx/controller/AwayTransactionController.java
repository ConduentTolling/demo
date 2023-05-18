package com.conduent.tpms.ibts.away.tx.controller;

import java.time.format.DateTimeFormatter;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.conduent.app.timezone.utility.TimeZoneConv;
import com.conduent.tpms.ibts.away.tx.model.AwayTransaction;
import com.conduent.tpms.ibts.away.tx.service.AwayTransactionService;
import com.conduent.tpms.ibts.away.tx.service.ValidationService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * ICTX Creation Process Controller
 * 
 * @author deepeshb
 *
 */
@RestController
@RequestMapping("/api/away/tx")
public class AwayTransactionController {

	private static final Logger logger = LoggerFactory.getLogger(AwayTransactionController.class);

	@Autowired
	ValidationService ictxProcessService;

	@Autowired
	AwayTransactionService awayTransactionService;
	
	@Autowired
	TimeZoneConv timeZoneConv;

	//@PostMapping("/")
	@RequestMapping(value = "/",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE, 
    produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK",response = ResponseEntity.class )})
    @ApiOperation(value="Ibts to away transaction")
	public ResponseEntity<String> process(@RequestBody @Valid AwayTransaction awayTransaction) {
		logger.info("Away transaction: {}", awayTransaction);
		MDC.put("logFileName", "IBTS_AWAY_TXN_API_".concat(timeZoneConv.currentTime().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"))));
        logger.info("logFileName: {}",MDC.get("logFileName"));
		if (awayTransactionService.insert(awayTransaction)) {
			StringBuilder sb = new StringBuilder();
			sb.append("Transaction inserted successfully with laneTxId:")
					.append(String.valueOf(awayTransaction.getLaneTxId()));
			return new ResponseEntity<>((sb.toString()), HttpStatus.CREATED);
		}
		StringBuilder sb = new StringBuilder();
		sb.append("Failed to insert transaction with laneTxId:").append(String.valueOf(awayTransaction.getLaneTxId()));
		return new ResponseEntity<>((sb.toString()), HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
