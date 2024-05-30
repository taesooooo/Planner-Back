package com.planner.planner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import java.util.Map;
import java.util.function.Supplier;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;

import com.planner.planner.Common.Security.AccessCheck;
import com.planner.planner.Dao.Impl.AccountDaoImpl;
import com.planner.planner.Dto.AccountDto;
import com.planner.planner.Exception.UserNotFoundException;

@ExtendWith(MockitoExtension.class)
class AccessCheckTest {
	@Mock
	private AccountDaoImpl testDao;
	
	
	@BeforeEach
	void setUp() throws Exception {
	}

	@DisplayName("권한 확인 성공")
	@Test
	void accessCheck() {
		AccountDto user = AccountDto.builder()
				.accountId(1)
				.build();
		// user 재사용
		Supplier<Authentication> authentication = ()-> new UsernamePasswordAuthenticationToken(user, null);
		RequestAuthorizationContext context = new RequestAuthorizationContext(null, Map.of("accountId", "1"));
		
		when(testDao.findById(anyInt())).thenReturn(user);
		
		AccessCheck accessCheck = new AccessCheck(testDao, "#accountId");
		AuthorizationDecision decision = accessCheck.check(authentication, context);
		
		assertThat(decision.isGranted()).isTrue();
	}
	
	@DisplayName("로그인 정보에서 유저 정보를 찾지 못했을 경우")
	@Test
	void accessCheckException() {
		AccountDto user = AccountDto.builder()
				.accountId(1)
				.build();
		// user 재사용
		Supplier<Authentication> authentication = ()-> new AnonymousAuthenticationToken("key", "anonymousUser", AuthorityUtils.createAuthorityList("ROLE_ANONYMOUS"));
		RequestAuthorizationContext context = new RequestAuthorizationContext(null, Map.of("accountId", "1"));
		
//		when(testDao.findById(anyInt())).thenReturn(user);
		
		AccessCheck accessCheck = new AccessCheck(testDao, "#accountId");
		
		assertThatThrownBy(() -> accessCheck.check(authentication, context))
				.isExactlyInstanceOf(UserNotFoundException.class);
	}

}
