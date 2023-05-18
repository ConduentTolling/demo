package com.conduent.tpms.qatp.validation;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import com.conduent.tpms.qatp.constants.AckStatusCode;
import com.conduent.tpms.qatp.constants.Constants;
import com.conduent.tpms.qatp.constants.FileStatusInd;
import com.conduent.tpms.qatp.dao.IQatpDao;
import com.conduent.tpms.qatp.dao.ITransDetailDao;
import com.conduent.tpms.qatp.dao.TQatpMappingDao;
import com.conduent.tpms.qatp.dao.impl.InsertMTADaoImpl;
import com.conduent.tpms.qatp.dto.AckFileInfoDto;
import com.conduent.tpms.qatp.dto.AckFileWrapper;
import com.conduent.tpms.qatp.dto.FileNameDetailVO;
import com.conduent.tpms.qatp.dto.FileParserParameters;
import com.conduent.tpms.qatp.dto.MappingInfoDto;
import com.conduent.tpms.qatp.dto.MtaFileNameInfo;
import com.conduent.tpms.qatp.exception.CustomException;
import com.conduent.tpms.qatp.exception.EmptyLineException;
import com.conduent.tpms.qatp.exception.FileAlreadyProcessedException;
import com.conduent.tpms.qatp.exception.InvalidDateTimeException;
import com.conduent.tpms.qatp.exception.InvalidFileNameException;
import com.conduent.tpms.qatp.exception.InvalidFileStatusException;
import com.conduent.tpms.qatp.exception.InvalidRecordCountException;
import com.conduent.tpms.qatp.exception.InvalidTrailerException;
import com.conduent.tpms.qatp.exception.InvlidFileDetailException;
import com.conduent.tpms.qatp.exception.InvlidFileHeaderException;
import com.conduent.tpms.qatp.model.ReconciliationCheckPoint;
import com.conduent.tpms.qatp.model.XferControl;
import com.conduent.tpms.qatp.service.ITransDetailService;
import com.conduent.tpms.qatp.utility.AsyncProcessCall;
import com.conduent.tpms.qatp.utility.GenericValidation;
import com.conduent.tpms.qatp.utility.MasterDataCache;
import com.google.common.base.Stopwatch;

public class FileParserImpl implements Runnable {

	private static final Logger logger = LoggerFactory.getLogger(FileParserImpl.class);
//	protected MtaFileNameInfo fileNameVO;
//	protected boolean isHederPresentInFile;
//	protected boolean isDetailsPresentInFile;
//	protected boolean isTrailerPresentInFile;
//	protected boolean isLaneHeaderPresentInFile;
//
//	@Autowired
//	protected TQatpMappingDao tQatpMappingDao;
//
//	public boolean isLaneHeaderPresentInFile() {
//		return isLaneHeaderPresentInFile;
//	}
//
//	public void setLaneHeaderPresentInFile(boolean isLaneHeaderPresentInFile) {
//		this.isLaneHeaderPresentInFile = isLaneHeaderPresentInFile;
//	}
//
//	@Autowired
//	private IQatpDao qatpDao;
//
//	@Autowired
//	protected ITransDetailDao transDetailDao;
//
//	@Autowired
//	protected GenericValidation genericValidation;
//
//	@Autowired
//	private TextFileReader textFileReader;
//
//	@Autowired
//	protected MasterDataCache masterDataCache;
//
//	@Autowired
//	protected ITransDetailService transDetailService;
//	
//	@Autowired
//	InsertMTADaoImpl insertMTADaoImpl;
//
//	private FileUtil fileUtil = new FileUtil();
//	private String fileType;
//	private String fileFormat;
//	private String agencyId;
//	private String fileName;
//	protected XferControl xferControl;
//	protected ReconciliationCheckPoint reconciliationCheckPoint;
//	private Integer recordCounter;
//	protected boolean isFirstRead = true;
//
//	protected long recordCount;
	
	@Value("${mtafile.connect.url}")
	private String mtaFileURL;
	
	@Autowired 
	private RestTemplate restTemplate;
	
	protected boolean isHederPresentInFile;
	protected boolean isDetailsPresentInFile;
	protected boolean isTrailerPresentInFile;
	protected boolean isLaneHeaderPresentInFile;
	protected MtaFileNameInfo fileNameVO;
	protected int count = 0;
	
