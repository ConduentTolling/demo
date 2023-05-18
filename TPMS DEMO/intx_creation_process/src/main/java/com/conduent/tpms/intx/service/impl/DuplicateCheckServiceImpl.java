package com.conduent.tpms.intx.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.conduent.tpms.intx.constants.IntxConstant;
import com.conduent.tpms.intx.dao.AwayTransactionDao;
import com.conduent.tpms.intx.model.AwayTransaction;
import com.conduent.tpms.intx.model.TCode;
import com.conduent.tpms.intx.service.DuplicateCheckService;
import com.conduent.tpms.intx.utility.LocalDateTimeUtility;
import com.conduent.tpms.intx.utility.StaticDataLoad;

/**
 * 
 * Duplication check service
 * 
 * @author deepeshb
 *
 */
@Service
public class DuplicateCheckServiceImpl implements DuplicateCheckService {

	@Autowired
	AwayTransactionDao awayTransactionDao;

	@Autowired
	StaticDataLoad staticDataLoad;

	@Autowired
	LocalDateTimeUtility localDateTimeUtility;

	/**
	 * Duplicate Validation on given transaction
	 * 
	 * @param awayTx
	 * @return boolean
	 */
	@Override
	public boolean validateDuplicateTx(AwayTransaction awayTx) {
		boolean duplicateFlag = true; //false

		Integer poachingSeconds = staticDataLoad.getPoachingLimitByAgency(awayTx.getPlazaAgencyId());
		
		if(poachingSeconds == null)
		{
			poachingSeconds = 0;
		}
		
		// TODO: Fix the timezone issue while comparing duplicate transaction
		Optional<List<AwayTransaction>> optionalAwayTxList = awayTransactionDao.getAwayTransaction(awayTx.getDeviceNo(),
				awayTx.getPlazaId(), awayTx.getLaneId(),
//				localDateTimeUtility.getUTCLocalDateTime(awayTx.getTxTimestamp().minusSeconds(poachingSeconds)),
//				localDateTimeUtility.getUTCLocalDateTime(awayTx.getTxTimestamp().plusSeconds(poachingSeconds)));
				localDateTimeUtility.getUTCLocalDateTime(awayTx.getEtcTxTimestamp().minusSeconds(poachingSeconds)),
				localDateTimeUtility.getUTCLocalDateTime(awayTx.getEtcTxTimestamp().plusSeconds(poachingSeconds)));

		duplicateFlag = checkDuplicateTx(awayTx, duplicateFlag, optionalAwayTxList);

		if (duplicateFlag) {
			// TODO: Fix the timezone issue while comparing duplicate transaction
			optionalAwayTxList = awayTransactionDao.getAwayTransactionFpms(awayTx.getDeviceNo(), awayTx.getPlazaId(),
					awayTx.getLaneId(),
//					localDateTimeUtility.getUTCLocalDateTime(awayTx.getTxTimestamp().minusSeconds(poachingSeconds)),
//					localDateTimeUtility.getUTCLocalDateTime(awayTx.getTxTimestamp().plusSeconds(poachingSeconds)));
					localDateTimeUtility.getUTCLocalDateTime(awayTx.getEtcTxTimestamp().minusSeconds(poachingSeconds)),
					localDateTimeUtility.getUTCLocalDateTime(awayTx.getEtcTxTimestamp().plusSeconds(poachingSeconds)));
			if (optionalAwayTxList.isPresent() && !optionalAwayTxList.get().isEmpty()) {
				duplicateFlag = false;
				TCode tempTCode = staticDataLoad.geEtcTxStatusByCodeValue(IntxConstant.CONSTANT_DUPL);
				awayTx.setEtcTxStatus(tempTCode.getCodeId());
			}
		}
		return duplicateFlag;
	}

	/**
	 * Validate Duplicate Tx
	 * 
	 * @param awayTx
	 * @param duplicateFlag
	 * @param optionalAwayTxList
	 * @return boolean
	 */
	private boolean checkDuplicateTx(AwayTransaction awayTx, boolean duplicateFlag,
			Optional<List<AwayTransaction>> optionalAwayTxList) {
		List<AwayTransaction> tempAwayTxList;
		if (optionalAwayTxList.isPresent()) {
			tempAwayTxList = optionalAwayTxList.get();
			if (tempAwayTxList.size() == 1) {
				duplicateFlag = true; //not dupl
			} else if (tempAwayTxList.size() > 1) {
				//when getDstAtpFileId!=null record is processed and laneTxIDs are diff i.e records are diff -> not dupl
				for (int i = 0; i < tempAwayTxList.size(); i++) { 
					if (tempAwayTxList.get(i).getDstAtpFileId() != null
							&& (!awayTx.getLaneTxId().equals(tempAwayTxList.get(i).getLaneTxId()))) {
						duplicateFlag = false; // dupl
						TCode tempTCode = staticDataLoad.geEtcTxStatusByCodeValue(IntxConstant.CONSTANT_DUPL);
						awayTx.setEtcTxStatus(tempTCode.getCodeId());
						break;
					}
				}
			}
		}
		return duplicateFlag;
	}

}
