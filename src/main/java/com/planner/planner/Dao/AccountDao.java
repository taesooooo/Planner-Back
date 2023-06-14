package com.planner.planner.Dao;

import com.planner.planner.Dto.AccountDto;

public interface AccountDao {
	public boolean create(AccountDto accountDto);
	public AccountDto read(AccountDto accountDto);
	public boolean update(AccountDto accountDto);
	public boolean delete(AccountDto accountDto);
	public AccountDto findById(int accountId);
	public AccountDto findAccountIdByNickName(String nickName);
	public boolean accountImageUpdate(int accountId, String imagePath);
	public boolean passwordUpdate(AccountDto accountDto);
	public boolean nickNameUpdate(AccountDto accountDto);
	public AccountDto searchEmail(String searchEmail);
}