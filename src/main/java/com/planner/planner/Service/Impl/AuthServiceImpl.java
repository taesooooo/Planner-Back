package com.planner.planner.Service.Impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.planner.planner.Dao.AccountDao;
import com.planner.planner.Dao.RefreshTokenDao;
import com.planner.planner.Dto.AccountDto;
import com.planner.planner.Dto.LoginInfoDto;
import com.planner.planner.Dto.RefreshTokenDto;
import com.planner.planner.Dto.ReissueTokenDto;
import com.planner.planner.Exception.TokenNotFoundException;
import com.planner.planner.Exception.UserNotFoundException;
import com.planner.planner.Exception.PasswordCheckFailException;
import com.planner.planner.Service.AuthService;
import com.planner.planner.Util.JwtUtil;

@Service
public class AuthServiceImpl implements AuthService {
	private static final Logger LOGGER = LoggerFactory.getLogger(AuthServiceImpl.class);

	private AccountDao accountDao;
	private BCryptPasswordEncoder passwordEncoder;
	private JwtUtil jwtUtil;
	private RefreshTokenDao refreshTokenDao;

	public AuthServiceImpl(AccountDao accountDao, BCryptPasswordEncoder passwordEncoder, JwtUtil jwtUtil,
			RefreshTokenDao refreshTokenDao) {
		this.accountDao = accountDao;
		this.passwordEncoder = passwordEncoder;
		this.jwtUtil = jwtUtil;
		this.refreshTokenDao = refreshTokenDao;
	}

	@Override
	public boolean register(AccountDto accountDto) {
		return accountDao.create(accountDto);
	}

	@Override
	public LoginInfoDto login(AccountDto accountDto) throws Exception {
		AccountDto user = accountDao.findByEmail(accountDto.getEmail());
		if (user == null) {
			throw new UserNotFoundException("아이디 또는 비빌먼호를 잘못 입력했습니다.");
		}

		if (!passwordEncoder.matches(accountDto.getPassword(), user.getPassword())) {
			throw new PasswordCheckFailException();
		}

		String accessToken = jwtUtil.createAccessToken(user.getAccountId());
		String refreshToken = jwtUtil.createRefreshToken();

		RefreshTokenDto refreshTokenDto = refreshTokenDao.findByEmail(user.getEmail());
		if (refreshTokenDto == null) {
			refreshTokenDao.createRefreshToken(user.getEmail(), refreshToken);
		}
		else {
			refreshTokenDao.updateRefreshToken(user.getEmail(), refreshToken);
		}

		LoginInfoDto loginDto = LoginInfoDto.builder()
				.user(user)
				.accessToken(accessToken)
				.refreshToken(refreshToken)
				.build();

		return loginDto;
	}

	@Override
	public boolean logout(int userId) throws Exception {
		AccountDto user = accountDao.findById(userId);
		if (user == null) {
			throw new UserNotFoundException("사용자를 찾을 수 없습니다.");
		}
		
		RefreshTokenDto refreshTokenDto = refreshTokenDao.findByEmail(user.getEmail());
		if (refreshTokenDto != null) {
			refreshTokenDao.deleteRefreshToken(user.getEmail());
		}
		
		return true;
	}

	@Override
	public ReissueTokenDto reissueToken(String refreshToken) throws Exception {
		// 리프레시 토큰을 이용해 토큰 정보를 가져옴
		RefreshTokenDto refreshTokenDto = refreshTokenDao.findByToken(refreshToken);
		if(refreshTokenDto == null) {
			throw new TokenNotFoundException("해당하는 토큰 정보가 없습니다. 다시 로그인을 진행하세요.");
		}
		
		// 토큰 정보에 이메일을 이용해 계정 정보 가져옴
		AccountDto user = accountDao.findByEmail(refreshTokenDto.getEmail());
		if(user == null) {
			throw new UserNotFoundException("해당 하는 계정이 존재 하지 않습니다.");
		}
		
		// 계정 정보를 이용해 토큰 재발급
		String newAccessToken = jwtUtil.createAccessToken(user.getAccountId());
		String newRefreshToken = jwtUtil.createRefreshToken();

		refreshTokenDao.updateRefreshToken(user.getEmail(), newRefreshToken);
		
		ReissueTokenDto reissueTokenDto = ReissueTokenDto.builder()
				.accessToken(newAccessToken)
				.refreshToken(newRefreshToken)
				.build();
		
		return reissueTokenDto;
	}

}
