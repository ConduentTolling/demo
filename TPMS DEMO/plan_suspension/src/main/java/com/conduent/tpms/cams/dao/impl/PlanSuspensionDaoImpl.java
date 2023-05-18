package com.conduent.tpms.cams.dao.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.conduent.tpms.cams.config.LoadJpaQueries;
import com.conduent.tpms.cams.constants.PlanSuspensionConstant;
import com.conduent.tpms.cams.dao.PlanSuspensionDao;
import com.conduent.tpms.cams.dto.PlanSuspensionRequestModel;
import com.conduent.tpms.cams.dto.PlanSuspensionRequestModel2;
import com.conduent.tpms.cams.dto.PlanSuspensionResponseModel;
import com.conduent.tpms.cams.util.ValidationUtil;

@Repository
public class PlanSuspensionDaoImpl implements PlanSuspensionDao {

	@PersistenceContext
	EntityManager em;

	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;

	final static Logger logger = LoggerFactory.getLogger(PlanSuspensionDaoImpl.class);

	@Autowired
	ValidationUtil validationUtil;

	MapSqlParameterSource paramSource = new MapSqlParameterSource();
	
	

	@Override
	public Page<PlanSuspensionResponseModel> fetchPlanSuspensionDaoList(
			PlanSuspensionRequestModel planSuspensionRequestModel) throws ParseException {
		
		String queryCountString =LoadJpaQueries.getQueryById("GET_COUNT_PLAN_SUSPENSION_DETAILS_RECORD");
		
        javax.persistence.Query query = null;
		
		javax.persistence.Query queryCount= null;
		
		Map<String, String> paramMaps = new HashMap<>();
		
		

		logger.info("Entering fetchPlanSuspensionDaoList Method ");

		String queryString = LoadJpaQueries.getQueryById("GET_PLAN_SUSPENSION_DETAILS");
		

		if (planSuspensionRequestModel.getEtcAccountId() != null) {
			logger.info("Etc AccountId not null");
			queryString = queryString + " WHERE ETC_ACCOUNT_ID=:ETC_ACCOUNT_ID ";
			paramSource.addValue(PlanSuspensionConstant.ETC_ACCOUNT_ID, planSuspensionRequestModel.getEtcAccountId());
			queryCountString = queryCountString+" WHERE ETC_ACCOUNT_ID=:ETC_ACCOUNT_ID ";
			
			
			
			query=em.createNativeQuery(queryString);
			logger.info("Search query ::: {}",queryString);
			query.setParameter("ETC_ACCOUNT_ID", planSuspensionRequestModel.getEtcAccountId());
			//queryCount=em.createNativeQuery(queryCountString);
			//logger.info("Search Count query ::: {}",queryCount);			
			//queryCount.setParameter("ETC_ACCOUNT_ID", planSuspensionRequestModel.getEtcAccountId());
			
			//paramMaps.put("ETC_ACCOUNT_ID", planSuspensionRequestModel.getEtcAccountId().toString());
			paramMaps.put(PlanSuspensionConstant.ETC_ACCOUNT_ID,planSuspensionRequestModel.getEtcAccountId().toString());
			
			
			if (planSuspensionRequestModel.getApdId() != null) {
				queryString = queryString + " AND APD_ID =:APD_ID ";
				paramSource.addValue(PlanSuspensionConstant.APD_ID, planSuspensionRequestModel.getApdId());
				queryCountString = queryCountString.concat(" AND APD_ID=:APD_ID");
				

				query=em.createNativeQuery(queryString);
				logger.info("Search query ::: {}",queryString);
				query.setParameter("APD_ID", planSuspensionRequestModel.getEtcAccountId());
				//queryCount=em.createNativeQuery(queryCountString); // Dont call this operation here
				//logger.info("Search Count query ::: {}",queryCount);
				//queryCount.setParameter("APD_ID", planSuspensionRequestModel.getApdId());
				
				//paramMaps.put("APD_ID", planSuspensionRequestModel.getApdId().toString());
				paramMaps.put(PlanSuspensionConstant.APD_ID,planSuspensionRequestModel.getApdId().toString());
				
				
			}
			if (planSuspensionRequestModel.getStartDate() != null && planSuspensionRequestModel.getEndDate() != null) {
				if (ValidationUtil.isDateValid(planSuspensionRequestModel.getStartDate().toString())
						&& ValidationUtil.isDateValid(planSuspensionRequestModel.getEndDate().toString())) {
					queryString = queryString + " AND START_DATE >=:START_DATE AND END_DATE <=:END_DATE ";
					paramSource.addValue(PlanSuspensionConstant.START_DATE,
							convertToDate(planSuspensionRequestModel.getStartDate()));
					paramSource.addValue(PlanSuspensionConstant.END_DATE,
							convertToDate(planSuspensionRequestModel.getEndDate()));
					queryCountString = queryCountString.concat(" AND START_DATE >=:START_DATE AND END_DATE <=:END_DATE");
					
					//paramMaps.put("START_DATE", convertToDate(planSuspensionRequestModel.getStartDate()));
					paramMaps.put(PlanSuspensionConstant.START_DATE,convertToDate(planSuspensionRequestModel.getStartDate()));
					//paramMaps.put("END_DATE", convertToDate(planSuspensionRequestModel.getStartDate()));
					paramMaps.put(PlanSuspensionConstant.END_DATE,convertToDate(planSuspensionRequestModel.getEndDate()));
					
					
				} else {
					logger.info("invalid date format ");
				}
				
				query=em.createNativeQuery(queryString);
				logger.info("Search query ::: {}",queryString);
				query.setParameter("START_DATE",convertToDate(planSuspensionRequestModel.getStartDate()) );
				query.setParameter("END_DATE",convertToDate(planSuspensionRequestModel.getEndDate()) );
				//queryCount=em.createNativeQuery(queryCountString); // Donot call this opertaion
				//logger.info("Search Count query ::: {}",queryCount);
				//queryCount.setParameter("START_DATE",convertToDate(planSuspensionRequestModel.getStartDate()));
				//queryCount.setParameter("END_DATE", convertToDate(planSuspensionRequestModel.getEndDate()));
			}

			if (planSuspensionRequestModel.getStartDate() != null && planSuspensionRequestModel.getEndDate() == null) {
				logger.info("line## 79 ");
				if (ValidationUtil.isDateValid(planSuspensionRequestModel.getStartDate().toString())) {
					queryString = queryString + " AND START_DATE >=:START_DATE ";
					paramSource.addValue(PlanSuspensionConstant.START_DATE,convertToDate(planSuspensionRequestModel.getStartDate()));
					queryCountString = queryCountString.concat(" AND START_DATE >=:START_DATE ");
					
					//paramMaps.put("START_DATE", convertToDate(planSuspensionRequestModel.getStartDate()));					
					paramMaps.put(PlanSuspensionConstant.START_DATE,convertToDate(planSuspensionRequestModel.getStartDate()));
					
					
				} else {
					logger.info("invalid date format ");
				}
				
				query=em.createNativeQuery(queryString);
				logger.info("Search query ::: {}",queryString);
				query.setParameter("START_DATE", convertToDate(planSuspensionRequestModel.getStartDate()));
				//queryCount=em.createNativeQuery(queryCountString);
				//logger.info("Search Count query ::: {}",queryCount);
				//queryCount.setParameter("START_DATE", convertToDate(planSuspensionRequestModel.getStartDate()));
			}

			if (planSuspensionRequestModel.getStartDate() == null && planSuspensionRequestModel.getEndDate() != null) {
				if (ValidationUtil.isDateValid(planSuspensionRequestModel.getEndDate().toString())) {
					queryString = queryString + " AND END_DATE <=:END_DATE ";
					paramSource.addValue(PlanSuspensionConstant.END_DATE,convertToDate(planSuspensionRequestModel.getEndDate()));					
					queryCountString = queryCountString.concat(" AND END_DATE <=:END_DATE ");
					logger.info("EndDate:::{}",planSuspensionRequestModel.getEndDate());
					
					//paramMaps.put("END_DATE", convertToDate(planSuspensionRequestModel.getEndDate()));
					paramMaps.put(PlanSuspensionConstant.END_DATE,convertToDate(planSuspensionRequestModel.getEndDate()));
				}
					 else {
					logger.info("invalid date format ");
				}
				
				query=em.createNativeQuery(queryString);
				logger.info("Search query ::: {}",queryString);
				query.setParameter("END_DATE", convertToDate(planSuspensionRequestModel.getEndDate()));
				//queryCount=em.createNativeQuery(queryCountString);
				//logger.info("Search Count query ::: {}",queryCount);
				//queryCount.setParameter("END_DATE", convertToDate(planSuspensionRequestModel.getEndDate()));
			}

			if (planSuspensionRequestModel.getStartDate() == null && planSuspensionRequestModel.getEndDate() == null) {
				queryString = queryString + " AND END_DATE IS NULL ";
				queryCountString = queryCountString.concat(" AND END_DATE=NULL");
				
				query=em.createNativeQuery(queryString);
				logger.info("Search query ::: {}",queryString);
				
			}

			if (planSuspensionRequestModel.getStatus() != null) {
				if (planSuspensionRequestModel.getStatus().equalsIgnoreCase(PlanSuspensionConstant.OPEN_SUSPENSION_STATUS)) {
					queryString = queryString + " AND SUSPENSION_STATUS = 1 ";
					queryCountString = queryCountString.concat(" AND SUSPENSION_STATUS = 1");
				} else if (planSuspensionRequestModel.getStatus()
						.equalsIgnoreCase(PlanSuspensionConstant.CLOSED_SUSPENSION_STATUS)) {
					queryString = queryString + " AND SUSPENSION_STATUS = 2 ";
					queryCountString = queryCountString.concat(" AND SUSPENSION_STATUS = 2");
				}
				
						
			}	
			
			queryCount= em.createNativeQuery(queryCountString);
			for (Map.Entry<String, String> entry : paramMaps.entrySet()) {
				queryCount.setParameter(entry.getKey(), entry.getValue());	
			} 
			
			//queryString=queryString + " LIMIT " + planSuspensionRequestModel.getSize() + " OFFSET " + planSuspensionRequestModel.getPage();
			queryString=queryString +" OFFSET "+planSuspensionRequestModel.getPage()*planSuspensionRequestModel.getSize()+" ROWS FETCH NEXT "+ planSuspensionRequestModel.getSize() + " ROWS ONLY ";
			
			logger.info("queryString {}", queryString);
			/*queryCount.setFirstResult(planSuspensionRequestModel.getPage()*planSuspensionRequestModel.getSize());
			queryCount.setMaxResults(planSuspensionRequestModel.getSize());
			query.setFirstResult(planSuspensionRequestModel.getPage()*planSuspensionRequestModel.getSize());
			query.setMaxResults(planSuspensionRequestModel.getSize());*/
			
			@SuppressWarnings("unchecked")
			Page<PlanSuspensionResponseModel> listInfo = new PageImpl<>(jdbcTemplate.query(queryString, paramSource,new BeanPropertyRowMapper<>(PlanSuspensionResponseModel.class)));
			logger.info("List info {}", listInfo.toString());
			
		
			logger.info("queryCount.getMaxResults() ::: {}",(((BigDecimal) queryCount.getSingleResult()).longValue()));
			planSuspensionRequestModel.setRecordCount(((BigDecimal) queryCount.getSingleResult()).longValue());
	    	
			return listInfo;
				
			
		} 
		else {
			logger.info("Enter Account Id");
		}
		
		
				return null;	
	}


