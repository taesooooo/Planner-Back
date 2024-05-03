package com.planner.planner.Interceptor;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import com.planner.planner.Config.Properites.CommonProperties;
import com.planner.planner.Exception.NotFoundToken;
import com.planner.planner.Exception.TokenCheckFailException;
import com.planner.planner.Util.JwtUtil;

@SpringBootTest(classes = {JwtUtil.class, CommonProperties.class})
@EnableConfigurationProperties
public class TokenInterceptorTest {
	
	private TokenInterceptor tokenInterceptor;
	@Autowired
	private JwtUtil jwtUtil;

	@BeforeEach
	public void setUp() throws Exception {
//		this.jwtUtil = new JwtUtil("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
		this.tokenInterceptor = new TokenInterceptor(jwtUtil);
	}
	
	@Test
	public void 토큰_없는경우() throws Exception {
		MockHttpServletRequest req = new MockHttpServletRequest("GET", "/test");
		MockHttpServletResponse res = new MockHttpServletResponse();
		
		assertThatThrownBy(() -> {
			tokenInterceptor.preHandle(req, res, null);			
		})
		.isInstanceOf(NotFoundToken.class)
		.hasMessage("로그인이 필요합니다.");
	}
	
	@Test
	public void 엑세스_토큰_날짜만료() throws Exception {
		String expireAccessToken = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJwbGFubmVyIiwiaWF0IjoxNzAzODI5MTAzLCJleHAiOjE3MDM4MjkxMDMsInVzZXJJZCI6MX0.gQ5sBc0j72i7xfgS-6bi8tlxRXihTllp5k7eHPFTv9Y";
//		String expireRefreshToken = "eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJwbGFubmVyIiwiaWF0IjoxNzAzODI5MTA1LCJleHAiOjE3MDQ5NTIzMDV9.w2ZPMtRy2vVKKYMUUIUE15Bs-kx9gpWoS4wjkH5uP9Y";
		
		MockHttpServletRequest req = new MockHttpServletRequest("GET", "/test");
		req.addHeader("Authorization", expireAccessToken);
		MockHttpServletResponse res = new MockHttpServletResponse();
		
		
		assertThatThrownBy(() -> {
			tokenInterceptor.preHandle(req, res, null);			
		})
		.isInstanceOf(TokenCheckFailException.class)
		.hasMessage("유효기간이 만료되었습니다.");
	}

	@Test
	public void 잘못된_토큰인경우() throws Exception {
		String expireAccessToken = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJwbGFubmVyIiwiaWF0IjoxNzAzODI5MTAzLCJleHAiOjE3MDM4MjkxMDMsInVzZXJJZCI6MX0.1111";
		MockHttpServletRequest req = new MockHttpServletRequest("GET", "/test");
		req.addHeader("Authorization", expireAccessToken);
		MockHttpServletResponse res = new MockHttpServletResponse();
		
		
		assertThatThrownBy(() -> {
			tokenInterceptor.preHandle(req, res, null);			
		})
		.isInstanceOf(TokenCheckFailException.class)
		.hasMessage("검증에 실패헀습니다.");
	}
}
