package com.conduent.tpms.qatp.classification.service.impl;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;

import com.conduent.tpms.qatp.classification.dao.AccountInfoDao;
import com.conduent.tpms.qatp.classification.dao.TCodeDao;
import com.conduent.tpms.qatp.classification.dao.TSpeedThresholdDao;
import com.conduent.tpms.qatp.classification.dto.AccountApiInfoDto;
import com.conduent.tpms.qatp.classification.dto.AccountInfoDto;
import com.conduent.tpms.qatp.classification.dto.TransactionDetailDto;
import com.conduent.tpms.qatp.classification.dto.TransactionDto;
import com.conduent.tpms.qatp.classification.model.AccountInfo;
import com.conduent.tpms.qatp.classification.model.Agency;
import com.conduent.tpms.qatp.classification.model.ConfigVariable;
import com.conduent.tpms.qatp.classification.model.DeviceAway;
import com.conduent.tpms.qatp.classification.model.DeviceStatus;
import com.conduent.tpms.qatp.classification.model.ProcessParameter;
import com.conduent.tpms.qatp.classification.model.SpeedThreshold;
import com.conduent.tpms.qatp.classification.model.TCode;
import com.conduent.tpms.qatp.classification.model.TollPostLimit;
import com.conduent.tpms.qatp.classification.service.MessageReaderService;
import com.conduent.tpms.qatp.classification.service.SpeedProcessingService;
import com.conduent.tpms.qatp.classification.utility.ClassificationBusinessRuleHelper;
import com.conduent.tpms.qatp.classification.utility.MasterDataCache;

/**
 * Test Class for Account Info Service
 * 
 * @author deepeshb
 *
 */
@ExtendWith(MockitoExtension.class)
class AccountInfoServiceImplTest {

	@Mock
	private TCodeDao tCodeDao;

	@Mock
	private RestTemplate restTemplate;

	@Mock
	private MessageReaderService messageReaderService;

	@Mock
	private AccountInfoDao accountInfoDao;

	@Mock
	private MasterDataCache masterDataCache;

	@Mock
	private ConfigVariable configVariable;

	@Mock
	private TSpeedThresholdDao tSpeedThresholdDao;

	@InjectMocks
	private AccountInfoServiceImpl accountInfoServiceImpl;

	@Mock
	private ClassificationBusinessRuleHelper classificationBusinessRuleHelper;

	@Mock
	private SpeedProcessingService speedProcessingService;
	
	OffsetDateTime today = OffsetDateTime.parse("2022-02-14T10:15:30-05:00");
	//2022-02-14T10:15:30-05:00

	//@Test
	void TestInValidAgency() throws IOException {
		// PREPARATION
		// Create Tx Data
		TransactionDto tempTxDto = new TransactionDto();
		tempTxDto.setLaneTxId(239L);
		tempTxDto.setTxType("R");
		tempTxDto.setTxSubType("C");
		tempTxDto.setTollSystemType("B");
		tempTxDto.setTollRevenueType(1);
		tempTxDto.setEntryTxTimeStamp(today);
		tempTxDto.setEntryPlazaId(72);
		tempTxDto.setEntryDataSource("*");
		tempTxDto.setPlazaId(72);
		tempTxDto.setLaneMode(9);
		tempTxDto.setDeviceNo("00400198386");
		tempTxDto.setAgTrxType("E");
		tempTxDto.setPlazaAgencyId(1);
		tempTxDto.setImageCaptured("F");
		tempTxDto.setEtcViolObserved(1);

		// Create Agency Data-agencyId=1, devicePrefix=004,
		// isHomeAgency=Y,parentAgencyId=2
//		Agency tempAgency = new Agency();
//		tempAgency.setAgencyId(1L);
//		tempAgency.setDevicePrefix("004");
//		tempAgency.setIsHomeAgency("Y");
//		tempAgency.setParentAgencyId(2);

		// List of tx
		List<TransactionDto> tempTxDtoList = new ArrayList<TransactionDto>();
		tempTxDtoList.add(tempTxDto);

		Mockito.when(masterDataCache.getAgency("004")).thenReturn(null);

		// ACTION
		TransactionDetailDto transactionDetailDto = accountInfoServiceImpl.getAccountInformation(tempTxDto);

		// ASSERTION
		Assert.isTrue("V".equals(transactionDetailDto.getTransactionDto().getTxType()),
				"Not a valid result- Tx Type ");
		Assert.isTrue("F".equals(transactionDetailDto.getTransactionDto().getTxSubType()),
				"Not a valid result- Tx Sub Type");
		Assert.isTrue(Integer.valueOf(1).equals(transactionDetailDto.getAccountInfoDto().getInvalidAgencyPfx()),
				"Not a valid result- Invalid Agency Prefix ");
	}

	@Test
	void TestBeyondForTxTypeV() throws IOException { // PREPARATION

		// Create Tx Data
		TransactionDto tempTxDto = new TransactionDto();
		tempTxDto.setLaneTxId(239L);
		tempTxDto.setTxType("V");
		tempTxDto.setTxSubType("C");
		tempTxDto.setTollSystemType("B");
		tempTxDto.setTollRevenueType(1);
		tempTxDto.setEntryTxTimeStamp(today);
		tempTxDto.setEntryPlazaId(72);
		tempTxDto.setEntryDataSource("*");
		tempTxDto.setPlazaId(72);
		tempTxDto.setLaneMode(9);
		tempTxDto.setDeviceNo("00400198386");
		tempTxDto.setAgTrxType("E");
		tempTxDto.setPlazaAgencyId(1);
		tempTxDto.setImageCaptured("F");
		tempTxDto.setEtcViolObserved(1);
		tempTxDto.setTxDate(today.toLocalDate().toString());

		TollPostLimit tollPostLimit = new TollPostLimit();
		tollPostLimit.setPlazaAgencyId(1L);
		tollPostLimit.setAllowedDays(30);
		tollPostLimit.setEtcTxStatus(9);
		tollPostLimit.setPoachingLimit("4");
		tollPostLimit.setUpdateTs(LocalDateTime.now());
		// List of tx
		List<TransactionDto> tempTxDtoList = new ArrayList<TransactionDto>();
		tempTxDtoList.add(tempTxDto);
		
		
		Mockito.when(masterDataCache.getTollPostLimit(tempTxDto.getPlazaAgencyId(), 9)).thenReturn(tollPostLimit);
		// ACTION
		TransactionDetailDto transactionDetailDto = accountInfoServiceImpl.getAccountInformation(tempTxDto);

		// ASSERTION
		Assert.isTrue("R".equals(transactionDetailDto.getTransactionDto().getTxType()),
				"Not a valid Tx Type result");
		Assert.isTrue("B".equals(transactionDetailDto.getTransactionDto().getTxSubType()),
				"Not a valid Tx Sub Typeresult");

	}
	
