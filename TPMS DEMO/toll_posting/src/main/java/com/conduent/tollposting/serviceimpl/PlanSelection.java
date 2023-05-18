package com.conduent.tollposting.serviceimpl;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.conduent.tollposting.constant.AccountType;
import com.conduent.tollposting.constant.Constants;
import com.conduent.tollposting.constant.PlanType;
import com.conduent.tollposting.constant.TxStatus;
import com.conduent.tollposting.dto.AccountInfoDTO;
import com.conduent.tollposting.dto.AccountTollPostDTO;
import com.conduent.tollposting.dto.EtcTransaction;
import com.conduent.tollposting.exception.NoRecordFoundException;
import com.conduent.tollposting.model.AccountPlan;
import com.conduent.tollposting.model.AccountPlanSuspension;
import com.conduent.tollposting.model.Agency;
import com.conduent.tollposting.model.PlanPolicy;
import com.conduent.tollposting.model.PlazaPlanLimit;
import com.conduent.tollposting.model.TripHistory;
import com.conduent.tollposting.utility.MasterCache;

@Component
public class PlanSelection
{
	private static final Logger logger = LoggerFactory.getLogger(PlanSelection.class);

	@Autowired
	private MasterCache masterCache;

	@Autowired
	private AccountPlanSuspensionService accountPlanSuspensionService;

	@Autowired
	private PlazaPlanLimitService plazaPlanLimitService;

	@Autowired
	private TripHistoryService tripHistoryService;

	@Autowired
	private TollCalculation tollCalculation;


	public void planSelectionPAAgency(EtcTransaction etcTran,AccountInfoDTO accountInfo,AccountTollPostDTO tollPost)
	{
		AccountPlan accountPlan=null;
		Integer planType=tollPost.getPlanTypeId();
		if(planType!=null && planType.intValue()==0)
		{
			selectDefaultPlan(etcTran, planType, accountInfo, tollPost);
			
		}
		else if(planType!=null && planType.intValue()==999)
		{
			logger.info("PA Agency planType is 999 for laneTxId {} checking non-revenue plan",tollPost.getLaneTxId());
			//findAppropriatePlan(accountInfo,tollPost);
			findNRPlan(accountInfo,tollPost,etcTran);
			//if plan not found
			if(accountInfo.getSelectedPlan()==null) 
			{
				logger.info("Non revenue plan not found checking in accountPlanList {}",tollPost.getLaneTxId());
				for(int i=0;i<accountInfo.getAccountPlanList().size();i++ )
				{
					accountPlan=accountInfo.getAccountPlanList().get(i);
					if(accountPlan.getPlanType().intValue()==tollPost.getPlanTypeId().intValue())
					{
						accountInfo.setSelectedPlan(accountPlan);
						break;
					}
				}
				if(accountInfo.getSelectedPlan()!=null)
				{

				}
			}
		}
		else if(etcTran.getAccountType().intValue()==AccountType.STVA.getCode() &&
				((tollPost.getTxTypeInd().equals("V") && tollPost.getTxSubtypeInd().equals("I")) ||
				(tollPost.getTxTypeInd().equals("V") && tollPost.getTxSubtypeInd().equals("T")) ||
				(tollPost.getTxTypeInd().equals("X") && tollPost.getTxSubtypeInd().equals("I")) ||
				(tollPost.getTxTypeInd().equals("V") && tollPost.getTxSubtypeInd().equals("V"))))
		{
			findAppropriatePlan(accountInfo,tollPost);
			if(accountInfo.getSelectedPlan()!=null)
			{
				accountInfo.getSelectedPlan().setDiscountAmount(0.0);
			}
			else 
			{
				AccountPlan accountPlanSelected=new AccountPlan();
				int passiPlan=-1;
				int stdPlan=-1;
				int unregPlan=-1;
				int stvaPlan=-1;
				int grnPlan=-1;
				for(int i=0;i<accountInfo.getAccountPlanList().size();i++ )
				{
					accountPlan=accountInfo.getAccountPlanList().get(i);
					if(accountPlan.getPlanType()==PlanType.PASI.getCode())
					{
						if(etcTran.getDiscountedAmount().intValue()==0) //if disc1< disc2 or disc1==0
						{
							continue;
						}
						else
						{
							passiPlan=i;
						}
					}
					if(accountPlan.getPlanType()==PlanType.PAGREEN.getCode())
					{
						if((etcTran.getTxTypeInd().equals("V") && etcTran.getTxSubtypeInd().equals("I")) || (etcTran.getTxTypeInd().equals("X") && etcTran.getTxSubtypeInd().equals("I")))
						{
							continue;
						}
						if(etcTran.getDiscountedAmount2().intValue()==0)
						{
							continue;
						}
						grnPlan=i;
					}
					if(accountPlan.getPlanType().intValue()==PlanType.STANDARD.getCode() || accountPlan.getPlanType().intValue()==PlanType.BUSINESS.getCode()) //:TODO PRIV_STD,BUSI_STD
					{
						stdPlan=i;
					}
					if(accountPlan.getPlanType().intValue()==PlanType.STANDARD.getCode()) //:TODO UNREG_STD
					{
						unregPlan=i;
					}
					if(accountPlan.getPlanType().intValue()==PlanType.STVA.getCode()) //:TODO UNREG_STD
					{
						stvaPlan=i;
					}
				}
				if(grnPlan>0 && passiPlan>0)
				{
					if(etcTran.getDiscountedAmount()<etcTran.getDiscountedAmount2() || etcTran.getDiscountedAmount2().intValue()==0)
					{
						accountPlanSelected.setDiscountAmount(etcTran.getDiscountedAmount());
						accountPlanSelected.setPlanType(accountInfo.getAccountPlanList().get(passiPlan).getPlanType());
					}
					else
					{
						accountPlanSelected.setDiscountAmount(etcTran.getDiscountedAmount2());
						accountPlanSelected.setPlanType(accountInfo.getAccountPlanList().get(grnPlan).getPlanType());
					}
				}
				else if(passiPlan>0)
				{
					accountPlanSelected.setDiscountAmount(etcTran.getDiscountedAmount());
					accountPlanSelected.setPlanType(accountInfo.getAccountPlanList().get(passiPlan).getPlanType());
				}
				else if(grnPlan>0)
				{
					accountPlanSelected.setDiscountAmount(etcTran.getDiscountedAmount2());
					accountPlanSelected.setPlanType(accountInfo.getAccountPlanList().get(grnPlan).getPlanType());
				}
				else if(stdPlan>0)
				{
					if(tollPost.getAccountType().intValue()==AccountType.BUSINESS.getCode() || tollPost.getAccountType().intValue()==AccountType.COMMERCIAL.getCode())
					{
						accountPlanSelected.setPlanType(PlanType.BUSINESS.getCode());
						accountPlanSelected.setDiscountAmount(tollPost.getEtcFareAmount());
					}
					if(tollPost.getAccountType().intValue()==AccountType.PRIVATE.getCode() || tollPost.getAccountType().intValue()==AccountType.NONREVENUE.getCode())
					{
						accountPlanSelected.setPlanType(PlanType.STANDARD.getCode());
						accountPlanSelected.setDiscountAmount(tollPost.getEtcFareAmount());
					}
				}
				else if(unregPlan>0)
				{
					accountPlanSelected.setDiscountAmount(etcTran.getVideoFareAmount());
					accountPlanSelected.setPlanType(accountInfo.getAccountPlanList().get(unregPlan).getPlanType());
				}
				else if(stvaPlan>0)
				{
					accountPlanSelected.setDiscountAmount(etcTran.getVideoFareAmount());
					accountPlanSelected.setPlanType(accountInfo.getAccountPlanList().get(stvaPlan).getPlanType());
				}
				else if((tollPost.getAccountType().intValue()==AccountType.PVIDEOUNREG.getCode() || tollPost.getAccountType().intValue()==AccountType.BVIDEOUNREG.getCode())
						&& tollPost.getLprEnabled().equals("Y"))
				{
					accountPlanSelected.setPlanType(PlanType.VIDEOUNREG.getCode());
					accountPlanSelected.setDiscountAmount(etcTran.getVideoFareAmount());
				}
				PlanPolicy planPolicy=masterCache.getPlanPolicy(accountPlanSelected.getPlanType());
				accountPlanSelected.setCommuteFlag(planPolicy.getIsCommute().equals("T")?"T":"F");
				accountPlanSelected.setTripPerTrx(1);
				accountInfo.setSelectedPlan(accountPlanSelected);
			}			
		}
		else { // Us 7366 PA etc posting plan logic
				for (AccountPlan accountPlanObj : accountInfo.getAccountPlanList()) 
				{
					if(accountPlanObj.getPlanType() == planType) 
					{ 																				// selected plan is associated with the account
						PlanPolicy planPolicy=masterCache.getPlanPolicy(accountPlanObj.getPlanType());
						
						if(planPolicy.getIsDeviceSpecific().equalsIgnoreCase("T")) {
							if(accountPlanObj.getDeviceNo().equals(tollPost.getDeviceNo()))	
							{
								setSelectedPlanObj(accountPlanObj, planPolicy, etcTran, tollPost, accountInfo);
								break;
							}else {
								selectDefaultPlan(etcTran, planType, accountInfo, tollPost);
								break;
							}
						}else {
							setSelectedPlanObj(accountPlanObj, planPolicy, etcTran, tollPost, accountInfo);
							break;
							
						}
					}
					else
					{
						findNRPlan(accountInfo, tollPost, etcTran);
					}
				}
				
				if(accountInfo.getSelectedPlanType() == null) {
					selectDefaultPlan(etcTran, planType, accountInfo, tollPost);
				}
				
			}
		logger.info("Selected plan for EtcAccountId {} is {} for lane_tx_id {} ",etcTran.getEtcAccountId(), accountInfo.getSelectedPlan(),tollPost.getLaneTxId());
	}
	

