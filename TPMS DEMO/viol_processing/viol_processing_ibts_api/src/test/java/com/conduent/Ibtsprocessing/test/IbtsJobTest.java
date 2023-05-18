package com.conduent.Ibtsprocessing.test;

import java.time.OffsetDateTime;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.conduent.Ibtsprocessing.constant.ConfigVariable;
import com.conduent.Ibtsprocessing.dto.IBTSViolDTO;
import com.conduent.Ibtsprocessing.dto.QueueMessage;
import com.conduent.Ibtsprocessing.oss.OssStreamClient;
import com.conduent.Ibtsprocessing.serviceimpl.IbtsProcessingJob;
import com.conduent.app.timezone.utility.TimeZoneConv;

@ExtendWith(MockitoExtension.class)
public class IbtsJobTest {
	@Mock
	RestTemplate restTemplate;

	@InjectMocks
	private ConfigVariable configVariable;
	
	@Mock
	private QueueMessage queueMessage;
	
	@Mock
	IBTSViolDTO ibtsViolDTO;

	@InjectMocks
	IbtsProcessingJob ibtsProcessingJob;

	@InjectMocks
	TestConstants testConstants;
	
	@Mock
	OssStreamClient ossStreamClient;
	
	@Autowired
	TimeZoneConv timeZoneConv;
	
	@Test
	void TestMessageFromQueueOkResponse() {
		String queueMsg ="{\"laneTxId\":1112,\"txExternRefNo\":\"0000406619510001\",\"txSeqNumber\":18279,\"externFileId\":11224256,\"plazaAgencyId\":1,\"plazaId\":15,\"laneId\":101,\"txTimeStamp\":\"2022-02-14T10:15:30-05:00\",\"txModSeq\":0,\"txTypeInd\":\"V\",\"txSubtypeInd\":\"F\",\"laneMode\":9,\"laneHealth\":0,\"collectorId\":39009,\"entryDataSource\":\"T\",\"entryLaneId\":74,\"entryPlazaId\":7,\"entryTimestamp\":\"2022-02-14T10:15:30-05:00\",\"entryVehicleSpeed\":70,\"laneTxStatus\":0,\"lanetxType\":0,\"tollRevenueType\":9,\"actualClass\":22,\"actualAxles\":2,\"actualExtraAxles\":2,\"collectorClass\":0,\"collectorAxles\":0,\"preClassClass\":0,\"preClassAxles\":0,\"postClassClass\":22,\"postClassAxles\":2,\"forwardAxles\":0,\"reverseAxles\":0,\"collectedAmount\":0.0,\"unrealizedAmount\":0.0,\"isDiscountable\":\"N\",\"isMedianFare\":\"N\",\"isPeak\":\"N\",\"priceScheduleId\":0,\"vehicleSpeed\":70,\"receiptIssued\":0,\"deviceNo\":\"00400012292\",\"deviceAxles\":12,\"etcAccountId\":11817219,\"readAviAxles\":12,\"deviceProgramStatus\":\"0\",\"bufferReadFlag\":\"T\",\"postDeviceStatus\":0,\"preTxnBalance\":0.0,\"planTypeId\":0,\"etcTxStatus\":72,\"imageTaken\":\"F\",\"plateCountry\":\"***\",\"plateState\":\"**\",\"plateNumber\":\"**********\",\"revenueDate\":\"2021-10-09\",\"atpFileId\":0,\"isReversed\":\"N\",\"corrReasonId\":0,\"reconStatusInd\":0,\"reconSubCodeInd\":0,\"mileage\":0,\"deviceReadCount\":82,\"deviceWriteCount\":0,\"entryDeviceReadCount\":82,\"entryDeviceWriteCount\":0,\"txDate\":\"2021-10-08\",\"updateTs\":\"2021-10-11T07:30:10.940483\",\"tollSystemType\":\"C\",\"aetFlag\":\"Y\",\"eventTimestamp\":\"2021-10-11T07:30:10.940428\",\"eventType\":1,\"prevViolTxStatus\":0,\"violTxStatus\":3,\"violType\":3,\"reciprocityTrx\":\"F\",\"etcFareAmount\":0.0,\"postedFareAmount\":0.0,\"expectedRevenueAmount\":0.0,\"videoFareAmount\":0.0,\"cashFareAmount\":0.0}";
		OffsetDateTime offsetDT = OffsetDateTime.now();  
		QueueMessage queueobj = new QueueMessage();
		queueobj = queueobj.dtoFromJson(queueMsg);
		queueMessage.setTxTimestamp(offsetDT);
		//queueMessage.setEventTimestamp(timeZoneConv.currentTime());
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		IBTSViolDTO accountTollPostDTO = testConstants.getIbtsDTO(queueobj);
		HttpEntity<IBTSViolDTO> entity = new HttpEntity<IBTSViolDTO>(accountTollPostDTO, headers);
		String result = "200 OK";
		Mockito.when(restTemplate.postForEntity(testConstants.getIbtsApiUri(), entity, String.class))
				.thenReturn(new ResponseEntity<String>(result, HttpStatus.OK));
		
		ResponseEntity<String> response = restTemplate.postForEntity(testConstants.getIbtsApiUri(), entity, String.class);
		Assertions.assertEquals(result,response.getBody());
	
	}
	

