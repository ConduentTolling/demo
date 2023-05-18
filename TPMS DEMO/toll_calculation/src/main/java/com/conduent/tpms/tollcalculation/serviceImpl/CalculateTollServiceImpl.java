package com.conduent.tpms.tollcalculation.serviceImpl;

import java.text.ParseException;
import java.time.LocalDate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import com.conduent.app.timezone.utility.TimeZoneConv;
import com.conduent.tpms.tollcalculation.constant.Constants;
import com.conduent.tpms.tollcalculation.dao.CalculateTollDao;
import com.conduent.tpms.tollcalculation.exception.TPMSGlobalException;
import com.conduent.tpms.tollcalculation.model.APIInputs;
import com.conduent.tpms.tollcalculation.model.APIOutput;
import com.conduent.tpms.tollcalculation.model.AgencyHoliday;
import com.conduent.tpms.tollcalculation.model.TTranInputs;
import com.conduent.tpms.tollcalculation.service.CalculateTollService;
import com.conduent.tpms.tollcalculation.utility.Validation;

@Service
public class CalculateTollServiceImpl implements CalculateTollService 
{
	String methodName = null;
	String className = this.getClass().getName();
	HttpHeaders headers = new HttpHeaders();

	@Autowired
	Validation validation;

	private static final Logger log = LoggerFactory.getLogger(CalculateTollServiceImpl.class);

	@Autowired
	CalculateTollDao calculateTollDao;

	AgencyHoliday agencyHoliday = new AgencyHoliday();
	// public int priceScheduleId = 0;
	Integer priceScheduleId;

	APIOutput outputAmount;

	@Override
	public APIOutput process(APIInputs inputs) throws ParseException 
	{
		APIOutput amount = new APIOutput();

		if (inputs.getLaneTxId() != null) 
		{
			amount = getInfoFromTTranDetails(inputs);
			return amount;
		} 
		else 
		{
			amount = getInfoFromAPIInputs(inputs);
			return amount;
		}

	}

	private APIOutput getInfoFromAPIInputs(APIInputs inputs) throws ParseException 
	{		
		if ((inputs.getExitPlazaId() != null) && (inputs.getTollRevenueType() != null)
				&& (inputs.getTxTimestamp() != null) && (inputs.getAgencyId() != null)
				&& (inputs.getActualClass() != null) && (inputs.getAccountType() != null)
				&& (inputs.getTollSystemType() != null)) 
		{
			if (validation.numberFormatValidation(inputs.getExitPlazaId().toString())
					&& validation.numberFormatValidation(inputs.getTollRevenueType().toString())
					&& validation.numberFormatValidation(inputs.getActualClass().toString())
					&& validation.numberFormatValidation(inputs.getAgencyId().toString())
					&& validation.numberFormatValidation(inputs.getAccountType().toString())
					&& validation.alphabetFormatValidation(inputs.getTollSystemType().toString())) 
			{
				if (validation.dateValidation(inputs.getTxTimestamp().toString().substring(0, 10))
						&& validation.timeValidation(validation.timeFormatCorrection(inputs.getTxTimestamp().toString().substring(11, 19)))) 
				{
					// start of process
					agencyHoliday = calculateTollDao.getAgencyHolidayfromAPIInputs(inputs);

					if (agencyHoliday != null) //if the Date is AgencyHoliday
					{
						log.debug("Information from Agency Holiday Table {}", agencyHoliday.toString());
						priceScheduleId = calculateTollDao.getPriceScheduleIdfromAPIInputs(inputs, agencyHoliday.getDaysInd());
						
						if(priceScheduleId != null) 
						{
							outputAmount = calculateTollDao.getDiscountFarefromAPIInputs(priceScheduleId, inputs);
							
							if (outputAmount != null) 
							{
								log.info("Amounts are {}", outputAmount);
								return outputAmount;
							} 
							else 
							{
								priceScheduleId = calculateTollDao.getPriceScheduleIdfromAPIInputs(inputs, "D");
								outputAmount = calculateTollDao.getDiscountFarefromAPIInputs(priceScheduleId, inputs);
								
								if (outputAmount != null) 
								{
									log.info("Amounts are {}", outputAmount);
									return outputAmount;
								} 
								else 
								{
									throw new TPMSGlobalException("No Record Found.", null, null);
								}
							}
						}
						else 
						{
							priceScheduleId = calculateTollDao.getPriceScheduleIdfromAPIInputs(inputs, "D");
							outputAmount = calculateTollDao.getDiscountFarefromAPIInputs(priceScheduleId, inputs);
							
							if (outputAmount != null) 
							{
								log.info("Amounts are {}", outputAmount);
								return outputAmount;
							} 
							else 
							{
								throw new TPMSGlobalException("No Record Found.", null, null);
							}
						}					
					}
					else // if the date is not agencyHoliday
					{
						Integer yy = Integer.parseInt(inputs.getTxTimestamp().substring(0, 4));
						Integer mn = Integer.parseInt(inputs.getTxTimestamp().substring(5, 7));
						Integer dt = Integer.parseInt(inputs.getTxTimestamp().substring(8, 10));

						String daysInd = getDaysIndFromWeekDay(yy, mn, dt).toString();

						log.info("Days Ind From WeekDay Function is : {}", daysInd);

						priceScheduleId = calculateTollDao.getPriceScheduleIdfromAPIInputs(inputs, daysInd);

						if(priceScheduleId != null) 
						{
							outputAmount = calculateTollDao.getDiscountFarefromAPIInputs(priceScheduleId, inputs);
							
							if (outputAmount != null) 
							{
								log.info("Amounts are {}", outputAmount);
								return outputAmount;
							} 
							else 
							{
								priceScheduleId = calculateTollDao.getPriceScheduleIdfromAPIInputs(inputs, "D");
								outputAmount = calculateTollDao.getDiscountFarefromAPIInputs(priceScheduleId, inputs);
								
								if (outputAmount != null) 
								{
									log.info("Amounts are {}", outputAmount);
									return outputAmount;
								} 
								else 
								{
									throw new TPMSGlobalException("No Record Found.", null, null);
								}
							}
						}
						else 
						{
							priceScheduleId = calculateTollDao.getPriceScheduleIdfromAPIInputs(inputs, "D");
							outputAmount = calculateTollDao.getDiscountFarefromAPIInputs(priceScheduleId, inputs);
							
							if (outputAmount != null) 
							{
								log.info("Amounts are {}", outputAmount);
								return outputAmount;
							} 
							else 
							{
								throw new TPMSGlobalException("No Record Found.", null, null);
							}
						}	
					
					}				
					// end of process
				} 
				else 
				{
					throw new TPMSGlobalException("TX_TIMESTAMP should be in correct format", className, methodName);
				}
			} 
			else 
			{
				throw new TPMSGlobalException("Except TOLL_SYSTEM_TYPE and TX_TIMESTAMP, rest all fields should be in numeric format",className, methodName);
			}
		} 
		else 
		{
			throw new TPMSGlobalException("Except ENTRY_PLAZA_ID and PLAN_TYPE, rest all fields are mandatory", className,methodName);
		}
	}

