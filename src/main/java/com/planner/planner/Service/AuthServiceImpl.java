package com.planner.planner.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.planner.planner.Dao.AccountDao;
import com.planner.planner.Dto.AccountDto;
import com.planner.planner.Entity.Account;

@Service
public class AuthServiceImpl implements AuthService {
	private static final Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);

	private AccountDao accountDao;

	public AuthServiceImpl(AccountDao accountDao) {
		this.accountDao = accountDao;
	}

	@Override
	public boolean register(AccountDto accountDto) {
		return accountDao.create(accountDto.toEntity());
	}

	@Override
	public AccountDto login(AccountDto accountDto) {
		Account user = accountDao.read(accountDto.toEntity());
		return user.toDto();
	}

}
