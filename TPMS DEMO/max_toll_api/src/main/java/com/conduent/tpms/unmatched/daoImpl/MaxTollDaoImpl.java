package com.conduent.tpms.unmatched.daoImpl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import com.conduent.tpms.unmatched.constant.Constants;
import com.conduent.tpms.unmatched.constant.LoadJpaQueries;
import com.conduent.tpms.unmatched.dao.MaxTollDao;
import com.conduent.tpms.unmatched.dto.THighwaySection;
import com.conduent.tpms.unmatched.model.APIInputs;
import com.conduent.tpms.unmatched.model.AgencyHoliday;
import com.conduent.tpms.unmatched.model.SessionIdInputs;
import com.conduent.tpms.unmatched.model.TranDetail;
import com.conduent.tpms.unmatched.utility.Validation;

@Repository
public class MaxTollDaoImpl implements MaxTollDao
{

	private static final Logger dao_log = LoggerFactory.getLogger(MaxTollDaoImpl.class);
	
	@Autowired
	private NamedParameterJdbcTemplate namedJdbcTemplate;

	@Autowired
	Validation validation;
	
	@Override
	public TranDetail getTrxnInfo(long lane_tx_id) 
	{
		try 
		{
			TranDetail trxnInfo = new TranDetail();
			MapSqlParameterSource paramSource = new MapSqlParameterSource();
			//paramSource.addValue(Constants.ETC_ACCOUNT_ID, etc_account_id);
			paramSource.addValue(Constants.LANE_TX_ID, lane_tx_id);
			
			String query = LoadJpaQueries.getQueryById("GET_INFO_FROM_T_TRAN_DETAIL");
			trxnInfo = namedJdbcTemplate.queryForObject(query, paramSource, BeanPropertyRowMapper.newInstance(TranDetail.class));
			
			if(trxnInfo  !=null)
			{
				return trxnInfo;
			}
		}
		catch (DataAccessException e) 
		{
			dao_log.info("No record found for etc_account_id {} and lane_tx_id {}",lane_tx_id);
		}
		
		
		return null;
	}


	@Override
	public SessionIdInputs getSectionId(TranDetail tranDetail , APIInputs inputs) 
	{
		try 
		{
			SessionIdInputs sessionId = new SessionIdInputs();
			MapSqlParameterSource paramSource = new MapSqlParameterSource();
			paramSource.addValue(Constants.LANE_ID, inputs.getLaneId()!=null?inputs.getLaneId():tranDetail.getLaneId());
			
			String query = LoadJpaQueries.getQueryById("GET_SESSION_ID_INFO");
			sessionId = namedJdbcTemplate.queryForObject(query, paramSource, BeanPropertyRowMapper.newInstance(SessionIdInputs.class));
			
			if(sessionId  !=null)
			{
				return sessionId;
			}
		}
		catch (DataAccessException e) 
		{
			dao_log.info("No session id found");
			return null;
		}
		return null;
	}


	@Override
	public THighwaySection getTerminalPlazaInfo(int sessionId) 
	{
		try 
		{
			THighwaySection terminalInfo = new THighwaySection();
			MapSqlParameterSource paramSource = new MapSqlParameterSource();
			paramSource.addValue(Constants.SECTION_ID, sessionId);
			
			String query = LoadJpaQueries.getQueryById("GET_TERMINAL_INFO");
			terminalInfo = namedJdbcTemplate.queryForObject(query, paramSource, BeanPropertyRowMapper.newInstance(THighwaySection.class));
			
			if(terminalInfo  !=null)
			{
				return terminalInfo;
			}
		}
		catch (DataAccessException e) 
		{
			dao_log.info("No terminal plaza found for session_id {}",sessionId);
		}
		
		return null;
	}
	
	@Override
	public List<AgencyHoliday> getAgencyHoliday() {
		String queryToCheckFile = LoadJpaQueries.getQueryById("GET_T_AGENCY_HOLIDAY");
		return namedJdbcTemplate.query(queryToCheckFile, new BeanPropertyRowMapper<AgencyHoliday>(AgencyHoliday.class));
	}


