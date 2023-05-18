package com.conduent.tpms.parking.validation;
//package com.conduent.tpms.iag.validation;
//
//import java.io.File;
//import java.io.FileNotFoundException;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.time.format.DateTimeParseException;
//import java.util.List;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//import java.util.concurrent.TimeUnit;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import com.conduent.tpms.iag.constants.AckStatusCode;
//import com.conduent.tpms.iag.constants.IITCConstants;
//import com.conduent.tpms.iag.dao.TQatpMappingDao;
//import com.conduent.tpms.iag.dao.impl.TAgencyDaoImpl;
//import com.conduent.tpms.iag.dto.AckFileInfoDto;
//import com.conduent.tpms.iag.dto.AckFileWrapper;
//import com.conduent.tpms.iag.dto.FileParserParameters;
//import com.conduent.tpms.iag.dto.MappingInfoDto;
//import com.conduent.tpms.iag.exception.CustomException;
//import com.conduent.tpms.iag.exception.EmptyLineException;
//import com.conduent.tpms.iag.exception.FileAlreadyProcessedException;
//import com.conduent.tpms.iag.exception.InvalidFileNameException;
//import com.conduent.tpms.iag.exception.InvalidFileStatusException;
//import com.conduent.tpms.iag.exception.InvalidFileTypeException;
//import com.conduent.tpms.iag.exception.InvalidRecordCountException;
//import com.conduent.tpms.iag.exception.InvalidRecordException;
//import com.conduent.tpms.iag.exception.InvlidFileHeaderException;
//import com.conduent.tpms.iag.model.ConfigVariable;
//import com.conduent.tpms.iag.model.FileDetails;
//import com.conduent.tpms.iag.model.XferControl;
//import com.conduent.tpms.iag.utility.GenericValidation;
//import com.conduent.tpms.iag.utility.MasterDataCache;
//
//public class FileParserImpl {
//
//	private static final Logger log = LoggerFactory.getLogger(FileParserImpl.class);
//
//	protected boolean isHederPresentInFile;
//	protected boolean isDetailsPresentInFile;
//	protected boolean isTrailerPresentInFile;
//
//	@Autowired
//	protected TQatpMappingDao tQatpMappingDao;
//
//	@Autowired
//	GenericValidation genericValidation;
//
//	@Autowired
//	private TextFileReader textFileReader;
//
//	@Autowired
//	TAgencyDaoImpl agencyDao;
//	
//	@Autowired
//	private ConfigVariable configVariable;
//	
//	@Autowired
//	protected MasterDataCache masterDataCache;
//	
//	private FileUtil fileUtil = new FileUtil();
//	private String fileType;
//	private String fileFormat;
//	private String agencyId;
//
//	private FileParserParameters configurationMapping;
//	private FileParserParameters fileParserParameter = new FileParserParameters();
//	protected String fileName;
//	protected volatile Long recordCount;
//	public File newFile;
//	protected XferControl xferControl;
//	public File threadFile;
//	
//	/*
//	 * Checkpoint table object
//	 */
//	protected FileDetails fileDetails;
//
//	public Integer recordCounter;
//	
//	public File getNewFile() {
//		return newFile;
//	}
//
//	public void setNewFile(File newFile) {
//		this.newFile = newFile;
//	}
//	
//	public File getThreadFile() {
//		return threadFile;
//	}
//
//	public void setThreadFile(File threadFile) {
//		this.threadFile = threadFile;
//	}
//
//	/*
//	 * To initialize basic IITC file properties
//	 */
//	public void initialize() {
//		
//	}
//
//	/**
//	 * Starts processing of IITC file.
//	 * 
//	 * @throws IOException
//	 * @throws InterruptedException 
//	 */
//	public void fileParseTemplate() throws InterruptedException, IOException{
//		try {
//			initialize();
//			loadConfigurationMapping();
//			List<File> allfilesFromSrcFolder = getAllFilesFromSourceFolder();
//				
//			if (allfilesFromSrcFolder.isEmpty()) {
//				log.info("File does not exist in source directory.");
//			} else {
//
//				log.info("Thread size {}",configVariable.getThreadCount());
//
//				ExecutorService executorService = Executors.newFixedThreadPool(configVariable.getThreadCount());
//				for (File file : allfilesFromSrcFolder) {
//					log.info("File {} added in job......",file);
//					Runnable task = new Runnable() {
//					@Override
//					public void run() {
//						log.info("Current Thread name: {}, processing the file: {} ", Thread.currentThread().getName(), file.getAbsolutePath());
//			
//							try {
//								log.info("File name {} taken for process", file);
//								recordCount = textFileReader.getRecordCount(file);
//								validateAndProcessFile(file);
//								AckStatusCode ackStatusCode = AckStatusCode.SUCCESS;
//								AckFileWrapper ackObj = prePareAckFileMetaData(ackStatusCode, file);
//								ackObj.setFileDestDir(getConfigurationMapping().getFileInfoDto().getProcDir());
//								processAckFile(ackObj);
//							} 
//							catch (Exception ex) 
//							{
//								try {
//									textFileReader.closeFile();
//								} catch (IOException e) {
//									e.printStackTrace();
//								}
//								if (ex instanceof InvalidFileNameException) 
//								{
//									log.info("Invalid file name or date");
//									AckStatusCode ackStatusCode = AckStatusCode.INVALID_FILE;
//									AckFileWrapper ackObj = prePareAckFileMetaData(ackStatusCode, file);
//									ackObj.setFileDestDir(getConfigurationMapping().getFileInfoDto().getUnProcDir());
//									processAckFile(ackObj);
//								} 
//								else if (ex instanceof InvlidFileHeaderException) {
//									log.info("Invalid Header  File....",ex);
//									AckStatusCode ackStatusCode = AckStatusCode.HEADER_FAIL;
//									if(ex.getMessage().contains("Date mismatch") || ex.getMessage().contains("Time mismatch")) {
//										ackStatusCode = AckStatusCode.DATE_MISMATCH;
//									}
//									AckFileWrapper ackObj = prePareAckFileMetaData(ackStatusCode, file);
//									ackObj.setFileDestDir(getConfigurationMapping().getFileInfoDto().getUnProcDir());
//									processAckFile(ackObj);
//									log.info("Invalid Header2 File....",ex);
//								} 
//								else if (ex instanceof InvalidRecordCountException) {
//									log.info("Invalid Record File....",ex);
//									AckStatusCode ackStatusCode = AckStatusCode.INVALID_RECORD_COUNT;
//									AckFileWrapper ackObj = prePareAckFileMetaData(ackStatusCode, file);
//									ackObj.setFileDestDir(getConfigurationMapping().getFileInfoDto().getUnProcDir());
//									processAckFile(ackObj);
//									log.info("Invalid Record2 File....",ex);
//								} 
//								else if (ex instanceof FileAlreadyProcessedException) {
//									log.info("File already Processed......",ex);
//									AckStatusCode ackStatusCode = AckStatusCode.SUCCESS;
//									AckFileWrapper ackObj = prePareAckFileMetaData(ackStatusCode, file);
//									ackObj.setFileDestDir(getConfigurationMapping().getFileInfoDto().getProcDir());
//									processAckFile(ackObj);
//									log.info(ex.getMessage());
//									log.info("File already Processed2.....",ex);
//								}
//								else if (ex instanceof InvalidRecordException) {
//									log.info("Invalid Detail Fail File....",ex);
//									AckStatusCode ackStatusCode = AckStatusCode.DETAIL_FAIL;
//									AckFileWrapper ackObj = prePareAckFileMetaData(ackStatusCode, file);
//									ackObj.setFileDestDir(getConfigurationMapping().getFileInfoDto().getUnProcDir());
//									processAckFile(ackObj);
//									log.info(ex.getMessage());
//									log.info("Invalid Detail Fail File2....",ex);
//								}
//								else if (ex instanceof InvalidFileStatusException) {
//									log.info("Invalid File Status....",ex);
//								} 
//								else if (ex instanceof EmptyLineException) {
//									log.info("Current line is empty..",ex);
//								}
//								else if (ex instanceof InvalidFileTypeException) {
//									log.info("Invalid File Type....",ex);
//								}
//								else if(ex instanceof DateTimeParseException) {
//									log.info("DateTimeParseException .....",ex);
//									AckStatusCode ackStatusCode = AckStatusCode.DATE_MISMATCH;
//									AckFileWrapper ackObj = prePareAckFileMetaData(ackStatusCode, file);
//									ackObj.setFileDestDir(getConfigurationMapping().getFileInfoDto().getUnProcDir());
//									processAckFile(ackObj);
//									log.info("DateTimeParseException2 .....",ex);
//								}
//								else {
//									ex.printStackTrace();
//									log.info("Print Stack Trace....",ex);
//								}
//							}
//						}
//					};
//
//					TimeUnit.SECONDS.sleep(10);
//					executorService.execute(task);
//
//					}
//					log.info("All jobs executed successfully.......");
//					executorService.shutdown();
//					}
//		getMissingFilesForAgency();
//		log.info("Exiting File processing ..");
//			
//	}catch(Exception ex)
//	{
//		log.info("Exception caught {}....",ex.getMessage());
//		ex.printStackTrace();
//	}
//	} 
//	
//	public void getMissingFilesForAgency() {
//		// TODO Auto-generated method stub
//	}
//
//	/**
//	 * Validate and Process File
//	 * Validates the file and if true then only further process happens
//	 * validation of header and footer also
//	 */
//	public void validateAndProcessFile(File file) throws InvalidFileNameException, InvlidFileHeaderException, EmptyLineException, InvalidRecordException, InvalidRecordCountException,DateTimeParseException, InvalidFileStatusException, IOException, InvalidFileTypeException, FileAlreadyProcessedException {
//		startFileProcess(file);
//		validateAndProcessFileName(file.getName());
//
//		textFileReader.openFile(file);
//
//		String prviousLine = textFileReader.readLine();
//		String currentLine = textFileReader.readLine();
//
//		/*
//		 * Handles empty file
//		 */
//		if (prviousLine == null) {
//
//		}
//
//		/*
//		 * When only header is present.
//		 */
//		if (prviousLine != null && currentLine == null) {
//		}
//
//		if (prviousLine != null && currentLine != null) {
//			if (isHederPresentInFile) {
//				validateAndProcessHeader(prviousLine);
//				validateRecordCount();
//			} else {
//				parseValidateAndProcessDetail(prviousLine);
//			}
//		}
//		prviousLine = handleDetailLines(currentLine);
//
//		if (isTrailerPresentInFile) {
//			parseAndValidateFooter(prviousLine);
//		} else { 
//			if(prviousLine != null)
//			{
//				parseValidateAndProcessDetail(prviousLine);
//			}
//		}
//
//		textFileReader.closeFile();
//
//		finishFileProcess();
//
//	}
//
//	/**
//	 * Start of file process
//	 * Checks whether file is completly downloaded or not
//	 * @throws Invalid File Status exception
//	 */
//	public void startFileProcess(File file) throws InvalidFileStatusException, IOException, InvalidFileNameException, InvalidFileTypeException {
//		setFileName(file.getName());
//		setNewFile(file);
//		CheckValidFileType(file);
//		xferControl = tQatpMappingDao.checkFileDownloaded(file.getName());
//		if(xferControl==null)
//		{
//			log.info("File is not downloaded completely: {}", file.getName());
//			throw new InvalidFileStatusException("File not downloded");
//		}
//	}
//
//	public void validateAndProcessFileName(String fileName)
//			throws InvalidFileNameException, InvlidFileHeaderException, IOException, FileAlreadyProcessedException, EmptyLineException, InvalidRecordException, DateTimeParseException {
//		parseAndValidate(fileName, configurationMapping.getFileNameMappingInfo());
//
//	}
//
//	public void insertFileDetailsIntoCheckpoint() {
//		// TODO Auto-generated method stub
//	}
//
//	/*
//	 * Validates header
//	 */
//	public void validateAndProcessHeader(String line) throws InvalidFileNameException, InvlidFileHeaderException, EmptyLineException, InvalidRecordException,DateTimeParseException {
//		parseAndValidate(line, configurationMapping.getHeaderMappingInfoList());
//	}
//
//	/*
//	 * Validates detail record
//	 */
//	public void parseAndValidateDetails(String line) throws InvalidFileNameException, InvlidFileHeaderException, EmptyLineException, InvalidRecordException {
//		parseAndValidate(line, configurationMapping.getDetailRecMappingInfo());
//	}
//
//	/*
//	 * Validates footer
//	 */
//	public void parseAndValidateFooter(String line) throws InvalidFileNameException, InvlidFileHeaderException, EmptyLineException, InvalidRecordException {
//		parseAndValidate(line, configurationMapping.getTrailerMappingInfoDto());
//	}
//
//	/*
//	 * Loads configuration mapping details for IITC file from T_FIELD_MAPPING table
//	 */
//	public void loadConfigurationMapping() {
//		getFileParserParameter().setFileType(IITCConstants.IITC);
//		setConfigurationMapping(tQatpMappingDao.getMappingConfigurationDetails(getFileParserParameter()));
//	}
//
//	/*
//	 * Validates field's according to their mapping from T_FIELD_MAPPING table
//	 */
//	public void doFieldMapping(String value, MappingInfoDto fMapping) throws InvalidFileNameException, InvlidFileHeaderException, DateTimeParseException, InvalidRecordException {
//	}
//
//	/**
//	 *Validation for file , detail , header
//	 * @throws invalid file name exception
//	 * @throws invalid header exception
//	 */
//	public void parseAndValidate(String line, List<MappingInfoDto> mappings)
//			throws InvalidFileNameException, InvlidFileHeaderException, EmptyLineException, InvalidRecordException, DateTimeParseException {
//	
//		boolean isValid = true;
//		String fileSection = mappings.get(0).getFieldType();
//
//		processStartOfLine(fileSection);
//
//		for (MappingInfoDto fieldMapping : mappings)
//
//		{
//			if (fileSection == null) {
//				fileSection = fieldMapping.getFieldType();
//			}
//			String value = null;
//			
//			if(fieldMapping.getFieldName().equalsIgnoreCase(IITCConstants.D_CUST_ZIP)) {
//				value = line.substring(fieldMapping.getStartPosition().intValue(),
//						line.length());
//				isValid = genericValidation.doValidation(fieldMapping, value.trim());
//
//				doFieldMapping(value.trim(), fieldMapping);
//				log.info("Validation for field {} :value: {} valid: {}",fieldMapping.getFieldName(),value.trim(),isValid);
//			}
//			else {
//				value = line.substring(fieldMapping.getStartPosition().intValue(),
//						fieldMapping.getEndPosition().intValue());
//				isValid = genericValidation.doValidation(fieldMapping, value);
//				doFieldMapping(value.trim(), fieldMapping);
//				log.info("Validation for field {} :value: {} valid: {}",fieldMapping.getFieldName(),value.trim(),isValid);
//			}
//
//
//			if (!isValid) {
//				if (fieldMapping.getFieldType().equals(IITCConstants.FILENAME)) {
//					throw new InvalidFileNameException("Invalid File Name format");
//				}
//				if (fieldMapping.getFieldType().equals(IITCConstants.HEADER)) {
//					throw new InvlidFileHeaderException("Invalid File Header format");
//				}
//				if (fieldMapping.getFieldType().equals(IITCConstants.DETAIL)) {
//					log.info("Invlalid record data in record {}",value);
//					//throw new InvalidRecordException("Invalid record data.");
//				}
//			}
//		}
//		processEndOfLine(fileSection);
//	}
//
//	public void processStartOfLine(String fileSection) {
//		// TODO Auto-generated method stub
//	}
//
//	public void processEndOfLine(String fileSection) {
//		// TODO Auto-generated method stub
//	}
//
//	/*
//	 * Parses details record in IITC file
//	 */
//	public void parseValidateAndProcessDetail(String prviousLine)
//			throws InvalidFileNameException, InvlidFileHeaderException, EmptyLineException, InvalidRecordException {
//		parseAndValidateDetails(prviousLine);
//		processDetailLine();
//	}
//
//	/**
//	 * handles detail line
//	 * 
//	 * @return previous line 
//	 */
//	public String handleDetailLines(String currentLine) throws InvalidFileNameException, InvlidFileHeaderException, EmptyLineException, InvalidRecordException {
//		String prviousLine;
//		prviousLine = currentLine;
//		currentLine = textFileReader.readLine();
//
//		do {
//			parseValidateAndProcessDetail(prviousLine);
//			prviousLine = currentLine;
//			currentLine = textFileReader.readLine();
//		} while (currentLine != null);
//		return prviousLine;
//	}
//
//	public void processDetailLine() {
//
//	}
//
//	public void finishFileProcess() throws InvlidFileHeaderException, FileAlreadyProcessedException, InvalidRecordException {
//
//	}
//
//
//	/**
//	 * get files from scource folder
//	 * @return files from source folder
//	 */
//	public List<File> getAllFilesFromSourceFolder() throws IOException {
//		List<File> allfilesFromSrcDirectory = fileUtil
//				.getAllfilesFromSrcDirectory(getConfigurationMapping().getFileInfoDto().getSrcDir());
//		log.info("List of files : {}",allfilesFromSrcDirectory.toString());
//		return allfilesFromSrcDirectory;
//	}
//
//	public String getFileType() {
//		return fileType;
//	}
//
//	public void setFileType(String fileType) {
//		this.fileType = fileType;
//	}
//
//	public String getFileFormat() {
//		return fileFormat;
//	}
//
//	public void setFileFormat(String fileFormat) {
//		this.fileFormat = fileFormat;
//	}
//
//	public String getAgencyId() {
//		return agencyId;
//	}
//
//	public void setAgencyId(String agencyId) {
//		this.agencyId = agencyId;
//	}
//
//	public FileParserParameters getFileParserParameter() {
//		return fileParserParameter;
//	}
//
//	public void setFileParserParameter(FileParserParameters fileParserParameter) {
//		this.fileParserParameter = fileParserParameter;
//	}
//
//	public FileParserParameters getConfigurationMapping() {
//		return configurationMapping;
//	}
//
//	public void setConfigurationMapping(FileParserParameters configurationMapping) {
//		this.configurationMapping = configurationMapping;
//	}
//
//	public Boolean getIsHederPresentInFile() {
//		return isHederPresentInFile;
//	}
//
//	public void setIsHederPresentInFile(Boolean isHederPresentInFile) {
//		this.isHederPresentInFile = isHederPresentInFile;
//	}
//
//	public Boolean getIsDetailsPresentInFile() {
//		return isDetailsPresentInFile;
//	}
//
//	public void setIsDetailsPresentInFile(Boolean isDetailsPresentInFile) {
//		this.isDetailsPresentInFile = isDetailsPresentInFile;
//	}
//
//	public Boolean getIsTrailerPresentInFile() {
//		return isTrailerPresentInFile;
//	}
//
//	public void setIsTrailerPresentInFile(Boolean isTrailerPresentInFile) {
//		this.isTrailerPresentInFile = isTrailerPresentInFile;
//	}
//
//	public String getFileName() {
//		return fileName;
//	}
//
//	public void setFileName(String fileName) {
//		this.fileName = fileName;
//	}
//
//
//	/**
//	 * Transfer call
//	 * Moves file from one folder to another
//	 * 
//	 */
//	public synchronized void  transferFile(File srcFile, String destDirPath) throws IOException {
//		try {
//			log.info("Calling tranfer file : {}",srcFile);
//			log.info("Calling tranfer file : {}",destDirPath);
//			log.info("SrcFilePath ===>"+srcFile.getName());
//			Path destDir = new File(destDirPath, srcFile.getName()).toPath();
//			log.info("Destination Path is.....{}",destDir.toString());			 
//			File temp = destDir.toFile();
//			
//	        if (temp.exists()) 
//	        {
//	                temp.delete();
//	        }
//				log.info("Src File Path...{}",srcFile.getAbsolutePath().toString());
//				Path result = Files.move(Paths.get(srcFile.getAbsolutePath()), destDir);
//				log.info("File move result..{}",result == null? "null":result.toString());
//			if (null != result) {
//				log.info("File {} moved successfully to {}",srcFile.getName(),destDirPath);
//			} else {
//				log.info("File {} transfer failed..",srcFile.getName());
//			}
//		
//		} catch (Exception iex) {
//			  log.info("Exception {}", iex);
//			  iex.printStackTrace();
//			throw iex;
//		}
//	}
//
//	public void validateRecordCount() throws InvalidRecordCountException {
//
//	}
//
//	public AckFileWrapper prePareAckFileMetaData(AckStatusCode ackStatusCode, File file) {
//		return null;
//	}
//
//
//	/**
//	 * Ack preparation
//	 * 
//	 */
//	public void processAckFile(AckFileWrapper ackObj) {
//		log.debug("Processing ack file..");
//		try {
//			createAckFile(ackObj.getAckFileName(), ackObj.getSbFileContent(), ackObj.getAckFileInfoDto());
//			log.debug("****************** Src Directory********************"+ackObj.getFile()+"DestDir==>"+ackObj.getFileDestDir());
//			transferFile(ackObj.getFile(), ackObj.getFileDestDir());
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//	
//	/**
//	 * creation of ack file
//	 * 
//	 * @throws file not found exception 
//	 */
//	public void createAckFile(String ackFileName, String ackFileContent, AckFileInfoDto ackFileInfoDto)
//			throws IOException, CustomException {
//		log.debug("Creating Ack file {}",ackFileName);
//
//		File destFile = new File(getConfigurationMapping().getFileInfoDto().getAckDir(), ackFileName);
//		log.info("Destination file: {} ",destFile);
//		if (destFile.exists()) {
//			destFile.delete();
//		}
//
//		File ackFile = new File(getConfigurationMapping().getFileInfoDto().getAckDir(), ackFileName);
//		log.info("Ack file: {} ",ackFile);
//		try (FileOutputStream fos = new FileOutputStream(ackFile)) {
//			fos.write(ackFileContent.getBytes());
//			tQatpMappingDao.insertAckDetails(ackFileInfoDto);
//		} catch (FileNotFoundException e) {
//            log.info("Exception {}", e);
//			throw e;
//		} catch (IOException e) {
//			 log.info("Exception {}", e);
//			throw e;
//		}
//
//	}
//
//	public void saveRecords(FileDetails laneTxCheckPoint) {
//		// TODO Auto-generated method stub
//	}
//
//	public void CheckValidFileType(File file) throws InvalidFileTypeException {
//		// TODO Auto-generated method stub
//	}
//}
