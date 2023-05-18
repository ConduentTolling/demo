package com.conduent.tpms.iag.validation;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.conduent.app.timezone.utility.TimeZoneConv;
import com.conduent.tpms.iag.constants.AckStatusCode;
import com.conduent.tpms.iag.constants.Constants;
import com.conduent.tpms.iag.constants.ICTXConstants;
import com.conduent.tpms.iag.dao.IaStatsDao;
import com.conduent.tpms.iag.dao.TQatpMappingDao;
import com.conduent.tpms.iag.dao.TQatpStatisticsDao;
import com.conduent.tpms.iag.dao.impl.TAgencyDaoImpl;
import com.conduent.tpms.iag.dao.impl.TransDetailDao;
import com.conduent.tpms.iag.dto.AccountApiInfoDto;
import com.conduent.tpms.iag.dto.AckFileInfoDto;
import com.conduent.tpms.iag.dto.AckFileWrapper;
import com.conduent.tpms.iag.dto.AgencyInfoVO;
import com.conduent.tpms.iag.dto.FileParserParameters;
import com.conduent.tpms.iag.dto.IctxItxcDetailInfoVO;
import com.conduent.tpms.iag.dto.IctxItxcHeaderInfoVO;
import com.conduent.tpms.iag.dto.IctxItxcNameVO;
import com.conduent.tpms.iag.dto.MappingInfoDto;
import com.conduent.tpms.iag.dto.Plaza;
import com.conduent.tpms.iag.dto.TCodesVO;
import com.conduent.tpms.iag.dto.TLaneDto;
import com.conduent.tpms.iag.exception.CustomException;
import com.conduent.tpms.iag.exception.EmptyLineException;
import com.conduent.tpms.iag.exception.FileAlreadyProcessedException;
import com.conduent.tpms.iag.exception.InvalidFileDetailException;
import com.conduent.tpms.iag.exception.InvalidFileHeaderException;
import com.conduent.tpms.iag.exception.InvalidFileNameException;
import com.conduent.tpms.iag.exception.InvalidFileStatusException;
import com.conduent.tpms.iag.exception.InvalidFileTypeException;
import com.conduent.tpms.iag.exception.InvalidRecordCountException;
import com.conduent.tpms.iag.exception.InvalidRecordException;
import com.conduent.tpms.iag.exception.GapInSeqNumException;
import com.conduent.tpms.iag.exception.ICTXFileNumDuplicationException;
import com.conduent.tpms.iag.model.AccountInfo;
import com.conduent.tpms.iag.model.ConfigVariable;
import com.conduent.tpms.iag.model.FileDetails;
import com.conduent.tpms.iag.model.LaneIdExtLaneInfo;
import com.conduent.tpms.iag.model.TollPostLimitInfo;
import com.conduent.tpms.iag.model.TranDetail;
import com.conduent.tpms.iag.model.XferControl;
import com.conduent.tpms.iag.utility.FPMSAccountApi;
import com.conduent.tpms.iag.utility.GenericValidation;
import com.conduent.tpms.iag.utility.LocalDateAdapter;
import com.conduent.tpms.iag.utility.LocalDateTimeAdapter;
import com.conduent.tpms.iag.utility.MasterDataCache;
import com.conduent.tpms.iag.utility.MessagePublisher;
import com.conduent.tpms.iag.utility.OffsetDateTimeConverter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.lang.Math;

public class FileParserImpl {

	private static final Logger log = LoggerFactory.getLogger(FileParserImpl.class);

	public enum ProgStat { STAR, S, U, F }
	
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

	@Autowired
	protected TransDetailDao transDetailDao;

	@Autowired
	protected IaStatsDao iaStatsDao;

	@Autowired
	FPMSAccountApi fpmsAccountApi;

	@Autowired
	protected ConfigVariable configVariable;

	@Autowired
	protected TQatpStatisticsDao tQatpStatisticsDao;

	@Autowired
	private MessagePublisher messagePublisher;

	@Autowired
	TimeZoneConv timeZoneConv;

	private FileUtil fileUtil = new FileUtil();
	private String fileType;
	private String fileFormat;
	private String agencyId;
	private String toAgencyId;

	private FileParserParameters configurationMapping;
	private FileParserParameters fileParserParameter = new FileParserParameters();
	private FileParserParameters fileParserParameterITXC = new FileParserParameters();
	protected String fileName;
	// protected Long recordCount;
	protected Map<String, Long> recordCount = new HashMap<>();
	public File newFile;
	protected XferControl xferControl;
	public File threadFile;
	protected List<IctxItxcDetailInfoVO> detailVOList;
	protected IctxItxcNameVO fileNameVO;

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

