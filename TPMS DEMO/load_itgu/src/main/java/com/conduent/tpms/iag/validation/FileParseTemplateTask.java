package com.conduent.tpms.iag.validation;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.conduent.tpms.iag.constants.AckStatusCode;
import com.conduent.tpms.iag.constants.ItguConstants;
import com.conduent.tpms.iag.dao.TQatpMappingDao;
import com.conduent.tpms.iag.dao.impl.TAgencyDaoImpl;
import com.conduent.tpms.iag.dto.AckFileInfoDto;
import com.conduent.tpms.iag.dto.AckFileWrapper;
import com.conduent.tpms.iag.dto.FileParserParameters;
import com.conduent.tpms.iag.dto.MappingInfoDto;
import com.conduent.tpms.iag.exception.CustomException;
import com.conduent.tpms.iag.exception.EmptyLineException;
import com.conduent.tpms.iag.exception.FileAlreadyProcessedException;
import com.conduent.tpms.iag.exception.InvalidFileNameException;
import com.conduent.tpms.iag.exception.InvalidFileStatusException;
import com.conduent.tpms.iag.exception.InvalidFileTypeException;
import com.conduent.tpms.iag.exception.InvalidRecordCountException;
import com.conduent.tpms.iag.exception.InvalidRecordException;
import com.conduent.tpms.iag.exception.InvlidFileHeaderException;
import com.conduent.tpms.iag.model.ConfigVariable;
import com.conduent.tpms.iag.model.FileDetails;
import com.conduent.tpms.iag.model.XferControl;
import com.conduent.tpms.iag.utility.GenericValidation;

public class FileParseTemplateTask implements Runnable {

	private static final Logger log = LoggerFactory.getLogger(FileParseTemplateTask.class);

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
	TAgencyDaoImpl agencyDao;
	
	
	@Autowired
	protected ConfigVariable configVariable;
	
	private FileUtil fileUtil = new FileUtil();
	private String fileType;
	private String fileFormat;
	private String agencyId;

	private FileParserParameters configurationMapping;
	private FileParserParameters fileParserParameter = new FileParserParameters();
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
	
	public FileParseTemplateTask(File threadFile) {
		super();
		this.threadFile = threadFile;
	}

	public File getThreadFile() {
		return threadFile;
	}

	public void setThreadFile(File threadFile) {
		this.threadFile = threadFile;
	}

	/*
	 * To initialize basic ITAG file properties
	 */
	public void initialize() {
	}



	/*
	 * Validated and process ITAG file.
	 */

