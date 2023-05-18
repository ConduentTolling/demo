package com.conduent.tpms.cams.service.impl;

import java.math.BigDecimal;
import java.text.ParseException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Service;

import com.conduent.tpms.cams.dao.PlanSuspensionDao;
import com.conduent.tpms.cams.dto.PlanSuspensionRequestModel;
import com.conduent.tpms.cams.dto.PlanSuspensionRequestModel2;
import com.conduent.tpms.cams.dto.PlanSuspensionResponseModel;
import com.conduent.tpms.cams.service.PlanSuspensionService;
import com.conduent.tpms.cams.util.ValidationUtil;

@Service
public class PlanSuspensionServiceImpl implements PlanSuspensionService {

	@Autowired
	PlanSuspensionDao planSuspensionDao;
	Page<BigDecimal> page;

	@Autowired
	ValidationUtil validationUtil;

	final Logger logger = LoggerFactory.getLogger(PlanSuspensionServiceImpl.class);
	MapSqlParameterSource paramSource = new MapSqlParameterSource();

	@Override
	public Page<PlanSuspensionResponseModel> fetchPlanSuspensionList(PlanSuspensionRequestModel planSuspensionRequest)
			throws ParseException {

		/*
		 * PlanSuspensionRequestModel planSuspensionRequestModel=new
		 * PlanSuspensionRequestModel();
		 * planSuspensionRequestModel.setPage(planSuspensionRequestModel.getPage());
		 * planSuspensionRequestModel.setSize(planSuspensionRequestModel.getSize());
		 */
		logger.info("calling method ");
		if (planSuspensionRequest == null) {
			return null;
		}
		Page<PlanSuspensionResponseModel>planSusResponseInfo = planSuspensionDao.fetchPlanSuspensionDaoList(planSuspensionRequest);

		if ( planSusResponseInfo!=null && !planSusResponseInfo.isEmpty() ) {
			return  (Page<PlanSuspensionResponseModel>) planSusResponseInfo;
			//return new PageImpl<>(planSusResponseInfo, PageRequest.of(planSuspensionRequest.getPage(), planSuspensionRequest.getSize()),planSuspensionDao.fetchPlanSuspensionCount(planSuspensionRequest));
		}
		return planSusResponseInfo;
		
		
	}

	public Integer insertPlanSuspensionDetails(PlanSuspensionRequestModel2 planSuspensionRequest2)
			throws ParseException {

		return planSuspensionDao.insertPlanSuspensionDaoDetails(planSuspensionRequest2);

	}

	/*
	 * private boolean ifEtcAccountPresent(PlanSuspensionResponseModel
	 * planSuspensionResponseModel) {
	 * if(planSuspensionResponseModel.getSuspensionStatus() == 2 &&
	 * planSuspensionResponseModel.getEndDate() != null) { return false; }else
	 * if(planSuspensionResponseModel.getSuspensionStatus() == 1 &&
	 * planSuspensionResponseModel.getEndDate() == null) { //update end date and
	 * status =2
	 * 
	 * String
	 * queryString="UPDATE t_account_plan_suspension SET end_Date = CURRENT_TIMESTAMP, SUSPENSION_STATUS=2 where etc_account_id=:etc_account_id"
	 * ; paramSource.addValue(PlanSuspensionConstant.ETC_ACCOUNT_ID,
	 * planSuspensionResponseModel.getEtcAccountId());
	 * 
	 * } return false; }
	 */

	public Integer updatePlanSuspensionDetails(PlanSuspensionRequestModel2 planSuspensionRequest2)
			throws ParseException {

		return planSuspensionDao.updatePlanSuspensionDaoDetails(planSuspensionRequest2);

	}

}
