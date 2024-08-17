package com.planner.planner.Service.Impl;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.planner.planner.Dto.AuthenticationCodeDto;
import com.planner.planner.Exception.AuthenticationCodeExpireException;
import com.planner.planner.Exception.AuthenticationCodeNotFoundException;
import com.planner.planner.Mapper.AuthenticationCodeMapper;
import com.planner.planner.Service.AuthenticationCodeService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationCodeServiceImpl implements AuthenticationCodeService {

	private final AuthenticationCodeMapper authentcationCodeMapper;

	@Override
	public boolean createPhoneAuthenticationCode(String phone, String code) {
		return authentcationCodeMapper.createByPhone(phone, code);
	}

	@Override
	public boolean createEmailAuthenticationCode(String email, String code) {
		return authentcationCodeMapper.createByEmail(email, code);
	}

	@Override
	public AuthenticationCodeDto findByPhone(String phone) {
		return authentcationCodeMapper.findByPhone(phone);
	}

	@Override
	public AuthenticationCodeDto findByEmail(String email) {
		return authentcationCodeMapper.findByEmail(email);
	}

	@Override
	public boolean codeCheck(AuthenticationCodeDto authenticationCodeDto) {
		boolean phoneCheck = (authenticationCodeDto.getPhone() != null && !authenticationCodeDto.getPhone().isEmpty());

		AuthenticationCodeDto authCodeDto = null;

		if (phoneCheck) {
			authCodeDto = this.authentcationCodeMapper.findByPhone(authenticationCodeDto.getPhone());
		} 
		else {
			authCodeDto = this.authentcationCodeMapper.findByEmail(authenticationCodeDto.getEmail());
		}

		if (authCodeDto == null) {
			throw new AuthenticationCodeNotFoundException("인증 코드가 전송되지 않았습니다. 다시 시도해 주세요.");
		}
		
		LocalDateTime nowDateTime = LocalDateTime.now();

		if (!authCodeDto.getExpireDate().isEqual(nowDateTime) && authCodeDto.getExpireDate().isBefore(nowDateTime)) {
			throw new AuthenticationCodeExpireException("인증 코드 시간이 만료되었습니다. 다시 시도하세요.");
		}

		if (authCodeDto.getCode().equals(authenticationCodeDto.getCode())) {
			if (phoneCheck) {
				this.authentcationCodeMapper.updateCodeConfirmByPhone(authenticationCodeDto.getPhone());
			} 
			else {
				this.authentcationCodeMapper.updateCodeConfirmByEmail(authenticationCodeDto.getEmail());
			}
		} 
		else {
			return false;
		}

		return true;
	}

	@Override
	public void delete(String phone) {
		this.authentcationCodeMapper.deleteByPhone(phone);
	}
}
