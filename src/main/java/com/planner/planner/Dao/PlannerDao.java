package com.planner.planner.Dao;

import java.util.List;

import com.planner.planner.Common.PageInfo;
import com.planner.planner.Common.SortCriteria;
import com.planner.planner.Dto.PlannerDto;

public interface PlannerDao {
	// 플래너 생성, 조회, 수정, 삭제
	public int insertPlanner(PlannerDto plannerDto);

	public PlannerDto findPlannerByPlannerId(int plannerId);

	public List<PlannerDto> findPlannersByAccountId(int accountId, SortCriteria criteria, String keyword,
			PageInfo pageInfo);

	public List<PlannerDto> findPlannerAll(Integer accountId, SortCriteria criteria, String keyword, PageInfo pageInfo);
	
	public List<PlannerDto> likePlannerList(int accountId, SortCriteria criteria, String keyword, PageInfo pageInfo);

	public int updatePlanner(int plannerId, PlannerDto plannerDto);

	public int deletePlanner(int plannerId);

	public int getTotalCount();

	public int getTotalCount(int accountId);

	public int getTotalCountByLike(int accountId);

	public int getTotalCountByLike(int accountId, String keyword);

	public int getTotalCountByKeyword(String keyword);

	public int getTotalCountByKeyword(int accountId, String keyword);
}
