package com.conduent.tpms.NY12.dao.impl;

import java.sql.ResultSet;

import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

//import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

//import com.conduent.tpms.NY12.Bean;
import com.conduent.tpms.NY12.config.LoadJpaApplicationContext;
import com.conduent.tpms.NY12.config.LoadJpaQueries;
import com.conduent.tpms.NY12.config.MyConfigProperties;
import com.conduent.tpms.NY12.constants.NY12_Constants;
import com.conduent.tpms.NY12.dao.NY12_ProcessDao;
import com.conduent.tpms.NY12.dto.NY12_ProcessDto;
//import com.conduent.tpms.NY12.model.Device;
import com.conduent.tpms.NY12.model.Plaza;
import com.conduent.tpms.NY12.model.ProcessParameter;
//import com.conduent.tpms.NY12.service.impl.$missing$;
import com.conduent.tpms.NY12.vo.CompleteTransactionVO;
import com.conduent.tpms.NY12.vo.DeviceTransactionVO;
import com.conduent.tpms.NY12.vo.DistinctEntryExitPlazaVO;
import com.conduent.tpms.NY12.vo.DistinctLaneTxPlazaVO;
import com.conduent.tpms.NY12.vo.HighwaySectionVO;
import com.conduent.tpms.NY12.vo.LaneTxIdVO;
import com.conduent.tpms.NY12.vo.LastSectionTransactionVO;
import com.conduent.tpms.NY12.vo.MatchedTransactionsVO;
import com.conduent.tpms.NY12.vo.MessagesVO;
import com.conduent.tpms.NY12.vo.Ny12ProcessFinalResponseVO;
import com.conduent.tpms.NY12.vo.Ny12ProcessResponseVO;
import com.conduent.tpms.NY12.vo.PendingQueuePerLaneTxIdVO;
import com.conduent.tpms.NY12.vo.SameSegmentTransactionVO;
import com.conduent.tpms.NY12.vo.TransactionVO;
import com.conduent.tpms.NY12.vo.VehicleClassNotEqualTo22TransactionVO;

import oracle.jdbc.datasource.OracleDataSource;

@Component(value="nY12_ProcessDao")
public class NY12_ProcessDaoImpl implements NY12_ProcessDao {
	private static Logger logger = LoggerFactory.getLogger(NY12_ProcessDaoImpl.class);
	private String currentStepName ="FIRST STEP";
	@Autowired
	Environment env;
	
	/*@ Autowired
	@Qualifier("applicationContext")
	private ApplicationContext applicationContext;
	*/

	@Autowired
	@Qualifier("myConfigProperties")
	MyConfigProperties myConfigProperties;    // = new MyConfigProperties();
	
	@Autowired
	private NamedParameterJdbcTemplate namedJdbcTemplate;

	@Autowired
	private JdbcTemplate jdbcTemplate;
    //JdbcTemplate jdbcTemplate = new JdbcTemplate();

    @Value("{spring.datasource.username}")
	private String username;

    @Value("{username}")
	private String username2;

    @Value("{spring.datasource.password}")
	private String password;

    @Value("{spring.datasource.url}")
	private String url;

    @Value("{spring.datasource.driver-class-name}")
	private String driverClassName;

	@Autowired
	DataSource dataSource ;
	
	@Bean(name="loadJpaApplicationContext")
	public ApplicationContext loadJpaApplicationContext() { 
			return new LoadJpaApplicationContext().getApplicationContext(); 
	}
    
	
	private int totalNumberOfErroredRecords=0;
	private int totalNumberOfRecordsProcessed=0;
	private int totalNumberOfRecordsToBeProcessed=0;
	
	
	
	private NY12_ProcessDaoImpl ny12_ProcessDaoImpl;
	public NY12_ProcessDaoImpl() {
	//	loadJpaApplicationContext();
	}
	
   
	/*ny12processresponsevo has message , used to set message, since that is commented now, this variable of no use*/
	private Ny12ProcessResponseVO ny12ProcessResponseVO = new Ny12ProcessResponseVO();
	
	/*list has ny12processresponsevo objects, since that is commented now , this variable of no use*/
	private List<Ny12ProcessResponseVO> ny12ProcessResponseVO_List = new ArrayList<>();
	/*
	 * public List<Ny12ProcessResponseVO> getNy12ProcessResponseVOList(){ return
	 * ny12ProcessResponseVO_List; }
	 */
	
	MessagesVO messagesVO = new MessagesVO();
	private List<MessagesVO> messagesVO_List = new ArrayList<>();
	/*
	 * public List<MessagesVO> getListOfMessagesVO() 
	 * { 
	 * return messagesVO_List; 
	 * }
	 */
	
	
	

	/*used, in not used method stithcing*/
	//protected List<NY12_ProcessDto> listOfRecords=null;
	
	//private String currentLaneTxId;
	

	public Ny12ProcessResponseVO getNy12ProcessResponseVO() {
		return ny12ProcessResponseVO;
	}
	public void setNy12ProcessResponseVO(Ny12ProcessResponseVO ny12ProcessResponseVO) {
		this.ny12ProcessResponseVO = ny12ProcessResponseVO;
	}
	public List<Ny12ProcessResponseVO> getNy12ProcessResponseVO_List() {
		return ny12ProcessResponseVO_List;
	}
	public void setNy12ProcessResponseVO_List(List<Ny12ProcessResponseVO> ny12ProcessResponseVO_List) {
		this.ny12ProcessResponseVO_List = ny12ProcessResponseVO_List;
	}
	public List<MessagesVO> getMessagesVO_List() {
		return messagesVO_List;
	}
	public void setMessagesVO_List(List<MessagesVO> messagesVO_List) {
		this.messagesVO_List = messagesVO_List;
	}
	public MessagesVO getMessagesVO() {
		return messagesVO;
	}
	public void setMessagesVO(MessagesVO messagesVO) {
		this.messagesVO = messagesVO;
	}
	public int getTotalNumberOfErroredRecords() {
		return totalNumberOfErroredRecords;
	}
	public void setTotalNumberOfErroredRecords(int totalNumberOfErroredRecords) {
		this.totalNumberOfErroredRecords = totalNumberOfErroredRecords;
	}
	public int getTotalNumberOfRecordsProcessed() {
		return totalNumberOfRecordsProcessed;
	}
	public void setTotalNumberOfRecordsProcessed(int totalNumberOfRecordsProcessed) {
		this.totalNumberOfRecordsProcessed = totalNumberOfRecordsProcessed;
	}
	public int getTotalNumberOfRecordsToBeProcessed() {
		return totalNumberOfRecordsToBeProcessed;
	}
	public void setTotalNumberOfRecordsToBeProcessed(int totalNumberOfRecordsToBeProcessed) {
		this.totalNumberOfRecordsToBeProcessed = totalNumberOfRecordsToBeProcessed;
	}
	
	public NY12_ProcessDaoImpl getNY12_ProcessDaoImpl() {
		return this;
	}
	public void setNY12_ProcessDaoImpl(NY12_ProcessDaoImpl ny12_ProcessDaoImpl) {
		this.ny12_ProcessDaoImpl = ny12_ProcessDaoImpl;
	}
	

	/*message passed here is set in messageVo , for now all setmessage are commented*/
	public void setMessage(String message) {
		messagesVO.setMessage(message);
		messagesVO_List.add(messagesVO);
	}
	
	/*message of ny12processresponsevo is set here, for now this is commented*/
	public void setProcessingSetpMessage(List<MessagesVO> messagesVO_List) {
		ny12ProcessResponseVO.setProcessingStepMessages(messagesVO_List);
		return;
	} 
	
	
	
	 
	/*called in serviceimpl start method, has been commented*/
	public void initializeMessagesVO_Objects() 
	{
		if (ny12ProcessResponseVO_List!= null) 
		setNy12ProcessResponseVO_List(ny12ProcessResponseVO_List);
		ny12ProcessResponseVO_List = new ArrayList<>();
		messagesVO_List = new ArrayList<>();

	}
	
	
	/*
	 * public void setLaneTxId(String currentLaneTxId) { this.currentLaneTxId
	 * =currentLaneTxId; }
	 */
	/*
	 * public String getCurrentLaneTxId() {
	 * 
	 * return this.currentLaneTxId; }
	 */
		
	@Override
	public int getNextSequenceNumber() {
		/*if (jdbcTemplate == null) {
			logger.info("jdbcTemplate Is null");
		}
		 */
		int sequenceNumber =0;
		logger.info("Class Name :"+this.getClass()+" Method Name : getNextSequenceNumber - STARTED");
		//this.setMessage("STEP 1");
		//this.setMessage("Step  ... Getting Next Sequence Number");
		String queryToCheckFile = LoadJpaQueries.getQueryById("GET_NEXT_SEQUENCE_NUMBER");
		
		MapSqlParameterSource paramSource = new MapSqlParameterSource();

		List<Map<String,Object>> resultMap = jdbcTemplate.queryForList(queryToCheckFile);
		
		if (resultMap != null) 
		{
			Iterator itr =  resultMap.iterator();
			while (itr.hasNext()) 
			{
				Map map = (Map) itr.next();
				sequenceNumber = Integer.parseInt(map.get("SEQUENCE_NUMBER").toString());
				logger.info("sequence Number from query is : "+sequenceNumber);
				break;
			}
		}

		logger.info("Class Name :"+this.getClass()+" Method Name : getNextSequenceNumber - ENDED");
		//totalNumberOfRecordsProcessed++;  
		return sequenceNumber;    //currentSequenceNumber;
	}
	
	
	
