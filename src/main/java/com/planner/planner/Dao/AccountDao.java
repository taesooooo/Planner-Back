package com.planner.planner.Dao;

import java.util.List;

import com.planner.planner.Dto.AccountDto;
import com.planner.planner.Dto.PlannerDto;
import com.planner.planner.Dto.SpotLikeDto;
import com.planner.planner.Entity.Account;
import com.planner.planner.Entity.Like;
import com.planner.planner.Entity.Planner;
import com.planner.planner.Entity.Spot;

public interface AccountDao {
	public boolean create(Account account);
	public Account read(Account account);
	public boolean update(Account account);
	public boolean delete(Account account);
	public Account findById(int accountId);
	public boolean accountImageUpdate(int accountId, String imagePath);
	public boolean passwordUpdate(Account account);
	public boolean nickNameUpdate(Account account);
	public List<PlannerDto> likePlanners(int accountId);
	public List<SpotLikeDto> likeSpots(int accountId);
}

