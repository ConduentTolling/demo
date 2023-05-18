package com.conduent.transactionSearch.dao.impl;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.conduent.app.timezone.utility.TimeZoneConv;
import com.conduent.transactionSearch.constants.TransactionSearchConstants;
import com.conduent.transactionSearch.dao.AgencyDao;
import com.conduent.transactionSearch.dao.PlazaDao;
import com.conduent.transactionSearch.dao.TranDetailDao;
import com.conduent.transactionSearch.exception.TPMSGlobalException;
import com.conduent.transactionSearch.model.Agency;
import com.conduent.transactionSearch.model.Plaza;
import com.conduent.transactionSearch.model.TranDetailClass;
import com.conduent.transactionSearch.model.TranQueryReturn;
import com.conduent.transactionSearch.model.TranQueryReturnFromView;

import oracle.security.crypto.core.RSA;
@Repository
public class TranDetailDaoImpl implements TranDetailDao 
{

	@Autowired
	private NamedParameterJdbcTemplate namedJdbcTemplate;

	
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	TimeZoneConv timeZoneConv;

    @Autowired
    TransactionSearchConstants transactionSearchConstants;
    
    @Autowired
    PlazaDao plazaDao;

    @Autowired
    AgencyDao agencyDao;
    
    @Autowired
    TranDetailClass tranDetailClass;
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    String className = this.getClass().getName();	
	public List<TranDetailClass> getAll(String queryToCheckFile)
	{
		logger.info("Class : "+this.getClass()+"Method Name : getAll  Parameter Query : "+queryToCheckFile);
		try
		{
			List<TranDetailClass> tranDetailClassList = namedJdbcTemplate.query(queryToCheckFile, BeanPropertyRowMapper.newInstance(TranDetailClass.class));
			logger.info("Class : "+this.getClass()+"Method Name : getAll  Query successful : Number of Rows Returned : "+tranDetailClassList.size());
			
			return tranDetailClassList;
		}
		catch (EmptyResultDataAccessException e) 
		{
			logger.info("Class : "+this.getClass()+"Method Name : getAll  EXCEPTION OCCURED");
			return null;
		}
	}
	
