package com.conduent.tpms.intx.utility;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.conduent.tpms.intx.constants.IntxConstant;
import com.conduent.tpms.intx.model.TCode;

/**
 * Get ETC Transaction Status Info
 * 
 * @author deepeshb
 *
 */

@Component
public class EtcTxStatusHelper {

	private static final Logger logger = LoggerFactory.getLogger(EtcTxStatusHelper.class);

	@Autowired
	private StaticDataLoad staticDataLoad;

	/**
	 * Get etc tx status
	 * 
	 * @param txnType
	 * @param txnSubType
	 * @param etcAccountId
	 * @return Integer
	 */
	public Integer getEtcTxStatus(String txnType, String txnSubType, Long etcAccountId, Long laneTxId) {
		String etcTxStatus = null;

		switch (txnType) {
		case "E":
			etcTxStatus = IntxConstant.QETOL;
			break;
		case "V":
			etcTxStatus = IntxConstant.QVIOL;
			break;
		case "I":
			etcTxStatus = IntxConstant.QCTOL;
			break;
		case "O":
			etcTxStatus = IntxConstant.QECTX;
			break;
		case "U":
			etcTxStatus = IntxConstant.QUNMA;
			break;
		case "D":
			etcTxStatus = IntxConstant.QDISC;
			break;
		case "M":
			etcTxStatus = IntxConstant.QMTOL;
			if ("O".equalsIgnoreCase(txnSubType)) {
				etcTxStatus = IntxConstant.QECTX;
			}
			break;
		case "X":
			if (etcAccountId != null) {
				etcTxStatus = IntxConstant.QXTOL;
			} else {
				etcTxStatus = IntxConstant.QITXC;
			}
			break;
		case "P":
			etcTxStatus = IntxConstant.QFTOL;
			break;
		case "R":
			switch (txnSubType) {
			case "L":
				etcTxStatus = IntxConstant.QINVPLAZA;
				break;
			case "D":
				etcTxStatus = IntxConstant.QINVXDATE;
				break;
			case "T":
				etcTxStatus = IntxConstant.QINVAGENCY;
				break;
			case "V":
				etcTxStatus = IntxConstant.QNONVTRX;
				break;
			case "C":
				etcTxStatus = IntxConstant.QINVCLASS;
				break;
			case "M":
				etcTxStatus = IntxConstant.QINVMODE;
				break;
			case "N":
				etcTxStatus = IntxConstant.QNONNUMVAL;
				break;
			case "E":
				etcTxStatus = IntxConstant.QINVEDATE;
				break;
			case "G":
				etcTxStatus = IntxConstant.QINVTAGGRP;
				break;
			case "X":
				etcTxStatus = IntxConstant.QINVTRX;
				break;
			case "R":
				etcTxStatus = IntxConstant.QINVRECLEN;
				break;
			case "B":
				etcTxStatus = IntxConstant.BEYOND;
				break;
			}
		}
		
		if(etcTxStatus!=null) {
			TCode tempTCode = staticDataLoad.geEtcTxStatusByCodeValue(etcTxStatus);
			return tempTCode != null ? tempTCode.getCodeId() : null;
		}
		logger.info("ETC tx status:{} for laneTxId: {}", etcTxStatus, laneTxId);
		return null;
	}

}
