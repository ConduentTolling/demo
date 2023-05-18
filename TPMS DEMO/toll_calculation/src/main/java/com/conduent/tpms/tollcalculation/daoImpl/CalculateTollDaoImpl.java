package com.conduent.tpms.tollcalculation.daoImpl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.conduent.tpms.tollcalculation.constant.Constants;
import com.conduent.tpms.tollcalculation.constant.LoadJpaQueries;
import com.conduent.tpms.tollcalculation.dao.CalculateTollDao;
import com.conduent.tpms.tollcalculation.model.APIInputs;
import com.conduent.tpms.tollcalculation.model.APIOutput;
import com.conduent.tpms.tollcalculation.model.AgencyHoliday;
import com.conduent.tpms.tollcalculation.model.TTranInputs;
import com.conduent.tpms.tollcalculation.utility.Validation;

@Repository
public class CalculateTollDaoImpl implements CalculateTollDao
{
	private static final Logger dao_log = LoggerFactory.getLogger(CalculateTollDaoImpl.class);
	
	@Autowired
	private NamedParameterJdbcTemplate namedJdbcTemplate;

	@Autowired
	Validation validation;
	
	//Get Days Ind from T_AGENCY_HOLIDAY table
	@Override
	public AgencyHoliday getAgencyHoliday(APIInputs inputs, TTranInputs ttinputs) 
	{
		
		try 
		{
			AgencyHoliday agencyInfo = new AgencyHoliday();
			
			MapSqlParameterSource paramSource = new MapSqlParameterSource();
			
			paramSource.addValue(Constants.AGENCY_ID, null!=inputs.getAgencyId()?inputs.getAgencyId():ttinputs.getAgencyId());
			paramSource.addValue(Constants.TX_TIMESTAMP, null!=inputs.getTxTimestamp()?inputs.getTxTimestamp().substring(0,10):ttinputs.getTxTimestamp().substring(0,10));
			
			//dao_log.info("TX_DATE is : {}", null!=inputs.getTxTimestamp()?inputs.getTxTimestamp().substring(0,10):ttinputs.getTxTimestamp().substring(0,10));
			
			String query = LoadJpaQueries.getQueryById("GET_T_AGENCY_HOLIDAY");
			
			agencyInfo = namedJdbcTemplate.queryForObject(query, paramSource, BeanPropertyRowMapper.newInstance(AgencyHoliday.class));
			
			if(agencyInfo  !=null)
			{
				return agencyInfo;
			}
			
		} catch (DataAccessException e)
		{
			dao_log.info("No record found from T_AGENCY_HOLIDAY Table for TX_DATE {} ",null!=inputs.getTxTimestamp()?inputs.getTxTimestamp().substring(0,10):ttinputs.getTxTimestamp().substring(0,10));
		}
		
		return null;
	}

	//Get Price Schedule Id from T_TOLL_PRICE_SCHEDULE table
	@Override
	public Integer getPriceScheduleId(APIInputs inputs, TTranInputs ttinputs, String daysInd) 
	{
		try 
		{
			Integer priceScheduleId;
			
			String time = validation.timeFormatCorrection(null!=inputs.getTxTimestamp()?inputs.getTxTimestamp().toString().substring(11,19):ttinputs.getTxTimestamp().toString().substring(11,19));
			//dao_log.info("Time is : {}", time);
			
			MapSqlParameterSource paramSource = new MapSqlParameterSource();
			
			paramSource.addValue(Constants.AGENCY_ID, null!=inputs.getAgencyId()?inputs.getAgencyId():ttinputs.getAgencyId());
			paramSource.addValue(Constants.DAYS_IND, daysInd);			
			paramSource.addValue(Constants.START_TIME, time);
			paramSource.addValue(Constants.TX_TIMESTAMP, null!=inputs.getTxTimestamp()?inputs.getTxTimestamp().substring(0,10):ttinputs.getTxTimestamp().substring(0,10));
			// timestamp
			String query = LoadJpaQueries.getQueryById("GET_PRICE_SCHEDULE_ID");
			
			priceScheduleId = namedJdbcTemplate.queryForObject(query, paramSource,Integer.class);
			
			if(priceScheduleId  !=null)
			{
				return priceScheduleId;
			}
			
		} catch (DataAccessException e)
		{
			dao_log.info("No record found or price schedule id is zero.");
		}
		
		return null;
	}

