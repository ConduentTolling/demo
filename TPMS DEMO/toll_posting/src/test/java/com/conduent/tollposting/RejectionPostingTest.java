package com.conduent.tollposting;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.conduent.tollposting.dto.EtcTransaction;
import com.conduent.tollposting.utility.MasterCache;

@ExtendWith(MockitoExtension.class)
public class RejectionPostingTest{
	
	@InjectMocks
	EtcTransaction etcTransaction;
	
	
	public static EtcTransaction getEtcTrxn() {
		
		EtcTransaction etcTransaction = new EtcTransaction();
		etcTransaction.setLaneTxId(20000540218L);
		etcTransaction.setLaneTxStatus(0);
		etcTransaction.setAccountAgencyId(1);
		etcTransaction.setAccountType(1);
		etcTransaction.setAtpFileId("57084");
		etcTransaction.setCollectedAmount(0.0);
		etcTransaction.setCashFareAmount(0.0);
		etcTransaction.setCorrReasonId(0);
		etcTransaction.setDeviceNo("01611600461");
		etcTransaction.setDiscountedAmount(0.0);
		etcTransaction.setDiscountedAmount2(0.0);
		etcTransaction.setEtcAccountId(1201L);
		etcTransaction.setEntryLaneId(77);
		etcTransaction.setEntryPlazaId(8);
		etcTransaction.setPlazaAgencyId(1);
		etcTransaction.setEtcFareAmount(0.0);
		etcTransaction.setPostedFareAmount(0.0);
		etcTransaction.setTxTypeInd("R");
		etcTransaction.setTxSubtypeInd("D");
		etcTransaction.setVideoFareAmount(0.0);
		etcTransaction.setUpdateTs(LocalDateTime.now().toString());
		
		return etcTransaction;
	}
	
	@Mock
	private MasterCache masterCache;
	
/*	@Test
	void validateRejTxn() {
		EtcTransaction etxTxn = getEtcTrxn();
		Integer etcId = 8330;
		Mockito.when(masterCache.getSystemAccount(etcId)).thenReturn(8330L);
		//Assertions.assertDoesNotThrow(() -> etcTransaction.validateRejectionTransaction(etxTxn));
		Assertions.assertFalse(() -> etcTransaction.validateRejectionTransaction(etxTxn,masterCache));
	}*/
	
}