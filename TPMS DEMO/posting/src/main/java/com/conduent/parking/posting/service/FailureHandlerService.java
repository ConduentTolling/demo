package com.conduent.parking.posting.service;

import com.conduent.parking.posting.dto.FailureMessageDto;
import com.conduent.parking.posting.oss.OssStreamClient;
import com.google.gson.Gson;

public interface FailureHandlerService {

	public void persistFailureEvent(FailureMessageDto failureMessageDto, OssStreamClient failureQueueClient, Gson gsonNew);

}
