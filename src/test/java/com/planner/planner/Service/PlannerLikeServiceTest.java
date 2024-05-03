package com.planner.planner.Service;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.planner.planner.Dao.PlannerLikeDao;
import com.planner.planner.Service.Impl.PlannerLikeServiceImpl;

@ExtendWith(MockitoExtension.class)
public class PlannerLikeServiceTest {
	@InjectMocks
	private PlannerLikeServiceImpl plannerLikeService;
	
	@Mock
	private PlannerLikeDao plannerLikeDao;
	

	@BeforeEach
	public void setUp() throws Exception {
//		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void 플래너_좋아요() {
		int accountId = 1;
		int plannerId = 1;
		when(plannerLikeDao.isLike(accountId, plannerId)).thenReturn(false);
		
		plannerLikeService.plannerLikeOrUnLike(accountId, plannerId);
		
		verify(plannerLikeDao).plannerLike(accountId, plannerId);
	}
	
	@Test
	public void 플래너_좋아요_취소() {
		int accountId = 1;
		int plannerId = 1;
		when(plannerLikeDao.isLike(accountId, plannerId)).thenReturn(true);
		
		plannerLikeService.plannerLikeOrUnLike(accountId, plannerId);
		
		verify(plannerLikeDao).plannerUnLike(accountId, plannerId);
	}

}
