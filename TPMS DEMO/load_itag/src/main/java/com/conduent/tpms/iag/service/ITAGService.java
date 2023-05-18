package com.conduent.tpms.iag.service;

import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.conduent.tpms.iag.validation.GenericITagFileParserImpl;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ITAGService {
	
	@Autowired
	protected GenericITagFileParserImpl genericITAGFileParserImpl;
	
	private static final Logger logger = LoggerFactory.getLogger(ITAGService.class);
	List<String> processedfiles=new ArrayList<>(); 
	
	public void processFiles(File file, int agencySequence) throws IOException, InterruptedException
	{
		genericITAGFileParserImpl.initialize();
		
		String fileType = FilenameUtils.getExtension(file.getName());
		
		genericITAGFileParserImpl.loadConfigurationMapping(fileType);

		logger.info("Async Call Thread: "+Thread.currentThread().getName() +" FilName :"+file.getName());

		genericITAGFileParserImpl.fileParseTemplate(file, agencySequence);

	}

}
