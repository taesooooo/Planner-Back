package com.planner.planner.Service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.planner.planner.Common.Page;
import com.planner.planner.Dto.AccountDto;
import com.planner.planner.Dto.CommonRequestParamDto;
import com.planner.planner.Dto.FindEmailDto;
import com.planner.planner.Dto.PlannerDto;
import com.planner.planner.Dto.SpotLikeDto;

public interface AccountService {
	public AccountDto findById(int accountId) throws Exception;

	public List<String> findEmailByPhone(String phone) throws Exception;

	public AccountDto findByEmail(String email) throws Exception;

	public AccountDto findByNameAndPhone(String name, String phone) throws Exception;

	public boolean accountUpdate(int accountId, String nickname, String phone) throws Exception;

	public boolean accountImageUpdate(int accountId, MultipartFile image) throws Exception;

	public boolean passwordUpdate(int accountId, String password, String key) throws Exception;

	public Page<PlannerDto> myPlanners(int accountId, CommonRequestParamDto commonRequestParamDto) throws Exception;

	public Page<PlannerDto> likePlanners(int accountId, CommonRequestParamDto commonRequestParamDto) throws Exception;

	public Page<SpotLikeDto> likeSpots(int accountId, CommonRequestParamDto commonRequestParamDto) throws Exception;

	public boolean searchEmail(String searchEmail) throws Exception;

	public List<String> findId(FindEmailDto findEmailDto) throws Exception;
}