	public List<TranQueryReturn> getAllRowsFromQuery(String queryToCheckFile,int page,int size)
	{
		String methodName = "getAllRowsFromQuery";
		List<Plaza> plazaList = plazaDao.getPlaza();
		List<Agency> agencyList = agencyDao.getAgency();
		String pagingClause = " OFFSET "+ page*size+" ROWS FETCH NEXT "+ size+" ROWS ONLY";
		logger.info("Class : "+this.getClass()+"Method Name : getAllRowsFromQuery  Parameter Query : "+queryToCheckFile);
		TranQueryReturn tranQueryReturn = new TranQueryReturn();
		List<TranQueryReturn> tranQueryReturnList = new ArrayList<TranQueryReturn>();
		String sql = "Select Count(*) FROM ("+queryToCheckFile+")";
		SqlParameterSource parameterSource=null;
		int count = 0;
		
		try
		{
	        long startingTime = System.currentTimeMillis();	
//	    	List<TranQueryReturn> tranQueryReturnList = namedJdbcTemplate.query(queryToCheckFile, new BeanPropertyRowMapper<TranQueryReturn>(TranQueryReturn.class));
	    	//	List<TranQueryReturn> tranQueryReturnList = namedJdbcTemplate.query(queryToCheckFile, BeanPropertyRowMapper.newInstance(TranQueryReturn.class));
	        queryToCheckFile = queryToCheckFile+pagingClause;
	    				 tranQueryReturnList = namedJdbcTemplate.query(queryToCheckFile, new RowMapper() {
	    				     public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
	    					        TranQueryReturn tranQueryReturn = new TranQueryReturn();
//	    					        for(int i=1;i<25;i++) {
//	    					        	ResultSetMetaData data = rs.getMetaData();
//	    					        	System.out.println("Col index: "+i+"="+data.getColumnName(i));
//	    					        }
	    					        //tranQueryReturn.setRowNumber(rs.getInt(1));
	    					        tranQueryReturn.setAccountNumber(rs.getString("ETC_ACCOUNT_ID"));
	    					        tranQueryReturn.setTransponderNumber(rs.getString("DEVICE_NO"));
	    					        tranQueryReturn.setTransactionDateTime(rs.getTimestamp("TX_TIMESTAMP"));
	    					        tranQueryReturn.setPostedDate(rs.getDate("POSTED_DATE"));
	    					        tranQueryReturn.setPlateNumber(rs.getString("PLATE_NUMBER"));
	    					        tranQueryReturn.setPlateState(rs.getString("PLATE_STATE"));
	    					        tranQueryReturn.setLaneTxID(rs.getString("LANE_TX_ID"));
	    					        tranQueryReturn.setExtReferenceNo(rs.getString("TX_EXTERN_REF_NO"));
	    					        tranQueryReturn.setPlazaId(rs.getString("ENTRY_PLAZA_ID"));
	    					        tranQueryReturn.setLaneId(rs.getString("LANE_ID"));
	    					        tranQueryReturn.setEntryTransactionDateTime(rs.getTimestamp("ENTRY_TX_TIMESTAMP"));
	    					        tranQueryReturn.setUpdateTs(rs.getTimestamp("UPDATE_TS"));
	    					        tranQueryReturn.setTxStatus(rs.getString("TX_STATUS"));
	    					        tranQueryReturn.setPostedFareAmount(rs.getDouble("POSTED_FARE_AMOUNT"));
	    					        tranQueryReturn.setExitPlazaDescription(plazaDao.getPlazaName(rs.getString("PLAZA_ID"),plazaList));
	    					        tranQueryReturn.setEntryPlazaDescription(plazaDao.getPlazaName(rs.getString("ENTRY_PLAZA_ID"),plazaList));
	    					        tranQueryReturn.setExitLane(rs.getString("LANE_ID"));
	    					        tranQueryReturn.setEntryLane(rs.getString("ENTRY_LANE_ID"));
	    					        tranQueryReturn.setAgencyStatementName(agencyDao.getStmtDescription(rs.getString("PLAZA_AGENCY_ID"),agencyList));
	    					        tranQueryReturn.setVehicleClass(rs.getString("ACTUAL_CLASS"));
	    					        tranQueryReturn.setDisputedFlag(rs.getBoolean("DST_FLAG"));
	    					        tranQueryReturn.setDisputedAmount(rs.getDouble("UNREALIZED_AMOUNT"));
	    					        tranQueryReturn.setPlanName(rs.getString("PLAN_TYPE"));
	    					        tranQueryReturn.setImageURL(rs.getString("IMAGE_URL"));
	    					        return tranQueryReturn;
	    					 }});
	    				 try {
	    						count = namedJdbcTemplate.queryForObject(sql,parameterSource,Integer.class);
	    					} catch (DataAccessException e1) {
	    						// TODO Auto-generated catch block
	    						e1.printStackTrace();
	    					}
	    					tranQueryReturnList.get(0).setTotalElements(count);
				long endingTime = System.currentTimeMillis();
				logger.info("Time taken to execute the query : "+(endingTime - startingTime)+" (in milliseconds)");
				if (tranQueryReturnList.isEmpty()) {
					TransactionSearchConstants.RESPONSE_ERROR_MESSAGE="No Rows Matching specified Criteria";
					throw new TPMSGlobalException(TransactionSearchConstants.RESPONSE_ERROR_MESSAGE,this.getClass().getName(),"getAllRowsFromQuery");
				}
			logger.info("Class : "+this.getClass()+"Method Name : getAllRowsFromQuery  Query successful : Number of Rows Returned : "+tranQueryReturnList.size()+" AccountNumber =>  "+tranQueryReturnList.get(0).getAccountNumber());
            return tranQueryReturnList;
		}
		catch (EmptyResultDataAccessException e) 
		{
			TransactionSearchConstants.RESPONSE_ERROR_MESSAGE="Error While retrieving the Query Results ";
			logger.info("Class : "+this.getClass()+"Method Name : getAllRowsFromQuery  EXCEPTION OCCURED");
			throw new TPMSGlobalException(TransactionSearchConstants.RESPONSE_ERROR_MESSAGE,className,methodName);
			//return null;
		}
	}

	
	//
	public List<TranQueryReturnFromView> getAllRowsFromQueryView(String queryToCheckFile,int page,int size)
	{
		String methodName = "getAllRowsFromQueryView";
//		List<Plaza> plazaList = plazaDao.getPlaza();
//		List<Agency> agencyList = agencyDao.getAgency();
		page = page-1;
		String pagingClause = " OFFSET "+ page*size+" ROWS FETCH NEXT "+ size+" ROWS ONLY";
		logger.info("Class : "+this.getClass()+"Method Name : getAllRowsFromQueryFromView  Parameter Query : "+queryToCheckFile);
		TranQueryReturnFromView tranQueryReturnFromView = new TranQueryReturnFromView();
		List<TranQueryReturnFromView> tranQueryReturnList = new ArrayList<TranQueryReturnFromView>();
		String sql = "Select Count(*) FROM ("+queryToCheckFile+")";
		SqlParameterSource parameterSource=null;
		int count = 0;
		
		try
		{
	        long startingTime = System.currentTimeMillis();	
//	    	List<TranQueryReturn> tranQueryReturnList = namedJdbcTemplate.query(queryToCheckFile, new BeanPropertyRowMapper<TranQueryReturn>(TranQueryReturn.class));
	    	//	List<TranQueryReturn> tranQueryReturnList = namedJdbcTemplate.query(queryToCheckFile, BeanPropertyRowMapper.newInstance(TranQueryReturn.class));
	        queryToCheckFile = queryToCheckFile+pagingClause;
	        logger.info("Class : "+this.getClass()+"Method Name : getAllRowsFromQueryFromView  Final Query : "+queryToCheckFile);
			    
	    				 tranQueryReturnList = namedJdbcTemplate.query(queryToCheckFile, new RowMapper() {
	    				     public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
	    				    	    TranQueryReturnFromView tranQueryReturnFromView = new TranQueryReturnFromView();
//	    					        for(int i=1;i<25;i++) {
//	    					        	ResultSetMetaData data = rs.getMetaData();
//	    					        	System.out.println("Col index: "+i+"="+data.getColumnName(i));
//	    					        }
	    					        //tranQueryReturn.setRowNumber(rs.getInt(1));
	    					        tranQueryReturnFromView.setAccountNo(rs.getString("ACCOUNT_NO"));
	    					        tranQueryReturnFromView.setDeviceNo(rs.getString("DEVICE_NO"));
	    					        tranQueryReturnFromView.setTxTimestamp(rs.getTimestamp("TX_TIMESTAMP"));
	    					        tranQueryReturnFromView.setPostedDate(rs.getDate("POSTED_DATE"));
	    					        tranQueryReturnFromView.setPlateNumber(rs.getString("PLATE_NUMBER"));
	    					        tranQueryReturnFromView.setPlateState(rs.getString("PLATE_STATE"));
	    					        tranQueryReturnFromView.setLaneTxId(rs.getString("LANE_TX_ID"));
	    					        tranQueryReturnFromView.setTxExternRefNo(rs.getString("TX_EXTERN_REF_NO"));
	    					        tranQueryReturnFromView.setEntryPlazaId(rs.getString("ENTRY_PLAZA_ID"));
	    					        tranQueryReturnFromView.setExitPlazaId(rs.getString("EXIT_PLAZA_ID"));
	    					        tranQueryReturnFromView.setEntryTxTimestamp(rs.getTimestamp("ENTRY_TX_TIMESTAMP"));
	    					        tranQueryReturnFromView.setUpdateTs(rs.getTimestamp("UPDATE_TS"));
	    					        tranQueryReturnFromView.setTxStatus(rs.getString("TX_STATUS"));
	    					        tranQueryReturnFromView.setDiscountedFare(rs.getDouble("DISCOUNTED_FARE"));
	    					        tranQueryReturnFromView.setEntryPlazaDescription(rs.getString("ENTRY_PLAZA_DESCRIPTION"));
	    					        tranQueryReturnFromView.setExitPlazaDescription(rs.getString("EXIT_PLAZA_DESCRIPTION"));
	    					        tranQueryReturnFromView.setEntryExternLaneId(rs.getString("ENTRY_EXTERN_LANE_ID"));
	    					        tranQueryReturnFromView.setExitExternLaneId(rs.getString("EXIT_EXTERN_LANE_ID"));
	    					        tranQueryReturnFromView.setAgencyName(rs.getString("AGENCY_NAME"));
	    					        tranQueryReturnFromView.setVehicleClass(rs.getString("VEHICLE_CLASS"));
	    					        tranQueryReturnFromView.setDisputedFlag(rs.getBoolean("DISPUTED_FLAG"));
	    					        tranQueryReturnFromView.setDisputedAmount(rs.getDouble("DISPUTED_AMOUNT"));
	    					        tranQueryReturnFromView.setPlanName(rs.getString("PLAN_NAME"));
	    					        tranQueryReturnFromView.setImageUrl(rs.getString("IMAGE_URL"));
	    					        tranQueryReturnFromView.setInvoiceNumber(rs.getString("INVOICE_NUMBER"));
	    					        tranQueryReturnFromView.setTxDate(rs.getDate("TX_DATE"));
	    					        tranQueryReturnFromView.setEscalationLevel(rs.getString("ESCALATION_LEVEL"));
	    					        tranQueryReturnFromView.setCategory(rs.getString("CATEGORY"));
	    					        tranQueryReturnFromView.setEntryExternPlazaId(rs.getString("ENTRY_EXTERN_PLAZA_ID"));
	    					        tranQueryReturnFromView.setExitExternPlazaId(rs.getString("EXIT_EXTERN_PLAZA_ID"));
	    					        tranQueryReturnFromView.setEntryLaneId(rs.getString("ENTRY_LANE_ID"));
	    					        tranQueryReturnFromView.setExitLaneId(rs.getString("EXIT_LANE_ID"));
	    					        tranQueryReturnFromView.setEtcAccountId(rs.getString("ETC_ACCOUNT_ID"));
	    					        tranQueryReturnFromView.setCategory(rs.getString("CATEGORY"));
	    					 
	    					        tranQueryReturnFromView.setActualAxles(rs.getString("ACTUAL_AXLES"));
	    					        tranQueryReturnFromView.setDescription(rs.getString("DESCRIPTION"));
	    					        tranQueryReturnFromView.setTransactionRowId(rs.getString("TRANSACTION_ROW_ID"));
	    					        tranQueryReturnFromView.setPostTransactionBalance(rs.getDouble("POST_POSTPAID_BALANCE"));
	    					        tranQueryReturnFromView.setTranType(rs.getString("TRAN_CODE"));
	    					      
	    					        return tranQueryReturnFromView;
	    					 }});
	    				 try {
	    						count = namedJdbcTemplate.queryForObject(sql,parameterSource,Integer.class);
	    					} catch (DataAccessException e1) {
	    						// TODO Auto-generated catch block
	    						e1.printStackTrace();
	    					}
	    				 if(!tranQueryReturnList.isEmpty()) {
	    					tranQueryReturnList.get(0).setTotalElements(count);
	    				 }
				long endingTime = System.currentTimeMillis();
				logger.info("Time taken to execute the query : "+(endingTime - startingTime)+" (in milliseconds) "+ " Total Rows Returned : "+count);
				if (tranQueryReturnList.isEmpty()) {
					TransactionSearchConstants.RESPONSE_ERROR_MESSAGE="No Rows Matching specified Criteria";
					throw new TPMSGlobalException(TransactionSearchConstants.RESPONSE_ERROR_MESSAGE,this.getClass().getName(),"getAllRowsFromQuery");
				}
			logger.info("Class : "+this.getClass()+"Method Name : getAllRowsFromQueryFromView  Query successful : Number of Rows Returned : "+tranQueryReturnList.size());
            return tranQueryReturnList;
		}
		catch (EmptyResultDataAccessException e) 
		{
			TransactionSearchConstants.RESPONSE_ERROR_MESSAGE="Error While retrieving the Query Results ";
			logger.info("Class : "+this.getClass()+"Method Name : getAllRowsFromQuery  EXCEPTION OCCURED");
			throw new TPMSGlobalException(TransactionSearchConstants.RESPONSE_ERROR_MESSAGE,className,methodName);
			//return null;
		}
	}

	
	//
}
