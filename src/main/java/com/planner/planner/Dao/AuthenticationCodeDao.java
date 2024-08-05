package com.planner.planner.Dao;

import com.planner.planner.Dto.AuthenticationCodeDto;

public interface AuthenticationCodeDao {
	public boolean createByPhone(String phone, String code);
	public boolean createByEmail(String email, String code);
	public AuthenticationCodeDto findByPhone(String phone);
	public AuthenticationCodeDto findByEmail(String email);
	public boolean updateCodeConfirmByPhone(String phone);
	public boolean updateCodeConfirmByEmail(String email);
	public void deleteByPhone(String phone);
}
