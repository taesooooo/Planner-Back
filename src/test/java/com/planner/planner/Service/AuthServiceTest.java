package com.planner.planner.Service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.planner.planner.Dao.AccountDao;
import com.planner.planner.Dao.RefreshTokenDao;
import com.planner.planner.Dto.AccountDto;
import com.planner.planner.Dto.LoginInfoDto;
import com.planner.planner.Dto.RefreshTokenDto;
import com.planner.planner.Dto.ReissueTokenDto;
import com.planner.planner.Exception.NotFoundToken;
import com.planner.planner.Exception.NotFoundUserException;
import com.planner.planner.Exception.PasswordCheckFailException;
import com.planner.planner.Service.Impl.AuthServiceImpl;
import com.planner.planner.Util.JwtUtil;

public class AuthServiceTest {
	@Mock
	private AccountDao accountDao;
	@Mock
	private BCryptPasswordEncoder passwordEncoder;
	@Mock
	private RefreshTokenDao refreshTokenDao;
	@Mock
	private JwtUtil jwtUtil;
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
		String accessToken = "accessToken";
		String refreshToken = "refreshToken";
		RefreshTokenDto refreshTokenDto = new RefreshTokenDto.Builder()
				.setEmail("test@naver.com")
				.setId(1)
				.setToken("testRefershToken")
				.build();
		
		LoginInfoDto testLoginInfo = new LoginInfoDto.Builder()
				.setUser(newTestUser)
				.setAccessToken(accessToken)
				.setReflashToken(refreshToken)
				.build();
		
		when(accountDao.findByEmail(anyString())).thenReturn(newTestUser);
		when(passwordEncoder.matches(anyString(), anyString())).thenReturn(true);
		when(jwtUtil.createAccessToken(anyInt())).thenReturn(accessToken);
		when(jwtUtil.createReflashToken()).thenReturn(refreshToken);
		when(refreshTokenDao.findByEmail(anyString())).thenReturn(refreshTokenDto);
		
		LoginInfoDto user = authService.login(newTestUser);
		
