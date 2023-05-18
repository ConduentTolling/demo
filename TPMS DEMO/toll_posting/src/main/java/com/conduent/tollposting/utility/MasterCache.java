package com.conduent.tollposting.utility;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.conduent.tollposting.dao.IAccountPlanDao;
import com.conduent.tollposting.dto.RevenueDateStatisticsDto;
import com.conduent.tollposting.model.AccountPlan;
import com.conduent.tollposting.model.Agency;
import com.conduent.tollposting.model.AgencyHoliday;
import com.conduent.tollposting.model.Lane;
import com.conduent.tollposting.model.PlanPolicy;
import com.conduent.tollposting.model.Plaza;
import com.conduent.tollposting.model.ProcessParameters;
import com.conduent.tollposting.model.TollPostLimit;
import com.conduent.tollposting.service.IAgencyHolidayService;
import com.conduent.tollposting.service.IAgencyService;
import com.conduent.tollposting.service.ILaneService;
import com.conduent.tollposting.service.IPlanPolicyService;
import com.conduent.tollposting.service.IPlazaService;
import com.conduent.tollposting.service.IProcessParameterService;
import com.conduent.tollposting.service.ITollPostLimitService;

@Component
public class MasterCache 
{
	
	private static final Logger logger = LoggerFactory.getLogger(MasterCache.class);
	
	@Autowired
	private IAgencyService agencyService;

	@Autowired
	private IAgencyHolidayService agencyHolidayService;

	@Autowired
	private IPlazaService plazaService;

	@Autowired
	private ILaneService laneService;

	@Autowired
	private IPlanPolicyService planPolicyService;

	@Autowired
	private IProcessParameterService processParameterService;

	@Autowired
	private ITollPostLimitService tollPostLimitService;
	
	@Autowired
	private IAccountPlanDao iaAccountPlanDao;
	
	private List<AccountPlan> accountPlanList = new ArrayList<>();

	private List<Agency> agencyList;

	private List<AgencyHoliday> agencyHolidayList;

	private List<Plaza> plazaList;

	private List<Lane> laneList;

	private List<PlanPolicy> planPolicyList;

	private List<ProcessParameters> processParametersList;
	
	private List<ProcessParameters> processParametersICTX;
	
	private List<ProcessParameters> mtaRejAccountList;

	private List<TollPostLimit> tollPostLimitList;
	
	private Map<Integer,Long> systemAccount=new HashMap<Integer,Long>();
	
	private Map<Integer,Long> systemAccountICTX=new HashMap<Integer,Long>();
	
	private Map<String,Long> mtaSystemAccount=new HashMap<String,Long>();
	
	private Map<Integer, RevenueDateStatisticsDto> revenueStatsByAgencyMap = new HashMap<Integer, RevenueDateStatisticsDto>();
	
	private ProcessParameters tollAmountValue;
	
	public Long getSystemAccount(Integer plazaAgencyId)
	{
		return systemAccount.get(plazaAgencyId);
	}
	
	public Long getSystemAccountICTX(Integer agencyId)
	{
		return systemAccountICTX.get(agencyId);
	}
	
	public Long getMTASystemAccount(String name) {
		return mtaSystemAccount.get(name);
	}
	
	
	public Agency getAgency(Integer agencyId)
	{
		if(agencyId==null || agencyList==null)
		{
			return null;
		}
		Optional<Agency> agency=agencyList.stream().filter(e->e.getAgencyId().intValue()==agencyId.intValue()).findFirst();
		if(agency.isPresent())
		{
			return agency.get();
		}
		return null;
	}
	
	
	public Plaza getPlaza(Integer plazaId)
	{
		if(plazaId==null || plazaList==null)
		{
			return null;
		}
		Optional<Plaza> plaza=plazaList.stream().filter(e->e.getPlazaId().longValue()==plazaId.longValue()).findFirst();
		if(plaza.isPresent())
		{
			return plaza.get();
		}
		return null;
	}
		
	public AgencyHoliday getAgencyHoliday(Integer agencyId, LocalDate trxDate)
	{
		if(agencyHolidayList != null || !agencyHolidayList.isEmpty()) {
		Optional<AgencyHoliday> agencyHoliday=agencyHolidayList.stream().filter(e->e.getAgencyId().equals(agencyId) && 
				e.getHoliday().equals(trxDate)).findFirst();
		if(agencyHoliday.isPresent())
		{
			return agencyHoliday.get();
		} }
		return null;
	}

