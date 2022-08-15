package com.planner.planner;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.planner.planner.Dto.AccountDto;
import com.planner.planner.Service.AccountService;
import com.planner.planner.Service.AccountServiceImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:root-context.xml","classpath:servlet-context.xml"})
public class AccountDaoTest {
	
	@Autowired
	private AccountService accountSerivce;
	
	@Test
	@Transactional
	public void pwUpdateTest() {
		AccountDto test = new AccountDto.Builder().setAccountId(2).setEmail("test2@naver.com").setPassword("5678").build();
		accountSerivce.passwordUpdate(test);
	}
	
	
}
