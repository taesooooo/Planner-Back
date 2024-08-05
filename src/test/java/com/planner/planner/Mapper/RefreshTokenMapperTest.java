package com.planner.planner.Mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.test.context.ActiveProfiles;

import com.planner.planner.Dto.RefreshTokenDto;

@ActiveProfiles("test")
@MybatisTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class RefreshTokenMapperTest {
	@Autowired
	private RefreshTokenMapper mapper;
	
	@DisplayName("리프래시 토큰 생성")
	@Test
	public void createRefreshToken() throws Exception {
		String testEmail = "test@naver.com";
		String testToken = "testToken";
		
		boolean result = mapper.createRefreshToken(testEmail, testToken);
		
		assertThat(result).isTrue();
	}
	
	@DisplayName("이메일로 리프래시 토큰 가져오기")
	@Test
	public void findByEmail() throws Exception {
		RefreshTokenDto testRefreshToken = RefreshTokenDto.builder()
				.id(1)
				.email("test@naver.com")
				.token("eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJwbGFubmVyIiwiaWF0IjoxNzAzOTE1ODE1fQ.iLobjjcY4pEUEnmHJhJNrg8-wddr_xxkrn7DtNRL2CM")
				.build();
		
		RefreshTokenDto findRefreshToken = mapper.findByEmail(testRefreshToken.getEmail());
		
		assertThat(findRefreshToken).usingRecursiveComparison()
				.isEqualTo(testRefreshToken);
	}
	
	@DisplayName("토큰으로 리프래시 토큰 가져오기")
	@Test
	public void findByToken() throws Exception {
		RefreshTokenDto testRefreshToken = RefreshTokenDto.builder()
				.id(1)
				.email("test@naver.com")
				.token("eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJwbGFubmVyIiwiaWF0IjoxNzAzOTE1ODE1fQ.iLobjjcY4pEUEnmHJhJNrg8-wddr_xxkrn7DtNRL2CM")
				.build();
		
		RefreshTokenDto findRefreshToken = mapper.findByToken(testRefreshToken.getToken());
		
		assertThat(findRefreshToken).usingRecursiveComparison()
				.isEqualTo(testRefreshToken);
	}
	
	@DisplayName("리프래시 토큰 수정")
	@Test
	public void updateRefreshToken() throws Exception {
		String testEmail = "test@naver.com";
		String testToken = "testToken";
		
		boolean result = mapper.updateRefreshToken(testEmail, testToken);
		
		assertThat(result).isTrue();
	}
	
	@DisplayName("리프래시 토큰 삭제")
	@Test
	public void deleteRefreshToken() throws Exception {
		String testEmail = "test@naver.com";
		
		boolean result = mapper.deleteRefreshToken(testEmail);
		
		assertThat(result).isTrue();
	}
}
