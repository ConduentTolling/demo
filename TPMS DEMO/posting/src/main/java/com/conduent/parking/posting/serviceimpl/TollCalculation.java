package com.conduent.parking.posting.serviceimpl;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.conduent.parking.posting.constant.AccountType;
import com.conduent.parking.posting.constant.AgencyId;
import com.conduent.parking.posting.constant.Constants;
import com.conduent.parking.posting.constant.LaneMode;
import com.conduent.parking.posting.constant.PlanType;
import com.conduent.parking.posting.constant.TollRevenueType;
import com.conduent.parking.posting.constant.TxStatus;
import com.conduent.parking.posting.dao.ITollScheduleDao;
import com.conduent.parking.posting.dto.AccountInfoDTO;
import com.conduent.parking.posting.dto.AccountTollPostDTO;
import com.conduent.parking.posting.dto.TollPostResponseDTO;
import com.conduent.parking.posting.model.AccountPlan;
import com.conduent.parking.posting.model.AgencyHoliday;
import com.conduent.parking.posting.model.Device;
import com.conduent.parking.posting.model.Plaza;
import com.conduent.parking.posting.model.TollPostLimit;
import com.conduent.parking.posting.model.TollSchedule;
import com.conduent.parking.posting.service.IAgencyHolidayService;
import com.conduent.parking.posting.utility.MasterCache;

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



	private static final Logger logger = LoggerFactory.getLogger(TollCalculation.class);

	public void tollSchedule(AccountInfoDTO accountInfo,AccountTollPostDTO tollPost,AccountPlan accountPlan)
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
		if(toll.getPlazaAgencyId().equals(Constants.AGENCY_TB) && (externPlaza.equals("06") || externPlaza.equals("26")) 
				&& (toll.getActualClass().intValue()==1 || toll.getActualClass().intValue()==9) &&
				(toll.getPlanTypeId().equals(PlanType.RR.getCode()) || toll.getPlanTypeId().equals(PlanType.RRT.getCode())))
		{
			systemAccount=masterCache.getSystemAccount(toll.getAccountAgencyId());
		}
		if(toll.getPlazaAgencyId().equals(Constants.AGENCY_TB) && (externPlaza.equals("11") || externPlaza.equals("30")) 
				&& (toll.getActualClass().intValue()==1 || toll.getActualClass().intValue()==2 || toll.getActualClass().intValue()==3
				|| toll.getActualClass().intValue()==15 || toll.getActualClass().intValue()==16) &&
				toll.getPlanTypeId().equals(PlanType.SIR.getCode()))
		{
			systemAccount=masterCache.getSystemAccount(toll.getAccountAgencyId());
		}
		if(toll.getPlazaAgencyId().equals(Constants.AGENCY_TB) && (externPlaza.equals("24")) 
				&& (toll.getActualClass().intValue()==1 || toll.getActualClass().intValue()==2 || toll.getActualClass().intValue()==3
				|| toll.getActualClass().intValue()==10 || toll.getActualClass().intValue()==15 || toll.getActualClass().intValue()==16) &&
				toll.getPlanTypeId().equals(PlanType.BR.getCode()))
		{
			systemAccount=masterCache.getSystemAccount(toll.getAccountAgencyId());
		}
		if(toll.getPlazaAgencyId().equals(Constants.AGENCY_TB) && (externPlaza.equals("26")) 
				&& (toll.getActualClass().intValue()==1 || toll.getActualClass().intValue()==2 || toll.getActualClass().intValue()==3
				|| toll.getActualClass().intValue()==10 || toll.getActualClass().intValue()==15 || toll.getActualClass().intValue()==16) &&
				toll.getPlanTypeId().equals(PlanType.QR.getCode()))
		{
			systemAccount=masterCache.getSystemAccount(toll.getAccountAgencyId());
		}
		return systemAccount;
	}

	public void exceptionValidation(AccountTollPostDTO toll,TollPostResponseDTO tollPostResponce,TollException tollException)
	{
		TxStatus etcTxStatus=TxStatus.getByCode(tollPostResponce.getTxStatus());
		try
		{
			boolean tagNPST=false;
			boolean accNPST=false;
			Device device=toll.getDevice();
			if(etcTxStatus==null)
			{
				logger.info("{} TxStatus not configured for laneTxId {}",tollPostResponce.getTxStatus(),toll.getLaneTxId());
				return;
			}
			LocalDate postDate=LocalDate.now();
			Long txAge = ChronoUnit.DAYS.between(postDate, toll.getTxDate());
			TollPostLimit tollPostLimit=masterCache.getTollPostLimit(toll.getPlazaAgencyId(),tollPostResponce.getTxStatus());
			if(toll.getTxTypeInd().equals("I") || toll.getTxTypeInd().equals("P")) //:TODO && type_of_txns = TOLL_TRX
			{
				switch(etcTxStatus)
				{
				case TX_STATUS_XLANE:
				case TX_STATUS_POACHING:
				case TX_STATUS_DUPL:
					etcTxStatus=TxStatus.TX_STATUS_RJDP;
					break;
				case TX_STATUS_TAGINV:
				case TX_STATUS_TAGLOST:
				case TX_STATUS_TAGSTOLEN:
				case TX_STATUS_TAGRETURNED:
				case TX_STATUS_TAGDAMAGED:
				case TX_STATUS_INVTAG:

					if(device!=null && (device.getDeviceStatus().longValue()==3 || device.getDeviceStatus().longValue()==4))
					{
						etcTxStatus=TxStatus.TX_STATUS_TAGB;
					}
					else
					{
						etcTxStatus=TxStatus.TX_STATUS_NPST;
						tagNPST=true;
					}
					break;
				case TX_STATUS_INVACC:
				case TX_STATUS_INVACCLSP:
				case TX_STATUS_INVACPEND:
				case TX_STATUS_INVACCLOS:
				case TX_STATUS_INVACRVKF:
					if(device!=null && (device.getDeviceStatus().longValue()==3 || device.getDeviceStatus().longValue()==4))
					{
						etcTxStatus=TxStatus.TX_STATUS_ACCB;
					}
					else
					{
						etcTxStatus=TxStatus.TX_STATUS_NPST;
						accNPST=true;
					}
					break;
				}
				if(etcTxStatus.equals(TxStatus.TX_STATUS_NPST))
				{
					if(txAge>tollPostLimit.getAllowedDays() || txAge>0)
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
					if(txAge>tollPostLimit.getAllowedDays() || txAge>0)
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
			}
			else if(toll.getTxTypeInd().equals("P") || toll.getTxTypeInd().equals("C"))
			{
				switch(etcTxStatus)
				{
				case TX_STATUS_TAGINV:
				case TX_STATUS_TAGLOST:
				case TX_STATUS_TAGSTOLEN:
				case TX_STATUS_TAGRETURNED:
				case TX_STATUS_TAGDAMAGED:
				case TX_STATUS_INVTAG:
					etcTxStatus=TxStatus.TX_STATUS_TAGB;
					break;
				case TX_STATUS_INVACC:
				case TX_STATUS_INVACCLSP:
				case TX_STATUS_INVACPEND:
				case TX_STATUS_INVACCLOS:
				case TX_STATUS_INVACRVKF:
					etcTxStatus=TxStatus.TX_STATUS_ACCC;
					break;
				case TX_STATUS_XLANE:
				case TX_STATUS_POACHING:
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
			}
		}
		catch(Exception ex)
		{
			logger.error("Exceptin for laneTxId {} msg {}",toll.getLaneTxId(),ex.getMessage());
		}
		finally
		{
			tollException.setTxStatus(etcTxStatus.getCode());
		}
	}


}