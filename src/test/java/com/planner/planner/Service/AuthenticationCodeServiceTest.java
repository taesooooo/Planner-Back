package com.planner.planner.Service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.planner.planner.Dao.AuthenticationCodeDao;
import com.planner.planner.Dto.AuthenticationCodeDto;
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
	public void 인증코드_전송_테스트_정상() throws Exception {
		when(randomCode.createCode()).thenReturn("123456");
		when(authenticationCodeDao.insert(anyString(), anyString())).thenReturn(true);
		when(sensService.authenticationCodeSMSSend(anyString(), anyString())).thenReturn(true);
		
		boolean result = codeService.codeSend("01012345678");
		
		assertThat(result).isTrue();
	}
	
	@Test
	public void 인증코드_전송_테스트_DB_저장_실패() throws Exception {
		when(randomCode.createCode()).thenReturn("123456");
		when(authenticationCodeDao.insert(anyString(), anyString())).thenReturn(false);
		when(sensService.authenticationCodeSMSSend(anyString(), anyString())).thenReturn(true);
		
		boolean result = codeService.codeSend("01012345678");
		
		assertThat(result).isFalse();
	}
	
	@Test
	public void 인증코드_전송_테스트_SMS_전송_실패() throws Exception {
		when(randomCode.createCode()).thenReturn("123456");
		when(authenticationCodeDao.insert(anyString(), anyString())).thenReturn(true);
		when(sensService.authenticationCodeSMSSend(anyString(), anyString())).thenReturn(false);
		
		boolean result = codeService.codeSend("01012345678");
		
		assertThat(result).isFalse();
	}
	
	@Test
	public void 인증코드_확인_정상() {
		AuthenticationCodeDto requestDto = new AuthenticationCodeDto.Builder()
				.setId(1)
				.setPhone("01012345678")
				.setCode("123456")
				.setCreateDate(LocalDateTime.now())
				.build();
		
		AuthenticationCodeDto findCode = new AuthenticationCodeDto.Builder()
				.setId(1)
				.setPhone("01012345678")
				.setCode("123456")
				.setCreateDate(LocalDateTime.now())
				.build();
		
		when(authenticationCodeDao.find(anyString())).thenReturn(findCode);
		
		boolean result = codeService.codeCheck(requestDto);
		
		assertThat(result).isTrue();
	}
	
	@Test
	public void 인증코드_확인_코드_다를경우() {
		AuthenticationCodeDto requestDto = new AuthenticationCodeDto.Builder()
				.setId(1)
				.setPhone("01012345678")
				.setCode("123456")
				.setCreateDate(LocalDateTime.now())
				.build();
		
		AuthenticationCodeDto findCode = new AuthenticationCodeDto.Builder()
				.setId(1)
				.setPhone("01012345678")
				.setCode("654321")
				.setCreateDate(LocalDateTime.now())
				.build();
		
		when(authenticationCodeDao.find(anyString())).thenReturn(findCode);
		
		boolean result = codeService.codeCheck(requestDto);
		
		assertThat(result).isFalse();
	}
}
