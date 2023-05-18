package com.conduent.tpms.intx.service.impl;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.conduent.tpms.intx.constants.IntxConstant;
import com.conduent.tpms.intx.dao.AwayTransactionDao;
import com.conduent.tpms.intx.dao.FoFileStatsDao;
import com.conduent.tpms.intx.dao.FoReconFileStatsDao;
import com.conduent.tpms.intx.dao.ReconciliationCheckPointDao;
import com.conduent.tpms.intx.dao.SequenceDao;
import com.conduent.tpms.intx.dao.TransactionDetailDao;
import com.conduent.tpms.intx.dto.AtpMessageDto;
import com.conduent.tpms.intx.dto.FileOperationStatus;
import com.conduent.tpms.intx.dto.MessageConversionDto;
import com.conduent.tpms.intx.model.Agency;
import com.conduent.tpms.intx.model.AwayTransaction;
import com.conduent.tpms.intx.model.ConfigVariable;
import com.conduent.tpms.intx.model.FoReconFileStats;
import com.conduent.tpms.intx.model.ReconciliationCheckPoint;
import com.conduent.tpms.intx.service.AtpMessageFormatService;
import com.conduent.tpms.intx.service.DuplicateCheckService;
import com.conduent.tpms.intx.service.IntxConversionService;
import com.conduent.tpms.intx.service.IntxProcessService;
import com.conduent.tpms.intx.service.MessagePublisherService;
import com.conduent.tpms.intx.service.ValidationService;
import com.conduent.tpms.intx.utility.FileOperation;
import com.conduent.tpms.intx.utility.StaticDataLoad;

@Service
public class IntxProcessServiceImpl implements IntxProcessService {

	private static final Logger logger = LoggerFactory.getLogger(IntxProcessServiceImpl.class);

	@Autowired
	ConfigVariable configVariable;

	@Autowired
	StaticDataLoad staticDataLoad;

	@Autowired
	AwayTransactionDao awayTransactionDao;

	@Autowired
	ReconciliationCheckPointDao reconciliationCheckPointDao;

	@Autowired
	SequenceDao sequenceDao;

	@Autowired
	FileOperation fileOperation;

//	@Autowired
//	IagFileStatisticsDao iagFileStatisticsDao;
//
//	@Autowired
//	IagFileStatsDao iagFileStatsDao;
	
	@Autowired
	FoReconFileStatsDao foReconFileStatsDao;

	@Autowired
	FoFileStatsDao foFileStatsDao;

	@Autowired
	TransactionDetailDao transactionDetailDao;

//	@Autowired
//	IntxStatisticsService intxStatisticsService;
	
	@Autowired
	AtpMessageFormatService atpMessageFormatService;
	
	@Autowired
	MessagePublisherService messagePublisherService;
	
	@Autowired
	DuplicateCheckService duplicateCheckService;
	
	@Autowired
	IntxConversionService intxConversionService;
	
	@Autowired
	ValidationService validationService;

	@Override
	public void process(String externalFileId, Long awayAgencyId, String fileType) throws Exception {
		// Get the agency prefix
		logger.info("Get the agency prefix {}", awayAgencyId);
		Agency tempAgency = staticDataLoad.getAgencyById(awayAgencyId);
		logger.info("Got the agency {}", tempAgency);
		List<String> fileRecord = new ArrayList<>();
		List<AwayTransaction> tempAwayTxList = new ArrayList<>();

		if (tempAgency != null) {
			// Get the records from table based on *(getAll for now)
			logger.info("Get the records from table based on agency prefix");
			if (fileType.equalsIgnoreCase(IntxConstant.FILE_EXTENSION_INTX)) {
				tempAwayTxList = awayTransactionDao.getAwayTransactionByExternalIdAndDevicePrefix(externalFileId,
						tempAgency.getDevicePrefix());
			} else if (fileType.equalsIgnoreCase(IntxConstant.FILE_EXTENSION_ITXN)) {
				tempAwayTxList = awayTransactionDao.getCorrAwayTransactionByExternalIdAndDevicePrefix(externalFileId,
						tempAgency.getDevicePrefix());
			}

			logger.info("Number of {} records :{} for Agency Id: {}", fileType, tempAwayTxList.size(),
					tempAgency.getAgencyId());

			if (!tempAwayTxList.isEmpty()) {
				processAwayTransaction(externalFileId, awayAgencyId, tempAgency, fileRecord, tempAwayTxList, fileType);
			}
		}
	}

