package com.conduent.tpms.iag.utility;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class FileUtilityClass {

	private static final Logger log = LoggerFactory.getLogger(FileUtilityClass.class);

	/**
	 * 
	 * @param dirLocation
	 * @return
	 * @throws IOException
	 */
	public List<File> getAllfilesFromSrcDirectory(String dirLocation) throws IOException {
		try (Stream<Path> paths = Files.list(Paths.get(dirLocation));) {
			return paths.map(Path::toFile).distinct().collect(Collectors.toList());
		}
	}

	/**
	 * 
	 * @param dirLocation
	 * @param fileNameWithExt
	 * @return
	 */
	public File getFileFromDirectory(String dirLocation, String fileNameWithExt) {
		List<File> filelist = null;
		log.info("File name to pick from PROC: {}", fileNameWithExt);
		log.info("PROC dir location: {}", dirLocation);
		try (Stream<Path> paths = Files.list(Paths.get(dirLocation));) {
			filelist = paths.map(Path::toFile).filter(f -> f.getName().equals(fileNameWithExt)).distinct()
					.collect(Collectors.toList());
			if (!filelist.isEmpty()) {
				log.info("File from PROC: {}", filelist.get(0).getAbsolutePath());
				return filelist.get(0);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
