package com.conduent.tpms.ictx.utility;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.conduent.tpms.ictx.constants.IctxConstant;
import com.conduent.tpms.ictx.model.TCode;



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
			etcTxStatus = IctxConstant.QETOL;
			break;
		case "V":
			etcTxStatus = IctxConstant.QVIOL;
			break;
		case "I":
			etcTxStatus = IctxConstant.QCTOL;
			break;
		case "O":
			etcTxStatus = IctxConstant.QECTX;
			break;
		case "U":
			etcTxStatus = IctxConstant.QUNMA;
			break;
		case "D":
			etcTxStatus = IctxConstant.QDISC;
			break;
		case "M":
			etcTxStatus = IctxConstant.QMTOL;
			if ("O".equalsIgnoreCase(txnSubType)) {
				etcTxStatus = IctxConstant.QECTX;
			}
			break;
		case "X":
			if (etcAccountId != null) {
				etcTxStatus = IctxConstant.QXTOL;
			} else {
				etcTxStatus = IctxConstant.QITXC;
			}
			break;
		case "P":
			etcTxStatus = IctxConstant.QFTOL;
			break;
		case "R":
			switch (txnSubType) {
			case "L":
				etcTxStatus = IctxConstant.QINVPLAZA;
				break;
			case "D":
				etcTxStatus = IctxConstant.QINVXDATE;
				break;
			case "T":
				etcTxStatus = IctxConstant.QINVAGENCY;
				break;
			case "V":
				etcTxStatus = IctxConstant.QNONVTRX;
				break;
			case "C":
				etcTxStatus = IctxConstant.QINVCLASS;
				break;
			case "M":
				etcTxStatus = IctxConstant.QINVMODE;
				break;
			case "N":
				etcTxStatus = IctxConstant.QNONNUMVAL;
				break;
			case "E":
				etcTxStatus = IctxConstant.QINVEDATE;
				break;
			case "G":
				etcTxStatus = IctxConstant.QINVTAGGRP;
				break;
			case "X":
				etcTxStatus = IctxConstant.QINVTRX;
				break;
			case "R":
				etcTxStatus = IctxConstant.QINVRECLEN;
				break;
			case "B":
				etcTxStatus = IctxConstant.BEYOND;
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