	@Override
	public Integer getPriceScheduleId(APIInputs inputs,TranDetail info , String daysInd) throws ParseException 
	{
		try 
		{
			Integer priceScheduleId = 0;
			MapSqlParameterSource paramSource = new MapSqlParameterSource();
			String time = validation.timeFormatCorrection(null!=inputs.getTxTimestamp()?inputs.getTxTimestamp().substring(11,19):info.getTxTimeStamp().toString().substring(11,19));
			paramSource.addValue(Constants.AGENCY_ID, inputs.getAgencyId()!=null?inputs.getAgencyId():info.getPlazaAgencyId());
			paramSource.addValue(Constants.DAYS_IND, daysInd);			
			paramSource.addValue(Constants.START_TIME, time);
			paramSource.addValue(Constants.TX_DATE, null!=inputs.getTxTimestamp()?inputs.getTxTimestamp().substring(0,10):info.getTxTimeStamp().toString().substring(0,10));
			
			String query = LoadJpaQueries.getQueryById("GET_PRICE_SCHEDULE_ID");
			priceScheduleId = namedJdbcTemplate.queryForObject(query, paramSource,Integer.class);
			
			if(priceScheduleId!=null)
			{
				return priceScheduleId;
			}
			
		} catch (DataAccessException e)
		{
			dao_log.info("No price schedule id found for lane_tx_id {}",info.getLaneTxId());
		}
		
		return 0;
	}


	@Override
	public Double getDiscountFare(TranDetail info, int terminalPlaza, int priceScheduleId , APIInputs inputs) throws ParseException 
	{
		String query=null;
		try 
		{
			Double discountFareAmount;
			MapSqlParameterSource paramSource = new MapSqlParameterSource();
//			paramSource.addValue(Constants.EFFECTIVE_DATE, converDate(info.getTxDate().toString()));
//			paramSource.addValue(Constants.EXPIRY_DATE, converDate(info.getTxDate().toString()));
//			paramSource.addValue(Constants.PLAZA_ID, info.getPlazaId());
//			paramSource.addValue(Constants.TERMINAL_PLAZA,null!=inputs.getEntryPlazaId()?inputs.getEntryPlazaId():terminalPlaza);
//			paramSource.addValue(Constants.ACTUAL_CLASS, null!=inputs.getActualClass()?inputs.getActualClass():info.getActualClass());
//			paramSource.addValue(Constants.TOLL_REVENUE_TYPE, null!=inputs.getTollRevenueType()?inputs.getTollRevenueType():info.getTollRevenueType());
//			paramSource.addValue(Constants.PRICE_SCHEDULE_ID, priceScheduleId);
//			paramSource.addValue(Constants.PLAN_TYPE, null!=inputs.getPlanType()?inputs.getPlanType():info.getPlanType());
//			
			paramSource.addValue(Constants.EFFECTIVE_DATE, converDate(null!=inputs.getTxTimestamp()?inputs.getTxTimestamp().substring(0,10):info.getTxTimeStamp().substring(0,10)));
			paramSource.addValue(Constants.EXPIRY_DATE, converDate(null!=inputs.getTxTimestamp()?inputs.getTxTimestamp().substring(0,10):info.getTxTimeStamp().substring(0,10)));
			paramSource.addValue(Constants.PLAZA_ID, null!=inputs.getExitPlazaId()?inputs.getExitPlazaId():info.getPlazaId());
			paramSource.addValue(Constants.TERMINAL_PLAZA, null!=inputs.getEntryPlazaId()?inputs.getEntryPlazaId():terminalPlaza);
			paramSource.addValue(Constants.ACTUAL_CLASS, null!=inputs.getActualClass()?inputs.getActualClass():info.getActualClass());
			paramSource.addValue(Constants.TOLL_REVENUE_TYPE,null!=inputs.getTollRevenueType()?inputs.getTollRevenueType():info.getTollRevenueType());
			paramSource.addValue(Constants.PRICE_SCHEDULE_ID, priceScheduleId);
			paramSource.addValue(Constants.PLAN_TYPE, null!=inputs.getPlanType()?inputs.getPlanType():findPlanType(null!=inputs.getAccountType()?inputs.getAccountType():info.getPlanType()));
						
			
			//if(info.getReciprocityTrx().equals(Constants.T) && info.getTxSubType().equals(Constants.E))
//			if(info.getDeviceNo()!= null || inputs.getTollRevenueType().equals(1))
//			{
//				query = LoadJpaQueries.getQueryById("GET_DISCOUNTED_AMOUNT");
//			}
//			else
//			{
//				query = LoadJpaQueries.getQueryById("GET_FULL_FARE_AMOUNT");
//			}
			
			//change told by ronnie
			query = LoadJpaQueries.getQueryById("GET_DISCOUNTED_AMOUNT");
			
			discountFareAmount = namedJdbcTemplate.queryForObject(query, paramSource, Double.class);
			
			if(discountFareAmount != null)
			{
				return discountFareAmount;
			}
		} catch (DataAccessException e) 
		{
			dao_log.info("No discounted/full fare amount found for lane_tx_id {}",info.getLaneTxId());
			
		} catch (ParseException e) 
		{
			dao_log.info("Exception {} ",e.getMessage());
		}
		
		return 0D;
	}
	
