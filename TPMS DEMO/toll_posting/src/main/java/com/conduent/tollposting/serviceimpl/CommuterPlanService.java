package com.conduent.tollposting.serviceimpl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.util.Assert;

import com.conduent.app.timezone.utility.TimeZoneConv;
import com.conduent.tollposting.constant.PlanType;
import com.conduent.tollposting.dto.AccountTollPostDTO;
import com.conduent.tollposting.model.AccountPlan;
import com.conduent.tollposting.model.PlanPolicy;
import com.conduent.tollposting.model.TripHistory;

@Component
public class CommuterPlanService 
{
	private static final Logger logger = LoggerFactory.getLogger(CommuterPlanService.class);

	@Autowired
	private TripHistoryService tripHistoryService;
	
	@Autowired
	private TransactionTemplate transactionTemplate;
	
	@Autowired
	PlatformTransactionManager transactionManager;
	
	@Autowired
	TimeZoneConv timeZoneConv;

	@PostConstruct
	public void init() {
		 Assert.notNull(transactionManager, "The 'transactionManager' argument must not be null.");
		 this.transactionTemplate = new TransactionTemplate(transactionManager);
		 transactionTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
	}
	

	
	public void CommuterPlanActions(PlanPolicy planPolicy,AccountPlan accountPlan,AccountTollPostDTO toll) throws Exception{
		transactionTemplate.execute(new TransactionCallbackWithoutResult() { //PROPAGATION_REQUIRED,ISOLATION_DEFAULT
			
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status) {
				try { 
					logger.info("Semaphore lock acquired by thread: {}",Thread.currentThread().getName());
					logger.info("Commuter Thread {} started the transaction ", Thread.currentThread().getName());
					commuterPlan(toll.getAccountInfoDTO().getSelectedPlanPolicy(), toll.getAccountInfoDTO().getSelectedPlan(), toll);
				} catch (Exception e) {
					status.setRollbackOnly();
					logger.error("Commuter plan txns rolled back for laneTxId {}, exception: {}",toll.getLaneTxId(), e.getMessage());
				}finally {
					logger.info("Thread {} commited the transaction ", Thread.currentThread().getName());
				}
					
			}	
		});
	}
	
	public void commuterPlan(PlanPolicy planPolicy,AccountPlan accountPlan,AccountTollPostDTO toll)
	{
		try
		{
			if(accountPlan.getCommuteFlag().equals("T") || accountPlan.getPlanType().equals(PlanType.VNC.getCode()) || accountPlan.getPlanType().equals(PlanType.TVD.getCode()))	//added this condition for US 22293
			{
				if(accountPlan.getSuspendFlag()!=null && accountPlan.getSuspendFlag().equals("F"))
				{
					accountPlan.setSuspendDays(0L);
				}
				if(toll.getAccountInfoDTO().getSelectedPlanPolicy().getIsAccountLevelDiscount().equals("T"))
				{
					if(planPolicy.getMaxTrips()<=0)	//min trip
					{
						logger.info("AccountLevel discount is true & maxTrip <=0 for laneTxId {} calling tripLogicAccountDiscount",toll.getLaneTxId());
						LocalDateTime from = LocalDateTime.now();
						tripLogicAccountDiscount(planPolicy,accountPlan,toll);
						logger.debug("Time Taken for thread {} in tripLogicAccountDiscount: {} ms", Thread.currentThread().getName(), ChronoUnit.MILLIS.between(from, LocalDateTime.now()));
					}
				}
				else if(planPolicy.getMaxTrips()>=0)	//max trip
				{
					logger.info("maxTrips >=0 for laneTxId {} excute normal flow",toll.getLaneTxId());
					TripHistory tripHistory=null;
					Integer firstTrip=0;
					if(planPolicy.getRenewLimitInd().equals("T"))
					{
						logger.info("RenewLimitInd is T for laneTxId {} checking getTripHistoryBeforeEndDate",toll.getLaneTxId());
						LocalDateTime from = LocalDateTime.now();
						tripHistory=tripHistoryService.getTripHistoryBeforeEndDate(accountPlan.getApdId(), toll.getTxDate(), accountPlan.getSuspendDays());
						logger.info("Time Taken for thread {} in getting TripHistoryBeforeEndDate: {} ms", Thread.currentThread().getName(), ChronoUnit.MILLIS.between(from, LocalDateTime.now()));
					}
					else
					{
						logger.info("RenewLimitInd is F for laneTxId {} checking getTripHistory",toll.getLaneTxId());
						LocalDateTime from = LocalDateTime.now();
						if(accountPlan.getPlanType().equals(PlanType.VNC.getCode()) || accountPlan.getPlanType().equals(PlanType.TVD.getCode()))
						{
							String apd_id = tripHistoryService.getApdId(toll.getEtcAccountId());
							accountPlan.setApdId(apd_id!=null?apd_id:null);
						}
						tripHistory=tripHistoryService.getTripHistory(accountPlan.getApdId(), toll.getTxDate(), accountPlan.getSuspendDays());
						logger.info("Time Taken for thread {} in getting TripHistory: {} ms", Thread.currentThread().getName(), ChronoUnit.MILLIS.between(from, LocalDateTime.now()));
					}
					if(tripHistory!=null)
					{
						Boolean updateTripEnd=false;
						if(tripHistory.getReconFlag().equals("T")) // || tripHistory.getTripEndDate().isAfter(toll.getTxDate()))
						{
							logger.info("tripHistory found for laneTxId {} preparing to update for late trips",toll.getLaneTxId());
							tripHistory.setUsageAmount(tripHistory.getUsageAmount()+toll.getPostedFareAmount());
							tripHistory.setFullFareAmount(tripHistory.getFullFareAmount()+toll.getExpectedRevenueAmount());
							tripHistory.setDiscountedAmount(tripHistory.getDiscountedAmount()+toll.getPostedFareAmount());
							tripHistory.setLastLaneTxId(toll.getLaneTxId());
							tripHistory.setLateTrips(accountPlan.getTripPerTrx());
							tripHistory.setLastTxPostTimestamp(timeZoneConv.currentTime());
							tripHistory.setRecongDate(toll.getReconDate());
							tripHistory.setLastTxDate(toll.getTxDate());
							LocalDateTime from = LocalDateTime.now();
							tripHistoryService.updateLateTrip(tripHistory);
							logger.info("Time Taken for thread {} in update LateTripHistory: {} ms", Thread.currentThread().getName(), ChronoUnit.MILLIS.between(from, LocalDateTime.now()));
							return;
						}
						if(accountPlan.getSuspendFlag().equals("T") )
						{
							tripHistory.setTripEndDate(tripHistory.getTripEndDate().plusDays(accountPlan.getSuspendDays()));
							updateTripEnd=true;
							logger.info("trip end date changed for laneTxId {} update with 2nd query",toll.getLaneTxId());
						}
						Long txAge = ChronoUnit.DAYS.between(tripHistory.getTripEndDate(),toll.getTxDate()); //End date is always future, age +ve
						if(txAge>0)
						{
							tripHistory.setLastTxDate(toll.getTxDate());
							updateTripEnd=true;
							logger.info("txAge is {} for laneTxId {} update with 2nd query",txAge,toll.getLaneTxId());
						}
						tripHistory.setUsageAmount(tripHistory.getUsageAmount()+toll.getPostedFareAmount());
						tripHistory.setFullFareAmount((tripHistory.getFullFareAmount()==null?0:tripHistory.getFullFareAmount())+toll.getExpectedRevenueAmount());
						tripHistory.setDiscountedAmount(tripHistory.getDiscountedAmount()+toll.getPostedFareAmount());
						tripHistory.setTripsMade(tripHistory.getTripsMade()+accountPlan.getTripPerTrx());
						int tripsLeft = tripHistory.getTripsLeft()-accountPlan.getTripPerTrx();
						tripHistory.setTripsLeft(tripsLeft<0?0:tripsLeft);
						tripHistory.setLastLaneTxId(toll.getLaneTxId());
						tripHistory.setLastTxPostTimestamp(timeZoneConv.currentTime());
						tripHistory.setLastTxDate(toll.getTxDate());
						tripHistory.setRecongDate(toll.getReconDate());
						tripHistory.setApdId(accountPlan.getApdId());
						//T only for max trips, min trips will be reconciled by UUCT
						if(tripHistory.getTripsMade()==planPolicy.getMaxTrips()) {
						tripHistory.setReconFlag("T");
						}

						if(updateTripEnd.equals(Boolean.FALSE))
						{	LocalDateTime from = LocalDateTime.now();
							tripHistoryService.updateLateTripEnd_1(tripHistory);//reconcile trip
							logger.info("Time Taken for thread {} in update reconciled trip: {} ms", Thread.currentThread().getName(), ChronoUnit.MILLIS.between(from, LocalDateTime.now()));
							return;
						}
						else
						{	LocalDateTime from = LocalDateTime.now();
							tripHistoryService.updateLateTripEnd_2(tripHistory);
							logger.info("Time Taken for thread {} in update LateTripEnd_2: {} ms", Thread.currentThread().getName(), ChronoUnit.MILLIS.between(from, LocalDateTime.now()));
							return;
						}
					}
					else
					{
						tripHistory=new TripHistory();
						tripHistory.setApdId(accountPlan.getApdId());
						tripHistory.setUsageAmount(toll.getPostedFareAmount());
						tripHistory.setFullFareAmount(toll.getExpectedRevenueAmount());
						tripHistory.setDiscountedAmount(toll.getPostedFareAmount());
						tripHistory.setEtcAccountId(accountPlan.getEtcAccountId());
						tripHistory.setLastLaneTxId(toll.getLaneTxId()); //:TODO
						tripHistory.setPlanTypeId(accountPlan.getPlanType());
						tripHistory.setReconFlag("F");
						tripHistory.setLastTxDate(toll.getTxDate());
						tripHistory.setLastTxPostTimestamp(timeZoneConv.currentTime());
						tripHistory.setLateTrips(0);

					}
					if(firstTrip==0)
					{  
						tripHistory.setTripsMade(accountPlan.getTripPerTrx());
						tripHistory.setTripsLeft(planPolicy.getMinTrips()-accountPlan.getTripPerTrx());
					}
					else
					{
						tripHistory.setTripsLeft(planPolicy.getMinTrips()-accountPlan.getTripPerTrx());
						if(planPolicy.getRenewLimitInd().equals("T"))
						{
							Long txAge = ChronoUnit.DAYS.between(toll.getTxDate(),tripHistory.getTripEndDate());
							if(txAge>0)
							{
								tripHistory.setTripStartDate(tripHistory.getTripEndDate().plusDays(1));
							}
						}
						else
						{
							tripHistory.setUsageAmount(tripHistory.getUsageAmount()+toll.getPostedFareAmount());
							tripHistory.setFullFareAmount(tripHistory.getFullFareAmount()+toll.getExpectedRevenueAmount());
							tripHistory.setDiscountedAmount(tripHistory.getDiscountedAmount()+toll.getPostedFareAmount());
							tripHistory.setLastLaneTxId(toll.getLaneTxId());
							tripHistory.setLastTxPostTimestamp(timeZoneConv.currentTime());
							tripHistory.setLateTrips(accountPlan.getTripPerTrx());
							tripHistory.setRecongDate(toll.getReconDate());
							tripHistory.setLastTxDate(toll.getTxDate());
							LocalDateTime from = LocalDateTime.now();
							tripHistoryService.updateLateTrip(tripHistory);
							logger.info("Time Taken for thread {} in update LateTrip2: {} ms", Thread.currentThread().getName(), ChronoUnit.MILLIS.between(from, LocalDateTime.now()));
							return;
						}

					}
					if(planPolicy.getMonthlyPlan().equals("T"))
					{
						if(planPolicy.getIsCalendarPeriod().equals("T")) //always T for PASI
						{
							tripHistory.setTripStartDate(tripHistory.getTripStartDate().withDayOfMonth(1));
							tripHistory.setTripEndDate(tripHistory.getTripStartDate().plusDays(tripHistory.getTripStartDate().lengthOfMonth()-1));
						}
						else
						{
							//tripHistory.setTripEndDate(tripHistory.getTripStartDate().plusDays(planPolicy.getPlanDays()-1));
							/***********bug 16517*************/
							buildTripEndDate(accountPlan.getApdId(),tripHistory,toll,planPolicy);
						}
					}
					else if(accountPlan.getPlanType().equals(PlanType.VNC.getCode()) || accountPlan.getPlanType().equals(PlanType.TVD.getCode()))
					{
						//insert record for TVD and VNC
						logger.info("Building start date and end date for TVD or VNC Plan Type for lane_tx_id {}",toll.getLaneTxId());
						buildStartAndEndDate(accountPlan,toll,tripHistory);
						
						tripHistory.setTripsLeft(0);
					}
					else
					{
							buildTripEndDate(accountPlan.getApdId(),tripHistory,toll,planPolicy);
						 	//tripHistory.setTripEndDate(tripHistory.getTripStartDate().plusDays(planPolicy.getPlanDays()-1));
					}
					if(accountPlan.getSuspendFlag().equals("T"))
					{
						tripHistory.setTripEndDate(tripHistory.getTripEndDate().plusDays(accountPlan.getSuspendDays()));
					}
					LocalDateTime from = LocalDateTime.now();
					logger.info("Inserting into T_TRIP_HISTORY: {}", tripHistory);
					tripHistory.setRecongDate(toll.getReconDate());
					tripHistory.setLastTxDate(toll.getTxDate());
					tripHistoryService.insertTripHistory(tripHistory);
					logger.info("Time Taken for thread {} in insert TripHistory: {} ms", Thread.currentThread().getName(), ChronoUnit.MILLIS.between(from, LocalDateTime.now()));
				}
				else
				{
					logger.info("MaxTrips >=0 for laneTxId {} execute fixedTrip flow",toll.getLaneTxId());
					LocalDateTime from = LocalDateTime.now();
					if(planPolicy.getRenewLimitInd().equals("T")) {
					fixedTripLogic(planPolicy,accountPlan,toll);
					}
					logger.info("Time Taken for thread {} in fixedTripLogic: {} ms", Thread.currentThread().getName(), ChronoUnit.MILLIS.between(from, LocalDateTime.now()));
				}
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			logger.error("Exception {} while updating commuterPlan {}",ex.getMessage(),toll);
		}
	}
	
	
	private void buildStartAndEndDate(AccountPlan accountPlan, AccountTollPostDTO toll, TripHistory tripHistory) 
	{

		LocalDate date = toll.getTxTimestamp().toLocalDate();
		
		//VNC Plan Type				
		if(accountPlan.getPlanType().equals(PlanType.VNC.getCode()))
		{
			LocalDate startDateForVNCPlan   = YearMonth.of(date.getYear(), date.getMonth()).atDay(1);
			tripHistory.setTripStartDate(startDateForVNCPlan);
			
			LocalDate endDateForVNCPlan   = YearMonth.of(date.getYear(), date.getMonth()).atEndOfMonth();
			tripHistory.setTripEndDate(endDateForVNCPlan);
			
			logger.info("Start Date for VNC Plan {} and End Date for VNC Plan {}",tripHistory.getTripStartDate(),tripHistory.getTripEndDate());
		}
		
		//TVD Plan Type
		if(accountPlan.getPlanType().equals(PlanType.TVD.getCode()))
		{
			int anniversaryDom = tripHistoryService.getAnniversaryDom(toll.getEtcAccountId());
			logger.info("Anniversary DOM for etc_account_id {} is {}",toll.getEtcAccountId(),anniversaryDom);
			
			if(anniversaryDom > 28)
			{
				LocalDate startDateForTVDPlan   = YearMonth.of(date.getYear(), date.getMonth()).atDay(1);
				tripHistory.setTripStartDate(startDateForTVDPlan);
				
				LocalDate endDateForTVDPlan   = YearMonth.of(date.getYear(), date.getMonth()).atEndOfMonth();
				tripHistory.setTripEndDate(endDateForTVDPlan);
			}
			else
			{
				if(anniversaryDom > date.getDayOfMonth())		//changed due to bug 23631
				{
					LocalDate startDateForTVDPlan   = YearMonth.of(date.getYear(), date.getMonth().minus(1)).atDay(anniversaryDom);
					tripHistory.setTripStartDate(startDateForTVDPlan);
					
					LocalDate endDateForTVDPlan = YearMonth.of(date.getYear(), date.getMonth()).atDay(anniversaryDom-1);
					tripHistory.setTripEndDate(endDateForTVDPlan);
				}
				else
				{
					LocalDate startDateForTVDPlan   = YearMonth.of(date.getYear(), date.getMonth()).atDay(anniversaryDom);
					tripHistory.setTripStartDate(startDateForTVDPlan);
					
					LocalDate endDateForTVDPlan = YearMonth.of(date.getYear(), date.getMonth().plus(1)).atDay(anniversaryDom-1);
					tripHistory.setTripEndDate(endDateForTVDPlan);
				}
				
			}
			
			logger.info("Start Date for TVD Plan {} and End Date for TVD Plan {}",tripHistory.getTripStartDate(),tripHistory.getTripEndDate());
			
		}
	}



	private void buildTripEndDate(String apdId,TripHistory tripHistory,AccountTollPostDTO toll,PlanPolicy planPolicy) {
		LocalDate lastTripEndDate = tripHistoryService.getLatestTripForApdId(apdId);
		if(null != lastTripEndDate) {
			tripHistory.setTripStartDate(lastTripEndDate.plusDays(1));
			if(lastTripEndDate.getDayOfMonth()>=28) {
				LocalDate next = lastTripEndDate.plusMonths(1);
				LocalDate endDate   = YearMonth.of(next.getYear(), next.getMonth()).atEndOfMonth();
				tripHistory.setTripEndDate(endDate);
			}else {
				tripHistory.setTripEndDate(lastTripEndDate.plusMonths(1));
			}
		}
		else {
			tripHistory.setTripStartDate(toll.getTxDate());
			tripHistory.setTripEndDate(toll.getTxDate().plusDays(planPolicy.getPlanDays()));
		}
		
	}



	public void fixedTripLogic(PlanPolicy planPolicy,AccountPlan accountPlan,AccountTollPostDTO toll)
	{
		LocalDateTime fromTime = LocalDateTime.now();
		TripHistory tripHistory=tripHistoryService.getTripHistory(accountPlan.getApdId(), toll.getTxDate());
		logger.debug("##SQL Time Taken for thread {} HOSTNAME: {} in fixedTripLogic: {} ms", Thread.currentThread().getName(),System.getenv("HOSTNAME"), ChronoUnit.MILLIS.between(fromTime, LocalDateTime.now()));
		if(tripHistory==null)
		{
			tripHistory=new TripHistory();
			tripHistory.setApdId(accountPlan.getApdId());
			tripHistory.setUsageAmount(accountPlan.getDiscountAmount());
			tripHistory.setFullFareAmount(accountPlan.getFullFareAmount());
			tripHistory.setDiscountedAmount(accountPlan.getDiscountAmount());
			tripHistory.setEtcAccountId(accountPlan.getEtcAccountId());
			tripHistory.setLastLaneTxId(toll.getLaneTxId()); //:TODO
			tripHistory.setLastTxPostTimestamp(timeZoneConv.currentTime());
			tripHistory.setPlanTypeId(accountPlan.getPlanType());
			tripHistory.setReconFlag("F");
			tripHistory.setRecongDate(toll.getReconDate());
			tripHistory.setTripStartDate(toll.getTxDate());
			tripHistory.setTripEndDate(tripHistory.getTripStartDate().plusDays(planPolicy.getNumberOfPeriods()-1));
			tripHistory.setTripsLeft(planPolicy.getMaxTrips()-accountPlan.getTripPerTrx());
			tripHistory.setTripsMade(tripHistory.getTripsMade()+accountPlan.getTripPerTrx());
			tripHistory.setLastTxDate(toll.getTxDate());
			tripHistoryService.insertTripHistory(tripHistory);
		}
		else
		{
			if(accountPlan.getSuspendFlag().equals("T"))
			{
				//:TODO
			}
			else
			{
				tripHistory.setTripsMade(tripHistory.getTripsMade()+accountPlan.getTripPerTrx());
				tripHistory.setUsageAmount(tripHistory.getUsageAmount()+accountPlan.getDiscountAmount());
				tripHistory.setFullFareAmount(tripHistory.getFullFareAmount()+accountPlan.getFullFareAmount());
				tripHistory.setDiscountedAmount(tripHistory.getDiscountedAmount()+accountPlan.getDiscountAmount());
				tripHistory.setTripsLeft(tripHistory.getTripsLeft()-accountPlan.getTripPerTrx());
				tripHistory.setLastTxPostTimestamp(timeZoneConv.currentTime());
				tripHistory.setRecongDate(toll.getReconDate());
				tripHistory.setLastTxDate(toll.getTxDate());
				
				if(tripHistory.getTripsMade()<=planPolicy.getMaxTrips())
				{
					tripHistoryService.updateLateTripEnd_1(tripHistory);
					return;
				}
				Integer tripAdjust=tripHistory.getTripsMade()-planPolicy.getMaxTrips();
				tripHistory.setTripsMade(planPolicy.getMaxTrips());
				
				tripHistoryService.updateLateTripEnd_1(tripHistory);
				TripHistory history=new TripHistory();
				BeanUtils.copyProperties(tripHistory, history);
				tripHistory.setTripStartDate(toll.getTxDate());
				tripHistory.setTripEndDate(tripHistory.getTripStartDate().plusDays(planPolicy.getNumberOfPeriods()-1));
				tripHistory.setTripsMade(tripAdjust);
				tripHistory.setUsageAmount(tripHistory.getUsageAmount()+accountPlan.getDiscountAmount());
				tripHistory.setFullFareAmount(tripHistory.getFullFareAmount()+accountPlan.getFullFareAmount());
				tripHistory.setDiscountedAmount(tripHistory.getDiscountedAmount()+accountPlan.getDiscountAmount());
				tripHistory.setTripsLeft(planPolicy.getMaxTrips()-tripAdjust);
				tripHistory.setRecongDate(toll.getReconDate());
				tripHistoryService.insertTripHistory(history);
			}
		}
	}

	public void tripLogicAccountDiscount(PlanPolicy planPolicy,AccountPlan accountPlan,AccountTollPostDTO toll)
	{
		if(accountPlan.getSuspendFlag().equals("F"))
		{
			accountPlan.setSuspendDays(0L);
		}
		TripHistory tripHistory=null;
		if(planPolicy.getRenewLimitInd().equals("T"))
		{
			List<String> flag=new ArrayList<String>();
			flag.add("T");
			LocalDateTime from = LocalDateTime.now();
			tripHistory=tripHistoryService.getTripHistoryStartEndDate(accountPlan.getApdId(), toll.getTxDate());
			logger.debug("##SQL Time Taken for thread {} HOSTNAME: {} in getTripHistory in RenewLimitInd1: {} ms", Thread.currentThread().getName(),System.getenv("HOSTNAME"), ChronoUnit.MILLIS.between(from, LocalDateTime.now()));
		}
		else
		{
			LocalDateTime from = LocalDateTime.now();
			tripHistory=tripHistoryService.getTripHistory(accountPlan.getApdId(), toll.getTxDate());

			logger.debug("##SQL Time Taken for thread {} HOSTNAME: {} in getTripHistory in RenewLimitInd2: {} ms", Thread.currentThread().getName(),System.getenv("HOSTNAME"), ChronoUnit.MILLIS.between(from, LocalDateTime.now()));


		}
		if(tripHistory!=null)
		{
			if(tripHistory.getReconFlag().equals("T"))
			{
				tripHistory.setUsageAmount(tripHistory.getUsageAmount()+accountPlan.getDiscountAmount());
				tripHistory.setFullFareAmount(tripHistory.getFullFareAmount()+accountPlan.getFullFareAmount());
				tripHistory.setDiscountedAmount(tripHistory.getDiscountedAmount()+accountPlan.getDiscountAmount());
				tripHistory.setLastLaneTxId(toll.getLaneTxId());
				tripHistory.setLastTxPostTimestamp(timeZoneConv.currentTime());
				tripHistory.setLateTrips(accountPlan.getTripPerTrx());
				tripHistory.setRecongDate(toll.getReconDate());
				tripHistory.setLastTxDate(toll.getTxDate());
				LocalDateTime from = LocalDateTime.now();
				tripHistoryService.updateLateTrip(tripHistory);
				logger.debug("Time Taken for thread {} in updateLateTrip: {} ms", Thread.currentThread().getName(), ChronoUnit.MILLIS.between(from, LocalDateTime.now()));

			}
			if(accountPlan.getSuspendFlag().equals("T"))
			{
				tripHistory.setTripEndDate(tripHistory.getTripEndDate().plusDays(accountPlan.getSuspendDays()));
			}
			Long txAge = ChronoUnit.DAYS.between(tripHistory.getLastTxDate(), toll.getTxDate());
			if(txAge<0)
			{
				tripHistory.setLastTxDate(toll.getTxDate());
			}
			if(accountPlan.getIsDiscountEligible().equals("T"))
			{
				tripHistory.setUsageAmount(tripHistory.getUsageAmount()+accountPlan.getDiscountAmount());
				tripHistory.setDiscountedAmount(tripHistory.getDiscountedAmount()+accountPlan.getDiscountAmount());
			}
			else
			{
				tripHistory.setUsageAmount(tripHistory.getUsageAmount()+accountPlan.getFullFareAmount());
				tripHistory.setDiscountedAmount(tripHistory.getDiscountedAmount()+accountPlan.getFullFareAmount());
			}
			tripHistory.setTripsMade(tripHistory.getTripsMade()+accountPlan.getTripPerTrx());
			int tripsLeft = tripHistory.getTripsLeft()-accountPlan.getTripPerTrx();
			tripHistory.setTripsLeft(tripsLeft<0?0:tripsLeft);
			tripHistory.setRecongDate(toll.getReconDate());
			tripHistory.setLastTxDate(toll.getTxDate());
			//tripHistory.setTripsLeft(tripHistory.getTripsLeft()-accountPlan.getTripPerTrx());
			tripHistory.setFullFareAmount(accountPlan.getFullFareAmount());
			LocalDateTime from = LocalDateTime.now();
			tripHistoryService.updateLateTripEnd_1(tripHistory);
			logger.debug("Time Taken for thread {} in updateLateTripEnd_1: {} ms", Thread.currentThread().getName(), ChronoUnit.MILLIS.between(from, LocalDateTime.now()));
			
		}
		else
		{
			boolean found=false;
			tripHistory=new TripHistory();
			tripHistory.setApdId(accountPlan.getApdId());
			tripHistory.setLastTxPostTimestamp(timeZoneConv.currentTime());
			tripHistory.setTripsMade(accountPlan.getTripPerTrx());
			tripHistory.setFullFareAmount(toll.getExpectedRevenueAmount());
			tripHistory.setUsageAmount(toll.getPostedFareAmount());
			tripHistory.setDiscountedAmount(toll.getPostedFareAmount());
			tripHistory.setTripCharged(0.0);
			tripHistory.setLateTrips(0);
			tripHistory.setAmountCharged(0.0);
			tripHistory.setReconFlag("F");
			tripHistory.setRecongDate(toll.getReconDate());
			if(found)
			{
				if(planPolicy.getRenewLimitInd().equals("T"))
				{
					if(planPolicy.getMinTrips()>0)
					{
						tripHistory.setTripsLeft(planPolicy.getMinTrips()-accountPlan.getTripPerTrx());
					}
					else
					{
						tripHistory.setTripsLeft(planPolicy.getMaxTrips()-accountPlan.getTripPerTrx());
					}
					if(tripHistory.getTripsLeft()<0)
					{
						tripHistory.setTripsLeft(0);
					}
				}
			}
			else
			{
				LocalDateTime from = LocalDateTime.now();
				TripHistory lastTrip=tripHistoryService.getTripHistory(accountPlan.getApdId(), "F");
				logger.debug("Time Taken for thread {} in getTripHistory: {} ms", Thread.currentThread().getName(), ChronoUnit.MILLIS.between(from, LocalDateTime.now()));
				
				if(lastTrip!=null)
				{
					if(accountPlan.getSuspendFlag().equals("T"))
					{
						tripHistory.setTripEndDate(lastTrip.getTripEndDate().plusDays(accountPlan.getSuspendDays()));
					}
					if(accountPlan.getIsDiscountEligible().equals("F"))
					{
						tripHistory.setUsageAmount(accountPlan.getFullFareAmount());
						tripHistory.setDiscountedAmount(accountPlan.getFullFareAmount());
					}
					else
					{
						tripHistory.setUsageAmount(accountPlan.getDiscountAmount());
						tripHistory.setDiscountedAmount(accountPlan.getDiscountAmount());
					}
				}
			}
			LocalDateTime from = LocalDateTime.now();
			tripHistory.setLastTxDate(toll.getTxDate());
			tripHistoryService.insertTripHistory(tripHistory);
			logger.debug("Time Taken for thread {} in insertTripHistory: {} ms", Thread.currentThread().getName(), ChronoUnit.MILLIS.between(from, LocalDateTime.now()));
		
		}
	}
}
