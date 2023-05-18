package com.conduent.tpms.iag.service;


import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.conduent.tpms.iag.validation.FileUtil;
import com.conduent.tpms.iag.validation.GenericICLPFileParserImpl;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ICLPService {
	
	@Autowired
	protected GenericICLPFileParserImpl genericICLPFileParserImpl;
	
	private static final Logger logger = LoggerFactory.getLogger(ICLPService.class);
	private FileUtil fileUtil = new FileUtil();
	List<String> processedfiles=new ArrayList<>(); 
	
	public void processFiles(File file , int agencySequence) throws IOException, InterruptedException{
		genericICLPFileParserImpl.initialize();
		String fileType=FilenameUtils.getExtension(file.getName());
		genericICLPFileParserImpl.loadConfigurationMapping(fileType);
		logger.info("Async Call Thread: "+Thread.currentThread().getName() +" FilName :"+file.getName());

		
		genericICLPFileParserImpl.fileParseTemplate(file,agencySequence);

	}

}

