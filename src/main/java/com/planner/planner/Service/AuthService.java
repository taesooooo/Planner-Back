package com.planner.planner.Service;

import com.planner.planner.Dto.AccountDto;
import com.planner.planner.Dto.LoginInfoDto;
import com.planner.planner.Dto.ReissueTokenDto;

public interface AuthService {
	public boolean register(AccountDto accountDto);
	public LoginInfoDto login(AccountDto accountDto) throws Exception;
	public boolean logout(int userId) throws Exception;
	public ReissueTokenDto reissueToken(String refreshToken) throws Exception;
}
