package com.conduent.tollposting.service;

import java.text.ParseException;
import java.time.LocalDate;

import com.conduent.tollposting.dto.AccountInfoDTO;

public interface IAccountInfoService 
{
	public AccountInfoDTO getAccountInfo(Long etcAccountId,Integer accountType, LocalDate txDate) throws ParseException;
	public void getAccountBalance(AccountInfoDTO accountInfo);
}