	public Integer insertPlanSuspensionDaoDetails(PlanSuspensionRequestModel2 planSuspensionRequest2) throws ParseException {
		logger.info("Entering insertPlanSuspensionDaoDetails Dao Method ");
		String queryCountString = LoadJpaQueries.getQueryById("GET_COUNT_PLAN_SUSPENSION_DETAILS");
		javax.persistence.Query queryCount = null;
		Integer id = null; // here set ETC_ACCOUNT_ID
		
		queryCount = em.createNativeQuery(queryCountString);
		logger.info(queryCountString);
		logger.info("Existing plans Count for  ::: {}", queryCount);
		queryCount.setParameter("ETC_ACCOUNT_ID", planSuspensionRequest2.getEtcAccountId());
		queryCount.setParameter("APD_ID", planSuspensionRequest2.getApdId());
	
		logger.info("queryCount.getMaxResults() ::: {}", ((BigDecimal) queryCount.getSingleResult()).longValue());
		

        if (((BigDecimal) queryCount.getSingleResult()).longValue() == 0) {
			logger.info("No existing plan found for etcAccountID {}", planSuspensionRequest2.getEtcAccountId());
			paramSource.addValue(PlanSuspensionConstant.ETC_ACCOUNT_ID, planSuspensionRequest2.getEtcAccountId());
			paramSource.addValue(PlanSuspensionConstant.APD_ID, planSuspensionRequest2.getApdId());
			paramSource.addValue(PlanSuspensionConstant.USER_ID, planSuspensionRequest2.getUserId());
			paramSource.addValue(PlanSuspensionConstant.START_DATE,planSuspensionRequest2.getStartDate() != null ? convertToDate(planSuspensionRequest2.getStartDate()): getLocalDateDD_MMM_YY());
			paramSource.addValue(PlanSuspensionConstant.SUSPENSION_STATUS, 1);
			// MM/DD/YYYY
			String queryString = LoadJpaQueries.getQueryById("INSERT_PLAN_SUSPENSION_DETAILS");
			id = jdbcTemplate.update(queryString, paramSource);
			
		}else if(((BigDecimal) queryCount.getSingleResult()).longValue() > 0) {
			
			String queryCountString2 = LoadJpaQueries.getQueryById("GET_COUNT_PLAN_SUSPENSION_DETAILS_STATUS");
			javax.persistence.Query queryCount2 = null;
			Integer id2=null;
			
			queryCount2 = em.createNativeQuery(queryCountString2);
			logger.info(queryCountString2);
			logger.info("Existing plans Count for  ::: {}", queryCount2);
			queryCount2.setParameter("ETC_ACCOUNT_ID", planSuspensionRequest2.getEtcAccountId());
			queryCount2.setParameter("APD_ID", planSuspensionRequest2.getApdId());
		
			logger.info("queryCount.getMaxResults() ::: {}", ((BigDecimal) queryCount2.getSingleResult()).longValue());
			
			if (((BigDecimal) queryCount2.getSingleResult()).longValue() >= 1 ) {

			logger.info("Existing plan found for etcAccountID {}", planSuspensionRequest2.getEtcAccountId());
			paramSource.addValue(PlanSuspensionConstant.ETC_ACCOUNT_ID, planSuspensionRequest2.getEtcAccountId());
			paramSource.addValue(PlanSuspensionConstant.APD_ID, planSuspensionRequest2.getApdId());
			//paramSource.addValue(PlanSuspensionConstant.USER_ID, planSuspensionRequest2.getUserId());
			//paramSource.addValue(PlanSuspensionConstant.START_DATE,planSuspensionRequest2.getStartDate() != null ? convertToDate(planSuspensionRequest2.getStartDate()): getLocalDateDD_MMM_YY());
			paramSource.addValue(PlanSuspensionConstant.SUSPENSION_STATUS, 1);
		
			
			String queryString2 = LoadJpaQueries.getQueryById("UPDATE_STATUS_PLAN_SUSPENSION_DETAILS");
			id2 = jdbcTemplate.update(queryCountString2, paramSource);
			}
			else {
				return id2;
			}
			
			
			
		}
        return id;
	}
		

       
	
