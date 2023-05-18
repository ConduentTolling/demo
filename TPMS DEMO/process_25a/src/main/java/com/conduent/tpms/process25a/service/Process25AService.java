package com.conduent.tpms.process25a.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.conduent.tpms.process25a.dao.Process25ADao;
import com.conduent.tpms.process25a.dto.Plaza;
import com.conduent.tpms.process25a.model.AgencyInfoVO;
import com.conduent.tpms.process25a.model.ConfigVariable;
import com.conduent.tpms.process25a.model.Pending25ATransactions;
import com.conduent.tpms.process25a.utility.MasterDataCache;


@Service
public class Process25AService {
	
		
		
		@Autowired
		private Process25ADao process25ADao; 
		
		@Autowired
		MasterDataCache masterDataCache;
		
		@Autowired
		private ConfigVariable configVariable;
		
		
		private static final Logger logger = LoggerFactory.getLogger(Process25AService.class);
		List<String> processedfiles=new ArrayList<>(); 
		
		final String PLAZA_24 = "24";
		final String PLAZA_25 = "25";
		final String PLAZA_25A = "25A";
		final String PLAZA_26 = "26";
		
		final String PLAZA_34 = "34";
		
		final String WAIT_DAYS = "25A_MATCH_DAYS";
		int max_wait_days_device = 0;
		
		final String WAIT_DAYS_PLATE = "25A_MATCH_DAYS_PLATE";
		int max_wait_days_plate = 0;
		
		final String TRAVEL_MINS = "25A_TRAVEL_MINS";
		int max_travel_mins = 0;
		

		
		public String processTransactions() throws IOException, InterruptedException
		{
			/*
			// Get passage time between plazas
			t_process_parameters.25A_TRAVEL_MINS/25A_MAX_TRAVEL_MINS
			t_process_parameters.25A_WAIT_DAYS/25A_MAX_WAIT_DAYS
			t_process_parameters.25A_WAIT_DAYS_PLATE/25A_MAX_WAIT_DAYS
			
			Didnt get these values in  select * from master.t_process_parameters
			*/
			max_travel_mins = Integer.parseInt(masterDataCache.getParamValueByName(TRAVEL_MINS).getParamValue());
			max_wait_days_device = Integer.parseInt(masterDataCache.getParamValueByName(WAIT_DAYS).getParamValue());
			max_wait_days_plate = Integer.parseInt(masterDataCache.getParamValueByName(WAIT_DAYS_PLATE).getParamValue());
			
			
		//Step 1:  processes tolls between entry 25A and Exit 26
			processEntry25AExit26();
			
			processEntry26Exit25A();
			
			processEntry25AExit25();
			
			processEntry25Exit25A();
			
			processEntry24Exit25();
			
			processEntry25Exit24();
			
			
			
			
			return "Transaction processed";

		}
		
