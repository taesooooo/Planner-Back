package com.planner.planner.Service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.planner.planner.Common.Page;
import com.planner.planner.Dto.AccountDto;
import com.planner.planner.Dto.ContentIdListDto;
import com.planner.planner.Dto.LikeDto;
import com.planner.planner.Dto.PlannerDto;
import com.planner.planner.Dto.SpotLikeDto;
import com.planner.planner.Dto.SpotLikeStateDto;

public interface AccountService {
	public AccountDto findById(int accountId);
	public boolean accountUpdate(AccountDto accountDto) throws Exception;
	public boolean accountImageUpdate(int accountId, MultipartFile image) throws Exception;
	public boolean passwordUpdate(AccountDto accountDto);
	public void acceptInvite(int plannerId, int accountId);
	public LikeDto allLikesList(int accountId);
	public Page<PlannerDto> getMyPlanner(int page, int accountId) throws Exception;
	public Page<PlannerDto> getLikePlanner(int page, int accountId) throws Exception;
	public List<SpotLikeDto> spotLikesByAccountId(int accountId);
	public List<SpotLikeStateDto> spotLikeStateCheck(int accountId, ContentIdListDto contentIds);
	public boolean searchEmail(String searchEmail) throws Exception;
}
