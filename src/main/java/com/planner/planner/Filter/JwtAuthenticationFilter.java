package com.planner.planner.Filter;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.GenericFilterBean;

import com.planner.planner.Exception.InValidTokenException;
import com.planner.planner.Exception.TokenExpiredException;
import com.planner.planner.Exception.TokenNotFoundException;
import com.planner.planner.Util.JwtUtil;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends GenericFilterBean {
	
	private static final Logger log = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

	private final JwtUtil jwtUtil;
	private final UserDetailsService userDetailsService;
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// 토큰확인
		String bearerToken = ((HttpServletRequest)request).getHeader("Authorization");
		String token = jwtUtil.seperateToken(bearerToken);
		if (token != null) {
			jwtUtil.verifyToken(token);				
				
			Integer id = jwtUtil.getUserId(token);
			request.setAttribute("userId", id);
			
			UserDetails userDetails = userDetailsService.loadUserByUsername(id.toString());
			
			Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());					
			SecurityContextHolder.getContext().setAuthentication(authentication);
		}
		
		chain.doFilter(request, response);

	}

}
