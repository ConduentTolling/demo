package com.conduent.tpms.ictx.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.conduent.tpms.ictx.config.LoadJpaQueries;
import com.conduent.tpms.ictx.constants.IctxConstant;
import com.conduent.tpms.ictx.dao.SequenceDao;

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
		System.out.println("queryName::"+queryName);//remove while commit
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
		case "0001":
			return "GET_ICTX_SEQUENCE_FILE_001";
		case "0002":
			return "GET_ICTX_SEQUENCE_FILE_002";
		case "0003":
			return "GET_ICTX_SEQUENCE_FILE_003";
		case "0004":
			return "GET_ICTX_SEQUENCE_FILE_004";
		case "0005":
			return "GET_ICTX_SEQUENCE_FILE_005";
		case "0006":
			return "GET_ICTX_SEQUENCE_FILE_006";
		case "0007":
			return "GET_ICTX_SEQUENCE_FILE_007";
		case "0008":
			return "GET_ICTX_SEQUENCE_FILE_008";
		case "0009":
			return "GET_ICTX_SEQUENCE_FILE_009";
		case "0010":
			return "GET_ICTX_SEQUENCE_FILE_010";
		case "0011":
			return "GET_ICTX_SEQUENCE_FILE_011";
		case "0012":
			return "GET_ICTX_SEQUENCE_FILE_012";
		case "0013":
			return "GET_ICTX_SEQUENCE_FILE_013";
		case "0014":
			return "GET_ICTX_SEQUENCE_FILE_014";
		case "0015":
			return "GET_ICTX_SEQUENCE_FILE_015";
		case "0016":
			return "GET_ICTX_SEQUENCE_FILE_016";
		case "0017":
			return "GET_ICTX_SEQUENCE_FILE_017";
		case "0018":
			return "GET_ICTX_SEQUENCE_FILE_018";
		case "0019":
			return "GET_ICTX_SEQUENCE_FILE_019";
		case "0020":
			return "GET_ICTX_SEQUENCE_FILE_020";
		case "0021":
			return "GET_ICTX_SEQUENCE_FILE_021";
		case "0022":
			return "GET_ICTX_SEQUENCE_FILE_022";
		case "0023":
			return "GET_ICTX_SEQUENCE_FILE_023";
		case "0024":
			return "GET_ICTX_SEQUENCE_FILE_024";
		case "0025":
			return "GET_ICTX_SEQUENCE_FILE_025";
		case "0026":
			return "GET_ICTX_SEQUENCE_FILE_026";
		case "0027":
			return "GET_ICTX_SEQUENCE_FILE_027";
		case "0028":
			return "GET_ICTX_SEQUENCE_FILE_028";
		case "0029":
			return "GET_ICTX_SEQUENCE_FILE_029";
		case "0030":
			return "GET_ICTX_SEQUENCE_FILE_030";
		case "0031":
			return "GET_ICTX_SEQUENCE_FILE_031";
		case "0032":
			return "GET_ICTX_SEQUENCE_FILE_032";
		case "0033":
			return "GET_ICTX_SEQUENCE_FILE_033";
		case "0036":
			return "GET_ICTX_SEQUENCE_FILE_036";
		case "0044":
			return "GET_ICTX_SEQUENCE_FILE_044";
		case "0099":
			return "GET_ICTX_SEQUENCE_FILE_099";
		case "0045":
			return "GET_ICTX_SEQUENCE_FILE_0045";
		case "0120":
			return "GET_ICTX_SEQUENCE_FILE_0120";
		case "0125":
			return "GET_ICTX_SEQUENCE_FILE_0125";
		case "0128":
			return "GET_ICTX_SEQUENCE_FILE_0128";
		case "0130":
			return "GET_ICTX_SEQUENCE_FILE_0130";
		case "0131":
			return "GET_ICTX_SEQUENCE_FILE_0131";
		case "0132":
			return "GET_ICTX_SEQUENCE_FILE_0132";
		case "9003":
			return "GET_ICTX_SEQUENCE_FILE_HUB";

		}
		return null;
	}

	@Override
	public Long getAtpFileSequence() {
		String queryFile = LoadJpaQueries.getQueryById(IctxConstant.GET_ATP_FILE_SEQUENCE);
		return jdbcTemplate.queryForObject(queryFile, Long.class);
	}

}
