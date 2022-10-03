package com.planner.planner.Service;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.CALLS_REAL_METHODS;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.planner.planner.Config.JwtContext;
import com.planner.planner.Config.RootAppContext;
import com.planner.planner.Config.SecurityContext;
import com.planner.planner.Config.ServletAppContext;
import com.planner.planner.Dao.SpotDao;
import com.planner.planner.Dao.SpotDaoImpl;
import com.planner.planner.Dto.SpotDto;
import com.planner.planner.Dto.SpotLikeDto;
import com.planner.planner.Dto.SpotLikeStateDto;
import com.planner.planner.Service.SpotService;
import com.planner.planner.Service.SpotServiceImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { RootAppContext.class})
@Transactional
public class SpotServiceTest {
	private static final Logger logger = LoggerFactory.getLogger(SpotServiceTest.class);
	
	@Mock
	private SpotDaoImpl spotDao;
	
	@InjectMocks
	private SpotServiceImpl service;
	
	@Before
	public void setup() {
		MockitoAnnotations.openMocks(this);
	}
	
	@Test
	public void getSpotLikes() {
		List<SpotLikeDto> likes = service.spotLikesFindByAccountId(1);
		assertNotNull(likes);
		for(int i=0;i<likes.size();i++) {
			assertEquals(1, likes.get(i).getAccountId());			
		}
	}
	
	@Test
	public void 여행지_콘텐츠아이디별_좋아요_가져오기() {
		List<Integer> contentIds = Arrays.asList(3,4,5);
		List<SpotLikeDto> list = new ArrayList<SpotLikeDto>();
		list.add(new SpotLikeDto.Builder().setAccountId(1).setContentId(3).setLikeId(1).build());
		list.add(new SpotLikeDto.Builder().setAccountId(1).setContentId(4).setLikeId(2).build());
		list.add(new SpotLikeDto.Builder().setAccountId(1).setContentId(5).setLikeId(3).build());
		
		List<SpotLikeStateDto> resultList = new ArrayList<SpotLikeStateDto>();
		resultList.add(new SpotLikeStateDto(3,true));
		resultList.add(new SpotLikeStateDto(4,true));
		resultList.add(new SpotLikeStateDto(5,true));
		
		when(spotDao.spotLikeByContentIds(1, contentIds)).thenReturn(list);
		
		//		
		List<SpotLikeStateDto> likes = service.spotLikeStateCheck(1, contentIds);
		
		//
		for(int i=0;i<likes.size();i++) {
			assertEquals(likes.get(i).getContentId(), resultList.get(i).getContentId());
			assertEquals(likes.get(i).getState(), resultList.get(i).getState());
		}
	}
	
	@Test
	public void spotLike() {
		assertTrue(service.spotLike(1, 0));
	}
	
	@Test
	public void SpotCancel() {
		assertTrue(service.spotLikeCancel(1, 2));
	}
}
