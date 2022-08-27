package com.planner.planner.Service;

import java.util.List;

import com.planner.planner.Dto.AccountDto;
import com.planner.planner.Dto.LikeDto;
import com.planner.planner.Dto.PlannerDto;

public interface AccountService {
	public AccountDto findById(int accountId);
	public boolean register(AccountDto accountDto);
	public AccountDto login(AccountDto accountDto);
	public boolean passwordUpdate(AccountDto accountDto);
	public boolean nickNameUpdate(AccountDto accountDto);
	public LikeDto getLikes(int accountId);
}
