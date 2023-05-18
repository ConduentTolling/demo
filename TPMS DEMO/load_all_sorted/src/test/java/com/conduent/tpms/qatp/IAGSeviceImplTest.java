package com.conduent.tpms.qatp;

import java.sql.SQLException;
import java.util.ArrayList;
import
java.util.List;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.web.client.RestTemplate;

import com.conduent.tpms.qatp.dao.IAGDao;
import com.conduent.tpms.qatp.dao.impl.IAGDaoImpl;
import com.conduent.tpms.qatp.dto.AccountApiInfoDto;
import com.conduent.tpms.qatp.dto.CustomerInfoDto;
import com.conduent.tpms.qatp.dto.PlanPolicyInfoDto;
import com.conduent.tpms.qatp.dto.TagStatusSortedRecord;
import com.conduent.tpms.qatp.model.ConfigVariable;
import com.conduent.tpms.qatp.model.VDeviceDto;
import com.conduent.tpms.qatp.service.impl.BuildTagStatusServiceImpl;
import com.conduent.tpms.qatp.service.impl.IAGSeviceImpl;
import com.conduent.tpms.qatp.service.impl.PlanStringServiceImpl;

/**
 * Test class for IAGSevice
 * 
 * @author Urvashi C
 *
 */

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
@TestInstance(Lifecycle.PER_CLASS) public class IAGSeviceImplTest {

	@InjectMocks IAGSeviceImpl iagSeviceImpl;

	@Mock IAGDao iagDao;

	@Mock IAGDaoImpl iagDaoImpl;

	@Mock RestTemplate restTemplate;

	@Mock ConfigVariable configVariable;

	@Mock BuildTagStatusServiceImpl buildTagStatusService;

	@Mock PlanStringServiceImpl planStringServiceImpl;


	@Mock private NamedParameterJdbcTemplate namedJdbcTemplate;

	List<VDeviceDto> list = new ArrayList<>();

	@BeforeAll public void setup() { VDeviceDto VDeviceDto = new VDeviceDto();
	VDeviceDto.setDeviceNo("00400010066"); VDeviceDto.setDeviceStatus(0);
	list.add(VDeviceDto); }


	@SuppressWarnings("deprecation")

	@Test public void loadValidTagDetailsTest() throws SQLException {
		List<VDeviceDto> deviceDtoList = new ArrayList<VDeviceDto>(); VDeviceDto
		VDeviceDto = new VDeviceDto(); VDeviceDto.setDeviceNo("00400010066");
		VDeviceDto.setDeviceStatus(0); deviceDtoList.add(VDeviceDto);

		List<TagStatusSortedRecord> tagStatusSortedRecordList = new ArrayList<>();
		TagStatusSortedRecord tagStatusSortedRecord = new TagStatusSortedRecord();
		tagStatusSortedRecord.setAccountNo("500000006297");
		tagStatusSortedRecord.setAgencyId(3);
		tagStatusSortedRecord.setEtcAccountId(521);
		tagStatusSortedRecord.setDeviceNo("deviceNo");
		tagStatusSortedRecord.setFinancialStatus(4);

		tagStatusSortedRecordList.add(tagStatusSortedRecord); String URI =
				"http://129.213.67.244/fpms/account/getfpmsaccount"; HttpHeaders headers =
				new HttpHeaders(); headers.setContentType(MediaType.APPLICATION_JSON); 
				String AccountApiInfoDto =
				"[etcAccountId=627, agencyId=3, accountType=PRIVATE, prepaidBalance=109.0, postpaidBalance=0.0, deviceDeposit=0.0, isDiscountEligible=false, lastTollTxId=null, postpaidFee=0.0, accountBalance=109.0, rebillAmount=25.0, rebillThresholdAmount=null, crmAccountId=15811dc6-3752-46f5-888b-a226745ab467, accountNumber=500000006271, payType=null, acctFinStatus=GOOD, deviceNo=00400010066]"; 
				AccountApiInfoDto dto = new AccountApiInfoDto();
				dto.setEtcAccountId(AccountApiInfoDto); ResponseEntity<String> responseEntity
				= new ResponseEntity<String>(HttpStatus.OK);

				Mockito.when(iagDao.getDeviceInformation(Mockito.anyString(),
						Mockito.anyString(),
						Mockito.anyString())).thenReturn(tagStatusSortedRecordList);
				Mockito.when(iagDao.getETCAccountInfo(Mockito.anyList(),Mockito.any(),Mockito.any())).thenReturn(
						tagStatusSortedRecordList);
				
				PlanPolicyInfoDto planPolicyInfoDto = new PlanPolicyInfoDto();
				planPolicyInfoDto.setLcAllPlans(1); planPolicyInfoDto.setRevPlanCount(1);
				planPolicyInfoDto.setItagInfo(0.5);
			
				//Mockito.when(iagSeviceImpl.callAccountApi(Mockito.any())).thenReturn(dto);
				
				Mockito.when(iagDao.checkifrecordexists(Mockito.any())).thenReturn(tagStatusSortedRecord);

				Mockito.when(iagDao.getPlanPolicyInfo(tagStatusSortedRecord.getEtcAccountId()
						, tagStatusSortedRecord.getDeviceNo())).thenReturn(planPolicyInfoDto);
				//Mockito.doNothing().when(iagDao).insertIntoTTagAllSorted(Mockito.anyList());
				iagSeviceImpl.loadValidTagDetails("00400010000", "00400010000",
						"Full","YES");

	}

