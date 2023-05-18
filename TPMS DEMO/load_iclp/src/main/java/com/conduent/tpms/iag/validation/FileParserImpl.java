package com.conduent.tpms.iag.validation;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.conduent.tpms.iag.constants.AckStatusCode;
import com.conduent.tpms.iag.constants.ICLPConstants;
import com.conduent.tpms.iag.dao.TQatpMappingDao;
import com.conduent.tpms.iag.dao.TransactionDao;
import com.conduent.tpms.iag.dao.impl.TAgencyDaoImpl;
import com.conduent.tpms.iag.dto.AckFileInfoDto;
import com.conduent.tpms.iag.dto.AckFileWrapper;
import com.conduent.tpms.iag.dto.FileParserParameters;
import com.conduent.tpms.iag.dto.ICLPDetailInfoVO;
import com.conduent.tpms.iag.dto.MappingInfoDto;
import com.conduent.tpms.iag.exception.BucketFullException;
import com.conduent.tpms.iag.exception.CustomException;
import com.conduent.tpms.iag.exception.EmptyLineException;
import com.conduent.tpms.iag.exception.FileAlreadyProcessedException;
import com.conduent.tpms.iag.exception.InvalidFileNameException;
import com.conduent.tpms.iag.exception.InvalidFileStatusException;
import com.conduent.tpms.iag.exception.InvalidFileTypeException;
import com.conduent.tpms.iag.exception.InvalidRecordCountException;
import com.conduent.tpms.iag.exception.InvalidRecordException;
import com.conduent.tpms.iag.exception.InvlidFileHeaderException;
import com.conduent.tpms.iag.exception.RecordThresholdLimitExceeded;
import com.conduent.tpms.iag.model.ConfigVariable;
import com.conduent.tpms.iag.model.FileDetails;
import com.conduent.tpms.iag.model.XferControl;
import com.conduent.tpms.iag.utility.GenericValidation;

public class FileParserImpl {

	private static final Logger log = LoggerFactory.getLogger(FileParserImpl.class);

	protected boolean isHederPresentInFile;
	protected boolean isDetailsPresentInFile;
	protected boolean isTrailerPresentInFile;

	private boolean isrecordValid;

	public boolean isIsrecordValid() {
		return isrecordValid;
	}

	public void setIsrecordValid(boolean isrecordValid) {
		this.isrecordValid = isrecordValid;
	}

	@Autowired(required = true)
	protected TQatpMappingDao tQatpMappingDao;

	@Autowired
	GenericValidation genericValidation;

	@Autowired
	private TextFileReader textFileReader;

	@Autowired
	TAgencyDaoImpl agencyDao;

	@Autowired
	protected TransactionDao transactionDao;

	@Autowired
	protected ConfigVariable configVariable;

	@Autowired
	GenericICLPFileParserImpl genericICLPFileParserImpl;

	private FileUtil fileUtil = new FileUtil();
	private String fileType;
	private String fileFormat;
	private String agencyId;

	private FileParserParameters configurationMapping;
	private FileParserParameters fileParserParameter = new FileParserParameters();
	AckStatusCode ackStatusCode;
	protected String fileName;
	protected Map<String, Long> recordCount = new HashMap<>();
	protected XferControl xferControl;
	public File newFile;
	public Integer recordCounter;
	public File threadFile;

	
	/*
	 * Checkpoint table object
	 */
	protected FileDetails fileDetails = null;

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

	public void initialize() {

	}