	//************************STEP 1******************************** (generate seqnum and updateseqn)
	public boolean completeReferenceNumberAndUpdate(List<LaneTxIdVO> distinctLaneTxIds) {
		//logger.info("Class Name :"+this.getClass()+" Method Name : completeReferenceNumberAndUpdate - STARTED");
		// executing Query 2
		//this.setMessage("Step  ... Get Distinct Lane Tx Id");
		
		if(distinctLaneTxIds!=null) 
		{
			logger.info("Class Name : "+this.getClass()+"Method Name :completeReferenceNumberAndUpdate  STEP 1 : Total Number of LaneTxId to be processed is "+ distinctLaneTxIds.size());

			
			try {
				int sequenceNumber = 0;
				String queryForNY12_Condition = "";
				String distinctLaneTxId = null;	
				MapSqlParameterSource paramSource = new MapSqlParameterSource();
				LaneTxIdVO laneTxIdVO = new LaneTxIdVO();
				// one by one laneTxId is taken and update the sequence number for same laneTxId. This is done for all laneTxIds in the pending queue.
				if (distinctLaneTxIds != null) {
					Iterator<LaneTxIdVO> itr =  distinctLaneTxIds.iterator();
					List<TransactionVO> transactionVO_List;
					int updateCount = 0;
					while (itr.hasNext()) {

						laneTxIdVO = itr.next();
						distinctLaneTxId=laneTxIdVO.getDistinctLaneTransactionId();
						//logger.info("distinctLaneTxId : "+distinctLaneTxId);
						//this.currentLaneTxId= distinctLaneTxId;
						if (distinctLaneTxId == null) break;
						// getting next sequence number for this lane tx Id
						sequenceNumber=getNextSequenceNumber();
						logger.info("distinctLaneTxId"+distinctLaneTxId+"sequence Number from query is : "+sequenceNumber);

						transactionVO_List = new ArrayList();
						//this.setMessage("STEP 2");
						//this.setMessage("Step  ... Updating Tx Complete Reference Number in Pending Queue for LaneTxId : "+distinctLaneTxId);

						queryForNY12_Condition = LoadJpaQueries.getQueryById("UPDATE_TX_COMPLETE_REF_NUMBER");
						paramSource.addValue("lv_complete_ref_no", sequenceNumber);
						paramSource.addValue("lv_lane_tx_id",distinctLaneTxId);

						try 
						{
							updateCount = namedJdbcTemplate.update(queryForNY12_Condition, paramSource);
							logger.info("update count for completeReferenceNumberAndUpdate : "+updateCount);
						} catch (EmptyResultDataAccessException empty) 
						{
							logger.info("no data in Pending Queue table -completeReferenceNumberAndUpdate");
							continue;
						}
						//ny12ProcessResponseVO.setProcessingStepMessages(getMessagesVO_List());
					}
					//ny12ProcessResponseVO_List.add(ny12ProcessResponseVO);
					//setNy12ProcessResponseVO_List(ny12ProcessResponseVO_List);
				}
			} catch (Exception e) {
				logger.error("Error in step 1 completeReferenceNumberAndUpdate", e);
				return false;
			}
			
			
		}
		else {
			logger.info("distinctLaneTxids are null");
			return false;
		     }
		
		
		
		logger.info("Class Name :"+this.getClass()+" Method Name : completeReferenceNumberAndUpdate - ENDED");
		return true;	

	}
	
	
	public List<MatchedTransactionsVO> getAllMatchedTransactions(String distinctLaneTxId) {
		logger.info("Class Name :"+this.getClass()+" Method Name : getAllMatchedTransactions - STARTED"+distinctLaneTxId+"to be processed");
		//logger.info("Parameter passed to this method distinctLaneTxId "+distinctLaneTxId);
		List<MatchedTransactionsVO> matchedTransactionsVO_List = new ArrayList<MatchedTransactionsVO>();
		
		
		try 
		{
			if (distinctLaneTxId!=null) 
			{
				MapSqlParameterSource paramSource = new MapSqlParameterSource();
				//this.setMessage("Step  ... Get All Matched Transactions of Lane Tx Id : "+distinctLaneTxId);

				String queryForNY12_Condition = LoadJpaQueries.getQueryById("GET_ALL_MATCHED_TRANSACTIONS");
				paramSource.addValue("lv_lane_tx_id", distinctLaneTxId);

				try {
					matchedTransactionsVO_List = namedJdbcTemplate.query(queryForNY12_Condition, paramSource,
							BeanPropertyRowMapper.newInstance(MatchedTransactionsVO.class));
					if (matchedTransactionsVO_List != null)
					{
						logger.info("Total number of Matched Transactions : "+matchedTransactionsVO_List.size());
					} else {
						logger.error("Error While Getting Matched Transactions (Query Q3)");
					}
				} catch (EmptyResultDataAccessException empty) {
					logger.error("Error while fetching matchedtxn data",empty);
                    
				}
			}
		    else {
				logger.info("distinctLaneTxId is null for method getAllMatchedTransaction");
				return matchedTransactionsVO_List;
			     }
			
		}  catch (Exception e) 
		{
			logger.error("Error in method getAllMatchedTransactions ", e);
			
		}
		
		logger.info("Class Name :"+this.getClass()+" Method Name :  getAllMatchedTransactions - ENDED");

		return matchedTransactionsVO_List;	

	}
	
	
	public boolean updateMatchedTxExternalReferenceNumber(List<MatchedTransactionsVO> matchedTransactionsVOList,String laneTxId) 
	{
		logger.info("Class Name :"+this.getClass()+" Method Name : updateMatchedTxExternalReferenceNumber - STARTED");
		
		//if (matchedTransactionsVOList == null) {
			//logger.info("matchedTransactionsVOList is null in updateMatchedTxExternalReferenceNumber");	
			//return false;
		//}
		
		try 
		{
			int updateCount =0;
			MapSqlParameterSource paramSource = new MapSqlParameterSource();
			Iterator matchedTransactionsItr =  matchedTransactionsVOList.iterator();
			MatchedTransactionsVO matchedTransactionsVO = new MatchedTransactionsVO();
			while (matchedTransactionsItr.hasNext()) 
			{
				matchedTransactionsVO = (MatchedTransactionsVO) matchedTransactionsItr.next();
				paramSource = new MapSqlParameterSource();
				paramSource.addValue("lv_lane_tx_id", matchedTransactionsVO.getLaneTxId());
				paramSource.addValue("lv_match_ref_no", matchedTransactionsVO.getMatchRefNo());
				//this.setMessage("Step  ... Update Matched_Tx External Reference Number in Pending Queue Table");

				String queryForNY12_Condition = LoadJpaQueries.getQueryById("UPDATE_MATCHED_TX_EXTERNAL_REF_NUMBER");

				try 
				{
					updateCount = namedJdbcTemplate.update(queryForNY12_Condition, paramSource);
					logger.info("update count for updateextrefnumber"+updateCount);
				} catch (EmptyResultDataAccessException empty) 
				{
					//logger.info("Error while updatingMatchedRefNum -updateMatchedRxExternalReferenceNumber");
					logger.error("Errror while updatingMatchedRefNum", empty);
					return false;
				}
			}
		} catch (Exception e) 
		{
			logger.error("Error in updateMatchedTxExternalReferenceNumber ", e);
			return false;
		}
		
		logger.info("Class Name :"+this.getClass()+" Method Name : updateMatchedTxExternalReferenceNumber - ENDED");

		return true;	
	}
	
	
	public boolean insertCompleteTransactions(String laneTxId)
	{
		logger.info("Class Name :"+this.getClass()+" Method Name : insertCompleteTransactions - STARTED");
		//logger.info("Parameter passed to this method LaneTxId "+laneTxId);
		if (laneTxId == null) {
			logger.info("LaneTxId passed tp insertCompleteTransactions is null");
			return false;
		}
		try {
			//List<Object[]> objectList = new ArrayList<Object[]>();
			int insertCount = 0;
			//MapSqlParameterSource paramSource = new MapSqlParameterSource();
			//paramSource.addValue("lv_lane_tx_id", laneTxId);
			//this.setMessage("Step  ... Insert Complete Transactions");

			String queryForNY12_Condition = LoadJpaQueries.getQueryById("INSERT_COMPLETE_TXN");

			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("lv_lane_tx_id", laneTxId);
			
			try 
			{
				insertCount = namedJdbcTemplate.update(queryForNY12_Condition, paramMap);
			} catch (EmptyResultDataAccessException empty) 
			{
				//logger.info("No data to process in Pending Queue table -insertCompleteTransactions");
				logger.error("Error while insertinginCompleteTrxn", empty);
				return false;
			}
		} catch (Exception e) 
		{
			logger.error("Error in method insertCompleteTransactions", e);
			return false;
		}
		logger.info("Class Name :"+this.getClass()+" Method Name : insertCompleteTransactions - ENDED");

		return true;	
	}
	
	
	public boolean updateBalancesAndPlazaInformation(String laneTxId)
	{
		logger.info("Class Name :"+this.getClass()+" Method Name : updateBalancesAndPlazaInformation  - STARTED");
		//logger.info("Parameter passed to this method LaneTxId "+laneTxId);
		if (laneTxId == null) {
			logger.info("LaneTxId passed to updateBalancesAndPlazaInformation is null");
			return false;
		}

		List<PendingQueuePerLaneTxIdVO> objectList = new ArrayList<>();
		PendingQueuePerLaneTxIdVO pendingQueuePerLaneTxIdVO = new PendingQueuePerLaneTxIdVO();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		MapSqlParameterSource paramSource = new MapSqlParameterSource();

		try {
			paramSource.addValue("lv_lane_tx_id", laneTxId);
			int updateCount = 0;
			//this.setMessage("Step  ...Get All Pending Queue Transactions of Lane Tx id "+laneTxId);

			String queryForNY12_Condition = LoadJpaQueries.getQueryById("GET_ALL_PENDING_QUEUE_TXN_PER_LANE_TX_ID");
			try 
			{
				objectList = namedJdbcTemplate.query(queryForNY12_Condition, paramSource,
						BeanPropertyRowMapper.newInstance(PendingQueuePerLaneTxIdVO.class));
			} catch (EmptyResultDataAccessException empty) 
			{
				logger.info("No data for that lanetxid in pendingqueue  -updateBalancesAndPlazaInformation");
				return false;
			}
			if (objectList == null) {
				logger.info("No data inside pending queue for lanetxid "+laneTxId);
				return false;
			}
			
			Iterator<PendingQueuePerLaneTxIdVO> pendingQueuePerLaneTxIdVOItr = objectList.iterator();

			while (pendingQueuePerLaneTxIdVOItr.hasNext()) {

				pendingQueuePerLaneTxIdVO = (PendingQueuePerLaneTxIdVO) pendingQueuePerLaneTxIdVOItr.next();
				paramMap = new HashMap<String, Object>();
				paramMap.put("lv_entry_plaza", pendingQueuePerLaneTxIdVO.getEntryPlazaId());
				paramMap.put("lv_exit_plaza", pendingQueuePerLaneTxIdVO.getPlazaId());
				paramMap.put("lv_entry_lane_id", pendingQueuePerLaneTxIdVO.getEntryLaneId());
				paramMap.put("lv_entry_timestmp", pendingQueuePerLaneTxIdVO.getEntryTimeStamp()!=null?pendingQueuePerLaneTxIdVO.getEntryTimeStamp():null);
				paramMap.put("lv_discounted_amount", String.valueOf(pendingQueuePerLaneTxIdVO.getDiscountedAmount()));
				paramMap.put("lv_collected_amount", String.valueOf(pendingQueuePerLaneTxIdVO.getCollectedAmount()));
				paramMap.put("lv_unrealized_amount", String.valueOf(pendingQueuePerLaneTxIdVO.getUnrealizedAmount()));
				paramMap.put("lv_lane_tx_id", pendingQueuePerLaneTxIdVO.getLaneTxId());

				//this.setMessage("Step  ...Update Complete Transactions");

				queryForNY12_Condition = LoadJpaQueries.getQueryById("UPDATE_COMPLETE_TXN");
				
				updateCount = namedJdbcTemplate.update(queryForNY12_Condition, paramMap);
				if(updateCount != 0)
				{
					logger.info("Successfully updated rows in complete Transaction Table " +updateCount+" for Lane Tx Id : "+laneTxId);
				}else
				{
					logger.info("No rows updated -updateBalancesAndPlazaInformation");
					return false;
				}
			}
		} catch (Exception e) {
			logger.error("Error in updateBalancesAndPlazaInformation", e);
			return false;
		}
		logger.info("Class Name :"+this.getClass()+" Method Name : updateBalancesAndPlazaInformation  - ENDED");

		return true;	
	}
	
	
	public boolean deleteTransactionsFromPendingQueue(String laneTxId)
	{
		logger.info("Class Name :"+this.getClass()+" Method Name : deleteTransactionsFromPendingQueue - STARTED");
		//logger.info("Parameter for laneTxId :"+laneTxId);
		if (laneTxId == null) {
			logger.info("LaneTxId passed tp deleteTransactionsFromPendingQueue is null");
			return false;
		}
		try {

			//MapSqlParameterSource paramSource = new MapSqlParameterSource();
			//paramSource.addValue("lv_lane_tx_id", laneTxId);
			//this.setMessage("Step  ... Delete Pending Queue Transactions of Lane Tx Id "+laneTxId);

			String queryForNY12_Condition = LoadJpaQueries.getQueryById("DELETE_PENDING_QUEUE");


			SqlParameterSource namedParameters = new MapSqlParameterSource("lv_lane_tx_id", laneTxId);
			int status = namedJdbcTemplate.update(queryForNY12_Condition, namedParameters);
			if(status != 0){
				logger.info("Successfully deleted rows in Pending Queue table for lane_tx_id "+laneTxId+"number of row deleted"+status );
			}else{
				logger.info("No rows deleted  in Pending Queue table for lane_tx_id "+laneTxId );
                return false;
			}
		} catch (Exception e) 
		{
			logger.error("Error in deleteTransactionFromPendingQueue", e);
			return false;
		}
		logger.info("Class Name :"+this.getClass()+" Method Name : deleteTransactionsFromPendingQueue  - ENDED");

		return true;	
	}
	
	
	//************************STEP 2********************************
	public boolean completeTransactionsToNy12(List<LaneTxIdVO> laneTxIds) {
		logger.info("Class Name :"+this.getClass()+" Method Name : completeTransactionsToNy12 - STARTED"+laneTxIds.size()+"to be processed");
		

		//this.setMessage("completeTransactionsToNy12 begins ... ");
		try {
			//int sequenceNumber = 0;
			String distinctLaneTxId = null;	
			//Object[] txidArray = new String[1];
			LaneTxIdVO laneTxIdVO = new LaneTxIdVO();

			// one by one laneTxId is taken and update the sequence number for same laneTxId. This is done for all laneTxIds in the pending queue.
			if (laneTxIds != null) {
				Iterator itr =  laneTxIds.iterator();
				//List<TransactionVO> transactionVO_List;
				//int updateCount = 0;
				while (itr.hasNext()) {
					laneTxIdVO = (LaneTxIdVO) itr.next();
					distinctLaneTxId=laneTxIdVO.getDistinctLaneTransactionId();
					//this.setMessage("completeTransactionsToNy12 for LaneTxId : "+distinctLaneTxId);
					//this.currentLaneTxId= distinctLaneTxId;
					
					if (distinctLaneTxId == null) break;
					
					// execute Query Q3, Q4, Q5,Q6 & Q7
					List<MatchedTransactionsVO> matchedTransactionsVO = getAllMatchedTransactions(distinctLaneTxId);          // 	Query Q3
					if (matchedTransactionsVO != null) 
					{
						//this.setMessage("Matched Transactions for LaneTxId : "+distinctLaneTxId+" Exists");
						if (updateMatchedTxExternalReferenceNumber( matchedTransactionsVO,distinctLaneTxId))    // Query Q4
						{	   
							// executing Q5 , Q6 and Q7
							if (insertCompleteTransactions(distinctLaneTxId) && updateBalancesAndPlazaInformation(distinctLaneTxId) && deleteTransactionsFromPendingQueue(distinctLaneTxId))
							{
								totalNumberOfRecordsProcessed++;       
								logger.info("Class Name :"+this.getClass()+" Method Name : completeTransactionsToNy12 - insert,update, delete SUCCESSFULLY COMPLETED");
								//this.setMessage("Insert Complete Transactions, UpdateBalanceAndPlaza Informations and Delete Transactions From Pending Queue for LaneTxId "+distinctLaneTxId+" Successfully completed");
								//return true;
							} else 
							{
								logger.error("Error While performing either insert Complete Transaction / update BalancesAndPlazaInforamtion / deleteTransaction From Pending Queue for LaneTxId : "+distinctLaneTxId);
								//this.setMessage("Error while Insert Complete Transactions, UpdateBalanceAndPlaza Informations and Delete Transactions From Pending Queue for LaneTxId "+distinctLaneTxId+" Successfully completed");
								totalNumberOfErroredRecords++;  
								return false;

							}
						}
						
					} else 
					{
						logger.info("matchedTransactions is null");
						//this.setMessage("No matched Transactions for laneTxId "+distinctLaneTxId);
						return false;
					}
					   //ny12ProcessResponseVO.setProcessingStepMessages(getMessagesVO_List());
				}
				//ny12ProcessResponseVO_List.add(ny12ProcessResponseVO);
				//setNy12ProcessResponseVO_List(ny12ProcessResponseVO_List);
			}
		} catch (Exception e) 
		{
			logger.error("Error in completeTransactionsToNY12", e);
		}
		logger.info("Class Name :"+this.getClass()+" Method Name : completeTransactionsToNy12 - ENDED");
		// this completes the STEP 2
		return true;	

	}


