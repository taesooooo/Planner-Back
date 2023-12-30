package com.planner.planner.Dao;

import com.planner.planner.Dto.RefreshTokenDto;

public interface RefreshTokenDao {
	public boolean create(String email, String token) throws Exception;
	public RefreshTokenDto findByEmail(String email) throws Exception;
	public RefreshTokenDto findByToken(String token) throws Exception;
	public boolean update(String email, String token) throws Exception;
	public boolean delete(String email) throws Exception;
}
