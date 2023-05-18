package com.conduent.tpms.cams.dao.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.conduent.tpms.cams.config.LoadJpaQueries;
import com.conduent.tpms.cams.dao.TripHistoryDao;
import com.conduent.tpms.cams.dto.TTripHistory;

@Repository
public class TripHistoryDaoImpl implements TripHistoryDao{

	@PersistenceContext
	EntityManager em;
	
	@Autowired
	private NamedParameterJdbcTemplate namedJdbcTemplate;
	
	private static final DecimalFormat df = new DecimalFormat("0.00");
	
		
	final Logger logger = LoggerFactory.getLogger(TripHistoryDaoImpl.class);
		

	@Override
	public List<TTripHistory> fetchTripHistory(TTripHistory tripHistory) {
		
		logger.info("Entering Dao Method");	
		
		String queryString =LoadJpaQueries.getQueryById("GET_TRIP_HISTORY_DETAILS");
		
		String queryCountString =LoadJpaQueries.getQueryById("GET_COUNT_TRIP_HISTORY_DETAILS");
		
		String contactOrderQuery = " ORDER BY APD_ID,TRIP_START_DATE,TRIP_END_DATE,TRIPS_MADE";
		
		javax.persistence.Query query = null;
		
		javax.persistence.Query queryCount= null;
		
		if(tripHistory.getEtcAccountId()!=null && tripHistory.getApdId()==null && 
		tripHistory.getTripEndDate()==null && tripHistory.getTripStartDate()==null) {
			
			if(tripHistory.getStatus()!=null && tripHistory.getStatus().equals("ALL")) {
			
			queryString = queryString.concat(" WHERE ETC_ACCOUNT_ID=:ETC_ACCOUNT_ID").concat(contactOrderQuery);
			queryCountString = queryCountString.concat(" WHERE ETC_ACCOUNT_ID=:ETC_ACCOUNT_ID").concat(contactOrderQuery);
			}else {
				queryString = queryString.concat(" WHERE ETC_ACCOUNT_ID=:ETC_ACCOUNT_ID AND TRIP_END_DATE>=TRUNC(SYSDATE-1)").concat(contactOrderQuery);
				queryCountString = queryCountString.concat(" WHERE ETC_ACCOUNT_ID=:ETC_ACCOUNT_ID AND TRIP_END_DATE>=TRUNC(SYSDATE-1)").concat(contactOrderQuery);
			}
			query=em.createNativeQuery(queryString);
			logger.info("Search query ::: {}",queryString);
			query.setParameter("ETC_ACCOUNT_ID", tripHistory.getEtcAccountId());
			queryCount=em.createNativeQuery(queryCountString);
			logger.info("Search Count query ::: {}",queryCount);
			queryCount.setParameter("ETC_ACCOUNT_ID", tripHistory.getEtcAccountId());
			
		}else if(tripHistory.getEtcAccountId()==null && tripHistory.getApdId()!=null && 
				tripHistory.getTripEndDate()==null && tripHistory.getTripStartDate()==null) {
			
			if(tripHistory.getStatus()!=null && tripHistory.getStatus().equals("ALL")) {
				
				queryString = queryString.concat(" WHERE APD_ID=:APD_ID").concat(contactOrderQuery);
				queryCountString = queryCountString.concat(" WHERE APD_ID=:APD_ID").concat(contactOrderQuery);
			}else {
			queryString = queryString.concat(" WHERE APD_ID=:APD_ID AND TRIP_END_DATE>=TRUNC(SYSDATE-1)").concat(contactOrderQuery);
			queryCountString = queryCountString.concat(" WHERE APD_ID=:APD_ID AND TRIP_END_DATE>=TRUNC(SYSDATE-1)").concat(contactOrderQuery);
			}
			query=em.createNativeQuery(queryString);
			logger.info("Search query ::: {}",queryString);
			query.setParameter("APD_ID", tripHistory.getApdId());
			queryCount=em.createNativeQuery(queryCountString);
			logger.info("Search Count query ::: {}",queryCount);
			queryCount.setParameter("APD_ID", tripHistory.getApdId());
			
		}else if(tripHistory.getEtcAccountId()==null && tripHistory.getApdId()==null && 
				tripHistory.getTripEndDate()!=null && tripHistory.getTripStartDate()==null) {
			
			if(tripHistory.getStatus()!=null && tripHistory.getStatus().equals("ALL")) {
			
			queryString = queryString.concat(" WHERE TRIP_END_DATE=:TRIP_END_DATE").concat(contactOrderQuery);
			queryCountString = queryCountString.concat(" WHERE TRIP_END_DATE=:TRIP_END_DATE").concat(contactOrderQuery);
			
			}else {
				
				queryString = queryString.concat(" WHERE TRIP_END_DATE=:TRIP_END_DATE AND TRIP_END_DATE>=TRUNC(SYSDATE-1)").concat(contactOrderQuery);
				queryCountString = queryCountString.concat(" WHERE TRIP_END_DATE=:TRIP_END_DATE AND TRIP_END_DATE>=TRUNC(SYSDATE-1)").concat(contactOrderQuery);
				
			}
			query=em.createNativeQuery(queryString);
			logger.info("Search query ::: {}",queryString);
			query.setParameter("TRIP_END_DATE", tripHistory.getTripEndDate());
			queryCount=em.createNativeQuery(queryCountString);
			logger.info("Search Count query ::: {}",queryCount);
			queryCount.setParameter("TRIP_END_DATE", tripHistory.getTripEndDate());
			
		}else if(tripHistory.getEtcAccountId()==null && tripHistory.getApdId()==null && 
				tripHistory.getTripEndDate()==null && tripHistory.getTripStartDate()!=null) {
			
			if(tripHistory.getStatus()!=null && tripHistory.getStatus().equals("ALL")) {
			queryString = queryString.concat(" WHERE TRIP_START_DATE=:TRIP_START_DATE").concat(contactOrderQuery);
			queryCountString = queryCountString.concat(" WHERE TRIP_START_DATE=:TRIP_START_DATE").concat(contactOrderQuery);
			
			}else {
				
				queryString = queryString.concat(" WHERE TRIP_START_DATE=:TRIP_START_DATE AND TRIP_END_DATE>=TRUNC(SYSDATE-1)").concat(contactOrderQuery);
				queryCountString = queryCountString.concat(" WHERE TRIP_START_DATE=:TRIP_START_DATE AND TRIP_END_DATE>=TRUNC(SYSDATE-1)").concat(contactOrderQuery);
			}
			query=em.createNativeQuery(queryString);
			logger.info("Search query ::: {}",queryString);
			query.setParameter("TRIP_START_DATE", tripHistory.getTripStartDate());
			queryCount=em.createNativeQuery(queryCountString);
			logger.info("Search Count query ::: {}",queryCount);
			queryCount.setParameter("TRIP_START_DATE", tripHistory.getTripStartDate());
			
		}else if(tripHistory.getEtcAccountId()!=null && tripHistory.getApdId()!=null && 
				tripHistory.getTripEndDate()==null && tripHistory.getTripStartDate()==null) {
			
			if(tripHistory.getStatus()!=null && tripHistory.getStatus().equals("ALL")) {
				
			queryString = queryString.concat(" WHERE ETC_ACCOUNT_ID=:ETC_ACCOUNT_ID and APD_ID=:APD_ID").concat(contactOrderQuery);
			queryCountString = queryCountString.concat(" WHERE ETC_ACCOUNT_ID=:ETC_ACCOUNT_ID and APD_ID=:APD_ID").concat(contactOrderQuery);
			
			}else {
				
				queryString = queryString.concat(" WHERE ETC_ACCOUNT_ID=:ETC_ACCOUNT_ID and APD_ID=:APD_ID AND TRIP_END_DATE>=TRUNC(SYSDATE-1)").concat(contactOrderQuery);
				queryCountString = queryCountString.concat(" WHERE ETC_ACCOUNT_ID=:ETC_ACCOUNT_ID and APD_ID=:APD_ID AND TRIP_END_DATE>=TRUNC(SYSDATE-1)").concat(contactOrderQuery);
			}
			query=em.createNativeQuery(queryString);
			logger.info("Search query ::: {}",queryString);
			query.setParameter("ETC_ACCOUNT_ID", tripHistory.getEtcAccountId());
			query.setParameter("APD_ID", tripHistory.getApdId());
			queryCount=em.createNativeQuery(queryCountString);
			logger.info("Search Count query ::: {}",queryCount);
			queryCount.setParameter("ETC_ACCOUNT_ID", tripHistory.getEtcAccountId());
			queryCount.setParameter("APD_ID", tripHistory.getApdId());
			
			
		}else if(tripHistory.getEtcAccountId()!=null && tripHistory.getApdId()==null && 
				tripHistory.getTripEndDate()!=null && tripHistory.getTripStartDate()==null) {
			
			if(tripHistory.getStatus()!=null && tripHistory.getStatus().equals("ALL")) {
			
			queryString = queryString.concat(" WHERE ETC_ACCOUNT_ID=:ETC_ACCOUNT_ID and TRIP_END_DATE=:TRIP_END_DATE").concat(contactOrderQuery);
			queryCountString = queryCountString.concat(" WHERE ETC_ACCOUNT_ID=:ETC_ACCOUNT_ID and TRIP_END_DATE=:TRIP_END_DATE").concat(contactOrderQuery);
			
			}else {
				
				queryString = queryString.concat(" WHERE ETC_ACCOUNT_ID=:ETC_ACCOUNT_ID and TRIP_END_DATE=:TRIP_END_DATE AND TRIP_END_DATE>=TRUNC(SYSDATE-1)").concat(contactOrderQuery);
				queryCountString = queryCountString.concat(" WHERE ETC_ACCOUNT_ID=:ETC_ACCOUNT_ID and TRIP_END_DATE=:TRIP_END_DATE AND TRIP_END_DATE>=TRUNC(SYSDATE-1)").concat(contactOrderQuery);	
			}
			query=em.createNativeQuery(queryString);
			logger.info("Search query ::: {}",queryString);
			query.setParameter("ETC_ACCOUNT_ID", tripHistory.getEtcAccountId());
			query.setParameter("TRIP_END_DATE", tripHistory.getTripEndDate());
			queryCount=em.createNativeQuery(queryCountString);
			logger.info("Search Count query ::: {}",queryCount);
			queryCount.setParameter("ETC_ACCOUNT_ID", tripHistory.getEtcAccountId());
			queryCount.setParameter("TRIP_END_DATE", tripHistory.getTripEndDate());
			
		}else if(tripHistory.getEtcAccountId()!=null && tripHistory.getApdId()==null && 
				tripHistory.getTripEndDate()==null && tripHistory.getTripStartDate()!=null) {
			
			if(tripHistory.getStatus()!=null && tripHistory.getStatus().equals("ALL")) {
			
			queryString = queryString.concat(" WHERE ETC_ACCOUNT_ID=:ETC_ACCOUNT_ID and TRIP_START_DATE=:TRIP_START_DATE").concat(contactOrderQuery);
			queryCountString = queryCountString.concat(" WHERE ETC_ACCOUNT_ID=:ETC_ACCOUNT_ID and TRIP_START_DATE=:TRIP_START_DATE").concat(contactOrderQuery);
			
			}else {
				
				queryString = queryString.concat(" WHERE ETC_ACCOUNT_ID=:ETC_ACCOUNT_ID and TRIP_START_DATE=:TRIP_START_DATE AND TRIP_END_DATE>=TRUNC(SYSDATE-1)").concat(contactOrderQuery);
				queryCountString = queryCountString.concat(" WHERE ETC_ACCOUNT_ID=:ETC_ACCOUNT_ID and TRIP_START_DATE=:TRIP_START_DATE AND TRIP_END_DATE>=TRUNC(SYSDATE-1)").concat(contactOrderQuery);
			}
			query=em.createNativeQuery(queryString);
			logger.info("Search query ::: {}",queryString);
			query.setParameter("ETC_ACCOUNT_ID", tripHistory.getEtcAccountId());
			query.setParameter("TRIP_START_DATE", tripHistory.getTripStartDate());
			queryCount=em.createNativeQuery(queryCountString);
			logger.info("Search Count query ::: {}",queryCount);
			queryCount.setParameter("ETC_ACCOUNT_ID", tripHistory.getEtcAccountId());
			queryCount.setParameter("TRIP_START_DATE", tripHistory.getTripStartDate());
			
		}else if(tripHistory.getEtcAccountId()==null && tripHistory.getApdId()==null && 
				tripHistory.getTripEndDate()!=null && tripHistory.getTripStartDate()!=null) {
			
			if(tripHistory.getStatus()!=null && tripHistory.getStatus().equals("ALL")) {
			queryString = queryString.concat(" WHERE TRIP_START_DATE>=:TRIP_START_DATE and TRIP_END_DATE<=:TRIP_END_DATE").concat(contactOrderQuery);
			queryCountString = queryCountString.concat(" WHERE TRIP_START_DATE>=:TRIP_START_DATE and TRIP_END_DATE<=:TRIP_END_DATE").concat(contactOrderQuery);
			
			}else {
				
				queryString = queryString.concat(" WHERE TRIP_START_DATE>=:TRIP_START_DATE and TRIP_END_DATE<=:TRIP_END_DATE AND TRIP_END_DATE>=TRUNC(SYSDATE-1)").concat(contactOrderQuery);
				queryCountString = queryCountString.concat(" WHERE TRIP_START_DATE>=:TRIP_START_DATE and TRIP_END_DATE<=:TRIP_END_DATE AND TRIP_END_DATE>=TRUNC(SYSDATE-1)").concat(contactOrderQuery);
			}
			query=em.createNativeQuery(queryString);
			logger.info("Search query ::: {}",queryString);
			query.setParameter("TRIP_START_DATE", tripHistory.getTripStartDate());
			query.setParameter("TRIP_END_DATE", tripHistory.getTripEndDate());
			queryCount=em.createNativeQuery(queryCountString);
			logger.info("Search Count query ::: {}",queryCount);
			queryCount.setParameter("TRIP_START_DATE", tripHistory.getTripStartDate());
			queryCount.setParameter("TRIP_END_DATE", tripHistory.getTripEndDate());
			
		}else if(tripHistory.getEtcAccountId()!=null && tripHistory.getApdId()==null && 
				tripHistory.getTripEndDate()!=null && tripHistory.getTripStartDate()!=null) {
			
			if(tripHistory.getStatus()!=null && tripHistory.getStatus().equals("ALL")) {
			
			queryString = queryString.concat(" WHERE TRIP_START_DATE>=:TRIP_START_DATE and TRIP_END_DATE<=:TRIP_END_DATE and ETC_ACCOUNT_ID=:ETC_ACCOUNT_ID").concat(contactOrderQuery);
			queryCountString = queryCountString.concat(" WHERE TRIP_START_DATE>=:TRIP_START_DATE and TRIP_END_DATE<=:TRIP_END_DATE and ETC_ACCOUNT_ID=:ETC_ACCOUNT_ID").concat(contactOrderQuery);
			
			}else {
				
				queryString = queryString.concat(" WHERE TRIP_START_DATE>=:TRIP_START_DATE and TRIP_END_DATE<=:TRIP_END_DATE and ETC_ACCOUNT_ID=:ETC_ACCOUNT_ID AND TRIP_END_DATE>=TRUNC(SYSDATE-1)").concat(contactOrderQuery);
				queryCountString = queryCountString.concat(" WHERE TRIP_START_DATE>=:TRIP_START_DATE and TRIP_END_DATE<=:TRIP_END_DATE and ETC_ACCOUNT_ID=:ETC_ACCOUNT_ID AND TRIP_END_DATE>=TRUNC(SYSDATE-1)").concat(contactOrderQuery);
			}
			query=em.createNativeQuery(queryString);
			logger.info("Search query ::: {}",queryString);
			query.setParameter("TRIP_START_DATE", tripHistory.getTripStartDate());
			query.setParameter("TRIP_END_DATE", tripHistory.getTripEndDate());
			query.setParameter("ETC_ACCOUNT_ID", tripHistory.getEtcAccountId());
			queryCount=em.createNativeQuery(queryCountString);
			logger.info("Search Account query ::: {}",queryCount);
			queryCount.setParameter("TRIP_START_DATE", tripHistory.getTripStartDate());
			queryCount.setParameter("TRIP_END_DATE", tripHistory.getTripEndDate());
			queryCount.setParameter("ETC_ACCOUNT_ID", tripHistory.getEtcAccountId());
			
//		}else if(tripHistory.getEtcAccountId()==null && tripHistory.getApdId()!=null && 
//				tripHistory.getTripEndDate()!=null && tripHistory.getTripStartDate()!=null) {
//			
//			queryString = queryString.concat(" WHERE TRIP_START_DATE>=:TRIP_START_DATE and TRIP_END_DATE<=:TRIP_END_DATE and APD_ID=:APD_ID");
//			queryCountString = queryCountString.concat(" WHERE TRIP_START_DATE>=:TRIP_START_DATE and TRIP_END_DATE<=:TRIP_END_DATE and APD_ID=:APD_ID");
//			query=em.createNativeQuery(queryString);
//			logger.info("Search query ::: {}",queryString);
//			query.setParameter("TRIP_START_DATE", tripHistory.getTripStartDate());
//			query.setParameter("TRIP_END_DATE", tripHistory.getTripEndDate());
//			query.setParameter("APD_ID", tripHistory.getApdId());
//			queryCount=em.createNativeQuery(queryCountString);
//			logger.info("Search Count query ::: {}",queryCount);
//			queryCount.setParameter("TRIP_START_DATE", tripHistory.getTripStartDate());
//			queryCount.setParameter("TRIP_END_DATE", tripHistory.getTripEndDate());
//			queryCount.setParameter("APD_ID", tripHistory.getApdId());
			
		}else if (tripHistory.getEtcAccountId()!=null && tripHistory.getApdId()!=null && 
				tripHistory.getTripEndDate()==null && tripHistory.getTripStartDate()!=null) {
			
			if(tripHistory.getStatus()!=null && tripHistory.getStatus().equals("ALL")) {
			queryString = queryString.concat(" WHERE TRIP_START_DATE=:TRIP_START_DATE and APD_ID=:APD_ID and ETC_ACCOUNT_ID=:ETC_ACCOUNT_ID").concat(contactOrderQuery);
			queryCountString = queryCountString.concat(" WHERE TRIP_START_DATE=:TRIP_START_DATE and APD_ID=:APD_ID and ETC_ACCOUNT_ID=:ETC_ACCOUNT_ID").concat(contactOrderQuery);
			
			}else {
				
				queryString = queryString.concat(" WHERE TRIP_START_DATE=:TRIP_START_DATE and APD_ID=:APD_ID and ETC_ACCOUNT_ID=:ETC_ACCOUNT_ID AND TRIP_END_DATE>=TRUNC(SYSDATE-1)").concat(contactOrderQuery);
				queryCountString = queryCountString.concat(" WHERE TRIP_START_DATE=:TRIP_START_DATE and APD_ID=:APD_ID and ETC_ACCOUNT_ID=:ETC_ACCOUNT_ID AND TRIP_END_DATE>=TRUNC(SYSDATE-1)").concat(contactOrderQuery);
			}
			query=em.createNativeQuery(queryString);
			logger.info("Search query ::: {}",queryString);
			query.setParameter("TRIP_START_DATE", tripHistory.getTripStartDate());
			query.setParameter("APD_ID", tripHistory.getApdId());
			query.setParameter("ETC_ACCOUNT_ID", tripHistory.getEtcAccountId());
			queryCount=em.createNativeQuery(queryCountString);
			logger.info("Search Count query ::: {}",queryCount);
			queryCount.setParameter("TRIP_START_DATE", tripHistory.getTripStartDate());
			queryCount.setParameter("APD_ID", tripHistory.getApdId());
			queryCount.setParameter("ETC_ACCOUNT_ID", tripHistory.getEtcAccountId());
			
		}else if (tripHistory.getEtcAccountId()!=null && tripHistory.getApdId()!=null && 
				tripHistory.getTripEndDate()!=null && tripHistory.getTripStartDate()==null) {
			
			if(tripHistory.getStatus()!=null && tripHistory.getStatus().equals("ALL")) {
			queryString = queryString.concat(" WHERE TRIP_END_DATE=:TRIP_END_DATE and APD_ID=:APD_ID and ETC_ACCOUNT_ID=:ETC_ACCOUNT_ID").concat(contactOrderQuery);
			queryCountString = queryCountString.concat(" WHERE TRIP_END_DATE=:TRIP_END_DATE and APD_ID=:APD_ID and ETC_ACCOUNT_ID=:ETC_ACCOUNT_ID").concat(contactOrderQuery);
			
			}else {
				
				queryString = queryString.concat(" WHERE TRIP_END_DATE=:TRIP_END_DATE and APD_ID=:APD_ID and ETC_ACCOUNT_ID=:ETC_ACCOUNT_ID AND TRIP_END_DATE>=TRUNC(SYSDATE-1)").concat(contactOrderQuery);
				queryCountString = queryCountString.concat(" WHERE TRIP_END_DATE=:TRIP_END_DATE and APD_ID=:APD_ID and ETC_ACCOUNT_ID=:ETC_ACCOUNT_ID AND TRIP_END_DATE>=TRUNC(SYSDATE-1)").concat(contactOrderQuery);
			}
			query=em.createNativeQuery(queryString);
			logger.info("Search query ::: {}",queryString);
			query.setParameter("TRIP_END_DATE", tripHistory.getTripEndDate());
			query.setParameter("APD_ID", tripHistory.getApdId());
			query.setParameter("ETC_ACCOUNT_ID", tripHistory.getEtcAccountId());
			queryCount=em.createNativeQuery(queryCountString);
			logger.info("Search Count query ::: {}",queryCount);
			queryCount.setParameter("TRIP_END_DATE", tripHistory.getTripEndDate());
			queryCount.setParameter("APD_ID", tripHistory.getApdId());
			queryCount.setParameter("ETC_ACCOUNT_ID", tripHistory.getEtcAccountId());
			
		}else if (tripHistory.getEtcAccountId()!=null && tripHistory.getApdId()!=null && 
				tripHistory.getTripEndDate()!=null && tripHistory.getTripStartDate()!=null) {
			
			if(tripHistory.getStatus()!=null && tripHistory.getStatus().equals("ALL")) {
			queryString = queryString.concat(" WHERE TRIP_START_DATE>=:TRIP_START_DATE and TRIP_END_DATE<=:TRIP_END_DATE and APD_ID=:APD_ID and ETC_ACCOUNT_ID=:ETC_ACCOUNT_ID").concat(contactOrderQuery);
			queryCountString = queryCountString.concat(" WHERE TRIP_START_DATE>=:TRIP_START_DATE and TRIP_END_DATE<=:TRIP_END_DATE and APD_ID=:APD_ID and ETC_ACCOUNT_ID=:ETC_ACCOUNT_ID").concat(contactOrderQuery);
			
			}else {
				
				queryString = queryString.concat(" WHERE TRIP_START_DATE>=:TRIP_START_DATE and TRIP_END_DATE<=:TRIP_END_DATE and APD_ID=:APD_ID and ETC_ACCOUNT_ID=:ETC_ACCOUNT_ID AND TRIP_END_DATE>=TRUNC(SYSDATE-1)").concat(contactOrderQuery);
				queryCountString = queryCountString.concat(" WHERE TRIP_START_DATE>=:TRIP_START_DATE and TRIP_END_DATE<=:TRIP_END_DATE and APD_ID=:APD_ID and ETC_ACCOUNT_ID=:ETC_ACCOUNT_ID AND TRIP_END_DATE>=TRUNC(SYSDATE-1)").concat(contactOrderQuery);
			}
			query=em.createNativeQuery(queryString);
			logger.info("Search query ::: {}",queryString);
			query.setParameter("TRIP_START_DATE", tripHistory.getTripStartDate());
			query.setParameter("TRIP_END_DATE", tripHistory.getTripEndDate());
			query.setParameter("APD_ID", tripHistory.getApdId());
			query.setParameter("ETC_ACCOUNT_ID", tripHistory.getEtcAccountId());
			queryCount=em.createNativeQuery(queryCountString);
			logger.info("Search Count query ::: {}",queryCount);
			queryCount.setParameter("TRIP_START_DATE", tripHistory.getTripStartDate());
			queryCount.setParameter("TRIP_END_DATE", tripHistory.getTripEndDate());
			queryCount.setParameter("APD_ID", tripHistory.getApdId());
			queryCount.setParameter("ETC_ACCOUNT_ID", tripHistory.getEtcAccountId());
			
		}else {
			
			if(tripHistory.getStatus()!=null && tripHistory.getStatus().equals("ALL")) {
			queryString = queryString.concat(" WHERE TRIP_START_DATE>=:TRIP_START_DATE and TRIP_END_DATE<=:TRIP_END_DATE and APD_ID=:APD_ID and ETC_ACCOUNT_ID=:ETC_ACCOUNT_ID").concat(contactOrderQuery);
			queryCountString = queryCountString.concat(" WHERE TRIP_START_DATE>=:TRIP_START_DATE and TRIP_END_DATE<=:TRIP_END_DATE and APD_ID=:APD_ID and ETC_ACCOUNT_ID=:ETC_ACCOUNT_ID").concat(contactOrderQuery);
			}else {
				queryString = queryString.concat(" WHERE TRIP_START_DATE>=:TRIP_START_DATE and TRIP_END_DATE<=:TRIP_END_DATE and APD_ID=:APD_ID and ETC_ACCOUNT_ID=:ETC_ACCOUNT_ID AND TRIP_END_DATE>=TRUNC(SYSDATE-1)").concat(contactOrderQuery);
				queryCountString = queryCountString.concat(" WHERE TRIP_START_DATE>=:TRIP_START_DATE and TRIP_END_DATE<=:TRIP_END_DATE and APD_ID=:APD_ID and ETC_ACCOUNT_ID=:ETC_ACCOUNT_ID AND TRIP_END_DATE>=TRUNC(SYSDATE-1)").concat(contactOrderQuery);
			}
			query=em.createNativeQuery(queryString);
			logger.info("Search query ::: {}",queryString);
			query.setParameter("TRIP_START_DATE", tripHistory.getTripStartDate());
			query.setParameter("TRIP_END_DATE", tripHistory.getTripEndDate());
			query.setParameter("APD_ID", tripHistory.getApdId());
			query.setParameter("ETC_ACCOUNT_ID", tripHistory.getEtcAccountId());
			queryCount=em.createNativeQuery(queryCountString);
			logger.info("Search Count query ::: {}",queryCount);
			queryCount.setParameter("TRIP_START_DATE", tripHistory.getTripStartDate());
			queryCount.setParameter("TRIP_END_DATE", tripHistory.getTripEndDate());
			queryCount.setParameter("APD_ID", tripHistory.getApdId());
			queryCount.setParameter("ETC_ACCOUNT_ID", tripHistory.getEtcAccountId());
		}
		
		query.setFirstResult(tripHistory.getPage()*tripHistory.getSize());
		query.setMaxResults(tripHistory.getSize());
		tripHistory.setRecordCount(((BigDecimal) queryCount.getSingleResult()).longValue());
//		Page<TTripHistory> page = new PageImpl<>(convertToDto(query.getResultList()), PageRequest.of(tripHistory.getPage(), tripHistory.getSize()), queryCount.getFirstResult());
//		return page;
		return convertToDto(query.getResultList());
	}
	
	
	public static Long convertToLong(Object o){
		if(o!=null) {
        String stringToConvert = String.valueOf(o);
        Long convertedLong = Long.parseLong(stringToConvert);
        return convertedLong;
		}
		return null;
    }
	