	/**step1acctoseq**/
	public boolean completeReferenceNumberAndUpdateAccToSeq(List<String> distinctLaneTxIds, int sequenceno) {
		logger.info("Class Name :"+this.getClass()+" Method Name : completeReferenceNumberAndUpdateAccToSeq - STARTED");
		
		// executing Query 2
		//this.setMessage("Step  ... Get Distinct Lane Tx Id");
		
		if(distinctLaneTxIds!=null) {
			
			try {
				//int sequenceNumber = 0;
				String queryForNY12_Condition = "";
				String distinctLaneTxId = null;	
				MapSqlParameterSource paramSource = new MapSqlParameterSource();
				LaneTxIdVO laneTxIdVO = new LaneTxIdVO();
				String lanetxid;
				// one by one laneTxId is taken and update the sequence number for same laneTxId. This is done for all laneTxIds in the pending queue.
				if (distinctLaneTxIds != null) {
					Iterator<String> itr =  distinctLaneTxIds.iterator();
					List<TransactionVO> transactionVO_List;
					int updateCount = 0;
					while (itr.hasNext()) {

						lanetxid = itr.next();
						//distinctLaneTxId=laneTxIdVO.getDistinctLaneTransactionId();
						//logger.info("distinctLaneTxId : "+lanetxid);
						//this.currentLaneTxId= lanetxid;
						//if (distinctLaneTxId == null) break;
						// getting next sequence number for this lane tx Id
						//sequenceNumber=getNextSequenceNumber();
						//logger.info("sequence Number from query is : "+sequenceNumber);

						//transactionVO_List = new ArrayList();
						//this.setMessage("STEP 2");
						//this.setMessage("Step  ... Updating Tx Complete Reference Number in Pending Queue for LaneTxId : "+distinctLaneTxId);

						queryForNY12_Condition = LoadJpaQueries.getQueryById("UPDATE_TX_COMPLETE_REF_NUMBER_IN_PENDING_QUEUE");
						paramSource.addValue("lv_complete_ref_no", sequenceno);
						paramSource.addValue("lv_lane_tx_id",lanetxid);

						try {
							updateCount = namedJdbcTemplate.update(queryForNY12_Condition, paramSource);
							logger.info("update count in completeReferenceNumberAndUpdateAccToSeq: "+updateCount);
						} catch (EmptyResultDataAccessException empty) {
							logger.info("No data in pending queue - completeReferenceNumberAndUpdateAccToSeq ");
							continue;
						}
						//ny12ProcessResponseVO.setProcessingStepMessages(getListOfMessagesVO());
					}
					//ny12ProcessResponseVO_List.add(ny12ProcessResponseVO);
					//setNy12ProcessResponseVO_List(ny12ProcessResponseVO_List);
				}
			} catch (Exception e) {
				logger.error("Error in completeReferenceNumberAndUpdateAccToSeq", e);
				return false;
			}
			
		}else 
		{
			logger.info("distinctLaneTxIds in completeReferenceNumberAndUpdateAccToSeq is null");
			return false;
		}
		
		
		
		logger.info("Class Name :"+this.getClass()+" Method Name : completeReferenceNumberAndUpdateAccToSeq - ENDED");
		return true;	

	}
	
	
	/**step2acctose**/
	public boolean completeTransactionsToNy12AccToSeq(List<String> laneTxIds) {
		logger.info("Class Name :"+this.getClass()+" Method Name : completeTransactionsToNy12 - STARTED"+laneTxIds.size()+"to be processed");
	

		//this.setMessage("completeTransactionsToNy12 begins ... ");
		try {
			//int sequenceNumber = 0;
			String distinctLaneTxId = null;	
			//Object[] txidArray = new String[1];
			//LaneTxIdVO laneTxIdVO = new LaneTxIdVO();

			// one by one laneTxId is taken and update the sequence number for same laneTxId. This is done for all laneTxIds in the pending queue.
			if (laneTxIds != null) 
			{
				Iterator<String> itr =  laneTxIds.iterator();
				//List<TransactionVO> transactionVO_List;
				//int updateCount = 0;
				while (itr.hasNext()) {
					 distinctLaneTxId= itr.next();
					//laneTxIdVO = (LaneTxIdVO) itr.next();
					//distinctLaneTxId=laneTxIdVO.getDistinctLaneTransactionId();
					//logger.info("distinctLaneTxId : "+distinctLaneTxId);
					//this.setMessage("completeTransactionsToNy12 for LaneTxId : "+distinctLaneTxId);
					//this.currentLaneTxId= distinctLaneTxId;
					if (distinctLaneTxId == null) break;
					// execute Query Q3, Q4, Q5,Q6 & Q7
					List<MatchedTransactionsVO> matchedTransactionsVO = getAllMatchedTransactions(distinctLaneTxId);          // 	Query Q3
					if (matchedTransactionsVO != null) 
					{
						//this.setMessage("Matched Transactions for LaneTxId : "+distinctLaneTxId+" Exists");
						if (updateMatchedTxExternalReferenceNumber( matchedTransactionsVO,distinctLaneTxId))    // Query Q4
						{	   
							// executing Q5 , Q6 and Q7
							if (insertCompleteTransactions(distinctLaneTxId) && updateBalancesAndPlazaInformation(distinctLaneTxId) && deleteTransactionsFromPendingQueue(distinctLaneTxId))
							{
								totalNumberOfRecordsProcessed++;       
								logger.info("Class Name :"+this.getClass()+" Method Name : completeTransactionsToNy12AccToSeq -insert, update , delete  SUCCESSFULLY COMPLETED");
								//this.setMessage("Insert Complete Transactions, UpdateBalanceAndPlaza Informations and Delete Transactions From Pending Queue for LaneTxId "+distinctLaneTxId+" Successfully completed");
								//return true;
							} else {
								logger.error("Error While performing either insert Complete Transaction / update BalancesAndPlazaInforamtion / deleteTransaction From Pending Queue for LaneTxId : "+distinctLaneTxId);
								//this.setMessage("Error while Insert Complete Transactions, UpdateBalanceAndPlaza Informations and Delete Transactions From Pending Queue for LaneTxId "+distinctLaneTxId+" Successfully completed");
								totalNumberOfErroredRecords++;   
								return false;

							}
						}	   
					 } else 
					   {
						logger.info("matchedTransactions is null");
						//this.setMessage("No matched Transactions for laneTxId "+distinctLaneTxId);
						return false;
					
					   }
					//ny12ProcessResponseVO.setProcessingStepMessages(getListOfMessagesVO());
				}
				//ny12ProcessResponseVO_List.add(ny12ProcessResponseVO);
				//setNy12ProcessResponseVO_List(ny12ProcessResponseVO_List);
			} 
		} catch (Exception e) {
			logger.error("Error in completeTransactionsToNY12AccToSeq", e);
			return false;
		}
		logger.info("Class Name :"+this.getClass()+" Method Name : completeTransactionsToNy12AccToSeq - ENDED");
		// this completes the STEP 2
		return true;	

	}
	
	

	
	public List<VehicleClassNotEqualTo22TransactionVO> getAllTransactionsWhereVehicleClassNot22()
	{
		//currentStepName ="THIRD STEP";

		logger.info("Class Name :"+this.getClass()+" Method Name : getAllTransactionsWhereVehicleClassNot22 - STARTED");
		
		//List<TransactionVO> transactionVO_List = new ArrayList<TransactionVO>();
		List<VehicleClassNotEqualTo22TransactionVO> objectList = new ArrayList<>();
		try 
		{

			MapSqlParameterSource paramSource = new MapSqlParameterSource();
			//this.setMessage("STEP 3 : ");
			//this.setMessage("STEP 3 : (1 of 3)");
			//this.setMessage("Step  ... Selecting Pending Queue Transactions Where vehicle class is other than 22");

			String queryForNY12_Condition = LoadJpaQueries.getQueryById("VEHICLE_CLASS_NOTEQUALTO_22");
			try 
			{
				objectList = namedJdbcTemplate.query(queryForNY12_Condition, paramSource,
						BeanPropertyRowMapper.newInstance(VehicleClassNotEqualTo22TransactionVO.class));

			} catch (EmptyResultDataAccessException empty) 
			{
				logger.error("Error while fetching txn for vehicle not class22",empty);

			}
		} catch (Exception e) 
		{
			logger.error("Error in getAllTransactionsWhereVehicleClassNot22", e);
		}
		logger.info("Class Name :"+this.getClass()+" Method Name : getAllTransactionsWhereVehicleClassNot22 - ENDED");
	

		return objectList;	
	}
	
	

