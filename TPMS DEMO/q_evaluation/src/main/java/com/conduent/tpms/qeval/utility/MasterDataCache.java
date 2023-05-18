package com.conduent.tpms.qeval.utility;

import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.conduent.tpms.qeval.BatchDao.BatchDao;
import com.conduent.tpms.qeval.BatchModel.EMailHouseDetails;
import com.conduent.tpms.qeval.BatchModel.IssueTypeMapping;
import com.conduent.tpms.qeval.BatchModel.QvalPolicyData;
import com.conduent.tpms.qeval.BatchModel.TPricingData;
import com.conduent.tpms.qeval.BatchModel.VAccountPlanDatail;
import com.conduent.tpms.qeval.dao.TAgencyDao;
import com.conduent.tpms.qeval.dao.TCodesDao;
import com.conduent.tpms.qeval.dao.TProcessParamDao;
import com.conduent.tpms.qeval.dto.AgencyInfoVO;
import com.conduent.tpms.qeval.dto.TCodesVO;
import com.conduent.tpms.qeval.model.TProcessParam;

@Component
public class MasterDataCache {

	private static final Logger log = LoggerFactory.getLogger(MasterDataCache.class);

	@Autowired
	private TCodesDao tCodeDao;

	@Autowired
	private TAgencyDao tAgencyDao;
	
	@Autowired
	private TProcessParamDao tProcessParamDao;
	
	@Autowired
	private BatchDao batchDao;
	
	//Data list
	protected List<TCodesVO> tcodesList;

	protected List<AgencyInfoVO> tagencyList;
	
	protected List<TProcessParam> tprocessParamList;
	
	protected List<QvalPolicyData> qValPolicyData;
	
	protected List<IssueTypeMapping> issueTypeMappingData;
	
	protected List<TPricingData> tPricingData;
	
	protected List<VAccountPlanDatail> vAccountPlan;

	protected List<EMailHouseDetails> mailHouseData;
	
	
	public VAccountPlanDatail findByAccountID(Long accountID) {
		
		if(accountID==null)
		{
		return null;
		}
		
		//Iterate vAccountPlan and find one VAccountPlanDatail and return.
		
		Optional<VAccountPlanDatail> vAccountPlanDatail = vAccountPlan.stream().filter(item -> item.getEtcAccountId().equals(accountID)).findFirst();
		if(vAccountPlanDatail.isPresent())
		{
		return vAccountPlanDatail.get();
		}
		return null;
		}
		// it return policy rage data QvalPolicyData
	public QvalPolicyData getPolicyDetail(Integer agencyId,String accountType) {
		
		if(agencyId==null)
		{
		return null;
		}
		
		Optional<QvalPolicyData> QvalPolicyDetail = qValPolicyData.stream().filter(item -> item.getAgencyId().equals(agencyId) && item.getAccountType().equals(accountType)).findFirst();
		if(QvalPolicyDetail.isPresent())
		{
		return QvalPolicyDetail.get();
		}
		return null;
		}
	
	@PostConstruct
	public void init() {
		try {
		//	tagencyList = tAgencyDao.getAgencyInfoList();
		//	log.info("tagencyList: {}",tagencyList);
		//	tcodesList = tCodeDao.getTCodes();
		//	setTcodesList(tcodesList);
		//	log.info("tcodesList: {}",tcodesList);
		//	tprocessParamList = tProcessParamDao.getProcessParamList();
		//	log.info("tprocessParamList: {}",tprocessParamList);
			
			//qValPolicyData=batchDao.loadQVALPolicyData();
			//setqValPolicyData(qValPolicyData);
			//log.info("qValPolicyData: {}",qValPolicyData);
			
		//	mailHouseData=batchDao.loadMailHouseData();
		//	setMailHouseData(mailHouseData);
		//	log.info("loadMailHouseData: {}",mailHouseData);
			
	/*		issueTypeMappingData=batchDao.issueTypeMappingData();
			setIssueTypeMappingData(issueTypeMappingData);	
			log.info("requiredPolicyData: {}",issueTypeMappingData);
			
			tPricingData=batchDao.tPricingData();
			settPricingData(tPricingData);
			//log.info("tPricingData: {}",tPricingData);
			
			vAccountPlan=batchDao.vAccountPlanDatailData();
			setvAccountPlan(vAccountPlan);
			//log.info("vAccountPlan: {}",vAccountPlan);
			*/
			

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}


	public List<EMailHouseDetails> getMailHouseData() {
		return mailHouseData;
	}
	public void setMailHouseData(List<EMailHouseDetails> mailHouseData) {
		this.mailHouseData = mailHouseData;
	}
	public List<TCodesVO> getTcodesList() {
		return tcodesList;
	}


	public void setTcodesList(List<TCodesVO> tcodesList) {
		this.tcodesList = tcodesList;
	}


	public List<QvalPolicyData> getqValPolicyData() {
		return qValPolicyData;
	}


	public void setqValPolicyData(List<QvalPolicyData> qValPolicyData) {
		this.qValPolicyData = qValPolicyData;
	}


	


	public List<IssueTypeMapping> getIssueTypeMappingData() {
		return issueTypeMappingData;
	}


	public void setIssueTypeMappingData(List<IssueTypeMapping> issueTypeMappingData) {
		this.issueTypeMappingData = issueTypeMappingData;
	}


	public List<TPricingData> gettPricingData() {
		return tPricingData;
	}


	public void settPricingData(List<TPricingData> tPricingData) {
		this.tPricingData = tPricingData;
	}


	public List<VAccountPlanDatail> getvAccountPlan() {
		return vAccountPlan;
	}


	public void setvAccountPlan(List<VAccountPlanDatail> vAccountPlan) {
		this.vAccountPlan = vAccountPlan;
	}
	

}
