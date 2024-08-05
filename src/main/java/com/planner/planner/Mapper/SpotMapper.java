package com.planner.planner.Mapper;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.planner.planner.Common.PageInfo;
import com.planner.planner.Dto.CommonRequestParamDto;
import com.planner.planner.Dto.SpotLikeCountDto;
import com.planner.planner.Dto.SpotLikeDto;

@Mapper
public interface SpotMapper {
	public boolean createSpotLike(int accountId, SpotLikeDto spotLikeDto) throws SQLException;

	public boolean deleteSpotLike(int accountId, int contentId) throws Exception;

	public SpotLikeDto findSpotLike(int accountId, int contentId) throws Exception;

	public List<SpotLikeDto> findSpotLikeList(int accountId, CommonRequestParamDto commonRequestParamDto, PageInfo pageInfo)
			throws Exception;

	public int findListTotalCount(int accountId, CommonRequestParamDto commonRequestParamDto) throws Exception;

	public int findSpotLikeCountByContentId(int contentId) throws Exception;

	public List<SpotLikeCountDto> findSpotLikeCountByContentIdList(@Param("contentIdList") List<Integer> contentIdList) throws Exception;

	public boolean findSpotLikeStateByContentId(Integer accountId, List<Integer> contentIdList) throws Exception;

	public List<SpotLikeDto> findSpotLikeStateByContentIdList(Integer accountId, List<Integer> contentIdList)
			throws Exception;
}
