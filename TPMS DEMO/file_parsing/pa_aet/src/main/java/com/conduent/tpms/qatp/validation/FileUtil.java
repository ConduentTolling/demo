package com.conduent.tpms.qatp.validation;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class FileUtil {

	public List<File> getAllfilesFromSrcDirectory(String dirLocation,String filterText) throws IOException {
		/*
		 * try (Stream<Path> paths = Files.list(Paths.get(dirLocation));) { return
		 * paths.map(Path::toFile).collect(Collectors.toList()); }
		 */
//		File dir = new File(dirLocation);
//		File[] files = dir.listFiles(new FilenameFilter() 
//		{
//		    public boolean accept(File dir, String name) 
//		    {
//		        return name.toLowerCase().endsWith("."+filterText.toLowerCase());
//		    }
//		});
//		return Arrays.asList(files);
		
		File dir = new File(dirLocation);

		File[] lockFiles = dir.listFiles(new FilenameFilter() {
			public boolean accept(File dir, String name) {
				return name.toLowerCase().endsWith("." + "lock".toLowerCase());
			}
		});

		List<String> lockFileName = new ArrayList<>();
		for (File f : lockFiles) {
			lockFileName.add(f.getName().split("\\.")[0]);

		}

		File[] files = dir.listFiles(new FilenameFilter() {
			public boolean accept(File dir, String name) {
				return name.toLowerCase().endsWith("." + filterText.toLowerCase());
			}
		});

		List<File> result = new ArrayList<>();

		for (File f : files) {
			if (!lockFileName.contains(f.getName().split("\\.")[0])) {
				result.add(f);
				File lock = new File(dir.getAbsolutePath() + "\\" + f.getName() + ".lock");
				lock.createNewFile();
			}
		}
		return result;
	}
	
	public synchronized List<File> getAllfilesFromSrcDirectoryParallel(String dirLocation,String filterText) throws IOException 
	{
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