	@Test public void loadValidTagDetailsExceptionTest() throws SQLException {
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		List<VDeviceDto> deviceDtoList = new ArrayList<VDeviceDto>(); VDeviceDto
		VDeviceDto = new VDeviceDto(); VDeviceDto.setDeviceNo("00400010066");
		VDeviceDto.setDeviceStatus(0); deviceDtoList.add(VDeviceDto);

		List<TagStatusSortedRecord> tagStatusSortedRecordList = new ArrayList<>();
		TagStatusSortedRecord tagStatusSortedRecord = new TagStatusSortedRecord();
		tagStatusSortedRecord.setAccountNo("500000006297");
		tagStatusSortedRecord.setAgencyId(3);
		tagStatusSortedRecord.setEtcAccountId(521);
		tagStatusSortedRecord.setDeviceNo("deviceNo");
		tagStatusSortedRecord.setFinancialStatus(4);

		tagStatusSortedRecordList.add(tagStatusSortedRecord); String URI =
				"http://129.213.67.244/fpms/account/getfpmsaccount"; HttpHeaders headers =
				new HttpHeaders(); headers.setContentType(MediaType.APPLICATION_JSON); String
				AccountApiInfoDto =
				"[etcAccountId=627, agencyId=3, accountType=PRIVATE, prepaidBalance=109.0, postpaidBalance=0.0, deviceDeposit=0.0, isDiscountEligible=false, lastTollTxId=null, postpaidFee=0.0, accountBalance=109.0, rebillAmount=25.0, rebillThresholdAmount=null, crmAccountId=15811dc6-3752-46f5-888b-a226745ab467, accountNumber=500000006271, payType=null, acctFinStatus=GOOD, deviceNo=00400010066]"
				; HttpEntity<String> entity = new HttpEntity<String>(AccountApiInfoDto,
						headers); CustomerInfoDto customerInfoDto = new CustomerInfoDto();

						String result = "{\r\n" + "    \"status\": true,\r\n" +
								"    \"httpStatus\": \"OK\",\r\n" + "    \"message\": null,\r\n" +
								"    \"result\": {\r\n" + "        \"etcAccountId\": \"521\",\r\n" +
								"        \"agencyId\": 3,\r\n" + "        \"accountType\": \"PRIVATE\",\r\n"
								+ "        \"prepaidBalance\": 0.0,\r\n" +
								"        \"postpaidBalance\": 0.0,\r\n" +
								"        \"updateTs\": \"2021-04-29 20:43:17.734\",\r\n" +
								"        \"openDate\": \"2021-04-29\",\r\n" +
								"        \"acctFinStatus\": \"AUTOPAY\",\r\n" +
								"        \"lastPaymentTxId\": null,\r\n" +
								"        \"lastPaymentDate\": null,\r\n" +
								"        \"lastFinTxId\": null,\r\n" + "        \"lastFinTxAmt\": 0.0,\r\n" +
								"        \"postpaidFee\": 0.0,\r\n" + "        \"accountBalance\": 0.0,\r\n"
								+ "        \"rebillAmount\": 25.0,\r\n" +
								"        \"thresholdAmount\": 10.0,\r\n" +
								"        \"crmAccountId\": \"e6f98aa9-53fb-471e-a201-d4b76221920e\",\r\n" +
								"        \"accountNumber\": \"500000005521\",\r\n" +
								"        \"tranType\": null\r\n" + "    },\r\n" + "    \"errors\": null,\r\n"
								+ "    \"fieldErrors\": null\r\n" + "}";

						Mockito.when(iagDao.getDeviceInformation(Mockito.anyString(),Mockito.
								anyString(), Mockito.anyString())).thenReturn(tagStatusSortedRecordList);
						Mockito.when(iagDao.getETCAccountInfo(Mockito.anyList(),Mockito.any(),Mockito.any())).thenReturn(
								tagStatusSortedRecordList);
						/*
						 * Mockito.when(configVariable.getAccountApiUri()).thenReturn(URI);
						 * Mockito.when(restTemplate.postForEntity(Mockito.anyString(), Mockito.any(),
						 * Mockito.eq(String.class))) .thenReturn(new ResponseEntity<String>(result,
						 * HttpStatus.BAD_REQUEST)); AccountApiInfoDto accountApiInfoDto = new
						 * AccountApiInfoDto(); accountApiInfoDto.setAccountBalance(100.0);
						 * accountApiInfoDto.setEtcAccountId("627"); accountApiInfoDto.setAgencyId(3);
						 * accountApiInfoDto.setAccountType("PRIVATE");
						 */

										Mockito.doNothing().when(iagDao).insertIntoTTagAllSorted(Mockito.anyList());
										iagSeviceImpl.loadValidTagDetails("00400010000", "00400010000",
												"Full","YES");

	}