	public boolean isLaneHeaderPresentInFile() {
	return isLaneHeaderPresentInFile;
}

public void setLaneHeaderPresentInFile(boolean isLaneHeaderPresentInFile) {
	this.isLaneHeaderPresentInFile = isLaneHeaderPresentInFile;
}

	protected TQatpMappingDao tQatpMappingDao;

	protected IQatpDao qatpDao;

	protected ITransDetailService transDetailService;

	protected GenericValidation genericValidation;

	protected TextFileReader textFileReader;

	protected MasterDataCache masterDataCache;
	
	protected InsertMTADaoImpl insertMTADaoImpl;
	
	protected AsyncProcessCall asyncProcessCall;
	
	protected ITransDetailDao transDetailDao;

	private FileUtil fileUtil=new FileUtil();
	private String fileType;
	private String fileFormat;
	private String agencyId;
	private String fileName;
	protected XferControl xferControl;
	protected ReconciliationCheckPoint reconciliationCheckPoint;
	protected long recordCount;
	protected boolean isFirstRead=true;
	private Map<String,Integer> lineSize;
	private File file;
	protected Integer recordCounter;
	private FileParserParameters configurationMapping;
	protected FileParserParameters fileParserParameter=new FileParserParameters();
	
	private BufferedReader reader;
	private String STX = new String("\u0002".getBytes(StandardCharsets.US_ASCII));
	String ETX = new String("\u0003".getBytes(StandardCharsets.US_ASCII));
	private String HT = new String("\u0009".getBytes(StandardCharsets.US_ASCII));
	
	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}
	
	public TextFileReader getTextFileReader() {
		return textFileReader;
	}

	public void setTextFileReader(TextFileReader textFileReader) {
		this.textFileReader = textFileReader;
	}
	

	public void initialize() {
	}

	public void validateAndProcessFileName(String fileName) throws InvalidFileNameException, InvlidFileHeaderException,
			EmptyLineException, InvlidFileDetailException, InvalidTrailerException, InvalidDateTimeException {
		logger.info("Validate And process file name {}", fileName);
		parseAndValidate(fileName, configurationMapping.getFileNameMappingInfo());
	}

	public void validateAndProcessHeader(String line) throws InvalidFileNameException, InvlidFileHeaderException,
			EmptyLineException, InvlidFileDetailException, InvalidTrailerException, InvalidDateTimeException {
		parseAndValidate(line, configurationMapping.getHeaderMappingInfoList());
	}

	public void parseAndValidateLaneHeader(String line) throws Exception, InvalidFileNameException,
			InvlidFileHeaderException, EmptyLineException, InvlidFileDetailException, InvalidTrailerException {
		parseAndValidate(line, configurationMapping.getLaneHeaderappingInfo());
	}

	public void parseAndValidateDetails(String line) throws InvalidFileNameException, InvlidFileHeaderException,
			EmptyLineException, InvlidFileDetailException, InvalidTrailerException, InvalidDateTimeException {
		parseAndValidate(line, configurationMapping.getDetailRecMappingInfo());
	}

	public void parseAndValidateFooter(String line) throws InvalidFileNameException, InvlidFileHeaderException,
			EmptyLineException, InvlidFileDetailException, InvalidTrailerException, InvalidDateTimeException {
		parseAndValidate(line, configurationMapping.getTrailerMappingInfoDto());
	}

	public void loadConfigurationMapping() {
		logger.info("Start load configuration mapping...");
		setConfigurationMapping(tQatpMappingDao.getMappingConfigurationDetails(getFileParserParameter()));
	}

	public void doFieldMapping(String value, MappingInfoDto fMapping) throws InvlidFileHeaderException {
	}

	public void parseAndValidate(String line, List<MappingInfoDto> mappings)
			throws InvalidFileNameException, InvlidFileHeaderException, EmptyLineException, InvlidFileDetailException,
			InvalidTrailerException, InvalidDateTimeException {
		if (line == null || line.isEmpty()) {
			throw new EmptyLineException("Record is empty");
		}
		boolean isValid = true;
		processStartOfLine(mappings.get(0).getFieldType());
		for (MappingInfoDto fieldMapping : mappings) {
			logger.info("Processing Raw record {}", line);
			String value = line.substring(fieldMapping.getStartPosition().intValue(),
					fieldMapping.getEndPosition().intValue());
			try {
				isValid = genericValidation.doValidation(fieldMapping, value);

				if (fieldMapping.getFieldName().equals("H_CURRENT_TS")) {
					if (customDateValidation(value)) {
						isValid = true;
					} else {
						isValid = false;
					}
				}
			} catch (Exception ex) {
				logger.info("Exception {}", ex.getMessage());
			}

			logger.info("Field Mapping validation {} value {}, mapping {}", isValid, value, fieldMapping.toString());
			doFieldMapping(value, fieldMapping);
			if (!isValid && fieldMapping.getFileLevelRejection() != null
					&& fieldMapping.getFileLevelRejection().equalsIgnoreCase("Y")) {
				if (fieldMapping.getFieldType().equals("FILENAME")) {
					throw new InvalidFileNameException("Invalid File Name format");
				}
				if (fieldMapping.getFieldType().equals("HEADER")) {
					throw new InvlidFileHeaderException("Invalid File Header format");
				}
				if (fieldMapping.getFieldType().equals("LANEHEADER")) {
					throw new InvlidFileHeaderException("Invalid File Lane Header format");
				}
				if (fieldMapping.getFieldType().equals("DETAIL")) {
					throw new InvlidFileDetailException("Invalid File Detail format");
				}
				if (fieldMapping.getFieldType().equals("TRAILER")) {
					throw new InvalidTrailerException("Invalid File Trailer formatting");
				}

			}
		}
		if (!isFirstRead) {
			processEndOfLine(mappings.get(0).getFieldType());
		}
	}

	public boolean customDateValidation(String value) throws InvlidFileHeaderException {
		String validDate = value.substring(0, 10);
		if (fileNameVO.getFileDate().equals(validDate)) {

			return true;
		}
		logger.info("Header Date{} is not matching with Header Date", value);

		return false;

	}

	public void handleException(String fileSection) throws CustomException {

	}

	public void processEndOfLine(String fileSection) {

	}

	public void processStartOfLine(String fileSection) {

	}
	
	public void fileParseTemplatewithFileName(String fileName) throws IOException
	{		
//		Stopwatch stopwatch = Stopwatch.createStarted();
//		logger.info("Start time {}",new Date().toString());
		initialize();
		loadConfigurationMapping();
		List<File> allfilesFromSrcFolder = getAllfilesFromSrcDirectoryParallel();
		
		File file = checkFileExits(allfilesFromSrcFolder,fileName);
		logger.debug("File to be added in list of files {}",file);
		if(file!=null)
		{
			List<File> listOfFiles = new ArrayList<File>();
			listOfFiles.add(file);
			logger.debug("List of file {}",listOfFiles);
			fileParseTemplateInternal(listOfFiles); 
		}
		else
		{
			logger.info("File {} not  found in directory ",fileName);
		}
		
//		stopwatch.stop();
//		long millis = stopwatch.elapsed(TimeUnit.MILLISECONDS);
//		logger.info("Total time taken to process the files {}",millis);
//		logger.info("End time {}",new Date().toString());
	}

	public void fileParseTemplate() throws IOException
	{
		Stopwatch stopwatch = Stopwatch.createStarted();
		initialize();
		loadConfigurationMapping();
		List<File> allfilesFromSrcFolder = getAllFilesFromSourceFolder();
		
		fileParseTemplateInternal(allfilesFromSrcFolder);

		logger.info("No file found in directory {}",getConfigurationMapping().getFileInfoDto().getSrcDir());
		
		stopwatch.stop();
		long millis = stopwatch.elapsed(TimeUnit.MILLISECONDS);
		logger.info("Total time taken to process the files {}",millis);
	}
	
	/**
	 * 
	 * @throws IOException
	 * @throws Exception
	 */
	public void fileParseTemplateInternal(List<File> allfilesFromSrcFolder) throws IOException 
	{
//		initialize();
//		loadConfigurationMapping();
//		List<File> allfilesFromSrcFolder = getAllFilesFromSourceFolder();
//		logger.info("{} Files present in src Folder", allfilesFromSrcFolder.size());
		if (allfilesFromSrcFolder != null && allfilesFromSrcFolder.size() > 0) {
			logger.info("Total {} files selected for process", allfilesFromSrcFolder.size());
			for (File file : allfilesFromSrcFolder) {
				try {
					logger.info("File name {} taken for process", file.getName());
					fileName = file.getName();
					//recordCount = textFileReader.getRecordCount(file);
					recordCount = getRecordCount1(file);
					validateAndProcessFile(file);
					AckStatusCode ackStatusCode = AckStatusCode.SUCCESS;
					AckFileWrapper ackObj = prePareAckFileMetaData(ackStatusCode, file);

					processAckFile(ackObj);

				} catch (Exception ex) {
					ex.printStackTrace();
					//textFileReader.closeFile();
					//textFileReader.closeFile();
					closeFile();
					closeFile();

					if (ex instanceof InvalidFileNameException) {
						AckStatusCode ackStatusCode = AckStatusCode.INVALID_FILE_STRUCTURE;
						AckFileWrapper ackObj = prePareAckFileMetaData(ackStatusCode, file);
						processAckFile(ackObj);

					} else if (ex instanceof InvalidFileStatusException) {

					} else if (ex instanceof InvlidFileHeaderException) {
						AckStatusCode ackStatusCode = AckStatusCode.HEADER_FAIL;
						AckFileWrapper ackObj = prePareAckFileMetaData(ackStatusCode, file);
						processAckFile(ackObj);
//					} else if (ex instanceof InvalidDateTimeException) {
//						AckStatusCode ackStatusCode = AckStatusCode.INVALID_DATE_TIME;
//						AckFileWrapper ackObj = prePareAckFileMetaData(ackStatusCode, file);
//						processAckFile(ackObj);
					} else if (ex instanceof InvalidRecordCountException || ex instanceof InvalidTrailerException) {
						AckStatusCode ackStatusCode = AckStatusCode.INVALID_RECORD_COUNT;
						AckFileWrapper ackObj = prePareAckFileMetaData(ackStatusCode, file);
						processAckFile(ackObj);
					} else if (ex instanceof FileAlreadyProcessedException) {
						AckStatusCode ackStatusCode = AckStatusCode.DUPLICATE_FILE_NUM;
						AckFileWrapper ackObj = prePareAckFileMetaData(ackStatusCode, file);
						processAckFile(ackObj);
					} 
					else {
						AckStatusCode ackStatusCode = AckStatusCode.DETAIL_FAIL;
						AckFileWrapper ackObj = prePareAckFileMetaData(ackStatusCode, file);
						processAckFile(ackObj);
					}

				}
			}
		}
		logger.info("No file found in directory {}", getConfigurationMapping().getFileInfoDto().getSrcDir());
	}

	public AckFileWrapper prePareAckFileMetaData(AckStatusCode ackStatusCode, File file) {
		return null;
	}

	public void processAckFile(AckFileWrapper ackObj) {
		try {
			logger.info("Creating ack file {}", ackObj.getAckFileName());
			createAckFile(ackObj.getAckFileName(), ackObj.getSbFileContent(), ackObj.getAckFileInfoDto());
			transferFile(ackObj.getFile(), ackObj.getFileDestDir());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void validateAndProcessFile(File file) throws Exception {
		startFileProcess(file);
		validateAndProcessFileName(file.getName());
		for (int i = 1; i <= 2; i++) {
			// textFileReader.openFile(file, ETX);
//			textFileReader.openFile(file);
//			String prviousLine = textFileReader.readLine();
//			String currentLine = textFileReader.readLine();
			
			BufferedReader reader = getFileReader(file);
			String prviousLine = reader.readLine();
			String currentLine = reader.readLine();
			
			//String trailerLine = textFileReader.getlineBreak(file);
			String trailerLine = getlineBreak(file);
			if (prviousLine.charAt(75) == '') {
				String[] Extractcurrent = prviousLine.replaceFirst(STX, "seperator" + STX).split("seperator");
				prviousLine = Extractcurrent[0];
				currentLine = Extractcurrent[1] + trailerLine;
			} else {
				if(prviousLine.charAt(75)!='') {
					throw new InvlidFileHeaderException("Invalid File Lane Header format");
				}
				currentLine = prviousLine.substring(75) + trailerLine;
				prviousLine = prviousLine.substring(0, 75);
			}

			if (prviousLine == null) {
				// File is Empty.
			}

			if (prviousLine != null && currentLine == null) {
				// Only header is present.
			}

			if (prviousLine != null && currentLine != null) {
				if (isHederPresentInFile) {
					validateAndProcessHeader(prviousLine);
					validateRecordCount();
				} else {
					parseValidateAndProcessDetail(prviousLine);
				}

			}
			if (isLaneHeaderPresentInFile && prviousLine != null) {
				String laneHeader = null;
				if (prviousLine.charAt(52) == '	') {
					String[] extractLane = prviousLine.replaceFirst(HT, "seperator" + HT).split("seperator");
					laneHeader = extractLane[1];
				} else {
					laneHeader = prviousLine.substring(52);
				}
				parseAndValidateLaneHeader(laneHeader);
			}

			prviousLine = handleDetailLines(currentLine);

			if (isTrailerPresentInFile) {
				parseAndValidateFooter(prviousLine);
			}
//			else {
//			if (prviousLine != null) {
//				parseValidateAndProcessDetail(prviousLine);
//
//			}
//			// parseValidateAndProcessDetail(prviousLine);
//		}
//			textFileReader.closeFile();
			closeFile();
			isFirstRead = false;
		}
//		textFileReader.closeFile();
		closeFile();
		finishFileProcess();
	}

	public void parseValidateAndProcessDetail(String prviousLine)
			throws InvalidFileNameException, InvlidFileHeaderException, EmptyLineException, InvlidFileDetailException,
			InvalidTrailerException, InvalidDateTimeException {
		parseAndValidateDetails(prviousLine);
		processDetailLine();
	}

	public String handleDetailLines(String currentLine) throws Exception {
		String prviousLine = null;
		String[] current = currentLine.replace(ETX, ETX + "seperator").split("seperator");
		Integer counter = 0;
		Integer preProcessedRecordCount = -1;
		recordCounter = 0;
		
		if (reconciliationCheckPoint != null) {
			preProcessedRecordCount = reconciliationCheckPoint.getRecordCount();
		}
		// Integer recordCount = 1;
		int i = 0;
//		prviousLine = currentLine;
//		currentLine = textFileReader.readLine();
		do {

			try {
				for (i = 0; i < current.length - 1; i++) {
					
					prviousLine = (current[i]);
					if(prviousLine.contains(HT)) {
						String[] getLaneHeader = current[i].replaceFirst(STX, "seperator" + STX).split("seperator");
						parseAndValidateLaneHeader(getLaneHeader[0]);
						prviousLine = (getLaneHeader[1]);
						
					}
					
					if (recordCounter >= preProcessedRecordCount) {
						
						if(prviousLine.charAt(0)=='' && prviousLine.charAt(153)=='') {
							parseValidateAndProcessDetail(prviousLine);
						}
						else {
							throw new InvlidFileDetailException("Invalid File Detail format");
						}
						
						if (counter == Constants.BATCH_RECORD_COUNT && !isFirstRead) {
							saveRecords(reconciliationCheckPoint);
							reconciliationCheckPoint.setFileStatusInd(FileStatusInd.IN_PROGRESS);
							reconciliationCheckPoint.setRecordCount(recordCounter);
							tQatpMappingDao.updateRecordCount(reconciliationCheckPoint);
							counter = 0;
						}
						//parseValidateAndProcessDetail(prviousLine);
						//counter++;
					}
					recordCounter++;
				}
			} catch (EmptyLineException ex) {
				logger.info("Exception {}", ex.getMessage());
			}
//			prviousLine = currentLine;
//			currentLine = textFileReader.readLine();
			if (current.length == i + 1) {
				currentLine = null;
				prviousLine = (current[i]);
			}
		} while (currentLine != null);
		if (!isFirstRead) {
			saveRecords(reconciliationCheckPoint);
			reconciliationCheckPoint.setFileStatusInd(FileStatusInd.COMPLETE);
			reconciliationCheckPoint.setRecordCount(recordCounter);
			tQatpMappingDao.updateRecordCount(reconciliationCheckPoint);
		}
		if(prviousLine.contains(HT)) {
			String[] getLaneHeader = current[i].replaceFirst("\r", "seperator" + "\r").split("seperator");
			parseAndValidateLaneHeader(getLaneHeader[0]);
			prviousLine = (getLaneHeader[1]);	
		}
		return prviousLine;
	}

	public void saveRecords(ReconciliationCheckPoint reconciliationCheckPoint) throws ParseException {

	}

	public void processDetailLine() {

	}

	public void validateRecordCount() throws InvalidRecordCountException {

	}

	public void startFileProcess(File file) throws InvalidFileStatusException, FileAlreadyProcessedException {
		xferControl = tQatpMappingDao.checkFileDownloaded(file.getName());
		if (xferControl == null) {
			logger.info("File {} is not downloded", file.getName());
			throw new InvalidFileStatusException("File not downloded");
		}

		reconciliationCheckPoint = tQatpMappingDao.getReconsilationCheckPoint(file.getName());
		XferControl insertionMtaData = insertMTADaoImpl.getXferDataForStatistics(xferControl.getXferFileName());
		if (reconciliationCheckPoint == null) {
			reconciliationCheckPoint = new ReconciliationCheckPoint();
			reconciliationCheckPoint.setFileName(file.getName());
			reconciliationCheckPoint.setFileStatusInd(FileStatusInd.START);
			reconciliationCheckPoint.setRecordCount(0);
			reconciliationCheckPoint.setProcessDate(new Date());
			tQatpMappingDao.insertReconciliationCheckPoint(reconciliationCheckPoint);
			logger.info("Record is inserted in checkpoint table for file {}", file.getName());
			// Getting Data from TPMS.T_XFER_CONTROL table
			// Inserting Data into TPMS.T_QATP_STATISTICS table from TPMS.T_XFER_CONTROL table
			if(insertionMtaData !=null) 
			{
				insertMTADaoImpl.insertMtaFileInQATPStats(insertionMtaData);
			}

		} else if (reconciliationCheckPoint.getFileStatusInd()==FileStatusInd.START)
		{
			logger.info("Entering in else if where file status is START");
			if(insertionMtaData !=null) 
			{
				insertMTADaoImpl.updateStatisticsTable(insertionMtaData.getXferControlId(), fileName);
			}
		}else if (reconciliationCheckPoint.getFileStatusInd() == FileStatusInd.COMPLETE) 
		{
			//Uat Bug 69592
			//long prevXferControlId = tQatpMappingDao.getPrevXferControlId(file.getName());
			logger.info("File {} is already processed with xferControlId {} and Process date {}",file.getName(), reconciliationCheckPoint.getXferControlId(),reconciliationCheckPoint.getProcessDate());
			throw new FileAlreadyProcessedException("File already processed");
		}

	}

	public void finishFileProcess() throws CustomException {

	}

	public List<File> getAllfilesFromSrcDirectoryParallel() throws IOException {
		List<File> allfilesFromSrcDirectory = fileUtil
				.getAllfilesFromSrcDirectory(getConfigurationMapping().getFileInfoDto().getSrcDir(),fileType);
		logger.info("src Folder Path {}", getConfigurationMapping().getFileInfoDto().getSrcDir());
		return allfilesFromSrcDirectory;
	}
	
	public List<File> getAllFilesFromSourceFolder() throws IOException 
	{
		List<File> allfilesFromSrcDirectory = fileUtil.getAllfilesFromSrcDirectory(getConfigurationMapping().getFileInfoDto().getSrcDir(),fileType);
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

	public void transferFile(File srcFile, String destDirPath) throws IOException {
		try {

			Path destDir = new File(destDirPath, srcFile.getName()).toPath();

			File temp = destDir.toFile();
			if (temp.exists()) {
				temp.delete();
			}

			Path result = Files.move(Paths.get(srcFile.getAbsolutePath()), destDir);

			if (null != result) {
				logger.info("File moved from {} to {} successfully", srcFile.getName(), destDirPath);
			} else {
				logger.info("File transfer failed.");
			}
		} catch (IOException iex) {
			throw iex;
		}
	}

	public void createAckFile(String ackFileName, String ackFileContent, AckFileInfoDto ackFileInfoDto)
			throws IOException, CustomException {
		isFirstRead = true;
		File destFile = new File(getConfigurationMapping().getFileInfoDto().getAckDir(), ackFileName);
		if (destFile.exists()) {
			destFile.delete();
		}
		File ackFile = new File(getConfigurationMapping().getFileInfoDto().getAckDir(), ackFileName);
		try (FileOutputStream fos = new FileOutputStream(ackFile)) {
			fos.write(ackFileContent.getBytes());
			logger.info("File {} created successfully", ackFile);
			qatpDao.insertAckStatus(ackFileInfoDto);
			logger.info("Inserting record in ack table {}", ackFileInfoDto.toString());
		} catch (FileNotFoundException e) {
			throw e;
		} catch (IOException e) {
			throw e;
		}
		logger.info("File {} created successfully", ackFileName);

	}

	@Override
	public void run() 
	{
		logger.info("Thread Start Time {} and Thread Name {} ",new Date().toString(),Thread.currentThread().getName());
		try 
		{
			Stopwatch stopwatch = Stopwatch.createStarted();
			
			//processFile();
			fileParseTemplatewithFileName(file.getName().toString());
			
			stopwatch.stop();
			long millis = stopwatch.elapsed(TimeUnit.MILLISECONDS);
			logger.info("Total time taken to process the filename {} is {}",file.getName(),millis);
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	
	
	private File checkFileExits(List<File> allfilesFromSrcFolder,String fileName)
	{
		Optional<File> file = allfilesFromSrcFolder.stream().filter(e-> e.getName().equalsIgnoreCase(fileName)).findFirst(); ///  .map(file -> file.getName()).collect());
		
		if(!file.isEmpty())
		{
			logger.info("File {} is present in source folder",fileName);
			return file.get();
		}
		return null;
		
		//return false;
	}
	
	public List<String> process() throws IOException
	{
		loadConfigurationMapping();
		List<File> allfilesFromSrcFolder = fileUtil.getAllfilesFromSrcDirectory1(getConfigurationMapping().getFileInfoDto().getSrcDir(),fileType);
		logger.info("Src Folder Path {}", getConfigurationMapping().getFileInfoDto().getSrcDir());
		
		List<String> result = new ArrayList<String>();
		logger.info("Files picked from source directory are : {}",allfilesFromSrcFolder.toString());
		
		for(File file : allfilesFromSrcFolder)
		{
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<String> entity = new HttpEntity<String>(headers);
			
			String url = null;
			url = mtaFileURL.concat("?fileName=" + file.getName());
			logger.info("URL to be used {}",url);
				
//			ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, entity,String.class);
//			if(responseEntity!=null)
//			{
//				logger.info("Response:{}",responseEntity.getBody());
//				result.add(responseEntity.getBody());
//			}
			
			String info = asyncProcessCall.callParallelProcess(url,entity);
			result.add(info);
		}
		 return result;
	}
	
	private BufferedReader getFileReader(File file) throws IOException
	{
		//BufferedReader reader = new BufferedReader(new FileReader(file));
		
		reader = new BufferedReader(new FileReader(file));
		reader.mark(1);
		
		return reader;
	}
	
	public long getRecordCount1(File file)
	{
		int count=0;
		int count1 = 0;
		try {
            
             String STX = new String("\u0002".getBytes(StandardCharsets.US_ASCII));
			Scanner scanner = new java.util.Scanner(file);
			while(scanner.hasNext())
			{
				 String paragraph = scanner.next();
				if(paragraph.contains(STX)) {
					count++;
				}
				
			}
			scanner.close();
			count1 = getRecordCountForETX(file);
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(count1>count) {
			return count1;
		}
		else if(count1<count) {
			return count;
		}
		return count;
	}
	
	public int getRecordCountForETX(File file2) {
		int count=0;
		try {
            
             String ETX = new String("\u0003".getBytes(StandardCharsets.US_ASCII));
			Scanner scanner = new java.util.Scanner(file2);
			while(scanner.hasNext())
			{
				 String paragraph = scanner.next();
				if(paragraph.contains(ETX)) {
					count++;
				}
				
			}
			scanner.close();
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}
	
	public void closeFile() throws IOException {
		if(reader != null ) 
		{
			reader.close();
		}
	}
	
	public String getlineBreak(File file2) throws IOException {
		FileReader fr = new FileReader(file2);
        String line1 = "";
        String line2 = "";
        int c;
        while ((c = fr.read()) >= 0) {
            if(c == '\r' || c =='\n') {
                line1 += (char)c;
            }else {
                line1 = "empty";
            }
        }
        fr.close();
        if(line1.contains(new String(Character.toString((char) 13))) || line1.contains(new String(Character.toString((char) 10)))){
        	line1="";
        	return line1+=(char)'\r';
        }else {
		return line1;
   
        }
	}

}