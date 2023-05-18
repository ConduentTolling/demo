package com.conduent.tpms.qatp.service.impl;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.conduent.app.timezone.utility.TimeZoneConv;
import com.conduent.tpms.qatp.dao.IQatpDao;
import com.conduent.tpms.qatp.dao.ITransDetailDao;
import com.conduent.tpms.qatp.dao.TQatpMappingDao;
import com.conduent.tpms.qatp.dao.impl.InsertMTADaoImpl;
import com.conduent.tpms.qatp.parser.agency.MtaFixlengthParser;
import com.conduent.tpms.qatp.service.ITransDetailService;
import com.conduent.tpms.qatp.service.QatpService;
import com.conduent.tpms.qatp.utility.AsyncProcessCall;
import com.conduent.tpms.qatp.utility.GenericValidation;
import com.conduent.tpms.qatp.utility.MasterDataCache;
import com.conduent.tpms.qatp.validation.TextFileReader;

@Service
public class QatpServiceImpl implements QatpService {
	
	private static final Logger logger = LoggerFactory.getLogger(QatpServiceImpl.class);

	@Autowired
	MtaFixlengthParser mtaFixlengthParser;
	
	@Autowired
	MtaFixlengthParser mtaFixlengthParserThreadSafe;
	
	@Autowired
	protected TQatpMappingDao tQatpMappingDao;

	@Autowired
	private IQatpDao qatpDao;

	@Autowired
	protected ITransDetailService transDetailService;

	@Autowired
	protected
	GenericValidation genericValidation;

	@Autowired
	protected TextFileReader textFileReader;

	@Autowired
	protected MasterDataCache masterDataCache;

	@Autowired
	InsertMTADaoImpl insertMTADaoImpl;

	@Autowired
	TimeZoneConv timeZoneConv;
	
	@Autowired
	AsyncProcessCall asyncProcessCall;
	
	@Autowired
	protected ITransDetailDao transDetailDao;
	

	@Override
	public void process() {
		try {
			mtaFixlengthParser.fileParseTemplate();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void parallelProcess(String fileName) throws Exception 
	{
		try 
		{
			//nystaFixlengthParser.fileParseTemplatewithFileName(fileName);
			mtaFixlengthParserThreadSafe = new MtaFixlengthParser(tQatpMappingDao,qatpDao,transDetailService,genericValidation,masterDataCache,insertMTADaoImpl,timeZoneConv,asyncProcessCall,transDetailDao);
			mtaFixlengthParserThreadSafe.fileParseTemplatewithFileName(fileName);
		}
		catch (IOException e) 
		{
			logger.info("Exception {}",e.getMessage());
		}
	}	
}