package com.planner.planner.Service;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import com.planner.planner.Common.FileInfo;
import com.planner.planner.Dao.AccountDao;
import com.planner.planner.Dto.AccountDto;
import com.planner.planner.Dto.PlannerDto;
import com.planner.planner.Exception.NotFoundUserException;
import com.planner.planner.Service.Impl.AccountServiceImpl;
import com.planner.planner.Service.Impl.PlannerServiceImpl;
import com.planner.planner.Util.FileStore;

public class AccountServiceTest {
	private static final Logger logger = LoggerFactory.getLogger(AccountServiceTest.class);

	// junit 임시폴더 테스트 종료시 자동 삭제(이미지 업로드 용도로 사용)
	@Rule
    public final TemporaryFolder folder = new TemporaryFolder();

	@Mock
	private FileStore fileStore;

	@Mock
	private AccountDao accountDao;

	@InjectMocks
	private AccountServiceImpl accountService;
	@Mock
	private PlannerServiceImpl plannerService;

	private AccountDto testDto;

	@Before
	public void setup() {
		 MockitoAnnotations.openMocks(this);
		 testDto = new AccountDto.Builder().setAccountId(1).setEmail("test@naver.com").setUsername("test").setNickname("test").build();
	}
	
	@Test
	public void 회원_조회_아이디() throws Exception {
		int accountId = 1;
		AccountDto user = createAccount(1, "test@naver.com", "홍길동", "test");
		
		when(accountDao.findById(anyInt())).thenReturn(user);
		
		AccountDto findUser = accountService.findById(accountId);
		
		verify(accountDao).findById(accountId);
		assertEquals(findUser.getAccountId(), accountId);
	}
	
	@Test(expected = NotFoundUserException.class)
	public void 회원_조회_아이디_없는경우() throws Exception {
		int accountId = 1;
		AccountDto user = createAccount(1, "test@naver.com", "홍길동", "test");
		
		when(accountDao.findById(anyInt())).thenReturn(null);
		
		AccountDto findUser = accountService.findById(accountId);
		
		verify(accountDao).findById(accountId);
	}
	
	@Test
	public void 회원_수정() throws Exception {
		AccountDto user = createAccount(1, "test@naver.com", "홍길동", "닉네임수정");
		
		when(accountDao.update(user)).thenReturn(true);
		
		boolean result = accountService.accountUpdate(user);
		
		verify(accountDao).update(user);
		assertEquals(result, true);
	}

	@Test
	public void 계정이미지변경() throws Exception {
		MockMultipartFile imageFile = new MockMultipartFile("image", "test.jpg", MediaType.IMAGE_JPEG_VALUE,"<<jpeg data>>".getBytes());
		File dir = folder.newFolder("image\\test");
		FileInfo image = new FileInfo("Account\\test.jpg",dir.getAbsolutePath() + File.separator +"test.jpg","test", imageFile);

		when(fileStore.createFilePath(imageFile, "Account")).thenReturn(image);
		when(accountDao.accountImageUpdate(anyInt(), anyString())).thenReturn(true);

		boolean result = accountService.accountImageUpdate(testDto.getAccountId(), imageFile);

		verify(fileStore).createFilePath(imageFile, "Account");
		verify(accountDao).accountImageUpdate(anyInt(), anyString());

		assertTrue(new File(dir.getAbsolutePath() + File.separator + "test.jpg").exists());
		assertTrue(result);
	}
	
	@Test
	public void 이메일_검색() throws Exception {
		String testEmail = "test@naver.com";
		AccountDto user = createAccount(1, "test@naver.com", "홍길동", "test");
		when(accountDao.searchEmail(testEmail)).thenReturn(user);
		
		boolean search = accountService.searchEmail(testEmail);
		
		assertEquals(search, true);
	}
	
	@Test(expected = NotFoundUserException.class)
	public void 이메일_검색_실패_없는_이메일인경우() throws Exception {
		String testEmail = "";
		
		when(accountDao.searchEmail(testEmail)).thenReturn(null);
		
		boolean search = accountService.searchEmail(testEmail);
	}
	
	private AccountDto createAccount(int accountId, String email, String name, String nickName) {
		return new AccountDto.Builder().setAccountId(accountId).setEmail(email).setUsername(name).setNickname(nickName).build();
	}
}
