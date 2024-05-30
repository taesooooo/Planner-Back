package com.planner.planner.Service.Impl;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.planner.planner.Dao.AccountDao;
import com.planner.planner.Dao.PasswordResetKeyDao;
import com.planner.planner.Dto.AccountDto;
import com.planner.planner.Dto.PasswordResetkeyDto;
import com.planner.planner.Exception.PasswordResetKeyNotFoundException;
import com.planner.planner.Exception.UserNotFoundException;
import com.planner.planner.Service.EmailService;
import com.planner.planner.Service.PasswordResetKeyService;

@Service
@Transactional
public class PasswordResetKeyServiceImpl implements PasswordResetKeyService {
	
	private PasswordResetKeyDao passwordResetKeyDao;
	private AccountDao accountDao;
	private EmailService emailService;

	public PasswordResetKeyServiceImpl(PasswordResetKeyDao passwordResetKeyDao, AccountDao accountDao, EmailService emailService) {
		this.passwordResetKeyDao = passwordResetKeyDao;
		this.accountDao = accountDao;
		this.emailService = emailService;
	}

	@Override
	public void createPasswordResetKey(String resetKey, AccountDto account) throws Exception {
		AccountDto user = this.accountDao.findById(account.getAccountId());
		if(user == null) {
			throw new UserNotFoundException();
		}
		
		this.passwordResetKeyDao.createPasswordResetKey(resetKey, account.getAccountId());
		
		emailService.sendPasswordResetEmail(account.getEmail(), resetKey);
	}

	@Override
	public PasswordResetkeyDto findBykey(String key) {
		PasswordResetkeyDto pwResetKey = this.passwordResetKeyDao.findByResetKey(key);
		if(pwResetKey == null) {
			throw new PasswordResetKeyNotFoundException("존재 하지 않는 재설정 요청입니다.");
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
			throw new PasswordResetKeyNotFoundException("존재 하지 않는 재설정 요청입니다.");
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
