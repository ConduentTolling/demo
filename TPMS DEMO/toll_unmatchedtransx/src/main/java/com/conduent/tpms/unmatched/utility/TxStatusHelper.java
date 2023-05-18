package com.conduent.tpms.unmatched.utility;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.conduent.tpms.unmatched.constant.UnmatchedConstant;
import com.conduent.tpms.unmatched.model.TCode;




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
		case "E":
			txStatus = UnmatchedConstant.QETOL;
			break;
		case "V":
			txStatus = UnmatchedConstant.QVIOL;
			break;
		case "I":
			txStatus = UnmatchedConstant.QECTX;//updated QCTOL to QECTX
			break;
		case "O":
			txStatus = UnmatchedConstant.QCTOL;//updated QECTX to QCTOL
			break;
		case "U":
			//txStatus = UnmatchedConstant.QUNMA;
			txStatus = UnmatchedConstant.UNMATCHED;
			break;
//		case "D":
//			txStatus = UnmatchedConstant.QDISC;
//			break;
		case "M":
			txStatus = UnmatchedConstant.QMTOL;
			if ("O".equalsIgnoreCase(txnSubType)) {
				txStatus = UnmatchedConstant.QECTX;
			}
			break;
		case "X":
			if (etcAccountId != null) {
				txStatus = UnmatchedConstant.QXTOL;
			} else {
				txStatus = UnmatchedConstant.QITXC;
			}
			break;
		case "P":
			txStatus = UnmatchedConstant.QFTOL;
			break;
		case "R":
			switch (txnSubType) {
			case "L":
				txStatus = UnmatchedConstant.QINVPLAZA;
				break;
			case "D":
				txStatus = UnmatchedConstant.QINVXDATE;
				break;
			case "T":
				txStatus = UnmatchedConstant.QINVAGENCY;
				break;
			case "V":
				txStatus = UnmatchedConstant.QNONVTRX;
				break;
			case "C":
				txStatus = UnmatchedConstant.QINVCLASS;
				break;
			case "M":
				txStatus = UnmatchedConstant.QINVMODE;
				break;
			case "N":
				txStatus = UnmatchedConstant.QNONNUMVAL;
				break;
			case "E":
				txStatus = UnmatchedConstant.QINVEDATE;
				break;
			case "G":
				txStatus = UnmatchedConstant.QINVTAGGRP;
				break;
			case "X":
				txStatus = UnmatchedConstant.QINVTRX;
				break;
			case "R":
				txStatus = UnmatchedConstant.QINVRECLEN;
				break;
			case "B":
				txStatus = UnmatchedConstant.BEYOND;
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
