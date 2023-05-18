package com.conduent.Ibtsprocessing.test;

import org.springframework.stereotype.Component;

import com.conduent.Ibtsprocessing.dto.IBTSViolDTO;
import com.conduent.Ibtsprocessing.dto.QueueMessage;
import com.conduent.Ibtsprocessing.model.XferControl;
@Component
public class TestConstants {

	
	private String IbtsApiUri="https://image-dev-tollingbos.services.conduent.com/api/image/transactions";

	
	public String getIbtsApiUri() {
		return IbtsApiUri;
	}

	public void setIbtsApiUri(String ibtsApiUri) {
		IbtsApiUri = ibtsApiUri;
	}


	private String config_atpQueue="ocid1.stream.oc1.iad.amaaaaaai6on7cyawwwtvu2tvxvsw44bvuhpmtdvyecgt6fmyaxa2tvevfpa";
	private String config_groupname="violProcessQueueGroup";
	//private String config_configfilepath="C:/Users/sakshib1/Documents/conduent/config.txt";
	private String config_streamid="ocid1.stream.oc1.iad.amaaaaaai6on7cyaf5xcklrpfzylqs3q4luycybtavp3xfb5mss2jhbnafva";
	private String config_away_streamid="ocid1.stream.oc1.iad.amaaaaaai6on7cyabc7l2s5nqecyck7mntgl2uw4xe2lemof4aho3tvlpsnq";
	private String config_exclusionUri="http://129.213.67.244/api/viol-exclusion";
	
	/*
	 * public String getConfig_configfilepath() { return config_configfilepath; }
	 */

	public String getConfig_away_streamid() {
		return config_away_streamid;
	}

	public void setConfig_away_streamid(String config_away_streamid) {
		this.config_away_streamid = config_away_streamid;
	}

//	public void setConfig_configfilepath(String config_configfilepath) {
//		this.config_configfilepath = config_configfilepath;
//	}

	public String getConfig_streamid() {
		return config_streamid;
	}

	public void setConfig_streamid(String config_streamid) {
		this.config_streamid = config_streamid;
	}

	public String getConfig_atpQueue() {
		return config_atpQueue;
	}

	public void setConfig_atpQueue(String config_atpQueue) {
		this.config_atpQueue = config_atpQueue;
	}

	public String getConfig_groupname() {
		return config_groupname;
	}