		/*
			 * else if(((BigDecimal) queryCount.getSingleResult()).longValue() > 0) { String
			 * queryCountString2 =
			 * LoadJpaQueries.getQueryById("GET_COUNT_PLAN_SUSPENSION_DETAILS_STATUS");
			 * javax.persistence.Query queryCount2 = null; Integer id2 = null; queryCount2 =
			 * em.createNativeQuery(queryCountString2); logger.info(queryCountString2);
			 * logger.info("Existing plans Count for  ::: {}", queryCount2);
			 * queryCount2.setParameter("ETC_ACCOUNT_ID",
			 * planSuspensionRequest2.getEtcAccountId());
			 * queryCount2.setParameter("APD_ID",planSuspensionRequest2.getApdId());
			 * 
			 * logger.info("queryCount2.getMaxResults() ::: {}",
			 * ((BigDecimal)queryCount2.getSingleResult()).longValue());
			 * 
			 * if (((BigDecimal) queryCount2.getSingleResult()).longValue() > 0 ) {
			 * logger.info("No existing plan found for apdId {}",
			 * planSuspensionRequest2.getEtcAccountId());
			 * paramSource.addValue(PlanSuspensionConstant.ETC_ACCOUNT_ID,
			 * planSuspensionRequest2.getEtcAccountId());
			 * paramSource.addValue(PlanSuspensionConstant.APD_ID,
			 * planSuspensionRequest2.getApdId());
			 * paramSource.addValue(PlanSuspensionConstant.USER_ID,
			 * planSuspensionRequest2.getUserId());
			 * paramSource.addValue(PlanSuspensionConstant.START_DATE,planSuspensionRequest2
			 * .getStartDate() != null
			 * ?convertToDate(planSuspensionRequest2.getStartDate()):
			 * getLocalDateDD_MMM_YY()); // MM/DD/YYYY String queryString =
			 * LoadJpaQueries.getQueryById("INSERT_PLAN_SUSPENSION_DETAILS"); id2 =
			 * jdbcTemplate.update(queryCountString2, paramSource);
			 * 
			 * }
			 */
			
