package com.conduent.tpms.qatp.service.impl;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.conduent.tpms.qatp.parser.agency.NystaFixlengthParser;
import com.conduent.tpms.qatp.service.QatpService;

@Service
public class QatpServiceImpl implements QatpService 
{
	private static final Logger logger = LoggerFactory.getLogger(QatpServiceImpl.class);
	
	@Autowired
	NystaFixlengthParser nystaFixlengthParser;
	
	
	@Override
	public void process() throws Exception 
	{	
		try 
		{
			nystaFixlengthParser.fileParseTemplate();
		}
		catch (IOException e) 
		{
			logger.info("Exception {}",e.getMessage());
		}
	}	
}