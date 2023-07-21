package com.planner.planner.Service.Impl;

import org.springframework.stereotype.Service;

import com.planner.planner.Dao.AuthenticationCodeDao;
import com.planner.planner.Dto.AuthenticationCodeDto;
import com.planner.planner.Exception.NotFoundAuthenticationCodeException;
import com.planner.planner.Service.AuthenticationCodeService;
import com.planner.planner.Service.SENSService;
import com.planner.planner.Util.RandomCode;

@Service
public class AuthenticationCodeServiceImpl implements AuthenticationCodeService {
	
	private AuthenticationCodeDao authentcationCodeDao;
	private SENSService sensService;
	
	private RandomCode randomCode;
	
	public AuthenticationCodeServiceImpl(AuthenticationCodeDao authentcationCodeDao, SENSService sensService, RandomCode randomCode) {
		this.authentcationCodeDao = authentcationCodeDao;
		this.sensService = sensService;
		this.randomCode = randomCode;
	}

	@Override
	public boolean codeSend(String phone) throws Exception {
		String code = randomCode.createCode();
		
		boolean isInserted = this.authentcationCodeDao.insert(phone, code);
		if(!isInserted) {
			return false;
		}
		
		return this.sensService.authenticationCodeSMSSend(phone, code);
	}

	@Override
	public boolean codeCheck(AuthenticationCodeDto authenticationCodeDto) {
		AuthenticationCodeDto authCodeDto =  this.authentcationCodeDao.find(authenticationCodeDto.getPhone());
		if(authCodeDto == null) {
			throw new NotFoundAuthenticationCodeException("인증 코드가 전송되지 않았습니다. 다시 시도해 주세요.");
		}
		
		if(!authCodeDto.getCode().equals(authenticationCodeDto.getCode())) {
			return false;
		}
		
		authentcationCodeDao.delete(authenticationCodeDto.getPhone());
		
		return true;
	}

	@Override
	public void delete(String phone) {
		this.authentcationCodeDao.delete(phone);
	}

}
