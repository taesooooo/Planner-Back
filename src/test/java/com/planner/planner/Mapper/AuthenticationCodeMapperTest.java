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

import com.planner.planner.Dto.AuthenticationCodeDto;

@ActiveProfiles("test")
@MybatisTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class AuthenticationCodeMapperTest {
	@Autowired
	private AuthenticationCodeMapper mapper;
	
	@DisplayName("휴대폰 인증 코드 생성")
	@Test
	public void createByPhone() {
		String phone = "01012345678";
		String code = "123456";
		boolean result = mapper.createByPhone(phone, code);
		
		assertThat(result).isTrue();
	}
	
	@DisplayName("이메일 인증 코드 생성")
	@Test
	public void createByEmail() {
		String email = "testEmail";
		String code = "123456";
		boolean result = mapper.createByEmail(email, code);
		
		assertThat(result).isTrue();
	}
	
	@DisplayName("휴대폰 번호로 인증 코드 가져오기")
	@Test
	public void findByPhone() {
		AuthenticationCodeDto authCode = AuthenticationCodeDto.builder()
				.id(1)
				.phone("01012345678")
				.code("123456")
				.codeConfirm(false)
				.expireDate(LocalDateTime.of(2024, 07, 28, 11, 03, 00))
				.createDate(null)
				.build();
		
		AuthenticationCodeDto findAuthCode = mapper.findByPhone("01012345678");
		
		assertThat(findAuthCode).usingRecursiveComparison()
				.ignoringFields("createDate")
				.isEqualTo(authCode);
		
		assertThat(findAuthCode.getCreateDate()).isNotNull();
	}
	
	@DisplayName("이메일로 인증 코드 가져오기")
	@Test
	public void findByEmail() {
		AuthenticationCodeDto authCode = AuthenticationCodeDto.builder()
				.id(2)
				.email("testEmail")
				.code("123456")
				.codeConfirm(false)
				.expireDate(LocalDateTime.of(2024, 07, 28, 11, 03, 00))
				.createDate(null)
				.build();
		
		AuthenticationCodeDto findAuthCode = mapper.findByEmail("testEmail");
		
		assertThat(findAuthCode).usingRecursiveComparison()
				.ignoringFields("createDate")
				.isEqualTo(authCode);
	
		assertThat(findAuthCode.getCreateDate()).isNotNull();
	}
	
	@DisplayName("휴대폰 인증코드 확인")
	@Test
	public void updateCodeConfirmByPhone() {
		boolean result = mapper.updateCodeConfirmByPhone("01012345678");
		
		assertThat(result).isTrue();
	}
	
	@DisplayName("이메일 인증코드 확인")
	@Test
	public void updateCodeConfirmByEmail() {
		boolean result = mapper.updateCodeConfirmByPhone("01012345678");
		
		assertThat(result).isTrue();
	}
	
	@DisplayName("휴대폰번호로 인증 코드 삭제")
	@Test
	public void deleteByPhone() {
		boolean result = mapper.deleteByPhone("01012345678");
		
		assertThat(result).isTrue();
	}
}
