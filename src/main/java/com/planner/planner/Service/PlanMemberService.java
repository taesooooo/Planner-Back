package com.planner.planner.Service;

import java.util.List;

import com.planner.planner.Dto.PlanMemberDto;

public interface PlanMemberService {
	// 플래너 멤버 추가, 가져오기, 삭제
	public List<PlanMemberDto> findMembersByPlannerId(int plannerId) throws Exception;

	public void inviteMembers(int plannerId, List<String> nickNames) throws Exception;

	public void deleteMember(int plannerId, String nickName) throws Exception;

}
