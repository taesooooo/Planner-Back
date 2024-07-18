package com.planner.planner.Mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.planner.planner.Common.PageInfo;
import com.planner.planner.Common.Security.UserIdentifierDao;
import com.planner.planner.Dto.AccountDto;
import com.planner.planner.Dto.CommonRequestParamDto;
import com.planner.planner.Dto.PlannerDto;

@Mapper
public interface PlannerMapper extends UserIdentifierDao {
	// 플래너 생성, 조회, 수정, 삭제
	public int createPlanner(AccountDto user, PlannerDto plannerDto);
	
	@Override
	public PlannerDto findById(int dataId) throws Exception;

	public PlannerDto findByPlannerId(Integer accountId, int plannerId);

	public List<PlannerDto> findListByAccountId(Integer accountId, CommonRequestParamDto commonRequestParamDto,
			PageInfo pageInfo) throws Exception;

	public List<PlannerDto> findAll(Integer accountId, CommonRequestParamDto commonRequestParamDto,
			PageInfo pageInfo) throws Exception;

	public List<PlannerDto> findLikeList(Integer accountId, CommonRequestParamDto commonRequestParamDto,
			PageInfo pageInfo) throws Exception;

	public int updatePlanner(int plannerId, PlannerDto plannerDto);

	public int deletePlanner(int plannerId);

	public int getListTotalCount(Integer accountId, CommonRequestParamDto commonRequestParamDto) throws Exception;

	public int getLikeListTotalCount(Integer accountId, CommonRequestParamDto commonRequestParamDto) throws Exception;
}