	public List<LastSectionTransactionVO> retriveAllTransactionsPertainingToLastSection(){
		List<DistinctEntryExitPlazaVO> objectList = new ArrayList<>();
		List<LastSectionTransactionVO> lastSectionTransactionVO_List = new ArrayList<>();
		List<LastSectionTransactionVO> lastFinalSectionTransactionVO_List = new ArrayList<>();

		logger.info("Class Name :"+this.getClass()+" Method Name : retriveAllTransactionsPertainingToLastSection - STARTED");
		try {
			//this.setMessage("Step  ... Get Distinct Lane Tx Id/Entry Plaza Id /Exit Plaza ");

			//String queryForNY12_Condition = LoadJpaQueries.getQueryById("GET_DISTINCT_ENTRY_AND_EXIT_PLAZA_IDS_FOR_LAST_SECTION");
			//String entryPlazaId,exitPlazaId = null;
			
			///DistinctEntryExitPlazaVO distinctEntryExitPlazaVO;

			//MapSqlParameterSource paramSource = new MapSqlParameterSource();
			//objectList = namedJdbcTemplate.query(queryForNY12_Condition,paramSource,
					//BeanPropertyRowMapper.newInstance(DistinctEntryExitPlazaVO.class));
			//for (DistinctEntryExitPlazaVO object: objectList) {
				//logger.info(" Entry Plaza id :"+object.getEntryPlazaId()+" Exit Plaza Id : "+object.getPlazaId());
			//}
			//if (objectList != null) {
				//Iterator<DistinctEntryExitPlazaVO> itr =  objectList.iterator();
				//while (itr.hasNext()) {

					//distinctEntryExitPlazaVO = (DistinctEntryExitPlazaVO) itr.next();
					//entryPlazaId = distinctEntryExitPlazaVO.getEntryPlazaId();
					//exitPlazaId  = distinctEntryExitPlazaVO.getPlazaId();
					//logger.info(" Entry Plaza Id : "+entryPlazaId+" Exit Plaza Id : "+exitPlazaId);
					//this.setMessage(" Entry Plaza Id : "+entryPlazaId+" Exit Plaza Id : "+exitPlazaId);
					try {
						//sectionId = findLastSectionId(entryPlazaId,exitPlazaId);
						lastSectionTransactionVO_List = getAllTransactionsOfLastSegment(NY12_Constants.LAST_SECTION_ID);    // Query Q9
						for (LastSectionTransactionVO object:lastSectionTransactionVO_List) 
						{
							lastFinalSectionTransactionVO_List.add(object);
						}
					   } catch (Exception e) {
						//logger.info("Exception occured while finding last section id for Entry Plaza Id : "+entryPlazaId+" Exit Plaza Id : "+exitPlazaId);
						//continue;
						logger.error("Error in while fetching tranoflastsection",e);
						
					}
				//}
			//}

		} catch (Exception e) {
			logger.error("Error in retriveAllTransactionsPertaningToLastSection", e);
		}
		logger.info("Class Name :"+this.getClass()+" Method Name : retriveAllTransactionsPertainingToLastSection - ENDED");


		return lastFinalSectionTransactionVO_List;

	}
	
	public List<LastSectionTransactionVO> getAllTransactionsOfLastSegment(String lastSectionId)
	{
		

		logger.info("Class Name :"+this.getClass()+" Method Name : getAllTransactionsOfLastSegment - With Parameter LastSectionId"+lastSectionId+"STARTED");

		
		List<LastSectionTransactionVO> objectList = new ArrayList<>();

		
		//String lastSectionId = null;
		try {
			MapSqlParameterSource paramSource = new MapSqlParameterSource();
			paramSource.addValue("lc_last_section_id", lastSectionId);
			//this.setMessage("STEP 3 - (2 of 3");
			//this.setMessage("Step  ...  Selecting Pending QueueTransactions of Last Section");

			String queryForNY12_Condition = LoadJpaQueries.getQueryById("TXNS_OF_LAST_SECTION");

			try {
				objectList = namedJdbcTemplate.query(queryForNY12_Condition, paramSource,
						BeanPropertyRowMapper.newInstance(LastSectionTransactionVO.class));
			    } catch (EmptyResultDataAccessException empty) 
			    {
				logger.info("NO trn for lastsection");
			    }
		} catch (Exception e) {
			logger.error("Error in getAllTransactionsOfLastSegment", e);
		}
		logger.info("Class Name :"+this.getClass()+" Method Name : getAllTransactionsOfLastSegment - With Parameter LastSectionId - ENDED");
		

		return objectList;	
	}

	public List<SameSegmentTransactionVO> getAllTransactionsOfSameSegmentPlazasAndNonBoundaryPlazas()
	{
		
		
		logger.info("Class Name :"+this.getClass()+" Method Name : getAllTransactionsOfSameSegmentPlazasAndNonBoundaryPlazas - STARTED");
		List<SameSegmentTransactionVO> objectList = new ArrayList<>();
		try {
			MapSqlParameterSource paramSource = new MapSqlParameterSource();
			//this.setMessage("STEP 3 - (3 of 3");
			//this.setMessage("Step  ... Getting all Transactions of Same Segment and it is not Non Boundary");

			String queryForNY12_Condition = LoadJpaQueries.getQueryById("SAME_SEGMENT_PLAZA_AND_NOT_NON_BOUNDARY");
			try {
				objectList = namedJdbcTemplate.query(queryForNY12_Condition, paramSource,
						BeanPropertyRowMapper.newInstance(SameSegmentTransactionVO.class));
				//totalNumberOfRecordsToBeProcessed += objectList.size();
				
				//wht this is used for
				//for (SameSegmentTransactionVO vo : objectList) {
					//logger.info("3rd Step Part 3 ...laneTxId ..."+vo.getLaneTxId());
				//}
				
				
				/*
				if (!updateCompleteReferenceNumber(false, objectArrayList)) 
				{
					logger.info("Error while updating and loading complete transactions of same segment plaza nad Non Boundary Plaza");
				    this.setMessage("Error while updating and loading complete transactions of same segment plaza nad Non Boundary Plaza");
					return objectList;
				}
				 */
				
			} catch (EmptyResultDataAccessException empty) 
			{
				logger.info("no data for samesegm and non boundary plaza");
			}
		} catch (Exception e) {
			logger.error("Error in getAllTransactionsOfSameSegmentPlazasAndNOnBoundaryPlazas", e);
		}
		logger.info("Class Name :"+this.getClass()+" Method Name : getAllTransactionsOfSameSegmentPlazasAndNonBoundaryPlazas - ENDED");
		
		//this.setMessage(currentStepName+" - PART 3 - ENDED ....");
		
		//ny12ProcessResponseVO.setProcessingStepMessages(getListOfMessagesVO());
		//ny12ProcessResponseVO_List.add(ny12ProcessResponseVO);
		//setNy12ProcessResponseVO_List(ny12ProcessResponseVO_List);

		return objectList;	
	}
	
	public int getProcessParameterForNY12_WaitDays(String paramname) {
		logger.info("Class Name :"+this.getClass()+" Method Name : getProcessParameterForNY12_WaitDays - STARTED");
		List<ProcessParameter> processParameterList = new ArrayList<ProcessParameter>();
		try {

			MapSqlParameterSource paramSource = new MapSqlParameterSource();
		     paramSource.addValue("param_name", paramname);
			//this.setMessage("Step  ...  Selecting Process parameter of NY12 Waiting days");

			String queryForNY12_Condition = LoadJpaQueries.getQueryById("SELECT_PROCESS_PARAMETER_NY12_WAIT_DAYS");

			//logger.info("Query to get records : {}", queryForNY12_Condition);

			try {
				
				  processParameterList = namedJdbcTemplate.query(queryForNY12_Condition,
				  paramSource, BeanPropertyRowMapper.newInstance(ProcessParameter.class));
				 
				//processParameterList =jdbcTemplate.queryForList(queryForNY12_Condition, ProcessParameter.class);
				
				//return processParameterList.get(0).getParam_value();

			} catch (EmptyResultDataAccessException empty) {
				logger.info("no data in TPMS table");

			}
		} catch (Exception e) {
			logger.error("Error while fetching data for param wait days", e);
		}
		logger.info("Class Name :"+this.getClass()+" Method Name : getProcessParameterForNY12_WaitDays - ENDED");

		//return 100000;
		return processParameterList.get(0).getParam_value();
	}
	
	
	
	public List<String> getAllDistinctDevices(String lastSectionId)
	{
		logger.info("Class Name :"+this.getClass()+" Method Name : getAllDistinctDevices - STARTED");
		//logger.info("Parameters passed to this method is lastSectionId : "+lastSectionId);
		//currentStepName = "FOURTH STEP...";
		//logger.info(currentStepName+ " STARTED....");
		List<String> deviceList = new ArrayList<>();

		/*
		 * if (lastSectionId == null) { logger.info("Since Last Section Id is null");
		 * return deviceList; }
		 */

		try {

			MapSqlParameterSource paramSource = new MapSqlParameterSource();
			paramSource.addValue("lc_last_section_id", lastSectionId);
			//this.setMessage("STEP 4");
			//this.setMessage("Step  ... Select Device Number from Pending Queue");
			String queryForNY12_Condition = LoadJpaQueries.getQueryById("SELECT_DEVICE_NO_FROM_PENDING_QUEUE");

			

			try {
				
				deviceList=namedJdbcTemplate.queryForList(queryForNY12_Condition, paramSource, String.class);
				
				/*
				 * deviceList = namedJdbcTemplate.query(queryForNY12_Condition, paramSource,
				 * BeanPropertyRowMapper.newInstance(String.class));
				 */
				 
				  //System.out.println(deviceList);
				  //deviceList=jdbcTemplate.queryForList(queryForNY12_Condition);
				//deviceList=jdbcTemplate.queryForList(queryForNY12_Condition, String.class);
			} catch (EmptyResultDataAccessException empty) {
				logger.info("No Devices selected From Pending Queue ");
				return deviceList;
			}
		} catch (Exception e) {
			logger.error("Error while fetching device list", e);
		}
		logger.info("Class Name :"+this.getClass()+" Method Name :  getAllDistinctDevices - ENDED");
		//logger.info(currentStepName+ " ENDED....");
		//setProcessingSetpMessage(messagesVO_List) ;

		return deviceList;	
	}

