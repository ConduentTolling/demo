package com.conduent.tollposting.service;

import com.conduent.tollposting.dto.FailureMessageDto;
import com.conduent.tollposting.oss.OssStreamClient;
import com.google.gson.Gson;

public interface FailureHandlerService {

	public void persistFailureEvent(FailureMessageDto failureMessageDto, OssStreamClient failureQueueClient, Gson gsonNew);

}
