package com.planner.planner.Dao;

import java.util.List;

import com.planner.planner.Dto.AccountDto;
import com.planner.planner.Entity.Account;
import com.planner.planner.Entity.Like;
import com.planner.planner.Entity.Planner;

public interface AccountDao {
	public boolean create(Account account);
	public Account read(Account account);
	public boolean update(Account account);
	public boolean delete(Account account);
	public boolean passwordUpdate(Account account);
	public boolean nickNameUpdate(Account account);
	public List<Planner> getLikes(int accountId);
}

