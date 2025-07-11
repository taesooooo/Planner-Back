package com.planner.planner.Service.Impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.planner.planner.Dto.AccountDto;
import com.planner.planner.Mapper.AccountMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
	private static final Logger LOGGER = LoggerFactory.getLogger(UserDetailsServiceImpl.class);
	
	private final AccountMapper accountMapper;
	
	@Override
	public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
		AccountDto accountDto = accountMapper.findById(Integer.parseInt(id));
		if(accountDto == null) {
			throw new UsernameNotFoundException(id);
		}
		
		return accountDto;
	}

}