	public List<DeviceTransactionVO> getAllTransactionsByDevice(String deviceNo,String lastSectionId)
	{
		//currentStepName = "FIFTH STEP ";
		//logger.info(currentStepName+" STARTED.....");
		logger.info("Class Name :"+this.getClass()+" Method Name : getAllTransactionsByDevice - STARTED");
		//logger.info("Parameters passed are deviceNo :"+deviceNo+" Last Section Id : "+lastSectionId);
		List<DeviceTransactionVO> deviceTransactionList =  new ArrayList<>();
		try {

			MapSqlParameterSource paramSource = new MapSqlParameterSource();
			paramSource.addValue("lv_eachtag_device_no", deviceNo);
			paramSource.addValue("lc_last_section_id",lastSectionId);
			//this.setMessage("STEP 5 ");
			//this.setMessage("Step  ... Getting All Transactions of device");

			String queryForNY12_Condition = LoadJpaQueries.getQueryById("GET_ALL_TRANSACTIONS_FOR_DEVICE");

			

			try {
				deviceTransactionList = namedJdbcTemplate.query(queryForNY12_Condition, paramSource,
						BeanPropertyRowMapper.newInstance(DeviceTransactionVO.class));


			} catch (EmptyResultDataAccessException empty) {
				logger.info("NO trx in pending queue for device: "+deviceNo);
				return deviceTransactionList;

			}
		} catch (Exception e) {
			logger.error("Error while fetching trx for device- getAllTransactionsByDevice", e);
		}
		logger.info("Class Name :"+this.getClass()+" Method Name : getAllTransactionsByDevice - ENDED");

		//logger.info(currentStepName+ " ENDED....");

		return deviceTransactionList;	
	}
	
	
	
	
/********/
	public boolean updateEntryExit(String entry_plaza_id,String plaza_id) {
		String queryForNY12=" ";
		int updateCount;
		queryForNY12= LoadJpaQueries.getQueryById("UPDATE_ENTRY_EXIT");
		

		try {
			MapSqlParameterSource paramSource = new MapSqlParameterSource();
			paramSource.addValue("entry_plaza_id", entry_plaza_id);
			paramSource.addValue("exit_plaza_id", plaza_id);
			updateCount = namedJdbcTemplate.update(queryForNY12, paramSource);
			logger.info("update count : "+updateCount);
		}catch(Exception e) {
			e.getStackTrace();
			
		}
		return true;
	
		
		
	}
	
	@Override
	public String getLastPlazaOfSegment(String sectionid) {
		// TODO Auto-generated method stub
		//List<String> lastplaza = new ArrayList<>();
		String lastplaza=null;
		
		try {

			MapSqlParameterSource paramSource = new MapSqlParameterSource();
			paramSource.addValue("lc_section_id", sectionid);
			//this.setMessage("STEP 4");
			//this.setMessage("Step  ... Select Device Number from Pending Queue");
			String queryForNY12_Condition = LoadJpaQueries.getQueryById("SELECT_DEVICE_NO_FROM_PENDING_QUEUE");

			logger.info("Query to get records : {}", queryForNY12_Condition);

			try {
				
				
				  //lastplaza=namedJdbcTemplate.queryForList(queryForNY12_Condition, paramSource, String.class);
				 lastplaza=namedJdbcTemplate.queryForObject(queryForNY12_Condition, paramSource, String.class);
			} catch (EmptyResultDataAccessException empty) {
				logger.info("No Devices selected From Pending Queue ");
			}
		} catch (Exception e) {
			logger.error("Something is wrong {}", e);
		}
		
		return lastplaza;
	}
	
/*************/
	

	

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	/*********/
	
	   
	   // called by findWhetherAnyRowsToBeProcessed , not used 
		public int getCountQuery() {
		logger.info("Class Name :"+this.getClass()+" Method Name : getCountQuery - STARTED");
		String queryToCheckFile = LoadJpaQueries.getQueryById("QUERY_COUNT");
		int totalRowCount = 0;
		try {
			if (jdbcTemplate == null) {
				logger.info("jdbcTemplate is null ....");
			}
			List<Map<String,Object>> map =   jdbcTemplate.queryForList(queryToCheckFile);
			Iterator mapIterator = map.iterator();    
			while (mapIterator.hasNext())
			{
				Map record = (Map) mapIterator.next();
				totalRowCount = Integer.parseInt( record.get("TOTAL_COUNT").toString());
			}
			logger.info("Total Number of Lane Tx Id to be processed is : "+totalRowCount);
			this.totalNumberOfRecordsToBeProcessed=totalRowCount;
			return totalRowCount; 

		} catch (Exception ex) {
			logger.error("Error while retrieving total count query");  
			ex.printStackTrace();
		}
		logger.info("Class Name :"+this.getClass()+" Method Name : getCountQuery - ENDED");
		return 0;
	}
		
		
		// not used
		public boolean updateCompleteReferenceNumber(List<Object[]> objectList) {
			logger.info("Class Name :"+this.getClass()+" Method Name : updateCompleteReferenceNumber - STARTED");
			// executing Query 2
			this.setMessage("Step  ... Get Distinct Lane Tx Id");
			try {
				int sequenceNumber = 0;
				String queryForNY12_Condition = null;
				String distinctLaneTxId = null;	
				MapSqlParameterSource paramSource = new MapSqlParameterSource();
				List<LaneTxIdVO> distinctLaneTxIds = new ArrayList<>();
				Object[] txidArray = new String[1];
				LaneTxIdVO laneTxIdVO = new LaneTxIdVO();

				if (objectList != null) {
					Iterator iterator=objectList.iterator();
					while (iterator.hasNext())
					{
						txidArray=	(Object[])iterator.next();
						laneTxIdVO = new LaneTxIdVO();
						laneTxIdVO.setDistinctLaneTransactionId(txidArray[0].toString());
						distinctLaneTxIds.add(laneTxIdVO);
					}
				}
				// one by one laneTxId is taken and update the sequence number for same laneTxId. This is done for all laneTxIds in the pending queue.
				if (distinctLaneTxIds != null) {
					Iterator itr =  distinctLaneTxIds.iterator();
					List<TransactionVO> transactionVO_List;
					int updateCount = 0;
					while (itr.hasNext()) {

						laneTxIdVO = (LaneTxIdVO) itr.next();
						distinctLaneTxId=laneTxIdVO.getDistinctLaneTransactionId();
						logger.info("distinctLaneTxId : "+distinctLaneTxId);
						//this.currentLaneTxId= distinctLaneTxId;
						if (distinctLaneTxId == null) break;
						// getting next sequence number for this lane tx Id
						sequenceNumber=getNextSequenceNumber();
						logger.info("sequence Number from query is : "+sequenceNumber);

						transactionVO_List = new ArrayList();
						//queryForNY12_Condition = LoadJpaQueries.getQueryById("UPDATE_TX_COMPLETE_REF_NUMBER_IN_PENDING_QUEUE_USING_ALTERNATE_QUERY");
						//this.setMessage("STEP 2");
						//this.setMessage("Step  ... Updating Tx Complete Reference Number in Pending Queue for LaneTxId : "+distinctLaneTxId);

						queryForNY12_Condition = LoadJpaQueries.getQueryById("UPDATE_TX_COMPLETE_REF_NUMBER_IN_PENDING_QUEUE");

						// parameters passed to the above query 
						paramSource.addValue("lv_complete_ref_no", sequenceNumber);
						paramSource.addValue("lv_lane_tx_id",distinctLaneTxId);

						try {
							updateCount = namedJdbcTemplate.update(queryForNY12_Condition, paramSource);
							logger.info("update count : "+updateCount);
						} catch (EmptyResultDataAccessException empty) {
							logger.info("no data in Pending Queue table");
							continue;
						}
						// execute Query Q3, Q4, Q5,Q6 & Q7
						List<MatchedTransactionsVO> matchedTransactionsVO = getAllMatchedTransactions(distinctLaneTxId);          // 	Query Q3
						if (matchedTransactionsVO != null) 
						{
							if (updateMatchedTxExternalReferenceNumber( matchedTransactionsVO,distinctLaneTxId))    // Query Q4
							{	   
								// executing Q5 , Q6 and Q7
								if (insertCompleteTransactions(distinctLaneTxId) && updateBalancesAndPlazaInformation(distinctLaneTxId) && deleteTransactionsFromPendingQueue(distinctLaneTxId))
								{
									//			totalNumberOfRecordsProcessed++;       
									//logger.info("Class Name :"+this.getClass()+" Method Name : updateCompleteReferenceNumber -ENDED - SECOND STEP SUCCESSFULLY COMPLETED");
									logger.info("Class Name :"+this.getClass()+" Method Name : updateCompleteReferenceNumber -ENDED - SUCCESSFULLY COMPLETED");
									//return true;
								} else {
									logger.error("Error While performing either insert Complete Transaction / update BalancesAndPlazaInforamtion / deleteTransaction From Pending Queue for LaneTxId : "+distinctLaneTxId);
									

								}
								totalNumberOfErroredRecords++;        	
							}	   
						} else 
						{
							logger.info("matchedTransactions is null");
							//this.setMessage("No matched Transactions for laneTxId "+distinctLaneTxId);
							//return false;
						}
						 //ny12ProcessResponseVO.setProcessingStepMessages(getListOfMessagesVO());

					}
				}
				       // ny12ProcessResponseVO_List.add(ny12ProcessResponseVO);


			} catch (Exception e) {
				logger.error("Error in step 2 updateCompleteReferenceNumber", e);
			}
			logger.info("Class Name :"+this.getClass()+" Method Name : updateCompleteReferenceNumber - ENDED");
			// this completes the STEP 2
			return true;	
		}

		
		//called in service not used
		public boolean startProcessingTransactionsForVehicleClassNot22()
		{
			logger.info("Class Name :"+this.getClass()+" Method Name : startProcessingTransactionsForVehicleClassNot22 - STARTED");

			logger.info("Class Name :"+this.getClass()+" Method Name :  startProcessingTransactionsForVehicleClassNot22 - ENDED");

			return true;	
		}
		
		
		