		assertThat(user)
			.usingRecursiveComparison()
			.isEqualTo(testLoginInfo);
	}
	
	@Test
	public void 로그인_비밀번호_틀린경우() throws Exception {
		AccountDto newTestUser = createUser(1);
		
		when(accountDao.findByEmail(anyString())).thenReturn(newTestUser);
		when(passwordEncoder.matches(anyString(), anyString())).thenReturn(false);

		
		assertThatThrownBy(() -> authService.login(newTestUser))
			.isInstanceOf(PasswordCheckFailException.class);
	}
	
	@Test
	public void 로그인_아이디_없는경우() throws Exception {
		AccountDto newTestUser = createUser(1);
		
		when(accountDao.read(any())).thenReturn(null);

		assertThatThrownBy(() -> authService.login(newTestUser))
			.isInstanceOf(NotFoundUserException.class);
	}
	
	@Test
	public void 로그인_리프래시_토큰_없는경우_새로_저장() throws Exception {
		AccountDto newTestUser = createUser(1);
		String accessToken = "accessToken";
		String refreshToken = "refreshToken";
		
		LoginInfoDto testLoginInfo = new LoginInfoDto.Builder()
				.setUser(newTestUser)
				.setAccessToken(accessToken)
				.setReflashToken(refreshToken)
				.build();
		
		when(accountDao.findByEmail(anyString())).thenReturn(newTestUser);
		when(passwordEncoder.matches(anyString(), anyString())).thenReturn(true);
		when(jwtUtil.createAccessToken(anyInt())).thenReturn(accessToken);
		when(jwtUtil.createReflashToken()).thenReturn(refreshToken);
		when(refreshTokenDao.findByEmail(anyString())).thenReturn(null);
		
		LoginInfoDto user = authService.login(newTestUser);
		
		verify(refreshTokenDao).create(newTestUser.getEmail(), refreshToken);
		
		assertThat(user)
			.usingRecursiveComparison()
			.isEqualTo(testLoginInfo);
	}
	
	@Test
	public void 로그인_리프래시_토큰_있는경우_변경() throws Exception {
		AccountDto newTestUser = createUser(1);
		String accessToken = "accessToken";
		String refreshToken = "refreshToken";
		RefreshTokenDto refreshTokenDto = new RefreshTokenDto.Builder()
				.setEmail("test@naver.com")
				.setId(1)
				.setToken("testRefershToken")
				.build();
		
		LoginInfoDto testLoginInfo = new LoginInfoDto.Builder()
				.setUser(newTestUser)
				.setAccessToken(accessToken)
				.setReflashToken(refreshToken)
				.build();
		
		when(accountDao.findByEmail(anyString())).thenReturn(newTestUser);
		when(passwordEncoder.matches(anyString(), anyString())).thenReturn(true);
		when(jwtUtil.createAccessToken(anyInt())).thenReturn(accessToken);
		when(jwtUtil.createReflashToken()).thenReturn(refreshToken);
		when(refreshTokenDao.findByEmail(anyString())).thenReturn(refreshTokenDto);
		
		LoginInfoDto user = authService.login(newTestUser);
		
		verify(refreshTokenDao).update(newTestUser.getEmail(), refreshToken);
		
		assertThat(user)
			.usingRecursiveComparison()
			.isEqualTo(testLoginInfo);
	}
	
	@Test
	public void 토큰_재발급() throws Exception {
		AccountDto newTestUser = createUser(1);
		String testRefreshToken = "testRefreshToken";
		RefreshTokenDto refreshTokenDto = new RefreshTokenDto.Builder()
				.setId(1)
				.setEmail("test@naver.com")
				.setToken("testRefreshToken")
				.build();
		String newAccessToken = "newAccessToken";
		String newRefreshToken = "newRefreshToken";
		ReissueTokenDto tokenDto = new ReissueTokenDto.Builder()
				.setAccessToken(newAccessToken)
				.setRefreshToken(newRefreshToken)
				.build();
		
		
		when(refreshTokenDao.findByToken(anyString())).thenReturn(refreshTokenDto);
		when(accountDao.findByEmail(anyString())).thenReturn(newTestUser);
		when(jwtUtil.createAccessToken(anyInt())).thenReturn(newAccessToken);
		when(jwtUtil.createReflashToken()).thenReturn(newRefreshToken);
		when(refreshTokenDao.update(anyString(), anyString())).thenReturn(true);
		
		ReissueTokenDto newTokenDto = authService.reissueToken(testRefreshToken);
		
		assertThat(newTokenDto)
			.usingRecursiveComparison()
			.isEqualTo(tokenDto);
	}
	
	@Test
	public void 토큰_재발급_토큰정보_없는경우() throws Exception {
		String testRefreshToken = "testRefreshToken";
		RefreshTokenDto refreshTokenDto = new RefreshTokenDto.Builder()
				.setId(1)
				.setEmail("test@naver.com")
				.setToken("testRefreshToken")
				.build();
		
		
		when(refreshTokenDao.findByToken(anyString())).thenReturn(null);


		assertThatThrownBy(() -> authService.reissueToken(testRefreshToken))
			.isInstanceOf(NotFoundToken.class);
	}
	
	@Test
	public void 토큰_재발급_유저_없는경우() throws Exception {
		String testRefreshToken = "testRefreshToken";
		RefreshTokenDto refreshTokenDto = new RefreshTokenDto.Builder()
				.setId(1)
				.setEmail("test@naver.com")
				.setToken("testRefreshToken")
				.build();
		
		
		when(refreshTokenDao.findByToken(anyString())).thenReturn(refreshTokenDto);
		when(accountDao.findByEmail(anyString())).thenReturn(null);

		
		assertThatThrownBy(() -> authService.reissueToken(testRefreshToken))
			.isInstanceOf(NotFoundUserException.class);
	}
	
	private AccountDto createUser(int accountId) {
		return new AccountDto.Builder()
				.setAccountId(accountId)
				.setEmail("test@naver.com")
				.setPassword("")
				.setUsername("test")
				.setNickname("test")
				.setPhone("010-1234-1234")
				.setImage("")
				.setCreateDate(LocalDateTime.now())
				.setUpdateDate(LocalDateTime.now())
				.build();
	}
}
