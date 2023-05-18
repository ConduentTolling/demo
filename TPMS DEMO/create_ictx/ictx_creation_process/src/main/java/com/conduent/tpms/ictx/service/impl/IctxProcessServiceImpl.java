package com.conduent.tpms.ictx.service.impl;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.conduent.tpms.ictx.constants.IctxConstant;
import com.conduent.tpms.ictx.dao.AwayTransactionDao;
import com.conduent.tpms.ictx.dao.IagFileStatisticsDao;
import com.conduent.tpms.ictx.dao.IagFileStatsDao;
import com.conduent.tpms.ictx.dao.QatpStatisticsDao;
import com.conduent.tpms.ictx.dao.ReconciliationCheckPointDao;
import com.conduent.tpms.ictx.dao.SequenceDao;
import com.conduent.tpms.ictx.dao.TransactionDetailDao;
import com.conduent.tpms.ictx.dto.AtpMessageDto;
import com.conduent.tpms.ictx.dto.FileOperationStatus;
import com.conduent.tpms.ictx.dto.MessageConversionDto;
import com.conduent.tpms.ictx.model.Agency;
import com.conduent.tpms.ictx.model.AwayTransaction;
import com.conduent.tpms.ictx.model.ConfigVariable;
import com.conduent.tpms.ictx.model.IagFileStats;
import com.conduent.tpms.ictx.model.Lane;
import com.conduent.tpms.ictx.model.Plaza;
import com.conduent.tpms.ictx.model.QatpStatistics;
import com.conduent.tpms.ictx.model.ReconciliationCheckPoint;
import com.conduent.tpms.ictx.service.AtpMessageFormatService;
import com.conduent.tpms.ictx.service.DuplicateCheckService;
import com.conduent.tpms.ictx.service.IctxConversionService;
import com.conduent.tpms.ictx.service.IctxProcessService;
import com.conduent.tpms.ictx.service.IctxStatisticsService;
import com.conduent.tpms.ictx.service.MessagePublisherService;
import com.conduent.tpms.ictx.utility.EtcTxStatusHelper;
import com.conduent.tpms.ictx.utility.FileOperation;
import com.conduent.tpms.ictx.utility.StaticDataLoad;


/**
 * ICTX Process Service Implementation
 * 
 * @author deepeshb
 *
 */
@Service
public class IctxProcessServiceImpl implements IctxProcessService {

	private static final Logger logger = LoggerFactory.getLogger(IctxProcessServiceImpl.class);

	@Autowired
	StaticDataLoad staticDataLoad;

	@Autowired
	IctxConversionService ictxConversionService;

	@Autowired
	ConfigVariable configVariable;

	@Autowired
	AwayTransactionDao awayTransactionDao;

	@Autowired
	FileOperation fileOperation;

	@Autowired
	SequenceDao sequenceDao;

	@Autowired
	IagFileStatisticsDao iagFileStatisticsDao;
	
	@Autowired
	IagFileStatsDao iagFileStatsDao;

	@Autowired
	QatpStatisticsDao qatpStatisticsDao;

	@Autowired
	AtpMessageFormatService atpMessageFormatService;

	@Autowired
	MessagePublisherService messagePublisherService;

	@Autowired
	TransactionDetailDao transactionDetailDao;

	@Autowired
	EtcTxStatusHelper etcTxStatusHelper;

	@Autowired
	ReconciliationCheckPointDao reconciliationCheckPointDao;

	@Autowired
	IctxStatisticsService ictxStatisticsService;

	@Autowired
	DuplicateCheckService duplicateCheckService;
	
	


	/**
	 * To start the processing of transaction messsage
	 * 
	 * @throws Exception
	 */
	@Override
	//@Async
	public void process(Long awayAgencyId, String fileType) throws Exception {
		// Get the agency prefix
		logger.info("Get the agency prefix {}",awayAgencyId);
		Agency tempAgency = staticDataLoad.getAgencyById(awayAgencyId);
		logger.info("Got the agency {}",tempAgency);
		List<String> fileRecord = new ArrayList<>();
		List<AwayTransaction> tempAwayTxList = new ArrayList<>();

		if (tempAgency != null) {
			// Get the records from table based on agency prefix
			logger.info("Get the records from table based on agency prefix");
			if(fileType.equalsIgnoreCase(IctxConstant.FILE_EXTENSION_ITXC)) {
				List<AwayTransaction> tempAwayTxListITXC = awayTransactionDao
						.getCorrAwayTransactionByDevicePrefix(tempAgency.getDevicePrefix());
				//tasks for new release
				//1.062 changes fixes  on account agency id
				//fetch data from account_AGENCY  		
				//toll amount will be api call
				//code cleanup
			
				
				//getPlateNumber()
				//getPlateState()
				//Need to insert it in t_agency_tx_pending
				/*
				 * tempAwayTxListITXC.stream().forEach(e -> { e.setTxType("O");
				 * e.setTxSubType("T"); //e.setEtcTxStatus(502); e.setTxStatus(502);
				 * awayTransactionDao.insertITXCInAgencyPending(e); });
				 */
				
				for(AwayTransaction e:tempAwayTxListITXC) {
					e.setTxType("O");
					e.setTxSubType("T");
					//e.setEtcTxStatus(502);
					e.setTxStatus(502);
					awayTransactionDao.insertITXCInAgencyPending(e);
					tempAwayTxList.add(e);
				}

			}else {

				tempAwayTxList = awayTransactionDao
						.getAwayTransactionByAgencyId(awayAgencyId);
			}
			
			logger.info("Number of {} records :{} for Agency Id: {}", fileType, tempAwayTxList.size(), tempAgency.getAgencyId());
			
			if (!tempAwayTxList.isEmpty()) {
				processAwayTransaction(awayAgencyId, tempAgency, fileRecord, tempAwayTxList, fileType);
			}
		}
	}

