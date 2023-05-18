package com.conduent.tpms.icrx.service.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.conduent.app.timezone.utility.TimeZoneConv;
import com.conduent.tpms.icrx.constants.Constants;
import com.conduent.tpms.icrx.dao.ICRXDao;
import com.conduent.tpms.icrx.model.Agency;
import com.conduent.tpms.icrx.model.FileCreationParameters;
import com.conduent.tpms.icrx.model.FileStats;
import com.conduent.tpms.icrx.model.MappingInfoDto;
import com.conduent.tpms.icrx.model.TxDetailRecord;
import com.conduent.tpms.icrx.service.IcrxProcessService;
import com.conduent.tpms.icrx.utility.FileWriteOperation;
import com.conduent.tpms.icrx.utility.StaticDataLoad;

/**
 * ICRX Process Service Implementation
 * 
 * @author urvashic
 *
 */
@Service
public class IcrxProcessServiceImpl implements IcrxProcessService {

	private static final Logger log = LoggerFactory.getLogger(IcrxProcessServiceImpl.class);

	@Autowired
	StaticDataLoad staticDataLoad;
	
	@Autowired
	ICRXDao icrxDao;
	
	@Autowired
	TimeZoneConv timeZoneConv;
	
	@Autowired
	public FileWriteOperation fileWriteOperation;
	
	protected boolean isHederPresentInFile;
	protected boolean isDetailsPresentInFile;
	protected boolean isTrailerPresentInFile;
	private FileCreationParameters configurationMapping = new FileCreationParameters();
	private FileCreationParameters fileCreationParameter = new FileCreationParameters();
	private List<String> ICTXNotReadyForICRXGeneration = new ArrayList<String>();
	
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

	//Current Agency
	//protected Agency tempAgency;
	//Record Counters
	protected long totalRecords = 0;
	
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
			log.info("Fetching awayAgency properties{}",tempAgency.toString());
			String tempFilename = null;
			File tempFile = null;
			String creationFileType = null;
			String path = getConfigurationMapping().getFileInfoDto().getParentDirectory();
			if (Constants.FILE_EXTENSION_ICTX.equals(fileType)) {
				path = path.concat(Constants.CREATE_ICRX);
				creationFileType = Constants.ICRX;
			} else {
				path = path.concat(Constants.CREATE_IRXC);
				creationFileType = Constants.IRXC;
			}
			log.info("Destination path for ICRX file: {}", path);
			
			log.info("Fetching list of NAME mapping properties..");
			List<MappingInfoDto> nameProperties = getConfigurationMapping().getFileNameMappingInfo();
			log.debug("NAME mapping properties:{}", nameProperties.toString());
			
			log.info("Fetching list of HEADER mapping properties..");
			List<MappingInfoDto> headerProperties = getConfigurationMapping().getHeaderMappingInfoList();
			log.debug("HEADER mapping properties:{}",headerProperties.toString());
			
			log.info("Fetching list of all xfer_control_ids from T_IA_FILE_STATS, for away agencies: {}",tempAgency.getDevicePrefix());
			List<FileStats> fileList = icrxDao.getXferIdListForICTXFiles(fileType, tempAgency.getDevicePrefix());
			
