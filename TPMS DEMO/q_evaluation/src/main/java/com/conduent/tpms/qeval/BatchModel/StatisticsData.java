package com.conduent.tpms.qeval.BatchModel;

import java.sql.Date;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.stereotype.Component;

@StepScope
@Component
public class StatisticsData {
	
	private int stmtRunId;
	private Date stmtRunDate;
	private Long totalRecProcessed=0L;
	
	private Long rebilleUpCount=0L;
	private Long rebilleDownCount=0L;
	private Long skipRecCount=0L;
	
	public int getStmtRunId() {
		return stmtRunId;
	}
	public void setStmtRunId(int stmtRunId) {
		this.stmtRunId = stmtRunId;
	}
	public Date getStmtRunDate() {
		return stmtRunDate;
	}
	public void setStmtRunDate(Date stmtRunDate) {
		this.stmtRunDate = stmtRunDate;
	}
	public Long getTotalRecProcessed() {
		return totalRecProcessed;
	}
	public void setTotalRecProcessed(Long totalRecProcessed) {
		this.totalRecProcessed = totalRecProcessed;
	}
	public Long getRebilleUpCount() {
		return rebilleUpCount;
	}
	public void setRebilleUpCount(Long rebilleUpCount) {
		this.rebilleUpCount = rebilleUpCount;
	}
	public Long getRebilleDownCount() {
		return rebilleDownCount;
	}
	public void setRebilleDownCount(Long rebilleDownCount) {
		this.rebilleDownCount = rebilleDownCount;
	}
	public Long getSkipRecCount() {
		return skipRecCount;
	}
	public void setSkipRecCount(Long skipRecCount) {
		this.skipRecCount = skipRecCount;
	}

}
