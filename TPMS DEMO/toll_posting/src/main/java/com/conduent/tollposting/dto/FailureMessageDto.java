package com.conduent.tollposting.dto;

import java.time.OffsetDateTime;

import com.google.gson.annotations.Expose;

public class FailureMessageDto {
	
	@Expose(serialize = true, deserialize = true)
	private Long laneTxId;
	
	@Expose(serialize = true, deserialize = true)
	private String key;
	
	@Expose(serialize = true, deserialize = true)
	private String keyType;
	
	@Expose(serialize = true, deserialize = true)
	private String failureMsg;
	
	@Expose(serialize = true, deserialize = true)
	private String failureState;
	
	@Expose(serialize = true, deserialize = true)
	private String failureType;
	
	@Expose(serialize = true, deserialize = true)
	private String failureSubtype;
	
	@Expose(serialize = true, deserialize = true)
	private Integer errorCode;
	
	@Expose(serialize = true, deserialize = true)
	private OffsetDateTime failureTimestamp;
	
	@Expose(serialize = true, deserialize = true)
	private Object payload;
	
	@Expose(serialize = true, deserialize = true)
	private String msgOffset;
	
	@Expose(serialize = true, deserialize = true)
	private String originQueueName;

	public Long getLaneTxId() {
		return laneTxId;
	}

	public void setLaneTxId(Long laneTxId) {
		this.laneTxId = laneTxId;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getKeyType() {
		return keyType;
	}

	public void setKeyType(String keyType) {
		this.keyType = keyType;
	}

	public String getFailureMsg() {
		return failureMsg;
	}

	public void setFailureMsg(String failureMsg) {
		this.failureMsg = failureMsg;
	}

	public String getFailureState() {
		return failureState;
	}

	public void setFailureState(String failureState) {
		this.failureState = failureState;
	}

	public String getFailureType() {
		return failureType;
	}

	public void setFailureType(String failureType) {
		this.failureType = failureType;
	}

	public String getFailureSubtype() {
		return failureSubtype;
	}

	public void setFailureSubtype(String failureSubtype) {
		this.failureSubtype = failureSubtype;
	}

	public Integer getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(Integer errorCode) {
		this.errorCode = errorCode;
	}

	public OffsetDateTime getFailureTimestamp() {
		return failureTimestamp;
	}

	public void setFailureTimestamp(OffsetDateTime failureTimestamp) {
		this.failureTimestamp = failureTimestamp;
	}

	public Object getPayload() {
		return payload;
	}

	public void setPayload(Object payload) {
		this.payload = payload;
	}

	public String getMsgOffset() {
		return msgOffset;
	}

	public void setMsgOffset(String msgOffset) {
		this.msgOffset = msgOffset;
	}

	public String getOriginQueueName() {
		return originQueueName;
	}

	public void setOriginQueueName(String originQueueName) {
		this.originQueueName = originQueueName;
	}

	@Override
	public String toString() {
		return "FailureMessageDto [laneTxId=" + laneTxId + ", key=" + key + ", keyType=" + keyType + ", failureMsg="
				+ failureMsg + ", failureState=" + failureState + ", failureType=" + failureType + ", failureSubtype="
				+ failureSubtype + ", errorCode=" + errorCode + ", failureTimestamp=" + failureTimestamp + ", payload="
				+ payload + ", msgOffset=" + msgOffset + ", originQueueName=" + originQueueName + "]";
	}
	
}
