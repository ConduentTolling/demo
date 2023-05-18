package com.conduent.tpms.qatp.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.conduent.tpms.qatp.config.LoadJpaQueries;
import com.conduent.tpms.qatp.constants.ExclusionConstants;
import com.conduent.tpms.qatp.dao.ExclusionDao;
import com.conduent.tpms.qatp.dto.Exclusion;

@Repository
public class ExclusionDaoImpl implements ExclusionDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private NamedParameterJdbcTemplate namedJdbcTemplate;
	@Autowired
	@Qualifier("OracleDataSource")
	DataSource dataSource;

	@Autowired
	DataSource h2DataSource;
	List<Exclusion> list = new ArrayList<Exclusion>();
	public boolean isExclude = false;

	@Override
	public List<Exclusion> getDataFromOracleDb() throws SQLException {
		Connection oracle = dataSource.getConnection();
		String queryToLoadData = LoadJpaQueries.getQueryById("SELECT_RECORDS_FROM_ORACLE");
		PreparedStatement stmt = oracle.prepareStatement(queryToLoadData);
		ResultSet rs = stmt.executeQuery();
		Integer counter = 0;
		List<Exclusion> exclusionList =  new ArrayList<>();
		
		Exclusion exclusion = null;

		try {
			while (rs.next()) {

				exclusion = new Exclusion(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getInt(4), rs.getInt(5),
						rs.getInt(6), rs.getInt(7), rs.getInt(8), rs.getString(9), rs.getString(10), rs.getInt(11),
						rs.getString(12), rs.getString(13), rs.getTimestamp(14).toLocalDateTime(),
						rs.getTimestamp(15).toLocalDateTime(), rs.getInt(16), rs.getString(17), rs.getString(18),
						rs.getTimestamp(19));

				System.out.println(exclusion);
				exclusionList.add(exclusion);

				/*
				 * jdbcTemplate.update(
				 * "insert into T_VIOL_TX_EXCLUSION (AGENCY_ID,PLAZA_ID,LANE_ID,LANE_MODE,VIOL_TYPE,TOLL_REVENUE_TYPE,EXCLUSION_STAGE,ETC_ACCOUNT_ID,PLATE_STATE,PLATE_NUMBER,VEHICLE_SPEED,TX_TYPE_IND,TX_SUBTYPE_IND,START_DATE_TIME,END_DATE_TIME,EMP_ID,PEL_NAME,CSC_LOOKUP_KEY,UPDATE_TS) "
				 * + "values(?, ?, ?, ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)", new Object[] {
				 * exclusion.getAgencyId(), exclusion.getPlazaId(), exclusion.getLaneId(),
				 * exclusion.getLaneMode(), exclusion.getViolType(),
				 * exclusion.getTollRevenuType(), exclusion.getExclusionStage(),
				 * 
				 * exclusion.getEtcAccountId(), exclusion.getPlateState(),
				 * exclusion.getPlateNumber(), exclusion.getVehicleSpeed(),
				 * exclusion.getTxTypeInd(), exclusion.getTxSubtypeInd(),
				 * exclusion.getStartDate(), exclusion.getEndDate(), exclusion.getEmpId(),
				 * exclusion.getPelName(), exclusion.getCscLookUp(), exclusion.getUpdateTs() });
				 */

				counter++;

			}
			System.out.println("Total Records in exclusionList " + exclusionList);
            System.out.println("Total Records Inserted into h2 Db " + counter);
			rs.close();
		} catch (SQLException e) {
			System.out.println(e);
			e.printStackTrace();
		}
		oracle.close();
		return exclusionList;
	}

	public boolean checkExclusion(Exclusion obj) throws SQLException {

		List<Exclusion> exclusionRecords = new ArrayList<Exclusion>();

		String query = "SELECT * FROM T_VIOL_TX_EXCLUSION WHERE ('" + obj.getTrxDateTime()
				+ "' BETWEEN  START_DATE_TIME AND END_DATE_TIME)AND " + " NVL(EXCLUSION_STAGE,0)=NVL("
				+ obj.getExclusionStage() + ",0)AND NVL(AGENCY_ID,0)=NVL(" + obj.getAgencyId()
				+ ",0) AND NVL(PLAZA_ID,0)=NVL(" + obj.getPlazaId() + ",0) AND NVL(LANE_ID,0)=NVL(" + obj.getLaneId()
				+ ",0) AND NVL(LANE_MODE,0)=NVL(" + obj.getLaneMode() + ",0)AND NVL(VIOL_TYPE,0)=NVL("
				+ obj.getViolType() + ",0)AND NVL(TOLL_REVENUE_TYPE,0)=NVL(" + obj.getTollRevenuType()
				+ ",0)AND((PLATE_STATE is null AND PLATE_NUMBER is null) OR (PLATE_STATE='" + obj.getPlateState()
				+ "' AND PLATE_NUMBER='" + obj.getPlateNumber() + "')) AND NVL(VEHICLE_SPEED,0)=NVL("
				+ obj.getVehicleSpeed() + ",0)AND NVL(ETC_ACCOUNT_ID,0)=NVL(" + obj.getEtcAccountId()
				+ ",0) AND((TX_TYPE_IND is null AND TX_SUBTYPE_IND  is null)OR(TX_TYPE_IND='" + obj.getTxTypeInd()
				+ "' AND TX_SUBTYPE_IND='" + obj.getTxSubtypeInd() + "'))";

		/*
		 * String sql = "SELECT * FROM T_VIOL_TX_EXCLUSION WHERE ('" +
		 * obj.getTrxDateTime() +
		 * "' BETWEEN  START_DATE_TIME AND END_DATE_TIME)AND(EXCLUSION_STAGE is null OR EXCLUSION_STAGE=0 OR EXCLUSION_STAGE="
		 * + obj.getExclusionStage() +
		 * ") AND (AGENCY_ID is null OR AGENCY_ID=0 OR AGENCY_ID=" + obj.getAgencyId() +
		 * ") AND (PLAZA_ID=null OR PLAZA_ID=0 OR PLAZA_ID=" + obj.getPlazaId() +
		 * ")AND(LANE_ID is null OR LANE_ID=0 OR LANE_ID=" + obj.getLaneId() +
		 * ")AND(LANE_MODE is null OR LANE_MODE=0 OR LANE_MODE=" + obj.getLaneMode() +
		 * ")AND(VIOL_TYPE is null OR VIOL_TYPE=0 OR VIOL_TYPE=" + obj.getViolType() +
		 * ")AND(TOLL_REVENUE_TYPE is null OR TOLL_REVENUE_TYPE=0 OR TOLL_REVENUE_TYPE="
		 * + obj.getTollRevenuType() +
		 * ")AND(PLATE_STATE is null AND PLATE_NUMBER=null OR PLATE_STATE='" +
		 * obj.getPlateState() + "'AND PLATE_NUMBER='" + obj.getPlateNumber() +
		 * "')AND (VEHICLE_SPEED is null OR VEHICLE_SPEED=0 OR VEHICLE_SPEED=" +
		 * obj.getVehicleSpeed() +
		 * ")AND(ETC_ACCOUNT_ID is null OR ETC_ACCOUNT_ID=0 OR ETC_ACCOUNT_ID=" +
		 * obj.getEtcAccountId() +
		 * ")AND(TX_TYPE_IND is null AND TX_SUBTYPE_IND is null OR TX_TYPE_IND='" +
		 * obj.getTxTypeInd() + "'AND TX_SUBTYPE_IND='" + obj.getTxSubtypeInd() + "')";
		 */

		// insertRecordsinOracleDB(); //This method is for inserting random dummy data
		// in oracle DB

		System.out.println("Query--" + query);
		exclusionRecords = namedJdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(Exclusion.class));
		if (exclusionRecords.size() > 0) {
			System.out.println("Excluded Record: " + exclusionRecords);
			return true;
		}
		return false;

	}

	public void insertRecordsinOracleDB() throws SQLException {
		Connection oracle = dataSource.getConnection();
		PreparedStatement pstmt = null;
		for (int j = 0; j < 1000; j++) {

			int leftLimit = 48;
			int rightLimit = 122;
			int targetStringLength = 2;
			int targetStringLength2 = 10;
			int targetStringLength3 = 1;
			int targetStringLength4 = 1;

			Random rand = new Random();

			String plateState = rand.ints(leftLimit, rightLimit + 1)
					.filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97)).limit(targetStringLength)
					.collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();

			String plateNumber = rand.ints(leftLimit, rightLimit + 1)
					.filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97)).limit(targetStringLength2)
					.collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();

			String txTypeInd = rand.ints(leftLimit, rightLimit + 1)
					.filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97)).limit(targetStringLength3)
					.collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();

			String txSubtypeInd = rand.ints(leftLimit, rightLimit + 1)
					.filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97)).limit(targetStringLength4)
					.collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();

			int minDay = (int) LocalDate.of(2010, 1, 1).toEpochDay();
			int maxDay = (int) LocalDate.of(2021, 1, 1).toEpochDay();
			long randomDay = minDay + rand.nextInt(maxDay - minDay);
			long randomDay2 = minDay + rand.nextInt(maxDay - minDay);

			LocalDate randomStartDate = LocalDate.ofEpochDay(randomDay);
			LocalDate randomEndDate = LocalDate.ofEpochDay(randomDay2);

			pstmt = oracle.prepareStatement(
					"insert into T_VIOL_TX_EXCLUSION (AGENCY_ID,PLAZA_ID,LANE_ID,LANE_MODE,VIOL_TYPE,TOLL_REVENUE_TYPE,EXCLUSION_STAGE,ETC_ACCOUNT_ID,PLATE_STATE,PLATE_NUMBER,VEHICLE_SPEED,TX_TYPE_IND,TX_SUBTYPE_IND,START_DATE_TIME,END_DATE_TIME,EMP_ID,PEL_NAME,CSC_LOOKUP_KEY)values(?, ?, ?, ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			pstmt.setInt(1, rand.nextInt(9) + 1);
			pstmt.setInt(2, rand.nextInt(9) + 1);
			pstmt.setInt(3, rand.nextInt(9) + 1);
			pstmt.setInt(4, rand.nextInt(9) + 1);
			pstmt.setInt(5, rand.nextInt(9) + 1);
			pstmt.setInt(6, rand.nextInt(9) + 1);
			pstmt.setInt(7, 0);
			pstmt.setInt(8, 0);
			pstmt.setString(9, plateState);
			pstmt.setString(10, plateNumber);
			pstmt.setInt(11, rand.nextInt(9) + 1);
			pstmt.setString(12, "*");
			pstmt.setString(13, "*");
			pstmt.setDate(14, Date.valueOf(randomStartDate));
			pstmt.setDate(15, Date.valueOf(randomEndDate));
			pstmt.setInt(16, rand.nextInt(9) + 1);
			pstmt.setString(17, txTypeInd);
			pstmt.setString(18, txSubtypeInd);
			pstmt.executeUpdate();

		}
	}
}
