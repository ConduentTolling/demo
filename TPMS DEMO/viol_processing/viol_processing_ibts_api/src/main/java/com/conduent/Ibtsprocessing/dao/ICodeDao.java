package com.conduent.Ibtsprocessing.dao;

import java.util.List;

import com.conduent.Ibtsprocessing.model.Codes;

public interface ICodeDao 
{
	public List<Codes> getCodes();
	public List<Codes> getCodesList(String codeType, String codeValue);
}
