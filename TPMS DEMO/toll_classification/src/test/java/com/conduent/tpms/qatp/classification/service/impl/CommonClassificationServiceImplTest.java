package com.conduent.tpms.qatp.classification.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.conduent.tpms.qatp.classification.dao.ComputeTollDao;
import com.conduent.tpms.qatp.classification.dto.CommonClassificationDto;
import com.conduent.tpms.qatp.classification.model.ConfigVariable;
import com.conduent.tpms.qatp.classification.utility.MessagePublisherImpl;

@ExtendWith(MockitoExtension.class)
public class CommonClassificationServiceImplTest {

	@Mock
	ConfigVariable configVariable;

	@Mock
	private ComputeTollDao computeTollDAO;

	@Mock
	private MessagePublisherImpl messagePublisher;
	
	@InjectMocks
	private CommonClassificationServiceImpl commonClassificationServiceImpl;

	@Test
	void testInsertToHomeEtcQueue() {
		CommonClassificationDto commonClassificationDto = new CommonClassificationDto();
		commonClassificationServiceImpl.insertToHomeEtcQueue(commonClassificationDto);
		Mockito.verify(computeTollDAO, Mockito.times(1)).insertInToHomeEtcQueue(commonClassificationDto);
	}

	@Test
	void testInsertToViolQueue() {
		CommonClassificationDto commonClassificationDto = new CommonClassificationDto();
		commonClassificationServiceImpl.insertToViolQueue(commonClassificationDto);
		Mockito.verify(computeTollDAO, Mockito.times(1)).insertInToViolQueue(commonClassificationDto);
	}

	//@Test
	void testPushMessage() {
		CommonClassificationDto commonClassificationDto = new CommonClassificationDto();
		commonClassificationServiceImpl.pushMessage(commonClassificationDto, "1234");
		Mockito.verify(messagePublisher, Mockito.times(1)).publishMessage(Mockito.any(String.class), Mockito.any(),
				Mockito.any(String.class));
	}

}
