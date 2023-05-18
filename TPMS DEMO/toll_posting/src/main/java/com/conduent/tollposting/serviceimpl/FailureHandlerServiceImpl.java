package com.conduent.tollposting.serviceimpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.conduent.tollposting.dto.FailureMessageDto;
import com.conduent.tollposting.oss.OssStreamClient;
import com.conduent.tollposting.service.FailureHandlerService;
import com.google.gson.Gson;

@Service
public class FailureHandlerServiceImpl implements FailureHandlerService{
	private static final Logger logger = LoggerFactory.getLogger(FailureHandlerServiceImpl.class);
	@Override
	public void persistFailureEvent(FailureMessageDto failureMessageDto, OssStreamClient failureQueueClient, Gson gsonNew) {
		// TODO Auto-generated method stub
		failureQueueClient.publishMessage(String.valueOf(failureMessageDto.getLaneTxId()),gsonNew.toJson(failureMessageDto).getBytes());
		logger.info("Message published to failure queue: {} with laneTxId {}",failureQueueClient, failureMessageDto.getLaneTxId());
	}

}