	//Get Amounts from T_TOLL_SCHEDULE table
	@Override
	public APIOutput getDiscountFare(Integer priceScheduleId, APIInputs inputs, TTranInputs ttinputs) throws ParseException 
	{
		APIOutput amount;
		
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		String query = LoadJpaQueries.getQueryById("GET_DISCOUNTED_AMOUNT");
				
		//outer try start
		try 
		{	
									
			paramSource.addValue(Constants.EFFECTIVE_DATE, converDate(null!=inputs.getTxTimestamp()?inputs.getTxTimestamp().substring(0,10):ttinputs.getTxTimestamp().substring(0,10)));
			paramSource.addValue(Constants.EXPIRY_DATE, converDate(null!=inputs.getTxTimestamp()?inputs.getTxTimestamp().substring(0,10):ttinputs.getTxTimestamp().substring(0,10)));
			paramSource.addValue(Constants.EXIT_PLAZA_ID, null!=inputs.getExitPlazaId()?inputs.getExitPlazaId():ttinputs.getExitPlazaId());
			paramSource.addValue(Constants.ENTRY_PLAZA_ID, null!=inputs.getEntryPlazaId()?inputs.getEntryPlazaId():ttinputs.getEntryPlazaId());
			paramSource.addValue(Constants.ACTUAL_CLASS, null!=inputs.getActualClass()?inputs.getActualClass():ttinputs.getActualClass());
			paramSource.addValue(Constants.TOLL_REVENUE_TYPE,null!=inputs.getTollRevenueType()?inputs.getTollRevenueType():ttinputs.getTollRevenueType());
			paramSource.addValue(Constants.PRICE_SCHEDULE_ID, priceScheduleId);
			paramSource.addValue(Constants.PLAN_TYPE, null!=inputs.getPlanType()?inputs.getPlanType():findPlanType(null!=inputs.getAccountType()?inputs.getAccountType():ttinputs.getAccountType()));
						
			amount = namedJdbcTemplate.queryForObject(query, paramSource, BeanPropertyRowMapper.newInstance(APIOutput.class));
			
			if(amount != null)
			{
				return amount;
			}
			
		} //outer try end
		//outer catch start
		catch (DataAccessException e) 
		{
			try //inner try start
			{				
				String tollSystemType = null!=inputs.getTollSystemType()?inputs.getTollSystemType():ttinputs.getTollSystemType();
				dao_log.info("Toll System Type is : {}", tollSystemType);
				
				if(tollSystemType.equals("B") || tollSystemType.equals("P") || tollSystemType.equals("X"))
				{				
														
					paramSource.addValue(Constants.EFFECTIVE_DATE, converDate(null!=inputs.getTxTimestamp()?inputs.getTxTimestamp().substring(0,10):ttinputs.getTxTimestamp().substring(0,10)));
					paramSource.addValue(Constants.EXPIRY_DATE, converDate(null!=inputs.getTxTimestamp()?inputs.getTxTimestamp().substring(0,10):ttinputs.getTxTimestamp().substring(0,10)));
					paramSource.addValue(Constants.EXIT_PLAZA_ID, null!=inputs.getExitPlazaId()?inputs.getExitPlazaId():ttinputs.getExitPlazaId());
					paramSource.addValue(Constants.ENTRY_PLAZA_ID, null!=inputs.getExitPlazaId()?inputs.getExitPlazaId():ttinputs.getExitPlazaId());
					paramSource.addValue(Constants.ACTUAL_CLASS, null!=inputs.getActualClass()?inputs.getActualClass():ttinputs.getActualClass());
					paramSource.addValue(Constants.TOLL_REVENUE_TYPE,null!=inputs.getTollRevenueType()?inputs.getTollRevenueType():ttinputs.getTollRevenueType());
					paramSource.addValue(Constants.PRICE_SCHEDULE_ID, priceScheduleId);
					paramSource.addValue(Constants.PLAN_TYPE, null!=inputs.getPlanType()?inputs.getPlanType():findPlanType(null!=inputs.getAccountType()?inputs.getAccountType():ttinputs.getAccountType()));
										
					amount = namedJdbcTemplate.queryForObject(query, paramSource, BeanPropertyRowMapper.newInstance(APIOutput.class));
					
					if(amount != null)
					{
						return amount;
					}			
				}
			} //inner try end
			catch (DataAccessException e1) 
			{
				dao_log.info("No Amount found.");
			} 
			catch (ParseException e1) 
			{
				dao_log.info("Exception {} ",e.getMessage());
			}
		}//outer catch end 
		catch (ParseException e) 
		{
			dao_log.info("Exception {} ",e.getMessage());
		}
		
		return null;
	}
	
