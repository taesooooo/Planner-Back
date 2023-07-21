package com.planner.planner.Dao;

import com.planner.planner.Dto.AuthenticationCodeDto;

public interface AuthenticationCodeDao {
	public boolean insert(String phone, String code);
	public AuthenticationCodeDto find(String phone);
	public void delete(String phone);
}
