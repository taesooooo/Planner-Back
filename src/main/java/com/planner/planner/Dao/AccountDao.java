package com.planner.planner.Dao;

import java.util.List;

import com.planner.planner.Common.Security.UserIdentifierDao;
import com.planner.planner.Dto.AccountDto;

public interface AccountDao extends UserIdentifierDao {
	public boolean create(AccountDto accountDto);
	public AccountDto read(AccountDto accountDto);
	public boolean update(int accountId, String nickname, String phone);
	public boolean delete(AccountDto accountDto);
	public AccountDto findById(int accountId);
	public AccountDto findAccountIdByNickName(String nickName);
	public List<AccountDto> findByNameAndPhone(String userName, String phone);
	public List<String> findEmailByPhone(String phone);
	public AccountDto findByEmail(String email);
	public boolean accountImageUpdate(int accountId, String imagePath);
	public boolean passwordUpdate(int accountId, String password);
	public boolean nickNameUpdate(AccountDto accountDto);
	public AccountDto searchEmail(String searchEmail);
}