	private APIOutput getInfoFromTTranDetails(APIInputs inputs) throws ParseException 
	{		
		// load data from T_TRAN_DETAIL in ttinputs
		TTranInputs ttinputs = calculateTollDao.getInfoFromTTranDetails(inputs.getLaneTxId());
		log.info("TTran Inputs are : {}", ttinputs.toString());

		// start of process
		agencyHoliday = calculateTollDao.getAgencyHoliday(inputs,ttinputs);

		if (agencyHoliday != null) //if the Date is AgencyHoliday
		{
			log.debug("Information from Agency Holiday Table {}", agencyHoliday.toString());
			priceScheduleId = calculateTollDao.getPriceScheduleId(inputs, ttinputs,agencyHoliday.getDaysInd());
			
			if(priceScheduleId != null) 
			{
				outputAmount = calculateTollDao.getDiscountFare(priceScheduleId, inputs, ttinputs);
				
				if (outputAmount != null) 
				{
					log.info("Amounts are {}", outputAmount);
					return outputAmount;
				} 
				else 
				{
					priceScheduleId = calculateTollDao.getPriceScheduleId(inputs, ttinputs,"D");
					outputAmount = calculateTollDao.getDiscountFare(priceScheduleId, inputs, ttinputs);
					
					if (outputAmount != null) 
					{
						log.info("Amounts are {}", outputAmount);
						return outputAmount;
					} 
					else 
					{
						throw new TPMSGlobalException("No Record Found.", null, null);
					}
				}
			}
			else 
			{
				priceScheduleId = calculateTollDao.getPriceScheduleId(inputs, ttinputs,"D");
				outputAmount = calculateTollDao.getDiscountFare(priceScheduleId, inputs, ttinputs);
				
				if (outputAmount != null) 
				{
					log.info("Amounts are {}", outputAmount);
					return outputAmount;
				} 
				else 
				{
					throw new TPMSGlobalException("No Record Found.", null, null);
				}
			}					
		}
		else // if the date is not agencyHoliday
		{
			Integer yy = Integer.parseInt(null != inputs.getTxTimestamp() ? inputs.getTxTimestamp().substring(0, 4)
					: ttinputs.getTxTimestamp().substring(0, 4));
			Integer mn = Integer.parseInt(null != inputs.getTxTimestamp() ? inputs.getTxTimestamp().substring(5, 7)
					: ttinputs.getTxTimestamp().substring(5, 7));
			Integer dt = Integer.parseInt(null != inputs.getTxTimestamp() ? inputs.getTxTimestamp().substring(8, 10)
					: ttinputs.getTxTimestamp().substring(8, 10));

			String daysInd = getDaysIndFromWeekDay(yy, mn, dt).toString();

			log.info("Days Ind From WeekDay Function is : {}", daysInd);

			priceScheduleId = calculateTollDao.getPriceScheduleId(inputs, ttinputs,daysInd);

			if(priceScheduleId != null) 
			{
				outputAmount = calculateTollDao.getDiscountFare(priceScheduleId, inputs, ttinputs);
				
				if (outputAmount != null) 
				{
					log.info("Amounts are {}", outputAmount);
					return outputAmount;
				} 
				else 
				{
					priceScheduleId = calculateTollDao.getPriceScheduleId(inputs, ttinputs,"D");
					outputAmount = calculateTollDao.getDiscountFare(priceScheduleId, inputs, ttinputs);
					
					if (outputAmount != null) 
					{
						log.info("Amounts are {}", outputAmount);
						return outputAmount;
					} 
					else 
					{
						throw new TPMSGlobalException("No Record Found.", null, null);
					}
				}
			}
			else 
			{
				priceScheduleId = calculateTollDao.getPriceScheduleId(inputs, ttinputs,"D");
				outputAmount = calculateTollDao.getDiscountFare(priceScheduleId, inputs, ttinputs);
				
				if (outputAmount != null) 
				{
					log.info("Amounts are {}", outputAmount);
					return outputAmount;
				} 
				else 
				{
					throw new TPMSGlobalException("No Record Found.", null, null);
				}
			}	
		
		}			
		// end of process
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