package com.planner.planner.Dao.Impl;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.planner.planner.Dao.FileUploadDao;
import com.planner.planner.Dto.FileInfoDto;
import com.planner.planner.RowMapper.FileInfoRowMapper;

@Repository
public class FileUploadDaoImpl implements FileUploadDao {
	
	private JdbcTemplate jdbcTemplate;
	
	private static final String INSERT_FILEINFO_SQL = "INSERT INTO upload_file_info(file_writer_id, file_board_id, file_name, file_path, file_type, upload_date) VALUES(?, 0, ?, ?, ?, NOW());";
	private static final String SELECT_FILEINFO_BY_FILENAME_SQL = "SELECT file_id, file_writer_id, file_board_id, file_name, file_path, file_type, upload_date FROM upload_file_info WHERE file_name = ?;";
	private static final String SELECT_FILEINFO_BY_FILEID_SQL = "SELECT file_id, file_writer_id, file_board_id, file_name, file_path, file_type, upload_date FROM upload_file_info WHERE file_id = ?;";
	private static final String SELECT_FILEINFO_TEMPLIST_SQL = "SELECT file_id, file_writer_id, file_board_id, file_name, file_path, file_type, upload_date FROM upload_file_info WHERE file_board_id = 0 AND datediff(now(), upload_date) = 7;";
	private static final String UPDATE_FILEINFO_BOARDID_SQL = "UPDATE upload_file_info SET file_board_id = ? WHERE file_name IN(%s);";
	private static final String DELETE_FILEINFO_BY_FILEID_SQL ="DELETE FROM upload_file_info WHERE file_id = ?;";
	
	public FileUploadDaoImpl(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public FileInfoDto getFileInfo(String fileName) {
		try {
			FileInfoDto fileInfo = jdbcTemplate.queryForObject(SELECT_FILEINFO_BY_FILENAME_SQL, new FileInfoRowMapper(), fileName);
			return fileInfo;
		}
		catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public List<FileInfoDto> getTempFileInfoList() {
		List<FileInfoDto> fileInfo = jdbcTemplate.query(SELECT_FILEINFO_TEMPLIST_SQL, new FileInfoRowMapper());
		return fileInfo.isEmpty() ? null : fileInfo;
	}

	@Override
	public void createFileInfo(int userId, FileInfoDto fileInfo) {
		jdbcTemplate.update(INSERT_FILEINFO_SQL, userId, fileInfo.getFileName(), fileInfo.getFilePath(), fileInfo.getFileType());
	}

	@Override
	public void updateBoardId(int boardId, List<String> fileNames) {
		String joinStr = fileNames.stream().map((name) -> "\"" + name + "\"").collect(Collectors.joining(","));
		String sql = String.format(UPDATE_FILEINFO_BOARDID_SQL, joinStr);
		
		jdbcTemplate.update(sql, boardId);
	}

	@Override
	public void deleteFileInfoById(int fileId) {
		jdbcTemplate.update(DELETE_FILEINFO_BY_FILEID_SQL, fileId);
	}

}
