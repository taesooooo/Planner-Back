package com.planner.planner.Service;

import java.util.List;

import com.planner.planner.Common.Page;
import com.planner.planner.Dto.PlanDto;
import com.planner.planner.Dto.PlanLocationDto;
import com.planner.planner.Dto.PlanMemberDto;
import com.planner.planner.Dto.PlanMemoDto;
import com.planner.planner.Dto.PlannerDto;

public interface PlannerService {
	// 플래너 생성, 가져오기, 수정, 삭제
	public int newPlanner(PlannerDto plannerDto) throws Exception;
	public PlannerDto findPlannerByPlannerId(int plannerId) throws Exception;
	public Page<PlannerDto> findPlannersByAccountId(int itemCount, int page, int accountId) throws Exception;
	public Page<PlannerDto> findPlannerAll(int itemCount, int page) throws Exception;
	public void updatePlanner(PlannerDto plannerDto) throws Exception;
	public void deletePlanner(int plannerId) throws Exception;
	
	// 메모, 일정, 여행지 플래너 메소드에서 모두 가져옴
	// 메모 추가, 수정, 삭제
	public int newMemo(int plannerId, PlanMemoDto planMemoDto);
	public void updateMemo(int memoId, PlanMemoDto planMemoDto);
	public void deleteMemo(int memoId);
	
	// 플래너 멤버 추가, 가져오기, 삭제
	public List<PlanMemberDto> findMembersByPlannerId(int plannerId) throws Exception;
	public void inviteMembers(int plannerId, List<String> nickNames) throws Exception;
	public void deleteMember(int plannerId, String nickName) throws Exception;
	
	// 일정 추가, 수정, 삭제
	public int newPlan(PlanDto planDto) throws Exception;
	public void updatePlan(int planId, PlanDto planDto) throws Exception;
	public void deletePlan(int planId) throws Exception;
	
	// 일정 여행지 추가, 수정, 삭제
	public int newPlanLocation(PlanLocationDto planLocationDto) throws Exception;
	public void updatePlanLocation(int planLocationId, PlanLocationDto planLocationDto) throws Exception;
	public void deletePlanLocation(int planLocationId) throws Exception;
	
	// 플래너 좋아요, 좋아요 취소, 좋아요 목록 가져오기
	public void plannerLikeOrUnLike(int accountId, int plannerId);
	public Page<PlannerDto> getLikePlannerList(int itemCount, int page, int accountId) throws Exception;
}
