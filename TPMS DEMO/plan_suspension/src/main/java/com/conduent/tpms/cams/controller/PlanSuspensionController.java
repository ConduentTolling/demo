package com.conduent.tpms.cams.controller;

import java.text.ParseException;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.conduent.tpms.cams.dto.PlanSuspensionRequestModel;
import com.conduent.tpms.cams.dto.PlanSuspensionRequestModel2;
import com.conduent.tpms.cams.dto.PlanSuspensionResponseModel;
import com.conduent.tpms.cams.service.PlanSuspensionService;
import com.conduent.tpms.umatched.exception.TPMSGateway;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/planSuspension")
public class PlanSuspensionController {

	final Logger logger = LoggerFactory.getLogger(PlanSuspensionController.class);

	@Autowired

	PlanSuspensionService planSuspensionService;

	String methodName = null;
	String className = this.getClass().getName();

	@Operation(summary = "Fetch Plan Suspension Details Api")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Plan Suspension has been Successfully fetched ", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = PlanSuspensionResponseModel.class)) }),
			@ApiResponse(responseCode = "401", description = "You are not authorized to view the resource", content = @Content),
			@ApiResponse(responseCode = "403", description = "Accessing the resource you were trying to reach is forbidden", content = @Content),
			@ApiResponse(responseCode = "404", description = "Plan Suspension details are not found", content = @Content) })

	@PostMapping(value = "/fetchPlans", produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_ATOM_XML_VALUE }, consumes = { MediaType.APPLICATION_JSON_VALUE,
					MediaType.APPLICATION_ATOM_XML_VALUE })
	public TPMSGateway<?> getPlanSuspensionDetails(
			@RequestBody @Valid PlanSuspensionRequestModel planSuspensionRequestModel) throws ParseException {
		logger.info("Starting with get plan suspension api..");
		//System.out.println("Size::"+planSuspensionRequestModel.getSize());
	
		if (planSuspensionRequestModel.getSize() == 0) {
			planSuspensionRequestModel.setSize(10);
		}
		
		Page<PlanSuspensionResponseModel> list = (Page<PlanSuspensionResponseModel>) planSuspensionService.fetchPlanSuspensionList(planSuspensionRequestModel);

		if (list != null && !list.isEmpty()) {
			// return new ResponseEntity<>(list, HttpStatus.OK);
			
			
			return new TPMSGateway<>(true, HttpStatus.OK, list);
		} else {

			return new TPMSGateway<>(HttpStatus.OK, list, "List is NULL");
		}
		/*
		 * try {
		 * list=planSuspensionService.fetchPlanSuspensionList(planSuspensionRequestModel
		 * ); return ResponseEntity.of(Optional.of(list)); } catch (Exception e) { //
		 * TODO: handle exception e.printStackTrace(); return
		 * ResponseEntity.badRequest().build(); }
		 */
//		return null;

		/*
		 * if(list.size()<=0) { return
		 * ResponseEntity.status(HttpStatus.NOT_FOUND).build(); } return
		 * ResponseEntity.of(Optional.of(list));
		 */
		/*
		 * if(list != null && !list.isEmpty()) { return new ResponseEntity<>(list,
		 * HttpStatus.OK); }else { return new ResponseEntity<>("Data not found",
		 * HttpStatus.OK); }
		 */
	}

	@Operation(summary = "Insert Plan Suspension Details Api")

	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Plan Suspension has been Successfully inserted ", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = PlanSuspensionRequestModel.class)) }),

			@ApiResponse(responseCode = "401", description = "You are not authorized to view the resource", content = @Content),

			@ApiResponse(responseCode = "403", description = "Accessing the resource you were trying to reach is forbidden", content = @Content),

			@ApiResponse(responseCode = "404", description = "Plan Suspension details are not found", content = @Content) })

	@PostMapping(value = "/insertPlan", produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_ATOM_XML_VALUE }, consumes = { MediaType.APPLICATION_JSON_VALUE,
					MediaType.APPLICATION_ATOM_XML_VALUE })
	public TPMSGateway<Object> insertPlanSuspensionDetails(@RequestBody @Valid PlanSuspensionRequestModel2 planSuspensionRequestModel2) throws ParseException {

		logger.info("Starting with insert plan suspension api..");
		Integer responseId = planSuspensionService.insertPlanSuspensionDetails(planSuspensionRequestModel2);

		if (responseId != null && responseId.intValue() > 0) {
			return new TPMSGateway<Object>(true, HttpStatus.OK, "Record inserted successfully");
		} else if (responseId == null || responseId.intValue() == 0) {
			return new TPMSGateway<Object>(false, HttpStatus.OK, "This account is already under suspension");
		} else {
			return new TPMSGateway<Object>(false, HttpStatus.OK, "Record insertion got failed");
		}

	}

	@Operation(summary = "Update Plan Suspension Details Api")

	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Plan Suspension has been updated Successfully", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = PlanSuspensionRequestModel.class)) }),
			@ApiResponse(responseCode = "401", description = "You are not authorized to view the resource", content = @Content),
			@ApiResponse(responseCode = "403", description = "Accessing the resource you were trying to reach is forbidden", content = @Content),
			@ApiResponse(responseCode = "404", description = "Plan Suspension details are not found", content = @Content) })
	@PostMapping(value = "/suspendPlan", produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_ATOM_XML_VALUE }, consumes = { MediaType.APPLICATION_JSON_VALUE,
					MediaType.APPLICATION_ATOM_XML_VALUE })
	public TPMSGateway<Object> updatePlanSuspensionDetails(@RequestBody @Valid PlanSuspensionRequestModel2 planSuspensionRequestModel2) throws ParseException {
		logger.info("Starting with update plan suspension api..");
		Integer responseId = planSuspensionService.updatePlanSuspensionDetails(planSuspensionRequestModel2);
		if (responseId != null && responseId.intValue() > 0) {
			return new TPMSGateway<Object>(true, HttpStatus.OK, "Record updated successfully");
		} else if (responseId == null || responseId.intValue() == 0) {
			return new TPMSGateway<Object>(false, HttpStatus.OK, "The Plan Suspension for this account is already cancelled");
		} else {
			return new TPMSGateway<Object>(false, HttpStatus.OK, "Plan suspension cancellation got failed");
		}
	}

}
