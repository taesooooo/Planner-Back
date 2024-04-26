package com.planner.planner.Config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest.H2ConsoleRequestMatcher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.planner.planner.Filter.JwtAuthenticationFilter;
import com.planner.planner.Service.Impl.UserDetailsServiceImpl;
import com.planner.planner.Util.JwtUtil;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {
	
	private final JwtUtil jwtUtil;
	private final UserDetailsService userDetailsService;

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
		httpSecurity
		// csrf 비활성화
		.csrf(csrf -> csrf.disable())
		// 세션 상태 없음으로 세션 생성 비활성화
		.sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
		// 요청에 대한 인증 규칙 설정(요청 메소드마다 권한확인이 필요하기 때문에 메소드 보안 방법을 사용한다.)
		.authorizeHttpRequests(auth -> auth
				.requestMatchers(PathRequest.toH2Console()).permitAll()
				.requestMatchers("/api/auth/**").permitAll()
				.requestMatchers("/api/upload/**").permitAll()
				.anyRequest().authenticated())
		.headers(headers -> headers.frameOptions(f -> f.sameOrigin()))
		// fromLogin, httpBaisc 로그인 방식 비활성화
		.formLogin(formLogin -> formLogin.disable())
		.httpBasic(httpBasic -> httpBasic.disable())
		.addFilterBefore(new JwtAuthenticationFilter(jwtUtil, userDetailsService), UsernamePasswordAuthenticationFilter.class);
		
		return httpSecurity.build();
	}
	
	@Bean
	public BCryptPasswordEncoder bcryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
//	@Bean
//	public UserDetailsService userDetailsService() {
//		return new UserDetailsServiceImpl();
//	}
}
