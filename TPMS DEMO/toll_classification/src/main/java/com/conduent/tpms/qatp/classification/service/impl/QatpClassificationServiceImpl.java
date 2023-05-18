package com.conduent.tpms.qatp.classification.service.impl;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.conduent.tpms.qatp.classification.constants.QatpClassificationConstant;
import com.conduent.tpms.qatp.classification.dao.ComputeTollDao;
import com.conduent.tpms.qatp.classification.dto.AccountInfoDto;
import com.conduent.tpms.qatp.classification.dto.NY12Dto;
import com.conduent.tpms.qatp.classification.dto.TransactionDetailDto;
import com.conduent.tpms.qatp.classification.dto.TransactionDto;
import com.conduent.tpms.qatp.classification.service.AccountInfoService;
import com.conduent.tpms.qatp.classification.service.CommonNotificationService;
import com.conduent.tpms.qatp.classification.service.ComputeTollService;
import com.conduent.tpms.qatp.classification.service.QatpClassificationService;
import com.conduent.tpms.qatp.classification.service.TransactionClassificationService;
import com.conduent.tpms.qatp.classification.utility.AsyncProcessCall;
import com.google.common.base.Stopwatch;

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
	private ComputeTollDao computeTollDao;
	
	@Autowired
	private AsyncProcessCall asyncProcessCall;

	/**
	 * Process Transaction Message
	 * 
	 * @param List<TransactionDto>
	 */
	public void processTxMsg(TransactionDto tempTxDto) 
	{
		Optional<String> podName = Optional.ofNullable(System.getenv("HOSTNAME"));
		logger.info("2# Process trnx with lane_tx_id {} by thread {} on pod {} ",
				tempTxDto.getLaneTxId(),Thread.currentThread().getName(),podName);
		
			if (checkTxType(tempTxDto)) 
			{
				TransactionDetailDto transactionDetailDto = accountInfoService.getAccountInformation(tempTxDto);
				logger.info("Transaction detail info after get account Info: {}", transactionDetailDto);

				if (checkTxType(tempTxDto)) {
					transactionDetailDto = setTxOnClassification(transactionDetailDto);
					logger.info("Transaction detail info after classification condition: {}", transactionDetailDto);
					if (checkTxType(transactionDetailDto)) {
						transactionDetailDto = computeToll(transactionDetailDto);//03 plazaAgencyId
						logger.info("Transaction detail info after compute toll: {}", transactionDetailDto);
						pushMessage(transactionDetailDto);
					}
				}else {
					// Push Rejected Transaction

					transactionDetailDto.setTransactionDto(tempTxDto);
					transactionDetailDto.setAccountInfoDto(new AccountInfoDto());
					transactionDetailDto = computeToll(transactionDetailDto);
					logger.info("Transaction detail info after compute toll: {}", transactionDetailDto);
					pushMessage(transactionDetailDto);
				}	
			}
			else 
			{
				//Push Rejected Transaction
				TransactionDetailDto transactionDetailDto = new TransactionDetailDto();
				transactionDetailDto.setTransactionDto(tempTxDto);
				transactionDetailDto.setAccountInfoDto(new AccountInfoDto());
				transactionDetailDto = computeToll(transactionDetailDto);
				logger.info("Transaction detail info after compute toll: {}", transactionDetailDto);
				pushMessage(transactionDetailDto);
			}
	}
	
	/**
	 * Classify the transaction
	 * 
	 * @param TransactionDetailDto
	 * @return TransactionDetailDto
	 */
	private TransactionDetailDto setTxOnClassification(TransactionDetailDto transactionDetailDto) {
		return transactionClassificationService.processTransactionClassification(
				transactionDetailDto.getTransactionDto(), transactionDetailDto.getAccountInfoDto());
	}

	/**
	 * Compute Toll of the Transaction
	 * 
	 * @param TransactionDetailDto
	 * @return TransactionDetailDto
	 */
	
