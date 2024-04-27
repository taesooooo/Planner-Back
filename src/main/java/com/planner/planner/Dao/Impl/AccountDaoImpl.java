package com.planner.planner.Dao.Impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.planner.planner.Dao.AccountDao;
import com.planner.planner.Dto.AccountDto;
import com.planner.planner.RowMapper.AccountResultSetExtrator;
import com.planner.planner.RowMapper.AccountRowMapper;

@Repository
public class AccountDaoImpl implements AccountDao {

	private static final Logger logger = LoggerFactory.getLogger(AccountDaoImpl.class);

	private JdbcTemplate jdbcTemplate;

	private final String INSERT_ACCOUNT_SQL = "INSERT INTO account(email, password, name, nickname, phone, image, create_date, update_date) VALUES(?,?,?,?,?,?, now(), now());";
	private final String FIND_BY_EMAIL = "SELECT account_id, email, password, name, nickname, phone, image, create_date, update_date, user_role.role_id, role.authority "
			+ "FROM account "
			+ "INNER JOIN user_role ON user_role.user_id = account_id "
			+ "INNER JOIN role ON user_role.role_id = id "
			+ "WHERE email = ?";
	private final String FIND_BY_NICKNAME = "SELECT account_id, email, password, name, nickname, phone, image, create_date, update_date, user_role.role_id, role.authority "
			+ "FROM account "
			+ "INNER JOIN user_role ON user_role.user_id = account_id "
			+ "INNER JOIN role ON user_role.role_id = id "
			+ " WHERE nickname = ?";
	private final String FIND_BY_ID_SQL = "SELECT account_id, email, password, name, nickname, phone, image, create_date, update_date, user_role.role_id, role.authority "
			+ "FROM account "
			+ "INNER JOIN user_role ON user_role.user_id = account_id "
			+ "INNER JOIN role ON user_role.role_id = id "
			+ "WHERE account_id = ?";
	private final String FIND_BY_NAME_AND_PHONE_SQL = "SELECT account_id, email, password, name, nickname, phone, image, create_date, update_date "
			+ "FROM account "
			+ "WHERE name = ? AND phone = ?;";
	private final String FIND_EMAIL_BY_PHONE_SQL = "SELECT email FROM account WHERE phone = ?";;
	private final String updateSQL = "UPDATE account SET nickname = ?, phone = ?, update_date = now() WHERE account_id = ?;";
	private final String deleteSQL = "DELETE FROM account WHERE email = ?;";
	private final String imageUpdateSQL = "UPDATE account SET image = ?, update_date = now() WHERE account_id = ?";
	private final String passwordUpdateSQL = "UPDATE account SET password = ?, update_date = now() WHERE account_id = ?";
	private final String nicknameUpdateSQL = "UPDATE account SET nickname = ?, update_date = now() WHERE account_id = ?";

	public AccountDaoImpl(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public boolean create(AccountDto accountDto) {
		int result = jdbcTemplate.update(INSERT_ACCOUNT_SQL, accountDto.getEmail(), accountDto.getPassword(), accountDto.getUsername(),
				accountDto.getNickname(), accountDto.getPhone(), "");
		return result > 0 ? true : false;
	}
	
	@Deprecated
	@Override
	public AccountDto read(AccountDto accountDto) {
		try {
			return jdbcTemplate.query(FIND_BY_EMAIL, new AccountResultSetExtrator(), accountDto.getEmail());
		}
		catch (EmptyResultDataAccessException e) {
			return null;
		}
	}
	
	@Override
	public AccountDto findById(int accountId) {
		try {
			return jdbcTemplate.query(FIND_BY_ID_SQL, new AccountResultSetExtrator(), accountId);
		}
		catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public AccountDto findAccountIdByNickName(String nickName) {
		try {
			return jdbcTemplate.query(FIND_BY_NICKNAME, new AccountResultSetExtrator(), nickName);
		}
		catch (EmptyResultDataAccessException e) {
			return null;
		}
	}
	
	@Override
	public List<AccountDto> findByNameAndPhone(String userName, String phone) {
		try {
			return jdbcTemplate.query(FIND_BY_NAME_AND_PHONE_SQL, new AccountRowMapper(), userName, phone);
		}
		catch (EmptyResultDataAccessException e) {
			return new ArrayList<AccountDto>();
		}
	}

	@Override
	public List<String> findEmailByPhone(String phone) {
		try {
			return jdbcTemplate.queryForList(FIND_EMAIL_BY_PHONE_SQL, String.class, phone);
		}
		catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public AccountDto findByEmail(String email) {
		try {
			return jdbcTemplate.query(FIND_BY_EMAIL, new AccountResultSetExtrator(), email);
		}
		catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public boolean update(int accountId, String nickname, String phone) {
		int result = jdbcTemplate.update(updateSQL, nickname, phone,
				accountId);
		return result > 0 ? true : false;
	}

	@Override
	public boolean delete(AccountDto accountDto) {
		int result = jdbcTemplate.update(deleteSQL, accountDto.getEmail());
		return result > 0 ? true : false;
	}

	@Override
	public boolean accountImageUpdate(int accountId, String imagePath) {
		int result = jdbcTemplate.update(imageUpdateSQL, imagePath, accountId);
		return result > 0 ? true : false;
	}

	@Override
	public boolean passwordUpdate(int accountId, String password) {
		int result = jdbcTemplate.update(passwordUpdateSQL, password, accountId);
		return result > 0 ? true : false;
	}

	@Deprecated
	@Override
	public boolean nickNameUpdate(AccountDto accountDto) {
		int result = jdbcTemplate.update(nicknameUpdateSQL, accountDto.getNickname(), accountDto.getAccountId());
		return result > 0 ? true : false;
	}

	@Override
	public AccountDto searchEmail(String searchEmail) {
		try {
			return jdbcTemplate.query(FIND_BY_EMAIL, new AccountResultSetExtrator(), searchEmail);
		}
		catch (EmptyResultDataAccessException e) {
			return null;
		}
	}
}
