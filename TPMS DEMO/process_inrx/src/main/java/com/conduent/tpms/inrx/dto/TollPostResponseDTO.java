package com.conduent.tpms.inrx.dto;

import java.time.LocalDate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.conduent.tpms.inrx.constants.INRXConstants;
import com.conduent.tpms.inrx.constants.TxStatus;
import com.conduent.tpms.inrx.model.TranDetail;
import com.conduent.tpms.inrx.utility.MasterDataCache;
import com.conduent.tpms.inrx.utility.Util;

/*import com.conduent.tollposting.constant.TxStatus;
import com.conduent.tollposting.model.TranDetail;*/

public class TollPostResponseDTO {
	private Long laneTxId;
	private Integer status;
	private Integer txStatus;
	private String postedDate;
	private String depositId;
	private Double unrealizedAmount;
	private Double collectedAmount;
	private String revenueDate;
	private String rowId;
	private Long etcAccountId;
	
	
	private static final Logger log = LoggerFactory.getLogger(TollPostResponseDTO.class);
	public Long getEtcAccountId() {
		return etcAccountId;
	}

	public void setEtcAccountId(Long etcAccountId) {
		this.etcAccountId = etcAccountId;
	}

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

	


	public static TranDetail getTranDetail(TollPostResponseDTO tollPostResponseDTO) {
		TranDetail tranDetail = new TranDetail();
		tranDetail.setLaneTxId(tollPostResponseDTO.getLaneTxId());
		tranDetail.setDepositId(tollPostResponseDTO.getDepositId());
		tranDetail.setEtcTxStatus(TxStatus.getByCode(tollPostResponseDTO.getTxStatus()));
		tranDetail.setRowId(tollPostResponseDTO.getRowId());
		return tranDetail;
	}
	
	public static AwayAgencyDto getAwayAgencyDetail(TollPostResponseDTO tollPostResponseDTO, AccountTollPostDTO toll, MasterDataCache masterCache) {
		AwayAgencyDto awayAgencyDto = new AwayAgencyDto();
		
		awayAgencyDto.setLaneTxId(tollPostResponseDTO.getLaneTxId());
		awayAgencyDto.setDepositId(tollPostResponseDTO.getDepositId());
		awayAgencyDto.setEtcAccountId(tollPostResponseDTO.getEtcAccountId());
		LocalDate postedDate = LocalDate.parse(tollPostResponseDTO.getPostedDate());
		awayAgencyDto.setPostedDate(postedDate);
		//LocalDate revenueDate = LocalDate.parse(tollPostResponseDTO.getRevenueDate());
		//awayAgencyDto.setRevenueDate(revenueDate);
		awayAgencyDto.setTxStatus(tollPostResponseDTO.getTxStatus());
		awayAgencyDto.setRowId(tollPostResponseDTO.getRowId());
		
		awayAgencyDto.setTxDate(toll.getTxDate());
		awayAgencyDto.setTxTimestamp(toll.getTxTimestamp());
		awayAgencyDto.setTxExternRefNo(toll.getTxExternRefNo());
		awayAgencyDto.setExternFileId(toll.getExternFileId());
		awayAgencyDto.setPlazaAgencyId(toll.getPlazaAgencyId());
		awayAgencyDto.setPlazaId(toll.getPlazaId());
		awayAgencyDto.setLaneId(toll.getLaneId());
		awayAgencyDto.setDeviceNo(toll.getDeviceNo());
		awayAgencyDto.setPlateCountry(toll.getPlateCountry());
		awayAgencyDto.setPlateState(toll.getPlateState());
		awayAgencyDto.setPlateNumber(toll.getPlateNumber());
		awayAgencyDto.setUpdateTs(toll.getUpdateTs());
		
		awayAgencyDto.setRevenueDate(Util.setRevenueDate(toll.getPlazaId(),postedDate, masterCache));
		return awayAgencyDto;
	}
	
	
	
	public static INRXQueueDto getINRXQueueDetails(TollPostResponseDTO tollPostResponseDTO, AccountTollPostDTO toll) {
		INRXQueueDto inrxQueueDto = new INRXQueueDto();
		
		inrxQueueDto.setLaneTxId(tollPostResponseDTO.getLaneTxId());
		inrxQueueDto.setDepositId(tollPostResponseDTO.getDepositId());
		LocalDate postedDate = LocalDate.parse(tollPostResponseDTO.getPostedDate());
		inrxQueueDto.setPostedDate(postedDate);
		inrxQueueDto.setAtpFileId(toll.getAtpFileId());
		inrxQueueDto.setReconDate(toll.getReconDate());
		inrxQueueDto.setEventType(INRXConstants.EVENT_TYPE);
		inrxQueueDto.setPlanTypeId(toll.getPlanTypeId());
		inrxQueueDto.setAccountAgencyId(toll.getAccountAgencyId());
		inrxQueueDto.setPostedFareAmount(toll.getPostedFareAmount());
		inrxQueueDto.setExpectedRevenueAmount(toll.getExpectedRevenueAmount());
		
		TxStatus etcTxStatus=TxStatus.getByCode(tollPostResponseDTO.getTxStatus());
		if (etcTxStatus.equals(TxStatus.TX_STATUS_POST)) {
			inrxQueueDto.setTxStatus(INRXConstants.AWPOSTVTOL);
			log.info("If ETC_POST_STATUS having [POST] --> AWPOSTVTOL");
		}else if(etcTxStatus.equals(TxStatus.TX_STATUS_PPST)) {
			inrxQueueDto.setTxStatus(INRXConstants.AWPOSTITOL);
			log.info("If ETC_POST_STATUS having [PPST] --> AWPOSTITOL");
		}
		
		//|| etcTxStatus.equals(TxStatus.TX_STATUS_NPST)-- logic not required as NPST is a part of rejection code.
		String plateNumber =toll.getPlateNumber();
		if((TollPostResponseDTO.isInValidPlateNumber(plateNumber)) &&
				(!(etcTxStatus.equals(TxStatus.TX_STATUS_POST) ||etcTxStatus.equals(TxStatus.TX_STATUS_PPST)
						))) {
			inrxQueueDto.setTxStatus(INRXConstants.AWREJVTOL);
			log.info("If PlateNumber is null and having rejection code --> AWREJVTOL");
		}else if((TollPostResponseDTO.isPlateNumberAlphanumeric(plateNumber) ) &&
				(!(etcTxStatus.equals(TxStatus.TX_STATUS_POST) ||etcTxStatus.equals(TxStatus.TX_STATUS_PPST)
						))) {
			inrxQueueDto.setTxStatus(INRXConstants.AWREJITOL);
			log.info("If PlateNumber is not null and having rejection code --> AWREJITOL");
		}
		
		
		return inrxQueueDto;
	}
	
	
	public static boolean isInValidPlateNumber(String plateNumber) {
		return plateNumber == null || plateNumber.contains("*") || plateNumber.trim().isEmpty();			
	}
	
	public static boolean isPlateNumberAlphanumeric(String plateNumber) {
		
		return plateNumber !=null && plateNumber.matches("^[a-zA-Z0-9]*$");
	}

	@Override
	public String toString() {
		return "TollPostResponseDTO [laneTxId=" + laneTxId + ", status=" + status + ", txStatus=" + txStatus
				+ ", postedDate=" + postedDate + ", depositId=" + depositId + ", unrealizedAmount=" + unrealizedAmount
				+ ", collectedAmount=" + collectedAmount + ", revenueDate=" + revenueDate + ", rowId=" + rowId + "]";
	}
}