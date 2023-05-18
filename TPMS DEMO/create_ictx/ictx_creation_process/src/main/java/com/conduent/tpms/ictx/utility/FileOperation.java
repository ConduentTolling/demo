package com.conduent.tpms.ictx.utility;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.util.List;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * File Operation Class
 * 
 * @author deepeshb
 *
 */
@Component
public class FileOperation {

	private static final Logger logger = LoggerFactory.getLogger(FileOperation.class);

	/**
	 * Write to File
	 * 
	 * @param fileName
	 * @param dstDirPath
	 * @param records
	 * @return boolean
	 */
	public boolean writeToFile(String fileName, String dstDirPath, List<String> records) {
		File file = new File(dstDirPath, fileName);
		try (FileWriter fos = new FileWriter(file, true)) {
			for (int i = 0; i < records.size(); i++) {
				fos.write(records.get(i));
			}
			return true;
		} catch (FileNotFoundException e) {
			logger.error("File not exists in the directory: {}, Exception: {}", file.getName(), e);
			return false;
		} catch (IOException e) {
			logger.error("File write operation failed: {}, Exception: {}", file.getName(), e);
			return false;
		}

	}


	/**
	 * Get Record Count
	 * 
	 * @param fileName
	 * @param dstDirPath
	 * @return Long
	 */
	public Long getRecordCount(String fileName, String dstDirPath) {
		File file = new File(dstDirPath, fileName);
		try (Stream<String> lines = Files.lines(file.toPath())) {
			long numOfLines = lines.count();
			if (numOfLines >= 1) {
				return numOfLines - 1;
			}
			return numOfLines;
		} catch (IOException e) {
			logger.error("File Record count operation failed: {}", file.getName());
		}
		return 0L;
	}

	/**
	 * Overwrite mutiple file data
	 * 
	 * @param fileName
	 * @param records
	 * @param position
	 * @param dstDirPath
	 * @return boolean
	 */
	public boolean overwriteFileData(String fileName, List<String> records, Long position, String dstDirPath) {
		File tempfile = new File(dstDirPath, fileName);
		try (RandomAccessFile file = new RandomAccessFile(tempfile.getAbsolutePath(), "rw");) {
			file.seek(position);
			for (int i = 0; i < records.size(); i++) {
				file.write(records.get(i).getBytes());
			}
			return true;
		} catch (FileNotFoundException e) {
			logger.error("File not exists in the directory: {}", tempfile.getAbsolutePath());
			return false;
		} catch (IOException e) {
			logger.error("File overwrite operation failed: {}", tempfile.getAbsolutePath());
			return false;
		}
	}

	/**
	 * Overwrite file data
	 * 
	 * @param fileName
	 * @param data
	 * @param position
	 * @param dstDirPath
	 * @return
	 */
	public boolean overwriteFileData(String fileName, String data, int position, String dstDirPath) {
		File tempfile = new File(dstDirPath, fileName);
		try (RandomAccessFile file = new RandomAccessFile(tempfile.getAbsolutePath(), "rw");) {
			file.seek(position);
			logger.info("Data to write: {}, in position: {}",data,position);
			file.write(data.getBytes());
			return true;
		} catch (FileNotFoundException e) {
			logger.error("File not exists in the directory: {}", tempfile.getAbsolutePath());
			return false;
		} catch (IOException e) {
			logger.error("File overwrite operation failed: {}", tempfile.getAbsolutePath());
			return false;
		} catch (Exception e) {
			logger.info("Data cannot be written for reason: {}",e.getMessage());
			e.printStackTrace();
			return false;
		}
	}

	/**
	 *Rename File
	 * 
	 * @param src
	 * @param target
	 * @return boolean
	 */
	public boolean renameFile(File src, File target) {
		return src.renameTo(target);
	}
}