	/**
	 * Process away transaction
	 * 
	 * @param externalFileId
	 * @param awayAgencyId
	 * @param tempAgency
	 * @param fileRecord
	 * @param tempAwayTxList
	 * @param tempAwayCorrList
	 * @throws Exception
	 */
	private void processAwayTransaction(String externalFileId, Long awayAgencyId, Agency tempAgency,
			List<String> fileRecord, List<AwayTransaction> tempAwayTxList, String fileType) throws Exception {
		FileOperationStatus fileOperationStatus = new FileOperationStatus();

		fileOperationStatus.setFromAgency(IntxConstant.CONSTANT_PARENT_AGENCY_ID_008);
		fileOperationStatus.setToAgency(tempAgency.getDevicePrefix());

		String createdDateTime;
		String fileName = null;
		String header = null;

		// Check in T_RECONCILATION_CHECKPOINT if any of file exists in S,P state
		ReconciliationCheckPoint tempReconciliationCheckPoint = reconciliationCheckPointDao
				.getFileInfo(fileOperationStatus.getFromAgency(), fileOperationStatus.getToAgency(), fileType);
		
		if (tempReconciliationCheckPoint != null) {
			// In case of yes
			logger.info("tempReconciliationCheckPoint not null: {}", tempReconciliationCheckPoint);
			fileName = tempReconciliationCheckPoint.getFileName();
			if (fileType.equalsIgnoreCase(IntxConstant.FILE_EXTENSION_INTX)) {
				header = getHeader(IntxConstant.FILE_EXTENSION_INTX, IntxConstant.CONSTANT_PARENT_AGENCY_ID_008,
						tempAgency.getDevicePrefix().trim(), 0L, fileName.substring(8, 22));
			} else if (fileType.equalsIgnoreCase(IntxConstant.FILE_EXTENSION_ITXN)) {
				header = getHeader(IntxConstant.FILE_EXTENSION_ITXN, IntxConstant.CONSTANT_PARENT_AGENCY_ID_008,
						tempAgency.getDevicePrefix().trim(), 0L, fileName.substring(8, 22));
			}
			logger.info("header: {}", header);
			fileOperationStatus.setExistingFileFlag(true);
			if (tempReconciliationCheckPoint.getRecordCount() != null) {
				fileOperationStatus.setExistingRecordCount(tempReconciliationCheckPoint.getRecordCount());
			}
		} else {
			// In case of no
			logger.info("tempReconciliationCheckPoint is null ");
			createdDateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
			fileName = getFileName(tempAgency.getDevicePrefix(), createdDateTime, fileType);
			if (fileType.equalsIgnoreCase(IntxConstant.FILE_EXTENSION_INTX)) {
				header = getHeader(IntxConstant.FILE_EXTENSION_INTX, IntxConstant.CONSTANT_PARENT_AGENCY_ID_008,
						tempAgency.getDevicePrefix().trim(), 0L, createdDateTime);
			} else if (fileType.equalsIgnoreCase(IntxConstant.FILE_EXTENSION_ITXN)) {
				header = getHeader(IntxConstant.FILE_EXTENSION_ITXN, IntxConstant.CONSTANT_PARENT_AGENCY_ID_008,
						tempAgency.getDevicePrefix().trim(), 0L, createdDateTime);
			}
			logger.info("header: {}", header);
			fileOperationStatus.setExistingFileFlag(false);
		}
		String tempFilename = "";
		if (fileType.equalsIgnoreCase(IntxConstant.FILE_EXTENSION_INTX)) {
			tempFilename = fileName.replace(IntxConstant.FILE_EXTENSION_INTX, IntxConstant.FILE_EXTENSION_TEMP);
		} else if (fileType.equalsIgnoreCase(IntxConstant.FILE_EXTENSION_ITXN)) {
			tempFilename = fileName.replace(IntxConstant.FILE_EXTENSION_ITXN, IntxConstant.FILE_EXTENSION_TEMP);
		}
		fileOperationStatus.setTempFileName(tempFilename);
		fileOperationStatus.setFileName(fileName);

		if (header != null) {
			processTransaction(externalFileId, tempAgency, fileRecord, tempAwayTxList, fileOperationStatus, fileName, header, fileType);
		} else {
			fileOperationStatus.setOperationSuccessFlag(false);
		}

		if (!fileOperationStatus.isOperationSuccessFlag()) {
			logger.info("File operation failed for away agency: {} ,filename: {} ", awayAgencyId, fileName);
		}
	}

