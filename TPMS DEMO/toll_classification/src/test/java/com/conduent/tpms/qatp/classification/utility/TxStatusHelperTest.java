package com.conduent.tpms.qatp.classification.utility;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.conduent.tpms.qatp.classification.constants.QatpClassificationConstant;
import com.conduent.tpms.qatp.classification.model.TCode;

/**
 * Test cases for etc tx status
 * 
 * @author deepeshb
 *
 */
@ExtendWith(MockitoExtension.class)
class TxStatusHelperTest {

	@Mock
	private MasterDataCache masterDataCache;

	@InjectMocks
	private TxStatusHelper txStatusHelper;

	@Test
	void TestgetTxStatusQETOL() {
		String txnType = "E";
		TCode tcode = new TCode();
		tcode.setCodeId(71);
		Mockito.when(masterDataCache.geTxStatusByCodeValue(QatpClassificationConstant.QETOL)).thenReturn(tcode);
		Integer txStatus = txStatusHelper.getTxStatus(txnType, null, null,null);
		assertEquals(71, txStatus);
	}

	@Test
	void TestgetTxStatusQVTOL() {
		String txnType = "V";
		TCode tcode = new TCode();
		tcode.setCodeId(72);
		Mockito.when(masterDataCache.geTxStatusByCodeValue(QatpClassificationConstant.QVIOL)).thenReturn(tcode);
		Integer txStatus = txStatusHelper.getTxStatus(txnType, null, null,null);
		assertEquals(72, txStatus);
	}

	@Test
	void TestgetTxStatusQCTOL() {
		String txnType = "I";
		TCode tcode = new TCode();
		tcode.setCodeId(73);
		Mockito.when(masterDataCache.geTxStatusByCodeValue(QatpClassificationConstant.QECTX)).thenReturn(tcode);
		Integer txStatus = txStatusHelper.getTxStatus(txnType, null, null,null);
		assertEquals(73, txStatus);
	}

	@Test
	void TestgetTxStatusQECTX() {
		String txnType = "O";
		TCode tcode = new TCode();
		tcode.setCodeId(83);
		Mockito.when(masterDataCache.geTxStatusByCodeValue(QatpClassificationConstant.QECTX)).thenReturn(tcode);
		Integer txStatus = txStatusHelper.getTxStatus(txnType, null, null,null);
		assertEquals(83, txStatus);
	}

	@Test
	void TestgetTxStatusQUNMA() {
		String txnType = "U";
		TCode tcode = new TCode();
		tcode.setCodeId(79);
		Mockito.when(masterDataCache.geTxStatusByCodeValue(QatpClassificationConstant.UNMATCHED)).thenReturn(tcode);
		Integer txStatus = txStatusHelper.getTxStatus(txnType, null, null,null);
		assertEquals(79, txStatus);
	}

	@Test
	void TestgetTxStatusQDISC() {
		String txnType = "D";
		TCode tcode = new TCode();
		tcode.setCodeId(62);
		Mockito.when(masterDataCache.geTxStatusByCodeValue(QatpClassificationConstant.QDISC)).thenReturn(tcode);
		Integer txStatus = txStatusHelper.getTxStatus(txnType, null, null,null);
		assertEquals(62, txStatus);
	}

	@Test
	void TestgetTxStatusQMTOL() {
		String txnType = "M";
		TCode tcode = new TCode();
		tcode.setCodeId(78);
		Mockito.when(masterDataCache.geTxStatusByCodeValue(QatpClassificationConstant.QMTOL)).thenReturn(tcode);
		Integer txStatus = txStatusHelper.getTxStatus(txnType, null, null,null);
		assertEquals(78, txStatus);
	}

	@Test
	void TestgetTxStatusMQECTX() {
		String txnType = "M";
		String txnSubType = "O";
		TCode tcode = new TCode();
		tcode.setCodeId(83);
		Mockito.when(masterDataCache.geTxStatusByCodeValue(QatpClassificationConstant.QECTX)).thenReturn(tcode);
		Integer txStatus = txStatusHelper.getTxStatus(txnType, txnSubType, null,null);
		assertEquals(83, txStatus);
	}

	@Test
	void TestgetTxStatusQXTOL() {
		String txnType = "X";
		Long etcAccountId = 201L;
		TCode tcode = new TCode();
		tcode.setCodeId(1);
		Mockito.when(masterDataCache.geTxStatusByCodeValue(QatpClassificationConstant.QXTOL)).thenReturn(tcode);
		Integer txStatus = txStatusHelper.getTxStatus(txnType, null, etcAccountId,null);
		assertEquals(1, txStatus);
	}

	@Test
	void TestgetTxStatusQITXC() {
		String txnType = "X";
		TCode tcode = new TCode();
		tcode.setCodeId(80);
		Mockito.when(masterDataCache.geTxStatusByCodeValue(QatpClassificationConstant.QITXC)).thenReturn(tcode);
		Integer txStatus = txStatusHelper.getTxStatus(txnType, null, null,null);
		assertEquals(80, txStatus);
	}

	@Test
	void TestgetTxStatusQFTOL() {
		String txnType = "P";
		TCode tcode = new TCode();
		tcode.setCodeId(76);
		Mockito.when(masterDataCache.geTxStatusByCodeValue(QatpClassificationConstant.QFTOL)).thenReturn(tcode);
		Integer txStatus = txStatusHelper.getTxStatus(txnType, null, null,null);
		assertEquals(76, txStatus);
	}

