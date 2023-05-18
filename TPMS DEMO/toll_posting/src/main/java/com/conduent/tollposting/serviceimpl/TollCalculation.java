package com.conduent.tollposting.serviceimpl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.conduent.tollposting.constant.AccountType;
import com.conduent.tollposting.constant.AgencyId;
import com.conduent.tollposting.constant.ConfigVariable;
import com.conduent.tollposting.constant.Constants;
import com.conduent.tollposting.constant.LaneMode;
import com.conduent.tollposting.constant.PlanType;
import com.conduent.tollposting.constant.TollRevenueType;
import com.conduent.tollposting.constant.TxStatus;
import com.conduent.tollposting.dao.ITollScheduleDao;
import com.conduent.tollposting.dto.AccountInfoDTO;
import com.conduent.tollposting.dto.AccountTollPostDTO;
import com.conduent.tollposting.dto.TollCalculationDTO;
import com.conduent.tollposting.dto.TollCalculationResponseDTO;
import com.conduent.tollposting.dto.TollPostResponseDTO;
import com.conduent.tollposting.exception.NoRecordFoundException;
import com.conduent.tollposting.model.AccountPlan;
import com.conduent.tollposting.model.AgencyHoliday;
import com.conduent.tollposting.model.Device;
import com.conduent.tollposting.model.PlanPolicy;
import com.conduent.tollposting.model.Plaza;
import com.conduent.tollposting.model.TollPostLimit;
import com.conduent.tollposting.model.TollSchedule;
import com.conduent.tollposting.service.IAgencyHolidayService;
import com.conduent.tollposting.service.IDeviceService;
import com.conduent.tollposting.utility.MasterCache;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;

@Component
public class TollCalculation 
{

	@Autowired
	private TollScheduleService tollScheduleService;

	@Autowired
	private IAgencyHolidayService agencyHolidayService;

	@Autowired
	private MasterCache masterCache;	
	
	@Autowired
	private ITollScheduleDao tollScheduleDao;
	
	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private Gson gson;
	
	@Autowired
	protected ConfigVariable configVariable;
	
	@Autowired
	private IDeviceService deviceService;

	private static final Logger logger = LoggerFactory.getLogger(TollCalculation.class);