	public void fileParseTemplate(File file, int agencySequence) throws IOException, InterruptedException {
		/*
		 * initialize(); loadConfigurationMapping(); List<File> allfilesFromSrcFolder =
		 * getAllFilesFromSourceFolder();
		 * 
		 * 
		 * if (allfilesFromSrcFolder.isEmpty()) {
		 * log.info("File does not exist in source directory."); }
		 * 
		 * 
		 * for (File file : allfilesFromSrcFolder) {
		 */

		log.info("File {} added in job....", file.getName());
		try {
			log.info("File name {} taken for process", file.getName());
			recordCount.put(file.getName(), textFileReader.getRecordCount(file));
			validateAndProcessFile(file, agencySequence);
			ackStatusCode = AckStatusCode.SUCCESS;
			AckFileWrapper ackObj = prePareAckFileMetaData(ackStatusCode, file);
			ackObj.setFileDestDir(getConfigurationMapping().getFileInfoDto().getProcDir());
			processAckFile(ackObj);
		} catch (Exception ex) {
			try {
				textFileReader.closeFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (ex instanceof InvalidFileNameException) {
				AckStatusCode ackStatusCode = AckStatusCode.INVALID_FILE;
				AckFileWrapper ackObj = prePareAckFileMetaData(ackStatusCode, file);
				ackObj.setFileDestDir(getConfigurationMapping().getFileInfoDto().getUnProcDir());
				processAckFile(ackObj);
			} else if (ex instanceof InvlidFileHeaderException) {
				AckStatusCode ackStatusCode = AckStatusCode.HEADER_FAIL;
				if (ex.getMessage().contains("Date mismatch") || ex.getMessage().contains("Time mismatch")) {
					ackStatusCode = AckStatusCode.DATE_MISMATCH;
				}
				AckFileWrapper ackObj = prePareAckFileMetaData(ackStatusCode, file);
				ackObj.setFileDestDir(getConfigurationMapping().getFileInfoDto().getUnProcDir());
				processAckFile(ackObj);
			} else if (ex instanceof InvalidRecordCountException) {
				AckStatusCode ackStatusCode = AckStatusCode.INVALID_RECORD_COUNT;
				AckFileWrapper ackObj = prePareAckFileMetaData(ackStatusCode, file);
				ackObj.setFileDestDir(getConfigurationMapping().getFileInfoDto().getUnProcDir());
				processAckFile(ackObj);
			} else if (ex instanceof FileAlreadyProcessedException) {
				AckStatusCode ackStatusCode = AckStatusCode.SUCCESS;
				AckFileWrapper ackObj = prePareAckFileMetaData(ackStatusCode, file);
				ackObj.setFileDestDir(getConfigurationMapping().getFileInfoDto().getProcDir());
				processAckFile(ackObj);
				log.info(ex.getMessage());
			} else if (ex instanceof InvalidRecordException) {
				AckStatusCode ackStatusCode = AckStatusCode.DETAIL_FAIL;
				AckFileWrapper ackObj = prePareAckFileMetaData(ackStatusCode, file);
				ackObj.setFileDestDir(getConfigurationMapping().getFileInfoDto().getUnProcDir());
				processAckFile(ackObj);
				log.info(ex.getMessage());
			} else if (ex instanceof DateTimeParseException) {
				AckStatusCode ackStatusCode = AckStatusCode.DATE_MISMATCH;
				AckFileWrapper ackObj = prePareAckFileMetaData(ackStatusCode, file);
				ackObj.setFileDestDir(getConfigurationMapping().getFileInfoDto().getUnProcDir());
				processAckFile(ackObj);
			} else if (ex instanceof InvalidFileStatusException) {
			} else if (ex instanceof EmptyLineException) {
				log.info("Current line is empty..");
			} else if (ex instanceof InvalidFileTypeException) {
			} else if(ex instanceof RecordThresholdLimitExceeded) {
				log.info("record threshold limit exceeded");
				AckStatusCode ackStatusCode = AckStatusCode.THRESHOLD_EXCEED;
				AckFileWrapper ackObj = prePareAckFileMetaData(ackStatusCode, file);
				ackObj.setFileDestDir(getConfigurationMapping().getFileInfoDto().getUnProcDir());
				moveThresholdLimitExceedFileToUnProc(ackObj);
			}
			else {
				ex.printStackTrace();
			}
		}
		// }
		getMissingFilesForAgency();
		log.info("Exiting File processing ..");
	}

	/**
	 * 
	 * @throws IOException
	 * @throws RecordThresholdLimitExceeded 
	 */

	public void validateAndProcessFile(File file, int agencySequence)
			throws InvalidFileStatusException, IOException, InvalidFileNameException, FileAlreadyProcessedException,
			InvalidFileTypeException, InvlidFileHeaderException, EmptyLineException, InvalidRecordCountException,
			InvalidRecordException, BucketFullException, RecordThresholdLimitExceeded {
		log.info("FileParseImpl validateAndProcessFile() start ");
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
		} else if (prviousLine != null) {
			if (prviousLine != null) {
				parseValidateAndProcessDetail(prviousLine);
			}
		}
		textFileReader.closeFile();
		log.info("FileParseImpl before finishFileProcess() start ");
		genericICLPFileParserImpl.finishFileProcess(agencySequence);
		log.info("FileParseImpl validateAndProcessFile() End ");
	}

