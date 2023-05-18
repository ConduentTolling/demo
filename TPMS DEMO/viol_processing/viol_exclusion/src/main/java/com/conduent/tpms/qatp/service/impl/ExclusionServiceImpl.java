package com.conduent.tpms.qatp.service.impl;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.conduent.tpms.qatp.dao.impl.ExclusionDaoImpl;
import com.conduent.tpms.qatp.dto.Exclusion;
import com.conduent.tpms.qatp.service.ExclusionService;

@Service
public class ExclusionServiceImpl implements ExclusionService {

	@Autowired
	ExclusionDaoImpl dao;

	@Override
	public boolean checkExclusionRecords(List<Exclusion> exclusionList, Exclusion exclusion) {
		boolean ExclusionRecordsStatus = false;

		HashMap<String, Object> mapExclusion = new HashMap<>();

		if (exclusion.getAgencyId() != null) {
			mapExclusion.put("agencyId", exclusion.getAgencyId());
		}
		if (exclusion.getPlazaId() != null) {
			mapExclusion.put("plazaId", exclusion.getPlazaId());
		}
		if (exclusion.getLaneId() != null) {
			mapExclusion.put("laneId", exclusion.getLaneId());
		}
		if (exclusion.getLaneMode() != null) {
			mapExclusion.put("laneMode", exclusion.getLaneMode());
		}
		if (exclusion.getViolType() != null) {
			mapExclusion.put("violType", exclusion.getViolType());
		}
		if (exclusion.getTollRevenuType() != null) {
			mapExclusion.put("tollRevenuType", exclusion.getTollRevenuType());
		}
		if (exclusion.getExclusionStage() != null) {
			mapExclusion.put("exclusionStage", exclusion.getExclusionStage());
		}
		if (exclusion.getEtcAccountId() != null) {
			mapExclusion.put("etcAccountId", exclusion.getEtcAccountId());
		}
		if (exclusion.getPlateState() != null) {
			mapExclusion.put("plateState", exclusion.getPlateState());
		}
		if (exclusion.getPlateNumber() != null) {
			mapExclusion.put("plateNumber", exclusion.getPlateNumber());
		}
		if (exclusion.getVehicleSpeed() != null) {
			mapExclusion.put("vehicleSpeed", exclusion.getVehicleSpeed());
		}
		if (exclusion.getTxTypeInd() != null) {
			mapExclusion.put("txTypeInd", exclusion.getTxTypeInd());
		}
		if (exclusion.getTxSubtypeInd() != null) {
			mapExclusion.put("txSubtypeInd", exclusion.getTxSubtypeInd());
		}
		if (exclusion.getTrxDateTime() != null) {
			mapExclusion.put("trxDateTime", exclusion.getTrxDateTime());
		}

		List<Exclusion> intermediateList = new ArrayList<>();

		if (!mapExclusion.isEmpty()) {
			Set<String> fieldSet = mapExclusion.keySet();

			if (!fieldSet.isEmpty()) {

				intermediateList = exclusionList.stream()
						.filter(e -> exclusion.getTrxDateTime().isAfter(e.getStartDate())
								&& (exclusion.getTrxDateTime().isBefore(e.getEndDate())))
						.collect(Collectors.toList());

				if (!intermediateList.isEmpty() && fieldSet.contains("agencyId")) {
					intermediateList = intermediateList.stream()
							.filter(e -> e.getAgencyId() == mapExclusion.get("agencyId")).collect(Collectors.toList());
				}
				if (!intermediateList.isEmpty() && fieldSet.contains("plazaId")) {
					intermediateList = intermediateList.stream().filter(e -> e.getPlazaId().equals(mapExclusion.get("plazaId")))
							.collect(Collectors.toList());
				}
				if (!intermediateList.isEmpty() && fieldSet.contains("laneId")) {
					intermediateList = intermediateList.stream()
							.filter(e -> e.getLaneId().equals(mapExclusion.get("laneId"))).collect(Collectors.toList());
				}
				if (!intermediateList.isEmpty() && fieldSet.contains("laneMode")) {
					intermediateList = intermediateList.stream()
							.filter(e -> e.getLaneMode() == mapExclusion.get("laneMode")).collect(Collectors.toList());
				}
				if (!intermediateList.isEmpty() && fieldSet.contains("violType")) {
					intermediateList = intermediateList.stream()
							.filter(e -> e.getViolType() == mapExclusion.get("violType")).collect(Collectors.toList());
				}
				if (!intermediateList.isEmpty() && fieldSet.contains("tollRevenuType")) {
					intermediateList = intermediateList.stream()
							.filter(e -> e.getTollRevenuType() == mapExclusion.get("tollRevenuType"))
							.collect(Collectors.toList());
				}
				if (!intermediateList.isEmpty() && fieldSet.contains("exclusionStage")) {
					intermediateList = intermediateList.stream()
							.filter(e -> e.getExclusionStage() == mapExclusion.get("exclusionStage"))
							.collect(Collectors.toList());
				}
				if (!intermediateList.isEmpty() && fieldSet.contains("etcAccountId")) {
					intermediateList = intermediateList.stream()
							.filter(e -> e.getEtcAccountId().equals(mapExclusion.get("etcAccountId")))
							.collect(Collectors.toList());
				}
				if (!intermediateList.isEmpty() && fieldSet.contains("plateState")) {
					intermediateList = intermediateList.stream()
							.filter(e -> e.getPlateState().equals(mapExclusion.get("plateState")))
							.collect(Collectors.toList());
				}
				if (!intermediateList.isEmpty() && fieldSet.contains("plateNumber")) {
					intermediateList = intermediateList.stream()
							.filter(e -> e.getPlateNumber().equals(mapExclusion.get("plateNumber")))
							.collect(Collectors.toList());
				}
				if (!intermediateList.isEmpty() && fieldSet.contains("vehicleSpeed")) {
					intermediateList = intermediateList.stream()
							.filter(e -> e.getVehicleSpeed() == mapExclusion.get("vehicleSpeed"))
							.collect(Collectors.toList());
				}
				if (!intermediateList.isEmpty() && fieldSet.contains("txTypeInd")) {
					intermediateList = intermediateList.stream()
							.filter(e -> e.getTxTypeInd().equals(mapExclusion.get("txTypeInd")))
							.collect(Collectors.toList());
				}
				if (!intermediateList.isEmpty() && fieldSet.contains("txSubtypeInd")) {
					intermediateList = intermediateList.stream()
							.filter(e -> e.getTxSubtypeInd().equals(mapExclusion.get("txSubtypeInd")))
							.collect(Collectors.toList());
				}

			}

		} else {
			intermediateList = exclusionList.stream().filter(e -> exclusion.getTrxDateTime().isAfter(e.getStartDate())
					&& (exclusion.getTrxDateTime().isBefore(e.getEndDate()))).collect(Collectors.toList());
		}

		if (intermediateList.isEmpty()) {
			return ExclusionRecordsStatus;
		}

		return ExclusionRecordsStatus = true;

	}

}
