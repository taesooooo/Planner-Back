package com.planner.planner.Mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.planner.planner.Dto.PlanMemberDto;

@Mapper
public interface PlanMemberMapper {
	// 플래너 멤버 생성, 조회, 수정, 삭제
		public int insertPlanMember(int plannerId, int accountId) throws Exception;

		public List<PlanMemberDto> findPlanMemberListByPlannerId(int plannerId) throws Exception;

		public int deletePlanMember(int plannerId, int accountId) throws Exception;
}
