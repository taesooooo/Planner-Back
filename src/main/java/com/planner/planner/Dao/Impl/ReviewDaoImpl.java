package com.planner.planner.Dao.Impl;

import java.sql.PreparedStatement;
import java.sql.SQLType;
import java.sql.Types;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.mysql.cj.MysqlType;
import com.planner.planner.Common.PageInfo;
import com.planner.planner.Common.SortCriteria;
import com.planner.planner.Dao.ReviewDao;
import com.planner.planner.Dto.AccountDto;
import com.planner.planner.Dto.CommonRequestParamDto;
import com.planner.planner.Dto.ReviewDto;
import com.planner.planner.RowMapper.ReviewResultSetExtrator;
import com.planner.planner.RowMapper.ReviewRowMapper;

@Repository
public class ReviewDaoImpl implements ReviewDao {
	private static final Logger logger = LoggerFactory.getLogger(ReviewDaoImpl.class);
	
	private JdbcTemplate jdbcTemplate;
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	// 동적 쿼리
	private final String INSERT_REVIEW_SQL = "INSERT INTO review(planner_id, title, content, areacode, thumbnail, writer, writer_id, create_date, update_date) VALUES(:planner_id, :title, :content, :areaCode, :thumbnail, :writer, :writerId, now(), now());";
	// 동적 쿼리
	private final String FIND_ALL_REVIEW_SQL = "SELECT review_id, planner_id, title, content, areacode, thumbnail, writer, writer_id, like_count, create_date, update_date "
			+ "FROM review ";
	private final String FIND_REVIEW_SQL = "SELECT R.review_id, R.planner_id, R.title, R.content, R.thumbnail, R.writer, R.writer_id, R.like_count, R.create_date, R.update_date, \r\n"
			+ "RC.comment_id AS RC_comment_id, RC.review_id AS RC_review_id, RC.writer_id AS RC_writer_id, RC.content AS RC_content, \r\n"
			+ "RC.parent_id AS RC_parent_id, RC.create_date AS RC_create_date, RC.update_date AS RC_update_date, AC.nickname AS AC_nickname \r\n"
			+ "FROM review AS R \r\n"
			+ "LEFT JOIN review_comment AS RC ON RC.review_id = R.review_id \r\n"
			+ "LEFT JOIN account AS AC ON AC.account_id = RC.writer_id\r\n"
			+ "WHERE R.review_id = :reviewId;";
	private final String UPDATE_REVIEW_SQL = "UPDATE review SET title = :title, content = :content, areacode = :areaCode, update_date = now() WHERE review_id = :reviewId;";
	private final String UPDATE_REVIEW_THUMBNAIL_SQL = "UPDATE review SET thumbnail = :thumbnail, update_date = now() WHERE review_id = :reviewId;";
	private final String DELETE_UPDATE_SQL = "DELETE FROM review WHERE review_id = :reviewId;";
	// 동적 쿼리
	private final String FIND_TOTAL_COUNT_SQL = "SELECT count(*) FROM review ";
	private final String FIND_TOTAL_COUNT_KEYWORD_SQL = "SELECT count(*) FROM review WHERE title LIKE \"%%%s%%\";";
	
	public ReviewDaoImpl(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
	}

	@Override
	public int insertReview(ReviewDto reviewDto, AccountDto accountDto, String thumbnailName ) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		
		SqlParameterSource parameterSource = new MapSqlParameterSource()
				.addValue("planner_id", reviewDto.getPlannerId())
				.addValue("title", reviewDto.getTitle())
				.addValue("content", reviewDto.getContent())
				.addValue("areaCode", reviewDto.getAreaCode(), reviewDto.getAreaCode() != null ? Types.INTEGER : Types.NULL)
				.addValue("thumbnail", thumbnailName)
				.addValue("writer", accountDto.getNickname())
				.addValue("writerId", accountDto.getAccountId());

		namedParameterJdbcTemplate.update(INSERT_REVIEW_SQL, parameterSource, keyHolder);
		
