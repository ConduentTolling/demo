package com.conduent.tpms.qatp.validation;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class FileUtil 
{

	public List<File> getAllfilesFromSrcDirectory(String dirLocation,String filterText) throws IOException 
	{
		/*
		 * final Path p = Paths.get(dirLocation); final PathMatcher filter =
		 * p.getFileSystem().getPathMatcher("."+filterText); try (Stream<Path> paths =
		 * Files.list(p);) { return
		 * paths.filter(filter::matches).map(Path::toFile).collect(Collectors.toList());
		 * }
		 */
		
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
}
