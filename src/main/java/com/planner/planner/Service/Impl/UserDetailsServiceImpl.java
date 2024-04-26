package com.planner.planner.Service.Impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.planner.planner.Dao.AccountDao;
import com.planner.planner.Dto.AccountDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
	private static final Logger LOGGER = LoggerFactory.getLogger(UserDetailsServiceImpl.class);
	
	private final AccountDao accountDao;
	
	@Override
	public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
		AccountDto accountDto = accountDao.findById(Integer.parseInt(id));
		if(accountDto == null) {
			throw new UsernameNotFoundException(id);
		}
		
		return accountDto;
	}

}
