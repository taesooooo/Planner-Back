package com.planner.planner.Service.Impl;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.planner.planner.Dao.AuthenticationCodeDao;
import com.planner.planner.Dto.AuthenticationCodeDto;
import com.planner.planner.Exception.AuthenticationCodeExpireException;
import com.planner.planner.Exception.NotFoundAuthenticationCodeException;
import com.planner.planner.Service.AuthenticationCodeService;

@Service
public class AuthenticationCodeServiceImpl implements AuthenticationCodeService {

	private AuthenticationCodeDao authentcationCodeDao;

	public AuthenticationCodeServiceImpl(AuthenticationCodeDao authentcationCodeDao) {
		this.authentcationCodeDao = authentcationCodeDao;
	}

	@Override
	public boolean createPhoneAuthenticationCode(String phone, String code) {
		return authentcationCodeDao.createByPhone(phone, code);
	}

	@Override
	public boolean createEmailAuthenticationCode(String email, String code) {
		return authentcationCodeDao.createByEmail(email, code);
	}

	@Override
	public AuthenticationCodeDto findByPhone(String phone) {
		return authentcationCodeDao.findByPhone(phone);
	}

	@Override
	public AuthenticationCodeDto findByEmail(String email) {
		return authentcationCodeDao.findByEmail(email);
	}

	@Override
	public boolean codeCheck(AuthenticationCodeDto authenticationCodeDto) {
		boolean phoneCheck = (authenticationCodeDto.getPhone() != null && !authenticationCodeDto.getPhone().isEmpty());

		AuthenticationCodeDto authCodeDto = null;

		if (phoneCheck) {
			authCodeDto = this.authentcationCodeDao.findByPhone(authenticationCodeDto.getPhone());
		} 
		else {
			authCodeDto = this.authentcationCodeDao.findByEmail(authenticationCodeDto.getEmail());
		}

		if (authCodeDto == null) {
			throw new NotFoundAuthenticationCodeException("인증 코드가 전송되지 않았습니다. 다시 시도해 주세요.");
		}
		
		LocalDateTime nowDateTime = LocalDateTime.now();

		if (!authCodeDto.getExpireDate().isEqual(nowDateTime) && authCodeDto.getExpireDate().isBefore(nowDateTime)) {
			throw new AuthenticationCodeExpireException("인증 코드 시간이 만료되었습니다. 다시 시도하세요.");
		}

		if (authCodeDto.getCode().equals(authenticationCodeDto.getCode())) {
			if (phoneCheck) {
				this.authentcationCodeDao.updateCodeConfirmByPhone(authenticationCodeDto.getPhone());
			} 
			else {
				this.authentcationCodeDao.updateCodeConfirmByEmail(authenticationCodeDto.getEmail());
			}
		} 
		else {
			return false;
		}

		return true;
	}

	@Override
	public void delete(String phone) {
		this.authentcationCodeDao.delete(phone);
	}
}
