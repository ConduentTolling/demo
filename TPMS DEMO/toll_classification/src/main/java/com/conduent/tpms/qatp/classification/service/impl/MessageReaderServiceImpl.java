package com.conduent.tpms.qatp.classification.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.conduent.tpms.qatp.classification.dto.TransactionDto;
import com.conduent.tpms.qatp.classification.service.MessageReaderService;
import com.conduent.tpms.qatp.classification.utility.MessageReader;
import com.oracle.bmc.streaming.responses.GetMessagesResponse;

/**
 * Read Transaction Message Service Implementation from Oracle Streaming Service
 * 
 * @author deepeshb
 *
 */
@Service
public class MessageReaderServiceImpl implements MessageReaderService{
	
	@Autowired
	private MessageReader messageReader;

	
	/**
	 * Read Transaction Messages from Oracle Streaming Service
	 * 
	 * @return List<TransactionDto>
	 * @throws IOException
	 */
	@Override
	public List<TransactionDto> readMessages() throws IOException {
		List<TransactionDto> dataList = null;
		GetMessagesResponse getMessagesResponse = messageReader.getMessage();
		if (getMessagesResponse != null) {
			dataList = new ArrayList<>(getMessagesResponse.getItems().size());
			for (int i = 0; i < getMessagesResponse.getItems().size(); i++) {
				String message = new String(getMessagesResponse.getItems().get(i).getValue());
				TransactionDto t = TransactionDto.dtoFromJson(message);
				dataList.add(t);
			}
		}
		return dataList;
	}

}