	private void setSelectedPlanObj(AccountPlan accountPlanObj, PlanPolicy planPolicy, EtcTransaction etcTran,
			AccountTollPostDTO tollPost, AccountInfoDTO accountInfo) {

		accountPlanObj.setCommuteFlag(planPolicy.getIsCommute() != null? planPolicy.getIsCommute():"F");
		accountPlanObj.setDiscountAmount(accountPlanObj.getDiscountAmount()!=null?accountPlanObj.getDiscountAmount():0.0); //?
		accountPlanObj.setTripPerTrx(1);
		accountPlanObj.setFullFareAmount(etcTran.getEtcFareAmount());
		accountPlanObj.setEtcAccountId(tollPost.getEtcAccountId());
		accountPlanObj.setExtraAxleCharge(accountPlanObj.getExtraAxleCharge()!=null?accountPlanObj.getExtraAxleCharge():0.0);
		buildPostedFareAmount(accountPlanObj,tollPost,etcTran); 
		accountPlanObj.setDiscountAmount(tollPost.getPostedFareAmount());
		accountInfo.setSelectedPlan(accountPlanObj);
		accountInfo.setSelectedPlanType(accountPlanObj.getPlanType());
		accountInfo.setSelectedPlanPolicy(planPolicy);
		tollPost.setAccountInfoDTO(accountInfo);
		
	}
	

	public void selectDefaultPlan(EtcTransaction etcTran, Integer planType, AccountInfoDTO accountInfo, AccountTollPostDTO tollPost) {

		logger.info("PlanType is 0 for laneTxId {} and plaza_agency_id {}",tollPost.getLaneTxId(),tollPost.getPlazaAgencyId());
		if(etcTran.getAccountType().intValue()==AccountType.BUSINESS.getCode() || etcTran.getAccountType().intValue()==AccountType.COMMERCIAL.getCode())
		{
			accountInfo.setSelectedPlanType(PlanType.BUSINESS.getCode());
		}
		else if(etcTran.getAccountType().intValue()==AccountType.PRIVATE.getCode())
		{
			accountInfo.setSelectedPlanType(PlanType.STANDARD.getCode());
		}
		else if(etcTran.getAccountType().intValue()==AccountType.STVA.getCode()) 
		{
			accountInfo.setSelectedPlanType(PlanType.STVA.getCode());
		}
		else if(etcTran.getAccountType().intValue()==AccountType.PRVA.getCode()) 
		{
			accountInfo.setSelectedPlanType(PlanType.PRVA.getCode());
		}
		else if(etcTran.getAccountType().intValue()==AccountType.BRVA.getCode()) 
		{
			accountInfo.setSelectedPlanType(PlanType.BRVA.getCode());
		}
		else {
			accountInfo.setSelectedPlanType(PlanType.VIDEOUNREG.getCode());
		}
		logger.info("PlanType is 0 for laneTxId {} so selected plan is {}",tollPost.getLaneTxId(),PlanType.getByCode(accountInfo.getSelectedPlanType()));
		
	}