		private void processEntry25Exit24() {

			List<Pending25ATransactions> objectList = new ArrayList<>();

			Plaza entryPlaza = masterDataCache.getPlazaByExtPlazaId(PLAZA_25);
			Plaza exitPlaza = masterDataCache.getPlazaByExtPlazaId(PLAZA_24);
			logger.info("entryPlaza.getPlazaId(): {}, exitPlaza.getPlazaId(): {}", entryPlaza.getPlazaId(), exitPlaza.getPlazaId());
			//objectList = process25ADao.getPending25ATolls(entryPlaza.getPlazaId(), exitPlaza.getPlazaId());

			//all 25A-26+ tolls
			objectList = process25ADao.getPending25ATollsExit26(entryPlaza.getPlazaId(), exitPlaza.getPlazaId());

			//objectList.stream().forEach(e -> findRelatedToll(e));
			for(Pending25ATransactions transaction: objectList) {
				int tollFindStatus = 0;
				List<Pending25ATransactions> relatedList = new ArrayList<>();
				//tollFindStatus = findAllRelatedToll(transaction,PLAZA_25,PLAZA_25A);
				relatedList = findAllRelatedToll(transaction,PLAZA_25A,PLAZA_25);
				tollFindStatus = relatedList.size();
				// what if transaction.getMatchRefNo() is already available
				int seqNo = process25ADao.getCompleteSeq();

				int waitDays;
				if ((transaction.isReciprocityTxn()!=null && transaction.isReciprocityTxn().equalsIgnoreCase("T")) || transaction.getDeviceNo() != null) {
					waitDays = max_wait_days_device; //Need to check these values from PB
				}else {
					waitDays =max_wait_days_plate; //value of this needed
				}

				if (tollFindStatus != 0) {
					/*
					-- -- check for 25a-34 toll
				-- check 25a-34 entry with 25-25a exit
				what does this mean?

					 */
					int tollFindStatus24_25A = 0;
					List<Pending25ATransactions> relatedList24_25A = new ArrayList<>();
					//tollFindStatus = findAllRelatedToll(transaction,PLAZA_25,PLAZA_25A);
					relatedList24_25A = findAllRelatedToll(transaction,PLAZA_34,PLAZA_25A);
					tollFindStatus24_25A = relatedList24_25A.size();

							if (tollFindStatus24_25A != 0) {
								/*
						  -- post 24-25 at regular rates
					complete_ref_no_upd (each_txn.lane_tx_id);
					complete_txn_ins

					-- post 25-25a at regular rates
					complete_ref_no_upd (lv_addnl_lane_tx_id);
					complete_txn_ins
								 */
								transaction.setMatchRefNo(seqNo);
								completeRefNoUpdate(transaction.getLaneTxId(),seqNo);
								completeTnxIns(transaction);
								
								relatedList.get(0).setMatchRefNo(seqNo);
								completeRefNoUpdate(relatedList.get(0).getLaneTxId(),seqNo);
								completeTnxIns(relatedList.get(0));

								if (tollFindStatus24_25A == 1) {
									/*
							 -- post 25a-26+ at regular rates
					   complete_ref_no_upd (lv_lane_tx_id);
					   complete_txn_ins
									 */
									completeRefNoUpdate(relatedList24_25A.get(0).getLaneTxId(),seqNo);
									completeTnxIns(relatedList24_25A.get(0));
								}else if(relatedList24_25A.get(0).getWaitDays() > waitDays) {
									/*
								 -- did not find 25a-34 toll
                    -- after wait days, stitch 25a-24 and 25-24 tolls to 25a-24 for $0
									 */
									completeRefNoUpdate(relatedList24_25A.get(0).getLaneTxId(),seqNo);
									completeTnxIns(relatedList24_25A.get(0));

								}

							}else {
								/*
								 * -- 34-25a toll not found
                 			-- after wait days, stitch 25a-24 and 25-24 tolls to 25a-24 for $0
								 */
								if(transaction.getWaitDays() > waitDays) {
									transaction.setMatchRefNo(seqNo);
									completeRefNoUpdate(transaction.getLaneTxId(),seqNo);
									completeTnxIns(transaction);
								}

								if (relatedList.get(0).getWaitDays() > waitDays) {
									/*-- -- post 25a-24+ at regular rate
									complete_ref_no_upd (lv_lane_tx_id);
									complete_txn_ins
									 */
									String laneTxId = transaction.getLaneTxId()+""+relatedList.get(0).getLaneTxId();
									relatedList.get(0).setMatchRefNo(seqNo);
									completeRefNoUpdate(laneTxId,seqNo);
									completeTnxIns(relatedList.get(0));
								}
							}

				}else if(transaction.getWaitDays() > waitDays) {
					/*
					-- 25-25a toll not found
				-- post 24-25, after wait days for regular rate
					 */
					transaction.setMatchRefNo(seqNo);
					completeRefNoUpdate(transaction.getLaneTxId(),seqNo);
					completeTnxIns(transaction);
				}
			}

		}

