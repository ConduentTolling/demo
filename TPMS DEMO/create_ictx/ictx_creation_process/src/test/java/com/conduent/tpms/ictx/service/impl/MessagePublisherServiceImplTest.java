package com.conduent.tpms.ictx.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.util.Assert;

import com.conduent.tpms.ictx.dto.AtpMessageDto;
import com.conduent.tpms.ictx.model.ConfigVariable;
import com.conduent.tpms.ictx.utility.MessagePublisher;

/**
 * MessagePublisherServiceImpl Test class
 * 
 * @author deepeshb
 *
 */
@ExtendWith(MockitoExtension.class)
public class MessagePublisherServiceImplTest {

	@Mock
	private ConfigVariable configVariable;

	@Mock
	private MessagePublisher messagePublisher;

	@InjectMocks
	private MessagePublisherServiceImpl messagePublisherServiceImpl;

	@Test
	void testPublishMessage() {
		AtpMessageDto atpMessageDto = new AtpMessageDto();
		atpMessageDto.setLaneTxId(202L);
		List<AtpMessageDto> atpMessageDtoList = new ArrayList<AtpMessageDto>();
		atpMessageDtoList.add(atpMessageDto);

		Mockito.when(configVariable.getStreamId()).thenReturn("testStreanId");
		long tempValue = 1L;
		Mockito.doReturn(tempValue).when(messagePublisher).publishMessages(Mockito.anyList(), Mockito.anyString());

		long msgCounter = messagePublisherServiceImpl.publishMessageToStream(atpMessageDtoList);
		Assert.isTrue(1L == msgCounter, "Value should be one");

		tempValue = 2L;
		Mockito.doReturn(tempValue).when(messagePublisher).publishMessages(Mockito.anyList(), Mockito.anyString());
		msgCounter = messagePublisherServiceImpl.publishMessageToStream(atpMessageDtoList);
		Assert.isTrue(2L == msgCounter, "Value should be one");

		tempValue = 0L;
		Mockito.doReturn(tempValue).when(messagePublisher).publishMessages(Mockito.anyList(), Mockito.anyString());
		msgCounter = messagePublisherServiceImpl.publishMessageToStream(atpMessageDtoList);
		Assert.isTrue(0L == msgCounter, "Value should be one");
	}
}