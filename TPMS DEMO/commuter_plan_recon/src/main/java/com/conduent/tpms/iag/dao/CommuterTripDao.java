package com.conduent.tpms.iag.dao;


import java.time.LocalDate;

import java.util.List;

import com.conduent.tpms.iag.dto.PaymentDTO;
import com.conduent.tpms.iag.dto.PostUsagePlanDetailDto;
import com.conduent.tpms.iag.dto.TProcessParamterDto;
import com.conduent.tpms.iag.dto.TransactionDTO;
import com.conduent.tpms.iag.model.BatchUserInfo;
import com.conduent.tpms.iag.model.TTripHistory;

/**
 * Interface for DAO operations
 * 
 * @author taniyan
 *
 */

public interface CommuterTripDao {
	
	public List<TTripHistory> getTripHistoryDetails();
	
	public void UpdateTTripHistoryforTripLeftZero(int tripLeft , int apdId);
	
	public void UpdateTTripHistory(int apdId, TTripHistory tripInfo);
	
	public String getPlanType(int etc_account_id);
	
	public String getPlanName(Integer planType);
	
	public String getAccountType(int etc_account_id);
	
	public List<PostUsagePlanDetailDto> getPostUsagePlanDetails();
	
	public Integer getMapAgencyID(String planName);
	
	public Integer getAccountAgencyID(int etc_account_id);
	
	public boolean checkRecordExitsin_T_PAYMENT(int etc_account_id);
	
	public boolean checkRecordExitsin_T_TRANSACTION(int etc_account_id, int month);

	public List<TProcessParamterDto> getProcessParamtersList();

	
	public String getnumdays();
	
   public String getAccountsuspended(int etc_act_id);
   
   public void UpdateTTripHistory(int apdId, double amt);
   
   public String getAccountStatus(int etc_act_id);
   
   public String getPlanRenew(int id);
   
   public List<TTripHistory> checktripfornextperiod(int ect_act_id, int month);

   public void inserttriphistorynextperiod(TTripHistory nexttrip);
   
   //public void inserttriphistorynextperiod(int apd_id);
   
   public PostUsagePlanDetailDto getPostUsagePlanDetailsAccToPlan(String planname);
   
   public LocalDate getLatestTripfromApdId(int apd_id);
   
   public int getplandays(int plantype);
   
   public int getmintrips(int plantype);

   public List<TTripHistory> getTripForPostUsage();
}
