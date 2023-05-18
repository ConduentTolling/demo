package com.conduent.tpms.unmatched.serviceImpl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.conduent.tpms.umatched.exception.TPMSGateway;
import com.conduent.tpms.umatched.exception.TPMSGlobalException;
import com.conduent.tpms.unmatched.dao.MaxTollDao;
import com.conduent.tpms.unmatched.dto.THighwaySection;
import com.conduent.tpms.unmatched.model.APIInputs;
import com.conduent.tpms.unmatched.model.AgencyHoliday;
import com.conduent.tpms.unmatched.model.SessionIdInputs;
import com.conduent.tpms.unmatched.model.TranDetail;
import com.conduent.tpms.unmatched.service.GetMaxTollAmountService;
import com.conduent.tpms.unmatched.utility.Validation;

@Service
public class GetMaxTollAmountServiceImpl implements GetMaxTollAmountService
{
	private static final Logger log = LoggerFactory.getLogger(GetMaxTollAmountServiceImpl.class);
	
	@Autowired
	MaxTollDao maxTollDao;
	
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
	
	public String converDate(String date) throws ParseException
	{
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
        Date convertedDate = format1.parse(date);
        String convertedDate2 = format2. format(convertedDate);
        System. out. println(convertedDate2);
        return convertedDate2;
	}

	@Override
	public double getMaxTollAmount(APIInputs inputs) throws ParseException
	{
		double amount = 0d;
		if (inputs.getLaneTxId() != null) 
		{
			log.debug("Lane Tx Id is taken from Inputs {}",inputs.getLaneTxId());
			amount = getInfoFromTTranDetails(inputs);
			return amount;
		} 
		else 
		{
			log.debug("Lane Tx Id is not present");
			amount = getInfoFromAPIInputs(inputs);
			return amount;
		}
	}

