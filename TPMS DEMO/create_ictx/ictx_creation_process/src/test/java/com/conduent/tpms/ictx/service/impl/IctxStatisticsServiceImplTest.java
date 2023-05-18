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
import com.conduent.tpms.ictx.dao.QatpStatisticsDao;
import com.conduent.tpms.ictx.dao.ReconciliationCheckPointDao;
import com.conduent.tpms.ictx.dao.SequenceDao;
import com.conduent.tpms.ictx.dto.FileOperationStatus;
import com.conduent.tpms.ictx.model.AwayTransaction;
import com.conduent.tpms.ictx.model.IagFileStatistics;
import com.conduent.tpms.ictx.model.QatpStatistics;
import com.conduent.tpms.ictx.model.ReconciliationCheckPoint;
import com.conduent.tpms.ictx.service.IctxStatisticsTxService;

/**
 * IctxStatisticsServiceImpl Test class
 * 
 * @author deepeshb
 *
 */
@ExtendWith(MockitoExtension.class)
public class IctxStatisticsServiceImplTest {

	@Mock
	private ReconciliationCheckPointDao reconciliationCheckPointDao;

	@Mock
	private AwayTransactionDao awayTransactionDao;

	@Mock
	private SequenceDao sequenceDao;

	@Mock
	private IagFileStatisticsDao iagFileStatisticsDao;

	@Mock
	private QatpStatisticsDao qatpStatisticsDao;

	@Mock
	private IctxStatisticsTxService ictxStatisticsTxService;

	@InjectMocks
	private IctxStatisticsServiceImpl ictxStatisticsServiceImpl;

	@SuppressWarnings("unchecked")
	@Test
	public void testUpdateIagStatistics_insert() throws Exception {
		FileOperationStatus fileOperationStatus = new FileOperationStatus();
		fileOperationStatus.setFromAgency("008");
		fileOperationStatus.setToAgency("016");
		fileOperationStatus.setFileName("008_016_20211014173110.ICTX");

		AwayTransaction awayTransaction = new AwayTransaction();
		awayTransaction.setAtpFileId(201L);

		List<AwayTransaction> awayTxList = new ArrayList<>();
		awayTxList.add(awayTransaction);

		fileOperationStatus.setIagStatusRecordList(awayTxList);

		QatpStatistics qatpStatistics = new QatpStatistics();
		qatpStatistics.setRecordCount(10L);

		Mockito.when(reconciliationCheckPointDao.getFileInfoByFileName(fileOperationStatus.getFileName()))
				.thenReturn(null);
		Mockito.when(iagFileStatisticsDao.getIagFileStatistics(Mockito.any(IagFileStatistics.class))).thenReturn(null);
		Mockito.when(iagFileStatisticsDao.getAtpFileId(Mockito.any(IagFileStatistics.class))).thenReturn(null);
		//Mockito.when(sequenceDao.getAtpFileSequence()).thenReturn(201L);

		Mockito.when(qatpStatisticsDao.getQatpStatistics(Mockito.any(Long.class))).thenReturn(qatpStatistics);
		ictxStatisticsServiceImpl.updateIagStatistics(fileOperationStatus,"380");

		Mockito.verify(reconciliationCheckPointDao, Mockito.times(1)).insertStartEntry(Mockito.any(String.class),
				Mockito.any(Long.class), Mockito.any(String.class));

		Mockito.verify(ictxStatisticsTxService, Mockito.times(1)).insertIctxTxStatistics(
				Mockito.any(FileOperationStatus.class), Mockito.any(List.class), Mockito.any(IagFileStatistics.class),
				Mockito.any(Long.class),Mockito.any(String.class));

	}

	@Test
	public void testUpdateIagStatistics_insert_scenario2() throws Exception {
		FileOperationStatus fileOperationStatus = new FileOperationStatus();
		fileOperationStatus.setFromAgency("008");
		fileOperationStatus.setToAgency("016");
		fileOperationStatus.setFileName("008_016_20211014173110.ICTX");

		AwayTransaction awayTransaction = new AwayTransaction();
		awayTransaction.setAtpFileId(201L);

		List<AwayTransaction> awayTxList = new ArrayList<>();
		awayTxList.add(awayTransaction);

		fileOperationStatus.setIagStatusRecordList(awayTxList);

		QatpStatistics qatpStatistics = new QatpStatistics();
		qatpStatistics.setRecordCount(10L);

		ReconciliationCheckPoint reconciliationCheckPoint = new ReconciliationCheckPoint();
		reconciliationCheckPoint.setRecordCount(201L);

		IagFileStatistics iagFileStatistics = new IagFileStatistics();
		iagFileStatistics.setAtpFileId(205L);

		Mockito.when(reconciliationCheckPointDao.getFileInfoByFileName(fileOperationStatus.getFileName()))
				.thenReturn(reconciliationCheckPoint);
		Mockito.when(iagFileStatisticsDao.getIagFileStatistics(Mockito.any(IagFileStatistics.class))).thenReturn(null);
		Mockito.when(iagFileStatisticsDao.getAtpFileId(Mockito.any(IagFileStatistics.class)))
				.thenReturn(iagFileStatistics);
		//Mockito.when(sequenceDao.getAtpFileSequence()).thenReturn(201L);

		Mockito.when(qatpStatisticsDao.getQatpStatistics(Mockito.any(Long.class))).thenReturn(qatpStatistics);
		ictxStatisticsServiceImpl.updateIagStatistics(fileOperationStatus,"380");

		Mockito.verify(ictxStatisticsTxService, Mockito.times(1)).insertIctxTxStatistics(
				Mockito.any(FileOperationStatus.class), Mockito.any(List.class), Mockito.any(IagFileStatistics.class),
				Mockito.any(Long.class),Mockito.any(String.class));

	}

	@Test
	public void testUpdateIagStatistics_update() throws Exception {
		FileOperationStatus fileOperationStatus = new FileOperationStatus();
		fileOperationStatus.setFromAgency("008");
		fileOperationStatus.setToAgency("016");
		fileOperationStatus.setFileName("008_016_20211014173110.ICTX");

		AwayTransaction awayTransaction = new AwayTransaction();
		awayTransaction.setAtpFileId(201L);

		List<AwayTransaction> awayTxList = new ArrayList<>();
		awayTxList.add(awayTransaction);

		fileOperationStatus.setIagStatusRecordList(awayTxList);

		QatpStatistics qatpStatistics = new QatpStatistics();
		qatpStatistics.setRecordCount(10L);

		ReconciliationCheckPoint reconciliationCheckPoint = new ReconciliationCheckPoint();
		reconciliationCheckPoint.setRecordCount(201L);

		IagFileStatistics iagFileStatistics = new IagFileStatistics();
		iagFileStatistics.setAtpFileId(205L);
		iagFileStatistics.setOutputRecCount(100L);

		Mockito.when(iagFileStatisticsDao.getIagFileStatistics(Mockito.any(IagFileStatistics.class)))
				.thenReturn(iagFileStatistics);

		ictxStatisticsServiceImpl.updateIagStatistics(fileOperationStatus,"380");

		Mockito.verify(ictxStatisticsTxService, Mockito.times(1)).updateIctxTxStatistics(
				Mockito.any(FileOperationStatus.class), Mockito.any(List.class), Mockito.any(IagFileStatistics.class),
				Mockito.any(Long.class));

	}

}
