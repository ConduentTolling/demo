package com.conduent.tpms.qatp.validation;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.util.stream.Stream;
import org.springframework.stereotype.Component;

@Component
public class TextFileReader 
{

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

	public void openFile(File file) throws FileNotFoundException,IOException{
		reader = new BufferedReader(new FileReader(file));
		reader.mark(1);

	}

	public void closeFile() throws IOException {
		if(reader != null ) 
		{
			reader.close();
		}
	}

	public long getRecordCount(File file)
	{
		try (Stream<String> lines = Files.lines(file.toPath()))
		{
			long numOfLines = lines.count();
			return numOfLines;
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		return 0L;
	}

	public long getRecordCount1(File file)
	{
		try 
		{
			int count=0;
			StringBuilder sb = new StringBuilder();
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);

			String s = br.readLine();
			while (s!=null)
			{
				if(s.contains("["))
				{
					count++;
				}
				s = br.readLine();
			}
			br.close();
			return count;
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return 0L;
	}
	public void reset() throws IOException
	{
		if(reader!=null)
		{
			reader.reset();
		}
	}
}