		// AFter query q8 is fired we have to fire ....
		// called in service impl not sure what it is used for
		public boolean updateAllTrasactions(List<VehicleClassNotEqualTo22TransactionVO> objectList)
		{
			logger.info("Class Name :"+this.getClass()+" Method Name : updateAllTrasactions - STARTED");
			if (objectList == null) {
				logger.info("ObjectList passed to updateAllTransactions is null");
				return false;
			}

			String laneTxId = null;
			String entryPlaza = null;
			String exitPlaza = null;
			String entryLaneId = null;
			Timestamp entryTimestamp = null;
			double discountedAmount = 0.00;
			double collectedAmount = 0.00;
			double unrealizedAmount = 0.00;
			String actualClass = null;
			VehicleClassNotEqualTo22TransactionVO vehicleClassNotEqualTo22TransactionVO = new VehicleClassNotEqualTo22TransactionVO();
			try {
				MapSqlParameterSource paramSource = new MapSqlParameterSource();
				paramSource.addValue("lv_lane_tx_id", laneTxId);
				List<Integer> updateCount = new ArrayList<Integer>();
				Iterator<VehicleClassNotEqualTo22TransactionVO> objectArrayItr = objectList.iterator();
				while (objectArrayItr.hasNext()) {
					vehicleClassNotEqualTo22TransactionVO = (VehicleClassNotEqualTo22TransactionVO) objectArrayItr.next();
					laneTxId = vehicleClassNotEqualTo22TransactionVO.getLaneTxId();
					entryPlaza = vehicleClassNotEqualTo22TransactionVO.getEntryPlazaId();
					exitPlaza = vehicleClassNotEqualTo22TransactionVO.getPlazaId();
					entryLaneId = vehicleClassNotEqualTo22TransactionVO.getEntryLaneId();
					entryTimestamp = vehicleClassNotEqualTo22TransactionVO.getEntryTimeStamp();
					discountedAmount = vehicleClassNotEqualTo22TransactionVO.getDiscountedAmount();
					collectedAmount = vehicleClassNotEqualTo22TransactionVO.getCollectedAmount();
					unrealizedAmount = vehicleClassNotEqualTo22TransactionVO.getUnrealizedAmount();
					actualClass = vehicleClassNotEqualTo22TransactionVO.getActualClass();			
					paramSource = new MapSqlParameterSource();
					paramSource.addValue("lv_lane_tx_id", laneTxId);
					paramSource.addValue("lv_entry_plaza", entryPlaza);
					paramSource.addValue("lv_exit_plaza", exitPlaza);
					paramSource.addValue("lv_entry_lane_id", entryLaneId);
					paramSource.addValue("lv_entry_timestmp", entryTimestamp);
					paramSource.addValue("lv_discounted_amount", discountedAmount);
					paramSource.addValue("lv_collected_amount", collectedAmount);
					paramSource.addValue("lv_unrealized_amount", unrealizedAmount);

					/*paramSource = new MapSqlParameterSource();
					paramSource.addValue("lv_lane_tx_id", matchedTransactionsVO.getLaneTxId());
					paramSource.addValue("lv_match_ref_no", matchedTransactionsVO.getMatchRefNo());
					updateCount = namedJdbcTemplate.update(queryForNY12_Condition, paramSource);
					 */
					//???? executeStep1 & 2	          
					//				String queryForNY12_Condition = LoadJpaQueries.getQueryById("QUERY_Q6_UPDATE_COMPLETE_TXN");


					try {
						//					updateCount = namedJdbcTemplate.query(queryForNY12_Condition, paramSource,
						//							BeanPropertyRowMapper.newInstance(Integer.class));


					} catch (EmptyResultDataAccessException empty) {
						logger.info("No data to process in Pending Queue table");

					}
				}
			} catch (Exception e) {
				logger.error("Something is wrong {}", e);
			}
			logger.info("Class Name :"+this.getClass()+" Method Name : updateAllTrasactions - ENDED");

			return true;	
		}
		
		
		
		//////****////
		
		public boolean loadCompleteTransaction(List<VehicleClassNotEqualTo22TransactionVO> transactionVO_List)
		{
			logger.info("Class Name :"+this.getClass()+" Method Name : loadCompleteTransaction - STARTED");
			boolean success = true;   
			//????	boolean success = loadCompleteTransactionsOfSameSegmentPlazasAndNonBoundaryPlazas(transactionVO_List);

			logger.info("Class Name :"+this.getClass()+" Method Name : loadCompleteTransaction  - ENDED");

			return success;	
		}
		
		public boolean doesPlazaExistOnSameSegment(String entryPlazaId,String exitPlazaId)
		{
			logger.info("Class Name :"+this.getClass()+" Method Name : doesPlazaExistOnSameSegment - STARTED");
			logger.info("Parameters passed to  this method is EntryPlazaid "+entryPlazaId +" exitPlazaId : "+exitPlazaId);
			if (entryPlazaId == null || exitPlazaId == null) {
				logger.info("Entry Plaza Id or Exit Plaza id is null. Entry Plaza Id : "+entryPlazaId+" exitPlazaId : "+exitPlazaId);
				return false;
			}

			try {
				List<HighwaySectionVO> highwaySectionVO_List = new ArrayList<>();

				MapSqlParameterSource paramSource = new MapSqlParameterSource();
				paramSource.addValue("lv_entry_plaza_id", entryPlazaId);
				paramSource.addValue("lv_exit_plaza_id", exitPlazaId);

				//this.setMessage("Step  ... Does plaza exists on same segment");

				String queryForNY12_Condition = LoadJpaQueries.getQueryById("DOES_PLAZA_EXISTS_ON_SAME_SEGMENT");
				try {
					highwaySectionVO_List = namedJdbcTemplate.query(queryForNY12_Condition, paramSource,
							BeanPropertyRowMapper.newInstance(HighwaySectionVO.class));

					if (highwaySectionVO_List != null) {
						return true;
					} else {
						return false;
					}
				} catch (EmptyResultDataAccessException empty) {
					logger.info("no data in highway section  table");

				}
			} catch (Exception e) {
				logger.error("Something is wrong {}", e);
			}

			logger.info("Class Name :"+this.getClass()+" Method Name : doesPlazaExistOnSameSegment - ENDED");
			return true;	
		}
		
		
		public boolean doesPlazaBelongsToBoundarySegmentPlaza(String plazaId)
		{
			logger.info("Class Name :"+this.getClass()+" Method Name : doesPlazaBelongsToBoundarySegmentPlaza - STARTED");
			logger.info("Parameters passed to  this method is plazaid "+plazaId );
			if (plazaId == null) {
				logger.info("Plaza Id is null ");
				return false;
			}
			try {
				List<Plaza> plazaList = new ArrayList<>();
				MapSqlParameterSource paramSource = new MapSqlParameterSource();
				paramSource.addValue("lv_plazaId", plazaId);
				//this.setMessage("Step  ... Query  ... Bounday Segment Plaza");

				String queryForNY12_Condition = LoadJpaQueries.getQueryById("BOUNDARY_SEGMENT_PLAZA");

				logger.info("Query to get records : {}", queryForNY12_Condition);

				try {
					plazaList = namedJdbcTemplate.query(queryForNY12_Condition, paramSource,
							BeanPropertyRowMapper.newInstance(Plaza.class));
					if (plazaList != null) {
						return true;
					}
					else {
						return false;
					}	
				} catch (EmptyResultDataAccessException empty) {
					logger.info("no data in Plaza table for this plaza Id : "+plazaId);
				}
			} catch (Exception e) {
				logger.error("Something is wrong {}", e);
			}
			logger.info("Class Name :"+this.getClass()+" Method Name : doesPlazaBelongsToBoundarySegmentPlaza - ENDED");

			return false;	
		}
		
		
		
		public List<Plaza> getAllBoundaryPlazaOfSegment(String segmentId)
		{
			logger.info("Class Name :"+this.getClass()+" Method Name : getAllBoundaryPlazaOfSegment - STARTED");
			logger.info("Parameters passed to  this method is segmentId "+segmentId );
			List<Plaza> plazaList = new ArrayList<>();

			if (segmentId == null) {
				logger.info("Segment  Id is null ");
				return plazaList;
			}

			try {

				MapSqlParameterSource paramSource = new MapSqlParameterSource();
				//this.setMessage("Step  ... Query  ... Getting all boundary plaza of segment");

				String queryForNY12_Condition = LoadJpaQueries.getQueryById("GET_ALL_BOUNDARY_PLAZA_OF_SEGMENT");

				logger.info("Query to get records : {}", queryForNY12_Condition);

				try {
					plazaList = namedJdbcTemplate.query(queryForNY12_Condition, paramSource,
							BeanPropertyRowMapper.newInstance(Plaza.class));
				} catch (EmptyResultDataAccessException empty) {
					logger.info("no data in TPMS table");
				}
			} catch (Exception e) {
				logger.error("Something is wrong {}", e);
			}
			logger.info("Class Name :"+this.getClass()+" Method Name : getAllBoundaryPlazaOfSegment - ENDED");

			return plazaList;	
		}
		
		
		
		public List<LastSectionTransactionVO> getAllTransactionsOfLastSegment(String entryPlazaId,String exitPlazaId)
		{
			logger.info(currentStepName+ " - PART 2 STARTED....");

			logger.info("Class Name :"+this.getClass()+" Method Name : getAllTransactionsOfLastSegment - With PlazaId Parameters STARTED");
			logger.info("Parameters passed to  this method is EntryPlazaid "+entryPlazaId +" exitPlazaId : "+exitPlazaId);
			List<LastSectionTransactionVO> objectList = new ArrayList<>();

			if (entryPlazaId == null || exitPlazaId == null) {
				logger.info("Entry Plaza Id or Exit Plaza id is null. Entry Plaza Id : "+entryPlazaId+" exitPlazaId : "+exitPlazaId);
				return objectList;
			}

			String lastSectionId = null;
			try {
				MapSqlParameterSource paramSource = new MapSqlParameterSource();
				paramSource.addValue("lc_last_section_id", lastSectionId);

				List<Integer> deleteCount = new ArrayList<Integer>();
				//this.setMessage("STEP 3 - (2 of 3");
				//this.setMessage("Step  ... Selecting Pending QueueTransactions of Last Section");

				String queryForNY12_Condition = LoadJpaQueries.getQueryById("SELECT_PENDING_QUEUE_TXNS_OF_LAST_SECTION");

				logger.info("Query to get records : {}", queryForNY12_Condition);

				try {
					objectList = namedJdbcTemplate.query(queryForNY12_Condition, paramSource,
							BeanPropertyRowMapper.newInstance(LastSectionTransactionVO.class));
					List<Object[]> objectArrayList = new ArrayList<>();
					Object[] objectArray = new Object[1];
					totalNumberOfRecordsToBeProcessed += objectList.size();

					for (LastSectionTransactionVO vo : objectList) {
						objectArray[0] = vo.getLaneTxId();
						objectArrayList.add(objectArray);
					}

				} catch (EmptyResultDataAccessException empty) {
					logger.info("no data in Pending Queue table");

				}
			} catch (Exception e) {
				logger.error("Something is wrong {}", e);
			}
			logger.info("Class Name :"+this.getClass()+" Method Name : getAllTransactionsOfLastSegment With PlazaId Parameters - ENDED");
			logger.info(currentStepName+ "  PART 2 ENDED....");

			return objectList;	
		}
		
		
		
		


		public boolean updateAllTransactionsOfLastSegment(List<LastSectionTransactionVO> transactionVO_List)
		{
			logger.info("Class Name :"+this.getClass()+" Method Name : updateAllTransactionsOfLastSegment  - STARTED");
			try {
				//transactionVO_List = getAllTransactionsOfLastSegment();
			} catch (Exception e) {
				logger.error("Something is wrong {}", e);
			}
			logger.info("Class Name :"+this.getClass()+" Method Name : updateAllTransactionsOfLastSegment - ENDED");

			return true;	
		}
		
		public boolean loadCompleteTransactionsOfLastSegment(List<LastSectionTransactionVO> transactionVO_List)
		{
			logger.info("Class Name :"+this.getClass()+" Method Name : loadCompleteTransactionsOfLastSegment - STARTED");
			//????	boolean success = loadCompleteTransactionsOfSameSegmentPlazasAndNonBoundaryPlazas(transactionVO_List);
			boolean success = true;
			logger.info("Class Name :"+this.getClass()+" Method Name : loadCompleteTransactionsOfLastSegment - ENDED");
			return success;	
		}
		
		
		
		
		
		public boolean updateAllTransactionsOfSameSegmentPlazasAndNonBoundaryPlazas(List<SameSegmentTransactionVO> transactionVOList)
		{
			logger.info("Class Name :"+this.getClass()+" Method Name : updateAllTransactionsOfSameSegmentPlazasAndNonBoundaryPlazas - STARTED");
			try {
				List<Object[]> objectArrayList = new ArrayList<>();
				Object[] objectArray = new Object[1];
				for (SameSegmentTransactionVO vo : transactionVOList) {
					objectArray[0] = vo.getLaneTxId();
					objectArrayList.add(objectArray);
				}

			} catch (Exception e) {
				logger.error("Something is wrong {}", e);
			}
			logger.info("Class Name :"+this.getClass()+" Method Name : updateAllTransactionsOfSameSegmentPlazasAndNonBoundaryPlazas - ENDED");

			return true;	
		}
		
