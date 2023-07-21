package com.planner.planner.Dao;

import java.util.List;

import com.planner.planner.Dto.AccountDto;
import com.planner.planner.Dto.PasswordDto;

public interface AccountDao {
	public boolean create(AccountDto accountDto);
	public AccountDto read(AccountDto accountDto);
	public boolean update(AccountDto accountDto);
	public boolean delete(AccountDto accountDto);
	public AccountDto findById(int accountId);
	public AccountDto findAccountIdByNickName(String nickName);
	public AccountDto findAccount(String userName, String phone);
	public List<String> findEmailByPhone(String phone);
	public AccountDto findByEmail(String email);
	public boolean accountImageUpdate(int accountId, String imagePath);
	public boolean passwordUpdate(int accountId, String password);
	public boolean nickNameUpdate(AccountDto accountDto);
	public AccountDto searchEmail(String searchEmail);
}