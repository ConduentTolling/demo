package com.conduent.tpms.cams.dao;

import java.text.ParseException;

import org.springframework.data.domain.Page;

import com.conduent.tpms.cams.dto.PlanSuspensionRequestModel;
import com.conduent.tpms.cams.dto.PlanSuspensionRequestModel2;
import com.conduent.tpms.cams.dto.PlanSuspensionResponseModel;

public interface PlanSuspensionDao {
	
	public Page<PlanSuspensionResponseModel> fetchPlanSuspensionDaoList(PlanSuspensionRequestModel planSuspensionRequestModel) throws ParseException;

	public Integer insertPlanSuspensionDaoDetails(PlanSuspensionRequestModel2 planSuspensionRequest2) throws ParseException;
	
	public Integer updatePlanSuspensionDaoDetails(PlanSuspensionRequestModel2 planSuspensionRequest2) throws ParseException;

	public long fetchPlanSuspensionCount(PlanSuspensionRequestModel planSuspensionRequest) throws ParseException;




}
