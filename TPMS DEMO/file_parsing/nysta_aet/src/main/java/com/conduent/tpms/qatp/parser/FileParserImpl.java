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
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
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
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.conduent.tpms.qatp.constants.AckStatusCode;
import com.conduent.tpms.qatp.constants.Constants;
import com.conduent.tpms.qatp.constants.FileStatusInd;
import com.conduent.tpms.qatp.dao.IQatpDao;
import com.conduent.tpms.qatp.dao.TQatpMappingDao;
import com.conduent.tpms.qatp.dao.impl.InsertNystaStatDaoImpl;
import com.conduent.tpms.qatp.dto.AckFileInfoDto;
import com.conduent.tpms.qatp.dto.AckFileWrapper;
import com.conduent.tpms.qatp.dto.FileNameDetailVO;
import com.conduent.tpms.qatp.dto.FileParserParameters;
import com.conduent.tpms.qatp.dto.MappingInfoDto;
import com.conduent.tpms.qatp.dto.NystaAetTxDto;
import com.conduent.tpms.qatp.exception.CustomException;
import com.conduent.tpms.qatp.exception.EmptyLineException;
import com.conduent.tpms.qatp.exception.FileAlreadyProcessedException;
import com.conduent.tpms.qatp.exception.InvalidDetailException;
import com.conduent.tpms.qatp.exception.InvalidFileHeaderException;
import com.conduent.tpms.qatp.exception.InvalidFileNameException;
import com.conduent.tpms.qatp.exception.InvalidFileStatusException;
import com.conduent.tpms.qatp.exception.InvalidFileStructureException;
import com.conduent.tpms.qatp.exception.InvalidRecordCountException;
import com.conduent.tpms.qatp.exception.InvalidTrailerException;
import com.conduent.tpms.qatp.model.ReconciliationCheckPoint;
import com.conduent.tpms.qatp.model.Transaction;
import com.conduent.tpms.qatp.model.XferControl;
import com.conduent.tpms.qatp.parser.agency.NystaFixlengthParser;
import com.conduent.tpms.qatp.service.ITransDetailService;
import com.conduent.tpms.qatp.utility.AsyncProcessCall;
import com.conduent.tpms.qatp.utility.DateUtils;
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
	
	@Value("${nystafile.connect.url}")
	private String nystaFileURL;
	
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
	
	protected InsertNystaStatDaoImpl insertNystaStatDaoImpl;
	
	protected AsyncProcessCall asyncProcessCall;

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
	private FileParserParameters configurationMapping;
	protected FileParserParameters fileParserParameter=new FileParserParameters();
	
	private BufferedReader reader;
	public boolean partiallyExecuted = false;
	protected Integer agencyIdNystaOrNysba;

	public void initialize() {}

	public void validateAndProcessFileName(String fileName) throws InvalidFileNameException, InvalidFileHeaderException, EmptyLineException, InvalidDetailException, InvalidTrailerException, InvalidFileStructureException
	{
		logger.info("Validate And process file name {}", fileName);
		Stopwatch stopwatch = Stopwatch.createStarted();
		
		parseAndValidate(fileName,configurationMapping.getFileNameSize(), configurationMapping.getFileNameMappingInfo());
		
		stopwatch.stop();
		long millis = stopwatch.elapsed(TimeUnit.MILLISECONDS);
		logger.info("Time taken for validate And Process File Name  {}", millis);
		
	}

	public void validateAndProcessHeader(String line) throws InvalidFileNameException, InvalidFileHeaderException, EmptyLineException, InvalidDetailException, InvalidTrailerException, InvalidFileStructureException {
		Stopwatch stopwatch = Stopwatch.createStarted();
		
		parseAndValidate(line,configurationMapping.getHeaderSize(), configurationMapping.getHeaderMappingInfoList());
		
		stopwatch.stop();
		long millis = stopwatch.elapsed(TimeUnit.MILLISECONDS);
		logger.info("Time taken for validate And Process Header {}", millis);
	}

	public void parseAndValidateDetails(String line) throws InvalidFileNameException, InvalidFileHeaderException, EmptyLineException, InvalidDetailException, InvalidTrailerException, InvalidFileStructureException  {
		Stopwatch stopwatch = Stopwatch.createStarted();
		
		parseAndValidate(line,configurationMapping.getDetailSize(), configurationMapping.getDetailRecMappingInfo());
	
		stopwatch.stop();
		long millis = stopwatch.elapsed(TimeUnit.MILLISECONDS);
		logger.info("Time taken for parse And Validate Details {}", millis);
	}

	public void parseAndValidateFooter(String line) throws InvalidFileNameException, InvalidFileHeaderException,EmptyLineException, InvalidDetailException, InvalidTrailerException, InvalidFileStructureException{
		Stopwatch stopwatch = Stopwatch.createStarted();
		
		parseAndValidate(line,configurationMapping.getTrailerSize(), configurationMapping.getTrailerMappingInfoDto());
		
		stopwatch.stop();
		long millis = stopwatch.elapsed(TimeUnit.MILLISECONDS);
		logger.info("Time taken for parseAndValidateFooter {}", millis);
	}

	public void loadConfigurationMapping() 
	{
		logger.info("Start load configuration mapping...");
		setConfigurationMapping(tQatpMappingDao.getMappingConfigurationDetails(getFileParserParameter(),agencyIdNystaOrNysba));
	}



	public void doFieldMapping(String value, MappingInfoDto fMapping) {}

	public void parseAndValidate(String line,Long size,List<MappingInfoDto> mappings) throws InvalidFileNameException, InvalidFileHeaderException, EmptyLineException, InvalidDetailException, InvalidTrailerException, InvalidFileStructureException 
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

				if (fieldMapping.getFieldName().equals("H_DATE")) {
					if (customDateValidation(value)) 
					{
						isValid = true;
					} 
					else {
						isValid = false;
					}

				}
			}
			catch(Exception ex)
			{
				logger.info("Exception {}",ex.getMessage());
			}
			doFieldMapping(value,fieldMapping);
			if(!isValid && fieldMapping.getFileLevelRejection()!=null && fieldMapping.getFileLevelRejection().equalsIgnoreCase("y")) 
			{
				if(fieldMapping.getFieldType().equals("FILENAME"))
				{
					throw new InvalidFileNameException("Invalid File Name format "+value+"");
				}
				if(fieldMapping.getFieldType().equals("HEADER"))
				{
					throw new InvalidFileHeaderException("Invalid File Header format "+value+"");
				}
				if(fieldMapping.getFieldType().equals("DETAIL"))
				{
					throw new InvalidDetailException("Invalid Detail Record "+value+"");
				}
				if(fieldMapping.getFieldType().equals("TRAILER"))
				{
					throw new InvalidTrailerException("Invalid trailer Record "+value+"");
				}

			}
		}
		if(!isFirstRead)
		{
			processEndOfLine(mappings.get(0).getFieldType());
		}
		
	}

	public boolean customDateValidation(String value) throws InvalidFileHeaderException 
	{

		if (fileNameVO.getFileDate().equals(value)) 
		{

			return true;
		}
		logger.info("Header Date{} is not matching with Header Date", value);

		return false;

	}

	public boolean customTimeValidation(String value) throws InvalidFileHeaderException {

		if (fileNameVO.getFileTime().equals(value)) {

			return true;
		}
		logger.info("Header Time{} is not matching with Header Time", value);

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
	public void fileParseTemplatewithFileName(String fileName,Integer agencyId ) throws IOException
	{		
//		Stopwatch stopwatch = Stopwatch.createStarted();
//		logger.info("Start time {}",new Date().toString());
		agencyIdNystaOrNysba=agencyId;
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


	private void fileParseTemplateInternal(List<File> allfilesFromSrcFolder) throws IOException 
	{
		LocalDateTime from;
		if(allfilesFromSrcFolder!=null && allfilesFromSrcFolder.size()>0)
		{
			logger.info("Total {} files selected for process with name {}",allfilesFromSrcFolder.size(),allfilesFromSrcFolder.toString());
			for (File file : allfilesFromSrcFolder)
			{
				from = LocalDateTime.now();
				try 
				{
					logger.info("File name {} taken for process", file.getName());
					fileName=file.getName();
					recordCount = getRecordCount(file);
					validateAndProcessFile(file);
					AckStatusCode ackStatusCode=AckStatusCode.SUCCESS;
					AckFileWrapper ackObj=prePareAckFileMetaData(ackStatusCode,file);
					processAckFile(ackObj);
				} 
				catch(Exception ex)
				{
					logger.info("Exception {}",ex.getMessage());
					ex.printStackTrace();
					closeFile();
					if(ex instanceof InvalidFileStatusException)
					{

					}
					else if(ex instanceof InvalidRecordCountException || ex instanceof InvalidTrailerException)
					{
						AckStatusCode ackStatusCode=AckStatusCode.INVALID_RECORD_COUNT;
						AckFileWrapper ackObj=prePareAckFileMetaData(ackStatusCode,file);
						processAckFile(ackObj);
					}
					else if(ex instanceof FileAlreadyProcessedException)
					{
						AckFileWrapper ackObj = new AckFileWrapper();
						ackObj.setFile(file);
						ackObj.setFileDestDir(getConfigurationMapping().getFileInfoDto().getProcDir());
						
						File lock = new File(getConfigurationMapping().getFileInfoDto().getSrcDir()+ "\\"+ file.getName()+ ".lock");
				        ackObj.setLockFile(lock);
				        
				        transferFileForDuplicateProcess(ackObj.getFile(), ackObj.getFileDestDir());
						ackObj.getLockFile().delete();
						
					}
					else if(ex instanceof InvalidFileHeaderException)
					{
						AckStatusCode ackStatusCode=AckStatusCode.INVALID_HEADR_FORMAT;
						AckFileWrapper ackObj=prePareAckFileMetaData(ackStatusCode,file);
						processAckFile(ackObj);
					}
					else if(ex instanceof InvalidFileNameException || ex instanceof InvalidFileStructureException)
					{
						AckStatusCode ackStatusCode=AckStatusCode.INVALID_FILE_STRUCTURE;
						AckFileWrapper ackObj=prePareAckFileMetaData(ackStatusCode,file);
						processAckFile(ackObj);
					}
					else
					{
						AckStatusCode ackStatusCode=AckStatusCode.INVALID_DETAIL_RECORD;
						AckFileWrapper ackObj=prePareAckFileMetaData(ackStatusCode,file);
						processAckFile(ackObj);
					}

				}
				logger.debug("Time take for file {} to complete the process is {}",file.getName(),ChronoUnit.MILLIS.between(from, LocalDateTime.now()));
			}
		}
	}
	
	/**
	 * 
	 * @param agencyId 
	 * @throws IOException 
	 * @throws Exception 
	 */
	public void fileParseTemplate(Integer agencyId) throws IOException
	{
		agencyIdNystaOrNysba=agencyId;
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

	public AckFileWrapper prePareAckFileMetaData(AckStatusCode ackStatusCode,File file)
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
	public void validateAndProcessFile(File file) throws InvalidFileStatusException, FileAlreadyProcessedException, InvalidFileNameException, InvalidFileHeaderException, EmptyLineException, IOException, CustomException, InvalidRecordCountException, InvalidDetailException, InvalidTrailerException, InvalidFileStructureException, InterruptedException
	{
		Stopwatch stopwatch = Stopwatch.createStarted();
		startFileProcess(file);
		if(!partiallyExecuted)
		{
			validateAndProcessFileName(file.getName());
			isFirstRead=true;
			for(int i=1;i<=2;i++)
			{	
				BufferedReader reader = getFileReader(file);
				String prviousLine = reader.readLine();
				String currentLine = reader.readLine();
	
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
				
				prviousLine = handleDetailLines(currentLine);
	
				if(isTrailerPresentInFile)
				{
					parseAndValidateFooter(prviousLine);
				}
				else 
				{
					parseValidateAndProcessDetail(prviousLine);
				}
				closeFile();
				isFirstRead=false;
			}
			stopwatch.stop();
			long millis = stopwatch.elapsed(TimeUnit.MILLISECONDS);
			logger.info("Time taken for validate And Process File {}", millis);
			
			closeFile();
		}
		//finishFileProcess();
	}

	public void parseValidateAndProcessDetail(String prviousLine) throws InvalidFileNameException, InvalidFileHeaderException, EmptyLineException, InvalidDetailException, InvalidTrailerException, InvalidFileStructureException {
		parseAndValidateDetails(prviousLine);
		processDetailLine();
	}

	public String handleDetailLines(String currentLine) throws InvalidFileNameException, InvalidFileHeaderException, InvalidDetailException, InvalidTrailerException, InvalidFileStructureException, InterruptedException, IOException 
	{
		Integer counter = 0;
		Integer preProcessedRecordCount=0;
		
		if(reconciliationCheckPoint!=null)
		{
			preProcessedRecordCount=reconciliationCheckPoint.getRecordCount();
		}
		Integer recordCount=0;
		String prviousLine;
		prviousLine = currentLine;
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
						saveRecords(reconciliationCheckPoint);
						reconciliationCheckPoint.setRecordCount(recordCount);
						reconciliationCheckPoint.setFileStatusInd(FileStatusInd.IN_PROGRESS);
						asyncProcessCall.updateCheckpointTable(reconciliationCheckPoint);
						counter = 0;
					}
					parseValidateAndProcessDetail(prviousLine);
//					if(this instanceof NystaFixlengthParser)
//					{
//						processUnmatched(((NystaFixlengthParser)this).getDetailVO(),currentLine,prviousLine);
//					}
					counter++;
					
				}
				recordCount++;
			}
			catch(EmptyLineException ex)
			{
				logger.info("Exception {}",ex.getMessage());
			}
			prviousLine = currentLine;
			//currentLine = textFileReader.readLine();
			currentLine = reader.readLine();
		} while(currentLine != null);

		if(!isFirstRead)
		{
			//update checkpoint table setFileStatusInd to P
			//tQatpMappingDao.updateFileStatus(reconciliationCheckPoint,FileStatusInd.IN_PROGRESS);
			
			saveRecords(reconciliationCheckPoint);
			
			reconciliationCheckPoint.setFileStatusInd(FileStatusInd.COMPLETE);
			reconciliationCheckPoint.setRecordCount(recordCount);
			tQatpMappingDao.updateRecordCount(reconciliationCheckPoint);
		}
		return prviousLine;
	}
	
	public void processUnmatched(NystaAetTxDto nystaAetTxDto, String currentLine, String prviousLine) 
	{
		if(!currentLine.substring(0,1).equals("E"))
		{
			if(!prviousLine.isEmpty() &&   (prviousLine.substring(1,2)).equals("E"))
			{
				if(((currentLine.substring(1,2)).equals("C")  || (currentLine.substring(1,2)).equals("T")) && !currentLine.isEmpty())
				{
					if( ( prviousLine.substring(18,21).equals(currentLine.substring(18,21)) ) && 
							(prviousLine.substring(30,41).equals(currentLine.substring(30,41)) && 
									deviceNotNull(prviousLine.substring(30,41),currentLine.substring(30,41))) )
					{
						if(checkEntryTimestampDiff(prviousLine,currentLine))
						{
					       nystaAetTxDto.setEtcTrxTypeNew("D");
					       nystaAetTxDto.setEtcTrxTypeSubType("E");
					       nystaAetTxDto.setTxExternRefNoMatched(currentLine.substring(133, 144));
					       nystaAetTxDto.setUnmatched(true);
						} 
					    else
					    {
							nystaAetTxDto.setEtcTrxTypeNew("U");
							nystaAetTxDto.setEtcTrxTypeSubType("E");
							nystaAetTxDto.setUnmatched(true);
						}
						     
					}
					else
					{
						logger.info("Difference in Plaza Id or Device Number");
					}
				}
				else if(currentLine.substring(1,2).equals("E"))
				{
					nystaAetTxDto.setEtcTrxTypeNew("U");
					nystaAetTxDto.setEtcTrxTypeSubType("E");
					nystaAetTxDto.setUnmatched(true);
				}
				else
				{
					logger.info("Next record read after 'E' is not 'C' or 'T'");
				}
			}
			else
			{
				logger.info("No 'E' transaction record is present");
			}
		}
		else
		{
			logger.info("End of File");
		}
	}
	private boolean deviceNotNull(String substring, String substring2) 
	{
		if(substring.equals("***********") && substring2.equals("***********") ||
				substring.equals("           ") && substring2.equals("           ") ||
				substring.equals("00000000000") && substring2.equals("00000000000"))
		{
			return false;
		}
		return true;
	}

	private boolean checkEntryTimestampDiff(String prviousLine, String currentLine) 
	{
		if( (DateUtils.parseLocalDate(prviousLine.substring(2, 10),"yyyyMMdd") != null) && (DateUtils.parseLocalDate(currentLine.substring(2, 10),"yyyyMMdd") != null))
		{
			LocalDateTime temp=LocalDateTime.parse(prviousLine.substring(2,18),DateTimeFormatter.ofPattern("yyyyMMddHHmmssSS"));
			logger.info("Previous Timestamp {}",temp);
			
			LocalDateTime temp2=LocalDateTime.parse(currentLine.substring(2,18),DateTimeFormatter.ofPattern("yyyyMMddHHmmssSS"));
			logger.info("Current Timestamp {}",temp2);
			
			//long diff =ChronoUnit.SECONDS.between(temp, temp2);
			
	       // LocalDateTime fromTemp = LocalDateTime.from(temp);
	        long years = temp.until(temp2, ChronoUnit.YEARS);
	        temp = temp.plusYears(years);

	        long months = temp.until(temp2, ChronoUnit.MONTHS);
	        temp = temp.plusMonths(months);

	        long days = temp.until(temp2, ChronoUnit.DAYS);
	        temp = temp.plusDays(days);

	        long hours = temp.until(temp2, ChronoUnit.HOURS);
	        temp = temp.plusHours(hours);

	        long minutes = temp.until(temp2, ChronoUnit.MINUTES);
	        temp = temp.plusMinutes(minutes);

	        long seconds = temp.until(temp2, ChronoUnit.SECONDS);
	        temp = temp.plusSeconds(seconds);
	        
	        if(seconds <= 10)
	        {
	        	return true;
	        }
	        else
	        {
	        	return false;
	        }
		}
		return false;
		
	}

	public void saveRecords(ReconciliationCheckPoint reconciliationCheckPoint) throws InterruptedException
	{

	}

	public void processDetailLine() 
	{

	}
	public void validateRecordCount() throws InvalidRecordCountException
	{

	}

	public void startFileProcess(File file) throws InvalidFileStatusException, FileAlreadyProcessedException, IOException, InvalidFileNameException, InvalidFileHeaderException, EmptyLineException, InvalidDetailException, InvalidTrailerException, InvalidFileStructureException, InterruptedException 
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
			if(insertionData !=null) 
			{
				insertNystaStatDaoImpl.insertNystaData(insertionData);
			}
			
			logger.info("Record is inserted in checkpoint table for file {}", file.getName());
		}
		else if (reconciliationCheckPoint.getFileStatusInd()==FileStatusInd.START)
		{
			logger.info("Entering in else if where file status is START");
			if(insertionData !=null) 
			{
				insertNystaStatDaoImpl.updateStatisticsTable(insertionData.getXferControlId(), fileName);
			}
		}
		else if(reconciliationCheckPoint.getFileStatusInd()==FileStatusInd.IN_PROGRESS)
		{
			//check how many records are processed and get the max extern_ref_no
			String txExternRefNo = tQatpMappingDao.getLastProcessedExternFileNumber(xferControl.getXferControlId());
			
			//open the file and check the reference no and process from the next reference no
			if(txExternRefNo!=null)
			{
				processedPartiallyExecutedFile(file,txExternRefNo);
				partiallyExecuted = true;
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

	public void finishFileProcess() throws CustomException, InterruptedException
	{
		System.out.println("Finish File Process from FileParserImpl");
	}

	public List<File> getAllFilesFromSourceFolder() throws IOException 
	{
		List<File> allfilesFromSrcDirectory = fileUtil.getAllfilesFromSrcDirectory(getConfigurationMapping().getFileInfoDto().getSrcDir(),fileFormat);
		return allfilesFromSrcDirectory;
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

	public void transferFileForDuplicateProcess(File srcFile, String destDirPath) throws IOException 
	{
		try
		{
			logger.info("As file {} is already processed,adding a _DUP extension",srcFile.getName());
			Path destDir = new File(destDirPath, srcFile.getName().concat("_DUP")).toPath();
			
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
		Stopwatch stopwatch = Stopwatch.createStarted();
		
		//processFile();
		//fileParseTemplatewithFileName(file.getName().toString());
		
		stopwatch.stop();
		long millis = stopwatch.elapsed(TimeUnit.MILLISECONDS);
		logger.info("Total time taken to process the filename {} is {}",file.getName(),millis);
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
		agencyIdNystaOrNysba=1;
		loadConfigurationMapping();
		List<File> allfilesFromSrcFolder = fileUtil.getFilesFromSrcDir(getConfigurationMapping().getFileInfoDto().getSrcDir(),fileFormat);
		List<String> result = new ArrayList<String>();
		
		for(File file : allfilesFromSrcFolder)
		{
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<String> entity = new HttpEntity<String>(headers);
			
			String url = null;
			url = nystaFileURL.concat("?fileName=" + file.getName());
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
	
	
	protected BufferedReader getFileReader(File file) throws IOException
	{
		//BufferedReader reader = new BufferedReader(new FileReader(file));
		
		reader = new BufferedReader(new FileReader(file));
		reader.mark(1);
		
		return reader;
	}

	public long getRecordCount1(File file)
	{
		try 
		{
			int count=0;
			StringBuilder sb = new StringBuilder();
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);

			String s = br.readLine();
			while (s!=null)
			{
				if(s.contains("["))
				{
					count++;
				}
				s = br.readLine();
			}
			br.close();
			return count;
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return 0L;
	}
	
	public long getRecordCount(File file)
	{
		try (Stream<String> lines = Files.lines(file.toPath()))
		{
			long numOfLines = lines.count()-2;
			return numOfLines;
		} catch (IOException e) 
		{
			// TODO Auto-generated catch block
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

	public AckFileWrapper prePareAckFileMetaDataForFileAlreadyProcessed(AckStatusCode ackStatusCode, File file) {
		// TODO Auto-generated method stub
		return null;
	}

	public void processedPartiallyExecutedFile(File file, String txExternRefNo) throws IOException, InvalidFileNameException, InvalidFileHeaderException, EmptyLineException, InvalidDetailException, InvalidTrailerException, InvalidFileStructureException, InterruptedException {
		// TODO Auto-generated method stub
		
	}
}
