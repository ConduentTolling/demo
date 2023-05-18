package com.conduent.tpms.cams.dto;



import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.NumberFormat;
import org.springframework.format.annotation.NumberFormat.Style;

//@JsonInclude(Include.NON_NULL)

public class PlanSuspensionRequestModel2{
    @NotNull(message="EtcAccountId shouldn't be null")
    @NumberFormat(style=Style.NUMBER)
	private Long etcAccountId;
    
    @NumberFormat(style=Style.NUMBER)
    @NotNull(message="ApdId shouldn't be null")
	private Long apdId;
	
    @Pattern(regexp="[0-9]{2}[/][0-9]{2}[/]{1}[0-9]{4}", message = "Date format should be in MM/DD/YYYY format")
	private String startDate;
    
    @Pattern(regexp="[0-9]{2}[/][0-9]{2}[/]{1}[0-9]{4}", message = "Date format should be in MM/DD/YYYY format")
	private String endDate;
	
   
	

    @NotNull(message="UserId shouldn't be null")
    @Pattern(regexp = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$",message= "Given email id is not valid")
    @Size(min=3,max=50)
	private String userId;
	
	
	public PlanSuspensionRequestModel2() {
		// this parameterless constructor is mandatory when you have parameter constructor defined.
	}
	
    public PlanSuspensionRequestModel2(Long etcAccountId, Long apdId, String startDate, String endDate,String userId)
    {
		super();
		this.etcAccountId = etcAccountId;
		this.apdId = apdId;
		this.startDate = startDate;
		this.endDate= endDate;
		this.userId = userId;
		
	}

   
	


	@Override
	public String toString() {
		return "PlanSuspensionRequestModel2 [etcAccountId=" + etcAccountId + ", apdId=" + apdId + ", startDate="
				+ startDate + ", endDate=" + endDate + ", userId=" + userId + "]";
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

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}


}