		private void processEntry24Exit25() {

			List<Pending25ATransactions> objectList = new ArrayList<>();

			Plaza entryPlaza = masterDataCache.getPlazaByExtPlazaId(PLAZA_24);
			Plaza exitPlaza = masterDataCache.getPlazaByExtPlazaId(PLAZA_25);
			logger.info("entryPlaza.getPlazaId(): {}, exitPlaza.getPlazaId(): {}", entryPlaza.getPlazaId(), exitPlaza.getPlazaId());
			//objectList = process25ADao.getPending25ATolls(entryPlaza.getPlazaId(), exitPlaza.getPlazaId());

			//all 25A-26+ tolls
			objectList = process25ADao.getPending25ATollsExit26(entryPlaza.getPlazaId(), exitPlaza.getPlazaId());

			//objectList.stream().forEach(e -> findRelatedToll(e));
			for(Pending25ATransactions transaction: objectList) {
				int tollFindStatus = 0;
				List<Pending25ATransactions> relatedList = new ArrayList<>();
				//tollFindStatus = findAllRelatedToll(transaction,PLAZA_25,PLAZA_25A);
				relatedList = findAllRelatedToll(transaction,PLAZA_25,PLAZA_25A);
				tollFindStatus = relatedList.size();
				int seqNo = process25ADao.getCompleteSeq();

				int waitDays;
				if ((transaction.isReciprocityTxn()!=null && transaction.isReciprocityTxn().equalsIgnoreCase("T")) || transaction.getDeviceNo() != null) {
					waitDays = max_wait_days_device; //Need to check these values from PB
				}else {
					waitDays =max_wait_days_plate; //value of this needed
				}

				if (tollFindStatus != 0) {
					/*
					-- -- check for 25a-34 toll
				-- check 25a-34 entry with 25-25a exit
				what does this mean?

					 */
					int tollFindStatus25A24 = 0;
					List<Pending25ATransactions> relatedList25A24 = new ArrayList<>();
					//tollFindStatus = findAllRelatedToll(transaction,PLAZA_25,PLAZA_25A);
					relatedList25A24 = findAllRelatedToll(transaction,PLAZA_25A,PLAZA_34);
					tollFindStatus25A24 = relatedList25A24.size();

					if (tollFindStatus25A24 != 0) {
						/*
						  -- post 24-25 at regular rates
					complete_ref_no_upd (each_txn.lane_tx_id);
					complete_txn_ins

					-- post 25-25a at regular rates
					complete_ref_no_upd (lv_addnl_lane_tx_id);
					complete_txn_ins
						 */
						transaction.setMatchRefNo(seqNo);
						completeRefNoUpdate(transaction.getLaneTxId(),seqNo);
						completeTnxIns(transaction);

						relatedList.get(0).setMatchRefNo(seqNo);
						completeRefNoUpdate(relatedList.get(0).getLaneTxId(),seqNo);
						completeTnxIns(relatedList.get(0));

						if (tollFindStatus25A24 == 1) {
							/*
							 -- post 25a-26+ at regular rates
					   complete_ref_no_upd (lv_lane_tx_id);
					   complete_txn_ins
							 */
							completeRefNoUpdate(relatedList25A24.get(0).getLaneTxId(),seqNo);
							completeTnxIns(relatedList25A24.get(0));
						}else if(relatedList25A24.get(0).getWaitDays() > waitDays) {
							/*
								 -- did not find 25a-34 toll
                    -- after wait days, stitch 24-25 and 25-25a tolls to 24-25a for $0
							 */
							completeRefNoUpdate(relatedList25A24.get(0).getLaneTxId(),seqNo);
							completeTnxIns(relatedList25A24.get(0));

						}

					}else {
						/*
						 * -- 34-25a toll not found
                 			-- after wait days, stitch 25a-24 and 25-24 tolls to 25a-24 for $0
						 */
						if (relatedList.get(0).getWaitDays() > waitDays) {
							/*-- -- post 25a-24+ at regular rate
					complete_ref_no_upd (lv_lane_tx_id);
					complete_txn_ins
							 */
							String laneTxId = transaction.getLaneTxId()+""+relatedList.get(0).getLaneTxId();
							relatedList.get(0).setMatchRefNo(seqNo);
							completeRefNoUpdate(laneTxId,seqNo);
							completeTnxIns(relatedList.get(0));
							
							transaction.setMatchRefNo(seqNo);
							completeRefNoUpdate(transaction.getLaneTxId(),seqNo);
							completeTnxIns(transaction);
						}

					}
				}else if(transaction.getWaitDays() > waitDays) {
					/*
					-- 25-25a toll not found
				-- post 24-25, after wait days for regular rate
					 */
					transaction.setMatchRefNo(seqNo);
					completeRefNoUpdate(transaction.getLaneTxId(),seqNo);
					completeTnxIns(transaction);
				}
			}

		}

