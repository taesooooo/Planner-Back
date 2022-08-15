package com.planner.planner.Dao;

import java.util.List;

import com.planner.planner.Dto.AccountDto;
import com.planner.planner.Entity.Account;
import com.planner.planner.Entity.Like;

public interface AccountDao {
	public boolean create(Account account);
	public AccountDto read(Account account);
	public boolean update(Account account);
	public boolean delete(Account account);
	public boolean passwordUpdate(Account account);
	public List<Like> getLikes(int accountId);
}

