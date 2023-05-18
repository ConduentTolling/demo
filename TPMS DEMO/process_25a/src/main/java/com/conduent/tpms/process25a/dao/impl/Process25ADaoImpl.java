package com.conduent.tpms.process25a.dao.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import com.conduent.tpms.process25a.config.LoadJpaQueries;
import com.conduent.tpms.process25a.dao.Process25ADao;
import com.conduent.tpms.process25a.model.Pending25ATransactions;

@Component(value="nY25_ProcessDao")
public class Process25ADaoImpl implements Process25ADao{

	private static Logger logger = LoggerFactory.getLogger(Process25ADaoImpl.class);
	
	@Autowired
	private NamedParameterJdbcTemplate namedJdbcTemplate;

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	
	@Override
	public List<Pending25ATransactions> getPending25ATolls(Long entryPlazaId, Long exitPlazaId) {
		// TODO Auto-generated method stub
		
		List<Pending25ATransactions> objectList = new ArrayList<>();
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		
		paramSource.addValue("ENTRY_PLAZA_ID", entryPlazaId);
		paramSource.addValue("PLAZA_ID", exitPlazaId);
		logger.info("Process25ADaoImpl.getPending25ATolls entryPlazaId: {} :: exitPlazaId:{}  ",entryPlazaId,exitPlazaId);
		String queryForNY25_Condition = LoadJpaQueries.getQueryById("GET_25A_TRANSACTIONS_BY_PLAZA");
		try {
			objectList = namedJdbcTemplate.query(queryForNY25_Condition, paramSource,
					BeanPropertyRowMapper.newInstance(Pending25ATransactions.class));

		} catch (EmptyResultDataAccessException empty) {
			logger.info("no data in Pending Queue Table");

		}
		logger.info("objectList.size:{}  ",objectList.size());
		return objectList;
		
	}

	
	@Override
	public List<Pending25ATransactions> getPending25ATollsExit26(Long entryPlazaId, Long exitPlazaId) {
		// TODO Auto-generated method stub
		
		List<Pending25ATransactions> objectList = new ArrayList<>();
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		
		paramSource.addValue("ENTRY_PLAZA_ID", entryPlazaId);
		paramSource.addValue("PLAZA_ID", exitPlazaId);
		logger.info("Process25ADaoImpl.getPending25ATollsExit26 entryPlazaId: {} :: exitPlazaId:{}  ",entryPlazaId,exitPlazaId);
		String queryForNY25_Condition = LoadJpaQueries.getQueryById("GET_25A_TRANSACTIONS_BY_PLAZA_EXIT_26");
		try {
			objectList = namedJdbcTemplate.query(queryForNY25_Condition, paramSource,
					BeanPropertyRowMapper.newInstance(Pending25ATransactions.class));

		} catch (EmptyResultDataAccessException empty) {
			logger.info("no data in Pending Queue Table ");

		}
		logger.info("objectList.size:{}  ",objectList.size());
		return objectList;
		
	}
	
