package com.conduent.tollposting.serviceimpl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.conduent.tollposting.dao.ICodeDao;
import com.conduent.tollposting.model.Codes;
import com.conduent.tollposting.service.ICodeService;

@Service
public class CodeService implements ICodeService 
{
	@Autowired
	private ICodeDao codeDao;

	@Override
	public List<Codes> getCodes() 
	{
		return codeDao.getCodes();
	}

}
