package com.conduent.tpms.qeval.BatchImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.conduent.tpms.qeval.BatchDao.BatchDao;
import com.conduent.tpms.qeval.BatchModel.AccountInfo;
import com.conduent.tpms.qeval.BatchModel.EMailHouseDetails;
import com.conduent.tpms.qeval.BatchModel.IssueTypeMapping;
import com.conduent.tpms.qeval.BatchModel.QvalPolicyData;
import com.conduent.tpms.qeval.BatchModel.StatisticsData;
import com.conduent.tpms.qeval.BatchModel.TPricingData;
import com.conduent.tpms.qeval.BatchModel.ThresholdDTO;
import com.conduent.tpms.qeval.BatchModel.VAccountPlanDatail;
import com.conduent.tpms.qeval.config.LoadJpaQueries;
import com.conduent.tpms.qeval.constants.Constants;
@Repository
public class BatchDaoImpl implements BatchDao{
	private static final Logger log = LoggerFactory.getLogger(BatchDaoImpl.class);
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Override
	public Double usageAmount(AccountInfo account) {
		String queryRules = LoadJpaQueries.getQueryById("GET_SUM_POSTFAREAMOUNT");
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue(Constants.ETC_ACCOUNT_ID, account.getEtcAccountId());
		paramSource.addValue(Constants.STATUS,Constants.STATUS_VALUE);
		paramSource.addValue(Constants.POSTED_START_DATE,account.getEvalStartDate());
		paramSource.addValue(Constants.POSTED_END_DATE,account.getEvalEndDate());

		//return jdbcTemplate.queryForObject(queryRules,paramSource,Long.class); 
		Double sum=namedParameterJdbcTemplate.queryForObject(queryRules, paramSource, Double.class);
		if(sum==null)
			return 0.0;
		return sum;
	}

	@Override
	public void insertStmtRecord(List<? extends AccountInfo> items) {
		//String queryRules = "INSERT INTO T_STATEMENT_MESSAAGE VALUES (STATEMENT_MESSAGE_TYPE,APPLICABEL_DATE,EXPIRY_DATE,PARAM_VALUE,MESSAGE,UPDATE_TS,MESSAGE1,MESSAGE)"+ " VALUES()";
		String queryRules = LoadJpaQueries.getQueryById("INSERT_INTO_T_STATEMENT_MESSAAGE");

		List<Map<String,Object>> paramSourceList = new ArrayList<>(items.size());

		for(AccountInfo account:items )
		{

			//paramSource.put(Constants.EVAL_START_DATE,account.getEvalStartDate());
			//Long rebill=account.getRebillAmount();
			int status=account.getRecordStatus();
			if(status==1 || status==2)
			{
				Map<String,Object> paramSource = new HashMap<String,Object>();
				paramSource.put(Constants.ETC_ACCOUNT_ID,account.getEtcAccountId());
				paramSource.put(Constants.MESSAGE1,Constants.MESSAGE1_VALUE);
				paramSource.put(Constants.MESSAGE2,"$"+account.getRebillAmount()+" to $"+account.getNewRebillAmount());
				paramSourceList.add(paramSource);
			}
		/*	else if(account.getTollUsage()==0)
			{
				Map<String,Object> paramSource = new HashMap<String,Object>();
				paramSource.put(Constants.ETC_ACCOUNT_ID,account.getEtcAccountId());
				paramSource.put(Constants.MESSAGE1,Constants.MESSAGE1_VALUE);
				paramSource.put(Constants.MESSAGE2,"$"+account.getRebillAmount()+" to $"+account.getNewRebillAmount());
				paramSourceList.add(paramSource);
			}*/
			//else
			//paramSource.put(Constants.MESSAGE2,account.getRebillAmount()+" to $"+account.getRebillAmount());

			//log.info(Arrays.toString(paramSource.entrySet().toArray()));
		}

		Map<String, Object>[] maps = new HashMap[paramSourceList.size()];
		Map<String, Object>[] batchValues = (Map<String, Object>[]) paramSourceList.toArray(maps);
		namedParameterJdbcTemplate.batchUpdate(queryRules, batchValues);	
		System.out.println("Batch excecute successfuly");
		log.info("Record insertion successfull in T_STATEMENT_MESSAAGE");


	}

