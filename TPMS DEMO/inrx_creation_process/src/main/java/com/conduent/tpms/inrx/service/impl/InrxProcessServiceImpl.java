package com.conduent.tpms.inrx.service.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.conduent.tpms.inrx.constants.Constants;
import com.conduent.tpms.inrx.dao.INRXDao;
import com.conduent.tpms.inrx.model.Agency;
import com.conduent.tpms.inrx.model.FileCreationParameters;
import com.conduent.tpms.inrx.model.FileStats;
import com.conduent.tpms.inrx.model.MappingInfoDto;
import com.conduent.tpms.inrx.model.TxDetailRecord;
import com.conduent.tpms.inrx.service.InrxProcessService;
import com.conduent.tpms.inrx.utility.FileWriteOperation;
import com.conduent.tpms.inrx.utility.StaticDataLoad;

/**
 * INRX Process Service Implementation
 * 
 *@author petetid
 */
@Service
public class InrxProcessServiceImpl implements InrxProcessService {

	private static final Logger log = LoggerFactory.getLogger(InrxProcessServiceImpl.class);

	@Autowired
	StaticDataLoad staticDataLoad;

	@Autowired
	INRXDao inrxDao;

	@Autowired
	public FileWriteOperation fileWriteOperation;

	protected boolean isHederPresentInFile;
	protected boolean isDetailsPresentInFile;
	protected boolean isTrailerPresentInFile;
	private FileCreationParameters configurationMapping = new FileCreationParameters();
	private FileCreationParameters fileCreationParameter = new FileCreationParameters();

	public FileCreationParameters getConfigurationMapping() {
		return configurationMapping;
	}

	public void setConfigurationMapping(FileCreationParameters configurationMapping) {
		this.configurationMapping = configurationMapping;
	}

	public FileCreationParameters getFileCreationParameter() {
		return fileCreationParameter;
	}

	public void setFileCreationParameter(FileCreationParameters fileCreationParameter) {
		this.fileCreationParameter = fileCreationParameter;
	}

	public boolean isHederPresentInFile() {
		return isHederPresentInFile;
	}

	public void setIsHederPresentInFile(boolean isHederPresentInFile) {
		this.isHederPresentInFile = isHederPresentInFile;
	}

	public boolean isDetailsPresentInFile() {
		return isDetailsPresentInFile;
	}

	public void setIsDetailsPresentInFile(boolean isDetailsPresentInFile) {
		this.isDetailsPresentInFile = isDetailsPresentInFile;
	}

	public boolean isTrailerPresentInFile() {
		return isTrailerPresentInFile;
	}

	public void setIsTrailerPresentInFile(boolean isTrailerPresentInFile) {
		this.isTrailerPresentInFile = isTrailerPresentInFile;
	}

	/*
	 * To initialize basic TAG file properties
	 */
	public void initialize() {
		loadOutputConfigurationMapping();
	}

	// Current Agency
	// protected Agency tempAgency;
	// Record Counters
	protected long totalRecords = 0;
	
	protected String fileTypeExtension ;

