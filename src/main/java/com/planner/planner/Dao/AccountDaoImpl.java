package com.planner.planner.Dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.planner.planner.Dto.AccountDto;
import com.planner.planner.Dto.PlannerDto;
import com.planner.planner.Dto.SpotLikeDto;
import com.planner.planner.Entity.Account;
import com.planner.planner.Entity.Like;
import com.planner.planner.Entity.Planner;
import com.planner.planner.Entity.Spot;
import com.planner.planner.Entity.SpotLike;
import com.planner.planner.RowMapper.AccountRowMapper;
import com.planner.planner.RowMapper.LikePlannersRowMapper;
import com.planner.planner.RowMapper.LikeSpotsRowMapper;
import com.planner.planner.RowMapper.PlannerRowMapper;

@Repository
public class AccountDaoImpl implements AccountDao {

	private static final Logger logger = LoggerFactory.getLogger(AccountDaoImpl.class);

	private JdbcTemplate jdbcTemplate;

	private final String createSQL = "INSERT INTO ACCOUNT(email,password,name,nickname, image,create_date,update_date) VALUES(?,?,?,?,?, now(), now());";
	private final String readSQL = "SELECT account_id, email, password, name, nickname, image, create_date, update_date FROM account WHERE email = ?";
	private final String findByIdSQL = "SELECT account_id, email, password, name, nickname, image, create_date, update_date FROM account WHERE account_id = ?";
	private final String updateSQL = "UPDATE ACCOUNT SET name = ?, nickname = ?, update_date = now() WHERE account_id = ?;";
	private final String deleteSQL = "DELETE FROM ACCOUNT WHERE email = ?;";
	private final String imageUpdateSQL = "UPDATE account SET image = ?, update_date = now() WHERE account_id = ?";
	private final String passwordUpdateSQL = "UPDATE account SET password = ?, update_date = now() WHERE account_id = ?";
	private final String nicknameUpdateSQL = "UPDATE account SET nickname = ?, update_date = now() WHERE account_id = ?";
	private final String likePlannersSQL = "SELECT planner_id, title, plan_date_start, plan_date_end FROM planner WHERE planner_id "
			+ "IN (SELECT planner_id FROM plannerlike WHERE account_id = ?);";
	private final String likeSpotsSQL = "SELECT like_id, account_id, content_id, like_date FROM spotlike WHERE account_id = ?;";

	public AccountDaoImpl(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	@Override
	public boolean create(Account account) {
		int result = jdbcTemplate.update(createSQL, account.getEmail(), account.getPassword(), account.getUserName(),
				account.getNickName(), "");
		return result > 0 ? true : false;
	}

	@Override
	public Account read(Account account) {
		return jdbcTemplate.queryForObject(readSQL, new AccountRowMapper(), account.getEmail());
	}

	@Override
	public boolean update(Account account) {
		int result = jdbcTemplate.update(updateSQL, account.getUserName(), account.getNickName(),
				account.getAccountId());
		return result > 0 ? true : false;
	}

	@Override
	public boolean delete(Account account) {
		int result = jdbcTemplate.update(deleteSQL, account.getAccountId());
		return result > 0 ? true : false;
	}

	@Override
	public boolean accountImageUpdate(int accountId, String imagePath) {
		int result = jdbcTemplate.update(imageUpdateSQL, imagePath, accountId);
		return result > 0 ? true : false;
	}

	@Override
	public boolean passwordUpdate(Account account) {
		int result = jdbcTemplate.update(passwordUpdateSQL, account.getPassword(), account.getAccountId());
		return result > 0 ? true : false;
	}

	@Override
	public boolean nickNameUpdate(Account account) {
		int result = jdbcTemplate.update(nicknameUpdateSQL, account.getNickName(), account.getAccountId());
		return result > 0 ? true : false;
	}

	@Override
	public Account findById(int accountId) {
		return jdbcTemplate.queryForObject(findByIdSQL, new AccountRowMapper(), accountId);
	}

	@Override
	public List<PlannerDto> likePlanners(int accountId) {
		return jdbcTemplate.query(likePlannersSQL, new LikePlannersRowMapper(), accountId).stream().map((p) -> p.toDto()).collect(Collectors.toList());
	}

	@Override
	public List<SpotLikeDto> likeSpots(int accountId) {
		List<SpotLike> likes = jdbcTemplate.query(likeSpotsSQL, new RowMapper<SpotLike>() {
			@Override
			public SpotLike mapRow(ResultSet rs, int rowNum) throws SQLException {
				SpotLike spotLike = new SpotLike.Builder().setLikeId(rs.getInt(1)).setAccountId(rs.getInt(2))
						.setContentId(rs.getInt(3)).setLikeDate(rs.getDate(4).toLocalDate()).build();
				return spotLike;
			}
		}, accountId);
		return likes.stream().map(like -> like.toDto()).collect(Collectors.toList());
	}

}
