package com.conduent.tollposting.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.conduent.tollposting.dao.IPlanPolicyDao;
import com.conduent.tollposting.model.PlanPolicy;
import com.conduent.tollposting.service.IPlanPolicyService;

@Service
public class PlanPolicyService implements IPlanPolicyService 
{
	@Autowired
	private IPlanPolicyDao planPolicyDao;

	@Override
	public List<PlanPolicy> getPlanPolicy() 
	{
		return planPolicyDao.getPlanPolicy();
	}
}
