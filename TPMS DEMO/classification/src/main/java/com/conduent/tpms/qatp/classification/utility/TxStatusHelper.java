package com.conduent.tpms.qatp.classification.utility;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.conduent.tpms.qatp.classification.constants.QatpClassificationConstant;
import com.conduent.tpms.qatp.classification.model.TCode;

/**
 * Get ETC Transaction Status Info
 * 
 * @author deepeshb
 *
 */
@Component
public class TxStatusHelper {

	private static final Logger logger = LoggerFactory.getLogger(TxStatusHelper.class);

	@Autowired
	private MasterDataCache masterDataCache;

	/**
	 * Get etc tx status
	 * 
	 * @param txnType
	 * @param txnSubType
	 * @param etcAccountId
	 * @return Integer
	 */
	public Integer getTxStatus(String txnType, String txnSubType, Long etcAccountId, Long laneTxId) {
		String txStatus = null;

		switch (txnType) {
		case "C":
			txStatus = QatpClassificationConstant.TX_STATUS_NOCC;
			break;
		case "E":
			txStatus = QatpClassificationConstant.QETOL;
			break;
		case "V":
			txStatus = QatpClassificationConstant.QVIOL;
			break;
		case "I":
			txStatus = QatpClassificationConstant.QECTX;//updated QCTOL to QECTX
			break;
		case "O":
			txStatus = QatpClassificationConstant.QCTOL;//updated QECTX to QCTOL
			break;
		case "U":
			txStatus = QatpClassificationConstant.UNMATCHED;
			//txStatus = QatpClassificationConstant.QUNMA;//UNMATCHED
			break;
		case "D":
			txStatus = QatpClassificationConstant.QDISC;
			break;
		case "M":
			txStatus = QatpClassificationConstant.QMTOL;
			if ("O".equalsIgnoreCase(txnSubType)) {
				txStatus = QatpClassificationConstant.QECTX;
			}
			break;
		case "X":
			if (etcAccountId != null) {
				txStatus = QatpClassificationConstant.QXTOL;
			} else {
				txStatus = QatpClassificationConstant.QITXC;
			}
			break;
		case "P":
			txStatus = QatpClassificationConstant.TX_STATUS_NOCC;
			break;
		case "R":
			switch (txnSubType) {
			case "L":
				txStatus = QatpClassificationConstant.QINVPLAZA;
				break;
			case "D":
				txStatus = QatpClassificationConstant.QINVXDATE;
				break;
			case "T":
				txStatus = QatpClassificationConstant.QINVAGENCY;
				break;
			case "V":
				txStatus = QatpClassificationConstant.QNONVTRX;
				break;
			case "C":
				txStatus = QatpClassificationConstant.QINVCLASS;
				break;
			case "M":
				txStatus = QatpClassificationConstant.QINVMODE;
				break;
			case "N":
				txStatus = QatpClassificationConstant.QNONNUMVAL;
				break;
			case "E":
				txStatus = QatpClassificationConstant.QINVEDATE;
				break;
			case "G":
				txStatus = QatpClassificationConstant.QINVTAGGRP;
				break;
			case "X":
				txStatus = QatpClassificationConstant.QINVTRX;
				break;
			case "R":
				txStatus = QatpClassificationConstant.QINVRECLEN;
				break;
			case "B":
				txStatus = QatpClassificationConstant.BEYOND;
				break;
			case "Z":
				txStatus = QatpClassificationConstant.TX_STATUS_NOCC;
				break;
			}
		}
		
		if(txStatus!=null) {
			TCode tempTCode = masterDataCache.geTxStatusByCodeValue(txStatus);
			return tempTCode != null ? tempTCode.getCodeId() : null;
		}
		logger.info("Tx status:{} for laneTxId: {}", txStatus, laneTxId);
		return null;
	}

}
