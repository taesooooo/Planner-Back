package com.planner.planner.Service;

import com.planner.planner.Dto.PasswordResetkeyDto;

public interface PasswordResetKeyService {
	public void createPasswordResetKey(String resetKey, int accountId) throws Exception;

	public PasswordResetkeyDto findBykey(String key);

	public void deleteByKey(String key);

	public boolean validatePasswordResetKey(String key);
}
