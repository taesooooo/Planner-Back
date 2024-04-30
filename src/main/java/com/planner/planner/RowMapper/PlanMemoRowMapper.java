package com.planner.planner.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.planner.planner.Dto.PlanMemoDto;

public class PlanMemoRowMapper implements RowMapper<PlanMemoDto> {

	@Override
	public PlanMemoDto mapRow(ResultSet rs, int rowNum) throws SQLException {
		return PlanMemoDto.builder()
				.memoId(rs.getInt("memo_id"))
				.title(rs.getString("title"))
				.content(rs.getString("content"))
				.createDate(rs.getTimestamp("create_date").toLocalDateTime())
				.updateDate(rs.getTimestamp("update_date").toLocalDateTime())
				.build();
	}

}
