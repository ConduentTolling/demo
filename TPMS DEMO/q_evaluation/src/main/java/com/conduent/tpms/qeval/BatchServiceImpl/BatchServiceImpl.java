package com.conduent.tpms.qeval.BatchServiceImpl;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.conduent.tpms.qeval.BatchDao.BatchDao;
import com.conduent.tpms.qeval.BatchModel.AccountInfo;
import com.conduent.tpms.qeval.BatchModel.EMailHouseDetails;
import com.conduent.tpms.qeval.BatchModel.IssueTypeMapping;
import com.conduent.tpms.qeval.BatchModel.MailHouseDetails;
import com.conduent.tpms.qeval.BatchModel.QvalPolicyData;
import com.conduent.tpms.qeval.BatchModel.StatisticsData;
import com.conduent.tpms.qeval.BatchModel.TPricingData;
import com.conduent.tpms.qeval.BatchModel.ThresholdDTO;
import com.conduent.tpms.qeval.BatchModel.ThresholdVO;
import com.conduent.tpms.qeval.BatchModel.VAccountPlanDatail;
import com.conduent.tpms.qeval.BatchService.BatchService;
import com.conduent.tpms.qeval.constants.Constants;
import com.conduent.tpms.qeval.model.ConfigVariable;
import com.conduent.tpms.qeval.utility.MasterDataCache;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

@Service
public class BatchServiceImpl implements BatchService {
	private static final Logger log = LoggerFactory.getLogger(BatchServiceImpl.class);

	@Autowired
	private BatchDao batchDao;
	/*
	 * @Autowired MasterDataCache masterCatch;
	 */
	@Autowired
	private RestTemplate restTemplate;
	@Autowired
	ConfigVariable configVariable;

	protected List<QvalPolicyData> qValPolicyData;

	protected List<IssueTypeMapping> issueTypeMappingData;

	protected List<TPricingData> tPricingData;

	protected List<VAccountPlanDatail> vAccountPlan;
	@Autowired
	StatisticsData statisticsData;
	
	//String resoureceUrl = "https://image-qa-tollingbos.services.conduent.com/notifications/sendNotification";
	//String resoureceUrl="http://129.80.14.232/notifications/sendNotification";
	// @Autowired
	// MailHouse mailData;

	// get new rebill amt(){
	// total usage param pass
	// total usage /fix const 90= will per day amnt
	// get processparamforFreq()
	// e.g 30
	// new usage =perday * freq e.g 10 * 30=300
	// return new rebillamt
	// }

	@Override
	public Double getUsageSum(AccountInfo account) {
		Double sum = batchDao.usageAmount(account);
		String roundValue=getRoundUpValue(account);
		if(roundValue.equalsIgnoreCase("UP"))
		{
			sum=Math.ceil(sum);
		}
		else 
		{
			sum=Math.floor(sum);
		}
		log.info("Total Usage of Account  " + account.getEtcAccountId() + " is " + sum);
		log.info("Rebill  Account  " + account.getRebillAmount());
		return sum;
	}

	private String getRoundUpValue(AccountInfo account) {
		String paramName=Constants.QEVAL_ROUND_FREQUENCY;
		String paramValue=Constants.QEVAL_PARAM;
		int agencyId = account.getAgencyId();
		String roundValue=batchDao.getRoundVal(paramName,paramValue,agencyId);
		return roundValue;
	}

	// process parameter
	public String getProcessParameter(AccountInfo account) {
		String paramName = Constants.QEVAL_PROCESS_PARAM;
		String paramCode = Constants.QEVAL_PARAM;
		int agencyId = account.getAgencyId();

		String val = "";
		val = batchDao.getParamVal(paramName, paramCode, agencyId);

		// val p then cal for percentage
		// val A then cal for amount

		return val;

	}

	public Double getNewrebill(AccountInfo account) {

		String paramName = Constants.QEVAL_PROCESS_PARAM_FREQUENCY;
		String paramCode = Constants.QEVAL_PARAM;
		int agencyId = account.getAgencyId();
		double totalUsage = account.getTollUsage();

		double getTotalUsage = totalUsage / Constants.QEVAL_PERIOD;

		int frequency = 0;
		frequency = batchDao.getParamValForFrequency(paramName, paramCode, agencyId);
		double newUsage1 = getTotalUsage * frequency;
		double newUsage=Math.round(newUsage1*100.0)/100.0;
		log.info("New RebillPeriod amount " + newUsage);
		return newUsage;

	}

	// this method for to cal rebill if val is P(percentage)

