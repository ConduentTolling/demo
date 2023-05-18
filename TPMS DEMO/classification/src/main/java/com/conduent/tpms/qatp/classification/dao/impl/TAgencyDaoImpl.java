package com.conduent.tpms.qatp.classification.dao.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.conduent.tpms.qatp.classification.config.LoadJpaQueries;
import com.conduent.tpms.qatp.classification.constants.QatpClassificationConstant;
import com.conduent.tpms.qatp.classification.dao.TAgencyDao;
import com.conduent.tpms.qatp.classification.model.Agency;
import com.conduent.tpms.qatp.classification.model.AgencyInfoVO;

/**
 * Agency Dao Implementation
 * 
 * @author deepeshb
 *
 */
@Repository
public class TAgencyDaoImpl implements TAgencyDao {
	
	private static final Logger dao_log = LoggerFactory.getLogger(TAgencyDaoImpl.class);

	@Autowired
	private JdbcTemplate jdbcTemplate;

	/**
	 *Get Agency Info
	 */
	@Override
	public List<Agency> getAll() {
		String queryToCheckFile = LoadJpaQueries.getQueryById(QatpClassificationConstant.GET_T_AGENCY);
		return jdbcTemplate.query(queryToCheckFile, new BeanPropertyRowMapper<Agency>(Agency.class));
	}

	/**
	 *Get Schedule Pricing Info By Agency Id
	 */
	@Override
    public Agency getAgencySchedulePricingById(Integer agencyId) {
        String queryToCheckFile = LoadJpaQueries.getQueryById(QatpClassificationConstant.GET_T_AGENCY_SCHEDULE_PRICING_BY_ID);
        List<Agency> list = jdbcTemplate.query(queryToCheckFile,new BeanPropertyRowMapper<Agency>(Agency.class)
                ,agencyId);

        if(!list.isEmpty()) {
            return list.get(0);
        }

        return null;
    }

	@Override
	public List<AgencyInfoVO> getAgencyInfoListforAway() {
			
			String queryRules =	LoadJpaQueries.getQueryById("SELECT_INFO_FROM_T_AGENCY_AWAY");
			
			dao_log.info("Agency info fetched from T_Agency table successfully.");
			
			return jdbcTemplate.query(queryRules, new BeanPropertyRowMapper<AgencyInfoVO>(AgencyInfoVO.class));
			
		}
	
	@Override
	public List<AgencyInfoVO> getAgencyInfoListforHome() {
			
			String queryRules =	LoadJpaQueries.getQueryById("SELECT_INFO_FROM_T_AGENCY_HOME");
			
			dao_log.info("Agency info fetched from T_Agency table successfully.");
			
			return jdbcTemplate.query(queryRules, new BeanPropertyRowMapper<AgencyInfoVO>(AgencyInfoVO.class));
			
		}

}