		 /*else {
			}
			String updateQueryString = "UPDATE T_ACCOUNT_PLAN_SUSPENSION SET END_DATE = CURRENT_TIMESTAMP, SUSPENSION_STATUS=2 where ETC_ACCOUNT_ID=:ETC_ACCOUNT_ID";
			logger.info("Update Query  ::: {}", updateQueryString);
			paramSource.addValue(PlanSuspensionConstant.ETC_ACCOUNT_ID, planSuspensionRequest.getEtcAccountId());
			jdbcTemplate.update(updateQueryString, paramSource);
			paramSource.addValue(PlanSuspensionConstant.ETC_ACCOUNT_ID, planSuspensionRequest.getEtcAccountId());
			paramSource.addValue(PlanSuspensionConstant.APD_ID, planSuspensionRequest.getApdId());
			paramSource.addValue(PlanSuspensionConstant.USER_ID, planSuspensionRequest.getUserId());
			try {
				paramSource.addValue(PlanSuspensionConstant.START_DATE,
						convertToDate(planSuspensionRequest.getStartDate()));
			} catch (ParseException pe) {
				logger.error("ParseException line#167 {}", pe);
			}
			String queryString = LoadJpaQueries.getQueryById("INSERT_PLAN_SUSPENSION_DETAILS");
			id = jdbcTemplate.update(queryString, paramSource);
		}*/
			
			