	public void calculatePeriodVal(AccountInfo account) {

		// log.info("calculating varience ");
		Double newRebillAmount = this.getNewrebill(account);
		account.setCalculatedRebillAmount(newRebillAmount);
	}

	@Override
	public Double calculateVarience(AccountInfo account) {
		double varienceAsperVal = 0.00;
		String valForRebill = this.getProcessParameter(account);

		if (valForRebill.equalsIgnoreCase("P")) {

			if (account.getRebillAmount() != 0 && account.getCalculatedRebillAmount() != 0) {
				// use new new rebill insted of total usage
				//				varienceAsperVal = (Math.abs((Math.subtractExact(account.getTollUsage(), account.getRebillAmount())) * 100)
				//						/  account.getRebillAmount());

				varienceAsperVal = (Math.abs((account.getCalculatedRebillAmount()-account.getRebillAmount()) * 100)
						/ account.getRebillAmount());

			}
			else if(account.getRebillAmount()==0 && account.getTollUsage()!=0)
			{
				varienceAsperVal=account.getNewRebillAmount();
			}
			log.info("calculating varience on basis percentage is " + varienceAsperVal);
		} else {

			varienceAsperVal = (Math.abs((account.getCalculatedRebillAmount()- account.getRebillAmount())));
			log.info("calculating varience on basis of Amount is " + varienceAsperVal);

		}
		double varienceAsperVal1=Math.round(varienceAsperVal*100.0)/100.0;
		return varienceAsperVal1;
		// currentusage-rebillamt*100/rebillamt
	}

	public String checkRebillStatus(AccountInfo account)

	{
		String paramName = "QEVAL_REDUCE_REBILL_AMOUNT";
		String paramCode = "QEVAL";
		int agencyId = account.getAgencyId();
		String val = "";
		val = batchDao.getParamValForRebill(paramName, paramCode, agencyId);
		return val;

	}

	@Override
	public void checkVarience(AccountInfo account) {
		Double varience = account.getVarience();
		//Long newRebillAmount = this.getNewrebill(account);
		String valForRebill = this.getProcessParameter(account);
		// rebill status
		String val = checkRebillStatus(account);
		// policy data
		//QvalPolicyData policyData = masterCatch.getPolicyDetail(account.getAgencyId(), account.getAccountType());
		QvalPolicyData policyData=batchDao.getPolicyDataByAccount(account.getAgencyId(),account.getAccountType());
		log.info("Policy data :" + policyData);
		// call based on Percentage

		if (valForRebill.equalsIgnoreCase("P")) {
			// policy data max or min range will come
			// percent increase in usage
			// increaseUsage=100 + varience(e.g 100 +30 =130)

			// increaseUsage>policyData.getMaxRange()//range 120 so 130 is >120 rebill up
			if (varience > policyData.getMaxRange()) {
				//account.setNewRebillAmount(account.getRebillPeriod());// current usage
				account.setRecordStatus(1); // up
				log.info("Rebill Up :" + account.getNewRebillAmount());
			}

			//
			else if (varience < policyData.getMinRange() && val.equalsIgnoreCase("NO")) {

				if (account.getRebillAmount() == 0 && account.getCalculatedRebillAmount() != 0) {
					//account.setNewRebillAmount(account.getRebillPeriod());
					account.setRecordStatus(1); // up
					log.info("Rebill Up :" + account.getNewRebillAmount());
				} else {
					account.setRecordStatus(3); // down but no change
					log.info("Skipp Record");
				}

			}

			else if (varience < policyData.getMinRange() && val.equalsIgnoreCase("YES")) {

				if (account.getRebillAmount() == 0 && account.getCalculatedRebillAmount() != 0) {
					//	account.setNewRebillAmount(account.getRebillPeriod());
					account.setRecordStatus(2); // down
					log.info("Rebill Down :" + account.getNewRebillAmount());
				}
				else
				{
					account.setRecordStatus(2); // down
					log.info("Rebill Down :" + account.getNewRebillAmount());
				}

			}

			else if (varience > policyData.getMinRange() && varience < policyData.getMaxRange()) {

				account.setRecordStatus(3); // Skipped
				log.info("Skipp Record");

			} else if (account.getRebillAmount() == 0 && account.getCalculatedRebillAmount() == 0) {
				account.setRecordStatus(3); // Skipped
				log.info("Skipp Record");

			}
		}

		// cal based on amount

		if (valForRebill.equalsIgnoreCase("A")) {

			// policy data max or min range will come
			if (varience > policyData.getMaxAmount()) {
				//account.setNewRebillAmount(account.getRebillPeriod());// current usage
				account.setRecordStatus(1); // up
				log.info("Rebill UP :"+account.getNewRebillAmount());
			}

			else if (varience < policyData.getMinAmount() && val.equalsIgnoreCase("NO")) {

				if (account.getRebillAmount() == 0) {
					//	account.setNewRebillAmount(account.getRebillPeriod());
					account.setRecordStatus(1); // up
					log.info("Rebill Up :" + account.getNewRebillAmount());
				} else {
					account.setRecordStatus(3); // down but no change
					log.info("Skipp Record");
				}

			}
			else if (varience < policyData.getMinAmount() && val.equalsIgnoreCase("YES")) {

				if (account.getRebillAmount() == 0 && account.getCalculatedRebillAmount() != 0) {
					//	account.setNewRebillAmount(account.getRebillPeriod());
					account.setRecordStatus(2); // down
					log.info("Rebill Down :" + account.getNewRebillAmount());
				}
				else
				{
					account.setRecordStatus(2); // down
					log.info("Rebill Down :" + account.getNewRebillAmount());
				}

			}

			else if (varience > policyData.getMinAmount() && varience < policyData.getMaxAmount()) {

				account.setRecordStatus(3); // Skipped
				log.info("Skipp Record");

			} else if (account.getRebillAmount() == 0 && account.getCalculatedRebillAmount() == 0) {
				account.setRecordStatus(3); // Skipped
				log.info("Skipp Record");

			}

		}

	}

