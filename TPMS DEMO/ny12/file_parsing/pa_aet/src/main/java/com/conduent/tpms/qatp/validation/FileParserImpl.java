package com.conduent.tpms.qatp.validation;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

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
import com.conduent.tpms.qatp.dao.impl.InsertPaDaoImpl;
import com.conduent.tpms.qatp.dto.AckFileInfoDto;
import com.conduent.tpms.qatp.dto.AckFileWrapper;
import com.conduent.tpms.qatp.dto.FileNameDetailVO;
import com.conduent.tpms.qatp.dto.FileParserParameters;
import com.conduent.tpms.qatp.dto.MappingInfoDto;
import com.conduent.tpms.qatp.dto.PaFileNameDetailVO;
import com.conduent.tpms.qatp.dto.PaHeaderInfoVO;
import com.conduent.tpms.qatp.exception.ATRNFileNumDuplicationException;
import com.conduent.tpms.qatp.exception.CustomException;
import com.conduent.tpms.qatp.exception.EmptyLineException;
import com.conduent.tpms.qatp.exception.FileAlreadyProcessedException;
import com.conduent.tpms.qatp.exception.GapInSeqNumException;
import com.conduent.tpms.qatp.exception.InvalidFileNameException;
import com.conduent.tpms.qatp.exception.InvalidFileStatusException;
import com.conduent.tpms.qatp.exception.InvalidRecordCountException;
import com.conduent.tpms.qatp.exception.InvlidFileDetailException;
import com.conduent.tpms.qatp.exception.InvlidFileHeaderException;
import com.conduent.tpms.qatp.model.ReconciliationCheckPoint;
import com.conduent.tpms.qatp.model.XferControl;
import com.conduent.tpms.qatp.parser.agency.PaFixlengthParser;
import com.conduent.tpms.qatp.service.ITransDetailService;
import com.conduent.tpms.qatp.utility.AsyncProcessCall;
import com.conduent.tpms.qatp.utility.Convertor;
import com.conduent.tpms.qatp.utility.GenericValidation;
import com.conduent.tpms.qatp.utility.MasterDataCache;
import com.google.common.base.Stopwatch;

public class FileParserImpl {

	private static final Logger logger = LoggerFactory.getLogger(FileParserImpl.class);

//	protected boolean isHederPresentInFile;
//	protected boolean isDetailsPresentInFile;
//	protected boolean isTrailerPresentInFile;
//	protected PaFileNameDetailVO fileNameVO;
//	protected PaHeaderInfoVO headerVO;
//	private boolean recordCounterFilelevelRejection = false;
//
//	@Autowired
//	protected TQatpMappingDao tQatpMappingDao;
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
//	private InsertPaDaoImpl insertPaDaoImpl;
//
//	private FileUtil fileUtil = new FileUtil();
//	private String fileType;
//	private String fileFormat;
//	private String agencyId;
//	private String fileName;
//	protected XferControl xferControl;
//	protected ReconciliationCheckPoint reconciliationCheckPoint;
//
//	protected long recordCount;
//	protected Integer recordCounter;
//	private FileParserParameters configurationMapping;
//	protected FileParserParameters fileParserParameter = new FileParserParameters();
//	protected boolean isFirstRead = true;
	
	@Value("${pafile.connect.url}")
	private String paFileURL;
	
	@Autowired 
	private RestTemplate restTemplate;
	
	protected boolean isHederPresentInFile;
	protected boolean isDetailsPresentInFile;
	protected boolean isTrailerPresentInFile;
	protected PaFileNameDetailVO fileNameVO;
	protected PaHeaderInfoVO headerVO;
	private boolean recordCounterFilelevelRejection = false;

	protected TQatpMappingDao tQatpMappingDao;

	protected IQatpDao qatpDao;
	
	protected ITransDetailDao transDetailDao;

	protected ITransDetailService transDetailService;

	protected GenericValidation genericValidation;

	//protected TextFileReader textFileReader;

	protected MasterDataCache masterDataCache;
	
	protected InsertPaDaoImpl insertPaDaoImpl;
	
	protected AsyncProcessCall asyncProcessCall;

