package com.conduent.tpms.iag.service;

import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.conduent.tpms.iag.validation.GenericITguFileParserImpl;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ITGUService {
	
	@Autowired
	protected GenericITguFileParserImpl genericITGUFileParserImpl;
	
	private static final Logger logger = LoggerFactory.getLogger(ITGUService.class);
	List<String> processedfiles=new ArrayList<>(); 
	
	public void processFiles(File file) throws IOException, InterruptedException
	{
		genericITGUFileParserImpl.initialize();
		
		String fileType = FilenameUtils.getExtension(file.getName());
		
		genericITGUFileParserImpl.loadConfigurationMapping(fileType);

		logger.info("Async Call Thread: "+Thread.currentThread().getName() +" FilName :"+file.getName());

		genericITGUFileParserImpl.fileParseTemplate(file);

	}

}
