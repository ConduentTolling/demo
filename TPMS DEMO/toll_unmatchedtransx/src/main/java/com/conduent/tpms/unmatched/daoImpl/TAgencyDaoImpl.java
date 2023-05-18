package com.conduent.tpms.unmatched.daoImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.conduent.tpms.unmatched.constant.LoadJpaQueries;
import com.conduent.tpms.unmatched.constant.UnmatchedConstant;
import com.conduent.tpms.unmatched.dao.TAgencyDao;
import com.conduent.tpms.unmatched.model.Agency;


/**
 * Agency Dao Implementation
 * 
 * @author deepeshb
 *
 */
@Repository
public class TAgencyDaoImpl implements TAgencyDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	/**
	 *Get Agency Info
	 */
	@Override
	public List<Agency> getAll() {
		String queryToCheckFile = LoadJpaQueries.getQueryById(UnmatchedConstant.GET_T_AGENCY);
		return jdbcTemplate.query(queryToCheckFile, new BeanPropertyRowMapper<Agency>(Agency.class));
	}

	/**
	 *Get Schedule Pricing Info By Agency Id
	 */
	@Override
    public Agency getAgencySchedulePricingById(Integer agencyId) {
        String queryToCheckFile = LoadJpaQueries.getQueryById(UnmatchedConstant.GET_T_AGENCY_SCHEDULE_PRICING_BY_ID);
        List<Agency> list = jdbcTemplate.query(queryToCheckFile,new BeanPropertyRowMapper<Agency>(Agency.class)
                ,agencyId);

        if(!list.isEmpty()) {
            return list.get(0);
        }

        return null;
    }

}
