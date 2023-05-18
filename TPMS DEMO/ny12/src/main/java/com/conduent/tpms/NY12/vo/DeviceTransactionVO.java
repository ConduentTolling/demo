package com.conduent.tpms.NY12.vo;

import java.sql.Timestamp;

import oracle.jdbc.internal.OracleTimestampWithTimeZone;

public class DeviceTransactionVO {
	private String    laneTxId;
	private String    entryPlazaId;
	private String    plazaId;
	private String    entryLaneId;
	private String    sectionId;
	private Timestamp entryTimeStamp;
	private Timestamp txTimeStamp;
	private double    discountedAmount;
	private double    collectedAmount;
	private double    unrealizedAmount;
	private int       timeLimitMin;
	private Integer      entryTimeLimit;
	
	private Timestamp nextEntryTimeStamp;
	
	private Integer      nextSectionId;
	
	
	private int       minDiff;
	private boolean   withinTime;
	private boolean   newSection;
	

	
	/*
	 * private OracleTimestampWithTimeZone tx_TimeStamp;
	 * 
	 * 
	 * 
	 * public OracleTimestampWithTimeZone getTx_TimeStamp() { return tx_TimeStamp; }
	 * public void setTx_TimeStamp(OracleTimestampWithTimeZone tx_TimeStamp) {
	 * this.tx_TimeStamp = tx_TimeStamp; }
	 */
	
	
	public Integer getNextSectionId() {
		return nextSectionId;
	}
	public void setNextSectionId(Integer nextSectionId) {
		this.nextSectionId = nextSectionId;
	}
	
	public Integer getEntryTimeLimit() {
		return entryTimeLimit;
	}
	public void setEntryTimeLimit(Integer entryTimeLimit) {
		this.entryTimeLimit = entryTimeLimit;
	}
	
	
	public String getLaneTxId() {
		return laneTxId;
	}
	public void setLaneTxId(String laneTxId) {
		this.laneTxId = laneTxId;
	}
	public String getEntryPlazaId() {
		return entryPlazaId;
	}
	public void setEntryPlazaId(String entryPlazaId) {
		this.entryPlazaId = entryPlazaId;
	}
	public String getPlazaId() {
		return plazaId;
	}
	public void setPlazaId(String plazaId) {
		this.plazaId = plazaId;
	}
	public String getEntryLaneId() {
		return entryLaneId;
	}
	public void setEntryLaneId(String entryLaneId) {
		this.entryLaneId = entryLaneId;
	}
	public String getSectionId() {
		return sectionId;
	}
	public void setSectionId(String sectionId) {
		this.sectionId = sectionId;
	}
	public Timestamp getEntryTimeStamp() {
		return entryTimeStamp;
	}
	public void setEntryTimeStamp(Timestamp entryTimeStamp) {
		this.entryTimeStamp = entryTimeStamp;
	}
	public Timestamp getTxTimeStamp() {
		return txTimeStamp;
	}
	public void setTxTimeStamp(Timestamp txTimeStamp) {
		this.txTimeStamp = txTimeStamp;
	}
	public double getDiscountedAmount() {
		return discountedAmount;
	}
	public void setDiscountedAmount(double discountedAmount) {
		this.discountedAmount = discountedAmount;
	}
	public double getCollectedAmount() {
		return collectedAmount;
	}
	public void setCollectedAmount(double collectedAmount) {
		this.collectedAmount = collectedAmount;
	}
	public double getUnrealizedAmount() {
		return unrealizedAmount;
	}
	public void setUnrealizedAmount(double unrealizedAmount) {
		this.unrealizedAmount = unrealizedAmount;
	}
	public int getTimeLimitMin() {
		return timeLimitMin;
	}
	public void setTimeLimitMin(int timeLimitMin) {
		this.timeLimitMin = timeLimitMin;
	}

	/*
	 * public int getEntryTimeLimit() { return entryTimeLimit; } public void
	 * setEntryTimeLimit(int entryTimeLimit) { this.entryTimeLimit = entryTimeLimit;
	 * }
	 */
	public Timestamp getNextEntryTimeStamp() {
		return nextEntryTimeStamp;
	}
	public void setNextEntryTimeStamp(Timestamp nextEntryTimeStamp) {
		this.nextEntryTimeStamp = nextEntryTimeStamp;
	}

	/*
	 * public int getNextSectionId() { return nextSectionId; } public void
	 * setNextSectionId(int nextSectionId) { this.nextSectionId = nextSectionId; }
	 */
	public int getMinDiff() {
		return minDiff;
	}
	public void setMinDiff(int minDiff) {
		this.minDiff = minDiff;
	}
	public boolean isWithinTime() {
		return withinTime;
	}
	public void setWithinTime(boolean withinTime) {
		this.withinTime = withinTime;
	}
	public boolean isNewSection() {
		return newSection;
	}
	public void setNewSection(boolean newSection) {
		this.newSection = newSection;
	}




	/*npq.lane_tx_id, 
               npq.entry_plaza_id, 
               npq.plaza_id, 
               npq.entry_lane_id, 
               tl.section_id, 
               npq.entry_timestamp, 
               npq.tx_timestamp, 
               npq.discounted_amount, 
               npq.collected_amount, 
               npq.unrealized_amount, 
               tp.time_limit_min, 
               tpe.time_limit_min entry_time_limit, 
               lead(entry_timestamp, 1, tx_timestamp) over (order by entry_timestamp) as next_entry_ts, 
               lead(tl.section_id, 1, -1) over (order by entry_timestamp) as next_section_id 
               round((cast(next_entry_ts as date) - cast(tx_timestamp as date))* 24 * 60) as min_diff 
               (case when min_diff &lt; time_limit_min then 1 else 0 end) within_time, 
               (case when section_id != next_section_id then 1 else 0 end) new_section 
	 */
}
