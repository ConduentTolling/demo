package com.conduent.tollposting.serviceimpl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.conduent.app.timezone.utility.TimeZoneConv;
import com.conduent.tollposting.constant.ConfigVariable;
import com.conduent.tollposting.dto.EtcTransaction;
import com.conduent.tollposting.oss.OssStreamClient;
import com.conduent.tollposting.utility.AllAsyncUtil;
import com.google.common.base.Stopwatch;
import com.google.gson.Gson;


@Service
public class TollPostingJob implements Runnable
{
	private static final Logger logger = LoggerFactory.getLogger(TollPostingJob.class);

	@Autowired
	protected ConfigVariable configVariable;

	@Autowired
	private AllAsyncUtil allAsyncUtil;
	
	@Autowired
	private Gson gson;
	
	@Autowired
	private TimeZoneConv timeZoneConv;

	@Autowired
	OssStreamClient failureQueueClient;

	@Autowired
	OssStreamClient ibtsQueueClient;
	
	@Autowired 
	OssStreamClient atpQueueClient;
	
	
	@Autowired
	private Map<String, String> myTollPostingContext;
	public String podName = null;
	
	@PostConstruct
	public void init()
	{
		podName = myTollPostingContext.get("podName") != null ? myTollPostingContext.get("podName"):"defaultPod";	
	}
	
	@Override
	public void run() 
	{
		logger.info("Toll posting job started sucessfully on {}",podName); 
		Stopwatch stopwatch1 = Stopwatch.createStarted();
		try
		{
			while(!Thread.currentThread().isInterrupted())
			{
				try 
				{
					LocalDateTime fromT1 = LocalDateTime.now();
					/** Step 1: Read message from queue **/	
					logger.debug("Batch size: {}", configVariable.getMessageQueueSize());
					List<EtcTransaction> queueMessageList=getMessages(atpQueueClient,configVariable.getMessageQueueSize());
					logger.debug("1##.Time Taken for fetching {} message(s) by {} HOSTNAME: {} in run.getMessages: {} ms",queueMessageList.size(), Thread.currentThread().getName(), podName, ChronoUnit.MILLIS.between(fromT1, LocalDateTime.now()));
					
					for(EtcTransaction etcTransaction:queueMessageList)
					{
						allAsyncUtil.processTollPosting(etcTransaction, ibtsQueueClient, gson, failureQueueClient);
						
					}

					//sleep 200ms bcz in 200ms above 100 will be submitted 
					//Uninterruptibles.sleepUninterruptibly(configVariable.getSleepTime(), TimeUnit.MILLISECONDS);
				}
				catch (Exception e) 
				{
					e.printStackTrace();
					logger.error("Exception {}",e.getMessage());
				}
			}
		} 
		catch(Exception ex) 
		{
			ex.printStackTrace();
		}
		finally {
			stopwatch1.stop();
			long millis = stopwatch1.elapsed(TimeUnit.MILLISECONDS);
			logger.debug("##Finally: Total time taken in {} HOSTNAME: {} is: {} ms",Thread.currentThread().getName(), podName, millis);
		}
	}

	/** Read messages from OSS classification stream queue **/
	

	public List<EtcTransaction> getMessages(OssStreamClient ossStreamClient,Integer limit)
	{
		List<String> messageList=ossStreamClient.getMessage(limit);

		if(messageList==null || messageList.isEmpty())
		{
			logger.info("Empty list: {}", new Date());
			return Collections.emptyList();
		}else {
			MDC.put("logFileName", "TOLL_POSTING_".concat(timeZoneConv.currentTime().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"))));
		} 	

		logger.info("Got {} messages from queue",messageList.size());
		List<EtcTransaction> queueMessageList=new ArrayList<>(messageList.size());
		EtcTransaction queueMessage=null;
		for(String msg:messageList)
		{
			LocalDateTime from = LocalDateTime.now();
			queueMessage=EtcTransaction.dtoFromJson(msg);
			logger.debug("## Time Taken for {} HOSTNAME: {}  in EtcTransaction.dtoFromJson: {} ms", Thread.currentThread().getName(), podName, ChronoUnit.MILLIS.between(from, LocalDateTime.now()));
			if(queueMessage!=null) 
			{
				logger.debug("Serialized message for {} ",queueMessage.getLaneTxId());
				queueMessageList.add(queueMessage); 
			}
			else 
			{
				logger.error("Error: Unable to parse queue message {}",msg); 
			}
		}
		return queueMessageList;
	}

	@PostConstruct
	public void startJob()
	{
		Thread t=new Thread(this);
		t.start();
	} 
}