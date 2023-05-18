package com.conduent.tpms.iag.validation;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.conduent.tpms.iag.config.LoadJpaQueries;
import com.conduent.tpms.iag.constants.Constants;
import com.conduent.tpms.iag.constants.IAGConstants;
import com.conduent.tpms.iag.dao.TIagDao;
import com.conduent.tpms.iag.dao.TIagMappingDao;
import com.conduent.tpms.iag.dto.AckFileInfoDto;
import com.conduent.tpms.iag.dto.AckFileWrapper;
import com.conduent.tpms.iag.dto.AgencyInfoVO;
import com.conduent.tpms.iag.dto.FileParserParameters;
import com.conduent.tpms.iag.dto.IagFileNameInfo;
import com.conduent.tpms.iag.dto.MappingInfoDto;
import com.conduent.tpms.iag.dto.TFOFileStats;
import com.conduent.tpms.iag.exception.CustomException;
import com.conduent.tpms.iag.exception.EmptyLineException;
import com.conduent.tpms.iag.exception.InvalidFileNameException;
import com.conduent.tpms.iag.exception.InvlidFileDetailException;
import com.conduent.tpms.iag.model.XferControl;
import com.conduent.tpms.iag.utility.GenericValidation;
import com.conduent.tpms.iag.utility.MasterDataCache;

public class FileParserImpl {

	private static final Logger logger = LoggerFactory.getLogger(FileParserImpl.class);
	protected IagFileNameInfo fileNameVO;
	protected boolean isDetailsPresentInFile;

	@Autowired
	protected TIagDao tIagDao;

	@Autowired
	protected TIagMappingDao tIagMappingDao;

	@Autowired
	private TIagDao qatpDao;

	@Autowired
	protected GenericValidation genericValidation;

	@Autowired
	private TextFileReader textFileReader;

	@Autowired
	protected MasterDataCache masterDataCache;

	@Autowired
	private NamedParameterJdbcTemplate namedJdbcTemplate;

	private FileUtil fileUtil = new FileUtil();
	private String fileType;
	private String fileFormat;
	private String agencyId;
	private String fileName;
	protected XferControl xferControl;
	protected boolean isFirstRead = true;

	private FileParserParameters configurationMapping;
	protected FileParserParameters fileParserParameter = new FileParserParameters();

	public void initialize() {
	}

	public void validateAndProcessFileName(String fileName)
			throws InvalidFileNameException, EmptyLineException, InvlidFileDetailException {
		logger.info("Validate And process file name {}", fileName);
		parseAndValidate(fileName, configurationMapping.getFileNameMappingInfo());
	}

	public void validateAndProcessLongFileName(String fileName)
			throws InvalidFileNameException, EmptyLineException, InvlidFileDetailException {
		logger.info("Validate And process file name {}", fileName);
		parseAndValidate(fileName, configurationMapping.getLongFileNameMappingInfo());
	}

	public void parseAndValidateDetails(String line)
			throws InvalidFileNameException, EmptyLineException, InvlidFileDetailException {
		parseAndValidate(line, configurationMapping.getDetailRecMappingInfo());
	}

	public void parseAndValidateLongDetails(String line)
			throws InvalidFileNameException, EmptyLineException, InvlidFileDetailException {
		parseAndValidate(line, configurationMapping.getLongdetailRecMappingInfo());
	}

	public void loadConfigurationMapping() {
		logger.info("Start load configuration mapping...");
		setConfigurationMapping(tIagMappingDao.getMappingConfigurationDetails(getFileParserParameter()));
	}

	public void doFieldMapping(String value, MappingInfoDto fMapping) throws InvlidFileDetailException {
	}

	public void parseAndValidate(String line, List<MappingInfoDto> mappings)
			throws InvalidFileNameException, EmptyLineException, InvlidFileDetailException {
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

				if (fieldMapping.getFieldType().equals("2AGFILENAME")
						&& fieldMapping.getFieldName().equals("F_FROM_AGENCY_ID")
						|| fieldMapping.getFieldName().equals("F_TO_AGENCY_ID")) {
					AgencyInfoVO checkAgencgIDValid = masterDataCache.getAgencyId(value);
					if (checkAgencgIDValid == null) {
						logger.info("Agency Id not Present for file Record", value);
						isValid = false;
						throw new InvalidFileNameException("Invalid File Name format");
					}
				}

				if (fieldMapping.getFieldType().equals("3AGFILENAME")
						&& fieldMapping.getFieldName().equals("F_FROM_AGENCY_ID")
						|| fieldMapping.getFieldName().equals("F_TO_AGENCY_ID")
						|| fieldMapping.getFieldName().equals("F_FROM_TO_AGENCY_ID")) {
					AgencyInfoVO checkAgencgIDValid = masterDataCache.getAgencyId(value);
					if (checkAgencgIDValid == null) {
						logger.info("Agency Id not Present for long file Record", value);
						isValid = false;
						throw new InvalidFileNameException("Invalid File Name format");
					}
				}

				if (fieldMapping.getFieldType().equals("2AGDETAIL")
						&& fieldMapping.getFieldName().equals("D_FROM_AGENCY_ID")
						|| fieldMapping.getFieldName().equals("D_TO_AGENCY_ID")
						|| fieldMapping.getFieldName().equals("D_FILE_FROM_AGENCY_ID")) {

					AgencyInfoVO checkAgencgIDValid = masterDataCache.getAgencyId(value);
					if (checkAgencgIDValid == null) {
						logger.info("Agency Id not Present for Detaild Record", value);
						isValid = false;
						throw new InvlidFileDetailException("Invalid File Detail format");
					}
				}

				if (fieldMapping.getFieldType().equals("3AGDETAIL")
						&& fieldMapping.getFieldName().equals("D_FROM_AGENCY_ID")
						|| fieldMapping.getFieldName().equals("D_TO_AGENCY_ID")
						|| fieldMapping.getFieldName().equals("D_FILE_FROM_AGENCY_ID")
						|| fieldMapping.getFieldName().equals("D_FILE_TO_AGENCY_ID")) {

					AgencyInfoVO checkAgencgIDValid = masterDataCache.getAgencyId(value);
					if (checkAgencgIDValid == null) {
						logger.info("Agency Id not Present for Detaild Record", value);
						isValid = false;
						throw new InvlidFileDetailException("Invalid File Detail format");
					}
				}

			} catch (Exception ex) {
				logger.info("Exception {}", ex.getMessage());
			}

