package com.conduent.tpms.intx.service;

import java.util.List;

import com.conduent.tpms.intx.dto.FileOperationStatus;
import com.conduent.tpms.intx.model.AwayTransaction;
import com.conduent.tpms.intx.model.IagFileStatistics;

public interface IntxStatisticsTxService {

	void insertIntxTxStatistics(FileOperationStatus fileOperationStatus, List<AwayTransaction> tempAwayTxListByAtpId,
			IagFileStatistics iagFileStatistics, Long tempRecordCount,String header);

	void updateIntxTxStatistics(FileOperationStatus fileOperationStatus,
			List<AwayTransaction> tempAwayTxListByAtpId, IagFileStatistics iagFileStatisticsDb, Long tempRecordCount);

}
