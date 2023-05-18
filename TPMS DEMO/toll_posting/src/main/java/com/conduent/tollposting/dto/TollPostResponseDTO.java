package com.conduent.tollposting.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

import com.conduent.tollposting.constant.Constants;
import com.conduent.tollposting.model.TranDetail;
import com.conduent.tollposting.utility.DateUtils;
import com.conduent.tollposting.utility.LocalDateAdapter;
import com.conduent.tollposting.utility.LocalDateTimeAdapter;
import com.conduent.tollposting.utility.OffsetDateTimeConverter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;

public class TollPostResponseDTO
{
	@Expose(serialize = true, deserialize = true)
	private Long laneTxId;
	
	@Expose(serialize = true, deserialize = true)
	private Integer status; 
	
	@Expose(serialize = true, deserialize = true)
	private Integer txStatus;
	
	@Expose(serialize = true, deserialize = true)
	private String postedDate; 
	
	@Expose(serialize = true, deserialize = true)
	private String depositId;
	
	@Expose(serialize = true, deserialize = true)
	private Double unrealizedAmount;
	
	@Expose(serialize = true, deserialize = true)
	private Double collectedAmount;
	
	@Expose(serialize = true, deserialize = true)
	private String revenueDate;
	
	@Expose(serialize = true, deserialize = true)
	private String rowId;
		
	public Long getLaneTxId() {
		return laneTxId;
	}
	public void setLaneTxId(Long laneTxId) {
		this.laneTxId = laneTxId;
	}
	
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getPostedDate() {
		return postedDate;
	}
	public void setPostedDate(String postedDate) {
		this.postedDate = postedDate;
	}
	
	public String getDepositId() {
		return depositId;
	}
	public void setDepositId(String depositId) {
		this.depositId = depositId;
	}
	public static TranDetail getTranDetail(TollPostResponseDTO tollPostResponseDTO)
	{
		TranDetail tranDetail=new TranDetail();
		tranDetail.setLaneTxId(tollPostResponseDTO.getLaneTxId());
		tranDetail.setDepositId(tollPostResponseDTO.getDepositId());
		tranDetail.setTxStatus(tollPostResponseDTO.getTxStatus());
		tranDetail.setRowId(tollPostResponseDTO.getRowId());
		tranDetail.setPostedDate(DateUtils.parseLocalDate(tollPostResponseDTO.getPostedDate(),Constants.dateFormator));
		tranDetail.setUnrealizedAmount(tollPostResponseDTO.getUnrealizedAmount());
		return tranDetail;
	}
	
	public Double getUnrealizedAmount() {
		return unrealizedAmount;
	}
	public void setUnrealizedAmount(Double unrealizedAmount) {
		this.unrealizedAmount = unrealizedAmount;
	}
	public Double getCollectedAmount() {
		return collectedAmount;
	}
	public void setCollectedAmount(Double collectedAmount) {
		this.collectedAmount = collectedAmount;
	}
	public Integer getTxStatus() {
		return txStatus;
	}
	public void setTxStatus(Integer txStatus) {
		this.txStatus = txStatus;
	}
	public String getRevenueDate() {
		return revenueDate;
	}
	public void setRevenueDate(String revenueDate) {
		this.revenueDate = revenueDate;
	}
	public String getRowId() {
		return rowId;
	}
	public void setRowId(String rowId) {
		this.rowId = rowId;
	}
	@Override
	public String toString() {
		return "TollPostResponseDTO [laneTxId=" + laneTxId + ", status=" + status + ", txStatus=" + txStatus
				+ ", postedDate=" + postedDate + ", depositId=" + depositId + ", unrealizedAmount=" + unrealizedAmount
				+ ", collectedAmount=" + collectedAmount + ", revenueDate=" + revenueDate + ", rowId=" + rowId + "]";
	}
	
	public static TollPostResponseDTO dtoFromJson(String msg)
	{
		Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
				.registerTypeAdapter(OffsetDateTime.class, new OffsetDateTimeConverter(DateTimeFormatter.ISO_OFFSET_DATE_TIME))
				.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
				.registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
				.excludeFieldsWithoutExposeAnnotation().create();
		return gson.fromJson(msg, TollPostResponseDTO.class);
	}
	
}