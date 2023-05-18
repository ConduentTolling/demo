package com.conduent.tpms.cams.service;

import java.text.ParseException;
import java.util.List;

import org.springframework.data.domain.Page;

import com.conduent.tpms.cams.dto.PlanSuspensionRequestModel;
import com.conduent.tpms.cams.dto.PlanSuspensionRequestModel2;
import com.conduent.tpms.cams.dto.PlanSuspensionResponseModel;

public interface PlanSuspensionService {
	
	public Page<PlanSuspensionResponseModel> fetchPlanSuspensionList(PlanSuspensionRequestModel planSuspensionRequest) throws ParseException;
    public Integer insertPlanSuspensionDetails(PlanSuspensionRequestModel2 planSuspensionRequest2) throws ParseException;
    public Integer updatePlanSuspensionDetails(PlanSuspensionRequestModel2 planSuspensionRequest2) throws ParseException;
}
