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
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.conduent.tpms.iag.constants.AckStatusCode;
import com.conduent.tpms.iag.constants.ICRXConstants;
import com.conduent.tpms.iag.dao.TQatpMappingDao;
import com.conduent.tpms.iag.dao.impl.TAgencyDaoImpl;
import com.conduent.tpms.iag.dto.AckFileInfoDto;
import com.conduent.tpms.iag.dto.AckFileWrapper;
import com.conduent.tpms.iag.dto.FileParserParameters;
import com.conduent.tpms.iag.dto.MappingInfoDto;
import com.conduent.tpms.iag.exception.CustomException;
import com.conduent.tpms.iag.exception.EmptyLineException;
import com.conduent.tpms.iag.exception.FileAlreadyProcessedException;
import com.conduent.tpms.iag.exception.InvalidFileDetailException;
import com.conduent.tpms.iag.exception.InvalidFileNameException;
import com.conduent.tpms.iag.exception.InvalidFileStatusException;
import com.conduent.tpms.iag.exception.InvalidFileTypeException;
import com.conduent.tpms.iag.exception.InvalidRecordCountException;
import com.conduent.tpms.iag.exception.InvalidRecordException;
import com.conduent.tpms.iag.exception.InvlidFileHeaderException;
import com.conduent.tpms.iag.exception.TransactionFileDataMismatch;
import com.conduent.tpms.iag.model.FileDetails;
import com.conduent.tpms.iag.model.XferControl;
import com.conduent.tpms.iag.utility.FileComparator;
import com.conduent.tpms.iag.utility.GenericValidation;
import com.conduent.tpms.iag.utility.MasterDataCache;

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
	TAgencyDaoImpl agencyDao;

	@Autowired
	protected MasterDataCache masterDataCache;

	private FileUtil fileUtil = new FileUtil();
	private String fileType;
	private String fileFormat;
	private String agencyId;
	private String toAgencyId;

	private FileParserParameters configurationMapping;
	private FileParserParameters fileParserParameter = new FileParserParameters();
	protected String fileName;
	// protected Long recordCount;
	protected Map<String, Long> recordCount = new HashMap<>();
	public File newFile;
	protected XferControl xferControl;
	public File threadFile;
	protected boolean isFirstRead = true;

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

	public void initialize(String processFile) {

	}

	/**
	 * Starts processing of ICRX and IRXC file.
	 * 
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public void fileParseTemplate(String fileType, File file) throws IOException, InterruptedException {
		
		try {
			log.info("File name {} taken for process", file.getName());
			recordCount.put(file.getName(), textFileReader.getRecordCount(file));
			// recordCount = textFileReader.getRecordCount(file);
			validateAndProcessFile(file);
			AckStatusCode ackStatusCode = AckStatusCode.SUCCESS;
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
				log.info("Start of InvalidFileNameException...");
				{
					AckStatusCode ackStatusCode = AckStatusCode.INVALID_FILE;
					if (ex.getMessage().contains("Date mismatch") || ex.getMessage().contains("Time mismatch")) {
						ackStatusCode = AckStatusCode.DATE_MISMATCH;
					}
					AckFileWrapper ackObj = prePareAckFileMetaData(ackStatusCode, file);
					ackObj.setFileDestDir(getConfigurationMapping().getFileInfoDto().getUnProcDir());
					processAckFile(ackObj);
				}
			}
			else if (ex instanceof InvlidFileHeaderException) {
				log.info("Start of InvlidFileHeaderException...");
				AckStatusCode ackStatusCode = AckStatusCode.HEADER_FAIL;
				if (ex.getMessage().contains("Date mismatch") || ex.getMessage().contains("Time mismatch")) {
					ackStatusCode = AckStatusCode.DATE_MISMATCH;
				}
				AckFileWrapper ackObj = prePareAckFileMetaData(ackStatusCode, file);
				ackObj.setFileDestDir(getConfigurationMapping().getFileInfoDto().getUnProcDir());
				processAckFile(ackObj);
			} else if (ex instanceof InvalidRecordCountException) {
				log.info("Start of InvalidRecordCountException...");
				AckStatusCode ackStatusCode = AckStatusCode.INVALID_RECORD_COUNT;
				AckFileWrapper ackObj = prePareAckFileMetaData(ackStatusCode, file);
				ackObj.setFileDestDir(getConfigurationMapping().getFileInfoDto().getUnProcDir());
				processAckFile(ackObj);
			} else if (ex instanceof FileAlreadyProcessedException) {
				log.info("Start of FileAlreadyProcessedException...");
				//Need to Check if this should be moved to proc or unproc directory
				AckStatusCode ackStatusCode = AckStatusCode.FILE_ALREADY_PROCESSED;
				AckFileWrapper ackObj = prePareAckFileMetaData(ackStatusCode, file);
				//ackObj.setFileDestDir(getConfigurationMapping().getFileInfoDto().getProcDir());
				ackObj.setFileDestDir(getConfigurationMapping().getFileInfoDto().getUnProcDir());
				processAckFile(ackObj);
				log.info(ex.getMessage());
			} else if (ex instanceof InvalidRecordException) {
				log.info("Start of InvalidRecordException...");
				AckStatusCode ackStatusCode = AckStatusCode.DETAIL_FAIL;
				AckFileWrapper ackObj = prePareAckFileMetaData(ackStatusCode, file);
				ackObj.setFileDestDir(getConfigurationMapping().getFileInfoDto().getUnProcDir());
				processAckFile(ackObj);
				log.info(ex.getMessage());
			} else if (ex instanceof InvalidFileStatusException) {
				log.info("Start of InvalidFileStatusException...");
				{
					AckStatusCode ackStatusCode = AckStatusCode.INVALID_FILE;
					if (ex.getMessage().contains("Date mismatch") || ex.getMessage().contains("Time mismatch")) {
						ackStatusCode = AckStatusCode.DATE_MISMATCH;
					}
					AckFileWrapper ackObj = prePareAckFileMetaData(ackStatusCode, file);
					ackObj.setFileDestDir(getConfigurationMapping().getFileInfoDto().getUnProcDir());
					processAckFile(ackObj);
				}
			} else if (ex instanceof EmptyLineException) {
				log.info("Start of EmptyLineException...");
				AckStatusCode ackStatusCode = AckStatusCode.DETAIL_FAIL;
				AckFileWrapper ackObj = prePareAckFileMetaData(ackStatusCode, file);
				ackObj.setFileDestDir(getConfigurationMapping().getFileInfoDto().getUnProcDir());
				processAckFile(ackObj);
				log.info(ex.getMessage());
			} else if (ex instanceof InvalidFileTypeException) {
				log.info("Start of InvalidFileTypeException...");
				{
					AckStatusCode ackStatusCode = AckStatusCode.INVALID_FILE;
					if (ex.getMessage().contains("Date mismatch") || ex.getMessage().contains("Time mismatch")) {
						ackStatusCode = AckStatusCode.DATE_MISMATCH;
					}
					AckFileWrapper ackObj = prePareAckFileMetaData(ackStatusCode, file);
					ackObj.setFileDestDir(getConfigurationMapping().getFileInfoDto().getUnProcDir());
					processAckFile(ackObj);
				}
			} else if (ex instanceof DateTimeParseException) {
				ex.printStackTrace();
				log.info("Start of DateTimeParseException...");
				AckStatusCode ackStatusCode = AckStatusCode.DATE_MISMATCH;
				AckFileWrapper ackObj = prePareAckFileMetaData(ackStatusCode, file);
				ackObj.setFileDestDir(getConfigurationMapping().getFileInfoDto().getUnProcDir());
				processAckFile(ackObj);
			}else if (ex instanceof TransactionFileDataMismatch) {
				ex.printStackTrace();
				log.info("Getting duplicate LaneTxId or Transaction mismatch in record");
				AckStatusCode ackStatusCode = AckStatusCode.TRANSACTION_FILE_UNAVAILABLE;
				AckFileWrapper ackObj = prePareAckFileMetaData(ackStatusCode, file);
				ackObj.setFileDestDir(getConfigurationMapping().getFileInfoDto().getUnProcDir());
				processAckFile(ackObj);
			} else {
				ex.printStackTrace();
			}
		}
		
		getMissingFilesForAgency();
		log.info("Exiting File processing ..");
		
	}

	public void getMissingFilesForAgency() {
		// TODO Auto-generated method stub

	}

	/*
	 * Validated and process ICTXfile.
	 */

	public void validateAndProcessFile(File file)
			throws InvalidFileNameException, InvalidFileDetailException, InvlidFileHeaderException, EmptyLineException,
			InvalidRecordException, InvalidRecordCountException, DateTimeParseException, InvalidFileStatusException,
			IOException, InvalidFileTypeException, FileAlreadyProcessedException, TransactionFileDataMismatch {
		
		startFileProcess(file);
		validateAndProcessFileName(file.getName());

	//	String record = textFileReader.readLine();
		log.info("record count =======> "+textFileReader.getRecordCount(file));
		//for (int i = 1; i <= textFileReader.getRecordCount(file); i++) {
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
			if (prviousLine != null) {
				parseValidateAndProcessDetail(prviousLine);
			}
		}

		textFileReader.closeFile();
		//isFirstRead = true;
		//}
		finishFileProcess();
		log.info("End of finish file process method...");

	}

	public void startFileProcess(File file)
			throws InvalidFileStatusException, IOException, InvalidFileNameException, InvalidFileTypeException {
		setFileName(file.getName());
		setNewFile(file);
		if (fileType.equalsIgnoreCase("icrx")) {
			CheckValidFileType(file);
			log.info("====> CheckValidFileType() for icrx file");
		}
		if (fileType.equalsIgnoreCase("irxc")) {
			CheckIRXCValidFileType(file);
			log.info("====> CheckValidFileType() for irxc file");
		}
		xferControl = tQatpMappingDao.checkFileDownloaded(file.getName());
		if (xferControl == null) {
			log.info("File is not downloaded completely: {}", file.getName());
			throw new InvalidFileStatusException("File not downloded");
		}
	}

	public void validateAndProcessFileName(String fileName)
			throws InvalidFileNameException, InvlidFileHeaderException, InvalidFileDetailException, IOException,
			FileAlreadyProcessedException, EmptyLineException, InvalidRecordException, DateTimeParseException {
		parseAndValidate(fileName, configurationMapping.getFileNameMappingInfo());

	}

	public void insertFileDetailsIntoCheckpoint() {
		// TODO Auto-generated method stub

	}

	/*
	 * Validates header
	 */
	public void validateAndProcessHeader(String line) throws InvalidFileNameException, InvalidFileDetailException,
			InvlidFileHeaderException, EmptyLineException, InvalidRecordException, DateTimeParseException, FileAlreadyProcessedException {
		if(line.length()!=ICRXConstants.HEADER_SIZE) {
			throw new InvlidFileHeaderException("Invalid File Header format: Header Length Mismatch");
		}
		parseAndValidate(line, configurationMapping.getHeaderMappingInfoList());
		
	}

	/*
	 * Validates detail record
	 */
	public void parseAndValidateDetails(String line) throws InvalidFileNameException, InvalidFileDetailException,
			InvlidFileHeaderException, EmptyLineException, InvalidRecordException, DateTimeParseException, FileAlreadyProcessedException {
		if(line.length()!=ICRXConstants.DATA_SIZE) {
			throw new InvalidRecordException("Record Length Mismatch");
		}
		parseAndValidate(line, configurationMapping.getDetailRecMappingInfo());
	}

	/*
	 * Validates footer
	 */
	public void parseAndValidateFooter(String line) throws InvalidFileNameException, InvalidFileDetailException,
			InvlidFileHeaderException, EmptyLineException, InvalidRecordException, DateTimeParseException, FileAlreadyProcessedException {
		parseAndValidate(line, configurationMapping.getTrailerMappingInfoDto());
	}

	/*
	 * Loads configuration mapping details for ICRX file from T_FIELD_MAPPING table
	 */
	public void loadConfigurationMapping(String fileType) {
		getFileParserParameter().setFileType(fileType);
		setConfigurationMapping(tQatpMappingDao.getMappingConfigurationDetails(fileType));
	}

	/*
	 * Loads configuration mapping details for IRXC file from T_FIELD_MAPPING table
	 */
	public void loadIRXCConfigurationMapping(String fileType) {
		getFileParserParameter().setFileType(ICRXConstants.IRXC);
		setConfigurationMapping(tQatpMappingDao.getIRXCMappingConfigurationDetails(getFileParserParameter()));
	}

	/*
	 * Validates field's according to their mapping from T_FIELD_MAPPING table
	 */
	public void doFieldMapping(String value, MappingInfoDto fMapping)
			throws InvalidFileNameException, InvlidFileHeaderException, DateTimeParseException, InvalidRecordException, FileAlreadyProcessedException {
	}

	/*
	 * Parse IITC file according to their sections i.e Header, Detail
	 */
	public void parseAndValidate(String line, List<MappingInfoDto> mappings)
			throws InvalidFileNameException, InvalidFileDetailException, InvlidFileHeaderException, EmptyLineException,
			InvalidRecordException, DateTimeParseException, FileAlreadyProcessedException {

		boolean isValid = true;
		String etcTrxType = "";
		String fileSection = mappings.get(0).getFieldType();

		processStartOfLine(fileSection);

		for (MappingInfoDto fieldMapping : mappings)

		{
			if (fileSection == null) {
				fileSection = fieldMapping.getFieldType();
			}
			String value = null;
			/*if (fieldMapping.getFieldName().equalsIgnoreCase(ICRXConstants.D_ETC_TRX_TYPE)) {
				value = line.substring(fieldMapping.getStartPosition().intValue(),
						fieldMapping.getEndPosition().intValue());
				etcTrxType = value.trim();
			}
			if (fieldMapping.getFieldName().equalsIgnoreCase(ICRXConstants.D_CUST_ZIP)) {
				value = line.substring(fieldMapping.getStartPosition().intValue(), line.length());
				isValid = genericValidation.doValidation(fieldMapping, value.trim());

				doFieldMapping(value.trim(), fieldMapping);
				log.info("Validation for field {} :value: {} valid: {}", fieldMapping.getFieldName(), value.trim(),
						isValid);
			} else if ((fieldMapping.getFieldName().equalsIgnoreCase(ICRXConstants.D_ETC_ENTRY_DATE)
					|| fieldMapping.getFieldName().equalsIgnoreCase(ICRXConstants.D_ETC_ENTRY_TIME)
					|| fieldMapping.getFieldName().equalsIgnoreCase(ICRXConstants.D_ETC_ENTRY_PLAZA)
					|| fieldMapping.getFieldName().equalsIgnoreCase(ICRXConstants.D_ETC_ENTRY_LANE))
					&& etcTrxType.equalsIgnoreCase(ICRXConstants.LOV_B)) { // validate date based on field
																			// D_ETC_TRX_TYPE

				value = line.substring(fieldMapping.getStartPosition().intValue(),
						fieldMapping.getEndPosition().intValue());

				doFieldMapping(value.trim(), fieldMapping);
				log.info("Validation for field {} :value: {} valid: {}", fieldMapping.getFieldName(), value.trim(),
						isValid);

			} else*/ {
				value = line.substring(fieldMapping.getStartPosition().intValue(),
						fieldMapping.getEndPosition().intValue());
				isValid = genericValidation.doValidation(fieldMapping, value);
				log.info("Validation for {} is: {}",value,isValid);
				doFieldMapping(value, fieldMapping);
				log.info("Validation for field {} :value: {} valid: {}", fieldMapping.getFieldName(), value.trim(),
						isValid);
			}

			if (!isValid && fieldMapping.getFileLevelRejection() != null
					&& fieldMapping.getFileLevelRejection().equalsIgnoreCase("y")) {
				if (fieldMapping.getFieldType().equals(ICRXConstants.FILENAME)) {
					throw new InvalidFileNameException("Invalid File Name format");
				}
				if (fieldMapping.getFieldType().equals(ICRXConstants.HEADER)) {
					throw new InvlidFileHeaderException("Invalid File Header format");
				}
				if (fieldMapping.getFieldType().equals(ICRXConstants.DETAIL)) {
					log.info("Invlalid record data in record {}", value);
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
	 * Parses details record in ICRX file
	 */
	public void parseValidateAndProcessDetail(String prviousLine) throws InvalidFileNameException,
			InvlidFileHeaderException, EmptyLineException, InvalidRecordException, InvalidFileDetailException, DateTimeParseException, FileAlreadyProcessedException {
		parseAndValidateDetails(prviousLine);
		processDetailLine();
	}

	/*
	 * Handles details record in iCRX file
	 */
	public String handleDetailLines(String currentLine) throws InvalidFileNameException, InvlidFileHeaderException,
			EmptyLineException, InvalidRecordException, InvalidFileDetailException, DateTimeParseException, FileAlreadyProcessedException {
		String prviousLine;
		prviousLine = currentLine;
		currentLine = textFileReader.readLine();
		//Logic commented for duplicate records
		/*
		 * if (prviousLine.equals(currentLine)) {
		 * log.info("prvious Line ==> "+prviousLine+ "Current Line ==> "+currentLine);
		 * throw new InvalidRecordException("file record having exact same value.."); }
		 */
		do {
			parseValidateAndProcessDetail(prviousLine);
			prviousLine = currentLine;
			currentLine = textFileReader.readLine();
		} while (currentLine != null);
		return prviousLine;
	}

	public void processDetailLine() {

	}

	public void finishFileProcess()
			throws InvlidFileHeaderException, FileAlreadyProcessedException, InvalidRecordException, TransactionFileDataMismatch {

	}

	/*
	 * Get ICRX file's list
	 */
	public List<File> getAllFilesFromSourceFolder() throws IOException {
		List<File> allfilesFromSrcDirectory = fileUtil
				.getAllfilesFromSrcDirectory(getConfigurationMapping().getFileInfoDto().getSrcDir());
		log.info("List of files : {}", allfilesFromSrcDirectory.toString());
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
	 * Transfers the ICRX file
	 */
	public void transferFile(File srcFile, String destDirPath) throws IOException {
		try {
			log.info("Calling tranfer file : {}", destDirPath);
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
	 * Process acknowledgement and transfer the ICRX file accordingly
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
	 * Create acknowledgement for ICRX file
	 */
	public void createAckFile(String ackFileName, String ackFileContent, AckFileInfoDto ackFileInfoDto)
			throws IOException, CustomException {
		log.info("Creating Ack file {}", ackFileName);
		log.info("ackFileContent: {} ", ackFileContent);
		File destFile = new File(getConfigurationMapping().getFileInfoDto().getAckDir(), ackFileName);
		log.info("Destination file: {} ", destFile);
		if (destFile.exists()) {
			destFile.delete();
		}

		File ackFile = new File(getConfigurationMapping().getFileInfoDto().getAckDir(), ackFileName);
		log.info("Ack file: {} ", ackFile);
		try (FileOutputStream fos = new FileOutputStream(ackFile)) {
			fos.write(ackFileContent.getBytes());
			tQatpMappingDao.insertAckDetails(ackFileInfoDto);
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

	public void CheckIRXCValidFileType(File file) throws InvalidFileTypeException {
		// TODO Auto-generated method stub

	}

	public void updateFileDetailsIntoCheckpoint() {
		// TODO Auto-generated method stub

	}

	public void insertFileDetailsIntoStatistics() {
		// TODO Auto-generated method stub

	}

	public void insertFileDetailsIntoCheckpoint(String fileName) {
		// TODO Auto-generated method stub

	}
}