	//commenting as PB told to the right logic
	private void buildPostedFareAmount(AccountPlan accountPlanObj, AccountTollPostDTO tollPost, EtcTransaction etcTran) {
		
		if(etcTran.getDiscountedAmount()==null) {
			etcTran.setDiscountedAmount(0.0);
		}
		if(etcTran.getDiscountedAmount2()==null) {
			etcTran.setDiscountedAmount2(0.0);
		}
		logger.info("DiscountedAmount: {} DiscountedAmount2: {}",etcTran.getDiscountedAmount(), etcTran.getDiscountedAmount2());
		/**	logic for 2 discounts **/
		if(etcTran.getDiscountedAmount() != 0 && etcTran.getDiscountedAmount2() != 0) {
			if(etcTran.getDiscountedAmount() < etcTran.getDiscountedAmount2())
			{
				tollPost.setPostedFareAmount(etcTran.getDiscountedAmount());
			}
			else {
				tollPost.setPostedFareAmount(etcTran.getDiscountedAmount2());
			}
		}else if(etcTran.getDiscountedAmount() == 0 && etcTran.getDiscountedAmount2() != 0) {
			tollPost.setPostedFareAmount(etcTran.getDiscountedAmount2());
		}
		else if(etcTran.getDiscountedAmount() != 0 && etcTran.getDiscountedAmount2() == 0) {
			tollPost.setPostedFareAmount(etcTran.getDiscountedAmount());
		}else if(etcTran.getDiscountedAmount() == 0 && etcTran.getDiscountedAmount2() == 0) {
			if(tollPost.getTollRevenueType().intValue()==1) {
				tollPost.setPostedFareAmount(etcTran.getEtcFareAmount());
			}
			if(tollPost.getTollRevenueType().intValue()==9 || tollPost.getTollRevenueType().intValue()==60) {
				tollPost.setPostedFareAmount(etcTran.getVideoFareAmount());
			}
		}else {
			tollPost.setPostedFareAmount(etcTran.getPostedFareAmount());
		}
		
	}	
	
	//	logic for 3 discounts not required --PB
		private void buildPostedFareAmountPA(AccountPlan accountPlanObj, AccountTollPostDTO tollPost, EtcTransaction etcTran) {
			
		//logic for 3 discounts 
		if(etcTran.getEtcFareAmount() != 0 && etcTran.getDiscountedAmount() != 0 && etcTran.getDiscountedAmount2() != 0)
		{
			if(etcTran.getEtcFareAmount() < etcTran.getDiscountedAmount() && etcTran.getEtcFareAmount() < etcTran.getDiscountedAmount2() )
			{	
				if(tollPost.getAccountType()==AccountType.BUSINESS.getCode() || tollPost.getAccountType()==AccountType.COMMERCIAL.getCode())
				{
					tollPost.setPostedFareAmount(etcTran.getEtcFareAmount());
				}
			}else if(etcTran.getDiscountedAmount() < etcTran.getEtcFareAmount() && etcTran.getDiscountedAmount() < etcTran.getDiscountedAmount2())
			{
				tollPost.setPostedFareAmount(etcTran.getDiscountedAmount());
			}else {
				tollPost.setPostedFareAmount(etcTran.getDiscountedAmount2());
			}
		}else if(etcTran.getEtcFareAmount() != 0 && etcTran.getDiscountedAmount() == 0 && etcTran.getDiscountedAmount2() == 0) {
			tollPost.setPostedFareAmount(etcTran.getEtcFareAmount());
		}else if(etcTran.getEtcFareAmount() == 0 && etcTran.getDiscountedAmount() != 0 && etcTran.getDiscountedAmount2() == 0) {
			tollPost.setPostedFareAmount(etcTran.getDiscountedAmount());
		}else if(etcTran.getEtcFareAmount() == 0 && etcTran.getDiscountedAmount() == 0 && etcTran.getDiscountedAmount2() != 0) {
			tollPost.setPostedFareAmount(etcTran.getDiscountedAmount2());
		}else if (etcTran.getEtcFareAmount() == 0 && (etcTran.getDiscountedAmount() != 0 && etcTran.getDiscountedAmount2() != 0)) {
			if(etcTran.getDiscountedAmount() < etcTran.getDiscountedAmount2() )
			{
				tollPost.setPostedFareAmount(etcTran.getDiscountedAmount());
			}else {
				tollPost.setPostedFareAmount(etcTran.getDiscountedAmount2());
			}
		}else if (etcTran.getDiscountedAmount() == 0 && (etcTran.getEtcFareAmount() != 0 && etcTran.getDiscountedAmount2() != 0)) {
			if(etcTran.getEtcFareAmount() < etcTran.getDiscountedAmount2() )
			{
				tollPost.setPostedFareAmount(etcTran.getEtcFareAmount());
			}else {
				tollPost.setPostedFareAmount(etcTran.getDiscountedAmount2());
			}
		}else if (etcTran.getDiscountedAmount2() == 0 && (etcTran.getDiscountedAmount() != 0 && etcTran.getEtcFareAmount() != 0)) {
			if(etcTran.getDiscountedAmount() < etcTran.getEtcFareAmount() )
			{
				tollPost.setPostedFareAmount(etcTran.getDiscountedAmount());
			}else {
				tollPost.setPostedFareAmount(etcTran.getEtcFareAmount());
			}
		}else {
			tollPost.setPostedFareAmount(etcTran.getPostedFareAmount());
		}
		
	}

