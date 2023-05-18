package com.conduent.tpms.parking.model;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ConfigVariable {

	@Value("${config.batchsize}")
	private int batchSize;

	@Value("${server.tomcat.threads.max}")
	private int threadCount;
	
	@Value("${config.threadcount}")
	private int sublistsize;
	
	@Value("${config.threadsleepcount}")
	private int threadSleepCount;
	
	public int getSublistsize() {
		return sublistsize;
	}

	public void setSublistsize(int sublistsize) {
		this.sublistsize = sublistsize;
	}

	public int getThreadsleepcount() {
		return threadSleepCount;
	}

	public void setThreadsleepcount(int threadsleepcount) {
		this.threadSleepCount = threadsleepcount;
	}

	public int getThreadCount() {
		return threadCount;
	}

	public void setThreadCount(int threadCount) {
		this.threadCount = threadCount;
	}

	public int getBatchSize() {
		return batchSize;
	}

	public void setBatchSize(int batchSize) {
		this.batchSize = batchSize;
	}
}
