package com.conduent.Ibtsprocessing.daoimpl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.conduent.Ibtsprocessing.constant.LoadJpaQueries;
import com.conduent.Ibtsprocessing.dao.IXferControlDao;
import com.conduent.Ibtsprocessing.model.TxTypeMapping;
import com.conduent.Ibtsprocessing.model.XferControl;

@Repository
public class XferControlDao implements IXferControlDao {
	
	private static final Logger logger = LoggerFactory.getLogger(XferControlDao.class);

	@Autowired
	private NamedParameterJdbcTemplate namedJdbcTemplate;
	
	List<XferControl> XferControlDetails=new ArrayList<XferControl>();


	@Override
	public XferControl getXferControlDate(Long externFileId) {
		
		String queryRules =	 LoadJpaQueries.getQueryById("GET_T_XFER_CONTROL");
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		
		paramSource.addValue("XFER_CONTROL_ID", externFileId);
		logger.info("Xfer Control Data fetched from T_XFER_CONTROL table successfully.");
		
		List<XferControl> listOfObj = namedJdbcTemplate.query(queryRules, paramSource,BeanPropertyRowMapper.newInstance(XferControl.class));
		if(listOfObj.size()>0) {
			return listOfObj.get(0);
		}
		return null;
		
	}

}