		private void findNRPlan(AccountInfoDTO accountInfo, AccountTollPostDTO tollPost, EtcTransaction etcTran) {
			for(AccountPlan accountPlan:accountInfo.getAccountPlanList())
			{
				Integer planType=accountPlan.getPlanType();
				//Integer planTripCount=accountPlan.getTripCount();
				PlanPolicy planPolicy=masterCache.getPlanPolicy(planType);
				
				if(planPolicy.getIsRevenuePlan().equalsIgnoreCase("F"))
				{
					if(planPolicy.getIsDeviceSpecific().equalsIgnoreCase("T")) 
					{
						if(accountPlan.getDeviceNo().equals(tollPost.getDeviceNo()))	
						{
							tollPost.setPostedFareAmount(0.0);
							tollPost.setPlanTypeId(accountPlan.getPlanType());
							accountPlan.setActiveFlag("T");
							accountPlan.setDiscountAmount(etcTran.getDiscountedAmount());
							accountInfo.setSelectedPlan(accountPlan);
							accountInfo.setSelectedPlanPolicy(planPolicy);
							accountInfo.setSelectedPlanType(accountPlan.getPlanType());
							
						//	accountPlan.setPriceScheduleId(etcTran.getPriceScheduleId()); in NR??
							return;
						}
					}
					else
					{
						tollPost.setPostedFareAmount(0.0);
						tollPost.setPlanTypeId(accountPlan.getPlanType());
						accountPlan.setActiveFlag("T");
						accountPlan.setDiscountAmount(etcTran.getDiscountedAmount());
						accountInfo.setSelectedPlan(accountPlan);
						accountInfo.setSelectedPlanPolicy(planPolicy);
						accountInfo.setSelectedPlanType(accountPlan.getPlanType());
					}
				}
				if(planPolicy.getIsRevenuePlan().equalsIgnoreCase("T"))
				{
					continue;
				}
			
		}
	}
		
		
		
		
	public void findAppropriatePlan(AccountInfoDTO accountInfo,AccountTollPostDTO tollPost)
	{
		for(AccountPlan accountPlan:accountInfo.getAccountPlanList())
		{
			Integer planType=accountPlan.getPlanType();
			Integer planTripCount=accountPlan.getTripCount();
			PlanPolicy planPolicy=masterCache.getPlanPolicy(planType);
			if(planPolicy.getMapAgencyId().intValue()!=Constants.PA_AGENCY_ID.intValue())
			{
				continue;
			}
			if(planPolicy.getIsRevenuePlan().equalsIgnoreCase("T"))
			{
				continue;
			}else if(planPolicy.getIsRevenuePlan().equalsIgnoreCase("F")){
				accountPlan.setActiveFlag("T");
				accountInfo.setSelectedPlan(accountPlan);
				accountInfo.setSelectedPlanPolicy(planPolicy);
				tollPost.setPlanTypeId(accountPlan.getPlanType());
				tollPost.setPostedFareAmount(0.0);
				//return;
				
			}
			if(planPolicy.getIsDeviceSpecific().equalsIgnoreCase("T") && !accountPlan.getDeviceNo().equalsIgnoreCase(tollPost.getDeviceNo()))
			{
				continue;
			}
			if(tollPost.getTxTypeInd().equalsIgnoreCase("V"))
			{
				if(planType.intValue()!=PlanType.PANRPL.getCode())
				{
					continue;
				}
				else
				{
					if(tollPost.getAccountAgencyId().intValue()!=Constants.PA_AGENCY_ID.intValue() || tollPost.getPlazaAgencyId().intValue()!=Constants.PA_AGENCY_ID.intValue()) // :TODO 
					{
						continue;
					}
				}
			}
			if(planTripCount>0 && planPolicy.getMaxTrips().intValue()!=planTripCount.intValue())
			{

				planPolicy.setIsAnnualPlan("Y");  //:TODO  Doubt
				planPolicy.setMaxTrips(planTripCount); //:TODO Doubt
			}

			//If plan suspended Ignore the plan
			if(isPlanSuspensed(planPolicy,accountPlan,accountInfo,tollPost)==Boolean.TRUE)
			{
				continue;
			}

			if(planPolicy.getIsCommute().equalsIgnoreCase("T") && planPolicy.getRenewLimitInd().equalsIgnoreCase("F") && accountPlan.getHistoryFlag().equalsIgnoreCase("T"))
			{
				List<String> flagList=new ArrayList<String>();
				flagList.add("F");
				LocalDateTime fromTime = LocalDateTime.now();
				List<TripHistory> tripHistory= tripHistoryService.getTripHistory(accountPlan.getApdId(), tollPost.getTxDate(), flagList);
				logger.debug("##SQL Time Taken for thread {} HOSTNAME: {} in tripHistoryList in findPlan: {} ms", Thread.currentThread().getName(),System.getenv("HOSTNAME"), ChronoUnit.MILLIS.between(fromTime, LocalDateTime.now()));
				Long diffDays=ChronoUnit.DAYS.between(tollPost.getTxDate(),accountPlan.getPlanEndDate());
				if(diffDays>0 && (tripHistory==null || tripHistory.isEmpty()))
				{
					continue;
				}
			}

			if(planPolicy.getPlazaLimited().equalsIgnoreCase("T"))
			{
				if(tollPost.getTollSystemType().equalsIgnoreCase("C") || tollPost.getTollSystemType().equalsIgnoreCase("X") || tollPost.getTollSystemType().equalsIgnoreCase("T"))
				{
					PlazaPlanLimit plazaPlanLimit=plazaPlanLimitService.getPlazaPlazaPlanLimit(tollPost.getEntryPlazaId(), tollPost.getPlazaId(),accountPlan.getApdId());
					if(plazaPlanLimit==null)
					{
						continue;
					}
				}
				else
				{
					PlazaPlanLimit plazaPlanLimit=plazaPlanLimitService.getPlazaPlanLimit(tollPost.getPlazaId(),0,accountPlan.getApdId());

					if(plazaPlanLimit==null)
					{
						continue;
					}

					plazaPlanLimit=plazaPlanLimitService.getPlazaPlanLimit(0,tollPost.getPlazaId(),accountPlan.getApdId());
					if(plazaPlanLimit==null)
					{
						continue;
					}
				}
			}
			if(accountInfo.getAccountType().intValue()==AccountType.COMMERCIAL.getCode() && planPolicy.getIsPostPaid().equalsIgnoreCase("T")) // :
			{
				if(planPolicy.getIsTripLimited().equalsIgnoreCase("T"))
				{

				}
			}
		}

		/** END PlanList Loop**/
		AccountPlan accountPlan=tollCalculation.accountPlanSection(accountInfo.getAccountPlanList());
		if(accountPlan!=null)
		{
			PlanPolicy planPolicy=masterCache.getPlanPolicy(accountPlan.getPlanType());
			accountPlan.setCommuteFlag(planPolicy.getIsCommute().equalsIgnoreCase("T")?"T":"F");
			accountInfo.setSelectedPlan(accountPlan);
			accountPlan.setTripPerTrx(1);
			/** Update suspended status in database if selected plan is suspended**/
			updateIfSuspendedPlan(accountInfo,tollPost);
		}
	}

