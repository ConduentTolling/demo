package com.conduent.tpms.ictx.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.conduent.tpms.ictx.constants.IctxConstant;
import com.conduent.tpms.ictx.dao.AwayTransactionDao;
import com.conduent.tpms.ictx.dao.IagFileStatisticsDao;
import com.conduent.tpms.ictx.dao.QatpStatisticsDao;
import com.conduent.tpms.ictx.dao.ReconciliationCheckPointDao;
import com.conduent.tpms.ictx.dao.SequenceDao;
import com.conduent.tpms.ictx.dto.FileOperationStatus;
import com.conduent.tpms.ictx.model.AwayTransaction;
import com.conduent.tpms.ictx.model.IagFileStatistics;
import com.conduent.tpms.ictx.model.QatpStatistics;
import com.conduent.tpms.ictx.model.ReconciliationCheckPoint;
import com.conduent.tpms.ictx.service.IctxStatisticsService;
import com.conduent.tpms.ictx.service.IctxStatisticsTxService;

import ch.qos.logback.classic.Logger;

/**
 * Ictx Statistics Service
 * 
 * @author deepeshb
 *
 */
@Service
public class IctxStatisticsServiceImpl implements IctxStatisticsService {

	private static final Logger logger = (Logger) LoggerFactory.getLogger(IctxStatisticsServiceImpl.class);	
	
	@Autowired
	ReconciliationCheckPointDao reconciliationCheckPointDao;

	@Autowired
	AwayTransactionDao awayTransactionDao;

	@Autowired
	SequenceDao sequenceDao;

	@Autowired
	IagFileStatisticsDao iagFileStatisticsDao;

	@Autowired
	QatpStatisticsDao qatpStatisticsDao;

	@Autowired
	IctxStatisticsTxService ictxStatisticsTxService;

	/**
	 * Update Iag Statistics
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void updateIagStatistics(FileOperationStatus fileOperationStatus,String header) throws Exception { //handle exception
		if (fileOperationStatus.isOperationSuccessFlag()) {

			ReconciliationCheckPoint tempReconciliationCheckPoint = reconciliationCheckPointDao
					.getFileInfoByFileName(fileOperationStatus.getFileName());
			if (tempReconciliationCheckPoint == null) {
				// CHECK IF ENTRY EXISTS IN T_RECONCILIATION_CHECKPOINT THEN RETRIEVE ELSE
				// MAKE AN ENTRY WITH COUNT AS 0 and STATUS as 'S'
				reconciliationCheckPointDao.insertStartEntry(fileOperationStatus.getFileName(), 0L,
						IctxConstant.FILE_STATUS_START);
			}

			// segregate based on atp file Id
			List<AwayTransaction> tempAwayTxList = fileOperationStatus.getIagStatusRecordList();
			if (tempAwayTxList != null && !tempAwayTxList.isEmpty()) {
				Map<Long, List<AwayTransaction>> tempAwayTxMapByAtpId = tempAwayTxList.stream()
						.collect(Collectors.groupingBy(AwayTransaction::getAtpFileId));
				// iterate each atp file Id
				for (Long tempAtpFileId : tempAwayTxMapByAtpId.keySet()) {
					List<AwayTransaction> tempAwayTxListByAtpId = tempAwayTxMapByAtpId.get(tempAtpFileId);
					logger.info("tempAtpFileId: {} total records: {} ",tempAtpFileId, tempAwayTxListByAtpId !=null ? tempAwayTxListByAtpId.size() : 0);
					// Check in IAG_STATISTICS
					IagFileStatistics iagFileStatistics = new IagFileStatistics();
					iagFileStatistics.setInputFileName("ECTX/VCTX");
					iagFileStatistics.setOutputFileName(fileOperationStatus.getFileName());
					iagFileStatistics.setXferControlId(tempAtpFileId);
					iagFileStatistics.setFromAgency(fileOperationStatus.getFromAgency());
					iagFileStatistics.setToAgency(fileOperationStatus.getToAgency());
					//iagFileStatistics.setInputRecCount(Long.valueOf(tempAwayTxList.size()));
					
					IagFileStatistics iagFileStatisticsDb = iagFileStatisticsDao
							.getIagFileStatistics(iagFileStatistics);
					if (iagFileStatisticsDb != null) {
						updateStatsForExistingRecord(fileOperationStatus, tempReconciliationCheckPoint,
								tempAwayTxListByAtpId, iagFileStatisticsDb);
					} else {
						insertStatistics(fileOperationStatus, tempReconciliationCheckPoint, tempAtpFileId,
								tempAwayTxListByAtpId, iagFileStatistics,header);
					}
				}
			}

		}
	}

	/**
	 * Insert Iag Statistics
	 * 
	 * @param fileOperationStatus
	 * @param tempReconciliationCheckPoint
	 * @param tempAtpFileId
	 * @param tempAwayTxListByAtpId
	 * @param iagFileStatistics
	 */
	private void insertStatistics(FileOperationStatus fileOperationStatus,
			ReconciliationCheckPoint tempReconciliationCheckPoint, Long tempAtpFileId,
			List<AwayTransaction> tempAwayTxListByAtpId, IagFileStatistics iagFileStatistics,String header) {
		IagFileStatistics tempiagFileStatisticsDb = iagFileStatisticsDao.getAtpFileId(iagFileStatistics);
		// If not exists, Get the record from T_QATP_STATISTICS,Insert into the
		logger.info("tempAtpFileId: {}",tempAtpFileId);
		QatpStatistics qatpStatisticsDb = qatpStatisticsDao.getQatpStatistics(tempAtpFileId);
		
		if (qatpStatisticsDb != null) {
			logger.info("qatpStatisticsDb.getRecordCount(): {}",qatpStatisticsDb.getRecordCount());
			iagFileStatistics.setInputRecCount(qatpStatisticsDb.getRecordCount());
			//iagFileStatistics.setInputRecCount(iagFileStatistics.getInputRecCount());
			iagFileStatistics.setOutputRecCount(Long.valueOf(tempAwayTxListByAtpId.size()));
			iagFileStatistics.setUpdateTs(LocalDateTime.now());
			iagFileStatistics.setOutputFileName(fileOperationStatus.getFileName());

			// Get Atp file Id
			Long tempAtpFileIdDb = tempiagFileStatisticsDb != null ? tempiagFileStatisticsDb.getAtpFileId() : null;
			if (tempAtpFileIdDb != null) {
				iagFileStatistics.setAtpFileId(tempAtpFileIdDb);
			} else {
				iagFileStatistics.setAtpFileId(sequenceDao.getAtpFileSequence());
			}
			logger.info("iagFileStatistics.getAtpFileId: {}",iagFileStatistics.getAtpFileId());
			Long tempRecordCount = getRecordCount(tempReconciliationCheckPoint, tempAwayTxListByAtpId);
			ictxStatisticsTxService.insertIctxTxStatistics(fileOperationStatus, tempAwayTxListByAtpId,
					iagFileStatistics, tempRecordCount,header);
		}
	}
	
	

