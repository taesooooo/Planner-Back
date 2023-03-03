package com.planner.planner.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.planner.planner.Dto.FileInfoDto;

public class FileInfoRowMapper implements RowMapper<FileInfoDto> {

	@Override
	public FileInfoDto mapRow(ResultSet rs, int rowNum) throws SQLException {
		return new FileInfoDto(rs.getInt("file_id"),
				rs.getInt("file_writer_id"),
				rs.getInt("file_board_id"), 
				rs.getString("file_name"), 
				rs.getString("file_path"), 
				rs.getString("file_type"), 
				rs.getTimestamp("upload_date").toLocalDateTime());
	}

}