	/** Plan selection logic for NYSTA/MTA 
	 * @throws NoRecordFoundException **/
	public void planSelectionLogic(EtcTransaction etcTrx,AccountInfoDTO accountInfo,AccountTollPostDTO toll) throws NoRecordFoundException
	{
			toll.setIsDiscountable("T"); 
			Agency plazaAgency=masterCache.getAgency(toll.getPlazaAgencyId());

			Integer nonRevenuePlan=0;

			if((toll.getTxTypeInd().equals("E") || toll.getTxTypeInd().equals("V"))
					&& toll.getAccountAgencyId().intValue()==toll.getPlazaAgencyId().intValue()
					&& (toll.getAccountType().intValue()==AccountType.PRIVATE.getCode() || 
					toll.getAccountType().intValue()==AccountType.BUSINESS.getCode() || 
					toll.getAccountType().intValue()==AccountType.COMMERCIAL.getCode() || 
					toll.getAccountType().intValue()==AccountType.NONREVENUE.getCode()))
			{
				nonRevenuePlan=1;
				logger.info("nonRevenue plan will be applicable for laneTxId {}",toll.getLaneTxId()); 
			}
			else if(!(toll.getAccountAgencyId()==toll.getPlazaAgencyId()) && toll.getPlazaAgencyId().equals(4))
			{
				nonRevenuePlan=1;
				logger.info("nonRevenue plan will be applicable for NYSBA laneTxId {}",toll.getLaneTxId());
			}
		
			/**bug 73241, only for E/B**/
		/*	if(!toll.getTollSystemType().equals("C") && !toll.getTollSystemType().equals("T") && !toll.getTollSystemType().equals("X")
					&& toll.getPlazaAgencyId().intValue()==Constants.AGENCY_NY.intValue())
			{
				
				if(toll.getTollSystemType().equals("E") && toll.getTxTypeInd().equals("D"))
				{
					toll.setEntryPlazaId(toll.getPlazaId());  //TBD
				}else if(toll.getTollSystemType().equals("B") ) {
					logger.info("TollSystemType is:{}",toll.getTollSystemType());
				}
			}
	*/
			Integer isRrtPlan=0;
			Integer isRntPlan=0; 
			Integer isVrtPlan=0; 
			Integer isVnbcpPlan=0; 
			Integer pmtppflag=0;

			for(int i=0;i<accountInfo.getAccountPlanList().size();i++)
			{
				AccountPlan accountPlan=accountInfo.getAccountPlanList().get(i);
				accountPlan.setActiveFlag("F"); //For best plan selection 
				if(!toll.getAccountType().equals(AccountType.PRIVATE.getCode()) && (accountPlan.getPlanType().equals(PlanType.RNT.getCode()) || accountPlan.getPlanType().equals(PlanType.RRT.getCode()) || accountPlan.getPlanType().equals(PlanType.VRT.getCode())))
				{
					logger.info("Skip plan {} for laneTxId {} condition 1 failed.",accountPlan.getPlanType(),toll.getLaneTxId());
					continue;
				}
				if((toll.getAccountType().intValue()!=AccountType.BUSINESS.getCode() && toll.getAccountType().intValue()!=AccountType.NONREVENUE.getCode()) && accountPlan.getPlanType().intValue()==PlanType.TBNRFP.getCode())
				{
					logger.info("Skip plan {} for laneTxId {} condition 2 failed.",accountPlan.getPlanType(),toll.getLaneTxId());
					continue;
				}

				if(toll.getPlazaAgencyId().intValue()==Constants.AGENCY_PA.intValue() && 
						accountPlan.getPlanType().intValue()==PlanType.PASI.getCode() &&
						toll.getTxTypeInd().equalsIgnoreCase("X") &&
						toll.getBufferReadFlag().equalsIgnoreCase("P"))
				{
					logger.info("Skip plan {} for laneTxId {} condition 3 failed.",accountPlan.getPlanType(),toll.getLaneTxId());
					continue;
				}
				if(accountPlan.getApdId().equals("0"))
				{
					logger.info("Skip plan {} for laneTxId {} account.ApdId is zero",accountPlan.getPlanType(),toll.getLaneTxId());
					continue;
				}
				/*Check plaza and device specific plans*/
				PlanPolicy planPolicy=masterCache.getPlanPolicy(accountPlan.getPlanType()); 
				logger.info("IsRevnue: {} for planType: {}", planPolicy.getIsRevenuePlan(), planPolicy.getPlanType());
				if(nonRevenuePlan==1 && planPolicy.getIsRevenuePlan().equalsIgnoreCase("F") && planPolicy.getMapAgencyId().intValue()==toll.getPlazaAgencyId().intValue())
				{
					logger.info("Got non-revenue plan for laneTxId {} plan_type {} ",accountPlan.getPlanType(),toll.getLaneTxId());
					tollCalculation.tollScheduleApi(accountInfo, toll, accountPlan);
					if(accountPlan.getActiveFlag()!=null && accountPlan.getActiveFlag().equals("T")) 	//added this condition for Bug 18912
					{
						toll.setPostedFareAmount(accountPlan.getDiscountAmount());
						accountPlan.setCommuteFlag(planPolicy.getIsCommute().equalsIgnoreCase("T")?"T":"F");
						accountInfo.setSelectedPlan(accountPlan);
						accountInfo.setSelectedPlanPolicy(planPolicy);
						return;
					}
				}
				else if(planPolicy.getIsRevenuePlan().equalsIgnoreCase("F"))
				{
					logger.info("Skip plan {} for laneTxId {} IsRevenuePlan F",accountPlan.getPlanType(),toll.getLaneTxId());
					continue;
				}
				if(accountPlan.getTripCount()>0 && planPolicy.getMaxTrips()>=accountPlan.getTripCount())
				{
					planPolicy.setIsAnnualPlan("Y");  
					planPolicy.setMaxTrips(accountPlan.getTripCount());
				}
				if((toll.getPlazaAgencyId().equals(Constants.AGENCY_MICC) || toll.getPlazaAgencyId().equals(Constants.AGENCY_METL)) && accountPlan.getPlanType()==221 )
				{
					pmtppflag =accountPlan.getDeviceNo().equals(toll.getDeviceNo())?0:1;
					if(!planPolicy.getIsRevenuePlan().equalsIgnoreCase("F"))
					{
						logger.info("Skip plan {} for laneTxId {} condition 6 failed.",accountPlan.getPlanType(),toll.getLaneTxId());
						continue;
					}
				}
				if((toll.getPlazaAgencyId().equals(Constants.AGENCY_NCTA) || toll.getLprEnabled().equals("Y")) 
						&& !planPolicy.getIsPostPaid().equalsIgnoreCase("T")
						&& toll.getPostPaidFlag()!=null && !toll.getPostPaidFlag().equals("T")) //:TODD from where will get toll.getPostPaid flag
				{
					logger.info("Skip plan {} for laneTxId {} condition {} failed.",accountPlan.getPlanType(),toll.getLaneTxId(),7);
					continue;
				}

				if(isPlanSuspensed(planPolicy,accountPlan,accountInfo,toll)==Boolean.TRUE)
				{
					logger.info("Skip plan {} for laneTxId {} planSuspended condition failed.",accountPlan.getPlanType(),toll.getLaneTxId());
					continue;
				}

				if(planPolicy.getIsCommute().equals("T") && planPolicy.getRenewLimitInd().equals("F") && planPolicy.getHistoryFlag()!=null && planPolicy.getHistoryFlag().equals("T")) //:TODO historyFlag not there in db
				{
					List<String> reconFlag=new ArrayList<String>();
					reconFlag.add("F");
					LocalDateTime fromTime = LocalDateTime.now();
					List<TripHistory> tripHistory=tripHistoryService.getTripHistory(accountPlan.getApdId(), toll.getTxDate(),reconFlag);
					logger.debug("##SQL Time Taken for thread {} HOSTNAME: {} in tripHistoryList in Plan: {} ms", Thread.currentThread().getName(),System.getenv("HOSTNAME"), ChronoUnit.MILLIS.between(fromTime, LocalDateTime.now()));
					if(tripHistory!=null && !tripHistory.isEmpty())
					{
						Long txAge = ChronoUnit.DAYS.between(toll.getTxDate(),tripHistory.get(0).getTripEndDate());
						if (txAge > 0 )	{
							logger.info("Skip plan {} for laneTxId {} commute flag is T and txAge is {}",accountPlan.getPlanType(),toll.getLaneTxId(),txAge);
							continue;
						}
					}
				}
				if(accountPlan.getPlanType()==PlanType.DELDOT.getCode() && toll.getLaneMode()!=8) 	
				{ 
					logger.info("Skip plan {} for laneTxId {} lane mode manual condition failed.",accountPlan.getPlanType(),toll.getLaneTxId());
					continue;
				}

				//:TODO  
				/**	if (plan_type = HAC && plan_id in ( HA_08, HA_08A)   // 16, 2, 73         
				    {
				       vector_etcmgr_hac_match_plaza  //need api to check the record in t_account_toll
				            if (found)
				             continue
				    } **/

				/** if plaza limited not found in t_acct_plan_limit ignore the plan **/
				if(isPlazaLimitedPlan(planPolicy,accountPlan,toll).equals(Boolean.FALSE))
				{
					logger.info("Skip plan {} for laneTxId {} isPlazaimited condition failed.",accountPlan.getPlanType(),toll.getLaneTxId());
					continue;
				}else {
					logger.info("IsPlazaLimited condition passed for accountPlan: {} and laneTxId:{}", accountPlan.getPlanType(), toll.getLaneTxId());
				}

				/** if plan is post paid and status is suspended then ignore **/	 //:TODO Post paid flag is not checked
				if(isDeviceSpecific(planPolicy, accountPlan, toll).equals(Boolean.FALSE))
				{
					logger.info("Skip plan {} for laneTxId {} deviceSpecific condition failed.",accountPlan.getPlanType(),toll.getLaneTxId());
					continue;
				}else {
					logger.info("IsDeviceSpecific condition passed for accountPlan: {} and laneTxId:{}", accountPlan.getPlanType(), toll.getLaneTxId());
				}

				if(planPolicy.getIsTripLimited().equalsIgnoreCase("T"))
				{
					Integer tripsMade = 0;
					List<String> flagList=new ArrayList<>();
					flagList.add("T");
					flagList.add("F");
					List<TripHistory> tripHistoryList=tripHistoryService.getTripHistory(accountPlan.getApdId(), toll.getTxDate(), flagList);
					if(tripHistoryList==null || flagList.isEmpty())
					{
						tripsMade = 0;
					}
					else if(tripHistoryList.size()>1)
					{
						flagList=new ArrayList<>();
						flagList.add("F");
						tripHistoryList=tripHistoryService.getTripHistory(accountPlan.getApdId(), toll.getTxDate(), flagList);
						if(tripHistoryList!=null && !flagList.isEmpty())
						{
							tripsMade =tripHistoryList.get(0).getTripsMade();
						}
					}
					else
					{
						tripsMade =tripHistoryList.get(0).getTripsMade();
					}
					if(accountPlan.getTripCount()>0 && tripsMade>accountPlan.getTripCount())
					{
						accountPlan.setActiveFlag("F");
						logger.info("Skip plan {} for laneTxId {} - plan is trip limited & trip made {} is greater than tripCount {} ",accountPlan.getPlanType(),toll.getLaneTxId(),tripsMade,accountPlan.getTripCount());
						continue;
					}
				}
				logger.info("Calcuate tollSchedule for plan {} for laneTxId {}",accountPlan.getPlanType(),toll.getLaneTxId());
				tollCalculation.tollScheduleApi(accountInfo, toll, accountPlan);
				
				if(planPolicy.getIsPostPaid().equalsIgnoreCase("T") && plazaAgency.getIsHomeAgency().equals("T"))
				{
					if(toll.getIsPeak()!=null && toll.getIsPeak().equals("T") && accountPlan.getIsPeak().equals("F")) 
					{
						toll.setIsDiscountable("F");
					}
				}
				//TBNRFP plan cannot be combined with RRT,RNT and VRT
				if(accountPlan.getActiveFlag().equalsIgnoreCase("T"))
				{
					logger.info("Plan {} selected for laneTxId {} checking RRT,RNT,VRT,VNBCP",accountPlan.getPlanType(),toll.getLaneTxId());
					if(toll.getPlazaId().equals(Constants.PLAZA_MARINE_PARKWAY) || toll.getPlazaId().equals(Constants.PLAZA_CROSS_BAY_BR))
					{
						if(accountPlan.getPlanType().intValue()==PlanType.RRT.getCode())
						{
							isRrtPlan = 1;
							isRntPlan = 0;
						}
						else if(accountPlan.getPlanType().intValue()==PlanType.RNT.getCode())
						{
							isRrtPlan = 0;
							isRntPlan = 1;
						}
					}
					else if(toll.getPlazaId().equals(Constants.PLAZA_VERRAZANO_NARROES_BR))
					{
						if(accountPlan.getPlanType().intValue()==PlanType.VRT.getCode() && isVnbcpPlan==0)  
						{
							isVrtPlan = 1;
						}
						else if(accountPlan.getPlanType().intValue()==PlanType.VNB.getCode()) 
						{
							isVnbcpPlan= 1;
							isVrtPlan = 0; //VNBCP overrides VRT plan if the lan is availabe to this account
						}
					}
				}
			}
			/**----------------------------------Account plan list loop end----------------------------------**/
			logger.info("Plan selection loop for laneTxId {} completed",toll.getLaneTxId());
			Integer lowest = 0;
			logger.info("Checking selected plan is RNT or RRT for laneTxId {} completed",toll.getLaneTxId());
			if (( toll.getPlazaId().equals(Constants.PLAZA_MARINE_PARKWAY) || toll.getPlazaId().equals(Constants.PLAZA_CROSS_BAY_BR)) && (isRrtPlan==1 ||isRntPlan==1))
			{
				for(int i=0;i<accountInfo.getAccountPlanList().size();i++)
				{
					AccountPlan accountPlan=accountInfo.getAccountPlanList().get(i);
					if(accountPlan.getActiveFlag().equalsIgnoreCase("T") && (accountPlan.getPlanType().intValue()==PlanType.RNT.getCode() || accountPlan.getPlanType().intValue()==PlanType.RRT.getCode()))
					{
						logger.info("Plan is RNT or RRT for laneTxId {} made it selected",toll.getLaneTxId());
						accountInfo.setSelectedPlan(accountPlan);
						lowest = 1;
					}
				}
			}
			else if (toll.getPlazaId().equals(Constants.PLAZA_VERRAZANO_NARROES_BR) && isVnbcpPlan==0 && isVrtPlan==1)
			{
				for(int i=0;i<accountInfo.getAccountPlanList().size();i++)
				{
					AccountPlan accountPlan=accountInfo.getAccountPlanList().get(i);
					if(accountPlan.getActiveFlag().equalsIgnoreCase("T") && accountPlan.getPlanType().intValue()==PlanType.VRT.getCode())
					{
						logger.info("Plan is VRT for laneTxId {} made it selected",toll.getLaneTxId());
						accountInfo.setSelectedPlan(accountPlan);
						lowest = 1;
					}
				}
			}
			else
			{
				for(int i=0;i<accountInfo.getAccountPlanList().size();i++)
				{
					AccountPlan accountPlan=accountInfo.getAccountPlanList().get(i);
					if(accountPlan.getActiveFlag().equalsIgnoreCase("T"))
					{
						if(lowest==0)
						{
							accountInfo.setSelectedPlan(accountPlan);
							lowest = 1;
						}
						else if(accountPlan.getDiscountAmount()<=accountInfo.getSelectedPlan().getDiscountAmount()) //:TODO temp null pointer
						{
							if(accountPlan.getDiscountAmount().equals(accountInfo.getSelectedPlan().getDiscountAmount()))
							{
								Integer plan1=accountInfo.getSelectedPlan().getPlanType();
								Integer plan2=accountPlan.getPlanType();
								if(masterCache.getPlanPolicy(plan1).getIsCommute().equalsIgnoreCase("T") && !masterCache.getPlanPolicy(plan2).getIsCommute().equalsIgnoreCase("T"))
								{
									continue;
								}
							}
							accountInfo.setSelectedPlan(accountPlan);
							lowest=1;
						}
					}
				}
			}
			if(lowest!=0)
			{
				AccountPlan accountPlan=accountInfo.getSelectedPlan();
				PlanPolicy planPolicy=masterCache.getPlanPolicy(accountPlan.getPlanType());
				accountInfo.setSelectedPlanPolicy(planPolicy);
				accountPlan.setCommuteFlag(planPolicy.getIsCommute().equalsIgnoreCase("T")?"T":"F");
				/** Update suspended status in database if selected plan is suspended**/
				updateIfSuspendedPlan(accountInfo,toll);

				if (etcTrx.getPlazaAgencyId()== 30)
				{
					toll.setExpectedRevenueAmount(toll.getPostedFareAmount()); //:TODO
				}
			}
			else
			{
				logger.info("No best plan found in database for laneTxId {} checking in default list",toll.getLaneTxId());
				AccountPlan accountPlan=new AccountPlan();
				if(!toll.getPlazaAgencyId().equals(Constants.AGENCY_NCTA) && !toll.getPlazaAgencyId().equals(Constants.AGENCY_MTA) )//&& !toll.getLprEnabled().equals("Y")
				{
					if(accountInfo.getAccountType()==AccountType.COMMERCIAL.getCode() && plazaAgency.getIsHomeAgency().equals("T") && toll.getPlazaAgencyId().equals(Constants.AGENCY_NY))
					{
						accountPlan.setPlanType(PlanType.NYCOML.getCode());
					}
					else if (toll.getPlazaAgencyId()==53 || toll.getPlazaAgencyId()==54)
					{
						if(pmtppflag==1)
						{
							accountPlan.setPlanType(221);
						}
						else
						{
							if(toll.getAccountType().equals(AccountType.BUSINESS.getCode()) || toll.getAccountType().equals(AccountType.COMMERCIAL.getCode()))
							{
								accountPlan.setPlanType(PlanType.BUSINESS.getCode());
							}
							else if(toll.getAccountType().equals(AccountType.STVA.getCode()))
							{
								accountPlan.setPlanType(PlanType.STVA.getCode());
							}
							else
							{
								accountPlan.setPlanType(toll.getPlanTypeId());
							}
						}
					}
					else
					{
						if(toll.getAccountType().equals(AccountType.BUSINESS.getCode()) || toll.getAccountType().equals(AccountType.COMMERCIAL.getCode()))
						{
							accountPlan.setPlanType(PlanType.BUSINESS.getCode());
						}
						else if(toll.getAccountType().equals(AccountType.PRIVATE.getCode()) || toll.getAccountType().equals(AccountType.NONREGVIDEO.getCode()))
						{
							accountPlan.setPlanType(PlanType.STANDARD.getCode());
						}
						else if(toll.getAccountType().equals(AccountType.PVIDEOUNREG.getCode()) || toll.getAccountType().equals(AccountType.BVIDEOUNREG.getCode()))
						{
							accountPlan.setPlanType(PlanType.VIDEOUNREG.getCode());
						}
						else if(toll.getAccountType().intValue()==AccountType.STVA.getCode())
						{
							accountPlan.setPlanType(PlanType.STVA.getCode());  //:TODO STVA_PLAN 
						}
						else
						{
							accountPlan.setPlanType(toll.getPlanTypeId()); // Default
						}
					}
				}
				else
				{
					if(accountInfo.getAccountType()==AccountType.COMMERCIAL.getCode() && plazaAgency.getIsHomeAgency().equals("T") && toll.getPlazaAgencyId().equals(Constants.AGENCY_NY))
					{
						accountPlan.setPlanType(PlanType.NYCOML.getCode());
					}
					else if(accountInfo.getPostPaidFlag()==null || !accountInfo.getPostPaidFlag().equals("Y")) //:TODO
					{
						if(toll.getAccountType().equals(AccountType.BUSINESS.getCode()) || toll.getAccountType().equals(AccountType.COMMERCIAL.getCode()))
						{
							accountPlan.setPlanType(PlanType.BUSINESS.getCode());
						}
						else if(toll.getAccountType().equals(AccountType.PRIVATE.getCode()) || toll.getAccountType().equals(AccountType.NONREGVIDEO.getCode()))
						{
							accountPlan.setPlanType(PlanType.STANDARD.getCode());
						}
						else if(toll.getAccountType().equals(AccountType.PVIDEOUNREG.getCode()) || toll.getAccountType().equals(AccountType.BVIDEOUNREG.getCode()))
						{
							accountPlan.setPlanType(PlanType.VIDEOUNREG.getCode());
						}
						else if(toll.getAccountType().equals(AccountType.STVA.getCode()))
						{
							accountPlan.setPlanType(PlanType.STVA.getCode());
						}
						else
						{
							accountPlan.setPlanType(toll.getPlanTypeId());
						}
					}
					else
					{
						accountPlan.setPlanType(PlanType.VIDEOUNREG.getCode());
					}
				}
				logger.info("Default plan {} selected for laneTxId {} ",PlanType.getByCode(accountPlan.getPlanType()),toll.getLaneTxId());
				logger.info("Checking in toll api for laneTxId {} and planType {}",toll.getLaneTxId(),accountPlan.getPlanType());
				//here add get call for plan, in case of default plan search in toll
				//accountPlan=masterCache.getAccountDefaultPlan(accountPlan.getPlanType());
				tollCalculation.tollScheduleApi(accountInfo, toll, accountPlan); 
				if(accountPlan.getActiveFlag().equals("T"))
				{
					logger.info("Plan {} is active selcted for laneTxId {} ",accountPlan.getPlanType(),toll.getLaneTxId());
					PlanPolicy planPolicy=masterCache.getPlanPolicy(accountPlan.getPlanType());
					accountInfo.setSelectedPlan(accountPlan);
					accountInfo.setSelectedPlanPolicy(planPolicy);
					accountPlan.setCommuteFlag(planPolicy.getIsCommute().equalsIgnoreCase("T")?"T":"F");
				}
				else
				{
					toll.setPlanTypeId(accountPlan.getPlanType()); //:IBTS issue fixed
					/**71959 UAT bug**/
//					if (!(toll.getTxTypeInd().equals("U") || toll.getTxTypeInd().equals("V"))
//							&& !(toll.getTxSubtypeInd().equals("X") && toll.getTollSystemType().equals("X"))) 
					if (!(toll.getTxTypeInd().equals("U"))
							&& !(toll.getTxSubtypeInd().equals("X") && toll.getTollSystemType().equals("X"))) //V condition removed for bug 29372
					{
						logger.error("No record found for planType:{},AccountType:{} and entryPlazaId:{} in t_toll_schedule table",accountPlan.getPlanType(),toll.getAccountType(),toll.getEntryPlazaId());	
	//					throw new NoRecordFoundException("No record found in t_toll_schedule for specific planType..");
						logger.error("Setting TX_STATUS To Invalid Plan for laneTxId {}",toll.getLaneTxId());
						toll.setTxStatus(TxStatus.TX_STATUS_INVP.getCode());						//changed due to bug 17773
					}
				}
			}
	}


