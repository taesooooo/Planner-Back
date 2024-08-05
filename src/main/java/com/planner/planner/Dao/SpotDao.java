package com.planner.planner.Dao;

import java.sql.SQLException;
import java.util.List;

import com.planner.planner.Common.PageInfo;
import com.planner.planner.Common.Security.UserIdentifierDao;
import com.planner.planner.Dto.CommonRequestParamDto;
import com.planner.planner.Dto.SpotLikeCountDto;
import com.planner.planner.Dto.SpotLikeDto;

public interface SpotDao {
	public boolean createSpotLike(int accountId, SpotLikeDto spotLikeDto) throws SQLException;

	public boolean deleteSpotLike(int accountId, int contentId) throws Exception;
	
	public SpotLikeDto findSpotLike(int accountId, int contentId) throws Exception;

	public List<SpotLikeDto> findSpotLikeList(int accountId, CommonRequestParamDto requestParamDto, PageInfo pageInfo)
			throws Exception;

	public int findListTotalCount(int accountId, CommonRequestParamDto requestParamDto) throws Exception;

	public int findSpotLikeCountByContentId(int contentId) throws Exception;

	public List<SpotLikeCountDto> findSpotLikeCountByContentIdList(List<Integer> contentIdList) throws Exception;

	public boolean findSpotLikeStateByContentId(Integer accountId, int contentId) throws Exception;

	public List<SpotLikeDto> findSpotLikeStateByContentIdList(Integer accountId, List<Integer> contentIdList)
			throws Exception;
}