	public void tollSchedule(AccountInfoDTO accountInfo,AccountTollPostDTO tollPost,AccountPlan accountPlan) throws NoRecordFoundException
	{
		accountPlan.setTripPerTrx(1);
		accountPlan.setPriceScheduleId(0L);
		accountPlan.setActiveFlag("F");
		Integer revenueType=tollPost.getTollRevenueType();
		/*
		 * if((tollPost.getTxStatus().intValue()==2 ||
		 * tollPost.getTxStatus().intValue()==9) && (
		 * tollPost.getTollRevenueType().intValue()==9 ||
		 * tollPost.getTollRevenueType().intValue()==60)) { revenueType=1; }
		 */
		if(tollPost.getTollRevenueType()==null)
		{
			tollPost.setTollRevenueType(1);
			revenueType=1;
		}
		if(tollPost.getIsPeak()==null)
		{
			tollPost.setIsPeak("F");
		}
		Integer planType=null;

		if(accountPlan.getPlanType()==PlanType.MCLP.getCode() && tollPost.getLaneMode().intValue()!=LaneMode.MANUAL.getCode())
		{
			planType=PlanType.MCC.getCode();
		}
		else if(tollPost.getAccountType()==AccountType.COMMERCIAL.getCode() && tollPost.getPlazaAgencyId()==AgencyId.NY.getCode()  && accountPlan.getPlanType()==PlanType.NYCOML.getCode()) //:TODO && tollPost.getIsHomeAgency().equals("T")
		{
			planType=PlanType.BUSINESS.getCode();
		}
		else
		{
			planType=accountPlan.getPlanType();
		}
		int actualClass=tollPost.getActualClass();
		if(((accountPlan.getPlanType()==PlanType.NY12.getCode() || accountPlan.getPlanType()==PlanType.NYNREM.getCode() || accountPlan.getPlanType()==PlanType.NYNRL.getCode() || accountPlan.getPlanType()==PlanType.NYNRTH.getCode()) && (tollPost.getActualClass()==0 || tollPost.getActualClass()==9))
				|| ((accountPlan.getPlanType()==PlanType.STANDARD.getCode() || accountPlan.getPlanType()==PlanType.BUSINESS.getCode()) && tollPost.getActualClass()==0))
		{
			actualClass=1;
		}
		//AgencyHoliday agencyHoliday=agencyHoliday=masterCache.getAgencyHoliday(tollPost.getAccountAgencyId(), tollPost.getTxDate());
		if(tollPost.getTransSchedPrice()!=null && tollPost.getTransSchedPrice().equals("T"))
			{
				LocalDate txDate=tollPost.getTxDate();
				if((tollPost.getTollSystemType().equals("C") || tollPost.getTollSystemType().equals("T") || tollPost.getTollSystemType().equals("X")) && tollPost.getEntryTimestamp()!=null && tollPost.getAccountAgencyId()!=AgencyId.NY.getCode())
				{
					//:TODO txDate=tollPost.geten;
				}
				//AgencyHoliday agencyHoliday=agencyHolidayService.getAgencyHoliday(tollPost.getAccountAgencyId(), tollPost.getTxDate());
				AgencyHoliday agencyHoliday=masterCache.getAgencyHoliday(tollPost.getAccountAgencyId(), tollPost.getTxDate());
				List<TollSchedule> tollScheduleList=tollScheduleService.getTollAndPriceSchedule(txDate,tollPost.getPlazaAgencyId(),tollPost.getEntryPlazaId(),tollPost.getPlazaId(),actualClass);
				if(tollScheduleList!=null && !tollScheduleList.isEmpty()) /**If data found*/
				{
					
					TollSchedule tollSchedule=TollSchedule.getTollSchedule(tollScheduleList,revenueType,planType,tollPost.getIsPeak(),agencyHoliday!=null?agencyHoliday.getDaysInd():Constants.DEFAULT_DAYS_IND);
					if(tollSchedule!=null)
					{
						accountPlan.setFullFareAmount(tollSchedule.getFullFare());
						accountPlan.setDiscountAmount(tollSchedule.getDiscountFare());
						accountPlan.setExtraAxleCharge(tollSchedule.getExtraAxleCharge());
						accountPlan.setTripPerTrx(tollSchedule.getTripPerTrx());
						accountPlan.setPriceScheduleId(tollSchedule.getPriceScheduleId());
						accountPlan.setActiveFlag("T");
						if(accountInfo.getPostPaidFlag().equals("Y"))
						{
							tollSchedule=TollSchedule.getTollSchedule(tollScheduleList,TollRevenueType.ETC.getCode(),PlanType.STANDARD.getCode(),"F",agencyHoliday!=null?agencyHoliday.getDaysInd():Constants.DEFAULT_DAYS_IND);
							if(tollSchedule!=null)
							{
								accountPlan.setCollectedAmount(tollSchedule.getDiscountFare());
								accountPlan.setUnrealizedAmount(tollSchedule.getFullFare());
							}
						}
					}
					else
					{
						tollSchedule=TollSchedule.getTollSchedule(tollScheduleList,revenueType,planType,tollPost.getIsPeak(),"D");
						if(tollSchedule!=null)
						{
							accountPlan.setFullFareAmount(tollSchedule.getFullFare());
							accountPlan.setDiscountAmount(tollSchedule.getDiscountFare());
							accountPlan.setExtraAxleCharge(tollSchedule.getExtraAxleCharge());
							accountPlan.setTripPerTrx(tollSchedule.getTripPerTrx());
							accountPlan.setPriceScheduleId(tollSchedule.getPriceScheduleId());
							accountPlan.setActiveFlag("T");
							if(accountInfo.getPostPaidFlag().equals("Y"))
							{
								tollSchedule=TollSchedule.getTollSchedule(tollScheduleList,TollRevenueType.ETC.getCode(),PlanType.STANDARD.getCode(),"F");
								if(tollSchedule!=null)
								{
									accountPlan.setCollectedAmount(tollSchedule.getDiscountFare());
									accountPlan.setUnrealizedAmount(tollSchedule.getFullFare());
								}
							}
						}
						else
						{
							//:TODO
							// pa_etcmgr_apd_st->full_fare_amount = 0;
							// pa_etcmgr_apd_st->discount_amount = 0;
							// pa_etcmgr_apd_st->extra_axle_charge = 0;
							// pa_etcmgr_apd_st->trip_per_trx = 1;    
							// pa_etcmgr_apd_st->price_schedule_id = 0;    
							// pa_etcmgr_apd_st->is_peak = 'F';
						}
					}
				}
			}
		else
		{
			//change to api
			logger.info("Checking tollSchedule for LaneTxId {} txDate {} plazaId {} entryPlazaId {} actualClass {} revenueType {} planType {} ",tollPost.getLaneTxId(),
					tollPost.getTxDate(),tollPost.getPlazaId(),tollPost.getEntryPlazaId(),actualClass,revenueType,planType);
			List<TollSchedule> tollScheduleList=tollScheduleService.getTollSchedule(tollPost.getTxDate(),tollPost.getPlazaId(),tollPost.getEntryPlazaId(),actualClass);
			if(tollScheduleList!=null && !tollScheduleList.isEmpty()) /**If data found*/
			{
				AgencyHoliday agencyHoliday=masterCache.getAgencyHoliday(tollPost.getAccountAgencyId(), tollPost.getTxDate());
				Long priceSchedId=tollScheduleDao.getTollSchedPriceObj(tollPost.getTxDate(),tollPost.getPlazaAgencyId(), tollPost.getEntryPlazaId(), tollPost.getTxTimestamp(),agencyHoliday!=null?agencyHoliday.getDaysInd():Constants.DEFAULT_DAYS_IND);
				TollSchedule tollSchedule=TollSchedule.getTollSchedule(tollScheduleList,revenueType,planType,priceSchedId,tollPost.getPlazaId());
				if(tollSchedule!=null)
				{
					accountPlan.setFullFareAmount(tollSchedule.getFullFare());
					accountPlan.setDiscountAmount(tollSchedule.getDiscountFare());
					accountPlan.setExtraAxleCharge(tollSchedule.getExtraAxleCharge());
					accountPlan.setPriceScheduleId(tollSchedule.getPriceScheduleId());
					accountPlan.setTripPerTrx(tollSchedule.getTripPerTrx()!=null && tollSchedule.getTripPerTrx()<=0?1:tollSchedule.getTripPerTrx());
					accountPlan.setActiveFlag("T");
					if(accountInfo.getPostPaidFlag()!=null && accountInfo.getPostPaidFlag().equals("Y"))
					{
						tollSchedule=TollSchedule.getTollSchedule(tollScheduleList,TollRevenueType.ETC.getCode(),PlanType.STANDARD.getCode());
						if(tollSchedule!=null)
						{
							accountPlan.setCollectedAmount(tollSchedule.getDiscountFare());
							accountPlan.setUnrealizedAmount(tollSchedule.getFullFare());
						}
					}
				}
			}
			else
			{
				accountPlan.setActiveFlag("F");
				logger.info("tollScheduleList is null or empty: {}", tollScheduleList);
				/**71959 UAT bug**/
				if (!(tollPost.getTxTypeInd().equals("U") || tollPost.getTxTypeInd().equals("V"))
						&& !(tollPost.getTxSubtypeInd().equals("X") && tollPost.getTollSystemType().equals("X"))) {
				throw new NoRecordFoundException("No record found in t_toll_schedule..");
				}
			}
		}
	}

