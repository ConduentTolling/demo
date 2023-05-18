package com.conduent.tollposting.dao;

import java.time.LocalDate;
import com.conduent.tollposting.model.AccountPlanSuspension;

public interface IAccountPlanSuspensionDao 
{
	public AccountPlanSuspension getAccountPlanSuspension(String apdId, Long etcAccountId, LocalDate trxDate);
	public void updateAcountPlanSuspension(LocalDate txDate,String apdId,Long etcAccountId,LocalDate suspStart,LocalDate suspEnd);
}