	@Test
	void TestMessageFromQueueBadResponse() {
		String queueMsg = "{\"laneTxId\":1112,\"txExternRefNo\":\"0000406619510001\",\"txSeqNumber\":18279,\"externFileId\":11224256,\"plazaAgencyId\":1,\"plazaId\":15,\"laneId\":101,\"txTimestamp\":\"2022-02-14T10:15:12.065-05:00\",\"txModSeq\":0,\"txTypeInd\":\"V\",\"txSubtypeInd\":\"F\",\"laneMode\":9,\"laneHealth\":0,\"collectorNumber\":39009,\"entryDataSource\":\"T\",\"entryLaneId\":74,\"entryPlazaId\":7,\"entryTimestamp\":\"2022-02-14T10:15:12.065-05:00\",\"entryVehicleSpeed\":70,\"laneTxStatus\":0,\"lanetxType\":0,\"tollRevenueType\":9,\"actualClass\":22,\"actualAxles\":2,\"actualExtraAxles\":2,\"collectorClass\":0,\"collectorAxles\":0,\"preClassClass\":0,\"preClassAxles\":0,\"postClassClass\":22,\"postClassAxles\":2,\"forwardAxles\":0,\"reverseAxles\":0,\"collectedAmount\":0.0,\"unrealizedAmount\":0.0,\"isDiscountable\":\"N\",\"isMedianFare\":\"N\",\"isPeak\":\"N\",\"priceScheduleId\":0,\"vehicleSpeed\":70,\"receiptIssued\":0,\"deviceNo\":\"00400012292\",\"deviceAxles\":12,\"etcAccountId\":11817219,\"readAviAxles\":12,\"deviceProgramStatus\":\"0\",\"bufferReadFlag\":\"T\",\"postDeviceStatus\":0,\"preTxnBalance\":0.0,\"planTypeId\":0,\"etcTxStatus\":72,\"imageTaken\":\"F\",\"plateCountry\":\"***\",\"plateState\":\"**\",\"plateNumber\":\"**********\",\"revenueDate\":\"2021-10-09\",\"atpFileId\":0,\"isReversed\":\"N\",\"corrReasonId\":0,\"reconStatusInd\":0,\"reconSubCodeInd\":0,\"mileage\":0,\"deviceReadCount\":82,\"deviceWriteCount\":0,\"entryDeviceReadCount\":82,\"entryDeviceWriteCount\":0,\"txDate\":\"2021-10-08\",\"updateTs\":\"2021-10-11T07:30:10.940483\",\"tollSystemType\":\"C\",\"aetFlag\":\"Y\",\"eventTimestamp\":\"2021-10-11T07:30:10.940428\",\"eventType\":1,\"prevViolTxStatus\":0,\"violTxStatus\":3,\"violType\":3,\"reciprocityTrx\":\"F\",\"etcFareAmount\":0.0,\"postedFareAmount\":0.0,\"expectedRevenueAmount\":0.0,\"videoFareAmount\":0.0,\"cashFareAmount\":0.0}";
		QueueMessage queueobj = new QueueMessage();
		OffsetDateTime offsetDT = OffsetDateTime.now();  
		queueobj = queueobj.dtoFromJson(queueMsg);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		queueMessage.setTxTimestamp(null);
		IBTSViolDTO accountTollPostDTO = testConstants.getIbtsDTO(queueobj);
		HttpEntity<IBTSViolDTO> entity = new HttpEntity<IBTSViolDTO>(accountTollPostDTO, headers);
		String result = "400 Bad Request";
		Mockito.when(restTemplate.postForEntity(testConstants.getIbtsApiUri(), entity, String.class))
				.thenReturn(new ResponseEntity<String>(result, HttpStatus.BAD_REQUEST));
		
		ResponseEntity<String> response = restTemplate.postForEntity(testConstants.getIbtsApiUri(), entity, String.class);
		Assertions.assertEquals(result,response.getBody());
	}

	}