	private Object findPlanType(long l) {			
		Integer planType = 1;
		
		if (l == Constants.ACC_TYPE_PRIVATE)
		{
			planType = Constants.PLAN_TYPE_STANDARD;
		}
        else if (l == Constants.ACC_TYPE_BUSINESS || l == Constants.ACC_TYPE_COMMERCIAL)     
        {
        	planType = Constants.PLAN_TYPE_BUSINESS;
        }
        else if (l == Constants.ACC_TYPE_STVA)
        {
        	planType = Constants.PLAN_TYPE_STVA;
        }
        else if (l == Constants.ACC_TYPE_RVA)
        {
        	planType = Constants.PLAN_TYPE_RVA;
        }
        else if (l == Constants.ACC_TYPE_PRVA)
        {
        	planType = Constants.PLAN_TYPE_PRVA;
        }
        else if (l == Constants.ACC_TYPE_BRVA)
        {
        	planType = Constants.PLAN_TYPE_BRVA;
        }
        else if (l == Constants.ACC_TYPE_PVIDEOUNREG)
        {
        	planType = Constants.PLAN_TYPE_PVIDEOUNREG;
        }
        else if (l == Constants.ACC_TYPE_BVIDEOUNREG)
        {
        	planType = Constants.PLAN_TYPE_BVIDEOUNREG;
        }
        // logic for plantype acc. to agencyId
		
		dao_log.info("plantType is : {}" ,planType);
		
		return planType;
	}


	@Override
	public Double getDiscountFarePlaza2(TranDetail info, int terminalPlaza2, int priceScheduleId , APIInputs inputs) throws ParseException 
	{
		String query=null;
		try 
		{
			Double discountFareAmount;
			MapSqlParameterSource paramSource = new MapSqlParameterSource();

			paramSource.addValue(Constants.EFFECTIVE_DATE, converDate(null!=inputs.getTxTimestamp()?inputs.getTxTimestamp().substring(0,10):info.getTxTimeStamp().substring(0,10)));
			paramSource.addValue(Constants.EXPIRY_DATE, converDate(null!=inputs.getTxTimestamp()?inputs.getTxTimestamp().substring(0,10):info.getTxTimeStamp().substring(0,10)));
			paramSource.addValue(Constants.PLAZA_ID, null!=inputs.getExitPlazaId()?inputs.getExitPlazaId():info.getPlazaId());
			paramSource.addValue(Constants.TERMINAL_PLAZA, null!=inputs.getEntryPlazaId()?inputs.getEntryPlazaId():terminalPlaza2);
			paramSource.addValue(Constants.ACTUAL_CLASS, null!=inputs.getActualClass()?inputs.getActualClass():info.getActualClass());
			paramSource.addValue(Constants.TOLL_REVENUE_TYPE,null!=inputs.getTollRevenueType()?inputs.getTollRevenueType():info.getTollRevenueType());
			paramSource.addValue(Constants.PRICE_SCHEDULE_ID, priceScheduleId);
			paramSource.addValue(Constants.PLAN_TYPE, null!=inputs.getPlanType()?inputs.getPlanType():findPlanType(null!=inputs.getAccountType()?inputs.getAccountType():info.getPlanType()));
					
			//if(info.getReciprocityTrx().equals(Constants.T) && info.getTxSubType().equals(Constants.E))
//			if(info.getDeviceNo()!= null || inputs.getTollRevenueType().equals(1))
//			{
//				query = LoadJpaQueries.getQueryById("GET_DISCOUNTED_AMOUNT");
//			}
//			else
//			{
//				query = LoadJpaQueries.getQueryById("GET_FULL_FARE_AMOUNT");
//			}
			
			//change told by ronnie
			query = LoadJpaQueries.getQueryById("GET_DISCOUNTED_AMOUNT");
			
			discountFareAmount = namedJdbcTemplate.queryForObject(query, paramSource, Double.class);
			
			if(discountFareAmount != null)
			{
				return discountFareAmount;
			}
		} catch (DataAccessException e) 
		{
			dao_log.info("No discounted/full fare amount found for lane_tx_id {}",info.getLaneTxId());
			
		} catch (ParseException e) 
		{
			dao_log.info("Exception {} ",e.getMessage());
		}
		
		return 0D;
	}
	
