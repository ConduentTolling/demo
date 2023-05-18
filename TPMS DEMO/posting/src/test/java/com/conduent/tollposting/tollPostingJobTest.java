package com.conduent.tollposting;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.conduent.parking.posting.constant.ConfigVariable;
import com.conduent.parking.posting.dto.TollPostResponseDTO;
import com.conduent.parking.posting.serviceimpl.ParkingTollPostingJob;

//@ExtendWith(MockitoExtension.class)
public class tollPostingJobTest {
	@Mock
	RestTemplate restTemplate;

	@Mock
	private ConfigVariable configVariable;

	@InjectMocks
	ParkingTollPostingJob tollPostingJob;

	@InjectMocks
	TestConstants testConstants;



	//@Test
	void TestSetTransactionTollPosting_restApiBadRequest() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		String accountTollPostDTO = "{\"laneTxId\":1552,\"txExternRefNo\":\"000000023509000007\",\"txSeqNumber\":27856,\"externFileId\":26,\"plazaAgencyId\":1,\"plazaId\":72,\"laneId\":9,\"txTimestamp\":\"2021-04-14T21:45:50.892\",\"txModSeq\":0,\"txTypeInd\":\"V\",\"txSubtypeInd\":\"T\",\"tillSystemType\":null,\"laneMode\":9,\"laneType\":null,\"laneState\":null,\"laneHealth\":0,\"collectorId\":\"78999\",\"tourSegmentId\":null,\"entryDataSource\":\"*\",\"entryLaneId\":null,\"entryPlazaId\":null,\"entryTimeStamp\":null,\"entryTxSeqNumber\":null,\"entryVehicleSpeed\":null,\"laneTxStatus\":0,\"lanetxType\":0,\"tollRevenueType\":1,\"actualClass\":28,\"actualAxles\":5,\"actualExtraAxles\":3,\"collectorClass\":0,\"collectorAxles\":0,\"preClassClass\":0,\"preClassAxles\":0,\"postClassClass\":28,\"postClassAxles\":5,\"forwardAxles\":0,\"reverseAxles\":0,\"fullFareAmount\":350.0,\"discountedAmount\":0.0,\"collectedAmount\":0.0,\"unrealizedAmount\":0.0,\"isDiscountable\":\"N\",\"isMedianFare\":\"N\",\"isPeak\":\"N\",\"priceScheduleId\":0,\"vehicleSpeed\":null,\"receiptIssued\":0,\"deviceNo\":\"12345\",\"accountType\":null,\"deviceCodedClass\":null,\"deviceAgencyClass\":null,\"deviceIagClass\":null,\"deviceAxles\":5,\"etcAccountId\":250111,\"accountAgencyId\":1234,\"readAviClass\":null,\"readAviAxles\":5,\"deviceProgramStatus\":0,\"bufferReadFlag\":25,\"laneDeviceStatus\":null,\"postDeviceStatus\":0,\"preTxnBalance\":0,\"planTypeId\":0,\"etcTxStatus\":72,\"speedViolFlag\":null,\"imageTaken\":\"F\",\"plateCountry\":\"***\",\"plateState\":\"**\",\"plateNumber\":\"**********\",\"revenueDate\":\"2021-04-13\",\"postedDate\":null,\"atpFileId\":\"\",\"isReversed\":\"N\",\"corrReasonId\":0,\"reconDate\":null,\"reconStatusInd\":0,\"reconSubCodeInd\":0,\"externFileDate\":null,\"mileage\":0,\"deviceReadCount\":25,\"deviceWriteCount\":0,\"entryDeviceReadCount\":25,\"entryDeviceWriteCount\":0,\"depositId\":0,\"txDate\":\"2021-04-13\",\"cscLookupKey\":null,\"updateTs\":\"2021-04-14T21:45:50.892\",\"tollSystemType\":\"P\",\"aetFlag\":\"Y\",\"eventTimestamp\":\"14-Apr-21 21.04.89.891000000 PM\",\"eventType\":1,\"prevViolTxStatus\":0,\"violTxStatus\":3,\"violType\":3}";
		HttpEntity<String> entity = new HttpEntity<String>(accountTollPostDTO, headers);
		String result = "{\r\n" + "    \"status\": true,\r\n" + "    \"httpStatus\": \"OK\",\r\n"
				+ "    \"message\": null,\r\n" + "    \"result\": {\r\n" + "        \"laneTxId\": 411,\r\n"
				+ "        \"etcAccountId\": \"207\",\r\n" + "        \"status\": \"1\",\r\n"
				+ "        \"accountBalance\": 163.0,\r\n"
				+ "        \"revenueDate\": \"2021-04-21T04:48:50.581+00:00\",\r\n"
				+ "        \"postedDate\": \"2021-04-21T04:48:50.581+00:00\",\r\n"
				+ "        \"depositId\": \"2-CH8RpOje\"\r\n" + "    },\r\n" + "    \"errors\": null,\r\n"
				+ "    \"fieldErrors\": null\r\n" + "}";
		Mockito.when(restTemplate.postForEntity(testConstants.getTollPostingUri(), entity, String.class))
				.thenReturn(new ResponseEntity<String>(result, HttpStatus.BAD_REQUEST));
		//TollPostResponseDTO obj = tollPostingJob.tollPosting(accountTollPostDTO, testConstants.getTollPostingUri());
		//Assertions.assertEquals(null, obj);
	}

	//@Test
	void TestSetTransactionTollPosting_restApiServerError() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		String accountTollPostDTO = "{\"laneTxId\":1552,\"txExternRefNo\":\"000000023509000007\",\"txSeqNumber\":27856,\"externFileId\":26,\"plazaAgencyId\":1,\"plazaId\":72,\"laneId\":9,\"txTimestamp\":\"2021-04-14T21:45:50.892\",\"txModSeq\":0,\"txTypeInd\":\"V\",\"txSubtypeInd\":\"T\",\"tillSystemType\":null,\"laneMode\":9,\"laneType\":null,\"laneState\":null,\"laneHealth\":0,\"collectorId\":\"78999\",\"tourSegmentId\":null,\"entryDataSource\":\"*\",\"entryLaneId\":null,\"entryPlazaId\":null,\"entryTimeStamp\":null,\"entryTxSeqNumber\":null,\"entryVehicleSpeed\":null,\"laneTxStatus\":0,\"lanetxType\":0,\"tollRevenueType\":1,\"actualClass\":28,\"actualAxles\":5,\"actualExtraAxles\":3,\"collectorClass\":0,\"collectorAxles\":0,\"preClassClass\":0,\"preClassAxles\":0,\"postClassClass\":28,\"postClassAxles\":5,\"forwardAxles\":0,\"reverseAxles\":0,\"fullFareAmount\":350.0,\"discountedAmount\":0.0,\"collectedAmount\":0.0,\"unrealizedAmount\":0.0,\"isDiscountable\":\"N\",\"isMedianFare\":\"N\",\"isPeak\":\"N\",\"priceScheduleId\":0,\"vehicleSpeed\":null,\"receiptIssued\":0,\"deviceNo\":\"12345\",\"accountType\":null,\"deviceCodedClass\":null,\"deviceAgencyClass\":null,\"deviceIagClass\":null,\"deviceAxles\":5,\"etcAccountId\":250111,\"accountAgencyId\":1234,\"readAviClass\":null,\"readAviAxles\":5,\"deviceProgramStatus\":0,\"bufferReadFlag\":25,\"laneDeviceStatus\":null,\"postDeviceStatus\":0,\"preTxnBalance\":0,\"planTypeId\":0,\"etcTxStatus\":72,\"speedViolFlag\":null,\"imageTaken\":\"F\",\"plateCountry\":\"***\",\"plateState\":\"**\",\"plateNumber\":\"**********\",\"revenueDate\":\"2021-04-13\",\"postedDate\":null,\"atpFileId\":\"\",\"isReversed\":\"N\",\"corrReasonId\":0,\"reconDate\":null,\"reconStatusInd\":0,\"reconSubCodeInd\":0,\"externFileDate\":null,\"mileage\":0,\"deviceReadCount\":25,\"deviceWriteCount\":0,\"entryDeviceReadCount\":25,\"entryDeviceWriteCount\":0,\"depositId\":0,\"txDate\":\"2021-04-13\",\"cscLookupKey\":null,\"updateTs\":\"2021-04-14T21:45:50.892\",\"tollSystemType\":\"P\",\"aetFlag\":\"Y\",\"eventTimestamp\":\"14-Apr-21 21.04.89.891000000 PM\",\"eventType\":1,\"prevViolTxStatus\":0,\"violTxStatus\":3,\"violType\":3}";
		HttpEntity<String> entity = new HttpEntity<String>(accountTollPostDTO, headers);
		String result = "{\r\n" + "    \"status\": true,\r\n" + "    \"httpStatus\": \"OK\",\r\n"
				+ "    \"message\": null,\r\n" + "    \"result\": {\r\n" + "        \"laneTxId\": 411,\r\n"
				+ "        \"etcAccountId\": \"207\",\r\n" + "        \"status\": \"1\",\r\n"
				+ "        \"accountBalance\": 163.0,\r\n"
				+ "        \"revenueDate\": \"2021-04-21T04:48:50.581+00:00\",\r\n"
				+ "        \"postedDate\": \"2021-04-21T04:48:50.581+00:00\",\r\n"
				+ "        \"depositId\": \"2-CH8RpOje\"\r\n" + "    },\r\n" + "    \"errors\": null,\r\n"
				+ "    \"fieldErrors\": null\r\n" + "}";
		Mockito.when(restTemplate.postForEntity(testConstants.getTollPostingUri(), entity, String.class))
				.thenReturn(new ResponseEntity<String>(result, HttpStatus.INTERNAL_SERVER_ERROR));
		//TollPostResponseDTO obj = tollPostingJob.tollPosting(accountTollPostDTO, testConstants.getTollPostingUri());
		//Assertions.assertEquals(null, obj);
	}

	//@Test
	void TestSetTransactionTollPosting_restApiRequestWithValidResponse() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		String accountTollPostDTO = "{\"laneTxId\":1552,\"result\":1552,\"txExternRefNo\":\"000000023509000007\",\"txSeqNumber\":27856,\"externFileId\":26,\"plazaAgencyId\":1,\"plazaId\":72,\"laneId\":9,\"txTimestamp\":\"2021-04-14T21:45:50.892\",\"txModSeq\":0,\"txTypeInd\":\"V\",\"txSubtypeInd\":\"T\",\"tillSystemType\":null,\"laneMode\":9,\"laneType\":null,\"laneState\":null,\"laneHealth\":0,\"collectorId\":\"78999\",\"tourSegmentId\":null,\"entryDataSource\":\"*\",\"entryLaneId\":null,\"entryPlazaId\":null,\"entryTimeStamp\":null,\"entryTxSeqNumber\":null,\"entryVehicleSpeed\":null,\"laneTxStatus\":0,\"lanetxType\":0,\"tollRevenueType\":1,\"actualClass\":28,\"actualAxles\":5,\"actualExtraAxles\":3,\"collectorClass\":0,\"collectorAxles\":0,\"preClassClass\":0,\"preClassAxles\":0,\"postClassClass\":28,\"postClassAxles\":5,\"forwardAxles\":0,\"reverseAxles\":0,\"fullFareAmount\":350.0,\"discountedAmount\":0.0,\"collectedAmount\":0.0,\"unrealizedAmount\":0.0,\"isDiscountable\":\"N\",\"isMedianFare\":\"N\",\"isPeak\":\"N\",\"priceScheduleId\":0,\"vehicleSpeed\":null,\"receiptIssued\":0,\"deviceNo\":\"12345\",\"accountType\":null,\"deviceCodedClass\":null,\"deviceAgencyClass\":null,\"deviceIagClass\":null,\"deviceAxles\":5,\"etcAccountId\":250111,\"accountAgencyId\":1234,\"readAviClass\":null,\"readAviAxles\":5,\"deviceProgramStatus\":0,\"bufferReadFlag\":25,\"laneDeviceStatus\":null,\"postDeviceStatus\":0,\"preTxnBalance\":0,\"planTypeId\":0,\"etcTxStatus\":72,\"speedViolFlag\":null,\"imageTaken\":\"F\",\"plateCountry\":\"***\",\"plateState\":\"**\",\"plateNumber\":\"**********\",\"revenueDate\":\"2021-04-13\",\"postedDate\":null,\"atpFileId\":\"\",\"isReversed\":\"N\",\"corrReasonId\":0,\"reconDate\":null,\"reconStatusInd\":0,\"reconSubCodeInd\":0,\"externFileDate\":null,\"mileage\":0,\"deviceReadCount\":25,\"deviceWriteCount\":0,\"entryDeviceReadCount\":25,\"entryDeviceWriteCount\":0,\"depositId\":0,\"txDate\":\"2021-04-13\",\"cscLookupKey\":null,\"updateTs\":\"2021-04-14T21:45:50.892\",\"tollSystemType\":\"P\",\"aetFlag\":\"Y\",\"eventTimestamp\":\"14-Apr-21 21.04.89.891000000 PM\",\"eventType\":1,\"prevViolTxStatus\":0,\"violTxStatus\":3,\"violType\":3}";
		HttpEntity<String> entity = new HttpEntity<String>(accountTollPostDTO, headers);
		String result = "{\r\n" + "    \"status\": true,\r\n" + "    \"httpStatus\": \"OK\",\r\n"
				+ "    \"message\": null,\r\n" + "    \"result\": {\r\n" + "        \"laneTxId\": 411,\r\n"
				+ "        \"etcAccountId\": \"207\",\r\n" + "        \"status\": \"1\",\r\n"
				+ "        \"accountBalance\": 163.0,\r\n"
				+ "        \"revenueDate\": \"2021-04-21T04:48:50.581+00:00\",\r\n"
				+ "        \"postedDate\": \"2021-04-21T04:48:50.581+00:00\",\r\n"
				+ "        \"depositId\": \"2-CH8RpOje\"\r\n" + "    },\r\n" + "    \"errors\": null,\r\n"
				+ "    \"fieldErrors\": null\r\n" + "}";
		Mockito.when(restTemplate.postForEntity(testConstants.getTollPostingUri(), entity, String.class))
				.thenReturn(new ResponseEntity<String>(result, HttpStatus.OK));

		//TollPostResponseDTO actualObj = tollPostingJob.tollPosting(accountTollPostDTO,
				//testConstants.getTollPostingUri());
		TollPostResponseDTO expectedObj = testConstants.getValues();
		//Assertions.assertNotNull(actualObj);
		//Assertions.assertEquals(expectedObj.getLaneTxId(), actualObj.getLaneTxId());
		//Assertions.assertEquals(expectedObj.getDepositId(), actualObj.getDepositId());
		//Assertions.assertEquals(expectedObj.getStatus(), actualObj.getStatus());
		//Assertions.assertEquals(expectedObj.getPostedDate(), actualObj.getPostedDate());

	}

	
	// @Test
	void tollPosting() throws IOException {
		// OssStreamClient atpQueueClient=new
		// OssStreamClient(configVariable,configVariable.getAtpQueue(),"tollingGroup");
		// Mockito.when(tollPostingJob.getMessages(atpQueueClient,
		// 1)).thenReturn({\"laneTxId\":1552,\"txExternRefNo\":\"000000023509000007\",\"txSeqNumber\":27856,\"externFileId\":26,\"plazaAgencyId\":1,\"plazaId\":72,\"laneId\":9,\"txTimestamp\":\"2021-04-14T21:45:50.892\",\"txModSeq\":0,\"txTypeInd\":\"V\",\"txSubtypeInd\":\"T\",\"tillSystemType\":null,\"laneMode\":9,\"laneType\":null,\"laneState\":null,\"laneHealth\":0,\"collectorId\":\"78999\",\"tourSegmentId\":null,\"entryDataSource\":\"*\",\"entryLaneId\":null,\"entryPlazaId\":null,\"entryTimeStamp\":null,\"entryTxSeqNumber\":null,\"entryVehicleSpeed\":null,\"laneTxStatus\":0,\"lanetxType\":0,\"tollRevenueType\":1,\"actualClass\":28,\"actualAxles\":5,\"actualExtraAxles\":3,\"collectorClass\":0,\"collectorAxles\":0,\"preClassClass\":0,\"preClassAxles\":0,\"postClassClass\":28,\"postClassAxles\":5,\"forwardAxles\":0,\"reverseAxles\":0,\"fullFareAmount\":350.0,\"discountedAmount\":0.0,\"collectedAmount\":0.0,\"unrealizedAmount\":0.0,\"isDiscountable\":\"N\",\"isMedianFare\":\"N\",\"isPeak\":\"N\",\"priceScheduleId\":0,\"vehicleSpeed\":null,\"receiptIssued\":0,\"deviceNo\":\"12345\",\"accountType\":null,\"deviceCodedClass\":null,\"deviceAgencyClass\":null,\"deviceIagClass\":null,\"deviceAxles\":5,\"etcAccountId\":250111,\"accountAgencyId\":1234,\"readAviClass\":null,\"readAviAxles\":5,\"deviceProgramStatus\":0,\"bufferReadFlag\":25,\"laneDeviceStatus\":null,\"postDeviceStatus\":0,\"preTxnBalance\":0,\"planTypeId\":0,\"etcTxStatus\":72,\"speedViolFlag\":null,\"imageTaken\":\"F\",\"plateCountry\":\"***\",\"plateState\":\"**\",\"plateNumber\":\"**********\",\"revenueDate\":\"2021-04-13\",\"postedDate\":null,\"atpFileId\":\"\",\"isReversed\":\"N\",\"corrReasonId\":0,\"reconDate\":null,\"reconStatusInd\":0,\"reconSubCodeInd\":0,\"externFileDate\":null,\"mileage\":0,\"deviceReadCount\":25,\"deviceWriteCount\":0,\"entryDeviceReadCount\":25,\"entryDeviceWriteCount\":0,\"depositId\":0,\"txDate\":\"2021-04-13\",\"cscLookupKey\":null,\"updateTs\":\"2021-04-14T21:45:50.892\",\"tollSystemType\":\"P\",\"aetFlag\":\"Y\",\"eventTimestamp\":\"14-Apr-21
		// 21.04.89.891000000
		// PM\",\"eventType\":1,\"prevViolTxStatus\":0,\"violTxStatus\":3,\"violType\":3});
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		String accountTollPostDTO = "{\"laneTxId\":1552,\"txExternRefNo\":\"000000023509000007\",\"txSeqNumber\":27856,\"externFileId\":26,\"plazaAgencyId\":1,\"plazaId\":72,\"laneId\":9,\"txTimestamp\":\"2021-04-14T21:45:50.892\",\"txModSeq\":0,\"txTypeInd\":\"V\",\"txSubtypeInd\":\"T\",\"tillSystemType\":null,\"laneMode\":9,\"laneType\":null,\"laneState\":null,\"laneHealth\":0,\"collectorId\":\"78999\",\"tourSegmentId\":null,\"entryDataSource\":\"*\",\"entryLaneId\":null,\"entryPlazaId\":null,\"entryTimeStamp\":null,\"entryTxSeqNumber\":null,\"entryVehicleSpeed\":null,\"laneTxStatus\":0,\"lanetxType\":0,\"tollRevenueType\":1,\"actualClass\":28,\"actualAxles\":5,\"actualExtraAxles\":3,\"collectorClass\":0,\"collectorAxles\":0,\"preClassClass\":0,\"preClassAxles\":0,\"postClassClass\":28,\"postClassAxles\":5,\"forwardAxles\":0,\"reverseAxles\":0,\"fullFareAmount\":350.0,\"discountedAmount\":0.0,\"collectedAmount\":0.0,\"unrealizedAmount\":0.0,\"isDiscountable\":\"N\",\"isMedianFare\":\"N\",\"isPeak\":\"N\",\"priceScheduleId\":0,\"vehicleSpeed\":null,\"receiptIssued\":0,\"deviceNo\":\"12345\",\"accountType\":null,\"deviceCodedClass\":null,\"deviceAgencyClass\":null,\"deviceIagClass\":null,\"deviceAxles\":5,\"etcAccountId\":250111,\"accountAgencyId\":1234,\"readAviClass\":null,\"readAviAxles\":5,\"deviceProgramStatus\":0,\"bufferReadFlag\":25,\"laneDeviceStatus\":null,\"postDeviceStatus\":0,\"preTxnBalance\":0,\"planTypeId\":0,\"etcTxStatus\":72,\"speedViolFlag\":null,\"imageTaken\":\"F\",\"plateCountry\":\"***\",\"plateState\":\"**\",\"plateNumber\":\"**********\",\"revenueDate\":\"2021-04-13\",\"postedDate\":null,\"atpFileId\":\"\",\"isReversed\":\"N\",\"corrReasonId\":0,\"reconDate\":null,\"reconStatusInd\":0,\"reconSubCodeInd\":0,\"externFileDate\":null,\"mileage\":0,\"deviceReadCount\":25,\"deviceWriteCount\":0,\"entryDeviceReadCount\":25,\"entryDeviceWriteCount\":0,\"depositId\":0,\"txDate\":\"2021-04-13\",\"cscLookupKey\":null,\"updateTs\":\"2021-04-14T21:45:50.892\",\"tollSystemType\":\"P\",\"aetFlag\":\"Y\",\"eventTimestamp\":\"14-Apr-21 21.04.89.891000000 PM\",\"eventType\":1,\"prevViolTxStatus\":0,\"violTxStatus\":3,\"violType\":3}";
		String url = "http://193.122.158.101:9696/fpms/tpmsIntegration/accountToll";
		HttpEntity<String> entity = new HttpEntity<String>(accountTollPostDTO, headers);
		/*
		 * Mockito.when(restTemplate.postForEntity(
		 * "http://193.122.158.101:9696/fpms/tpmsIntegration/accountToll", entity,
		 * String.class)).thenReturn(new ResponseEntity<String>(accountTollPostDTO,
		 * HttpStatus.OK));
		 */
		//TollPostResponseDTO obj = tollPostingJob.tollPosting(accountTollPostDTO, url);
		ResponseEntity<String> result = restTemplate.postForEntity(url, entity, String.class);
		// Assertions.assertSame("200", result.getStatusCode());

		// Mockito.when(new Gson().fromJson(JsonObject.class, JsonObject.class)).
		// Assertions.assertEquals(accountTollPostDTO, obj);
		/*
		 * assertThrows(NullPointerException.class, () -> { TollPostResponseDTO obj1 =
		 * tollPostingJob.tollPosting(null, null); ResponseEntity<String> result1 =
		 * restTemplate.postForEntity(url, entity, String.class);
		 * 
		 * });
		 */

	}

	// @Test(expected=NullPointerException.class)
	void nullPointerException() {

		try {
			//tollPostingJob.tollPosting(null, null);
			fail();
		} catch (NullPointerException ex) {
			assertFalse(ex instanceof NullPointerException);
		}

		/*
		 * assertThrows(NullPointerException.class, () -> { TollPostResponseDTO obj =
		 * tollPostingJob.tollPosting(null, null);
		 * 
		 * });
		 */

		/*
		 * Assertions.assertThrows(NullPointerException.class, () -> {
		 * tollPostingJob.tollPosting(null, null); });
		 */

		/*
		 * NullPointerException thrown = assertThrows( NullPointerException.class, () ->
		 * tollPostingJob.tollPosting(null, null),
		 * "Expected doThing() to throw, but it didn't" );
		 */

		/*
		 * HttpHeaders headers = new HttpHeaders();
		 * headers.setContentType(MediaType.APPLICATION_JSON); String accountTollPostDTO
		 * =
		 * "{\"laneTxId\":1552,\"txExternRefNo\":\"000000023509000007\",\"txSeqNumber\":27856,\"externFileId\":26,\"plazaAgencyId\":1,\"plazaId\":72,\"laneId\":9,\"txTimestamp\":\"2021-04-14T21:45:50.892\",\"txModSeq\":0,\"txTypeInd\":\"V\",\"txSubtypeInd\":\"T\",\"tillSystemType\":null,\"laneMode\":9,\"laneType\":null,\"laneState\":null,\"laneHealth\":0,\"collectorId\":\"78999\",\"tourSegmentId\":null,\"entryDataSource\":\"*\",\"entryLaneId\":null,\"entryPlazaId\":null,\"entryTimeStamp\":null,\"entryTxSeqNumber\":null,\"entryVehicleSpeed\":null,\"laneTxStatus\":0,\"lanetxType\":0,\"tollRevenueType\":1,\"actualClass\":28,\"actualAxles\":5,\"actualExtraAxles\":3,\"collectorClass\":0,\"collectorAxles\":0,\"preClassClass\":0,\"preClassAxles\":0,\"postClassClass\":28,\"postClassAxles\":5,\"forwardAxles\":0,\"reverseAxles\":0,\"fullFareAmount\":350.0,\"discountedAmount\":0.0,\"collectedAmount\":0.0,\"unrealizedAmount\":0.0,\"isDiscountable\":\"N\",\"isMedianFare\":\"N\",\"isPeak\":\"N\",\"priceScheduleId\":0,\"vehicleSpeed\":null,\"receiptIssued\":0,\"deviceNo\":\"12345\",\"accountType\":null,\"deviceCodedClass\":null,\"deviceAgencyClass\":null,\"deviceIagClass\":null,\"deviceAxles\":5,\"etcAccountId\":250111,\"accountAgencyId\":1234,\"readAviClass\":null,\"readAviAxles\":5,\"deviceProgramStatus\":0,\"bufferReadFlag\":25,\"laneDeviceStatus\":null,\"postDeviceStatus\":0,\"preTxnBalance\":0,\"planTypeId\":0,\"etcTxStatus\":72,\"speedViolFlag\":null,\"imageTaken\":\"F\",\"plateCountry\":\"***\",\"plateState\":\"**\",\"plateNumber\":\"**********\",\"revenueDate\":\"2021-04-13\",\"postedDate\":null,\"atpFileId\":\"\",\"isReversed\":\"N\",\"corrReasonId\":0,\"reconDate\":null,\"reconStatusInd\":0,\"reconSubCodeInd\":0,\"externFileDate\":null,\"mileage\":0,\"deviceReadCount\":25,\"deviceWriteCount\":0,\"entryDeviceReadCount\":25,\"entryDeviceWriteCount\":0,\"depositId\":0,\"txDate\":\"2021-04-13\",\"cscLookupKey\":null,\"updateTs\":\"2021-04-14T21:45:50.892\",\"tollSystemType\":\"P\",\"aetFlag\":\"Y\",\"eventTimestamp\":\"14-Apr-21 21.04.89.891000000 PM\",\"eventType\":1,\"prevViolTxStatus\":0,\"violTxStatus\":3,\"violType\":3}"
		 * ; String url =
		 * "http://193.122.158.101:9696/fpms/tpmsIntegration/accountToll";
		 * HttpEntity<String> entity = new HttpEntity<String>(accountTollPostDTO,
		 * headers);
		 * 
		 * Assertions.assertThrows(NullPointerException.class, () -> {
		 * Mockito.when(restTemplate.postForEntity(
		 * "http://193.122.158.101:9696/fpms/tpmsIntegration/accountToll", entity,
		 * String.class)).thenReturn(new ResponseEntity<String>(accountTollPostDTO,
		 * HttpStatus.OK));
		 * 
		 * });
		 */

	}

}
