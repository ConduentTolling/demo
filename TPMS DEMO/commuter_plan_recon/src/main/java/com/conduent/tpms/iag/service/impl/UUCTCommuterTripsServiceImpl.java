package com.conduent.tpms.iag.service.impl;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.jvnet.hk2.annotations.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.conduent.tpms.iag.constants.UUCTConstants;
import com.conduent.tpms.iag.dao.impl.CommuterTripDaoImpl;
import com.conduent.tpms.iag.dto.PostUsagePlanDetailDto;
import com.conduent.tpms.iag.dto.TProcessParamterDto;
import com.conduent.tpms.iag.exception.InvalidDataException;
import com.conduent.tpms.iag.exception.InvalidPlanTypeException;
import com.conduent.tpms.iag.model.APIResponse;
import com.conduent.tpms.iag.model.AdjustmentResponseVO;
import com.conduent.tpms.iag.model.ConfigVariable;
import com.conduent.tpms.iag.model.FPMSAPIInputsforCommuterTrips;
import com.conduent.tpms.iag.model.FPMSAPIInputsforDiscountPlan;
import com.conduent.tpms.iag.model.TODBatchUser;
import com.conduent.tpms.iag.model.TODOutput;
import com.conduent.tpms.iag.model.TTripHistory;
import com.conduent.tpms.iag.service.UUCTCommuterTripsService;
import com.google.gson.Gson;

import com.google.gson.JsonElement;
import com.google.gson.JsonNull;


import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

/**
 * 
 * Service for UUCT Processing
 * 
 * @author taniyan
 *
 */
@Service
@Component
public class UUCTCommuterTripsServiceImpl implements UUCTCommuterTripsService {

	private static final Logger log = LoggerFactory.getLogger(UUCTCommuterTripsServiceImpl.class);

	@Autowired
	CommuterTripDaoImpl commuteTripdaoimpl;

	@Autowired
	TODCreationServiceImpl todCreationServiceimpl;

	@Autowired
	ConfigVariable configVariable;

	@Autowired
	RestTemplate restTemplate;

	protected List<TTripHistory> tripHistoryInfoList = new ArrayList<>();
	protected List<TTripHistory> tripHistoryInfoListPost = new ArrayList<>();
	protected List<PostUsagePlanDetailDto> planPolicyDetails = new ArrayList<>();
	protected List<TProcessParamterDto> processparamDetails = new ArrayList<>();
	protected double feeByTrip;
	protected double feeByFullFareRate;
	protected String invoiceNo;
	protected APIResponse apiResponse;
	protected AdjustmentResponseVO response;
	protected TODOutput todOutput = null;
	protected String checkIfRecordExist;
	Optional<TProcessParamterDto> commuterparamters;
	Optional<TProcessParamterDto> discountparamters;
	Optional<TProcessParamterDto> bytripforcommuparamters;
	Optional<TProcessParamterDto> byamountfordiscparamters;

	private long recordnotprocessd = 0;
    private long recordprocessed =0;
    private long recordprocessedforusagepost =0;


	public void initialize() {
		feeByTrip = 0;
		feeByFullFareRate = 0;
	}

	public void processingCommuterTrips() {
		try 
		{
			processparamDetails = commuteTripdaoimpl.getProcessParamtersList();
			getParamtersInfo(processparamDetails);
			
			PostUsagePlanDetailDto planinfo= commuteTripdaoimpl.getPostUsagePlanDetailsAccToPlan("TVD");
			
			// getting all trip details
			tripHistoryInfoList = commuteTripdaoimpl.getTripHistoryDetails();
			log.info("Trip Details..{}", tripHistoryInfoList);
			
			
			tripHistoryInfoListPost = commuteTripdaoimpl.getTripForPostUsage();
			log.info("Trip Details..{}", tripHistoryInfoListPost);

			// getting plan policy details
			planPolicyDetails = commuteTripdaoimpl.getPostUsagePlanDetails();
			log.info("Plan Ploicy Details..{}", planPolicyDetails);
			
			log.info("Param value from T_PROCESS_PARAMTERS {}  for Param Name {} ",commuterparamters.get().getParam_value(),commuterparamters.get().getParam_name());
			log.info("Param value from T_PROCESS_PARAMTERS {}  for Param Name {} ",discountparamters.get().getParam_value(),discountparamters.get().getParam_name());
			
			// fee to be charged depending upon plan type
			//if (commuterparamters.isPresent() && commuterparamters.get().getParam_value().equals(configVariable.getProcessparameterY()))
			
				// calculating fees for the commuter trips
			
			
				feesToBeChargedforCommuterTrips(tripHistoryInfoList);
				
				
				//processparamDetails.clear();
			 
				/*
				 * else if (discountparamters.isPresent() &&
				 * discountparamters.get().getParam_value().equals(configVariable.
				 * getProcessparameterY())) { // calculating fees for the post usage discount
				 * plan //feesToBeChargedforDiscountPlan(planPolicyDetails,
				 * tripHistoryInfoList); feesToBeChargedforDiscountPlan(planPolicyDetails,
				 * tripHistoryInfoListPost); //processparamDetails.clear(); } else {
				 * log.info("Invalid Plan Type to be used.."); throw new
				 * InvalidPlanTypeException("Invalid Plan Type"); }
				 */
		} catch (Exception e) 
		{
			log.info("Exception occured...", e);
			e.getMessage();
		}finally {
			log.info("uuct done");
			recordprocessed =0;
			recordprocessedforusagepost=0;
		}

	}

