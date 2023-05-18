package com.conduent.tpms.unmatched.controller;

import java.text.ParseException;
import java.time.format.DateTimeFormatter;

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

import com.conduent.app.timezone.utility.TimeZoneConv;
import com.conduent.tpms.umatched.exception.TPMSGateway;
import com.conduent.tpms.umatched.exception.TPMSGlobalException;
import com.conduent.tpms.umatched.exception.TPMSGlobalExceptionHandler;
import com.conduent.tpms.unmatched.constant.Constants;
import com.conduent.tpms.unmatched.model.APIInputs;
import com.conduent.tpms.unmatched.service.GetMaxTollAmountCRMService;
import com.conduent.tpms.unmatched.service.GetMaxTollAmountService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;




@RestController
@RequestMapping("/api")
public class TollUnmatchedTxController 
{
	String methodName = null;
	String className = this.getClass().getName();
	HttpHeaders headers = new HttpHeaders();

	private static final Logger logger = LoggerFactory.getLogger(TollUnmatchedTxController.class);

	@Autowired
	GetMaxTollAmountService getMaxTollService;
	
	@Autowired
	GetMaxTollAmountCRMService getMaxTollServiceCRM;
	
	@Autowired
	TPMSGlobalExceptionHandler tPMSGlobalExceptionHandler;
	
	@Autowired
	TimeZoneConv timeZoneConv;
	

	@RequestMapping(value = "/max_toll_amount",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE, 
	        produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK",response = TPMSGateway.class )})
    @ApiOperation(value="Calculating max toll amount")
	public TPMSGateway<?> getMaxTollAmount(@RequestBody APIInputs inputs) throws ParseException
	{
		logger.info("Inputs are : {}",inputs.toString());
		MDC.put("logFileName", "MAX_TOLL_API_".concat(timeZoneConv.currentTime().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"))));
        logger.info("logFileName: {}",MDC.get("logFileName"));
        
        if(inputs.getDataSource()!=null && inputs.getDataSource().equals(Constants.TPMS))
        {
        	double result = getMaxTollService.getMaxTollAmount(inputs);
			logger.info("Max Toll Amount is {}",result);
			
			return new TPMSGateway<>(true, HttpStatus.OK,result);
        }
        else if (inputs.getDataSource()!=null && inputs.getDataSource().equals(Constants.CRM))
        {
        	//crm call
        	double result = getMaxTollServiceCRM.getMaxTollAmount(inputs);
			logger.info("Max Toll Amount is {}",result);
			
        	return new TPMSGateway<>(true, HttpStatus.OK,result);
        }
		else 
		{
			throw new TPMSGlobalException("Incorrect Data Source",className, methodName);
		}
	}
}
