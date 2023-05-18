package com.conduent.tollposting.dto;

import java.time.LocalDateTime;

public class TollPostMessage
{
	private Long laneTxId;
	private String txExternRefNo;
	private Long txSeqNumber;
	private Long externFileId;
	private Integer plazaAgencyId;
	private Integer plazaId;
	private Integer laneId;
	private Integer txModSeq;
	private String txTypeInd;
	private String txSubtypeInd;
	private Integer laneMode;
	private Integer laneHealth;
	private String collectorId;
	private String entryDataSource;
	private LocalDateTime entryTimeStamp;
	private Integer laneTxStatus;
	private Integer lanetxType;
	private Integer tollRevenueType;
	private Integer actualClass;
	private Integer actualAxles;
	private Integer actualExtraAxles;
	private Integer collectorClass;
	private Integer collectorAxles;
	private Integer preClassClass;
	private Integer preClassAxles;
	private Integer postClassClass;
	private Integer postClassAxles;
	private Integer forwardAxles;
	private Integer reverseAxles;
	private Double fullFareAmount;
	private Double discountedAmount;
	private Double collectedAmount;
	private Double unrealizedAmount;
	private String isDiscountable;
	private String isMedianFare;
	private String isPeak;
	private Integer priceScheduleId;
	private Integer receiptIssued;
	private String deviceNo;
	private Integer deviceAxles;
	
	
	
	private Integer readAviAxles;
	private Integer deviceProgramStatus;
	private Integer bufferReadFlag;
	
	private Integer postDeviceStatus;
	private Integer preTxnBalance;
	private Integer planTypeId;
	
	private String imageTaken;
	private String plateCountry;
	private String plateState;
	private String plateNumber;
	
	private String atpFileId;
	private String isReversed;
	private Integer corrReasonId;
	
	private Integer reconStatusInd;
	private Integer reconSubCodeInd;
	
	private Integer mileage;
	private Integer deviceReadCount;
	private Integer deviceWriteCount;
	private Integer entryDeviceReadCount;
	private Integer entryDeviceWriteCount;
	private Integer depositId;
	
	private String tollSystemType;
	
	
}
