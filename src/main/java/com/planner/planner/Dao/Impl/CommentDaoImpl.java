package com.planner.planner.Dao.Impl;

import java.sql.PreparedStatement;
import java.sql.Types;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.planner.planner.Dao.CommentDao;
import com.planner.planner.Dto.CommentDto;
import com.planner.planner.RowMapper.CommentRowMapper;

@Repository
public class CommentDaoImpl implements CommentDao {

	private JdbcTemplate jdbcTemplate;
	private KeyHolder keyHolder;
	
	private final String INSERT_COMMENT_SQL = "INSERT INTO review_comment(review_id, writer_id, content, parent_id, create_date, update_date) VALUES "
			+ "(?, ?, ?, ?, NOW(), NOW())";
	private final String SELECT_COMMENT_BY_REVIEW_ID_SQL = "SELECT RC.comment_id, RC.review_id, RC.writer_id, RC.content, RC.parent_id, RC.create_date, RC.update_date, A.nickname AS writer FROM review_comment AS RC\r\n"
			+ "LEFT JOIN account AS A ON A.account_id = RC.writer_id\r\n"
			+ "WHERE review_id = ?;";
	private final String SELECT_COMMENT_BY_COMMENT_ID_SQL = "SELECT RC.comment_id, RC.review_id, RC.writer_id, RC.content, RC.parent_id, RC.create_date, RC.update_date, A.nickname AS writer FROM review_comment AS RC\r\n"
			+ "LEFT JOIN account AS A ON A.account_id = RC.writer_id\r\n"
			+ "WHERE comment_id = ?;";
	private final String UPDATE_COMMENT_SQL = "UPDATE review_comment SET content = ?, update_date = NOW() WHERE review_id = ? AND comment_id = ?";
	private final String DELETE_COMMENT_SQL = "UPDATE review_comment SET writer_id = NULL, content = '', update_date = NOW() WHERE review_id = ? AND comment_id = ?";
	
	public CommentDaoImpl(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
		this.keyHolder = new GeneratedKeyHolder();
	}

	@Override
	public int insertComment(int accountId, int reviewId, CommentDto comment) throws Exception {
		int result = jdbcTemplate.update(conn -> {
			PreparedStatement ps = conn.prepareStatement(INSERT_COMMENT_SQL, new String[] {"comment_id"});
			ps.setInt(1, reviewId);
			ps.setInt(2, accountId);
			ps.setString(3, comment.getContent());
			ps.setObject(4, comment.getParentId(), Types.INTEGER);
			return ps;
		},keyHolder);
		
		return keyHolder.getKey().intValue();
	}

	@Override
	public CommentDto findById(int commentId) throws Exception {
		CommentDto comment = jdbcTemplate.queryForObject(SELECT_COMMENT_BY_COMMENT_ID_SQL, new CommentRowMapper(), commentId);
		return comment;
	}
	
	@Override
	public List<CommentDto> findAll(int reviewId) throws Exception {
		List<CommentDto> list = jdbcTemplate.query(SELECT_COMMENT_BY_REVIEW_ID_SQL, new CommentRowMapper(), reviewId);
		return list;
	}

	@Override
	public void updateComment(int reviewId, CommentDto comment) throws Exception {
		jdbcTemplate.update(UPDATE_COMMENT_SQL, comment.getContent(), reviewId, comment.getCommentId());
	}

	@Override
	public void deleteComment(int reviewId, int commentId) throws Exception {
		jdbcTemplate.update(DELETE_COMMENT_SQL, reviewId, commentId);
	}
	
}
