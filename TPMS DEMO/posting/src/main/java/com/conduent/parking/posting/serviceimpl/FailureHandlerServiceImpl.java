package com.conduent.parking.posting.serviceimpl;

import org.springframework.stereotype.Service;

import com.conduent.parking.posting.dto.FailureMessageDto;
import com.conduent.parking.posting.oss.OssStreamClient;
import com.conduent.parking.posting.service.FailureHandlerService;
import com.google.gson.Gson;

@Service
public class FailureHandlerServiceImpl implements FailureHandlerService{

	@Override
	public void persistFailureEvent(FailureMessageDto failureMessageDto, OssStreamClient failureQueueClient, Gson gsonNew) {
		// TODO Auto-generated method stub
		failureQueueClient.publishMessage(String.valueOf(failureMessageDto.getLaneTxId()),gsonNew.toJson(failureMessageDto).getBytes());
	}

}
