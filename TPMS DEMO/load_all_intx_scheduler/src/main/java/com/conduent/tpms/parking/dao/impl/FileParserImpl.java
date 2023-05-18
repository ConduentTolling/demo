package com.conduent.tpms.parking.dao.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.format.DateTimeParseException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.conduent.tpms.parking.constants.AckStatusCode;
import com.conduent.tpms.parking.constants.IITCConstants;
import com.conduent.tpms.parking.dao.TAgencyDao;
import com.conduent.tpms.parking.dao.TQatpMappingDao;
import com.conduent.tpms.parking.dto.AckFileInfoDto;
import com.conduent.tpms.parking.dto.AckFileWrapper;
import com.conduent.tpms.parking.dto.FileParserParameters;
import com.conduent.tpms.parking.dto.MappingInfoDto;
import com.conduent.tpms.parking.exception.CustomException;
import com.conduent.tpms.parking.exception.EmptyLineException;
import com.conduent.tpms.parking.exception.FileAlreadyProcessedException;
import com.conduent.tpms.parking.exception.InvalidFileNameException;
import com.conduent.tpms.parking.exception.InvalidFileStatusException;
import com.conduent.tpms.parking.exception.InvalidFileTypeException;
import com.conduent.tpms.parking.exception.InvalidRecordCountException;
import com.conduent.tpms.parking.exception.InvalidRecordException;
import com.conduent.tpms.parking.exception.InvlidFileHeaderException;
import com.conduent.tpms.parking.model.FileDetails;
import com.conduent.tpms.parking.model.XferControl;
import com.conduent.tpms.parking.utility.GenericValidation;
import com.conduent.tpms.parking.validation.FileUtil;
import com.conduent.tpms.parking.validation.TextFileReader;

public class FileParserImpl {

	private static final Logger log = LoggerFactory.getLogger(FileParserImpl.class);

	protected boolean isHederPresentInFile;
	protected boolean isDetailsPresentInFile;
	protected boolean isTrailerPresentInFile;

	@Autowired
	protected TQatpMappingDao tQatpMappingDao;

	@Autowired
	GenericValidation genericValidation;


	@Autowired
	private TextFileReader textFileReader;
	
	@Autowired
	protected TAgencyDao tAgencyDao;
//	
//	@Autowired
//	protected MasterDataCache masterDataCache;
	
	private FileUtil fileUtil = new FileUtil();
	private String fileType;
	private String fileFormat;
	private String agencyId;
	private String toAgencyId;

	private FileParserParameters configurationMapping;
	private FileParserParameters fileParserParameter = new FileParserParameters();
	private FileParserParameters fileParserParameterITXC = new FileParserParameters();
	protected String fileName;
	protected Long recordCount;
	public File newFile;
	protected XferControl xferControl;
	public File threadFile;
	
	/*
	 * Checkpoint table object
	 */
	protected FileDetails fileDetails;


	public Integer recordCounter;
	
	public File getNewFile() {
		return newFile;
	}

	public void setNewFile(File newFile) {
		this.newFile = newFile;
	}
	
	public File getThreadFile() {
		return threadFile;
	}

	public void setThreadFile(File threadFile) {
		this.threadFile = threadFile;
	}

	public String getToAgencyId() {
		return toAgencyId;
	}

	public void setToAgencyId(String toAgencyId) {
		this.toAgencyId = toAgencyId;
	}

	
	public void initialize() {
		
	}

