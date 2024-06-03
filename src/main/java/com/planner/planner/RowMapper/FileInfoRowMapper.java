package com.planner.planner.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.planner.planner.Dto.FileInfoDto;

public class FileInfoRowMapper implements RowMapper<FileInfoDto> {

	@Override
	public FileInfoDto mapRow(ResultSet rs, int rowNum) throws SQLException {
		return FileInfoDto.builder()
				.fileId(rs.getInt("file_id"))
				.fileWriterId(rs.getInt("file_writer_id"))
				.fileBoradId(rs.getInt("file_board_id"))
				.fileName(rs.getString("file_name"))
				.filePath(rs.getString("file_path"))
				.fileType(rs.getString("file_type"))
				.uploadDate(rs.getTimestamp("upload_date").toLocalDateTime())
				.build();
	}

}
