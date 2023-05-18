package com.conduent.tpms.inrx.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.conduent.tpms.inrx.validation.InrxFileParserImpl;


@Service
public class IRXNService {
	
		
		@Autowired
		protected InrxFileParserImpl InrxFileParserImpl;
		
		private static final Logger logger = LoggerFactory.getLogger(IRXNService.class);
		List<String> processedfiles=new ArrayList<>(); 
		
		public void processFiles(File file) throws IOException, InterruptedException
		{
			
			String fileType = FilenameUtils.getExtension(file.getName());
			
			InrxFileParserImpl.initialize(fileType);
			
			InrxFileParserImpl.loadIRXNConfigurationMapping(fileType);

			logger.info("Async Call Thread: "+Thread.currentThread().getName() +" FilName :"+file.getName());

			InrxFileParserImpl.fileParseTemplate(fileType , file);

		}


}