	public void setConfig_groupname(String config_groupname) {
		this.config_groupname = config_groupname;
	}

	
	public IBTSViolDTO getIbtsDTO(QueueMessage queueObj) {
	IBTSViolDTO violDTO = new IBTSViolDTO();
	violDTO.setAccountAgencyId(queueObj.getAccountAgencyId()!=null?queueObj.getAccountAgencyId():null);
	violDTO.setAccountType(queueObj.getAccountType()!=null?queueObj.getAccountType():null);
	violDTO.setActualAxles(queueObj.getActualAxles()!=null?queueObj.getActualAxles():0);
	violDTO.setActualClass(queueObj.getActualClass()!=null?queueObj.getActualClass():0);
	violDTO.setActualExtraAxles(queueObj.getActualExtraAxles()!=null?queueObj.getActualExtraAxles():0);
	violDTO.setAetFlag(queueObj.getAetFlag());
	violDTO.setAtpFileId(queueObj.getAtpFileId());
	violDTO.setBasefilename(null);
	if (queueObj.getBufferReadFlag() != null) {
		violDTO.setBufferedReadFlag(queueObj.getBufferReadFlag());
	}
	violDTO.setClassAdjType(null);
	violDTO.setCollectorAxles(queueObj.getCollectorAxles()!=null?queueObj.getCollectorAxles():0);
	violDTO.setCollectorClass(queueObj.getCollectorClass()!=null?queueObj.getCollectorClass():0);
	violDTO.setcollectorNumber(queueObj.getCollectorNumber()!=null?queueObj.getCollectorNumber():0);
	if (queueObj.getDepositId() != null) {
		violDTO.setDepositId(queueObj.getDepositId().toString());
	}
	violDTO.setDeviceAgencyClass(queueObj.getDeviceAgencyClass());
	violDTO.setDeviceAxles(queueObj.getDeviceAxles());
	violDTO.setDeviceCodedClass(queueObj.getDeviceCodedClass());
	violDTO.setDeviceIagClass(queueObj.getDeviceIagClass());
	violDTO.setDeviceNo(queueObj.getDeviceNo());
	if (queueObj.getDeviceProgramStatus() != null) {
		violDTO.setDeviceProgramStatus(queueObj.getDeviceProgramStatus());
	}
	violDTO.setDeviceReadCount(queueObj.getDeviceReadCount());
	violDTO.setDeviceWriteCount(queueObj.getDeviceWriteCount());
	violDTO.setDiscountedAmount(queueObj.getDiscountedAmount());
	if (queueObj.getEntryTimestamp() != null) {
		violDTO.setEntryTimestamp(queueObj.getEntryTimestamp().toString());
	}
	violDTO.setEntryDataSource(queueObj.getEntryDataSource());
	violDTO.setEntryDeviceReadCount(queueObj.getEntryDeviceReadCount());
	violDTO.setEntryDeviceWriteCount(queueObj.getEntryDeviceWriteCount());
	violDTO.setEntryLaneId(queueObj.getEntryLaneId()!=null?queueObj.getEntryLaneId():0);
	violDTO.setEntryPlazaId(queueObj.getEntryPlazaId()!=null?queueObj.getEntryPlazaId():0);
	violDTO.setEntryTxSeqNumber(queueObj.getEntryTxSeqNumber()!=null?queueObj.getEntryTxSeqNumber():0);
	violDTO.setEtcAccountId(queueObj.getEtcAccountId());
	violDTO.setTxStatus(queueObj.getTxStatus());
//	if (queueObj.getEventTimestamp() != null) {
//		violDTO.setEventTimestamp(queueObj.getEventTimestamp());
//	}
	violDTO.setEventType(queueObj.getEventType());
	if(queueObj.getExternFileId()!=null) {
	violDTO.setExternFileId(queueObj.getExternFileId());
	}
	violDTO.setForwardAxles(queueObj.getForwardAxles());
	//violDTO.setFullFareAmount(queueObj.getFullFareAmount());
	violDTO.setIsDiscountable(queueObj.getIsDiscountable()!=null?queueObj.getIsDiscountable():"F");
	violDTO.setIsMedianFare(queueObj.getIsMedianFare());
	violDTO.setIsPeak(queueObj.getIsPeak()!=null?queueObj.getIsPeak():"F");
	violDTO.setIsReciprocityTxn(queueObj.getReciprocityTrx()!=null?queueObj.getReciprocityTrx():"F");
	violDTO.setIsReversed(queueObj.getIsReversed());
	violDTO.setLaneId(queueObj.getLaneId());
	violDTO.setLaneMode(queueObj.getLaneMode()!=null?queueObj.getLaneMode():0);
	violDTO.setLaneState(queueObj.getLaneState()!=null?queueObj.getLaneState():0);
	violDTO.setLaneHealth(queueObj.getLaneHealth());
	if (queueObj.getLaneTxId() != null) {
		violDTO.setLaneTxId(queueObj.getLaneTxId());
	}
	violDTO.setImageTaken(queueObj.getImageTaken());
	violDTO.setLaneTxStatus(queueObj.getLaneTxStatus());
	violDTO.setLaneTxType(queueObj.getLanetxType());
	violDTO.setLaneType(queueObj.getLaneType()!=null?queueObj.getLaneType():0);
	violDTO.setPlanTypeId(queueObj.getPlanTypeId());
	violDTO.setPlateCountry(queueObj.getPlateCountry());
	violDTO.setPlateNumber(queueObj.getPlateNumber());
	violDTO.setPlateState(queueObj.getPlateState());
	violDTO.setPlazaAgencyId(queueObj.getPlazaAgencyId());
	violDTO.setPlazaId(queueObj.getPlazaId());
	violDTO.setPostClassAxles(queueObj.getPostClassAxles());
	violDTO.setPostClassClass(queueObj.getPostClassClass());
	violDTO.setPostDeviceStatus(queueObj.getPostDeviceStatus());
	violDTO.setPreTxnBalance(queueObj.getPreTxnBalance());
	violDTO.setPrevEventType(queueObj.getPrevEventType()!=null?queueObj.getPrevEventType():0);
	violDTO.setPrevViolTxStatus(queueObj.getPrevViolTxStatus());
	violDTO.setPriceScheduleId(queueObj.getPriceScheduleId());
	violDTO.setReadAviAxles(queueObj.getReadAviAxles());
	if (queueObj.getSpeedViolFlag() != null) {
		violDTO.setSpeedViolFlag(queueObj.getSpeedViolFlag());
	}
	violDTO.setTollRevenueType(queueObj.getTollRevenueType());
	violDTO.setTollSystemType(queueObj.getTollSystemType());
	violDTO.setTxDate(queueObj.getTxDate());
	violDTO.setTxExternRefNo(queueObj.getTxExternRefNo());
	violDTO.setTxModSeq(queueObj.getTxModSeq());
	if (queueObj.getTxSeqNumber() != null) {
		violDTO.setTxSeqNumber(queueObj.getTxSeqNumber());
	}else {
		violDTO.setTxSeqNumber((long)0);
	}
	violDTO.setTxSubtypeInd(queueObj.getTxSubtypeInd());
	if (queueObj.getTxTimestamp() != null) {
	violDTO.setTxTimestamp(queueObj.getTxTimestamp().toString());
	}
	//violDTO.setTxTypeInd(queueObj.getTxTypeInd());
	violDTO.setTxTypeInd(queueObj.getTxTypeInd()!= null?queueObj.getTxTypeInd() : " ");
	violDTO.setUnrealizedAmount(queueObj.getUnrealizedAmount());
	violDTO.setVehicleSpeed(queueObj.getVehicleSpeed());
	violDTO.setViolTxStatus(queueObj.getViolTxStatus()); //Temporary change for IBTS issue
	violDTO.setViolType(queueObj.getViolType());
	violDTO.setHovFlag("N");
	violDTO.setVideoFareAmount(queueObj.getVideoFareAmount());
	violDTO.setEtcFareAmount(queueObj.getEtcFareAmount());
	violDTO.setCashFareAmount(queueObj.getCashFareAmount());
	violDTO.setExpectedRevenueAmount(queueObj.getExpectedRevenueAmount());
	violDTO.setPostedFareAmount(queueObj.getPostedFareAmount());
	violDTO.setTxStatus(queueObj.getTxStatus());

	return violDTO;

}

}