	public Double getThreshold(AccountInfo vo) {
		double d = 0.0;
		log.info("*** getThreshold() Entered ***");
		Long etcAccountId = vo.getEtcAccountId();
		String accountType = vo.getAccountType();
		String payType = vo.getPayType();
		int agencyId = vo.getAgencyId();
		String category = "TOLDEPOSIT";
		int val = 0;
		int sum = 0;
		int unitPrice = 0;

		// getting data from t threshold
		ThresholdDTO optDto = batchDao.findBy(accountType, payType, agencyId);


		String paramName = "REBILL_CALCULATION";
		String paramCode = vo.getAccountType();

		String paramvalForThresholdCal = batchDao.getParamValForThreshold(paramName, paramCode);
		// get details from v account plan detail
		// will get multiple plan
		// String subCategory=batchDao.getPlanDetails(etcAccountId);
		List<String> planList = batchDao.getPlanDetails(etcAccountId);
		// get unit price from tpricing
		// cal on basis of max unit price

		for (String subCategory : planList) {

			unitPrice = batchDao.getUnitPrice(category, subCategory, accountType, payType, agencyId);

			if (paramvalForThresholdCal.equalsIgnoreCase("MAX")) {
				if (val < unitPrice) {
					val = unitPrice;
				}

			} else {
				sum = sum + unitPrice;

			}

		}

		if (paramvalForThresholdCal.equalsIgnoreCase("MAX")) {
			d = val * (optDto.getMinThresholdPercentage() / 100);
		} else {

			d = sum * (optDto.getMinThresholdPercentage() / 100);
		}
		double minvalue = optDto.getAmount();
		if (d < minvalue) {
			log.info("Threashhold amount is  " + d);
			return minvalue;
		} else {
			log.info("Threashhold amount is " + d);
			return d;
		}

	}

