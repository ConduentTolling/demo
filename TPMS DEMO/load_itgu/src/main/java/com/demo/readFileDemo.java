package com.demo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class readFileDemo {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		String dirLocation = "C:\\Users\\mayurig1\\Documents\\conduent\\tag_status\\008_20200713194418_ITAG";
		List<File> files = Files.list(Paths.get(dirLocation))
				// .filter(path -> path.toString().endsWith(".ITAG"))
				.map(Path::toFile).collect(Collectors.toList());
		Collections.sort(files, Comparator.comparingLong(File::lastModified));

		String itagFileName = files.get(0).getName();
		FileReader fileReader = new FileReader(itagFileName);

	    try (BufferedReader bufferedReader = new BufferedReader(fileReader)) {
	      String line;
	      String headerLine = bufferedReader.readLine();
	      System.out.println("headerline"+headerLine);
	      while((line = bufferedReader.readLine()) != null) {
	    	//  String headerLine = line;
	    	  String detail =line;
	    	  System.out.println("detail"+detail);
	    	  break;
	    	  
	    	  }
	      }
	}

}
