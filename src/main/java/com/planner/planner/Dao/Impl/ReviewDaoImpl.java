package com.planner.planner.Dao.Impl;

import java.sql.PreparedStatement;
import java.sql.Types;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.planner.planner.Common.PageInfo;
import com.planner.planner.Common.SortCriteria;
import com.planner.planner.Dao.ReviewDao;
import com.planner.planner.Dto.AccountDto;
import com.planner.planner.Dto.ReviewDto;
import com.planner.planner.RowMapper.ReviewResultSetExtrator;
import com.planner.planner.RowMapper.ReviewRowMapper;

@Repository
public class ReviewDaoImpl implements ReviewDao {
	private static final Logger logger = LoggerFactory.getLogger(ReviewDaoImpl.class);
	
	private JdbcTemplate jdbcTemplate;
	private KeyHolder keyHolder;
	
	private final String INSERT_REVIEW_SQL = "INSERT INTO review(planner_id, title, content, writer, writer_id, create_date, update_date) VALUES(?, ?, ?, ?, ?, now(), now());";
	private final String FIND_ALL_REVIEW_SQL = "SELECT review_id, planner_id, title, writer, writer_id, content, like_count, create_date, update_date "
			+ "FROM review "; // 동적 쿼리
	private final String FIND_REVIEW_SQL = "SELECT R.review_id, R.planner_id, R.title, R.writer, R.writer_id, R.content, R.like_count, R.create_date, R.update_date, \r\n"
			+ "RC.comment_id AS RC_comment_id, RC.review_id AS RC_review_id, RC.writer_id AS RC_writer_id, RC.content AS RC_content, \r\n"
			+ "RC.parent_id AS RC_parent_id, RC.create_date AS RC_create_date, RC.update_date AS RC_update_date, AC.nickname AS AC_nickname \r\n"
			+ "FROM review AS R \r\n"
			+ "LEFT JOIN review_comment AS RC ON RC.review_id = R.review_id \r\n"
			+ "LEFT JOIN account AS AC ON AC.account_id = RC.writer_id\r\n"
			+ "WHERE R.review_id = ?;";
	private final String UPDATE_REVIEW_SQL = "UPDATE review SET title = ?, content = ?, update_date = now() WHERE review_id = ?;";
	private final String DELETE_UPDATE_SQL = "DELETE FROM review WHERE review_id = ?;";
	private final String FIND_TOTAL_COUNT_SQL = "SELECT count(*) FROM review;";
	private final String FIND_TOTAL_COUNT_KEYWORD_SQL = "SELECT count(*) FROM review WHERE title LIKE \"%%%s%%\";";
	
	public ReviewDaoImpl(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
		this.keyHolder = new GeneratedKeyHolder();
	}

	@Override
	public int insertReview(ReviewDto reviewDto, AccountDto accountDto) {
		int result = jdbcTemplate.update(conn -> {
			PreparedStatement ps = conn.prepareStatement(INSERT_REVIEW_SQL, new String[] {"review_id"});
			ps.setObject(1, reviewDto.getPlannerId(), Types.INTEGER);
			ps.setString(2, reviewDto.getTitle());
			ps.setString(3, reviewDto.getContent());
			ps.setString(4, reviewDto.getWriter());
			ps.setInt(5, accountDto.getAccountId());
			return ps;
		}, keyHolder);
		
		return keyHolder.getKey().intValue();
	}

	@Override
	public List<ReviewDto> findAllReview(SortCriteria sortCriteria, String keyword, PageInfo pageInfo) {
		StringBuilder sb = new StringBuilder(FIND_ALL_REVIEW_SQL);
		
		if(keyword != null) {
			sb.append("WHERE title LIKE \"%").append(keyword).append("%\" ");
		}
		
		if(sortCriteria == SortCriteria.LATEST) {
			sb.append("ORDER BY review_id DESC ");
		}
		else if(sortCriteria == SortCriteria.LIKECOUNT) {
			sb.append("ORDER BY like_count DESC ");
		}
		
		sb.append("LIMIT ?, ?;");
		
		List<ReviewDto> list = jdbcTemplate.query(sb.toString(), new ReviewRowMapper(), pageInfo.getPageOffSet(), pageInfo.getPageItemCount());			
		return list;
	}

	@Override
	public ReviewDto findReview(int reviewId) {
		ReviewDto review = jdbcTemplate.query(FIND_REVIEW_SQL, new ReviewResultSetExtrator(), reviewId);
		return review;
	}

	@Override
	public void updateReview(ReviewDto reviewDto) {
		int result = jdbcTemplate.update(UPDATE_REVIEW_SQL,reviewDto.getTitle(), reviewDto.getContent(), reviewDto.getReviewId());
	}

	@Override
	public void deleteReview(int reviewId) {
		int result = jdbcTemplate.update(DELETE_UPDATE_SQL, reviewId);
	}

	@Override
	public int getTotalCount() {
		int result = jdbcTemplate.queryForObject(FIND_TOTAL_COUNT_SQL, Integer.class);
		return result;
	}

	@Override
	public int getTotalCountByKeyword(String keyword) {
		String sql = String.format(FIND_TOTAL_COUNT_KEYWORD_SQL, keyword);
		
		return jdbcTemplate.queryForObject(sql, Integer.class);
	}
}
