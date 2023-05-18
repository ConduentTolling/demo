package com.conduent.tpms.inserttable.controller;

import java.text.ParseException;
import java.time.format.DateTimeFormatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.conduent.app.timezone.utility.TimeZoneConv;
import com.conduent.tpms.inserttable.model.TLaneRequestModel;
import com.conduent.tpms.inserttable.service.TLaneService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping
public class InsertTLaneController {

	final Logger logger = LoggerFactory.getLogger(InsertTLaneController.class);

	@Autowired
	TLaneService tLaneService;
	
	@Autowired
	TimeZoneConv timezoneConv;

	String methodName = null;
	String className = this.getClass().getName();


	@ApiOperation(value = "Insert T_LANE Api")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Record 20036 inserted successfully", response = String.class) })

	@PostMapping(value = "/insertTLane", produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_ATOM_XML_VALUE }, consumes = { MediaType.APPLICATION_JSON_VALUE,
					MediaType.APPLICATION_ATOM_XML_VALUE })
	public ResponseEntity<?> insertTLane(@RequestBody TLaneRequestModel tLaneRequestModel) throws ParseException {

		logger.info("Starting with insert plan suspension api..");
		MDC.put("logFileName", "INSERT_T_LANE".concat(timezoneConv.currentTime().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"))));
		logger.info("logFileName: {}",MDC.get("logFileName"));
		if (tLaneRequestModel.getLaneId() > 0) {
			Integer responseId = tLaneService.insertTLane(tLaneRequestModel);

			if (responseId != null && responseId.intValue() > 0) {
				// return new TPMSGateway<Object>(true, HttpStatus.OK, "Record
				// "+tLaneRequestModel.getLaneId()+" inserted successfully");
				String msg="Record "+tLaneRequestModel.getLaneId()+" inserted successfully";
				HttpHeaders header=new HttpHeaders();
				header.add("Description", "Insert T_LANE");
				return new ResponseEntity<String>(msg,header,HttpStatus.OK);
			} else if (responseId == null || responseId.intValue() == 0) {
				String msg="The record " + tLaneRequestModel.getLaneId() + " has already been inserted";
				HttpHeaders header=new HttpHeaders();
				header.add("Description", "Insert T_LANE");
				return new ResponseEntity<String>(msg,header,HttpStatus.BAD_REQUEST);
			}
		}
		String msg="Insertion Failed";
		HttpHeaders header=new HttpHeaders();
		header.add("Description", "Insert T_LANE");
		return new ResponseEntity<String>(msg,header,HttpStatus.BAD_REQUEST);

//		else if (responseId == null || responseId.intValue() == 0) {
//			return new TPMSGateway<String>(false, "The record " + tLaneRequestModel.getLaneId() + " has already been inserted");
//		} 
//		
//		}
//			return new TPMSGateway<String>(false, "Record insertion Failed");

	}
}