	/**
	 * Extract Processing of transaction
	 * 
	 * @param tempAgency
	 * @param fileRecord
	 * @param tempAwayTxList
	 * @param fileOperationStatus
	 * @param fileName
	 * @param header
	 * @throws Exception
	 */
	private void processTransaction(String externalFileId, Agency tempAgency, List<String> fileRecord, List<AwayTransaction> tempAwayTxList,
			FileOperationStatus fileOperationStatus, String fileName, String header, String fileType) throws Exception {
		logger.info("Process transaction for Agency Id: {}, fileName: {}", tempAgency.getAgencyId(), fileName);

		processTx(fileRecord, tempAwayTxList, fileName, header, fileOperationStatus, fileType);
		logger.info("Process transaction for Agency Id: {}, fileName: {}, isNoRecordFlag: {}", tempAgency.getAgencyId(),
				fileName, fileOperationStatus.isNoRecordFlag());
		if (!fileOperationStatus.isNoRecordFlag()) {
			Long fileRecordCount = 0L;
			if (fileType.equalsIgnoreCase(IntxConstant.FILE_EXTENSION_INTX)) {
				fileRecordCount = fileOperation.getRecordCount(fileOperationStatus.getTempFileName(),
						configVariable.getDstDirPathIntx());
			} else if (fileType.equalsIgnoreCase(IntxConstant.FILE_EXTENSION_ITXN)) {
				fileRecordCount = fileOperation.getRecordCount(fileOperationStatus.getTempFileName(),
						configVariable.getDstDirPathItxn());
			}
			logger.info("Transaction Record Count: {} for Agency Id: {}, fileName: {}", fileRecordCount,
					tempAgency.getAgencyId(), fileName);
			
			overwriteHeaderRecord(fileName, fileRecordCount, fileOperationStatus, fileType);
			// updateRecordCounts(fileName, fileRecordCount);
			fileRenameOperation(fileName, fileOperationStatus, fileType);
			updateReconciliationFileStatus(fileOperationStatus);
			
			// Insert into agencyTxPending
			updateAgencyTxPending(tempAwayTxList);
			
			// Insert into foReconFileStats
			FoReconFileStats foReconFileStats = new FoReconFileStats();
			Double totalEpsAmount = (double) 0;
			Long recordCount = (long) 0;
			foReconFileStats.setTrxEpsFileName(fileName);
			for (AwayTransaction awayTx: tempAwayTxList) {
				totalEpsAmount += awayTx.getEtcAmountDue();
				recordCount += 1;
			}
			foReconFileStats.setTrxEpsAmount(totalEpsAmount);			
			foReconFileStats.setTrxEpsCount(recordCount);
			foReconFileStatsDao.insertInFoReconFileStats(foReconFileStats, header);
			// Insert into FoFileStats
			foFileStatsDao.insertFoFileStats(foReconFileStats, header);
		}
	}

//	private void updateRecordCounts(String fileName, Long fileRecordCount) {
//		iagFileStatsDao.updateRecordCount(fileName, fileRecordCount);
//		iagFileStatisticsDao.updateRecordCount(fileName, fileRecordCount);
//	}

	private void updateReconciliationFileStatus(FileOperationStatus fileOperationStatus) {
		if (fileOperationStatus.isOperationSuccessFlag()) {
			reconciliationCheckPointDao.updateStatus(fileOperationStatus.getFileName(),
					IntxConstant.FILE_STATUS_COMPLETE);
		}
	}

