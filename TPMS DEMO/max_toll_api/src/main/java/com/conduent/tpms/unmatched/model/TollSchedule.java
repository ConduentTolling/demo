package com.conduent.tpms.unmatched.model;

import java.util.List;
import java.util.Optional;

public class TollSchedule
{
	private Double fullFare;
	private Double discountFare;
	private Double extraAxleCharge;
	private Long priceScheduleId;
	private String isPeak;
	private Integer revenueType;
	private Integer tripPerTrx;
	private Integer planType;
	private String daysInd;
	
	public Double getFullFare() {
		return fullFare;
	}
	public void setFullFare(Double fullFare) {
		this.fullFare = fullFare;
	}
	public Double getDiscountFare() {
		return discountFare;
	}
	public void setDiscountFare(Double discountFare) {
		this.discountFare = discountFare;
	}
	public Double getExtraAxleCharge() {
		return extraAxleCharge;
	}
	public void setExtraAxleCharge(Double extraAxleCharge) {
		this.extraAxleCharge = extraAxleCharge;
	}
	public Long getPriceScheduleId() {
		return priceScheduleId;
	}
	public void setPriceScheduleId(Long priceScheduleId) {
		this.priceScheduleId = priceScheduleId;
	}
	public String getIsPeak() {
		return isPeak;
	}
	public void setIsPeak(String isPeak) {
		this.isPeak = isPeak;
	}
	public Integer getTripPerTrx() {
		return tripPerTrx;
	}
	public void setTripPerTrx(Integer tripPerTrx) {
		this.tripPerTrx = tripPerTrx;
	}
	
	public Integer getRevenueType() {
		return revenueType;
	}
	public void setRevenueType(Integer revenueType) {
		this.revenueType = revenueType;
	}
	
	public Integer getPlanType() {
		return planType;
	}
	public void setPlanType(Integer planType) {
		this.planType = planType;
	}
	
	
	
	public String getDaysInd() {
		return daysInd;
	}
	public void setDaysInd(String daysInd) {
		this.daysInd = daysInd;
	}
	public static TollSchedule getTollSchedule(List<TollSchedule> list,Integer tollRevenueType,Integer planType,String isPeak)
	{
		if(list==null)
		{
			return null;
		}
		Optional<TollSchedule> tollSchedule=list.stream().filter(e->e.getRevenueType().intValue()==tollRevenueType && e.getPlanType().intValue()==planType && e.isPeak.equals(isPeak) ).findFirst();
		if(tollSchedule.isPresent())
		{
			return tollSchedule.get();
		}
		return null;
	}
	public static TollSchedule getTollSchedule(List<TollSchedule> list,Integer tollRevenueType,Integer planType)
	{
		if(list==null || tollRevenueType==null || planType==null)
		{
			return null;
		}
		Optional<TollSchedule> tollSchedule=list.stream().filter(e->e.getRevenueType().intValue()==tollRevenueType && e.getPlanType().intValue()==planType).findFirst();
		if(tollSchedule.isPresent())
		{
			return tollSchedule.get();
		}
		return null;
	}
	
	public static TollSchedule getTollSchedule(List<TollSchedule> list,Integer revenueType,Integer planTypeId,String isPeak,String inDaysInd)
	{
		if(list==null || revenueType==null || planTypeId==null || isPeak==null || inDaysInd==null)
		{
			return null;
		}
		Optional<TollSchedule> tollSchedule=list.stream().filter(e->e.getRevenueType().equals(revenueType) && e.getPlanType().equals(planTypeId) && e.getDaysInd().equals(inDaysInd)).findFirst();
		if(tollSchedule.isPresent())
		{
			return tollSchedule.get();
		}
		return null;
	}
}