			logger.info("Field Mapping validation {} value {}, mapping {}", isValid, value, fieldMapping.toString());
			doFieldMapping(value, fieldMapping);
			if (!isValid) {
				if (fieldMapping.getFieldType().equals("2AGFILENAME")
						|| fieldMapping.getFieldType().equals("3AGFILENAME")) {
					throw new InvalidFileNameException("Invalid File Name format");
				}

				if (fieldMapping.getFieldType().equals("2AGDETAIL")
						|| fieldMapping.getFieldType().equals("3AGDETAIL")) {
					throw new InvlidFileDetailException("Invalid File Detail format");
				}

			}
			if (fieldMapping.getFieldType().equals("2AGDETAIL") || fieldMapping.getFieldType().equals("3AGDETAIL")) {
				isFirstRead = false;
			}
		}

		if (!isFirstRead) {
			processEndOfLine(mappings.get(0).getFieldType());
			isFirstRead = true;
		}

	}

	public void handleException(String fileSection) throws CustomException {

	}

	public void processEndOfLine(String fileSection) {

	}

	public void processStartOfLine(String fileSection) {

	}

	/**
	 * 
	 * @throws IOException
	 * @throws Exception
	 */
	public void fileParseTemplate() throws IOException {
		initialize();
		loadConfigurationMapping();
		List<File> allfilesFromSrcFolder = getAllFilesFromSourceFolder();
		if (allfilesFromSrcFolder != null && allfilesFromSrcFolder.size() > 0) {
			logger.info("Total {} files selected for process", allfilesFromSrcFolder.size());
			for (File file : allfilesFromSrcFolder) {
				try {
					logger.info("File name {} taken for process", file.getName());
					fileName = file.getName();
					// validate if file present in ACk Table
					AckFileInfoDto isAckFilePresent = tIagDao.getAckFileNameInfo(fileName);
					XferControl isXferFilePresent = null;
					TFOFileStats tFOFilePresent = null;

					if (fileName.substring(23, 27).equals(Constants.FNTX_FILE_NAME)
							|| fileName.substring(23, 27).equals(Constants.FTXN_FILE_NAME)
							|| fileName.substring(23, 27).equals(Constants.FNDX_FILE_NAME)
							|| fileName.substring(23, 27).equals(Constants.FDXN_FILE_NAME)
							|| fileName.substring(23, 27).equals(Constants.FNRX_FILE_NAME)
							|| fileName.substring(23, 27).equals(Constants.FRXN_FILE_NAME)) {
						isXferFilePresent = tIagDao.getXferControlInfo(fileName.substring(0, 27).replace("_F", ".F"));// (4,27)

						tFOFilePresent = tIagDao.getFOFileStatsInfo(fileName.substring(0, 27).replace("_F", ".F"));

					} else if (fileName.substring(23, 27).equals(Constants.INTX_FILE_NAME)
							|| fileName.substring(23, 27).equals(Constants.ITXN_FILE_NAME)
							|| fileName.substring(23, 27).equals(Constants.INRX_FILE_NAME)
							|| fileName.substring(23, 27).equals(Constants.IRXN_FILE_NAME)) {
						isXferFilePresent = tIagDao.getXferControlInfo(fileName.substring(0, 27).replace("_I", ".I"));

						tFOFilePresent = tIagDao.getFOFileStatsInfo(fileName.substring(0, 27).replace("_I", ".I"));

					}

					if (isAckFilePresent != null) {
						validateAndProcessFile(file);// Is it relevant?
						processSucess(file);
					} else if (fileName.substring(23, 27).equals(Constants.FNTX_FILE_NAME)
							|| fileName.substring(23, 27).equals(Constants.FTXN_FILE_NAME)
							|| fileName.substring(23, 27).equals(Constants.FNDX_FILE_NAME)
							|| fileName.substring(23, 27).equals(Constants.FDXN_FILE_NAME)
							|| fileName.substring(23, 27).equals(Constants.FNRX_FILE_NAME)
							|| fileName.substring(23, 27).equals(Constants.FRXN_FILE_NAME)
							|| fileName.substring(23, 27).equals(Constants.INTX_FILE_NAME)
							|| fileName.substring(23, 27).equals(Constants.ITXN_FILE_NAME)
							|| fileName.substring(23, 27).equals(Constants.INRX_FILE_NAME)
							|| fileName.substring(23, 27).equals(Constants.IRXN_FILE_NAME)) {

						if (isXferFilePresent != null) {
							validateAndProcessFile(file);
							processSucess(file);
						} else {
							throw new InvalidFileNameException("Invalid File format");
						}
					} else {

						if (tFOFilePresent != null) {
							validateAndProcessFile(file);
							processSucess(file);
						} else {
							throw new InvalidFileNameException("Invalid File format");
						}
					}
				} catch (Exception ex) {
					ex.printStackTrace();
					textFileReader.closeFile();

					if (ex instanceof InvalidFileNameException) {
						AckFileWrapper ackObj = prePareAckFileMetaData(IAGConstants.INVALID_FILE, file);

						processAckFile(ackObj, file);
					} else {
						AckFileWrapper ackObj = prePareAckFileMetaData(IAGConstants.INVALID_DETAIL, file);

						processAckFile(ackObj, file);
					}

				}
			}
		}
		logger.info("No file found in directory {}", getConfigurationMapping().getFileInfoDto().getSrcDir());
	}

	public AckFileWrapper prePareAckFileMetaData(String StatusCode, File file) {
		return null;
	}

	public void processSucess(File file) {

		AckFileWrapper ackObj = prePareAckFileMetaData(IAGConstants.SUCCESS, file);

		processAckFile(ackObj, file);

	}

	@SuppressWarnings("unused")
	public void processAckFile(AckFileWrapper ackObj, File file) {

		String query = LoadJpaQueries.getQueryById("GET_FILE_EXISTS_OR_NOT");

		MapSqlParameterSource paramSource = null;

		paramSource = new MapSqlParameterSource();
		paramSource.addValue(Constants.ACK_FILE_NAME, file.getName());

		@SuppressWarnings("unchecked")
		AckFileWrapper file_exists;
		try {
			file_exists = namedJdbcTemplate.queryForObject(query, paramSource,
					BeanPropertyRowMapper.newInstance(AckFileWrapper.class));

			if (file_exists != null && ackObj.getAckFileInfoDto() != null) {
				qatpDao.updateAckStatus(ackObj.getAckFileInfoDto());
				logger.info("File updated successfully");
			}

		} catch (DataAccessException e1) {
			if (ackObj.getAckFileInfoDto() != null) {
				qatpDao.insertAckStatus(ackObj.getAckFileInfoDto());
				logger.info("File inserted successfully");
			}
		}

		try {
			transferFile(ackObj.getFile(), ackObj.getFileDestDir());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.getMessage();
		}

	}

	public void validateAndProcessFile(File file) throws Exception {
		fileName = file.getName();
		Integer fileLength = fileName.length();
		if (fileLength == 35) {
			validateAndProcessLongFileName(file.getName());
		} else if (fileLength == 31) {
			validateAndProcessFileName(file.getName());
		} else {
			logger.info("Invalid File Length");
			throw new InvalidFileNameException("Invalid File format");
		}
		textFileReader.openFile(file);
		String prviousLine = textFileReader.readLine();
		String currentLine = textFileReader.getlineBreak(file);
		String FinalLine = prviousLine + currentLine;
		if (prviousLine == null) {
			// File is Empty.
		}

		if (prviousLine != null && !currentLine.isEmpty() && (prviousLine.replaceAll(" ", "").length() == 48)) {
			parseValidateAndProcessDetail(FinalLine);
		} else {
			parseAndValidateLongDetails(FinalLine);
		}
		textFileReader.closeFile();

		// closing file second time
		textFileReader.closeFile();
	}

	public void parseValidateAndProcessDetail(String prviousLine)
			throws InvalidFileNameException, EmptyLineException, InvlidFileDetailException {
		parseAndValidateDetails(prviousLine);
		processDetailLine();
	}

	public void parseValidateAndProcessLongDetail(String prviousLine)
			throws InvalidFileNameException, EmptyLineException, InvlidFileDetailException {
		parseAndValidateLongDetails(prviousLine);
		processDetailLine();
	}

	public void processDetailLine() {

	}

	public List<File> getAllFilesFromSourceFolder() throws IOException {
		// List<File> allfilesFromSrcDirectory =
		// fileUtil.getAllfilesFromSrcDirectory(getConfigurationMapping().getFileInfoDto().getSrcDir(),fileType);
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

	public Boolean getIsDetailsPresentInFile() {
		return isDetailsPresentInFile;
	}

	public void setIsDetailsPresentInFile(Boolean isDetailsPresentInFile) {
		this.isDetailsPresentInFile = isDetailsPresentInFile;
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
}