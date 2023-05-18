package com.conduent.tpms.qatp;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.conduent.tpms.qatp.config.OracleConfiguration;
import com.conduent.tpms.qatp.dao.impl.ExclusionDaoImpl;
import com.conduent.tpms.qatp.dto.Exclusion;
import com.conduent.tpms.qatp.service.impl.ExclusionServiceImpl;

@ExtendWith(MockitoExtension.class)
class QatpApplicationTests {

	@InjectMocks
	ExclusionServiceImpl exclusionServiceImpl;

	@InjectMocks
	ExclusionDaoImpl exclusionDaompl;

	@Mock
	NamedParameterJdbcTemplate namedJdbcTemplate;

	@Mock
	OracleConfiguration oracle;

	@Test
	void TestProcessCheckExclusionPositiveCase() throws SQLException {
		Exclusion obj = new Exclusion();
		obj.setAgencyId(1);
		obj.setPlazaId(0);
		obj.setLaneId(0);
		obj.setLaneMode(1);
		obj.setViolType(3);
		obj.setPlateState("NY");
		obj.setPlateNumber("302DC");
		obj.setEtcAccountId(0);
		obj.setExclusionStage(0);
		obj.setVehicleSpeed(0);
		LocalDateTime aDateTime = LocalDateTime.of(2021, Month.MAY, 15, 19, 30, 40);
		obj.setTrxDateTime(aDateTime);

		List<Exclusion> exclusionRecords = new ArrayList<Exclusion>();
		exclusionRecords.add(obj);

		Mockito.when(namedJdbcTemplate.query(Mockito.anyString(), Mockito.any(BeanPropertyRowMapper.class)))
				.thenReturn(exclusionRecords);

		Assert.assertEquals(true, exclusionDaompl.checkExclusion(obj));
	}

	@Test
	void TestProcessCheckExclusionNegativeCase() throws SQLException {
		Exclusion obj = new Exclusion();
		obj.setAgencyId(1);
		obj.setPlazaId(0);
		obj.setLaneId(0);
		obj.setLaneMode(1);
		obj.setViolType(3);
		obj.setPlateState("NY");
		obj.setPlateNumber("302DC");
		obj.setEtcAccountId(0);
		obj.setExclusionStage(0);
		obj.setVehicleSpeed(0);
		LocalDateTime aDateTime = LocalDateTime.of(2021, Month.MAY, 15, 19, 30, 40);
		obj.setTrxDateTime(aDateTime);
		List<Exclusion> exclusionRecords = new ArrayList<Exclusion>();

		Mockito.when(namedJdbcTemplate.query(Mockito.anyString(), Mockito.any(BeanPropertyRowMapper.class)))
				.thenReturn(exclusionRecords);

		Assert.assertEquals(false, exclusionDaompl.checkExclusion(obj));
	}

	
}

	
