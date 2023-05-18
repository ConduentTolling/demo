package com.conduent.tpms.qatp.dao;

import java.util.List;

import com.conduent.tpms.qatp.dto.TLaneDto;
import com.conduent.tpms.qatp.model.LaneIdExtLaneInfo;

public interface TLaneDAO {

	public List<TLaneDto> getAllTLane();

	public List<LaneIdExtLaneInfo> getAwayExtLanePlazaList();
}
