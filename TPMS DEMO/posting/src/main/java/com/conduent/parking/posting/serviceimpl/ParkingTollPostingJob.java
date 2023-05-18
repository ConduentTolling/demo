package com.conduent.parking.posting.serviceimpl;

import java.time.LocalDateTime;
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

import com.conduent.parking.posting.dto.ParkingTransaction;
import com.conduent.parking.posting.oss.OssStreamClient;
import com.conduent.parking.posting.utility.AllAsyncUtil;
import com.google.common.base.Stopwatch;
import com.google.gson.Gson;


@Service
public class ParkingTollPostingJob implements Runnable
{
	private static final Logger logger = LoggerFactory.getLogger(ParkingTollPostingJob.class);

	@Autowired
	private AllAsyncUtil allAsyncUtil;
	
	@Autowired
	//@Qualifier("failureQueueClient")
	OssStreamClient failureQueueClient;
	
	@Autowired
	//@Qualifier("parkingQueueClient")
	OssStreamClient parkingQueueClient;
	
	@Autowired
	private Gson gson;

	@Autowired
	private Map<String, String> myTollPostingContext;
	
	public String podName = null;
	
	@Override
	public void run() 
	{
		//MDC.put("logFileName", podName);
		logger.info("Parking tollpost job started sucessfully on {}",podName); 
		Stopwatch stopwatch1 = Stopwatch.createStarted();
		try
		{
			while(!Thread.currentThread().isInterrupted())
			{
				try 
				{
					logger.info("logFileName: {}",MDC.get("logFileName"));
					List<ParkingTransaction> queueMessageList=getMessages(parkingQueueClient,20);
					
					for(ParkingTransaction etcTransaction:queueMessageList)
					{
						allAsyncUtil.processTollPosting(etcTransaction, gson, failureQueueClient);
					}
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
		}finally {
			stopwatch1.stop();
			long millis = stopwatch1.elapsed(TimeUnit.MILLISECONDS);
			logger.info("##Finally: Total time taken in {} HOSTNAME: {} is: {} ms",Thread.currentThread().getName(), podName, millis);
		}
	}
 
	/** Read messages from OSS classification stream queue **/
	

	public List<ParkingTransaction> getMessages(OssStreamClient ossStreamClient, Integer limit)
	{
		List<String> messageList=ossStreamClient.getMessage(limit);

		if(messageList==null || messageList.isEmpty())
		{
			logger.info("Empty list: {}", new Date());
			return Collections.emptyList();
		}

		logger.info("Got {} messages from queue",messageList.size());
		List<ParkingTransaction> queueMessageList=new ArrayList<>(messageList.size());
		ParkingTransaction queueMessage=null;
		for(String msg:messageList)
		{
			LocalDateTime from = LocalDateTime.now();
			queueMessage=ParkingTransaction.dtoFromJson(msg);
			logger.info("## Time Taken for thread {} HOSTNAME: {}  in EtcTransaction.dtoFromJson: {} ms", Thread.currentThread().getName(), podName, ChronoUnit.MILLIS.between(from, LocalDateTime.now()));
			if(queueMessage!=null) 
			{
				logger.info("Serialized message for {} ",queueMessage.getLaneTxId());
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
		podName = myTollPostingContext.get("podName") != null ? myTollPostingContext.get("podName"):"defaultPod";
		Thread t=new Thread(this);
		t.start();
	} 
	
}