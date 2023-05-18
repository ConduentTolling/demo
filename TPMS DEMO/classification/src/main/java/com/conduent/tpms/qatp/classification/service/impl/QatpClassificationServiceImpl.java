package com.conduent.tpms.qatp.classification.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.conduent.tpms.qatp.classification.constants.QatpClassificationConstant;
import com.conduent.tpms.qatp.classification.dao.AccountInfoDao;
import com.conduent.tpms.qatp.classification.dao.ComputeTollDao;
import com.conduent.tpms.qatp.classification.dto.AccountApiInfoDto;
import com.conduent.tpms.qatp.classification.dto.AccountInfoDto;
import com.conduent.tpms.qatp.classification.dto.CommonClassificationDto;
import com.conduent.tpms.qatp.classification.dto.CustomerInfoDto;
import com.conduent.tpms.qatp.classification.dto.NY12Dto;
import com.conduent.tpms.qatp.classification.dto.TransactionDetailDto;
import com.conduent.tpms.qatp.classification.dto.TransactionDto;
import com.conduent.tpms.qatp.classification.model.AccountInfo;
import com.conduent.tpms.qatp.classification.model.Agency;
import com.conduent.tpms.qatp.classification.model.ConfigVariable;
import com.conduent.tpms.qatp.classification.model.DeviceAway;
import com.conduent.tpms.qatp.classification.model.DeviceStatus;
import com.conduent.tpms.qatp.classification.model.TCode;
import com.conduent.tpms.qatp.classification.model.TollPostLimit;
import com.conduent.tpms.qatp.classification.service.AccountInfoService;
import com.conduent.tpms.qatp.classification.service.CommonClassificationService;
import com.conduent.tpms.qatp.classification.service.CommonNotificationService;
import com.conduent.tpms.qatp.classification.service.ComputeTollService;
import com.conduent.tpms.qatp.classification.service.QatpClassificationService;
import com.conduent.tpms.qatp.classification.service.TransactionClassificationService;
import com.conduent.tpms.qatp.classification.utility.AsyncProcessCall;
import com.conduent.tpms.qatp.classification.utility.Convertor;
import com.conduent.tpms.qatp.classification.utility.MasterDataCache;
import com.google.common.base.Stopwatch;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

@Service
public class QatpClassificationServiceImpl implements QatpClassificationService {

	private static final Logger logger = LoggerFactory.getLogger(QatpClassificationServiceImpl.class);

	@Autowired
	private TransactionClassificationService transactionClassificationService;

	@Autowired
	private ComputeTollService computeTollService;

	@Autowired
	private CommonNotificationService commonNotificationService;

	@Autowired
	private AccountInfoService accountInfoService;

	@Autowired
	private MasterDataCache masterDataCache;
	
	@Autowired
	private AccountInfoDao accountInfoDao;
	
	@Autowired
	private ComputeTollDao computeTollDAO;


	/**
	 * Process Transaction Message
	 * 
	 * @param List<TransactionDto>
	 */
	@SuppressWarnings("unlikely-arg-type")
	public void processTxMsg(TransactionDto tempTxDto) 
	{
		Optional<String> podName = Optional.ofNullable(System.getenv("HOSTNAME"));
		logger.info("2# Process trnx with lane_tx_id {} by thread {} on pod {} ",
				tempTxDto.getLaneTxId(),Thread.currentThread().getName(),podName);
		
		TransactionDetailDto transactionDetailDto = accountInfoService.getAccountInformation(tempTxDto);
		
		transactionDetailDto.setTransactionDto(tempTxDto);
		pushMessageToParkingQueue(transactionDetailDto,masterDataCache);
	}

	/**
	 * Compute Toll of the Transaction
	 * 
	 * @param TransactionDetailDto
	 * @return TransactionDetailDto
	 */
	protected TransactionDetailDto computeToll(TransactionDetailDto transactionDetailDto) {
		logger.info("Enter Base class compute logic");
		if (transactionDetailDto == null || transactionDetailDto.getTransactionDto() == null
				|| transactionDetailDto.getAccountInfoDto() == null) {
			return null;
		}
		TransactionDto transactionDto = computeTollService.computeToll(transactionDetailDto.getTransactionDto(),
				transactionDetailDto.getAccountInfoDto());
		transactionDetailDto.setTransactionDto(transactionDto);
		return transactionDetailDto;

	}


	/**
	 * Push Transaction Based on Transaction type
	 * @param masterDataCache2 
	 * 
	 * @param TransactionDetailDto
	 */
	private void pushMessageToParkingQueue(TransactionDetailDto transactionDetailDto, MasterDataCache masterDataCache2) {
		
		if (transactionDetailDto == null || transactionDetailDto.getTransactionDto() == null) 
		{
			return;
		}
		commonNotificationService.pushMessagetoParkingQueue(transactionDetailDto.getTransactionDto(),masterDataCache2);
	}
}
