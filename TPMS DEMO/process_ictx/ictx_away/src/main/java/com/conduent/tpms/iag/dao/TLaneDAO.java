package com.conduent.tpms.iag.dao;

import java.util.List;

import com.conduent.tpms.iag.dto.TLaneDto;
import com.conduent.tpms.iag.model.LaneIdExtLaneInfo;

public interface TLaneDAO {

	public List<TLaneDto> getAllTLane();

	public List<LaneIdExtLaneInfo> getAwayExtLanePlazaList();

	public TLaneDto getMaxLaneId();
}