	public Integer updatePlanSuspensionDaoDetails(PlanSuspensionRequestModel2 planSuspensionRequest2)
			throws ParseException {
		logger.info("Entering insertPlanSuspensionDaoDetails Dao Method ");
		String queryCountString = LoadJpaQueries.getQueryById("GET_COUNT_PLAN_SUSPENSION_DETAILS");
		javax.persistence.Query queryCount = null;
		Integer id = null;
		queryCount = em.createNativeQuery(queryCountString);
		logger.info(queryCountString);
		logger.info("Search Count query ::: {}", queryCount);
		queryCount.setParameter("ETC_ACCOUNT_ID", planSuspensionRequest2.getEtcAccountId());
		queryCount.setParameter("APD_ID", planSuspensionRequest2.getApdId());

		logger.info("queryCount.getMaxResults() ::: {}", ((BigDecimal) queryCount.getSingleResult()).longValue());
		if (((BigDecimal) queryCount.getSingleResult()).longValue() > 0) {
			paramSource.addValue(PlanSuspensionConstant.ETC_ACCOUNT_ID, planSuspensionRequest2.getEtcAccountId());
			paramSource.addValue(PlanSuspensionConstant.APD_ID,
					planSuspensionRequest2.getApdId() != null ? planSuspensionRequest2.getApdId() : 0);
			paramSource.addValue(PlanSuspensionConstant.USER_ID, planSuspensionRequest2.getUserId());
			paramSource.addValue(PlanSuspensionConstant.END_DATE,
					planSuspensionRequest2.getEndDate() != null ? convertToDate(planSuspensionRequest2.getEndDate())
							: getLocalDateDD_MMM_YY());
			String queryString = LoadJpaQueries.getQueryById("UPDATE_PLAN_SUSPENSION_DETAILS");
			id = jdbcTemplate.update(queryString, paramSource);
		}
		return id;
	}

