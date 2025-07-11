package com.planner.planner.Service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.File;
import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import com.planner.planner.Common.FileInfo;
import com.planner.planner.Dto.AccountDto;
import com.planner.planner.Exception.UserNotFoundException;
import com.planner.planner.Mapper.AccountMapper;
import com.planner.planner.Service.Impl.AccountServiceImpl;
import com.planner.planner.Service.Impl.PlannerServiceImpl;
import com.planner.planner.Util.FileStore;

@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {
	private static final Logger logger = LoggerFactory.getLogger(AccountServiceTest.class);

	// junit 임시폴더 테스트 종료시 자동 삭제(이미지 업로드 용도로 사용)
	@TempDir
	public Path folder;

	@Mock
	private FileStore fileStore;

	@Mock
	private AccountMapper accountMapper;

	@InjectMocks
	private AccountServiceImpl accountService;
	@Mock
	private PlannerServiceImpl plannerService;

	private AccountDto testDto;

	@BeforeEach
	public void setup() {
//		 MockitoAnnotations.openMocks(this);
		testDto = AccountDto.builder().accountId(1).email("test@naver.com").username("test").nickname("test").build();
	}

	@Test
	public void 회원_조회_아이디() throws Exception {
		int accountId = 1;
		AccountDto user = createAccount(1, "test@naver.com", "홍길동", "test");

		when(accountMapper.findById(anyInt())).thenReturn(user);

		AccountDto findUser = accountService.findById(accountId);

		verify(accountMapper).findById(accountId);

		assertThat(findUser.getAccountId()).isEqualTo(accountId);
	}

	@Test
	public void 회원_조회_아이디_없는경우() throws Exception {
		int accountId = 1;
		AccountDto user = createAccount(1, "test@naver.com", "홍길동", "test");

		when(accountMapper.findById(anyInt())).thenReturn(null);

		assertThatThrownBy(() -> accountService.findById(accountId)).isExactlyInstanceOf(UserNotFoundException.class);

		verify(accountMapper).findById(accountId);
	}

	@Test
	public void 회원_수정() throws Exception {
		AccountDto user = createAccount(1, "test@naver.com", "홍길동", "닉네임수정");

		when(accountMapper.update(anyInt(), anyString(), anyString())).thenReturn(true);

		boolean result = accountService.accountUpdate(1, "홍길동", "닉네임수정");

		verify(accountMapper).update(anyInt(), anyString(), anyString());

		assertThat(result).isTrue();
	}

	@Test
	public void 계정이미지변경() throws Exception {
		folder = folder.resolve("image\\test");
		folder.toFile().mkdirs();

		MockMultipartFile imageFile = new MockMultipartFile("image", "test.jpg", MediaType.IMAGE_JPEG_VALUE,
				"<<jpeg data>>".getBytes());

		FileInfo image = new FileInfo("Account\\test.jpg", folder.toAbsolutePath() + File.separator + "test.jpg",
				"test", imageFile);

		when(fileStore.createFilePath(imageFile, "Account")).thenReturn(image);
		when(accountMapper.accountImageUpdate(anyInt(), anyString())).thenReturn(true);

		boolean result = accountService.accountImageUpdate(testDto.getAccountId(), imageFile);

		verify(fileStore).createFilePath(imageFile, "Account");
		verify(accountMapper).accountImageUpdate(anyInt(), anyString());

		assertThat(new File(folder.toAbsolutePath() + File.separator + "test.jpg").exists()).isTrue();
		assertThat(result).isTrue();
	}

	@Test
	public void 이메일_검색() throws Exception {
		String testEmail = "test@naver.com";
		AccountDto user = createAccount(1, "test@naver.com", "홍길동", "test");
		when(accountMapper.searchEmail(testEmail)).thenReturn(user);

		boolean search = accountService.searchEmail(testEmail);

		assertThat(search).isTrue();
	}

	@Test
	public void 이메일_검색_실패_없는_이메일인경우() throws Exception {
		when(accountMapper.searchEmail(anyString())).thenReturn(null);

		assertThatThrownBy(() -> accountService.searchEmail("")).isExactlyInstanceOf(UserNotFoundException.class);
	}

	private AccountDto createAccount(int accountId, String email, String name, String nickName) {
		return AccountDto.builder().accountId(accountId).email(email).username(name).nickname(nickName).build();
	}
}
