package com.planner.planner.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.planner.planner.Dto.PlannerDto;

public class PlannerRowMapper implements RowMapper<PlannerDto> {
	@Override
	public PlannerDto mapRow(ResultSet rs, int rowNum) throws SQLException {
		String thumbnail = rs.getString("thumbnail");
		
		return new PlannerDto.Builder()
				.setPlannerId(rs.getInt("planner_id"))
				.setAccountId(rs.getInt("account_id"))
				.setAreaCode(rs.getInt("area_code"))
				.setCreator(rs.getString("creator"))
				.setTitle(rs.getString("title"))
				.setPlanDateStart(rs.getDate("plan_date_start").toLocalDate())
				.setPlanDateEnd(rs.getDate("plan_date_end").toLocalDate())
				.setExpense(rs.getInt("expense"))
				.setMemberCount(rs.getInt("member_count"))
				.setMemberTypeId(rs.getInt("member_type_id"))
				.setLikeCount(rs.getInt("like_count"))
				.setLikeState(rs.getInt("like_id") != 0 ? true : false)
				.setThumbnail(thumbnail == null ? "" : thumbnail)
				.setCreateDate(rs.getTimestamp("create_date").toLocalDateTime())
				.setUpdateDate(rs.getTimestamp("update_date").toLocalDateTime())
				.build();
	}
}