	/**
	 * To start the processing of transaction messsage
	 * 
	 * @throws Exception
	 */
	@Override
	//@Async
	public void processHubFile(String fileType) throws Exception {
		// Get the agency prefix
		
		List<String> fileRecord = new ArrayList<>();
		List<AwayTransaction> tempAwayTxList = new ArrayList<>();

		if (fileType != null) {
			// Get the records from table based on agency prefix
			logger.info("Get the records from table for hub file");
			if(fileType.equalsIgnoreCase(IctxConstant.FILE_EXTENSION_ITXC)) {
				List<AwayTransaction> tempAwayTxListITXC = awayTransactionDao
						.getCorrAwayTransactionHub();
				
				for(AwayTransaction e:tempAwayTxListITXC) {
					e.setTxType("O");
					e.setTxSubType("T");
					//e.setEtcTxStatus(502);
					e.setTxStatus(502);
					awayTransactionDao.insertITXCInAgencyPending(e);
					tempAwayTxList.add(e);
				}

			}else {

				tempAwayTxList = awayTransactionDao
						.getAwayTransactionHub();
			}
			
			logger.info("Number of {} records :{} for HUB File is", fileType, tempAwayTxList.size());
			
			if (!tempAwayTxList.isEmpty()) {
				processAwayTransactionHub(fileRecord, tempAwayTxList, fileType);
			}
		}
	}
	
	
	private void processAwayTransactionHub(List<String> fileRecord, List<AwayTransaction> tempAwayTxList,
			String fileType) throws Exception {
		FileOperationStatus fileOperationStatus = new FileOperationStatus();

		fileOperationStatus.setFromAgency(IctxConstant.CONSTANT_PARENT_AGENCY_ID_0008);
		fileOperationStatus.setToAgency(IctxConstant.CONSTANT_PARENT_HUB_9003);

		String createdDateTimeHeader;
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
			if(fileType.equalsIgnoreCase(IctxConstant.FILE_EXTENSION_ICTX)) {
				header = getHeader(IctxConstant.FILE_EXTENSION_ICTX, IctxConstant.CONSTANT_PARENT_AGENCY_ID_0008,
						IctxConstant.CONSTANT_PARENT_HUB_9003.trim(), 0L, fileName.substring(8, 22));
			}else {
				header = getHeader(IctxConstant.FILE_EXTENSION_ITXC, IctxConstant.CONSTANT_PARENT_AGENCY_ID_0008,
						IctxConstant.CONSTANT_PARENT_HUB_9003.trim(), 0L, fileName.substring(8, 22));//need to fetch hub id from t_process param
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
			createdDateTimeHeader = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'"));
			fileName = getFileName(IctxConstant.CONSTANT_PARENT_HUB_9003, createdDateTime,fileType);
			if(fileType.equalsIgnoreCase(IctxConstant.FILE_EXTENSION_ICTX)) {
				header = getHeader(IctxConstant.FILE_EXTENSION_ICTX, IctxConstant.CONSTANT_PARENT_AGENCY_ID_0008,
						IctxConstant.CONSTANT_PARENT_HUB_9003.trim(), 0L, createdDateTimeHeader);
			}else {
				header = getHeader(IctxConstant.FILE_EXTENSION_ITXC, IctxConstant.CONSTANT_PARENT_AGENCY_ID_0008,
						IctxConstant.CONSTANT_PARENT_HUB_9003.trim(), 0L, createdDateTimeHeader);
			}
			logger.info("header: {}", header);
			fileOperationStatus.setExistingFileFlag(false);
		}
		String tempFilename = "";
		if(fileType.equalsIgnoreCase(IctxConstant.FILE_EXTENSION_ICTX)) {
		    tempFilename = fileName.replace(IctxConstant.FILE_EXTENSION_ICTX, IctxConstant.FILE_EXTENSION_TEMP);
		}else {
			tempFilename = fileName.replace(IctxConstant.FILE_EXTENSION_ITXC, IctxConstant.FILE_EXTENSION_TEMP);
		}
		fileOperationStatus.setTempFileName(tempFilename);
		fileOperationStatus.setFileName(fileName);

		if (header != null) {
			processTransactionHub(fileRecord, tempAwayTxList, fileOperationStatus, fileName, header, fileType);

		} else {
			fileOperationStatus.setOperationSuccessFlag(false);
		}

		if (!fileOperationStatus.isOperationSuccessFlag()) {
			logger.info("File operation failed for HUB File ,filename: {} ", fileName);
		}
	}

