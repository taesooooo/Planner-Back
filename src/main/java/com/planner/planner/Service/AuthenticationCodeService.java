package com.planner.planner.Service;

import com.planner.planner.Dto.AuthenticationCodeDto;

public interface AuthenticationCodeService {
	public boolean codeSend(String phone) throws Exception;
	public boolean codeCheck(AuthenticationCodeDto authenticationCodeDto);
	public void delete(String phone);
}
