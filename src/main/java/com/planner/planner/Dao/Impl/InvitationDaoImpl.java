package com.planner.planner.Dao.Impl;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.planner.planner.Dao.InvitationDao;
import com.planner.planner.Dto.InvitationDto;
import com.planner.planner.RowMapper.InvitationRowMapper;

@Repository
public class InvitationDaoImpl implements InvitationDao {
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	private static final String INSERT_INVITATION_SQL = "INSERT INTO invitation(account_id, planner_id, invite_date, expire_date) VALUES "
			+ "(:accountId, :plannerId, NOW(), DATE_ADD(NOW(), INTERVAL 7 DAY));";
	private static final String SELECT_BY_ID_INVITATION_SQL = "SELECT id, account_id, planner_id, invite_date, expire_date FROM invitation WHERE id = :id;";
	private static final String DELETE_BY_ID_INVITATION_SQL = "DELETE FROM invitation WHERE id = :id;";
	
	public InvitationDaoImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
	}

	@Override
	public int createInvitation(InvitationDto invitationDto) throws Exception {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource()
				.addValue("accountId", invitationDto.getAccountId())
				.addValue("plannerId", invitationDto.getPlannerId());
		
		namedParameterJdbcTemplate.update(INSERT_INVITATION_SQL, mapSqlParameterSource, keyHolder, new String[] {"inviteId"});
		
		return keyHolder.getKey().intValue();
	}

	@Override
	public InvitationDto findById(int id) throws Exception {
		MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource()
				.addValue("id", id);
		try {
			return namedParameterJdbcTemplate.queryForObject(SELECT_BY_ID_INVITATION_SQL, mapSqlParameterSource, new InvitationRowMapper());			
		}
		catch(DataAccessException e) {
			return null;
		}
	}

	@Override
	public void deleteById(int id) throws Exception {
		MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource()
				.addValue("id", id);
		
		namedParameterJdbcTemplate.update(DELETE_BY_ID_INVITATION_SQL, mapSqlParameterSource);
	}

}