	@Override
	public void updateStmtRunDate() {
		//String query = "UPDATE T_STMT_RUN_DATE SET LAST_RUNDATE=(SELECT CURRENT_DATE FROM DUAL) WHERE STMT_RUN_ID=401 AND FILE_TYPE='QVAL' ";
		String query = LoadJpaQueries.getQueryById("UPDATE_STMT_RUN_DATE");
		jdbcTemplate.update(query);
		log.info("Last run update successfuly");
	}

	@Override
	public void updateQvalStartEndDate(List<? extends AccountInfo> items) {
		//String query = "UPDATE FPMS.T_FPMS_ACCOUNT_EXT SET EVAL_START_DATE=:sysdate WHERE ETC_ACCOUNT_ID=:ETC_ACCOUNT_ID ";
		String query = LoadJpaQueries.getQueryById("UPDATE_EVAL_START_DATE");

		List<Map<String,Object>> paramSourceList = new ArrayList<>(items.size());

		for(AccountInfo account:items )
		{
			Map<String,Object> paramSource = new HashMap<String,Object>();
			//paramSource.put(Constants.EVAL_START_DATE,account.getEvalStartDate());
			paramSource.put(Constants.ETC_ACCOUNT_ID,account.getEtcAccountId());
			paramSourceList.add(paramSource);
		}
		//@SuppressWarnings("unchecked")
		Map<String, Object>[] maps = new HashMap[paramSourceList.size()];
		Map<String, Object>[] batchValues = (Map<String, Object>[]) paramSourceList.toArray(maps);
		namedParameterJdbcTemplate.batchUpdate(query, batchValues);	
		log.info("Eval Start and End Date Updated successfully");

	}

	@Override
	public void updateRebillAmount(List<? extends AccountInfo> items) {
		
		String query = LoadJpaQueries.getQueryById("UPDATE_REBILL_AMOUNT");

		List<Map<String,Object>> paramSourceList = new ArrayList<>(items.size());

		for(AccountInfo account:items )
		{
			int status=account.getRecordStatus();
			if(status==1 || status==2)
			{
				Map<String,Object> paramSource = new HashMap<String,Object>();
				paramSource.put(Constants.REBILL_AMOUNT,account.getNewRebillAmount());
				paramSource.put(Constants.ETC_ACCOUNT_ID,account.getEtcAccountId());
				paramSourceList.add(paramSource);
			}
			/*if(account.getTollUsage()==0)
			{
				Map<String,Object> paramSource = new HashMap<String,Object>();
				paramSource.put(Constants.REBILL_AMOUNT,account.getNewRebillAmount());
				paramSource.put(Constants.ETC_ACCOUNT_ID,account.getEtcAccountId());
				paramSourceList.add(paramSource);
			}*/
		}
		Map<String, Object>[] maps = new HashMap[paramSourceList.size()];
		Map<String, Object>[] batchValues = (Map<String, Object>[]) paramSourceList.toArray(maps);
		namedParameterJdbcTemplate.batchUpdate(query, batchValues);
		log.info("Rebill Amount Updated successfully in T_FPMS_ACCOUNT");
	}

	@Override
	public void mailDetail(AccountInfo acf) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<QvalPolicyData>  loadQVALPolicyData() {

		String queryRules = LoadJpaQueries.getQueryById("GET_QVAL_POLICY_DATA");
		return jdbcTemplate.query(queryRules,new BeanPropertyRowMapper<QvalPolicyData>(QvalPolicyData.class));	

	}