		private void processEntry25Exit25A() {

			List<Pending25ATransactions> objectList = new ArrayList<>();

			Plaza entryPlaza = masterDataCache.getPlazaByExtPlazaId(PLAZA_25);
			Plaza exitPlaza = masterDataCache.getPlazaByExtPlazaId(PLAZA_25A);
			logger.info("entryPlaza.getPlazaId(): {}, exitPlaza.getPlazaId(): {}", entryPlaza.getPlazaId(), exitPlaza.getPlazaId());
			//objectList = process25ADao.getPending25ATolls(entryPlaza.getPlazaId(), exitPlaza.getPlazaId());
			
			//all 25A-26+ tolls
			objectList = process25ADao.getPending25ATollsExit26(entryPlaza.getPlazaId(), exitPlaza.getPlazaId());

			//objectList.stream().forEach(e -> findRelatedToll(e));
			for(Pending25ATransactions transaction: objectList) {
				int tollFindStatus = 0;
				List<Pending25ATransactions> relatedList = new ArrayList<>();
				//tollFindStatus = findAllRelatedToll(transaction,PLAZA_25,PLAZA_25A);
				relatedList = findAllRelatedToll(transaction,PLAZA_25A,PLAZA_34);
				tollFindStatus = relatedList.size();
				int seqNo = process25ADao.getCompleteSeq();

				int waitDays;
				if ((transaction.isReciprocityTxn()!=null && transaction.isReciprocityTxn().equalsIgnoreCase("T")) || transaction.getDeviceNo() != null) {
					waitDays = max_wait_days_device; //Need to check these values from PB
				}else {
					waitDays =max_wait_days_plate; //value of this needed
				}

				if (tollFindStatus != 0) {
					/*
					-- post 25-25a at regular rate
				complete_ref_no_upd (each_txn.lane_tx_id);
				complete_txn_ins
				 */
					transaction.setMatchRefNo(seqNo);
					completeRefNoUpdate(transaction.getLaneTxId(),seqNo);
					completeTnxIns(transaction);
					if (tollFindStatus == 1) {
						/*-- -- post 25a-24+ at regular rate
					complete_ref_no_upd (lv_lane_tx_id);
					complete_txn_ins
						 */
						relatedList.get(0).setMatchRefNo(seqNo);
						completeRefNoUpdate(relatedList.get(0).getLaneTxId(),seqNo);
						completeTnxIns(relatedList.get(0));
					}

				}else if (transaction.getWaitDays() > waitDays){
					/*
					-- check for 25-24 toll
				-- check 25-24 entry with 25A-25 exit
					 */
					List<Pending25ATransactions> relatedList2524 = findAllRelatedToll(transaction,PLAZA_25,PLAZA_24);
					int tollFindStatus2524 = relatedList2524.size();
					if (tollFindStatus2524 == 1) {
						/*-- stitch 24-25 and 25-25a to 24-25A for $0
				complete_ref_no_upd (lv_lane_tx_list);
                complete_txn_ins 
						 */
						completeRefNoUpdate(relatedList2524.get(0).getLaneTxId()+','+transaction.getLaneTxId(),seqNo);
						completeTnxIns(relatedList2524.get(0));
						
						completeRefNoUpdate(transaction.getLaneTxId(),seqNo);
						completeTnxIns(transaction);
					}else {
						/*
						 -- post 25-25a for $0
				complete_ref_no_upd (each_txn.lane_tx_id);
                complete_txn_ins
						 
						 */
						transaction.setMatchRefNo(seqNo);
						completeRefNoUpdate(transaction.getLaneTxId(),seqNo);
						completeTnxIns(transaction);
					}
				}


			}
		
			
		}

		private void processEntry25AExit25() {

			List<Pending25ATransactions> objectList = new ArrayList<>();

			Plaza entryPlaza = masterDataCache.getPlazaByExtPlazaId(PLAZA_25A);
			Plaza exitPlaza = masterDataCache.getPlazaByExtPlazaId(PLAZA_25);
			logger.info("entryPlaza.getPlazaId(): {}, exitPlaza.getPlazaId(): {}", entryPlaza.getPlazaId(), exitPlaza.getPlazaId());
			//objectList = process25ADao.getPending25ATolls(entryPlaza.getPlazaId(), exitPlaza.getPlazaId());
			
			//all 25A-26+ tolls
			objectList = process25ADao.getPending25ATollsExit26(entryPlaza.getPlazaId(), exitPlaza.getPlazaId());

			//objectList.stream().forEach(e -> findRelatedToll(e));
			for(Pending25ATransactions transaction: objectList) {
				int tollFindStatus = 0;
				List<Pending25ATransactions> relatedList = new ArrayList<>();
				//tollFindStatus = findAllRelatedToll(transaction,PLAZA_25,PLAZA_25A);
				relatedList = findAllRelatedToll(transaction,PLAZA_34,PLAZA_25A);
				tollFindStatus = relatedList.size();
				int seqNo = process25ADao.getCompleteSeq();

				int waitDays;
				if ((transaction.isReciprocityTxn()!=null && transaction.isReciprocityTxn().equalsIgnoreCase("T")) || transaction.getDeviceNo() != null) {
					waitDays = max_wait_days_device; //Need to check these values from PB
				}else {
					waitDays =max_wait_days_plate; //value of this needed
				}

				if (tollFindStatus != 0) {
					/*
					-- post 25A-25 at regular rate
				complete_ref_no_upd (each_txn.lane_tx_id);
				complete_txn_ins
				 */
					transaction.setMatchRefNo(seqNo);
					completeRefNoUpdate(transaction.getLaneTxId(),seqNo);
					completeTnxIns(transaction);
					if (tollFindStatus == 1) {
						/*-- -- post 26+-25A at regular rate
					complete_ref_no_upd (lv_lane_tx_id);
					complete_txn_ins
						 */
						relatedList.get(0).setMatchRefNo(seqNo);
						completeRefNoUpdate(relatedList.get(0).getLaneTxId(),seqNo);
						completeTnxIns(relatedList.get(0));
					}

				}else if (transaction.getWaitDays() > waitDays){
					/*
					-- check for 25-24 toll
				-- check 25-24 entry with 25A-25 exit
					 */
					List<Pending25ATransactions> relatedList2524 = findAllRelatedToll(transaction,PLAZA_25,PLAZA_24);
					int tollFindStatus2524 = relatedList2524.size();
					
					if (tollFindStatus2524 == 1) {
						/*-- stitch 25A-25 and 25-24 tolls to 25A-24 for $0
					lv_lane_tx_list := each_txn.lane_tx_id ||',' ||lv_lane_tx_id;
					complete_ref_no_upd (lv_lane_tx_list);
					complete_txn_ins
						 */
						completeRefNoUpdate(relatedList2524.get(0).getLaneTxId()+','+transaction.getLaneTxId(),seqNo);
						completeTnxIns(relatedList2524.get(0));
						
						completeRefNoUpdate(transaction.getLaneTxId(),seqNo);
						completeTnxIns(transaction);
					}else {
						/*
						 -- post 25A-25 for $0
					complete_ref_no_upd (each_txn.lane_tx_id);
					complete_txn_ins
						 
						 */
						
						completeRefNoUpdate(transaction.getLaneTxId(),seqNo);
						completeTnxIns(transaction);
					}
				}


			}
		
			
		}