	@Test
	void TestBeyondForTxTypeE() throws IOException { // PREPARATION

		// Create Tx Data
		TransactionDto tempTxDto = new TransactionDto();
		tempTxDto.setLaneTxId(239L);
		tempTxDto.setTxType("E");
		tempTxDto.setTxSubType("C");
		tempTxDto.setTollSystemType("B");
		tempTxDto.setTollRevenueType(1);
		tempTxDto.setEntryTxTimeStamp(today);
		tempTxDto.setEntryPlazaId(72);
		tempTxDto.setEntryDataSource("*");
		tempTxDto.setPlazaId(72);
		tempTxDto.setLaneMode(9);
		tempTxDto.setDeviceNo("00400198386");
		tempTxDto.setAgTrxType("E");
		tempTxDto.setPlazaAgencyId(1);
		tempTxDto.setImageCaptured("F");
		tempTxDto.setEtcViolObserved(1);
		tempTxDto.setTxDate(today.toLocalDate().toString());

		TollPostLimit tollPostLimit = new TollPostLimit();
		tollPostLimit.setPlazaAgencyId(1L);
		tollPostLimit.setAllowedDays(30);
		tollPostLimit.setEtcTxStatus(9);
		tollPostLimit.setPoachingLimit("4");
		tollPostLimit.setUpdateTs(LocalDateTime.now());
		// List of tx
		List<TransactionDto> tempTxDtoList = new ArrayList<TransactionDto>();
		tempTxDtoList.add(tempTxDto);
		
		
		Mockito.when(masterDataCache.getTollPostLimit(tempTxDto.getPlazaAgencyId(), 1)).thenReturn(tollPostLimit);
		// ACTION
		TransactionDetailDto transactionDetailDto = accountInfoServiceImpl.getAccountInformation(tempTxDto);

		// ASSERTION
		Assert.isTrue("R".equals(transactionDetailDto.getTransactionDto().getTxType()),
				"Not a valid Tx Type result");
		Assert.isTrue("B".equals(transactionDetailDto.getTransactionDto().getTxSubType()),
				"Not a valid Tx Sub Typeresult");

	}
	
	//@Test
	void TestForViolation() throws IOException { // PREPARATION

		// Create Tx Data
		TransactionDto tempTxDto = new TransactionDto();
		tempTxDto.setLaneTxId(239L);
		tempTxDto.setTxType("V");
		tempTxDto.setTxSubType("C");
		tempTxDto.setTollSystemType("B");
		tempTxDto.setTollRevenueType(1);
		tempTxDto.setEntryTxTimeStamp(today);
		tempTxDto.setEntryPlazaId(72);
		tempTxDto.setEntryDataSource("*");
		tempTxDto.setPlazaId(72);
		tempTxDto.setLaneMode(9);
		tempTxDto.setDeviceNo("00400198386");
		tempTxDto.setAgTrxType("E");
		tempTxDto.setPlazaAgencyId(1);
		tempTxDto.setImageCaptured("F");
		tempTxDto.setEtcViolObserved(1);
		tempTxDto.setTxDate(today.toLocalDate().toString());
		
		// Create Device Status
		DeviceStatus tempDeviceStatus = new DeviceStatus();
		tempDeviceStatus.setEtcAccountId(134111L);
		tempDeviceStatus.setDeviceStatus(8);
		tempDeviceStatus.setIsPrevalidated("N");
		tempDeviceStatus.setRetailTagStatus(0);

		// Create AccountInfo
		AccountInfo tempAccountInfo = new AccountInfo();
		tempAccountInfo.setPostPaidStatus(0);
		tempAccountInfo.setPrimaryRebillPayType(1);
		tempAccountInfo.setAcctActStatus(3);

		// Create AccountInfo
		AccountApiInfoDto tempAccountApiInfoDto = new AccountApiInfoDto();
		tempAccountApiInfoDto.setPostpaidBalance(0.0);
		// tempAccountApiInfoDto.setCurrentBalance(400.0);
		tempAccountApiInfoDto.setAccountType("PRIVATE");
		tempAccountApiInfoDto.setAgencyId(1);
		tempAccountApiInfoDto.setAcctFinStatus("GOOD");

		TollPostLimit tollPostLimit = null;
		// List of tx
		List<TransactionDto> tempTxDtoList = new ArrayList<TransactionDto>();
		tempTxDtoList.add(tempTxDto);
		
		
		Mockito.when(masterDataCache.getTollPostLimit(tempTxDto.getPlazaAgencyId(), 9)).thenReturn(tollPostLimit);
		
		
		
		// ACTION
		TransactionDetailDto transactionDetailDto = accountInfoServiceImpl.getAccountInformation(tempTxDto);

		// ASSERTION
		Assert.isTrue("V".equals(transactionDetailDto.getTransactionDto().getTxType()),
				"Not a valid Tx Type result");
		Assert.isTrue("F".equals(transactionDetailDto.getTransactionDto().getTxSubType()),
				"Not a valid Tx Sub Typeresult");

	}

