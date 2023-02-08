package com.planner.planner.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.planner.planner.Dto.PlanMemoDto;

public class PlanMemoRowMapper implements RowMapper<PlanMemoDto> {

	@Override
	public PlanMemoDto mapRow(ResultSet rs, int rowNum) throws SQLException {
		return new PlanMemoDto.Builder()
				.setMemoId(rs.getInt("memo_id"))
				.setTitle(rs.getString("title"))
				.setContent(rs.getString("content"))
				.setCreateDate(rs.getTimestamp("create_date").toLocalDateTime())
				.setUpdateDate(rs.getTimestamp("update_date").toLocalDateTime())
				.build();
	}

}
