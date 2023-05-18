package com.conduent.tpms.ictx.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.conduent.tpms.ictx.constants.IctxConstant;
import com.conduent.tpms.ictx.dao.AwayTransactionDao;
import com.conduent.tpms.ictx.model.AwayTransaction;
import com.conduent.tpms.ictx.model.TCode;
import com.conduent.tpms.ictx.service.DuplicateCheckService;
import com.conduent.tpms.ictx.utility.LocalDateTimeUtility;
import com.conduent.tpms.ictx.utility.StaticDataLoad;

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
		/*
		Optional<List<AwayTransaction>> optionalAwayTxList = awayTransactionDao.getAwayTransaction(awayTx.getDeviceNo(),
				awayTx.getPlazaId(), awayTx.getLaneId(),
				localDateTimeUtility.getUTCLocalDateTime(awayTx.getTxTimestampLocalDateTime().minusSeconds(poachingSeconds)),
				localDateTimeUtility.getUTCLocalDateTime(awayTx.getTxTimestampLocalDateTime().plusSeconds(poachingSeconds)));
		*/
		Optional<List<AwayTransaction>> optionalAwayTxList = awayTransactionDao.getAwayTransactionNew(awayTx.getLaneTxId());
		duplicateFlag = checkDuplicateTx(awayTx, duplicateFlag, optionalAwayTxList);

		if (duplicateFlag) {
			// TODO: Fix the timezone issue while comparing duplicate transaction
			optionalAwayTxList = awayTransactionDao.getAwayTransactionFpms(awayTx.getDeviceNo(), awayTx.getPlazaId(),
					awayTx.getLaneId(),
					localDateTimeUtility.getUTCLocalDateTime(awayTx.getTxTimestampLocalDateTime().minusSeconds(poachingSeconds)),
					localDateTimeUtility.getUTCLocalDateTime(awayTx.getTxTimestampLocalDateTime().plusSeconds(poachingSeconds)));
			if (optionalAwayTxList.isPresent() && !optionalAwayTxList.get().isEmpty()) {
				duplicateFlag = false;
				TCode tempTCode = staticDataLoad.geEtcTxStatusByCodeValue(IctxConstant.CONSTANT_DUPL);
				awayTx.setEtcTxStatus(tempTCode.getCodeId());
				TCode tempTxTCode = staticDataLoad.geTxStatusByCodeValue(IctxConstant.CONSTANT_DUPL);
				awayTx.setTxStatus(tempTxTCode.getCodeId());
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
							&& (awayTx.getLaneTxId().equals(tempAwayTxList.get(i).getLaneTxId()))) {
						duplicateFlag = false; // dupl
						TCode tempTCode = staticDataLoad.geEtcTxStatusByCodeValue(IctxConstant.CONSTANT_DUPL);
						awayTx.setEtcTxStatus(tempTCode.getCodeId());
						TCode tempTxTCode = staticDataLoad.geTxStatusByCodeValue(IctxConstant.CONSTANT_DUPL);
						awayTx.setTxStatus(tempTxTCode.getCodeId());
						break;
					}
				}
			}
		}
		return duplicateFlag;
	}

}
