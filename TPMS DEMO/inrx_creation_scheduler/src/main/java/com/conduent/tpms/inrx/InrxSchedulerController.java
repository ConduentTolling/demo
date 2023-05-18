package com.conduent.tpms.inrx;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.conduent.tpms.inrx.constants.InrxSchedulerConstant;
import com.conduent.tpms.inrx.service.InrxSchedulerService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * Inrx Controller
 * 
 * 
 * @author petetid
 *
 */
@RestController
@RequestMapping("/api/scheduler")
public class InrxSchedulerController {

	@Autowired
	InrxSchedulerService inrxProcessService;

	/**
	 * Start the INRX process
	 */
	@ApiOperation(value = "Scheduler for creation of INRX")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Job executed successfully", response = String.class) })
	@GetMapping("/create_inrx_scheduler")
	public void startINRXProcess()
	{
		inrxProcessService.process(InrxSchedulerConstant.INTX);
	}
	
	/**
	 * Start the IRXN process
	 */
	@ApiOperation(value = "Scheduler for creation of IRXN")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Job executed successfully", response = String.class) })
	@GetMapping("/create_irxn_scheduler")
	public void startIRXNProcess()
	{
		inrxProcessService.process(InrxSchedulerConstant.ITXN);
	} 

}
