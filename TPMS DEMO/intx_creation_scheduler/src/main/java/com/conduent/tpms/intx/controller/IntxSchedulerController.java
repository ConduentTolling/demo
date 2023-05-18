package com.conduent.tpms.intx.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.conduent.tpms.intx.constant.IntxSchedulerConstant;
import com.conduent.tpms.intx.service.IntxSchedulerService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api")
public class IntxSchedulerController {

	private static final Logger logger = LoggerFactory.getLogger(IntxSchedulerController.class);

	@Autowired
	IntxSchedulerService intxProcessService;

	@GetMapping("/intx-scheduler/process")
	@ApiOperation("Create Intx Files")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Intx Process completed.",response = String.class )})
	public String startIntxProcess() {
		logger.info("Intx Process started.");
		intxProcessService.process(IntxSchedulerConstant.FILE_TYPE_INTX);
		logger.info("Intx Process completed.");
		return "Intx Process completed.";
	}
	
	@GetMapping("/itxn-scheduler/process")
	@ApiOperation("Create Itxn Files")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Itxn Process completed.",response = String.class )})
    public String startItxnProcess() {
        logger.info("Itxn Process started.");
        intxProcessService.process(IntxSchedulerConstant.FILE_TYPE_ITXN);
        logger.info("Itxn Process completed.");
        return "Itxn Process completed.";
    }

}