	public static LocalDate convertToLocalDate(Object o){
		if(o!=null) {
			 String stringToConvert = String.valueOf(o);
		    Timestamp timestamp = Timestamp.valueOf(stringToConvert);
			String dateFormated = new SimpleDateFormat("yyyy-MM-dd").format(timestamp);
			return LocalDate.parse(dateFormated);
		}
		return null;
    }
	
	public static Timestamp convertToTimestamp(Object o){
		if(o!=null) {
        String stringToConvert = String.valueOf(o);
        Timestamp timestamp = Timestamp.valueOf(stringToConvert);
        return timestamp;
		}
		return null;

    }
	
	public static String convertToDouble(Object o){
		if(o!=null) {
        String stringToConvert = String.valueOf(o);
        Double convertedLong = Double.valueOf(stringToConvert);   
        return df.format(convertedLong);
		}
		return null;

    }
	
//	public static Double convertToDouble(Object o){
//		if(o!=null) {
//        String stringToConvert = String.valueOf(o);
//        Double convertedLong = Double.valueOf(stringToConvert);
//        return convertedLong;
//		}
//		return null;
//
//    }
	
	private List<TTripHistory> convertToDto(List<Object[]> resultList) {
		List<TTripHistory> responseList=new ArrayList<>();
		for (Object[] object : resultList) {
			TTripHistory dto=new TTripHistory();
			dto.setApdId(convertToLong(object[0]));
			dto.setTripStartDate(convertToLocalDate(object[1]));
			dto.setTripEndDate(convertToLocalDate(object[2]));
			dto.setReconDate(convertToLocalDate(object[3]));
			dto.setTripsMade(convertToLong(object[4]));
			dto.setTripsCharged(convertToLong(object[5]));
			dto.setLateTrips(convertToLong(object[6]));
			dto.setAmountCharged(convertToDouble(object[7]));
			dto.setReconFlag(String.valueOf(object[8]));
			dto.setTripsLeft(convertToLong(object[9]));
			dto.setUpdateTs(convertToTimestamp(object[10]));
			dto.setLastTxDate(convertToLocalDate(object[11]));
			dto.setUsageAmount(convertToDouble(object[12]));
			dto.setFullFareAmount(convertToDouble(object[13]));
			dto.setDiscountedAmount(convertToDouble(object[14]));
			dto.setEtcAccountId(String.valueOf(object[15]));
			dto.setPlanType(convertToLong(object[16]));
			dto.setLastLaneTxId(convertToLong(object[17]));
			dto.setLastTxPostTimestamp(convertToTimestamp(object[18]));
			dto.setCompositeTransactionId(convertToLong(object[19]));
			dto.setCscLookupKey(String.valueOf(object[20]));
			
			responseList.add(dto);
		}
		return responseList;
	}


	@Override
	public long fetchTripHistoryCount(TTripHistory dto) {
		// TODO Auto-generated method stub
		return dto.getRecordCount();
	}

}