	public void updateIfSuspendedPlan(AccountInfoDTO accountInfo,AccountTollPostDTO toll)
	{
		if(accountInfo.getSelectedPlan().getSuspendFlag()!=null && accountInfo.getSelectedPlan().getSuspendFlag().equals("T"))
		{
			//txDate should be withing 7 days of txDate
			//txDate should be after 7 days of txDate
			accountPlanSuspensionService.updateAcountPlanSuspension(toll.getTxDate(),accountInfo.getSelectedPlan().getApdId() ,accountInfo.getEtcAccountId(), accountInfo.getSelectedPlan().getSuspStartDate(), accountInfo.getSelectedPlan().getSuspEndDate());
		}
	}

	public Boolean isPlanSuspensed(PlanPolicy planPolicy,AccountPlan accountPlan,AccountInfoDTO accountInfo,AccountTollPostDTO toll)
	{
		logger.info("Checking plan {} suspension for laneTxId {}",accountPlan.getPlanType(),toll.getLaneTxId());
		Integer isSuspend=0;
		if(planPolicy.getManualSuspension().equals("T"))
		{
			accountPlan.setSuspendDays(0L);
			accountPlan.setSuspendFlag("F");
			AccountPlanSuspension accountPlanSuspension=accountPlanSuspensionService.getAccountPlanSuspension(accountPlan.getApdId(), accountInfo.getEtcAccountId(), toll.getTxDate()); //:TODO suspension_status = 1
			if(accountPlanSuspension!=null)
			{
				logger.info("Got accountPlanSuspension data for plan {} for laneTxId {} use this plan",accountPlan.getPlanType(),toll.getLaneTxId());
				if(planPolicy.getAutomaticReactivation().equalsIgnoreCase("T"))
				{
					Long diffDay = ChronoUnit.DAYS.between(toll.getTxDate(),accountPlanSuspension.getStartDate())*-1;
					if((diffDay)>=planPolicy.getMinSuspensionDays())
					{
						accountPlan.setSuspendFlag("T");
						accountPlan.setSuspendDays(diffDay+1);
						accountPlan.setSuspStartDate(accountPlanSuspension.getStartDate());
						accountPlan.setSuspEndDate(accountPlanSuspension.getEndDate());
						isSuspend=0;
						logger.info("Days diff {} is greater than minSuspensionDays for plan {} for laneTxId {} use this plan",diffDay,accountPlan.getPlanType(),toll.getLaneTxId());
					}
					else
					{
						logger.info("Days diff {} is less than minSuspensionDays for plan {} for laneTxId {} skip this plan",diffDay,accountPlan.getPlanType(),toll.getLaneTxId());
						isSuspend=1;
					}
				}
				else
				{
					logger.info("AutomaticReactivation flag is F for plan {} for laneTxId {} skip this plan",accountPlan.getPlanType(),toll.getLaneTxId());
					isSuspend=1;
				}
			}
			else
			{
				logger.info("No accountPlanSuspension data found for plan {} for laneTxId {} use this plan",accountPlan.getPlanType(),toll.getLaneTxId());
				isSuspend=0;
			}
			if(isSuspend==1)
			{
				return Boolean.TRUE;
			}
		}
		else
		{
			logger.info("ManualSuspension flag is F for plan {} for laneTxId {} use this plan",accountPlan.getPlanType(),toll.getLaneTxId());
		}
		return Boolean.FALSE;
	}

