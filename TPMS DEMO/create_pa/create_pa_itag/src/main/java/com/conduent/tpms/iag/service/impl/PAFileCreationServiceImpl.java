package com.conduent.tpms.iag.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.jvnet.hk2.annotations.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.conduent.tpms.iag.constants.Constants;
import com.conduent.tpms.iag.dao.IAGDao;
import com.conduent.tpms.iag.dto.MappingInfoDto;
import com.conduent.tpms.iag.model.TAGDevice;
import com.conduent.tpms.iag.service.IAGFileCreationSevice;
import com.conduent.tpms.iag.utility.TextFileReader;
import com.conduent.tpms.iag.utility.FileUtil;

@Service
@Component
public class PAFileCreationServiceImpl extends IAGFileCreationSevice {
	
	private static final Logger log = LoggerFactory.getLogger(PAFileCreationServiceImpl.class);
	
	String tagFileDest;
	String statFileDest;
	String workingDest;
	String srcDest;
	
	@Autowired
	IAGDao iagDao;
	
	@Autowired
	private TextFileReader textFileReader;
	
	String deviceno;

	List<String> listofdeviceno = new ArrayList<String>();
	
	List<String> taginfo = new ArrayList<String>();
	
	private int devicestatus;
	private FileUtil fileutil = new FileUtil();
	
	File file;
	String zipFileName;

	/**
	 * 
	 * @return File
	 */
	public File getFile() {
		return file;
	}
	/**
	 * 
	 * @param file
	 */
	public void setFile(File file) {
		this.file = file;
	}
	
	@Override
	public void initialize() {
		setIsHederPresentInFile(true);
		setIsDetailsPresentInFile(true);
		setIsTrailerPresentInFile(false);
	}
	
	//@Override
	public boolean createPAFile(List<TAGDevice> info) throws IOException {
		try {
			tagFileDest = parentDest.concat("//").concat(Constants.PA_FILES); 
			statFileDest = parentDest.concat("//").concat(Constants.STAT_FILES); 
			recordCountMap.put(Constants.TAG_SORTED_ALL, validationUtils.customFormatString(Constants.DEFAULT_COUNT, info.size()));
			if (!info.isEmpty()) {
				// Creating file 
				List<MappingInfoDto> nameProperties = getConfigurationMapping().getFileNameMappingInfo();
				fileName = buildFileSection(nameProperties, null);
				File file = new File(tagFileDest, fileName);
				FileWriter filewriter = new FileWriter(file);
				
				//Building TAG file header
				//After writing file
				List<MappingInfoDto> headerProperties = getConfigurationMapping().getHeaderMappingInfoList();
				String header = buildFileSection(headerProperties, null);

				if (header.length() == 66) {
					log.info("Writing header to PA file: {}",header);
					filewriter.write(header + "\n");
				} else {
					log.error("Invalid header: {}", header);
					filewriter.flush();
					filewriter.close();
					file.delete();
					return false;
				}

				//Build details
				List<MappingInfoDto> detailRecordProperties = getConfigurationMapping().getDetailRecMappingInfo();
				for (TAGDevice tagDevice : info) {
				boolean isValidRecord = validateTagRecord(tagDevice);	
				if(isValidRecord) {
					String record = buildFileSection(detailRecordProperties,tagDevice);	
					writeContentToFile(filewriter, record);
					updateRecordCounter(tagDevice);
					log.info("Records added to file is...{}",totalRecords);
					}
				else {
					skipCount++;
					log.info("Record not added to file: {}", tagDevice.toString());
				}
				
			}
					headerinfo.setRecordCount(String.valueOf(totalRecords));
					log.info("Total Record added ..{}",headerinfo.getRecordCount());
					filewriter.flush();
					filewriter.close();
				    //Overwriting header of TAG file
					overwriteFileHeader(file, headerProperties);
					
					//saving file to working dirc
					File workingdirFile = saveFileToWorkingDir(fileName,file);
					log.info("Loaded a copy of new file {} into working directory...", workingdirFile.getName());
					
					//making zip of file created
					File convertedFileName = convertFiletoZip(workingdirFile);
					log.info("Converted File Name is ..{}",convertedFileName);
					
					//move that zip to src dirc
					//srcDest = parentDest.concat("//").concat(Constants.SRC_DEST);
					srcDest = parentDest;
					log.info("Src File destination..{}",srcDest);
					log.info("Zip File Name:{}",convertedFileName.getName());
					transferFile(convertedFileName, srcDest);
					
					
					populateRecordCountInMap();
					summaryFileCreationService.buildSummaryFile(recordCountMap,Constants.PA, statFileDest,fileName,convertedFileName,zipFileCount);
					return true;
			}
			log.error("No Data Found in T_TAG_STATUS_ALLSORTED table.");
			return false;
		} catch (Exception e) {
			log.error("Error Occured: {}", e.getMessage());
			return false;
		} finally {
			skipCount = 0;
			totalRecords = 0;
			tsGood = 0;
			tsLow = 0;
			tsInvalid = 0;
			tsLost = 0;
			info.clear();
			tagDeviceList.clear();	
		}
	}
	
