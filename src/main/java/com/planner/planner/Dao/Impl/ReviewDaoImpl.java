package com.planner.planner.Dao.Impl;

import java.sql.PreparedStatement;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.planner.planner.Common.PageInfo;
import com.planner.planner.Dao.ReviewDao;
import com.planner.planner.Dto.AccountDto;
import com.planner.planner.Dto.ReviewDto;
import com.planner.planner.RowMapper.ReviewRowMapper;

@Repository
public class ReviewDaoImpl implements ReviewDao {
	private static final Logger logger = LoggerFactory.getLogger(ReviewDaoImpl.class);
	
	private JdbcTemplate jdbcTemplate;
	private KeyHolder keyHolder;
	
	private final String INSERT_REVIEW_SQL = "INSERT INTO review(planner_id, title, content, writer, writer_id, create_date, update_date) VALUES(?, ?, ?, ?, ?, now(), now());";
	private final String FIND_ALL_REVIEW_SQL = "SELECT review_id, planner_id, title, writer, writer_id, content, like_count, create_date, update_date FROM review ORDER BY review_id LIMIT ?, ?;";
	private final String FIND_REVIEW_SQL = "SELECT review_id, planner_id, title, content, writer, writer_id, like_count, create_date, update_date FROM review WHERE review_id = ?;";
	private final String UPDATE_REVIEW_SQL = "UPDATE review SET title = ?, content = ?, update_date = now() WHERE review_id = ?;";
	private final String DELETE_UPDATE_SQL = "DELETE FROM review WHERE review_id = ?;";
	private final String FIND_TOTAL_COUNT_SQL = "SELECT count(*) FROM review;";
	
	public ReviewDaoImpl(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
		this.keyHolder = new GeneratedKeyHolder();
	}

	@Override
	public int insertReview(ReviewDto reviewDto, AccountDto accountDto) {
		int result = jdbcTemplate.update(conn -> {
			PreparedStatement ps = conn.prepareStatement(INSERT_REVIEW_SQL, new String[] {"review_id"});
			ps.setInt(1, reviewDto.getPlannerId());
			ps.setString(2, reviewDto.getTitle());
			ps.setString(3, reviewDto.getContent());
			ps.setString(4, reviewDto.getWriter());
			ps.setInt(5, accountDto.getAccountId());
			return ps;
		}, keyHolder);
		return keyHolder.getKey().intValue();
	}

	@Override
	public List<ReviewDto> findAllReview(PageInfo pageInfo) {
		List<ReviewDto> list = jdbcTemplate.query(FIND_ALL_REVIEW_SQL, new ReviewRowMapper(), pageInfo.getPageOffSet(), pageInfo.getPageItemCount());			
		return list;
	}

	@Override
	public ReviewDto findReview(int reviewId) {
		List<ReviewDto> list = jdbcTemplate.query(FIND_REVIEW_SQL, new ReviewRowMapper(), reviewId);
		ReviewDto review = DataAccessUtils.singleResult(list);
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
	public int findTotalCount() {
		int result = jdbcTemplate.queryForObject(FIND_TOTAL_COUNT_SQL, Integer.class);
		return result;
	}

}
