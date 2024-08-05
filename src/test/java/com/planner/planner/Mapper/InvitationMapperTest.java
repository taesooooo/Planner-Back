package com.planner.planner.Mapper;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.test.context.ActiveProfiles;

import com.planner.planner.Dto.InvitationDto;

@ActiveProfiles("test")
@MybatisTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class InvitationMapperTest {
	@Autowired
	private InvitationMapper mapper;
	
	@DisplayName("멤버 초대")
	@Test
	public void createInvitation() throws Exception {
		InvitationDto testInvite = InvitationDto.builder()
				.accountId(1)
				.plannerId(1)
				.build();
		
		mapper.createInvitation(testInvite);
		
		assertThat(testInvite.getId()).isEqualTo(4);
	}
	
	@DisplayName("초대 아이디로 초대 정보 가져오기")
	@Test
	public void findById() throws Exception {
		InvitationDto testInvite = InvitationDto.builder()
				.id(1)
				.accountId(1)
				.plannerId(2)
				.build();
		
		InvitationDto findInvite = mapper.findById(1);
		
		assertThat(findInvite).usingRecursiveComparison()
				.ignoringFields("inviteDate", "expireDate")
				.isEqualTo(testInvite);
		
		assertThat(findInvite.getInviteDate()).isExactlyInstanceOf(LocalDateTime.class);
		assertThat(findInvite.getExpireDate()).isAfter(LocalDateTime.now());
	}
	
	@DisplayName("초대 삭제")
	@Test
	public void deleteById() throws Exception {
		int result = mapper.deleteById(1);
		
		assertThat(result).isEqualTo(1);
	}
}
