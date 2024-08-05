package com.planner.planner.Mapper;

import org.apache.ibatis.annotations.Mapper;

import com.planner.planner.Dto.RefreshTokenDto;

@Mapper
public interface RefreshTokenMapper {
	public boolean createRefreshToken(String email, String token) throws Exception;

	public RefreshTokenDto findByEmail(String email) throws Exception;

	public RefreshTokenDto findByToken(String token) throws Exception;

	public boolean updateRefreshToken(String email, String token) throws Exception;

	public boolean deleteRefreshToken(String email) throws Exception;
}