			if (tempAgency != null && fileList != null && !fileList.isEmpty()) {
				
				for (FileStats fileStatsRecord : fileList) {
					totalRecords = 0;
					log.info("Processing ICTX file {} with XFER Id : {} ", fileStatsRecord.getIctxFileName(), fileStatsRecord.getXferControlId());
					//long hCount = icrxDao.getHCountForICTXFile(fileStatsRecord.getXferControlId()); 
					long hCount = fileStatsRecord.getInputCount() == null ? 0 : fileStatsRecord.getInputCount();
					//long eCount = icrxDao.getECountForICTXFile(fileStatsRecord.getXferControlId());
					long eCount = icrxDao.getECountForICTXFileFromICRXRecon(fileStatsRecord.getXferControlId());
					if (hCount != eCount || (hCount == 0 && eCount== 0)) {
						ICTXNotReadyForICRXGeneration.add(fileStatsRecord.getIctxFileName());
						log.info("File is not processed completely: {}", fileStatsRecord.getIctxFileName());
					} else {
						log.info("Fetching DETAIL records for plaza agency id {} and XferControlId {}.",tempAgency.getAgencyId(),fileStatsRecord.getXferControlId());
						List<TxDetailRecord> list = icrxDao.getTxRecordList(fileStatsRecord.getXferControlId(), tempAgency.getAgencyId()); 
						log.info("TxDetailRecord list: {} for XferControlId {} and current ageny id {}",list, fileStatsRecord.getXferControlId(), tempAgency.getAgencyId());
						if (list != null && !list.isEmpty()) {
							Thread.sleep(4000);//Delay so multiple files with same to and from agency dont have same name
							totalRecords = list.size();
							Long recordCount = 0L;
							recordCount = (long) list.size();
							log.info("TxDetailRecord list Size:{}",list.size());
							fileStatsRecord.setOutputCount(recordCount);
							
							tempFilename = buildFileSection(nameProperties, null, fileStatsRecord, tempAgency);
							log.info("Built temporary file name : {}",tempFilename);
							tempFile = createNewFile(tempFilename, path);
							log.info("Built temporary reconciliation file {} for file {}",tempFile.getAbsolutePath(), fileStatsRecord.getIctxFileName());
							
							FileWriter filewriter = new FileWriter(tempFile); 
							
							//build header
							String header = buildFileSection(headerProperties, null, fileStatsRecord, tempAgency);
							log.info("Built header record: {}",header);

							if (header != null) {
								writeContentToFile(filewriter, header);
								log.info("Written header: {} to  file: {}",header, tempFile.getAbsoluteFile());
							}
							List<MappingInfoDto> detailProperties = getConfigurationMapping().getDetailRecMappingInfo();
							boolean status = false;
							for (TxDetailRecord txrecord : list) {
								if (validateRecord(txrecord)) {
									//build details	
									String detailRecord = buildFileSection(detailProperties, txrecord, fileStatsRecord, tempAgency);
									status = writeContentToFile(filewriter, detailRecord);
									log.info("Record:{} added to file:{}",detailRecord, tempFile.getAbsolutePath());
								}
							}
							filewriter.flush();
							filewriter.close();
							log.info("File {} status: {}",tempFile.getAbsolutePath(),status);
							if (status) {
								overwriteFileHeader(tempFile, headerProperties, fileStatsRecord, tempAgency);
								String icrxFileName = fileRenameOperation(tempFilename, path, status, creationFileType);
								if (icrxFileName != null) {
									fileStatsRecord.setIcrxFileName(icrxFileName);
									fileStatsRecord.setProcessedFlag(Constants.Y);
									fileStatsRecord.setUpdateTs(timeZoneConv.currentTime());
									icrxDao.updateICRXDetailsIntoFileStats(fileStatsRecord);
								}
							}
						}
					} 
				}
				if (!ICTXNotReadyForICRXGeneration.isEmpty()) {
					log.info("List of ICTX Files not ready for ICRX Generation");
					ICTXNotReadyForICRXGeneration.stream().forEach(e -> log.info(" -> " + e));
					log.info("    Total Files: {}", ICTXNotReadyForICRXGeneration.size());
				}
			} else {
				log.info("No ICTX file found with awayAgencyId {} for reconciliation . List of XFER ids:{}", awayAgencyId, fileList.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			totalRecords = 0;
			ICTXNotReadyForICRXGeneration.clear();
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
	
	public String buildFileSection(List<MappingInfoDto> mappings, TxDetailRecord tagDevice, FileStats fileStatsRecord, Agency tempAgency) {
		
		StringBuilder tempField = new StringBuilder();
		for(int i = 1; i<=mappings.size(); i++) {
			int index = i;
			Optional<MappingInfoDto> mappingInfoDto = mappings.stream().filter(e -> e.getFieldIndexPosition() == index).findAny();
			tempField.append(doFieldMapping(mappingInfoDto.get(), tagDevice, fileStatsRecord, tempAgency));
		}
		log.debug(tempField.toString());
		return tempField.toString();
	}
	
	public String doFieldMapping(MappingInfoDto fMapping, TxDetailRecord txRecord, FileStats fileRecord, Agency tempAgency) {
		String value = "";
		try {
			log.debug("Mapping the field: {}", fMapping.getFieldName());
			switch (fMapping.getFieldName()) {
				case Constants.F_FROM_AGENCY_ID:
					value = doPadding(fMapping, fMapping.getDefaultValue());
					break;
				case Constants.F_UNERSCORE:
					value = fMapping.getDefaultValue();
					break;
				case Constants.F_TO_AGENCY_ID:
					value = doPadding(fMapping, String.valueOf(tempAgency.getDevicePrefix()));
					break;
				case Constants.F_FILE_DATE_TIME:
					//value = LocalDateTime.now().format(DateTimeFormatter.ofPattern(fMapping.getFormat()));
					LocalDateTime currentTime = timeZoneConv.currentTime();
					fileRecord.setIcrxFileDateTime(currentTime);
					value = currentTime.format(DateTimeFormatter.ofPattern(fMapping.getFormat()));
					break;	
				case Constants.F_DOT:
					value = fMapping.getDefaultValue();
					break;		
				case Constants.F_FILE_EXTENSION:
					value = Constants.TEMP;
					break;
				case Constants.H_FILE_TYPE:
					value = fileRecord.getFileType().equals(Constants.FILE_EXTENSION_ICTX) ? Constants.ICRX : Constants.IRXC;
					break;
				case Constants.H_VERSION:
					value = fMapping.getDefaultValue();
					break;
				case Constants.H_FROM_AGENCY_ID:
					value = doPadding(fMapping, fMapping.getDefaultValue());
					break;
				case Constants.H_TO_AGENCY_ID:
					value = doPadding(fMapping, String.valueOf(tempAgency.getDevicePrefix()));
					break;
				case Constants.H_FILE_DATE_TIME:
					//value = LocalDateTime.now().format(DateTimeFormatter.ofPattern(fMapping.getFormat()));
					value = fileRecord.getIcrxFileDateTime().format(DateTimeFormatter.ofPattern(fMapping.getFormat()));
					break;
				case Constants.H_RECORD_COUNT:
					value = doPadding(fMapping, String.valueOf(fileRecord.getOutputCount()));
					break;
				case Constants.H_ICTX_FILE_NUM:
					value = doPadding(fMapping,String.valueOf(fileRecord.getFileSeqNumber()));
					break;
				case Constants.D_ETC_TRX_SERIAL_NUM:   
					value = doPadding(fMapping, txRecord.getTxExternRefNo());
					break;
				case Constants.D_ETC_POST_STATUS:
					value = doPadding(fMapping, txRecord.getTxStatus() == null || "0".equals(txRecord.getTxStatus())
							? fMapping.getDefaultValue() : getTxPostingStatus(txRecord.getTxStatus()));
					break;
				case Constants.D_ETC_POST_PLAN:
					value = doPadding(fMapping, (txRecord.getPlanTypeId() == null || "0".equals(txRecord.getPlanTypeId())) 
							? fMapping.getDefaultValue() : getPostPlan(txRecord.getPlanTypeId()));
					break;
				case Constants.D_ETC_DEBIT_CREDIT:
					value = txRecord.getPostedFareAmount() >= 0 ? Constants.PLUS : Constants.MINUS;
					break;
				case Constants.D_ETC_OWED_AMOUNT:
					value = doPadding(fMapping, String.valueOf(txRecord.getPostedFareAmount()));
					break;
				case Constants.D_ETC_DUP_SERIAL_NUM:
					value = doPadding(fMapping, (txRecord.getPlanTypeId() == null || "0".equals(txRecord.getPlanTypeId()) || !Constants.RJDP.equals(getPostPlan(txRecord.getPlanTypeId()))) 
							? fMapping.getDefaultValue() : getDupTxExternRefNo(txRecord.getTransactionRowId()));
					break;
			}
		} catch (Exception e) {
			log.info("Mapping done for the field: {} ", fMapping.getFieldName());
			e.printStackTrace();
		}
		log.info("Mapping done for the field: {} as value: {}", fMapping.getFieldName(), value);
		return value;	
	}
	
	private String getDupTxExternRefNo(String transactionRowId) {
		return icrxDao.getDupExternRefForRJDPTxn(transactionRowId);
	}
	
	/**
	 * 
	 * @param txStatus
	 * @return
	 */
	private String getTxPostingStatus(String txStatus) {
		return icrxDao.getPostStatusForId(txStatus);
	}

	/**
	 * 
	 * @param planTypeId
	 * @return
	 */
	private String getPostPlan(String planTypeId) {
		String plancode = icrxDao.getPlanNameForPlanId(planTypeId);
		return staticDataLoad.getPlanCode(plancode);
	}

	/**
	 * 
	 */
	private void loadOutputConfigurationMapping() {
		log.info("loadOutputConfigurationMapping called");
		getFileCreationParameter().setFileType(Constants.ICRX);
		getFileCreationParameter().setAgencyId(Constants.HOME_AGENCY_ID);
		getFileCreationParameter().setGenType(Constants.FULL);
		setConfigurationMapping(icrxDao.getMappingConfigurationDetails(getFileCreationParameter()));
		
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
	public boolean writeContentToFile(FileWriter filewriter, String record ) {
		boolean isRecordAdded = true;
		try {
			filewriter.write(record + "\r");
			log.info("Record written to file successfully: {}",record);
		} catch (IOException e) {
			isRecordAdded = false;
			log.error("Exception occurred while writing record {}",record);
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
	public boolean overwriteFileHeader(java.io.File file, List<MappingInfoDto> headerProperties, FileStats fileStatsRecord, Agency tempAgency) {

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
			
			log.info("Replacing ICRX file {} header",file.getName());
			log.info("Old header: {}",oldLine);
			log.info("New header: {}",newLine);
			
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
	public String fileRenameOperation(String tempFileName, String tagFileDest, Boolean fileCompletionStatus, String fileType) throws FileNotFoundException {
		boolean fileRenameStatus = false;
		File renamefile = null;
		try {
			log.info("Rename file {} to type {} at location {}",tempFileName, fileType, tagFileDest);
			File oldfile = new File(tagFileDest, tempFileName);
			//write file content
			if (fileCompletionStatus) {
				renamefile = new File(tagFileDest, tempFileName.replace("TEMP", fileType));
				log.info("Renaming file {} to ICRX file {} ",oldfile.getAbsolutePath(), renamefile.getAbsolutePath());
				fileRenameStatus = oldfile.renameTo(renamefile);
				log.info("ICRX file created: {}",renamefile.getAbsolutePath());
				log.info("File renamed operation status: {}", fileRenameStatus);
			}
			if (!fileRenameStatus) {
				log.info("Exception while renaming the temp file: {}",tempFileName);
			}
		} catch (Exception e) {
			log.error("Exception while renaming the file: {}", tempFileName);
			e.printStackTrace();
		}
		return renamefile.getName();
	}
	
}
