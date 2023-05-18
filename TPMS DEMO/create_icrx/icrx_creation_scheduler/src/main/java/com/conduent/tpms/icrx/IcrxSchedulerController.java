package com.conduent.tpms.icrx;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.conduent.tpms.icrx.constants.IcrxSchedulerConstant;
import com.conduent.tpms.icrx.service.IcrxSchedulerService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * Icrx Controller
 * 
 * 
 * @author deepeshb
 *
 */
@RestController
@RequestMapping("/api/scheduler")
public class IcrxSchedulerController {

	@Autowired
	IcrxSchedulerService icrxProcessService;

	/**
	 * Start the ICRX process
	 */
	@GetMapping("/create/icrx")
	@ApiOperation("Creates reconcilliation files")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK",response = String.class )})
	public void startICRXProcess()
	{
		icrxProcessService.process(IcrxSchedulerConstant.ICTX);
	}
	
	/**
	 * Start the IRXC process
	 */
	@GetMapping("/create/irxc/")
	@ApiOperation("Creates reconcilliation files")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK",response = String.class )})
	public void startIRXCProcess()
	{
		icrxProcessService.process(IcrxSchedulerConstant.ITXC);
	} 

}