	private void writeContentToFile(FileWriter filewriter, String record ) {
		
		try {
			filewriter.write(record + "\r");
			//error recovery
		} catch (IOException e) {
			log.error("Exception occurred while writing record {}",record);
			e.printStackTrace();
		}
		log.info("Record added to file: {}", record);
		
	}
	
	
	@Override
	public void updateRecordCounter(TAGDevice tagDeviceObj) {

		if (tagDeviceObj.getItagTagStatus() == Constants.TS_GOOD) {
			tsGood++;
		}
		if (tagDeviceObj.getItagTagStatus() == Constants.TS_LOW) {
			tsLow++;
		}
		if (tagDeviceObj.getItagTagStatus() == Constants.TS_INVALID) {
			tsInvalid++;
		}
		if (tagDeviceObj.getItagTagStatus() == Constants.TS_LOST) {
			tsLost++;
		}
		
		totalRecords++;
	}

	/**
	 * 
	 * @param fileHeaderDto
	 * @param file
	 * @param headerProperties 
	 * @return
	 */
	@Override
	public boolean overwriteFileHeader(File file, List<MappingInfoDto> headerProperties) {
		boolean headerUpdatedInFile = false;
				
		//Building new header with latest record count in file
		String newLine = buildFileSection(headerProperties, null);
		
		try {
			String filePath = file.getAbsolutePath();
			Scanner sc = new Scanner(new File(filePath));
			StringBuffer buffer = new StringBuffer();
			String oldLine = null;
			if (sc.hasNextLine() != false) {
				oldLine = sc.nextLine();
			}
			Scanner sc1 = new Scanner(new File(filePath));
			while (sc1.hasNextLine()) {
				buffer.append(sc1.nextLine() + "\n");
			}

			String fileContents = buffer.toString();
			log.debug("Contents of the file: " + fileContents);
			
			sc.close();
			//String newLine = header.toString();
			log.info("Replacing PA file {} header",file.getName());
			log.info("Old header: {}",oldLine);
			log.info("New header: {}",newLine);
			fileContents = fileContents.replaceAll(oldLine, newLine);
			// remove extra line appended at the end
			fileContents = fileContents.substring(0, fileContents.length()-1);
			FileWriter writer = new FileWriter(filePath);
			log.debug("new data: {}", fileContents);
			writer.append(fileContents);
			writer.flush();
			writer.close();
			headerUpdatedInFile = true;
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return headerUpdatedInFile;
	}
	
	/**
	 * 
	 * @param TAGDeviceObj
	 * @return true/false
	 */
	
	private boolean validateTagRecord(TAGDevice tagDeviceObj) {
		boolean isValidRecord = false;
		// Validate device prefix
		
		if (masterDataCache.validateHomeDevice(tagDeviceObj.getDevicePrefix())) {
			if (validationUtils.onlyDigits(tagDeviceObj.getDeviceNo())) {
				if (validationUtils.validateTagStatus(tagDeviceObj.getItagTagStatus())) {
						isValidRecord = true;
				}
			}
		}
		return isValidRecord;
	}
	

 	private void populateRecordCountInMap() {
		log.info("Setting counters to recordCountMap..");
		//recordCountMap.put(Constants.TOTAL, headerinfo.getRecordCount()); 
		recordCountMap.put(Constants.TOTAL, validationUtils.customFormatString(Constants.DEFAULT_COUNT, totalRecords - skipCount));
		recordCountMap.put(Constants.GOOD, validationUtils.customFormatString(Constants.DEFAULT_COUNT, tsGood));
		recordCountMap.put(Constants.LOW_BAL, validationUtils.customFormatString(Constants.DEFAULT_COUNT, tsLow));
		recordCountMap.put(Constants.INVALID, validationUtils.customFormatString(Constants.DEFAULT_COUNT, tsInvalid));
		recordCountMap.put(Constants.LOST, validationUtils.customFormatString(Constants.DEFAULT_COUNT, tsLost));
		recordCountMap.put(Constants.SKIP, validationUtils.customFormatString(Constants.DEFAULT_COUNT,skipCount));
	}
 	
public File saveFileToWorkingDir(String workingfilename , File file) {
		
		/*
		 * Environment path
		 */
		workingDest = parentDest.concat("//").concat(Constants.WORKING_DEST).concat("//").concat(workingfilename);
		setFile(file);
		File workingFile = new File(workingDest);
		
		File temp = workingFile;
        if (temp.exists()) {
            temp.delete();
        }
		try 
		{
			fileutil.copyFileUsingApacheCommonsIO(getFile(), workingFile);
		} catch (IOException e) {
			e.getMessage();
			e.printStackTrace();
		}
		return workingFile;

	}

private File convertFiletoZip(File workingdirFile) throws IOException {
	
	String workingDestforZipFile = parentDest.concat("//").concat(Constants.WORKING_DEST).concat("//"); 
	
	log.info("zip file datetime: {}", workingdirFile.getName().substring(5,19));
	zipFileName = workingDestforZipFile.concat(Constants.FROM_ENTITY).concat(Constants.UNDERSCORE).concat(Constants.To_ENTITY).
			concat(Constants.UNDERSCORE).concat(workingdirFile.getName().substring(5,19)).concat(Constants.UNDERSCORE).
			concat(Constants.ATGU).concat(Constants.DOT).concat(Constants.zip);
	
	File ZipFileNAme = new File(zipFileName);
	
	try (FileOutputStream fos = new FileOutputStream(ZipFileNAme);
		    ZipOutputStream zos = new ZipOutputStream(fos);
		    FileInputStream in = new FileInputStream(workingDest);) 
	{
		   ZipEntry ze= new ZipEntry(fileName);
		      zos.putNextEntry(ze);
		   byte[] buffer = new byte[1024];
		   int len;
		   while ((len = in.read(buffer)) > 0) 
		   {
		    zos.write(buffer, 0, len);
		   }
		   
		log.info("Zip file for file name {} has been created in working directory..",workingdirFile.getName());
		zipFileCount++;
	} 
	catch (IOException e) 
	{
		   e.printStackTrace();
	}
	
	return ZipFileNAme;
}

/*
 * Transfers the Zip file
 */
public void transferFile(File srcFile, String destDirPath) throws IOException {
	log.info("Transfering files from working destination to src destination....");
	try {
		Path destDir = new File(destDirPath, srcFile.getName()).toPath();
		log.info("Destination path..{}",destDir);
		
		 File temp = destDir.toFile();
            if (temp.exists()) {
                temp.delete();
            }
		
		Path result = Files.move(Paths.get(srcFile.getAbsolutePath()), destDir);
		log.info("Result Path..{}",result);
		
		if (null != result) {
			log.info("File {} moved successfully to {}",srcFile.getName(),destDirPath);
		} else {
			log.info("File {} transfer failed.",srcFile.getName());
		}
	} catch (IOException iex) {
		iex.getMessage();
	}
}
}
