package com.planner.planner.Service.Impl;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.planner.planner.Dao.AccountDao;
import com.planner.planner.Dao.PasswordResetKeyDao;
import com.planner.planner.Dto.AccountDto;
import com.planner.planner.Dto.PasswordResetkeyDto;
import com.planner.planner.Exception.NotFoundPasswordResetKeyException;
import com.planner.planner.Exception.NotFoundUserException;
import com.planner.planner.Service.PasswordResetKeyService;

@Service
public class PasswordResetKeyServiceImpl implements PasswordResetKeyService {
	
	private PasswordResetKeyDao passwordResetKeyDao;
	private AccountDao accountDao;

	public PasswordResetKeyServiceImpl(PasswordResetKeyDao passwordResetKeyDao, AccountDao accountDao) {
		this.passwordResetKeyDao = passwordResetKeyDao;
		this.accountDao = accountDao;
	}

	@Override
	public void createPasswordResetKey(String resetKey, int accountId) throws Exception {
		AccountDto user = this.accountDao.findById(accountId);
		if(user == null) {
			throw new NotFoundUserException();
		}
		
		this.passwordResetKeyDao.createPasswordResetKey(resetKey, accountId);
	}

	@Override
	public PasswordResetkeyDto findBykey(String key) {
		PasswordResetkeyDto pwResetKey = this.passwordResetKeyDao.findByResetKey(key);
		if(pwResetKey == null) {
			throw new NotFoundPasswordResetKeyException("존재 하지 않는 재설정 요청입니다.");
		}
		
		return pwResetKey;
	}

	@Override
	public void deleteByKey(String key) {
		this.passwordResetKeyDao.deleteByResetKey(key);
	}

	@Override
	public boolean validatePasswordResetKey(String key) {
		PasswordResetkeyDto resetKey = this.passwordResetKeyDao.findByResetKey(key);
		if(resetKey == null) {
			throw new NotFoundPasswordResetKeyException("존재 하지 않는 재설정 요청입니다.");
		}
		
		LocalDateTime expireDate = resetKey.getExpireDate();
	
		if(LocalDateTime.now().isAfter(expireDate)) {
			return false;
		}
		else {
			return true;
		}
	}

}
