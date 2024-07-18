package com.planner.planner.Mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.planner.planner.Common.Security.UserIdentifierDao;
import com.planner.planner.Dto.AccountDto;

@Mapper
public interface AccountMapper extends UserIdentifierDao {
	public boolean create(AccountDto accountDto);

	public AccountDto read(AccountDto accountDto);

	public AccountDto findById(int accountId);

	public AccountDto findByEmail(String email);

	public AccountDto findByNickName(String nickname);

	public List<AccountDto> findByNameAndPhone(String username, String phone);

	public List<String> findEmailByPhone(String phone);

	public boolean update(int accountId, String nickname, String phone);

	public boolean deleteByEmail(String email);

	public boolean accountImageUpdate(int accountId, String imagePath);

	public boolean passwordUpdate(int accountId, String password);

	public AccountDto searchEmail(String searchEmail);
}
