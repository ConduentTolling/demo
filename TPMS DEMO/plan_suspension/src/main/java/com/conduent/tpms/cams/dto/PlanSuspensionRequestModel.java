package com.conduent.tpms.cams.dto;



import java.math.BigDecimal;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.NumberFormat;
import org.springframework.format.annotation.NumberFormat.Style;

//@JsonInclude(Include.NON_NULL)

public class PlanSuspensionRequestModel extends PageInfo{
    @NotNull(message="EtcAccountId shouldn't be null")
    @NumberFormat(style=Style.NUMBER)
	private Long etcAccountId;
    
    @NumberFormat(style=Style.NUMBER)
	private Long apdId;
	/*
	 * @DateTimeFormat(iso = ISO.DATE)
	 * 
	 * @JsonFormat(pattern = "MM/dd/yyyy")
	 */
    @Pattern(regexp="[0-9]{2}[/][0-9]{2}[/]{1}[0-9]{4}", message = "Date format should be in MM/DD/YYYY format")
	private String startDate;
	
	/*
	 * @DateTimeFormat(iso = ISO.DATE)
	 * 
	 * @JsonFormat(pattern = "MM/DD/YYYY")
	 */
    @Pattern(regexp="[0-9]{2}[/][0-9]{2}[/]{1}[0-9]{4}", message = "Date format should be in MM/DD/YYYY format")
	private String endDate;
	
	//@JsonInclude(Include.NON_NULL)
	
	@Pattern(regexp="(?i)(OPEN|CLOSED)",message="Status should be OPEN or CLOSED")
	private String status;
	
	
    @NumberFormat(style=Style.NUMBER)
    @Size(min=3,max=20)
	private String userId;
    private Long recordCount;
    
	
   
    
    
	public String stringResponse1() {
		// this parameterless constructor is mandatory when you have parameter constructor defined.
		return "Plan Suspension Inserted Successfully";
	}
	
	public PlanSuspensionRequestModel() {
		// this parameterless constructor is mandatory when you have parameter constructor defined.
	}
	
    public PlanSuspensionRequestModel(Long etcAccountId, Long apdId, String startDate, String endDate, String status,String userId)
    {
		super();
		this.etcAccountId = etcAccountId;
		this.apdId = apdId;
		this.startDate = startDate;
		this.endDate = endDate;
		this.status = status;
		this.userId = userId;
		
	}

   
	
    @Override
	public String toString() {
		return "PlanSuspensionRequestModel [etcAccountId=" + etcAccountId + ", apdId=" + apdId + ", startDate="
				+ startDate + ", endDate=" + endDate + ", status=" + status + ", userId=" + userId + "]";
	}
	


	public Long getEtcAccountId() {
		return etcAccountId;
	}

	

	public void setEtcAccountId(Long etcAccountId) {
		this.etcAccountId = etcAccountId;
	}

	public Long getApdId() {
		return apdId;
	}

	public void setApdId(Long apdId) {
		this.apdId = apdId;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String stringResponse() {
		return null;
	}

	public Long getRecordCount() {
		return recordCount;
	}

	public void setRecordCount(Long recordCount) {
		this.recordCount = recordCount;
	}

	
}

