package com.conduent.tollposting.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

import com.conduent.tollposting.constant.Constants;
import com.conduent.tollposting.utility.LocalDateAdapter;
import com.conduent.tollposting.utility.LocalDateTimeAdapter;
import com.conduent.tollposting.utility.OffsetDateTimeConverter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.annotations.Expose;

public class TollCalculationDTO {

	@Expose(serialize = true, deserialize = true)
	private Long laneTxId;
	
	@Expose(serialize = true, deserialize = true)
	private Integer entryPlazaId;
	
	@Expose(serialize = true, deserialize = true)
	private Integer exitPlazaId;
	
	@Expose(serialize = true, deserialize = true)
	private Integer planType;
	
	@Expose(serialize = true, deserialize = true)
	private Integer tollRevenueType;
	
	@Expose(serialize = true, deserialize = true)
	private OffsetDateTime txTimestamp;
	
	@Expose(serialize = true, deserialize = true)
	private Integer agencyId;
	
	@Expose(serialize = true, deserialize = true)
	private Integer actualClass;
	
	@Expose(serialize = true, deserialize = true)
	private String tollSystemType;
	
	@Expose(serialize = true, deserialize = true)
	private Integer accountType;
	
	public Long getLaneTxId() {
		return laneTxId;
	}

	public void setLaneTxId(Long laneTxId) {
		this.laneTxId = laneTxId;
	}

	public Integer getEntryPlazaId() {
		return entryPlazaId;
	}

	public void setEntryPlazaId(Integer entryPlazaId) {
		this.entryPlazaId = entryPlazaId;
	}

	public Integer getExitPlazaId() {
		return exitPlazaId;
	}

	public void setExitPlazaId(Integer exitPlazaId) {
		this.exitPlazaId = exitPlazaId;
	}

	public Integer getPlanType() {
		return planType;
	}

	public void setPlanType(Integer planType) {
		this.planType = planType;
	}

	public Integer getTollRevenueType() {
		return tollRevenueType;
	}

	public void setTollRevenueType(Integer tollRevenueType) {
		this.tollRevenueType = tollRevenueType;
	}

	public OffsetDateTime getTxTimestamp() {
		return txTimestamp;
	}

	public void setTxTimestamp(OffsetDateTime txTimestamp) {
		this.txTimestamp = txTimestamp;
	}

	public Integer getAgencyId() {
		return agencyId;
	}

	public void setAgencyId(Integer agencyId) {
		this.agencyId = agencyId;
	}

	public Integer getActualClass() {
		return actualClass;
	}

	public void setActualClass(Integer actualClass) {
		this.actualClass = actualClass;
	}

	public String getTollSystemType() {
		return tollSystemType;
	}

	public void setTollSystemType(String tollSystemType) {
		this.tollSystemType = tollSystemType;
	}
	
	

	public Integer getAccountType() {
		return accountType;
	}

	public void setAccountType(Integer accountType) {
		this.accountType = accountType;
	}

	@Override
	public String toString() {
		return "TollCalculationDTO [laneTxId=" + laneTxId + ", entryPlazaId=" + entryPlazaId + ", exitPlazaId="
				+ exitPlazaId + ", planType=" + planType + ", tollRevenueType=" + tollRevenueType + ", txTimestamp="
				+ txTimestamp + ", agencyId=" + agencyId + ", actualClass=" + actualClass + ", tollSystemType="
				+ tollSystemType + ", accountType=" + accountType + "]";
	}

	public static TollCalculationDTO dtoFromJson(String msg)
	{
		Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
				.registerTypeAdapter(OffsetDateTime.class, new OffsetDateTimeConverter(DateTimeFormatter.ISO_OFFSET_DATE_TIME))
				.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
				.registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
				.excludeFieldsWithoutExposeAnnotation()
				.registerTypeAdapter(LocalDate.class, (JsonDeserializer<LocalDate>) (json, type, jsonDeserializationContext) ->
			    LocalDate.parse(json.getAsJsonPrimitive().getAsString(), DateTimeFormatter.ofPattern(Constants.dateFormator)))
				.registerTypeAdapter(LocalDateTime.class, (JsonDeserializer<LocalDateTime>) (json, type, jsonDeserializationContext) ->
				LocalDateTime.parse(json.getAsJsonPrimitive().getAsString(), DateTimeFormatter.ofPattern(Constants.dateTimeFormator)))
				.create();
		return gson.fromJson(msg, TollCalculationDTO.class);
	}	
}




/*
{
"entryPlazaId": 7,
"exitPlazaId": 9,
"planType": 1,
"tollRevenueType": 9,
"txTimestamp": "2022-11-14 16.44.22.470000000 PM -05:00",
"agencyId": 1,
"actualClass": 22,
"accountType": 1,
"tollSystemType": "C",
"laneTxId": 20000539398
}
*/