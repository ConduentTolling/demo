package com.conduent.tpms.qeval.BatchService;

import java.util.List;
import java.util.Map;

import com.conduent.tpms.qeval.BatchModel.AccountInfo;
import com.conduent.tpms.qeval.BatchModel.StatisticsData;

public interface BatchService {
	public Double getUsageSum(AccountInfo account);
	public void processUpdatedRecords(List<? extends AccountInfo> items, StatisticsData statisticsData) throws Exception;
	
	//public Long calculateVarience(AccountInfo account);
	//load lookup table
	public void checkVarience(AccountInfo account);
	public Double compareThreshHoldAmount(Double Usage);
	public Double calculateRebillAmount(Double Usage);
	public boolean sentMail(AccountInfo acf) throws Exception;
	public void excuteBusinesslogic(AccountInfo acf);
	public void loadMasterData();
	public AccountInfo processAccount(AccountInfo account, Map<String, Object> processContext);
	public Double calculateVarience(AccountInfo account);
	
	
	
	
}

