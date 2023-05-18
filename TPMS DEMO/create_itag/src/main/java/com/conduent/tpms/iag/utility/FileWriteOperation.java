package com.conduent.tpms.iag.utility;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
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

	private static final Logger logger = LoggerFactory.getLogger(FileWriteOperation.class);
	
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
			logger.error("File Record count operation failed: {}", file.getName());
		}
		return 0L;
	}

	public boolean overwriteFileData(String fileName, String data, int position,String dstDirPath) {
		File tempfile = new File(dstDirPath, fileName);
		try (RandomAccessFile file = new RandomAccessFile(tempfile.getAbsolutePath(), "rw");) {
			file.seek(position);
			file.write(data.getBytes());
			return true;
		} catch (FileNotFoundException e) {
			logger.error("File not exists in the directory: {}", tempfile.getAbsolutePath());
			return false;
		} catch (IOException e) {
			logger.error("File overwrite operation failed: {}", tempfile.getAbsolutePath());
			return false;
		}
	}

	public boolean renameFile(File src, File target) {
		return src.renameTo(target);
	}
	
}
