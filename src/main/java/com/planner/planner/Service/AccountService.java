package com.planner.planner.Service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.planner.planner.Dto.AccountDto;
import com.planner.planner.Dto.LikeDto;

public interface AccountService {
	public boolean register(AccountDto accountDto);
	public AccountDto login(AccountDto accountDto);
	
	public AccountDto findById(int accountId);
	public AccountDto accountUpdate(AccountDto accountDto, MultipartFile image) throws Exception;
	public boolean accountImageUpdate(int accountId, MultipartFile image) throws Exception;
	public boolean passwordUpdate(AccountDto accountDto);
	public LikeDto allLikes(int accountId);
}
