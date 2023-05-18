package com.conduent.tpms.iag.validation;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOCase;
import org.apache.commons.io.filefilter.WildcardFileFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


@Service	
public class FileUtil {
//	
//	@Autowired
//	protected TAgencyDao tAgencyDao;
	
	private static final Logger logger = LoggerFactory.getLogger(FileUtil.class);


//	public List<File> getAllfilesFromSrcDirectory(String dirLocation) throws IOException {
//		try (Stream<Path> paths = Files.list(Paths.get(dirLocation ));) { 
//			logger.info("paths:"+paths);
//			return paths.map(Path::toFile).distinct().collect(Collectors.toList());
//		}  
//	}
	
	public List<File> getAllfilesFromSrcDirectory(String dirLocation,String awayAgencyId,String fileType) throws IOException {
		File directoryPath = new File(dirLocation+"/"+awayAgencyId);
		logger.info("Files loaded from directory {}", directoryPath);
		 FileFilter fileFilter = new WildcardFileFilter(awayAgencyId+"*."+fileType, IOCase.INSENSITIVE); 

		 if(directoryPath.listFiles(fileFilter)!=null)
			return Arrays.asList(directoryPath.listFiles(fileFilter)).stream().distinct().collect(Collectors.toList());
		 else {
			 logger.info("folder is not present for Agency Id {}" ,awayAgencyId);
			 return new ArrayList<File>();
		 }
	}  
//	}
	
	public void copyFileUsingApacheCommonsIO(File source, File dest) throws IOException {
	        FileUtils.copyFile(source, dest);
	          
	    }
}
