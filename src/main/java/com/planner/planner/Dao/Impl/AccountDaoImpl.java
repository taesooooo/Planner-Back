package com.planner.planner.Dao.Impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.planner.planner.Dao.AccountDao;
import com.planner.planner.Dto.AccountDto;
import com.planner.planner.Dto.SpotLikeDto;
import com.planner.planner.RowMapper.AccountRowMapper;

@Repository
public class AccountDaoImpl implements AccountDao {

	private static final Logger logger = LoggerFactory.getLogger(AccountDaoImpl.class);

	private JdbcTemplate jdbcTemplate;

	private final String INSERT_ACCOUNT_SQL = "INSERT INTO ACCOUNT(email, password, name, nickname, phone, image,create_date,update_date) VALUES(?,?,?,?,?,?, now(), now());";
	private final String FIND_BY_EMAIL = "SELECT account_id, email, password, name, nickname, phone, image, create_date, update_date FROM account WHERE email = ?";
	private final String FIND_BY_NICKNAME = "SELECT account_id, email, password, name, nickname, phone, image, create_date, update_date FROM account WHERE nickname = ?";
	private final String FIND_BY_ID_SQL = "SELECT account_id, email, password, name, nickname, phone, image, create_date, update_date FROM account WHERE account_id = ?";
	private final String FIND_ACCOUNTID_BY_EMAIL = "SELECT account_id FROM account WHERE email = ?";
	private final String updateSQL = "UPDATE ACCOUNT SET nickname = ?, phone = ?, update_date = now() WHERE account_id = ?;";
	private final String deleteSQL = "DELETE FROM ACCOUNT WHERE email = ?;";
	private final String imageUpdateSQL = "UPDATE account SET image = ?, update_date = now() WHERE account_id = ?";
	private final String passwordUpdateSQL = "UPDATE account SET password = ?, update_date = now() WHERE account_id = ?";
	private final String nicknameUpdateSQL = "UPDATE account SET nickname = ?, update_date = now() WHERE account_id = ?";
	private final String likePlannersSQL = "SELECT planner_id, title, plan_date_start, plan_date_end FROM planner WHERE planner_id "
			+ "IN (SELECT planner_id FROM plannerlike WHERE account_id = ?);";
	private final String likeSpotsSQL = "SELECT like_id, account_id, content_id, like_date FROM spotlike WHERE account_id = ?;";
	private final String spotLikesByAccountId = "SELECT like_id, account_id, content_id, like_date FROM spotlike WHERE account_id = ?;";
	private final String spotLikeStateSQL = "SELECT like_id, account_id, content_id, like_date FROM spotlike WHERE content_id IN (%s) and account_id = ?;";
	private final String SEARCH_EMAIL_SQL = "SELECT A.email FROM account AS A WHERE A.email like ?;";

	public AccountDaoImpl(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public boolean create(AccountDto accountDto) {
		int result = jdbcTemplate.update(INSERT_ACCOUNT_SQL, accountDto.getEmail(), accountDto.getPassword(), accountDto.getUserName(),
				accountDto.getNickName(), accountDto.getPhone(), "");
		return result > 0 ? true : false;
	}

	@Override
	public AccountDto read(AccountDto accountDto) {
		try {
			return jdbcTemplate.queryForObject(FIND_BY_EMAIL, new AccountRowMapper(), accountDto.getEmail());
		}
		catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public boolean update(AccountDto accountDto) {
		int result = jdbcTemplate.update(updateSQL, accountDto.getNickName(), accountDto.getPhone(),
				accountDto.getAccountId());
		return result > 0 ? true : false;
	}

	@Override
	public boolean delete(AccountDto accountDto) {
		int result = jdbcTemplate.update(deleteSQL, accountDto.getAccountId());
		return result > 0 ? true : false;
	}

	@Override
	public boolean accountImageUpdate(int accountId, String imagePath) {
		int result = jdbcTemplate.update(imageUpdateSQL, imagePath, accountId);
		return result > 0 ? true : false;
	}

	@Override
	public boolean passwordUpdate(AccountDto accountDto) {
		int result = jdbcTemplate.update(passwordUpdateSQL, accountDto.getPassword(), accountDto.getAccountId());
		return result > 0 ? true : false;
	}

	@Override
	public boolean nickNameUpdate(AccountDto accountDto) {
		int result = jdbcTemplate.update(nicknameUpdateSQL, accountDto.getNickName(), accountDto.getAccountId());
		return result > 0 ? true : false;
	}

	@Override
	public AccountDto findById(int accountId) {
		try {
			return jdbcTemplate.queryForObject(FIND_BY_ID_SQL, new AccountRowMapper(), accountId);
		}
		catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public AccountDto findAccountIdByNickName(String nickName) {
		try {
			return jdbcTemplate.queryForObject(FIND_BY_NICKNAME, new AccountRowMapper(), nickName);
		}
		catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public List<SpotLikeDto> likeSpots(int accountId) {
		List<SpotLikeDto> likes = jdbcTemplate.query(likeSpotsSQL, new RowMapper<SpotLikeDto>() {
			@Override
			public SpotLikeDto mapRow(ResultSet rs, int rowNum) throws SQLException {
				SpotLikeDto spotLike = new SpotLikeDto.Builder().setLikeId(rs.getInt(1)).setAccountId(rs.getInt(2))
						.setContentId(rs.getInt(3)).setLikeDate(rs.getDate(4).toLocalDate()).build();
				return spotLike;
			}
		}, accountId);
		return likes;
	}

	@Override
	public List<SpotLikeDto> spotLikesByAccountId(int accountId) {
		List<SpotLikeDto> likes =  jdbcTemplate.query(spotLikesByAccountId, new RowMapper<SpotLikeDto>() {
			@Override
			public SpotLikeDto mapRow(ResultSet rs, int rowNum) throws SQLException {
				SpotLikeDto spotLike = new SpotLikeDto.Builder()
						.setLikeId(rs.getInt(1))
						.setAccountId(rs.getInt(2))
						.setContentId(rs.getInt(3))
						.setLikeDate(rs.getDate(4).toLocalDate())
						.build();
				return spotLike;
			}
		}, accountId);
		return likes;
	}

	@Override
	public List<SpotLikeDto> spotLikesByContentIds(int accountId, List<Integer> contentId) {
		String contentList = contentId.stream().map(String::valueOf).collect(Collectors.joining(","));
		String sql = String.format(spotLikeStateSQL, contentList);
		List<SpotLikeDto> states = jdbcTemplate.query(sql, (rs, rowNum) -> {
			SpotLikeDto spotLike = new SpotLikeDto.Builder()
					.setLikeId(rs.getInt(1))
					.setAccountId(rs.getInt(2))
					.setContentId(rs.getInt(3))
					.setLikeDate(rs.getDate(4).toLocalDate())
					.build();
			return spotLike;
		}, accountId);

		return states;
	}

	@Override
	public AccountDto searchEmail(String searchEmail) {
		try {
			return jdbcTemplate.queryForObject(FIND_BY_EMAIL, new AccountRowMapper(), searchEmail);
		}
		catch (EmptyResultDataAccessException e) {
			return null;
		}
	}
}
