package com.conduent.tpms.qatp.classification.factory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.conduent.tpms.qatp.classification.service.QatpClassificationService;
import com.conduent.tpms.qatp.classification.service.impl.PAClassificationServiceImpl;
import com.conduent.tpms.qatp.classification.service.impl.QatpClassificationServiceImpl;

/**
 * Factory of QATP classification process object
 * 
 * @author deepeshb
 *
 */
@Component
public class QatpClassificationFactory {

	private static final Logger logger = LoggerFactory.getLogger(QatpClassificationFactory.class);

	@Autowired
	QatpClassificationServiceImpl qatpClassificationServiceImpl;

	@Autowired
	PAClassificationServiceImpl pAClassificationServiceImpl;

	/**
	 * Get QATP classification object based on plaza agency id
	 * 
	 * @param plazaAgencyId
	 * @return
	 */
	public QatpClassificationService getQatpClassificationObject(int plazaAgencyId) {
		switch (plazaAgencyId) {
		case 1:
		case 2:
			// NYSTA and MTA AGENCY
			logger.info("NYSTA or MTA classification process");
			return qatpClassificationServiceImpl;
		case 3:
			// PA AGENCY
			logger.info("PA classification process");
			return pAClassificationServiceImpl;
		default:
			return qatpClassificationServiceImpl;
		}
	}

}
