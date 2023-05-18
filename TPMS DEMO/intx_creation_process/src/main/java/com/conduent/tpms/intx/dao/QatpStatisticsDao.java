package com.conduent.tpms.intx.dao;

import com.conduent.tpms.intx.model.QatpStatistics;

/**
 * 
 * Qatp Statistics Dao 
 * 
 * @author deepeshb
 *
 */
public interface QatpStatisticsDao {

	/**
	 * Get QatpStatistics Info
	 * 
	 * @param atpFileId
	 * @return QatpStatistics
	 */
	QatpStatistics getQatpStatistics(Long atpFileId);

	QatpStatistics getQatpStatisticsByXferId(Long xferFileId);
	
	public QatpStatistics getQatpStatisticsFromFileName(String fileName);
}