	public String converDate(String localDateTime) throws ParseException
	{
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat format2 = new SimpleDateFormat("dd-MMM-YY");
        Date convertedDate = format1.parse(localDateTime);
        String convertedDate2 = format2.format(convertedDate).toUpperCase();
		return convertedDate2;
	}


	@Override
	public AgencyHoliday getAgencyHoliday(TranDetail info,APIInputs inputs) 
	{
		try {
			AgencyHoliday agencyInfo = new AgencyHoliday();
			MapSqlParameterSource paramSource = new MapSqlParameterSource();
			paramSource.addValue(Constants.AGENCY_ID, null!=inputs.getAgencyId()?inputs.getAgencyId():info.getPlazaAgencyId());
			paramSource.addValue(Constants.TX_DATE, null!=inputs.getTxTimestamp()?inputs.getTxTimestamp().substring(0,10):info.getTxTimeStamp().toString().substring(0,10));
			
			String query = LoadJpaQueries.getQueryById("GET_T_AGENCY_HOLIDAY");
			agencyInfo = namedJdbcTemplate.queryForObject(query, paramSource, BeanPropertyRowMapper.newInstance(AgencyHoliday.class));
			
			if(agencyInfo  !=null)
			{
				return agencyInfo;
			}
			
		} catch (DataAccessException e)
		{
			dao_log.info("No record found in T_AGENCY_HOLIDAY Table");
		}
		
		return null;
	}


	@Override
	public Integer getPriceScheduleIdTPMS(APIInputs info, String daysInd) throws ParseException 
	{
		try 
		{
			Integer priceScheduleId;
			MapSqlParameterSource paramSource = new MapSqlParameterSource();
			paramSource.addValue(Constants.PLAZA_AGENCY_ID, info.getAgencyId());
			//paramSource.addValue(Constants.EFFECTIVE_DATE, converDate(info.getTxDate().toString()));
			//paramSource.addValue(Constants.EXPIRY_DATE, converDate(info.getTxDate().toString()));
			paramSource.addValue(Constants.DAYS_IND, daysInd);
			String time = info.getTxTimestamp().substring(11,19);
			paramSource.addValue(Constants.START_TIME, time);
			//paramSource.addValue(Constants.END_TIME, info.getTxTimeStamp().toLocalTime());
			paramSource.addValue(Constants.TX_DATE, info.getTxTimestamp().substring(0,11));
			
			String query = "SELECT price_schedule_id        \r\n"
					+ "FROM master.t_toll_price_schedule p        \r\n"
					+ "WHERE p.days_ind = :DAYS_IND AND p.agency_id = :PLAZA_AGENCY_ID        \r\n"
					+ "AND to_date('"+info.getTxTimestamp().substring(0,11)+"','YYYY-MM-DD') \r\n"
					+ "between p.effective_date AND p.expiry_date        \r\n"
					+ "and to_date('"+time+"', 'HH24:MI:SS')\r\n"
					+ "between to_date(p.start_time, 'HH24:MI:SS') and       \r\n"
					+ "to_date(p.end_time, 'HH24:MI:SS')        \r\n"
					+ "AND ROWNUM < 2";
			
			priceScheduleId = namedJdbcTemplate.queryForObject(query, paramSource,Integer.class);
			
			if(priceScheduleId  !=null)
			{
				return priceScheduleId;
			}
			
		} catch (DataAccessException e)
		{
			dao_log.info("No price schedule id found for lane_tx_id {}",info.getLaneTxId());
		}
		
		return 0;
	}


