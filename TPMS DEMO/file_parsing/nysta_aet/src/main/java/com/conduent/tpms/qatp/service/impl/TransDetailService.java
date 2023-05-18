package com.conduent.tpms.qatp.service.impl;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import com.conduent.tpms.qatp.constants.QATPConstants;
import com.conduent.tpms.qatp.dao.ITransDetailDao;
import com.conduent.tpms.qatp.dto.OCRDto;
import com.conduent.tpms.qatp.dto.TollCalculationResponseDto;
import com.conduent.tpms.qatp.model.AtpFileIdObject;
import com.conduent.tpms.qatp.model.QatpStatistics;
import com.conduent.tpms.qatp.model.TranDetail;
import com.conduent.tpms.qatp.model.XferControl;
import com.conduent.tpms.qatp.service.ITransDetailService;
import com.conduent.tpms.qatp.utility.Convertor;
import com.conduent.tpms.qatp.utility.MasterDataCache;
import com.conduent.tpms.qatp.utility.MessagePublisher;
import com.conduent.tpms.qatp.utility.AsyncProcessCall;
import com.google.gson.Gson;
import com.oracle.bmc.streaming.model.PutMessagesDetailsEntry;

@Service
public class TransDetailService implements ITransDetailService
{
	private static final Logger logger = LoggerFactory.getLogger(TransDetailService.class);

	@Autowired
	protected ITransDetailDao transDetailDao;

	@Autowired
	private MessagePublisher messagePublisher;
	
	@Autowired
	private MasterDataCache masterDataCache;
	
	@Autowired
	private AsyncProcessCall asycnProcessCall;
	
	@Autowired
	private NamedParameterJdbcTemplate namedJdbcTemplate;
	
	@Autowired
	Gson gson;

	@Override
	public void saveTransDetail(TranDetail tranDetail) {
		transDetailDao.saveTransDetail(tranDetail);
	}

	@Override
	public List<Long> loadNextSeq(Integer numOfSeq) {
		return transDetailDao.loadNextSeq(numOfSeq);
	}

	@Override
	public void batchInsert(List<TranDetail> txlist) {
		transDetailDao.batchInsertnEW(txlist);
	}

	public long publishMessages(List<TranDetail> txlist , List<AtpFileIdObject> atpFileIdList,Integer agencyIdNystaOrNysba) throws Exception 
	{
		TranDetail tempTx;
		long msgCounter = 0;
		List<PutMessagesDetailsEntry> messages = new LinkedList<>();
		for (int i = 0; i < txlist.size(); i++) 
		{
			tempTx=txlist.get(i);
			
			if(tempTx.getPlazaAgencyId()==null) 
			{
				tempTx.setPlazaAgencyId(agencyIdNystaOrNysba);
			}
			
			if(tempTx.getPlazaId()==null) 
			{
				tempTx.setPlazaId(masterDataCache.getPlazaIfNull(agencyIdNystaOrNysba));
			}
			
			tempTx.setAtpFileIdList(atpFileIdList);
			
			byte[] msg = gson.toJson(tempTx).getBytes();
			logger.info("Publish Message ====>"+ gson.toJson(tempTx));
			messages.add(PutMessagesDetailsEntry.builder().key(tempTx.getLaneTxId().toString().trim().getBytes())
					.value(msg).build());
		}
		int msgsSize = messages.size();
		if (msgsSize != 0) 
		{
			msgCounter = messagePublisher.publishMessages(messages);
			if (msgCounter == msgsSize) 
			{
				logger.debug("Messages Published Successfully: Expectd : {},Published : {} ", msgsSize, msgCounter);

			}
			else if (msgCounter == 0) 
			{
				logger.info("Failed to publish Messages: Expectd : {},Published : {}", msgsSize, msgCounter);
			}
			else 
			{
				logger.info("Messages Published Partially: Expectd : {},Published : {} ", msgsSize, msgCounter);
			}
		}
		return msgCounter;
	}

	@Override
	public void pushToOCR(List<TranDetail> txlist) 
	{
		try
		{
			List<OCRDto> list=new ArrayList<>(txlist.size());
			for(TranDetail t:txlist)
			{
				if(t.getTxType()=="E" || t.getTxType()=="V")
				{
					if(!t.getFrontOcrPlateState().equals("**") && !t.getRearOcrPlateState().equals("**") &&
							!t.getFrontOcrPlateNumber().equals("**********") && !t.getRearOcrPlateNumber().equals("**********"))
					{
						list.add(t.getOCRDto());
					}
				}
			}
			logger.info("Calling OCR API with data {}",gson.toJson(list));
			
			asycnProcessCall.calltoOCRAPI(list);
		}
		catch(Exception ex)
		{
			logger.info(ex.getMessage());
		}
	}

	@Override
	public List<AtpFileIdObject> getAtpFileId(Long xferControlId) 
	{
		try
		{
			MapSqlParameterSource paramSource = new MapSqlParameterSource();
			String queryToCheckFile = "SELECT ATP_FILE_ID , FILE_TYPE from TPMS.T_QATP_STATISTICS where XFER_CONTROL_ID = '"+xferControlId+"' ";
			List<AtpFileIdObject> listOfObj = namedJdbcTemplate.query(queryToCheckFile, paramSource,BeanPropertyRowMapper.newInstance(AtpFileIdObject.class));
			if(listOfObj.size()>0) 
			{
				return listOfObj;
			}
			else
			{
				return null;
			}
		}
		catch(EmptyResultDataAccessException ex)
		{
			logger.info("Exception: {}",ex.getMessage());
		}
		return null;
	}
	
	private boolean validateStar(String value) {
		String matcher_String = "\\s{"+value.length()+"}|[*]{"+value.length()+"}";
		if(value.matches(matcher_String)) 
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	
}