package com.planner.planner.Dao;

import java.sql.SQLException;
import java.util.List;

import com.planner.planner.Dto.SpotLikeDto;

public interface SpotDao {
	public boolean insertSpotLike(int accountId, int contentId)throws SQLException;
	public boolean deleteSpotLike(int accountId, int contentId) throws Exception;
	public int selectSpotLikeCountByContentId(int contentId) throws Exception;
	public boolean selectSpotLikeByContentId(int accountId, int contentId) throws Exception;
	public List<SpotLikeDto> selectSpotLikeByContentIdList(int accountId, List<Integer> contentIdList) throws Exception;
}
