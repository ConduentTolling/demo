package com.conduent.tpms.reconciliation.serviceImpl;

import java.time.LocalDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.conduent.app.timezone.utility.TimeZoneConv;
import com.conduent.tpms.reconciliation.dao.ReconciliationDao;
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
import com.conduent.tpms.reconciliation.model.ConfigVariable;
import com.conduent.tpms.reconciliation.model.RTMapping;
import com.conduent.tpms.reconciliation.model.ReconPolicy;
import com.conduent.tpms.reconciliation.service.ReconciliationService;
import com.conduent.tpms.reconciliation.utility.MasterDataCache;

/**
 * Reconciliation service
 * @author shrikantm3
 *
 */
@Service
public class ReconciloationServiceImpl implements ReconciliationService {


	@Autowired
	private ReconciliationDao reconciliationDAO;

	@Autowired
	ConfigVariable configVariable;

	@Autowired 
	private TimeZoneConv timeZoneConv;
	
	@Autowired
	private MasterDataCache masterDataCache;

	private static final Logger logger = LoggerFactory.getLogger(ReconciliationService.class);


	@Override
	public void loadReconciliationData() {

		List<TranDetailDto> tranDetailList = reconciliationDAO.getTranLaneTxId();
		ReconciliationDto rec = new ReconciliationDto();

		if (tranDetailList != null) {
			
			LocalDateTime lastRunDateTime = timeZoneConv.currentTime();
			Long firstLaneTxId =  tranDetailList.get(0).getLaneTxId();
			Long LastLaneTxId =  tranDetailList.get(tranDetailList.size()-1).getLaneTxId();
			
			for (TranDetailDto tranDetail : tranDetailList) {

				Long LaneTxId = tranDetail.getLaneTxId();
				tranDetail.setLaneTxId(LaneTxId);
				rec.setLaneTxId(LaneTxId);
				//rec.setDebitCreditFlag(tranDetail.getDebitCredit());
				rec.setTrxSerialNumber(tranDetail.getTrxLaneSerial());

				List<AccountTollDto> accountList = reconciliationDAO.getTAccountData(LaneTxId);
				List<AgencyPendingDto> agencyDetailList = reconciliationDAO.getAgencyPendingDetails(LaneTxId);
				List<ViolTxDto> violTxList = reconciliationDAO.getViolTxDetails(LaneTxId);
				List<EntryTxDto> entryTxList = reconciliationDAO.getEntryTxDetails(LaneTxId);
				List<ExitTxDto> exitTxList = reconciliationDAO.getExitTxDetails(LaneTxId);
				

				boolean flag = false;

				if (accountList != null) {
					
					for (AccountTollDto acc : accountList) {
						if (acc.getLaneTxId().equals(LaneTxId)) {
							rec.setTxExternRefNo(acc.getTxExternRefNo());
							rec.setPostedFareAmount(acc.getPostedFareAmount());
							rec.setAccountAgencyId(acc.getAccountAgencyId());
							rec.setRevenueDate(acc.getRevenueDate());
							rec.setPostedDate(acc.getPostedDate());
							rec.setPlateCountry(acc.getPlateCountry());
							rec.setPlateState(acc.getPlateState());
							rec.setPlateNumber(acc.getPlateNumber());
							rec.setTxTypeInd(acc.getTxTypeInd());
							rec.setTxStatus(acc.getTxStatus());
							rec.setPlanTypeId(acc.getPlanTypeId());
							rec.setPlazaAgencyId(acc.getPlazaAgencyId());
							rec.setExternFileId(acc.getExternFileId());
							rec.setPlateType(0);
							rec.setProcessStatus("F");
							rec.setDeleteFlag("F");
							// data collection for MTA
//							rec.setCollectorId(acc.getCollectorId());
//							rec.setLaneMode(acc.getLaneMode());
//							rec.setDeviceNo(acc.getDeviceNo());
//							rec.setPlazaId(acc.getPlazaId());
//							rec.setLaneId(acc.getLaneId());
//							rec.setTxTimeStamp(acc.getTxTimeStamp());
							flag=true;

						} 
					}
					
				}

				// if accountList is getting null then check for T_Agency_Tx_Pending
				if (agencyDetailList != null) {
					if (flag == false) {
						for (AgencyPendingDto agencyList : agencyDetailList) {
							{
								if (agencyList.getLaneTxId().equals(LaneTxId) && agencyList.getTxType().equals("O")) {
									rec.setTxExternRefNo(agencyList.getTxExternRefNo());
									rec.setPostedFareAmount(agencyList.getPostedFareAmount());
									rec.setAccountAgencyId(agencyList.getAccountAgencyId());
									rec.setRevenueDate(agencyList.getRevenueDate());
									rec.setPostedDate(agencyList.getPostedDate());
									rec.setPlateCountry(agencyList.getPlateCountry());
									rec.setPlateState(agencyList.getPlateState());
									rec.setPlateNumber(agencyList.getPlateNumber());
									rec.setTxTypeInd(agencyList.getTxType());
									rec.setTxStatus(agencyList.getTxStatus());
									rec.setPlanTypeId(agencyList.getPlanTypeId());
									rec.setPlazaAgencyId(agencyList.getPlazaAgencyId());
									rec.setExternFileId(agencyList.getExternFileId());
									rec.setPlateType(0);
									rec.setProcessStatus("I");
									rec.setDeleteFlag("F");
									// data collection for MTA
//									rec.setCollectorId(agencyList.getCollectorId());
//									rec.setLaneMode(agencyList.getLaneMode());
//									rec.setDeviceNo(agencyList.getDeviceNo());
//									rec.setPlazaId(agencyList.getPlazaId());
//									rec.setLaneId(agencyList.getLaneId());
//									rec.setTxTimeStamp(agencyList.getTxTimeStamp());
									flag=true;
								}
							}

						}
					}
				}

				// if accountList is getting null then check for T_VIOL_TX
				if (violTxList != null) {
					if (flag == false) {
						for (ViolTxDto violList : violTxList) {
							{
								if (violList.getLaneTxId().equals(LaneTxId) && violList.getTxTypeInd().equals("V")) {
									rec.setTxExternRefNo(violList.getTxExternRefNo());
									rec.setPostedFareAmount(violList.getPostedFareAmount());
									rec.setAccountAgencyId(violList.getAccountAgencyId());
									rec.setRevenueDate(violList.getRevenueDate());
									rec.setPostedDate(violList.getPostedDate());
									rec.setPlateCountry(violList.getPlateCountry());
									rec.setPlateState(violList.getPlateState());
									rec.setPlateNumber(violList.getPlateNumber());
									rec.setTxTypeInd(violList.getTxTypeInd());
									rec.setTxStatus(violList.getTxStatus());
									rec.setPlanTypeId(violList.getPlanTypeId());
									rec.setPlazaAgencyId(violList.getPlazaAgencyId());
									rec.setExternFileId(violList.getExternFileId());
									rec.setPlateType(0);
									rec.setProcessStatus("I");
									rec.setDeleteFlag("F");
									// data collection for MTA
//									rec.setCollectorId(violList.getCollectorId());
//									rec.setLaneMode(violList.getLaneMode());
//									rec.setDeviceNo(violList.getDeviceNo());
//									rec.setPlazaId(violList.getPlazaId());
//									rec.setLaneId(violList.getLaneId());
//									rec.setTxTimeStamp(violList.getTxTimeStamp());
									flag=true;
								}
							}

						}

					}
				}

				// if accountList is getting null then check for T_UNMATCHED_ENTRY_TX
				if (entryTxList != null) {
					if (flag == false) {
						for (EntryTxDto entryList : entryTxList) {
							{
								if (entryList.getLaneTxId().equals(LaneTxId) 
										&& (entryList.getTxTypeInd().equals("D") || entryList.getTxTypeInd().equals("U"))
										&& entryList.getTxSubtypeInd().equals("E")
										&& entryList.getTollSystemType().equals("E")) {
									rec.setTxExternRefNo(entryList.getTxExternRefNo());
									rec.setPostedFareAmount(entryList.getPostedFareAmount());
									rec.setAccountAgencyId(entryList.getAccountAgencyId());
									rec.setRevenueDate(entryList.getRevenueDate());
									rec.setPostedDate(entryList.getPostedDate());
									rec.setPlateCountry(entryList.getPlateCountry());
									rec.setPlateState(entryList.getPlateState());
									rec.setPlateNumber(entryList.getPlateNumber());
									rec.setTxTypeInd(entryList.getTxTypeInd());
									rec.setTxStatus(entryList.getTxStatus());
									rec.setPlanTypeId(entryList.getPlanTypeId());
									rec.setPlazaAgencyId(entryList.getPlazaAgencyId());
									rec.setExternFileId(entryList.getExternFileId());
									rec.setPlateType(0);
									rec.setProcessStatus("I");
									rec.setDeleteFlag("F");
									// data collection for MTA
//									rec.setCollectorId(entryList.getCollectorId());
//									rec.setLaneMode(entryList.getLaneMode());
//									rec.setDeviceNo(entryList.getDeviceNo());
//									rec.setPlazaId(entryList.getPlazaId());
//									rec.setLaneId(entryList.getLaneId());
//									rec.setTxTimeStamp(entryList.getTxTimeStamp());
									flag=true;
								}
							}

						}
					}
				}

				// if accountList is getting null then check for T_UNMATCHED_EXIT_TX
				if (exitTxList != null) {
					if (flag == false) {
						for (ExitTxDto exitList : exitTxList) {
							{
								if (exitList.getLaneTxId().equals(LaneTxId) && exitList.getTxTypeInd().equals("U")
										&& exitList.getTxSubtypeInd().equals("E")
										&& exitList.getTollSystemType().equals("X")) {
									rec.setTxExternRefNo(exitList.getTxExternRefNo());
									rec.setPostedFareAmount(exitList.getPostedFareAmount());
									rec.setAccountAgencyId(exitList.getAccountAgencyId());
									rec.setRevenueDate(exitList.getRevenueDate());
									rec.setPostedDate(exitList.getPostedDate());
									rec.setPlateCountry(exitList.getPlateCountry());
									rec.setPlateState(exitList.getPlateState());
									rec.setPlateNumber(exitList.getPlateNumber());
									rec.setTxTypeInd(exitList.getTxTypeInd());
									rec.setTxStatus(exitList.getTxStatus());
									rec.setPlanTypeId(exitList.getPlanTypeId());
									rec.setPlazaAgencyId(exitList.getPlazaAgencyId());
									rec.setExternFileId(exitList.getExternFileId());
									rec.setPlateType(0);
									rec.setProcessStatus("I");
									rec.setDeleteFlag("F");
									// data collection for MTA
//									rec.setCollectorId(exitList.getCollectorId());
//									rec.setLaneMode(exitList.getLaneMode());
//									rec.setDeviceNo(exitList.getDeviceNo());
//									rec.setPlazaId(exitList.getPlazaId());
//									rec.setLaneId(exitList.getLaneId());
//									rec.setTxTimeStamp(exitList.getTxTimeStamp());
									flag=true;
								}
							}

						}
					}
				}
				
				// if accountList is getting null then check for T_AGENCY_TX_PENDING
				if (tranDetailList != null) {
					if (flag == false) {
						for (TranDetailDto tran : tranDetailList) {
							if (tran.getLaneTxId().equals(LaneTxId)) {
								rec.setTxExternRefNo(tran.getTxExternRefNo());
								rec.setPostedFareAmount(tran.getPostedFareAmount());
								rec.setAccountAgencyId(tran.getAccountAgencyId());//ACCT_AGENCY_ID
								rec.setRevenueDate(tran.getRevenueDate());
								rec.setPostedDate(tran.getPostedDate());
								rec.setPlateCountry(tran.getPlateCountry());
								rec.setPlateState(tran.getPlateState());
								rec.setPlateNumber(tran.getPlateNumber());
								rec.setTxTypeInd(tran.getTxTypeInd());
								rec.setTxStatus(tran.getTxStatus());
								rec.setPlanTypeId(tran.getPlanTypeId());
								rec.setPlazaAgencyId(tran.getPlazaAgencyId());
								rec.setExternFileId(tran.getExtFileId());
								rec.setPlateType(0);
								rec.setProcessStatus("I");
								rec.setDeleteFlag("F");
								// data collection for MTA
//								rec.setCollectorId(tran.getCollectorId());
//								rec.setLaneMode(tran.getLaneMode());
//								rec.setDeviceNo(tran.getDeviceNo());
//								rec.setPlazaId(tran.getPlazaId());
//								rec.setLaneId(tran.getLaneId());
//								rec.setTxTimeStamp(tran.getTxTimeStamp());
								flag=true;

							}
						}
					}
				}

				//if (flag == false) {
					//AgencyInfoVO agencyInfoVO=masterDataCache.getAgency(etcAgencyId);
					
					//PlazaDto plaza = masterDataCache.getPlaza(rec.getPlazaId());
					//TLaneDto lane =  masterDataCache.getLane(rec.getLaneId());
					//rec.setExternPlazaId(plaza.getExternPlazaId());
					//rec.setExternLaneId(lane.getExternLaneId());
					String status = rec.getTxStatus();
					Integer plazaAgencyId = rec.getPlazaAgencyId();
					ReconPolicy reconPolicyInfo = reconciliationDAO.getReconPolicyInfo(status,plazaAgencyId);
					rec.setReconTxStatus(reconPolicyInfo.getReconTxStatus());
					rec.setIsFinalState(reconPolicyInfo.getIsFinalState());
					rec.setCreateRt(reconPolicyInfo.getCreateRt());
					
					RTMapping rtMapping = reconciliationDAO.getRtMappingInfo(rec.getPlazaAgencyId(), rec.getAccountAgencyId(),
							rec.getPlanTypeId(), rec.getReconTxStatus());
					rec.setReconStatusInd(rtMapping.getReconStatusInd());
					rec.setReconSubCodeInd(rtMapping.getReconSubCodeInd());
					boolean count = reconciliationDAO.isRecordExist(LaneTxId);
					
					String debitCredit = checkPositiveNegative(rec.getPostedFareAmount());
					rec.setDebitCreditFlag(debitCredit);
					
					// select data from recon_policy and update in reconciliation table
					List<ReconciliationDto> reconList = reconciliationDAO.selectReconDataForUpdate();
					logger.info("--------------- reconList ---------------"+reconList);
					if (reconList != null) {
						for (ReconciliationDto rcon : reconList) {
							ReconPolicy reconPolicyInfo1 = reconciliationDAO.getReconPolicyInfo(rcon.getTxStatus(),
									rcon.getPlazaAgencyId());
							rcon.setReconTxStatus(reconPolicyInfo1.getReconTxStatus());
							rcon.setIsFinalState(reconPolicyInfo1.getIsFinalState());
							rcon.setCreateRt(reconPolicyInfo1.getCreateRt());
							if(rcon.getCreateRt().equals("T")) 
							{
							reconciliationDAO.updateRecon(rcon);
							}
						}
					}
					
					
					logger.info("Record List==== > {}", rec.toString());
					if (count == true) {
						logger.info("--------------- inside true block for updatation ---------------");
						reconciliationDAO.updateIntoReconciliation(rec);
					} else {
						logger.info("--------------- inside false block for insertion ---------------");
						
						reconciliationDAO.insertIntoReconciliation(rec);
						
					}
					
					
					
					
			}
			String TableName =  "T_TRAN_DETAIL";
			reconciliationDAO.insertIntoT_RT_CheckPoint(firstLaneTxId, LastLaneTxId, TableName);
			reconciliationDAO.insert_RT_CheckPoint_ForLastRun(lastRunDateTime);	
			
//			boolean count = reconciliationDAO.isRecordExist(firstLaneTxId);
//			//String TableName =  "T_TRAN_DETAIL";
//			logger.info("--------------- firstLaneTxId ---------------"+firstLaneTxId);
//			logger.info("--------------- LastLaneTxId ---------------"+LastLaneTxId);
//			if (count == true) {
//				logger.info("--------------- firstLaneTxId already present---------------" + firstLaneTxId);
//			} else {
//				reconciliationDAO.insertIntoT_RT_CheckPoint(firstLaneTxId, LastLaneTxId, TableName);
//				reconciliationDAO.insert_RT_CheckPoint_ForLastRun(lastRunDateTime);	
//			}
			
		}
	}



	private String checkPositiveNegative(Double postedFareAmount) {
		if(postedFareAmount>0) {
			return "+";
		}else {
			return "-";
		}
	}



	public void m1(List<AccountTollDto> accountList, ReconciliationDto rec ){
		
		for (AccountTollDto acc : accountList) {
			rec.setTxExternRefNo(acc.getTxExternRefNo());
			rec.setPostedFareAmount(acc.getPostedFareAmount());
			rec.setAccountAgencyId(acc.getAccountAgencyId());
			rec.setRevenueDate(acc.getRevenueDate());
			rec.setPostedDate(acc.getPostedDate());
			rec.setPlateCountry(acc.getPlateCountry());
			rec.setPlateState(acc.getPlateState());
			rec.setPlateNumber(acc.getPlateNumber());
		}
		
	}


	@Override
	public void loadReconciliationData1() {
		// TODO Auto-generated method stub
		
	}
}