	@Override
	public List<Pending25ATransactions> getAllRelatedTransactions(Long entryPlazaId, Long exitPlazaId, String tableName, String deviceNo, String plateNo) {
		// TODO Auto-generated method stub
		
		List<Pending25ATransactions> objectList = new ArrayList<>();
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		
		paramSource.addValue("ENTRY_PLAZA_ID", entryPlazaId);
		paramSource.addValue("PLAZA_ID", exitPlazaId);
		paramSource.addValue("DEVICE_NO", deviceNo);
		paramSource.addValue("PLATE_NO", plateNo);
		logger.info("Process25ADaoImpl.getAllRelatedTransactions entryPlazaId: {} :: exitPlazaId:{} ::tableName{} ",entryPlazaId,exitPlazaId,tableName);
		String queryForNY25_Condition = LoadJpaQueries.getQueryById("GET_RELATED_TRANSACTIONS_BY_PLAZA_1");
		queryForNY25_Condition = queryForNY25_Condition.concat(tableName);
		queryForNY25_Condition = queryForNY25_Condition.concat(LoadJpaQueries.getQueryById("GET_RELATED_TRANSACTIONS_BY_PLAZA_2"));
		logger.info("Process25ADaoImpl.getAllRelatedTransactions queryForNY25_Condition:{}",queryForNY25_Condition);
		try {
			objectList = namedJdbcTemplate.query(queryForNY25_Condition, paramSource,
					BeanPropertyRowMapper.newInstance(Pending25ATransactions.class));

		} catch (EmptyResultDataAccessException empty) {
			logger.info("no data in  {}",tableName);

		}
		logger.info("objectList.size:{}  ",objectList.size());
		return objectList;
		
	}
	
	
	@Override
	public List<Pending25ATransactions> getAllRelatedTransactionsTAccountToll(Long entryPlazaId, Long exitPlazaId, String tableName, Long agencyId, String deviceNo, String plateNo) {
		// TODO Auto-generated method stub
		
		List<Pending25ATransactions> objectList = new ArrayList<>();
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		
		paramSource.addValue("ENTRY_PLAZA_ID", entryPlazaId);
		paramSource.addValue("PLAZA_ID", exitPlazaId);
		paramSource.addValue("DEVICE_NO", deviceNo);
		paramSource.addValue("PLATE_NO", plateNo);
		paramSource.addValue("AGENCY_ID", agencyId);
		
		logger.info("Process25ADaoImpl.getAllRelatedTransactionsTAccountToll entryPlazaId: {} :: exitPlazaId:{} ::tableName{} ::agencyId{} "
				,entryPlazaId,exitPlazaId,tableName,agencyId);
		
		String queryForNY25_Condition = LoadJpaQueries.getQueryById("GET_RELATED_TRANSACTIONS_BY_PLAZA_1");
		queryForNY25_Condition = queryForNY25_Condition.concat(tableName);
		queryForNY25_Condition = queryForNY25_Condition.concat(LoadJpaQueries.getQueryById("GET_RELATED_TRANSACTIONS_BY_PLAZA_3"));
		
		logger.info("Process25ADaoImpl.getAllRelatedTransactionsTAccountToll queryForNY25_Condition:{}",queryForNY25_Condition);
		try {
			objectList = namedJdbcTemplate.query(queryForNY25_Condition, paramSource,
					BeanPropertyRowMapper.newInstance(Pending25ATransactions.class));

		} catch (EmptyResultDataAccessException empty) {
			logger.info("no data in  {}",tableName);

		}
		logger.info("objectList.size:{}  ",objectList.size());
		return objectList;
		
	}


	@Override
	public int getCompleteSeq() {
		
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		String queryForNY25_Condition = LoadJpaQueries.getQueryById("GET_25A_COMPLETE_SEQ");
		logger.info("Process25ADaoImpl.getCompleteSeq queryForNY25_Condition:{}",queryForNY25_Condition);
		int sequenceNumber = 0;

		List<Map<String,Object>> resultMap = jdbcTemplate.queryForList(queryForNY25_Condition);
		if (resultMap != null) {
			Iterator itr =  resultMap.iterator();
			while (itr.hasNext()) {
				Map map = (Map) itr.next();
				sequenceNumber = Integer.parseInt(map.get("NEXTVAL").toString());
				logger.info("sequence Number from query is : "+sequenceNumber);
				break;
			}
		}

		logger.info("Class Name :"+this.getClass()+" Method Name : getNextSequenceNumber - ENDED");
		return sequenceNumber;    //currentSequenceNumber;
	}


	@Override
	public int updatePendingQueue(String laneTxId, int refNo) {
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		
		paramSource.addValue("lv_complete_ref_no", refNo);
		paramSource.addValue("lv_lane_tx_id", laneTxId);
		
		String queryForNY25_Condition = LoadJpaQueries.getQueryById("UPDATE_25A_PENDING_QUEUE");
		logger.info("Process25ADaoImpl.getCompleteSeq updatePendingQueue:{}",queryForNY25_Condition);
		int updatedRows = 0;
		try {
			updatedRows = namedJdbcTemplate.update(queryForNY25_Condition, paramSource);

		} catch (EmptyResultDataAccessException empty) {
			logger.info("no data Updated ");

		}
		logger.info("updatedRows:{}  ",updatedRows);
		return updatedRows;
	}


