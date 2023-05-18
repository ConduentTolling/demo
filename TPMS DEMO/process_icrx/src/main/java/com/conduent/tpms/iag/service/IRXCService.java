package com.conduent.tpms.iag.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.conduent.tpms.iag.validation.IcrxFileParserImpl;


@Service
public class IRXCService {
	
		
		@Autowired
		protected IcrxFileParserImpl IcrxFileParserImpl;
		
		private static final Logger logger = LoggerFactory.getLogger(IRXCService.class);
		List<String> processedfiles=new ArrayList<>(); 
		
		public void processFiles(File file) throws IOException, InterruptedException
		{
			
			String fileType = FilenameUtils.getExtension(file.getName());
			
			IcrxFileParserImpl.initialize(fileType);
			
			IcrxFileParserImpl.loadIRXCConfigurationMapping(fileType);

			logger.info("Async Call Thread: "+Thread.currentThread().getName() +" FilName :"+file.getName());

			IcrxFileParserImpl.fileParseTemplate(fileType , file);

		}


}