	@Test
	void TestgetTxStatusQINVPLAZA() {
		String txnType = "R";
		String txnSubType = "L";
		TCode tcode = new TCode();
		tcode.setCodeId(51);
		Mockito.when(masterDataCache.geTxStatusByCodeValue(QatpClassificationConstant.QINVPLAZA)).thenReturn(tcode);
		Integer txStatus = txStatusHelper.getTxStatus(txnType, txnSubType, null,null);
		assertEquals(51, txStatus);
	}

	@Test
	void TestgetTxStatusQINVXDATE() {
		String txnType = "R";
		String txnSubType = "D";
		TCode tcode = new TCode();
		tcode.setCodeId(58);
		Mockito.when(masterDataCache.geTxStatusByCodeValue(QatpClassificationConstant.QINVXDATE)).thenReturn(tcode);
		Integer txStatus = txStatusHelper.getTxStatus(txnType, txnSubType, null,null);
		assertEquals(58, txStatus);
	}

	@Test
	void TestgetTxStatusQINVAGENCY() {
		String txnType = "R";
		String txnSubType = "T";
		TCode tcode = new TCode();
		tcode.setCodeId(53);
		Mockito.when(masterDataCache.geTxStatusByCodeValue(QatpClassificationConstant.QINVAGENCY)).thenReturn(tcode);
		Integer txStatus = txStatusHelper.getTxStatus(txnType, txnSubType, null,null);
		assertEquals(53, txStatus);
	}

	@Test
	void TestgetTxStatusQNONVTRX() {
		String txnType = "R";
		String txnSubType = "V";
		TCode tcode = new TCode();
		tcode.setCodeId(54);
		Mockito.when(masterDataCache.geTxStatusByCodeValue(QatpClassificationConstant.QNONVTRX)).thenReturn(tcode);
		Integer txStatus = txStatusHelper.getTxStatus(txnType, txnSubType, null,null);
		assertEquals(54, txStatus);
	}

	@Test
	void TestgetTxStatusQINVCLASS() {
		String txnType = "R";
		String txnSubType = "C";
		TCode tcode = new TCode();
		tcode.setCodeId(55);
		Mockito.when(masterDataCache.geTxStatusByCodeValue(QatpClassificationConstant.QINVCLASS)).thenReturn(tcode);
		Integer txStatus = txStatusHelper.getTxStatus(txnType, txnSubType, null,null);
		assertEquals(55, txStatus);
	}

	@Test
	void TestgetTxStatusQINVMODE() {
		String txnType = "R";
		String txnSubType = "M";
		TCode tcode = new TCode();
		tcode.setCodeId(56);
		Mockito.when(masterDataCache.geTxStatusByCodeValue(QatpClassificationConstant.QINVMODE)).thenReturn(tcode);
		Integer txStatus = txStatusHelper.getTxStatus(txnType, txnSubType, null,null);
		assertEquals(56, txStatus);
	}

	@Test
	void TestgetTxStatusQNONNUMVAL() {
		String txnType = "R";
		String txnSubType = "N";
		TCode tcode = new TCode();
		tcode.setCodeId(57);
		Mockito.when(masterDataCache.geTxStatusByCodeValue(QatpClassificationConstant.QNONNUMVAL)).thenReturn(tcode);
		Integer txStatus = txStatusHelper.getTxStatus(txnType, txnSubType, null,null);
		assertEquals(57, txStatus);
	}

	@Test
	void TestgetTxStatusQINVEDATE() {
		String txnType = "R";
		String txnSubType = "E";
		TCode tcode = new TCode();
		tcode.setCodeId(52);
		Mockito.when(masterDataCache.geTxStatusByCodeValue(QatpClassificationConstant.QINVEDATE)).thenReturn(tcode);
		Integer txStatus = txStatusHelper.getTxStatus(txnType, txnSubType, null,null);
		assertEquals(52, txStatus);
	}

	@Test
	void TestgetTxStatusQINVTAGGRP() {
		String txnType = "R";
		String txnSubType = "G";
		TCode tcode = new TCode();
		tcode.setCodeId(59);
		Mockito.when(masterDataCache.geTxStatusByCodeValue(QatpClassificationConstant.QINVTAGGRP)).thenReturn(tcode);
		Integer txStatus = txStatusHelper.getTxStatus(txnType, txnSubType, null,null);
		assertEquals(59, txStatus);
	}

	@Test
	void TestgetTxStatusQINVTRX() {
		String txnType = "R";
		String txnSubType = "X";
		TCode tcode = new TCode();
		tcode.setCodeId(60);
		Mockito.when(masterDataCache.geTxStatusByCodeValue(QatpClassificationConstant.QINVTRX)).thenReturn(tcode);
		Integer txStatus = txStatusHelper.getTxStatus(txnType, txnSubType, null,null);
		assertEquals(60, txStatus);
	}

	@Test
	void TestgetTxStatusQINVRECLEN() {
		String txnType = "R";
		String txnSubType = "R";
		TCode tcode = new TCode();
		tcode.setCodeId(61);
		Mockito.when(masterDataCache.geTxStatusByCodeValue(QatpClassificationConstant.QINVRECLEN)).thenReturn(tcode);
		Integer txStatus = txStatusHelper.getTxStatus(txnType, txnSubType, null,null);
		assertEquals(61, txStatus);
	}

	@Test
	void TestgetTxStatusBEYOND() {
		String txnType = "R";
		String txnSubType = "B";
		TCode tcode = new TCode();
		tcode.setCodeId(35);
		Mockito.when(masterDataCache.geTxStatusByCodeValue(QatpClassificationConstant.BEYOND)).thenReturn(tcode);
		Integer txStatus = txStatusHelper.getTxStatus(txnType, txnSubType, null,null);
		assertEquals(35, txStatus);
	}
}
