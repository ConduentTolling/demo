package com.conduent.tpms.qeval.BatchDao;

import java.util.List;
import java.util.Optional;

import com.conduent.tpms.qeval.BatchModel.AccountInfo;
import com.conduent.tpms.qeval.BatchModel.EMailHouseDetails;
import com.conduent.tpms.qeval.BatchModel.IssueTypeMapping;
import com.conduent.tpms.qeval.BatchModel.MailHouseDetails;
import com.conduent.tpms.qeval.BatchModel.QvalPolicyData;
import com.conduent.tpms.qeval.BatchModel.StatisticsData;
import com.conduent.tpms.qeval.BatchModel.TPricingData;
import com.conduent.tpms.qeval.BatchModel.ThresholdDTO;
import com.conduent.tpms.qeval.BatchModel.VAccountPlanDatail;

public interface BatchDao {
	
	public Double usageAmount(AccountInfo account);

	public void insertStmtRecord(List<? extends AccountInfo> items);

	public void updateStmtRunDate();

	public void updateQvalStartEndDate(List<? extends AccountInfo> items);

	public void updateRebillAmount(List<? extends AccountInfo> items);

	public void mailDetail(AccountInfo acf);

	public List<QvalPolicyData>  loadQVALPolicyData();
//	public List<MailHouse> loadMailHouseData();
	public EMailHouseDetails loadMailHouseData(AccountInfo acf);
	public List<IssueTypeMapping> issueTypeMappingData();

	public List<VAccountPlanDatail> vAccountPlanDatailData();

	public List<TPricingData> tPricingData();

	public void insertStatisticData(StatisticsData statisticsData);

	public String getParamVal(String paramName, String paramCode, int agencyId);

	public String getParamValForRebill(String paramName, String paramCode, int agencyId);

	public int getParamValForFrequency(String paramName, String paramCode, int agencyId);


	public ThresholdDTO findBy(String accountType, String payType, int agencyId);


	public List<String> getPlanDetails(Long etcAccountId);

	public int getUnitPrice(String category, String planName, String accountType, String payType, int agencyId);

	public String getParamValForThreshold(String paramName, String paramCode);

	public String getRoundVal(String paramName, String paramValue, int agencyId);

	public QvalPolicyData getPolicyDataByAccount(Integer agencyId, String accountType);
}