	// Convert date from 2022-11-14 to 14-NOV-22
	public String converDate(String localDateTime) throws ParseException
	{
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat format2 = new SimpleDateFormat("dd-MMM-yy");
        Date convertedDate = format1.parse(localDateTime);
        String convertedDate2 = format2.format(convertedDate).toUpperCase();
		return convertedDate2;
	}

	// Get Info from T_TRAN_DETAIL Table
	@Override
	public TTranInputs getInfoFromTTranDetails(Long laneTxId) 
	{
		try 
		{
			TTranInputs TTraninputs = new TTranInputs();
			
			MapSqlParameterSource paramSource = new MapSqlParameterSource();
			
			paramSource.addValue(Constants.LANE_TX_ID, laneTxId);
			
			String query = LoadJpaQueries.getQueryById("GET_PAYLOAD_DETAILS");			
			
			TTraninputs = namedJdbcTemplate.queryForObject(query, paramSource, BeanPropertyRowMapper.newInstance(TTranInputs.class));			
			//dao_log.info("TimeStamp is {} " ,TTraninputs.getTxTimestamp());
			
			if(TTraninputs  !=null )
			{
				return TTraninputs;
			}			
		} catch (DataAccessException e)
		{
			dao_log.info("No record found from T_TRAN_DETAIL Table for LANE_TX_DATE {} " ,laneTxId);
		}
		
		return null;		
	}

	// Find plan_type using account_type
	public Integer findPlanType(Integer accountType)
	{			
		Integer planType = 1;
		
		if (accountType == Constants.ACC_TYPE_PRIVATE)
		{
			planType = Constants.PLAN_TYPE_STANDARD;
		}
        else if (accountType == Constants.ACC_TYPE_BUSINESS || accountType == Constants.ACC_TYPE_COMMERCIAL)     
        {
        	planType = Constants.PLAN_TYPE_BUSINESS;
        }
        else if (accountType == Constants.ACC_TYPE_STVA)
        {
        	planType = Constants.PLAN_TYPE_STVA;
        }
        else if (accountType == Constants.ACC_TYPE_RVA)
        {
        	planType = Constants.PLAN_TYPE_RVA;
        }
        else if (accountType == Constants.ACC_TYPE_PRVA)
        {
        	planType = Constants.PLAN_TYPE_PRVA;
        }
        else if (accountType == Constants.ACC_TYPE_BRVA)
        {
        	planType = Constants.PLAN_TYPE_BRVA;
        }
        else if (accountType == Constants.ACC_TYPE_PVIDEOUNREG)
        {
        	planType = Constants.PLAN_TYPE_PVIDEOUNREG;
        }
        else if (accountType == Constants.ACC_TYPE_BVIDEOUNREG)
        {
        	planType = Constants.PLAN_TYPE_BVIDEOUNREG;
        }
        // logic for plantype acc. to agencyId
		
		dao_log.info("plantType is : {}" ,planType);
		
		return planType;
	}
	
	// for APIInputs only	
	@Override
	public AgencyHoliday getAgencyHolidayfromAPIInputs(APIInputs inputs) 
	{
		
		try 
		{
			AgencyHoliday agencyInfo = new AgencyHoliday();
			
			MapSqlParameterSource paramSource = new MapSqlParameterSource();
			
			paramSource.addValue(Constants.AGENCY_ID, inputs.getAgencyId());
			paramSource.addValue(Constants.TX_TIMESTAMP, inputs.getTxTimestamp().substring(0,10));
			
			//dao_log.info("TX_DATE is : {}", null!=inputs.getTxTimestamp()?inputs.getTxTimestamp().substring(0,10):ttinputs.getTxTimestamp().substring(0,10));
			
			String query = LoadJpaQueries.getQueryById("GET_T_AGENCY_HOLIDAY");
			
			agencyInfo = namedJdbcTemplate.queryForObject(query, paramSource, BeanPropertyRowMapper.newInstance(AgencyHoliday.class));
			
			if(agencyInfo  !=null)
			{
				return agencyInfo;
			}
			
		} catch (DataAccessException e)
		{
			dao_log.info("No record found from T_AGENCY_HOLIDAY Table for TX_DATE {} ",inputs.getTxTimestamp().substring(0,10));
		}
		
		return null;
	}

