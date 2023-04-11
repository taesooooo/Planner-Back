package com.planner.planner.Dao;

import java.util.List;

import com.planner.planner.Dto.PlanDto;
import com.planner.planner.Dto.PlanLocationDto;
import com.planner.planner.Dto.PlanMemberDto;
import com.planner.planner.Dto.PlanMemoDto;
import com.planner.planner.Dto.PlannerDto;

public interface PlannerDao {
	// 플래너 생성, 조회, 수정, 삭제
	public int insertPlanner(PlannerDto plannerDto);
	public PlannerDto findPlannerByPlannerId(int plannerId);
	public List<PlannerDto> findPlannersByAccountId(int accountId);
	public List<PlannerDto> findPlannerAll();
	public int updatePlanner(int plannerId, PlannerDto plannerDto);
	public int deletePlanner(int plannerId);
	
	// 플래너 멤버 생성, 조회, 수정, 삭제
	public int insertPlanMember(int plannerId, int accountId);
	public List<PlanMemberDto> findMembersByPlannerId(int plannerId);
	public int deletePlanMember(int plannerId, int accountId);
	public int acceptInvitation(int plannerId, int accountId);
	
	// 메모 생성, 조회, 수정, 삭제
	public int insertPlanMemo(int plannerId, PlanMemoDto planMemoDto);
	public List<PlanMemoDto> findPlanMemoByPlannerId(int plannerId);
	public int updatePlanMemo(int planMemoId, PlanMemoDto planMemoDto);
	public int deletePlanMemo(int planMemoId);
	
	// 일정 생성, 조회, 수정, 삭제
	public int insertPlan(PlanDto planDto);
	public List<PlanDto> findPlansByPlannerId(int plannerId);
	public int updatePlan(int planId, PlanDto planDto);
	public int deletePlan(int planId);
	
	// 여행지 생성, 조회, 수정, 삭제
	public int insertPlanLocation(PlanLocationDto planLocationDto);
	public List<PlanLocationDto> findPlanLocationsByPlanId(int planId);
	public int updatePlanLocation(int planLocationId, PlanLocationDto planLocationDto);
	public int deletePlanLocation(int planLocationDto);
	
	// 플래너 좋아요, 좋아요 취소
	public int plannerLike(int accountId, int plannerId);
	public int plannerUnLike(int accountId, int plannerId);
	public boolean isLike(int accountId, int plannerId);
	public List<PlannerDto> likePlannerList(int accountId);
}


