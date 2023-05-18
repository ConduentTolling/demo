package com.conduent.tpms.tollcalculation.controller;

import java.text.ParseException;
import java.time.format.DateTimeFormatter;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.conduent.tpms.tollcalculation.exception.TPMSGateway;
import com.conduent.tpms.tollcalculation.exception.TPMSGlobalException;
import com.conduent.tpms.tollcalculation.model.APIInputs;
import com.conduent.tpms.tollcalculation.model.APIOutput;
import com.conduent.tpms.tollcalculation.service.CalculateTollService;
import com.conduent.tpms.tollcalculation.utility.Validation;
import com.conduent.app.timezone.utility.TimeZoneConv;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api")
public class TollCalculationController 
{
	String methodName = null;
	String className = this.getClass().getName();
	HttpHeaders headers = new HttpHeaders();
	
	private static final Logger logger = LoggerFactory.getLogger(TollCalculationController.class);

	@Autowired
	CalculateTollService calculateTollService;

	@Autowired
	TimeZoneConv timeZoneConv;

	@RequestMapping(value = "/toll_calculation",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE, 
    produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK",response = TPMSGateway.class )})
    @ApiOperation(value="Calculating toll")
	public TPMSGateway<?> calculateToll(@RequestBody APIInputs inputs) throws ParseException 
	{	
			
		
			logger.info("Starting Toll Calculation with Inputs : {}", inputs.toString());
			MDC.put("logFileName", "TOLL_CALCULATION_".concat(timeZoneConv.currentTime().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"))));
			logger.info("logFileName: {}",MDC.get("logFileName"));
				        
			APIOutput result = calculateTollService.process(inputs);
			
			String message = checkResultForNull(result);
						
			return new TPMSGateway<>(true, HttpStatus.OK, message, result);		
	}

	private String checkResultForNull(APIOutput result) 
	{
		String message = "Amounts Found.";
		
		Double discount_fare = result.getDiscountFare();
		Double full_fare = result.getFullFare();
		Double extra_axle_charge = result.getExtraAxleCharge();
		Double extra_axle_charge_cash = result.getExtraAxleChargeCash();
		
		if( discount_fare == null || full_fare == null || extra_axle_charge == null || extra_axle_charge_cash == null)
		{
			message = "Amount cannot be null.";
		}
		
		return message;
	} 
}