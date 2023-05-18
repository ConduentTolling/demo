package com.conduent.tpms.unmatched.serviceImpl;

import java.text.ParseException;
import java.time.LocalDate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.conduent.tpms.umatched.exception.TPMSGlobalException;
import com.conduent.tpms.unmatched.dao.MaxTollDao;
import com.conduent.tpms.unmatched.dao.MaxTollDaoCRM;
import com.conduent.tpms.unmatched.dto.THighwaySection;
import com.conduent.tpms.unmatched.model.APIInputs;
import com.conduent.tpms.unmatched.model.AgencyHoliday;
import com.conduent.tpms.unmatched.model.SessionIdInputs;
import com.conduent.tpms.unmatched.model.TranDetail;
import com.conduent.tpms.unmatched.service.GetMaxTollAmountCRMService;
import com.conduent.tpms.unmatched.utility.Validation;

@Service
public class GetMaxTollAmountCRMServiceImpl implements GetMaxTollAmountCRMService{
	
	private static final Logger log = LoggerFactory.getLogger(GetMaxTollAmountServiceImpl.class);
	
	@Autowired
	MaxTollDaoCRM maxTollDaoCRM;
	
	@Autowired
	Validation validation;

	TranDetail trandetailInfo = new TranDetail();
	SessionIdInputs info = new SessionIdInputs();
	THighwaySection terminalInfo = new THighwaySection();
	AgencyHoliday agencyHoliday = new AgencyHoliday();
	public int priceScheduleId = 0;
	Double terminalPlaza1Info;
	Double terminalPlaza2Info;
	Double terminalPlaza3Info;
	
