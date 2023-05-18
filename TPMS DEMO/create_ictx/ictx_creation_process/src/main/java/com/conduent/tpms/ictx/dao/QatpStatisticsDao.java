package com.conduent.tpms.ictx.dao;

import com.conduent.tpms.ictx.model.QatpStatistics;

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
