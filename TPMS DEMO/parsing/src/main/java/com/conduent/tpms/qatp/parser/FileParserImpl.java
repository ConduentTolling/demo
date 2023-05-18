package com.conduent.tpms.qatp.parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
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
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.web.client.RestTemplate;

import com.conduent.tpms.qatp.config.LoadJpaQueries;
import com.conduent.tpms.qatp.constants.AckStatusCode;
import com.conduent.tpms.qatp.constants.Constants;
import com.conduent.tpms.qatp.constants.FOConstants;
import com.conduent.tpms.qatp.constants.FileStatusInd;
import com.conduent.tpms.qatp.constants.INTXConstants;
import com.conduent.tpms.qatp.constants.QATPConstants;
import com.conduent.tpms.qatp.dao.IQatpDao;
import com.conduent.tpms.qatp.dao.TQatpMappingDao;
import com.conduent.tpms.qatp.dao.impl.InsertFOTrnxStatDaoImpl;
import com.conduent.tpms.qatp.dto.AckFileInfoDto;
import com.conduent.tpms.qatp.dto.AckFileWrapper;
import com.conduent.tpms.qatp.dto.FileNameDetailVO;
import com.conduent.tpms.qatp.dto.FileParserParameters;
import com.conduent.tpms.qatp.dto.MappingInfoDto;
import com.conduent.tpms.qatp.exception.FNTXFileNumDuplicationException;
import com.conduent.tpms.qatp.exception.CustomException;
import com.conduent.tpms.qatp.exception.EmptyLineException;
import com.conduent.tpms.qatp.exception.FileAlreadyProcessedException;
import com.conduent.tpms.qatp.exception.GapInSeqNumException;
import com.conduent.tpms.qatp.exception.InvalidDetailException;
import com.conduent.tpms.qatp.exception.InvalidFileHeaderException;
import com.conduent.tpms.qatp.exception.InvalidFileNameException;
import com.conduent.tpms.qatp.exception.InvalidFileStatusException;
import com.conduent.tpms.qatp.exception.InvalidFileStructureException;
import com.conduent.tpms.qatp.exception.InvalidRecordCountException;
import com.conduent.tpms.qatp.exception.InvalidRecordException;
import com.conduent.tpms.qatp.exception.InvalidTrailerException;
import com.conduent.tpms.qatp.model.ReconciliationCheckPoint;
import com.conduent.tpms.qatp.model.TFOFileStats;
import com.conduent.tpms.qatp.model.TFOReconFileStats;
import com.conduent.tpms.qatp.model.XferControl;
import com.conduent.tpms.qatp.service.ITransDetailService;
import com.conduent.tpms.qatp.utility.AsyncProcessCall;
import com.conduent.tpms.qatp.utility.Convertor;
import com.conduent.tpms.qatp.utility.GenericValidation;
import com.conduent.tpms.qatp.utility.MasterDataCache;
import com.conduent.tpms.qatp.validation.FileUtil;
import com.conduent.tpms.qatp.validation.TextFileReader;
import com.google.common.base.Stopwatch;

public class FileParserImpl implements Runnable
{

	private static final Logger logger = LoggerFactory.getLogger(FileParserImpl.class);

//	protected boolean isHederPresentInFile;
//	protected boolean isDetailsPresentInFile;
//	protected boolean isTrailerPresentInFile;
//	protected FileNameDetailVO fileNameVO;
//	protected int count = 0;
//	protected Transaction transInfo;
//
//	@Autowired
//	protected TQatpMappingDao tQatpMappingDao;
//
//	@Autowired
//	private IQatpDao qatpDao;
//
//	@Autowired
//	protected ITransDetailService transDetailService;
//
//	@Autowired
//	protected
//	GenericValidation genericValidation;
//
//	@Autowired
//	private TextFileReader textFileReader;
//
//	@Autowired
//	protected MasterDataCache masterDataCache;
//	
//	@Autowired
//	InsertNystaStatDaoImpl insertNystaStatDaoImpl;
//	
//	@Autowired
//	AsyncProcessCall asyncProcessCall;
//	
//	private FileUtil fileUtil=new FileUtil();
//	private String fileType;
//	private String fileFormat;
//	private String agencyId;
//	private String fileName;
//	protected XferControl xferControl;
//	private ReconciliationCheckPoint reconciliationCheckPoint;
//	protected long recordCount;
//	protected boolean isFirstRead=true;
//	private Map<String,Integer> lineSize;
//
//	private FileParserParameters configurationMapping;
//	protected FileParserParameters fileParserParameter=new FileParserParameters();
	