	/**
	 * Extracted processing of transaction
	 * 
	 * @param fileRecord
	 * @param tempAwayTxList
	 * @param fileName
	 * @param header
	 * @param fileOperationStatus
	 * @throws Exception
	 */
	private void processTx(List<String> fileRecord, List<AwayTransaction> tempAwayTxList, String fileName,
			String header, FileOperationStatus fileOperationStatus, String fileType) throws Exception {
		List<AwayTransaction> tempIagStatList = new ArrayList<>();
		List<AtpMessageDto> tempAtpMessageDto = new ArrayList<>();
		boolean oneTimeWriteFlag = true;
		if (!fileOperationStatus.isExistingFileFlag()) {
			fileRecord.add(header);
		}

		for (int i = 0; i < tempAwayTxList.size() && fileOperationStatus.isOperationSuccessFlag(); i++) {
			oneTimeWriteFlag = validateAndProcessTx(fileRecord, tempAwayTxList, fileOperationStatus, tempIagStatList,
					tempAtpMessageDto, oneTimeWriteFlag, i, header, fileType);
			if (!fileRecord.isEmpty() && fileOperationStatus.isOperationSuccessFlag()) {
				logger.info("fileRecord.size(): {} for header.equalsIgnoreCase(fileRecord.get(0)): {}, I: {}",
						fileRecord.size(), header.equalsIgnoreCase(fileRecord.get(0)), i);
				if (fileRecord.size() <= 1 && (header.equalsIgnoreCase(fileRecord.get(0)) || fileRecord.size() == 0)
						&& i == (tempAwayTxList.size() - 1)) {
					fileRecord.clear();
					tempIagStatList.clear();
					fileOperationStatus.setNoRecordFlag(true);
				} else if (fileRecord.size() >= 1 && !header.equalsIgnoreCase(fileRecord.get(fileRecord.size() - 1))) {
					if (fileOperationStatus.isExistingFileFlag() && oneTimeWriteFlag) {
						int recordLength = 110;
						int headearLength = 39;
						Long position = (fileOperationStatus.getExistingRecordCount() * recordLength) + headearLength
								+ 1;
						if (fileType.equalsIgnoreCase(IntxConstant.FILE_EXTENSION_INTX)) {
							fileOperationStatus.setOperationSuccessFlag(
									fileOperation.overwriteFileData(fileOperationStatus.getTempFileName(), fileRecord,
											position, configVariable.getDstDirPathIntx()));
						} else if (fileType.equalsIgnoreCase(IntxConstant.FILE_EXTENSION_ITXN)) {
							fileOperationStatus.setOperationSuccessFlag(
									fileOperation.overwriteFileData(fileOperationStatus.getTempFileName(), fileRecord,
											position, configVariable.getDstDirPathItxn()));
						}
						oneTimeWriteFlag = false;
					} else {
						if (fileType.equalsIgnoreCase(IntxConstant.FILE_EXTENSION_INTX)) {
							fileOperationStatus.setOperationSuccessFlag(
									fileOperation.writeToFile(fileOperationStatus.getTempFileName(),
											configVariable.getDstDirPathIntx(), fileRecord));
						} else if (fileType.equalsIgnoreCase(IntxConstant.FILE_EXTENSION_ITXN)) {
							fileOperationStatus.setOperationSuccessFlag(
									fileOperation.writeToFile(fileOperationStatus.getTempFileName(),
											configVariable.getDstDirPathItxn(), fileRecord));
						}
					}
					
					// Update Dst Atp file Id
					fileOperationStatus.setIagStatusRecordList(tempIagStatList);
				    // intxStatisticsService.updateIagStatistics(fileOperationStatus,header);

					fileOperationStatus.setNoRecordFlag(false);
					fileRecord.clear();
					tempIagStatList.clear();
					
				}
			}
			
			if (fileType.equalsIgnoreCase(IntxConstant.FILE_EXTENSION_INTX)) {
				// Delete Tx from T_EPS_INTX_LOGS
				awayTransactionDao.deleteFromIntxLogs(tempAwayTxList.get(i));
			} else if (fileType.equalsIgnoreCase(IntxConstant.FILE_EXTENSION_ITXN)) {
				// Delete Tx from T_EPS_ITXN_CORRECTION_LOGS
				// awayTransactionDao.deleteFromItxnCorrectionLogs(tempAwayTxList.get(i));
				awayTransactionDao.deleteFromIntxLogs(tempAwayTxList.get(i));
			}
			
		}
	}