	@Override
	public Double getDiscountFareTPMS(int terminalPlaza, int priceScheduleId, APIInputs info,TranDetail trandetailInfo) throws ParseException 
	{
		String query = null;
		try 
		{
			Double discountFareAmount;
			MapSqlParameterSource paramSource = new MapSqlParameterSource();
			paramSource.addValue(Constants.EFFECTIVE_DATE, converDate(info.getTxTimestamp().substring(0,11)));
			paramSource.addValue(Constants.EXPIRY_DATE, converDate(info.getTxTimestamp().substring(0,11)));
			paramSource.addValue(Constants.PLAZA_ID, info.getExitPlazaId());
			paramSource.addValue(Constants.TERMINAL_PLAZA,null!=info.getEntryPlazaId()?info.getEntryPlazaId():terminalPlaza);
			paramSource.addValue(Constants.ACTUAL_CLASS, info.getActualClass());
			paramSource.addValue(Constants.TOLL_REVENUE_TYPE, info.getTollRevenueType());
			paramSource.addValue(Constants.PRICE_SCHEDULE_ID, priceScheduleId);
			paramSource.addValue(Constants.PLAN_TYPE, info.getPlanType());
			
			//if(info.getReciprocityTrx().equals(Constants.T) && info.getTxSubType().equals(Constants.E))
//			if(trandetailInfo.getDeviceNo()!= null || info.getTollRevenueType().equals(1))
//			{
//				query = LoadJpaQueries.getQueryById("GET_DISCOUNTED_AMOUNT");
//			}
//			else
//			{
//				query = LoadJpaQueries.getQueryById("GET_FULL_FARE_AMOUNT");
//			}
			
			//change told by ronnie
			query = LoadJpaQueries.getQueryById("GET_DISCOUNTED_AMOUNT");
			
			discountFareAmount = namedJdbcTemplate.queryForObject(query, paramSource, Double.class);
			
			if(discountFareAmount != null)
			{
				return discountFareAmount;
			}
		} catch (DataAccessException e) 
		{
			dao_log.info("No discounted or full fare amount found for lane_tx_id {}",info.getLaneTxId());
			
		} catch (ParseException e) 
		{
			dao_log.info("Exception {} ",e.getMessage());
		}
		
		return 0D;
	}


	@Override
	public Double getDiscountFarePlaza2TPMS(int terminalPlaza, int priceScheduleId, APIInputs info,TranDetail trandetailInfo)
			throws ParseException 
	{
		String query = null;
		try 
		{
			Double discountFareAmount;
			MapSqlParameterSource paramSource = new MapSqlParameterSource();
			paramSource.addValue(Constants.EFFECTIVE_DATE, converDate(info.getTxTimestamp().substring(0,11)));
			paramSource.addValue(Constants.EXPIRY_DATE, converDate(info.getTxTimestamp().substring(0,11)));
			paramSource.addValue(Constants.PLAZA_ID,info.getExitPlazaId());
			paramSource.addValue(Constants.TERMINAL_PLAZA,terminalPlaza);
			paramSource.addValue(Constants.ACTUAL_CLASS, info.getActualClass());
			paramSource.addValue(Constants.TOLL_REVENUE_TYPE, info.getTollRevenueType());
			paramSource.addValue(Constants.PRICE_SCHEDULE_ID, priceScheduleId);
			paramSource.addValue(Constants.PLAN_TYPE, info.getPlanType());

			
			//if(info.getReciprocityTrx().equals(Constants.T) && info.getTxSubType().equals(Constants.E))
//			if(trandetailInfo.getDeviceNo()!= null || info.getTollRevenueType().equals(1))
//			{
//				query = LoadJpaQueries.getQueryById("GET_DISCOUNTED_AMOUNT");
//			}
//			else
//			{
//				query = LoadJpaQueries.getQueryById("GET_FULL_FARE_AMOUNT");
//			}
			
			//change told by ronnie
			query = LoadJpaQueries.getQueryById("GET_DISCOUNTED_AMOUNT");
			
			discountFareAmount = namedJdbcTemplate.queryForObject(query, paramSource, Double.class);
			
			if(discountFareAmount != null)
			{
				return discountFareAmount;
			}
		} catch (DataAccessException e) 
		{
			dao_log.info("No discounted/full fare amount found for lane_tx_id {}",info.getLaneTxId());
			
		} catch (ParseException e) 
		{
			dao_log.info("Exception {} ",e.getMessage());
		}
		
		return 0D;
	}
	
	@Override
	public TranDetail getTrnxInfo(Long laneTxId)
	{
		TranDetail trnxInfo=null;
		try 
		{
			MapSqlParameterSource paramSource = new MapSqlParameterSource();
			paramSource.addValue(Constants.LANE_TX_ID, laneTxId);
			
			String query = LoadJpaQueries.getQueryById("GET_TRNX_INFO");
			trnxInfo = namedJdbcTemplate.queryForObject(query, paramSource, BeanPropertyRowMapper.newInstance(TranDetail.class));
			
			if(trnxInfo != null)
			{
				return trnxInfo;
			}
			else
			{
				return null;
			}
		} catch (DataAccessException e) 
		{
			dao_log.info("No info present in T_TRAN_DETAIL Table for lane_tx_id {}",laneTxId);
			return null;
			
		}
	}

}
