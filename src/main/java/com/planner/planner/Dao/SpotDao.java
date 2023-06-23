package com.planner.planner.Dao;

import java.sql.SQLException;
import java.util.List;

import com.planner.planner.Common.PageInfo;
import com.planner.planner.Common.SortCriteria;
import com.planner.planner.Dto.SpotLikeCountDto;
import com.planner.planner.Dto.SpotLikeDto;

public interface SpotDao {
	public boolean insertSpotLike(int accountId, SpotLikeDto spotLikeDto)throws SQLException;
	public boolean deleteSpotLike(int accountId, int contentId) throws Exception;
	
	public int getTotalCountByAccountId(int accountId) throws Exception;
	public int getTotalCountByAccountId(int accountId, String keyword) throws Exception;
	public List<SpotLikeDto> selectSpotLikeList(int accountId, SortCriteria sortCriteria, String keyword, PageInfo pageInfo) throws Exception;
	
	public int selectSpotLikeCountByContentId(int contentId) throws Exception;
	public List<SpotLikeCountDto> selectSpotLikeCountByContentIdList(List<Integer> contentIdList) throws Exception;
	public boolean selectSpotLikeByContentId(int accountId, int contentId) throws Exception;
	public List<SpotLikeDto> selectSpotLikeByContentIdList(int accountId, List<Integer> contentIdList) throws Exception;
}