		AckStatusCode ackStatusCode = null;
		log.info("File {} added in job....", file.getName());
		try {
			log.info("File name {} taken for process", file.getName());
			recordCount.put(file.getName(), textFileReader.getRecordCount(file));
			validateAndProcessFile(file);
			ackStatusCode = AckStatusCode.SUCCESS;

		} catch (Exception ex) {
			log.error("Exception of cause in FileParserImpl.fileParseTemplate ICTX file Processing {}", ex.getCause());
			try {
				textFileReader.closeFile();
			} catch (IOException e) {
				log.error("Exception in FileParserImpl.fileParseTemplate {}", e.getMessage());
				e.printStackTrace();
			}
			if (ex instanceof InvalidFileNameException) {
				log.info("Invalid file name...");
				ackStatusCode = AckStatusCode.INVALID_FILE;
			} else if (ex instanceof InvalidFileHeaderException) {
				ackStatusCode = AckStatusCode.HEADER_FAIL;
				if (ex.getMessage().contains("Date mismatch") || ex.getMessage().contains("Time mismatch")) {
					ackStatusCode = AckStatusCode.DATE_MISMATCH;
				}
			} else if (ex instanceof InvalidRecordCountException) {
				ackStatusCode = AckStatusCode.INVALID_RECORD_COUNT;
			} else if (ex instanceof FileAlreadyProcessedException) {
				//ackStatusCode = AckStatusCode.SUCCESS;

				log.info("Start of FileAlreadyProcessedException...");
				//Need to Check if this should be moved to proc or unproc directory
				ackStatusCode = AckStatusCode.FILE_ALREADY_PROCESSED;
				AckFileWrapper ackObj = prePareAckFileMetaData(ackStatusCode, file);
				//ackObj.setFileDestDir(getConfigurationMapping().getFileInfoDto().getProcDir());
				ackObj.setFileDestDir(getConfigurationMapping().getFileInfoDto().getUnProcDir());
				processAckFile(ackObj);
				log.info(ex.getMessage());
			
			} else if (ex instanceof InvalidRecordException) {
				ackStatusCode = AckStatusCode.DETAIL_FAIL;
			} else if (ex instanceof InvalidFileStatusException) {
				ackStatusCode = AckStatusCode.INVALID_FILE;
			} else if (ex instanceof EmptyLineException) {
				 ackStatusCode = AckStatusCode.DETAIL_FAIL;
			} else if (ex instanceof InvalidFileTypeException) {
				 ackStatusCode = AckStatusCode.INVALID_FILE;
			} else if (ex instanceof DateTimeParseException) {
				 ackStatusCode = AckStatusCode.DATE_MISMATCH;
			} else if (ex instanceof ICTXFileNumDuplicationException) {
				ackStatusCode = AckStatusCode.DUPLICATE_FILE_NUM;
			} else if (ex instanceof GapInSeqNumException) {
				ackStatusCode = AckStatusCode.GAP_IN_SEQ_NUM;
			} else {
				ex.printStackTrace();
			}
			log.info(ex.getMessage());
			log.error(ex.getMessage());
		} finally {
			if (ackStatusCode != null) {
				AckFileWrapper ackObj = prePareAckFileMetaData(ackStatusCode, file);
				String objStatusCode = ackStatusCode.getCode();
				log.info("Ack file StatusCode: {}", objStatusCode);
				if (objStatusCode.equalsIgnoreCase(AckStatusCode.SUCCESS.getCode())
						|| objStatusCode.equalsIgnoreCase(AckStatusCode.GAP_IN_SEQ_NUM.getCode())) {
					ackObj.setFileDestDir(getConfigurationMapping().getFileInfoDto().getProcDir());
				} else if (objStatusCode.equalsIgnoreCase(AckStatusCode.HEADER_FAIL.getCode())
						|| objStatusCode.equalsIgnoreCase(AckStatusCode.DETAIL_FAIL.getCode())
						|| objStatusCode.equalsIgnoreCase(AckStatusCode.INVALID_RECORD_COUNT.getCode())
						|| objStatusCode.equalsIgnoreCase(AckStatusCode.INVALID_FILE.getCode())
						|| objStatusCode.equalsIgnoreCase(AckStatusCode.DATE_MISMATCH.getCode())
						|| objStatusCode.equalsIgnoreCase(AckStatusCode.DUPLICATE_FILE_NUM.getCode())
						|| objStatusCode.equalsIgnoreCase(AckStatusCode.FILE_ALREADY_PROCESSED.getCode())) {
					ackObj.setFileDestDir(getConfigurationMapping().getFileInfoDto().getUnProcDir());
				}
				processAckFile(ackObj);
			}
		}
		getMissingFilesForAgency();
		log.info("Exiting File processing ..");
	}

	
	  public void getMissingFilesForAgency() { // TODO Auto-generated method stub
	  
	  }
	  
	  /*Older fileParseTemplate method for reference -Niranjan Shinde
	   * public void fileParseTemplate(File file) throws IOException, InterruptedException {

		log.info("File {} added in job....", file.getName());
		try {
			log.info("File name {} taken for process", file.getName());
			recordCount.put(file.getName(), textFileReader.getRecordCount(file));
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
		} catch (Exception ex) {
			log.error("Exception of cause in first Catch {}", ex.getCause());
			try {
				textFileReader.closeFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (ex instanceof InvalidFileNameException) {
				log.info("PROC_UNPROC_ISSUE ===== >> 8 ##");
				log.info("Invalid file name...");
				AckStatusCode ackStatusCode = AckStatusCode.INVALID_FILE;
				AckFileWrapper ackObj = prePareAckFileMetaData(ackStatusCode, file);
				ackObj.setFileDestDir(getConfigurationMapping().getFileInfoDto().getUnProcDir());
				processAckFile(ackObj);
			} else if (ex instanceof InvalidFileHeaderException) {
				log.info("PROC_UNPROC_ISSUE ===== >> 9 ##");
				AckStatusCode ackStatusCode = AckStatusCode.HEADER_FAIL;
				if (ex.getMessage().contains("Date mismatch") || ex.getMessage().contains("Time mismatch")) {
					ackStatusCode = AckStatusCode.DATE_MISMATCH;
				}
				AckFileWrapper ackObj = prePareAckFileMetaData(ackStatusCode, file);
				ackObj.setFileDestDir(getConfigurationMapping().getFileInfoDto().getUnProcDir());
				processAckFile(ackObj);
			} else if (ex instanceof InvalidRecordCountException) {
				log.info("PROC_UNPROC_ISSUE ===== >> 10 ##");
				AckStatusCode ackStatusCode = AckStatusCode.INVALID_RECORD_COUNT;
				AckFileWrapper ackObj = prePareAckFileMetaData(ackStatusCode, file);
				ackObj.setFileDestDir(getConfigurationMapping().getFileInfoDto().getUnProcDir());
				processAckFile(ackObj);
			} else if (ex instanceof FileAlreadyProcessedException) {
				log.info("PROC_UNPROC_ISSUE ===== >> 11 ##");
				AckStatusCode ackStatusCode = AckStatusCode.SUCCESS;
				AckFileWrapper ackObj = prePareAckFileMetaData(ackStatusCode, file);
				ackObj.setFileDestDir(getConfigurationMapping().getFileInfoDto().getProcDir());
				processAckFile(ackObj);
				log.info(ex.getMessage());
			} else if (ex instanceof InvalidRecordException) {
				log.info("PROC_UNPROC_ISSUE ===== >> 12 ##");
				AckStatusCode ackStatusCode = AckStatusCode.DETAIL_FAIL;
				AckFileWrapper ackObj = prePareAckFileMetaData(ackStatusCode, file);
				ackObj.setFileDestDir(getConfigurationMapping().getFileInfoDto().getUnProcDir());
				processAckFile(ackObj);
				log.info(ex.getMessage());
			} else if (ex instanceof InvalidFileStatusException) {
				log.info("PROC_UNPROC_ISSUE ===== >> 13 ##");
			} else if (ex instanceof EmptyLineException) {
				log.info("PROC_UNPROC_ISSUE ===== >> 14 ##");
				log.info("Current line is empty..");
			} else if (ex instanceof InvalidFileTypeException) {
			} else if (ex instanceof DateTimeParseException) {
				log.info("PROC_UNPROC_ISSUE ===== >> 15 ##");
				AckStatusCode ackStatusCode = AckStatusCode.DATE_MISMATCH;
				AckFileWrapper ackObj = prePareAckFileMetaData(ackStatusCode, file);
				ackObj.setFileDestDir(getConfigurationMapping().getFileInfoDto().getUnProcDir());
				processAckFile(ackObj);
			} else {
				log.info("PROC_UNPROC_ISSUE ===== >> 16 ##");
				ex.printStackTrace();
			}
		}
		getMissingFilesForAgency();
		log.info("Exiting File processing ..");
	}
	   */
	 
