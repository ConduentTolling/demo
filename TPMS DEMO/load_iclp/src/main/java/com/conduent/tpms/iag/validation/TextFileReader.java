package com.conduent.tpms.iag.validation;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class TextFileReader {
	private static final Logger log = LoggerFactory.getLogger(TextFileReader.class);

	private File file;
	private BufferedReader reader;
	String prviousLine = null;
	String currentLine =  null;
	
	
	public String readLine() {
		try {
			return reader.readLine();
		} catch (IOException e) {
			return null;
		}
	}
	
	public void openFile(File file) throws FileNotFoundException{
		reader = new BufferedReader(new FileReader(file));
	}
	
	public void closeFile() throws IOException {
		if(reader != null || file != null) {
			reader.close();
			file = null;
		}
	}
	
	public long getRecordCount1(File file)
	{
		try (Stream<String> lines = Files.lines(file.toPath()))
		{
			long numOfLines = lines.count()-1;
			return numOfLines;
		} catch (IOException e) 
		{
			e.printStackTrace();
		}
		return 0L;
	}
	
	
	public long getRecordCount(File file)
	{
		log.info("TextFileReader getRecordCount() ");			

		try 
		{
			int count=0;
			StringBuilder sb = new StringBuilder();
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);

			String s = br.readLine();
			while (s!=null)
			{
				count++;
				s = br.readLine();
			}
			br.close();
			return count-1;
		} catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0L;
	}
}
