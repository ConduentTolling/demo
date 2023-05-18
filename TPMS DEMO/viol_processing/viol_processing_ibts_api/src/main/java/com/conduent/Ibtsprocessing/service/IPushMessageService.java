package com.conduent.Ibtsprocessing.service;

import java.text.ParseException;

import com.conduent.Ibtsprocessing.dto.QueueMessage;

public interface IPushMessageService {

	public void pushMessageToAway(QueueMessage txDTO);

	public boolean pushMessageToViol(QueueMessage txDTO) throws ParseException;

}