	@Override
	public List<Pending25ATransactions> getMatchedRecords(String laneTxId, int seqNo) {

		// TODO Auto-generated method stub
		
		List<Pending25ATransactions> objectList = new ArrayList<>();
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		
		paramSource.addValue("lv_complete_ref_no", laneTxId);
		paramSource.addValue("lv_lane_tx_id", seqNo);
		logger.info("Process25ADaoImpl.getMatchedRecords laneTxId: {} :: seqNo:{}  ",laneTxId,seqNo);
		String queryForNY25_Condition = LoadJpaQueries.getQueryById("GET_MATCHED_25A_TRANSACTIONS");
		try {
			objectList = namedJdbcTemplate.query(queryForNY25_Condition, paramSource,
					BeanPropertyRowMapper.newInstance(Pending25ATransactions.class));

		} catch (EmptyResultDataAccessException empty) {
			logger.info("no data in Pending Queue Table");

		}
		logger.info("objectList.size:{}  ",objectList.size());
		return objectList;
		
	
	}


	@Override
	public int updateMatchedRecords(String laneTxId, int seqNo) {

		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		
		paramSource.addValue("match_ref_no", seqNo);
		paramSource.addValue("lane_tx_id", laneTxId);
		
		String queryForNY25_Condition = LoadJpaQueries.getQueryById("UPDATE_25A_MATCHED_RECORDS");
		logger.info("Process25ADaoImpl.getCompleteSeq updatePendingQueue:{}",queryForNY25_Condition);
		int updatedRows = 0;
		try {
			updatedRows = namedJdbcTemplate.update(queryForNY25_Condition, paramSource);

		} catch (EmptyResultDataAccessException empty) {
			logger.info("no data Updated ");

		}
		logger.info("updatedRows:{}  ",updatedRows);
		return updatedRows;
	
		
	}


	@Override
	public void insertComplleteTransaction(Pending25ATransactions transaction) {
		logger.info("Inserting transaction details into T_25A_COMPLETE_TXN table..");

		try {
			String query = LoadJpaQueries.getQueryById("INSERT_T_25A_COMPLETE_TXN");
			MapSqlParameterSource paramSource = new MapSqlParameterSource();
			paramSource.addValue("ETC_ACCOUNT_ID", transaction.getEtcAccountId());
			paramSource.addValue("DEVICE_NO", transaction.getDeviceNo());
			paramSource.addValue("PLATE_NUMBER", transaction.getPlateNo());
			paramSource.addValue("PLATE_STATE", transaction.getPlateState());
			paramSource.addValue("LANE_TX_ID", transaction.getLaneTxId());
			paramSource.addValue("ENTRY_PLAZA_ID", transaction.getEntryPlazaId());
			paramSource.addValue("PLAZA_ID", transaction.getPlazaId());
			paramSource.addValue("ENTRY_TIMESTAMP", transaction.getEntryTimeStamp());
			paramSource.addValue("IS_RECIPROCITY_TXN", transaction.isReciprocityTxn());
			paramSource.addValue("TX_TIMESTAMP", transaction.getTxnTimeStamp());
			paramSource.addValue("UPDATE_TS", transaction.getTxnTimeStamp());
			//paramSource.addValue(ICTXConstants.ATP_FILE_ID, transaction.getWaitDays());
			paramSource.addValue("MATCHED_TX_EXTERN_REF_NO", transaction.getMatchRefNo());
			
			int id = namedJdbcTemplate.update(query, paramSource);

			logger.info("{} rows inserted into INSERT_T_25A_COMPLETE_TXN table", id);
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}

		
		
		@Override
		public void deletePendingTransaction(Pending25ATransactions transaction) {
			logger.info("deleting transaction details into t_25a_pending_queue table..");

			try {
				String query = LoadJpaQueries.getQueryById("DELETE_T_25A_PENDING_TXN");
				MapSqlParameterSource paramSource = new MapSqlParameterSource();
				paramSource.addValue("LANE_TX_ID", transaction.getLaneTxId());
				
				int id = namedJdbcTemplate.update(query, paramSource);

				logger.info("{} rows deleted from INSERT_T_25A_COMPLETE_TXN table", id);
				
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (DataAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	}
	
}
