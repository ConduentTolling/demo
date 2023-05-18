package com.conduent.tpms.iag.controller;


import java.io.File;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.conduent.tpms.ictx.service.LoadAllICTXService;

@RestController
public class LoadAllICTXProcessController {

	private static final Logger logger = LoggerFactory.getLogger(LoadAllICTXProcessController.class);

		
//	@Autowired
//	LoadAllICTXService loadAllICTXService;
	
	 @Autowired
	 private ApplicationContext applicationContext;
	
//	@GetMapping("/loadAllfilesICTX/")
//	public void  loadAllfilesICTX(@RequestParam String fileType) throws IOException, InterruptedException {
//	
//	logger.info("in CTRL");
//	 loadAllICTXService.loadAllFiles(fileType);
//
//
//	}
	@PostMapping("/processAllfilesICTX/file")
	public void  loadAllfilesICTX(@RequestBody File file) throws IOException, InterruptedException {
		LoadAllICTXService loadAllICTXService = applicationContext.getBean(LoadAllICTXService.class);
		logger.info("Instance :", loadAllICTXService);
	logger.info("in CTRL"+Thread.currentThread().getName());
	
	 loadAllICTXService.processFiles(file);


	}
	


	
}