	@Value("${fotrnxfile.connect.url}")
	private String foTrnxFileURL;
	
	@Autowired 
	private RestTemplate restTemplate;
	
	protected boolean isHederPresentInFile;
	protected boolean isDetailsPresentInFile;
	protected boolean isTrailerPresentInFile;
	protected FileNameDetailVO fileNameVO;
	protected int count = 0;

	protected TQatpMappingDao tQatpMappingDao;

	protected IQatpDao qatpDao;

	protected ITransDetailService transDetailService;

	protected GenericValidation genericValidation;

	protected TextFileReader textFileReader;

	protected MasterDataCache masterDataCache;
	
	protected InsertFOTrnxStatDaoImpl insertNystaStatDaoImpl;
	
	protected AsyncProcessCall asyncProcessCall;

	private FileUtil fileUtil=new FileUtil();
	private String fileType;
	private String fileFormat;
	private String agencyId;
	protected String fileName;
	protected XferControl xferControl;
	private ReconciliationCheckPoint reconciliationCheckPoint;
	protected long recordCount;
	protected boolean isFirstRead=true;
	private Map<String,Integer> lineSize;
	private File file;
	private FileParserParameters configurationMapping;
	protected FileParserParameters fileParserParameter=new FileParserParameters();
	private long atpFileId;
	
	private BufferedReader reader;


	public void initialize() {}

	public void validateAndProcessFileName(String fileName) throws InvalidFileNameException, InvalidFileHeaderException, EmptyLineException, InvalidDetailException, InvalidTrailerException, InvalidFileStructureException, InvalidRecordException, FNTXFileNumDuplicationException
	{
		logger.info("Validate And process file name {}", fileName);
		parseAndValidate(fileName,configurationMapping.getFileNameSize(),configurationMapping.getFileNameMappingInfo());
	}

	public void validateAndProcessHeader(String line) throws InvalidFileNameException, InvalidFileHeaderException, EmptyLineException, InvalidDetailException, InvalidTrailerException, InvalidFileStructureException, InvalidRecordException, FNTXFileNumDuplicationException {
		parseAndValidate(line,configurationMapping.getHeaderSize(),configurationMapping.getHeaderMappingInfoList());
	}

	public void parseAndValidateDetails(String line) throws InvalidFileNameException, InvalidFileHeaderException, EmptyLineException, InvalidDetailException, InvalidTrailerException, InvalidFileStructureException, InvalidRecordException, FNTXFileNumDuplicationException  {
		parseAndValidate(line,configurationMapping.getDetailSize(),configurationMapping.getDetailRecMappingInfo());
	}

	public void parseAndValidateFooter(String line) throws InvalidFileNameException, InvalidFileHeaderException,EmptyLineException, InvalidDetailException, InvalidTrailerException, InvalidFileStructureException, InvalidRecordException, FNTXFileNumDuplicationException{
		parseAndValidate(line,configurationMapping.getTrailerSize(),configurationMapping.getTrailerMappingInfoDto());
	}

	public void loadConfigurationMapping(String fileType) 
	{
		logger.info("Start load configuration mapping for fileType {}",fileType);
		setConfigurationMapping(tQatpMappingDao.getMappingConfigurationDetails(getFileParserParameter(),fileType));
		logger.info("Mapping Done for fileType {}",fileType);
	}