	/**
	 * Validate and process transaction
	 * 
	 * @param fileRecord
	 * @param tempAwayTxList
	 * @param fileOperationStatus
	 * @param tempIagStatList
	 * @param tempAtpMessageDto
	 * @param oneTimeWriteFlag
	 * @param i
	 * @return boolean
	 * @throws Exception
	 */
	private boolean validateAndProcessTx(List<String> fileRecord, List<AwayTransaction> tempAwayTxList,
			FileOperationStatus fileOperationStatus, List<AwayTransaction> tempIagStatList,
			List<AtpMessageDto> tempAtpMessageDto, boolean oneTimeWriteFlag, int i, String header, String fileType)
			throws Exception {
		if (validationService.validateTx(tempAwayTxList.get(i))) {
			try {
				oneTimeWriteFlag = validateDuplicateCheckAndProcessTx(fileRecord, tempAwayTxList, fileOperationStatus,
						tempIagStatList, oneTimeWriteFlag, i, header, fileType);
				logger.info("oneTimeWriteFlag:{}", oneTimeWriteFlag);
			} catch (Exception e) {
				e.printStackTrace();

			}

		} else {
			// Push Rejected Tx to ATP Queue and update txType and txSubType in
			// Convert to ATP Queue Format
			logger.info("Push Rejected Tx to ATP Queue. Tx: {}", tempAwayTxList.get(i));
			tempAtpMessageDto.add(atpMessageFormatService.getAtpMessage(tempAwayTxList.get(i), fileType));
			messagePublisherService.publishMessageToStream(tempAtpMessageDto);
			// Update T_TRAN_DETAIL
			transactionDetailDao.updateTransactionDetail(tempAwayTxList.get(i));

			// Delete record from T_AGENCY_TX_PENDING
			Integer deletedRecCount = awayTransactionDao.deleteRejectedTx(tempAwayTxList.get(i).getLaneTxId(),
					fileType);
			if (deletedRecCount != null) {
				logger.info("Deleted record in T_AGENCY_TX_PENDING with lane tx id:{}",
						tempAwayTxList.get(i).getLaneTxId());
			}

		}

		fileOperationStatus.setIagStatusRecordList(tempIagStatList);
		logger.info("Total Elements in IagStatusRecordList: {}", tempIagStatList.size());

//		if (fileType.equalsIgnoreCase(IntxConstant.FILE_EXTENSION_ITXN)) {
//			// Delete record from T_AGENCY_TX_PENDING
//			Integer deletedRecCountQ = awayTransactionDao.deleteRejectedITXCQ(tempAwayTxList.get(i).getLaneTxId(),
//					fileType);
//			if (deletedRecCountQ != null) {
//				logger.info("Deleted record in T_ITXC_IAG_Q with lane tx id:{}", tempAwayTxList.get(i).getLaneTxId());
//			}
//		}
		return oneTimeWriteFlag;
	}

