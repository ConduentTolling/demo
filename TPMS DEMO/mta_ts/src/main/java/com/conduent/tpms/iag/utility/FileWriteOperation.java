package com.conduent.tpms.iag.utility;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 
 * @author urvashic
 *
 */
@Component
public class FileWriteOperation {

	private static final Logger log = LoggerFactory.getLogger(FileWriteOperation.class);
	
	
	/**
	 * 
	 * @param fileName
	 * @param dstDirPath
	 * @return
	 */
	public Long getRecordCountFromFile(String fileName,String dstDirPath) {
		File file = new File(dstDirPath);
		try (Stream<String> lines = Files.lines(file.toPath())) {
			long numOfLines = lines.count();
			if(numOfLines>=1) {
				return numOfLines-1;
			}
			return numOfLines;
		} catch (IOException e) {
			log.error("File Record count operation failed: {}", file.getName());
		}
		return 0L;
	}
	
	/**
	 * 
	 * @param file
	 * @return
	 */
	public Long getRecordCountFromFile(File file) {
	//	File file = new File(dstDirPath);
		try (Stream<String> lines = Files.lines(file.toPath())) {
			long numOfLines = lines.count();
			if(numOfLines>=1) {
				return numOfLines-1;
			}
			return numOfLines;
		} catch (IOException e) {
			log.error("File Record count operation failed: {}", file.getName());
		}
		return 0L;
	}

	/**
	 * 
	 * @param src
	 * @param target
	 * @return
	 */
	public boolean renameFile(File src, File target) {
		return src.renameTo(target);
	}
	
}