		private void processEntry26Exit25A() {

			List<Pending25ATransactions> objectList = new ArrayList<>();

			Plaza entryPlaza = masterDataCache.getPlazaByExtPlazaId(PLAZA_26);
			Plaza exitPlaza = masterDataCache.getPlazaByExtPlazaId(PLAZA_25A);
			logger.info("entryPlaza.getPlazaId(): {}, exitPlaza.getPlazaId(): {}", entryPlaza.getPlazaId(), exitPlaza.getPlazaId());
			//objectList = process25ADao.getPending25ATolls(entryPlaza.getPlazaId(), exitPlaza.getPlazaId());
			
			//all 26-25A tolls
			objectList = process25ADao.getPending25ATollsExit26(entryPlaza.getPlazaId(), exitPlaza.getPlazaId());

			//objectList.stream().forEach(e -> findRelatedToll(e));
			for(Pending25ATransactions transaction: objectList) {
				int tollFindStatus = 0;
				List<Pending25ATransactions> relatedList = new ArrayList<>();
				//tollFindStatus = findAllRelatedToll(transaction,PLAZA_25,PLAZA_25A);
				relatedList = findAllRelatedToll(transaction,PLAZA_25A,PLAZA_25);
				tollFindStatus = relatedList.size();
				int seqNo = process25ADao.getCompleteSeq();

				int waitDays;
				if ((transaction.isReciprocityTxn()!=null && transaction.isReciprocityTxn().equalsIgnoreCase("T")) || transaction.getDeviceNo() != null) {
					waitDays = max_wait_days_device; //Need to check these values from PB
				}else {
					waitDays =max_wait_days_plate; //value of this needed
				}

				if (tollFindStatus != 0) {
					/*
					-- post both 26+-25A at regular rates
					complete_ref_no_upd (each_txn.lane_tx_id);..
					complete_txn_ins ::: complete_txn_ins: load complete txns to complete table
					 */
					transaction.setMatchRefNo(seqNo);
					completeRefNoUpdate(transaction.getLaneTxId(),seqNo);
					completeTnxIns(transaction);
					if (tollFindStatus == 1) {
						/*-- post 25A-25 at regular rate
						complete_ref_no_upd (lv_lane_tx_id);
						complete_txn_ins 
						 */
						relatedList.get(0).setMatchRefNo(seqNo);
						completeRefNoUpdate(relatedList.get(0).getLaneTxId(),seqNo);
						completeTnxIns(relatedList.get(0));
					}

				}else {
					/*
					 *  -- post 26+-25A at (26-25a at $0 and rest at regular) (after wait days)
             if (each_txn.wait_days > lv_max_wait_days) then
                complete_ref_no_upd (each_txn.lane_tx_id);
                complete_txn_ins
					 */
					if (transaction.getWaitDays() > waitDays) {
						transaction.setMatchRefNo(seqNo);
						completeRefNoUpdate(transaction.getLaneTxId(),seqNo);
						completeTnxIns(transaction);
					}
				}


			}
		
			
		}