	private FileUtil fileUtil = new FileUtil();
	private String fileType;
	private String fileFormat;
	private String agencyId;
	private String fileName;
	protected XferControl xferControl;
	protected ReconciliationCheckPoint reconciliationCheckPoint;
	private File file;
	protected long recordCount;
	protected Integer recordCounter;
	private FileParserParameters configurationMapping;
	protected FileParserParameters fileParserParameter = new FileParserParameters();
	protected boolean isFirstRead = true;
	private BufferedReader reader;
	
	
	

	public void initialize() {
	}

	public void validateAndProcessFileName(String fileName)
			throws InvalidFileNameException, InvlidFileHeaderException, EmptyLineException, InvlidFileDetailException, InvalidRecordCountException, ATRNFileNumDuplicationException {
		logger.info("Validate And process file name {}", fileName);
		parseAndValidate(fileName, configurationMapping.getFileNameSize(),
				configurationMapping.getFileNameMappingInfo());
	}

	public void validateAndProcessHeader(String line)
			throws InvalidFileNameException, InvlidFileHeaderException, EmptyLineException, InvlidFileDetailException, InvalidRecordCountException, ATRNFileNumDuplicationException {
		parseAndValidate(line, configurationMapping.getHeaderSize(), configurationMapping.getHeaderMappingInfoList());
	}

	public void parseAndValidateDetails(String line)
			throws InvalidFileNameException, InvlidFileHeaderException, EmptyLineException, InvlidFileDetailException, InvalidRecordCountException, ATRNFileNumDuplicationException {
		parseAndValidate(line, configurationMapping.getDetailSize(), configurationMapping.getDetailRecMappingInfo());
	}

	public void parseAndValidateFooter(String line)
			throws InvalidFileNameException, InvlidFileHeaderException, EmptyLineException, InvlidFileDetailException, InvalidRecordCountException, ATRNFileNumDuplicationException {
		parseAndValidate(line, configurationMapping.getTrailerSize(), configurationMapping.getTrailerMappingInfoDto());
	}

	public void loadConfigurationMapping() {
		logger.info("Start load configuration mapping...");
		setConfigurationMapping(tQatpMappingDao.getMappingConfigurationDetails(getFileParserParameter()));
	}

	public void doFieldMapping(String value, MappingInfoDto fMapping) throws InvlidFileHeaderException, ATRNFileNumDuplicationException {
	}

	public void parseAndValidate(String line, Long size, List<MappingInfoDto> mappings)
			throws InvalidFileNameException, InvlidFileHeaderException, EmptyLineException, InvlidFileDetailException, InvalidRecordCountException, ATRNFileNumDuplicationException {
		if(size!=null )
		{
			if(line==null || line.length()!=size)
			{
				if(mappings.get(0).getFieldType().equals("FILENAME"))
				{
					throw new InvalidFileNameException("Invalid File Name format "+line+"");
				}
				if(mappings.get(0).getFieldType().equals("HEADER"))
				{
					throw new InvlidFileHeaderException("Invalid File Header format "+line+"");
				}
				if(mappings.get(0).getFieldType().equals("DETAIL"))
				{
					throw new InvlidFileDetailException("Invalid Detail Record "+line+"");
				}
				if(mappings.get(0).getFieldType().equals("TRAILER"))
				{
					throw new InvlidFileDetailException("Invalid trailer Record "+line+"");
				}
			}
		}
		boolean isValid = true;
		processStartOfLine(mappings.get(0).getFieldType());
		logger.info("Processing Raw record {}", line);
		for (MappingInfoDto fieldMapping : mappings) {
			if (fieldMapping.getFieldName().equals("H_FILE_RECORD_COUNT")
					&& fieldMapping.getFileLevelRejection().equalsIgnoreCase("Y")) {
				recordCounterFilelevelRejection = true;
			}

			String value = line.substring(fieldMapping.getStartPosition().intValue(),
					fieldMapping.getEndPosition().intValue());
			try {
				isValid = genericValidation.doValidation(fieldMapping, value);
				if (fieldMapping.getFieldName().equals("H_FILE_DATE")) {
					if (customDateValidation(value)) {
						isValid = true;
					} else {
						isValid = false;
					}

				} else if (fieldMapping.getFieldName().equals("H_FILE_TIME")) {
					if (customTimeValidation(value)) {
						isValid = true;
					} else {
						isValid = false;
					}
				}
//				else if (fieldMapping.getFieldName().equals("H_FILE_RECORD_COUNT")) {
//					if (customRecordCountValidation(value)) {
//						isValid = true;
//					} else {
//						isValid = false;
//					}
//				}

			} catch (Exception ex) {
				logger.info("Exception for parse and validate {}", ex.getMessage());
				ex.printStackTrace();
			}
			doFieldMapping(value, fieldMapping);
			if (fieldMapping.getFieldName().equals("H_FILE_RECORD_COUNT")) {
				if (customRecordCountValidation(value)) {
					isValid = true;
				} else {
					isValid = false;
				}
			}
			if (!isValid && fieldMapping.getFileLevelRejection() != null
					&& fieldMapping.getFileLevelRejection().equalsIgnoreCase("Y")) {
				
				if (fieldMapping.getFieldType().equals("FILENAME")) {
					throw new InvalidFileNameException("Invalid File Name format " + value + "");
				}
				if (fieldMapping.getFieldType().equals("HEADER") && fieldMapping.getFieldName().equals("H_FILE_RECORD_COUNT")) {
					throw new InvalidRecordCountException("Invalid Record Count" + value + "");
				}
				if (fieldMapping.getFieldType().equals("HEADER")) {
					throw new InvlidFileHeaderException("Invalid File Header format " + value + "");
				}
				if (fieldMapping.getFieldType().equals("DETAIL")) {
					throw new InvlidFileDetailException("Invalid Detail Record " + value + "");
				}
				if (fieldMapping.getFieldType().equals("TRAILER")) {
					throw new InvlidFileDetailException("Invalid trailer Record " + value + "");
				}
				

			}
		}
		if (!isFirstRead) {
			processEndOfLine(mappings.get(0).getFieldType());
		}
	}

