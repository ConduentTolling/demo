package com.conduent.parking.posting.service;

import java.time.LocalDate;

import com.conduent.parking.posting.model.AccountPlanSuspension;

public interface IAccountPlanSuspensionService 
{
	public AccountPlanSuspension getAccountPlanSuspension(String apdId, Long etcAccountId, LocalDate trxDate);
	public void updateAcountPlanSuspension(LocalDate txDate,String apdId,Long etcAccountId,LocalDate suspStart,LocalDate suspEnd);
}
