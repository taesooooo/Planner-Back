package com.planner.planner.Common.Security;

import java.util.function.Supplier;

import org.springframework.expression.EvaluationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;

import com.planner.planner.Dto.AccountDto;
import com.planner.planner.Exception.DataNotFoundException;
import com.planner.planner.Exception.UserNotFoundException;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class AccessCheck implements AuthorizationManager<RequestAuthorizationContext> {
	
	private UserIdentifierDao repository;
	private String expression;
	
	public AccessCheck(UserIdentifierDao repository, String DataIdExpression) {
		this.repository = repository;
		this.expression = DataIdExpression;
	}

	
	@Override
	public AuthorizationDecision check(Supplier<Authentication> authentication, RequestAuthorizationContext object) {
		EvaluationContext context = new StandardEvaluationContext();
		ExpressionParser parser = new SpelExpressionParser();
		object.getVariables().forEach((key, value) -> context.setVariable(key, value));
		
		int userId = getAccountId(authentication.get());
		int ownerId = getOwnership(parser.parseExpression(expression).getValue(context, Integer.class));
		
		if(userId == ownerId) {
			return new AuthorizationDecision(true);
		}
		
		return new AuthorizationDecision(false);
	}
	
	private int getAccountId(Authentication authentication) {
		Object principal = authentication.getPrincipal();
		if((principal instanceof String) && ((String)principal).equals("anonymousUser")) {
			throw new UserNotFoundException("로그인이 필요합니다.");
		}
		
		return ((AccountDto)principal).getAccountId();
	}
	
	private int getOwnership(int dataId) {
		UserIdentifier user;
		
		try {
			user = repository.findById(dataId);
			if(user == null) {
				throw new DataNotFoundException("해당하는 데이터를 찾을 수 없습니다.");
			}
			
			return user.getAccountId();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		return 0;
	}
}
