package com.conduent.tpms.iag.validation;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class FileUtil {

	public List<File> getAllfilesFromSrcDirectory(String dirLocation,String filterText) throws IOException {

		File dir = new File(dirLocation);
		File[] files = dir.listFiles(new FilenameFilter()
		{
		public boolean accept(File dir, String name)
		{
		return name.toLowerCase().endsWith("."+filterText.toLowerCase());
		}
		});
		return Arrays.asList(files);
	}
	
	public List<File> getAllfilesFromSrcDirectory(String dirLocation) throws IOException {
		try (Stream<Path> paths = Files.list(Paths.get(dirLocation ));) { 
			return paths.map(Path::toFile).collect(Collectors.toList());
		}  
	}
}
