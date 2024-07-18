package com.planner.planner.Mapper;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.context.ActiveProfiles;

import com.planner.planner.Dto.AccountDto;

@ActiveProfiles("test")
// 테스트 데이터 초기화 안함
// @MybatisTest(properties = {"spring.sql.init.data-locations="})
@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class AccountMapperTest {
	
	@Autowired
	private AccountMapper mapper;
	@DisplayName("사용자 생성")
	@Test
	public void createTest() {
		AccountDto createUser = AccountDto.builder()
			.email("testtest@naver.com")
			.password("test")
			.username("test")
			.nickname("nick")
			.phone("01012345678")
			.image("test-image")
			.build();
		
		boolean dto = mapper.create(createUser);
		
		assertThat(dto).isTrue();
	}
	
	@DisplayName("사용자 ID로 사용자 가져오기")
	@Test
	public void findByIdTest() {
		AccountDto user = AccountDto.builder()
				.accountId(1)
				.email("test@naver.com")
				.password("$2a$10$ykMiqUMCB2bWWPdcJiToQOIEgkGM/plhUZu9Yc522pfoWQif8gWue")
				.username("테스트")
				.nickname("test")
				.phone("01012345678")
				.image("")
				.createDate(LocalDateTime.parse("2022-10-29 20:42:02", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
				.updateDate(LocalDateTime.parse("2022-10-29 20:42:02", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
				.authorities(Arrays.asList(new SimpleGrantedAuthority("ADMIN"), new SimpleGrantedAuthority("USER")))
				.build();
		
		AccountDto findUser = mapper.findById(1);
		
		assertThat(findUser).isNotNull()
			.usingRecursiveComparison()
			.isEqualTo(user);
	}
	
	@DisplayName("이메일로 사용자 가져오기")
	@Test
	public void findByEmailTest() {
		AccountDto user = AccountDto.builder()
				.accountId(1)
				.email("test@naver.com")
				.password("$2a$10$ykMiqUMCB2bWWPdcJiToQOIEgkGM/plhUZu9Yc522pfoWQif8gWue")
				.username("테스트")
				.nickname("test")
				.phone("01012345678")
				.image("")
				.createDate(LocalDateTime.parse("2022-10-29 20:42:02", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
				.updateDate(LocalDateTime.parse("2022-10-29 20:42:02", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
				.authorities(Arrays.asList(new SimpleGrantedAuthority("ADMIN"), new SimpleGrantedAuthority("USER")))
				.build();
		
		AccountDto findUser = mapper.findByEmail("test@naver.com");
		
		assertThat(findUser).isNotNull()
		.usingRecursiveComparison()
		.isEqualTo(user);
	}

	@DisplayName("닉네임으로 사용자 가져오기")
	@Test
	public void findByNickNameTest() {
		AccountDto user = AccountDto.builder()
				.accountId(1)
				.email("test@naver.com")
				.password("$2a$10$ykMiqUMCB2bWWPdcJiToQOIEgkGM/plhUZu9Yc522pfoWQif8gWue")
				.username("테스트")
				.nickname("test")
				.phone("01012345678")
				.image("")
				.createDate(LocalDateTime.parse("2022-10-29 20:42:02", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
				.updateDate(LocalDateTime.parse("2022-10-29 20:42:02", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
				.authorities(Arrays.asList(new SimpleGrantedAuthority("ADMIN"), new SimpleGrantedAuthority("USER")))
				.build();
		
		AccountDto findUser = mapper.findByNickName("test");
		
		assertThat(findUser).isNotNull()
			.usingRecursiveComparison()
			.isEqualTo(user);
	}
	
	@DisplayName("이름과 휴대폰 번호로 사용자 가져오기")
	@Test
	public void findByNameAndPhoneTest() {
		List<AccountDto> users = Arrays.asList(
				AccountDto.builder()
				.accountId(1)
				.email("test@naver.com")
				.password("$2a$10$ykMiqUMCB2bWWPdcJiToQOIEgkGM/plhUZu9Yc522pfoWQif8gWue")
				.username("테스트")
				.nickname("test")
				.phone("01012345678")
				.image("")
				.createDate(LocalDateTime.parse("2022-10-29 20:42:02", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
				.updateDate(LocalDateTime.parse("2022-10-29 20:42:02", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
				.authorities(null)
				.build());
		
		List<AccountDto> findUser = mapper.findByNameAndPhone("테스트", "01012345678");
		
		assertThat(findUser).isNotNull()
			.usingRecursiveComparison()
			.isEqualTo(users);
	}
	
	@DisplayName("휴대폰 번호로 사용자 아이디(이메일) 가져오기")
	@Test
	public void findEmailByPhoneTest() {
		List<String> emails = Arrays.asList(
				"test@naver.com", 
				"test2@naver.com", 
				"test3@naver.com");
		
		List<String> findUserEmail = mapper.findEmailByPhone("01012345678");
		
		assertThat(findUserEmail).isNotNull()
			.usingRecursiveComparison()
			.isEqualTo(emails);
	}
	
	@DisplayName("계정 수정")
	@Test
	public void accountUpdateTest() {
//		AccountDto updateUser = AccountDto.builder()
//				.nickname("nick")
//				.phone("01012345678")
//				.build();
			
		boolean dto = mapper.update(1, "updateNick", "01056781234");
		
		assertThat(dto).isTrue();
	}
	
	@DisplayName("계정 삭제")
	@Test
	public void deleteByEmailTest() {
		boolean dto = mapper.deleteByEmail("test@naver.com");
		
		assertThat(dto).isTrue();
	}
	
	@DisplayName("계정 이미지 변경")
	@Test
	public void imageUpdateTest() {
			
		boolean dto = mapper.accountImageUpdate(1, "ImageUpdateTest");
		
		assertThat(dto).isTrue();
	}
	
	@DisplayName("계정 비밀번호 변경")
	@Test
	public void passwordUpdateTest() {
			
		boolean dto = mapper.passwordUpdate(1, "UpdatePassword - Encrypt");
		
		assertThat(dto).isTrue();
	}
}