		public boolean loadCompleteTransactionsOfSameSegmentPlazasAndNonBoundaryPlazas(List<SameSegmentTransactionVO> transactionVO)
		{
			logger.info("Class Name :"+this.getClass()+" Method Name : loadCompleteTransactionsOfSameSegmentPlazasAndNonBoundaryPlazas - STARTED");
			try {
				List<CompleteTransactionVO> completeTransactionVO_List = new ArrayList<CompleteTransactionVO>();
				MapSqlParameterSource paramSource = new MapSqlParameterSource();

				String queryForNY12_Condition = LoadJpaQueries.getQueryById("GET_TRANSACTIONS_FROM_COMPLETE_TRANSACTION");

				logger.info("Query to get records : {}", queryForNY12_Condition);

				try {
					completeTransactionVO_List = namedJdbcTemplate.query(queryForNY12_Condition, paramSource,
							BeanPropertyRowMapper.newInstance(CompleteTransactionVO.class));


				} catch (EmptyResultDataAccessException empty) {
					logger.info("no data in TPMS table");

				}
			} catch (Exception e) {
				logger.error("Something is wrong {}", e);
			}
			logger.info("Class Name :"+this.getClass()+" Method Name : loadCompleteTransactionsOfSameSegmentPlazasAndNonBoundaryPlazas - ENDED");

			return true;	
		}
		
		
		public String findLastSectionId(String entryPlazaId,String exitPlazaId) {
			logger.info("Class Name : "+this.getClass()+" Method Name : findLastSectionId - STARTED ");
			logger.info("Parameters passed are Entry Plaza Id : "+entryPlazaId + " Exit Plaza Id : "+exitPlazaId);
			List<HighwaySectionVO> highwaySectionVO_List = new ArrayList<>();
			MapSqlParameterSource paramSource2 = new MapSqlParameterSource();
			paramSource2.addValue("lv_entry_plaza_id", entryPlazaId);
			paramSource2.addValue("lv_exit_plaza_id", exitPlazaId);
			int entryPlazaId_value = Integer.parseInt(entryPlazaId);
			int exitPlazaId_value = Integer.parseInt(exitPlazaId);
			boolean sameSegmentFlag = false;
			String startingSectionId = null;
			String endingSectionId = null;
			String queryForNY12_Condition = LoadJpaQueries.getQueryById("RETRIEVE_ALL_ROWS_FROM_HIGHWAY_SECTION_TABLE");
			try {
				highwaySectionVO_List = namedJdbcTemplate.query(queryForNY12_Condition, paramSource2,
						BeanPropertyRowMapper.newInstance(HighwaySectionVO.class));

				if (highwaySectionVO_List != null) {
					for (HighwaySectionVO highwaySectionVO:highwaySectionVO_List) {
						if (highwaySectionVO.getTerminalPlazaId1() <= entryPlazaId_value && exitPlazaId_value <=highwaySectionVO.getTerminalPlazaId2())
						{
							logger.info("Both entry plaza id and exit plaza id belongs to same segment"); 
							sameSegmentFlag = true;
							break;
						}
						if (highwaySectionVO.getTerminalPlazaId1() <= entryPlazaId_value) {
							startingSectionId = String.valueOf(highwaySectionVO.getSectionId());
						}
						if (highwaySectionVO.getTerminalPlazaId2() >= exitPlazaId_value) {
							endingSectionId = String.valueOf(highwaySectionVO.getSectionId());
							break;	
						}
						if (highwaySectionVO.getTerminalPlazaId1() > exitPlazaId_value ) { // didnt enter next segment
							break;
						}

					}
					logger.info("Starting section Id :"+startingSectionId + " Ending Section Id : "+endingSectionId);
					if (sameSegmentFlag) return null;
					if (endingSectionId != null) {
						return endingSectionId;
					} else {
						logger.info("No matching section id for the exit plaza Id : "+ exitPlazaId);
					}
					if (startingSectionId == null) {
						logger.info("No matching section id for the entry plaza Id : "+ entryPlazaId);
					}
				} else {
					logger.info("There are no rows in Highway Section Table");
				}
			} catch (Exception ex) {
				logger.info("Error occured While retrieving Highway Section Table ");
			}		
			logger.info("Class Name : "+this.getClass()+" Method Name : findLastSectionId - ENDED ");

			return null;

		}

		///////////////////not being caalled 
		public List<LastSectionTransactionVO> retriveAllTransactionsPertainingToLastSectionOfMultipleOccurenceOfSameLaneTxId(){
			List<DistinctLaneTxPlazaVO> objectList = new ArrayList<>();
			List<LastSectionTransactionVO> lastSectionTransactionVO_List = new ArrayList<>();
			List<LastSectionTransactionVO> lastFinalSectionTransactionVO_List = new ArrayList<>();

			logger.info("Class Name :"+this.getClass()+" Method Name : retriveAllTransactionsPertainingToLastSection - STARTED");
			try {
				//this.setMessage("Step  ... Get Distinct Lane Tx Id/Entry Plaza Id /Exit Plaza ");

				String queryForNY12_Condition = LoadJpaQueries.getQueryById("GET_DISTINCT_LANE_TX_ID_ENTRY_AND_EXIT_PLAZA_IDS");
				String distinctLaneTxId = null;	
				String laneTxId =null;
				String prevLaneTxId = null;
				String entryPlazaId,exitPlazaId = null;
				String sectionId = null;
				String lastSectionId = null;
				boolean firstTimeFlag = true;
				List<HighwaySectionVO> highwaySectionVO_List = new ArrayList<>();
				boolean pendingFlag = true;
				DistinctLaneTxPlazaVO distinctLaneTxPlazaVO;

				MapSqlParameterSource paramSource = new MapSqlParameterSource();
				objectList = namedJdbcTemplate.query(queryForNY12_Condition,paramSource,
						BeanPropertyRowMapper.newInstance(DistinctLaneTxPlazaVO.class));
				for (DistinctLaneTxPlazaVO object: objectList) {
					logger.info("Lane Tx Id : "+object.getLaneTxId()+" Entry Plaza id :"+object.getEntryPlazaId()+" Exit Plaza Id : "+object.getPlazaId());
				}
				if (objectList != null) {
					Iterator<DistinctLaneTxPlazaVO> itr =  objectList.iterator();
					while (itr.hasNext()) {
						pendingFlag = true;
						distinctLaneTxPlazaVO = (DistinctLaneTxPlazaVO) itr.next();
						laneTxId = distinctLaneTxPlazaVO.getLaneTxId();
						entryPlazaId = distinctLaneTxPlazaVO.getEntryPlazaId();
						exitPlazaId  = distinctLaneTxPlazaVO.getPlazaId();
						logger.info("Lane Tx Id : "+laneTxId+" Entry Plaza Id : "+entryPlazaId+" Exit Plaza Id : "+exitPlazaId);
						//this.setMessage("Lane Tx Id : "+laneTxId+" Entry Plaza Id : "+entryPlazaId+" Exit Plaza Id : "+exitPlazaId);
						try {
							MapSqlParameterSource paramSource2 = new MapSqlParameterSource();
							paramSource2.addValue("lv_entry_plaza_id", entryPlazaId);
							paramSource2.addValue("lv_exit_plaza_id", exitPlazaId);
							queryForNY12_Condition = LoadJpaQueries.getQueryById("RETRIEVE_LAST_SECTION_ID");
							try {
								highwaySectionVO_List = namedJdbcTemplate.query(queryForNY12_Condition, paramSource2,
										BeanPropertyRowMapper.newInstance(HighwaySectionVO.class));

								if (highwaySectionVO_List != null) {
									highwaySectionVO_List.get(0).toString();
									for (HighwaySectionVO highwaySection:highwaySectionVO_List) {
										logger.info("Section Id : "+highwaySection.getSectionId()+
												" Agency Id : "+highwaySection.getAgencyId()+
												" Section Name : "+highwaySection.getSectionName()+
												" Terminal Plaza Id 1 :"+highwaySection.getTerminalPlazaId1()+
												" Terminal Plaza Id 2 :"+highwaySection.getTerminalPlazaId2()+
												" Time Limit Min : "+highwaySection.getTimeLimitMin()+
												" Matching Enabled  : "+highwaySection.getMatchingEnabled()+
												" xmatching Enabled: "+highwaySection.getXmatchingEnabled());
									}


									sectionId = String.valueOf(highwaySectionVO_List.get(0).getSectionId());
									logger.info("Section Id : "+sectionId);
									//this.setMessage("Section Id : "+sectionId);

									if (firstTimeFlag) {
										prevLaneTxId = laneTxId;
										lastSectionId = sectionId;
										firstTimeFlag = false;
									} else {
										if (laneTxId != prevLaneTxId) {
											lastSectionTransactionVO_List = getAllTransactionsOfLastSegment(lastSectionId);    // Query Q9
											for (LastSectionTransactionVO object:lastSectionTransactionVO_List) {
												lastFinalSectionTransactionVO_List.add(object);
											}
											pendingFlag = false;
										}
									}
									lastSectionId = sectionId;
									prevLaneTxId = laneTxId;
								} else {
									sectionId = null;
								}
							} catch (EmptyResultDataAccessException empty) {
								logger.error("no data in highway section  table for finding last section Id. Values of Entry Plaza Id : "+entryPlazaId+" Exit Plaza Id : "+exitPlazaId);
								//this.setMessage("no data in highway section  table for finding last section Id. Values of Entry Plaza Id : "+entryPlazaId+" Exit Plaza Id : "+exitPlazaId);
								continue;
							}catch (Exception ex) 
							{
								ex.printStackTrace();
								logger.error("Exception occured...while retrieving highway section table."+" Values of Entry Plaza Id : "+entryPlazaId+" Exit Plaza Id : "+exitPlazaId+ " Exception message : "+ex.getMessage()+" Cause : "+ex.getCause());	
								//this.setMessage("Exception occured...while retrieving highway section table."+" Values of Entry Plaza Id : "+entryPlazaId+" Exit Plaza Id : "+exitPlazaId);
								continue;
							}
						} catch (Exception e) {
							logger.error("Exception occured for Lane Tx id : "+laneTxId);
							//this.setMessage("Exception occured for Lane Tx id : "+laneTxId);
							continue;
						}
					}
					logger.info("laneTxId : "+laneTxId);
				}
				if (pendingFlag) {
					sectionId = lastSectionId;
					lastSectionTransactionVO_List = getAllTransactionsOfLastSegment(sectionId);   // Query Q9
					if (lastSectionTransactionVO_List != null) {
						for (LastSectionTransactionVO object:lastSectionTransactionVO_List) {
							lastFinalSectionTransactionVO_List.add(object);
						}
					}
				}

			} catch (Exception e) {
				logger.error("Something is wrong {}", e);
			}
			logger.info("Class Name :"+this.getClass()+" Method Name : retriveAllTransactionsPertainingToLastSection - ENDED");


			return lastFinalSectionTransactionVO_List;

		}

