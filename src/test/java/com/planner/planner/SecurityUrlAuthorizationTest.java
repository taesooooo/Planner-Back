package com.planner.planner;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.planner.planner.Filter.JwtAuthenticationFilter;
import com.planner.planner.Util.JwtUtil;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
@DisplayName("URI 접근 제한 테스트")
public class SecurityUrlAuthorizationTest {
	@Autowired
	private WebApplicationContext context;

	private MockMvc mockMvc;

	@Autowired
	private JwtUtil jwtUtil;
	@Autowired
	private UserDetailsService userDetailsService;

	private ObjectMapper mapper = new ObjectMapper();

	private String token;

	@BeforeEach
	public void setUp() {
		JwtAuthenticationFilter jwtFilter = new JwtAuthenticationFilter(jwtUtil, userDetailsService);
		this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
//				.addFilter(jwtFilter)
				.apply(springSecurity())
				.build();
		token = "Bearer " + jwtUtil.createAccessToken(2);
	}
	
	@DisplayName("POST uri - 접근 제한 ")
	@ParameterizedTest
	@MethodSource("postUriParameters")
	public void postAccessDenied(String uri, Object ...id) throws Exception {
		this.mockMvc.perform(post(uri, id)
				.accept(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8")
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", token)
				)
		.andDo(print())
		.andExpect(status().isForbidden());

	}
	
	private static Stream<Arguments> postUriParameters() {
		return Stream.of(
				Arguments.of("/api/notifications/{notificationId}/read", new Integer[] {1}),
				Arguments.of("/api/invitation/{inviteId}/accept", new Integer[] {1}),
				Arguments.of("/api/planners/{plannerId}/memos", new Integer[] {1}),
				Arguments.of("/api/planners/{plannerId}/invite-member", new Integer[] {1}),
				Arguments.of("/api/planners/{plannerId}/plans", new Integer[] {1}),
				Arguments.of("/api/planners/{plannerId}/plans/{planId}/plan-locations", new Integer[] {1, 1})
//				Arguments.of("/api/reviews/{reviewId}/comments", new Integer[] {1})
				);
	}
	
	@DisplayName("PATCH uri - 접근 제한 ")
	@ParameterizedTest
	@MethodSource("patchUriParameters")
	public void patchAccessDenied(String uri, Object ...id) throws Exception {
		this.mockMvc.perform(patch(uri, id)
				.accept(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8")
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", token))
		.andDo(print())
		.andExpect(status().isForbidden());
	}
	
	private static Stream<Arguments> patchUriParameters() {
		return Stream.of(
				Arguments.of("/api/users/{accountId}", new Integer[] {1}),
				Arguments.of("/api/users/{accountId}/images", new Integer[] {1}),
				Arguments.of("/api/invitation/{inviteId}/accept", new Integer[] {1}),
				Arguments.of("/api/planners/{plannerId}", new Integer[] {1}),
				Arguments.of("/api/planners/{plannerId}/memos/{memoId}", new Integer[] {1, 1}),
				Arguments.of("/api/planners/{plannerId}/plans/{planId}", new Integer[] {1, 1}),
				Arguments.of("/api/planners/{plannerId}/plans/{planId}/plan-locations/{planLocationId}", new Integer[] {1, 1, 1}),
				Arguments.of("/api/reviews/{reviewId}", new Integer[] {1}),
				Arguments.of("/api/reviews/{reviewId}/comments/{commentId}", new Integer[] {1, 1})
				);
	}
	
	@DisplayName("DELETE uri - 접근 제한 ")
	@ParameterizedTest
	@MethodSource("deleteUriParameters")
	public void deleteAccessDenied(String uri, Object ...id) throws Exception {
		this.mockMvc.perform(delete(uri, id)
				.accept(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8")
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", token))
		.andDo(print())
		.andExpect(status().isForbidden());
	}
	
	private static Stream<Arguments> deleteUriParameters() {
		return Stream.of(
				Arguments.of("/api/notifications/{notificationId}", new Integer[] {1}),
				Arguments.of("/api/invitation/{inviteId}/reject", new Integer[] {1}),
				Arguments.of("/api/planners/{plannerId}", new Integer[] {1}),
				Arguments.of("/api/planners/{plannerId}/delete-member", new Integer[] {1}),
				Arguments.of("/api/planners/{plannerId}/memos/{memoId}", new Integer[] {1, 1}),
				Arguments.of("/api/planners/{plannerId}/plans/{planId}", new Integer[] {1, 1}),
				Arguments.of("/api/planners/{plannerId}/plans/{planId}/plan-locations/{planLocationId}", new Integer[] {1, 1, 1}),
				Arguments.of("/api/reviews/{reviewId}", new Integer[] {1}),
				Arguments.of("/api/reviews/{reviewId}/comments/{commentId}", new Integer[] {1, 1})
				);
	}
}
