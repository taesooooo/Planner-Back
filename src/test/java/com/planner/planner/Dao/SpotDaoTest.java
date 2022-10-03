package com.planner.planner.Dao;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.planner.planner.Config.RootAppContext;
import com.planner.planner.Dao.SpotDao;
import com.planner.planner.Dao.SpotDaoImpl;
import com.planner.planner.Dto.SpotLikeDto;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { RootAppContext.class})
@Transactional
public class SpotDaoTest { 
	
	@Autowired
	private SpotDao spotDao;

	@Test
	public void spotLikeStateTest() {
		List<Integer> data = new ArrayList<Integer>();
		data.add(3);
		data.add(4);
		data.add(5);
		
		assertFalse(spotDao.spotLikeByContentIds(1, data).isEmpty());
	}

}
