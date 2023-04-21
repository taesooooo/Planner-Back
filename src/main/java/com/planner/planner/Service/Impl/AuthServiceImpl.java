package com.planner.planner.Service.Impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.planner.planner.Dao.AccountDao;
import com.planner.planner.Dto.AccountDto;
import com.planner.planner.Exception.NotFoundUserException;
import com.planner.planner.Service.AuthService;

@Service
public class AuthServiceImpl implements AuthService {
	private static final Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);

	private AccountDao accountDao;

	public AuthServiceImpl(AccountDao accountDao) {
		this.accountDao = accountDao;
	}

	@Override
	public boolean register(AccountDto accountDto) {
		return accountDao.create(accountDto);
	}

	@Override
	public AccountDto login(AccountDto accountDto) throws Exception {
		AccountDto user = accountDao.read(accountDto);
		if(user == null) {
			throw new NotFoundUserException("아이디 또는 비빌먼호를 잘못 입력했습니다.");
		}
		return user;
	}

}
