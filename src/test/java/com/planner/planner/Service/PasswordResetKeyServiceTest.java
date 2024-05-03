package com.planner.planner.Service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.planner.planner.Dao.AccountDao;
import com.planner.planner.Dao.PasswordResetKeyDao;
import com.planner.planner.Dto.AccountDto;
import com.planner.planner.Dto.PasswordResetkeyDto;
import com.planner.planner.Exception.NotFoundPasswordResetKeyException;
import com.planner.planner.Exception.NotFoundUserException;
import com.planner.planner.Service.Impl.PasswordResetKeyServiceImpl;
import com.planner.planner.Util.RandomCode;

@ExtendWith(MockitoExtension.class)
public class PasswordResetKeyServiceTest {
	
	@Mock
	private PasswordResetKeyDao passwordResetKeyDao;
	@Mock
	private AccountDao accountDao;
	@Mock
	private EmailService emailService;
	
	private RandomCode randomCode = new RandomCode();
	
	@InjectMocks
	private PasswordResetKeyServiceImpl passwordResetKeyService;
	
	@BeforeEach
	public void setUp() {
//		MockitoAnnotations.openMocks(this);
	}
	
	@Test
	public void 재설정키_생성_계정_없는경우() {
		String resetkey = randomCode.createStrCode(6, true);
		AccountDto user = AccountDto.builder()
				.accountId(1)
				.build();
		when(accountDao.findById(anyInt())).thenReturn(null);

		assertThatThrownBy(() -> { passwordResetKeyService.createPasswordResetKey(resetkey, user); })
		.isExactlyInstanceOf(NotFoundUserException.class);
	}
	
	@Test
	public void 재설정키_생성_계정_정상() throws Exception {
		String resetkey = randomCode.createStrCode(6, true);
		
		AccountDto user = AccountDto.builder()
				.accountId(1)
				.email("test@naver.com")
				.username("test")
				.nickname("test")
				.phone("01012345678")
				.build();
		
		when(accountDao.findById(anyInt())).thenReturn(user);
		
		passwordResetKeyService.createPasswordResetKey(resetkey, user);
	}
	
	@Test
	public void 재설정키_가져오기_없는경우() {
		String resetKey = randomCode.createStrCode(6, true);
		when(passwordResetKeyDao.findByResetKey(anyString())).thenReturn(null);
		
		assertThatThrownBy(() -> passwordResetKeyService.findBykey(resetKey))
		.isExactlyInstanceOf(NotFoundPasswordResetKeyException.class);
	}
	
	@Test
	public void 재설정키_가져오기_정상() {
		String resetKey = randomCode.createStrCode(6, true);
		PasswordResetkeyDto testResetKeyDto = PasswordResetkeyDto.builder()
				.id(1)
				.resetKey("")
				.expireDate(LocalDateTime.now())
				.accountId(1)
				.createDate(LocalDateTime.now())
				.build();
		
		when(passwordResetKeyDao.findByResetKey(anyString())).thenReturn(testResetKeyDto);
		
		PasswordResetkeyDto resetKeyDto = passwordResetKeyDao.findByResetKey(resetKey);
		
		assertThat(resetKeyDto)
		.usingRecursiveComparison()
		.isEqualTo(resetKeyDto);
	}
	
	@Test
	public void 재설정키_유효성검사_키_없는경우() {
		String resetKey = randomCode.createStrCode(6, true);
		when(passwordResetKeyDao.findByResetKey(anyString())).thenReturn(null);
		
		assertThatThrownBy(() -> passwordResetKeyService.validatePasswordResetKey(resetKey))
		.isExactlyInstanceOf(NotFoundPasswordResetKeyException.class);
	}
	
	@Test
	public void 재설정키_유효성검사_키_기간만료() {
		String resetKey = randomCode.createStrCode(6, true);
		PasswordResetkeyDto testResetKeyDto = PasswordResetkeyDto.builder()
				.id(1)
				.resetKey("")
				.expireDate(LocalDateTime.now().minusDays(1))
				.accountId(1)
				.createDate(LocalDateTime.now())
				.build();
		
		when(passwordResetKeyDao.findByResetKey(anyString())).thenReturn(testResetKeyDto);
		
		boolean check = passwordResetKeyService.validatePasswordResetKey(resetKey);
		
		assertThat(check).isFalse();
	}
	
	@Test
	public void 재설정키_유효성검사_정상() {
		String resetKey = randomCode.createStrCode(6, true);
		PasswordResetkeyDto testResetKeyDto = PasswordResetkeyDto.builder()
				.id(1)
				.resetKey("")
				.expireDate(LocalDateTime.now().plusDays(1))
				.accountId(1)
				.createDate(LocalDateTime.now())
				.build();
		
		when(passwordResetKeyDao.findByResetKey(anyString())).thenReturn(testResetKeyDto);
		
		boolean check = passwordResetKeyService.validatePasswordResetKey(resetKey);
		
		assertThat(check).isTrue();
	}
}
