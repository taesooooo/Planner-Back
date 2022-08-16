package com.planner.planner.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.planner.planner.Dao.AccountDao;
import com.planner.planner.Dao.AccountDaoImpl;
import com.planner.planner.Dto.AccountDto;
import com.planner.planner.Dto.LikeDto;
import com.planner.planner.Dto.PlannerDto;
import com.planner.planner.Entity.Account;
import com.planner.planner.Entity.Like;

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
		Account user = accountDao.read(accountDto.toEntity());
		return user.toDto();
	}
	
	@Override
	public boolean passwordUpdate(AccountDto accountDto) {
		return accountDao.passwordUpdate(accountDto.toEntity());
	}

	@Override
	public boolean nickNameUpdate(AccountDto accountDto) {
		return accountDao.nickNameUpdate(accountDto.toEntity());
	}

	@Override
	public LikeDto getLikes(int accountId) {
		List<PlannerDto> likeP = accountDao.getLikes(accountId).stream().map((p) -> p.toDto()).collect(Collectors.toList());
		return new LikeDto.Builder().setLikePlanners(likeP).build();
	}

}
