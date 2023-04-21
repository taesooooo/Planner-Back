package com.planner.planner.Service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.planner.planner.Dao.AccountDao;
import com.planner.planner.Dto.AccountDto;
import com.planner.planner.Exception.NotFoundUserException;
import com.planner.planner.Service.Impl.AuthServiceImpl;

public class AuthServiceTest {
	@Mock
	private AccountDao accountDao;
	@InjectMocks
	private AuthServiceImpl authService;
	
	@Before
	public void setup() {
		 MockitoAnnotations.openMocks(this);
	}
	
	@Test
	public void 회원가입() {
		AccountDto newTestUser = createUser(1);
		
		when(accountDao.create(any())).thenReturn(true);
		
		boolean user = authService.register(newTestUser);
		
		assertTrue(user);
	}
	
	@Test
	public void 로그인() throws Exception {
		AccountDto newTestUser = createUser(1);
		
		when(accountDao.read(any())).thenReturn(newTestUser);
		
		AccountDto user = authService.login(newTestUser);
		
		assertEquals(newTestUser.getAccountId(), user.getAccountId());
	}
	
	@Test(expected = NotFoundUserException.class)
	public void 로그인_아이디_없는경우() throws Exception {
		AccountDto newTestUser = createUser(1);
		
		when(authService.login(any(newTestUser.getClass()))).thenReturn(null);
		
		AccountDto user = authService.login(newTestUser);
		
//		assertEquals(newTestUser.getAccountId(), user.getAccountId());
	}
	
	private AccountDto createUser(int accountId) {
		return new AccountDto.Builder()
				.setAccountId(accountId)
				.setEmail("test@naver.com")
				.setPassword("")
				.setUserName("test")
				.setNickName("test")
				.setPhone("010-1234-1234")
				.setImage("")
				.setCreateDate(LocalDateTime.now())
				.setUpdateDate(LocalDateTime.now())
				.build();
	}
}