	/**
	 * Validate Duplicate Check and process transaction
	 * 
	 * @param fileRecord
	 * @param tempAwayTxList
	 * @param fileOperationStatus
	 * @param tempIagStatList
	 * @param oneTimeWriteFlag
	 * @param i
	 * @return boolean
	 * @throws Exception
	 */
	private boolean validateDuplicateCheckAndProcessTx(List<String> fileRecord, List<AwayTransaction> tempAwayTxList,
			FileOperationStatus fileOperationStatus, List<AwayTransaction> tempIagStatList, boolean oneTimeWriteFlag,
			int i, String header, String fileType) throws Exception {
		if (duplicateCheckService.validateDuplicateTx(tempAwayTxList.get(i))) {
			logger.info("Process Valid transaction with lane tx id: {}", tempAwayTxList.get(i).getLaneTxId());
			MessageConversionDto tempMessageConversionDto = new MessageConversionDto();
			tempMessageConversionDto = intxConversionService.getIntxTransaction(tempAwayTxList.get(i), fileType);
			if (Boolean.TRUE.equals(tempMessageConversionDto.getIsValidLengthTx())) {
				fileRecord.add(tempMessageConversionDto.getTxMessage());
				tempIagStatList.add(tempAwayTxList.get(i));
				//logger.info("Record added : {}", tempAwayTxList.get(i));
				logger.info("Record added with EtcTrxSerialNum: {}", tempAwayTxList.get(i).getEtcTrxSerialNum());
				if (fileRecord.size() >= configVariable.getBatchSize()) {
					if (fileOperationStatus.isExistingFileFlag() && oneTimeWriteFlag) {
						int recordLength = 110;
						int headearLength = 39;
						Long position = (fileOperationStatus.getExistingRecordCount() * recordLength) + headearLength;
						if (fileType.equalsIgnoreCase(IntxConstant.FILE_EXTENSION_INTX)) {
							fileOperationStatus.setOperationSuccessFlag(
									fileOperation.overwriteFileData(fileOperationStatus.getTempFileName(), fileRecord,
											position, configVariable.getDstDirPathIntx()));
						} else if (fileType.equalsIgnoreCase(IntxConstant.FILE_EXTENSION_ITXN)) {
							fileOperationStatus.setOperationSuccessFlag(
									fileOperation.overwriteFileData(fileOperationStatus.getTempFileName(), fileRecord,
											position, configVariable.getDstDirPathItxn()));
						}
						oneTimeWriteFlag = false;
					} else {
						if (fileType.equalsIgnoreCase(IntxConstant.FILE_EXTENSION_INTX)) {
							fileOperationStatus.setOperationSuccessFlag(
									fileOperation.writeToFile(fileOperationStatus.getTempFileName(),
											configVariable.getDstDirPathIntx(), fileRecord));
						} else if (fileType.equalsIgnoreCase(IntxConstant.FILE_EXTENSION_ITXN)) {
							fileOperationStatus.setOperationSuccessFlag(
									fileOperation.writeToFile(fileOperationStatus.getTempFileName(),
											configVariable.getDstDirPathItxn(), fileRecord));
						}
					}
					// Update Dest Atp file Id
					fileOperationStatus.setIagStatusRecordList(tempIagStatList);
					// intxStatisticsService.updateIagStatistics(fileOperationStatus,header);
					fileRecord.clear();
					tempIagStatList.clear();
				}
			}

		} else {
			transactionDetailDao.updateEtcTxStatus(tempAwayTxList.get(i));
			Integer deletedRecCount = awayTransactionDao.deleteRejectedTx(tempAwayTxList.get(i).getLaneTxId(),
					fileType);
			if (deletedRecCount != null) {
				logger.info("Deleted Duplicate record in T_AGENCY_TX_PENDING with lane tx id:{}",
						tempAwayTxList.get(i).getLaneTxId());
			}

//			if (fileType.equalsIgnoreCase(IntxConstant.FILE_EXTENSION_ITXN)) {
//				// Delete record from T_AGENCY_TX_PENDING
//				Integer deletedRecCountQ = awayTransactionDao.deleteRejectedITXCQ(tempAwayTxList.get(i).getLaneTxId(),
//						fileType);
//				if (deletedRecCountQ != null) {
//					logger.info("Deleted record in T_ITXC_IAG_Q with lane tx id:{}",
//							tempAwayTxList.get(i).getLaneTxId());
//				}
//			}
		}
		return oneTimeWriteFlag;
	}

	/**
	 * File rename operation
	 * 
	 * @param fileName
	 * @param fileOperationStatus
	 */
	private void fileRenameOperation(String fileName, FileOperationStatus fileOperationStatus, String fileType) {
		logger.info("Calling rename file operation for file: {}", fileName);
		try {
			File file = null;
			if (fileType.equalsIgnoreCase(IntxConstant.FILE_EXTENSION_INTX)) {
				file = new File(configVariable.getDstDirPathIntx(), fileOperationStatus.getTempFileName());
			} else if (fileType.equalsIgnoreCase(IntxConstant.FILE_EXTENSION_ITXN)) {
				file = new File(configVariable.getDstDirPathItxn(), fileOperationStatus.getTempFileName());
			}
			if (fileOperationStatus.isOperationSuccessFlag()) {
				File renamefile = null;
				if (fileType.equalsIgnoreCase(IntxConstant.FILE_EXTENSION_INTX)) {
					renamefile = new File(configVariable.getDstDirPathIntx(), fileOperationStatus.getTempFileName()
							.replace(IntxConstant.FILE_EXTENSION_TEMP, IntxConstant.FILE_EXTENSION_INTX));
					
				} else if (fileType.equalsIgnoreCase(IntxConstant.FILE_EXTENSION_ITXN)) {
					renamefile = new File(configVariable.getDstDirPathItxn(), fileOperationStatus.getTempFileName()
							.replace(IntxConstant.FILE_EXTENSION_TEMP, IntxConstant.FILE_EXTENSION_ITXN));
				}
				fileOperationStatus.setFileName(renamefile.getName());
				fileOperationStatus.setOperationSuccessFlag(fileOperation.renameFile(file, renamefile));
				logger.info("Renamed file {} as {} file: {}", file.getAbsoluteFile(), fileType, renamefile.getAbsoluteFile());
			}
		} catch (Exception e) {
			logger.info("Renaming file operation failed for file: {}", fileName);
			e.printStackTrace();
			return;
		}
	}