	public AccountPlan accountPlanSection(List<AccountPlan> accountPlanList)
	{
		AccountPlan accountPlan=null;
		boolean isLowest=true;
		for(AccountPlan obj:accountPlanList)
		{
			if(obj.getActiveFlag().equals("T")) // :TODO active column not exist in database table
			{
				if(isLowest)
				{
					accountPlan=obj;
					isLowest=false;
				}
				else if(obj.getDiscountAmount()<=accountPlan.getDiscountAmount())
				{
					if(obj.getDiscountAmount().equals(accountPlan.getDiscountAmount()))
					{
						AccountPlan accountPlan1=obj;
						if(masterCache.getPlanPolicy(accountPlan.getPlanType()).getIsCommute().equals("T") &&
								!masterCache.getPlanPolicy(accountPlan1.getPlanType()).getIsCommute().equals("T"))
						{
							continue;
						}
					}
					else
					{
						accountPlan=obj;
					}
				}
			}
		}
		return accountPlan;
	}

	public Long systemAccountSelection(AccountTollPostDTO toll)
	{
		Long systemAccount=null;
		Plaza plaza=masterCache.getPlaza(toll.getPlazaId());
		String externPlaza=plaza.getExternPlazaId();
		if(toll.getPlazaAgencyId().equals(Constants.AGENCY_MTA) && (externPlaza.equals("06") || externPlaza.equals("26")) 
				&& (toll.getActualClass().intValue()==1 || toll.getActualClass().intValue()==9) &&
				(toll.getPlanTypeId().equals(PlanType.RR.getCode()) || toll.getPlanTypeId().equals(PlanType.RRT.getCode())))
		{
			systemAccount=masterCache.getMTASystemAccount("RR_SYSTEM_ACCOUNT");
		}
		if(toll.getPlazaAgencyId().equals(Constants.AGENCY_MTA) && (externPlaza.equals("11") || externPlaza.equals("30")) 
				&& (toll.getActualClass().intValue()==1 || toll.getActualClass().intValue()==2 || toll.getActualClass().intValue()==3
				|| toll.getActualClass().intValue()==15 || toll.getActualClass().intValue()==16) &&
				toll.getPlanTypeId().equals(PlanType.SIR.getCode()))
		{
			systemAccount=masterCache.getMTASystemAccount("SR_SYSTEM_ACCOUNT");
		}
		if(toll.getPlazaAgencyId().equals(Constants.AGENCY_MTA) && (externPlaza.equals("24")) 
				&& (toll.getActualClass().intValue()==1 || toll.getActualClass().intValue()==2 || toll.getActualClass().intValue()==3
				|| toll.getActualClass().intValue()==10 || toll.getActualClass().intValue()==15 || toll.getActualClass().intValue()==16) &&
				toll.getPlanTypeId().equals(PlanType.BR.getCode()))
		{
			systemAccount=masterCache.getMTASystemAccount("BR_SYSTEM_ACCOUNT");
		}
		if(toll.getPlazaAgencyId().equals(Constants.AGENCY_MTA) && (externPlaza.equals("26")) 
				&& (toll.getActualClass().intValue()==1 || toll.getActualClass().intValue()==2 || toll.getActualClass().intValue()==3
				|| toll.getActualClass().intValue()==10 || toll.getActualClass().intValue()==15 || toll.getActualClass().intValue()==16) &&
				toll.getPlanTypeId().equals(PlanType.QR.getCode()))
		{
			systemAccount=masterCache.getMTASystemAccount("QR_SYSTEM_ACCOUNT");
		}
		return systemAccount;
	}

