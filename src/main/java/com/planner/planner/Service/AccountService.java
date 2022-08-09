package com.planner.planner.Service;

import com.planner.planner.Dto.AccountDto;

public interface AccountService {
	public boolean register(AccountDto accountDto);
	public AccountDto login(AccountDto accountDto);
}
