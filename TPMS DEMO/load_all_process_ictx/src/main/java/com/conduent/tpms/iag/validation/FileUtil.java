package com.conduent.tpms.iag.validation;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.conduent.tpms.iag.controller.LoadAllICTXProcessController;


public class FileUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(FileUtil.class);


	public List<File> getAllfilesFromSrcDirectory(String dirLocation) throws IOException {
		try (Stream<Path> paths = Files.list(Paths.get(dirLocation ));) { 
			logger.info("paths:"+paths);
			return paths.map(Path::toFile).distinct().collect(Collectors.toList());
		}  
	}
	
	public void copyFileUsingApacheCommonsIO(File source, File dest) throws IOException {
	        FileUtils.copyFile(source, dest);
	          
	    }
}
