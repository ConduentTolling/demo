package com.conduent.tpms.qatp.classification.service.impl;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.conduent.tpms.qatp.classification.dto.AccountInfoDto;
import com.conduent.tpms.qatp.classification.dto.TransactionDetailDto;
import com.conduent.tpms.qatp.classification.dto.TransactionDto;
import com.conduent.tpms.qatp.classification.service.ComputeTollService;


/**
 * Test class for PA classification service
 * 
 * @author deepeshb
 *
 */
@ExtendWith(MockitoExtension.class)
 class PAClassificationServiceImplTest {

	@Mock
	private ComputeTollService computeTollService;

	@InjectMocks
	private PAClassificationServiceImpl pAClassificationServiceImpl;

	@Test
	void TestComputeToll() {
		TransactionDetailDto transactionDetailDto = new TransactionDetailDto();
		AccountInfoDto accountInfoDto = new AccountInfoDto();
		TransactionDto transactionDto = new TransactionDto();
		transactionDto.setLaneTxId(101L);
		transactionDetailDto.setAccountInfoDto(accountInfoDto);
		transactionDetailDto.setTransactionDto(transactionDto);

		Mockito.when(computeTollService.computePAToll(transactionDetailDto)).thenReturn(transactionDetailDto);

		TransactionDetailDto temptransactionDetailDto = pAClassificationServiceImpl.computeToll(transactionDetailDto);

		assertNotNull(temptransactionDetailDto);
	}
	
	@Test
	void TestComputeTollNullCheck() {
		TransactionDetailDto transactionDetailDto = new TransactionDetailDto();
		AccountInfoDto accountInfoDto = new AccountInfoDto();
		transactionDetailDto.setAccountInfoDto(accountInfoDto);
		transactionDetailDto.setTransactionDto(null);
		TransactionDetailDto temptransactionDetailDto = pAClassificationServiceImpl.computeToll(transactionDetailDto);
		assertNull(temptransactionDetailDto);
	}

}
