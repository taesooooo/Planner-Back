package com.planner.planner.Service;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
import com.planner.planner.Dao.AccountDao;
import com.planner.planner.Dto.AccountDto;
import com.planner.planner.Dto.ContentIdListDto;
import com.planner.planner.Dto.LikeDto;
import com.planner.planner.Dto.PlannerDto;
import com.planner.planner.Dto.SpotLikeDto;
import com.planner.planner.Dto.SpotLikeStateDto;
import com.planner.planner.Entity.Account;
import com.planner.planner.Service.Impl.AccountServiceImpl;
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

	private AccountDto testDto;

	@Before
	public void setup() {
		 MockitoAnnotations.openMocks(this);
		 testDto = new AccountDto.Builder().setAccountId(1).setEmail("test@naver.com").setUserName("test").setNickName("test").build();
	}

	@Test
	public void 계정이미지변경() throws Exception {
		MockMultipartFile imageFile = new MockMultipartFile("image", "test.jpg", MediaType.IMAGE_JPEG_VALUE,"<<jpeg data>>".getBytes());
		File dir = folder.newFolder("image\\test");
		Image image = new Image("Account\\test.jpg",dir.getAbsolutePath() + "\\test.jpg","test");

		when(fileStore.createFilePath(imageFile, "Account")).thenReturn(image);
		when(accountDao.accountImageUpdate(anyInt(), anyString())).thenReturn(true);

		boolean result = accountService.accountImageUpdate(testDto.getAccountId(), imageFile);

		verify(fileStore).createFilePath(imageFile, "Account");
		verify(accountDao).accountImageUpdate(anyInt(), anyString());

		assertTrue(new File(dir.getAbsolutePath()+ "\\test.jpg").exists());
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
	public void 계정좋아요모두가져오기() {
		List<PlannerDto> likesP = new ArrayList<>();
		List<SpotLikeDto> likesS = new ArrayList<>();
		for(int i=0;i<3;i++) {
			PlannerDto planner = new PlannerDto.Builder()
					.setPlannerId(i)
					.setAccountId(i)
					.setMemberCount(1)
					.setMember("test")
					.setTitle("test"+i)
					.setLikeCount(i)
					.setPlan("")
					.setPlanDateStart(LocalDate.now())
					.setPlanDateEnd(LocalDate.now())
					.setUpdateDate(LocalDateTime.now()).build();
			SpotLikeDto like = new SpotLikeDto.Builder().setLikeId(i).setAccountId(i).setContentId(i).setLikeDate(null).build();
			likesP.add(planner);
			likesS.add(like);
		}
		LikeDto testLikeDto = new LikeDto.Builder().setLikePlanners(likesP).setLikeSpots(likesS).build();

		when(accountDao.likePlanners(testDto.getAccountId())).thenReturn(likesP);
		when(accountDao.likeSpots(testDto.getAccountId())).thenReturn(likesS);

		LikeDto likes = accountService.allLikes(testDto.getAccountId());

		verify(accountDao).likePlanners(testDto.getAccountId());
		verify(accountDao).likeSpots(testDto.getAccountId());

		assertNotNull(likes.getLikePlanners());
		assertTrue(likes.getLikePlanners().size() > 0);

		assertNotNull(likes.getLikeSpots());
		assertTrue(likes.getLikeSpots().size() > 0);
	}

	@Test
	public void 좋아요여행지가져오기() {
		List<SpotLikeDto> likes = accountService.spotLikesByAccountId(1);
		assertNotNull(likes);
		for (SpotLikeDto like : likes) {
			assertEquals(1, like.getAccountId());
		}
	}

	@Test
	public void 좋아요한여행지확인() {
		ContentIdListDto contentIds = new ContentIdListDto(Arrays.asList(3,4,5));
		List<SpotLikeDto> list = new ArrayList<>();
		list.add(new SpotLikeDto.Builder().setAccountId(1).setContentId(3).setLikeId(1).build());
		list.add(new SpotLikeDto.Builder().setAccountId(1).setContentId(4).setLikeId(2).build());
		list.add(new SpotLikeDto.Builder().setAccountId(1).setContentId(5).setLikeId(3).build());

		List<SpotLikeStateDto> resultList = new ArrayList<>();
		resultList.add(new SpotLikeStateDto(3,true));
		resultList.add(new SpotLikeStateDto(4,true));
		resultList.add(new SpotLikeStateDto(5,true));

		when(accountDao.spotLikesByContentIds(1, contentIds.getContentIds())).thenReturn(list);

		//
		List<SpotLikeStateDto> likes = accountService.spotLikeStateCheck(1, contentIds);

		//
		for(int i=0;i<likes.size();i++) {
			assertEquals(likes.get(i).getContentId(), resultList.get(i).getContentId());
			assertEquals(likes.get(i).getState(), resultList.get(i).getState());
		}
	}
}
