package com.conduent.tpms.NY12.vo;

import java.util.Date;
import java.util.List;

public class Ny12ProcessFinalResponseVO {
	   //private String processName = "NY12_Process";
	   //private Date   processingDate = new Date();
	
       //private List<List<Ny12ProcessResponseVO>> response; 
       private int totalNumberOfRecordsToBeProcessed;
       private int totalNumberOfRecordsProcessed;
       private int totalNumberOfErroredRecords;
	
  
		/*
		 * public String getProcessName() { return processName; } public void
		 * setProcessName(String processName) { this.processName = processName; } public
		 * Date getProcessingDate() { return processingDate; } public void
		 * setProcessingDate(Date processingDate) { this.processingDate =
		 * processingDate; }
		 */
       
		/*
		 * public List<List<Ny12ProcessResponseVO>> getResponse() { return response; }
		 * public void setResponse(List<List<Ny12ProcessResponseVO>> response) {
		 * this.response = response; }
		 */
       
	public int getTotalNumberOfRecordsToBeProcessed() {
		return totalNumberOfRecordsToBeProcessed;
	}
	public void setTotalNumberOfRecordsToBeProcessed(int totalNumberOfRecordsToBeProcessed) {
		this.totalNumberOfRecordsToBeProcessed = totalNumberOfRecordsToBeProcessed;
	}
	public int getTotalNumberOfRecordsProcessed() {
		return totalNumberOfRecordsProcessed;
	}
	public void setTotalNumberOfRecordsProcessed(int totalNumberOfRecordsProcessed) {
		this.totalNumberOfRecordsProcessed = totalNumberOfRecordsProcessed;
	}
	public int getTotalNumberOfErroredRecords() {
		return totalNumberOfErroredRecords;
	}
	public void setTotalNumberOfErroredRecords(int totalNumberOfErroredRecords) {
		this.totalNumberOfErroredRecords = totalNumberOfErroredRecords;
	}
       
}
