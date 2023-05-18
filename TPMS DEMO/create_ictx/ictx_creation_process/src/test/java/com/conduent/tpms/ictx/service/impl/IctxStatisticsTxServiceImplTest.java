package com.conduent.tpms.ictx.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.conduent.tpms.ictx.dao.AwayTransactionDao;
import com.conduent.tpms.ictx.dao.IagFileStatisticsDao;
import com.conduent.tpms.ictx.dao.IagFileStatsDao;
import com.conduent.tpms.ictx.dao.QatpStatisticsDao;
import com.conduent.tpms.ictx.dao.ReconciliationCheckPointDao;
import com.conduent.tpms.ictx.dao.SequenceDao;
import com.conduent.tpms.ictx.dto.FileOperationStatus;
import com.conduent.tpms.ictx.model.AwayTransaction;
import com.conduent.tpms.ictx.model.IagFileStatistics;

/**
 * IctxStatisticsTxServiceImpl Test class
 * 
 * @author deepeshb
 *
 */
@ExtendWith(MockitoExtension.class)
public class IctxStatisticsTxServiceImplTest {

	@Mock
	private ReconciliationCheckPointDao reconciliationCheckPointDao;

	@Mock
	private AwayTransactionDao awayTransactionDao;

	@Mock
	private SequenceDao sequenceDao;

	@Mock
	private IagFileStatisticsDao iagFileStatisticsDao;
	@Mock
	private IagFileStatsDao iagFileStatsDao;
	@Mock
	private QatpStatisticsDao qatpStatisticsDao;

	@InjectMocks
	private IctxStatisticsTxServiceImpl ictxStatisticsTxServiceImpl;

	@Test
	public void testInsertIctxTxStatistics() {
		FileOperationStatus fileOperationStatus = new FileOperationStatus();
		fileOperationStatus.setFileName("008_016_20211014173110.ICTX");

		IagFileStatistics iagFileStatistics = new IagFileStatistics();
		iagFileStatistics.setAtpFileId(201L);
		List<AwayTransaction> tempList = new ArrayList<AwayTransaction>();
		AwayTransaction awayTransaction = new AwayTransaction();
		tempList.add(awayTransaction);
//		Mockito.verify(iagFileStatsDao, Mockito.times(1))
//		.insertIagFileStats(Mockito.any(IagFileStatistics.class));
//		Mockito.verify(iagFileStatisticsDao, Mockito.times(1))
//		.insertIagFileStatistics(Mockito.any(IagFileStatistics.class));
		ictxStatisticsTxServiceImpl.insertIctxTxStatistics(fileOperationStatus, tempList, iagFileStatistics, 2L,"380");
Mockito.verify(awayTransactionDao, Mockito.times(1)).updateDstAtpFileId(Mockito.any(List.class),
		Mockito.any(Long.class));
Mockito.verify(reconciliationCheckPointDao, Mockito.times(1)).updateRecord(Mockito.any(String.class),
		Mockito.any(Long.class), Mockito.any(String.class));
		ictxStatisticsTxServiceImpl.insertIctxTxStatistics(fileOperationStatus, tempList, iagFileStatistics, 2L,"380");

		
	}

	@Test
	public void testUpdateIctxTxStatistics() {
		FileOperationStatus fileOperationStatus = new FileOperationStatus();
		fileOperationStatus.setFileName("008_016_20211014173110.ICTX");

		IagFileStatistics iagFileStatistics = new IagFileStatistics();
		iagFileStatistics.setAtpFileId(201L);
		List<AwayTransaction> tempList = new ArrayList<AwayTransaction>();
		AwayTransaction awayTransaction = new AwayTransaction();
		tempList.add(awayTransaction);

		ictxStatisticsTxServiceImpl.updateIctxTxStatistics(fileOperationStatus, tempList, iagFileStatistics, 2L);
		Mockito.verify(iagFileStatsDao, Mockito.times(1))
		.updateIagFileStats(Mockito.any(IagFileStatistics.class));
		Mockito.verify(iagFileStatisticsDao, Mockito.times(1))
				.updateIagFileStatistics(Mockito.any(IagFileStatistics.class));
		Mockito.verify(awayTransactionDao, Mockito.times(1)).updateDstAtpFileId(Mockito.any(List.class),
				Mockito.any(Long.class));
		Mockito.verify(reconciliationCheckPointDao, Mockito.times(1)).updateRecord(Mockito.any(String.class),
				Mockito.any(Long.class), Mockito.any(String.class));
	}

}
