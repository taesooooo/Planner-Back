package com.planner.planner.Dao;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.planner.planner.Config.RootAppContext;
import com.planner.planner.Dto.SpotLikeCountDto;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { RootAppContext.class })
@Transactional
public class SpotDaoTest {
	@Autowired
	private SpotDao spotDao;

	@Test
	public void 여행지_좋아요개수_가져오기() {
		List<Integer> contentIds = Arrays.asList(3, 4, 5);
		String ids = contentIds.stream().map(String::valueOf).collect(Collectors.joining(","));

		List<SpotLikeCountDto> list = spotDao.spotLikeCount(ids);

		assertEquals(list.get(0).getConetntId(), 3);
	}

}