	/*
	public List<AccountPlan> getAccountPlan(Long etcAccountId,LocalDate txDate)
	{
		if(accountPlanList != null || !accountPlanList.isEmpty()) {
		List<AccountPlan> accountPlanListTemp=accountPlanList.stream().filter(e->e.getEtcAccountId().equals(etcAccountId) && 
				(e.getPlanStartDate().isBefore(txDate) || e.getPlanStartDate().isEqual(txDate))).collect(Collectors.toList());
		if(accountPlanListTemp.size()>0)
		{
			return accountPlanListTemp;
		} 
		}
		return null;
	}*/
	/*
	public AccountPlan getAccountDefaultPlan(Integer planType)
	{
		if(accountPlanList != null || !accountPlanList.isEmpty()) {
			Optional<AccountPlan> accountPlan = accountPlanList.stream().filter(x -> x.getPlanType().equals(planType)).findFirst();
		if(accountPlan.isPresent())
		{
			logger.info("PlanType {} is not present in ",planType);
			return accountPlan.get();
		} 
		}
		return null;
	}*/
	
	public PlanPolicy getPlanPolicy(Integer planType)
	{
		if(planType==null || planPolicyList==null)
		{
			return null;
		}
		Optional<PlanPolicy> planPolicy=planPolicyList.stream().filter(e->e.getPlanType().intValue()==planType).findFirst();
		if(planPolicy.isPresent())
		{
			return planPolicy.get();
		}
		return null;
	}
	
	public TollPostLimit getTollPostLimit(Integer plazaAgencyId,Integer txStatus)
	{
		if(plazaAgencyId==null || tollPostLimitList==null)
		{
			return null;
		}
		Optional<TollPostLimit> tollPostLimit=tollPostLimitList.stream().filter(e->e.getPlazaAgencyId().intValue()==plazaAgencyId.intValue() && e.getEtcTxStatus().intValue()==txStatus.intValue() ).findFirst();
		if(tollPostLimit.isPresent())
		{
			return tollPostLimit.get();
		}
		return null;
	}
	
	public Lane getLane(Integer plazaId,Integer laneId)
	{
		if(plazaId==null || laneId==null || laneList==null || laneList.isEmpty())
		{
			return null;
		}
		Optional<Lane> tollPostLimit=laneList.stream().filter(e->e.getLaneId().intValue()==laneId && e.getPlazaId().intValue()==plazaId).findFirst();
		if(tollPostLimit.isPresent())
		{
			return tollPostLimit.get();
		}else {
			laneList=laneService.getLane();
			tollPostLimit=laneList.stream().filter(e->e.getLaneId().intValue()==laneId && e.getPlazaId().intValue()==plazaId).findFirst();
			if(tollPostLimit.isPresent())
			{
				return tollPostLimit.get();
			}else {
				logger.debug("Lane not found after refreshing cache..");
				return null;
			}
		}
	}
	
	public RevenueDateStatisticsDto getRevenueStatusByPlaza(Integer plazaId) {
		RevenueDateStatisticsDto tempRevenueDateStatisticsDto = revenueStatsByAgencyMap.get(plazaId);
		if (tempRevenueDateStatisticsDto != null) {
			return tempRevenueDateStatisticsDto;
		} else {
			tempRevenueDateStatisticsDto = new RevenueDateStatisticsDto();
			Optional<Plaza> tempOptionalPlaza = plazaList.stream().filter(e -> plazaId.intValue()==e.getPlazaId().intValue())
					.findFirst();
			if (tempOptionalPlaza.isPresent()) {
				Plaza tempPlaza = tempOptionalPlaza.get();
				tempRevenueDateStatisticsDto.setPlazaId(tempPlaza.getPlazaId());
				tempRevenueDateStatisticsDto.setRevenueTime(tempPlaza.getRevenueTime());
				tempRevenueDateStatisticsDto.setRevenueSecondOfDay(
						LocalTime.parse("23:59:59", DateTimeFormatter.ofPattern("H:mm:ss")).toSecondOfDay());
				revenueStatsByAgencyMap.put(plazaId, tempRevenueDateStatisticsDto);
			}

		}
		return revenueStatsByAgencyMap.get(plazaId);
	}
	
	public Double getTollAmountValue()
	{
		return Double.valueOf(tollAmountValue.getParamValue());
	}

