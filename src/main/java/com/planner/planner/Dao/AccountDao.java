package com.planner.planner.Dao;

import java.util.List;

import com.planner.planner.Common.Security.UserIdentifierDao;
import com.planner.planner.Dto.AccountDto;

public interface AccountDao extends UserIdentifierDao {
	public boolean create(AccountDto accountDto);
	public AccountDto read(AccountDto accountDto);
	public boolean update(int accountId, String nickname, String phone);
	public boolean deleteByEmail(String email);
	public AccountDto findById(int accountId);
	public AccountDto findByEmail(String email);
	public AccountDto findByNickName(String nickName);
	public List<AccountDto> findByNameAndPhone(String userName, String phone);
	public List<String> findEmailByPhone(String phone);
	public boolean accountImageUpdate(int accountId, String imagePath);
	public boolean passwordUpdate(int accountId, String password);
	public boolean nickNameUpdate(AccountDto accountDto);
	public AccountDto searchEmail(String searchEmail);
}