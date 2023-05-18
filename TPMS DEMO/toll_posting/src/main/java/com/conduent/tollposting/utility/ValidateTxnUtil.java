package com.conduent.tollposting.utility;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.conduent.tollposting.constant.TxStatus;
import com.conduent.tollposting.dto.AccountTollPostDTO;

@Component
public class ValidateTxnUtil {

	private static final Logger logger = LoggerFactory.getLogger(ValidateTxnUtil.class);

	public void checkInvalidPlaza(AccountTollPostDTO tollPost) {
		if (tollPost.getPlazaId() == null || tollPost.getLaneId() == null || tollPost.getPlazaId().intValue() == 0
				|| tollPost.getLaneId().intValue() == 0) {
			logger.info("Invalid plazaId & laneId for laneTxId {} calling exception TxStatus {}",
					tollPost.getLaneTxId(), TxStatus.TX_STATUS_INVPLAZA);
			tollPost.setTxStatus(TxStatus.TX_STATUS_INVPLAZA.getCode());
			return;
		}

	}

	public void checkInvalidTag(AccountTollPostDTO tollPost) {
		if (!tollPost.getTxTypeInd().equals("V") && !Util.isValidDevice(tollPost.getDeviceNo())) {
			logger.info("Validation condition-1 failed for laneTxId {} calling exception TxStatus {}",
					tollPost.getLaneTxId(), TxStatus.TX_STATUS_TAGINV);
			tollPost.setTxStatus(TxStatus.TX_STATUS_TAGINV.getCode());
			return;
		}

	}
	
	public void checkInvalidAccount(AccountTollPostDTO tollPost) {
		if ((tollPost.getTxTypeInd().equals("X") && tollPost.getTxSubtypeInd().equals("I"))
				|| (tollPost.getTxTypeInd().equals("U") && tollPost.getTxSubtypeInd().equals("M")
						|| (tollPost.getTxTypeInd().equals("M") && tollPost.getTxSubtypeInd().equals("I")))) {
			logger.info("Validation condition-3 failed for laneTxId {} calling exception TxStatus {}",
					tollPost.getLaneTxId(), TxStatus.TX_STATUS_INVACC);
			tollPost.setTxStatus(TxStatus.TX_STATUS_INVACC.getCode());
			return;
		}
	}
}
