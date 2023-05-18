package com.conduent.tpms.ictx.service;

import java.util.List;

import com.conduent.tpms.ictx.dto.FileOperationStatus;
import com.conduent.tpms.ictx.model.AwayTransaction;
import com.conduent.tpms.ictx.model.IagFileStatistics;

/**
 * Ictx statistics tx service for Ictx Statistics Service
 * 
 * @author deepeshb
 *
 */
public interface IctxStatisticsTxService {

	void insertIctxTxStatistics(FileOperationStatus fileOperationStatus, List<AwayTransaction> tempAwayTxListByAtpId,
			IagFileStatistics iagFileStatistics, Long tempRecordCount,String header);

	void updateIctxTxStatistics(FileOperationStatus fileOperationStatus,
			List<AwayTransaction> tempAwayTxListByAtpId, IagFileStatistics iagFileStatisticsDb, Long tempRecordCount);

}
