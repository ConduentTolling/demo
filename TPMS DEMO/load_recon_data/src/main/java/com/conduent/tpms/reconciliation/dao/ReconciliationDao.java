package com.conduent.tpms.reconciliation.dao;

import java.time.LocalDateTime;
import java.util.List;

import com.conduent.tpms.reconciliation.dto.AccountTollDto;
import com.conduent.tpms.reconciliation.dto.AgencyPendingDto;
import com.conduent.tpms.reconciliation.dto.EntryTxDto;
import com.conduent.tpms.reconciliation.dto.ExitTxDto;
import com.conduent.tpms.reconciliation.dto.PlazaDto;
import com.conduent.tpms.reconciliation.dto.ReconciliationDto;
import com.conduent.tpms.reconciliation.dto.RtQueueDto;
import com.conduent.tpms.reconciliation.dto.TLaneDto;
import com.conduent.tpms.reconciliation.dto.TranDetailDto;
import com.conduent.tpms.reconciliation.dto.ViolTxDto;
import com.conduent.tpms.reconciliation.model.RTMapping;
import com.conduent.tpms.reconciliation.model.ReconPolicy;




/**
 * ComputeToll Dao interface.
 *
 */
public interface ReconciliationDao {



	public List<RtQueueDto> getLaneTxId();

	public List<AccountTollDto> getTAccountData(Long laneTxId);

	public void insertIntoReconciliation(ReconciliationDto items);

	public boolean isRecordExist(Long laneTxId);

	public void updateIntoReconciliation(ReconciliationDto rec);

	public List<TranDetailDto> getTranDetails(Long laneTxId);

	public List<AgencyPendingDto> getAgencyPendingDetails(Long laneTxId);

	public List<ViolTxDto> getViolTxDetails(Long laneTxId);

	public List<EntryTxDto> getEntryTxDetails(Long laneTxId);

	public List<ExitTxDto> getExitTxDetails(Long laneTxId);

	
	//////////

	public List<TranDetailDto> getTranLaneTxId();

	public void insertIntoT_RT_CheckPoint(List<TranDetailDto> tranDetailList);

	public void insertIntoT_RT_CheckPoint(Long firstLaneTxId, Long lastLaneTxId, String tableName);

	public ReconPolicy getReconPolicyInfo(String status, Integer plazaAgencyId);

	public RTMapping getRtMappingInfo(Integer plazaAgencyId, String accountAgencyId, String planTypeId,
			Integer reconTxStatus);

	public void insert_RT_CheckPoint_ForLastRun(LocalDateTime lastRunDateTime);

	public List<ReconciliationDto> selectReconDataForUpdate();

	public void updateRecon(ReconciliationDto rec);

	public List<PlazaDto> getPlazaInfo();

	public List<TLaneDto> getLaneInfo();


	


}
