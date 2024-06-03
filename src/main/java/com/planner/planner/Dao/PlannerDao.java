package com.planner.planner.Dao;

import java.util.List;

import com.planner.planner.Common.PageInfo;
import com.planner.planner.Common.Security.UserIdentifierDao;
import com.planner.planner.Dto.CommonRequestParamDto;
import com.planner.planner.Dto.PlannerDto;

public interface PlannerDao extends UserIdentifierDao {
	// 플래너 생성, 조회, 수정, 삭제
	public int insertPlanner(PlannerDto plannerDto);

	public PlannerDto findPlannerByPlannerId(Integer accountId, int plannerId);

	public List<PlannerDto> findPlannersByAccountId(Integer accountId, CommonRequestParamDto commonRequestParamDto,
			PageInfo pageInfo) throws Exception;

	public List<PlannerDto> findPlannerAll(Integer accountId, CommonRequestParamDto commonRequestParamDto,
			PageInfo pageInfo) throws Exception;

	public List<PlannerDto> findLikePlannerList(Integer accountId, CommonRequestParamDto commonRequestParamDto,
			PageInfo pageInfo) throws Exception;

	public int updatePlanner(int plannerId, PlannerDto plannerDto);

	public int deletePlanner(int plannerId);

	public int getListTotalCount(Integer accountId, CommonRequestParamDto commonRequestParamDto) throws Exception;

	public int getLikeListTotalCount(Integer accountId, CommonRequestParamDto commonRequestParamDto) throws Exception;

//	public int getTotalCount(int accountId);
//
//	public int getTotalCountByLike(int accountId);
//
//	public int getTotalCountByLike(int accountId, String keyword);
//
//	public int getTotalCountByKeyword(String keyword);
//
//	public int getTotalCountByKeyword(int accountId, String keyword);
}