		public void processEntry25AExit26() {
			List<Pending25ATransactions> objectList = new ArrayList<>();

			Plaza entryPlaza = masterDataCache.getPlazaByExtPlazaId(PLAZA_25A);
			Plaza exitPlaza = masterDataCache.getPlazaByExtPlazaId(PLAZA_26);
			logger.info("entryPlaza.getPlazaId(): {}, exitPlaza.getPlazaId(): {}", entryPlaza.getPlazaId(), exitPlaza.getPlazaId());
			//objectList = process25ADao.getPending25ATolls(entryPlaza.getPlazaId(), exitPlaza.getPlazaId());
			
			//all 25A-26+ tolls
			objectList = process25ADao.getPending25ATollsExit26(entryPlaza.getPlazaId(), exitPlaza.getPlazaId());

			//objectList.stream().forEach(e -> findRelatedToll(e));
			for(Pending25ATransactions transaction: objectList) {
				int tollFindStatus = 0;
				List<Pending25ATransactions> relatedList = new ArrayList<>();
				//tollFindStatus = findAllRelatedToll(transaction,PLAZA_25,PLAZA_25A);
				relatedList = findAllRelatedToll(transaction,PLAZA_25,PLAZA_25A);
				tollFindStatus = relatedList.size();
				int seqNo = process25ADao.getCompleteSeq();

				int waitDays;
				if ((transaction.isReciprocityTxn()!=null && transaction.isReciprocityTxn().equalsIgnoreCase("T")) || transaction.getDeviceNo() != null) {
					waitDays = max_wait_days_device; //Need to check these values from PB
				}else {
					waitDays =max_wait_days_plate; //value of this needed
				}

				if (tollFindStatus != 0) {
					/*
					-- post both 26+-25A at regular rates
					complete_ref_no_upd (each_txn.lane_tx_id);..
					complete_txn_ins ::: complete_txn_ins: load complete txns to complete table
					 */
					//postTransaction();
					transaction.setMatchRefNo(seqNo);
					completeRefNoUpdate(transaction.getLaneTxId(),seqNo);
					completeTnxIns(transaction);
					if (tollFindStatus == 1) {
						/*-- post 25A-25 at regular rate
						complete_ref_no_upd (lv_lane_tx_id);
						complete_txn_ins 
						 */
						relatedList.get(0).setMatchRefNo(seqNo);
						completeRefNoUpdate(relatedList.get(0).getLaneTxId(),seqNo);
						completeTnxIns(relatedList.get(0));
					}

				}else {
					/*
					 *  -- post 26+-25A at (26-25a at $0 and rest at regular) (after wait days)
             if (each_txn.wait_days > lv_max_wait_days) then
                complete_ref_no_upd (each_txn.lane_tx_id);
                complete_txn_ins
					 */
					if (transaction.getWaitDays() > waitDays) {
						transaction.setMatchRefNo(seqNo);
						completeRefNoUpdate(transaction.getLaneTxId(),seqNo);
						completeTnxIns(transaction);
					}
				}


			}
		}
		
