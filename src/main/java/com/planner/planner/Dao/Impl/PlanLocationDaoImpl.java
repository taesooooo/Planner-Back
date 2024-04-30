package com.planner.planner.Dao.Impl;

import java.sql.PreparedStatement;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.planner.planner.Dao.PlanLocationDao;
import com.planner.planner.Dto.PlanLocationDto;
import com.planner.planner.RowMapper.PlanLocationRowMapper;

@Repository
public class PlanLocationDaoImpl implements PlanLocationDao {
	private static final Logger LOGGER = LoggerFactory.getLogger(PlanLocationDaoImpl.class);
	
	private JdbcTemplate jdbcTemplate;

	private final String INSERT_PLANLOCATION_SQL = "INSERT INTO plan_location(location_name, location_content_id, location_image, location_addr, location_mapx, location_mapy, location_transportation, location_index, plan_id) VALUES"
			+ "(?, ?, ?, ?, ?, ?, ?, (SELECT ifnull(MAX(pl.location_index), 0) + 1024 FROM plan_location AS pl WHERE pl.plan_id = ?), ?);";
	private final String FINDS_PLANLOCATION_SQL = "SELECT plan_location.location_id, plan_location.location_name, plan_location.location_content_id, plan_location.location_image, plan_location.location_addr, plan_location.location_mapx, plan_location.location_mapy, plan_location.location_transportation, plan_location.location_index, plan_location.plan_id "
			+ "FROM plan_location " 
			+ "WHERE plan_location.plan_id = ? "
			+ "ORDER BY plan_location.location_index;";
	private final String UPDATE_PLANLOCATION_SQL = "UPDATE plan_location AS PL SET PL.location_name = ?, PL.location_content_id = ?, PL.location_image = ? , PL.location_addr = ?, PL.location_mapx = ?, PL.location_mapy = ?, PL.location_transportation = ?, PL.location_index = ? WHERE PL.location_id = ?;";
	private final String DELETE_PLANLOCATION_SQL = "DELETE FROM plan_location WHERE plan_location.location_id = ?;";


	public PlanLocationDaoImpl(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public int insertPlanLocation(int planId, PlanLocationDto planLocationDto) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		int result = jdbcTemplate.update(conn -> {
			PreparedStatement ps = conn.prepareStatement(INSERT_PLANLOCATION_SQL, new String[] { "location_id" });
			ps.setString(1, planLocationDto.getLocationName());
			ps.setInt(2, planLocationDto.getLocationContentId());
			ps.setString(3, planLocationDto.getLocationImage());
			ps.setString(4, planLocationDto.getLocationAddr());
			ps.setDouble(5, planLocationDto.getLocationMapx());
			ps.setDouble(6, planLocationDto.getLocationMapy());
			ps.setInt(7, planLocationDto.getLocationTransportation());
			ps.setInt(8, planId);
			ps.setInt(9, planId);
			return ps;
		}, keyHolder);
		
		return keyHolder.getKey().intValue();
	}

	@Override
	public List<PlanLocationDto> findPlanLocationsByPlanId(int planId) {
		return jdbcTemplate.query(FINDS_PLANLOCATION_SQL, new PlanLocationRowMapper(), planId);
	}

	@Override
	public int updatePlanLocation(int planLocationId, PlanLocationDto planLocationDto) {
		int result = jdbcTemplate.update(UPDATE_PLANLOCATION_SQL, 
				planLocationDto.getLocationName(),
				planLocationDto.getLocationContentId(), 
				planLocationDto.getLocationImage(),
				planLocationDto.getLocationAddr(),
				planLocationDto.getLocationMapx(),
				planLocationDto.getLocationMapy(),
				planLocationDto.getLocationTransportation(),
				planLocationDto.getIndex(),
				planLocationDto.getLocationId());
		return result;
	}

	@Override
	public int deletePlanLocation(int planLocationId) {
		int result = jdbcTemplate.update(DELETE_PLANLOCATION_SQL, planLocationId);
		return result;
	}

}