	public Boolean isDeviceSpecific(PlanPolicy planPolicy,AccountPlan accountPlan,AccountTollPostDTO toll)
	{
		if(planPolicy.getIsDeviceSpecific().equalsIgnoreCase("T") && !accountPlan.getDeviceNo().equalsIgnoreCase(toll.getDeviceNo())) {
			return Boolean.FALSE;
		}
		return Boolean.TRUE;
	}

	public Boolean isPlazaLimitedPlan(PlanPolicy planPolicy,AccountPlan accountPlan,AccountTollPostDTO toll)
	{
		if(planPolicy.getPlazaLimited().equals("T") && planPolicy.getIsDeviceSpecific().equalsIgnoreCase("T"))
		{
			if(!accountPlan.getDeviceNo().equalsIgnoreCase(toll.getDeviceNo()))
			{
				return Boolean.FALSE;
			}
			if(toll.getTollSystemType().equalsIgnoreCase("C") || toll.getTollSystemType().equalsIgnoreCase("X") || toll.getTollSystemType().equalsIgnoreCase("T"))
			{
				PlazaPlanLimit plazaPlanLimit=plazaPlanLimitService.getPlazaPlazaPlanLimit(toll.getEntryPlazaId(), toll.getPlazaId(), accountPlan.getApdId());
				if(plazaPlanLimit==null)
				{
					return Boolean.FALSE;
				}
			}
			else
			{
				PlazaPlanLimit plazaPlanLimit=plazaPlanLimitService.getPlazaPlanLimit(toll.getPlazaId(), 0, accountPlan.getApdId());
				if(plazaPlanLimit==null)
				{
					plazaPlanLimit=plazaPlanLimitService.getPlazaPlanLimit(0,toll.getPlazaId(), accountPlan.getApdId());
					if(plazaPlanLimit==null)
					{
						return Boolean.FALSE;
					}
				}
			}
		}
		return Boolean.TRUE;
	}

	
}