	/**
	 * Starts processing of Ictx file.
	 * 
	 * @throws IOException
	 * @throws InterruptedException 
	 */
	public void fileParseTemplate(File file) throws IOException, InterruptedException {


							log.info("File {} added in job....",file.getName());
								try {
									log.info("File name {} taken for process", file.getName());
									recordCount = textFileReader.getRecordCount(file);
									log.info("PROC_UNPROC_ISSUE ===== >> 1 ##");
									validateAndProcessFile(file);
									log.info("PROC_UNPROC_ISSUE ===== >> 2 ##");
									AckStatusCode ackStatusCode = AckStatusCode.SUCCESS;
									log.info("PROC_UNPROC_ISSUE ===== >> 3 ##");
									AckFileWrapper ackObj = prePareAckFileMetaData(ackStatusCode, file);
									log.info("PROC_UNPROC_ISSUE ===== >> 4 ##");
									ackObj.setFileDestDir(getConfigurationMapping().getFileInfoDto().getProcDir());
									log.info("PROC_UNPROC_ISSUE ===== >> 5 ##");
									processAckFile(ackObj);
									log.info("PROC_UNPROC_ISSUE ===== >> 6 ##");
								} 
								catch (Exception ex) 
								{
									log.info("PROC_UNPROC_ISSUE ===== >> 7 ##");
									try {
										textFileReader.closeFile();
									} catch (IOException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									if (ex instanceof InvalidFileNameException) 
									{
										log.info("PROC_UNPROC_ISSUE ===== >> 8 ##");
										log.info("Invalid file name or date");
										try {
											transferFile(file, getConfigurationMapping().getFileInfoDto().getUnProcDir());
										} catch (IOException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
									} 
									else if (ex instanceof InvlidFileHeaderException) {
										log.info("PROC_UNPROC_ISSUE ===== >> 9 ##");
										AckStatusCode ackStatusCode = AckStatusCode.HEADER_FAIL;
										if(ex.getMessage().contains("Date mismatch") || ex.getMessage().contains("Time mismatch")) {
											ackStatusCode = AckStatusCode.DATE_MISMATCH;
										}
										AckFileWrapper ackObj = prePareAckFileMetaData(ackStatusCode, file);
										ackObj.setFileDestDir(getConfigurationMapping().getFileInfoDto().getUnProcDir());
										processAckFile(ackObj);
									} 
									else if (ex instanceof InvalidRecordCountException) {
										log.info("PROC_UNPROC_ISSUE ===== >> 10 ##");
										AckStatusCode ackStatusCode = AckStatusCode.INVALID_RECORD_COUNT;
										AckFileWrapper ackObj = prePareAckFileMetaData(ackStatusCode, file);
										ackObj.setFileDestDir(getConfigurationMapping().getFileInfoDto().getUnProcDir());
										processAckFile(ackObj);
									} 
									else if (ex instanceof FileAlreadyProcessedException) {
										log.info("PROC_UNPROC_ISSUE ===== >> 11 ##");
										AckStatusCode ackStatusCode = AckStatusCode.SUCCESS;
										AckFileWrapper ackObj = prePareAckFileMetaData(ackStatusCode, file);
										ackObj.setFileDestDir(getConfigurationMapping().getFileInfoDto().getProcDir());
										processAckFile(ackObj);
										log.info(ex.getMessage());
									}
									else if (ex instanceof InvalidRecordException) {
										log.info("PROC_UNPROC_ISSUE ===== >> 12 ##");
										AckStatusCode ackStatusCode = AckStatusCode.DETAIL_FAIL;
										AckFileWrapper ackObj = prePareAckFileMetaData(ackStatusCode, file);
										ackObj.setFileDestDir(getConfigurationMapping().getFileInfoDto().getUnProcDir());
										processAckFile(ackObj);
										log.info(ex.getMessage());
									}
									else if (ex instanceof InvalidFileStatusException) {
										log.info("PROC_UNPROC_ISSUE ===== >> 13 ##");
									} 
									else if (ex instanceof EmptyLineException) {
										log.info("PROC_UNPROC_ISSUE ===== >> 14 ##");
										log.info("Current line is empty..");
									}
									else if (ex instanceof InvalidFileTypeException) {
									}
									else if(ex instanceof DateTimeParseException) {
										log.info("PROC_UNPROC_ISSUE ===== >> 15 ##");
										AckStatusCode ackStatusCode = AckStatusCode.DATE_MISMATCH;
										AckFileWrapper ackObj = prePareAckFileMetaData(ackStatusCode, file);
										ackObj.setFileDestDir(getConfigurationMapping().getFileInfoDto().getUnProcDir());
										processAckFile(ackObj);
									}
									else {
										log.info("PROC_UNPROC_ISSUE ===== >> 16 ##");
										ex.printStackTrace();
									}
								}
								log.info("PROC_UNPROC_ISSUE ===== >> 18 ##");
								getMissingFilesForAgency();
								log.info("Exiting File processing ..");
						}
						


	
	
	public void getMissingFilesForAgency() {
		// TODO Auto-generated method stub
		
		
	}
	public void fileParseTemplateITXC(File file) throws IOException, InterruptedException {


						log.info("File {} added in job....",file.getName());
							try {
								log.info("File name {} taken for process", file.getName());
								recordCount = textFileReader.getRecordCount(file);
								log.info("PROC_UNPROC_ISSUE ===== >> 1 ##");
								validateAndProcessFile(file);
								log.info("PROC_UNPROC_ISSUE ===== >> 2 ##");
								AckStatusCode ackStatusCode = AckStatusCode.SUCCESS;
								log.info("PROC_UNPROC_ISSUE ===== >> 3 ##");
								AckFileWrapper ackObj = prePareAckFileMetaData(ackStatusCode, file);
								log.info("PROC_UNPROC_ISSUE ===== >> 4 ##");
								ackObj.setFileDestDir(getConfigurationMapping().getFileInfoDto().getProcDir());
								log.info("PROC_UNPROC_ISSUE ===== >> 5 ##");
								processAckFile(ackObj);
								log.info("PROC_UNPROC_ISSUE ===== >> 6 ##");
							} 
							catch (Exception ex) 
							{
								log.info("PROC_UNPROC_ISSUE ===== >> 7 ##");
								try {
									textFileReader.closeFile();
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								if (ex instanceof InvalidFileNameException) 
								{
									log.info("PROC_UNPROC_ISSUE ===== >> 8 ##");
									log.info("Invalid file name or date");
									try {
										transferFile(file, getConfigurationMapping().getFileInfoDto().getUnProcDir());
									} catch (IOException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
								} 
								else if (ex instanceof InvlidFileHeaderException) {
									log.info("PROC_UNPROC_ISSUE ===== >> 9 ##");
									AckStatusCode ackStatusCode = AckStatusCode.HEADER_FAIL;
									if(ex.getMessage().contains("Date mismatch") || ex.getMessage().contains("Time mismatch")) {
										ackStatusCode = AckStatusCode.DATE_MISMATCH;
									}
									AckFileWrapper ackObj = prePareAckFileMetaData(ackStatusCode, file);
									ackObj.setFileDestDir(getConfigurationMapping().getFileInfoDto().getUnProcDir());
									processAckFile(ackObj);
								} 
								else if (ex instanceof InvalidRecordCountException) {
									log.info("PROC_UNPROC_ISSUE ===== >> 10 ##");
									AckStatusCode ackStatusCode = AckStatusCode.INVALID_RECORD_COUNT;
									AckFileWrapper ackObj = prePareAckFileMetaData(ackStatusCode, file);
									ackObj.setFileDestDir(getConfigurationMapping().getFileInfoDto().getUnProcDir());
									processAckFile(ackObj);
								} 
								else if (ex instanceof FileAlreadyProcessedException) {
									log.info("PROC_UNPROC_ISSUE ===== >> 11 ##");
									AckStatusCode ackStatusCode = AckStatusCode.SUCCESS;
									AckFileWrapper ackObj = prePareAckFileMetaData(ackStatusCode, file);
									ackObj.setFileDestDir(getConfigurationMapping().getFileInfoDto().getProcDir());
									processAckFile(ackObj);
									log.info(ex.getMessage());
								}
								else if (ex instanceof InvalidRecordException) {
									log.info("PROC_UNPROC_ISSUE ===== >> 12 ##");
									AckStatusCode ackStatusCode = AckStatusCode.DETAIL_FAIL;
									AckFileWrapper ackObj = prePareAckFileMetaData(ackStatusCode, file);
									ackObj.setFileDestDir(getConfigurationMapping().getFileInfoDto().getUnProcDir());
									processAckFile(ackObj);
									log.info(ex.getMessage());
								}
								else if (ex instanceof InvalidFileStatusException) {
									log.info("PROC_UNPROC_ISSUE ===== >> 13 ##");
								} 
								else if (ex instanceof EmptyLineException) {
									log.info("PROC_UNPROC_ISSUE ===== >> 14 ##");
									log.info("Current line is empty..");
								}
								else if (ex instanceof InvalidFileTypeException) {
								}
								else if(ex instanceof DateTimeParseException) {
									log.info("PROC_UNPROC_ISSUE ===== >> 15 ##");
									AckStatusCode ackStatusCode = AckStatusCode.DATE_MISMATCH;
									AckFileWrapper ackObj = prePareAckFileMetaData(ackStatusCode, file);
									ackObj.setFileDestDir(getConfigurationMapping().getFileInfoDto().getUnProcDir());
									processAckFile(ackObj);
								}
								else {
									log.info("PROC_UNPROC_ISSUE ===== >> 16 ##");
									ex.printStackTrace();
								}
							}						
				
					
		
	
		getMissingFilesForAgency();
		log.info("Exiting File processing ..");
	} 
	
	

	/*
	 * Validated and process ICTXfile.
	 */

	public void validateAndProcessFile(File file) throws InvalidFileNameException, InvlidFileHeaderException, EmptyLineException, InvalidRecordException, InvalidRecordCountException,DateTimeParseException, InvalidFileStatusException, IOException, InvalidFileTypeException, FileAlreadyProcessedException {
		log.info("validateAndProcessFile ===== >> 1 ##");
		startFileProcess(file);
		log.info("validateAndProcessFile ===== >> 2 ##");
		validateAndProcessFileName(file.getName());
		log.info("validateAndProcessFile ===== >> 3 ##");
		textFileReader.openFile(file);

		String prviousLine = textFileReader.readLine();
		String currentLine = textFileReader.readLine();

		/*
		 * Handles empty file
		 */
		if (prviousLine == null) {

		}

		/*
		 * When only header is present.
		 */
		if (prviousLine != null && currentLine == null) {
		}

		if (prviousLine != null && currentLine != null) {
			if (isHederPresentInFile) {
				log.info("validateAndProcessFile ===== >> 4 ##");
				validateAndProcessHeader(prviousLine);
				log.info("validateAndProcessFile ===== >> 5 ##");
				validateRecordCount();
				log.info("validateAndProcessFile ===== >> 6 ##");
			} else {
				parseValidateAndProcessDetail(prviousLine);
				log.info("validateAndProcessFile ===== >> 7 ##");
			}
		}
		log.info("validateAndProcessFile ===== >> 8 ##");
		prviousLine = handleDetailLines(currentLine);

		if (isTrailerPresentInFile) {
			log.info("validateAndProcessFile ===== >> 9 ##");
			parseAndValidateFooter(prviousLine);
			log.info("validateAndProcessFile ===== >> 10 ##");
		} else { 
			if(prviousLine != null)
			{
				log.info("validateAndProcessFile ===== >> 11 ##");
				parseValidateAndProcessDetail(prviousLine);
				log.info("validateAndProcessFile ===== >> 12 ##");
			}
		}

		textFileReader.closeFile();
		log.info("validateAndProcessFile ===== >> 13 ##");
		finishFileProcess();
		log.info("validateAndProcessFile ===== >> 14 ##");

	}

	public void startFileProcess(File file) throws InvalidFileStatusException, IOException, InvalidFileNameException, InvalidFileTypeException {
		setFileName(file.getName());
		setNewFile(file);
		CheckValidFileType(file);
		xferControl = tAgencyDao.checkFileDownloaded(file.getName());
		if(xferControl==null)
		{
			log.info("File is not downloaded completely: {}", file.getName());
			throw new InvalidFileStatusException("File not downloded");
		}
	}

	public void validateAndProcessFileName(String fileName)
			throws InvalidFileNameException, InvlidFileHeaderException, IOException, FileAlreadyProcessedException, EmptyLineException, InvalidRecordException, DateTimeParseException {
		parseAndValidate(fileName, configurationMapping.getFileNameMappingInfo());

	}

	public void insertFileDetailsIntoCheckpoint() {
		// TODO Auto-generated method stub

	}

	/*
	 * Validates header
	 */
	public void validateAndProcessHeader(String line) throws InvalidFileNameException, InvlidFileHeaderException, EmptyLineException, InvalidRecordException,DateTimeParseException {
		parseAndValidate(line, configurationMapping.getHeaderMappingInfoList());
	}

	/*
	 * Validates detail record
	 */
	public void parseAndValidateDetails(String line) throws InvalidFileNameException, InvlidFileHeaderException, EmptyLineException, InvalidRecordException {
		parseAndValidate(line, configurationMapping.getDetailRecMappingInfo());
	}

	/*
	 * Validates footer
	 */
	public void parseAndValidateFooter(String line) throws InvalidFileNameException, InvlidFileHeaderException, EmptyLineException, InvalidRecordException {
		parseAndValidate(line, configurationMapping.getTrailerMappingInfoDto());
	}

	/*
	 * Loads configuration mapping details for IITC file from T_FIELD_MAPPING table
	 */
//	public void loadConfigurationMapping() {
//		getFileParserParameter().setFileType(IITCConstants.ICTX);
//		setConfigurationMapping(tQatpMappingDao.getMappingConfigurationDetails(getFileParserParameter()));
//	}
	
//	public void loadConfigurationMappingITXC() {
//		getFileParserParameterITXC().setFileType(ITXCConstants.ITXC);
//		setConfigurationMappingITXC(tQatpMappingDao.getMappingConfigurationDetailsITXC(getFileParserParameterITXC()));
//	}

	/*
	 * Validates field's according to their mapping from T_FIELD_MAPPING table
	 */
	public void doFieldMapping(String value, MappingInfoDto fMapping) throws InvalidFileNameException, InvlidFileHeaderException, DateTimeParseException, InvalidRecordException {
	}

	/*
	 * Parse IITC file according to their sections i.e Header, Detail
	 */
	public void parseAndValidate(String line, List<MappingInfoDto> mappings)
			throws InvalidFileNameException, InvlidFileHeaderException, EmptyLineException, InvalidRecordException, DateTimeParseException {
	
		boolean isValid = true;
		String etcTrxType="";
		String fileSection = mappings.get(0).getFieldType();

		processStartOfLine(fileSection);

		for (MappingInfoDto fieldMapping : mappings)

		{
			if (fileSection == null) {
				fileSection = fieldMapping.getFieldType();
			}
			String value = null;
			if(fieldMapping.getFieldName().equalsIgnoreCase(IITCConstants.D_ETC_TRX_TYPE)) {
				value = line.substring(fieldMapping.getStartPosition().intValue(),
						fieldMapping.getEndPosition().intValue());
				etcTrxType = value.trim();
			}
			if(fieldMapping.getFieldName().equalsIgnoreCase(IITCConstants.D_CUST_ZIP)) {
				value = line.substring(fieldMapping.getStartPosition().intValue(),
						line.length());
				isValid = genericValidation.doValidation(fieldMapping, value.trim());

				doFieldMapping(value.trim(), fieldMapping);
				log.info("Validation for field {} :value: {} valid: {}",fieldMapping.getFieldName(),value.trim(),isValid);
			} else if((fieldMapping.getFieldName().equalsIgnoreCase(IITCConstants.D_ETC_ENTRY_DATE)
					|| fieldMapping.getFieldName().equalsIgnoreCase(IITCConstants.D_ETC_ENTRY_TIME)
					|| fieldMapping.getFieldName().equalsIgnoreCase(IITCConstants.D_ETC_ENTRY_PLAZA)
					|| fieldMapping.getFieldName().equalsIgnoreCase(IITCConstants.D_ETC_ENTRY_LANE)) 
					&& etcTrxType.equalsIgnoreCase(IITCConstants.LOV_B)) { 				//validate date based on field D_ETC_TRX_TYPE

				value = line.substring(fieldMapping.getStartPosition().intValue(),
						fieldMapping.getEndPosition().intValue());	
				
				doFieldMapping(value.trim(), fieldMapping);
				log.info("Validation for field {} :value: {} valid: {}",fieldMapping.getFieldName(),value.trim(),isValid);
			
			} else {
				value = line.substring(fieldMapping.getStartPosition().intValue(),
						fieldMapping.getEndPosition().intValue());
				isValid = genericValidation.doValidation(fieldMapping, value);
				doFieldMapping(value.trim(), fieldMapping);
				log.info("Validation for field {} :value: {} valid: {}",fieldMapping.getFieldName(),value.trim(),isValid);
			}


			if (!isValid) {
				if (fieldMapping.getFieldType().equals(IITCConstants.FILENAME)) {
					throw new InvalidFileNameException("Invalid File Name format");
				}
				if (fieldMapping.getFieldType().equals(IITCConstants.HEADER)) {
					throw new InvlidFileHeaderException("Invalid File Header format");
				}
				if (fieldMapping.getFieldType().equals(IITCConstants.DETAIL)) {
					log.info("Invlalid record data in record {}",value);
					throw new InvalidRecordException("Invalid record data.");
				}
			}
		}
		processEndOfLine(fileSection);

	}

	public void processStartOfLine(String fileSection) {
		// TODO Auto-generated method stub

	}

	public void processEndOfLine(String fileSection) {
		// TODO Auto-generated method stub

	}

	/*
	 * Parses details record in Ictx file
	 */
	public void parseValidateAndProcessDetail(String prviousLine)
			throws InvalidFileNameException, InvlidFileHeaderException, EmptyLineException, InvalidRecordException {
		parseAndValidateDetails(prviousLine);
		processDetailLine();
	}

	/*
	 * Handles details record in ictx file
	 */
	public String handleDetailLines(String currentLine) throws InvalidFileNameException, InvlidFileHeaderException, EmptyLineException, InvalidRecordException {
		String prviousLine;
		prviousLine = currentLine;
		currentLine = textFileReader.readLine();

		do {
			parseValidateAndProcessDetail(prviousLine);
			prviousLine = currentLine;
			currentLine = textFileReader.readLine();
		} while (currentLine != null);
		return prviousLine;
	}

	public void processDetailLine() {

	}

	public void finishFileProcess() throws InvlidFileHeaderException, FileAlreadyProcessedException, InvalidRecordException {

	}

	/*
	 * Get IITC file's list
	 */
	public List<File> getAllFilesFromSourceFolder(String fileType) throws IOException {
		List<File> allfilesFromSrcDirectory = fileUtil
				.getAllfilesFromSrcDirectory(getConfigurationMapping().getFileInfoDto().getSrcDir(),fileType, fileType);
		log.info("List of files : {}",allfilesFromSrcDirectory.toString());
		return allfilesFromSrcDirectory;
	}
//	public List<File> getAllFilesFromSourceFolderITXC() throws IOException {
//		List<File> allfilesFromSrcDirectory = fileUtil
//				.getAllfilesFromSrcDirectory(getConfigurationMapping().getFileInfoDto().getSrcDir());
//		log.info("List of files : {}",allfilesFromSrcDirectory.toString());
//		return allfilesFromSrcDirectory;
//	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public String getFileFormat() {
		return fileFormat;
	}

	public void setFileFormat(String fileFormat) {
		this.fileFormat = fileFormat;
	}

	public String getAgencyId() {
		return agencyId;
	}

	public void setAgencyId(String agencyId) {
		this.agencyId = agencyId;
	}

	public FileParserParameters getFileParserParameter() {
		return fileParserParameter;
	}
	
	public FileParserParameters getFileParserParameterITXC() {
		return fileParserParameterITXC;
	}

//	public void setFileParserParameter(FileParserParameters fileParserParameter) {
//		this.fileParserParameter = fileParserParameter;
//	}

	public FileParserParameters getConfigurationMapping() {
		return configurationMapping;
	}

	public void setConfigurationMapping(FileParserParameters configurationMapping) {
		this.configurationMapping = configurationMapping;
	}
	
	public void setConfigurationMappingITXC(FileParserParameters configurationMapping) {
		this.configurationMapping = configurationMapping;
	}
	public Boolean getIsHederPresentInFile() {
		return isHederPresentInFile;
	}

	public void setIsHederPresentInFile(Boolean isHederPresentInFile) {
		this.isHederPresentInFile = isHederPresentInFile;
	}

	public Boolean getIsDetailsPresentInFile() {
		return isDetailsPresentInFile;
	}

	public void setIsDetailsPresentInFile(Boolean isDetailsPresentInFile) {
		this.isDetailsPresentInFile = isDetailsPresentInFile;
	}

	public Boolean getIsTrailerPresentInFile() {
		return isTrailerPresentInFile;
	}

	public void setIsTrailerPresentInFile(Boolean isTrailerPresentInFile) {
		this.isTrailerPresentInFile = isTrailerPresentInFile;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	
	/*
	 * Transfers the Ictx file
	 */
	public void transferFile(File srcFile, String destDirPath) throws IOException {
		try {
			log.info("Calling tranfer file : {}",destDirPath);
			Path destDir = new File(destDirPath, srcFile.getName()).toPath();
			
			 File temp = destDir.toFile();
	            if (temp.exists()) {
	                temp.delete();
	            }
			
			Path result = Files.move(Paths.get(srcFile.getAbsolutePath()), destDir);

			if (null != result) {
				log.info("File {} moved successfully to {}",srcFile.getName(),destDirPath);
			} else {
				log.info("File {} transfer failed.",srcFile.getName());
			}
		} catch (IOException iex) {
			  log.info("Exception {}", iex);
			throw iex;
		}
	}

	public void validateRecordCount() throws InvalidRecordCountException {

	}

	public AckFileWrapper prePareAckFileMetaData(AckStatusCode ackStatusCode, File file) {
		return null;
	}

	/*
	 * Process acknowledgement and transfer the Ictx file accordingly
	 */
	public void processAckFile(AckFileWrapper ackObj) {
		log.debug("Processing ack file..");
		try {
			createAckFile(ackObj.getAckFileName(), ackObj.getSbFileContent(), ackObj.getAckFileInfoDto());
			transferFile(ackObj.getFile(), ackObj.getFileDestDir());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * Create acknowledgement for Ictx file
	 */
	public void createAckFile(String ackFileName, String ackFileContent, AckFileInfoDto ackFileInfoDto)
			throws IOException, CustomException {
		log.debug("Creating Ack file {}",ackFileName);

		File destFile = new File(getConfigurationMapping().getFileInfoDto().getAckDir(), ackFileName);
		log.info("Destination file: {} ",destFile);
		if (destFile.exists()) {
			destFile.delete();
		}

		File ackFile = new File(getConfigurationMapping().getFileInfoDto().getAckDir(), ackFileName);
		log.info("Ack file: {} ",ackFile);
		try (FileOutputStream fos = new FileOutputStream(ackFile)) {
			fos.write(ackFileContent.getBytes());
			tAgencyDao.insertAckDetails(ackFileInfoDto);
		} catch (FileNotFoundException e) {
            log.info("Exception {}", e);
			throw e;
		} catch (IOException e) {
			 log.info("Exception {}", e);
			throw e;
		}

	}

	public void saveRecords(FileDetails laneTxCheckPoint) {
		// TODO Auto-generated method stub
		
	}

	public void CheckValidFileType(File file) throws InvalidFileTypeException {
		// TODO Auto-generated method stub
		
	}

	public void updateFileDetailsIntoCheckpoint() {
		// TODO Auto-generated method stub
		
	}

	public void insertFileDetailsIntoStatistics() {
		// TODO Auto-generated method stub
		
	}
}