	//Get Price Schedule Id from T_TOLL_PRICE_SCHEDULE table
	@Override
	public Integer getPriceScheduleIdfromAPIInputs(APIInputs inputs, String daysInd) 
	{
		try 
		{
			Integer priceScheduleId;
			
			String time = validation.timeFormatCorrection(inputs.getTxTimestamp().toString().substring(11,19));
			//dao_log.info("Time is : {}", time);
			
			MapSqlParameterSource paramSource = new MapSqlParameterSource();
			
			paramSource.addValue(Constants.AGENCY_ID, inputs.getAgencyId());
			paramSource.addValue(Constants.DAYS_IND, daysInd);			
			paramSource.addValue(Constants.START_TIME, time);
			paramSource.addValue(Constants.TX_TIMESTAMP, inputs.getTxTimestamp().substring(0,10));
			
			String query = LoadJpaQueries.getQueryById("GET_PRICE_SCHEDULE_ID");
			
			priceScheduleId = namedJdbcTemplate.queryForObject(query, paramSource,Integer.class);
			
			if(priceScheduleId  !=null)
			{
				return priceScheduleId;
			}
			
		} catch (DataAccessException e)
		{
			dao_log.info("No record found or price schedule id is zero.");
		}
		
		return null;
	}

	//Get Amounts from T_TOLL_SCHEDULE table
	@Override
	public APIOutput getDiscountFarefromAPIInputs(Integer priceScheduleId, APIInputs inputs) throws ParseException 
	{
		APIOutput amount;
		
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		String query = LoadJpaQueries.getQueryById("GET_DISCOUNTED_AMOUNT");
				
		//outer try start
		try 
		{	
									
			paramSource.addValue(Constants.EFFECTIVE_DATE, converDate(inputs.getTxTimestamp().substring(0,10)));
			paramSource.addValue(Constants.EXPIRY_DATE, converDate(inputs.getTxTimestamp().substring(0,10)));
			paramSource.addValue(Constants.EXIT_PLAZA_ID, inputs.getExitPlazaId());
			paramSource.addValue(Constants.ENTRY_PLAZA_ID, null!=inputs.getEntryPlazaId()?inputs.getEntryPlazaId():0);
			paramSource.addValue(Constants.ACTUAL_CLASS, inputs.getActualClass());
			paramSource.addValue(Constants.TOLL_REVENUE_TYPE,inputs.getTollRevenueType());
			paramSource.addValue(Constants.PRICE_SCHEDULE_ID, priceScheduleId);
			paramSource.addValue(Constants.PLAN_TYPE, null!=inputs.getPlanType()?inputs.getPlanType():findPlanType(inputs.getAccountType()));
						
			amount = namedJdbcTemplate.queryForObject(query, paramSource, BeanPropertyRowMapper.newInstance(APIOutput.class));
			
			if(amount != null)
			{
				return amount;
			}
			
		} //outer try end
		//outer catch start
		catch (DataAccessException e) 
		{
			try //inner try start
			{				
				String tollSystemType = inputs.getTollSystemType();
				dao_log.info("Toll System Type is : {}", tollSystemType);
				
				if(tollSystemType.equals("B") || tollSystemType.equals("P") || tollSystemType.equals("X"))
				{				
														
					paramSource.addValue(Constants.EFFECTIVE_DATE, converDate(inputs.getTxTimestamp().substring(0,10)));
					paramSource.addValue(Constants.EXPIRY_DATE, converDate(inputs.getTxTimestamp().substring(0,10)));
					paramSource.addValue(Constants.EXIT_PLAZA_ID, inputs.getExitPlazaId());
					paramSource.addValue(Constants.ENTRY_PLAZA_ID, inputs.getExitPlazaId());
					paramSource.addValue(Constants.ACTUAL_CLASS, inputs.getActualClass());
					paramSource.addValue(Constants.TOLL_REVENUE_TYPE,inputs.getTollRevenueType());
					paramSource.addValue(Constants.PRICE_SCHEDULE_ID, priceScheduleId);
					paramSource.addValue(Constants.PLAN_TYPE, null!=inputs.getPlanType()?inputs.getPlanType():findPlanType(inputs.getAccountType()));
										
					amount = namedJdbcTemplate.queryForObject(query, paramSource, BeanPropertyRowMapper.newInstance(APIOutput.class));
					
					if(amount != null)
					{
						return amount;
					}			
				}
			} //inner try end
			catch (DataAccessException e1) 
			{
				dao_log.info("No Amount found.");
			} 
			catch (ParseException e1) 
			{
				dao_log.info("Exception {} ",e.getMessage());
			}
		}//outer catch end 
		catch (ParseException e) 
		{
			dao_log.info("Exception {} ",e.getMessage());
		}
		
		return null;
	}
	

}