	@Test public void callAccountApiTest() { String URI =
			"http://129.213.67.244/fpms/account/getfpmsaccount"; HttpHeaders headers =
			new HttpHeaders(); headers.setContentType(MediaType.APPLICATION_JSON);
			ResponseEntity<String> responseEntity = new
					ResponseEntity<String>(HttpStatus.ACCEPTED);

			Mockito.when(configVariable.getAccountApiUri()).thenReturn(URI);
			Mockito.when(restTemplate.postForEntity(Mockito.anyString(), Mockito.any(),
					Mockito.eq(String.class))) .thenReturn(responseEntity);

			AccountApiInfoDto accountApiInfoDto = iagSeviceImpl.callAccountApi("560");

			Assertions.assertNull(accountApiInfoDto);

	}

	@Test public void callAccountApiExceptionTest() { String URI =
			"http://129.213.67.244/fpms/account/getfpmsaccount"; HttpHeaders headers =
			new HttpHeaders(); headers.setContentType(MediaType.APPLICATION_JSON);
			ResponseEntity<String> responseEntity = new
					ResponseEntity<String>(HttpStatus.OK);
			Mockito.when(configVariable.getAccountApiUri()).thenReturn(URI);
			Mockito.when(restTemplate.postForEntity(Mockito.anyString(), Mockito.any(),
					Mockito.eq(String.class))) .thenReturn(responseEntity);

			AccountApiInfoDto accountApiInfoDto = iagSeviceImpl.callAccountApi("561");
			Assertions.assertNull(accountApiInfoDto); }
	
	
	@Test public void getFtagPlanInfoTest() {
		TagStatusSortedRecord tagStatusSortedRecord = new TagStatusSortedRecord();
		tagStatusSortedRecord.setAccountNo("500000006297");
		tagStatusSortedRecord.setAgencyId(3);
		tagStatusSortedRecord.setEtcAccountId(521);
		tagStatusSortedRecord.setDeviceNo("deviceNo");
		tagStatusSortedRecord.setFinancialStatus(4);
		tagStatusSortedRecord.setPlansStr("50000000629750000000629750000000629750000000");
		int val = iagSeviceImpl.getFtagPlanInfo(tagStatusSortedRecord);
		Assertions.assertNotNull(val);
		
	}
	
	
}

