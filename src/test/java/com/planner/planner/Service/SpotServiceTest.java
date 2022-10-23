package com.planner.planner.Service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.planner.planner.Config.RootAppContext;
import com.planner.planner.Dao.SpotDaoImpl;
import com.planner.planner.Dto.SpotLikeCountDto;

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
	public void spotLike() {
		assertTrue(service.spotLike(1, 0));
	}

	@Test
	public void SpotCancel() {
		assertTrue(service.spotLikeCancel(1, 2));
	}
	
	@Test
	public void 여행지좋아요개수가져오기() {
		List<Integer> contentIds = Arrays.asList(3,4,5);
		String ids = contentIds.stream().map(String::valueOf).collect(Collectors.joining(","));
		List<SpotLikeCountDto> list = new ArrayList<SpotLikeCountDto>();
		SpotLikeCountDto spotLikeCountDto1 = new SpotLikeCountDto.Builder().setConetntId(3).setLikeCount(5).build();
		list.add(spotLikeCountDto1);
		List<SpotLikeCountDto> resultList = new ArrayList<SpotLikeCountDto>();
		SpotLikeCountDto resultSpotLikeCountDto = new SpotLikeCountDto.Builder().setConetntId(3).setLikeCount(5).build();
		resultList.add(resultSpotLikeCountDto);

		
		when(spotDao.spotLikeCount(ids)).thenReturn(resultList);
		
		assertEquals(resultList.get(0).getConetntId(), list.get(0).getConetntId());
		assertEquals(resultList.get(0).getLikeCount(), list.get(0).getLikeCount());
	}
}
