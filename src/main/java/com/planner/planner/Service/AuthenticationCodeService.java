package com.planner.planner.Service;

import com.planner.planner.Dto.AuthenticationCodeDto;

public interface AuthenticationCodeService {
	public AuthenticationCodeDto findByPhone(String phone);
	public AuthenticationCodeDto findByEmail(String email);
	public boolean createPhoneAuthenticationCode(String phone, String code);
	public boolean createEmailAuthenticationCode(String email, String code);
	public boolean codeCheck(AuthenticationCodeDto authenticationCodeDto);
	public void delete(String phone);
}