/*
	public void fileParseTemplateITXC(File file) throws IOException, InterruptedException {

		log.info("File {} added in job....", file.getName());
		try {
			log.info("File name {} taken for process", file.getName());
			recordCount.put(file.getName(), textFileReader.getRecordCount(file));
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
		} catch (Exception ex) {
			log.error("Exception in first Catch {}", ex);
			log.error("Exception of cause in first Catch {}", ex.getCause());
			try {
				textFileReader.closeFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (ex instanceof InvalidFileNameException) {
				log.info("PROC_UNPROC_ISSUE ===== >> 8 ##");
				log.info("Invalid file name or date");
				try {
					transferFile(file, getConfigurationMapping().getFileInfoDto().getUnProcDir());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else if (ex instanceof InvalidFileHeaderException) {
				log.info("PROC_UNPROC_ISSUE ===== >> 9 ##");
				AckStatusCode ackStatusCode = AckStatusCode.HEADER_FAIL;
				if (ex.getMessage().contains("Date mismatch") || ex.getMessage().contains("Time mismatch")) {
					ackStatusCode = AckStatusCode.DATE_MISMATCH;
				}
				AckFileWrapper ackObj = prePareAckFileMetaData(ackStatusCode, file);
				ackObj.setFileDestDir(getConfigurationMapping().getFileInfoDto().getUnProcDir());
				processAckFile(ackObj);
			} else if (ex instanceof InvalidRecordCountException) {
				log.info("PROC_UNPROC_ISSUE ===== >> 10 ##");
				AckStatusCode ackStatusCode = AckStatusCode.INVALID_RECORD_COUNT;
				AckFileWrapper ackObj = prePareAckFileMetaData(ackStatusCode, file);
				ackObj.setFileDestDir(getConfigurationMapping().getFileInfoDto().getUnProcDir());
				processAckFile(ackObj);
			} else if (ex instanceof FileAlreadyProcessedException) {
				log.info("PROC_UNPROC_ISSUE ===== >> 11 ##");
				AckStatusCode ackStatusCode = AckStatusCode.SUCCESS;
				AckFileWrapper ackObj = prePareAckFileMetaData(ackStatusCode, file);
				ackObj.setFileDestDir(getConfigurationMapping().getFileInfoDto().getProcDir());
				processAckFile(ackObj);
				log.info(ex.getMessage());
			} else if (ex instanceof InvalidRecordException) {
				log.info("PROC_UNPROC_ISSUE ===== >> 12 ##");
				AckStatusCode ackStatusCode = AckStatusCode.DETAIL_FAIL;
				AckFileWrapper ackObj = prePareAckFileMetaData(ackStatusCode, file);
				ackObj.setFileDestDir(getConfigurationMapping().getFileInfoDto().getUnProcDir());
				processAckFile(ackObj);
				log.info(ex.getMessage());
			} else if (ex instanceof InvalidFileStatusException) {
				log.info("PROC_UNPROC_ISSUE ===== >> 13 ##");
			} else if (ex instanceof EmptyLineException) {
				log.info("PROC_UNPROC_ISSUE ===== >> 14 ##");
				log.info("Current line is empty..");
			} else if (ex instanceof InvalidFileTypeException) {
			} else if (ex instanceof DateTimeParseException) {
				log.info("PROC_UNPROC_ISSUE ===== >> 15 ##");
				AckStatusCode ackStatusCode = AckStatusCode.DATE_MISMATCH;
				AckFileWrapper ackObj = prePareAckFileMetaData(ackStatusCode, file);
				ackObj.setFileDestDir(getConfigurationMapping().getFileInfoDto().getUnProcDir());
				processAckFile(ackObj);
			} else {
				log.info("PROC_UNPROC_ISSUE ===== >> 16 ##");
				ex.printStackTrace();
			}
		}
		
		 * }
		 * 
		 * } }; log.info("PROC_UNPROC_ISSUE ===== >> 17 ##");
		 * executorService.submit(task); } }
		 * log.info("PROC_UNPROC_ISSUE ===== >> 18 ##");
		 
		getMissingFilesForAgency();
		log.info("Exiting File processing ..");
	}
*/
	/*
	 * Validated and process ICTXfile.
	 */

	public void validateAndProcessFile(File file)
			throws InvalidFileNameException, InvalidFileDetailException, InvalidFileHeaderException, EmptyLineException,
			InvalidRecordException, InvalidRecordCountException, DateTimeParseException, InvalidFileStatusException,
			IOException, InvalidFileTypeException, FileAlreadyProcessedException, ICTXFileNumDuplicationException, GapInSeqNumException {
		startFileProcess(file);
		validateAndProcessFileName(file.getName());
		textFileReader.openFile(file);

		String prviousLine = textFileReader.readLine();
		String currentLine = textFileReader.readLine();

		/*
		 * Handles empty file
		 * commenting the code due to redundancy -Niranjan Shinde
		 */
		/*
		 * if (prviousLine == null) {
		 * 
		 * }
		 */

		if (prviousLine != null && currentLine != null) {
			if (isHederPresentInFile) {
				validateAndProcessHeader(prviousLine);
				validateRecordCount();
			} else {
				parseValidateAndProcessDetail(prviousLine);
			}
		}
		log.info("Processing detail for file {}",file.getName());
		prviousLine = handleDetailLines(currentLine);

		if (isTrailerPresentInFile) {
			parseAndValidateFooter(prviousLine);
		} else {
			if (prviousLine != null) {
				parseValidateAndProcessDetail(prviousLine);
			}
		}

		textFileReader.closeFile();
		finishFileProcess();

	}

	public void startFileProcess(File file)
			throws InvalidFileStatusException, IOException, InvalidFileNameException, InvalidFileTypeException {
		log.info("startFileProcess Method Starting: ");
		setFileName(file.getName());
		setNewFile(file);
		CheckValidFileType(file);
		xferControl = tQatpMappingDao.checkFileDownloaded(file.getName());
		if (xferControl == null) {
			log.info("File is not downloaded completely: {}", file.getName());
			throw new InvalidFileStatusException("File not downloded");
		}
		log.info("startFileProcess Method exiting: ");
	}

	public void validateAndProcessFileName(String fileName)
			throws InvalidFileNameException, InvalidFileHeaderException, InvalidFileDetailException, IOException,
			FileAlreadyProcessedException, EmptyLineException, InvalidRecordException, DateTimeParseException, ICTXFileNumDuplicationException {
		log.info("validateAndProcessFileName Method starting: ");

		if (fileName.length() == Constants.FILE_NAME_LENGHT) {
			parseAndValidate(fileName, configurationMapping.getFileNameMappingInfo());
		} else {
			throw new InvalidFileNameException("Invalid line: " + fileName);
		}
		log.info("validateAndProcessFileName Method exiting: ");
	}
	
	public boolean validateFileAgency(String fileName) throws InvalidFileNameException {
		if (fileName != null) {
			MappingInfoDto fromAgency = configurationMapping.getFileNameMappingInfo().get(0);
			String filePrefix = fileName.substring(fromAgency.getStartPosition().intValue(),
					fromAgency.getEndPosition().intValue());
			Boolean devicePresent = false;
			Optional<AgencyInfoVO> agencyInfoVO = masterDataCache.getAgencyInfoVOList().stream()
					.filter(e -> e.getFilePrefix().equals(filePrefix)).findAny();

			if (agencyInfoVO.isPresent()) {
				agencyInfoVO.get().setFileProcessingStatus(ICTXConstants.YES);
				log.info("Valid prefix for file: {}", fileName);
				devicePresent = true;
			}
			return devicePresent;
		} else {
			throw new InvalidFileNameException("File name is empty");
		}
	}

	public void insertFileDetailsIntoCheckpoint() {
		// TODO Auto-generated method stub

	}

	/*
	 * Validates header
	 */
	public void validateAndProcessHeader(String line) throws InvalidFileNameException, InvalidFileDetailException,
			InvalidFileHeaderException, EmptyLineException, InvalidRecordException, DateTimeParseException, ICTXFileNumDuplicationException {
		log.info("validateAndProcessHeader Mathod starting: ");
		if (line.length() == Constants.HEADER_LENGHT) {
			parseAndValidate(line, configurationMapping.getHeaderMappingInfoList());
		} else {
			throw new InvalidFileHeaderException("Invalid header: " + line);
		}
		log.info("validateAndProcessHeader Mathod exiting: ");
	}

	/*
	 * Validates detail record
	 */
	public void parseAndValidateDetails(String line) throws InvalidFileNameException, InvalidFileDetailException,
			InvalidFileHeaderException, EmptyLineException, InvalidRecordException, DateTimeParseException, ICTXFileNumDuplicationException {
		log.info("parseAndValidateDetails Mathod starting: ");
		if (line.length() == Constants.DETAIL_RECORD_LENGHT || line.length() == Constants.ITXC_DETAIL_RECORD_LENGHT) {
			parseAndValidate(line, configurationMapping.getDetailRecMappingInfo());
		} else {
			throw new InvalidRecordException("Invalid line length: " + line);
		}
		log.info("parseAndValidateDetails Method exiting: ");
	}

	/*
	 * Validates footer
	 */
	public void parseAndValidateFooter(String line) throws InvalidFileNameException, InvalidFileDetailException,
			InvalidFileHeaderException, EmptyLineException, InvalidRecordException, DateTimeParseException, ICTXFileNumDuplicationException {
		log.info("parseAndValidateFooter Mathod starting: ");
		parseAndValidate(line, configurationMapping.getTrailerMappingInfoDto());
		log.info("parseAndValidateFooter Mathod exiting: ");
	}

	/*
	 * Loads configuration mapping details for file from T_FIELD_MAPPING table
	 */
	public void loadConfigurationMapping(String fileType) {
		getFileParserParameter().setFileType(fileType);
		setConfigurationMapping(tQatpMappingDao.getMappingConfigurationDetails(fileType));
	}

	public void loadConfigurationMappingITXC(String fileType) {
		getFileParserParameterITXC().setFileType(fileType);
		setConfigurationMappingITXC(tQatpMappingDao.getMappingConfigurationDetailsITXC(fileType));
	}

	/*
	 * Validates field's according to their mapping from T_FIELD_MAPPING table
	 */
	public void doFieldMapping(String value, MappingInfoDto fMapping) throws InvalidFileNameException,
			InvalidFileHeaderException, DateTimeParseException, InvalidRecordException, ICTXFileNumDuplicationException {
	}

	/*
	 * Parse file according to their sections i.e Header, Detail
	 */
	public void parseAndValidate(String line, List<MappingInfoDto> mappings)
			throws InvalidFileNameException, InvalidFileDetailException, InvalidFileHeaderException, EmptyLineException,
			InvalidRecordException, DateTimeParseException, ICTXFileNumDuplicationException {
		log.info("parseAndValidate Method starting and fileSection: {}", mappings.get(0).getFieldType());
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
			isValid = genericValidation.doValidation(fieldMapping, value);
			doFieldMapping(value.trim(), fieldMapping);
			log.info("Validation for field {} :value: {} valid: {}", fieldMapping.getFieldName(), value.trim(),
					isValid);

			if (!isValid) {
				if (fieldMapping.getFieldType().equals(ICTXConstants.FILENAME)) {
					throw new InvalidFileNameException("Invalid File Name format");
				}
				if (fieldMapping.getFieldType().equals(ICTXConstants.HEADER)) {
					throw new InvalidFileHeaderException("Invalid File Header format");
				}
				if (fieldMapping.getFieldType().equals(ICTXConstants.DETAIL)) {
					log.info("Invalid record data in record {}", value);
					throw new InvalidRecordException("Invalid record data.");
				}
			}
		}
		processEndOfLine(fileSection);
		log.info("parseAndValidate Method exiting and fileSection: {}", mappings.get(0).getFieldType());
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
	public void parseValidateAndProcessDetail(String prviousLine) throws InvalidFileNameException,
			InvalidFileHeaderException, EmptyLineException, InvalidRecordException, InvalidFileDetailException, DateTimeParseException, ICTXFileNumDuplicationException {
		parseAndValidateDetails(prviousLine);
		processDetailLine();
	}

	/*
	 * Handles details record in ictx file
	 */
	public String handleDetailLines(String currentLine) throws InvalidFileNameException, InvalidFileHeaderException,
			EmptyLineException, InvalidRecordException, InvalidFileDetailException, DateTimeParseException, ICTXFileNumDuplicationException {
		log.info("handleDetailLines Starting");
		String prviousLine;
		prviousLine = currentLine;
		currentLine = textFileReader.readLine();
		int i = 0;

		do {
			parseValidateAndProcessDetail(prviousLine);
			i = i + 1;
			fileDetails.setProcessedCount(i);
			fileDetails.setSuccessCount(i);
			prviousLine = currentLine;
			currentLine = textFileReader.readLine();
		} while (currentLine != null);
		log.info("handleDetailLines exiting");
		return prviousLine;
	}

	public void processDetailLine() {

	}

	public void finishFileProcess()
			throws InvalidFileHeaderException, FileAlreadyProcessedException, InvalidRecordException, ICTXFileNumDuplicationException, GapInSeqNumException {

	}

	/*
	 * Get file list
	 */
	public List<File> getAllFilesFromSourceFolder() throws IOException {
		List<File> allfilesFromSrcDirectory = fileUtil
				.getAllfilesFromSrcDirectory(getConfigurationMapping().getFileInfoDto().getSrcDir());
		log.info("List of files : {}", allfilesFromSrcDirectory.toString());
		return allfilesFromSrcDirectory;
	}

	public List<File> getAllFilesFromSourceFolderITXC() throws IOException {
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
	 * Create acknowledgement for ICTX file
	 */
	public void createAckFile(String ackFileName, String ackFileContent, AckFileInfoDto ackFileInfoDto)
			throws IOException, CustomException {
		log.debug("Creating Ack file {}", ackFileName);

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
			log.info("FileNotFoundException {}", e);
			throw e;
		} catch (IOException e) {
			log.info("IOException {}", e);
			throw e;
		}
	}

	public void updateTranDetails(IctxItxcHeaderInfoVO headerVO) {
		List<TranDetail> tranDetailList = getTranDetail(detailVOList, xferControl, fileNameVO, masterDataCache, headerVO);
		log.info("Inserting detail records into T_TRAN_DETAIL table..");
		saveTransDetail(tranDetailList);
		updateIAFileStatsData(tranDetailList,xferControl);
		if(tranDetailList != null) {
			
			// Pushing into ATP queue
			/*	Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
					.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
					.excludeFieldsWithoutExposeAnnotation().create(); */
			Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
			.registerTypeAdapter(OffsetDateTime.class, new OffsetDateTimeConverter(DateTimeFormatter.ISO_OFFSET_DATE_TIME))
			.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
			.registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
			.excludeFieldsWithoutExposeAnnotation().create();
			log.info("Pushing tranDetailList into ATP queue..");
			try {
				messagePublisher.publishMessagesList(tranDetailList, gson);
			} catch(Exception e) {
				System.out.println(e);
			}
		}
	}
	
	public void saveTransDetail(List<TranDetail> tranDetailList) {
		transDetailDao.batchInsert(tranDetailList);
	}
	
	@SuppressWarnings("unlikely-arg-type")
	public List<TranDetail> getTranDetail(List<IctxItxcDetailInfoVO> detailRecordList, XferControl xferControl,
			IctxItxcNameVO fileNameVO, MasterDataCache masterDataCache, IctxItxcHeaderInfoVO headerVO) {
		List<LaneIdExtLaneInfo> laneIdExtLaneInfoList = masterDataCache.getLaneIdExtLaneList();
		List<Plaza> plazaList = masterDataCache.getAllTPlaza();
		List<AgencyInfoVO> agencyInfoVOList = masterDataCache.getAgencyInfoVOList();
		List<TCodesVO> tCodeVOList = masterDataCache.gettCodeVOList();
		//List<TCodesVO> acctcodeList = masterDataCache.getTcodeList("ACCT_FIN_STATUS");
		
		boolean isCorrectionFile = false;
		try {
			if(headerVO != null) {
				log.info("Setting isCorrectionFile for FileType {}",headerVO.getFileType().trim());
				isCorrectionFile = headerVO.getFileType().trim().equalsIgnoreCase(Constants.ITXC);
			}
		}catch(NullPointerException ex) {
			log.info("FileType Not available");
		}
		Long atpFileId = transDetailDao.checkAtpFileInStatistics("ECTX",xferControl.getXferControlId());
		
		List<TranDetail> tranDetailList = new ArrayList<>();
		//Integer laneID = null;
		long pCount=0; long rCount=0;
		Double rTollAmt=0.0; Double pTollAmt=0.0;
		for (IctxItxcDetailInfoVO detailRecord : detailRecordList) {
			TranDetail tranDetail = new TranDetail();

			// setting as default values
			tranDetail.setTxType("I"); 
			tranDetail.setTxSubType("I");
			tranDetail.setTxStatus(Constants.QCTOL_502);

			//tranDetail.setTxExternRefNo("0000" + detailRecord.getEtcTrxSerialNum());
			tranDetail.setTxExternRefNo(StringUtils.leftPad(detailRecord.getEtcTrxSerialNum(), 20, "0"));
			
			log.info("isCorrectionFile::{}",isCorrectionFile);
			if(isCorrectionFile) {
				tranDetail.setTxSubType("A");
				tranDetail.setCorrReasonId(detailRecord.getEtcCorrReason());
				//will insert in t_tran_detail with same LaneTxId of ICTx while Correcting
				tranDetail.setLaneTxId(transDetailDao.getLaneTxIdFromSerialNo(tranDetail.getTxExternRefNo()));
			}
			
			tranDetail.setPlazaAgencyId(agencyInfoVOList.stream()
					.filter(c -> c.getFilePrefix().equals(String.valueOf(detailRecord.getEtcFacAgency()))).findFirst()
					.map(c -> {
						return c.getAgencyId();
					}).orElse(0));

			/* old logic for exit plazaId and laneId
			log.info("Setting PlazaId for EtcExitPlaza {} and EtcExitLane {}",detailRecord.getEtcExitPlaza(), detailRecord.getEtcExitLane());
			tranDetail.setPlazaId(laneIdExtLaneInfoList.stream()
					.filter(c -> c.getExternLaneId().equals(String.valueOf(detailRecord.getEtcExitLane())) && c.getExternPlazaId().equals(String.valueOf(detailRecord.getEtcExitPlaza()))).findFirst()
					.map(c -> {
						return c.getPlazaId(); 
					}).orElse(0));
			
			log.info("Setting LaneId for EtcExitLane {} and EtcExitPlaza {}",detailRecord.getEtcExitLane(), detailRecord.getEtcExitPlaza());
			tranDetail.setLaneId(Long.valueOf (laneIdExtLaneInfoList.stream()
					.filter(c -> c.getExternLaneId().equals(String.valueOf(detailRecord.getEtcExitLane())) && c.getExternPlazaId().equals(String.valueOf(detailRecord.getEtcExitPlaza()))).findFirst()
					.map(c -> {              
						return c.getLaneId();
					}).orElse(0)));
			*/
			
			log.info("Setting PlazaId for EtcExitPlaza {}", detailRecord.getEtcExitPlaza());
			tranDetail.setPlazaId(plazaList.stream()
					.filter(c -> c.getExternPlazaId().equals(String.valueOf(detailRecord.getEtcExitPlaza().trim()))
							&& c.getAgencyId().intValue() == tranDetail.getPlazaAgencyId().intValue()).findFirst()
					.map(c -> {
						return c.getPlazaId(); 
					}).orElse(0));
			log.info("Exit Plaza Id: {}", tranDetail.getPlazaId());
			
			// Exit Lane that doesn't exist should get inserted in MASTER.T_LANE
			log.info("Setting LaneId for EtcExitLane {} ", detailRecord.getEtcExitLane());
			log.debug("Checking for lane id present in master.T_LANE Table");
			int laneId = masterDataCache.getLaneId(detailRecord.getEtcExitLane() != null ? detailRecord.getEtcExitLane() : null,
					tranDetail.getPlazaId() != null ? tranDetail.getPlazaId() : null);

			if (laneId == 0) {
				tranDetail.setTxType("R");
				tranDetail.setTxSubType("L");
			} else {
				tranDetail.setLaneId(Long.valueOf(laneId));
			}
			
			tranDetail.setExternFileId(xferControl.getXferControlId()); 

			String txTypeInd = "P".equals(detailRecord.getEtcTrxType()) ? "P" : "I";
			tranDetail.setTxType(txTypeInd);
			if ("E".equals(detailRecord.getEtcLaneMode())) {
				tranDetail.setLaneMode(1);
			} else if ("A".equals(detailRecord.getEtcLaneMode())) {
				tranDetail.setLaneMode(2);
			} else if ("M".equals(detailRecord.getEtcLaneMode())) {
				tranDetail.setLaneMode(3);
			} else if ("P".equals(detailRecord.getEtcLaneMode())) {
				tranDetail.setLaneMode(4);
			} else if ("D".equals(detailRecord.getEtcLaneMode())) {
				tranDetail.setLaneMode(5);
			} else if ("O".equals(detailRecord.getEtcLaneMode())) {
				tranDetail.setLaneMode(7);
			} else if ("C".equals(detailRecord.getEtcLaneMode())) {
				tranDetail.setLaneMode(8);
			} else {
				tranDetail.setLaneMode(null);
			}

			tranDetail.setLaneType(0l);
			tranDetail.setLaneState(0);
			tranDetail.setLaneHealth(0l);
			tranDetail.setTourSegment(0l);
			tranDetail.setEntryDataSource("E");

			setEntryLaneIdAndEntryPlazaCal(tranDetail, detailRecord, laneIdExtLaneInfoList, plazaList, tranDetail.getPlazaAgencyId());
			log.info("Entry lane id set as : {}", detailRecord.getEtcEntryLane());
			log.info("Entry plaza id set as : {}", detailRecord.getEtcEntryPlaza());
			
			/* old logic for EtcEntryDateTime
			if ((detailRecord.getEtcEntryDate() != null) && !detailRecord.getEtcEntryDate().startsWith("*")) {
				String edateTime = detailRecord.getEtcEntryDate() + detailRecord.getEtcEntryTime();
				LocalDateTime etxTimeStamp = LocalDateTime.parse(edateTime, DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
				log.info("EntryTxTimeStamp edateTime: {}",etxTimeStamp);
				tranDetail.setEntryTxTimeStamp(timeZoneConv.zonedTxTimeOffset(etxTimeStamp,  Long.valueOf(tranDetail.getPlazaId())));
			} else {
				tranDetail.setEntryTxTimeStamp(null);
			}
			*/
			
			if ((detailRecord.getEtcEntryDateTime() != null) && !detailRecord.getEtcEntryDateTime().startsWith("*")) {
				OffsetDateTime entrytxTimeStamp = OffsetDateTime.parse(detailRecord.getEtcEntryDateTime(), DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssXXX"));
				log.info("EntryTxTimeStamp: {}", entrytxTimeStamp);
				tranDetail.setEntryTxTimeStamp(entrytxTimeStamp);
			} else {
				tranDetail.setEntryTxTimeStamp(null);
			}

			/* old logic for EtcExitDateTime
			if ((detailRecord.getEtcExitDate() != null) && !detailRecord.getEtcExitDate().startsWith("*")) {
				String dateTime = detailRecord.getEtcExitDate() + detailRecord.getEtcExitTime();
				LocalDateTime txTimeStamp = LocalDateTime.parse(dateTime,DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
				//LocalDateTime TxTimeStamp = LocalDateTime.parse(dateTime, formatter1 ) ;

				if(tranDetail.getPlazaId() != null) { 
					tranDetail.setTxTimestamp(timeZoneConv.zonedTxTimeOffset(txTimeStamp,  Long.valueOf(tranDetail.getPlazaId())));
				}
				System.out.println(timeZoneConv.zonedTxTimeOffset(txTimeStamp, Long.valueOf(tranDetail.getPlazaId())));
				tranDetail.setTxDate(detailRecord.getEtcExitDate()); //DateUtils.parseLocalDate(trxDate, "yyyyMMdd")
			} else {
				tranDetail.setTxType("R");
				tranDetail.setTxSubType("T");
				tranDetail.setTxTimestamp(null);
				tranDetail.setTxDate(null);
			}
			*/
			
			if ((detailRecord.getEtcExitDateTime() != null) && !detailRecord.getEtcExitDateTime().startsWith("*")) {
				OffsetDateTime txTimeStamp = OffsetDateTime.parse(detailRecord.getEtcExitDateTime(),DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssXXX"));
				
				if(tranDetail.getPlazaId() != null) { 
					tranDetail.setTxTimestamp(txTimeStamp);
				}
				System.out.println(txTimeStamp);
				tranDetail.setTxDate(txTimeStamp.format(DateTimeFormatter.ofPattern("yyyyMMdd")).toString()); 
				//DateUtils.parseLocalDate(trxDate, "yyyyMMdd")
			} else {
				tranDetail.setTxType("R");
				tranDetail.setTxSubType("T");
				tranDetail.setTxTimestamp(null);
				tranDetail.setTxDate(null);
			}
			
			//tranDetail.setEntryTxSeqNumber(Long.parseLong(detailRecord.getEtcTrxSerialNum()));
			tranDetail.setEntryTxSeqNumber(0l);
			tranDetail.setEntryVehicleSpeed(0);
			tranDetail.setLaneState(0);

			String laneTxType = "P".equals(detailRecord.getEtcTrxType()) ? "P" : "I";
			tranDetail.setTxType(laneTxType);
			tranDetail.setTollRevenueType(1);
			tranDetail.setActualClass(Integer.valueOf(detailRecord.getEtcClassCharged()));
			tranDetail.setActualAxles(Integer.valueOf(detailRecord.getEtcActualAxles()));
			tranDetail.setExtraAxles(0);
			tranDetail.setCollectorNumber("0");
			tranDetail.setIsPeak("F");
			tranDetail.setVehicleSpeed(Integer.valueOf(detailRecord.getEtcExitSpeed()));
			tranDetail.setSpeedViolation(detailRecord.getEtcOverSpeed().equals("Y") ? 1 : 0);

			tranDetail.setDeviceNo(detailRecord.getEtcTagAgency() + detailRecord.getEtcTagSerialNumber());
			tranDetail.setReadPerf(
					(detailRecord.getEtcReadPerformance() != null && !detailRecord.getEtcReadPerformance().startsWith("*"))
							? Integer.valueOf(detailRecord.getEtcReadPerformance())
							: 0);// clarification needed from PB
			tranDetail.setWritePerf((detailRecord.getEtcWritePref() != null && !detailRecord.getEtcWritePref().startsWith("*"))
					? Integer.valueOf(detailRecord.getEtcWritePref())
					: 0);// clarification needed from PB
			
			tranDetail.setProgStat((detailRecord.getEtcTagPgmStatus() != null && !detailRecord.getEtcTagPgmStatus().startsWith("*")) 
					? ProgStat.valueOf(detailRecord.getEtcTagPgmStatus()).ordinal() : 0);
			tranDetail.setDeviceCodedClass(null);
			tranDetail.setDeviceAgencyClass(Long.valueOf(detailRecord.getEtcClassCharged()));
			tranDetail.setDeviceIagClass(null);
			tranDetail.setEtcAccountId(null);
			tranDetail.setAccountType(null);
			tranDetail.setAccountAgencyId(Integer.valueOf(detailRecord.getEtcTagAgency()));
			tranDetail.setBufRead("F");
			tranDetail.setPlanType(null);
			tranDetail.setImageCaptured("F");
			tranDetail.setPlateCountry(null);
			tranDetail.setPlateState((detailRecord.getEtcLicState() != null && !detailRecord.getEtcLicState().startsWith("*"))
					? detailRecord.getEtcLicState()
					: null);
			tranDetail.setPlateNumber((detailRecord.getEtcLicNumber() != null && !detailRecord.getEtcLicNumber().startsWith("*"))
					? detailRecord.getEtcLicNumber()
					: null);
			tranDetail.setPlateType((detailRecord.getEtcLicType() != null && !detailRecord.getEtcLicType().startsWith("*"))
					? detailRecord.getEtcLicType().trim()
					: null);
			tranDetail.setAtpFileId(atpFileId);
			/*tranDetail.setTxStatus(
					(detailRecord.getEtcValidationStatus() != null && !detailRecord.getEtcValidationStatus().startsWith("*"))
							? detailRecord.getEtcValidationStatus()
							: "0");//502 from t_codes */
			
			tranDetail.setReceivedDate(headerVO.getFileDate());
			tranDetail.setUpdateTs(timeZoneConv.currentTime());
			tranDetail.setTollSystemType(detailRecord.getEtcTrxType());
			tranDetail.setCollectorNumber("0");
			tranDetail.setReciprocityTrx("T");
			
			log.info("Setting Toll amount..");
			setDebitCreditAndTollAmount(tranDetail,detailRecord);
			
			tranDetail.setEtcAccountId(null);
			tranDetail.setReciprocityTrx("T");
			tranDetail.setPostedDate(null);
			tranDetail.setDepositId(null);
			
			log.info("Setting Tx Status..");
			setTxStatus(tranDetail, detailRecord, tCodeVOList);
			/*
			if (tranDetail.getDeviceNo() != null) { 
				
				AccountInfo accountInfo = null;

				if (tranDetail.getEtcAccountId() == null
						&& (accountInfo = getAccountInfo("First", masterDataCache, tranDetail)) != null) {
					tranDetail.setEtcAccountId(accountInfo.getEtcAccountId());
				} else if (tranDetail.getEtcAccountId() == null
						&& (accountInfo = getAccountInfo("Second", masterDataCache, tranDetail)) != null) {
					tranDetail.setEtcAccountId(accountInfo.getEtcAccountId());
				} else if (tranDetail.getEtcAccountId() == null
						&& (accountInfo = getAccountInfo("Third", masterDataCache, tranDetail)) != null) {
					tranDetail.setEtcAccountId(accountInfo.getEtcAccountId());
				} else {
					if (accountInfo == null || accountInfo.getIagTagStatus() > 3) { //device status?
						tranDetail.setTxType("R");
						tranDetail.setTxSubType("T");
						//TBD 2 times set
						tranDetail.setTxStatus(
								tCodeVOList.stream().filter(c -> "TAGB".equals(c.getCodeValue())).findFirst().map(c -> {
									return String.valueOf(c.getCodeId());
								}).orElse("0"));
					}
				}
			
				if (tranDetail.getEtcAccountId() != null )//&& accountInfo.getDeviceStatus()<3) 
				{
					log.info("Checking details in FPMS for EtcAccountId: {}",tranDetail.getEtcAccountId());
					AccountApiInfoDto accountApiInfoDto = fpmsAccountApi.callAccountApi(String.valueOf(tranDetail.getEtcAccountId()));
					log.info("AccountApiInfoDto from FPMS: {}",accountApiInfoDto);
					if(accountApiInfoDto != null) {
						tranDetail.setAccountAgencyId(accountApiInfoDto.getAgencyId());
						log.info("accountApiInfoDto recieved from FPMS: {}", accountApiInfoDto.toString());
						for (TCodesVO tCodesVOObj : acctcodeList) {
							int codeId = 0;
							if(tCodesVOObj.getCodeValue().equals(accountApiInfoDto.getAcctFinStatus()))
								codeId = tCodesVOObj.getCodeId();
							if(codeId==1 || codeId==2 || codeId==3) {
								tranDetail.setTxType("I");
								tranDetail.setTxSubType("I");
								if(isCorrectionFile) {
									tranDetail.setTxSubType("A");
								}
							}
						}
					}
					
					// V_ETC_ACCOUNT
					accountInfo = masterDataCache.getAccountInfo(tranDetail);
					if (accountInfo == null) { 
						tranDetail.setTxType("R");
						tranDetail.setTxSubType("T");
					} else {
						if ("Y".equals(accountInfo.getUnregistered())) {
							if ("Y".equals(masterDataCache.getUnresgisteredStaus(tranDetail))) {
								tranDetail.setTxType("R");
								tranDetail.setTxSubType("T");
							}
						}
					}
				}
			}
			*/
			if("R".equals(tranDetail.getTxType()) && "T".equals(tranDetail.getTxSubType())) {
				rCount++;
				rTollAmt += tranDetail.getPostedFareAmount();
			}else if("I".equals(tranDetail.getTxType()) && ("I".equals(tranDetail.getTxSubType()) || "A".equals(tranDetail.getTxSubType()))){//Add it for A as well as its for correction subtype
				pCount++;
				pTollAmt += tranDetail.getPostedFareAmount();
			}else {
				// added rTollAmt increment
				rTollAmt += tranDetail.getPostedFareAmount();
				rCount++;
			}
			tranDetail.setTxTypeInd(tranDetail.getTxType());
			tranDetail.setTxSubtypeInd(tranDetail.getTxSubType());
			tranDetailList.add(tranDetail);

		}
		setPostCount(pCount); setRejctCount(rCount);
		setPostTollAmt(pTollAmt); setRejctTollAmt(rTollAmt);
		return tranDetailList;
	}
	
	private void setTxStatus(TranDetail tranDetailtemp, IctxItxcDetailInfoVO detailRecord,List<TCodesVO> tCodeVOList) {
		
		if (tranDetailtemp.getDeviceNo() != null) {
			AccountInfo accountInfo = null;
			int deviceStatus = 0;
			TollPostLimitInfo tollPostLimitInfo = null;
			if (tranDetailtemp.getEtcAccountId() == null && (accountInfo = getAccountInfo("First", masterDataCache, tranDetailtemp)) != null) {
				// GET_ETC_ACCOUNT_V_DEVICE
				tranDetailtemp.setEtcAccountId(accountInfo.getEtcAccountId());
				deviceStatus = accountInfo.getDeviceStatus();
				log.info("Account info from V_Device: {}", accountInfo.toString());
			} else if (tranDetailtemp.getEtcAccountId() == null && (accountInfo = getAccountInfo("Second", masterDataCache, tranDetailtemp)) != null) {
				// GET_ETC_ACCOUNT_V_H_DEVICE
				tranDetailtemp.setEtcAccountId(accountInfo.getEtcAccountId());
				deviceStatus = accountInfo.getDeviceStatus();
				log.info("Account info from V_H_Device: {}", accountInfo.toString());
			} else if (tranDetailtemp.getEtcAccountId() == null && (accountInfo = getAccountInfo("Third", masterDataCache, tranDetailtemp)) != null) {
				// GET_ETC_ACCOUNT_T_HA_DEVICES
				tranDetailtemp.setEtcAccountId(accountInfo.getEtcAccountId());
				deviceStatus = accountInfo.getDeviceStatus();
				log.info("Account info from T_HA_Devices: {}", accountInfo.toString());
			}
			
			if (accountInfo != null) {
				tranDetailtemp.setDeviceNoFromDb(accountInfo.getDeviceNo());
			}
			
			// AccountApiInfoDto accountApiInfoDto = null;
			if (tranDetailtemp.getEtcAccountId() != null) {
				//log.info("Checking details in FPMS for EtcAccountId: {}", tranDetailtemp.getEtcAccountId());
				//accountApiInfoDto = fpmsAccountApi.callAccountApi(String.valueOf(tranDetailtemp.getEtcAccountId()));
				//log.info("AccountApiInfoDto from FPMS: {}", accountApiInfoDto);
				
				// Get AccountInfo from CRM.V_ETC_ACCOUNT
				accountInfo = masterDataCache.getAccountInfo(tranDetailtemp);
				
				if (accountInfo == null) {
					log.info("No reecord found in CRM.V_ETC_ACCOUNT for ETC_ACCOUNT_ID: {}", tranDetailtemp.getEtcAccountId());
					tranDetailtemp.setTxType("I");
					tranDetailtemp.setTxSubType("C");
					tranDetailtemp.setTxStatus(
							tCodeVOList.stream().filter(c -> Constants.RINV.equals(c.getCodeValue())).findFirst().map(c -> {
								return String.valueOf(c.getCodeId());
							}).orElse("0"));
					return;
				}
				
				//Get IAG_TAG_STATUS from T_HA_DEVICES
				String deviceNo = tranDetailtemp.getDeviceNo();
				String deviceNo11 = getDeviceNo11(deviceNo);
				Integer iagTagStatus = masterDataCache.getHaDevicesIagTagStatus(deviceNo, deviceNo11, tranDetailtemp.getTxDate());
				log.info("iagTagStatus from T_HA_DEVICES: {}", iagTagStatus);
				
				if (accountInfo != null) {
					accountInfo.setDeviceStatus(deviceStatus);
					accountInfo.setIagTagStatus(iagTagStatus);
				}
				log.info("AccountInfo from CRM.V_ETC_ACCOUNT: {}", accountInfo.toString());
				
				if (detailRecord.getEtcLicNumber() == null || detailRecord.getEtcLicNumber().equals("**********")) {
				    // Tag TX
					tollPostLimitInfo = masterDataCache.getTollPostLimit(tranDetailtemp.getPlazaAgencyId(), 7);
				} else {
					// Image TX
					tollPostLimitInfo = masterDataCache.getTollPostLimit(tranDetailtemp.getPlazaAgencyId(), 6);
				}
				log.info("TollPostLimitInfo from MASTER.T_TOLL_POST_LIMIT: {}", tollPostLimitInfo.toString());
				
				// TX_STATUS 206
				// TX_STATUS 204
				if (accountInfo.getDeviceStatus() != 3) {
					tranDetailtemp.setTxType("I");
					tranDetailtemp.setTxSubType("C");
					if (accountInfo.getIagTagStatus().intValue() == 3) {
						tranDetailtemp.setTxStatus(
								tCodeVOList.stream().filter(c -> Constants.TAGB.equals(c.getCodeValue())).findFirst().map(c -> {
									return String.valueOf(c.getCodeId());
								}).orElse("0"));
					} else {
						tranDetailtemp.setTxStatus(
								tCodeVOList.stream().filter(c -> Constants.NPST.equals(c.getCodeValue())).findFirst().map(c -> {
									return String.valueOf(c.getCodeId());
								}).orElse("0"));
					}
					return;
				}
				
				// TX_STATUS 207
				// TX_STATUS 204
				if (accountInfo.getAcctActStatus() != 3) {
					tranDetailtemp.setTxType("I");
					tranDetailtemp.setTxSubType("C");
					if (accountInfo.getIagTagStatus().intValue() == 3 || "Y".equals(accountInfo.getAccountSuspended()) 
							|| "Y".equals(accountInfo.getUnregistered())) {
						tranDetailtemp.setTxStatus(
								tCodeVOList.stream().filter(c -> Constants.ACCB.equals(c.getCodeValue())).findFirst().map(c -> {
									return String.valueOf(c.getCodeId());
								}).orElse("0"));
					} else {
						tranDetailtemp.setTxStatus(
								tCodeVOList.stream().filter(c -> Constants.NPST.equals(c.getCodeValue())).findFirst().map(c -> {
									return String.valueOf(c.getCodeId());
								}).orElse("0"));
					}
					return;
				}
				
				// TX_STATUS 205
				if (accountInfo.getCurrentBalance().doubleValue() <= ICTXConstants.MIN_ACCOUNT_BALANCE_THRESHOLD 
						|| accountInfo.getIagTagStatus() == 3) {
					tranDetailtemp.setTxType("I");
					tranDetailtemp.setTxSubType("C");
					tranDetailtemp.setTxStatus(
							tCodeVOList.stream().filter(c -> Constants.INSU.equals(c.getCodeValue())).findFirst().map(c -> {
								return String.valueOf(c.getCodeId());
							}).orElse("0"));
					return;
				}
				
				// TX_STATUS 209
				// TX_STATUS 210
				long diff = ChronoUnit.DAYS.between(timeZoneConv.currentDate(), tranDetailtemp.getTxTimestamp());
				log.info("diff in days: {}, currentTime: {}, TxTimestamp: {}", Math.abs(diff), timeZoneConv.currentDate(), tranDetailtemp.getTxTimestamp());
				if (Math.abs(diff) > tollPostLimitInfo.getAllowedDays() 
						&& timeZoneConv.currentTime().isAfter(tranDetailtemp.getTxTimestamp().toLocalDateTime())) {
					
					tranDetailtemp.setTxType("I");
					tranDetailtemp.setTxSubType("C");
					
					// plate number doesn't exist
					if (detailRecord.getEtcLicNumber() == null 
							|| detailRecord.getEtcLicNumber().isBlank() 
							|| detailRecord.getEtcLicNumber().isEmpty() 
							|| detailRecord.getEtcLicNumber().equals("**********")) {
						tranDetailtemp.setTxStatus(
								tCodeVOList.stream().filter(c -> Constants.OLD2.equals(c.getCodeValue())).findFirst().map(c -> {
									return String.valueOf(c.getCodeId());
								}).orElse("0"));
					// plate number exist
					} else {
						tranDetailtemp.setTxStatus(
								tCodeVOList.stream().filter(c -> Constants.OLD1.equals(c.getCodeValue())).findFirst().map(c -> {
									return String.valueOf(c.getCodeId());
								}).orElse("0"));
					}
					return;
				}
				
			}
			
			// TX_STATUS 211
			if (accountInfo == null || accountInfo.getEtcAccountId() == null 
					|| accountInfo.getEtcAccountId().intValue() == 0 
					|| tranDetailtemp.getActualClass().intValue() == 0 
					|| tranDetailtemp.getPlazaAgencyId().intValue() == 0 
					|| tranDetailtemp.getPlazaId().intValue() == 0) {
				tranDetailtemp.setTxType("I");
				tranDetailtemp.setTxSubType("C");
				tranDetailtemp.setTxStatus(
						tCodeVOList.stream().filter(c -> Constants.RINV.equals(c.getCodeValue())).findFirst().map(c -> {
							return String.valueOf(c.getCodeId());
						}).orElse("0"));
				return;
			}
		}
	}
	
	private void setDebitCreditAndTollAmount(TranDetail tranDetailtemp, IctxItxcDetailInfoVO detailRecord) {
		String debitCredit = null;
		Double amount = 0.0;
		log.info("EtcDebitCredit from file is: {}",detailRecord.getEtcDebitCredit());
		if (Constants.PLUS.equalsIgnoreCase(detailRecord.getEtcDebitCredit())
				|| (Constants.SPACE.equalsIgnoreCase(detailRecord.getEtcDebitCredit()))) {
			debitCredit = Constants.PLUS;
		} else if (Constants.MINUS.equalsIgnoreCase(detailRecord.getEtcDebitCredit())) {
			debitCredit = Constants.MINUS;
		} else {
			debitCredit = Constants.PLUS;
		}
		tranDetailtemp.setDebitCredit(debitCredit);
		
		log.info("Setting amount for EtcTollAmount(): {}",detailRecord.getEtcTollAmount());
		if(detailRecord.getEtcTollAmount() != null) {
			/*
			 * if(debitCredit != null && Constants.MINUS.equalsIgnoreCase(debitCredit) &&
			 * StringUtils.isNotEmpty(detailRecord.getEtcTollAmount())) {
			 * 
			 * amount = (Double.valueOf(detailRecord.getEtcTollAmount()) / 100)*(-1); }else
			 * { amount = (Double.valueOf(detailRecord.getEtcTollAmount()) / 100)*(+1); }
			 */ 
			//Commenting above code as we have to set only Mod of value 
			amount = (Double.valueOf(detailRecord.getEtcTollAmount()) / 100);
		}
		
		if (detailRecord.getEtcLicNumber() == null || detailRecord.getEtcLicNumber().equals("**********")) {
			// It is a tag transaction
			tranDetailtemp.setEtcFareAmount(amount);
			tranDetailtemp.setVideoFareAmount(0.00d);
			tranDetailtemp.setTxSubType("C");
		} else {
			// It is a image transaction
			tranDetailtemp.setEtcFareAmount(0.00d);
			tranDetailtemp.setVideoFareAmount(amount);
		}
		
		tranDetailtemp.setPostedFareAmount(amount);
		tranDetailtemp.setExpectedRevenueAmount(amount);		
		tranDetailtemp.setDiscountedAmount(amount);
		tranDetailtemp.setTollAmount(amount);
		
		tranDetailtemp.setCashFareAmount(0.00d);
		tranDetailtemp.setFullFareAmount(0.00d);
		tranDetailtemp.setUnrealizedAmount(0.00d);
		tranDetailtemp.setDiscountedAmount2(0.00d);
	}
	
	private AccountInfo getAccountInfo(String str, MasterDataCache masterDataCache, TranDetail tranDetail) {
		AccountInfo accInfo = null;
		if ("First".equals(str)) {
			// V_DEVICE
			String deviceNo = tranDetail.getDeviceNo();
			String deviceNo11 = getDeviceNo11(deviceNo);
			accInfo = masterDataCache.getAccountInfoOne(deviceNo, deviceNo11, tranDetail.getTxTimestamp());
		} else if ("Second".equals(str)) {
			// V_H_DEVICE
			String deviceNo = tranDetail.getDeviceNo();
			String deviceNo11 = getDeviceNo11(deviceNo);
			accInfo = masterDataCache.getAccountInfoTwo(deviceNo, deviceNo11, tranDetail.getTxTimestamp());
		} else if ("Third".equals(str)) {
			// T_HA_DEVICES
			String deviceNo = tranDetail.getDeviceNo();
			String deviceNo11 = getDeviceNo11(deviceNo);
			accInfo = masterDataCache.getAccountInfoThree(deviceNo, deviceNo11, tranDetail.getTxDate());
		}

		return accInfo;
	}
	
	public String getDeviceNo11(String deviceNo) {
		String deviceNo11char = null;
		String a = String.valueOf(deviceNo.charAt(4));
		String b = String.valueOf(deviceNo.charAt(5));
		if (a.equals("0") && b.equals("0")) {
			log.info("check for 11 & 14 digit device a: {}, b: {}", a, b);
			deviceNo11char = deviceNo.substring(1, 4) + deviceNo.substring(6, 14);
			log.info("Device No 14: {} and Device No 11: {}", deviceNo, deviceNo11char);
		}
		return deviceNo11char;
	}
	
	private void setEntryLaneIdAndEntryPlazaCal(TranDetail tranDetail, IctxItxcDetailInfoVO detailRecord, List<LaneIdExtLaneInfo> laneIdExtLaneInfoList, List<Plaza> plazaList, Integer plazaAgencyId) {
		log.info("Setting Entry Plaza Id for EtcEntryPlaza {}",detailRecord.getEtcEntryPlaza());
		if ("***".equals(detailRecord.getEtcEntryPlaza())) {
			tranDetail.setEntryPlazaId(0);
		} else {
			tranDetail.setEntryPlazaId(plazaList.stream()
					.filter(c -> c.getExternPlazaId().equals(String.valueOf(detailRecord.getEtcEntryPlaza().trim()))
							&& c.getAgencyId().intValue() == plazaAgencyId.intValue()).findFirst()
					.map(c -> {
						return c.getPlazaId(); 
					}).orElse(0));
		}
		
		// Entry Lane that doesn't exist should get inserted in MASTER.T_LANE
		if ("***".equals(detailRecord.getEtcEntryLane())) {
			tranDetail.setEntryLaneId(0);
		} else {
			log.info("Setting LaneId for EtcEntryLane {} ", detailRecord.getEtcEntryLane());
			log.debug("Checking for lane id present in master.T_LANE Table");
			int laneId = masterDataCache.getLaneId(detailRecord.getEtcEntryLane() != null ? detailRecord.getEtcEntryLane() : null, 
					tranDetail.getEntryPlazaId() != null ? tranDetail.getEntryPlazaId() : null);
			if (laneId == 0) {
				tranDetail.setTxType("R");
				tranDetail.setTxSubType("L");
			} else {
				tranDetail.setEntryLaneId(laneId);
			}
		}
		
		/* old logic 
		if ("***".equals(detailRecord.getEtcEntryLane())) {
			tranDetail.setEntryLaneId(0);
		} else {
			tranDetail.setEntryLaneId(laneIdExtLaneInfoList.stream()
					.filter(c -> c.getExternLaneId().equals(detailRecord.getEtcEntryLane()) && c.getExternPlazaId().equals(detailRecord.getEtcEntryPlaza())).findFirst()
					.map(c -> {              
						return c.getLaneId();
					}).orElse(0));
		}
		
		log.info("Setting Entry Plaza Id for EtcEntryPlaza {} and EtcEntryLane {}",detailRecord.getEtcEntryPlaza(), detailRecord.getEtcEntryLane());
		if ("***".equals(detailRecord.getEtcEntryPlaza())) {
			tranDetail.setEntryPlazaId(0);
		} else {
			tranDetail.setEntryPlazaId(laneIdExtLaneInfoList.stream()
					.filter(c -> c.getExternLaneId().equals(detailRecord.getEtcEntryLane()) && c.getExternPlazaId().equals(detailRecord.getEtcEntryPlaza())).findFirst()
					.map(c -> {
						return c.getPlazaId(); 
					}).orElse(0));
		}*/	
	}
	
	long rejctCount = 0;
	long postCount = 0; 
	Double rejctTollAmt=0.0;
	Double postTollAmt=0.0;
	
	public Double getRejctTollAmt() {
		return rejctTollAmt;
	}
	public void setRejctTollAmt(Double rejctTollAmt) {
		this.rejctTollAmt = rejctTollAmt;
	}
	public Double getPostTollAmt() {
		return postTollAmt;
	}
	public void setPostTollAmt(Double postTollAmt) {
		this.postTollAmt = postTollAmt;
	}
	public long getRejctCount() {
		return rejctCount;
	}
	public void setRejctCount(long rejctCount) {
		this.rejctCount = rejctCount;
	}
	public long getPostCount() {
		return postCount;
	}
	public void setPostCount(long postCount) {
		this.postCount = postCount;
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

	public void insertQATPStatisticsData(XferControl xferControl) {
		// TODO Auto-generated method stub

	}

	public void updateQATPStatisticsData(XferControl xferControl, long postCount, long rejctCount, long recCount,
			Double postTollAmt, Double rejctTollAmt) {
		// TODO Auto-generated method stub

	}
	
	public void updateIAFileStatsData(List<TranDetail> tranDetailList,XferControl xferControl) {
//		
		
	}

}
