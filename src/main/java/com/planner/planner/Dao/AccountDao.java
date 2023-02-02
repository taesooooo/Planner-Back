package com.planner.planner.Dao;

import java.util.List;

import com.planner.planner.Dto.AccountDto;
import com.planner.planner.Dto.SpotLikeDto;

public interface AccountDao {
	public boolean create(AccountDto accountDto);
	public AccountDto read(AccountDto accountDto);
	public boolean update(AccountDto accountDto);
	public boolean delete(AccountDto accountDto);
	public AccountDto findById(int accountId);
	public AccountDto findAccountIdByEmail(String email);
	public boolean accountImageUpdate(int accountId, String imagePath);
	public boolean passwordUpdate(AccountDto accountDto);
	public boolean nickNameUpdate(AccountDto accountDto);
	public List<SpotLikeDto> likeSpots(int accountId);
	public List<SpotLikeDto> spotLikesByAccountId(int accountId);
	public List<SpotLikeDto> spotLikesByContentIds(int accountId, List<Integer> contentId);
	public AccountDto searchEmail(String searchEmail);
}