		/*
		 * private Double getMaxTollAmount(AccountTollPostDTO tollPostObj) {
		 * 
		 * try { //Need to write this method in AsyncUtil
		 * logger.info("## Thread {} HOSTNAME: {} in AllSync.getMaxTollAmount..",
		 * Thread.currentThread().getName(), podName); MaxTollDataDto maxTollDataDto =
		 * new MaxTollDataDto(); maxTollDataDto.setEtcAccountId(null);
		 * maxTollDataDto.setLaneTxId(tollPostObj.getLaneTxId());
		 * maxTollDataDto.setEntryPlazaId((tollPostObj.getEntryPlazaId() != null) &&
		 * (tollPostObj.getEntryPlazaId() != 0) ? tollPostObj.getEntryPlazaId() : null);
		 * maxTollDataDto.setTollRevenueType(tollPostObj.getTollRevenueType());
		 * maxTollDataDto.setcLass(tollPostObj.getActualClass());
		 * maxTollDataDto.setPlanId(tollPostObj.getPlanTypeId());
		 * maxTollDataDto.setLaneId(tollPostObj.getLaneId());
		 * maxTollDataDto.setPlazaAgencyId(tollPostObj.getPlazaAgencyId());
		 * maxTollDataDto.setTxTimestamp(tollPostObj.getTxTimestamp().toString().replace
		 * ("T", " ")); maxTollDataDto.setPlazaId(tollPostObj.getPlazaId());
		 * maxTollDataDto.setDataSource("TPMS");
		 * 
		 * HttpHeaders headers = new HttpHeaders();
		 * headers.setContentType(MediaType.APPLICATION_JSON);
		 * logger.info("Setting data for maxToll: {}", maxTollDataDto); Gson gson1 = new
		 * Gson(); HttpEntity<String> entity = new
		 * HttpEntity<String>(gson1.toJson(maxTollDataDto), headers);
		 * 
		 * LocalDateTime from = LocalDateTime.now(); ResponseEntity<String> result =
		 * restTemplate.postForEntity(configVariable.getMaxTollUri(), entity,
		 * String.class); logger.
		 * info("## Time Taken for thread {} HOSTNAME: {} in AllSync.getMaxTollAmount: {} ms"
		 * , Thread.currentThread().getName(), podName, ChronoUnit.MILLIS.between(from,
		 * LocalDateTime.now())); logger.info("MaxToll result: {}", result); if
		 * (result.getStatusCodeValue() == 200) { JsonObject jsonObject = new
		 * Gson().fromJson(result.getBody(), JsonObject.class);
		 * logger.info("Got maxToll response for laneTxId {}. Response: {}",
		 * maxTollDataDto.getLaneTxId(), jsonObject);
		 * 
		 * JsonElement element = jsonObject.get("maxTollAmount"); if (!(element
		 * instanceof JsonNull)) {
		 * logger.info("Max toll amount fetched: {}",element.getAsDouble()); return
		 * element.getAsDouble(); } else { logger.info("Error: {}",
		 * jsonObject.get("message")); } } else { JsonObject jsonObject = new
		 * Gson().fromJson(result.getBody(), JsonObject.class);
		 * logger.info("Got response {}", jsonObject); } } catch (Exception e) {
		 * e.printStackTrace(); logger.info("Error: Exception {} in maxTollApi",
		 * e.getMessage()); } return 0.0;
		 * 
		 * }
		 */

		public void completeRefNoUpdate(String laneTxId, int seqNo) {
			
			/* 
			 * public void completeRefNoUpdate(String laneTxId) {
			 * GET_25A_COMPLETE_SEQ
			1. Get the sequence no
			select tpms.seq_25a_complete_txn.nextval
        	into lv_complete_ref_no
        	from dual;
			 */
			//int seqNo = process25ADao.getCompleteSeq();
			
			process25ADao.updatePendingQueue(laneTxId, seqNo);

			/*
			2. update complete ref num :UPDATE_25A_PENDING_QUEUE
			update t_25a_pending_queue npq
	           set npq.tx_complete_ref_no = lv_complete_ref_no
	         where npq.lane_tx_id in (select regexp_substr(lv_lane_tx_id,'[^,]+', 1, level) from dual
	                                  connect by regexp_substr(lv_lane_tx_id, '[^,]+', 1, level) is not null);
			 */


			/*
		3. update match ref num to identify stitched records
			select lane_tx_id,
				case when row_number() over (partition by spq.tx_complete_ref_no
	                                         order by spq.tx_timestamp desc) = 1
	                 then first_value(spq.tx_extern_ref_no) over (partition by spq.tx_complete_ref_no
	                                                              order by spq.tx_timestamp)
	                 else first_value(spq.tx_extern_ref_no) over (partition by spq.tx_complete_ref_no
	                                                              order by spq.tx_timestamp desc)
				end match_ref_no
			from t_25a_pending_queue spq
			where spq.tx_complete_ref_no = lv_complete_ref_no
			  and spq.lane_tx_id in (select regexp_substr(lv_lane_tx_id,'[^,]+', 1, level) lane_tx_id from dual
									 connect by regexp_substr(lv_lane_tx_id, '[^,]+', 1, level) is not null)
									 
									 lv_complete_ref_no
									 lv_lane_tx_id
									 */
			List<Pending25ATransactions> stitchedRecords = new ArrayList<>();
			stitchedRecords = process25ADao.getMatchedRecords(laneTxId, seqNo);
			for(Pending25ATransactions stitchedRecord:stitchedRecords) {
				process25ADao.updateMatchedRecords(stitchedRecord.getLaneTxId(), seqNo);
			}
			/*
				update t_25a_pending_queue mpq
	             set mpq.matched_tx_extern_ref_no = each_rec.match_ref_no
	           where mpq.lane_tx_id = each_rec.lane_tx_id;

			 */
		}

