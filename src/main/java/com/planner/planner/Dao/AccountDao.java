package com.planner.planner.Dao;

import java.util.List;

import com.planner.planner.Dto.AccountDto;
import com.planner.planner.Dto.PlannerDto;
import com.planner.planner.Dto.SpotLikeDto;
import com.planner.planner.Entity.Account;

public interface AccountDao {
	public boolean create(AccountDto accountDto);
	public Account read(AccountDto accountDto);
	public boolean update(AccountDto accountDto);
	public boolean delete(AccountDto accountDto);
	public Account findById(int accountId);
	public boolean accountImageUpdate(int accountId, String imagePath);
	public boolean passwordUpdate(AccountDto accountDto);
	public boolean nickNameUpdate(AccountDto accountDto);
	public List<SpotLikeDto> likeSpots(int accountId);
	public List<SpotLikeDto> spotLikesByAccountId(int accountId);
	public List<SpotLikeDto> spotLikesByContentIds(int accountId, List<Integer> contentId);
}

