package com.conduent.tpms.qatp.parser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

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
import com.conduent.tpms.qatp.model.XferControl;
import com.conduent.tpms.qatp.service.ITransDetailService;
import com.conduent.tpms.qatp.utility.GenericValidation;
import com.conduent.tpms.qatp.utility.MasterDataCache;
import com.conduent.tpms.qatp.validation.FileUtil;
import com.conduent.tpms.qatp.validation.TextFileReader;
import com.google.common.base.Stopwatch;

public class FileParserImpl 
{

	private static final Logger logger = LoggerFactory.getLogger(FileParserImpl.class);

	protected boolean isHederPresentInFile;
	protected boolean isDetailsPresentInFile;
	protected boolean isTrailerPresentInFile;
	protected FileNameDetailVO fileNameVO;
	protected int count = 0;

	@Autowired
	protected TQatpMappingDao tQatpMappingDao;

	@Autowired
	private IQatpDao qatpDao;

	@Autowired
	protected ITransDetailService transDetailService;

	@Autowired
	protected
	GenericValidation genericValidation;

	@Autowired
	private TextFileReader textFileReader;

	@Autowired
	protected MasterDataCache masterDataCache;
	
	@Autowired
	InsertNystaStatDaoImpl insertNystaStatDaoImpl;

	private FileUtil fileUtil=new FileUtil();
	private String fileType;
	private String fileFormat;
	private String agencyId;
	private String fileName;
	protected XferControl xferControl;
	private ReconciliationCheckPoint reconciliationCheckPoint;
	protected long recordCount;
	protected boolean isFirstRead=true;
	private Map<String,Integer> lineSize;

	private FileParserParameters configurationMapping;
	protected FileParserParameters fileParserParameter=new FileParserParameters();


	public void initialize() {}

	public void validateAndProcessFileName(String fileName) throws InvalidFileNameException, InvalidFileHeaderException, EmptyLineException, InvalidDetailException, InvalidTrailerException, InvalidFileStructureException
	{
		logger.info("Validate And process file name {}", fileName);
		parseAndValidate(fileName,configurationMapping.getFileNameSize(), configurationMapping.getFileNameMappingInfo());
	}

	public void validateAndProcessHeader(String line) throws InvalidFileNameException, InvalidFileHeaderException, EmptyLineException, InvalidDetailException, InvalidTrailerException, InvalidFileStructureException {
		parseAndValidate(line,configurationMapping.getHeaderSize(), configurationMapping.getHeaderMappingInfoList());
	}

	public void parseAndValidateDetails(String line) throws InvalidFileNameException, InvalidFileHeaderException, EmptyLineException, InvalidDetailException, InvalidTrailerException, InvalidFileStructureException  {
		parseAndValidate(line,configurationMapping.getDetailSize(), configurationMapping.getDetailRecMappingInfo());
	}

	public void parseAndValidateFooter(String line) throws InvalidFileNameException, InvalidFileHeaderException,EmptyLineException, InvalidDetailException, InvalidTrailerException, InvalidFileStructureException{
		parseAndValidate(line,configurationMapping.getTrailerSize(), configurationMapping.getTrailerMappingInfoDto());
	}