	public void doFieldMapping(String value, MappingInfoDto fMapping, long recordCount2) throws InvalidRecordException, InvalidFileHeaderException, FNTXFileNumDuplicationException, InvalidFileNameException {}

	public void parseAndValidate(String line,Long size,List<MappingInfoDto> mappings) throws InvalidFileNameException, InvalidFileHeaderException, EmptyLineException, InvalidDetailException, InvalidTrailerException, InvalidFileStructureException, InvalidRecordException, FNTXFileNumDuplicationException 
	{
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
					throw new InvalidFileHeaderException("Invalid File Header format "+line+"");
				}
				if(mappings.get(0).getFieldType().equals("DETAIL"))
				{
					throw new InvalidDetailException("Invalid Detail Record "+line+"");
				}
				if(mappings.get(0).getFieldType().equals("TRAILER"))
				{
					throw new InvalidTrailerException("Invalid trailer Record "+line+"");
				}
			}
		}
		boolean isValid = false;
		processStartOfLine(mappings.get(0).getFieldType());
		logger.info("Processing Raw record {}",line);
		for (MappingInfoDto fieldMapping : mappings)
		{
			isValid = false;
			String value = line.substring(fieldMapping.getStartPosition().intValue(),fieldMapping.getEndPosition().intValue());
			try
			{
				isValid = genericValidation.doValidation(fieldMapping, value);
			}
			catch(Exception ex)
			{
				logger.info("Exception {}",ex.getMessage());
			}
			doFieldMapping(value,fieldMapping,recordCount);
			logger.info("Validation for field {} :value: {} valid: {}", fieldMapping.getFieldName(), value.trim(),isValid);
//			
//			if (fieldMapping.getFieldName().equals(FOConstants.D_ETC_ENTRY_DATE)
//					|| fieldMapping.getFieldName().equals(FOConstants.D_ETC_ENTRY_TIME)) {
//				doFieldMapping(value.trim(), fieldMapping);
//			} else {
//				isValid = genericValidation.doValidation(fieldMapping, value);
//				doFieldMapping(value.trim(), fieldMapping);
//				logger.info("Validation for field {} :value: {} valid: {}", fieldMapping.getFieldName(), value.trim(),
//						isValid);

				if (!isValid && fieldMapping.getFileLevelRejection()!=null && fieldMapping.getFileLevelRejection().equalsIgnoreCase("Y")) {
					if (fieldMapping.getFieldType().equals(FOConstants.FILENAME)) {
						throw new InvalidFileNameException("Invalid File Name format");
					}
					if (fieldMapping.getFieldType().equals(FOConstants.HEADER)) {
						throw new InvalidFileHeaderException("Invalid File Header format");
					}
					if (fieldMapping.getFieldType().equals(FOConstants.DETAIL)) {
						logger.info("Invalid record data in record {}", value);
						throw new InvalidRecordException("Invalid record data.");
					}
				}
			}
		if(!isFirstRead)
		{
			processEndOfLine(mappings.get(0).getFieldType());
		}
	}

//	public boolean customDateValidation(String value) throws InvalidFileHeaderException 
//	{
//
//		if (fileNameVO.getFileDate().equals(value)) 
//		{
//
//			return true;
//		}
//		logger.info("Header Date{} is not matching with Header Date", value);
//
//		return false;
//
//	}

//	public boolean customTimeValidation(String value) throws InvalidFileHeaderException {
//
//		if (fileNameVO.getFileTime().equals(value)) {
//
//			return true;
//		}
//		logger.info("Header Time{} is not matching with Header Time", value);
//
//		return false;
//
//	}

