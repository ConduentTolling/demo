package com.conduent.tpms.ictx.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.conduent.tpms.iag.validation.FileUtil;
import com.conduent.tpms.iag.validation.IctxFileParserImpl;

import io.swagger.annotations.Scope;




	@Service
	public class LoadAllICTXService {

		
		@Value("${config.urlDestination}")
		private String urlDestination;
		

		@Autowired
		protected IctxFileParserImpl fileParserImpl;


	private static final Logger logger = LoggerFactory.getLogger(LoadAllICTXService.class);
	private FileUtil fileUtil = new FileUtil();
	List<String> processedfiles=new ArrayList<>(); 

	
	public void processFiles(File file) throws IOException, InterruptedException{
		fileParserImpl.initialize();
		String fileType=FilenameUtils.getExtension(file.getName());
		fileParserImpl.loadConfigurationMapping(fileType);
		logger.info("Async Call Thread: "+Thread.currentThread().getName() +" FilName :"+file.getName());

		
		fileParserImpl.fileParseTemplate( file);

	}


	


	}