	/**
	 * Process away transaction
	 * 
	 * @param awayAgencyId
	 * @param tempAgency
	 * @param fileRecord
	 * @param tempAwayTxList
	 * @param tempAwayCorrList 
	 * @throws Exception
	 */
	private void processAwayTransaction(Long awayAgencyId, Agency tempAgency, List<String> fileRecord,
			List<AwayTransaction> tempAwayTxList, String fileType) throws Exception {
		FileOperationStatus fileOperationStatus = new FileOperationStatus();

		fileOperationStatus.setFromAgency(IctxConstant.CONSTANT_PARENT_AGENCY_ID_0008);
		fileOperationStatus.setToAgency(tempAgency.getDevicePrefix());

		String createdDateTime;
		String createdDateTimeHeader;
		String fileName = null;
		String header = null;

		// Check in T_RECONCILATION_CHECKPOINT if any of file exists in S,P state
		ReconciliationCheckPoint tempReconciliationCheckPoint = reconciliationCheckPointDao
				.getFileInfo(fileOperationStatus.getFromAgency(), fileOperationStatus.getToAgency(), fileType);

		if (tempReconciliationCheckPoint != null) {
			// In case of yes
			logger.info("tempReconciliationCheckPoint not null: {}", tempReconciliationCheckPoint);
			fileName = tempReconciliationCheckPoint.getFileName();
			if(fileType.equalsIgnoreCase(IctxConstant.FILE_EXTENSION_ICTX)) {
				header = getHeader(IctxConstant.FILE_EXTENSION_ICTX, IctxConstant.CONSTANT_PARENT_AGENCY_ID_0008,
					tempAgency.getDevicePrefix().trim(), 0L, fileName.substring(8, 22));
			}else {
				header = getHeader(IctxConstant.FILE_EXTENSION_ITXC, IctxConstant.CONSTANT_PARENT_AGENCY_ID_0008,
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
			createdDateTimeHeader = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'"));
			fileName = getFileName(tempAgency.getDevicePrefix(), createdDateTime,fileType);
			if(fileType.equalsIgnoreCase(IctxConstant.FILE_EXTENSION_ICTX)) {
				header = getHeader(IctxConstant.FILE_EXTENSION_ICTX, IctxConstant.CONSTANT_PARENT_AGENCY_ID_0008,
					tempAgency.getDevicePrefix().trim(), 0L, createdDateTimeHeader);
			}else {
				header = getHeader(IctxConstant.FILE_EXTENSION_ITXC, IctxConstant.CONSTANT_PARENT_AGENCY_ID_0008,
						tempAgency.getDevicePrefix().trim(), 0L, createdDateTimeHeader);
			}
			logger.info("header: {}", header);
			fileOperationStatus.setExistingFileFlag(false);
		}
		String tempFilename = "";
		if(fileType.equalsIgnoreCase(IctxConstant.FILE_EXTENSION_ICTX)) {
		    tempFilename = fileName.replace(IctxConstant.FILE_EXTENSION_ICTX, IctxConstant.FILE_EXTENSION_TEMP);
		}else {
			tempFilename = fileName.replace(IctxConstant.FILE_EXTENSION_ITXC, IctxConstant.FILE_EXTENSION_TEMP);
		}
		fileOperationStatus.setTempFileName(tempFilename);
		fileOperationStatus.setFileName(fileName);

		if (header != null) {
			processTransaction(tempAgency, fileRecord, tempAwayTxList, fileOperationStatus, fileName, header, fileType);

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
	private void processTransaction(Agency tempAgency, List<String> fileRecord, List<AwayTransaction> tempAwayTxList,
			FileOperationStatus fileOperationStatus, String fileName, String header, String fileType) throws Exception {
		logger.info("Process transaction for Agency Id: {}, fileName: {}", tempAgency.getAgencyId(), fileName);
		
		processTx(fileRecord, tempAwayTxList, fileName, header, fileOperationStatus, fileType);
		logger.info("Process transaction for Agency Id: {}, fileName: {}, isNoRecordFlag: {}", tempAgency.getAgencyId(), fileName, fileOperationStatus.isNoRecordFlag());
		if (!fileOperationStatus.isNoRecordFlag()) {
			Long fileRecordCount = 0L;
			if(fileType.equalsIgnoreCase(IctxConstant.FILE_EXTENSION_ICTX)) {
				fileRecordCount = fileOperation.getRecordCount(fileOperationStatus.getTempFileName(),
					configVariable.getDstDirPath());
			}else {
				fileRecordCount = fileOperation.getRecordCount(fileOperationStatus.getTempFileName(),
						configVariable.getDstDirPathItxc());
			}
			logger.info("Transaction Record Count: {} for Agency Id: {}, fileName: {}", fileRecordCount,
					tempAgency.getAgencyId(), fileName);
			overwriteHeaderRecord(fileName, fileRecordCount, fileOperationStatus,fileType);
			updateRecordCounts(fileName, fileRecordCount);
			fileRenameOperation(fileName, fileOperationStatus,fileType);
			updateReconciliationFileStatus(fileOperationStatus);
		}
	}

	private void processTransactionHub(List<String> fileRecord, List<AwayTransaction> tempAwayTxList,
			FileOperationStatus fileOperationStatus, String fileName, String header, String fileType) throws Exception {
		logger.info("Process transaction for HUB, fileName: {}", fileName);
		
		processTx(fileRecord, tempAwayTxList, fileName, header, fileOperationStatus, fileType);
		logger.info("Process transaction for HUB, fileName: {}, isNoRecordFlag: {}", fileName, fileOperationStatus.isNoRecordFlag());
		if (!fileOperationStatus.isNoRecordFlag()) {
			Long fileRecordCount = 0L;
			if(fileType.equalsIgnoreCase(IctxConstant.FILE_EXTENSION_ICTX)) {
				fileRecordCount = fileOperation.getRecordCount(fileOperationStatus.getTempFileName(),
					configVariable.getDstDirPath());
			}else {
				fileRecordCount = fileOperation.getRecordCount(fileOperationStatus.getTempFileName(),
						configVariable.getDstDirPathItxc());
			}
			logger.info("Transaction Record Count: {} for HUB, fileName: {}", fileRecordCount, fileName);
			overwriteHeaderRecordHub(fileName, fileRecordCount, fileOperationStatus,fileType);
			updateRecordCounts(fileName, fileRecordCount);
			fileRenameOperation(fileName, fileOperationStatus,fileType);
			updateReconciliationFileStatus(fileOperationStatus);
		}
	}
	
	private void updateRecordCounts(String fileName, Long fileRecordCount) {
		
		iagFileStatsDao.updateRecordCount( fileName, fileRecordCount);
		iagFileStatisticsDao.updateRecordCount( fileName, fileRecordCount);
		
	}
	
	private void updateReconciliationFileStatus(FileOperationStatus fileOperationStatus) {
		if (fileOperationStatus.isOperationSuccessFlag()) {
			reconciliationCheckPointDao.updateStatus(fileOperationStatus.getFileName(),
					IctxConstant.FILE_STATUS_COMPLETE);
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
			String header, FileOperationStatus fileOperationStatus,String fileType) throws Exception {
		List<AwayTransaction> tempIagStatList = new ArrayList<>();
		IagFileStats iagFileStats= new IagFileStats();
		List<AtpMessageDto> tempAtpMessageDto = new ArrayList<>();
		boolean oneTimeWriteflag = true;
		if (!fileOperationStatus.isExistingFileFlag()) {
			fileRecord.add(header);
		}
		
			for (int i = 0; i < tempAwayTxList.size() && fileOperationStatus.isOperationSuccessFlag(); i++) {
				oneTimeWriteflag = validateAndProcessTx(fileRecord, tempAwayTxList, fileOperationStatus, tempIagStatList,
						tempAtpMessageDto, oneTimeWriteflag, i,header, fileType);
		
		
		if (!fileRecord.isEmpty() && fileOperationStatus.isOperationSuccessFlag()) {
			logger.info("fileRecord.size(): {} for header.equalsIgnoreCase(fileRecord.get(0)): {}, I: {}", fileRecord.size(),
					header.equalsIgnoreCase(fileRecord.get(0)), i);
			if (fileRecord.size() <= 1 && (header.equalsIgnoreCase(fileRecord.get(0)) || fileRecord.size()==0)
					 && i == (tempAwayTxList.size()-1)) {
				fileRecord.clear();
				tempIagStatList.clear();
				fileOperationStatus.setNoRecordFlag(true);
			} else if (fileRecord.size() >= 1 && !header.equalsIgnoreCase(fileRecord.get(fileRecord.size()-1))) {
				if (fileOperationStatus.isExistingFileFlag() && oneTimeWriteflag) {
					int recordLength = IctxConstant.RECORD_LENGTH;
					int headearLength = IctxConstant.HEADER_LENGTH;
					Long position = (fileOperationStatus.getExistingRecordCount() * recordLength) + headearLength + 1;
					if(fileType.equalsIgnoreCase(IctxConstant.FILE_EXTENSION_ICTX)) {
						fileOperationStatus.setOperationSuccessFlag(
								fileOperation.overwriteFileData(fileOperationStatus.getTempFileName(), fileRecord, position,
										configVariable.getDstDirPath()));
					}else {
						fileOperationStatus.setOperationSuccessFlag(
								fileOperation.overwriteFileData(fileOperationStatus.getTempFileName(), fileRecord, position,
										configVariable.getDstDirPathItxc()));
					}
					oneTimeWriteflag = false;
				} else {
					if(fileType.equalsIgnoreCase(IctxConstant.FILE_EXTENSION_ICTX)) {
						fileOperationStatus.setOperationSuccessFlag(fileOperation.writeToFile(
								fileOperationStatus.getTempFileName(), configVariable.getDstDirPath(), fileRecord));
					}else {
						fileOperationStatus.setOperationSuccessFlag(fileOperation.writeToFile(
								fileOperationStatus.getTempFileName(), configVariable.getDstDirPathItxc(), fileRecord));
					}
				}
				// Update Dst Atp file Id

				fileOperationStatus.setIagStatusRecordList(tempIagStatList);
				ictxStatisticsService.updateIagStatistics(fileOperationStatus,header);
				
				fileOperationStatus.setNoRecordFlag(false);
				fileRecord.clear();
				tempIagStatList.clear();

			}
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
	 * @param oneTimeWriteflag
	 * @param i
	 * @return boolean
	 * @throws Exception
	 */
	private boolean validateAndProcessTx(List<String> fileRecord, List<AwayTransaction> tempAwayTxList,
			FileOperationStatus fileOperationStatus, List<AwayTransaction> tempIagStatList,
			List<AtpMessageDto> tempAtpMessageDto, boolean oneTimeWriteflag, int i,String header,String fileType) throws Exception {
		if (validateTx(tempAwayTxList.get(i))) {
			try {
			oneTimeWriteflag = validateDuplicateCheckAndProcessTx(fileRecord, tempAwayTxList, fileOperationStatus,
					tempIagStatList, oneTimeWriteflag, i,header, fileType, tempAtpMessageDto);
			logger.info("oneTimeWriteflag:{}",oneTimeWriteflag);
			}catch(Exception e) {
				e.printStackTrace();
				
			}

		} else {
			// Push Rejected Tx to ATP Queue and update txType and txSubType in
			// Convert to ATP Queue Format
			logger.info("Push Rejected Tx to ATP Queue. Tx: {}",tempAwayTxList.get(i));
			tempAtpMessageDto.add(atpMessageFormatService.getAtpMessage(tempAwayTxList.get(i), fileType));
			messagePublisherService.publishMessageToStream(tempAtpMessageDto);
			// Update T_TRAN_DETAIL
			transactionDetailDao.updateTransactionDetail(tempAwayTxList.get(i));
			
			// Delete record from T_AGENCY_TX_PENDING
			Integer deletedRecCount = awayTransactionDao.deleteRejectedTx(tempAwayTxList.get(i).getLaneTxId(), fileType);
			if (deletedRecCount != null) {
				logger.info("Deleted record in T_AGENCY_TX_PENDING with lane tx id:{}",
						tempAwayTxList.get(i).getLaneTxId());
			}
			
		}
		
		fileOperationStatus.setIagStatusRecordList(tempIagStatList);
		logger.info("Total Elements in IagStatusRecordList: {}",tempIagStatList.size());
		
		if(fileType.equalsIgnoreCase(IctxConstant.FILE_EXTENSION_ITXC)) {
			// Delete record from T_AGENCY_TX_PENDING
			Integer deletedRecCountQ = awayTransactionDao.deleteRejectedITXCQ(tempAwayTxList.get(i).getLaneTxId(), fileType);
			if (deletedRecCountQ != null) {
				logger.info("Deleted record in T_ITXC_IAG_Q with lane tx id:{}",
						tempAwayTxList.get(i).getLaneTxId());
			}
		}
		return oneTimeWriteflag;
	}

	/**
	 * Validate Duplicate Check and process transaction
	 * 
	 * @param fileRecord
	 * @param tempAwayTxList
	 * @param fileOperationStatus
	 * @param tempIagStatList
	 * @param oneTimeWriteflag
	 * @param i
	 * @param tempAtpMessageDto 
	 * @return boolean
	 * @throws Exception
	 */
	private boolean validateDuplicateCheckAndProcessTx(List<String> fileRecord, List<AwayTransaction> tempAwayTxList,
			FileOperationStatus fileOperationStatus, List<AwayTransaction> tempIagStatList, boolean oneTimeWriteflag,
			int i,String header,String fileType, List<AtpMessageDto> tempAtpMessageDto) throws Exception {
		if (duplicateCheckService.validateDuplicateTx(tempAwayTxList.get(i))) {
			logger.info("Process Valid transaction with lane tx id: {}", tempAwayTxList.get(i).getLaneTxId());
			MessageConversionDto tempMessageConversionDto = new MessageConversionDto();
			tempMessageConversionDto = ictxConversionService.getIctxTransaction(tempAwayTxList.get(i),fileType);
			if (Boolean.TRUE.equals(tempMessageConversionDto.getIsValidLengthTx())) {
				fileRecord.add(tempMessageConversionDto.getTxMessage());
				tempIagStatList.add(tempAwayTxList.get(i));
				logger.info("Record added : {}", tempAwayTxList.get(i));
				if (fileRecord.size() >= configVariable.getBatchSize()) {
					if (fileOperationStatus.isExistingFileFlag() && oneTimeWriteflag) {
						int recordLength = 110;
						int headearLength = 39;
						Long position = (fileOperationStatus.getExistingRecordCount() * recordLength) + headearLength;
						if(fileType.equalsIgnoreCase(IctxConstant.FILE_EXTENSION_ICTX)) {
							fileOperationStatus.setOperationSuccessFlag(
									fileOperation.overwriteFileData(fileOperationStatus.getTempFileName(), fileRecord,
											position, configVariable.getDstDirPath()));
						}else {
							fileOperationStatus.setOperationSuccessFlag(
									fileOperation.overwriteFileData(fileOperationStatus.getTempFileName(), fileRecord,
											position, configVariable.getDstDirPathItxc()));
						}
						oneTimeWriteflag = false;
					} else {
						if(fileType.equalsIgnoreCase(IctxConstant.FILE_EXTENSION_ICTX)) {
							fileOperationStatus.setOperationSuccessFlag(fileOperation.writeToFile(
									fileOperationStatus.getTempFileName(), configVariable.getDstDirPath(), fileRecord));
						}else {
							fileOperationStatus.setOperationSuccessFlag(fileOperation.writeToFile(
									fileOperationStatus.getTempFileName(), configVariable.getDstDirPathItxc(), fileRecord));
						}
					}
					// Update Dest Atp file Id
					fileOperationStatus.setIagStatusRecordList(tempIagStatList);
					ictxStatisticsService.updateIagStatistics(fileOperationStatus,header);
					fileRecord.clear();
					tempIagStatList.clear();
				}
			}

		} else {
			// Push Rejected Tx to ATP Queue and update txType and txSubType in
			// Convert to ATP Queue Format
			logger.info("Push Duplicate Rejected Tx to ATP Queue. Tx: {}",tempAwayTxList.get(i));
			tempAtpMessageDto.add(atpMessageFormatService.getAtpMessage(tempAwayTxList.get(i), fileType));
			messagePublisherService.publishMessageToStream(tempAtpMessageDto);
			// Update T_TRAN_DETAIL
			//transactionDetailDao.updateEtcTxStatus(tempAwayTxList.get(i));
			//transactionDetailDao.updateTransactionDetail(tempAwayTxList.get(i));
			Integer deletedRecCount = awayTransactionDao.deleteRejectedTxNew(tempAwayTxList.get(i).getLaneTxId(), fileType, tempAwayTxList.get(i).getTxExternRefNo());
			if (deletedRecCount != null) {
				logger.info("Deleted Duplicate record in T_AGENCY_TX_PENDING with lane tx id:{}",
						tempAwayTxList.get(i).getLaneTxId());
			}
			
			if(fileType.equalsIgnoreCase(IctxConstant.FILE_EXTENSION_ITXC)) {
				// Delete record from T_AGENCY_TX_PENDING
							Integer deletedRecCountQ = awayTransactionDao.deleteRejectedITXCQ(tempAwayTxList.get(i).getLaneTxId(), fileType);
							if (deletedRecCountQ != null) {
								logger.info("Deleted record in T_ITXC_IAG_Q with lane tx id:{}",
										tempAwayTxList.get(i).getLaneTxId());
							}
				}
		}
		return oneTimeWriteflag;
	}
	
	
	/**
	 * Validate transaction
	 * 
	 * @param tempAwayTx
	 * @return boolean
	 */
	private boolean validateTx(AwayTransaction tempAwayTx) {
		boolean resultFlag = false;
		if (tempAwayTx != null) {
			resultFlag = validateExitInfo(tempAwayTx);
			if (resultFlag) {
				resultFlag = validateEntryInfo(tempAwayTx);
			//	if (resultFlag) {
					//resultFlag = validateHomePlazaLaneInfo(tempAwayTx);
				//}
			}
		}
		return resultFlag;
	}

	/**
	 * Validate entry info
	 * 
	 * @param tempAwayTx
	 * @return Boolean
	 */
	private Boolean validateEntryInfo(AwayTransaction tempAwayTx) {
		String tempTollSysType = tempAwayTx.getTollSystemType() != null ? tempAwayTx.getTollSystemType() : null;
		if (IctxConstant.TOLL_SYSTEM_TYPE_C.equalsIgnoreCase(tempTollSysType)) {
			if (!validateEntryLaneId(tempAwayTx)) {
				tempAwayTx.setTxType("R");
				tempAwayTx.setTxSubType("L");
				logger.info("Invalid entry lane for lane tx id: {}", tempAwayTx.getLaneTxId());
				return false;
			}
			if (!validateEntryPlazaId(tempAwayTx)) {
				tempAwayTx.setTxType("R");
				tempAwayTx.setTxSubType("L");
				logger.info("Invalid entry plaza for lane tx id: {}", tempAwayTx.getLaneTxId());
				return false;
			}
			if (!validateEntryTimestamp(tempAwayTx)) {
				tempAwayTx.setTxType("R");
				tempAwayTx.setTxSubType("D");
				logger.info("Invalid entry transaction timestamp for lane tx id: {}", tempAwayTx.getLaneTxId());
				return false;
			}
		}
		return true;
	}

	/**
	 * Validate exit info
	 * 
	 * @param tempAwayTx
	 * @return Boolean
	 */
	private Boolean validateExitInfo(AwayTransaction tempAwayTx) {
		if (!validateExitLaneId(tempAwayTx)) {
			tempAwayTx.setTxType("R");
			tempAwayTx.setTxSubType("L");
			logger.info("Invalid exit lane for lane tx id: {}", tempAwayTx.getLaneTxId());
			return false;
		}
		if (!validateExitPlazaId(tempAwayTx)) {
			tempAwayTx.setTxType("R");
			tempAwayTx.setTxSubType("L");
			logger.info("Invalid exit plaza for lane tx id: {}", tempAwayTx.getLaneTxId());
			return false;
		}
		if (!validateTxDate(tempAwayTx)) {
			tempAwayTx.setTxType("R");
			tempAwayTx.setTxSubType("D");
			logger.info("Invalid transaction date for lane tx id: {}", tempAwayTx.getLaneTxId());
			return false;
		}
		if (!validateTxTimestamp(tempAwayTx)) {
			tempAwayTx.setTxType("R");
			tempAwayTx.setTxSubType("D");
			logger.info("Invalid transaction timestamp for lane tx id: {}", tempAwayTx.getLaneTxId());
			return false;
		}
		return true;
	}

	private Boolean validateHomePlazaLaneInfo(AwayTransaction tempAwayTx) {
		if (!validateHomePlazaId(tempAwayTx)) {
			tempAwayTx.setTxType("R");
			tempAwayTx.setTxSubType("L");
			logger.info("Invalid Home Plaza Id for lane tx id: {}", tempAwayTx.getLaneTxId());
			return false;
		}
		if (!validateHomeLaneId(tempAwayTx)) {
			tempAwayTx.setTxType("R");
			tempAwayTx.setTxSubType("L");
			logger.info("Invalid Home lane Id for lane tx id: {}", tempAwayTx.getLaneTxId());
			return false;
		}
		return true;
	}
	
	
	private boolean validateHomeLaneId(AwayTransaction tempAwayTx) {
		Integer tempLaneId = tempAwayTx.getLaneId() != null ? tempAwayTx.getLaneId() : null;
		if (tempLaneId != null && tempLaneId != 0) {
			Lane tempLane = staticDataLoad.getHomeLaneById(tempLaneId);
			if (tempLane != null) {
				return true;
			}
		}
		return false;
	}

	private boolean validateHomePlazaId(AwayTransaction tempAwayTx) {
		Integer tempPlazaId = tempAwayTx.getEntryPlazaId() != null ? tempAwayTx.getPlazaId() : null; // in case of tx type TC, UX
		if (tempPlazaId != null && tempPlazaId != 0) {
			Lane tempPlaza = staticDataLoad.getHomePlazaById(tempPlazaId);
			if (tempPlaza != null) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Validate tx timestamp
	 * 
	 * @param tempAwayTx
	 * @return boolean
	 */
	private boolean validateTxTimestamp(AwayTransaction tempAwayTx) {
		if (tempAwayTx.getTxTimestampLocalDateTime() != null) {
			return (tempAwayTx.getTxTimestampLocalDateTime().isEqual(LocalDateTime.now())
					|| tempAwayTx.getTxTimestampLocalDateTime().isBefore(LocalDateTime.now()));
		}
		return false;
	}

	/**
	 * Validate entry lane id
	 * 
	 * @param tempAwayTx
	 * @return boolean
	 */
	private boolean validateEntryLaneId(AwayTransaction tempAwayTx) {
		Integer tempEntryLaneId = tempAwayTx.getEntryLaneId() != null ? tempAwayTx.getEntryLaneId() : null;
		if (tempEntryLaneId != null && tempEntryLaneId != 0) {
			Lane tempEntryLane = staticDataLoad.getLaneById(tempEntryLaneId);
			if (tempEntryLane != null) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Validate entry plaza id
	 * 
	 * @param tempAwayTx
	 * @return boolean
	 */
	private boolean validateEntryPlazaId(AwayTransaction tempAwayTx) {
		Integer tempEntryPlazaId = tempAwayTx.getEntryPlazaId() != null ? tempAwayTx.getEntryPlazaId() : null;
		if (tempEntryPlazaId != null && tempEntryPlazaId != 0) {
			Plaza tempEntryPlaza = staticDataLoad.getPlazaById(tempEntryPlazaId);
			if (tempEntryPlaza != null) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Validate entry timestamp
	 * 
	 * @param tempAwayTx
	 * @return
	 */
	private boolean validateEntryTimestamp(AwayTransaction tempAwayTx) {
		if (tempAwayTx.getEntryTimestampLocalDateTime() != null) {
			return (tempAwayTx.getEntryTimestampLocalDateTime().isEqual(LocalDateTime.now())
					|| tempAwayTx.getEntryTimestampLocalDateTime().isBefore(LocalDateTime.now()));
		}
		return false;

	}

	/**
	 * Validate tx date
	 * 
	 * @param tempAwayTx
	 * @return boolean
	 */
	private boolean validateTxDate(AwayTransaction tempAwayTx) {
		if (tempAwayTx.getTxDate() != null) {
			return (tempAwayTx.getTxDate().isEqual(LocalDate.now())
					|| tempAwayTx.getTxDate().isBefore(LocalDate.now()));
		}
		return false;
	}

	/**
	 * Validate exit lane id
	 * 
	 * @param tempAwayTx
	 * @return boolean
	 */
	private boolean validateExitLaneId(AwayTransaction tempAwayTx) {
		Integer tempLaneId = tempAwayTx.getLaneId() != null ? tempAwayTx.getLaneId() : null;
		if (tempLaneId != null && tempLaneId != 0) {
			Lane tempLane = staticDataLoad.getLaneById(tempLaneId);
			if (tempLane != null) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Validate exit plaza id
	 * 
	 * @param tempAwayTx
	 * @return boolean
	 */
	private boolean validateExitPlazaId(AwayTransaction tempAwayTx) {
		Integer tempPlazaId = tempAwayTx.getPlazaId() != null ? tempAwayTx.getPlazaId() : null;
		if (tempPlazaId != null && tempPlazaId != 0) {
			Plaza tempPlaza = staticDataLoad.getPlazaById(tempPlazaId);
			if (tempPlaza != null) {
				return true;
			}
		}
		return false;
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
			if(fileType.equalsIgnoreCase(IctxConstant.FILE_EXTENSION_ICTX)) {
				file = new File(configVariable.getDstDirPath(), fileOperationStatus.getTempFileName());
			}else {
				file = new File(configVariable.getDstDirPathItxc(), fileOperationStatus.getTempFileName());
			}
			if (fileOperationStatus.isOperationSuccessFlag()) {
				File renamefile = null;
				if(fileType.equalsIgnoreCase(IctxConstant.FILE_EXTENSION_ICTX)) {
					renamefile = new File(configVariable.getDstDirPath(), fileOperationStatus.getTempFileName()
							.replace(IctxConstant.FILE_EXTENSION_TEMP, IctxConstant.FILE_EXTENSION_ICTX));
				}else {
					renamefile = new File(configVariable.getDstDirPathItxc(), fileOperationStatus.getTempFileName()
							.replace(IctxConstant.FILE_EXTENSION_TEMP, IctxConstant.FILE_EXTENSION_ITXC));
				}
				fileOperationStatus.setFileName(renamefile.getName());
				fileOperationStatus.setOperationSuccessFlag(fileOperation.renameFile(file, renamefile));
				logger.info("Renamed file {} as ICTX file: {}", file.getAbsoluteFile(), renamefile.getAbsoluteFile());
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
	private void overwriteHeaderRecord(String fileName, Long fileRecordCount, FileOperationStatus fileOperationStatus, String fileType) {
		if (fileOperationStatus.isOperationSuccessFlag()) {
			if(fileType.equalsIgnoreCase(IctxConstant.FILE_EXTENSION_ICTX)) {
				fileOperationStatus.setOperationSuccessFlag(fileOperation.overwriteFileData(
						fileOperationStatus.getTempFileName(), StringUtils.leftPad(String.valueOf(fileRecordCount), IctxConstant.RECORD_COUNT_LENGTH_ICTX, "0"),
						IctxConstant.RECORD_COUNT_LOCATION_ICTX, configVariable.getDstDirPath()));//40
			}else {
				fileOperationStatus.setOperationSuccessFlag(fileOperation.overwriteFileData(
						fileOperationStatus.getTempFileName(), StringUtils.leftPad(String.valueOf(fileRecordCount), 8, "0"),
						24, configVariable.getDstDirPathItxc()));
			}
		}

	}
	
	/**
	 * Overwrite header record
	 * 
	 * @param fileName
	 * @param fileRecordCount
	 * @param fileOperationStatus
	 */
	private void overwriteHeaderRecordHub(String fileName, Long fileRecordCount, FileOperationStatus fileOperationStatus, String fileType) {
		if (fileOperationStatus.isOperationSuccessFlag()) {
			if(fileType.equalsIgnoreCase(IctxConstant.FILE_EXTENSION_ICTX)) {
				fileOperationStatus.setOperationSuccessFlag(fileOperation.overwriteFileData(
						fileOperationStatus.getTempFileName(), StringUtils.leftPad(String.valueOf(fileRecordCount), IctxConstant.RECORD_COUNT_LENGTH_ICTX, "0"),
						IctxConstant.RECORD_COUNT_LOCATION_ICTX, configVariable.getDstDirPath()));
			}else {
				fileOperationStatus.setOperationSuccessFlag(fileOperation.overwriteFileData(
						fileOperationStatus.getTempFileName(), StringUtils.leftPad(String.valueOf(fileRecordCount), 8, "0"),
						20, configVariable.getDstDirPathItxc()));
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
			sb.append(filetype).append(IctxConstant.CONSTANT_VERSION).append(fromAgency).append(StringUtils.leftPad(String.valueOf(toAgency), 4, "0")).append(createdDateTime)
					.append(StringUtils.leftPad(String.valueOf(recordCount), 8, "0"))
					.append(StringUtils.leftPad(String.valueOf(seqNum), 12, "0")).append("\n");
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
		if(fileType.equalsIgnoreCase(IctxConstant.FILE_EXTENSION_ICTX)) {
			sb.append(IctxConstant.CONSTANT_PARENT_AGENCY_ID_0008).append(IctxConstant.CONSTANT_UNDERSCORE)
				.append(devicePrefix.trim()).append(IctxConstant.CONSTANT_UNDERSCORE).append(dateTime)
				.append(IctxConstant.CONSTANT_DOT).append(IctxConstant.FILE_EXTENSION_ICTX);
		}else {
			sb.append(IctxConstant.CONSTANT_PARENT_AGENCY_ID_0008).append(IctxConstant.CONSTANT_UNDERSCORE)
			.append(devicePrefix.trim()).append(IctxConstant.CONSTANT_UNDERSCORE).append(dateTime)
			.append(IctxConstant.CONSTANT_DOT).append(IctxConstant.FILE_EXTENSION_ITXC);
		}
		return sb.toString();
	}

	
}
