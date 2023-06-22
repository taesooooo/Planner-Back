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
import java.util.Arrays;
import java.util.List;

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

import com.planner.planner.Common.Image;
import com.planner.planner.Common.Page;
import com.planner.planner.Common.PageInfo;
import com.planner.planner.Dao.AccountDao;
import com.planner.planner.Dto.AccountDto;
import com.planner.planner.Dto.PlannerDto;
import com.planner.planner.Exception.NotFoundUserException;
import com.planner.planner.Service.Impl.AccountServiceImpl;
import com.planner.planner.Service.Impl.PlannerServiceImpl;
import com.planner.planner.util.FileStore;

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
		 testDto = new AccountDto.Builder().setAccountId(1).setEmail("test@naver.com").setUserName("test").setNickName("test").build();
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
		Image image = new Image("Account\\test.jpg",dir.getAbsolutePath() + File.separator +"test.jpg","test", imageFile);

		when(fileStore.createFilePath(imageFile, "Account")).thenReturn(image);
		when(accountDao.accountImageUpdate(anyInt(), anyString())).thenReturn(true);

		boolean result = accountService.accountImageUpdate(testDto.getAccountId(), imageFile);

		verify(fileStore).createFilePath(imageFile, "Account");
		verify(accountDao).accountImageUpdate(anyInt(), anyString());

		assertTrue(new File(dir.getAbsolutePath() + File.separator + "test.jpg").exists());
		assertTrue(result);
	}

	@Test
	public void 계정패스트워드변경() {
		AccountDto ac = testDto;
		when(accountDao.passwordUpdate(ac)).thenReturn(true);

		boolean result = accountService.passwordUpdate(testDto);

		verify(accountDao).passwordUpdate(ac);

		assertTrue(result);
	}

	@Test
	public void 나의_플래너_가져오기() throws Exception {
		int testAccountId = 1;
		List<PlannerDto> testList = Arrays.asList(createPlanner(1),createPlanner(2));
		PageInfo pInfo = new PageInfo.Builder().setPageNum(1).setPageItemCount(10).build();
		Page<PlannerDto> plannerListPage = new Page.Builder<PlannerDto>()
				.setList(testList)
				.setPageInfo(pInfo)
				.setTotalCount(testList.size())
				.build();
		
		when(plannerService.findPlannersByAccountId(anyInt(),anyInt())).thenReturn(plannerListPage);

		Page<PlannerDto> list = accountService.myPlanners(1, testAccountId);
		
		verify(plannerService).findPlannersByAccountId(anyInt(), anyInt());
		
		assertEquals(testList.get(0).getAccountId(), list.getList().get(0).getAccountId());
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
		return new AccountDto.Builder().setAccountId(accountId).setEmail(email).setUserName(name).setNickName(nickName).build();
	}
	
	private PlannerDto createPlanner(int plannerId) {
		return new PlannerDto.Builder()
				.setPlannerId(plannerId)
				.setAccountId(1)
				.setCreator("test")
				.setTitle("테스트여행")
				.setPlanDateStart(LocalDate.of(2023, 1, 29))
				.setPlanDateEnd(LocalDate.of(2023, 1, 31))
				.setExpense(1000)
				.setMemberCount(1)
				.setMemberTypeId(1)
				.setLikeCount(0)
				.setCreateDate(LocalDateTime.of(2023, 1, 29, 00, 00))
				.setUpdateDate(LocalDateTime.of(2023, 1, 29, 00, 00))
				.build();
	}
}