		public List<String> retriveAllTransactionsPertainingToLastSectionForDevice(){
			List<DistinctEntryExitPlazaVO> objectList = new ArrayList<>();
			List<LastSectionTransactionVO> lastSectionTransactionVO_List = new ArrayList<>();
			List<LastSectionTransactionVO> lastFinalSectionTransactionVO_List = new ArrayList<>();
			List<String> lastSectionIdList = new ArrayList<>();
			logger.info("Class Name :"+this.getClass()+" Method Name : retriveAllTransactionsPertainingToLastSectionForDevice - STARTED");
			try {
				//this.setMessage("Step  ... Get Distinct Lane Tx Id/Entry Plaza Id /Exit Plaza ");

				String queryForNY12_Condition = LoadJpaQueries.getQueryById("GET_DISTINCT_ENTRY_AND_EXIT_PLAZA_IDS_FOR_LAST_SECTION");
				String distinctLaneTxId = null;	
				String laneTxId =null;
				String prevLaneTxId = null;
				String entryPlazaId,exitPlazaId = null;
				String sectionId = null;
				List<HighwaySectionVO> highwaySectionVO_List = new ArrayList<>();
				boolean pendingFlag = true;
				DistinctEntryExitPlazaVO distinctEntryExitPlazaVO;

				MapSqlParameterSource paramSource = new MapSqlParameterSource();
				objectList = namedJdbcTemplate.query(queryForNY12_Condition,paramSource,
						BeanPropertyRowMapper.newInstance(DistinctEntryExitPlazaVO.class));
				for (DistinctEntryExitPlazaVO object: objectList) {
					logger.info(" Entry Plaza id :"+object.getEntryPlazaId()+" Exit Plaza Id : "+object.getPlazaId());
				}
				if (objectList != null) {
					Iterator<DistinctEntryExitPlazaVO> itr =  objectList.iterator();
					while (itr.hasNext()) {
						distinctEntryExitPlazaVO = (DistinctEntryExitPlazaVO) itr.next();
						entryPlazaId = distinctEntryExitPlazaVO.getEntryPlazaId();
						exitPlazaId  = distinctEntryExitPlazaVO.getPlazaId();
						logger.info("Retrieving section id for Lane Tx Id : "+laneTxId+" Entry Plaza Id : "+entryPlazaId+" Exit Plaza Id : "+exitPlazaId);
						//this.setMessage(" Entry Plaza Id : "+entryPlazaId+" Exit Plaza Id : "+exitPlazaId);
						try {
							sectionId = findLastSectionId(entryPlazaId,exitPlazaId);
							lastSectionIdList.add(sectionId);

						} catch (Exception e) {
							logger.error("Exception occured for Lane Tx id : "+laneTxId);
							//this.setMessage("Exception occured for Lane Tx id : "+laneTxId);
							continue;
						}
					}
				}

			} catch (Exception e) {
				logger.error("Something is wrong {}", e);
			}
			logger.info("Class Name :"+this.getClass()+" Method Name : retriveAllTransactionsPertainingToLastSectionForDevice - ENDED");
			return lastSectionIdList;

		}
		
		//////not being called
		public List<String> retriveAllTransactionsPertainingToLastSectionForDeviceMultipleOccurenceOfSameLaneTxId(){
			List<DistinctLaneTxPlazaVO> objectList = new ArrayList<>();
			List<LastSectionTransactionVO> lastSectionTransactionVO_List = new ArrayList<>();
			List<LastSectionTransactionVO> lastFinalSectionTransactionVO_List = new ArrayList<>();
			List<String> lastSectionIdList = new ArrayList<>();
			logger.info("Class Name :"+this.getClass()+" Method Name : retriveAllTransactionsPertainingToLastSectionForDevice - STARTED");
			try {
				//this.setMessage("Step  ... Get Distinct Lane Tx Id/Entry Plaza Id /Exit Plaza ");

				String queryForNY12_Condition = LoadJpaQueries.getQueryById("GET_DISTINCT_LANE_TX_ID_ENTRY_AND_EXIT_PLAZA_IDS");
				String distinctLaneTxId = null;	
				String laneTxId =null;
				String prevLaneTxId = null;
				String entryPlazaId,exitPlazaId = null;
				String sectionId = null;
				String lastSectionId = null;
				boolean firstTimeFlag = true;
				List<HighwaySectionVO> highwaySectionVO_List = new ArrayList<>();
				boolean pendingFlag = true;
				DistinctLaneTxPlazaVO distinctLaneTxPlazaVO;

				MapSqlParameterSource paramSource = new MapSqlParameterSource();
				objectList = namedJdbcTemplate.query(queryForNY12_Condition,paramSource,
						BeanPropertyRowMapper.newInstance(DistinctLaneTxPlazaVO.class));
				for (DistinctLaneTxPlazaVO object: objectList) {
					logger.info("Lane Tx Id : "+object.getLaneTxId()+" Entry Plaza id :"+object.getEntryPlazaId()+" Exit Plaza Id : "+object.getPlazaId());
				}
				if (objectList != null) {
					Iterator<DistinctLaneTxPlazaVO> itr =  objectList.iterator();
					while (itr.hasNext()) {
						pendingFlag = true;

						distinctLaneTxPlazaVO = (DistinctLaneTxPlazaVO) itr.next();
						laneTxId = distinctLaneTxPlazaVO.getLaneTxId();
						entryPlazaId = distinctLaneTxPlazaVO.getEntryPlazaId();
						exitPlazaId  = distinctLaneTxPlazaVO.getPlazaId();
						logger.info("Retrieving section id for Lane Tx Id : "+laneTxId+" Entry Plaza Id : "+entryPlazaId+" Exit Plaza Id : "+exitPlazaId);
						//this.setMessage("Lane Tx Id : "+laneTxId+" Entry Plaza Id : "+entryPlazaId+" Exit Plaza Id : "+exitPlazaId);
						try {
							MapSqlParameterSource paramSource2 = new MapSqlParameterSource();
							paramSource2.addValue("lv_entry_plaza_id", entryPlazaId);
							paramSource2.addValue("lv_exit_plaza_id", exitPlazaId);
							queryForNY12_Condition = LoadJpaQueries.getQueryById("RETRIEVE_LAST_SECTION_ID");
							try {
								highwaySectionVO_List = namedJdbcTemplate.query(queryForNY12_Condition, paramSource2,
										BeanPropertyRowMapper.newInstance(HighwaySectionVO.class));

								if (highwaySectionVO_List != null) {
									sectionId = String.valueOf(highwaySectionVO_List.get(0).getSectionId());
									logger.info("Section Id : "+sectionId);
									//this.setMessage("Section Id : "+sectionId);
									if (firstTimeFlag) {
										prevLaneTxId = laneTxId;		
										lastSectionId = sectionId;
										firstTimeFlag = false;

									} else {
										if (laneTxId != prevLaneTxId) {
											lastSectionIdList.add(lastSectionId);
											pendingFlag = false;
										}
									}
									lastSectionId = sectionId;
									prevLaneTxId = laneTxId;

								} else {
									sectionId = null;
								}
							} catch (EmptyResultDataAccessException empty) {
								logger.error("no data in highway section  table for finding last section Id. Values of Entry Plaza Id : "+entryPlazaId+" Exit Plaza Id : "+exitPlazaId);
								//this.setMessage("no data in highway section  table for finding last section Id. Values of Entry Plaza Id : "+entryPlazaId+" Exit Plaza Id : "+exitPlazaId);
								continue;
							} catch (Exception ex) 
							{
								logger.error("Exception occured...while retrieving highway section table."+" Values of Entry Plaza Id : "+entryPlazaId+" Exit Plaza Id : "+exitPlazaId);	
								//this.setMessage("Exception occured...while retrieving highway section table."+" Values of Entry Plaza Id : "+entryPlazaId+" Exit Plaza Id : "+exitPlazaId);
								continue;
							}
						} catch (Exception e) {
							logger.error("Exception occured for Lane Tx id : "+distinctLaneTxId);
							//this.setMessage("Exception occured for Lane Tx id : "+distinctLaneTxId);
							continue;
						}
					}
				}
				if (pendingFlag) {
					lastSectionIdList.add(sectionId);
				}

			} catch (Exception e) {
				logger.error("Something is wrong {}", e);
			}
			logger.info("Class Name :"+this.getClass()+" Method Name : retriveAllTransactionsPertainingToLastSectionForDevice - ENDED");


			return lastSectionIdList;

		}

	    /////not being called
		public String findLastSectionId(String laneTxId)
		{
			logger.info("Class Name :"+this.getClass()+" Method Name : findLastSectionId - STARTED");
			logger.info("Parameter laneTxId "+laneTxId);
			if (laneTxId == null) {
				logger.info("LaneTxId passed tp findLastSectionId is null");
				return "";
			}
			String[] entryPlazaId = null; 
			String[] exitPlazaId = null;

			try {
				List<HighwaySectionVO> highwaySectionVO_List = new ArrayList<>();

				MapSqlParameterSource paramSource = new MapSqlParameterSource();
				paramSource.addValue("lv_entry_plaza_id", entryPlazaId);
				paramSource.addValue("lv_exit_plaza_id", exitPlazaId);

				//this.setMessage("Step  ... Does plaza Exists on Same Segment");

				String queryForNY12_Condition = LoadJpaQueries.getQueryById("DOES_PLAZA_EXISTS_ON_SAME_SEGMENT");
				try {
					highwaySectionVO_List = namedJdbcTemplate.query(queryForNY12_Condition, paramSource,
							BeanPropertyRowMapper.newInstance(HighwaySectionVO.class));

					if (highwaySectionVO_List != null) {
						return "";
					} else {
						return "";
					}
				} catch (EmptyResultDataAccessException empty) {
					logger.info("no data in highway section  table");

				}		} catch (Exception e) {
					logger.error("Something is wrong {}", e);
				}

			logger.info("Class Name :"+this.getClass()+" Method Name : findLastSectionId - ENDED");
			return "";	
		}




		/*
		public List<LastSectionTransactionVO> getAllTransactionsOfLastSegment(String lastSectionId)
		{
			logger.info("Class Name :"+this.getClass()+" Method Name : getAllTransactionsOfLastSegment - STARTED");
			List<LastSectionTransactionVO> transactionVO_List = new ArrayList<>();
			try {
				MapSqlParameterSource paramSource = new MapSqlParameterSource();
				paramSource.addValue("lc_last_section_id", lastSectionId);

				String queryForNY12_Condition = LoadJpaQueries.getQueryById("QUERY_Q9_SELECT_PENDING_QUEUE_TXNS_OF_LAST_SECTION");
				logger.info("Query to get records : {}", queryForNY12_Condition);
				try {
					transactionVO_List = namedJdbcTemplate.query(queryForNY12_Condition, paramSource,
							BeanPropertyRowMapper.newInstance(LastSectionTransactionVO.class));
				} catch (EmptyResultDataAccessException empty) {
					logger.info("no data in TPMS table");

				}
			} catch (Exception e) {
				logger.error("Something is wrong {}", e);
			}
			logger.info("Class Name :"+this.getClass()+" Method Name : getAllTransactionsOfLastSegment - ENDED");

			return transactionVO_List;	
		}
		 */

		
		public boolean stitchingTransactions()
		{
			logger.info("Class Name :"+this.getClass()+" Method Name : stitchingTransactions - STARTED");
			try {

				MapSqlParameterSource paramSource = new MapSqlParameterSource();

				String queryForNY12_Condition = LoadJpaQueries.getQueryById("GET_RECORDS_FOR_PROCESSING");

				logger.info("Query to get records : {}", queryForNY12_Condition);

				try {
					 //old saved in listOfRecords
					//this.listOfRecords = namedJdbcTemplate.query(queryForNY12_Condition, paramSource,
							//BeanPropertyRowMapper.newInstance(NY12_ProcessDto.class));
					namedJdbcTemplate.query(queryForNY12_Condition, paramSource,BeanPropertyRowMapper.newInstance(NY12_ProcessDto.class));


				} catch (EmptyResultDataAccessException empty) {
					logger.info("no data in TPMS table");
				}
			} catch (Exception e) {
				logger.error("Something is wrong {}", e);
			}
			logger.info("Class Name :"+this.getClass()+" Method Name : stitchingTransactions  - ENDED");

			return true;	
		}

		

		
/************/
	
	
	
	
	
	
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