	public void loadConfigurationMapping() 
	{
		logger.info("Start load configuration mapping...");
		setConfigurationMapping(tQatpMappingDao.getMappingConfigurationDetails(getFileParserParameter()));
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


	/**
	 * 
	 * @throws IOException 
	 * @throws Exception 
	 */
	public void fileParseTemplate() throws IOException
	{
		Stopwatch stopwatch = Stopwatch.createStarted();
		initialize();
		loadConfigurationMapping();
		List<File> allfilesFromSrcFolder = getAllFilesFromSourceFolder();
		if(allfilesFromSrcFolder!=null && allfilesFromSrcFolder.size()>0)
		{
			
			logger.info("Total {} files selected for process",allfilesFromSrcFolder.size());
			for (File file : allfilesFromSrcFolder)
			{
				try 
				{
					logger.info("File name {} taken for process", file.getName());
					fileName=file.getName();
					recordCount=textFileReader.getRecordCount1(file);
					validateAndProcessFile(file);
					AckStatusCode ackStatusCode=AckStatusCode.SUCCESS;
					AckFileWrapper ackObj=prePareAckFileMetaData(ackStatusCode,file);
					processAckFile(ackObj);
				} 
				catch(Exception ex)
				{
					ex.printStackTrace();
					textFileReader.closeFile();
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
						AckStatusCode ackStatusCode=AckStatusCode.SUCCESS;
						AckFileWrapper ackObj=prePareAckFileMetaData(ackStatusCode,file);
						processAckFile(ackObj);
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
			}
		}
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
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	public void validateAndProcessFile(File file) throws InvalidFileStatusException, FileAlreadyProcessedException, InvalidFileNameException, InvalidFileHeaderException, EmptyLineException, IOException, CustomException, InvalidRecordCountException, InvalidDetailException, InvalidTrailerException, InvalidFileStructureException
	{

		startFileProcess(file);
		validateAndProcessFileName(file.getName());
		isFirstRead=true;
		for(int i=1;i<=2;i++)
		{
			textFileReader.openFile(file);
			String prviousLine = textFileReader.readLine();
			String currentLine = textFileReader.readLine();

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
			prviousLine = handleDetailLines(currentLine);

			if(isTrailerPresentInFile)
			{
				parseAndValidateFooter(prviousLine);
			}
			else 
			{
				parseValidateAndProcessDetail(prviousLine);
			}
			//textFileReader.reset();
			textFileReader.closeFile();
			isFirstRead=false;
		}
		textFileReader.closeFile();
		finishFileProcess();
	}

	public void parseValidateAndProcessDetail(String prviousLine) throws InvalidFileNameException, InvalidFileHeaderException, EmptyLineException, InvalidDetailException, InvalidTrailerException, InvalidFileStructureException {
		parseAndValidateDetails(prviousLine);
		processDetailLine();
	}

	public String handleDetailLines(String currentLine) throws InvalidFileNameException, InvalidFileHeaderException, InvalidDetailException, InvalidTrailerException, InvalidFileStructureException 
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
		currentLine = textFileReader.readLine();
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
						
						reconciliationCheckPoint.setFileStatusInd(FileStatusInd.IN_PROGRESS);
						reconciliationCheckPoint.setRecordCount(recordCount);
						tQatpMappingDao.updateRecordCount(reconciliationCheckPoint);
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
			prviousLine = currentLine;
			currentLine = textFileReader.readLine();
		} while(currentLine != null);

		if(!isFirstRead)
		{
			saveRecords(reconciliationCheckPoint);
			
			reconciliationCheckPoint.setFileStatusInd(FileStatusInd.COMPLETE);
			reconciliationCheckPoint.setRecordCount(recordCount);
			tQatpMappingDao.updateRecordCount(reconciliationCheckPoint);
		}
		return prviousLine;
	}
	public void saveRecords(ReconciliationCheckPoint reconciliationCheckPoint)
	{

	}

	public void processDetailLine() 
	{

	}
	public void validateRecordCount() throws InvalidRecordCountException
	{

	}

	public void startFileProcess(File file) throws InvalidFileStatusException, FileAlreadyProcessedException 
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
						if(insertionData !=null) {
						insertNystaStatDaoImpl.insertNystaData(insertionData);
						}
			
			logger.info("Record is inserted in checkpoint table for file {}", file.getName());
		}
		else if(reconciliationCheckPoint.getFileStatusInd()==FileStatusInd.COMPLETE)
		{
			throw new FileAlreadyProcessedException("File All ready processed");
		}

	}

	public void finishFileProcess() throws CustomException
	{

	}

	public List<File> getAllFilesFromSourceFolder() throws IOException 
	{
		List<File> allfilesFromSrcDirectory = fileUtil.getAllfilesFromSrcDirectory(getConfigurationMapping().getFileInfoDto().getSrcDir(),fileFormat);
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
}