	@PostConstruct
	public void loadData()
	{
		logger.info("cache data loading started");
		
		LocalDateTime fromTime1 = LocalDateTime.now();
		tollPostLimitList=tollPostLimitService.getTollPostLimit();
		logger.debug("##M-SQL Time Taken for thread {} HOSTNAME: {} in tollPostLimitList: {} ms", Thread.currentThread().getName(),System.getenv("HOSTNAME"), ChronoUnit.MILLIS.between(fromTime1, LocalDateTime.now()));
		logger.info("Toll Post Limit data loading completed");
		
		LocalDateTime fromTime2 = LocalDateTime.now();
		planPolicyList=planPolicyService.getPlanPolicy();
		logger.debug("##M-SQL Time Taken for thread {} HOSTNAME: {} in planPolicyList: {} ms", Thread.currentThread().getName(),System.getenv("HOSTNAME"), ChronoUnit.MILLIS.between(fromTime2, LocalDateTime.now()));
		logger.info("Plan Policy data loading completed");
		
		LocalDateTime fromTime3 = LocalDateTime.now();
		plazaList=plazaService.getPlaza();
		logger.debug("##M-SQL Time Taken for thread {} HOSTNAME: {} in plazaList: {} ms", Thread.currentThread().getName(),System.getenv("HOSTNAME"), ChronoUnit.MILLIS.between(fromTime3, LocalDateTime.now()));
		logger.info("plaza data loading completed");
		
		LocalDateTime fromTime4 = LocalDateTime.now();
		laneList=laneService.getLane();
		logger.debug("##M-SQL Time Taken for thread {} HOSTNAME: {} in laneList: {} ms", Thread.currentThread().getName(),System.getenv("HOSTNAME"), ChronoUnit.MILLIS.between(fromTime4, LocalDateTime.now()));
		logger.info("lane data loading completed");
		
		LocalDateTime fromTime5 = LocalDateTime.now();
		agencyList=agencyService.getAgency();
		logger.debug("##M-SQL Time Taken for thread {} HOSTNAME: {} in agencyList: {} ms", Thread.currentThread().getName(),System.getenv("HOSTNAME"), ChronoUnit.MILLIS.between(fromTime5, LocalDateTime.now()));
		logger.info("agency data loading completed");
		
		LocalDateTime fromTime6 = LocalDateTime.now();
		processParametersList=processParameterService.getProcessParameters();
		logger.debug("##M-SQL Time Taken for thread {} HOSTNAME: {} in processParametersList: {} ms", Thread.currentThread().getName(),System.getenv("HOSTNAME"), ChronoUnit.MILLIS.between(fromTime6, LocalDateTime.now()));
		logger.info("process parameter loading completed");
		
		for(ProcessParameters p:processParametersList)
		{
			systemAccount.put(p.getAgencyId(), Long.parseLong(p.getParamValue()));
		}
		
		LocalDateTime fromTime7 = LocalDateTime.now();
		processParametersICTX = processParameterService.getProcessParametersICTX();
		logger.debug("##M-SQL Time Taken for thread {} HOSTNAME: {} in processParametersList: {} ms", Thread.currentThread().getName(),System.getenv("HOSTNAME"), ChronoUnit.MILLIS.between(fromTime7, LocalDateTime.now()));
		logger.info("process parameter loading completed");
		
		for(ProcessParameters p:processParametersICTX)
		{
			systemAccountICTX.put(p.getAgencyId(), Long.parseLong(p.getParamValue()));
		}
		
		mtaRejAccountList=processParameterService.getMTARejAccount();
		for(ProcessParameters p:mtaRejAccountList)
		{
			mtaSystemAccount.put(p.getParamDesc(), Long.parseLong(p.getParamValue()));
		}
		logger.info("mta process parameter loading completed");
		
		LocalDateTime fromTime8 = LocalDateTime.now();
		agencyHolidayList=agencyHolidayService.getAgencyHoliday();
		logger.debug("##M-SQL Time Taken for thread {} HOSTNAME: {} in agencyHolidayList: {} ms", Thread.currentThread().getName(),System.getenv("HOSTNAME"), ChronoUnit.MILLIS.between(fromTime8, LocalDateTime.now()));
		logger.info("Agency Holiday data loading completed");
		
	/*	LocalDateTime fromTime8 = LocalDateTime.now();
		accountPlanList=iaAccountPlanDao.getAccountPlanList();
		logger.info("Account Plan List loading completed"); 
		logger.debug("##M-SQL Time Taken for thread {} HOSTNAME: {} in accountPlanList: {} ms", Thread.currentThread().getName(),System.getenv("HOSTNAME"), ChronoUnit.MILLIS.between(fromTime8, LocalDateTime.now()));
	*/	  
		
		tollAmountValue = processParameterService.getTollAmountValue();
		
		logger.info("cache data loading completed..");
	}


}
