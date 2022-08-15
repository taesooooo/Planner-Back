package com.planner.planner.Service;

import com.planner.planner.Dto.AccountDto;
import com.planner.planner.Dto.LikeDto;

public interface AccountService {
	public boolean register(AccountDto accountDto);
	public AccountDto login(AccountDto accountDto);
	public LikeDto getLikes(int accountId);
}
