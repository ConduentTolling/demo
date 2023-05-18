package com.conduent.tpms.inserttable.controller;

import java.text.ParseException;
import java.time.format.DateTimeFormatter;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.conduent.app.timezone.utility.TimeZoneConv;
import com.conduent.tpms.inserttable.insertexception.TPMSGateway;
import com.conduent.tpms.inserttable.model.T25APendingQueueRequestModel;
import com.conduent.tpms.inserttable.service.T25aPendingQueueService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;



@RestController
@RequestMapping("/api/ibts")
public class InsertData25AController {
	
	final Logger logger = LoggerFactory.getLogger(InsertData25AController.class);
	
	@Autowired
	T25aPendingQueueService t25aPendingQueueService;
	
	@Autowired
	TimeZoneConv timezoneConv;
	
	String methodName = null;
	String className = this.getClass().getName();
	
	// @Operation(summary = "Insert Pending Details Api")
	@ApiOperation(value = "Insert 25A Pending Details Api")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Record 20000145135 inserted successfully", response = String.class) })

//	@ApiResponses(value = {
//			@ApiResponse(responseCode = "200", description = "Pending Detail has Successfully got inserted ", content = {
//					@Content(mediaType = "application/json", schema = @Schema(implementation = TNY12PendingQueueRequestModel.class)) }),
//
//			@ApiResponse(responseCode = "401", description = "You are not authorized to view the resource", content = @Content),
//
//			@ApiResponse(responseCode = "403", description = "Accessing the resource you were trying to reach is forbidden", content = @Content),
//
//			@ApiResponse(responseCode = "404", description = "Pending details are not found", content = @Content) })

	@PostMapping(value = "/insertPendingDetails", produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_ATOM_XML_VALUE }, consumes = { MediaType.APPLICATION_JSON_VALUE,
					MediaType.APPLICATION_ATOM_XML_VALUE })
	public TPMSGateway<Object> insertPlanSuspensionDetails(@RequestBody @Valid T25APendingQueueRequestModel tollUnmatchedTxRequestModel) throws ParseException {

		logger.info("Starting with insert plan suspension api..");
		MDC.put("logFileName", "INSERT_DATA_25_25A".concat(timezoneConv.currentTime().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"))));
		logger.info("logFileName: {}",MDC.get("logFileName"));
		Integer responseId = t25aPendingQueueService.insertPendingDetails(tollUnmatchedTxRequestModel);

		if (responseId != null && responseId.intValue() > 0) {
			return new TPMSGateway<Object>(true, HttpStatus.OK, "Record "+tollUnmatchedTxRequestModel.getLaneTxId()+" inserted successfully");
		} else if (responseId == null || responseId.intValue() == 0) {
			return new TPMSGateway<Object>(false, HttpStatus.OK, "The record "+tollUnmatchedTxRequestModel.getLaneTxId()+" has already been inserted");
		} else {
			return new TPMSGateway<Object>(false, HttpStatus.OK, "Record insertion got failed");
		}

	}

}