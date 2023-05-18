package com.conduent.tpms.iag.service;

import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.conduent.tpms.iag.validation.InvalidTagFileParserImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.conduent.tpms.iag.validation.FileUtil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class IITCService {
	
	@Autowired
	protected InvalidTagFileParserImpl genericIITCFileParserImpl;
	
	private static final Logger logger = LoggerFactory.getLogger(IITCService.class);
	private FileUtil fileUtil = new FileUtil();
	List<String> processedfiles=new ArrayList<>(); 
	
	public void processFiles(File file) throws IOException, InterruptedException{
		genericIITCFileParserImpl.initialize();
		String fileType=FilenameUtils.getExtension(file.getName());
		genericIITCFileParserImpl.loadConfigurationMapping(fileType);
		logger.info("Async Call Thread: "+Thread.currentThread().getName() +" FilName :"+file.getName());

		
		genericIITCFileParserImpl.fileParseTemplate( file);

	}

}
