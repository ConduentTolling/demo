package com.conduent.tpms.NY12.vo;

import java.util.List;

public class Ny12ProcessResponseVO {
       private String laneTxId;
       private List<MessagesVO> processingStepMessages; 
   public String getLaneTxId() {
		return laneTxId;
	}
	public void setLaneTxId(String laneTxId) {
		this.laneTxId = laneTxId;
	}
	public List<MessagesVO> getProcessingStepMessages() {
		return processingStepMessages;
	}
	public void setProcessingStepMessages(List<MessagesVO> processingStepMessages) {
		this.processingStepMessages = processingStepMessages;
	}
	      
}
