package com.conduent.tollposting.dao;

import java.util.List;

import com.conduent.tollposting.dto.QatpStatistics;

public interface QatpStatisticsDao {

public List<QatpStatistics> getStatsRecords(Long externFileId);

public void updateStatisticsRecord(List<QatpStatistics> qStatsList);

public void updateTranDetailRecord(List<QatpStatistics> qStatsList, Long laneTxId);

}
