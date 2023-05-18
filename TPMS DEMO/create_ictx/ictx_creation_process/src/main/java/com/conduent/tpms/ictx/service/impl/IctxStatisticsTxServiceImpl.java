package com.conduent.tpms.ictx.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.conduent.tpms.ictx.constants.IctxConstant;
import com.conduent.tpms.ictx.dao.AwayTransactionDao;
import com.conduent.tpms.ictx.dao.IagFileStatisticsDao;
import com.conduent.tpms.ictx.dao.IagFileStatsDao;
import com.conduent.tpms.ictx.dao.QatpStatisticsDao;
import com.conduent.tpms.ictx.dao.ReconciliationCheckPointDao;
import com.conduent.tpms.ictx.dao.SequenceDao;
import com.conduent.tpms.ictx.dto.FileOperationStatus;
import com.conduent.tpms.ictx.model.AwayTransaction;
import com.conduent.tpms.ictx.model.IagFileStatistics;
import com.conduent.tpms.ictx.service.IctxStatisticsTxService;

/**
 * Ictx Statistics Tx Service
 * 
 * @author deepeshb
 *
 */
@Service
public class IctxStatisticsTxServiceImpl implements IctxStatisticsTxService {

	private static final Logger logger = LoggerFactory.getLogger(IctxStatisticsTxServiceImpl.class);
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
	 * Manange Tx of Insert Ictx Statistics
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void insertIctxTxStatistics(FileOperationStatus fileOperationStatus,
			List<AwayTransaction> tempAwayTxListByAtpId, IagFileStatistics iagFileStatistics, Long tempRecordCount,String header) {
		iagFileStatsDao.insertIagFileStats(iagFileStatistics,header);
		
		if(iagFileStatisticsDao.getIagFileStatisticsByXferControlId(iagFileStatistics)) {
			iagFileStatisticsDao.updateIagFileStatisticsValues(iagFileStatistics,header);
		}else{
			iagFileStatisticsDao.insertIagFileStatistics(iagFileStatistics,header);//Niranjan need to have this for each xfer control id
		}
		logger.info("Insert Ictx TxStatistics..");
		awayTransactionDao.updateDstAtpFileId(tempAwayTxListByAtpId, iagFileStatistics.getAtpFileId());
		reconciliationCheckPointDao.updateRecord(fileOperationStatus.getFileName(), tempRecordCount,
				IctxConstant.FILE_STATUS_IN_PROGRESS);
		logger.info("Inserted Ictx TxStatistics..");

	}

	/**
	 * Manange Tx of Update Ictx Statistics
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void updateIctxTxStatistics(FileOperationStatus fileOperationStatus,
			List<AwayTransaction> tempAwayTxListByAtpId, IagFileStatistics iagFileStatisticsDb, Long tempRecordCount) {
		iagFileStatsDao.updateIagFileStats(iagFileStatisticsDb);
		iagFileStatisticsDao.updateIagFileStatistics(iagFileStatisticsDb);
		awayTransactionDao.updateDstAtpFileId(tempAwayTxListByAtpId, iagFileStatisticsDb.getAtpFileId());
		reconciliationCheckPointDao.updateRecord(fileOperationStatus.getFileName(), tempRecordCount,
				IctxConstant.FILE_STATUS_IN_PROGRESS);
		logger.info("Updated Ictx TxStatistics..");
	}

}
