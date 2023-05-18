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


public class FileUtil {

	public List<File> getAllfilesFromSrcDirectory(String dirLocation) throws IOException {
		try (Stream<Path> paths = Files.list(Paths.get(dirLocation ));) { 
			return paths.map(Path::toFile).collect(Collectors.toList());
		}  
	}
	
	public void copyFileUsingApacheCommonsIO(File source, File dest) throws IOException {
	        FileUtils.copyFile(source, dest);
	          
	    }
}
