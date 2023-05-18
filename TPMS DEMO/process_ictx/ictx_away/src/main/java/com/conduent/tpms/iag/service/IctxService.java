package com.conduent.tpms.iag.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.conduent.tpms.iag.validation.FileUtil;
import com.conduent.tpms.iag.validation.IctxFileParserImpl;
import com.google.common.base.Stopwatch;


    @Service
	public class IctxService {
		

	@Autowired
	protected IctxFileParserImpl fileParserImpl;


	private static final Logger logger = LoggerFactory.getLogger(IctxService.class);
	private FileUtil fileUtil = new FileUtil();
	List<String> processedfiles=new ArrayList<>(); 

	
	public void processFiles(File file) throws IOException, InterruptedException{
		fileParserImpl.initialize();
		String fileType=FilenameUtils.getExtension(file.getName());
		fileParserImpl.loadConfigurationMapping(fileType);
		Stopwatch stopwatch = Stopwatch.createStarted();
		
		fileParserImpl.fileParseTemplate( file);
		
		stopwatch.stop();
		long millis = stopwatch.elapsed(TimeUnit.MILLISECONDS);
		logger.info("Total time taken to execute processFiles {}",millis);

	}


}
