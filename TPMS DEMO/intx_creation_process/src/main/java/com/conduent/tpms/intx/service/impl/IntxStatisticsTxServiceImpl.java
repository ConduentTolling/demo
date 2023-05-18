package com.conduent.tpms.intx.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.conduent.tpms.intx.constants.IntxConstant;
import com.conduent.tpms.intx.dao.AwayTransactionDao;
import com.conduent.tpms.intx.dao.IagFileStatisticsDao;
import com.conduent.tpms.intx.dao.IagFileStatsDao;
import com.conduent.tpms.intx.dao.QatpStatisticsDao;
import com.conduent.tpms.intx.dao.ReconciliationCheckPointDao;
import com.conduent.tpms.intx.dao.SequenceDao;
import com.conduent.tpms.intx.dto.FileOperationStatus;
import com.conduent.tpms.intx.model.AwayTransaction;
import com.conduent.tpms.intx.model.IagFileStatistics;
import com.conduent.tpms.intx.service.IntxStatisticsTxService;
import com.conduent.tpms.intx.service.impl.IntxStatisticsTxServiceImpl;

@Service
public class IntxStatisticsTxServiceImpl implements IntxStatisticsTxService {

	private static final Logger logger = LoggerFactory.getLogger(IntxStatisticsTxServiceImpl.class);
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
	IagFileStatsDao iagFileStatsDao;

	/**
	 * Manange Tx of Insert Intx Statistics
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void insertIntxTxStatistics(FileOperationStatus fileOperationStatus,
			List<AwayTransaction> tempAwayTxListByAtpId, IagFileStatistics iagFileStatistics, Long tempRecordCount,
			String header) {
		logger.info("Insert insertIagFileStats..");
		iagFileStatsDao.insertIagFileStats(iagFileStatistics, header);
		logger.info("Insert insertIagFileStatistics..");
		iagFileStatisticsDao.insertIagFileStatistics(iagFileStatistics, header);
		logger.info("Insert Intx TxStatistics..");
		awayTransactionDao.updateDstAtpFileId(tempAwayTxListByAtpId, iagFileStatistics.getAtpFileId());
		reconciliationCheckPointDao.updateRecord(fileOperationStatus.getFileName(), tempRecordCount,
				IntxConstant.FILE_STATUS_IN_PROGRESS);
		logger.info("Inserted Intx TxStatistics..");
	}

	/**
	 * Manange Tx of Update Intx Statistics
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void updateIntxTxStatistics(FileOperationStatus fileOperationStatus,
			List<AwayTransaction> tempAwayTxListByAtpId, IagFileStatistics iagFileStatisticsDb, Long tempRecordCount) {
		iagFileStatsDao.updateIagFileStats(iagFileStatisticsDb);
		iagFileStatisticsDao.updateIagFileStatistics(iagFileStatisticsDb);
		awayTransactionDao.updateDstAtpFileId(tempAwayTxListByAtpId, iagFileStatisticsDb.getAtpFileId());
		reconciliationCheckPointDao.updateRecord(fileOperationStatus.getFileName(), tempRecordCount,
				IntxConstant.FILE_STATUS_IN_PROGRESS);
		logger.info("Updated Intx TxStatistics..");
	}

}
