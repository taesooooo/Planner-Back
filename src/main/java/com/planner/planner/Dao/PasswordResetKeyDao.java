package com.planner.planner.Dao;

import com.planner.planner.Dto.PasswordResetkeyDto;

public interface PasswordResetKeyDao {
	public void createPasswordResetKey(String key, int accountId);
	public PasswordResetkeyDto findByResetKey(String key);
	public void deleteByResetKey(String key);
}