	@Override
	public List<IssueTypeMapping>  issueTypeMappingData() {
		String queryRules = LoadJpaQueries.getQueryById("GET_ISSUE_TYPE_MAPPING");
		return jdbcTemplate.query(queryRules,new BeanPropertyRowMapper<IssueTypeMapping>(IssueTypeMapping.class));	


	}


	@Override
	public List<VAccountPlanDatail>  vAccountPlanDatailData() {
		String queryRules = LoadJpaQueries.getQueryById("GET_V_ACCOUNT_PLAN_DETAIL");
		return jdbcTemplate.query(queryRules,new BeanPropertyRowMapper<VAccountPlanDatail>(VAccountPlanDatail.class));

	}

	@Override
	public List<TPricingData>  tPricingData() {
		String queryRules = LoadJpaQueries.getQueryById("GET_T_PRICING_INFO");
		return jdbcTemplate.query(queryRules,new BeanPropertyRowMapper<TPricingData>(TPricingData.class));

	}

	@Override
	public void insertStatisticData(StatisticsData statisticsData) {

		String queryRules = LoadJpaQueries.getQueryById("INSERT_INTO_STATISTICS");
		MapSqlParameterSource parameters=new MapSqlParameterSource();
		parameters.addValue(Constants.STMTRUNID, Constants.STMTRUNID_VALUE);
		parameters.addValue(Constants.TOTALRECPROCESSED, statisticsData.getTotalRecProcessed());
		parameters.addValue(Constants.REBILLUPCOUNT, statisticsData.getRebilleUpCount());
		parameters.addValue(Constants.REBILLDOWNCOUNT, statisticsData.getRebilleDownCount());
		parameters.addValue(Constants.SKIPRECCOUNT, statisticsData.getSkipRecCount());
		namedParameterJdbcTemplate.update(queryRules, parameters);	
		log.info("Record insertion successful into FPMS.T_QEVAL_STATISTICS");


	}

	@Override
	public EMailHouseDetails loadMailHouseData(AccountInfo acc) {
		
		String queryRules = LoadJpaQueries.getQueryById("GET_MAIL_DATA");
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue(Constants.ETC_ACCOUNT_ID,acc.getEtcAccountId());
		paramSource.addValue(Constants.ADDRESS_TYPE,Constants.ADDRESS_TYPE_VALUE);
		return namedParameterJdbcTemplate.queryForObject(queryRules, paramSource,BeanPropertyRowMapper.newInstance(EMailHouseDetails.class));
		//return jdbcTemplate.query(queryRules,new BeanPropertyRowMapper<MailHouse>(MailHouse.class));
		// TODO Auto-generated method stub
		//return null;
	}

	@Override
	public String getParamVal(String paramName, String paramCode, int agencyId) {
		String sql = "select PARAM_VALUE from MASTER.t_process_parameters where PARAM_NAME = :paramName and PARAM_CODE =:paramCode  and AGENCY_ID =:agencyId";

		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue("paramName",paramName);
		paramSource.addValue("paramCode",paramCode);
		paramSource.addValue("agencyId",agencyId);
		return namedParameterJdbcTemplate.queryForObject(sql, paramSource,String.class);
		
	
	}

	@Override
	public String getParamValForRebill(String paramName, String paramCode, int agencyId) {
		String sql = "select PARAM_VALUE from MASTER.t_process_parameters where PARAM_NAME = :paramName and PARAM_CODE =:paramCode  and AGENCY_ID =:agencyId";

		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue("paramName",paramName);
		paramSource.addValue("paramCode",paramCode);
		paramSource.addValue("agencyId",agencyId);
		return namedParameterJdbcTemplate.queryForObject(sql, paramSource,String.class);
		
	
	}
	