	@Override
	public double getMaxTollAmount(APIInputs inputs) throws ParseException
	{
		if(inputs.getEtcAccountId()!=null && inputs.getLaneTxId()!=null)
		{
			if(validation.numberFormatValidation(inputs.getEtcAccountId().toString()) && validation.numberFormatValidation(inputs.getLaneTxId().toString()))
			{
				double maxAmount = process(inputs);
				return maxAmount;
			}
			else
			{
				throw new TPMSGlobalException("ETC_ACCOUNT_ID and LANE_TX_ID should be in numeric format", null, null);
			}
			
		}
		else
		{
			throw new TPMSGlobalException("ETC_ACCOUNT_ID and LANE_TX_ID are mandatory", null, null);
		}
	}

	
	public double process(APIInputs inputs) throws ParseException 
	{		
		// load data from T_TRAN_DETAIL in ttinputs
		TranDetail trandetailInfo = maxTollDaoCRM.getTrxnInfo(inputs.getEtcAccountId(),inputs.getLaneTxId());
		if(trandetailInfo !=null)
		{
			log.info("Transaction Info fetched from T_TRAN_DETAIL : {}",trandetailInfo.toString());
		}
		else
		{
			throw new TPMSGlobalException("No record present in T_TRAN_DETAIL Table", null, null);
		}
		
		if(trandetailInfo.getLaneId()!=null || inputs.getLaneId()!=null)
		{
			info = maxTollDaoCRM.getSectionId(trandetailInfo,inputs);
			if(info !=null)
			{
				log.info("Session Id Info fetched : {}",info.toString());
			}
			
			if(info.getSectionId() > 0 && trandetailInfo.getTxDate() != null)
			{
				//get terminal_plaza_id_1 and 2 info
				terminalInfo = maxTollDaoCRM.getTerminalPlazaInfo(info.getSectionId());
				
				//days of week
				agencyHoliday = maxTollDaoCRM.getAgencyHoliday(trandetailInfo,inputs);
				//log.info("Days Ind for lane_tx_id {} is {}",trandetailInfo.getLaneTxId(),agencyHoliday.getDaysInd());
				
				if(info.getScheduledPricingFlag().equals("Y"))
				{
					if (agencyHoliday != null) //if the Date is AgencyHoliday
					{
						log.debug("Information from Agency Holiday Table {}", agencyHoliday.toString());
						priceScheduleId = maxTollDaoCRM.getPriceScheduleId(inputs, trandetailInfo,agencyHoliday.getDaysInd());
					
						if(priceScheduleId>0 && terminalInfo!=null) 
						{
							log.info("Terminal Plaza Info {}",terminalInfo.toString());
							terminalPlaza1Info = maxTollDaoCRM.getDiscountFare(trandetailInfo, terminalInfo.getTerminalPlazaId_1(), priceScheduleId,inputs);
							if(terminalPlaza1Info==0)
							{
								priceScheduleId = maxTollDaoCRM.getPriceScheduleId(inputs, trandetailInfo,"D");
								terminalPlaza1Info = maxTollDaoCRM.getDiscountFare(trandetailInfo, terminalInfo.getTerminalPlazaId_1(), priceScheduleId,inputs);
							}
							
							terminalPlaza2Info = maxTollDaoCRM.getDiscountFarePlaza2(trandetailInfo, terminalInfo.getTerminalPlazaId_2(), priceScheduleId,inputs);
							if(terminalPlaza2Info==0)
							{
								priceScheduleId = maxTollDaoCRM.getPriceScheduleId(inputs, trandetailInfo,"D");
								terminalPlaza2Info = maxTollDaoCRM.getDiscountFarePlaza2(trandetailInfo, terminalInfo.getTerminalPlazaId_2(), priceScheduleId,inputs);
							}
							
							if(info.getSectionId()==1)
							{
								terminalPlaza3Info = maxTollDaoCRM.getDiscountFarePlaza2(trandetailInfo, 71, priceScheduleId,inputs);
								if(terminalPlaza3Info==0)
								{
									priceScheduleId = maxTollDaoCRM.getPriceScheduleId(inputs, trandetailInfo,"D");
									terminalPlaza3Info = maxTollDaoCRM.getDiscountFarePlaza2(trandetailInfo, 71, priceScheduleId,inputs);
								}
								
								
									if ( terminalPlaza1Info > terminalPlaza2Info && terminalPlaza1Info > terminalPlaza3Info )
									{
										return terminalPlaza1Info;
								     }
									  else if ( terminalPlaza2Info > terminalPlaza1Info && terminalPlaza2Info> terminalPlaza3Info ) 
								      {
								    	  return terminalPlaza2Info;
								      }
								      else if ( terminalPlaza3Info > terminalPlaza1Info && terminalPlaza3Info > terminalPlaza2Info )
								      {
								    	  return terminalPlaza3Info;
								      }
								      else if ( terminalPlaza3Info.equals(terminalPlaza1Info) && terminalPlaza3Info.equals(terminalPlaza2Info))
								      {
								    	  return terminalPlaza3Info;
								      }
								      else
								      {
								    	  throw new TPMSGlobalException("Discounted Amount is null for all 3 plaza's", null, null);
								      }
								
							}
							
							//compare discount_fare
							if(terminalPlaza1Info != 0 &&  terminalPlaza2Info!= 0  &&  terminalPlaza1Info > terminalPlaza2Info)
							{
								return terminalPlaza1Info;
							}
							else if(terminalPlaza1Info != 0 &&  terminalPlaza2Info!= 0  &&  terminalPlaza1Info < terminalPlaza2Info)
							{
								return terminalPlaza2Info;
							}
							else if(terminalPlaza1Info != 0 &&  terminalPlaza2Info!= 0  &&  terminalPlaza1Info.equals(terminalPlaza2Info))
							{
								log.info("Discounted Amount is same");
								return terminalPlaza1Info;
							}
							else if(terminalPlaza1Info == 0 && terminalPlaza2Info != 0)
							{
								return terminalPlaza2Info;
							}
							else if(terminalPlaza2Info == 0 && terminalPlaza1Info != 0)
							{
								return terminalPlaza1Info;
							}
							else if(terminalPlaza1Info == 0 && terminalPlaza2Info == 0)
							{
								throw new TPMSGlobalException("No Record Found", null, null);
							}
							else
							{
								throw new TPMSGlobalException("Discounted Amount is null or zero", null, null);
							}
						}
						else if(priceScheduleId == 0 && terminalInfo!=null)
						{
							//throw new TPMSGlobalException("Terminal Info is null or Price Schedule Id is zero", null, null);
							priceScheduleId = maxTollDaoCRM.getPriceScheduleId(inputs, trandetailInfo,"D");
							terminalPlaza1Info = maxTollDaoCRM.getDiscountFare(trandetailInfo, terminalInfo.getTerminalPlazaId_1(), priceScheduleId,inputs);
							terminalPlaza2Info = maxTollDaoCRM.getDiscountFarePlaza2(trandetailInfo, terminalInfo.getTerminalPlazaId_2(), priceScheduleId,inputs);
							
							if(info.getSectionId()==1)
							{
								terminalPlaza3Info = maxTollDaoCRM.getDiscountFarePlaza2(trandetailInfo, 71, priceScheduleId,inputs);
								
								
									if ( terminalPlaza1Info > terminalPlaza2Info && terminalPlaza1Info > terminalPlaza3Info )
									{
										return terminalPlaza1Info;
								     }
									  else if ( terminalPlaza2Info > terminalPlaza1Info && terminalPlaza2Info> terminalPlaza3Info ) 
								      {
								    	  return terminalPlaza2Info;
								      }
								      else if ( terminalPlaza3Info > terminalPlaza1Info && terminalPlaza3Info > terminalPlaza2Info )
								      {
								    	  return terminalPlaza3Info;
								      }
								      else if ( terminalPlaza3Info.equals(terminalPlaza1Info) && terminalPlaza3Info.equals(terminalPlaza2Info))
								      {
								    	  return terminalPlaza3Info;
								      }
								      else
								      {
								    	  throw new TPMSGlobalException("Discounted Amount is null or zero for all 3 plaza's", null, null);
								      }
								
							}
						
							//compare discount_fare
							if(terminalPlaza1Info != 0 &&  terminalPlaza2Info!= 0  &&  terminalPlaza1Info > terminalPlaza2Info)
							{
								return terminalPlaza1Info;
							}
							else if(terminalPlaza1Info != 0 &&  terminalPlaza2Info!= 0  &&  terminalPlaza1Info < terminalPlaza2Info)
							{
								return terminalPlaza2Info;
							}
							else if(terminalPlaza1Info != 0 &&  terminalPlaza2Info!= 0  &&  terminalPlaza1Info.equals(terminalPlaza2Info))
							{
								log.info("Discounted Amount is same");
								return terminalPlaza1Info;
							}
							else if(terminalPlaza1Info == 0 && terminalPlaza2Info != 0)
							{
								return terminalPlaza2Info;
							}
							else if(terminalPlaza2Info == 0 && terminalPlaza1Info != 0)
							{
								return terminalPlaza1Info;
							}
							else
							{
								throw new TPMSGlobalException("Discounted Amount is null or zero", null, null);
							}
						}
						else
						{
							throw new TPMSGlobalException("Terminal Info from T_HIGHWAY_SECTION Table is null or not present", null, null);
						}
					
					}
					else
					{
						Integer yy = Integer.parseInt(null != inputs.getTxTimestamp() ? inputs.getTxTimestamp().substring(0, 4)
								: trandetailInfo.getTxTimeStamp().toString().substring(0, 4));
						Integer mn = Integer.parseInt(null != inputs.getTxTimestamp() ? inputs.getTxTimestamp().substring(5, 7)
								: trandetailInfo.getTxTimeStamp().toString().substring(5, 7));
						Integer dt = Integer.parseInt(null != inputs.getTxTimestamp() ? inputs.getTxTimestamp().substring(8, 10)
								: trandetailInfo.getTxTimeStamp().toString().substring(8, 10));

						String daysInd = getDaysIndFromWeekDay(yy, mn, dt).toString();

						log.info("Days Ind From WeekDay Function is : {}", daysInd);

						priceScheduleId = maxTollDaoCRM.getPriceScheduleId(inputs, trandetailInfo,daysInd);
						if(priceScheduleId>0 && terminalInfo!=null) 
						{
							terminalPlaza1Info = maxTollDaoCRM.getDiscountFare(trandetailInfo, terminalInfo.getTerminalPlazaId_1(), priceScheduleId,inputs);
							if(terminalPlaza1Info==null)
							{
								priceScheduleId = maxTollDaoCRM.getPriceScheduleId(inputs, trandetailInfo,"D");
								terminalPlaza1Info = maxTollDaoCRM.getDiscountFare(trandetailInfo, terminalInfo.getTerminalPlazaId_1(), priceScheduleId,inputs);
							}
							
							terminalPlaza2Info = maxTollDaoCRM.getDiscountFarePlaza2(trandetailInfo, terminalInfo.getTerminalPlazaId_2(), priceScheduleId,inputs);
							if(terminalPlaza2Info==null)
							{
								priceScheduleId = maxTollDaoCRM.getPriceScheduleId(inputs, trandetailInfo,"D");
								terminalPlaza2Info = maxTollDaoCRM.getDiscountFarePlaza2(trandetailInfo, terminalInfo.getTerminalPlazaId_2(), priceScheduleId,inputs);
							}
							if(info.getSectionId()==1)
							{
								terminalPlaza3Info = maxTollDaoCRM.getDiscountFarePlaza2(trandetailInfo, 71, priceScheduleId,inputs);
								
								
									if ( terminalPlaza1Info > terminalPlaza2Info && terminalPlaza1Info > terminalPlaza3Info )
									{
										return terminalPlaza1Info;
								     }
									  else if ( terminalPlaza2Info > terminalPlaza1Info && terminalPlaza2Info> terminalPlaza3Info ) 
								      {
								    	  return terminalPlaza2Info;
								      }
								      else if ( terminalPlaza3Info > terminalPlaza1Info && terminalPlaza3Info > terminalPlaza2Info )
								      {
								    	  return terminalPlaza3Info;
								      }
								      else if ( terminalPlaza3Info.equals(terminalPlaza1Info) && terminalPlaza3Info.equals(terminalPlaza2Info))
								      {
								    	  return terminalPlaza3Info;
								      }
								      else
								      {
								    	  throw new TPMSGlobalException("Discounted Amount is null or zero for all 3 plaza's", null, null);
								      }
								
							}
							
							//compare discount_fare
							if(terminalPlaza1Info != 0 &&  terminalPlaza2Info!= 0  &&  terminalPlaza1Info > terminalPlaza2Info)
							{
								return terminalPlaza1Info;
							}
							else if(terminalPlaza1Info != 0 &&  terminalPlaza2Info!= 0  &&  terminalPlaza1Info < terminalPlaza2Info)
							{
								return terminalPlaza2Info;
							}
							else if(terminalPlaza1Info != 0 &&  terminalPlaza2Info!= 0  &&  terminalPlaza1Info.equals(terminalPlaza2Info))
							{
								log.info("Discounted Amount is same");
								return terminalPlaza1Info;
							}
							else if(terminalPlaza1Info == 0 && terminalPlaza2Info != 0)
							{
								return terminalPlaza2Info;
							}
							else if(terminalPlaza2Info == 0 && terminalPlaza1Info != 0)
							{
								return terminalPlaza1Info;
							}
							else if(terminalPlaza1Info == 0 && terminalPlaza2Info == 0)
							{
								throw new TPMSGlobalException("No Record Found", null, null);
							}
							else
							{
								throw new TPMSGlobalException("Discounted Amount is null or zero", null, null);
							}
						}
						else if(priceScheduleId==0 && terminalInfo!=null)
						{
							//throw new TPMSGlobalException("Terminal Info is null or Price Schedule Id is zero", null, null);
							priceScheduleId = maxTollDaoCRM.getPriceScheduleId(inputs, trandetailInfo,"D");
							terminalPlaza1Info = maxTollDaoCRM.getDiscountFare(trandetailInfo, terminalInfo.getTerminalPlazaId_1(), priceScheduleId,inputs);
							terminalPlaza2Info = maxTollDaoCRM.getDiscountFarePlaza2(trandetailInfo, terminalInfo.getTerminalPlazaId_2(), priceScheduleId,inputs);
						
							if(info.getSectionId()==1)
							{
								terminalPlaza3Info = maxTollDaoCRM.getDiscountFarePlaza2(trandetailInfo, 71, priceScheduleId,inputs);
								
								
									if ( terminalPlaza1Info > terminalPlaza2Info && terminalPlaza1Info > terminalPlaza3Info )
									{
										return terminalPlaza1Info;
								     }
									  else if ( terminalPlaza2Info > terminalPlaza1Info && terminalPlaza2Info> terminalPlaza3Info ) 
								      {
								    	  return terminalPlaza2Info;
								      }
								      else if ( terminalPlaza3Info > terminalPlaza1Info && terminalPlaza3Info > terminalPlaza2Info )
								      {
								    	  return terminalPlaza3Info;
								      }
								      else if ( terminalPlaza3Info.equals(terminalPlaza1Info) && terminalPlaza3Info.equals(terminalPlaza2Info))
								      {
								    	  return terminalPlaza3Info;
								      }
								      else
								      {
								    	  throw new TPMSGlobalException("Discounted Amount is null or zero for all 3 plaza's", null, null);
								      }
								
							}
							//compare discount_fare
							if(terminalPlaza1Info != 0 &&  terminalPlaza2Info!= 0  &&  terminalPlaza1Info > terminalPlaza2Info)
							{
								return terminalPlaza1Info;
							}
							else if(terminalPlaza1Info != 0 &&  terminalPlaza2Info!= 0  &&  terminalPlaza1Info < terminalPlaza2Info)
							{
								return terminalPlaza2Info;
							}
							else if(terminalPlaza1Info != 0 &&  terminalPlaza2Info!= 0  &&  terminalPlaza1Info.equals(terminalPlaza2Info))
							{
								log.info("Discounted Amount is same");
								return terminalPlaza1Info;
							}
							else if(terminalPlaza1Info == 0 && terminalPlaza2Info != 0)
							{
								return terminalPlaza2Info;
							}
							else if(terminalPlaza2Info == 0 && terminalPlaza1Info != 0)
							{
								return terminalPlaza1Info;
							}
							else
							{
								throw new TPMSGlobalException("Discounted Amount is null or zero", null, null);
							}
						}
						else
						{
							throw new TPMSGlobalException("Terminal Info from T_HIGHWAY_SECTION Table is null or not present", null, null);
						}
					}
				}
				else
				{
					throw new TPMSGlobalException("Scheduled Pricing Flag is not Y", null, null);
				}
			}
			else
			{
				throw new TPMSGlobalException("Section ID is zero or TX_DATE is null", null, null);
			}
		}
		else
		{
			throw new TPMSGlobalException("Lane Id is null", null, null);
		}

		
	}

	public Integer getDaysIndFromWeekDay(Integer yr, Integer mn, Integer dt) 
	{
		LocalDate date = LocalDate.of(yr, mn, dt);
		String dayintext = date.getDayOfWeek().toString();
		log.info("Day is : {}", dayintext);
		Integer dayinnum = date.getDayOfWeek().getValue();
		// log.info("Day number is : {}", dayinnum);

		return dayinnum;
	}
}
