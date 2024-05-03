package com.planner.planner.Service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.planner.planner.Dao.AuthenticationCodeDao;
import com.planner.planner.Dto.AuthenticationCodeDto;
import com.planner.planner.Exception.AuthenticationCodeExpireException;
import com.planner.planner.Exception.NotFoundAuthenticationCodeException;
import com.planner.planner.Service.Impl.AuthenticationCodeServiceImpl;
import com.planner.planner.Util.RandomCode;

@ExtendWith(MockitoExtension.class)
public class AuthenticationCodeServiceTest {
	
	@Mock
	private AuthenticationCodeDao authenticationCodeDao;
	@Mock
	private SENSService sensService;
	@Mock
	private RandomCode randomCode;
	
	@InjectMocks
	private AuthenticationCodeServiceImpl codeService;
	
	@BeforeEach
	public void setup() {
//		MockitoAnnotations.openMocks(this);
	}
	
	@Test
	public void 휴대폰_인증코드_확인_인증_코드_전송_안된경우() {
		AuthenticationCodeDto requestDto = AuthenticationCodeDto.builder()
				.id(1)
				.phone("01012345678")
				.code("123456")
				.createDate(LocalDateTime.now())
				.build();
		
		when(authenticationCodeDao.findByPhone(anyString())).thenReturn(null);
		
		assertThatThrownBy(() -> codeService.codeCheck(requestDto))
		.isInstanceOf(NotFoundAuthenticationCodeException.class);
	}
	
	@Test
	public void 이메일_인증코드_확인_인증_코드_전송_안된경우() {
		AuthenticationCodeDto requestDto = AuthenticationCodeDto.builder()
				.id(1)
				.email("test@naver.com")
				.code("123456")
				.createDate(LocalDateTime.now())
				.build();
		
		when(authenticationCodeDao.findByEmail(anyString())).thenReturn(null);
		
		assertThatThrownBy(() -> codeService.codeCheck(requestDto))
		.isInstanceOf(NotFoundAuthenticationCodeException.class);
	}
	
	@Test
	public void 휴대폰_인증코드_확인_인증_코드_시간_만료() {
		LocalDateTime dateTime = LocalDateTime.now();
		AuthenticationCodeDto requestDto = AuthenticationCodeDto.builder()
				.id(1)
				.phone("01012345678")
				.code("123456")
				.expireDate(dateTime)
				.createDate(dateTime)
				.build();
		
		AuthenticationCodeDto findCode = AuthenticationCodeDto.builder()
				.id(1)
				.phone("01012345678")
				.code("123456")
				.expireDate(dateTime.minusMinutes(3))
				.createDate(dateTime)
				.build();
		
		when(authenticationCodeDao.findByPhone(anyString())).thenReturn(findCode);
		
		assertThatThrownBy(() -> codeService.codeCheck(requestDto))
		.isInstanceOf(AuthenticationCodeExpireException.class);
	}
	
	@Test
	public void 이메일_인증코드_확인_인증_코드_시간_만료() {
		LocalDateTime dateTime = LocalDateTime.now();
		AuthenticationCodeDto requestDto = AuthenticationCodeDto.builder()
				.id(1)
				.email("test@naver.com")
				.code("123456")
				.expireDate(dateTime)
				.createDate(dateTime)
				.build();
		
		AuthenticationCodeDto findCode = AuthenticationCodeDto.builder()
				.id(1)
				.email("test@naver.com")
				.code("123456")
				.expireDate(dateTime.minusMinutes(3))
				.createDate(dateTime)
				.build();
		
		when(authenticationCodeDao.findByEmail(anyString())).thenReturn(findCode);
		
		assertThatThrownBy(() -> codeService.codeCheck(requestDto))
		.isInstanceOf(AuthenticationCodeExpireException.class);
	}
	
	@Test
	public void 휴대폰_인증코드_확인_인증_코드_불일치() {
		LocalDateTime dateTime = LocalDateTime.now();
		AuthenticationCodeDto requestDto = AuthenticationCodeDto.builder()
				.id(1)
				.phone("01012345678")
				.code("123456")
				.expireDate(dateTime)
				.createDate(dateTime)
				.build();
		
		AuthenticationCodeDto findCode = AuthenticationCodeDto.builder()
				.id(1)
				.phone("01012345678")
				.code("789101")
				.expireDate(dateTime.plusMinutes(3))
				.createDate(dateTime)
				.build();
		
		when(authenticationCodeDao.findByPhone(anyString())).thenReturn(findCode);
		
		boolean result = codeService.codeCheck(requestDto);
		
		assertThat(result).isFalse();
	}
	
	@Test
	public void 이메일_인증코드_확인_인증_코드_불일치() {
		LocalDateTime dateTime = LocalDateTime.now();
		AuthenticationCodeDto requestDto = AuthenticationCodeDto.builder()
				.id(1)
				.email("test@naver.com")
				.code("123456")
				.expireDate(dateTime)
				.createDate(dateTime)
				.build();
		
		AuthenticationCodeDto findCode = AuthenticationCodeDto.builder()
				.id(1)
				.email("test@naver.com")
				.code("789101")
				.expireDate(dateTime.plusMinutes(3))
				.createDate(dateTime)
				.build();
		
		when(authenticationCodeDao.findByEmail(anyString())).thenReturn(findCode);
		
		boolean result = codeService.codeCheck(requestDto);
		
		assertThat(result).isFalse();
	}
	
	@Test
	public void 휴대폰_인증코드_확인_정상() {
		LocalDateTime dateTime = LocalDateTime.now();
		AuthenticationCodeDto requestDto = AuthenticationCodeDto.builder()
				.id(1)
				.phone("01012345678")
				.code("123456")
				.expireDate(dateTime)
				.createDate(dateTime)
				.build();
		
		AuthenticationCodeDto findCode = AuthenticationCodeDto.builder()
				.id(1)
				.phone("01012345678")
				.code("123456")
				.expireDate(dateTime.plusMinutes(3))
				.createDate(dateTime)
				.build();
		
		when(authenticationCodeDao.findByPhone(anyString())).thenReturn(findCode);
		
		boolean result = codeService.codeCheck(requestDto);
		
		assertThat(result).isTrue();
	}
	
	@Test
	public void 이메일_인증코드_확인_정상() {
		LocalDateTime dateTime = LocalDateTime.now();
		AuthenticationCodeDto requestDto = AuthenticationCodeDto.builder()
				.id(1)
				.email("test@naver.com")
				.code("123456")
				.expireDate(dateTime)
				.createDate(dateTime)
				.build();
		
		AuthenticationCodeDto findCode = AuthenticationCodeDto.builder()
				.id(1)
				.email("test@naver.com")
				.code("123456")
				.expireDate(dateTime.plusMinutes(3))
				.createDate(dateTime)
				.build();
		
		when(authenticationCodeDao.findByEmail(anyString())).thenReturn(findCode);
		
		boolean result = codeService.codeCheck(requestDto);
		
		assertThat(result).isTrue();
	}
}