	@Override
	public int getParamValForFrequency(String paramName, String paramCode, int agencyId) {
		String sql = "select PARAM_VALUE from MASTER.t_process_parameters where PARAM_NAME = :paramName and PARAM_CODE =:paramCode  and AGENCY_ID =:agencyId";

		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue("paramName",paramName);
		paramSource.addValue("paramCode",paramCode);
		paramSource.addValue("agencyId",agencyId);
		return namedParameterJdbcTemplate.queryForObject(sql, paramSource,Integer.class);
		
	
	}

	
	@Override
	public ThresholdDTO findBy(String accountType, String payType, int agencyId) {
		ThresholdDTO value=null;
		try {
			
			//check
		String sql = "SELECT * FROM T_THRESHOLD THD WHERE THD.AGENCY_ID=:agencyId And THD.ACCOUNT_TYPE=:accountType And THD.PAY_TYPE=:payType ";
		
		log.info("fetching the data in threshold values :[{}]");
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue("accountType",accountType);
		paramSource.addValue("payType",payType);
		paramSource.addValue("agencyId",agencyId);
		 value = namedParameterJdbcTemplate.queryForObject(sql, paramSource,
				 BeanPropertyRowMapper.newInstance(ThresholdDTO.class));
		}
		
		catch(Exception e){
			
			}
		return value;
		}

	@Override
	public int getUnitPrice(String category,String subCategory,String accountType,String payType,int agencyId) {
		
		String sql = "select unit_price from t_pricing where CATEGORY = :category and SUB_Category =:subCategory and ACCOUNT_TYPE=:accountType and REBILL_Type=:payType and  REVENUE_AGENCY =:agencyId";

		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue("category",category);
		paramSource.addValue("subCategory",subCategory);
		paramSource.addValue("accountType",accountType);
		paramSource.addValue("payType",payType);
		paramSource.addValue("agencyId",agencyId);
		
		return namedParameterJdbcTemplate.queryForObject(sql, paramSource,Integer.class);
		
		
		
	}

	@Override
	public List<String> getPlanDetails(Long etcAccountId) {
		List<String> value=null;
		String sql = "select plan_type_name from crm.v_account_plan_detail where  ETC_ACCOUNT_ID = :etcAccountId";

		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue("etcAccountId",etcAccountId);
		
		 value = namedParameterJdbcTemplate.queryForList(sql, paramSource,String.class);
		 return value;
		
		
	}

	@Override
	public String getParamValForThreshold(String paramName, String paramCode) {
		String sql = "select PARAM_VALUE from MASTER.t_process_parameters where PARAM_NAME = :paramName and PARAM_CODE =:paramCode";

		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue("paramName",paramName);
		paramSource.addValue("paramCode",paramCode);
		return namedParameterJdbcTemplate.queryForObject(sql, paramSource,String.class);
	}

	@Override
	public String getRoundVal(String paramName, String paramCode, int agencyId) {
		String sql = "select PARAM_VALUE from MASTER.t_process_parameters where PARAM_NAME = :paramName and PARAM_CODE =:paramCode  and AGENCY_ID =:agencyId";

		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue("paramName",paramName);
		paramSource.addValue("paramCode",paramCode);
		paramSource.addValue("agencyId",agencyId);
		return namedParameterJdbcTemplate.queryForObject(sql, paramSource,String.class);
	}

	@Override
	public QvalPolicyData getPolicyDataByAccount(Integer agencyId, String accountType) { 
		String queryRules = "SELECT * FROM fpms.T_QEVAL_POLICY where agency_id=:agencyId and account_type=:accountType";
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue("agencyId",agencyId);
		paramSource.addValue("accountType",accountType);
		return namedParameterJdbcTemplate.queryForObject(queryRules,paramSource,new BeanPropertyRowMapper<QvalPolicyData>(QvalPolicyData.class));
		
	}
	
	/*@Override
	public List<MailHouse> loadMailHouseData() {
		String queryRules = LoadJpaQueries.getQueryById("GET_MAIL_DATA");
		return jdbcTemplate.query(queryRules,new BeanPropertyRowMapper<MailHouse>(MailHouse.class));
		
	}*/

	



}
