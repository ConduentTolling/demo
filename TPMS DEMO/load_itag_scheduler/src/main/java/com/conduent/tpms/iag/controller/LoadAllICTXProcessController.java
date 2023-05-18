package com.conduent.tpms.iag.controller;


import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.conduent.tpms.iag.exception.InvalidFileTypeException;
import com.conduent.tpms.ictx.service.LoadAllICTXService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
public class LoadAllICTXProcessController {

	private static final Logger logger = LoggerFactory.getLogger(LoadAllICTXProcessController.class);

		
//	@Autowired
//	LoadAllICTXService loadAllICTXService;
	@Autowired
	ApplicationContext applicationContext;
	
	int count = 0;
	
	@GetMapping("/loadITGUfiles")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK",response = String.class )})
    @ApiOperation(value="Load ITAG and ICLP Files")
	public String loadAllfiles(@RequestParam String fileType) throws IOException, InterruptedException 
	{
		logger.info("Retrived file type :"+fileType);
		LoadAllICTXService loadAllICTXService = applicationContext.getBean(LoadAllICTXService.class);
		logger.info("in CTRL");
		loadAllICTXService.loadAllFiles(fileType);

		return "Job initiated successfully";
	 
	}
	
	@GetMapping("/loadITAGICLPFiles")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK",response = String.class )})
    @ApiOperation(value="Load ITAG and ICLP Files")
	public String loadITAGnICLPFiles(@RequestParam String fileType) throws IOException, InterruptedException, InvalidFileTypeException
	{
		logger.info("Starting ITAG/ICLP File Process..{}",fileType);
		LoadAllICTXService loadAllICTXService = applicationContext.getBean(LoadAllICTXService.class);
		loadAllICTXService.loadITAGICLPFile(fileType);
		
		return "Job initiated successfully ";
		
	}
	
	@GetMapping("/loadHomeITAGFiles")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK",response = String.class )})
    @ApiOperation(value="Load HOME ITAG  Files")
	public String loadHomeITAGFiles(@RequestParam String fileType) throws IOException, InterruptedException, InvalidFileTypeException
	{
		logger.info("Starting ITAG Home File Process..{}",fileType);
		LoadAllICTXService loadAllICTXService = applicationContext.getBean(LoadAllICTXService.class);
		loadAllICTXService.loadHomeITAGFile(fileType);
		
		return "Job initiated successfully ";
		
	}
	
	@GetMapping("/testService")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK",response = String.class )})
    @ApiOperation(value="Load ITAG and ICLP Files")
	public String test() {
	return "Success";
	}
}
