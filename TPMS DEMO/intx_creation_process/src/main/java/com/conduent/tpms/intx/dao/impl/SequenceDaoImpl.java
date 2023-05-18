package com.conduent.tpms.intx.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.conduent.tpms.intx.config.LoadJpaQueries;
import com.conduent.tpms.intx.constants.IntxConstant;
import com.conduent.tpms.intx.dao.SequenceDao;

/**
 * File Sequence Dao Implementation
 * 
 * @author deepeshb
 *
 */
@Repository
public class SequenceDaoImpl implements SequenceDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	/**
	 * Get File sequence based on device prefix
	 */
	public Long getFileSequence(String devicePrefix) {
		String queryName = getQueryName(devicePrefix);
		if (queryName != null) {
			String queryFile = LoadJpaQueries.getQueryById(queryName);
			return jdbcTemplate.queryForObject(queryFile, Long.class);
		} else {
			return null;
		}
	}

	/**
	 * Get sequence name based on devicePrefix
	 * 
	 * @param devicePrefix
	 * @return String
	 */
	private String getQueryName(String devicePrefix) {
		switch (devicePrefix) {
		case "001":
			return "GET_ICTX_SEQUENCE_FILE_001";
		case "002":
			return "GET_ICTX_SEQUENCE_FILE_002";
		case "003":
			return "GET_ICTX_SEQUENCE_FILE_003";
		case "004":
			return "GET_ICTX_SEQUENCE_FILE_004";
		case "005":
			return "GET_ICTX_SEQUENCE_FILE_005";
		case "006":
			return "GET_ICTX_SEQUENCE_FILE_006";
		case "007":
			return "GET_ICTX_SEQUENCE_FILE_007";
		case "008":
			return "GET_ICTX_SEQUENCE_FILE_008";
		case "009":
			return "GET_ICTX_SEQUENCE_FILE_009";
		case "010":
			return "GET_ICTX_SEQUENCE_FILE_010";
		case "011":
			return "GET_ICTX_SEQUENCE_FILE_011";
		case "012":
			return "GET_ICTX_SEQUENCE_FILE_012";
		case "013":
			return "GET_ICTX_SEQUENCE_FILE_013";
		case "014":
			return "GET_ICTX_SEQUENCE_FILE_014";
		case "015":
			return "GET_ICTX_SEQUENCE_FILE_015";
		case "016":
			return "GET_ICTX_SEQUENCE_FILE_016";
		case "017":
			return "GET_ICTX_SEQUENCE_FILE_017";
		case "018":
			return "GET_ICTX_SEQUENCE_FILE_018";
		case "019":
			return "GET_ICTX_SEQUENCE_FILE_019";
		case "020":
			return "GET_ICTX_SEQUENCE_FILE_020";
		case "021":
			return "GET_ICTX_SEQUENCE_FILE_021";
		case "022":
			return "GET_ICTX_SEQUENCE_FILE_022";
		case "023":
			return "GET_ICTX_SEQUENCE_FILE_023";
		case "024":
			return "GET_ICTX_SEQUENCE_FILE_024";
		case "025":
			return "GET_ICTX_SEQUENCE_FILE_025";
		case "026":
			return "GET_ICTX_SEQUENCE_FILE_026";
		case "027":
			return "GET_ICTX_SEQUENCE_FILE_027";
		case "028":
			return "GET_ICTX_SEQUENCE_FILE_028";
		case "029":
			return "GET_ICTX_SEQUENCE_FILE_029";
		case "030":
			return "GET_ICTX_SEQUENCE_FILE_030";
		case "031":
			return "GET_ICTX_SEQUENCE_FILE_031";
		case "032":
			return "GET_ICTX_SEQUENCE_FILE_032";
		case "033":
			return "GET_ICTX_SEQUENCE_FILE_033";
		case "036":
			return "GET_ICTX_SEQUENCE_FILE_036";
		case "044":
			return "GET_ICTX_SEQUENCE_FILE_044";
		case "099":
			return "GET_ICTX_SEQUENCE_FILE_099";

		}
		return null;
	}

	@Override
	public Long getAtpFileSequence() {
		String queryFile = LoadJpaQueries.getQueryById(IntxConstant.GET_ATP_FILE_SEQUENCE);
		return jdbcTemplate.queryForObject(queryFile, Long.class);
	}

}