	/**
	 * To start the processing of transaction message
	 * 
	 * @throws Exception
	 */
	@Override
	public void process(String fileType, Long awayAgencyId) throws Exception {
		try {
			initialize();
			Agency tempAgency = staticDataLoad.getAgencyById(awayAgencyId);
			log.info("Fetching awayAgency properties{}", tempAgency.toString());
			String tempFilename = null;
			File tempFile = null;
			String creationFileType = null;
			String path = getConfigurationMapping().getFileInfoDto().getParentDirectory();
			fileTypeExtension = fileType;
			if (Constants.FILE_EXTENSION_INTX.equals(fileType)) {
				path = path.concat(Constants.CREATE_INRX);
				creationFileType = Constants.INRX;
			} else {
				path = path.concat(Constants.CREATE_IRXN);
				creationFileType = Constants.IRXN;
			}
			log.info("Destination path for INRX file: {}", path);

			log.info("Fetching list of NAME mapping properties..");
			List<MappingInfoDto> nameProperties = getConfigurationMapping().getFileNameMappingInfo();
			log.debug("NAME mapping properties:{}", nameProperties.toString());

			log.info("Fetching list of HEADER mapping properties..");
			List<MappingInfoDto> headerProperties = getConfigurationMapping().getHeaderMappingInfoList();
			log.debug("HEADER mapping properties:{}", headerProperties.toString());

			log.info("Fetching list of all file_control_ids from FO_RECON_FILE_STATS, for away agencies: {}",
					tempAgency.getDevicePrefix());
			List<FileStats> fileList = inrxDao.getFileControlIdIdListForINTXFiles(fileType, tempAgency.getDevicePrefix());

			if (tempAgency != null && fileList != null && !fileList.isEmpty()) {

				for (FileStats fileStatsRecord : fileList) {
					totalRecords = 0;
					log.info("Processing INTX file {} with FileControl Id : {} ", fileStatsRecord.getTrxEpsFileName(),
							fileStatsRecord.getFileControlId());
					long hCount = inrxDao.getHCountForINTXFile(fileStatsRecord.getFileControlId());
					long eCount = inrxDao.getECountForINTXFile(fileStatsRecord.getFileControlId());
					if(hCount != eCount || (hCount == 0 && eCount== 0)) {
						log.info("File is not processed completely: {}", fileStatsRecord.getTrxEpsFileName());
					}
					else {
					log.info("Fetching DETAIL records for plaza agency id {} and FileControlId {}.",
							tempAgency.getAgencyId(), fileStatsRecord.getFileControlId());
					List<TxDetailRecord> list = inrxDao.getTxRecordList(fileStatsRecord.getFileControlId(),
							tempAgency.getAgencyId());
					log.info("TxDetailRecord list: {} for FileControlId {} and current ageny id {}", list,
							fileStatsRecord.getFileControlId(), tempAgency.getAgencyId());
					if (list != null && !list.isEmpty()) {
						Thread.sleep(4000);// Delay so multiple files with same to and from agency don't have same name
						totalRecords = list.size();
						log.info("TxDetailRecord list Size:{}", list.size());
						fileStatsRecord.setEpsReconCount(list.size());

						tempFilename = buildFileSection(nameProperties, null, fileStatsRecord, tempAgency);
						log.info("Built temporary file name : {}", tempFilename);
						tempFile = createNewFile(tempFilename, path);
						log.info("Built temporary reconciliation file {} for file {}", tempFile.getAbsolutePath(),
								fileStatsRecord.getEpsReconFileName());

						FileWriter filewriter = new FileWriter(tempFile);

						// build header
						String header = buildFileSection(headerProperties, null, fileStatsRecord, tempAgency);
						log.info("Built header record: {}", header);

						if (header != null) {
							writeContentToFile(filewriter, header);
							log.info("Written header: {} to  file: {}", header, tempFile.getAbsoluteFile());
						}
						List<MappingInfoDto> detailProperties = getConfigurationMapping().getDetailRecMappingInfo();
						boolean status = false;
						for (TxDetailRecord txrecord : list) {
							if (validateRecord(txrecord)) {
								// build details
								String detailRecord = buildFileSection(detailProperties, txrecord, fileStatsRecord,
										tempAgency);
								status = writeContentToFile(filewriter, detailRecord);
								log.info("Record:{} added to file:{}", detailRecord, tempFile.getAbsolutePath());
							}
						}
						filewriter.flush();
						filewriter.close();
						log.info("File {} status: {}", tempFile.getAbsolutePath(), status);
						if (status) {
							overwriteFileHeader(tempFile, headerProperties, fileStatsRecord, tempAgency);
							String inrxFileName = fileRenameOperation(tempFilename, path, status, creationFileType);
							if (inrxFileName != null) {
								fileStatsRecord.setEpsReconFileName(inrxFileName);
								fileStatsRecord.setProcessedFlag(Constants.Y);
								inrxDao.updateINRXDetailsIntoFileStats(fileStatsRecord);
							}
							}
						}
					}
				}
			} else {
				log.info("No INTX file found with awayAgencyId {} for reconciliation . List of FILECONTROL ids:{}",
						awayAgencyId, fileList.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			totalRecords = 0;
		}
	}

	private boolean validateRecord(TxDetailRecord txrecord) {
		if (Integer.valueOf(txrecord.getTxStatus()) == 0) {
			return false;
		} else {
			return true;
		}

	}

	private File createNewFile(String tempFilename, String path) {
		Path destDir = new File(path, tempFilename).toPath();

		File temp = destDir.toFile();
		if (temp.exists()) {
			temp.delete();
		}
		File file = new File(path, tempFilename);
		return file;
	}

	public String buildFileSection(List<MappingInfoDto> mappings, TxDetailRecord tagDevice, FileStats fileStatsRecord,
			Agency tempAgency) {

		StringBuilder tempField = new StringBuilder();
		for (int i = 1; i <= mappings.size(); i++) {
			int index = i;
			Optional<MappingInfoDto> mappingInfoDto = mappings.stream().filter(e -> e.getFieldIndexPosition() == index)
					.findAny();
			tempField.append(doFieldMapping(mappingInfoDto.get(), tagDevice, fileStatsRecord, tempAgency));
		}
		log.debug(tempField.toString());
		return tempField.toString();
	}

	public String doFieldMapping(MappingInfoDto fMapping, TxDetailRecord txRecord, FileStats fileRecord,
			Agency tempAgency) {
		String value = "";
		try {
			log.debug("Mapping the field: {}", fMapping.getFieldName());
			switch (fMapping.getFieldName()) {
			case Constants.F_FROM_AGENCY_ID:
				value = fMapping.getDefaultValue();
				break;
			case Constants.F_UNERSCORE:
				value = fMapping.getDefaultValue();
				break;
			case Constants.F_TO_AGENCY_ID:
				value = doPadding(fMapping, String.valueOf(tempAgency.getDevicePrefix()));
				break;
			case Constants.F_FILE_DATE_TIME:
				value = LocalDateTime.now().format(DateTimeFormatter.ofPattern("YYYYMMddHHMMss"));
				break;
			case Constants.F_DOT:
				value = fMapping.getDefaultValue();
				break;
			case Constants.F_FILE_EXTENSION:
				value = Constants.TEMP;
				break;
			case Constants.H_FILE_TYPE:
				if (fileTypeExtension!=null) {
					value = fileTypeExtension.equals("INTX") ? "INRX" : "IRXN";
				}
				break;
			case Constants.H_FROM_AGENCY_ID:
				value = fMapping.getDefaultValue();
				break;
			case Constants.H_TO_AGENCY_ID:
				value = doPadding(fMapping, String.valueOf(tempAgency.getDevicePrefix()));
				break;
			case Constants.H_FILE_DATE_TIME:
				value = LocalDateTime.now().format(DateTimeFormatter.ofPattern(fMapping.getFormat()));
				break;
			case Constants.H_RECORD_COUNT:
				value = doPadding(fMapping, String.valueOf(fileRecord.getTrxEpsCount()));
				break;
				
			case Constants.H_INTX_FILE_NUM:
				value = doPadding(fMapping, String.valueOf(fileRecord.getFileSeqNumber()));
				break;
			case Constants.D_ETC_TRX_SERIAL_NUM:
				value = doPadding(fMapping, txRecord.getTxExternRefNo());
				break;
			case Constants.D_ETC_POST_STATUS:
				value = getTxPostingStatus(txRecord.getTxStatus()) == null ? "    "
						: doPadding(fMapping, getTxPostingStatus(txRecord.getTxStatus()));
				break;
			case Constants.D_ETC_POST_PLAN:
				value = getPostPlan(
						txRecord.getPlanTypeId() == null ? fMapping.getDefaultValue() : txRecord.getPlanTypeId());// 0
				if (value == null) {
					value = fMapping.getDefaultValue();
				}
				break;
			case Constants.D_ETC_DEBIT_CREDIT:
				value = (txRecord.getPostedFareAmount() >= 0) ? "+" : "-";
				break;
			case Constants.D_ETC_OWED_AMOUNT:
				value = doPadding(fMapping, String.valueOf(txRecord.getPostedFareAmount()));
				break;
			}
		} catch (Exception e) {
			log.info("Mapping done for the field: {} ", fMapping.getFieldName());
			e.printStackTrace();
		}
		log.info("Mapping done for the field: {} as value: {}", fMapping.getFieldName(), value);
		return value;
	}

	/**
	 * 
	 * @param txStatus
	 * @return
	 */
	private String getTxPostingStatus(String txStatus) {
		return inrxDao.getPostStatusForId(txStatus);
	}

	/**
	 * 
	 * @param planTypeId
	 * @return
	 */
	private String getPostPlan(String planTypeId) {
		String plancode = inrxDao.getPlanNameForPlanId(planTypeId);
		if (plancode != null) {
			return staticDataLoad.getPlanCode(plancode);
		} else {
			return null;
		}
	}

	/**
	 * 
	 */
	private void loadOutputConfigurationMapping() {
		log.info("loadOutputConfigurationMapping called");
		getFileCreationParameter().setFileType(Constants.INRX);
		getFileCreationParameter().setAgencyId(Constants.HOME_AGENCY_ID);
		getFileCreationParameter().setGenType("FULL");
		setConfigurationMapping(inrxDao.getMappingConfigurationDetails(getFileCreationParameter()));

	}

	/**
	 * 
	 * @param fMapping
	 * @param value
	 * @return
	 */
	public String doPadding(MappingInfoDto fMapping, String value) {
		String str = "";
		if (value == null || value == "null" || value.isEmpty()) {
			str = fMapping.getDefaultValue();
		} else if ("L".equals(fMapping.getJustification())) {
			str = StringUtils.leftPad(value, fMapping.getFieldLength(), fMapping.getPadCharacter());
		} else if ("R".equals(fMapping.getJustification())) {
			str = StringUtils.rightPad(value, fMapping.getFieldLength(), fMapping.getPadCharacter());
		}
		return str;
	}

	/**
	 * 
	 * @param filewriter
	 * @param record
	 */
	public boolean writeContentToFile(FileWriter filewriter, String record) {
		boolean isRecordAdded = true;
		try {
			filewriter.write(record + "\r");
			log.info("Record written to file successfully: {}", record);
		} catch (IOException e) {
			isRecordAdded = false;
			log.error("Exception occurred while writing record {}", record);
			e.printStackTrace();
		}
		return isRecordAdded;
	}

	/**
	 * 
	 * @param fileHeaderDto
	 * @param file
	 * @param headerProperties
	 * @return
	 */
	public boolean overwriteFileHeader(java.io.File file, List<MappingInfoDto> headerProperties,
			FileStats fileStatsRecord, Agency tempAgency) {

		boolean headerUpdatedInFile = false;

		log.info("Building new header with latest record counts ..");
		String newLine = buildFileSection(headerProperties, null, fileStatsRecord, tempAgency);

		try {
			String filePath = file.getAbsolutePath();
			Scanner sc = new Scanner(new File(filePath));
			StringBuffer buffer = new StringBuffer();
			String oldLine = null;
			if (sc.hasNextLine() != false) {
				oldLine = sc.nextLine();
			}
			Scanner sc1 = new Scanner(new File(filePath));
			while (sc1.hasNextLine()) {
				buffer.append(sc1.nextLine() + "\n");
			}

			String fileContents = buffer.toString();
			log.debug("Contents of the file: " + fileContents);

			sc.close();
			sc1.close();

			log.info("Replacing MTAG file {} header", file.getName());
			log.info("Old header: {}", oldLine);
			log.info("New header: {}", newLine);

			fileContents = fileContents.replaceAll(oldLine, newLine);
			FileWriter writer = new FileWriter(filePath);
			log.debug("new data: {}", fileContents);
			writer.append(fileContents);
			writer.flush();
			writer.close();
			headerUpdatedInFile = true;
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return headerUpdatedInFile;
	}

	/**
	 * 
	 * @param tempFileName
	 * @param tagFileDest
	 * @param fileCompletionStatus
	 * @param fileType
	 * @return
	 * @throws FileNotFoundException
	 */
	/**
	 * 
	 * @param tempFileName
	 * @param tagFileDest
	 * @param fileCompletionStatus
	 * @param fileType
	 * @return
	 * @throws FileNotFoundException
	 */
	public String fileRenameOperation(String tempFileName, String tagFileDest, Boolean fileCompletionStatus,
			String fileType) throws FileNotFoundException {
		boolean fileRenameStatus = false;
		File renamefile = null;
		try {
			log.info("Rename file {} to type {} at location {}", tempFileName, fileType, tagFileDest);
			File oldfile = new File(tagFileDest, tempFileName);
			// write file content
			if (fileCompletionStatus) {
				renamefile = new File(tagFileDest, tempFileName.replace("TEMP", fileType));
				log.info("Renaming file {} to INRX file {} ", oldfile.getAbsolutePath(), renamefile.getAbsolutePath());
				fileRenameStatus = oldfile.renameTo(renamefile);
				log.info("ICRX file created: {}", renamefile.getAbsolutePath());
				log.info("File renamed operation status: {}", fileRenameStatus);
			}
			if (!fileRenameStatus) {
				log.info("Exception while renaming the temp file: {}", tempFileName);
			}
		} catch (Exception e) {
			log.error("Exception while renaming the file: {}", tempFileName);
			e.printStackTrace();
		}
		return renamefile.getName();
	}

}
