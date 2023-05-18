package com.conduent.tpms.unmatched.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.conduent.tpms.unmatched.dto.TransactionDetailDto;
import com.conduent.tpms.unmatched.service.CommonNotificationService;
import com.conduent.tpms.unmatched.service.MessageQueueService;



@Service
public class MessageQueueServiceImpl implements MessageQueueService {

	@Autowired
	private CommonNotificationService commonNotificationService;
	

	@Override
	public void pushMessage(TransactionDetailDto transactionDetailDto) {
		if (transactionDetailDto == null || transactionDetailDto.getTransactionDto() == null) {
			return;
		}
		commonNotificationService.pushMessage(transactionDetailDto.getTransactionDto());
	}

}