/*	
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
	
	*/
	//@Override
	protected TransactionDetailDto computeToll(TransactionDetailDto transactionDetailDto) {
		logger.info("Enter PA compute logic");
		if (transactionDetailDto == null || transactionDetailDto.getTransactionDto() == null
				|| transactionDetailDto.getAccountInfoDto() == null) {
			return null;
		}
		return computeTollService.computePAToll(transactionDetailDto);

	}


	/**
	 * Check Transaction Type
	 * 
	 * @param TransactionDto
	 * @return boolean
	 */
	private boolean checkTxType(TransactionDetailDto transactionDetailDto) {
		if (transactionDetailDto == null || transactionDetailDto.getTransactionDto() == null) {
			return false;
		}

		String txType = transactionDetailDto.getTransactionDto().getTxType() != null
				? transactionDetailDto.getTransactionDto().getTxType()
				: null;
		
		String txSubType = transactionDetailDto.getTransactionDto().getTxSubType() != null
				? transactionDetailDto.getTransactionDto().getTxSubType()
				: null;

		if (!StringUtils.isBlank(txType) && ((QatpClassificationConstant.E).equalsIgnoreCase(txType) || (QatpClassificationConstant.O).equalsIgnoreCase(txType))) {
			return true;
		}
		else if(!StringUtils.isBlank(txType) && (QatpClassificationConstant.R).equalsIgnoreCase(txType) )
		{
			return true;
		}
		else if (!StringUtils.isBlank(txType) && (QatpClassificationConstant.V).equalsIgnoreCase(txType)
				&& (QatpClassificationConstant.T).equalsIgnoreCase(txSubType)) {
			transactionDetailDto.getTransactionDto().setTollRevenueType(QatpClassificationConstant.REVENUE_TYPE_ETC);
			return true;
		}
		else if (!StringUtils.isBlank(txType) && (QatpClassificationConstant.V).equalsIgnoreCase(txType)
				&& (QatpClassificationConstant.Q).equalsIgnoreCase(txSubType)) {
			transactionDetailDto.getTransactionDto().setTollRevenueType(QatpClassificationConstant.REVENUE_TYPE_ETC);
			return true;
		}
		else if (!StringUtils.isBlank(txType) && (QatpClassificationConstant.V).equalsIgnoreCase(txType)) {
			// transactionDetailDto.getTransactionDto().setTollRevenueType(QatpClassificationConstant.VIOLATION_REVENUE_TYPE);
			String aetFlag = transactionDetailDto.getTransactionDto().getAetFlag();
			if (aetFlag != null && aetFlag.equals("Y")) {
				transactionDetailDto.getTransactionDto()
						.setTollRevenueType(QatpClassificationConstant.AET_VIOLATION_REVENUE_TYPE);
				logger.info("Calling AET flag condition...Y");
			} else {
				transactionDetailDto.getTransactionDto()
						.setTollRevenueType(QatpClassificationConstant.VIOLATION_REVENUE_TYPE);
				logger.info("Calling AET flag condition...N");
			}
			return true;
		} else if (!StringUtils.isBlank(txType) && (QatpClassificationConstant.U).equalsIgnoreCase(txType)) {
			//computeTollDao.updateTTransDetail(transactionDetailDto.getTransactionDto());
			return true;
			//return false;
		}
		return false;
	}

	/**
	 * Check Transaction Type
	 * 
	 * @param TransactionDto
	 * @return boolean
	 */
	private boolean checkTxType(TransactionDto transactionDto) {
		if (transactionDto == null) {
			return false;
		}
		String txType = transactionDto.getTxType() != null ? transactionDto.getTxType() : null;
		
		if(!StringUtils.isBlank(txType) && (QatpClassificationConstant.R).equalsIgnoreCase(txType) )
		{
			return false;
		}
		else if(!StringUtils.isBlank(txType) && (QatpClassificationConstant.D).equalsIgnoreCase(txType))
		{
			return false;
		}
		return true;

		//return (!StringUtils.isBlank(txType) && (!(QatpClassificationConstant.R).equalsIgnoreCase(txType) || (!(QatpClassificationConstant.D).equalsIgnoreCase(txType))));
	}

	/**
	 * Push Transaction Based on Transaction type
	 * 
	 * @param TransactionDetailDto
	 */
	private void pushMessage(TransactionDetailDto transactionDetailDto) {
		if (transactionDetailDto == null || transactionDetailDto.getTransactionDto() == null) {
			return;
		}
		commonNotificationService.pushMessage(transactionDetailDto.getTransactionDto());
	}
}