	public void startFileProcess(File file) throws InvalidFileStatusException, IOException, InvalidFileNameException,
			FileAlreadyProcessedException, InvalidFileTypeException {
		setFileName(file.getName().trim());
		setNewFile(file);
		CheckValidFileType(file);

		xferControl = tQatpMappingDao.checkFileDownloaded(file.getName());

		if (xferControl == null) {
			log.info("File is not downloaded completely: {}", file.getName());
			throw new InvalidFileStatusException("File not downloded");
		}
	}

	public void CheckValidFileType(File file) throws InvalidFileTypeException {
		// TODO Auto-generated method stub

	}

	public void validateAndProcessFileName(String fileName) throws InvalidFileNameException, InvlidFileHeaderException,
			IOException, FileAlreadyProcessedException, EmptyLineException, InvalidRecordException {
		parseAndValidate(fileName, configurationMapping.getFileNameMappingInfo());

	}

	public void insertFileDetailsIntoCheckpoint() {
		// TODO Auto-generated method stub

	}

	public void validateAndProcessHeader(String line)
			throws InvalidFileNameException, InvlidFileHeaderException, EmptyLineException, InvalidRecordException {
		parseAndValidate(line, configurationMapping.getHeaderMappingInfoList());
	}

	public void parseAndValidateDetails(String line)
			throws InvalidFileNameException, InvlidFileHeaderException, EmptyLineException, InvalidRecordException {
		parseAndValidate(line, configurationMapping.getDetailRecMappingInfo());
	}

	public void parseAndValidateFooter(String line)
			throws InvalidFileNameException, InvlidFileHeaderException, EmptyLineException, InvalidRecordException {
		parseAndValidate(line, configurationMapping.getTrailerMappingInfoDto());
	}

	public void loadConfigurationMapping(String fileType) {
		getFileParserParameter().setFileType(ICLPConstants.ICLP);
		setConfigurationMapping(tQatpMappingDao.getMappingConfigurationDetails(fileType));
	}

	public void doFieldMapping(String value, MappingInfoDto fMapping)
			throws InvalidFileNameException, InvlidFileHeaderException {
	}

	public void parseAndValidate(String line, List<MappingInfoDto> mappings)
			throws InvalidFileNameException, InvlidFileHeaderException, EmptyLineException, InvalidRecordException {

		if (line == null || line.isEmpty()) {
			throw new EmptyLineException("Record is empty");
		}

		boolean isValid = true;
		// detailVO.setIsrecordvalid(true);

		String fileSection = mappings.get(0).getFieldType();

		processStartOfLine(fileSection);

		for (MappingInfoDto fieldMapping : mappings) {
			if (fileSection == null) {
				fileSection = fieldMapping.getFieldType();
			}
			String value = line.substring(fieldMapping.getStartPosition().intValue(),
					fieldMapping.getEndPosition().intValue());

			isValid = genericValidation.doValidation(fieldMapping, value.trim());
			log.info("Validation for field {} :value: {} valid: {}", fieldMapping.getFieldName(), value.trim(),
					isValid);
			
			//boolean isstateval1= genericICLPFileParserImpl.validateLicState(value);
			
			

			if (isValid && ((fieldMapping.getFieldType().equals(ICLPConstants.FILENAME))
					|| (fieldMapping.getFieldType().equals(ICLPConstants.HEADER))
					|| (fieldMapping.getFieldType().equals(ICLPConstants.DETAIL))) ) {
				doFieldMapping(value.trim(), fieldMapping);
				setIsrecordValid(true);
			} else if (!isValid && !fieldMapping.getFieldType().equals(ICLPConstants.DETAIL)) {
				if (fieldMapping.getFieldType().equals(ICLPConstants.FILENAME)) {
					throw new InvalidFileNameException("Invalid File Name Format");
				}
				if (fieldMapping.getFieldType().equals(ICLPConstants.HEADER)) {
					throw new InvlidFileHeaderException("Invalid File Header Format");
				}

				/*
				 * if (fieldMapping.getFieldType().equals(ICLPConstants.DETAIL)) { throw new
				 * InvalidRecordException("Invalid Detail Record."); }
				 */

			} else if (!isValid && fieldMapping.getFieldType().equals(ICLPConstants.DETAIL)) {
				// detailVO.setIsrecordvalid(false);
				setIsrecordValid(false);
				break;
			}
			/*
			 * else if(isValid && fieldMapping.getFieldType().equals(ICLPConstants.DETAIL))
			 * { detailVO.setIsrecordvalid(true); }
			 */
		}

		processEndOfLine(fileSection);

	}

