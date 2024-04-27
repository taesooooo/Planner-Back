package com.planner.planner.Service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.planner.planner.Dao.AuthenticationCodeDao;
import com.planner.planner.Dto.AuthenticationCodeDto;
import com.planner.planner.Exception.AuthenticationCodeExpireException;
import com.planner.planner.Exception.NotFoundAuthenticationCodeException;
import com.planner.planner.Service.Impl.AuthenticationCodeServiceImpl;
import com.planner.planner.Util.RandomCode;

public class AuthenticationCodeServiceTest {
	
	@Mock
	private AuthenticationCodeDao authenticationCodeDao;
	@Mock
	private SENSService sensService;
	@Mock
	private RandomCode randomCode;
	
	@InjectMocks
	private AuthenticationCodeServiceImpl codeService;
	
	@Before
	public void setup() {
		MockitoAnnotations.openMocks(this);
	}
	
	@Test
	public void 휴대폰_인증코드_확인_인증_코드_전송_안된경우() {
		AuthenticationCodeDto requestDto = new AuthenticationCodeDto.Builder()
				.setId(1)
				.setPhone("01012345678")
				.setCode("123456")
				.setCreateDate(LocalDateTime.now())
				.build();
		
		when(authenticationCodeDao.findByPhone(anyString())).thenReturn(null);
		
		assertThatThrownBy(() -> codeService.codeCheck(requestDto))
		.isInstanceOf(NotFoundAuthenticationCodeException.class);
	}
	
	@Test
	public void 이메일_인증코드_확인_인증_코드_전송_안된경우() {
		AuthenticationCodeDto requestDto = new AuthenticationCodeDto.Builder()
				.setId(1)
				.setEmail("test@naver.com")
				.setCode("123456")
				.setCreateDate(LocalDateTime.now())
				.build();
		
		when(authenticationCodeDao.findByEmail(anyString())).thenReturn(null);
		
		assertThatThrownBy(() -> codeService.codeCheck(requestDto))
		.isInstanceOf(NotFoundAuthenticationCodeException.class);
	}
	
	@Test
	public void 휴대폰_인증코드_확인_인증_코드_시간_만료() {
		LocalDateTime dateTime = LocalDateTime.now();
		AuthenticationCodeDto requestDto = new AuthenticationCodeDto.Builder()
				.setId(1)
				.setPhone("01012345678")
				.setCode("123456")
				.setExpireDate(dateTime)
				.setCreateDate(dateTime)
				.build();
		
		AuthenticationCodeDto findCode = new AuthenticationCodeDto.Builder()
				.setId(1)
				.setPhone("01012345678")
				.setCode("123456")
				.setExpireDate(dateTime.minusMinutes(3))
				.setCreateDate(dateTime)
				.build();
		
		when(authenticationCodeDao.findByPhone(anyString())).thenReturn(findCode);
		
		assertThatThrownBy(() -> codeService.codeCheck(requestDto))
		.isInstanceOf(AuthenticationCodeExpireException.class);
	}
	
	@Test
	public void 이메일_인증코드_확인_인증_코드_시간_만료() {
		LocalDateTime dateTime = LocalDateTime.now();
		AuthenticationCodeDto requestDto = new AuthenticationCodeDto.Builder()
				.setId(1)
				.setEmail("test@naver.com")
				.setCode("123456")
				.setExpireDate(dateTime)
				.setCreateDate(dateTime)
				.build();
		
		AuthenticationCodeDto findCode = new AuthenticationCodeDto.Builder()
				.setId(1)
				.setEmail("test@naver.com")
				.setCode("123456")
				.setExpireDate(dateTime.minusMinutes(3))
				.setCreateDate(dateTime)
				.build();
		
		when(authenticationCodeDao.findByEmail(anyString())).thenReturn(findCode);
		
		assertThatThrownBy(() -> codeService.codeCheck(requestDto))
		.isInstanceOf(AuthenticationCodeExpireException.class);
	}
	
	@Test
	public void 휴대폰_인증코드_확인_인증_코드_불일치() {
		LocalDateTime dateTime = LocalDateTime.now();
		AuthenticationCodeDto requestDto = new AuthenticationCodeDto.Builder()
				.setId(1)
				.setPhone("01012345678")
				.setCode("123456")
				.setExpireDate(dateTime)
				.setCreateDate(dateTime)
				.build();
		
		AuthenticationCodeDto findCode = new AuthenticationCodeDto.Builder()
				.setId(1)
				.setPhone("01012345678")
				.setCode("789101")
				.setExpireDate(dateTime.plusMinutes(3))
				.setCreateDate(dateTime)
				.build();
		
		when(authenticationCodeDao.findByPhone(anyString())).thenReturn(findCode);
		
		boolean result = codeService.codeCheck(requestDto);
		
		assertThat(result).isFalse();
	}
	
	@Test
	public void 이메일_인증코드_확인_인증_코드_불일치() {
		LocalDateTime dateTime = LocalDateTime.now();
		AuthenticationCodeDto requestDto = new AuthenticationCodeDto.Builder()
				.setId(1)
				.setEmail("test@naver.com")
				.setCode("123456")
				.setExpireDate(dateTime)
				.setCreateDate(dateTime)
				.build();
		
		AuthenticationCodeDto findCode = new AuthenticationCodeDto.Builder()
				.setId(1)
				.setEmail("test@naver.com")
				.setCode("789101")
				.setExpireDate(dateTime.plusMinutes(3))
				.setCreateDate(dateTime)
				.build();
		
		when(authenticationCodeDao.findByEmail(anyString())).thenReturn(findCode);
		
		boolean result = codeService.codeCheck(requestDto);
		
		assertThat(result).isFalse();
	}
	
	@Test
	public void 휴대폰_인증코드_확인_정상() {
		LocalDateTime dateTime = LocalDateTime.now();
		AuthenticationCodeDto requestDto = new AuthenticationCodeDto.Builder()
				.setId(1)
				.setPhone("01012345678")
				.setCode("123456")
				.setExpireDate(dateTime)
				.setCreateDate(dateTime)
				.build();
		
		AuthenticationCodeDto findCode = new AuthenticationCodeDto.Builder()
				.setId(1)
				.setPhone("01012345678")
				.setCode("123456")
				.setExpireDate(dateTime.plusMinutes(3))
				.setCreateDate(dateTime)
				.build();
		
		when(authenticationCodeDao.findByPhone(anyString())).thenReturn(findCode);
		
		boolean result = codeService.codeCheck(requestDto);
		
		assertThat(result).isTrue();
	}
	
	@Test
	public void 이메일_인증코드_확인_정상() {
		LocalDateTime dateTime = LocalDateTime.now();
		AuthenticationCodeDto requestDto = new AuthenticationCodeDto.Builder()
				.setId(1)
				.setEmail("test@naver.com")
				.setCode("123456")
				.setExpireDate(dateTime)
				.setCreateDate(dateTime)
				.build();
		
		AuthenticationCodeDto findCode = new AuthenticationCodeDto.Builder()
				.setId(1)
				.setEmail("test@naver.com")
				.setCode("123456")
				.setExpireDate(dateTime.plusMinutes(3))
				.setCreateDate(dateTime)
				.build();
		
		when(authenticationCodeDao.findByEmail(anyString())).thenReturn(findCode);
		
		boolean result = codeService.codeCheck(requestDto);
		
		assertThat(result).isTrue();
	}
}