	private double getInfoFromAPIInputs(APIInputs inputs) throws ParseException {
		if ((inputs.getExitPlazaId() != null) && (inputs.getTollRevenueType() != null)
				&& (inputs.getTxTimestamp() != null) && (inputs.getAgencyId() != null)
				&& (inputs.getActualClass() != null) && (inputs.getAccountType() != null)
				&& (inputs.getTollSystemType() != null) && inputs.getLaneId()!=null) 
		{
			if (validation.numberFormatValidation(inputs.getExitPlazaId().toString())
					&& validation.numberFormatValidation(inputs.getTollRevenueType().toString())
					&& validation.numberFormatValidation(inputs.getActualClass().toString())
					&& validation.numberFormatValidation(inputs.getAgencyId().toString())
					&& validation.numberFormatValidation(inputs.getAccountType().toString())
					&& validation.alphabetFormatValidation(inputs.getTollSystemType().toString()) &&
					validation.numberFormatValidation(inputs.getLaneId().toString())) 
			{
				if (validation.dateValidation(inputs.getTxTimestamp().toString().substring(0, 10))
						&& validation.timeValidation(validation.timeFormatCorrection(inputs.getTxTimestamp().toString().substring(11, 19)))) 
				{
					info = maxTollDao.getSectionId(trandetailInfo,inputs);
					if(info !=null)
					{
						log.info("Session Id Info fetched : {}",info.toString());
					}
					
					if(info.getSectionId() > 0)
					{
						//get terminal_plaza_id_1 and 2 info
						terminalInfo = maxTollDao.getTerminalPlazaInfo(info.getSectionId());
						
						//days of week
						agencyHoliday = maxTollDao.getAgencyHoliday(trandetailInfo,inputs);
						//log.info("Days Ind for lane_tx_id {} is {}",trandetailInfo.getLaneTxId(),agencyHoliday.getDaysInd());
						
						if(info.getScheduledPricingFlag().equals("Y"))
						{
							if (agencyHoliday != null) //if the Date is AgencyHoliday
							{
								log.debug("Information from Agency Holiday Table {}", agencyHoliday.toString());
								priceScheduleId = maxTollDao.getPriceScheduleId(inputs, trandetailInfo,agencyHoliday.getDaysInd());
							
								if(priceScheduleId>0 && terminalInfo!=null) 
								{
									terminalPlaza1Info = maxTollDao.getDiscountFare(trandetailInfo, terminalInfo.getTerminalPlazaId_1(), priceScheduleId,inputs);
									if(terminalPlaza1Info==0)
									{
										priceScheduleId = maxTollDao.getPriceScheduleId(inputs, trandetailInfo,"D");
										terminalPlaza1Info = maxTollDao.getDiscountFare(trandetailInfo, terminalInfo.getTerminalPlazaId_1(), priceScheduleId,inputs);
									}
									
									terminalPlaza2Info = maxTollDao.getDiscountFare(trandetailInfo, terminalInfo.getTerminalPlazaId_2(), priceScheduleId,inputs);
									if(terminalPlaza2Info==0)
									{
										priceScheduleId = maxTollDao.getPriceScheduleId(inputs, trandetailInfo,"D");
										terminalPlaza2Info = maxTollDao.getDiscountFare(trandetailInfo, terminalInfo.getTerminalPlazaId_2(), priceScheduleId,inputs);
									}
									
									if(info.getSectionId()==1)
									{
										terminalPlaza3Info = maxTollDao.getDiscountFarePlaza2(trandetailInfo, 71, priceScheduleId,inputs);
										if(terminalPlaza3Info==0)
										{
											priceScheduleId = maxTollDao.getPriceScheduleId(inputs, trandetailInfo,"D");
											terminalPlaza3Info = maxTollDao.getDiscountFarePlaza2(trandetailInfo, 71, priceScheduleId,inputs);
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
										    	  throw new TPMSGlobalException("Discounted Amount is null or zero", null, null);
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
									priceScheduleId = maxTollDao.getPriceScheduleId(inputs, trandetailInfo,"D");
									terminalPlaza1Info = maxTollDao.getDiscountFare(trandetailInfo, terminalInfo.getTerminalPlazaId_1(), priceScheduleId,inputs);
									terminalPlaza2Info = maxTollDao.getDiscountFarePlaza2(trandetailInfo, terminalInfo.getTerminalPlazaId_2(), priceScheduleId,inputs);
								
									if(info.getSectionId()==1)
									{
										terminalPlaza3Info = maxTollDao.getDiscountFarePlaza2(trandetailInfo, 71, priceScheduleId,inputs);
										if(terminalPlaza3Info==0)
										{
											priceScheduleId = maxTollDao.getPriceScheduleId(inputs, trandetailInfo,"D");
											terminalPlaza3Info = maxTollDao.getDiscountFarePlaza2(trandetailInfo, 71, priceScheduleId,inputs);
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
										    	  throw new TPMSGlobalException("Discounted Amount is null or zero", null, null);
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

								priceScheduleId = maxTollDao.getPriceScheduleId(inputs, trandetailInfo,daysInd);
								if(priceScheduleId>0 && terminalInfo!=null) 
								{
									terminalPlaza1Info = maxTollDao.getDiscountFare(trandetailInfo, terminalInfo.getTerminalPlazaId_1(), priceScheduleId,inputs);
									if(terminalPlaza1Info==null)
									{
										priceScheduleId = maxTollDao.getPriceScheduleId(inputs, trandetailInfo,"D");
										terminalPlaza1Info = maxTollDao.getDiscountFare(trandetailInfo, terminalInfo.getTerminalPlazaId_1(), priceScheduleId,inputs);
									}
									
									terminalPlaza2Info = maxTollDao.getDiscountFare(trandetailInfo, terminalInfo.getTerminalPlazaId_2(), priceScheduleId,inputs);
									if(terminalPlaza2Info==null)
									{
										priceScheduleId = maxTollDao.getPriceScheduleId(inputs, trandetailInfo,"D");
										terminalPlaza2Info = maxTollDao.getDiscountFare(trandetailInfo, terminalInfo.getTerminalPlazaId_2(), priceScheduleId,inputs);
									}
									
									if(info.getSectionId()==1)
									{
										terminalPlaza3Info = maxTollDao.getDiscountFarePlaza2(trandetailInfo, 71, priceScheduleId,inputs);
										if(terminalPlaza3Info==0)
										{
											priceScheduleId = maxTollDao.getPriceScheduleId(inputs, trandetailInfo,"D");
											terminalPlaza3Info = maxTollDao.getDiscountFarePlaza2(trandetailInfo, 71, priceScheduleId,inputs);
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
										    	  throw new TPMSGlobalException("Discounted Amount is null or zero", null, null);
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
									priceScheduleId = maxTollDao.getPriceScheduleId(inputs, trandetailInfo,"D");
									terminalPlaza1Info = maxTollDao.getDiscountFare(trandetailInfo, terminalInfo.getTerminalPlazaId_1(), priceScheduleId,inputs);
									terminalPlaza2Info = maxTollDao.getDiscountFare(trandetailInfo, terminalInfo.getTerminalPlazaId_2(), priceScheduleId,inputs);
								
									
									if(info.getSectionId()==1)
									{
										terminalPlaza3Info = maxTollDao.getDiscountFarePlaza2(trandetailInfo, 71, priceScheduleId,inputs);
										if(terminalPlaza3Info==0)
										{
											priceScheduleId = maxTollDao.getPriceScheduleId(inputs, trandetailInfo,"D");
											terminalPlaza3Info = maxTollDao.getDiscountFarePlaza2(trandetailInfo, 71, priceScheduleId,inputs);
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
										    	  throw new TPMSGlobalException("Discounted Amount is null or zero", null, null);
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
					throw new TPMSGlobalException("Tx_Timestamp should be in correct format", null, null);
				}
			}
			else
			{
				throw new TPMSGlobalException("Except Lane_tx_id rest all fields should in correct format", null, null);
			}
		}
		else
		{
			throw new TPMSGlobalException("If lane_tx_id is null rest all fields are mandatory", null, null);
		}
	}


	private double getInfoFromTTranDetails(APIInputs inputs) throws ParseException 
	{		
		// load data from T_TRAN_DETAIL in ttinputs
		TranDetail trandetailInfo = maxTollDao.getTrxnInfo(inputs.getLaneTxId());
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
			info = maxTollDao.getSectionId(trandetailInfo,inputs);
			if(info !=null)
			{
				log.info("Session Id Info fetched : {}",info.toString());
			}
			
			if(info.getSectionId() > 0 && trandetailInfo.getTxDate() != null)
			{
				//get terminal_plaza_id_1 and 2 info
				terminalInfo = maxTollDao.getTerminalPlazaInfo(info.getSectionId());
				
				//days of week
				agencyHoliday = maxTollDao.getAgencyHoliday(trandetailInfo,inputs);
				//log.info("Days Ind for lane_tx_id {} is {}",trandetailInfo.getLaneTxId(),agencyHoliday.getDaysInd());
				
				if(info.getScheduledPricingFlag().equals("Y"))
				{
					if (agencyHoliday != null) //if the Date is AgencyHoliday
					{
						log.debug("Information from Agency Holiday Table {}", agencyHoliday.toString());
						priceScheduleId = maxTollDao.getPriceScheduleId(inputs, trandetailInfo,agencyHoliday.getDaysInd());
					
						if(priceScheduleId>0 && terminalInfo!=null) 
						{
							terminalPlaza1Info = maxTollDao.getDiscountFare(trandetailInfo, terminalInfo.getTerminalPlazaId_1(), priceScheduleId,inputs);
							if(terminalPlaza1Info==0)
							{
								priceScheduleId = maxTollDao.getPriceScheduleId(inputs, trandetailInfo,"D");
								terminalPlaza1Info = maxTollDao.getDiscountFare(trandetailInfo, terminalInfo.getTerminalPlazaId_1(), priceScheduleId,inputs);
							}
							
							terminalPlaza2Info = maxTollDao.getDiscountFarePlaza2(trandetailInfo, terminalInfo.getTerminalPlazaId_2(), priceScheduleId,inputs);
							if(terminalPlaza2Info==0)
							{
								priceScheduleId = maxTollDao.getPriceScheduleId(inputs, trandetailInfo,"D");
								terminalPlaza2Info = maxTollDao.getDiscountFarePlaza2(trandetailInfo, terminalInfo.getTerminalPlazaId_2(), priceScheduleId,inputs);
							}
							
							if(info.getSectionId()==1)
							{
								terminalPlaza3Info = maxTollDao.getDiscountFarePlaza2(trandetailInfo, 71, priceScheduleId,inputs);
								if(terminalPlaza3Info==0)
								{
									priceScheduleId = maxTollDao.getPriceScheduleId(inputs, trandetailInfo,"D");
									terminalPlaza3Info = maxTollDao.getDiscountFarePlaza2(trandetailInfo, 71, priceScheduleId,inputs);
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
							priceScheduleId = maxTollDao.getPriceScheduleId(inputs, trandetailInfo,"D");
							terminalPlaza1Info = maxTollDao.getDiscountFare(trandetailInfo, terminalInfo.getTerminalPlazaId_1(), priceScheduleId,inputs);
							terminalPlaza2Info = maxTollDao.getDiscountFarePlaza2(trandetailInfo, terminalInfo.getTerminalPlazaId_2(), priceScheduleId,inputs);
							
							if(info.getSectionId()==1)
							{
								terminalPlaza3Info = maxTollDao.getDiscountFarePlaza2(trandetailInfo, 71, priceScheduleId,inputs);
								
								
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

						priceScheduleId = maxTollDao.getPriceScheduleId(inputs, trandetailInfo,daysInd);
						if(priceScheduleId>0 && terminalInfo!=null) 
						{
							terminalPlaza1Info = maxTollDao.getDiscountFare(trandetailInfo, terminalInfo.getTerminalPlazaId_1(), priceScheduleId,inputs);
							if(terminalPlaza1Info==null)
							{
								priceScheduleId = maxTollDao.getPriceScheduleId(inputs, trandetailInfo,"D");
								terminalPlaza1Info = maxTollDao.getDiscountFare(trandetailInfo, terminalInfo.getTerminalPlazaId_1(), priceScheduleId,inputs);
							}
							
							terminalPlaza2Info = maxTollDao.getDiscountFarePlaza2(trandetailInfo, terminalInfo.getTerminalPlazaId_2(), priceScheduleId,inputs);
							if(terminalPlaza2Info==null)
							{
								priceScheduleId = maxTollDao.getPriceScheduleId(inputs, trandetailInfo,"D");
								terminalPlaza2Info = maxTollDao.getDiscountFarePlaza2(trandetailInfo, terminalInfo.getTerminalPlazaId_2(), priceScheduleId,inputs);
							}
							if(info.getSectionId()==1)
							{
								terminalPlaza3Info = maxTollDao.getDiscountFarePlaza2(trandetailInfo, 71, priceScheduleId,inputs);
								
								
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
							priceScheduleId = maxTollDao.getPriceScheduleId(inputs, trandetailInfo,"D");
							terminalPlaza1Info = maxTollDao.getDiscountFare(trandetailInfo, terminalInfo.getTerminalPlazaId_1(), priceScheduleId,inputs);
							terminalPlaza2Info = maxTollDao.getDiscountFarePlaza2(trandetailInfo, terminalInfo.getTerminalPlazaId_2(), priceScheduleId,inputs);
						
							if(info.getSectionId()==1)
							{
								terminalPlaza3Info = maxTollDao.getDiscountFarePlaza2(trandetailInfo, 71, priceScheduleId,inputs);
								
								
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


	// Get Day of the Week e.g.: Monday = 1
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