	public void processingPostUsageTrip() {
		try 
		{
			processparamDetails = commuteTripdaoimpl.getProcessParamtersList();
			getParamtersInfo(processparamDetails);
			
			PostUsagePlanDetailDto planinfo= commuteTripdaoimpl.getPostUsagePlanDetailsAccToPlan("TVD");
			
			// getting all trip details
			tripHistoryInfoList = commuteTripdaoimpl.getTripHistoryDetails();
			log.info("Trip Details..{}", tripHistoryInfoList);
			
			
			tripHistoryInfoListPost = commuteTripdaoimpl.getTripForPostUsage();
			log.info("Trip Details..{}", tripHistoryInfoListPost);

			// getting plan policy details
			planPolicyDetails = commuteTripdaoimpl.getPostUsagePlanDetails();
			log.info("Plan Ploicy Details..{}", planPolicyDetails);
			
			log.info("Param value from T_PROCESS_PARAMTERS {}  for Param Name {} ",commuterparamters.get().getParam_value(),commuterparamters.get().getParam_name());
			log.info("Param value from T_PROCESS_PARAMTERS {}  for Param Name {} ",discountparamters.get().getParam_value(),discountparamters.get().getParam_name());
			
			// fee to be charged depending upon plan type
			
			//if (discountparamters.isPresent() && discountparamters.get().getParam_value().equals(configVariable.getProcessparameterY()))
			
				// calculating fees for the post usage discount plan
				//feesToBeChargedforDiscountPlan(planPolicyDetails, tripHistoryInfoList);
			
				feesToBeChargedforDiscountPlan(planPolicyDetails, tripHistoryInfoListPost);
				
				//processparamDetails.clear();
			
				/*
				 * else { log.info("Invalid Plan Type to be used.."); throw new
				 * InvalidPlanTypeException("Invalid Plan Type"); }
				 */
		} catch (Exception e) 
		{
			log.info("Exception occured...", e);
			e.getMessage();
		}finally {
			log.info("uuct done");
			recordprocessed =0;
			recordprocessedforusagepost=0;
		}

	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	private void getParamtersInfo(List<TProcessParamterDto> processparamDetails2) 
	{
		//processparamDetails = masterdata.getParametersInfoVOList();
		
		commuterparamters = processparamDetails2.stream().filter(e -> e.getParam_name().equals(UUCTConstants.UUCT_COMMUTER_CHARGE)).findAny();
		discountparamters = processparamDetails2.stream().filter(e -> e.getParam_name().equals(UUCTConstants.UUCT_POST_USAGE_DISCOUNT)).findAny();
		bytripforcommuparamters = processparamDetails2.stream().filter(e -> e.getParam_name().equals(UUCTConstants.UUCT_COMMU_CHARGE_BY_TRIP)).findAny();
		byamountfordiscparamters = processparamDetails2.stream().filter(e -> e.getParam_name().equals(UUCTConstants.UUCT_POST_USAGE_DISC_BY_AMT)).findAny();
	}

	private void feesToBeChargedforCommuterTrips(List<TTripHistory> tripDetailsList) throws InvalidDataException 
	{
		
		

		String numday=commuteTripdaoimpl.getnumdays();
		
		if(bytripforcommuparamters.isPresent())
		{
	
			for (TTripHistory tripInfo : tripDetailsList) 
			{
				//LocalDate caltripdate = tripInfo.getTripEndDate().plusDays(Long.valueOf(numday));
			    LocalDate caltripdate =	tripInfo.getTripEndDate();
				LocalDate curentdate = LocalDate.now();
				
				Period p = Period.between(caltripdate, curentdate);
				//String datediff = String.valueOf(Math.abs(p.getDays()));
				//int datediff =Math.abs(p.getDays());
				//String month= String.valueOf(p.getMonths());
				//String year = String.valueOf(p.getYears());
				//datediff >= (Integer.parseInt(numday)) && month.equals(0) && year.equals(0)
				
				long days =ChronoUnit.DAYS.between(caltripdate, curentdate);
				
				//long datediff = Math.abs(days);
				
				if(days > (Long.parseLong(numday)) ) {
					
					String accsuspended = commuteTripdaoimpl.getAccountsuspended(tripInfo.getEtcAccountId());
					
					if(accsuspended.equals("N")) {
						log.info("Calculation for fees to be charged for commuter trips....");
						
						//createstatistics(tripDetailsList);
						
						//recordprocessed++;
						
						if(bytripforcommuparamters.get().getParam_value().equals(configVariable.getProcessparameterY()))
						{
							feeChargedByTrip(tripInfo);
						}
						else if(bytripforcommuparamters.get().getParam_value().equals(configVariable.getProcessparameterN()))
						{
							feeChargedByFullFareRate(tripInfo);
						}
						//createstatistics(tripDetailsList);
					}else {
						
						//update 
						commuteTripdaoimpl.UpdateTTripHistory(tripInfo.getApdId(),tripInfo);
					}
					
				}else {
					recordnotprocessd++;
					
					//log.info(tripInfo.getEtcAccountId() + "Not processed");
				}
				
				
			} 
		
	}   else
    {
	      throw new InvalidDataException("Invalid Data in T_PROCESS PARAMETER Table");
     }
		createstatistics(tripDetailsList);
	}

	private void feesToBeChargedforDiscountPlan(List<PostUsagePlanDetailDto> planPolicyDetails,List<TTripHistory> tripHistoryInfoList2) throws InvalidDataException 
	{
		
		String numday=commuteTripdaoimpl.getnumdays();
		
		
		if(byamountfordiscparamters.isPresent())
		{	
			for (TTripHistory tripInfo : tripHistoryInfoList2) 
			{ 
				 LocalDate caltripdate =tripInfo.getTripEndDate();
				 LocalDate curentdate = LocalDate.now();
				
				
				 long days =ChronoUnit.DAYS.between(caltripdate, curentdate);
					
				     if(days > Long.parseLong(numday)) {
				    	 
				    	 //for (PostUsagePlanDetailDto planInfo : planPolicyDetails) 
							//{
								if(byamountfordiscparamters.get().getParam_value().equals(configVariable.getProcessparameterN()))
								{
									//check for duplicate request
									LocalDate tripenddate= tripInfo.getTripEndDate().plusDays(1);
									int month = tripenddate.getMonthValue();
									//paymentRecordStatus == false
									//boolean paymentRecordStatus = commuteTripdaoimpl.checkRecordExitsin_T_PAYMENT(tripInfo.getEtcAccountId());
									boolean transactionRecordStatus = commuteTripdaoimpl.checkRecordExitsin_T_TRANSACTION(tripInfo.getEtcAccountId(),month);
									
									String plannametrip = commuteTripdaoimpl.getPlanName(tripInfo.getPlanType());
									
									PostUsagePlanDetailDto planinfo= commuteTripdaoimpl.getPostUsagePlanDetailsAccToPlan(plannametrip);
									
									if(transactionRecordStatus == false)
									{
										//calculateFeeforUsageTypeTrip(tripInfo,planInfo);
										calculateFeeforUsageTypeTrip(tripInfo,planinfo);
									}
									else
									{
										log.info("Fee already Applied for etc_account_id : {}",tripInfo.getEtcAccountId());
									}
								}
								else if(byamountfordiscparamters.get().getParam_value().equals(configVariable.getProcessparameterY()))
								{
									//check for duplicate request
									//paymentRecordStatus == false
									//boolean paymentRecordStatus = commuteTripdaoimpl.checkRecordExitsin_T_PAYMENT(tripInfo.getEtcAccountId());
									LocalDate tripenddate= tripInfo.getTripEndDate().plusDays(1);
									int month = tripenddate.getMonthValue();
									boolean transactionRecordStatus = commuteTripdaoimpl.checkRecordExitsin_T_TRANSACTION(tripInfo.getEtcAccountId(),month);
									

									String plannametrip = commuteTripdaoimpl.getPlanName(tripInfo.getPlanType());
									
									PostUsagePlanDetailDto planinfo= commuteTripdaoimpl.getPostUsagePlanDetailsAccToPlan(plannametrip);
									
									if(  transactionRecordStatus == false)
									{
										calculateFeeforUsageTypeAmount(tripInfo,planinfo);
									}
									else
									{
										log.info("Fee already Applied for etc_account_id : {}",tripInfo.getEtcAccountId());
									}
								}
								else 
								{
									log.info("Invalid Recon Flag...");
									//throw new InvalidDataException("Invalid Recon Flag");
								}
							//}
					 
				      }else {
				    	 log.info("account not processed");
				      }
					
				
			}
			
			
		}
		else
		{
			throw new InvalidDataException("Invalid Data in T_PROCESS PARAMETER Table");
		}
		createstatisticsforpostusage(tripHistoryInfoList2);
	}


	private void createstatisticsforpostusage(List<TTripHistory> tripHistoryInfoList2) {
		// TODO Auto-generated method stub
		
		log.info("creating statisticsfile");
		try {
			LocalDateTime lt = LocalDateTime.now();
			DateTimeFormatter dft = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
			String filename =lt.format(dft).toString().concat("_postusage").concat(".rpt");
			File file = new File(configVariable.getRptFile(),filename);
			//Path p =Paths.get(configVariable.getRptFile());
			
			//p.toFile();
			//Account that are processed as satisfying the configuration days after trip end date
			FileWriter fw= new FileWriter(file);
			fw.write("Total Records fetched from table with reconflag=F  or reconDate is null and plantype TVD or VNC - "+tripHistoryInfoList2.size()+"\r\n");
			long diff= tripHistoryInfoList2.size() - Long.valueOf(recordnotprocessd);
			fw.write("Total records updated with reconFlag=T satisfying configuration days condition  - " +recordprocessedforusagepost );
			
			fw.flush();
			fw.close();
			log.info("file created report");
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace(); //log 
		}
		
	}

	public void calculateFeeforUsageTypeAmount(TTripHistory tripInfo,PostUsagePlanDetailDto planInfo2) throws InvalidDataException 
	{
			String planName = commuteTripdaoimpl.getPlanName(tripInfo.getPlanType());
			log.info("Plan Name for plan type {} is {}", tripInfo.getPlanType(), planName);
	
			if (planInfo2.getUsageType().equals(UUCTConstants.AMOUNT)&& planInfo2.getBenefitType().equals(UUCTConstants.PERCENTAGE)
					&& planName.equals(planInfo2.getPlanName())) 
			{
				feechargedforUsageTypeAmount(planInfo2, tripInfo,planName);
			} 
			else
			{
				log.info("Invalid data");
			}

		
	}

	public void calculateFeeforUsageTypeTrip(TTripHistory tripInfo,PostUsagePlanDetailDto planInfo) throws InvalidDataException 
	{

			String planName = commuteTripdaoimpl.getPlanName(tripInfo.getPlanType());
			log.info("Plan Name for plan type {} is {}", tripInfo.getPlanType(), planName);
			
			//planInfo.getUsageType().equals(UUCTConstants.TRIPS)&& planInfo.getBenefitType().equals(UUCTConstants.AMOUNT)
			//&& planName.equals(planInfo.getPlanName()) 
			//planInfo.getUsageType().equals(UUCTConstants.TRIPS)&& planInfo.getBenefitType().equals(UUCTConstants.PERCENTAGE)
			//&& (planName.equals(UUCTConstants.TVD) || planName.equals(UUCTConstants.VNC))
			if (planInfo.getUsageType().equals(UUCTConstants.TRIPS)&& planInfo.getBenefitType().equals(UUCTConstants.PERCENTAGE)
					    ) 
			{
				feechargedforUsageTypeTrips(planInfo, tripInfo,planName);
			} 
			else
			{
				log.info("Invalid data");
			}

		
		
	}

	public void feeChargedByTrip(TTripHistory tripInfo) throws InvalidDataException 
	{
		log.info("Fee to be charged by Trip...");

		int tripLeft = tripInfo.getTripsLeft();
		log.info("Trips Left..{}", tripLeft);

		// calculate fee
		if (tripLeft > 0) 
		{
			// calculate Discount rate
			//double discountRate = calulateDiscountRate(tripInfo.getFullFareAmount(), tripInfo.getDiscountedAmount());

			
			feeByTrip = (tripInfo.getDiscountedAmount()/tripInfo.getTripsMade()) * tripLeft ;
			log.info("Fee charged by trip is...{}", feeByTrip);

			apiResponse = calltoFPMSAPIforCommuterTrips(tripInfo.getEtcAccountId(), tripInfo.getPlanType(), feeByTrip);
			if (apiResponse != null) 
			{
				log.info("API Response is..{}", apiResponse.getStatus());

				log.info("Updating T_TRIP_HISTORY Table after calculating the financials...");

				commuteTripdaoimpl.UpdateTTripHistory(tripInfo.getApdId(),feeByTrip,tripInfo);
				recordprocessed++;
				
				checkaccountplan(tripInfo.getEtcAccountId(),tripInfo.getPlanType(),tripInfo.getTripStartDate(),tripInfo.getApdId(),tripInfo);

				//commuteTripdaoimpl.UpdateTTripHistory(tripInfo.getApdId());

			} 
			else 
			{
				log.info("Api Response is null ..{}", apiResponse);
			}
		} 
		else 
		{
			// update T_TRIP_HISTORY Table
			log.info("Updating T_TRIP_HISTORY Table for trip left...{} and APD ID is {}", tripLeft,tripInfo.getApdId());
			commuteTripdaoimpl.UpdateTTripHistoryforTripLeftZero(tripLeft, tripInfo.getApdId());
		}

	}


	private void checkaccountplan(int etc_act_id, int plan_type,LocalDate date, int apd_id,TTripHistory tripInfo) {
		// TODO Auto-generated method stub
		String acctstatus =commuteTripdaoimpl.getAccountStatus(etc_act_id);
		String planrenewal= commuteTripdaoimpl.getPlanRenew(plan_type);
		
		log.info("accountstatus {}",acctstatus );
		log.info("planrenewal {}" ,planrenewal);
		
		if(acctstatus.equals("ACTIVE") && planrenewal.equals("Y")) {
			checktriphistoryfornextperiod(etc_act_id,date,apd_id,tripInfo);
				
			
		}
		
		//checktriphistoryfornextperiod(etc_act_id,date,apd_id,tripInfo);
		
	}

	private void checktriphistoryfornextperiod(int etc_act_id,LocalDate date,int apd_id, TTripHistory tripInfo) {
		// TODO Auto-generated method stub
		
		
		log.info("check tirp history for next period");
		
		LocalDate nextmonthdate = date.plusMonths(1);
		int nextmonth = nextmonthdate.getMonthValue() ;
		//int month = date.getMonthValue();
		//int nextmonth =month+1;
		List<TTripHistory> a= commuteTripdaoimpl.checktripfornextperiod(etc_act_id,nextmonth);
		System.out.println("listszie"+a.size());
		if(a.isEmpty()) {
			//create trip history for next 
		
             commuteTripdaoimpl.inserttriphistorynextperiod(tripInfo);
		}
		
	}

	private void createstatistics(List<TTripHistory> tripDetailsList) {
		// TODO Auto-generated method stub
		
		log.info("creating statisticsfile");
		try {
			LocalDateTime lt = LocalDateTime.now();
			DateTimeFormatter dft = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
			String filename =lt.format(dft).toString().concat(".rpt");
			File file = new File(configVariable.getRptFile(),filename);
			//Path p =Paths.get(configVariable.getRptFile());
			
			//p.toFile();
			//Account that are processed as satisfying the configuration days after trip end date
			FileWriter fw= new FileWriter(file);
			fw.write("Total Records fetched from table with reconflag=F  or reconDate is null - "+tripDetailsList.size()+"\r\n");
			long diff= tripDetailsList.size() - Long.valueOf(recordnotprocessd);
			fw.write("Total records updated with reconFlag=T satisfying configuration days condition  - " +recordprocessed );
			
			fw.flush();
			fw.close();
			log.info("file created report");
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace(); //log 
		}
		
		
	}


	public void feeChargedByFullFareRate(TTripHistory tripinfo) throws InvalidDataException {
		log.info("Fee to be charged by Full Fare Rate...");

		if (tripinfo.getFullFareAmount() > (tripinfo.getDiscountedAmount())) 
		{
			// calculate Discount rate
			feeByFullFareRate = calulateDiscountRate(tripinfo.getFullFareAmount(), tripinfo.getDiscountedAmount());
			log.info("Fee charged by Full Fare Rate is {}", feeByFullFareRate);

			//query for already fee applied
			
			apiResponse = calltoFPMSAPIforCommuterTrips(tripinfo.getEtcAccountId(), tripinfo.getPlanType(),feeByFullFareRate);
			if (apiResponse != null) 
			{
				log.info("API Response is..{}", apiResponse.getStatus());

				log.info("Updating T_TRIP_HISTORY Table after calculating the financials...");

				commuteTripdaoimpl.UpdateTTripHistory(tripinfo.getApdId(),feeByFullFareRate,tripinfo);
				recordprocessed++;
				
				checkaccountplan(tripinfo.getEtcAccountId(),tripinfo.getPlanType(),tripinfo.getTripStartDate(),tripinfo.getApdId(),tripinfo);

				//commuteTripdaoimpl.UpdateTTripHistory(tripinfo.getApdId());

			} 
			else 
			{
				log.info("Api Response is null ..{}", apiResponse);
			}

		} 
		else if (tripinfo.getFullFareAmount() == (tripinfo.getDiscountedAmount())) 
		{
			log.info("Updating T_TRIP_HISTORY Table as Full Fare Amount and Discounted Amount are same...");
			commuteTripdaoimpl.UpdateTTripHistory(tripinfo.getApdId(),tripinfo);
		}
	}

	private double calulateDiscountRate(double fullFareAmount, double discountedAmount) throws InvalidDataException 
	{
		log.info("Calculating Discount Rate for Full Fare Amount {} and Discount Amount {} ", fullFareAmount,discountedAmount);

		double discountRateCalulated = 0;

		if (fullFareAmount > discountedAmount) 
		{
			discountRateCalulated = fullFareAmount - discountedAmount;
			log.info("Discount Rate is {}", discountRateCalulated);
		} 
		else 
		{
			log.info("Full Fare Amount is less than Discounted Amount");
			throw new InvalidDataException("Invalid Data");
		}
		
		return discountRateCalulated;
	}

	public void feechargedforUsageTypeAmount(PostUsagePlanDetailDto planInfo, TTripHistory tripInfo ,String planName)throws InvalidDataException 
	{
		if (tripInfo.getUsageAmount() >= planInfo.getMinUsage() && tripInfo.getUsageAmount() <= planInfo.getMaxUsage()) 
		{
			double amountnPercentValue = (tripInfo.getUsageAmount() * planInfo.getBenefitValue()) / 100;
			log.info("Amount charged for amount and percentage combination is: {}", amountnPercentValue);

			response = calltoFPMSAPIforDiscountPlan(tripInfo.getEtcAccountId(), tripInfo.getPlanType(),amountnPercentValue,planName);
			if (response != null) 
			{
				log.info("API Response is..{}", response.getStatus());

				log.info("Updating T_TRIP_HISTORY Table after calculating the financials...");
				//commuteTripdaoimpl.UpdateTTripHistory(tripInfo.getApdId());
				commuteTripdaoimpl.UpdateTTripHistory(tripInfo.getApdId(),amountnPercentValue,tripInfo);
				recordprocessedforusagepost++;
				checkaccountplan(tripInfo.getEtcAccountId(), tripInfo.getPlanType(), tripInfo.getTripStartDate(), tripInfo.getApdId(), tripInfo);
			} 
			else 
			{
				log.info("Api Response is null ..{}", response);
			}
		} 
		else 
		{
			throw new InvalidDataException("Data is invalid");
		}
	}

	public void feechargedforUsageTypeTrips(PostUsagePlanDetailDto planInfo2, TTripHistory tripInfo2 , String planName) throws InvalidDataException 
	{
		if (tripInfo2.getTripsMade() >= planInfo2.getMinUsage() && tripInfo2.getTripsMade() <= planInfo2.getMaxUsage()) 
		{
			//double tripsnAmountValue = planInfo2.getBenefitValue();
			double tripsnAmountValue = ( tripInfo2.getUsageAmount() * planInfo2.getBenefitValue())/ 100;
			log.info("Amount charged for trips and amount combination is: {}", tripsnAmountValue);

			response = calltoFPMSAPIforDiscountPlan(tripInfo2.getEtcAccountId(), tripInfo2.getPlanType(),tripsnAmountValue,planName);
			if (response != null) 
			{
				log.info("API Response is..{}", response.getStatus());

				log.info("Updating T_TRIP_HISTORY Table after calculating the financials...");
				//commuteTripdaoimpl.UpdateTTripHistory(tripInfo2.getApdId());
				commuteTripdaoimpl.UpdateTTripHistory(tripInfo2.getApdId(),tripsnAmountValue,tripInfo2);
				recordprocessedforusagepost++;
				checkaccountplan(tripInfo2.getEtcAccountId(), tripInfo2.getPlanType(), tripInfo2.getTripStartDate(), tripInfo2.getApdId(), tripInfo2);
			} 
			else 
			{
				log.info("Api Response is null ..{}", response);
			}
		} 
		else 
		{
			//throw new InvalidDataException("Data is invalid");
			log.info("tripsmade not satisfied"+ tripInfo2.getEtcAccountId());
		}
	}

	public APIResponse calltoFPMSAPIforCommuterTrips(Integer etcAccountId, Integer planType, double amount) {

		try 
		{
			// tod creation
			String todId = null;
			TODBatchUser todBatchUser = new TODBatchUser();
			todOutput = todCreationServiceimpl.buildTodId(todId, todBatchUser);
			log.info("TOD Info ...{}", todOutput);

			// FPMS API Call
			FPMSAPIInputsforCommuterTrips apiInputs = new FPMSAPIInputsforCommuterTrips();
			apiInputs.setEtcAccountId(etcAccountId);
			apiInputs.setCategory(UUCTConstants.CATEGORY);

			// String planType = commutertripdao.getPlanType(etcAccountId);
			String planName = commuteTripdaoimpl.getPlanName(planType);
			apiInputs.setSubcategory(UUCTConstants.UUCT.concat(planName));

			Integer map_agency_id = commuteTripdaoimpl.getMapAgencyID(planName);
			apiInputs.setAccountAgency(map_agency_id);

			String accountType = commuteTripdaoimpl.getAccountType(etcAccountId);
			apiInputs.setAccountType(accountType);

			log.info("Tod ID to be used in FPMS API call is..{}", todOutput.getTodId());
			apiInputs.setTodId(todOutput.getTodId());

			apiInputs.setReasonCode(UUCTConstants.REASON_CODE);
			apiInputs.setRebillType(UUCTConstants.REBILL_TYPE);
			apiInputs.setTranType(UUCTConstants.TRAN_TYPE);
			apiInputs.setDescription(UUCTConstants.DESCRIPTION);
			apiInputs.setAmount(amount);

			Date date = new Date();
			SimpleDateFormat formatter = new SimpleDateFormat("MMddyyyy");
			String refDate = formatter.format(date);

			String referenceId = apiInputs.getEtcAccountId().toString().concat(apiInputs.getCategory())
					.concat(apiInputs.getSubcategory()).concat(refDate);
			apiInputs.setReferenceId(referenceId);

			log.info(
					"API Inputs are: ETC_ACCOUNT_ID:{} ,ACCOUNT_AGENCY:{} ,CATEGORY:{} ,SUB_CATEGORY:{} ,ACCOUNT_TYPE:{} ,"
							+ "TOD_ID:{} ,REASON_CODE:{} ,TRAN_TYPE:{} ,REBILL_TYPE:{} ,DESCRIPTION:{} ,AMOUNT:{},REFERENCE_ID:{}",
					apiInputs.getEtcAccountId(), apiInputs.getAccountAgency(), apiInputs.getCategory(),
					apiInputs.getSubcategory(), apiInputs.getAccountType(), apiInputs.getTodId(),
					apiInputs.getReasonCode(), apiInputs.getTranType(), apiInputs.getRebillType(),
					apiInputs.getDescription(), apiInputs.getAmount(), apiInputs.getReferenceId());

			System.out.println(configVariable.getFpmsApiUri()+"    ");

			ResponseEntity<String> result = restTemplate.postForEntity(configVariable.getFpmsApiUri(), apiInputs,
					String.class);
			log.info("result.getBody() " + result.getBody());
			

			if (result.getStatusCodeValue() == 200 ) 
			{
				JsonObject jsonObject = new Gson().fromJson(result.getBody(), JsonObject.class);
				Gson gson = new Gson();
				JsonElement element = jsonObject.get("result");
				if (!(element instanceof JsonNull)) {
					return gson.fromJson(jsonObject.getAsJsonObject("result"), APIResponse.class);
				}
		
			}
			else 
			{
				log.info("Output after hitting the url is...{}", result);
			}
			
		} catch (RestClientException e) 
		{
			log.info("Exception: {} while get balance API call for ETC Account ID: {}", e.getMessage(), etcAccountId);

		} catch (JsonSyntaxException e) 
		{
			log.info("Exception: {} while get balance API call for ETC Account ID: {}", e.getMessage(), etcAccountId);
		}

		return null;
	}
	
		
		
	public AdjustmentResponseVO calltoFPMSAPIforDiscountPlan(Integer etcAccountId, Integer planType, double amount,String planName) {

		try {
			
			String todId = null;
			TODBatchUser todBatchUser = new TODBatchUser();
			todOutput = todCreationServiceimpl.buildTodId(todId, todBatchUser);
			log.info("TOD Info ...{}", todOutput);
			
			FPMSAPIInputsforDiscountPlan apiInputs = new FPMSAPIInputsforDiscountPlan();
			apiInputs.setEtcAccountId(etcAccountId);
			Integer account_id = commuteTripdaoimpl.getAccountAgencyID(etcAccountId);
			apiInputs.setAccountAgency(account_id);

			apiInputs.setQty(UUCTConstants.QTY);
			apiInputs.setUnitAmount(amount);
			apiInputs.setTotalAmount(amount);
			apiInputs.setCategory(UUCTConstants.TOLDEPOSIT);
			apiInputs.setSubcategory(UUCTConstants.POSTUSG.concat(planName)); 
			if(todOutput.getTodId() != null)
			{
				apiInputs.setTodId(todOutput.getTodId());
			}
			apiInputs.setReasonCode(UUCTConstants.DISCOUNT_PLAN);
			apiInputs.setTranType(UUCTConstants.T);

			log.info("API Inputs for Discount Plan is: ETC_ACCOUNT_ID:{}, ACCOUNT_AGENCY:{}, QTY:{}, UNIT_AMOUNT:{}, TOTAL_AMOUNT:{},"
							+ "CATEGORY:{}, SUB_CATEGORY:{}, TOD_ID:{}, REASON_CODE:{} , TRAN_TYPE : {}",
					apiInputs.getEtcAccountId(), apiInputs.getAccountAgency(), apiInputs.getQty(),
					apiInputs.getUnitAmount(), apiInputs.getTotalAmount(), apiInputs.getCategory(),
					apiInputs.getSubcategory(), apiInputs.getTodId(), apiInputs.getReasonCode() , apiInputs.getTranType());

			System.out.println(configVariable.getFpmsApiUridistplan());

			ResponseEntity<String> result = restTemplate.postForEntity(configVariable.getFpmsApiUridistplan(),
					apiInputs, String.class);
			log.info("result.getBody() " + result.getBody());

			if (result.getStatusCodeValue() == 200) 
			{
				JsonObject jsonObject = new Gson().fromJson(result.getBody(), JsonObject.class);
				Gson gson = new Gson();
				JsonElement element = jsonObject.get("result");
				if( !(element instanceof JsonNull)) {
					return gson.fromJson(jsonObject.getAsJsonObject("result"), AdjustmentResponseVO.class);
				}
				//return gson.fromJson(jsonObject.getAsJsonObject("result"), AdjustmentResponseVO.class);
				
				
				
				
				
			
				
					
				
			} 
			else 
			{
				log.info("Output after hitting the url is...{}", result);
			}
		} catch (RestClientException e) {
			log.info("Exception: {} while get balance API call for ETC Account ID: {}", e.getMessage(), etcAccountId);

		} catch (JsonSyntaxException e) {
			log.info("Exception: {} while get balance API call for ETC Account ID: {}", e.getMessage(), etcAccountId);
		}

		return null;
	}

}