	public boolean customDateValidation(String value) throws InvlidFileHeaderException {

		if (fileNameVO.getFileDate().equals(value)) {

			return true;
		}
		logger.info("Header Date{} is not matching with Header Date", value);

		return false;

	}

	public boolean customTimeValidation(String value) throws InvlidFileHeaderException {

		if (fileNameVO.getFileTime().equals(value)) {

			return true;
		}
		logger.info("Header Time{} is not matching with Header Time", value);

		return false;

	}

	public boolean customRecordCountValidation(String value) {
		if (recordCount == Convertor.toLong(headerVO.getRecordCount())) {

			return true;
		}
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
		List<File> allfilesFromSrcFolder = getAllFilesFromSourceFolderParallel();
		
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
	public void fileParseTemplateInternal(List<File> allfilesFromSrcFolder) throws IOException {

		initialize();
//		loadConfigurationMapping();
//		List<File> allfilesFromSrcFolder = getAllFilesFromSourceFolder();
		if (allfilesFromSrcFolder != null && allfilesFromSrcFolder.size() > 0) {
			logger.info("Total {} files selected for process", allfilesFromSrcFolder.size());
			for (File file : allfilesFromSrcFolder) {
				try {
					logger.info("File name {} taken for process", file.getName());
					fileName = file.getName();
					//recordCount = textFileReader.getRecordCount(file);
					recordCount = getRecordCount(file);
					validateAndProcessFile(file);
					AckStatusCode ackStatusCode = AckStatusCode.SUCCESS;
					AckFileWrapper ackObj = prePareAckFileMetaData(ackStatusCode, file);
					processAckFile(ackObj);
					isHederPresentInFile=true;
					isDetailsPresentInFile=true;
				} catch (Exception ex) {
					ex.printStackTrace();
					//textFileReader.closeFile();
					closeFile();

					if (ex instanceof InvalidFileNameException) {
						logger.info("Invalid file name or date");
						{
							AckStatusCode ackStatusCode = AckStatusCode.INVALID_FILE;
							if (ex.getMessage().contains("Date mismatch") || ex.getMessage().contains("Time mismatch")) {
								ackStatusCode = AckStatusCode.INVALID_FILE;
							}
							AckFileWrapper ackObj = prePareAckFileMetaData(ackStatusCode, file);
							ackObj.setFileDestDir(getConfigurationMapping().getFileInfoDto().getUnProcDir());
							processAckFile(ackObj);
						}
					} else if (ex instanceof InvalidFileStatusException) {

					} else if (ex instanceof InvlidFileHeaderException) {
						AckStatusCode ackStatusCode = AckStatusCode.HEADER_FAIL;
						AckFileWrapper ackObj = prePareAckFileMetaData(ackStatusCode, file);
						processAckFile(ackObj);
					} else if (ex instanceof InvlidFileDetailException) {

						logger.info("Invalid File Detail Record.");
						AckStatusCode ackStatusCode = AckStatusCode.DETAIL_FAIL;
						AckFileWrapper ackObj = prePareAckFileMetaData(ackStatusCode, file);
						processAckFile(ackObj);
					} else if (ex instanceof InvalidRecordCountException) {
						AckStatusCode ackStatusCode = AckStatusCode.INVALID_RECORD_COUNT;
						AckFileWrapper ackObj = prePareAckFileMetaData(ackStatusCode, file);
						processAckFile(ackObj);
					} else if (ex instanceof FileAlreadyProcessedException) {
						AckStatusCode ackStatusCode = AckStatusCode.DUPLICATE_FILE_NUM;
						AckFileWrapper ackObj = prePareAckFileMetaData(ackStatusCode, file);
						processAckFile(ackObj);
					} else if (ex instanceof ATRNFileNumDuplicationException) {
						AckStatusCode ackStatusCode = AckStatusCode.DUPLICATE_FILE_NUM;
						AckFileWrapper ackObj = prePareAckFileMetaData(ackStatusCode, file);
						processAckFile(ackObj);
					}else if (ex instanceof GapInSeqNumException) {
						
						reconciliationCheckPoint.setFileStatusInd(FileStatusInd.COMPLETE);
						reconciliationCheckPoint.setRecordCount(Convertor.toInteger(recordCount));
						tQatpMappingDao.updateRecordCount(reconciliationCheckPoint);
					
						AckStatusCode ackStatusCode = AckStatusCode.GAP_IN_SEQ_NUM;
						AckFileWrapper ackObj = prePareAckFileMetaData(ackStatusCode, file);
						processAckFile(ackObj);
					}
					else {
						ex.printStackTrace();
					}

				}
				isHederPresentInFile=true;
				isDetailsPresentInFile=true;
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

	public void validateAndProcessFile(File file) throws InvalidFileStatusException, FileAlreadyProcessedException,
			InvalidFileNameException, InvlidFileHeaderException, EmptyLineException, IOException, CustomException,
			InvalidRecordCountException, InvlidFileDetailException, ATRNFileNumDuplicationException, GapInSeqNumException {
		startFileProcess(file);
		validateAndProcessFileName(file.getName());

		for (int i = 1; i <= 2; i++) {
//			textFileReader.openFile(file);
//			String prviousLine = textFileReader.readLine();
//			String currentLine = textFileReader.readLine();
			
			BufferedReader reader = getFileReader(file);
			String prviousLine = reader.readLine();
			String currentLine = reader.readLine();

			if (prviousLine == null) {
				// File is Empty. Handle
			}

			if (prviousLine != null && currentLine == null) {
				// Only header is present.
			}

			if (prviousLine != null && currentLine != null) {
				if (isHederPresentInFile) {
					validateAndProcessHeader(prviousLine);
					if (recordCounterFilelevelRejection) {
						validateRecordCount();
					}

				} else if(isDetailsPresentInFile) {
					parseValidateAndProcessDetail(prviousLine);
				}

			}

			prviousLine = handleDetailLines(currentLine);

			if (isTrailerPresentInFile) {
				parseAndValidateFooter(prviousLine);
			} 
			//else {
			//	if (prviousLine != null) {
		//			parseValidateAndProcessDetail(prviousLine);
					/*
					 * if (!isFirstRead) { saveRecords(reconciliationCheckPoint);
					 * reconciliationCheckPoint.setFileStatusInd(FileStatusInd.COMPLETE);
					 * reconciliationCheckPoint.setRecordCount(++recordCounter);
					 * tQatpMappingDao.updateRecordCount(reconciliationCheckPoint); }
					 */

			//	}
				/*
				 * if (!isFirstRead) {
				 * reconciliationCheckPoint.setFileStatusInd(FileStatusInd.COMPLETE);
				 * tQatpMappingDao.updateRecordCount(reconciliationCheckPoint); //
				 * parseValidateAndProcessDetail(prviousLine); }
				 */

			//}

			//textFileReader.closeFile();
			closeFile();
			isFirstRead = false;

		}

		//textFileReader.closeFile();
		closeFile();
		finishFileProcess();

	}

	public void parseValidateAndProcessDetail(String prviousLine)
			throws InvalidFileNameException, InvlidFileHeaderException, EmptyLineException, InvlidFileDetailException, InvalidRecordCountException, ATRNFileNumDuplicationException {
		parseAndValidateDetails(prviousLine);
		processDetailLine();
	}

	public String handleDetailLines(String currentLine)
			throws InvalidFileNameException, InvlidFileHeaderException, InvlidFileDetailException, InvalidRecordCountException, IOException, ATRNFileNumDuplicationException, GapInSeqNumException 
	{
		Integer counter = 0;
		Integer preProcessedRecordCount = 0;
		if (reconciliationCheckPoint != null) {
			preProcessedRecordCount = reconciliationCheckPoint.getRecordCount();
		}
		// Integer recordCount=0;
		recordCounter = 0;
		String prviousLine;
		String demoLine = "value";
		prviousLine = currentLine;
		//currentLine = textFileReader.readLine();
		currentLine = reader.readLine();
		if (prviousLine == null || prviousLine.isEmpty()) {
			recordCounter = 0;
		}
		do {
			try {
				if (prviousLine == null || prviousLine.isEmpty()) {
					throw new InvlidFileDetailException("Invalid Detail Record ");
					//throw new EmptyLineException("Record is empty");
				}
				if (preProcessedRecordCount == 0 || recordCounter >= preProcessedRecordCount) {
					if (counter == Constants.BATCH_RECORD_COUNT && !isFirstRead) {
						saveRecords(reconciliationCheckPoint);
						reconciliationCheckPoint.setFileStatusInd(FileStatusInd.IN_PROGRESS);
						reconciliationCheckPoint.setRecordCount(recordCounter);
						tQatpMappingDao.updateRecordCount(reconciliationCheckPoint);
						counter = 0;
					}
					parseValidateAndProcessDetail(prviousLine);
					counter++;
				}
				recordCounter++;
			} catch (EmptyLineException ex) {
				logger.info("Exception {}", ex.getMessage());
			}
			if(currentLine!=null) {
			prviousLine = currentLine;
//			currentLine = textFileReader.readLine();
			currentLine = reader.readLine();
			}else {
				demoLine = null;
			}
		} while (demoLine !=null);

		if (!isFirstRead) 
		{
			saveRecords(reconciliationCheckPoint);
			
			//check gap in seq num
			Integer lastSuccFilSeqNum = tQatpMappingDao.getLastSuccesfulProcessedFileSeqNum();
			if(lastSuccFilSeqNum == 0)
			{
				logger.info("No record found with File Type ATRN");
				reconciliationCheckPoint.setFileStatusInd(FileStatusInd.COMPLETE);
				reconciliationCheckPoint.setRecordCount(recordCounter);
				tQatpMappingDao.updateRecordCount(reconciliationCheckPoint);
			}
			else
			{
				Integer lastSuccFilSeqNum1 = lastSuccFilSeqNum + 1;
				if(lastSuccFilSeqNum1.equals(reconciliationCheckPoint.getFileSeqNum()))
				{
					logger.info("Entered in Sequential File Num Process");
					reconciliationCheckPoint.setFileStatusInd(FileStatusInd.COMPLETE);
					reconciliationCheckPoint.setRecordCount(recordCounter);
					tQatpMappingDao.updateRecordCount(reconciliationCheckPoint);
				}
				else if(lastSuccFilSeqNum > reconciliationCheckPoint.getFileSeqNum())
				{
					logger.info("Entered in less that previous File Num Process");
					reconciliationCheckPoint.setFileStatusInd(FileStatusInd.COMPLETE);
					reconciliationCheckPoint.setRecordCount(recordCounter);
					tQatpMappingDao.updateRecordCount(reconciliationCheckPoint);
				}
				else if(lastSuccFilSeqNum < reconciliationCheckPoint.getFileSeqNum())
				{
					logger.info("Gap In Sequence Number for file {}",reconciliationCheckPoint.getFileName());
					throw new GapInSeqNumException("Gap In Sequence Number");
				}
			}
		}
		return prviousLine;
	}

	public void saveRecords(ReconciliationCheckPoint reconciliationCheckPoint) {

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
		XferControl insertionData =	insertPaDaoImpl.getXferDataForStatistics(xferControl.getXferFileName()); //select column names in queries
		if (reconciliationCheckPoint == null) {
			reconciliationCheckPoint = new ReconciliationCheckPoint();
			reconciliationCheckPoint.setFileName(file.getName());
			reconciliationCheckPoint.setFileStatusInd(FileStatusInd.START);
			reconciliationCheckPoint.setRecordCount(0);
			reconciliationCheckPoint.setProcessDate(new Date());
			tQatpMappingDao.insertReconciliationCheckPoint(reconciliationCheckPoint);
			// Getting Data from TPMS.T_XFER_CONTROL table
			logger.info("Record is inserted in checkpoint table for file {}", file.getName());
			// Inserting Data into TPMS.T_QATP_STATISTICS table from TPMS.T_XFER_CONTROL table
			if(insertionData!=null)
			{
				insertPaDaoImpl.insertPaFileInQATPStats(insertionData);
			}
		}
		else if (reconciliationCheckPoint.getFileStatusInd()==FileStatusInd.START)
		{
			logger.info("Entering in else if where file status is START");
			if(insertionData !=null) 
			{
				insertPaDaoImpl.updateStatisticsTable(insertionData.getXferControlId(), fileName);
			}
		}
		else if (reconciliationCheckPoint.getFileStatusInd() == FileStatusInd.COMPLETE) 
		{
			//Uat Bug 69592
			//long prevXferControlId = tQatpMappingDao.getPrevXferControlId(file.getName());
			logger.info("File {} is already processed with xferControlId {} and Process date {}",file.getName(), reconciliationCheckPoint.getXferControlId(),reconciliationCheckPoint.getProcessDate());
			throw new FileAlreadyProcessedException("File already processed");

		}

	}

	public void finishFileProcess() throws CustomException {

	}

	public List<File> getAllFilesFromSourceFolder() throws IOException {
		List<File> allfilesFromSrcDirectory = fileUtil
				.getAllfilesFromSrcDirectory(getConfigurationMapping().getFileInfoDto().getSrcDir(), fileType);
		return allfilesFromSrcDirectory;
	}
	
	public List<File> getAllFilesFromSourceFolderParallel() throws IOException 
	{
		List<File> allfilesFromSrcDirectory = fileUtil.getAllfilesFromSrcDirectoryParallel(getConfigurationMapping().getFileInfoDto().getSrcDir(),fileType);
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
	
	
	public long getRecordCount(File file)
	{
		try (Stream<String> lines = Files.lines(file.toPath()))
		{
			long numOfLines = lines.count()-1;
			return numOfLines;
		} catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0L;
	}
	
	public String readLine() {
		try {
			return reader.readLine();
		} catch (IOException e) {
			return null;
		}
	}

	public void openFile(File file) throws FileNotFoundException{
		reader = new BufferedReader(new FileReader(file));
	}

	public void closeFile() throws IOException {
		if(reader != null ) {//&& file != null) {
			reader.close();
			file = null;
		}
	}
	
	private BufferedReader getFileReader(File file) throws IOException
	{
		//BufferedReader reader = new BufferedReader(new FileReader(file));
		
		reader = new BufferedReader(new FileReader(file));
		reader.mark(1);
		
		return reader;
	}
	
	public List<String> process() throws IOException
	{
		loadConfigurationMapping();
		List<File> allfilesFromSrcFolder = fileUtil.getAllfilesFromSrcDirectory(getConfigurationMapping().getFileInfoDto().getSrcDir(),fileType);
		List<String> result = new ArrayList<String>();
		
		for(File file : allfilesFromSrcFolder)
		{
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<String> entity = new HttpEntity<String>(headers);
			
			String url = null;
			url = paFileURL.concat("?fileName=" + file.getName());
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
	
}