		public void completeTnxIns(Pending25ATransactions transaction) {

//complete_txn_ins: load complete txns to complete table
			 process25ADao.insertComplleteTransaction(transaction);
			 process25ADao.deletePendingTransaction(transaction);
		}

		public List<Pending25ATransactions> findAllRelatedToll(Pending25ATransactions transaction, String plazaEntry, String plazaExit) {
		//public int findAllRelatedToll(Pending25ATransactions transaction, String plazaEntry, String plazaExit) {
			/*
			 * prc_find_toll:scans through different table to find related tolls return
			 * status 0 for no-find, 1 for pending, -1 for all others
			 */

			Plaza entryPlaza = masterDataCache.getPlazaByExtPlazaId(plazaEntry);
			Plaza exitPlaza = masterDataCache.getPlazaByExtPlazaId(plazaExit);

			List<Pending25ATransactions> relatedList = new ArrayList<>();

			relatedList = process25ADao.getAllRelatedTransactions(entryPlaza.getPlazaId(), exitPlaza.getPlazaId(),"tpms.T_25A_PENDING_QUEUE",transaction.getDeviceNo(),transaction.getPlateNo());
			if(relatedList.size()<1) {
				relatedList = process25ADao.getAllRelatedTransactions(entryPlaza.getPlazaId(), exitPlaza.getPlazaId(),"tpms.T_25A_COMPLETE_TXN",transaction.getDeviceNo(),transaction.getPlateNo());
			}
			if(relatedList.size()<1) {
				//&&transaction.isReciprocityTxn().equalsIgnoreCase("F") ) {
				AgencyInfoVO agency = masterDataCache.getAgency(transaction.getDeviceNo().substring(0, 3));
				logger.info("Agency: {}", agency);
				Long agencyId = agency != null ? agency.getAgencyId() : 0L;
				logger.info("Agency Id: {}", agencyId);

				if(agencyId != 0L) {
					relatedList = process25ADao.getAllRelatedTransactionsTAccountToll(entryPlaza.getPlazaId(), exitPlaza.getPlazaId(),"fpms.T_ACCOUNT_TOLL",agencyId,transaction.getDeviceNo(),transaction.getPlateNo());	
				}else {
					relatedList = process25ADao.getAllRelatedTransactions(entryPlaza.getPlazaId(), exitPlaza.getPlazaId(),"fpms.T_ACCOUNT_TOLL",transaction.getDeviceNo(),transaction.getPlateNo());	
				}	
			}
			/*
			if(relatedList.size()<1 &&transaction.isReciprocityTxn().equalsIgnoreCase("T") ) {
				relatedList = process25ADao.getAllRelatedTransactions(entryPlaza.getPlazaId(), exitPlaza.getPlazaId(),"fpms.T_ACCOUNT_TOLL");			
			}
			 */
			if(relatedList.size()<1) {
				relatedList = process25ADao.getAllRelatedTransactions(entryPlaza.getPlazaId(), exitPlaza.getPlazaId(),"tpms.T_AGENCY_TX_PENDING",transaction.getDeviceNo(),transaction.getPlateNo());			
			}
			if(relatedList.size()<1) {
				relatedList = process25ADao.getAllRelatedTransactions(entryPlaza.getPlazaId(), exitPlaza.getPlazaId(),"tpms.T_UNMATCHED_ENTRY_TX",transaction.getDeviceNo(),transaction.getPlateNo());			
			}
			if(relatedList.size()<1) {
				relatedList = process25ADao.getAllRelatedTransactions(entryPlaza.getPlazaId(), exitPlaza.getPlazaId(),"tpms.T_UNMATCHED_EXIT_TX",transaction.getDeviceNo(),transaction.getPlateNo());			
			}
			/*
			 * if(relatedList.size()<1) { 
			 * relatedList = process25ADao.getAllRelatedTransactions(entryPlaza.getPlazaId(),exitPlaza.getPlazaId(),"IBTS.T_DMV_REQUEST",transaction.getDeviceNo(),transaction.getPlateNo()); }
			 */
			if(relatedList.size()<1) {
				//Need to connect with IBTS for this
				logger.info("Get Data from IBTS.t_rov_queue");
				//relatedList = process25ADao.getAllRelatedTransactions(entryPlaza.getPlazaId(), exitPlaza.getPlazaId(),"IBTS.t_rov_queue",transaction.getDeviceNo(),transaction.getPlateNo());	//t_violator_info 		
			}

			/*
			if(relatedList.size()>0) {
				return 1;
			}

			return 0;
			*/
			return relatedList;
		}


}