	//commented for agTrxType
	//@Test
	void TestAwayTx() throws IOException { // PREPARATION

		// Create Tx Data
		TransactionDto tempTxDto = new TransactionDto();
		tempTxDto.setLaneTxId(239L);
		tempTxDto.setTxType("V");
		tempTxDto.setTxSubType("C");
		tempTxDto.setTollSystemType("C");
		tempTxDto.setTollRevenueType(1);
		tempTxDto.setEntryTxTimeStamp(today);
		tempTxDto.setTxTimeStamp(today);
		tempTxDto.setEntryPlazaId(72);
		tempTxDto.setEntryDataSource("*");
		tempTxDto.setPlazaId(72);
		tempTxDto.setLaneMode(9);
		tempTxDto.setDeviceNo("00400198386");
		tempTxDto.setAgTrxType("E");
		tempTxDto.setPlazaAgencyId(1);
		tempTxDto.setImageCaptured("F");
		tempTxDto.setEtcViolObserved(1);
		tempTxDto.setTxDate(today.toLocalDate().toString());

		// Create Agency Data-agencyId=1, devicePrefix=004, isHomeAgency=Y,
		// parentAgencyId=2
		Agency tempAgency = new Agency();
		tempAgency.setAgencyId(1L);
		tempAgency.setDevicePrefix("004");
		tempAgency.setIsHomeAgency("Y");
		tempAgency.setParentAgencyId(2);

		// Create Device Status
		DeviceStatus tempDeviceStatus = new DeviceStatus();
		tempDeviceStatus.setEtcAccountId(134111L);
		tempDeviceStatus.setDeviceStatus(8);
		tempDeviceStatus.setIsPrevalidated("N");
		tempDeviceStatus.setRetailTagStatus(0);

		// Create AccountInfo
		AccountInfo tempAccountInfo = new AccountInfo();
		tempAccountInfo.setPostPaidStatus(0);
		tempAccountInfo.setPrimaryRebillPayType(1);
		tempAccountInfo.setAcctActStatus(3);

		// Create AccountInfo
		AccountApiInfoDto tempAccountApiInfoDto = new AccountApiInfoDto();
		tempAccountApiInfoDto.setPostpaidBalance(0.0);
		// tempAccountApiInfoDto.setCurrentBalance(400.0);
		tempAccountApiInfoDto.setAccountType("PRIVATE");
		tempAccountApiInfoDto.setAgencyId(1);
		tempAccountApiInfoDto.setAcctFinStatus("GOOD");

		List<DeviceAway> deviceAway = new ArrayList<DeviceAway>();
		
		for(DeviceAway deviceInfo : deviceAway) {
			deviceInfo.setIagTagStatus(2L);
			deviceInfo.setDeviceNumber("00400198386");
		}

		
		// List of tx
		List<TransactionDto> tempTxDtoList = new ArrayList<TransactionDto>();
		tempTxDtoList.add(tempTxDto);
		TollPostLimit tollPostLimit = null;

		//Mockito.when(masterDataCache.getAgency("004")).thenReturn(tempAgency);
		
		Mockito.when(masterDataCache.getTollPostLimit(tempTxDto.getPlazaAgencyId(), 9)).thenReturn(tollPostLimit);

		Mockito.when(accountInfoDao.findDeviceAway("00400198386",null, today.toLocalDateTime())).thenReturn(null);

		// ACTION
		TransactionDetailDto transactionDetailDto = accountInfoServiceImpl.getAccountInformation(tempTxDto);

		// ASSERTION
		Assert.isTrue("O".equals(transactionDetailDto.getTransactionDto().getTxType()),
				"Not a valid Tx Type result");
		Assert.isTrue("T".equals(transactionDetailDto.getTransactionDto().getTxSubType()),
				"Not a valid Tx Sub Typeresult");

	}

	//commented for agTrxType
	//@Test
	void TestViolationTxTypeI() throws IOException { // PREPARATION

		// Create Tx Data
		TransactionDto tempTxDto = new TransactionDto();
		tempTxDto.setLaneTxId(239L);
		tempTxDto.setTxType("I");
		tempTxDto.setTxSubType("C");
		tempTxDto.setTollSystemType("B");
		tempTxDto.setTollRevenueType(1);
		tempTxDto.setEntryTxTimeStamp(today);
		tempTxDto.setEntryPlazaId(72);
		tempTxDto.setEntryDataSource("*");
		tempTxDto.setPlazaId(72);
		tempTxDto.setLaneMode(9);
		tempTxDto.setDeviceNo("00400198386");

		// Create Agency Data-agencyId=1, devicePrefix=004, isHomeAgency=Y,
		// parentAgencyId=2
		Agency tempAgency = new Agency();
		tempAgency.setAgencyId(1L);
		tempAgency.setDevicePrefix("004");
		tempAgency.setIsHomeAgency("Y");
		tempAgency.setParentAgencyId(2);

		// Create Device Status
		DeviceStatus tempDeviceStatus = new DeviceStatus();
		tempDeviceStatus.setEtcAccountId(134111L);
		tempDeviceStatus.setDeviceStatus(8);
		tempDeviceStatus.setIsPrevalidated("N");
		tempDeviceStatus.setRetailTagStatus(0);

		// Create AccountInfo
		AccountInfo tempAccountInfo = new AccountInfo();
		tempAccountInfo.setPostPaidStatus(0);
		tempAccountInfo.setPrimaryRebillPayType(1);
		tempAccountInfo.setAcctActStatus(3);

		// Create AccountInfo
		AccountApiInfoDto tempAccountApiInfoDto = new AccountApiInfoDto();
		tempAccountApiInfoDto.setPostpaidBalance(0.0);
		tempAccountApiInfoDto.setPrepaidBalance(400.0);
		tempAccountApiInfoDto.setAccountType("PRIVATE");
		tempAccountApiInfoDto.setAgencyId(1);
		tempAccountApiInfoDto.setAcctFinStatus("GOOD");

		// List of tx
		List<TransactionDto> tempTxDtoList = new ArrayList<TransactionDto>();
		tempTxDtoList.add(tempTxDto);

		Mockito.when(masterDataCache.getAgency("004")).thenReturn(tempAgency);

		// ACTION
		TransactionDetailDto transactionDetailDto = accountInfoServiceImpl.getAccountInformation(tempTxDto);

		// ASSERTION
		Assert.isTrue("V".equals(transactionDetailDto.getTransactionDto().getTxType()),
				"Not a valid Tx Type result");
		Assert.isTrue("F".equals(transactionDetailDto.getTransactionDto().getTxSubType()),
				"Not a valid Tx Sub Typeresult");

	}

