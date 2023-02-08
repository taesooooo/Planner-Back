package com.planner.planner;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.planner.planner.Config.RootAppContext;
import com.planner.planner.Dao.PlannerDao;
import com.planner.planner.Dto.PlanMemoDto;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { RootAppContext.class})
public class PlannerDaoTest {
	
	@Autowired
	private PlannerDao pDao;
	
	@Test
	public void test1() {
		PlanMemoDto memo = new PlanMemoDto.Builder().setTitle("aa").setContent("").build();
		int memoId = pDao.insertPlanMemo(2, memo);
		System.out.println(memoId);
	}
	
	@Test
	public void test2() {
		List<PlanMemoDto> list = pDao.findPlanMemoByPlannerId(1);
		System.out.println(list.get(0).toString());
	}

}
