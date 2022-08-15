package com.planner.planner.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.planner.planner.Dao.AccountDao;
import com.planner.planner.Dao.AccountDaoImpl;
import com.planner.planner.Dto.AccountDto;
import com.planner.planner.Dto.LikeDto;

@Service
public class AccountServiceImpl implements AccountService {
	private static final Logger logger = LoggerFactory.getLogger(AccountDaoImpl.class);
	
	@Autowired
	private AccountDao accountDao;

	@Override
	@Transactional
	public boolean register(AccountDto accountDto) {
		return accountDao.create(accountDto.toEntity());
	}

	@Override
	public AccountDto login(AccountDto accountDto) {
		return accountDao.read(accountDto.toEntity());
	}
	
	@Override
	public boolean passwordUpdate(AccountDto accountDto) {
		return accountDao.passwordUpdate(accountDto.toEntity());
	}

	public LikeDto getLikes(int accountId) {
		return null;
	}

}