	/**
	 * Update stats for existing record
	 * 
	 * @param fileOperationStatus
	 * @param tempReconciliationCheckPoint
	 * @param tempAwayTxListByAtpId
	 * @param iagFileStatisticsDb
	 */
	private void updateStatsForExistingRecord(FileOperationStatus fileOperationStatus,
			ReconciliationCheckPoint tempReconciliationCheckPoint, List<AwayTransaction> tempAwayTxListByAtpId,
			IagFileStatistics iagFileStatisticsDb) {
		// If exists then just update output count and update_ts only
		iagFileStatisticsDb.setOutputRecCount(tempAwayTxListByAtpId.size() + iagFileStatisticsDb.getOutputRecCount());
		iagFileStatisticsDb.setUpdateTs(LocalDateTime.now());
		Long tempRecordCount = getRecordCount(tempReconciliationCheckPoint, tempAwayTxListByAtpId);
		ictxStatisticsTxService.updateIctxTxStatistics(fileOperationStatus, tempAwayTxListByAtpId, iagFileStatisticsDb,
				tempRecordCount);
	}

	/**
	 * Get Record Count
	 * 
	 * @param tempReconciliationCheckPoint
	 * @param tempAwayTxList
	 * @return Long
	 */
	private Long getRecordCount(ReconciliationCheckPoint tempReconciliationCheckPoint,
			List<AwayTransaction> tempAwayTxList) {
		Long tempRecordCount = 0L;
		if (tempReconciliationCheckPoint != null) {
			tempRecordCount = tempReconciliationCheckPoint.getRecordCount() + tempAwayTxList.size();
		} else if (tempAwayTxList != null && !tempAwayTxList.isEmpty()) {
			tempRecordCount = Long.valueOf(tempAwayTxList.size());
		}
		return tempRecordCount;
	}
}