/*
 * package com.conduent.tpms.qatp;
 * 
 * import java.sql.SQLException; import java.util.ArrayList; import
 * java.util.List;
 * 
 * import org.junit.jupiter.api.Assertions; import
 * org.junit.jupiter.api.BeforeAll; import org.junit.jupiter.api.Test; import
 * org.junit.jupiter.api.TestInstance; import
 * org.junit.jupiter.api.TestInstance.Lifecycle; import
 * org.junit.jupiter.api.extension.ExtendWith; import org.mockito.InjectMocks;
 * import org.mockito.Matchers; import org.mockito.Mock; import
 * org.mockito.Mockito; import org.mockito.junit.jupiter.MockitoExtension;
 * import org.springframework.http.HttpEntity; import
 * org.springframework.http.HttpHeaders; import
 * org.springframework.http.HttpStatus; import
 * org.springframework.http.MediaType; import
 * org.springframework.http.ResponseEntity; import
 * org.springframework.jdbc.core.namedparam.MapSqlParameterSource; import
 * org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate; import
 * org.springframework.web.client.RestTemplate;
 * 
 * import com.conduent.tpms.qatp.constants.Constants; import
 * com.conduent.tpms.qatp.dao.IAGDao; import
 * com.conduent.tpms.qatp.dao.impl.IAGDaoImpl; import
 * com.conduent.tpms.qatp.dto.AccountApiInfoDto; import
 * com.conduent.tpms.qatp.dto.CustomerInfoDto; import
 * com.conduent.tpms.qatp.dto.PlanPolicyInfoDto; import
 * com.conduent.tpms.qatp.dto.TagStatusSortedRecord; import
 * com.conduent.tpms.qatp.model.ConfigVariable; import
 * com.conduent.tpms.qatp.model.VDeviceDto; import
 * com.conduent.tpms.qatp.service.impl.BuildTagStatusServiceImpl; import
 * com.conduent.tpms.qatp.service.impl.IAGSeviceImpl; import
 * com.conduent.tpms.qatp.service.impl.PlanStringServiceImpl;
 * 
 *//**
	 * Test class for IAGSevice
	 * 
	 * @author Urvashi C
	 *
	 *//*
		 * 
		 * @ExtendWith(MockitoExtension.class)
		 * 
		 * @TestInstance(Lifecycle.PER_CLASS) public class IAGSeviceImplTest {
		 * 
		 * @InjectMocks IAGSeviceImpl iagSeviceImpl;
		 * 
		 * @Mock IAGDao iagDao;
		 * 
		 * @Mock IAGDaoImpl iagDaoImpl;
		 * 
		 * @Mock RestTemplate restTemplate;
		 * 
		 * @Mock ConfigVariable configVariable;
		 * 
		 * @Mock BuildTagStatusServiceImpl buildTagStatusService;
		 * 
		 * @Mock PlanStringServiceImpl planStringServiceImpl;
		 * 
		 * 
		 * @Mock private NamedParameterJdbcTemplate namedJdbcTemplate;
		 * 
		 * List<VDeviceDto> list = new ArrayList<>();
		 * 
		 * @BeforeAll public void setup() { VDeviceDto VDeviceDto = new VDeviceDto();
		 * VDeviceDto.setDeviceNo("00400010066"); VDeviceDto.setDeviceStatus(0);
		 * list.add(VDeviceDto); }
		 * 
		 * 
		 * @SuppressWarnings("deprecation")
		 * 
		 * @Test public void loadValidTagDetailsTest() throws SQLException {
		 * List<VDeviceDto> deviceDtoList = new ArrayList<VDeviceDto>(); VDeviceDto
		 * VDeviceDto = new VDeviceDto(); VDeviceDto.setDeviceNo("00400010066");
		 * VDeviceDto.setDeviceStatus(0); deviceDtoList.add(VDeviceDto);
		 * 
		 * List<TagStatusSortedRecord> tagStatusSortedRecordList = new ArrayList<>();
		 * TagStatusSortedRecord tagStatusSortedRecord = new TagStatusSortedRecord();
		 * tagStatusSortedRecord.setAccountNo("500000006297");
		 * tagStatusSortedRecord.setAgencyId(3);
		 * tagStatusSortedRecord.setEtcAccountId(521);
		 * tagStatusSortedRecord.setDeviceNo("deviceNo");
		 * tagStatusSortedRecord.setFinancialStatus(4);
		 * 
		 * tagStatusSortedRecordList.add(tagStatusSortedRecord); String URI =
		 * "http://129.213.67.244/fpms/account/getfpmsaccount"; HttpHeaders headers =
		 * new HttpHeaders(); headers.setContentType(MediaType.APPLICATION_JSON); String
		 * AccountApiInfoDto =
		 * "[etcAccountId=627, agencyId=3, accountType=PRIVATE, prepaidBalance=109.0, postpaidBalance=0.0, deviceDeposit=0.0, isDiscountEligible=false, lastTollTxId=null, postpaidFee=0.0, accountBalance=109.0, rebillAmount=25.0, rebillThresholdAmount=null, crmAccountId=15811dc6-3752-46f5-888b-a226745ab467, accountNumber=500000006271, payType=null, acctFinStatus=GOOD, deviceNo=00400010066]"
		 * ; AccountApiInfoDto dto = new AccountApiInfoDto();
		 * dto.setEtcAccountId(AccountApiInfoDto); ResponseEntity<String> responseEntity
		 * = new ResponseEntity<String>(HttpStatus.OK);
		 * 
		 * Mockito.when(iagDao.getDeviceInformation(Mockito.anyString(),
		 * Mockito.anyString(),
		 * Mockito.anyString())).thenReturn(tagStatusSortedRecordList);
		 * Mockito.when(iagDao.getETCAccountInfo(Mockito.anyList())).thenReturn(
		 * tagStatusSortedRecordList);
		 * Mockito.when(configVariable.getAccountApiUri()).thenReturn(URI);
		 * Mockito.when(restTemplate.postForEntity(Mockito.anyString(),
		 * Mockito.anyObject(), Mockito.eq(String.class))) .thenReturn(responseEntity);
		 * PlanPolicyInfoDto planPolicyInfoDto = new PlanPolicyInfoDto();
		 * planPolicyInfoDto.setLcAllPlans(1); planPolicyInfoDto.setRevPlanCount(1);
		 * planPolicyInfoDto.setItagInfo(0.5);
		 * 
		 * Mockito.when(iagDao.getPlanPolicyInfo(tagStatusSortedRecord.getEtcAccountId()
		 * , tagStatusSortedRecord.getDeviceNo())).thenReturn(planPolicyInfoDto);
		 * Mockito.doNothing().when(iagDao).insertIntoTTagAllSorted(Matchers.anyList());
		 * iagSeviceImpl.loadValidTagDetails("00400010000", "00400010000",
		 * "Full","YES");
		 * 
		 * }
		 * 
		 * @Test public void loadValidTagDetailsExceptionTest() throws SQLException {
		 * MapSqlParameterSource paramSource = new MapSqlParameterSource();
		 * List<VDeviceDto> deviceDtoList = new ArrayList<VDeviceDto>(); VDeviceDto
		 * VDeviceDto = new VDeviceDto(); VDeviceDto.setDeviceNo("00400010066");
		 * VDeviceDto.setDeviceStatus(0); deviceDtoList.add(VDeviceDto);
		 * 
		 * List<TagStatusSortedRecord> tagStatusSortedRecordList = new ArrayList<>();
		 * TagStatusSortedRecord tagStatusSortedRecord = new TagStatusSortedRecord();
		 * tagStatusSortedRecord.setAccountNo("500000006297");
		 * tagStatusSortedRecord.setAgencyId(3);
		 * tagStatusSortedRecord.setEtcAccountId(521);
		 * tagStatusSortedRecord.setDeviceNo("deviceNo");
		 * tagStatusSortedRecord.setFinancialStatus(4);
		 * 
		 * tagStatusSortedRecordList.add(tagStatusSortedRecord); String URI =
		 * "http://129.213.67.244/fpms/account/getfpmsaccount"; HttpHeaders headers =
		 * new HttpHeaders(); headers.setContentType(MediaType.APPLICATION_JSON); String
		 * AccountApiInfoDto =
		 * "[etcAccountId=627, agencyId=3, accountType=PRIVATE, prepaidBalance=109.0, postpaidBalance=0.0, deviceDeposit=0.0, isDiscountEligible=false, lastTollTxId=null, postpaidFee=0.0, accountBalance=109.0, rebillAmount=25.0, rebillThresholdAmount=null, crmAccountId=15811dc6-3752-46f5-888b-a226745ab467, accountNumber=500000006271, payType=null, acctFinStatus=GOOD, deviceNo=00400010066]"
		 * ; HttpEntity<String> entity = new HttpEntity<String>(AccountApiInfoDto,
		 * headers); CustomerInfoDto customerInfoDto = new CustomerInfoDto();
		 * 
		 * String result = "{\r\n" + "    \"status\": true,\r\n" +
		 * "    \"httpStatus\": \"OK\",\r\n" + "    \"message\": null,\r\n" +
		 * "    \"result\": {\r\n" + "        \"etcAccountId\": \"521\",\r\n" +
		 * "        \"agencyId\": 3,\r\n" + "        \"accountType\": \"PRIVATE\",\r\n"
		 * + "        \"prepaidBalance\": 0.0,\r\n" +
		 * "        \"postpaidBalance\": 0.0,\r\n" +
		 * "        \"updateTs\": \"2021-04-29 20:43:17.734\",\r\n" +
		 * "        \"openDate\": \"2021-04-29\",\r\n" +
		 * "        \"acctFinStatus\": \"AUTOPAY\",\r\n" +
		 * "        \"lastPaymentTxId\": null,\r\n" +
		 * "        \"lastPaymentDate\": null,\r\n" +
		 * "        \"lastFinTxId\": null,\r\n" + "        \"lastFinTxAmt\": 0.0,\r\n" +
		 * "        \"postpaidFee\": 0.0,\r\n" + "        \"accountBalance\": 0.0,\r\n"
		 * + "        \"rebillAmount\": 25.0,\r\n" +
		 * "        \"thresholdAmount\": 10.0,\r\n" +
		 * "        \"crmAccountId\": \"e6f98aa9-53fb-471e-a201-d4b76221920e\",\r\n" +
		 * "        \"accountNumber\": \"500000005521\",\r\n" +
		 * "        \"tranType\": null\r\n" + "    },\r\n" + "    \"errors\": null,\r\n"
		 * + "    \"fieldErrors\": null\r\n" + "}";
		 * 
		 * Mockito.when(iagDao.getDeviceInformation(Mockito.anyString(),Mockito.
		 * anyString(), Mockito.anyString())).thenReturn(tagStatusSortedRecordList);
		 * Mockito.when(iagDao.getETCAccountInfo(Mockito.anyList())).thenReturn(
		 * tagStatusSortedRecordList);
		 * Mockito.when(configVariable.getAccountApiUri()).thenReturn(URI);
		 * Mockito.when(restTemplate.postForEntity(Mockito.anyString(),
		 * Mockito.anyObject(), Mockito.eq(String.class))) .thenReturn(new
		 * ResponseEntity<String>(result, HttpStatus.BAD_REQUEST)); AccountApiInfoDto
		 * accountApiInfoDto = new AccountApiInfoDto();
		 * accountApiInfoDto.setAccountBalance(100.0);
		 * accountApiInfoDto.setEtcAccountId("627"); accountApiInfoDto.setAgencyId(3);
		 * accountApiInfoDto.setAccountType("PRIVATE");
		 * 
		 * Mockito.doNothing().when(iagDao).insertIntoTTagAllSorted(Matchers.anyList());
		 * iagSeviceImpl.loadValidTagDetails("00400010000", "00400010000",
		 * "Full","YES");
		 * 
		 * }
		 * 
		 * 
		 * @Test public void callAccountApiTest() { String URI =
		 * "http://129.213.67.244/fpms/account/getfpmsaccount"; HttpHeaders headers =
		 * new HttpHeaders(); headers.setContentType(MediaType.APPLICATION_JSON);
		 * ResponseEntity<String> responseEntity = new
		 * ResponseEntity<String>(HttpStatus.ACCEPTED);
		 * 
		 * Mockito.when(configVariable.getAccountApiUri()).thenReturn(URI);
		 * Mockito.when(restTemplate.postForEntity(Mockito.anyString(), Mockito.any(),
		 * Mockito.eq(String.class))) .thenReturn(responseEntity);
		 * 
		 * AccountApiInfoDto accountApiInfoDto = iagSeviceImpl.callAccountApi("560");
		 * 
		 * Assertions.assertNull(accountApiInfoDto);
		 * 
		 * }
		 * 
		 * @Test public void callAccountApiExceptionTest() { String URI =
		 * "http://129.213.67.244/fpms/account/getfpmsaccount"; HttpHeaders headers =
		 * new HttpHeaders(); headers.setContentType(MediaType.APPLICATION_JSON);
		 * ResponseEntity<String> responseEntity = new
		 * ResponseEntity<String>(HttpStatus.OK);
		 * Mockito.when(configVariable.getAccountApiUri()).thenReturn(URI);
		 * Mockito.when(restTemplate.postForEntity(Mockito.anyString(), Mockito.any(),
		 * Mockito.eq(String.class))) .thenReturn(responseEntity);
		 * 
		 * AccountApiInfoDto accountApiInfoDto = iagSeviceImpl.callAccountApi("561");
		 * Assertions.assertNull(accountApiInfoDto); }
		 * 
		 * }
		 */