	/**
	 * Overwrite header record
	 * 
	 * @param fileName
	 * @param fileRecordCount
	 * @param fileOperationStatus
	 */
	private void overwriteHeaderRecord(String fileName, Long fileRecordCount, FileOperationStatus fileOperationStatus,
			String fileType) {
		if (fileOperationStatus.isOperationSuccessFlag()) {
			if (fileType.equalsIgnoreCase(IntxConstant.FILE_EXTENSION_INTX)) {
				fileOperationStatus
						.setOperationSuccessFlag(fileOperation.overwriteFileData(fileOperationStatus.getTempFileName(),
								StringUtils.leftPad(String.valueOf(fileRecordCount), 8, "0"), 24,
								configVariable.getDstDirPathIntx()));
			} else if (fileType.equalsIgnoreCase(IntxConstant.FILE_EXTENSION_ITXN)) {
				fileOperationStatus
						.setOperationSuccessFlag(fileOperation.overwriteFileData(fileOperationStatus.getTempFileName(),
								StringUtils.leftPad(String.valueOf(fileRecordCount), 8, "0"), 24,
								configVariable.getDstDirPathItxn()));
			}
		}
	}

	/**
	 * Get Header
	 * 
	 * @param filetype
	 * @param fromAgency
	 * @param toAgency
	 * @param recordCount
	 * @param createdDateTime
	 * @return String
	 */
	private String getHeader(String filetype, String fromAgency, String toAgency, Long recordCount,
			String createdDateTime) {
		StringBuilder sb = new StringBuilder();
		Long seqNum = sequenceDao.getFileSequence(toAgency);
		if (seqNum != null) {
			sb.append(filetype).append(fromAgency).append(toAgency).append(createdDateTime)
					.append(StringUtils.leftPad(String.valueOf(recordCount), 8, "0"))
					.append(StringUtils.leftPad(String.valueOf(seqNum), 6, "0")).append("\n");
			return sb.toString();
		}
		return null;
	}

	/**
	 * Get File Name
	 * 
	 * @param devicePrefix
	 * @param dateTime
	 * @return String
	 */
	private String getFileName(String devicePrefix, String dateTime, String fileType) {
		StringBuilder sb = new StringBuilder();
		if (fileType.equalsIgnoreCase(IntxConstant.FILE_EXTENSION_INTX)) {
			sb.append(IntxConstant.CONSTANT_PARENT_AGENCY_ID_008).append(IntxConstant.CONSTANT_UNDERSCORE)
					.append(devicePrefix.trim()).append(IntxConstant.CONSTANT_UNDERSCORE).append(dateTime)
					.append(IntxConstant.CONSTANT_DOT).append(IntxConstant.FILE_EXTENSION_INTX);
		} else {
			sb.append(IntxConstant.CONSTANT_PARENT_AGENCY_ID_008).append(IntxConstant.CONSTANT_UNDERSCORE)
					.append(devicePrefix.trim()).append(IntxConstant.CONSTANT_UNDERSCORE).append(dateTime)
					.append(IntxConstant.CONSTANT_DOT).append(IntxConstant.FILE_EXTENSION_ITXN);
		}
		return sb.toString();
	}
	
	/**
	 * Insert into Agency Tx Pending
	 * 
	 * @param tempAwayTxList
	 */
	public void updateAgencyTxPending(List<AwayTransaction> tempAwayTxList) throws Exception {
		// Insert in t_agency_tx_pending
		logger.info("Insert Intx in AgencyTxPending...");
		for (AwayTransaction awayTx : tempAwayTxList) {
			awayTx.setTxType("P");
			awayTx.setTxSubType("T");
			awayTx.setTxStatus((long) 502);
			awayTransactionDao.insertIntxInAgencyPending(awayTx);
		}
		logger.info("Inserted Intx in AgencyTxPending.");
	}

}
