package com.planner.planner.Service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.planner.planner.Dto.AccountDto;
import com.planner.planner.Dto.LoginInfoDto;
import com.planner.planner.Dto.RefreshTokenDto;
import com.planner.planner.Dto.ReissueTokenDto;
import com.planner.planner.Exception.PasswordCheckFailException;
import com.planner.planner.Exception.TokenNotFoundException;
import com.planner.planner.Exception.UserNotFoundException;
import com.planner.planner.Mapper.AccountMapper;
import com.planner.planner.Mapper.RefreshTokenMapper;
import com.planner.planner.Service.Impl.AuthServiceImpl;
import com.planner.planner.Util.JwtUtil;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {
	@Mock
	private AccountMapper accountMapper;
	@Mock
	private BCryptPasswordEncoder passwordEncoder;
	@Mock
	private RefreshTokenMapper refreshTokenMapper;
	@Mock
	private JwtUtil jwtUtil;
	@InjectMocks
	private AuthServiceImpl authService;

	@BeforeEach
	public void setup() {
//		 MockitoAnnotations.openMocks(this);
	}
	
	@Test
	public void 회원가입() {
		AccountDto newTestUser = createUser(1);
		
		when(accountMapper.create(any())).thenReturn(true);
		
		boolean user = authService.register(newTestUser);
		
		assertThat(user).isTrue();
	}
	
	@Test
	public void 로그인() throws Exception {
		AccountDto newTestUser = createUser(1);
		String accessToken = "accessToken";
		String refreshToken = "refreshToken";
		RefreshTokenDto refreshTokenDto = RefreshTokenDto.builder()
				.email("test@naver.com")
				.id(1)
				.token("testRefershToken")
				.build();
		
		LoginInfoDto testLoginInfo = LoginInfoDto.builder()
				.user(newTestUser)
				.accessToken(accessToken)
				.refreshToken(refreshToken)
				.build();
		
		when(accountMapper.findByEmail(anyString())).thenReturn(newTestUser);
		when(passwordEncoder.matches(anyString(), anyString())).thenReturn(true);
		when(jwtUtil.createAccessToken(anyInt())).thenReturn(accessToken);
		when(jwtUtil.createRefreshToken()).thenReturn(refreshToken);
		when(refreshTokenMapper.findByEmail(anyString())).thenReturn(refreshTokenDto);
		
		LoginInfoDto user = authService.login(newTestUser);
		
		assertThat(user)
			.usingRecursiveComparison()
			.isEqualTo(testLoginInfo);
	}
	
	@Test
	public void 로그인_비밀번호_틀린경우() throws Exception {
		AccountDto newTestUser = createUser(1);
		
		when(accountMapper.findByEmail(anyString())).thenReturn(newTestUser);
		when(passwordEncoder.matches(anyString(), anyString())).thenReturn(false);

		
		assertThatThrownBy(() -> authService.login(newTestUser))
			.isInstanceOf(PasswordCheckFailException.class);
	}
	
	@Test
	public void 로그인_아이디_없는경우() throws Exception {
		AccountDto newTestUser = createUser(1);
		
		when(accountMapper.findByEmail(anyString())).thenReturn(null);

		assertThatThrownBy(() -> authService.login(newTestUser))
			.isInstanceOf(UserNotFoundException.class);
	}
	
	@Test
	public void 로그인_리프래시_토큰_없는경우_새로_저장() throws Exception {
		AccountDto newTestUser = createUser(1);
		String accessToken = "accessToken";
		String refreshToken = "refreshToken";
		
		LoginInfoDto testLoginInfo = LoginInfoDto.builder()
				.user(newTestUser)
				.accessToken(accessToken)
				.refreshToken(refreshToken)
				.build();
		
		when(accountMapper.findByEmail(anyString())).thenReturn(newTestUser);
		when(passwordEncoder.matches(anyString(), anyString())).thenReturn(true);
		when(jwtUtil.createAccessToken(anyInt())).thenReturn(accessToken);
		when(jwtUtil.createRefreshToken()).thenReturn(refreshToken);
		when(refreshTokenMapper.findByEmail(anyString())).thenReturn(null);
		
		LoginInfoDto user = authService.login(newTestUser);
		
		verify(refreshTokenMapper).createRefreshToken(newTestUser.getEmail(), refreshToken);
		
		assertThat(user)
			.usingRecursiveComparison()
			.isEqualTo(testLoginInfo);
	}
	
	@Test
	public void 로그인_리프래시_토큰_있는경우_변경() throws Exception {
		AccountDto newTestUser = createUser(1);
		String accessToken = "accessToken";
		String refreshToken = "refreshToken";
		RefreshTokenDto refreshTokenDto = RefreshTokenDto.builder()
				.email("test@naver.com")
				.id(1)
				.token("testRefershToken")
				.build();
		
		LoginInfoDto testLoginInfo = LoginInfoDto.builder()
				.user(newTestUser)
				.accessToken(accessToken)
				.refreshToken(refreshToken)
				.build();
		
		when(accountMapper.findByEmail(anyString())).thenReturn(newTestUser);
		when(passwordEncoder.matches(anyString(), anyString())).thenReturn(true);
		when(jwtUtil.createAccessToken(anyInt())).thenReturn(accessToken);
		when(jwtUtil.createRefreshToken()).thenReturn(refreshToken);
		when(refreshTokenMapper.findByEmail(anyString())).thenReturn(refreshTokenDto);
		
		LoginInfoDto user = authService.login(newTestUser);
		
		verify(refreshTokenMapper).updateRefreshToken(newTestUser.getEmail(), refreshToken);
		
		assertThat(user)
			.usingRecursiveComparison()
			.isEqualTo(testLoginInfo);
	}
	
	@Test
	public void 토큰_재발급() throws Exception {
		AccountDto newTestUser = createUser(1);
		String testRefreshToken = "testRefreshToken";
		RefreshTokenDto refreshTokenDto = RefreshTokenDto.builder()
				.id(1)
				.email("test@naver.com")
				.token("testRefreshToken")
				.build();
		String newAccessToken = "newAccessToken";
		String newRefreshToken = "newRefreshToken";
		ReissueTokenDto tokenDto = ReissueTokenDto.builder()
				.accessToken(newAccessToken)
				.refreshToken(newRefreshToken)
				.build();
		
		
		when(refreshTokenMapper.findByToken(anyString())).thenReturn(refreshTokenDto);
		when(accountMapper.findByEmail(anyString())).thenReturn(newTestUser);
		when(jwtUtil.createAccessToken(anyInt())).thenReturn(newAccessToken);
		when(jwtUtil.createRefreshToken()).thenReturn(newRefreshToken);
		when(refreshTokenMapper.updateRefreshToken(anyString(), anyString())).thenReturn(true);
		
		ReissueTokenDto newTokenDto = authService.reissueToken(testRefreshToken);
		
		assertThat(newTokenDto)
			.usingRecursiveComparison()
			.isEqualTo(tokenDto);
	}
	
	@Test
	public void 토큰_재발급_토큰정보_없는경우() throws Exception {
		String testRefreshToken = "testRefreshToken";
		RefreshTokenDto refreshTokenDto = RefreshTokenDto.builder()
				.id(1)
				.email("test@naver.com")
				.token("testRefreshToken")
				.build();
		
		
		when(refreshTokenMapper.findByToken(anyString())).thenReturn(null);


		assertThatThrownBy(() -> authService.reissueToken(testRefreshToken))
			.isInstanceOf(TokenNotFoundException.class);
	}
	
	@Test
	public void 토큰_재발급_유저_없는경우() throws Exception {
		String testRefreshToken = "testRefreshToken";
		RefreshTokenDto refreshTokenDto = RefreshTokenDto.builder()
				.id(1)
				.email("test@naver.com")
				.token("testRefreshToken")
				.build();
		
		
		when(refreshTokenMapper.findByToken(anyString())).thenReturn(refreshTokenDto);
		when(accountMapper.findByEmail(anyString())).thenReturn(null);

		
		assertThatThrownBy(() -> authService.reissueToken(testRefreshToken))
			.isInstanceOf(UserNotFoundException.class);
	}
	
	private AccountDto createUser(int accountId) {
		return AccountDto.builder()
				.accountId(accountId)
				.email("test@naver.com")
				.password("")
				.username("test")
				.nickname("test")
				.phone("010-1234-1234")
				.image("")
				.createDate(LocalDateTime.now())
				.updateDate(LocalDateTime.now())
				.build();
	}
}
