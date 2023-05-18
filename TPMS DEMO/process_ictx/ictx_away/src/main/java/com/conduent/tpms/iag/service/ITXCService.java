package com.conduent.tpms.iag.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FilenameUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.conduent.tpms.iag.validation.FileUtil;
import com.conduent.tpms.iag.validation.ItxcFileParserImpl;

@Service
public class ITXCService {

	@Autowired
	protected ItxcFileParserImpl fileParserImpl;

	private static final Logger logger = LoggerFactory.getLogger(ITXCService.class);
	private FileUtil fileUtil = new FileUtil();
	List<String> processedfiles = new ArrayList<>();

	public void processFiles(File file) throws IOException, InterruptedException {
		fileParserImpl.initialize();
		String fileType = FilenameUtils.getExtension(file.getName());
		fileParserImpl.loadConfigurationMappingITXC(fileType);
		logger.info("Async Call Thread: " + Thread.currentThread().getName() + " FilName :" + file.getName());

		fileParserImpl.fileParseTemplate(file);

	}

}
