package com.planner.planner;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.planner.planner.Dto.AccountDto;
import com.planner.planner.Service.AccountService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:root-context.xml","classpath:servlet-context.xml"})
public class AccountDaoTest {
	
	@Autowired
	private AccountService ser;
	
	@Test
	@Transactional
	public void createTest() {
		AccountDto dto = new AccountDto("test","test","test","test");
		ser.login(dto);
		
	}

}
