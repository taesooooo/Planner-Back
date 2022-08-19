package com.planner.planner;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.planner.planner.Dao.AccountDaoImpl;
import com.planner.planner.Dto.AccountDto;
import com.planner.planner.Dto.LikeDto;
import com.planner.planner.Service.AccountService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:root-context.xml","classpath:servlet-context.xml"})
public class AccountDaoTest {
	private static final Logger logger = LoggerFactory.getLogger(AccountDaoTest.class);
	
	@Autowired
	private AccountService accountSerivce;
	
	@Test
	public void accountInfoTest() {
		//AccountDto account = accountSerivce.
	}
	
	@Test
	@Transactional
	public void pwUpdateTest() {
		AccountDto test = new AccountDto.Builder().setAccountId(2).setEmail("test2@naver.com").setPassword("5678").build();
		accountSerivce.passwordUpdate(test);
	}
	
	@Test
	@Transactional
	public void nickUpdateTest() {
		AccountDto test = new AccountDto.Builder().setAccountId(2).setEmail("test2@naver.com").setNickName("테스트 닉네임").build();
		accountSerivce.nickNameUpdate(test);
	}
	
	@Test
	public void getLikesTest() {
		LikeDto likes = accountSerivce.getLikes(1);
		logger.info(likes.toString());
	}
}