public boolean customDateValidation(String value) throws InvalidFileHeaderException 
{

	if (fileNameVO.getFileDateTime().equals(value)) 
	{

		return true;
	}
	logger.info("Header Date{} is not matching with Header Date", value);

	return false;

}

	public void handleException(String fileSection) throws CustomException
	{

	}

	public void processEndOfLine(String fileSection) 
	{

	}

	public void processStartOfLine(String fileSection) 
	{

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

	/**
	 * 
	 * @throws IOException 
	 * @throws Exception 
	 */
	public void fileParseTemplatewithFileName(String fileName) throws IOException
	{		
//		Stopwatch stopwatch = Stopwatch.createStarted();
//		logger.info("Start time {}",new Date().toString());
		initialize();
		//loadConfigurationMapping();
		List<File> allfilesFromSrcFolder = getAllFilesFromSourceFolderParallel();
		
		File file = checkFileExits(allfilesFromSrcFolder,fileName);
		logger.debug("File to be added in list of files {}",file);
		if(file!=null)
		{
			List<File> listOfFiles = new ArrayList<File>();
			listOfFiles.add(file);
			logger.debug("List of file {}",listOfFiles);
			//fileParseTemplateInternal(listOfFiles); 
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

	private void fileParseTemplateInternal(List<File> allfilesFromSrcFolder,String fileType) throws IOException 
	{
		LocalDateTime from;
		if(allfilesFromSrcFolder!=null && allfilesFromSrcFolder.size()>0)
		{
			logger.info("Total {} files selected for process",allfilesFromSrcFolder.size());
			for (File file : allfilesFromSrcFolder)
			{
				from = LocalDateTime.now();
				try 
				{
					logger.info("File name {} taken for process", file.getName());
					fileName=file.getName();
					//recordCount=textFileReader.getRecordCount1(file);
					recordCount = getRecordCount1(file);
					validateAndProcessFile(file,fileType);
					AckStatusCode ackStatusCode=AckStatusCode.SUCCESS;
					AckFileWrapper ackObj=prePareAckFileMetaData(ackStatusCode,file,fileType);
					processAckFile(ackObj);
					insertNystaStatDaoImpl.insertInFileStat(fileType,recordCount,xferControl);
					finishFileProcess(fileType);
				} 
				catch(Exception ex)
				{
					logger.info("Exception : {}",ex.getMessage());
					//textFileReader.closeFile();
					closeFile();
					if(ex instanceof InvalidFileStatusException)
					{

					}
					else if(ex instanceof InvalidRecordCountException || ex instanceof InvalidTrailerException)
					{
						AckStatusCode ackStatusCode=AckStatusCode.INVALID_RECORD_COUNT;
						AckFileWrapper ackObj=prePareAckFileMetaData(ackStatusCode,file,fileType);
						processAckFile(ackObj);
					}
					else if(ex instanceof FileAlreadyProcessedException)
					{
						AckStatusCode ackStatusCode=AckStatusCode.DUPLICATE_FILE_NUM;
						AckFileWrapper ackObj=prePareAckFileMetaData(ackStatusCode,file,fileType);
						processAckFile(ackObj);
					}
					else if(ex instanceof InvalidFileHeaderException)
					{
						AckStatusCode ackStatusCode=AckStatusCode.INVALID_HEADR_FORMAT;
						AckFileWrapper ackObj=prePareAckFileMetaData(ackStatusCode,file,fileType);
						processAckFile(ackObj);
					}
					else if(ex instanceof InvalidFileNameException || ex instanceof InvalidFileStructureException)
					{
						AckStatusCode ackStatusCode=AckStatusCode.INVALID_FILE_STRUCTURE;
						AckFileWrapper ackObj=prePareAckFileMetaData(ackStatusCode,file,fileType);
						processAckFile(ackObj);
					}
					else if (ex instanceof FNTXFileNumDuplicationException) {
						AckStatusCode ackStatusCode = AckStatusCode.DUPLICATE_FILE_NUM;
						AckFileWrapper ackObj = prePareAckFileMetaData(ackStatusCode, file,fileType);
						processAckFile(ackObj);
					}
					else if (ex instanceof GapInSeqNumException) {
						
						reconciliationCheckPoint.setFileStatusInd(FileStatusInd.COMPLETE);
						reconciliationCheckPoint.setRecordCount(Convertor.toInteger(recordCount));
						reconciliationCheckPoint.setFileType(fileType);
						tQatpMappingDao.updateRecordCount(reconciliationCheckPoint);
						
						AckStatusCode ackStatusCode = AckStatusCode.GAP_IN_SEQ_NUM;
						AckFileWrapper ackObj = prePareAckFileMetaData(ackStatusCode, file,fileType);
						processAckFile(ackObj);
					}
					else
					{
						AckStatusCode ackStatusCode=AckStatusCode.INVALID_DETAIL_RECORD;
						AckFileWrapper ackObj=prePareAckFileMetaData(ackStatusCode,file,fileType);
						processAckFile(ackObj);
					}
					

				}
				logger.debug("Time take for file {} to complete the process is {}",file.getName(),ChronoUnit.MILLIS.between(from, LocalDateTime.now()));
				isHederPresentInFile=true;
				isDetailsPresentInFile=true;
			}
		}
		
		
	}
	
	private void insertInFileStat(String fileType) 
	{}

	/**
	 * 
	 * @throws IOException 
	 * @throws Exception 
	 */
	public void fileParseTemplate(String fileType) throws IOException
	{
		Stopwatch stopwatch = Stopwatch.createStarted();
		initialize();
		loadConfigurationMapping(fileType);
		List<File> allfilesFromSrcFolder = getAllFilesFromSourceFolder(fileType);
		logger.info("List of files from src dirc {}",allfilesFromSrcFolder);
		
		fileParseTemplateInternal(allfilesFromSrcFolder,fileType);

		logger.info("No file found in directory {}",getConfigurationMapping().getFileInfoDto().getSrcDir());
		
		stopwatch.stop();
		long millis = stopwatch.elapsed(TimeUnit.MILLISECONDS);
		logger.info("Total time taken to process the files {}",millis);
	}

	public AckFileWrapper prePareAckFileMetaData(AckStatusCode ackStatusCode,File file,String fileType)
	{
		return null;
	}
	public void processAckFile(AckFileWrapper ackObj) 
	{
		try 
		{
			logger.info("Creating ack file {}",ackObj.getAckFileName());
			createAckFile(ackObj.getAckFileName(), ackObj.getSbFileContent(),ackObj.getAckFileInfoDto());
			transferFile(ackObj.getFile(), ackObj.getFileDestDir());
			ackObj.getLockFile().delete();

		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	public void validateAndProcessFile(File file,String fileType) throws InvalidFileStatusException, FileAlreadyProcessedException, InvalidFileNameException, InvalidFileHeaderException, EmptyLineException, IOException, CustomException, InvalidRecordCountException, InvalidDetailException, InvalidTrailerException, InvalidFileStructureException, InterruptedException, InvalidRecordException, FNTXFileNumDuplicationException, GapInSeqNumException
	{

		startFileProcess(file,fileType);
		validateAndProcessFileName(file.getName());
		isFirstRead=true;
		for(int i=1;i<=2;i++)
		{
//			if(i==1) 
//			{
//				isFirstRead=false;
//				continue;
//			}
			
			//textFileReader.openFile(file);
//			String prviousLine = textFileReader.readLine();
//			String currentLine = textFileReader.readLine();
			BufferedReader reader = getFileReader(file);
			String prviousLine = reader.readLine();
			String currentLine = reader.readLine();

			if(prviousLine == null) {
				//File is Empty. Handle 
			}

			if(prviousLine != null && currentLine == null) {
				//Only header is present.
			}

			if(prviousLine != null && currentLine != null)
			{
				if(isHederPresentInFile)
				{
					validateAndProcessHeader(prviousLine);
					validateRecordCount();
				}
				else 
				{
					parseValidateAndProcessDetail(prviousLine);
				}

			}
			prviousLine = handleDetailLines(currentLine,fileType);

			if(isTrailerPresentInFile)
			{
				parseAndValidateFooter(prviousLine);
			}
			else 
			{
				if(prviousLine!=null)
				{
					parseValidateAndProcessDetail(prviousLine);
				}
			}
			//textFileReader.reset();
			//textFileReader.closeFile();
			closeFile();
			isFirstRead=false;
		}
		//textFileReader.closeFile();
		closeFile();
	}

	public void parseValidateAndProcessDetail(String prviousLine) throws InvalidFileNameException, InvalidFileHeaderException, EmptyLineException, InvalidDetailException, InvalidTrailerException, InvalidFileStructureException, InvalidRecordException, FNTXFileNumDuplicationException {
		parseAndValidateDetails(prviousLine);
		processDetailLine();
	}

	public String handleDetailLines(String currentLine,String fileType) throws InvalidFileNameException, InvalidFileHeaderException, InvalidDetailException, InvalidTrailerException, InvalidFileStructureException, InterruptedException, IOException, InvalidRecordException, FNTXFileNumDuplicationException, GapInSeqNumException 
	{
		LocalDateTime from = null;
		Integer counter = 0;
		Integer preProcessedRecordCount=0;
		String demoLine = "value";
		
		if(reconciliationCheckPoint!=null)
		{
			preProcessedRecordCount=reconciliationCheckPoint.getRecordCount();
		}
		Integer recordCount=0;
		String prviousLine;
		prviousLine = currentLine;
		//currentLine = textFileReader.readLine();
		currentLine = reader.readLine();
		if(prviousLine==null || prviousLine.isEmpty())
		{
			recordCount=0;
		}
		do 
		{
			try
			{
				if(prviousLine==null || prviousLine.isEmpty())
				{
					throw new EmptyLineException("Record is empty");
				}
				if((preProcessedRecordCount==0 || recordCount>preProcessedRecordCount )  )
				{
					if (counter == Constants.BATCH_RECORD_COUNT && !isFirstRead) 
					{
						from = LocalDateTime.now();
						saveRecords(reconciliationCheckPoint,atpFileId,fileType);
						logger.debug("Total time taken for saveRecord - Completed in {} ms", ChronoUnit.MILLIS.between(from, LocalDateTime.now()));
						reconciliationCheckPoint.setFileStatusInd(FileStatusInd.IN_PROGRESS);
						reconciliationCheckPoint.setRecordCount(recordCount);
						reconciliationCheckPoint.setFileType(fileType);
						asyncProcessCall.updateCheckpointTable(reconciliationCheckPoint);
						counter = 0;
					}
					parseValidateAndProcessDetail(prviousLine);
					counter++;
					
				}
				recordCount++;
			}
			catch(EmptyLineException ex)
			{
				logger.info("Exception {}",ex.getMessage());
			}
			if(currentLine!=null) {
				prviousLine = currentLine;
//				currentLine = textFileReader.readLine();
				currentLine = reader.readLine();
				}else {
					demoLine = null;
				}
			} while (demoLine !=null);

		if(!isFirstRead)
		{
			saveRecords(reconciliationCheckPoint,atpFileId,fileType);
			
			//check gap in seq num
			Integer lastSuccFilSeqNum = tQatpMappingDao.getLastSuccesfulProcessedFileSeqNum(fileType);
			if(lastSuccFilSeqNum == 0)
			{
				reconciliationCheckPoint.setFileStatusInd(FileStatusInd.COMPLETE);
				reconciliationCheckPoint.setRecordCount(recordCount);
				reconciliationCheckPoint.setFileType(fileType);
				tQatpMappingDao.updateRecordCount(reconciliationCheckPoint);
			}
			else
			{
				Integer lastSuccFilSeqNum1 = lastSuccFilSeqNum + 1;
				if(lastSuccFilSeqNum1.equals(reconciliationCheckPoint.getFileSeqNum()))
				{
					logger.info("Entered in Sequential File Num Process");
					reconciliationCheckPoint.setFileStatusInd(FileStatusInd.COMPLETE);
					reconciliationCheckPoint.setRecordCount(recordCount);
					reconciliationCheckPoint.setFileType(fileType);
					tQatpMappingDao.updateRecordCount(reconciliationCheckPoint);
				}
				else if(lastSuccFilSeqNum > reconciliationCheckPoint.getFileSeqNum())
				{
					logger.info("Entered in less that previous File Num Process");
					reconciliationCheckPoint.setFileStatusInd(FileStatusInd.COMPLETE);
					reconciliationCheckPoint.setRecordCount(recordCount);
					reconciliationCheckPoint.setFileType(fileType);
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
	

	public void saveRecords(ReconciliationCheckPoint reconciliationCheckPoint,long atpFileId,String fileType) throws InterruptedException
	{

	}

	public void processDetailLine() 
	{

	}
	public void validateRecordCount() throws InvalidRecordCountException
	{

	}

	public void startFileProcess(File file,String fileType) throws InvalidFileStatusException, FileAlreadyProcessedException 
	{
		xferControl=tQatpMappingDao.checkFileDownloaded(file.getName());
		if(xferControl==null)
		{
			logger.info("File {} is not downloded", file.getName());
			throw new InvalidFileStatusException("File not downloded");
		}

		reconciliationCheckPoint = tQatpMappingDao.getReconsilationCheckPoint(file.getName());
		// Getting Data from TPMS.T_XFER_CONTROL table
		XferControl insertionData=	insertNystaStatDaoImpl.getXferDataForStatistics(xferControl.getXferFileName());
		if (reconciliationCheckPoint==null)
		{
			reconciliationCheckPoint = new ReconciliationCheckPoint();
			reconciliationCheckPoint.setFileName(file.getName());
			reconciliationCheckPoint.setFileStatusInd(FileStatusInd.START);
			reconciliationCheckPoint.setRecordCount(0);
			reconciliationCheckPoint.setProcessDate(new Date());
			tQatpMappingDao.insertReconciliationCheckPoint(reconciliationCheckPoint);
			
			// Inserting Data into TPMS.T_QATP_STATISTICS table from TPMS.T_XFER_CONTROL table
			if(insertionData !=null && fileType.equals(FOConstants.FNTX)) 
			{
				insertNystaStatDaoImpl.insertFNTXData(insertionData,fileType,recordCount);
			}
			else if(insertionData !=null && fileType.equals(INTXConstants.INTX)) 
			{
				insertNystaStatDaoImpl.insertINTXData(insertionData,fileType,recordCount);
			}
			
			logger.info("Record is inserted in checkpoint table for file {}", file.getName());
		}
		else if (reconciliationCheckPoint.getFileStatusInd()==FileStatusInd.START)
		{
			logger.info("Entering in else if where file status is START");
			if(insertionData !=null && fileType.equals(FOConstants.FNTX)) 
			{
				insertNystaStatDaoImpl.updateStatisticsTable(insertionData.getXferControlId(), fileName);
				insertNystaStatDaoImpl.updateQatpStatisticsTable(insertionData.getXferControlId(), fileName);
			}
			else if (insertionData !=null && fileType.equals(INTXConstants.INTX))
			{
				insertNystaStatDaoImpl.updateStatisticsTable(insertionData.getXferControlId(), fileName);
				insertNystaStatDaoImpl.updateQatpStatisticsTable(insertionData.getXferControlId(), fileName);
			}
		}
		else if(reconciliationCheckPoint.getFileStatusInd()==FileStatusInd.COMPLETE)
		{
			//Uat Bug 69592
			//long prevXferControlId = tQatpMappingDao.getPrevXferControlId(file.getName());
			logger.info("File {} is already processed with xferControlId {} and Process date {}",file.getName(), reconciliationCheckPoint.getXferControlId(),reconciliationCheckPoint.getProcessDate());
			throw new FileAlreadyProcessedException("File All ready processed");
		}

	}

	public void finishFileProcess(String fileType1) throws CustomException, InterruptedException, InvalidFileHeaderException
	{
		//update T_FO_FILE_STATS for remaining fields
		if(fileType1.equals(FOConstants.FNTX))
		{
			insertNystaStatDaoImpl.updateFOFileStats(xferControl.getXferControlId(), fileName,reconciliationCheckPoint,genericValidation);
		}
		else if(fileType1.equals(INTXConstants.INTX))
		{
			insertNystaStatDaoImpl.updateFOReconFileStats(xferControl.getXferControlId(), fileName,reconciliationCheckPoint);
		}
		
		System.out.println("Finish File Process from FileParserImpl");
	}

	public List<File> getAllFilesFromSourceFolder(String fileType) throws IOException 
	{
		if(fileType.equals(FOConstants.FNTX))
		{
			List<File> allfilesFromSrcDirectory = fileUtil.getAllfilesFromSrcDirectory(getConfigurationMapping().getFileInfoDto().getSrcDir(),fileType);
			return allfilesFromSrcDirectory;
		}
		else
		{
			List<File> allfilesFromSrcDirectory = fileUtil.getAllfilesFromSrcDirectory(getConfigurationMapping().getFileInfoDto().getSrcDir(),fileType);
			return allfilesFromSrcDirectory;
		}
	}
	
	public List<File> getAllFilesFromSourceFolderParallel() throws IOException 
	{
		List<File> allfilesFromSrcDirectory = fileUtil.getAllfilesFromSrcDirectoryParallel(getConfigurationMapping().getFileInfoDto().getSrcDir(),fileFormat);
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



	public void transferFile(File srcFile, String destDirPath) throws IOException 
	{
		try
		{

			Path destDir = new File(destDirPath, srcFile.getName()).toPath();

			File temp=destDir.toFile();
			if(temp.exists())
			{
				temp.delete();
			}

			Path result = Files.move(Paths.get(srcFile.getAbsolutePath()), destDir);



			if (null != result) 
			{
				logger.info("File moved from {} to {} successfully",srcFile.getName(),destDirPath);
			}
			else 
			{
				logger.info("File transfer failed.");
			}
		}
		catch (IOException iex) 
		{
			throw iex;
		}
	}

	public void createAckFile(String ackFileName,String ackFileContent,AckFileInfoDto ackFileInfoDto) throws IOException, CustomException 
	{

		File destFile = new File(getConfigurationMapping().getFileInfoDto().getAckDir(), ackFileName);
		if(destFile.exists())
		{
			destFile.delete();
		}
		File ackFile = new File(getConfigurationMapping().getFileInfoDto().getAckDir(), ackFileName);
		try (FileOutputStream fos = new FileOutputStream(ackFile)) 
		{
			fos.write(ackFileContent.getBytes());
			logger.info("File {} created successfully",ackFile);
			qatpDao.insertAckStatus(ackFileInfoDto);
			logger.info("Inserting record in ack table {}",ackFileInfoDto.toString());
		} 
		catch (FileNotFoundException e) 
		{
			throw e;
		}
		catch (IOException e) 
		{
			throw e;
		}
		logger.info("File {} created successfully",ackFileName);

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
	
	public List<String> process() throws IOException
	{
		//loadConfigurationMapping();
		List<File> allfilesFromSrcFolder = fileUtil.getFilesFromSrcDir(getConfigurationMapping().getFileInfoDto().getSrcDir(),fileFormat);
		List<String> result = new ArrayList<String>();
		
		for(File file : allfilesFromSrcFolder)
		{
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<String> entity = new HttpEntity<String>(headers);
			
			String url = null;
			url = foTrnxFileURL.concat("?fileName=" + file.getName());
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
		try (Stream<String> lines = Files.lines(file.toPath()))
		{
			long numOfLines = lines.count()-1;
			return numOfLines;
		} catch (IOException e) 
		{
			e.printStackTrace();
		}
		return 0L;
	}
	
	public void closeFile() throws IOException {
		if(reader != null ) 
		{
			reader.close();
		}
	}
}