	@Override
	public Double compareThreshHoldAmount(Double Usage) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Double calculateRebillAmount(Double Usage) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean sentMail(AccountInfo account) throws Exception {
		String resoureceUrl=configVariable.getMailApi();

		ObjectMapper mapper = new ObjectMapper();
		String json = null;
		mapper.enable(SerializationFeature.INDENT_OUTPUT);
		// RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

		EMailHouseDetails eMailData = batchDao.loadMailHouseData(account);
		if (eMailData != null) {
			if (eMailData.getDeliveryType().equalsIgnoreCase("MAIL")) {
				MailHouseDetails mailDetail = new MailHouseDetails();
				mailDetail.setAccountNumber(eMailData.getAccountNumber());
				mailDetail.setDeliveryType(eMailData.getDeliveryType());
				mailDetail.setCompanyName(eMailData.getCompanyName());
				mailDetail.setField1(account.getEvalStartDate().toString());
				mailDetail.setField2(account.getEvalEndDate().toString());
				mailDetail.setField3(account.getTollUsage().toString());
				mailDetail.setField4(account.getRebillAmount().toString());
				mailDetail.setField5(account.getNewRebillAmount().toString());
				mailDetail.setField6(account.getThresholdAmount().toString());
				json = mapper.writeValueAsString(mailDetail);
				log.info("MailData List -----:" + mailDetail);
				log.info("MailData Json -----:" + json);
				HttpEntity<String> request = new HttpEntity<String>(json.toString(), headers);

				// HttpEntity<String> request = new HttpEntity<String>(json.toString());
				// ResponseEntity<String> responseEntity =
				// restTemplate.postForEntity(resoureceUrl, request, String.class);
				ResponseEntity<String> responseEntity = restTemplate.exchange(resoureceUrl, HttpMethod.POST, request,
						String.class);
				if (responseEntity.getStatusCode() == HttpStatus.OK)
					log.info(responseEntity.getBody());

			} else // (eMailData.getDeliveryType().equalsIgnoreCase("EMAIL"))
			{
				eMailData.setEvalStartDate(account.getEvalStartDate().toString());
				eMailData.setEvalEndDate(account.getEvalEndDate().toString());
				eMailData.setTollUsage(account.getTollUsage().toString());
				eMailData.setOldRebillAmount(account.getRebillAmount().toString());
				eMailData.setNewRebillAmount(account.getNewRebillAmount().toString());
				eMailData.setThreshold(account.getThresholdAmount().toString());
				json = mapper.writeValueAsString(eMailData);
				log.info("EmailData List-----:" + eMailData);
				log.info("EmailData JSON-----:" + json);
				HttpEntity<String> request = new HttpEntity<String>(json.toString(), headers);
				ResponseEntity<String> responseEntity = restTemplate.postForEntity(resoureceUrl, request, String.class);

				// ResponseEntity responseEntity = restTemplate.exchange( resoureceUrl,
				// HttpMethod.POST, null, new ParameterizedTypeReference<EMailHsouseDeatils>()
				// {} );
				if (responseEntity.getStatusCode() == HttpStatus.OK)
					log.info(responseEntity.getBody());

			}

		}
		return true;
	}

	@Override
	public void processUpdatedRecords(List<? extends AccountInfo> items, StatisticsData statisticsData)
			throws Exception {
		for (AccountInfo account : items) {
			int status = account.getRecordStatus();
			if (status == 1)// Rebill up
			{
				statisticsData.setRebilleUpCount(statisticsData.getRebilleUpCount() + 1);
				// mail
				sentMail(account);

			} else if (status == 2)// Rebill down
			{
				statisticsData.setRebilleDownCount(statisticsData.getRebilleDownCount() + 1);
				sentMail(account);
			} else if (status == 3)// skip
			{
				statisticsData.setSkipRecCount(statisticsData.getSkipRecCount() + 1);
			}
		}
		statisticsData.setTotalRecProcessed(statisticsData.getTotalRecProcessed() + items.size());
		// step 1.Mail logic
		// if(sentMail(account))

		// step 2.insert into statement table
		batchDao.insertStmtRecord(items);

		// step 4. update rebill amount on T_FPMS_ACCOUNT
		batchDao.updateRebillAmount(items);

		// 5.update start date into the FPMS.T_FPMS_ACCOUNT_EXT(it is execute in batch
		// record)
		batchDao.updateQvalStartEndDate(items);

	}

	@Override
	public void excuteBusinesslogic(AccountInfo account) {
		// TODO Auto-generated method stub

	}

	@Override
	public void loadMasterData() {

		qValPolicyData = batchDao.loadQVALPolicyData();
		issueTypeMappingData = batchDao.issueTypeMappingData();
		tPricingData = batchDao.tPricingData();
		vAccountPlan = batchDao.vAccountPlanDatailData();

	}

	public void calculateRebillAmount(AccountInfo account) {
		// TODO Auto-generated method stub

	}

	@Override
	public AccountInfo processAccount(AccountInfo account, Map<String, Object> processContext) {

		// process->2.Execute following:calculate varience,Rebillamount,ThreshHold
		account.setTollUsage(getUsageSum(account));

		this.calculatePeriodVal(account);
		Double thresholdAmt=this.getThreshold(account);
		account.setNewRebillAmtForThreshold(thresholdAmt);
		this.compareRebillandThreshold(account);
		account.setVarience(calculateVarience(account));

		// 2. calculate variance ..
		checkVarience(account);

		log.info("Processor End");

		return account;

	}

	public void compareRebillandThreshold(AccountInfo account) {

		double thresholdAmt=Math.ceil( Math.round(account.getNewRebillAmtForThreshold()*100.0)/100.0);
		double newRebillAmt=Math.ceil(Math.round(account.getCalculatedRebillAmount()*100.0)/100.0);

		if (thresholdAmt>newRebillAmt) {
			account.setNewRebillAmount(thresholdAmt);
		}
		else {
			account.setNewRebillAmount(newRebillAmt);
		}
		log.info("MAX Rebill amount " + account.getNewRebillAmount());
	}

}
