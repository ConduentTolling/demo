package com.conduent.tpms.qatp.service.impl;

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
import com.conduent.tpms.qatp.model.AtpFileIdObject;
import com.conduent.tpms.qatp.model.TranDetail;
import com.conduent.tpms.qatp.service.ITransDetailService;
import com.conduent.tpms.qatp.utility.Convertor;
import com.conduent.tpms.qatp.utility.MasterDataCache;
import com.conduent.tpms.qatp.utility.MessagePublisher;
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
		transDetailDao.batchInsert(txlist);
	}
	
	public long publishMessages(List<TranDetail> txlist, List<AtpFileIdObject> atpFileIdList, Integer agencyIdPbaOrTiba) throws Exception
	{
		TranDetail tempTx;
		long msgCounter = 0;
		List<PutMessagesDetailsEntry> messages = new LinkedList<>();
		for (int i = 0; i < txlist.size(); i++) 
		{
			tempTx=txlist.get(i);
			
			if(tempTx.getPlazaAgencyId()==null) 
			{
				tempTx.setPlazaAgencyId(agencyIdPbaOrTiba);
			}
			if(tempTx.getPlazaId()==null) 
			{
				tempTx.setPlazaId(masterDataCache.getPlazaIfNull(agencyIdPbaOrTiba));
			}
			
			tempTx.setAtpFileIdList(atpFileIdList);
			
			byte[] msg = gson.toJson(tempTx).getBytes();
			logger.info("Message Publish : {}",gson.toJson(tempTx));
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
	
}