		return keyHolder.getKey().intValue();
	}

	@Override
	public List<ReviewDto> findAllReview(CommonRequestParamDto requestParamDto, PageInfo pageInfo) {
		StringBuilder sb = new StringBuilder(FIND_ALL_REVIEW_SQL);
		MapSqlParameterSource parameterSource = new MapSqlParameterSource();
		
		if(requestParamDto.getKeyword() != null) {
			sb.append("WHERE title LIKE :keyword ");
			parameterSource.addValue("keyword", "%"+requestParamDto.getKeyword()+"%");
		}
		
		if(requestParamDto.getAreaCode() != null) {
			sb.append("AND areacode = :areaCode ");
			parameterSource.addValue("areaCode", requestParamDto.getAreaCode());
		}
		
		if(requestParamDto.getSortCriteria() == SortCriteria.LATEST) {
			sb.append("ORDER BY review_id DESC ");
		}
		else if(requestParamDto.getSortCriteria() == SortCriteria.LIKECOUNT) {
			sb.append("ORDER BY like_count DESC ");
		}
		
		sb.append("LIMIT :offset, :itemCount;");
		
		parameterSource.addValue("offset",  pageInfo.getPageOffSet());
		parameterSource.addValue("itemCount",  pageInfo.getPageItemCount());
		
		List<ReviewDto> list = namedParameterJdbcTemplate.query(sb.toString(), parameterSource, new ReviewRowMapper());
					
		return list;
	}

	@Override
	public ReviewDto findReview(int reviewId) {
		MapSqlParameterSource parameterSource = new MapSqlParameterSource()
				.addValue("reviewId", reviewId);
		
		ReviewDto review = namedParameterJdbcTemplate.query(FIND_REVIEW_SQL, parameterSource, new ReviewResultSetExtrator());
		return review;
	}

	@Override
	public void updateReview(int reviewId, ReviewDto reviewDto) {
		MapSqlParameterSource parameterSource = new MapSqlParameterSource()
				.addValue("title", reviewDto.getTitle())
				.addValue("content", reviewDto.getContent())
				.addValue("areaCode", reviewDto.getAreaCode())
				.addValue("reviewId", reviewId);
	
		int result = namedParameterJdbcTemplate.update(UPDATE_REVIEW_SQL, parameterSource);
	}

	@Override
	public void updateReviewThumbnail(int reviewId, String thumbnailName) {
		SqlParameterSource parameterSource = new MapSqlParameterSource()
				.addValue("reviewId", reviewId)
				.addValue("thumbnail", thumbnailName);
		
		int result = namedParameterJdbcTemplate.update(UPDATE_REVIEW_THUMBNAIL_SQL, parameterSource);
	}

	@Override
	public void deleteReview(int reviewId) {
		MapSqlParameterSource parameterSource = new MapSqlParameterSource()
				.addValue("reviewId", reviewId);
		
		int result = namedParameterJdbcTemplate.update(DELETE_UPDATE_SQL, parameterSource);
	}

	@Override
	public int getTotalCount(CommonRequestParamDto requestParamDto) {
		StringBuilder sb = new StringBuilder(FIND_TOTAL_COUNT_SQL);
		MapSqlParameterSource parameterSource = new MapSqlParameterSource();
		
		String keyword = requestParamDto.getKeyword();
		Integer areaCode = requestParamDto.getAreaCode();
		
		if(keyword != null && !keyword.isEmpty()) {
			sb.append("WHERE title LIKE :keyword ");
			parameterSource.addValue("keyword", "%"+keyword+"%");
		}
		
		if(areaCode != null) {
			sb.append("AND areacode = :areaCode");
			parameterSource.addValue("areaCode", areaCode);
		}
				
		int result = namedParameterJdbcTemplate.queryForObject(sb.toString(), parameterSource, Integer.class);
		return result;
	}

	@Override
	public int getTotalCountByKeyword(String keyword) {
		String sql = String.format(FIND_TOTAL_COUNT_KEYWORD_SQL, keyword);
		
		return jdbcTemplate.queryForObject(sql, Integer.class);
	}
}
