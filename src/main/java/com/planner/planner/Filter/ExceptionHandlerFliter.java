package com.planner.planner.Filter;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.planner.planner.Exception.InValidTokenException;
import com.planner.planner.Exception.TokenExpiredException;
import com.planner.planner.Util.ResponseMessage;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ExceptionHandlerFliter extends OncePerRequestFilter{
	
	private static final Logger log = LoggerFactory.getLogger(ExceptionHandlerFliter.class);

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		try {
			filterChain.doFilter(request, response);
		}
		catch (InValidTokenException e) {
			// JWT 검증 관련 예외
			log.error("JWT 검증 실패");
			setExceptionResponse(response, HttpServletResponse.SC_UNAUTHORIZED, new ResponseMessage(false, e.getMessage()));
		}
		catch (TokenExpiredException e) {
			// JWT 검증 관련 예외	
			log.error("JWT 시간 만료");
			setExceptionResponse(response, HttpServletResponse.SC_UNAUTHORIZED, new ResponseMessage(false, e.getMessage()));
		}
	}
	
	private void setExceptionResponse(HttpServletResponse response, int status, ResponseMessage message) throws JsonProcessingException, IOException {
		response.setStatus(status);
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(new ObjectMapper().writeValueAsString(message));
	}

}