	public void exceptionValidation(AccountTollPostDTO toll,TollPostResponseDTO tollPostResponce,TollException tollException)
	{
		TxStatus etcTxStatus=TxStatus.getByCode(tollPostResponce.getTxStatus());
		int tx_status = 0;
		String deviceNo = null;
		try
		{
			boolean tagNPST=false;
			boolean accNPST=false;
//			Device device=toll.getDevice();
			if(toll.getDeviceNo().length()==11)
			{
				String deviceNo14 = toll.getDeviceNo().replaceFirst("0", "00");
				deviceNo = deviceNo14.substring(0, 4).concat("00").concat(deviceNo14.substring(4,12));
			}
			else
			{
				deviceNo = toll.getDeviceNo();
			}
			Device haDevice = deviceService.getHADeviceByDeviceNo(deviceNo);
			if(etcTxStatus==null)
			{
				logger.info("{} TxStatus not configured for laneTxId {}",tollPostResponce.getTxStatus(),toll.getLaneTxId());
				return;
			}
			LocalDate postDate=LocalDate.now();
			Long txAge = ChronoUnit.DAYS.between(postDate, toll.getTxDate());
			if(toll.getTxTypeInd().equals("I") && toll.getTxSubtypeInd().equals("I"))
			{
				tx_status = TxStatus.TX_STATUS_PPST.getCode();
			}
			else if (toll.getTxTypeInd().equals("I") && toll.getTxSubtypeInd().equals("C"))
			{
				tx_status = TxStatus.TX_STATUS_POST.getCode();
			}
			TollPostLimit tollPostLimit=masterCache.getTollPostLimit(toll.getPlazaAgencyId(),tx_status);
			if(toll.getTxTypeInd().equals("I") || toll.getTxTypeInd().equals("P")) //:TODO && type_of_txns = TOLL_TRX
			{
//				switch(etcTxStatus)
//				{
//				case TX_STATUS_XLANE:
//					etcTxStatus=TxStatus.TX_STATUS_RJDP;
//					break;
//				case TX_STATUS_POACHING:
//					etcTxStatus=TxStatus.TX_STATUS_RJDP;
//					break;
//				case TX_STATUS_DUPL:
//					etcTxStatus=TxStatus.TX_STATUS_RJDP;
//					break;
//				case TX_STATUS_TAGINV:
//				case TX_STATUS_TAGLOST:
//				case TX_STATUS_TAGSTOLEN:
//				case TX_STATUS_TAGRETURNED:
//				case TX_STATUS_TAGDAMAGED:
//				case TX_STATUS_INVTAG:
//
////					if(device!=null && (device.getDeviceStatus().longValue()==3 || device.getDeviceStatus().longValue()==4))
//					if(haDevice!=null && (haDevice.getIagTagStatus().longValue()==3 || haDevice.getIagTagStatus().longValue()==4))
//					{
//						etcTxStatus=TxStatus.TX_STATUS_TAGB;
//					}
//					else
//					{
//						etcTxStatus=TxStatus.TX_STATUS_NPST;
//						tagNPST=true;
//					}
//					break;
//				case TX_STATUS_INVACC:
//				case TX_STATUS_INVACCLSP:
//				case TX_STATUS_INVACPEND:
//				case TX_STATUS_INVACCLOS:
//				case TX_STATUS_INVACRVKF:
////					if(device!=null && (device.getDeviceStatus().longValue()==3 || device.getDeviceStatus().longValue()==4))
//					if(haDevice!=null && (haDevice.getIagTagStatus().longValue()==3 || haDevice.getIagTagStatus().longValue()==4))
//					{
//						etcTxStatus=TxStatus.TX_STATUS_ACCB;
//					}
//					else
//					{
//						etcTxStatus=TxStatus.TX_STATUS_NPST;
//						accNPST=true;
//					}
//					break;
//				}	//end of switch case
				
				
				if(etcTxStatus.equals(TxStatus.TX_STATUS_XLANE) || etcTxStatus.equals(TxStatus.TX_STATUS_POACHING) || 
						etcTxStatus.equals(TxStatus.TX_STATUS_DUPL))
				{
					etcTxStatus=TxStatus.TX_STATUS_RJDP;
				}
				else if(etcTxStatus.equals(TxStatus.TX_STATUS_INVACC) || etcTxStatus.equals(TxStatus.TX_STATUS_INVACCLSP) || 
						etcTxStatus.equals(TxStatus.TX_STATUS_INVACPEND) || etcTxStatus.equals(TxStatus.TX_STATUS_INVACCLOS) || 
						etcTxStatus.equals(TxStatus.TX_STATUS_INVACRVKF))
				{
	////			if(device!=null && (device.getDeviceStatus().longValue()==3 || device.getDeviceStatus().longValue()==4))
					if(haDevice!=null && (haDevice.getIagTagStatus().longValue()==3 || haDevice.getIagTagStatus().longValue()==4))
					{
						etcTxStatus=TxStatus.TX_STATUS_ACCB;
					}
					else
					{
						etcTxStatus=TxStatus.TX_STATUS_NPST;
						accNPST=true;
					}
				}
				else if(etcTxStatus.equals(TxStatus.TX_STATUS_TAGINV) || etcTxStatus.equals(TxStatus.TX_STATUS_TAGLOST) || 
						etcTxStatus.equals(TxStatus.TX_STATUS_TAGSTOLEN) || etcTxStatus.equals(TxStatus.TX_STATUS_TAGRETURNED) || 
						etcTxStatus.equals(TxStatus.TX_STATUS_TAGDAMAGED) || etcTxStatus.equals(TxStatus.TX_STATUS_INVTAG))
				{
//					if(device!=null && (device.getDeviceStatus().longValue()==3 || device.getDeviceStatus().longValue()==4))
					if(haDevice!=null && (haDevice.getIagTagStatus().longValue()==3 || haDevice.getIagTagStatus().longValue()==4))
					{
						etcTxStatus=TxStatus.TX_STATUS_TAGB;
					}
					else
					{
						etcTxStatus=TxStatus.TX_STATUS_NPST;
						tagNPST=true;
					}
				}
				else if(etcTxStatus.equals(TxStatus.TX_STATUS_BEYOND))	//added this condition for bug 27454
				{
					etcTxStatus=TxStatus.TX_STATUS_OLD2;
				}
				
				//2nd if
				if(etcTxStatus.equals(TxStatus.TX_STATUS_NPST))
				{
					if((tollPostLimit != null && (txAge * -1) > tollPostLimit.getAllowedDays()) || txAge > 0)
					{
						if(tagNPST)
						{
							etcTxStatus=TxStatus.TX_STATUS_OLD2;
						}
						if(accNPST)
						{
							etcTxStatus=TxStatus.TX_STATUS_OLD1;
						}
					}
				}
				else if(etcTxStatus.equals(TxStatus.TX_STATUS_ACCB) || etcTxStatus.equals(TxStatus.TX_STATUS_TAGB))
				{
					if((tollPostLimit != null && (txAge * -1) > tollPostLimit.getAllowedDays()) || txAge > 0)
					{
						if(etcTxStatus.equals(TxStatus.TX_STATUS_ACCB))
						{
							etcTxStatus=TxStatus.TX_STATUS_OLD1;
						}
						else
						{
							etcTxStatus=TxStatus.TX_STATUS_OLD2;
						}
					}
				}
				logger.info("TX_STATUS set to {} for lane_tx_id {}",etcTxStatus.getCode(),tollPostResponce.getLaneTxId());
			}
			else if(toll.getTxTypeInd().equals("P") || toll.getTxTypeInd().equals("C"))
			{
				switch(etcTxStatus)
				{
				case TX_STATUS_TAGINV:
					etcTxStatus=TxStatus.TX_STATUS_TAGB;
					break;
				case TX_STATUS_TAGLOST:
					etcTxStatus=TxStatus.TX_STATUS_TAGB;
					break;
				case TX_STATUS_TAGSTOLEN:
					etcTxStatus=TxStatus.TX_STATUS_TAGB;
					break;
				case TX_STATUS_TAGRETURNED:
					etcTxStatus=TxStatus.TX_STATUS_TAGB;
					break;
				case TX_STATUS_TAGDAMAGED:
					etcTxStatus=TxStatus.TX_STATUS_TAGB;
					break;
				case TX_STATUS_INVTAG:
					etcTxStatus=TxStatus.TX_STATUS_TAGB;
					break;
				case TX_STATUS_INVACC:
					etcTxStatus=TxStatus.TX_STATUS_ACCC;
					break;
				case TX_STATUS_INVACCLSP:
					etcTxStatus=TxStatus.TX_STATUS_ACCC;
					break;
				case TX_STATUS_INVACPEND:
					etcTxStatus=TxStatus.TX_STATUS_ACCC;
					break;
				case TX_STATUS_INVACCLOS:
					etcTxStatus=TxStatus.TX_STATUS_ACCC;
					break;
				case TX_STATUS_INVACRVKF:
					etcTxStatus=TxStatus.TX_STATUS_ACCC;
					break;
				case TX_STATUS_XLANE:
					etcTxStatus=TxStatus.TX_STATUS_RJDP;
					break;
				case TX_STATUS_POACHING:
					etcTxStatus=TxStatus.TX_STATUS_RJDP;
					break;
				case TX_STATUS_DUPL:
					etcTxStatus=TxStatus.TX_STATUS_RJDP;
					break;
				case TX_STATUS_BEYOND:
					etcTxStatus=TxStatus.TX_STATUS_OLD3;
					break;
				case TX_STATUS_NOCC:
					etcTxStatus=TxStatus.TX_STATUS_NOCC;
					break;
				default:
					etcTxStatus=TxStatus.TX_STATUS_RINV;
				}
				if(etcTxStatus.equals(TxStatus.TX_STATUS_ACCB) || etcTxStatus.equals(TxStatus.TX_STATUS_TAGB))
				{
					if(txAge>tollPostLimit.getAllowedDays() || txAge>0)
					{
						etcTxStatus=TxStatus.TX_STATUS_OLD3;
					}
				}
				logger.info("TX_STATUS set to {} for lane_tx_id {}",etcTxStatus.getCode(),tollPostResponce.getLaneTxId());
			}
		}
		catch(Exception ex)
		{
			logger.error("Exceptin for laneTxId {} msg {}",toll.getLaneTxId(),ex.getMessage());
		}
		finally
		{
			tollException.setTxStatus(etcTxStatus.getCode());
			tollPostResponce.setTxStatus(etcTxStatus.getCode());
		}
	}