	public static Long convertToLong(Object o) {
		if (o != null) {
			String stringToConvert = String.valueOf(o);
			logger.info("stringToConvert ::: {}", stringToConvert);
			Long convertedLong = Long.parseLong(stringToConvert);
			return convertedLong;
		}
		return null;
	}

	public static LocalDate convertToDate(Object o) {
		if (o != null) {
			String stringToConvert = String.valueOf(o);
			Timestamp timestamp = Timestamp.valueOf(stringToConvert);
			String dateFormated = new SimpleDateFormat("MM-dd-yyyy").format(timestamp);
			return LocalDate.parse(dateFormated);
		}
		return null;
	}

	public static Double convertToDouble(Object o) {
		if (o != null) {
			String stringToConvert = String.valueOf(o);
			Double convertedLong = Double.valueOf(stringToConvert);
			return convertedLong;
		}
		return null;

	}

	public static String convertToDate(String dateString) throws ParseException {
		logger.info("Given date is {}", dateString);
		Date date;
		try {
			DateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
			date = sdf.parse(dateString);
			logger.info("dd-MMM-yy : {}", new SimpleDateFormat("dd-MMM-yy").format(date));
			return new SimpleDateFormat("dd-MMM-yy").format(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static String getLocalDateDD_MMM_YY() throws ParseException {
		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = sdf.parse(LocalDate.now().toString());
		return new SimpleDateFormat("dd-MMM-yy").format(date);
	}
	
	@SuppressWarnings("unused")
	/*private List<PlanSuspensionResponseModel> convertToDto(List<Object[]> resultList) {
		List<PlanSuspensionResponseModel> responseList=new ArrayList<>();
		for (Object[] object : resultList) {
			PlanSuspensionResponseModel dto=new PlanSuspensionResponseModel();
			dto.setEtcAccountId(convertToLong(object[0]));
			dto.setApdId(convertToLong(object[1]));
			dto.setStartDate(String.valueOf(object[2]));
			dto.setEndDate(String.valueOf(object[3]));
			dto.setSuspensionStatus(convertToLong(object[4]));
			dto.setUpdateTS(String.valueOf(object[5]));
			dto.setCreateDate(String.valueOf(object[5]));
			dto.setCscLookupKey(String.valueOf(object[6]));
			dto.setUserId(String.valueOf(object[7]));
			
			responseList.add(dto);
		}
		return responseList;
	}*/

	
	@Override
	public long fetchPlanSuspensionCount(PlanSuspensionRequestModel planSuspensionRequestModel) {
		// TODO Auto-generated method stub
		return planSuspensionRequestModel.getRecordCount();
	} 

}
