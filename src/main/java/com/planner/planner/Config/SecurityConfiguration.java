package com.planner.planner.Config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.planner.planner.Common.Security.AccessCheck;
import com.planner.planner.Dao.AccountDao;
import com.planner.planner.Dao.CommentDao;
import com.planner.planner.Dao.InvitationDao;
import com.planner.planner.Dao.NotificationDao;
import com.planner.planner.Dao.PlannerDao;
import com.planner.planner.Dao.ReviewDao;
import com.planner.planner.Filter.JwtAuthenticationFilter;
import com.planner.planner.Util.JwtUtil;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {
	
	private final ApplicationContext context;
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
				// 유저
				.requestMatchers("/api/users/{accountId}", "/api/users/{accountId}/**").access(new AccessCheck(getBean(AccountDao.class), "#accountId"))
				.requestMatchers("/api/users/search-member").authenticated()
				.requestMatchers("/api/users/find-email").permitAll()
				.requestMatchers("/api/users/find-password").permitAll()
				.requestMatchers("/api/users/change-password").permitAll()
				// 알림
				.requestMatchers(HttpMethod.POST,  "/api/notifications/{notificationId}/read").access(new AccessCheck(getBean(NotificationDao.class), "#notificationId"))
				.requestMatchers(HttpMethod.DELETE,  "/api/notifications/{notificationId}").access(new AccessCheck(getBean(NotificationDao.class), "#notificationId"))
				// 초대
				.requestMatchers("/api/invitation/{inviteId}/**").access(new AccessCheck(getBean(InvitationDao.class), "#inviteId"))
				// 플래너 - 메모, 일정, 여행지 포함
				.requestMatchers(HttpMethod.POST, "/api/planners/{plannerId}/like").permitAll()
				.requestMatchers(HttpMethod.POST, "/api/planners/{plannerId}/**").access(new AccessCheck(getBean(PlannerDao.class), "#plannerId"))
				.requestMatchers(HttpMethod.PATCH,  "/api/planners/{plannerId}/**").access(new AccessCheck(getBean(PlannerDao.class), "#plannerId"))
				.requestMatchers(HttpMethod.DELETE,  "/api/planners/{plannerId}/**").access(new AccessCheck(getBean(PlannerDao.class), "#plannerId"))
				// 리뷰
				.requestMatchers(HttpMethod.PATCH,  "/api/reviews/{reviewId}").access(new AccessCheck(getBean(ReviewDao.class), "#reviewId"))
				.requestMatchers(HttpMethod.DELETE,  "/api/reviews/{reviewId}").access(new AccessCheck(getBean(ReviewDao.class), "#reviewId"))
				// 리뷰 댓글
				.requestMatchers(HttpMethod.PATCH,  "/api/reviews/{reviewId}/comments/{commentId}").access(new AccessCheck(getBean(CommentDao.class), "#commentId"))
				.requestMatchers(HttpMethod.DELETE,  "/api/reviews/{reviewId}/comments/{commentId}").access(new AccessCheck(getBean(CommentDao.class), "#commentId"))
				// 여행지 - 없음
				
				// 기타
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
	
	private <T> T getBean(Class<T> clazz) {
		return context.getBean(clazz);
	}
}