	//commented for agTrxType
	//@Test
	void TestViolationTxSubTypeV() throws IOException { // PREPARATION

		// Create Tx Data
		TransactionDto tempTxDto = new TransactionDto();
		tempTxDto.setLaneTxId(239L);
		tempTxDto.setTxType("V");
		tempTxDto.setTxSubType("V");
		tempTxDto.setTollSystemType("B");
		tempTxDto.setTollRevenueType(1);
		tempTxDto.setEntryTxTimeStamp(today);
		tempTxDto.setEntryPlazaId(72);
		tempTxDto.setEntryDataSource("*");
		tempTxDto.setPlazaId(72);
		tempTxDto.setLaneMode(9);
		tempTxDto.setDeviceNo("00400198386");

		// Create Agency Data-agencyId=1, devicePrefix=004, isHomeAgency=Y,
		// parentAgencyId=2
		Agency tempAgency = new Agency();
		tempAgency.setAgencyId(1L);
		tempAgency.setDevicePrefix("004");
		tempAgency.setIsHomeAgency("Y");
		tempAgency.setParentAgencyId(2);

		// Create Device Status
		DeviceStatus tempDeviceStatus = new DeviceStatus();
		tempDeviceStatus.setEtcAccountId(134111L);
		tempDeviceStatus.setDeviceStatus(8);
		tempDeviceStatus.setIsPrevalidated("N");
		tempDeviceStatus.setRetailTagStatus(0);

		// Create AccountInfo
		AccountInfo tempAccountInfo = new AccountInfo();
		tempAccountInfo.setPostPaidStatus(0);
		tempAccountInfo.setPrimaryRebillPayType(1);
		tempAccountInfo.setAcctActStatus(3);

		// Create AccountInfo
		AccountApiInfoDto tempAccountApiInfoDto = new AccountApiInfoDto();
		tempAccountApiInfoDto.setPostpaidBalance(0.0);
		tempAccountApiInfoDto.setPrepaidBalance(400.0);
		tempAccountApiInfoDto.setAccountType("PRIVATE");
		tempAccountApiInfoDto.setAgencyId(1);
		tempAccountApiInfoDto.setAcctFinStatus("GOOD");

		// List of tx
		List<TransactionDto> tempTxDtoList = new ArrayList<TransactionDto>();
		tempTxDtoList.add(tempTxDto);

		Mockito.when(masterDataCache.getAgency("004")).thenReturn(tempAgency);

		// ACTION
		TransactionDetailDto transactionDetailDto = null;
		try {
			transactionDetailDto = accountInfoServiceImpl.getAccountInformation(tempTxDto);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// ASSERTION
		Assert.isTrue("V".equals(transactionDetailDto.getTransactionDto().getTxType()),
				"Not a valid Tx Type result");
		Assert.isTrue("F".equals(transactionDetailDto.getTransactionDto().getTxSubType()),
				"Not a valid Tx Sub Typeresult");

	}

	//commented for agTrxType
	//@Test
	void TestHomeTxHaDevice4() throws IOException { // PREPARATION

		// Create Tx Data
		TransactionDto tempTxDto = new TransactionDto();
		tempTxDto.setLaneTxId(239L);
		tempTxDto.setTxType("V");
		tempTxDto.setTxSubType("C");
		tempTxDto.setTollSystemType("C");
		tempTxDto.setTollRevenueType(1);
		tempTxDto.setEntryTxTimeStamp(today);
		tempTxDto.setTxTimeStamp(today);
		tempTxDto.setEntryPlazaId(72);
		tempTxDto.setEntryDataSource("*");
		tempTxDto.setPlazaId(72);
		tempTxDto.setLaneMode(9);
		tempTxDto.setDeviceNo("00400198386");
		tempTxDto.setEtcAccountId(134111L);
		tempTxDto.setPlazaId(1);
		tempTxDto.setLaneId(1);
		tempTxDto.setAccountType(1);
		tempTxDto.setTxDate("2021-03-30");

		// Create Agency Data-agencyId=1, devicePrefix=004, isHomeAgency=Y,
		// parentAgencyId=2
		Agency tempAgency = new Agency();
		tempAgency.setAgencyId(1L);
		tempAgency.setDevicePrefix("004");
		tempAgency.setIsHomeAgency("Y");
		tempAgency.setParentAgencyId(2);

		// Create Device Status
		DeviceStatus tempDeviceStatus = new DeviceStatus();
		tempDeviceStatus.setEtcAccountId(134111L);
		tempDeviceStatus.setDeviceStatus(8);
		tempDeviceStatus.setIsPrevalidated("N");
		tempDeviceStatus.setRetailTagStatus(1);

		// Create AccountInfo
		AccountInfo tempAccountInfo = new AccountInfo();
		tempAccountInfo.setPostPaidStatus(0);
		tempAccountInfo.setPrimaryRebillPayType(1);
		tempAccountInfo.setAcctActStatus(3);

		// Create AccountApiInfoDto
		AccountApiInfoDto tempAccountApiInfoDto = new AccountApiInfoDto();
		tempAccountApiInfoDto.setPostpaidBalance(0.0);
		tempAccountApiInfoDto.setPrepaidBalance(400.0);
		tempAccountApiInfoDto.setAccountType("PRIVATE");
		tempAccountApiInfoDto.setAgencyId(1);
		tempAccountApiInfoDto.setAcctFinStatus("GOOD");

		// List of tx
		List<TransactionDto> tempTxDtoList = new ArrayList<TransactionDto>();
		tempTxDtoList.add(tempTxDto);

		// AcctType
		TCode accTypeStatus = new TCode();
		accTypeStatus.setCodeId(1);
		accTypeStatus.setCodeType("ACCOUNT_TYPE");
		accTypeStatus.setCodeValue("PRIVATE");

		// FinStatus
		TCode finStatus = new TCode();
		finStatus.setCodeId(1);
		finStatus.setCodeType("ACCT_FIN_STATUS");
		finStatus.setCodeValue("GOOD");

		// HaDeviceStatus
		DeviceStatus tempHaDeviceStatus = new DeviceStatus();
		tempHaDeviceStatus.setIagDeviceStatus(3L);

		TCode tempTCode = new TCode();
		tempTCode.setCodeId(1);

		SpeedThreshold speedThreshold = new SpeedThreshold();
		speedThreshold.setLowerSpeedThreshold(10);

		ProcessParameter tempProcessParameter = new ProcessParameter();
		tempProcessParameter.setParamValue("Y");

		String response = "{\"status\":true,\"httpStatus\":\"OK\",\"message\":null,\"result\":{\"etcAccountId\":\"134111\",\"agencyId\":103,\"accountType\":\"PRIVATE\",\"prepaidBalance\":20.0,\"postpaidBalance\":0.0,\"deviceDeposit\":18.0,\"isDiscountEligible\":false,\"lastTollTxId\":null,\"lastTollPostedDate\":null,\"lastTollTxAmt\":0.0,\"lastTollTxTimestamp\":null,\"firstTollTimestamp\":null,\"updateTs\":\"2021-04-05 08:14:31.804\",\"openDate\":\"2021-04-05\",\"lastTollTxDate\":null,\"lastFinTxDate\":null,\"acctFinStatus\":\"GOOD\",\"lastPaymentTxId\":null,\"lastPaymentDate\":null,\"lastFinTxId\":null,\"lastFinTxAmt\":null,\"postpaidFee\":0.0,\"accountBalance\":20.0,\"rebillAmount\":10.0,\"thresholdAmount\":5.0,\"crmAccountId\":\"2016\",\"accountNumber\":\"2501117\",\"payType\":null},\"errors\":null,\"fieldErrors\":null}";

		Mockito.when(masterDataCache.getAgency("004")).thenReturn(tempAgency);

		//commented to test NY flow
	//	Mockito.when(accountInfoDao.getDeviceStatus("00400198386", today.toLocalDateTime())).thenReturn(tempDeviceStatus);

		Mockito.when(configVariable.getAccountApiUri())
				.thenReturn("http://193.122.158.101:9898/fpms/account/getfpmsaccount");

		Mockito.when(restTemplate.postForEntity(Mockito.anyString(), Mockito.any(), Mockito.eq(String.class)))
				.thenReturn(new ResponseEntity<String>(response, HttpStatus.OK));
		Mockito.when(masterDataCache.getAccountTypeByCodeValue("PRIVATE")).thenReturn(accTypeStatus);
		Mockito.when(masterDataCache.getFinStatusByCodeValue("GOOD")).thenReturn(finStatus);

		Mockito.when(accountInfoDao.getAccountInfo(134111L,"2021-03-30")).thenReturn(tempAccountInfo);


		Mockito.when(masterDataCache.getProcessParameter("QATP_RETAIL_TAG_CHECK", 1L)).thenReturn(tempProcessParameter);

		//commented to test NY flow
		//Mockito.when(accountInfoDao.getHATagStatus("00400198386", "2021-03-30")).thenReturn(tempHaDeviceStatus);

		Mockito.when(tCodeDao.getAccountSpeedStatus(tempTxDto.getEtcAccountId(), tempTxDto.getTxDate()))
				.thenReturn(tempTCode);

		Mockito.when(tSpeedThresholdDao.getSpeedLimitForLane(tempTxDto.getPlazaAgencyId(), tempTxDto.getLaneId(),
				tempTxDto.getAccountType())).thenReturn(speedThreshold);

		Mockito.when(tSpeedThresholdDao.getSpeedLimitForLane(tempTxDto.getPlazaAgencyId(), tempTxDto.getLaneId(),
				tempTxDto.getAccountType())).thenReturn(speedThreshold);

		TransactionDetailDto tempTransactionDetailDto = new TransactionDetailDto();
		AccountInfoDto accountInfoDto = new AccountInfoDto();
		tempTransactionDetailDto.setAccountInfoDto(accountInfoDto);
		tempTransactionDetailDto.setTransactionDto(tempTxDto);

		Mockito.when(speedProcessingService.processSpeedBusinessRule(Mockito.any(TransactionDto.class),
				Mockito.any(AccountInfoDto.class))).thenReturn(tempTransactionDetailDto);

		// ACTION
		TransactionDetailDto transactionDetailDto = accountInfoServiceImpl.getAccountInformation(tempTxDto);

		// ASSERTION
		assertNotNull(transactionDetailDto);
	}

	//commented for agTrxType
	//@Test
	void TestHomeTxHaDevice3() throws IOException {

		// PREPARATION
		// Create Tx Data
		TransactionDto tempTxDto = new TransactionDto();
		tempTxDto.setLaneTxId(239L);
		tempTxDto.setTxType("V");
		tempTxDto.setTxSubType("C");
		tempTxDto.setTollSystemType("C");
		tempTxDto.setTollRevenueType(1);
		tempTxDto.setEntryTxTimeStamp(today);
		tempTxDto.setTxTimeStamp(today);
		tempTxDto.setEntryPlazaId(72);
		tempTxDto.setEntryDataSource("*");
		tempTxDto.setPlazaId(72);
		tempTxDto.setLaneMode(9);
		tempTxDto.setDeviceNo("00400198386");
		tempTxDto.setEtcAccountId(134111L);
		tempTxDto.setPlazaId(1);
		tempTxDto.setLaneId(1);
		tempTxDto.setAccountType(1);
		tempTxDto.setTxDate("2021-03-30");

		// Create Agency Data-agencyId=1, devicePrefix=004, isHomeAgency=Y,
		// parentAgencyId=2
		Agency tempAgency = new Agency();
		tempAgency.setAgencyId(1L);
		tempAgency.setDevicePrefix("004");
		tempAgency.setIsHomeAgency("Y");
		tempAgency.setParentAgencyId(2);

		// Create Device Status
		DeviceStatus tempDeviceStatus = new DeviceStatus();
		tempDeviceStatus.setEtcAccountId(134111L);
		tempDeviceStatus.setDeviceStatus(8);
		tempDeviceStatus.setIsPrevalidated("N");
		tempDeviceStatus.setRetailTagStatus(1);

		// Create AccountInfo
		AccountInfo tempAccountInfo = new AccountInfo();
		tempAccountInfo.setPostPaidStatus(0);
		tempAccountInfo.setPrimaryRebillPayType(1);
		tempAccountInfo.setAcctActStatus(3);

		// Create AccountApiInfoDto
		AccountApiInfoDto tempAccountApiInfoDto = new AccountApiInfoDto();
		tempAccountApiInfoDto.setPostpaidBalance(0.0);
		tempAccountApiInfoDto.setPrepaidBalance(400.0);
		tempAccountApiInfoDto.setAccountType("PRIVATE");
		tempAccountApiInfoDto.setAgencyId(1);
		tempAccountApiInfoDto.setAcctFinStatus("GOOD");

		// List of tx
		List<TransactionDto> tempTxDtoList = new ArrayList<TransactionDto>();
		tempTxDtoList.add(tempTxDto);

		// AcctType
		TCode accTypeStatus = new TCode();
		accTypeStatus.setCodeId(1);
		accTypeStatus.setCodeType("ACCOUNT_TYPE");
		accTypeStatus.setCodeValue("PRIVATE");

		// FinStatus
		TCode finStatus = new TCode();
		finStatus.setCodeId(1);
		finStatus.setCodeType("ACCT_FIN_STATUS");
		finStatus.setCodeValue("GOOD");

		// HaDeviceStatus
		DeviceStatus tempHaDeviceStatus = new DeviceStatus();
		tempHaDeviceStatus.setIagDeviceStatus(4L);

		TCode tempTCode = new TCode();
		tempTCode.setCodeId(1);

		SpeedThreshold speedThreshold = new SpeedThreshold();
		speedThreshold.setLowerSpeedThreshold(10);

		ProcessParameter tempProcessParameter = new ProcessParameter();
		tempProcessParameter.setParamValue("Y");
		tempProcessParameter.setParamName("QATP_RETAIL_TAG_CHECK");
		tempProcessParameter.setParamValue("1");

		String response = "{\"status\":true,\"httpStatus\":\"OK\",\"message\":null,\"result\":{\"etcAccountId\":\"134111\",\"agencyId\":103,\"accountType\":\"PRIVATE\",\"prepaidBalance\":20.0,\"postpaidBalance\":0.0,\"deviceDeposit\":18.0,\"isDiscountEligible\":false,\"lastTollTxId\":null,\"lastTollPostedDate\":null,\"lastTollTxAmt\":0.0,\"lastTollTxTimestamp\":null,\"firstTollTimestamp\":null,\"updateTs\":\"2021-04-05 08:14:31.804\",\"openDate\":\"2021-04-05\",\"lastTollTxDate\":null,\"lastFinTxDate\":null,\"acctFinStatus\":\"GOOD\",\"lastPaymentTxId\":null,\"lastPaymentDate\":null,\"lastFinTxId\":null,\"lastFinTxAmt\":null,\"postpaidFee\":0.0,\"accountBalance\":20.0,\"rebillAmount\":10.0,\"thresholdAmount\":5.0,\"crmAccountId\":\"2016\",\"accountNumber\":\"2501117\",\"payType\":null},\"errors\":null,\"fieldErrors\":null}";

		Mockito.when(masterDataCache.getAgency("004")).thenReturn(tempAgency);

		//commented to test NY flow
		//Mockito.when(accountInfoDao.getDeviceStatus("00400198386", today.toLocalDateTime())).thenReturn(tempDeviceStatus);

		Mockito.when(configVariable.getAccountApiUri())
				.thenReturn("http://193.122.158.101:9898/fpms/account/getfpmsaccount");

		Mockito.when(restTemplate.postForEntity(Mockito.anyString(), Mockito.any(), Mockito.eq(String.class)))
				.thenReturn(new ResponseEntity<String>(response, HttpStatus.OK));
		Mockito.when(masterDataCache.getAccountTypeByCodeValue("PRIVATE")).thenReturn(accTypeStatus);
		Mockito.when(masterDataCache.getFinStatusByCodeValue("GOOD")).thenReturn(finStatus);

		Mockito.when(accountInfoDao.getAccountInfo(134111L,"2021-03-30")).thenReturn(tempAccountInfo);

		Mockito.when(masterDataCache.getProcessParameter("QATP_RETAIL_TAG_CHECK", 1L)).thenReturn(tempProcessParameter);

		//commented for performance testing
		//Mockito.when(accountInfoDao.getHATagStatus("00800011163", "2021-08-12")).thenReturn(tempHaDeviceStatus);

		Mockito.when(tCodeDao.getAccountSpeedStatus(tempTxDto.getEtcAccountId(), tempTxDto.getTxDate()))
				.thenReturn(tempTCode);

		Mockito.when(tSpeedThresholdDao.getSpeedLimitForLane(tempTxDto.getPlazaAgencyId(), tempTxDto.getLaneId(),
				tempTxDto.getAccountType())).thenReturn(speedThreshold);

		TransactionDetailDto tempTransactionDetailDto = new TransactionDetailDto();
		AccountInfoDto accountInfoDto = new AccountInfoDto();
		tempTransactionDetailDto.setAccountInfoDto(accountInfoDto);
		tempTransactionDetailDto.setTransactionDto(tempTxDto);

		Mockito.when(speedProcessingService.processSpeedBusinessRule(Mockito.any(TransactionDto.class),
				Mockito.any(AccountInfoDto.class))).thenReturn(tempTransactionDetailDto);

		// ACTION
		TransactionDetailDto transactionDetailDto = accountInfoServiceImpl.getAccountInformation(tempTxDto);

		// ASSERTION
		assertNotNull(transactionDetailDto);

	}

	//commented for agTrxType
	//@Test
	void TestAwayTxInvalidIagStatus() throws IOException { // PREPARATION

		// Create Tx Data
		TransactionDto tempTxDto = new TransactionDto();
		tempTxDto.setLaneTxId(239L);
		tempTxDto.setTxType("V");
		tempTxDto.setTxSubType("C");
		tempTxDto.setTollSystemType("C");
		tempTxDto.setTollRevenueType(1);
		tempTxDto.setEntryTxTimeStamp(today);
		tempTxDto.setTxTimeStamp(today);
		tempTxDto.setEntryPlazaId(72);
		tempTxDto.setEntryDataSource("*");
		tempTxDto.setPlazaId(72);
		tempTxDto.setLaneMode(9);
		tempTxDto.setDeviceNo("00400198386");

		// Create Agency Data-agencyId=1, devicePrefix=004, isHomeAgency=Y,
		// parentAgencyId=2
		Agency tempAgency = new Agency();
		tempAgency.setAgencyId(1L);
		tempAgency.setDevicePrefix("004");
		tempAgency.setIsHomeAgency("Y");
		tempAgency.setParentAgencyId(2);

		// Create Device Status
		DeviceStatus tempDeviceStatus = new DeviceStatus();
		tempDeviceStatus.setEtcAccountId(134111L);
		tempDeviceStatus.setDeviceStatus(8);
		tempDeviceStatus.setIsPrevalidated("N");
		tempDeviceStatus.setRetailTagStatus(0);

		// Create AccountInfo
		AccountInfo tempAccountInfo = new AccountInfo();
		tempAccountInfo.setPostPaidStatus(0);
		tempAccountInfo.setPrimaryRebillPayType(1);
		tempAccountInfo.setAcctActStatus(3);

		// Create AccountInfo
		AccountApiInfoDto tempAccountApiInfoDto = new AccountApiInfoDto();
		tempAccountApiInfoDto.setPostpaidBalance(0.0);
		// tempAccountApiInfoDto.setCurrentBalance(400.0);
		tempAccountApiInfoDto.setAccountType("PRIVATE");
		tempAccountApiInfoDto.setAgencyId(1);
		tempAccountApiInfoDto.setAcctFinStatus("GOOD");

		DeviceAway deviceAway = new DeviceAway();
		deviceAway.setIagTagStatus(3L);

		// List of tx
		List<TransactionDto> tempTxDtoList = new ArrayList<TransactionDto>();
		tempTxDtoList.add(tempTxDto);

		Mockito.when(masterDataCache.getAgency("004")).thenReturn(tempAgency);

		//commented to test NY flow
		//Mockito.when(accountInfoDao.findDeviceAway("00400198386",today.toLocalDateTime())).thenReturn(deviceAway);

		// ACTION
		TransactionDetailDto transactionDetailDto = accountInfoServiceImpl.getAccountInformation(tempTxDto);

		// ASSERTION
		Assert.isTrue("V".equals(transactionDetailDto.getTransactionDto().getTxType()),
				"Not a valid Tx Type result");
		Assert.isTrue("F".equals(transactionDetailDto.getTransactionDto().getTxSubType()),
				"Not a valid Tx Sub Typeresult");

	}
	
	//commented for agTrxType
	//@Test
	void TestAwayTxClassMismatchCheck() throws IOException { // PREPARATION

		// Create Tx Data
		TransactionDto tempTxDto = new TransactionDto();
		tempTxDto.setLaneTxId(239L);
		tempTxDto.setTxType("V");
		tempTxDto.setTxSubType("C");
		tempTxDto.setTollSystemType("C");
		tempTxDto.setTollRevenueType(1);
		tempTxDto.setEntryTxTimeStamp(today);
		tempTxDto.setTxTimeStamp(today);
		tempTxDto.setEntryPlazaId(72);
		tempTxDto.setEntryDataSource("*");
		tempTxDto.setPlazaId(72);
		tempTxDto.setLaneMode(9);
		tempTxDto.setDeviceNo("00400198386");
		tempTxDto.setTransactionInfo("YNY0");

		// Create Agency Data-agencyId=1, devicePrefix=004, isHomeAgency=Y,
		// parentAgencyId=2
		Agency tempAgency = new Agency();
		tempAgency.setAgencyId(1L);
		tempAgency.setDevicePrefix("004");
		tempAgency.setIsHomeAgency("Y");
		tempAgency.setParentAgencyId(2);

		// Create Device Status
		DeviceStatus tempDeviceStatus = new DeviceStatus();
		tempDeviceStatus.setEtcAccountId(134111L);
		tempDeviceStatus.setDeviceStatus(8);
		tempDeviceStatus.setIsPrevalidated("N");
		tempDeviceStatus.setRetailTagStatus(0);

		// Create AccountInfo
		AccountInfo tempAccountInfo = new AccountInfo();
		tempAccountInfo.setPostPaidStatus(0);
		tempAccountInfo.setPrimaryRebillPayType(1);
		tempAccountInfo.setAcctActStatus(3);

		// Create AccountInfo
		AccountApiInfoDto tempAccountApiInfoDto = new AccountApiInfoDto();
		tempAccountApiInfoDto.setPostpaidBalance(0.0);
		// tempAccountApiInfoDto.setCurrentBalance(400.0);
		tempAccountApiInfoDto.setAccountType("PRIVATE");
		tempAccountApiInfoDto.setAgencyId(1);
		tempAccountApiInfoDto.setAcctFinStatus("GOOD");

		DeviceAway deviceAway = new DeviceAway();
		deviceAway.setIagTagStatus(2L);

		// List of tx
		List<TransactionDto> tempTxDtoList = new ArrayList<TransactionDto>();
		tempTxDtoList.add(tempTxDto);

		Mockito.when(masterDataCache.getAgency("004")).thenReturn(tempAgency);

		//commented to test NY flow
		//Mockito.when(accountInfoDao.findDeviceAway("00400198386",today.toLocalDateTime())).thenReturn(deviceAway);
		
		Mockito.when(classificationBusinessRuleHelper.checkTxInfoVehAndTagClass(tempTxDto)).thenReturn(true);
		
		// ACTION
		TransactionDetailDto transactionDetailDto = accountInfoServiceImpl.getAccountInformation(tempTxDto);

		// ASSERTION
		Assert.isTrue("V".equals(transactionDetailDto.getTransactionDto().getTxType()),
				"Not a valid Tx Type result");
		Assert.isTrue("G".equals(transactionDetailDto.getTransactionDto().getTxSubType()),
				"Not a valid Tx Sub Typeresult");

	}
	
	//commented for agTrxType
	//@Test
	void TestAwayTxClassMismatchCheckWidInvalidVehAndTagClass() throws IOException { // PREPARATION

		// Create Tx Data
		TransactionDto tempTxDto = new TransactionDto();
		tempTxDto.setLaneTxId(239L);
		tempTxDto.setTxType("V");
		tempTxDto.setTxSubType("C");
		tempTxDto.setTollSystemType("C");
		tempTxDto.setTollRevenueType(1);
		tempTxDto.setEntryTxTimeStamp(today);
		tempTxDto.setTxTimeStamp(today);
		tempTxDto.setEntryPlazaId(72);
		tempTxDto.setEntryDataSource("*");
		tempTxDto.setPlazaId(72);
		tempTxDto.setLaneMode(9);
		tempTxDto.setDeviceNo("00400198386");
		tempTxDto.setTransactionInfo("YNY0");

		// Create Agency Data-agencyId=1, devicePrefix=004, isHomeAgency=Y,
		// parentAgencyId=2
		Agency tempAgency = new Agency();
		tempAgency.setAgencyId(1L);
		tempAgency.setDevicePrefix("004");
		tempAgency.setIsHomeAgency("Y");
		tempAgency.setParentAgencyId(2);

		// Create Device Status
		DeviceStatus tempDeviceStatus = new DeviceStatus();
		tempDeviceStatus.setEtcAccountId(134111L);
		tempDeviceStatus.setDeviceStatus(8);
		tempDeviceStatus.setIsPrevalidated("N");
		tempDeviceStatus.setRetailTagStatus(0);

		// Create AccountInfo
		AccountInfo tempAccountInfo = new AccountInfo();
		tempAccountInfo.setPostPaidStatus(0);
		tempAccountInfo.setPrimaryRebillPayType(1);
		tempAccountInfo.setAcctActStatus(3);

		// Create AccountInfo
		AccountApiInfoDto tempAccountApiInfoDto = new AccountApiInfoDto();
		tempAccountApiInfoDto.setPostpaidBalance(0.0);
		// tempAccountApiInfoDto.setCurrentBalance(400.0);
		tempAccountApiInfoDto.setAccountType("PRIVATE");
		tempAccountApiInfoDto.setAgencyId(1);
		tempAccountApiInfoDto.setAcctFinStatus("GOOD");

		DeviceAway deviceAway = new DeviceAway();
		deviceAway.setIagTagStatus(2L);

		// List of tx
		List<TransactionDto> tempTxDtoList = new ArrayList<TransactionDto>();
		tempTxDtoList.add(tempTxDto);

		Mockito.when(masterDataCache.getAgency("004")).thenReturn(tempAgency);

		//commented to test NY flow
		//Mockito.when(accountInfoDao.findDeviceAway("00400198386", today.toLocalDateTime())).thenReturn(deviceAway);
		
		Mockito.when(classificationBusinessRuleHelper.checkTxInfoVehAndTagClass(tempTxDto)).thenReturn(false);
		
		// ACTION
		TransactionDetailDto transactionDetailDto = accountInfoServiceImpl.getAccountInformation(tempTxDto);

		// ASSERTION
		Assert.isTrue("V".equals(transactionDetailDto.getTransactionDto().getTxType()),
				"Not a valid Tx Type result");
		Assert.isTrue("C".equals(transactionDetailDto.getTransactionDto().getTxSubType()),
				"Not a valid Tx Sub Typeresult");

	}
	
//	@Test
	void TestCompanionTag() throws IOException {

		// Create Tx Data
		TransactionDto tempTxDto = new TransactionDto();
		tempTxDto.setLaneTxId(239L);
		tempTxDto.setTxType("V");
		tempTxDto.setTxSubType("C");
		tempTxDto.setTollSystemType("C");
		tempTxDto.setTollRevenueType(1);
		tempTxDto.setEntryTxTimeStamp(today);
		tempTxDto.setTxTimeStamp(today);
		tempTxDto.setEntryPlazaId(72);
		tempTxDto.setEntryDataSource("*");
		tempTxDto.setPlazaId(72);
		tempTxDto.setLaneMode(9);
		tempTxDto.setDeviceNo("00400198386");

		// Create Agency Data-agencyId=1, devicePrefix=004, isHomeAgency=Y,
		// parentAgencyId=2
		Agency tempAgency = new Agency();
		tempAgency.setAgencyId(1L);
		tempAgency.setDevicePrefix("004");
		tempAgency.setIsHomeAgency("Y");
		tempAgency.setParentAgencyId(2);

		// Create Device Status
		DeviceStatus tempDeviceStatus = new DeviceStatus();
		tempDeviceStatus.setEtcAccountId(134111L);
		tempDeviceStatus.setDeviceStatus(8);
		tempDeviceStatus.setIsPrevalidated("N");
		tempDeviceStatus.setRetailTagStatus(0);

		// Create AccountInfo
		AccountInfo tempAccountInfo = new AccountInfo();
		tempAccountInfo.setPostPaidStatus(0);
		tempAccountInfo.setPrimaryRebillPayType(1);
		tempAccountInfo.setAcctActStatus(3);

		// Create AccountInfo
		AccountApiInfoDto tempAccountApiInfoDto = new AccountApiInfoDto();
		tempAccountApiInfoDto.setPostpaidBalance(0.0);
		// tempAccountApiInfoDto.setCurrentBalance(400.0);
		tempAccountApiInfoDto.setAccountType("PRIVATE");
		tempAccountApiInfoDto.setAgencyId(1);
		tempAccountApiInfoDto.setAcctFinStatus("GOOD");
		
		
		AccountInfoDto tempAccountInfoDto = new AccountInfoDto();

		DeviceAway deviceAway = new DeviceAway();
		deviceAway.setIagTagStatus(2L);

		// List of tx
		List<TransactionDto> tempTxDtoList = new ArrayList<TransactionDto>();
		tempTxDtoList.add(tempTxDto);

		//Mockito.when(masterDataCache.getAgency("004")).thenReturn(tempAgency);

		//commented to test NY flow
		//Mockito.when(accountInfoDao.findDeviceAway("00400198386", today.toLocalDateTime())).thenReturn(deviceAway);

		// ACTION
		TransactionDetailDto transactionDetailDto = accountInfoServiceImpl.checkCompanionTag(tempTxDto, tempAccountInfoDto);

		// ASSERTION
		Assert.isTrue("O".equals(transactionDetailDto.getTransactionDto().getTxType()),
				"Not a valid Tx Type result");
		Assert.isTrue("T".equals(transactionDetailDto.getTransactionDto().getTxSubType()),
				"Not a valid Tx Sub Typeresult");
		
		
		deviceAway.setIagTagStatus(3L);
		//commented to test NY flow
		//Mockito.when(accountInfoDao.findDeviceAway("00400198386", today.toLocalDateTime())).thenReturn(deviceAway);

		// ACTION
		 transactionDetailDto = accountInfoServiceImpl.checkCompanionTag(tempTxDto, tempAccountInfoDto);
		// ASSERTION
				Assert.isTrue("V".equals(transactionDetailDto.getTransactionDto().getTxType()),
						"Not a valid Tx Type result");
				Assert.isTrue("F".equals(transactionDetailDto.getTransactionDto().getTxSubType()),
						"Not a valid Tx Sub Typeresult");
	}
}