	public void processStartOfLine(String fileSection) {
		// TODO Auto-generated method stub

	}

	public void processEndOfLine(String fileSection) {
		// TODO Auto-generated method stub

	}

	public void parseValidateAndProcessDetail(String prviousLine)
			throws InvalidFileNameException, InvlidFileHeaderException, EmptyLineException, InvalidRecordException {
		parseAndValidateDetails(prviousLine);
		processDetailLine(prviousLine);
	}

	public void processDetailLine() {
		// TODO Auto-generated method stub

	}
	
	public void processDetailLine(String line) {
		// TODO Auto-generated method stub

	}

	public String handleDetailLines(String currentLine)
			throws InvalidFileNameException, InvlidFileHeaderException, EmptyLineException, InvalidRecordException {
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

	public void finishFileProcess() throws InvalidFileNameException, IOException, InvlidFileHeaderException,
			FileAlreadyProcessedException, BucketFullException {

	}

	public List<File> getAllFilesFromSourceFolder() throws IOException {
		List<File> allfilesFromSrcDirectory = fileUtil
				.getAllfilesFromSrcDirectory(getConfigurationMapping().getFileInfoDto().getSrcDir());
		log.info(
				"Files are picked from the directory >>>>>> " + getConfigurationMapping().getFileInfoDto().getSrcDir());
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
	 * Transfers the ICLP file
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
				log.info("File {} moved successfully to {}", srcFile.getName(), destDirPath);
			} else {
				log.info("File {} transfer failed.", srcFile.getName());
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

	public void processAckFile(AckFileWrapper ackObj) {
		log.debug("Processing ack file..");
		try {
			createAckFile(ackObj.getAckFileName(), ackObj.getSbFileContent(), ackObj.getAckFileInfoDto());
			transferFile(ackObj.getFile(), ackObj.getFileDestDir());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void moveThresholdLimitExceedFileToUnProc(AckFileWrapper ackObj) {
		try {
			transferFile(ackObj.getFile(), ackObj.getFileDestDir());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void createAckFile(String ackFileName, String ackFileContent, AckFileInfoDto ackFileInfoDto)
			throws IOException, CustomException {
		log.debug("Creating Ack file {}", ackFileName);

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

	public void saveRecords(FileDetails fileDetails) {

	}

	public void getMissingFilesForAgency() {
		// TODO Auto-generated method stub

	}

	public void parseAndValidateNew(String line, List<MappingInfoDto> mappings)
			throws InvalidFileNameException, InvlidFileHeaderException, EmptyLineException, InvalidRecordException {

		

		if (line == null || line.isEmpty()) {
			throw new EmptyLineException("Record is empty");
		}

		boolean isValid = true;
		// detailVO.setIsrecordvalid(true);

		String fileSection = mappings.get(0).getFieldType();

		processStartOfLine(fileSection);

		for (MappingInfoDto fieldMapping : mappings) {
			if (fileSection == null) {
				fileSection = fieldMapping.getFieldType();
			}
			String value = line.substring(fieldMapping.getStartPosition().intValue(),
					fieldMapping.getEndPosition().intValue());
			
			/*
			 * if((fieldMapping.getFieldName().equals("D_LIC_EFFECTIVE_FROM")) ||
			 * (fieldMapping.getFieldName().equals("D_LIC_EFFECTIVE_TO"))) { isValid =
			 * genericValidation.doValidation(fieldMapping, value); }
			 */

			isValid = genericValidation.doValidation(fieldMapping, value.trim());
			log.info("Validation for field {} :value: {} valid: {}", fieldMapping.getFieldName(), value.trim(),
					isValid);
			
			
			if (isValid && ((fieldMapping.getFieldType().equals(ICLPConstants.FILENAME)) || (fieldMapping.getFieldType().equals(ICLPConstants.HEADER)) || (fieldMapping.getFieldType().equals(ICLPConstants.DETAIL)))) {
				setIsrecordValid(true);
			} 
			 else if (!isValid && fieldMapping.getFieldType().equals(ICLPConstants.DETAIL)) {
				// detailVO.setIsrecordvalid(false);
				setIsrecordValid(false);
				break;
			}
			
			
			/*
			 * else if(isValid && fieldMapping.getFieldType().equals(ICLPConstants.DETAIL))
			 * { detailVO.setIsrecordvalid(true); }
			 */
		}

		processEndOfLine(fileSection);
		
		

	}
}
