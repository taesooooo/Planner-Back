package com.planner.planner.Service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.planner.planner.Dto.AccountDto;
import com.planner.planner.Dto.ContentIdListDto;
import com.planner.planner.Dto.LikeDto;
import com.planner.planner.Dto.SpotLikeDto;
import com.planner.planner.Dto.SpotLikeStateDto;

public interface AccountService {
	public AccountDto findById(int accountId);
	public boolean accountUpdate(AccountDto accountDto) throws Exception;
	public boolean accountImageUpdate(int accountId, MultipartFile image) throws Exception;
	public boolean passwordUpdate(AccountDto accountDto);
	public void acceptInvite(int plannerId, int accountId);
	public LikeDto allLikes(int accountId);
	public List<SpotLikeDto> spotLikesByAccountId(int accountId);
	public List<SpotLikeStateDto> spotLikeStateCheck(int accountId, ContentIdListDto contentIds);
	public boolean searchEmail(String searchEmail) throws Exception;
}
