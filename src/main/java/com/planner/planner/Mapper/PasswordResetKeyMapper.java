package com.planner.planner.Mapper;

import org.apache.ibatis.annotations.Mapper;

import com.planner.planner.Dto.PasswordResetkeyDto;

@Mapper
public interface PasswordResetKeyMapper {
	public int createPasswordResetKey(String key, int accountId);

	public PasswordResetkeyDto findByResetKey(String key);

	public int deleteByResetKey(String key);
}