	public void validateAndProcessFile(File file) throws InvalidFileNameException, InvlidFileHeaderException, EmptyLineException, InvalidRecordException, InvalidRecordCountException,DateTimeParseException, InvalidFileStatusException, IOException, InvalidFileTypeException, FileAlreadyProcessedException {
		startFileProcess(file);
		validateAndProcessFileName(file.getName());

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
				validateAndProcessHeader(prviousLine);
				validateRecordCount();
			} else {
				parseValidateAndProcessDetail(prviousLine);
			}
		}
		prviousLine = handleDetailLines(currentLine);

		if (isTrailerPresentInFile) {
			parseAndValidateFooter(prviousLine);
		} else { 
			if(prviousLine != null)
			{
				parseValidateAndProcessDetail(prviousLine);
			}
		}

		textFileReader.closeFile();

		finishFileProcess();

	}

	public void startFileProcess(File file) throws InvalidFileStatusException, IOException, InvalidFileNameException, InvalidFileTypeException {
		setFileName(file.getName());
		setNewFile(file);
		CheckValidFileType(file);
		xferControl = tQatpMappingDao.checkFileDownloaded(file.getName());
		if(xferControl==null)
		{
			log.info("File is downloaded not completely: {}", file.getName());
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
	 * Loads configuration mapping details for ITAG file from t_qatp_mapping table
	 */
	public void loadConfigurationMapping(String fileType) {
		getFileParserParameter().setFileType(fileType);
		setConfigurationMapping(tQatpMappingDao.getMappingConfigurationDetails(fileType));
	}

	/*
	 * Validates field's according to their mapping from t_qatp_mapping table
	 */
	public void doFieldMapping(String value, MappingInfoDto fMapping) throws InvalidFileNameException, InvlidFileHeaderException {
	}

	/*
	 * Parse ITAG file according to their sections i.e Header, Detail
	 */
	public void parseAndValidate(String line, List<MappingInfoDto> mappings)
			throws InvalidFileNameException, InvlidFileHeaderException, EmptyLineException, InvalidRecordException, DateTimeParseException {
		/*
		 * if (line == null || line.isEmpty()) { throw new
		 * EmptyLineException("Record is empty"); }
		 */
		boolean isValid = true;
		String fileSection = mappings.get(0).getFieldType();

		processStartOfLine(fileSection);

		for (MappingInfoDto fieldMapping : mappings)

		{
			if (fileSection == null) {
				fileSection = fieldMapping.getFieldType();
			}

			String value = line.substring(fieldMapping.getStartPosition().intValue(),
					fieldMapping.getEndPosition().intValue());

			isValid = genericValidation.doValidation(fieldMapping, value.trim());
			
			log.info("Validation for {} is: {}",value.trim(),isValid);

			doFieldMapping(value, fieldMapping);

			if (!isValid) {
				if (fieldMapping.getFieldType().equals(ItguConstants.FILENAME)) {
					throw new InvalidFileNameException("Invalid File Name format");
				}
				if (fieldMapping.getFieldType().equals(ItguConstants.HEADER)) {
					throw new InvlidFileHeaderException("Invalid File Header format");
				}
				if (fieldMapping.getFieldType().equals(ItguConstants.DETAIL)) {
					log.info("Invlaied record data in record {}",value);
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
	 * Parses details record in ITAG file
	 */
	public void parseValidateAndProcessDetail(String prviousLine)
			throws InvalidFileNameException, InvlidFileHeaderException, EmptyLineException, InvalidRecordException {
		parseAndValidateDetails(prviousLine);
		processDetailLine();
	}

	/*
	 * Handles details record in ITAG file
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

	public void finishFileProcess() throws InvlidFileHeaderException, FileAlreadyProcessedException {

	}

	/*
	 * Get ITAG file's list
	 */
	public List<File> getAllFilesFromSourceFolder() throws IOException {
		List<File> allfilesFromSrcDirectory = fileUtil
				.getAllfilesFromSrcDirectory(getConfigurationMapping().getFileInfoDto().getSrcDir());
		return allfilesFromSrcDirectory;
	}

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

	public void setFileParserParameter(FileParserParameters fileParserParameter) {
		this.fileParserParameter = fileParserParameter;
	}

	public FileParserParameters getConfigurationMapping() {
		return configurationMapping;
	}

	public void setConfigurationMapping(FileParserParameters configurationMapping) {
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
	 * Transfers the ITAG file
	 */
	public void transferFile(File srcFile, String destDirPath) throws IOException {
		try {
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
			throw iex;
		}
	}

	public void validateRecordCount() throws InvalidRecordCountException {

	}

	public AckFileWrapper prePareAckFileMetaData(AckStatusCode ackStatusCode, File file) {
		return null;
	}

	/*
	 * Process acknowledgement and transfer the ITAG file accordingly
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
	 * Create acknowledgement for ITAG file
	 */
	public void createAckFile(String ackFileName, String ackFileContent, AckFileInfoDto ackFileInfoDto)
			throws IOException, CustomException {
		log.debug("Creating Ack file..");

		File destFile = new File(getConfigurationMapping().getFileInfoDto().getAckDir(), ackFileName);
		if (destFile.exists()) {
			destFile.delete();
		}

		File ackFile = new File(getConfigurationMapping().getFileInfoDto().getAckDir(), ackFileName);
		try (FileOutputStream fos = new FileOutputStream(ackFile)) {
			fos.write(ackFileContent.getBytes());
			tQatpMappingDao.insertAckDetails(ackFileInfoDto);
		} catch (FileNotFoundException e) {
			throw e;
		} catch (IOException e) {
			throw e;
		}

	}

	public void saveRecords(FileDetails laneTxCheckPoint) {
		// TODO Auto-generated method stub
		
	}

	public void CheckValidFileType(File file) throws InvalidFileTypeException {
		// TODO Auto-generated method stub
		
	}
	
	public void task(File file) throws IOException {
			try {
				log.info("File name {} taken for process", file.getName());
				recordCount = textFileReader.getRecordCount(file);
				validateAndProcessFile(file);
				AckStatusCode ackStatusCode = AckStatusCode.SUCCESS;
				AckFileWrapper ackObj = prePareAckFileMetaData(ackStatusCode, file);
				ackObj.setFileDestDir(getConfigurationMapping().getFileInfoDto().getProcDir());
				processAckFile(ackObj);
			} 
			catch (Exception ex) 
			{
				textFileReader.closeFile();
				if (ex instanceof InvalidFileNameException) 
				{
					transferFile(file, getConfigurationMapping().getFileInfoDto().getUnProcDir());
				} 
				else if (ex instanceof InvlidFileHeaderException) {
					AckStatusCode ackStatusCode = AckStatusCode.HEADER_FAIL;
					if(ex.getMessage().contains("Date mismatch") || ex.getMessage().contains("Time mismatch")) {
						ackStatusCode = AckStatusCode.DATE_MISMATCH;
					}
					AckFileWrapper ackObj = prePareAckFileMetaData(ackStatusCode, file);
					ackObj.setFileDestDir(getConfigurationMapping().getFileInfoDto().getUnProcDir());
					processAckFile(ackObj);
				} 
				else if (ex instanceof InvalidRecordCountException) {
					AckStatusCode ackStatusCode = AckStatusCode.INVALID_RECORD_COUNT;
					AckFileWrapper ackObj = prePareAckFileMetaData(ackStatusCode, file);
					ackObj.setFileDestDir(getConfigurationMapping().getFileInfoDto().getUnProcDir());
					processAckFile(ackObj);
				} 
				else if (ex instanceof FileAlreadyProcessedException) {
					AckStatusCode ackStatusCode = AckStatusCode.SUCCESS;
					AckFileWrapper ackObj = prePareAckFileMetaData(ackStatusCode, file);
					ackObj.setFileDestDir(getConfigurationMapping().getFileInfoDto().getProcDir());
					processAckFile(ackObj);
					log.info(ex.getMessage());
				}
				else if (ex instanceof InvalidRecordException) {
					AckStatusCode ackStatusCode = AckStatusCode.DETAIL_FAIL;
					AckFileWrapper ackObj = prePareAckFileMetaData(ackStatusCode, file);
					ackObj.setFileDestDir(getConfigurationMapping().getFileInfoDto().getUnProcDir());
					processAckFile(ackObj);
					log.info(ex.getMessage());
				}
				else if (ex instanceof InvalidFileStatusException) {
				} 
				else if (ex instanceof EmptyLineException) {
					log.info("Current line is empty..");
				}
				else if (ex instanceof InvalidFileTypeException) {
				}
				else if(ex instanceof DateTimeParseException) {
					AckStatusCode ackStatusCode = AckStatusCode.DATE_MISMATCH;
					AckFileWrapper ackObj = prePareAckFileMetaData(ackStatusCode, file);
					ackObj.setFileDestDir(getConfigurationMapping().getFileInfoDto().getUnProcDir());
					processAckFile(ackObj);
				
				}
				else {
					ex.printStackTrace();
				}
			}
		}

	@Override
	public void run() {
		File file = getThreadFile();
				

		try {
			log.info("File name {} taken for process", file.getName());
			log.info("Running Thread Name = "+ Thread.currentThread().getName());
			
			recordCount = textFileReader.getRecordCount(file);
			validateAndProcessFile(file);
			AckStatusCode ackStatusCode = AckStatusCode.SUCCESS;
			AckFileWrapper ackObj = prePareAckFileMetaData(ackStatusCode, file);
			ackObj.setFileDestDir(getConfigurationMapping().getFileInfoDto().getProcDir());
			processAckFile(ackObj);
		} 
		catch (Exception ex) 
		{
			try {
				textFileReader.closeFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (ex instanceof InvalidFileNameException) 
			{
				try {
					transferFile(file, getConfigurationMapping().getFileInfoDto().getUnProcDir());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} 
			else if (ex instanceof InvlidFileHeaderException) {
				AckStatusCode ackStatusCode = AckStatusCode.HEADER_FAIL;
				if(ex.getMessage().contains("Date mismatch") || ex.getMessage().contains("Time mismatch")) {
					ackStatusCode = AckStatusCode.DATE_MISMATCH;
				}
				AckFileWrapper ackObj = prePareAckFileMetaData(ackStatusCode, file);
				ackObj.setFileDestDir(getConfigurationMapping().getFileInfoDto().getUnProcDir());
				processAckFile(ackObj);
			} 
			else if (ex instanceof InvalidRecordCountException) {
				AckStatusCode ackStatusCode = AckStatusCode.INVALID_RECORD_COUNT;
				AckFileWrapper ackObj = prePareAckFileMetaData(ackStatusCode, file);
				ackObj.setFileDestDir(getConfigurationMapping().getFileInfoDto().getUnProcDir());
				processAckFile(ackObj);
			} 
			else if (ex instanceof FileAlreadyProcessedException) {
				AckStatusCode ackStatusCode = AckStatusCode.SUCCESS;
				AckFileWrapper ackObj = prePareAckFileMetaData(ackStatusCode, file);
				ackObj.setFileDestDir(getConfigurationMapping().getFileInfoDto().getProcDir());
				processAckFile(ackObj);
				log.info(ex.getMessage());
			}
			else if (ex instanceof InvalidRecordException) {
				AckStatusCode ackStatusCode = AckStatusCode.DETAIL_FAIL;
				AckFileWrapper ackObj = prePareAckFileMetaData(ackStatusCode, file);
				ackObj.setFileDestDir(getConfigurationMapping().getFileInfoDto().getUnProcDir());
				processAckFile(ackObj);
				log.info(ex.getMessage());
			}
			else if (ex instanceof InvalidFileStatusException) {
			} 
			else if (ex instanceof EmptyLineException) {
				log.info("Current line is empty..");
			}
			else if (ex instanceof InvalidFileTypeException) {
			}
			else if(ex instanceof DateTimeParseException) {
				AckStatusCode ackStatusCode = AckStatusCode.DATE_MISMATCH;
				AckFileWrapper ackObj = prePareAckFileMetaData(ackStatusCode, file);
				ackObj.setFileDestDir(getConfigurationMapping().getFileInfoDto().getUnProcDir());
				processAckFile(ackObj);
			}
			else {
				ex.printStackTrace();
			}
		}
	
	}
	
}
	