	public void tollScheduleApi(AccountInfoDTO accountInfo, AccountTollPostDTO toll, AccountPlan accountPlan) {
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		TollCalculationDTO tollObj = new TollCalculationDTO();
		tollObj.setActualClass(toll.getActualClass());
		tollObj.setAgencyId(toll.getPlazaAgencyId()); //agencyId or plazaAgencyId?
		tollObj.setEntryPlazaId(toll.getEntryPlazaId());
		tollObj.setExitPlazaId(toll.getPlazaId());
		tollObj.setLaneTxId(toll.getLaneTxId());
		tollObj.setPlanType(accountPlan.getPlanType());
		tollObj.setTollRevenueType(toll.getTollRevenueType());
		tollObj.setTxTimestamp(toll.getTxTimestamp());
		tollObj.setAccountType(toll.getAccountType());
		tollObj.setTollSystemType(toll.getTollSystemType());
		
		HttpEntity<String> entity = new HttpEntity<String>(gson.toJson(tollObj), headers);
		ResponseEntity<String> result;
		TollCalculationResponseDTO responseDTO;
		LocalDateTime from = LocalDateTime.now();
		logger.info("Calling toll api for laneTxId {}.", entity.toString());
		result = restTemplate.postForEntity(configVariable.getTollCalculationUri(), entity, String.class);
		
		logger.debug("2##.Time Taken for {} in getTollCalculationUri: {} ms",
				Thread.currentThread().getName(), ChronoUnit.MILLIS.between(from, LocalDateTime.now()));
		
		if (result.getStatusCodeValue() == 200) {
			JsonObject jsonObject = new Gson().fromJson(result.getBody(), JsonObject.class);
			logger.info("Got response for laneTxId {}. Response: {}", tollObj.getLaneTxId(), jsonObject);

			JsonElement element = jsonObject.get("amounts");
			if (!(element instanceof JsonNull)) {
				Gson gson = new Gson();
				responseDTO = gson.fromJson(jsonObject.getAsJsonObject("amounts"), TollCalculationResponseDTO.class);
				if (responseDTO != null) {
					accountPlan.setFullFareAmount(responseDTO.getFullFare());
					accountPlan.setDiscountAmount(responseDTO.getDiscountFare());
					accountPlan.setExtraAxleCharge(responseDTO.getExtraAxleCharge());
					accountPlan.setExtraAxleChargeCash(new Double(responseDTO.getExtraAxleChargeCash()).longValue());
					accountPlan.setPriceScheduleId(responseDTO.getPriceScheduleId());
					accountPlan.setActiveFlag("T");
					if(accountPlan.getPlanType()==400) {
						logger.info("Truck discount applicable for laneTxId {} ",toll.getLaneTxId());
						PlanPolicy planPolicy=masterCache.getPlanPolicy(tollObj.getPlanType());
						accountPlan.setCommuteFlag(planPolicy.getIsCommute().equalsIgnoreCase("T")?"T":"F");
						accountInfo.setSelectedPlan(accountPlan);
						accountInfo.setSelectedPlanType(400);
						accountInfo.setSelectedPlanPolicy(planPolicy);
						toll.setPlanTypeId(400);
						toll.getAccountInfoDTO().getSelectedPlan().setPlanType(400);
						toll.setAccountInfoDTO(accountInfo);
					}
					else if(accountPlan.getPlanType()==401) {				//added condition for US 9369			
						logger.info("Truck discount applicable for laneTxId {} ",toll.getLaneTxId());
						PlanPolicy planPolicy=masterCache.getPlanPolicy(tollObj.getPlanType());
						accountPlan.setCommuteFlag(planPolicy.getIsCommute().equalsIgnoreCase("T")?"T":"F");
						accountInfo.setSelectedPlan(accountPlan);
						accountInfo.setSelectedPlanType(401);
						accountInfo.setSelectedPlanPolicy(planPolicy);
						toll.setPlanTypeId(401);
						toll.getAccountInfoDTO().getSelectedPlan().setPlanType(401);
						toll.setAccountInfoDTO(accountInfo);
					}
				}
			} else {
				accountPlan.setActiveFlag("F");
				if(accountPlan.getPlanType()==400 || accountPlan.getPlanType()==401) {		//added condition for US 9369
					accountPlan.setPlanType(PlanType.BUSINESS.getCode());
				}
			}
		} else {
			JsonObject jsonObject = new Gson().fromJson(result.getBody(), JsonObject.class);
			logger.info("Got response {}", jsonObject);
			}
		}
		
	
}